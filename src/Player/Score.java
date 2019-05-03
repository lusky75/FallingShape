package Player;

import java.io.Serializable;

/**
 * La classe Score contient un nom et un point, elle sera utilisee pour enregistrer les scores des joueurs lorsqu'ils auront
 * enregistrer leur scores. Cet enregistrement fera appel au Serializable, qui nous permet de stocker en memoire le Score 
 * dans un fichier
 * 
 * @author Chen Lucas
 *
 */
public class Score implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Le nom du Score sous forme de chaine de caractere
	 */
	private String nom;
	
	/**
	 * Les points du Score sous forme d'entier
	 */
	private int point;
	
	/**
	 * Construit un Score vierge, nom pour valeur "........" et un point a 0
	 */
	public Score(){
		this.nom = new String("........");
	    this.point = 0;
	}

	/**
	 * Construit un score, en prenant les valeurs du parametre
	 * 
	 * @param nom
	 * 			Le nom du Score
	 * @param point
	 * 			Les points du Score
	 */
	public Score(String nom, int point){
		this.nom = nom;
		this.point = point;
	}
	
	/**
	 * Renvoie les points du Score
	 * 
	 * @return point
	 * 			Les points du Score
	 */
	public int getPoint(){
		return point;
	}

	/**
	 * Renvoie le nom du Score
	 * 
	 * @return nom
	 * 			Le nom du Score
	 */
	public String getNom(){
		return nom;
	}
  
	/**
	 * Met a jour le nom du Score par le nom pris en parametre
	 * Si le nom pris en parametre est nulle, nous le remplacerons par un nom vierge "........"
	 * 
	 * @param nom
	 * 			Le nouveau nom du Score
	 */
	public void setNom(String nom){
		this.nom = (nom != null) ? nom : "........";
	}
	
	/**
	 * Affichera les valeurs du Score sous forme de chaine de caractere
	 */
	public String toString(){
		return " "+nom+" : "+point+" pts ";	  
	}

}