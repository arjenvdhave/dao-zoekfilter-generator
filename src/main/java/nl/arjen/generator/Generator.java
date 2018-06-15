package nl.arjen.generator;

import nl.arjen.generator.cli.CLICommands;
import nl.arjen.generator.cli.CLIOption;
import nl.arjen.generator.cli.ConfigCommandHandler;
import nl.arjen.generator.cli.EntityCommandHandler;
import nl.arjen.generator.cli.HelpCommandHandler;
import nl.arjen.generator.cli.InteractiveCommandHandler;
import nl.arjen.generator.config.Config;
import nl.arjen.generator.exceptions.ParseException;

public class Generator
{

	private static DAOZoekfilterGenerator daoZoekfilterGenerator;

	private static final HelpCommandHandler helpCommandHandler = new HelpCommandHandler();

	private static final ConfigCommandHandler configCommandHandler = new ConfigCommandHandler();

	private static final InteractiveCommandHandler interactiveCommandHandler =
		new InteractiveCommandHandler();

	private static final EntityCommandHandler entityCommandHandler = new EntityCommandHandler();

	public static void main(String[] args)
	{
		CLICommands commands = parseCommandLineArgs(args);
		helpCommandHandler.handle(commands);

		Config config = configCommandHandler.handle(commands);
		daoZoekfilterGenerator =
			new DAOZoekfilterGenerator(config, commands.hasOption(CLIOption.VERBOSE));

		if (interactiveCommandHandler.isInteractive(commands))
		{
			generateInteractive(interactiveCommandHandler.getEntity());
		}
		else
		{
			entityCommandHandler.getEntities(commands).forEach(Generator::generate);
		}

	}

	private static CLICommands parseCommandLineArgs(String[] args)
	{
		try
		{
			return CLICommands.parseCommandLineArgs(args);
		}
		catch (org.apache.commons.cli.ParseException e)
		{
			System.err.println("Could not parse input parameters");
			e.printStackTrace();
		}
		return null;
	}

	private static void generate(String entity)
	{
		String subPackage = null;
		if (entity.contains(":"))
		{
			subPackage = entity.substring(0, entity.indexOf(":"));
			entity = entity.substring(entity.indexOf(":") + 1, entity.length());
		}
		System.out.println("Generating for: " + entity);
		try
		{
			daoZoekfilterGenerator.generate(entity, subPackage);
			System.out.println("DAO and Zoekfilter generated");
		}
		catch (ParseException e)
		{
			System.out.println(e.getMessage());
		}
	}

	private static void generateInteractive(String entity)
	{
		generate(entity);
		generateInteractive(interactiveCommandHandler.getEntity());
	}

}
