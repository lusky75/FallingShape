package Player;

/** Le Joueur nous permettra de collecter ses informations par rapport à la classe Jeu.
 * Chaque Joueur est unique, on ne peut pas recreer le même Joueur dans une nouvelle partie.
 * @author Chen Lucas
 * @see Jeu
 */

/**
 * Joueur est la classe qui represente le joueur du Jeu
 * 
 * Joueur est caracterise par :
 * <ul>
 * <li>Un nom</li>
 * <li>Un score</li>
 * <li>Points de vie</li>
 * </ul>
 * 
 * @see Jeu
 * 
 */
public class Joueur{
	
	/** String nom est le nom du Joueur sous forme de chaîne de caractère
	 *
	 */
	private final String nom;
	
	/** int score est le score du Joueur sous forme d'entier
	 * 
	 */
	private int score;
	
	/** int pv est le point de vie du Joueur sous forme d'entier
	 * 
	 */
	private int pv;
	
	/** Le constructeur prendra comme parametre un nom, 
	 * initialise son nom, son score a 0 et son pv a 3
	 * 
	 * 
	 * @param r
	 * 		nom du Joueur
	 *       
	 */
	public Joueur(String nom){
		this.nom = nom;
		this.score = 0;
		this.pv = 5;
	}

	
	/** Verifie si le Joueur a perdu
	 * 
	 * @return true si les pv du Joueur est a 0
	 */
	public boolean isDead(){
		if(this.pv == 0){
			return true;
		}
		return false;
	}

	/** Retourne nom du Joueur
	 * 
	 * @return une instance de Joueur, sous forme de chaîne de caractère
	 */
	public String getNom(){
		return nom;
	}

	/**	Retourne score du Joueur
	 * 
	 * @return une instance de Joueur, sous forme d'entier
	 */
	public int getScore(){
		return score;
	}
	
	/**	Met a jour le score du Joueur
	 * 
	 * @param score
	 * 		Le nouveau score du Joueur
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**	Retourne pv du Joueur
	 * 
	 * @return une instance de Joueur, sous forme d'entier
	 */
	public int getPV(){
		return pv;
	}
	
	/** Met a jour le pv du Joueur
	 * 
	 * @return
	 * 		Le nouveau pv du Joueur
	 */		
	public void setPV(int pv) {
		this.pv = pv;
	}

}