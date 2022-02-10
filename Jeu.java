import java.util.Scanner;
import java.util.InputMismatchException;

public class Jeu
{
	// Variable d'instance
	
	public Joueur player_blanc;
	public Joueur player_noir;
	public Plateau echiquier;
	private boolean joueuractif; // true pour player_blanc, false pour player_noir
	
	// Constructeur
	
	public Jeu(Joueur player_blanc, Joueur player_noir, Plateau echiquier)
	{
		this.player_blanc = player_blanc;
		this.player_noir = player_noir;
		this.echiquier = echiquier;
		this.joueuractif = true;
	}
	
	public Jeu(Joueur player_blanc, Joueur player_noir, Plateau echiquier, boolean bol)
	{
		this.player_blanc = player_blanc;
		this.player_noir = player_noir;
		this.echiquier = echiquier;
		this.joueuractif = bol;
	}
	
	public Jeu(String nomJ1, String nomJ2)
	{
		this.player_blanc = new Joueur(nomJ1,true);
		this.player_noir = new Joueur(nomJ2,false);
		this.echiquier = new Plateau();
		this.init();
	}
	
	public Jeu(String nomJ1, String nomJ2, String save)
	{
		this.player_blanc = new Joueur(nomJ1,true);
		this.player_noir = new Joueur(nomJ2,false);
		this.echiquier = new Plateau();
		this.Charger(save);
	}		
	
	// Getters
	
	public boolean getJoueurActif() 
	{
		return this.joueuractif;
	}
	
	public boolean getKRoque(boolean joueur)	
	{
		if (joueur) return this.player_blanc.getKRoque(); else return this.player_noir.getKRoque();
	}

	public boolean getQRoque(boolean joueur)	
	{
		if (joueur) return this.player_blanc.getQRoque(); else return this.player_noir.getQRoque();
	}
	
	public Joueur getJoueur(boolean couleur)
	{
		if (couleur) return this.player_blanc; else return this.player_noir;
	}
	
	// Setters
	
	public void setKRoque(boolean joueur, boolean bol)
	{
		if (joueur) this.player_blanc.setKRoque(bol); else this.player_noir.setKRoque(bol);
	}

	public void setQRoque(boolean joueur, boolean bol)
	{
		if (joueur) this.player_blanc.setQRoque(bol); else this.player_noir.setQRoque(bol);
	}
		
	public void setJoueurActif(boolean bol)
	{
		this.joueuractif = bol;
	}
	
	// Methodes
	
	public boolean deplacementAutorise(Case depart, Case arrivee) // Les cases doivent-être des cases du plateau
	{
		return	(	( depart != arrivee )
				&&	( arrivee.getPiece() == null || arrivee.getPiece().getCouleur() != this.joueuractif )
				);
	}
	
	public void init()
	{
		this.Charger("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq");
	}
	
	public String Sauver()
	{
		Piece p;
		
		String s = "";
		int nullcompte = 0;
		
		for (int i=7;i>=0;i--) 
		{
			for (int j=0;j<=7;j++)
			{
				p = this.echiquier.getCase(i,j).getPiece();
				if (p == null) nullcompte++;
				else
				{
					if (nullcompte != 0) { s+=nullcompte; nullcompte = 0; }
					if (p instanceof Cavalier) { if (p.getCouleur()) s+="N"; else s+="n"; }
					if (p instanceof Fou) { if (p.getCouleur()) s+="B"; else s+="b"; }
					if (p instanceof Reine) { if (p.getCouleur()) s+="Q"; else s+="q"; }
					if (p instanceof Pion) { if (p.getCouleur()) s+="P"; else s+="p"; }
					if (p instanceof Tour) { if (p.getCouleur()) s+="R"; else s+="r"; }
					if (p instanceof Roi) { if (p.getCouleur()) s+="K"; else s+="k"; }
				}
			}
			if (nullcompte != 0) { s+=nullcompte; nullcompte = 0; }
			s+="/";
		}
		s = s.replaceFirst(".$","");
		if (this.getJoueurActif()) s+=" w "; else s+= " b ";
		if (this.getKRoque(true)) s+= "K" ;
		if (this.getQRoque(true)) s+= "Q";
		if (this.getKRoque(false)) s+= "k";
		if (this.getQRoque(false)) s+= "q";
		if (s.charAt(s.length()-1)==' ') s+="-";
		
	return s;
	}
	
