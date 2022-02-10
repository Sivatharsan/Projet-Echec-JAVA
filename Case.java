public class Case
{
	// Variable d'instance
		
	private int ligne;
	private int colonne;
	private Piece piece;
	
	// Constructeur
	
	public Case(int i,int j)
	{
		this.ligne = i;
		this.colonne = j;
		this.piece = null;
	}
	
	// Getters
	
	public int getLigne()
	{
		return this.ligne;
	}
	
	public int getColonne()
	{
		return this.colonne;
	}
	
	public Piece getPiece()
	{
		return this.piece;
	}
	
	// Setters
	
	public void setPiece(Piece p)
	{
		this.piece = p;
	}
	
	// Methode
	
	public void majPieceCase(Joueur jo)
	{
		Case nouvCase = new Case(this.ligne, this.colonne);
		this.piece.setCase(nouvCase);
		if (this.piece instanceof Roi)
		{
			jo.setRoi(nouvCase);
		}
	}
	
}