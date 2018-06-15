package nl.arjen.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.lang.model.element.Modifier;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import nl.arjen.generator.config.Config;
import nl.arjen.generator.exceptions.DAOExistsException;
import nl.arjen.generator.exceptions.EntityNotFoundException;
import nl.arjen.generator.exceptions.MultipleEntitiesFoundException;
import nl.arjen.generator.exceptions.ParseException;
import nl.arjen.generator.exceptions.ZFExistsException;

public class DAOZoekfilterGenerator
{

	private static final String ESCAPED_DOT_REGEX = "\\.";

	private Config config;

	private boolean verbose = false;

	public DAOZoekfilterGenerator(Config config)
	{
		this(config, false);
	}

	public DAOZoekfilterGenerator(Config config, boolean verbose)
	{
		this.config = config;
		this.verbose = verbose;
	}

	public void generate(String entity, String entitySubPackage) throws ParseException
	{
		List<Path> entities = getEntities();
		List<Path> matches = entities.stream()
			.filter(path -> path.getFileName().toString().equals(formatEntity(entity)))
			.collect(Collectors.toList());
		if (matches.size() == 0)
			throw new EntityNotFoundException(entity);

		if (matches.size() > 1 && StringUtils.isBlank(entitySubPackage))
			throw new MultipleEntitiesFoundException(entity, matches);

		final Path path;
		if (matches.size() > 1 && StringUtils.isNotBlank(entitySubPackage))
		{
			path = entities.stream()
				.filter(p -> p.toString().equals(generateFullEntityPath(entity, entitySubPackage)))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException(entity));
		}
		else
		{
			path = matches.get(0);
		}

		createDaoAndZf(path);
	}

	private String formatEntity(String entity)
	{
		if (entity.endsWith(".java"))
			return entity;
		return entity + ".java";
	}

	private String generateFullEntityPath(String entity, String entitySubPackage)
	{
		return getEntitiesPath() + File.separator
			+ entitySubPackage.replaceAll(ESCAPED_DOT_REGEX, File.separator) + File.separator
			+ formatEntity(entity);
	}

	private void createDaoAndZf(Path path) throws ParseException
	{

		String entityClassName = FilenameUtils.getBaseName(path.getFileName().toString());

		int nameCountBasePackage = Paths
			.get(config.getEntitiesFullSrcPath() + File.separator
				+ config.getEntitiesPackage().replaceAll(ESCAPED_DOT_REGEX, File.separator))
			.getNameCount();

		Path pathOfEntity = path.subpath(nameCountBasePackage, path.getNameCount());

		String entitySubPackage = "";
		if (pathOfEntity.getNameCount() > 1)
		{
			entitySubPackage = "."
				+ pathOfEntity.subpath(0, pathOfEntity.getNameCount() - 1).toString().replaceAll(
					File.separator, ".");
		}

		String daoName = entityClassName + "DAO";
		String zfName = entityClassName + "ZoekFilter";

		String daoFileName = config.getTargetFullSrcPath() + File.separator
			+ config.getDaoPackage().replaceAll(ESCAPED_DOT_REGEX, File.separator) + File.separator
			+ daoName + ".java";

		String zfFileName = config.getTargetFullSrcPath() + File.separator
			+ config.getZfPackage().replaceAll(ESCAPED_DOT_REGEX, File.separator) + File.separator
			+ zfName + ".java";

		if (Paths.get(daoFileName).toFile().exists() && !config.isOverride())
			throw new DAOExistsException(daoName);

		if (Paths.get(zfFileName).toFile().exists() && !config.isOverride())
			throw new ZFExistsException(zfName);

		TypeSpec zf = TypeSpec.classBuilder(zfName)
			.superclass(ParameterizedTypeName.get(
				ClassName.get(config.getZfSuperPackage(), config.getZfSuperClassname()),
				ClassName.get(config.getEntitiesPackage() + entitySubPackage, entityClassName)))
			.addModifiers(Modifier.PUBLIC)
			.build();

		TypeSpec dao =
			TypeSpec.classBuilder(daoName)
				.superclass(ParameterizedTypeName
					.get(ClassName.get(config.getDaoSuperPackage(), config.getDaoSuperClassname()),
						ClassName.get(config.getEntitiesPackage() + entitySubPackage,
							entityClassName),
						ClassName.get(config.getZfPackage(), zfName)))
				.addModifiers(Modifier.PUBLIC)
				.addAnnotation(Stateless.class)
				.addAnnotation(AnnotationSpec.builder(TransactionAttribute.class)
					.addMember("value", "$T.MANDATORY", TransactionAttributeType.class)
					.build())
				.build();

		JavaFile daoJavaFile = JavaFile.builder(config.getDaoPackage(), dao).build();
		JavaFile zfJavaFile = JavaFile.builder(config.getZfPackage(), zf).build();
		try
		{
			if (verbose)
			{
				System.out.println("--------------- Generated DAO ---------------");
				daoJavaFile.writeTo(System.out);
				System.out.println("---------------------------------------------");
				System.out.println(System.lineSeparator());
			}
			daoJavaFile.writeTo(FileSystems.getDefault().getPath(config.getTargetFullSrcPath()));

			if (verbose)
			{
				System.out.println("--------------- Generated ZOEKFILTER ---------------");
				zfJavaFile.writeTo(System.out);
				System.out.println("----------------------------------------------------");
				System.out.println(System.lineSeparator());
			}
			zfJavaFile.writeTo(FileSystems.getDefault().getPath(config.getTargetFullSrcPath()));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private List<Path> getEntities()
	{

		try (Stream<Path> paths = Files.walk(Paths.get(getEntitiesPath())))
		{
			return paths.filter(Files::isRegularFile)
				.filter(FileSystems.getDefault().getPathMatcher("glob:**/*.java")::matches)
				.collect(Collectors.toList());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private String getEntitiesPath()
	{
		String path = config.getEntitiesFullSrcPath().endsWith(File.separator)
			? config.getEntitiesFullSrcPath() : config.getEntitiesFullSrcPath() + File.separator;
		String packageToDir =
			config.getEntitiesPackage().replaceAll(ESCAPED_DOT_REGEX, File.separator);

		return path + packageToDir;
	}
}
