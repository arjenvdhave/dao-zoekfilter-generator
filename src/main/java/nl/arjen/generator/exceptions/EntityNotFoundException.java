package nl.arjen.generator.exceptions;

public class EntityNotFoundException extends ParseException
{
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String entity)
	{
		super(String.format("Entity [%s] niet gevonden", entity));
	}

	public EntityNotFoundException(String entity, String entitySubPackage)
	{
		super(String.format("Entity [%s] niet gevonden in sub package [%s]", entity,
			entitySubPackage));
	}

}
