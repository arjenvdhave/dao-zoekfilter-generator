package nl.arjen.generator.exceptions;

public class ConfigValidationException extends ParseException
{

	private static final long serialVersionUID = 1L;

	public ConfigValidationException(String message)
	{
		super(message);
	}

}
