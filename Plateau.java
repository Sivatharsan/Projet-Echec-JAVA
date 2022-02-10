public class Plateau
{
	// Variable d'instance
	
	private Case[][] plateau;	// Ligne puis Colonne
	
	// Constructeur
	
	public Plateau()
	{
		this.plateau = new Case[8][8];
		for (int i=0;i<8;i++)
		{
			for (int j=0;j<8;j++)
			{
				this.plateau[i][j] = new Case(i,j);
			}
		}
	}
	
	// Getters
	
	public Case getCase(int i, int j)
	{
		return plateau[i][j];
	}
	
	public Case getCase(String a1) throws UnauthorizedException
	{
		int i; //possible erreur
		int j;
		
		UnauthorizedException ue = new UnauthorizedException("Ce n'est pas une case du plateau. Utilisez le format XY avec X une lettre et Y un chiffre");
		if ( a1.length() != 2) throw ue;
		
		if ( a1.charAt(1) > '8' || a1.charAt(1) < '1' ) throw ue;
		else i = a1.charAt(1)-'1';
		
		if ( a1.charAt(0) <= 'H' && a1.charAt(0) >= 'A' ) j = a1.charAt(0) - 'A';
		else 
		{
			if ( a1.charAt(0) <= 'h' && a1.charAt(0) >= 'a' ) j = a1.charAt(0) - 'a';
			else throw ue;
		}
		return this.getCase(i,j);
	}
	
	// Setters
	
	public void setCase(int i, int j, Piece p)
	{
		this.plateau[i][j].setPiece(p);
	}
	
	// Methodes
	
	public Piece isEchec(Joueur jo)
	{
		Case K_case = jo.getRoi();
		boolean colour = jo.getCouleur();
		
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				Piece current_piece = this.getCase(i,j).getPiece();
				if ( (current_piece != null) && (current_piece.getCouleur() != colour) )
				{
					if (current_piece.deplacementsPossibles(K_case,this)) return current_piece;
				}
			}
		}
		return null;
	}
	
	public boolean isEchec(Case K_case, boolean colour)
	{	
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				Piece current_piece = this.getCase(i,j).getPiece();
				if ( (current_piece != null) && (current_piece.getCouleur() != colour) )
				{
					if (current_piece.deplacementsPossibles(K_case,this)) return true;
				}
			}
		}
		return false;
	}
	
	/*public boolean isEchecEtMat(Joueur jo)
	{
		Piece pieceAttaquante = isEchec(jo);
		if(pieceAttaquante==null) return false;
		
		Case CaseAttaquante = pieceAttaquante.getCase();
		Case K_case = jo.getRoi();
		boolean couleur = jo.getCouleur();
		int K_colonne = K_case.getColonne();
		int K_ligne = K_case.getLigne();
		int A_colonne = CaseAttaquante.getColonne();
		int A_ligne = CaseAttaquante.getLigne();
		
		if (isEchec(CaseAttaquante, couleur)) return false;
		if ((pieceAttaquante instanceof Fou) || (pieceAttaquante instanceof Reine)) 
		{
			for (int i = 1 ; i<Math.abs(K_colonne - A_colonne) ; i++)//while ?
			{
				if (K_colonne > A_colonne && K_ligne > A_ligne) 
				{
					if (isEchec(this.getCase(K_ligne - i, K_colonne - i),!couleur)) return false;
				}
				else if (K_colonne < A_colonne && K_ligne > A_ligne)	
				{
					if (isEchec(this.getCase(K_ligne - i, K_colonne + i),!couleur)) return false;
				}
				else if (K_colonne > A_colonne && K_ligne < A_ligne)	
				{
					if (isEchec(this.getCase(K_ligne + i, K_colonne - i),!couleur)) return false;
				}
				else if (K_colonne < A_colonne && K_ligne < A_ligne)	
				{
					if (isEchec(this.getCase(K_ligne + i, K_colonne + i),!couleur)) return false;
				}
			}
		}
		if ((pieceAttaquante instanceof Tour) || (pieceAttaquante instanceof Reine)) 
		{
			if ((K_colonne != A_colonne) && (K_ligne == A_ligne)) 
			{
				for (int i = 1; i<Math.abs(K_colonne - A_colonne); i++)//while ?
				{
					if (K_colonne > A_colonne)
					{
						if (isEchec(this.getCase(K_ligne, K_colonne - i),!couleur)) return false;
					}
					else if (K_colonne < A_colonne)
					{
						if (isEchec(this.getCase(K_ligne, K_colonne + i),!couleur)) return false;
					}
				}
			}
			else if ((K_colonne == A_colonne) && (K_ligne != K_colonne))
			{
				for (int i = 1; i<Math.abs(K_ligne - A_ligne); i++)//while ?
				{
					if (K_ligne > A_ligne)
					{
						if (isEchec(this.getCase(K_ligne - i, K_colonne),!couleur)) return false;
					}
					else if (K_ligne < A_ligne)
					{
						if (isEchec(this.getCase(K_ligne + i, K_colonne),!couleur)) return false;
					}
				}
			}
		}
		for (int i=-1;i<=1;i++)
		{
			for (int j=-1;j<=1;j++)
			{
				if (	( K_ligne+i >= 0 && K_ligne+i < 8 )
					&&	( K_colonne+j >= 0 && K_colonne+j < 8 )
					&&	!( (this.getCase(K_ligne,K_colonne).getPiece() != null) && (this.getCase(K_ligne,K_colonne).getPiece().getCouleur() == couleur))
					&&	this.isEchec(new Case(K_ligne+i,K_colonne+i),couleur)
					)
					return false;
			}
		} 
		return true;
	}*/
	/*public boolean isPat(Joueur jo)
	{
		Case K_case = jo.getRoi();
		boolean couleur = jo.getCouleur();
		if (this.isEchec(K_case, couleur)) return false;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				Piece current_piece = this.getCase(i,j).getPiece();
				if ( (current_piece != null) && (current_piece.getCouleur() != couleur) )
				{
					for(int k=0;k<8;k++)
					{
						for(int l=0;l<8;l++)
						{
							Case current_case = this.getCase(k,l);
							if (current_piece.deplacementsPossibles(current_case,this)) return false;
						}
					}
				}
			}
		}
		return true;
	}*/
	
	public String toString()
	{
		String s = "";
		Piece p;
		
		for(int i=7;i>=0;i--)
		{
			s+=(i+1) +" | ";
			for(int j=0;j<8;j++)
			{
				p = this.getCase(i,j).getPiece();
				if (p == null) s+=" ";
				if (p instanceof Cavalier) { if (p.getCouleur()) s+="N"; else s+="n"; }
				if (p instanceof Fou) { if (p.getCouleur()) s+="B"; else s+="b"; }
				if (p instanceof Reine) { if (p.getCouleur()) s+="Q"; else s+="q"; }
				if (p instanceof Pion) { if (p.getCouleur()) s+="P"; else s+="p"; }
				if (p instanceof Tour) { if (p.getCouleur()) s+="R"; else s+="r"; }
				if (p instanceof Roi) { if (p.getCouleur()) s+="K"; else s+="k"; }
				s+=" | ";
			}
			s+= "\n";
		}
		s += "\n    A   B   C   D   E   F   G   H";
		return s;	
	}
}