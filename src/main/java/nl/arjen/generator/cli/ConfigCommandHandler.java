package nl.arjen.generator.cli;

import nl.arjen.generator.config.Config;
import nl.arjen.generator.config.ConfigParser;
import nl.arjen.generator.config.ConfigValidator;
import nl.arjen.generator.exceptions.ConfigParseException;
import nl.arjen.generator.exceptions.ConfigValidationException;

public class ConfigCommandHandler
{

	public Config handle(CLICommands commands)
	{
		if (commands.doesNotHaveOption(CLIOption.CONFIG))
		{
			System.err.println(String.format(
				"You need to provide a config file with option -%s or --%s. Run -%s or --%s for help.",
				CLIOption.CONFIG.getCmd(), CLIOption.CONFIG.getCmdLong(), CLIOption.HELP.getCmd(),
				CLIOption.HELP.getCmdLong()));
			System.exit(1);
		}

		try
		{
			ConfigParser converter = new ConfigParser(commands.getOptionValue(CLIOption.CONFIG));
			Config config = converter.parse();
			ConfigValidator.validate(config);
			if (commands.hasOption(CLIOption.VALIDATE_CONFIG))
			{
				System.out.println("Configuration is valid\n" + config.toDescription());
				System.exit(0);
			}
			return config;
		}
		catch (ConfigParseException | ConfigValidationException e)
		{
			System.err.println("Config file is not valid");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

}
