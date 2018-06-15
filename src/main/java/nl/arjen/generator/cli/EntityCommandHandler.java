package nl.arjen.generator.cli;

import java.util.List;

public class EntityCommandHandler
{

	public List<String> getEntities(CLICommands commands)
	{
		return commands.getOptionValues(CLIOption.ENTITY);
	}
}
