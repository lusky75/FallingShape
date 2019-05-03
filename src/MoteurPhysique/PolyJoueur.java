package MoteurPhysique;

import java.awt.Color;
import java.util.Arrays;

import java.util.ArrayList;

/**
 * Un PolyJoueur est un polygone représentant la figure manipulée par le joueur.
 * Il s'agit nécessairement d'un polygone convexe.
 * 
 */
public class PolyJoueur extends Polygone {

	/**
	 * Tableau de Point representant les sommets du polygone
	 */
	private Point[] points;

	/**
	 * Point représentant la dernière position connue du polygone
	 */
	private Point memory;

	/**
	 * Constructeur PolyJoueur
	 * 
	 */
	public PolyJoueur() {
		points = new Point[] { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0) };
		c = Color.RED;
		memory = getPos();
		Point centre = getPos();
		coord = new Deplacement(centre.getX(), centre.getY(), 0.3);
	}

	/**
	 * Calcule l'enveloppe convexe d'un ensemble de point.
	 * 
	 * Il s'agit d'une version légèrement modifiée d'une implémentation effectuée
	 * par quelqu'un d'autre :
	 * https://www.sanfoundry.com/java-program-to-implement-jarvis-algorithm/
	 * 
	 * @param pts
	 *            L'ensemble de Point pour lequel on veut calculer l'enveloppe
	 *            convexe.
	 */
	public void marcheDeJarvis(Point[] pts) {

		int n = pts.length;
		int nbPts = 0;
		if (n <= 3)
			return;
		int[] next = new int[n];
		Arrays.fill(next, -1);
		int gauche = 0;
		for (int i = 1; i < n; i++)
			if (pts[i].getX() < pts[gauche].getX())
				gauche = i;
		int p = gauche;
		int q;
		do {
			q = (p + 1) % n;
			for (int i = 0; i < n; i++)
				if (Vecteur.produitvectoriel(pts[p], pts[i], pts[q]) > 0)
					q = i;
			next[p] = q;
			p = q;
			nbPts++;
		} while (p != gauche);

		Point[] res = new Point[nbPts];
		for (int i = 0; i < nbPts; i++) {
			res[i] = pts[p];
			p = next[p];
		}

		points = res;
	}

	/**
	 * Mets à jour la position du PolyJoueur.
	 * 
	 * Cette fonction a 3 comportements différents :
	 * <ul>
	 * <li>Soit il n'y a plus de PolyJoueur à l'écran, alors PolyJoueur est ancré
	 * dans un coin de l'écran afin qu'il n'y ait plus de collision</li>
	 * <li>Soit le PolyJoueur existe et delai%5 != 0, alors on met simplement à jour
	 * la position du PolyJoueur</li>
	 * <li>Soit le PolyJoueur existe et delai%5 == 0, alors on met à jour la
	 * position du PolyJoueur et on recalcule sa vitesse par rapport à sa précédente
	 * position connue</li>
	 * </ul>
	 * 
	 * Le délai a été ajouté pour éviter qu'on calcule la vitesse en permanence. En
	 * effet la vitesse est calculée par rapport au centre de deux positions
	 * connues. Or la position du centre varie trop souvent, créant ainsi des
	 * vitesses fausses. C'est pourquoi on calcule la vitesse moins souvent.
	 * 
	 * @param nouvellePos
	 */
	public void miseAJour(Point[] nouvellePos) {
		if (nouvellePos != null && nouvellePos.length > 2) {
			marcheDeJarvis(nouvellePos);
			Point newPos = getPos();
			if (delai % 5 == 0) {
				Vecteur v = new Vecteur(memory, newPos);
				v.fois(0.1);
				coord.getVit().set(v.getX(), v.getY());
				memory = newPos;
			}
			coord.getPos().set(newPos);
		} else {
			points = new Point[] { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0) };
			coord.getPos().set(new Point(0, 0));
			coord.getVit().set(0, 0);
		}
		delai++;
	}

	/**
	 * Calcule le centre du PolyJoueur (moyenne de tous les points)
	 * 
	 * @return Centre du PolyJoueur
	 */
	public Point getPos() {
		double x = 0;
		double y = 0;
		for (Point p : points) {
			x += p.getX();
			y += p.getY();
		}
		return new Point(x / points.length, y / points.length);

	}

	/**
	 * Effectue la collision entre le PolyJoueur et la figure f.
	 * 
	 * Le PolyJoueur et f ne peuvent entrer en collision qu'une fois toutes les
	 * 10ms. Afin d'éviter d'avoir une figure coincée dans le PolyJoueur.
	 * 
	 * Après cela on calcule la nouvelle trajectoire avec la formule de la collision
	 * élastique, mais sans changer la trajectoire du PolyJoueur. Si après calcule f
	 * se rapproche quand même de PolyJoueur alors f part dans l'autre direction (et
	 * s'éloigne ainsi de PolyJoueur). Ceci pourrait arriver car la direction vers
	 * laquelle part PolyJoueur n'est pas forcément cohérente.
	 */
	public void collision(Figure f) {

		if (f.delai != 0)
			return;

		f.delai = 10;

		double distanceX = getX() - f.getX();
		double distanceY = getY() - f.getY();
		double distanceCarre = Math.pow(distanceX, 2) + Math.pow(distanceY, 2);

		double vitesseX = f.coord.getVit().getX() - coord.getVit().getX();
		double vitesseY = f.coord.getVit().getY() - coord.getVit().getY();

		double prodScalaire = vitesseX * distanceX + vitesseY * distanceY;

		double collision = prodScalaire / distanceCarre;
		double collisionX = collision * distanceX;
		double collisionY = collision * distanceY;

		double masse = coord.getMasse() + f.coord.getMasse();
		double masseFig = 2 * coord.getMasse() / masse;

		f.coord.getVit().plus(-(masseFig * collisionX), -(masseFig * collisionY));

		Point p = new Point(f.getX(), f.getY());
		p.translate(f.coord.getVit());

		double distanceX2 = getX() - p.getX();
		double distanceY2 = getY() - p.getY();
		double distanceCarre2 = Math.pow(distanceX2, 2) + Math.pow(distanceY2, 2);

		if (distanceCarre2 < distanceCarre)
			f.coord.getVit().fois(-1);

	}

	public void move() {

	}

	public Point[] getPoints() {
		return points;
	}

	public String toString() {
		String s = "PolyJoueur : ";
		s += "\n" + this.coord.toString() + "\n";
		for (Point p : points)
			s += p.toString() + " ";
		return s;
	}

	public boolean identique(Object o) {
		return false;
	}
}
