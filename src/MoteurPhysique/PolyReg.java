package MoteurPhysique;

import java.awt.Color;

/**
 * PolyReg représente un polygone régulier.
 *
 */
public class PolyReg extends Polygone {

	/**
	 * Rayon du polygone.
	 */
	private int rayon;

	/**
	 * Nombre de sommets du polygone.
	 */
	private int nbPoints;

	/**
	 * Constructeur PolyReg
	 * 
	 * @param rayon
	 *            Rayon du PolyReg
	 * @param nbPoints
	 *            Nombre de sommets
	 * @param c
	 *            Couleur
	 * @param coord
	 *            Coordonnées
	 */
	public PolyReg(int rayon, int nbPoints, Color c, Deplacement coord) {
		this.rayon = rayon;
		this.nbPoints = nbPoints;
		this.c = c;
		this.coord = coord;
	}
	
	/**
	 * Calcule les coordonnées des sommets du polygone.
	 * 
	 * La position des sommets dépendent de l'inclinaison de la figure.
	 * 
	 * @return Coordonnées des sommets.
	 */
	public Point[] getPoints() {
		Point[] pts = new Point[nbPoints];
		for (int i = 0; i < nbPoints; i++) {
			double angle;
			try {
				angle = coord.getAngle();
			} catch (Exception e) {
				angle = 0;
			}
			double posX = getX() + Math.cos(angle + (2 * i * Math.PI) / nbPoints) * rayon;
			double posY = getY() - Math.sin(angle + (2 * i * Math.PI) / nbPoints) * rayon;
			pts[i] = new Point(posX, posY);
		}
		return pts;
	}

	public int getRayon() {
		return rayon;
	}

	public boolean identique(Object o) {
		if (o instanceof PolyReg) {
			PolyReg pr = (PolyReg) o;
			return c == pr.c && nbPoints == pr.nbPoints;
		}
		return false;
	}

	public String toString() {
		String s = "";
		Point[] pts = getPoints();
		if (pts.length == 3)
			s = "Triangle : ";
		else if (pts.length == 4)
			s = "Carrï¿½ : ";
		else
			s = pts.length + "gone : ";
		for (int i = 0; i < pts.length; i++) {
			s += pts[i] + " ";
		}
		return s;
	}

}
