public class Fou extends Piece
{
	// Constructeur
	
	public Fou(Case pos, boolean c)
	{
		super(pos,c);
	}
	
	// Methodes
	
	public boolean deplacementsPossibles(Case c, Plateau pl)	// la case c DOIT venir du plateau
	{
		
		int departL = this.getLigne(); //1
		int departC = this.getColonne(); //3
		int arriveeL = c.getLigne(); //3
		int arriveeC = c.getColonne(); //5
		
		if 	( Math.abs(this.getLigne() - c.getLigne()) != Math.abs(this.getColonne() - c.getColonne()) ) return false;

		if ( departL > arriveeL && departC > arriveeC )
		{
			for (int i=1;i<departL-arriveeL;i++)
			{
				if ( pl.getCase(departL-i,departC-i).getPiece() != null ) return false;
			}
			return true;
		}
		
		if ( departL > arriveeL && departC < arriveeC )
		{
			for (int i=1;i<departL-arriveeL;i++)
			{
				if ( pl.getCase(departL-i,departC+i).getPiece() != null ) return false;
			}
			return true;
		}
		
		if ( departL < arriveeL && departC > arriveeC )
		{
			for (int i=1;i<arriveeL-departL;i++)
			{
				if ( pl.getCase(departL+i,departC-i).getPiece() != null ) return false;
			}
			return true;
		}

		if ( departL < arriveeL && departC < arriveeC )
		{
			for (int i=1;i<arriveeL-departL;i++)
			{
				if ( pl.getCase(departL+i,departC+i).getPiece() != null ) return false;
			}
			return true;
		}
		
		return false;
	}
}


