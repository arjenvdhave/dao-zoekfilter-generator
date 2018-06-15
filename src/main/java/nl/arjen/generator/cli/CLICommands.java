package nl.arjen.generator.cli;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

public class CLICommands extends CommandLine
{

	private static final long serialVersionUID = 1L;

	private CommandLine commandLine;

	public CLICommands(CommandLine commandLine)
	{
		this.commandLine = commandLine;
	}

	public boolean hasOption(CLIOption option)
	{
		return commandLine.hasOption(option.getCmd());
	}

	public boolean doesNotHaveOption(CLIOption option)
	{
		return !commandLine.hasOption(option.getCmd());
	}

	public String getOptionValue(CLIOption option)
	{
		return commandLine.getOptionValue(option.getCmd());
	}

	public List<String> getOptionValues(CLIOption option)
	{
		if (!commandLine.hasOption(option.getCmd()))
			return Collections.emptyList();

		return Arrays.asList(commandLine.getOptionValues(option.getCmd()));
	}

	public static CLICommands parseCommandLineArgs(String[] args) throws ParseException
	{
		CommandLineParser parser = new DefaultParser();
		return new CLICommands(parser.parse(CLIOption.toOptions(), args));
	}

}
