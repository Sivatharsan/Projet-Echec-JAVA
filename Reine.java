public class Reine extends Piece{
	
	// Constructeur
	
	public Reine(Case pos, boolean c)
	{
		super(pos,c);
	}
	
	// Methodes
		
	public boolean deplacementsPossibles(Case c, Plateau pl)	// la case c DOIT venir du plateau
	{
		int departL = this.getLigne();
		int departC = this.getColonne();
		int arriveeL = c.getLigne();
		int arriveeC = c.getColonne();
		
		if 	( !(	(departL == arriveeL)
					||	(departC == arriveeC)
					||	(Math.abs(departL - arriveeL) == Math.abs(departC - arriveeC))
			   )
			)
		{
			return false;
		}
		
		if (departL == arriveeL)
		{
			if (departC > arriveeC)
			{
				for (int i = departC-1; i>arriveeC; i--) { if (pl.getCase(departL,i).getPiece() != null) return false; }
			}
			else
			{
				for (int i = departC+1; i<arriveeC; i++) { if (pl.getCase(departL,i).getPiece() != null) return false; }
			}
			return true;
		}
		if (departC == arriveeC)
		{
			if (departL > arriveeL)
			{
				for (int i = departL-1; i>arriveeL; i--) { if (pl.getCase(i,departC).getPiece() != null) return false; }
			}
			else
			{
				for (int i = departL+1; i<arriveeL; i++) { if (pl.getCase(i,departC).getPiece() != null) return false; }
			}
			return true;
		}
		
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