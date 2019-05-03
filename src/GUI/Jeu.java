package GUI;

import java.util.ArrayList;

import MoteurPhysique.Cercle;
import MoteurPhysique.Deplacement;
//import Audio.Sound;
import MoteurPhysique.Figure;
import MoteurPhysique.Point;
import MoteurPhysique.PolyJoueur;
import MoteurPhysique.Polygone;
import MoteurPhysique.Usine;
import Player.AdapteScore;
import Player.Joueur;
import Player.Niveau;
import Player.Score;


/**
 * Jeu est la classe qui applique la gestion du Jeu
 * 
 * Le Jeu est caracterise par :
 * <ul>
 * <li>Trois listes de Figure</li>
 * <li>Une figure PolyJoueur</li>
 * <li>Un joueur</li>
 * <li>Une dimension x et y</li>
 * <li>Des compteurs de combinaison</li>
 * <li>Une usine qui fabrique des figures</li>
 * <li>D'un adaptateur de score</li>
 * <li>Un niveau</li>
 * 
 * @see Figure
 * @see PolyJoueur
 * @see Joueur
 * @see Usine
 * @see AdapteScore
 * @see Niveau
 */
/**
 * La classe Jeu permet de gerer les regles du jeu
 * 
 * @author Chen Lucas
 *
 */
public class Jeu {

	/** Trois listes de figures :
	 * <ul>
	 * <li> figures est la liste des figures qui doivent tomber</li>
	 * <li> figuresAconserver est la liste des figures a conserver</li>
	 * <li> figurememoire est la liste des figures gardees en memoire</li>
	 * </ul>
	 * 
	 * 3 listes de figures : figures est la liste des figures qui doivent tomber
	 * figuresAconserver est la liste des figures a conserver figurememoire est la
	 * liste des figures gardées en memoire lorsqu'on a plusieurs figures à
	 * conserver
	 */
	private ArrayList<Figure> figures, figuresAconserver, figurememoire;

	/**
	 * joueur du Jeu, ses informations seront detaillées dans la classe Joueur
	 * 
	 * @see Joueur
	 * 
	 */
	private Joueur joueur;

	/**
	 * figure du Joueur, ses informations seront detaillees dans le classe PolyJoueur
	 * 
	 * @see PolyJoueur
	 */
	private PolyJoueur pj;
	
	/**<ul>
	 * <li>x est la longueur du jeu</li>
	 * <li>y est la hauteur du jeu</li>
	 * <li>figuresEject est le compteur de nombre de figure ejectees</li>
	 * <li>combinaison est le compteur de nombre de figure correctement conservee</li>
	 * </ul>
	 */
	private int x, y, figureEject, combinaison, compteur;
	
	/**
	 * usine nous permet de fabriquer des figures, forme et couleur aleatoire
	 * 
	 * @see Usine
	 */
	private Usine usine;
	
	/**
	 * l'AdapteScore du jeu, qui permet de recuperer les top 10 score des joueurs qui ont enregistrer leur score
	 * 
	 * @see Player#AdapteScore
	 */
	private AdapteScore adaptescore;
	
	/**
	 * Le Niveau du Jeu
	 * 
	 * @see Player#Niveau
	 */
	private Niveau niveau;
	
	/**
	 * La valeur boolean pause du Jeu, qui verifie si le jeu est en pause
	 */
	private boolean pause;
	
