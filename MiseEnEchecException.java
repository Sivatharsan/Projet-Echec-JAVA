public class MiseEnEchecException extends UnauthorizedException
{
	// Constructeur
	
	public MiseEnEchecException()
	{
		super("Ce coup n'est pas permis car il vous met en echec !");
	}

}
