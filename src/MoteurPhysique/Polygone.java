package MoteurPhysique;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Polygone est la classe repr�sentant un polygone.
 * 
 * Un polygone est caract�ris� par :
 * <ul>
 * <li>Un ensemble de point �num�r� dans le sens horaire</li>
 * <li>Une couleur</li>
 * <li>Ses diverses coordonn�es</li>
 * </ul>
 * 
 * Sauf mention contraire, toutes les listes et tableaux de point correspondent
 * � des sommets ou des c�t�s d'un polygone ordonn�s dans le sens horaire.
 *
 * Le polygone est plein (pas de donut) et ne se croise pas (pas de sablier)..
 */
public abstract class Polygone extends Figure {

	/**
	 * Retourne l'ensemble des points du polygone.
	 * 
	 * @return Tableau de point repr�sentant les sommets du polygone.
	 */
	public abstract Point[] getPoints();

	/**
	 * Prends un tableau de point et retourne la liste des vecteurs normaux � chacun
	 * des c�t�s.
	 * 
	 * @param points
	 *            Tableau de points, correspondant aux sommets d'un polygone.
	 * @return Liste de vecteurs normaux aux c�t�s.
	 * 
	 * @see Vecteur#normalAGauche()
	 */
	public ArrayList<Vecteur> normaleCotes(Point[] points) {
		// Calcul des vecteurs correspondant � chaque c�t� du polygone
		ArrayList<Vecteur> side = new ArrayList<Vecteur>();
		for (int i = 0; i < points.length; i++) {
			if (i < points.length - 1)
				side.add(new Vecteur(points[i], points[i + 1]));
			else
				side.add(new Vecteur(points[i], points[0]));
		}
		ArrayList<Vecteur> normale = new ArrayList<Vecteur>();
		for (Vecteur v : side)
			normale.add(v.normalAGauche());
		return normale;
	}

	public boolean enCollision(Figure f) {
		return f.enCollision(this);
	}

	/**
	 * V�rifie si this est en collision avec le polygone p.
	 * 
	 * <p>
	 * Cette fonction est une impl�mentation du th�or�me des axes s�parateurs. Ce
	 * que le th�or�me �nonce : "Si deux objets convexes ne se p�n�trent pas alors
	 * il existe un axe tel que les projections des deux objets sur cet axe ne
	 * s'intersectent pas". �quivalence : s'il existe un axe qui passe entre les
	 * objets sans les toucher, alors cet axe est dit axe s�parateur et les deux
	 * objets ne sont pas en collision.
	 * </p>
	 * 
	 * <p>
	 * Impl�mentation :<br>
	 * Les axes � v�rifier sont les axes issus des vecteurs normaux � chaque cot� de
	 * chaque figure. Pour chacun de ces axes, tous les sommets de chaque polygone
	 * sont projet�s sur l'axe (avec le produit scalaire) puis on v�rifie que les
	 * segments reliant respectivements tous les points projet�s de la figure 1 et
	 * de la figure 2 ne s'intersectent pas. Soit ils ne s'intersectent pas donc il
	 * existe un axe (celui normal � celui qu'on vient de v�rifier) o� on peut
	 * tracer une droite entre les deux polygones et dans ce cas l� on est d�j� s�r
	 * que les deux polygones ne se touchent pas. Soit ils s'intersectent dans ce
	 * cas l� on v�rifie le prochain axe et ainsi de suite jusqu'� tous les faire.
	 * Si au bout du dernier axe il n'y a toujours pas d'axe s�parateur alors on est
	 * sur que les deux polygones s'intersectent.
	 * </p>
	 * 
	 * @param p
	 *            Polygone avec lequel v�rifier la collision.
	 * @return True si le polygone est en collision avec p, false sinon.
	 * @see Polygone#normaleCotes(Point[])
	 * @see Vecteur#produitScalaire(Vecteur)
	 */
	public boolean enCollision(Polygone p) {

		// R�cupration des coordonn�es des deux figures
		Point[] points1 = getPoints();
		Point[] points2 = p.getPoints();

		// R�cup�ration des vecteurs normales � chaque c�t� de chaque figure
		ArrayList<Vecteur> normale = new ArrayList<Vecteur>();
		normale.addAll(normaleCotes(points1));
		normale.addAll(normaleCotes(points2));

		// Projet� orthogonal de chaque point de chaque figure sur chacun des normales
		for (int i = 0; i < normale.size(); i++) {

			ArrayList<Double> projecThis = new ArrayList<Double>();
			ArrayList<Double> projecPoly = new ArrayList<Double>();

			// Projet� de this
			for (int j = 0; j < points1.length; j++) {
				double v = new Vecteur(points1[j].getX(), points1[j].getY()).produitScalaire(normale.get(i));
				projecThis.add(v);
			}

			// Projet� de p
			for (int j = 0; j < points2.length; j++) {
				double v = new Vecteur(points2[j].getX(), points2[j].getY()).produitScalaire(normale.get(i));
				projecPoly.add(v);
			}

			// V�rifie si les projet�s se croisent
			double minThis = Collections.min(projecThis);
			double maxThis = Collections.max(projecThis);
			double minPoly = Collections.min(projecPoly);
			double maxPoly = Collections.max(projecPoly);

			if (maxPoly < minThis || maxThis < minPoly) {
				return false;
			}
		}
		return true;
	}