	/**
	 * Constructeur Jeu.
	 * 
	 * Initialise les listes de Figure, le nouveau joueur, les compteurs.
	 * La dimension du jeu, le niveau et l'usine, l'adaptateur du score et la figure PolyJoueur.
	 * 
	 * l'usine nous permettra de creer des figures.
	 * l'adaptescore nous permettra d'enregistrer le score a la fin du jeu.
	 * la figure PolyJoueur nous permettra d'entrer en collision avec les figures.
	 * 
	 * @see Niveau
	 * @see Usine
	 * @see AdapteScore
	 * @see PolyJoueur
	 * 
	 * @param nom
	 * 		Le nom du nouveau joueur
	 * @param x
	 * 		La longueur de la dimension du jeu
	 * @param y
	 * 		La hauteur de la dimension du jeu
	 */
	public Jeu(String nom, int x, int y) {
		this.figures = new ArrayList<Figure>();
		this.figuresAconserver = new ArrayList<Figure>();
		this.figurememoire = new ArrayList<Figure>();
		this.joueur = new Joueur(nom);
		this.figureEject = 0;
		this.combinaison = 0;
		this.x = x;
		this.y = y;
		this.pause = false;
		this.niveau = new Niveau();
		this.usine = new Usine();
		this.adaptescore = new AdapteScore();
		this.pj = new PolyJoueur();
	}

	/**
	 * Verifie si la figure f prise en parametre est hors de l'ecran du Jeu
	 * 
	 * @param f la figure de l'ecran
	 * @return une valeur boolean vrai ou faux
	 */
	public boolean horsFenetre(Figure f) {
		return f.getX() > x || f.getX() < 0 || f.getY() > y;
	}

	/**
	 * Verifie si le "joueur" du Jeu a 0 point de vie, si c'est le cas la partie se
	 * termine Retourne une valeur boolean (vrai ou faux)
	 * 
	 * @return boolean
	 * @see Joueur#isDead()
	 */
	public boolean perdu() {
		if (joueur.isDead()) {
			return true;
		}
		return false;
	}

	/**
	 * Gestion des figures à conserver Lorsque qu'on monte d'un niveau, la liste des
	 * figures à conserver est mise à jour :
	 * 
	 * Il faut avoir 3 combinaisons correctes ou 10 figures correctement ejectées
	 * pour passer au niveau suivant
	 * 
	 * (Niveau Facile) Entre niveau 0 et 3, il y aura qu'une figure à conserver
	 * 
	 * @see Niveau#gestionNiveauFacile()
	 * 
	 *      (Niveau Moyen) Au niveau 4, il y aura 2 figures à conserver
	 * @see Niveau#gestionNiveauMoyen()
	 * 
	 *      Dans le niveau moyen, on fera appel à la liste des figures gardées en
	 *      memoire, la combinaison du Jeu incrémente lorsque les 2figures sont
	 *      correctement conservées. Pour cela, on garde en mémoire la première
	 *      figure conservée dans l'ArrayList figurememoire, lorsque la deuxième
	 *      figure est conservée, on fait reapparaître les 2figures tant qu'il reste
	 *      des combinaisons.
	 * 
	 */
	public void changeFigureAConserver() {
		if (this.niveau.getNiveau() >= 0 && this.niveau.getNiveau() <= 2) {
			if (compteur % 25 == 0) {
				System.out.println("Change de niveau en fonction du temps");
				this.niveau.gestionNiveauFacile();
				for (int i = 0; i < figuresAconserver.size(); i++) {
					figuresAconserver.remove(figuresAconserver.get(i));
				}
				System.out.println("Change de figure");
				dessineFigureAConserver(550, 30);
				compteur = 1;
			} else if (combinaison == 3) {
				this.niveau.gestionNiveauFacile();
				this.joueur.setPV(joueur.getPV() + 1);
				for (int i = 0; i < figuresAconserver.size(); i++) {
					figuresAconserver.remove(figuresAconserver.get(i));
				}
				System.out.println("Change de figure");
				combinaison = 0;
				dessineFigureAConserver(550, 30);
			} else if (figureEject == 10) {
				this.joueur.setPV(joueur.getPV() + 1);
				this.niveau.gestionNiveauFacile();
				for (int i = 0; i < figuresAconserver.size(); i++) {
					figuresAconserver.remove(figuresAconserver.get(i));
				}
				System.out.println("Change de figure");
				dessineFigureAConserver(550, 30);
				figureEject = 0;
			}
		} else if (this.niveau.getNiveau() >= 3) {
			if (compteur % 15 == 0) {
				this.niveau.gestionNiveauMoyen();
				for (int i = 0; i < figuresAconserver.size(); i++) {
					figuresAconserver.remove(figuresAconserver.get(i));
				}
				System.out.println("Change de figure en fonction du temps");
				dessineFigureAConserverMoyen(550, 30);
				compteur = 1;
			} else if (figurememoire.size() == 2) {
				System.out.println("Combinaison complete");
				for (int i = 0; i < figurememoire.size(); i++) {
					figuresAconserver.add(figurememoire.get(i));
				}
				figurememoire = new ArrayList<Figure>();

			} else if (combinaison == 3) {
				this.joueur.setPV(joueur.getPV() + 1);
				this.niveau.gestionNiveauMoyen();
				System.out.println("Nouvelle combinaison");
				for (int i = 0; i < figuresAconserver.size(); i++) {
					figuresAconserver.remove(figuresAconserver.get(i));
				}
				combinaison = 0;
				dessineFigureAConserverMoyen(550, 30);
			} else if (figureEject == 10) {
				this.joueur.setPV(joueur.getPV() + 1);
				this.niveau.gestionNiveauMoyen();
				System.out.println("Nouvelle combinaison");
				for (int i = 0; i < figuresAconserver.size(); i++) {
					figuresAconserver.remove(figuresAconserver.get(i));
				}
				dessineFigureAConserverMoyen(550, 30);
				figureEject = 0;
			}
		}

	}

