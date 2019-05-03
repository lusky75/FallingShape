package MoteurPhysique;

import java.awt.Color;

/**
 * Cercle est la classe représentant un cercle.
 * 
 * Un cercle est caractérisé par :
 * <ul>
 * <li>Un rayon</li>
 * <li>Une couleur</li>
 * <li>Ses diverses coordonnées</li>
 * </ul>
 * 
 * @see Figure
 * @see Deplacement
 * 
 */

public class Cercle extends Figure {

	/**
	 * Le rayon du cercle.
	 * 
	 * @see Cercle#Cercle(int, Color, Deplacement)
	 * @see Cercle#getRayon()
	 */
	private int rayon;

	/**
	 * Constructeur Cercle
	 * 
	 * Crée un Cercle de rayon r, de couleur c et avec des coordonnées de coord.
	 *
	 * @param r
	 *            Rayon du Cercle.
	 * @param c
	 *            Couleur du Cercle.
	 * @param coord
	 *            Position et vitesse du Cercle.
	 * 
	 * @see Cercle#rayon
	 * @see java.awt.Color
	 */
	public Cercle(int r, Color c, Deplacement coord) {
		this.rayon = r;
		this.c = c;
		this.coord = coord;
	}

	public boolean enCollision(Figure f) {
		return f.enCollision(this);
	}

	/**
	 * Vérifie si this entre en collision avec le Cercle c.
	 * 
	 * Deux cercles sont en collision si la distance entre leurs deux centres est
	 * inférieur à la somme de leurs deux rayons.
	 * 
	 * @param c
	 *            Cercle avec lequel vérifier la collision.
	 * @return true si this est en collision avec le Cercle c.
	 */
	public boolean enCollision(Cercle c) {
		return Math.sqrt(Math.pow(c.getX() - getX(), 2) + Math.pow(c.getY() - getY(), 2)) < rayon + c.rayon;
	}

	/**
	 * Vérifie si this entre en collision avec le Polygone p.
	 * 
	 * @param p
	 *            Polygone avec lequel vérifier la collision.
	 * @return true si this est en collision avec le Polygone p.
	 * @see Polygone#enCollision(Cercle)
	 */
	public boolean enCollision(Polygone p) {
		return p.enCollision(this);
	}

	/**
	 * Retourne le rayon du Cercle.
	 * 
	 * @return Le rayon.
	 */
	public int getRayon() {
		return rayon;
	}

	public boolean identique(Object o) {
		if (o instanceof Cercle) {
			Cercle cc = (Cercle) o;
			return c == cc.c;
		}
		return false;
	}

	/**
	 * Retourne les informations relatives au Cercle sous forme de String.
	 * 
	 * @return L'information du Cercle, avec son rayon, sa couleur et son
	 *         Deplacement.
	 */
	@Override
	public String toString() {
		return "Cercle - " + rayon + " - " + c + " :" + coord;
	}

}
