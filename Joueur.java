public class Joueur
{
	// Variables d'instances
	
	private String nom;
	private Case roi;
	private boolean couleur;
	private boolean KRoque;
	private boolean QRoque;
	
	// Constructeur
	
	public Joueur(String nom, boolean couleur) // Par défaut la case du roi est rempli à celle du début de jeu
	{
		this.nom = nom;
		this.couleur = couleur;
		if (couleur) this.roi = new Case(0,4); else this.roi = new Case(7,4);
	}
	
	// Getters
	
	public String getNom()
	{
		return this.nom;
	}
	
	public Case getRoi()
	{
		return this.roi;
	}
	
	public boolean getKRoque()
	{
		return this.KRoque;
	}
	
	public boolean getQRoque()
	{
		return this.QRoque;
	}
	
	public boolean getCouleur()
	{
		return this.couleur;
	}
	
	// Setters
	
	public void setKRoque(boolean bol)
	{
		this.KRoque = bol;
	}

	public void setQRoque(boolean bol)
	{
		this.QRoque = bol;
	}	
	
	public void setRoi(Case c)
	{
		this.roi = c;
	}
	
}