	/**
	 * Gestion du Jeu en fonction de chaque Figure dans l'ArrayList figures
	 * 
	 * @param f
	 *            f est une figure et on verifie ses propriétés par rapport au règle
	 *            du jeu
	 * 
	 *            A chaque instant, on met à jour la figure à conserver et la
	 *            vitesse de chaque figure qui doit tomber Si la figure depasse hors
	 *            de l'ecran, on vérifie ses propriétés et on la retire de la liste
	 *            des figures.
	 * 
	 *            On verifie si la figure f existe dans la liste des figures à
	 *            conserver et on garde son indice
	 * 
	 *            Au Niveau Facile : Le score et le point de vie est géré de la
	 *            façon suivante : La figure à conserver doit être le même que la
	 *            figure qui depasse sur l'axe Y, on gagne un point de score Parce
	 *            que le joueur doit ejecter les figures différentes que la figure à
	 *            conserver
	 * 
	 * @see Joueur#getScore()
	 * @see Joueur#getPV()
	 * 
	 *      Au Niveau Moyen : Meme condition qu'au Niveau Facile s'il n'y a pas de
	 *      combinaison de figures Si un des figures à conserver est pareil que la
	 *      figure qui depasse sur l'axe Y, elle disparaît et est gardée en memoire
	 *      dans la liste des figures gardées en memoire
	 */
	public void gestion(Figure f) {

		changeFigureAConserver();
		int indice = 0;
		boolean memeFig = false;
		for (int i = 0; i < figuresAconserver.size(); i++) {
			if (f.identique(figuresAconserver.get(i))) {
				memeFig = true;
				indice = i;
			}
		}

		if (this.niveau.getNiveau() < 4) {
			if (f.getY() > this.y && !memeFig) {
				System.out.println(" Mauvaise figure (point : -1) CC");
				joueur.setPV(joueur.getPV() - 1);
				figures.remove(f);
			} else if (f.getY() > this.y && memeFig) {
				joueur.setScore(joueur.getScore() + 1);
				figures.remove(f);
				// incremente combinaison (chaque 3 combinaisons le joueur augmente d'un niveau)
				combinaison += 1;
				System.out.println(" Bonne combinaison (score : +1) | (combinaison : +1)");
			} else if ((f.getX() < 0 || f.getX() > this.x) && memeFig) {
				joueur.setPV(joueur.getPV() - 1);
				figures.remove(f);
				System.out.println(" Mauvais rejet (point : -1)");
			} else if ((f.getX() < 0 || f.getX() > this.x) && !memeFig) {
				joueur.setScore(joueur.getScore() + 1);
				figures.remove(f);
				figureEject += 1;
				System.out.println(" Correctement rejette (score : +1) | (figureEject : +1)");
			}
		} else if (this.niveau.getNiveau() >= 4) {

			if (f.getY() > this.y && !memeFig) {
				System.out.println(" Mauvaise figure (point : -1)");
				joueur.setPV(joueur.getPV() - 1);
				figures.remove(f);
			} else if ((f.getX() < 0 || f.getX() > this.x) && memeFig) {
				joueur.setPV(joueur.getPV() - 1);
				figures.remove(f);
				System.out.println(" Mauvais rejet (point : -1)");
			} else if ((f.getX() < 0 || f.getX() > this.x) && !memeFig) {
				joueur.setScore(joueur.getScore() + 1);
				figures.remove(f);
				figureEject += 1;
				System.out.println(" Correctement rejette (score : +1) | (figureEject : +1)");
			} else if (f.getY() > this.y && memeFig) {
				figurememoire.add(figuresAconserver.get(indice));
				int taille = 2 - figurememoire.size();
				System.out.println(" Bien conserver, il reste " + taille + "figure a conserver pour gagner un point ");
				figures.remove(f);
				figuresAconserver.remove(figuresAconserver.get(indice));
				if (taille == 0) {
					joueur.setScore(joueur.getScore() + 1);
					combinaison += 1;
					changeFigureAConserver();
					System.out.println("(combinaison : +1), changement des 2 figures");
				}
			}
		}

	}