	public void Charger(String sauvegarde)
	{
		int ligne = 0;
		int colonne = 7;
		int avancer = 1;
		int espaces = 0;
		boolean stop = false;
		boolean placement = false;
		for (int i = sauvegarde.length() - 1; i >= 0; i--){
			//roque et tour de jeu
			if (!placement)
			{	
				switch (sauvegarde.charAt(i)){
					case 'K': 
						this.setKRoque(true, true);
						break;
					case 'Q': 
						this.setQRoque(true, true);
						break;
					case 'k': 
						this.setKRoque(false, true);
						break;
					case 'q': 
						this.setQRoque(false, true);
						break;
					case 'w':
						this.setJoueurActif(true);
						break;
					case 'b':
						this.setJoueurActif(false);
						break;
					case ' ':
						espaces += 1;
						if (espaces == 2)
							placement = true;
						break;
				}
			}
			//placement des pièces
			else
			{
				if ((Character.getNumericValue(sauvegarde.charAt(i)) <= 8)&&(Character.getNumericValue(sauvegarde.charAt(i)) > 0))
					avancer = Character.getNumericValue(sauvegarde.charAt(i));
				
				switch (sauvegarde.charAt(i)){
					case '/':
						ligne += 1;
						colonne = 7;
						avancer = 0;
						break;
					case 'r':
						this.echiquier.setCase(ligne, colonne, new Tour(new Case(ligne,colonne),false));
						break;
					case 'n':
						this.echiquier.setCase(ligne, colonne, new Cavalier(new Case(ligne,colonne),false));
						break;
					case 'b':
						this.echiquier.setCase(ligne, colonne, new Fou(new Case(ligne,colonne),false));
						break;
					case 'q':
						this.echiquier.setCase(ligne, colonne, new Reine(new Case(ligne,colonne),false));
						break;
					case 'k':
						this.echiquier.setCase(ligne, colonne, new Roi(new Case(ligne,colonne),false));
						this.player_noir.setRoi(new Case(ligne, colonne));
						break;
					case 'p':
						this.echiquier.setCase(ligne, colonne, new Pion(new Case(ligne,colonne),false));
						break;
					case 'R':
						this.echiquier.setCase(ligne, colonne, new Tour(new Case(ligne,colonne),true));
						break;
					case 'N':
						this.echiquier.setCase(ligne, colonne, new Cavalier(new Case(ligne,colonne),true));
						break;
					case 'B':
						this.echiquier.setCase(ligne, colonne, new Fou(new Case(ligne,colonne),true));
						break;
					case 'Q':
						this.echiquier.setCase(ligne, colonne, new Reine(new Case(ligne,colonne),true));
						break;
					case 'K':
						this.echiquier.setCase(ligne, colonne, new Roi(new Case(ligne,colonne),true));
						this.player_blanc.setRoi(new Case(ligne, colonne));
						break;
					case 'P':
						this.echiquier.setCase(ligne, colonne, new Pion(new Case(ligne,colonne),true));
						break;
				}
				colonne -= avancer;
				avancer = 1;
			}
		}
	}
	
	public void setRoqueTourFalse(Case c, boolean joueur)
	{
		if (joueur)
		{
			if (c.getLigne() == 0 && c.getColonne() == 0)
			{
				setQRoque(joueur,false);
			}
			else if (c.getLigne() == 0 && c.getColonne() == 7)
			{
				setKRoque(joueur,false);
			}
		}
		else
		{
			if (c.getLigne() == 7 && c.getColonne() == 0)
			{
				setKRoque(joueur,false);
			}
			else if (c.getLigne() == 7 && c.getColonne() == 7)
			{
				setQRoque(joueur,false);
			}
		}
	}
	
