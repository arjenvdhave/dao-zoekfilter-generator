package nl.arjen.generator.cli;

import java.util.Scanner;

public class InteractiveCommandHandler
{

	private static final String EXIT_STATEMENT = "exit";

	private final Scanner scanner;

	public InteractiveCommandHandler()
	{
		scanner = new Scanner(System.in);
	}

	public boolean isInteractive(CLICommands commands)
	{
		return commands.hasOption(CLIOption.INTERACIVE);
	}

	public String getEntity()
	{
		String entity = null;

		System.out.println(String.format(
			"For which entity (classname) should a DAO and zoekfilter be created?\nWhen there are multiple entities with the same name add the subpackage of the entity seperated by a semicolon e.a. 'landelijk.setting:SomeSetting.java'\nType %s to quit",
			EXIT_STATEMENT));

		if (scanner.hasNextLine())
			entity = scanner.nextLine();

		if (entity == null || entity.isEmpty())
		{
			System.out.println("No entity provided");
			return getEntity();
		}

		if (entity.equalsIgnoreCase(EXIT_STATEMENT))
		{
			System.exit(1);
		}

		return entity;

	}
}
