public abstract class Piece
{
	// Variable d'instance
	
	private boolean couleur;	// true pour blanc, false pour noir
	private Case position;

	// Constructeur
	
	public Piece(Case pos, boolean c)
	{
		this.position = pos;
		this.couleur = c;
	}
	
	// Getters
	
	public int getLigne()
	{
		return this.position.getLigne();
	}
	
	public int getColonne()
	{
		return this.position.getColonne();
	}	
	
	public boolean getCouleur()
	{
		return this.couleur;
	}
	
	public Case getCase()
	{
		return this.position;
	}
	
	public Case getPosition()
	{
		return this.position;
	}
	
	// Setters
	
	public void setCase(Case c)
	{
		this.position = c;
	}
	
	//Méthodes
	
	public abstract boolean deplacementsPossibles(Case c, Plateau pl);
}