	public void setRoqueRoiFalse(Case c, boolean joueur)
	{
		if ((joueur && c.getLigne() == 0 && c.getColonne() == 4) || (!joueur && c.getLigne() == 7 && c.getColonne() == 4))
		{
			setKRoque(joueur,false);
			setQRoque(joueur,false);
		}
	}
	
	public void Deplacements(Case depart, Case arrivee, Joueur jo) throws MiseEnEchecException, UnauthorizedException
	{
		boolean couleur = jo.getCouleur();
		if (deplacementAutorise(depart, arrivee) 
			&& depart.getPiece() != null
			&& depart.getPiece().getCouleur() == couleur
			&& depart.getPiece().deplacementsPossibles(arrivee, echiquier))
		{
			if (depart.getPiece() instanceof Roi && this.echiquier.isEchec(arrivee, couleur)) throw new MiseEnEchecException();
			if (arrivee.getPiece() instanceof Tour)
			{
				setRoqueTourFalse(arrivee, couleur);
			}
			if (depart.getPiece() instanceof Tour)
			{
				setRoqueTourFalse(depart, couleur);
			}
			if (depart.getPiece() instanceof Roi)
			{
				setRoqueRoiFalse(depart, couleur);
			}
			Case tmp_depart = depart;
			Case tmp_arrivee = arrivee;
			arrivee.setPiece(depart.getPiece());
			arrivee.majPieceCase(jo); // met à jour la case dans la pièce
			depart.setPiece(null);
			if (this.echiquier.isEchec(jo) != null)
			{
				depart.setPiece(arrivee.getPiece());
				arrivee.setPiece(tmp_arrivee.getPiece());
				depart.majPieceCase(jo);
				if (true) throw new MiseEnEchecException();
			if (tmp_arrivee.getPiece() instanceof Tour)
			{
				setRoqueTourFalse(tmp_arrivee, couleur);
			}
			if (tmp_depart.getPiece() instanceof Tour)
			{
				setRoqueTourFalse(tmp_depart, couleur);
			}
			if (tmp_depart.getPiece() instanceof Roi)
			{
				setRoqueRoiFalse(tmp_depart, couleur);
			}
			}	
		}
		else throw new UnauthorizedException("Ce deplacement n'est pas possible");
	}
	
	public boolean isQRoque(Joueur jo)
	{
		boolean couleur = jo.getCouleur();
		if (getQRoque(couleur))
		{
		if 	(couleur 
			&& this.echiquier.getCase(0,1).getPiece() == null
			&& this.echiquier.getCase(0,2).getPiece() == null
			&& this.echiquier.getCase(0,3).getPiece() == null
			&& !(this.echiquier.isEchec(this.echiquier.getCase(0,2),couleur))
			)
			return true;
		else if (!couleur
				&& this.echiquier.getCase(7,1).getPiece() == null
				&& this.echiquier.getCase(7,2).getPiece() == null
				&& this.echiquier.getCase(7,3).getPiece() == null
				&& !(this.echiquier.isEchec(this.echiquier.getCase(7,2),couleur))
				)
			return true;
		}
		return false;
	}
	
	public boolean isKRoque(Joueur jo)
	{
		boolean couleur = jo.getCouleur();
		if (getKRoque(couleur))
		{
			if 	(couleur 
				&& this.echiquier.getCase(0,5).getPiece() == null
				&& this.echiquier.getCase(0,6).getPiece() == null
				&& !(this.echiquier.isEchec(this.echiquier.getCase(0,6),couleur))
				)
				return true;
			else if (!couleur
					&& this.echiquier.getCase(7,5).getPiece() == null
					&& this.echiquier.getCase(7,6).getPiece() == null
					&& !(this.echiquier.isEchec(this.echiquier.getCase(7,6),couleur))
					)
				return true;
		}
		return false;
	}
	
	public boolean isRoque(Joueur jo)
	{
		return (this.isKRoque(jo) || this.isQRoque(jo));
	}
	
