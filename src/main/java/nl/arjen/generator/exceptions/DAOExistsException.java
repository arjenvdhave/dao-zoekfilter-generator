package nl.arjen.generator.exceptions;

public class DAOExistsException extends ParseException
{

	private static final long serialVersionUID = 1L;

	public DAOExistsException(String daoName)
	{
		super(String.format(
			"DAO %s bestaat al, set override op true in de config om deze te vervangen", daoName));
	}

}
