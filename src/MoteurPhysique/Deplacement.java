package MoteurPhysique;

/**
 * Deplacement est la classe représentant les différentes coordonnées d'une
 * Figure Un déplacement est caractérisé par :
 * <ul>
 * <li>Un Point référençant la position</li>
 * <li>Un Vecteur référençant une vitesse</li>
 * <li>Un angle pour son inclinaison - Une vitesse angulaire</li>
 * <li>Une masse</li>
 * </ul>
 * 
 * @see Point
 * @see Vecteur
 */

public class Deplacement {

	/**
	 * Un Point référençant la position.
	 * 
	 * @see Point
	 * @see Deplacement#Deplacement(double, double, double)
	 * @see Deplacement#Deplacement(double, double, double, double, double, double,
	 *      double)
	 * @see Deplacement#getPos()
	 * @see Deplacement#move()
	 */
	private Point pos;

	/**
	 * Un Vecteur référençant la vitesse.
	 * 
	 * @see Vecteur
	 * @see Deplacement#Deplacement(double, double, double)
	 * @see Deplacement#Deplacement(double, double, double, double, double, double,
	 *      double)
	 * @see Deplacement#getVit()
	 * @see Deplacement#move()
	 */
	private Vecteur vit;

	/**
	 * L'inclinaison de la figure, exprimée en radian.
	 * 
	 * @see Deplacement#Deplacement(double, double, double)
	 * @see Deplacement#Deplacement(double, double, double, double, double, double,
	 *      double)
	 * @see Deplacement#getAngle()
	 * @see Deplacement#move()
	 */
	private double angle;

	/**
	 * Vitesse de rotation de la figure, exprimée en radian par unité de temps
	 * (fréquence de mise à jour).
	 * 
	 * @see Deplacement#Deplacement(double, double, double)
	 * @see Deplacement#Deplacement(double, double, double, double, double, double,
	 *      double)
	 * @see Deplacement#vAngulaire()
	 * @see Deplacement#move()
	 */
	private double vAngulaire;

	/**
	 * Masse de la figure.
	 * 
	 * @see Deplacement#Deplacement(double, double, double)
	 * @see Deplacement#Deplacement(double, double, double, double, double, double,
	 *      double)
	 * @see Deplacement#getMasse()
	 * @see Deplacement#move()
	 */
	private double m;

	/**
	 * Constructeur Deplacement.
	 * 
	 * Initialise la position en posX et posY et avec une masse m. La vitesse
	 * linéaire est nulle en x et aléatoire en y. La vitesse angulaire est nulle.
	 * L'inclinaison est aléatoire.
	 * 
	 * @param posX
	 *            Position en x.
	 * @param posY
	 *            Position en y.
	 * @param masse
	 *            Masse.
	 * @see Deplacement#pos
	 * @see Deplacement#vit
	 * @see Deplacement#angle
	 * @see Deplacement#vAngulaire
	 * @see Deplacement#m
	 */
	public Deplacement(double posX, double posY, double masse) {
		this.pos = new Point(posX, posY);
		this.vit = new Vecteur(0, 0);
		this.m = masse;
		vit.set(Math.random() * 5, 0);
		this.angle = Math.random() * 2 * Math.PI;
		this.vAngulaire = Math.random() * Math.random() * Math.random();
	}

	/**
	 * Constructeur Deplacement.
	 * 
	 * Initialise la position en posX et posY. La vitesse linéaire est de vX et vY.
	 * La vitesse angulaire est de vAngulaire. L'inclinaison est de angle. La masse
	 * est de m.
	 * 
	 * @param posX
	 *            Position en x.
	 * @param posY
	 *            Position en y.
	 * @param vX
	 *            Vitesse en x.
	 * @param vY
	 *            Vitesse en y.
	 * @param inclinaison
	 *            Inclinaison.
	 * @param vitAngle
	 *            Vitesse angulaire.
	 * @param masse
	 *            Masse.
	 * @see Deplacement#pos
	 * @see Deplacement#vit
	 * @see Deplacement#angle
	 * @see Deplacement#vAngulaire
	 * @see Deplacement#m
	 */
	public Deplacement(double posX, double posY, double vX, double vY, double inclinaison, double vitAngle,
			double masse) {
		this.pos = new Point(posX, posY);
		this.vit = new Vecteur(vX, vY);
		this.angle = inclinaison;
		this.vAngulaire = vitAngle;
		this.m = masse;
	}

	/**
	 * Mets à jour la position en la faisant translater de son vecteur vitesse. Mets
	 * à jour l'inclinaison en la faisant pivoter en fonction de la vitesse
	 * angulaire. Modifie le vecteur vitesse en appliquant la masse de l'objet.
	 * 
	 * @see Vecteur#plus(double, double)
	 */
	public void move() {
		pos.translate(vit);
		vit.plus(0, m);
		angle += vAngulaire;
	}

	/**
	 * Retourne la position.
	 * 
	 * @return Le Point indiquant la position actuelle.
	 */
	public Point getPos() {
		return pos;
	}

	/**
	 * Retourne le vecteur vitesse.
	 * 
	 * @return Le Vecteur correspondant à la vitesse de translation.
	 */
	public Vecteur getVit() {
		return vit;
	}

	/**
	 * Retourne l'inclinaison.
	 * 
	 * @return L'angle en radian correspondant à l'inclinaison de la figure.
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Retourne la vitesse angulaire.
	 * 
	 * @return La valeur en radian correspondant à la vitesse de rotation, t étant
	 *         le délai entre chaque appel/
	 */
	public double vAngulaire() {
		return vAngulaire;
	}

	/**
	 * Retourne la masse.
	 * 
	 * @return La masse.
	 */
	public double getMasse() {
		return m;
	}

	/**
	 * Retourne les informations relatives au Deplacement sous forme de String.
	 * 
	 * @return L'information avec sa position et sa vitesse de translation.
	 */
	@Override
	public String toString() {
		String s = "";
		s += "Pos : " + pos.getX() + ", " + pos.getY() + "\n";
		s += "Vit : " + vit.getX() + ", " + vit.getY();
		return s;
	}

}
