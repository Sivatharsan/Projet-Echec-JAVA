public class Pion extends Piece{
	
	// Constructeur
	
	public Pion(Case pos, boolean c)
	{
		super(pos,c);
	}
	
	// Methodes
	
	public boolean deplacementsPossibles(Case c, Plateau pl){
		
		int departL = this.getLigne();
        int departC = this.getColonne();
        int arriveeL = c.getLigne();
        int arriveeC = c.getColonne();

		if	((departC == arriveeC)
			&& 	((this.getCouleur() && (pl.getCase(departL+1,departC).getPiece() == null))
				||	(!(this.getCouleur()) && (pl.getCase(departL-1,departC).getPiece() == null))))
		{
			if (this.getCouleur())
			{
				if ((arriveeL - departL == 1) || ((arriveeL - departL == 2) && (departL == 1)))
				{return true;}
				else return false;
			}
			else
			{
				if ((departL - arriveeL == 1) || ((departL - arriveeL == 2) && (departL == 6)))
				{return true;}
				else return false;
			}
		}
		else 
		{
			if ((Math.abs(departC - arriveeC) == 1) && (pl.getCase(arriveeL,arriveeC).getPiece() != null))
			{
				if (this.getCouleur())
				{
					if (arriveeL - departL == 1) return true; else return false;
				}
				else
				{
					if (departL - arriveeL == 1) return true; else return false;
				}
			}
			else return false;	//else if pour prise en passant (demande de changer la sauvergarde)		
		}
	}
}