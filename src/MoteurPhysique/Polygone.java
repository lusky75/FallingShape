package MoteurPhysique;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Polygone est la classe représentant un polygone.
 * 
 * Un polygone est caractérisé par :
 * <ul>
 * <li>Un ensemble de point énuméré dans le sens horaire</li>
 * <li>Une couleur</li>
 * <li>Ses diverses coordonnées</li>
 * </ul>
 * 
 * Sauf mention contraire, toutes les listes et tableaux de point correspondent
 * à des sommets ou des cétés d'un polygone ordonnés dans le sens horaire.
 *
 * Le polygone est plein (pas de donut) et ne se croise pas (pas de sablier)..
 */
public abstract class Polygone extends Figure {

	/**
	 * Retourne l'ensemble des points du polygone.
	 * 
	 * @return Tableau de point représentant les sommets du polygone.
	 */
	public abstract Point[] getPoints();

	/**
	 * Prends un tableau de point et retourne la liste des vecteurs normaux à chacun
	 * des cétés.
	 * 
	 * @param points
	 *            Tableau de points, correspondant aux sommets d'un polygone.
	 * @return Liste de vecteurs normaux aux cétés.
	 * 
	 * @see Vecteur#normalAGauche()
	 */
	public ArrayList<Vecteur> normaleCotes(Point[] points) {
		// Calcul des vecteurs correspondant à chaque cété du polygone
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
	 * Vérifie si this est en collision avec le polygone p.
	 * 
	 * <p>
	 * Cette fonction est une implémentation du théoréme des axes séparateurs. Ce
	 * que le théoréme énonce : "Si deux objets convexes ne se pénétrent pas alors
	 * il existe un axe tel que les projections des deux objets sur cet axe ne
	 * s'intersectent pas". équivalence : s'il existe un axe qui passe entre les
	 * objets sans les toucher, alors cet axe est dit axe séparateur et les deux
	 * objets ne sont pas en collision.
	 * </p>
	 * 
	 * <p>
	 * Implémentation :<br>
	 * Les axes à vérifier sont les axes issus des vecteurs normaux à chaque coté de
	 * chaque figure. Pour chacun de ces axes, tous les sommets de chaque polygone
	 * sont projetés sur l'axe (avec le produit scalaire) puis on vérifie que les
	 * segments reliant respectivements tous les points projetés de la figure 1 et
	 * de la figure 2 ne s'intersectent pas. Soit ils ne s'intersectent pas donc il
	 * existe un axe (celui normal à celui qu'on vient de vérifier) oé on peut
	 * tracer une droite entre les deux polygones et dans ce cas lé on est déjé sér
	 * que les deux polygones ne se touchent pas. Soit ils s'intersectent dans ce
	 * cas lé on vérifie le prochain axe et ainsi de suite jusqu'é tous les faire.
	 * Si au bout du dernier axe il n'y a toujours pas d'axe séparateur alors on est
	 * sur que les deux polygones s'intersectent.
	 * </p>
	 * 
	 * @param p
	 *            Polygone avec lequel vérifier la collision.
	 * @return True si le polygone est en collision avec p, false sinon.
	 * @see Polygone#normaleCotes(Point[])
	 * @see Vecteur#produitScalaire(Vecteur)
	 */
	public boolean enCollision(Polygone p) {

		// Récupration des coordonnées des deux figures
		Point[] points1 = getPoints();
		Point[] points2 = p.getPoints();

		// Récupération des vecteurs normales à chaque cété de chaque figure
		ArrayList<Vecteur> normale = new ArrayList<Vecteur>();
		normale.addAll(normaleCotes(points1));
		normale.addAll(normaleCotes(points2));

		// Projeté orthogonal de chaque point de chaque figure sur chacun des normales
		for (int i = 0; i < normale.size(); i++) {

			ArrayList<Double> projecThis = new ArrayList<Double>();
			ArrayList<Double> projecPoly = new ArrayList<Double>();

			// Projeté de this
			for (int j = 0; j < points1.length; j++) {
				double v = new Vecteur(points1[j].getX(), points1[j].getY()).produitScalaire(normale.get(i));
				projecThis.add(v);
			}

			// Projeté de p
			for (int j = 0; j < points2.length; j++) {
				double v = new Vecteur(points2[j].getX(), points2[j].getY()).produitScalaire(normale.get(i));
				projecPoly.add(v);
			}

			// Vérifie si les projetés se croisent
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
	 * Vérifie si this entre en collision avec le cercle c.
	 * 
	 * Un polygone est en collision avec un cercle si le cercle en question est en
	 * collision avec au moins un des cété du polygone.
	 * 
	 * @param c
	 *            Cercle avec lequel on vérifie la collision.
	 * @return True si le polygone est en collision avec le cercle, false sinon.
	 * @see Polygone#collisionFaceCercle(Point, Point, Cercle)
	 */
	public boolean enCollision(Cercle c) {
		Point[] points = getPoints();
		// Vérifie si le cercle est en contact avec au moins un cété du polygone
		for (int i = 0; i < points.length; i++)
			if (i < points.length - 1 && collisionFaceCercle(points[i], points[i + 1], c)
					|| i == points.length - 1 && collisionFaceCercle(points[i], points[0], c))
				return true;
		return false;
	}

	/**
	 * Vérifie si le segment ab est en collision avec le cercle c.
	 * 
	 * Un segment est en collision avec un cercle dans deux cas de figure :
	 * <ul>
	 * <li>Soit une extrémité ou deux du segment sont engoufrés dans le cercle</li>
	 * <li>Soit le cercle pénétre le segment entre les deux extrémités</li>
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
		 * supérieur au rayon c'est que le cercle ne touche pas le cété AB
		 */
		if ((Math.abs(ab.getX() * ac.getY() - ab.getY() * ac.getX()) / ab.norme()) >= c.getRayon())
			return false;
		/*
		 * Si un bout du cercle est entre le point a et b, donc si un bout du cercle est
		 * dans le segment AB alors le cercle touche le cété
		 */
		if (ab.produitScalaire(ac) >= 0 && ba.produitScalaire(bc) >= 0)
			return true;
		/*
		 * Si le sommet A ou B est dans le cercle c'est que le cété touche le cercle
		 */
		if (pointDansCercle(a, c) || pointDansCercle(b, c))
			return true;
		// Tous les cas sont traités, le cercle ne touche pas le cété
		return false;
	}

	/**
	 * Vérifie si un point est inclus dans un cercle.
	 * 
	 * Un point est inclus dans un cercle si la distance entre le point et le centre
	 * de ce point est inférieur au rayon du cercle.
	 * 
	 * @param p
	 *            Point à vérifier.
	 * @param c
	 *            Cercle à vérifier.
	 * @return True si p est inclut dans c, false sinon.
	 */
	public boolean pointDansCercle(Point p, Cercle c) {
		return Math.pow(p.getX() - c.getX(), 2) + Math.pow(p.getY() - c.getY(), 2) < Math.pow(c.getRayon(), 2);
	}

}