	/**
	 * Met a jour les figures et vérifie si les figures entrent en collision, pour
	 * plus de details, voir les documentations dans Figure et Deplacement.
	 * 
	 * @see MoteurPhysique#PolyJoueur#miseAJour(Point[])
	 * @see MoteurPhysique#Figure#collision(Figure)
	 * @see MoteurPhysique#Figure#enCollision(Figure)
	 * @see MoteurPhysique#Deplacement#move()
	 */
	public void maj(Point[] pts) {
		
		pj.miseAJour(pts);
		for (int i = 0; i < figures.size(); i++) {
			Figure fig = figures.get(i);
			if (pj.enCollision(fig))
				pj.collision(fig);
		}
		for (int i = 0; i < figures.size(); i++) {
			Figure fig = figures.get(i);
			if (horsFenetre(fig)) {
				gestion(fig);
				i--;
			} else {
				for (int j = i + 1; j < figures.size(); j++) {
					Figure fig2 = figures.get(j);
					if (fig.enCollision(fig2))
						fig.collision(fig2);
				}
			}
		}
		for (Figure fig : figures)
			fig.move();
	}

	/**
	 * Supprime les anciennes figures à conserver de l'ArrayList figuresAconserver
	 * du Jeu, et dessine 2 nouvelles figures à conserver dans l'ArrayList, en
	 * prenant une position des figures à dessiner sur la fenetre.
	 * 
	 * @param x
	 *            position de l'axe X de la figure à dessiner
	 * @param y
	 *            position de l'axe Y de la figure à dessiner
	 * 
	 * @see dessineFigureAConserver(int width,int height)
	 */
	public void dessineFigureAConserverMoyen(int x, int y) {
		for (int i = 0; i < figuresAconserver.size(); i++) {
			figuresAconserver.remove(figuresAconserver.get(i));
		}
		dessineFigureAConserver(x, y);
		dessineFigureAConserver(x + 70, y);
	}

