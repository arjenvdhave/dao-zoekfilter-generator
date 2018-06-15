package nl.arjen.generator.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nl.arjen.generator.utils.PrettyPrintUtil;

public class Config
{
	private String entitiesFullSrcPath;

	private String entitiesPackage;

	private String targetFullSrcPath;

	private String daoPackage;

	private String zfPackage;

	private String daoSuperPackage;

	private String daoSuperClassname;

	private String zfSuperPackage;

	private String zfSuperClassname;

	private boolean override = false;

	@JsonCreator
	public Config(
			@JsonProperty(value = "entitiesFullSrcPath",
					required = true) String entitiesFullSrcPath,
			@JsonProperty(value = "entitiesPackage", required = true) String entitiesPackage,
			@JsonProperty(value = "targetFullSrcPath", required = true) String targetFullSrcPath,
			@JsonProperty(value = "daoPackage", required = true) String daoPackage,
			@JsonProperty(value = "zfPackage", required = true) String zfPackage,
			@JsonProperty(value = "daoSuperPackage", required = true) String daoSuperPackage,
			@JsonProperty(value = "daoSuperClassname", required = true) String daoSuperClassname,
			@JsonProperty(value = "zfSuperPackage", required = true) String zfSuperPackage,
			@JsonProperty(value = "zfSuperClassname", required = true) String zfSuperClassname,
			@JsonProperty(value = "override", defaultValue = "false") boolean override)
	{
		this.entitiesFullSrcPath = entitiesFullSrcPath;
		this.entitiesPackage = entitiesPackage;
		this.targetFullSrcPath = targetFullSrcPath;
		this.daoPackage = daoPackage;
		this.zfPackage = zfPackage;
		this.daoSuperPackage = daoSuperPackage;
		this.daoSuperClassname = daoSuperClassname;
		this.zfSuperPackage = zfSuperPackage;
		this.zfSuperClassname = zfSuperClassname;
		this.override = override;
	}

	/**
	 * Full path of source folder containing the entities without the entities package.<br/>
	 * So for
	 * <code>/some/workspace/folder/src/main/java/nl/package/procuct/entities/Person.java</code>
	 * this would be <code>/some/workspace/folder/src/main/java</code>
	 */
	public String getEntitiesFullSrcPath()
	{
		return entitiesFullSrcPath;
	}

	/**
	 * The package containing the entities.<br/>
	 * For <code>/some/workspace/folder/src/main/java/nl/package/procuct/entities/Person.java</code>
	 * this would be <code>nl.package.product.entities</code>
	 */
	public String getEntitiesPackage()
	{
		return entitiesPackage;
	}

	/**
	 * Full path of target folder for the generated DAO and ZoekFilters.<br/>
	 * For <code>/some/workspace/folder/src/main/java/nl/package/procuct/dao/PersonDAO.java</code>
	 * this would be <code>/some/workspace/folder/src/main/java</code>
	 */
	public String getTargetFullSrcPath()
	{
		return targetFullSrcPath;
	}

	/**
	 * The package where the DAO should be generated.<br/>
	 * For <code>/some/workspace/folder/src/main/java/nl/package/procuct/dao/PersonDAO.java</code>
	 * this would be <code>nl.package.product.dao</code>
	 */
	public String getDaoPackage()
	{
		return daoPackage;
	}

	/**
	 * The package where the ZoekFilter should be generated.<br/>
	 * For
	 * <code>/some/workspace/folder/src/main/java/nl/package/procuct/filter/PersonDAO.java</code>
	 * this would be <code>nl.package.product.filter</code>
	 */
	public String getZfPackage()
	{
		return zfPackage;
	}

	/**
	 * The package containing the super class for the generated DAO.
	 */
	public String getDaoSuperPackage()
	{
		return daoSuperPackage;
	}

	/**
	 * The classname of the super class for the generated DAO.
	 */
	public String getDaoSuperClassname()
	{
		return daoSuperClassname;
	}

	/**
	 * The package containing the super class for the generated ZoekFilter.
	 */
	public String getZfSuperPackage()
	{
		return zfSuperPackage;
	}

	/**
	 * The classname of the super class for the generated ZoekFilter.
	 */
	public String getZfSuperClassname()
	{
		return zfSuperClassname;
	}

	/**
	 * Override existing DAO and ZF with generated class
	 */
	public boolean isOverride()
	{
		return override;
	}

	public String toDescription()
	{
		return PrettyPrintUtil.print(this);

	}
}