	public void doRoque(boolean cote, Joueur jo)
	{
		boolean couleur = jo.getCouleur();
		if (cote)
		{
			if (couleur)
			{
				setQRoque(couleur, false);
				setKRoque(couleur, false);
				(this.echiquier.getCase(0,0)).setPiece(null);
				(this.echiquier.getCase(0,4)).setPiece(null);
				(this.echiquier.getCase(0,3)).setPiece(new Tour(this.echiquier.getCase(0,3),couleur));
				(this.echiquier.getCase(0,2)).setPiece(new Roi(this.echiquier.getCase(0,2),couleur));
				jo.setRoi(this.echiquier.getCase(0,2));
			}
			else
			{
				setQRoque(couleur, false);
				setKRoque(couleur, false);
				(this.echiquier.getCase(7,0)).setPiece(null);
				(this.echiquier.getCase(7,4)).setPiece(null);
				(this.echiquier.getCase(7,3)).setPiece(new Tour(this.echiquier.getCase(7,3),couleur));
				(this.echiquier.getCase(7,2)).setPiece(new Roi(this.echiquier.getCase(7,2),couleur));
				jo.setRoi(this.echiquier.getCase(7,2));
			}
		}
		else
		{
			if (couleur)
			{
				setQRoque(couleur, false);
				setKRoque(couleur, false);
				(this.echiquier.getCase(0,7)).setPiece(null);
				(this.echiquier.getCase(0,4)).setPiece(null);
				(this.echiquier.getCase(0,5)).setPiece(new Tour(this.echiquier.getCase(0,5),couleur));
				(this.echiquier.getCase(0,6)).setPiece(new Roi(this.echiquier.getCase(0,6),couleur));
				jo.setRoi(this.echiquier.getCase(0,6));
			}
			else
			{
				setQRoque(couleur, false);
				setKRoque(couleur, false);
				(this.echiquier.getCase(7,7)).setPiece(null);
				(this.echiquier.getCase(7,4)).setPiece(null);
				(this.echiquier.getCase(7,5)).setPiece(new Tour(this.echiquier.getCase(7,5),couleur));
				(this.echiquier.getCase(7,6)).setPiece(new Roi(this.echiquier.getCase(7,6),couleur));
				jo.setRoi(this.echiquier.getCase(7,6));
			}
		}
	}
	
	public Case isPromotion(Joueur jo)
	{
		boolean couleur = jo.getCouleur();
		if (couleur)
		{
			for (int i=0 ; i>8 ; i++)
			{
				if (this.echiquier.getCase(7,i).getPiece() instanceof Pion) return this.echiquier.getCase(7,i);
			}
		}
		else
		{
			for (int i=0 ; i>8 ; i++)
			{
				if (this.echiquier.getCase(0,i).getPiece() instanceof Pion) return this.echiquier.getCase(0,i);
			}
		}
		return null;
	}
	
	public void doPromotion(Joueur jo, int pieceVoulue, Case c)
	{
		boolean couleur = jo.getCouleur();
		switch(pieceVoulue)
		{
			case 1:
				c.setPiece(new Tour(c, couleur));
				break;
			case 3:
				c.setPiece(new Cavalier(c, couleur));
				break;
			case 2:
				c.setPiece(new Fou(c, couleur));
				break;
			case 4:
				c.setPiece(new Reine(c, couleur));
				break;
		}
	}
	