	/**
	 * On pioche au hasard une figure à conserver, sa forme et sa couleur pour la
	 * positionner sur la fenêtre C'est une figure fixée de taille 30 et qui ne
	 * bouge pas. Une Figure peut être un cercle, @see Cercle ou bien un
	 * polygone, @see Polygone
	 * 
	 * @param width
	 *            position de l'axe X de la figure à dessiner
	 * @param height
	 *            position de l'axe Y de la figure à dessiner
	 */
	public void dessineFigureAConserver(int x, int y) {
		Figure f = usine.getFigure(x, y);
		figuresAconserver.add(f);
	}

	/**
	 * Ajoute une figure aléatoire dans l'ArrayList "figures" du Jeu, la taille de
	 * la figure est 30, on choisit au hasard ses coordonnées X, ses coordonnées Y à
	 * 0
	 * 
	 * @param vitesse
	 *            la vitesse pour determiner la vitesse d'axe Y de la figure à
	 *            ajouter
	 *
	 *            Pour plus d'informations @see Deplacement
	 */
	
	/**
	 * Ajoute une figure aléatoire dans l'ArrayList "figures" du Jeu, la taille de
	 * la figure est 30, on choisit au hasard ses coordonnées X, ses coordonnées Y à
	 * 0
	 * 
	 * @param vitesse
	 *            la vitesse pour determiner la vitesse d'axe Y de la figure à
	 *            ajouter
	 *
	 *            Pour plus d'informations @see Deplacement
	 */
	public void ajouteFigure(double vitesse) {
		Figure f = usine.getFigure(Math.random() * (500 -100) + 100, 0, 0, vitesse, 0.02);
		figures.add(f);
		compteur += 1;
	}

	/**
	 * Retourne la liste des figures qui doivent tomber
	 * 
	 * @return Une ArrayList de Figure de Jeu
	 */
	public ArrayList<Figure> getFigures() {
		return figures;
	}

	/**
	 * Retourne la liste des figures à conserver
	 * 
	 * @return Une ArrayList de Figure
	 */
	public ArrayList<Figure> getFiguresAconserver() {
		return figuresAconserver;
	}

	/**
	 * Retourne le joueur du Jeu
	 * 
	 * @return Un Joueur
	 * @see Joueur
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	/**
	 * Retourne la combinaison du Jeu
	 * 
	 * @return le nombre de combinaison du Jeu sous forme d'entier
	 */
	public int getCombinaison() {
		return combinaison;
	}

	/**
	 * Retourne figureEject du Jeu
	 * 
	 * @return le nombre de figure à ejecter du Jeu sous forme d'entier
	 */
	public int getFigureEject() {
		return figureEject;
	}

	/**
	 * Met à jour la combinaison du Jeu
	 * 
	 * @param comb
	 *            la nouvelle combinaison du Jeu
	 */
	public void setCombinaison(int comb) {
		this.combinaison = comb;
	}
	
	/**
	 * Renvoie le tableau de score du Jeu
	 * 
	 * @return tableau de Score du Jeu
	 * 			Un tableau de Score de la classe AdapteScore
	 *
	 *@see AdapteScore#getListeScore() 
	 */
	protected Score[] getScore() {
		return this.adaptescore.getListeScore();
	}

	/**
	 * Renvoie la liste des figures gardees en memoire du jeu, cette liste est utile a partir du niveau 4
	 * 
	 * @return figurememoire
	 * 			La liste des figures gardees en memoire du Jeu
	 */
	public ArrayList<Figure> getFigurememoire() {
		return figurememoire;
	}

	/**
	 * Met a jour une nouvelle liste de figure memoire du jeu
	 * 
	 * @param figurememoire
	 * 			la nouvelle liste de figurememoire du Jeu
	 */
	public void setFigurememoire(ArrayList<Figure> figurememoire) {
		this.figurememoire = figurememoire;
	}

	/**
	 * Renvoie les coordonnees x du jeu
	 * 
	 * @return x du Jeu
	 * 		coordonnee x du Jeu
	 */
	public int getX() {
		return x;
	}

