package MoteurPhysique;

/**
 * Deplacement est la classe repr�sentant les diff�rentes coordonn�es d'une
 * Figure Un d�placement est caract�ris� par :
 * <ul>
 * <li>Un Point r�f�ren�ant la position</li>
 * <li>Un Vecteur r�f�ren�ant une vitesse</li>
 * <li>Un angle pour son inclinaison - Une vitesse angulaire</li>
 * <li>Une masse</li>
 * </ul>
 * 
 * @see Point
 * @see Vecteur
 */

public class Deplacement {

	/**
	 * Un Point r�f�ren�ant la position.
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
	 * Un Vecteur r�f�ren�ant la vitesse.
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
	 * L'inclinaison de la figure, exprim�e en radian.
	 * 
	 * @see Deplacement#Deplacement(double, double, double)
	 * @see Deplacement#Deplacement(double, double, double, double, double, double,
	 *      double)
	 * @see Deplacement#getAngle()
	 * @see Deplacement#move()
	 */
	private double angle;

	/**
	 * Vitesse de rotation de la figure, exprim�e en radian par unit� de temps
	 * (fr�quence de mise � jour).
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
	 * lin�aire est nulle en x et al�atoire en y. La vitesse angulaire est nulle.
	 * L'inclinaison est al�atoire.
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
	 * Initialise la position en posX et posY. La vitesse lin�aire est de vX et vY.
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
	 * Mets � jour la position en la faisant translater de son vecteur vitesse. Mets
	 * � jour l'inclinaison en la faisant pivoter en fonction de la vitesse
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
	 * @return Le Vecteur correspondant � la vitesse de translation.
	 */
	public Vecteur getVit() {
		return vit;
	}

	/**
	 * Retourne l'inclinaison.
	 * 
	 * @return L'angle en radian correspondant � l'inclinaison de la figure.
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Retourne la vitesse angulaire.
	 * 
	 * @return La valeur en radian correspondant � la vitesse de rotation, t �tant
	 *         le d�lai entre chaque appel/
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