	/*public boolean isEchecEtMat(boolean colour)
	{
		if (colour) return this.echiquier.isEchecEtMat(player_blanc);
		else return this.echiquier.isEchecEtMat(player_noir);
	}
	
	public boolean isPat(boolean colour)
	{	
		if (colour) return this.echiquier.isPat(player_blanc);
		else return this.echiquier.isPat(player_noir);
	}*/
	
public static void main(String args[])
	{
		
		// Initialisation de quelques variables
		
		Scanner sc = new Scanner(System.in);
		int choix = -1; // possible erreur
		boolean isException;
		
		// Menu
		
		System.out.println("\nEchecs\n\n\nMenu :\n\n1. Nouvelle partie\n2. Charger une partie\n3. Quitter\n\n");

		do		// Vérification du choix dans le menu
		{
			isException = false;		
			try
			{
				System.out.print("Entrez le chiffre correspondant a votre choix : ");
				choix = sc.nextInt();
				if (choix > 3 || choix < 1) throw new UnauthorizedException("La valeur doit etre comprise entre 1 et 3");
			}
			catch(UnauthorizedException ue)
			{
				System.out.println(ue);
				isException = true;
			}
			catch(InputMismatchException ime)
			{
				System.out.println("Vous devez entrer un chiffre ...");
				isException = true;
			}
			finally {sc.nextLine();}
		} while( isException );
			
			
		// Lancement de la partie ( choix 1 et 2 )
		
		if (choix != 3)
		{
			//Initialisation de quelques variables
			boolean abandon = false;
			boolean demandeSave = false;
			Piece enEchec = null;
			String gagnant;
			boolean roquePossible;
			Jeu partie;
			
			//Noms des joueurs
			System.out.println("Entrez le nom du joueur 1 ( blanc / MAJUSCULE ) : ");
			String nom1 = sc.nextLine();
			System.out.println("Entrez le nom du joueur 2 ( noir / minuscule ): ");
			String nom2 = sc.nextLine();
			
			//Mise en place d'un nouveau plateau
			if (choix == 1)
			{
				partie = new Jeu(nom1,nom2);
			}
			
			//Chargement d'une partie existante sur le plateau
			else // choix == 2
			{
				partie = new Jeu(nom1,nom2); // sert à rien mais compile pas sinon
				do
				{
					isException = false;
					try
					{
						System.out.println("Entrez votre sauvegarde (notation de Forsyth-Edwards (jusqu'aux roques) : ");
						String save = sc.nextLine();
						partie = new Jeu(nom1,nom2,save);
					}
					catch(Exception e) // Jeu.Charger ne levera aucune exception
					{
						System.out.print("Sauvegarde non valide");
						isException = true;
					}
				}
				while(isException);
			}

			//Tour à tour
			while ( /*!( partie.isEchecEtMat(partie.getJoueurActif())) && (! partie.isPat(partie.getJoueurActif())) &&*/ (! abandon ) && (! demandeSave)) 
			{
				if (partie.getJoueurActif()) System.out.print("\nAUX BLANCS\n"); else System.out.print("\nAUX NOIRS\n");
				enEchec = partie.echiquier.isEchec(partie.getJoueur(partie.getJoueurActif()));
				roquePossible = partie.isRoque(partie.getJoueur(partie.getJoueurActif()));
				
				if (enEchec != null) System.out.println("Vous etes en echec !");
				
				System.out.println("\n" + partie.echiquier);
				
				if (roquePossible && (enEchec == null)) System.out.print("\nChoisir votre action :\n\n1. Deplacer une piece\n2. Roque\n3. Abandonner\n4. Sauvegarder\n\n") ;
				else System.out.print("\nChoisir votre action :\n\n1. Deplacer une piece\nX. Roque (non disponible)\n3. Abandonner\n4. Sauvegarder\n\n") ;
				
				do
				{
					isException = false;
					try
					{
						System.out.print("Entrez le chiffre correspondant a votre choix : ");
						choix = sc.nextInt();
						if (choix > 4 || choix < 1) throw new UnauthorizedException("La valeur doit etre comprise entre 1 et 4");
						if ( (choix == 2) &&  !roquePossible) throw new UnauthorizedException("Roque non disponible");
					}
					catch(UnauthorizedException ue)
					{
						System.out.println(ue);
						isException = true;
					}
					catch(InputMismatchException ime)
					{
						System.out.println("Vous devez entrer un chiffre ...");
						isException = true;
					}
					finally {sc.nextLine();}
				} while(isException);
				if (choix == 3) abandon = true;
				if (choix == 4) demandeSave = true;
				if (choix == 2) 
				{
					do
					{
						isException = false;
						try
						{
							System.out.println("Entrez le chiffre correspondant a votre choix : ");
							if ( partie.isQRoque(partie.getJoueur(partie.getJoueurActif())) ) System.out.println("1. Grand Roque "); else System.out.println("X. Grand Roque ");
							if ( partie.isKRoque(partie.getJoueur(partie.getJoueurActif())) ) System.out.println("2. Petit Roque "); else System.out.println("X. Petit Roque ");
							choix = sc.nextInt();
							if (choix == 1 && (! partie.isQRoque(partie.getJoueur(partie.getJoueurActif())))) throw new UnauthorizedException("Le Grand Roque n'est pas possible");
							if (choix == 2 && (! partie.isKRoque(partie.getJoueur(partie.getJoueurActif())))) throw new UnauthorizedException("Le Petit Roque n'est pas possible");
							if (choix == 1) partie.doRoque(true, partie.getJoueur(partie.getJoueurActif())); else partie.doRoque(false, partie.getJoueur(partie.getJoueurActif()));
						}
						catch(UnauthorizedException ue)
						{
							System.out.println(ue);
							isException = true;
						}
						catch(InputMismatchException ime)
						{
							System.out.println("Vous devez entrer un chiffre ...");
							isException = true;
						}
						finally {sc.nextLine();}
					}
					while(isException);
				}				
				if (choix == 1) 
				{
					Case dep;
					Case arr;
					do
					{
						isException = false;
						try
						{
							System.out.print("\nEntrez la case de depart : ");
							dep = partie.echiquier.getCase(sc.nextLine());
							System.out.print("\nEntrez la case d'arrivee : ");
							arr = partie.echiquier.getCase(sc.nextLine());
							Joueur joueur_actif = partie.getJoueur(partie.getJoueurActif());
							partie.Deplacements(dep,arr,joueur_actif);
						}
						catch(MiseEnEchecException meee)
						{
							System.out.print(meee);
							isException = true;
						}
						catch(UnauthorizedException ue)
						{
							System.out.print(ue);
							isException = true;
						}
					} 
					while( isException );
					
					if (partie.isPromotion(partie.getJoueur(partie.getJoueurActif())) != null)
					{
						Case c = partie.isPromotion(partie.getJoueur(partie.getJoueurActif()));
						do
						{
							isException = false;
							try
							{
								System.out.print("En quoi veux-tu transformer ta piece ?\n1. Tour\n2. Fou\n3. Cavalier\n4. Reine");
								choix = sc.nextInt();
								if (choix > 4 || choix < 1) throw new UnauthorizedException("La valeur doit etre comprise entre 1 et 4");
							}
							catch(UnauthorizedException ue)
							{
								System.out.println(ue);
								isException = true;
							}
							catch(InputMismatchException ime)
							{
								System.out.println("Vous devez entrer un chiffre ...");
								sc.nextLine();
								isException = true;
							}
							finally {sc.nextLine();}
						} 
						while( isException );
					}			
				}
				partie.setJoueurActif(! partie.getJoueurActif());
			}

			//Détermination du vainqueur en fonction des conditions d'arrêts
			
			System.out.print("\n\n\n") ;
			
			
			if (abandon)
			{
				if (partie.getJoueurActif()) gagnant = partie.player_blanc.getNom() ; else gagnant = partie.player_noir.getNom();
				System.out.print(gagnant + " gagne par abandon de son adversaire.\n");
			}
			if ( false /*partie.isEchecEtMat(partie.getJoueurActif())*/ )
			{
				if (partie.getJoueurActif()) gagnant = partie.player_noir.getNom() ; else gagnant = partie.player_blanc.getNom();
				System.out.print("ECHEC ET MAT ! \n" +gagnant + " remporte la partie.");
			}
			if ( false /*partie.isPat(partie.getJoueurActif())*/ ) System.out.print("Egalite ( c'est balo ) !");
			
			// Save
			
			if (demandeSave) 
			{
				partie.setJoueurActif(!partie.getJoueurActif());
				System.out.println(partie.Sauver()+"\n(conservez bien ce code pour pouvoir recharger votre partie !)\n");
			}

		}
		
		System.out.println("Fermeture du programme ...");
	}
}