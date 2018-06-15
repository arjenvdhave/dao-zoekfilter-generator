package nl.arjen.generator.config;

import org.apache.commons.lang3.StringUtils;

import nl.arjen.generator.exceptions.ConfigValidationException;

public class ConfigValidator
{
	public static boolean validate(Config config) throws ConfigValidationException
	{
		if (config == null)
			throw new ConfigValidationException("No configuration found");

		if (StringUtils.isEmpty(config.getEntitiesFullSrcPath()))
			throw new ConfigValidationException(
				"entitiesFullSrcPath is a required field and cannot be empty");

		if (StringUtils.isEmpty(config.getEntitiesPackage()))
			throw new ConfigValidationException(
				"entitiesPackage is a required field and cannot be empty");

		if (StringUtils.isEmpty(config.getTargetFullSrcPath()))
			throw new ConfigValidationException(
				"targetFullSrcPath is a required field and cannot be empty");

		if (StringUtils.isEmpty(config.getDaoPackage()))
			throw new ConfigValidationException(
				"daoPackage is a required field and cannot be empty");

		if (StringUtils.isEmpty(config.getZfPackage()))
			throw new ConfigValidationException(
				"zfPackage is a required field and cannot be empty");

		if (StringUtils.isEmpty(config.getDaoSuperPackage()))
			throw new ConfigValidationException(
				"daoSuperPackage is a required field and cannot be empty");

		if (StringUtils.isEmpty(config.getDaoSuperClassname()))
			throw new ConfigValidationException(
				"daoSuperClassname is a required field and cannot be empty");

		if (StringUtils.isEmpty(config.getZfSuperPackage()))
			throw new ConfigValidationException(
				"zfSuperPackage is a required field and cannot be empty");

		if (StringUtils.isEmpty(config.getZfSuperClassname()))
			throw new ConfigValidationException(
				"zfSuperClassname is a required field and cannot be empty");

		return true;
	}
}
