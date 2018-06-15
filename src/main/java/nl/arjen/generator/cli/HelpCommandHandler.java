package nl.arjen.generator.cli;

import org.apache.commons.cli.HelpFormatter;

public class HelpCommandHandler
{

	public void handle(CLICommands commands)
	{
		if (commands.hasOption(CLIOption.HELP))
		{
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("DAO and ZoekFilter generator", CLIOption.toOptions());
			System.exit(0);
		}
	}

}
