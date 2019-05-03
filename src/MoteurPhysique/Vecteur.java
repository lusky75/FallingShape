package MoteurPhysique;

public class Vecteur {

	private double x, y;

	public Vecteur(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vecteur(Point a, Point b) {
		this.x = b.getX() - a.getX();
		this.y = b.getY() - a.getY();
	}

	public double norme() {
		return Math.sqrt(x * x + y * y);
	}

	public void plus(Vecteur v) {
		x += v.x;
		y += v.y;
	}

	public void plus(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public void fois(double d) {
		x *= d;
		y *= d;
	}

	public void fois(double x, double y) {
		this.x *= x;
		this.y *= y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(Point a, Point b) {
		this.x = b.getX() - a.getX();
		this.y = b.getY() - a.getY();
	}

	public double produitScalaire(Vecteur v) {
		return x * v.x + y * v.y;
	}

	public Vecteur normaliser() {
		double sqrt = norme();
		if (sqrt != 0)
			return new Vecteur(x / sqrt, y / sqrt);
		return new Vecteur(0, 0);
	}

	public Vecteur normalAGauche() {
		return new Vecteur(-y, x).normaliser();
	}

	public Vecteur normalADroite() {
		return new Vecteur(y, -x).normaliser();
	}

	public static double produitvectoriel(Point a, Point b, Point c) {
		return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (c.getX() - a.getX()) * (b.getY() - a.getY());
	}

	public String toString() {
		return "v(" + x + ", " + y + ")";
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}