	/**
	 * V�rifie si this entre en collision avec le cercle c.
	 * 
	 * Un polygone est en collision avec un cercle si le cercle en question est en
	 * collision avec au moins un des c�t� du polygone.
	 * 
	 * @param c
	 *            Cercle avec lequel on v�rifie la collision.
	 * @return True si le polygone est en collision avec le cercle, false sinon.
	 * @see Polygone#collisionFaceCercle(Point, Point, Cercle)
	 */
	public boolean enCollision(Cercle c) {
		Point[] points = getPoints();
		// V�rifie si le cercle est en contact avec au moins un c�t� du polygone
		for (int i = 0; i < points.length; i++)
			if (i < points.length - 1 && collisionFaceCercle(points[i], points[i + 1], c)
					|| i == points.length - 1 && collisionFaceCercle(points[i], points[0], c))
				return true;
		return false;
	}

	/**
	 * V�rifie si le segment ab est en collision avec le cercle c.
	 * 
	 * Un segment est en collision avec un cercle dans deux cas de figure :
	 * <ul>
	 * <li>Soit une extr�mit� ou deux du segment sont engoufr�s dans le cercle</li>
	 * <li>Soit le cercle p�n�tre le segment entre les deux extr�mit�s</li>
	 * </ul>
	 * 
	 * @param a
	 *            Premier point du segment.
	 * @param b
	 *            Second point du segment.
	 * @param c
	 *            Cercle avec lequel tester la collision.
	 * @return True si [ab] est en contact avec c, false sinon.
	 * @see Vecteur#produitScalaire(Vecteur)
	 * @see Polygone#pointDansCercle(Point, Cercle)
	 */
	public boolean collisionFaceCercle(Point a, Point b, Cercle c) {
		Vecteur ab = new Vecteur(a, b);
		Vecteur ba = new Vecteur(b, a);
		Vecteur ac = new Vecteur(a, c.getPos());
		Vecteur bc = new Vecteur(b, c.getPos());
		/*
		 * Si la distance entre la droite passant par a et b et le centre du cercle est
		 * sup�rieur au rayon c'est que le cercle ne touche pas le c�t� AB
		 */
		if ((Math.abs(ab.getX() * ac.getY() - ab.getY() * ac.getX()) / ab.norme()) >= c.getRayon())
			return false;
		/*
		 * Si un bout du cercle est entre le point a et b, donc si un bout du cercle est
		 * dans le segment AB alors le cercle touche le c�t�
		 */
		if (ab.produitScalaire(ac) >= 0 && ba.produitScalaire(bc) >= 0)
			return true;
		/*
		 * Si le sommet A ou B est dans le cercle c'est que le c�t� touche le cercle
		 */
		if (pointDansCercle(a, c) || pointDansCercle(b, c))
			return true;
		// Tous les cas sont trait�s, le cercle ne touche pas le c�t�
		return false;
	}

	/**
	 * V�rifie si un point est inclus dans un cercle.
	 * 
	 * Un point est inclus dans un cercle si la distance entre le point et le centre
	 * de ce point est inf�rieur au rayon du cercle.
	 * 
	 * @param p
	 *            Point � v�rifier.
	 * @param c
	 *            Cercle � v�rifier.
	 * @return True si p est inclut dans c, false sinon.
	 */
	public boolean pointDansCercle(Point p, Cercle c) {
		return Math.pow(p.getX() - c.getX(), 2) + Math.pow(p.getY() - c.getY(), 2) < Math.pow(c.getRayon(), 2);
	}

}
