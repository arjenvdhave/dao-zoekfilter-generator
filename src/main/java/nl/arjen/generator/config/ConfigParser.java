package nl.arjen.generator.config;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.arjen.generator.exceptions.ConfigParseException;

public class ConfigParser
{
	private String location;

	public ConfigParser(String location) throws ConfigParseException
	{
		if (StringUtils.isEmpty(location))
			throw new ConfigParseException("No config file provided");

		this.location = location;
	}

	public Config parse() throws ConfigParseException
	{
		Path configPath = Paths.get(location);
		if (!configPath.toFile().exists())
			throw new ConfigParseException(String.format("[%s] does not exist", location));

		ObjectMapper mapper = new ObjectMapper();
		try
		{
			return mapper.readValue(configPath.toFile(), Config.class);
		}
		catch (IOException e)
		{
			throw new ConfigParseException("Could not parse configuration", e);
		}
	}
}
