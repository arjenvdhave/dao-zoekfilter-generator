package nl.arjen.generator.exceptions;

public class ZFExistsException extends ParseException
{

	private static final long serialVersionUID = 1L;

	public ZFExistsException(String zfName)
	{
		super(String.format(
			"ZoekFilter %s bestaat al, set override op true in de config om deze te vervangen",
			zfName));
	}

}
