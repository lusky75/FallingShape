package MoteurPhysique;

/**
 * Point est la classe décrivant un point en deux dimensions.
 * 
 * Un point représente une coordonnée (x,y).
 */
public class Point {

	/**
	 * La coordonnée x de ce point.
	 */
	private double x;

	/**
	 * La coordonnée y de ce point.
	 */
	private double y;

	/**
	 * Constructeur Point.
	 * 
	 * Crée un point de coordonnées (dx,dy).
	 * 
	 * @param dx
	 *            Position en x.
	 * @param dy
	 *            Position en y.
	 */
	public Point(double dx, double dy) {
		this.x = dx;
		this.y = dy;
	}

	/**
	 * Translate le point avec le vecteur v.
	 * 
	 * @param v
	 *            Vecteur de translation.
	 */
	public void translate(Vecteur v) {
		x += v.getX();
		y += v.getY();
	}

	/**
	 * Redéfinit le point en (dx, dy).
	 * 
	 * @param dx
	 *            Nouvelle coordonnée en x.
	 * @param dy
	 *            Nouvelle coordonnée en y.
	 */
	public void set(double dx, double dy) {
		this.x = dx;
		this.y = dy;
	}

	/**
	 * Redéfinit le point en p.
	 * 
	 * @param p
	 *            Nouveau point.
	 */
	public void set(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * Retourne la position en x.
	 * 
	 * @return La coordonnée en x.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Retourne la position en y.
	 * 
	 * @return La coordonnée en y.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Retourne la distance entre this et a.
	 * 
	 * @param a
	 *            Point avec lequel tracer le segment.
	 * @return La distance entre this et a.
	 */
	public double distance(Point a) {
		return Math.sqrt(Math.pow(x - a.x, 2) + Math.pow(y - a.y, 2));
	}

	/**
	 * Retourne les information relative au point sous forme de String.
	 * 
	 * @return L'information avec sous la forme (x,y).
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
