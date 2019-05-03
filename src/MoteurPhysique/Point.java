package MoteurPhysique;

/**
 * Point est la classe d�crivant un point en deux dimensions.
 * 
 * Un point repr�sente une coordonn�e (x,y).
 */
public class Point {

	/**
	 * La coordonn�e x de ce point.
	 */
	private double x;

	/**
	 * La coordonn�e y de ce point.
	 */
	private double y;

	/**
	 * Constructeur Point.
	 * 
	 * Cr�e un point de coordonn�es (dx,dy).
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
	 * Red�finit le point en (dx, dy).
	 * 
	 * @param dx
	 *            Nouvelle coordonn�e en x.
	 * @param dy
	 *            Nouvelle coordonn�e en y.
	 */
	public void set(double dx, double dy) {
		this.x = dx;
		this.y = dy;
	}

	/**
	 * Red�finit le point en p.
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
	 * @return La coordonn�e en x.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Retourne la position en y.
	 * 
	 * @return La coordonn�e en y.
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
