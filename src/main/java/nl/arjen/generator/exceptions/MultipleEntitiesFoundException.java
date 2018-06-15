package nl.arjen.generator.exceptions;

import java.nio.file.Path;
import java.util.List;

public class MultipleEntitiesFoundException extends ParseException
{
	private static final long serialVersionUID = 1L;

	public MultipleEntitiesFoundException(String entity, List<Path> matches)
	{
		super(formatException(entity, matches));
	}

	private static String formatException(String entity, List<Path> matches)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Meerdere entitietien gevonden met deze naam [%s]", entity));

		builder.append(System.lineSeparator());
		matches.stream().map(Path::toString).forEach(
			p -> builder.append(p).append(System.lineSeparator()));

		return builder.toString();
	}

}
