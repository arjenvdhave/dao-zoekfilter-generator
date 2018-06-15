package nl.arjen.generator.cli;

import java.util.stream.Stream;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public enum CLIOption
{
	// "Print the CLI options"
	HELP(Option.builder("?").longOpt("help").desc("Print the CLI options").hasArg(false).build()),

	CONFIG(Option.builder("c")
		.longOpt("config")
		.desc("Configuration file, path to the json file containing the configuration")
		.hasArg(true)
		.build()),

	INTERACIVE(Option.builder("i")
		.longOpt("interactive")
		.desc(
			"Interactive, use the console input to generate DAO's and ZoekFilters instead of using a configuration file")
		.hasArg(false)
		.build()),

	VALIDATE_CONFIG(Option.builder("vc")
		.longOpt("validate-config")
		.desc("Validate Configuration, read parse and validate the provided config file")
		.hasArg(false)
		.build()),

	VERBOSE(Option.builder("v")
		.longOpt("verbose")
		.desc("Verbose, print all stacktraces")
		.hasArg(false)
		.build()),

	ENTITY(Option.builder("e")
		.longOpt("entities")
		.desc(
			"Comma seperated list of entities for which the DAO and Zoekfilters should be generated")
		.hasArgs()
		.valueSeparator(',')
		.build()),;

	private Option option;

	private CLIOption(Option option)
	{
		this.option = option;
	}

	public String getCmd()
	{
		return option.getOpt();
	}

	public String getCmdLong()
	{
		return option.getLongOpt();
	}

	public static Options toOptions()
	{
		Options options = new Options();
		Stream.of(CLIOption.values()).forEach(o -> options.addOption(o.option));
		return options;
	}
}
