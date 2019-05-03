package MoteurPhysique;

import java.awt.Color;

/**
 * Figure est la classe repr�sentant une figure du jeu
 * 
 * Une figure est caract�ris�e par :
 * <ul>
 * <li>Une couleur</li>
 * <li>Ses diverses coordonn�es</li>
 * </ul>
 * 
 * Une figure a par ailleurs besoin d'une forme :une figure est soit un cercle
 * soit un polygone convexe sans trou.
 * 
 * @see Cercle
 * @see Polygone
 */
public abstract class Figure {

	/**
	 * Couleur de la figure.
	 * 
	 * @see Figure#identique(Figure)
	 * @see Figure#getColor()
	 */
	public Color c;

	/**
	 * Diverses coordonn�es de la figure.
	 * 
	 * @see Deplacement
	 */
	public Deplacement coord;

	/**
	 * D�lai entre chaque collision
	 * 
	 * @see PolyJoueur#collision(Figure)
	 */
	protected int delai = 0;

	/**
	 * V�rifie si this est �gale � f.
	 * 
	 * Deux figures sont dites �gales si elles sont la m�me forme et si elles sont
	 * de la m�me couleur. Seuls les cercles et les polygones r�guliers sont pris en
	 * compte; ceux qui ne sont pas pris en charge retourne false.
	 * 
	 * @param f
	 *            Figure avec laquelle v�rifier l'�galit�.
	 * @return Retourne true s'ils ont la m�me forme.
	 */
	public boolean identique(Object o) {
		return false;
	}

	public abstract boolean enCollision(Cercle c);

	public abstract boolean enCollision(Figure f);

	public abstract boolean enCollision(Polygone p);

	/**
	 * Applique la collision �lastique � deux figures qui sont en contacts. La
	 * vitesse de rotation n'est pas pris en compte.
	 * 
	 * La formule est de la fa�on suivante :
	 * <ul>
	 * <li>v1' = v1 - 2 * m2/(m1 + m2) * &lt;v1 - v2, x1 - x2&gt; / ||x1 - x2||� *
	 * (x1 - x2)</li>
	 * <li>v2' = v2 - 2 * m1/(m1 + m2) * &lt;v1 - v2, x1 - x2&gt; / ||x2 - x1||� *
	 * (x2 - x1)</li>
	 * </ul>
	 * <p>
	 * Avec vi' = vitesse de i apr�s la collision, vi = vitesse de i avant la
	 * collision, mi = masse de i, xi = position de i, &lt;a,b&gt; produit scalaire
	 * entre a et b<br>
	 * Par ailleurs on peut constater que &lt;v1 - v2, x1 - x2&gt; = &lt;v1 - v2, x1
	 * - x2&gt; et ||x1 - x2||� = ||x2 - x1||� et que x1 - x2 = -(x2 - x1) : pas
	 * besoin de recalculer cette section deux fois.
	 * </p>
	 * 
	 * <p>
	 * De plus on ne veut appliquer la collision seulement lorsque this et f se
	 * rapprochent l'un de l'autre : en effet s'ils s'�loignent d�j� l'un de l'autre
	 * rappliquer la collision les feraient de rapprocher et ainsi de suite et cr�er
	 * des figures statiques coll�es entre elles.<br>
	 * Pour cela on v�rifie si la distance entre les deux figures s'accroit ou
	 * d�croit. Si on prends comme point de vue 1, on a :
	 * </p>
	 * <ul>
	 * <li>v2" = v2 - v1 vitesse relative par rapport � v1</li>
	 * <li>x2" = x2 - x1 position relative par rapport � x1</li>
	 * </ul>
	 * <p>
	 * Avec le produit scalaire, si &lt;v2, x2&gt; est positif alors les deux
	 * figures s'�loignent et si &lt;v2, x2&gt; est nul alors la distance ne change
	 * pas : la fonction ne fait rien. Donc collision(Figure) modifie les vitesses
	 * si et seulement si &lt;v2, x2&gt; est n�gatif.
	 * </p>
	 * 
	 * @param f
	 *            Figure avec laquelle appliquer la collision.
	 * @see Vecteur#plus(double, double)
	 * @see Vecteur#produitScalaire(Vecteur)
	 */
	public void collision(Figure f) {

		double distanceX = getX() - f.getX();
		double distanceY = getY() - f.getY();
		double distanceCarre = Math.pow(distanceX, 2) + Math.pow(distanceY, 2);

		double vitesseX = f.coord.getVit().getX() - coord.getVit().getX();
		double vitesseY = f.coord.getVit().getY() - coord.getVit().getY();

		/*
		 * On pourrait faire appel � Vecteur.produitScalaire(Vecteur v) pour le produit
		 * scalaire mais pas besoin d'initialiser deux nouvelles instances de Vecteur
		 * pour la suite.
		 */
		double prodScalaire = vitesseX * distanceX + vitesseY * distanceY;

		if (prodScalaire <= 0)
			return;

		double collision = prodScalaire / distanceCarre;
		double collisionX = collision * distanceX;
		double collisionY = collision * distanceY;

		double masse = coord.getMasse() + f.coord.getMasse();
		double masseThis = 2 * f.coord.getMasse() / masse;
		double masseFig = 2 * coord.getMasse() / masse;

		coord.getVit().plus(masseThis * collisionX, masseThis * collisionY);
		f.coord.getVit().plus(-(masseFig * collisionX), -(masseFig * collisionY));

	}

	/**
	 * Retourne la position en x de la figure
	 * 
	 * @return Position en x
	 * @see Figure#coord
	 */
	public double getX() {
		return coord.getPos().getX();
	}

	/**
	 * Retourne la position en y de la figure
	 * 
	 * @return Position en y
	 * @see Figure#coord
	 */
	public double getY() {
		return coord.getPos().getY();
	}

	/**
	 * Retourne la position de la figure
	 * 
	 * @return Point indiquant la position
	 * @see Figure#coord
	 */
	public Point getPos() {
		return coord.getPos();
	}

	/**
	 * Deplace la figure
	 * 
	 * @see Figure#coord
	 */
	public void move() {
		coord.move();
		if (delai > 0)
			delai--;
	}

	/**
	 * Retourne la couleur de la figure
	 * 
	 * @return Couleur de la figure
	 */
	public Color getColor() {
		return c;
	}

}
