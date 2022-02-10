public class Cavalier extends Piece
{
	// Constructeur
	
	public Cavalier(Case pos, boolean c)
	{
		super(pos,c);
	}
	
	// Methodes
	
	public boolean deplacementsPossibles(Case c, Plateau pl){
		
		int departL = this.getLigne();
        int departC = this.getColonne();
        int arriveeL = c.getLigne();
        int arriveeC = c.getColonne();
		
		if 	(			((Math.abs(departL - arriveeL) == 2)
					&&	(Math.abs(departC - arriveeC) == 1))
				||		((Math.abs(departL - arriveeL) == 1)
					&&	(Math.abs(departC - arriveeC) == 2))){
			return true;
		}else{
			return false;
}}}