	/**
	 * Met a jour les coordonnes x du jeu
	 * 
	 * @param x
	 * 		la nouvelle coordonnee de x du Jeu
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Renvoie les coordonnees y du jeu
	 * 
	 * @return y du Jeu
	 * 		coordonnee y du Jeu
	 */
	public int getY() {
		return y;
	}

	/**
	 * Met a jour les coordonnes y du jeu
	 * 
	 * @param y
	 * 		la nouvelle coordonnee de y du Jeu
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Renvoie l'usine du jeu, qui permet de creer les figures du jeu
	 * 
	 * @return usine
	 * 		L'Usine du Jeu
	 * 
	 * @see MoteurPhysique#Usine
	 */
	public Usine getUsine() {
		return usine;
	}

	/**
	 * Met a jour l'usine du jeu, qui permet de creer les figures du jeu
	 * 
	 * @param usine
	 * 		la nouvelle Usine du Jeu
	 */
	public void setUsine(Usine usine) {
		this.usine = usine;
	}
	
	/**
	 * Renvoie l'adaptescore du jeu, qui nous permet de recuperer les scores
	 * 
	 * @return adaptescore
	 * 		adaptescore du Jeu
	 * 
	 * @see Player#AdapteScore
	 */	
	public AdapteScore getAdaptescore() {
		return adaptescore;
	}
	
	/**
	 * Met a jour l'adaptescore du jeu
	 * @param adaptescore
	 * 			Le nouveau AdapteScore du Jeu
	 */
	public void setAdaptescore(AdapteScore adaptescore) {
		this.adaptescore = adaptescore;
	}
	
	/**
	 * Met a jour, l'ArrayList<Figure> figures du jeu, qui contient les figures de l'ecran
	 * 
	 * @param figures
	 * 			La nouvelle liste de figures du Jeu
	 */
	public void setFigures(ArrayList<Figure> figures) {
		this.figures = figures;
	}

	/**
	 * Met a jour, l'ArrayList<Figure> figuresAconserver du jeu, qui contient les figures a conserver du jeu
	 * 
	 * @param figuresAconserver
	 * 			La nouvelle liste des figures a conserver du Jeu
	 */
	public void setFiguresAconserver(ArrayList<Figure> figuresAconserver) {
		this.figuresAconserver = figuresAconserver;
	}
	
	/**
	 * Met a jour, le joueur du jeu
	 * 
	 * @param joueur
	 * 			Le nouveau Joueur du Jeu
	 * 
	 * @see Player#Joueur
	 */
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	/**
	 * Met a jour l'instance figureEject, qui est un compteur de figures ejectees
	 * 
	 * @param figureEject
	 * 			le nouveau entier figureEject du Jeu
	 */
	public void setFigureEject(int figureEject) {
		this.figureEject = figureEject;
	}

	/**
	 * Renvoie le niveau du jeu
	 * 
	 * @return niveau
	 * 			le Niveau du Jeu
	 * 
	 * @see Player#Niveau
	 */
	public Niveau getNiveau() {
		return niveau;
	}

	/** 
	 * Met a jour le niveau du jeu
	 * 
	 * @param niveau
	 * 			Le nouveau Niveau du Jeu
	 */
	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}
	
	/**
	 * Renvoie la figure du joueur du jeu, qui est un PolyJoueur
	 * 
	 * @return pj
	 * 			Le PolyJoueur du Jeu
	 * 
	 * @see MoteurPhysique#PolyJoueur
	 */
	public PolyJoueur getPJ() {
		return pj;
	}
	
	/**
	 * Renvoie la valeur boolean pause du jeu, et verifie si le jeu est en pause
	 * 
	 * @return pause
	 * 			pause du Jeu
	 */
	public boolean getPause() {
		return pause;
	}
	
	/**
	 * Met a jour la valeur boolean pause du jeu
	 * 
	 * @param pause
	 *			La nouvelle valeur boolean pause du Jeu
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	/**
	 * Echange la valeur boolean pause du Jeu par sa valeur inverse
	 */
	public void switchPause() {
		this.pause = !pause;
	}
}