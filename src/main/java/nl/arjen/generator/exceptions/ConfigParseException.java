package nl.arjen.generator.exceptions;

public class ConfigParseException extends ParseException
{

	private static final long serialVersionUID = 1L;

	public ConfigParseException(String message)
	{
		super(message);
	}

	public ConfigParseException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
