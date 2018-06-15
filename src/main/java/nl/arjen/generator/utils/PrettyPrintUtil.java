package nl.arjen.generator.utils;

import java.util.stream.Stream;

public class PrettyPrintUtil
{
	public static String print(final Object object)
	{

		int largestNameLength = Stream.of(object.getClass().getDeclaredFields()).mapToInt(f -> {
			f.setAccessible(true);
			return f.getName().length();
		}).max().orElse(0);

		StringBuilder builder = new StringBuilder(object.getClass().getSimpleName());
		builder.append("[");
		builder.append(System.lineSeparator());
		Stream.of(object.getClass().getDeclaredFields()).forEach(f -> {
			f.setAccessible(true);
			builder.append("\t");
			builder.append(f.getName());
			builder.append(separator(largestNameLength, f.getName()));
			try
			{
				builder.append(f.get(object));
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				builder.append("");
			}
			builder.append(System.lineSeparator());
		});
		builder.append("]");
		return builder.toString();
	}

	private static String separator(int largestNameLength, String field)
	{
		if (field.length() == largestNameLength)
			return "\t=\t";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < largestNameLength - field.length(); i++)
			builder.append(" ");
		return builder.append("\t=\t").toString();
	}
}
