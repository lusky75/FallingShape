package Player;

/**
 * Niveau est la classe representant le niveau du Jeu :
 * 
 * Le niveau est caracterise par :
 * <ul>
 * <li>Un niveau</li>
 * <li>Une vitesse</li>
 * <li>Une frequence</li>
 * </ul>
 * 
 * @see Jeu
 * 
 */
public class Niveau {
	/**
	 * int niveau C'est le niveau du Niveau sous forme d'entier
	 */
	private int niveau;

	/**
	 * double vitesse C'est la vitesse du Niveau sous forme de double
	 */
	private double vitesse;

	/**
	 * int frequence est la frequence du Niveau sous forme d'entier
	 */
	private int frequence;

	/**
	 * int delai d'apparition de figure
	 */
	private int tick;
	
	/**
	 * Le constructeur de Niveau initialisera le niveau a 0,la vitesse a 1.0 et la frequence a 1
	 * 
	 */
	public Niveau() {
		this.niveau = 3;
		this.vitesse = 0.2;
		this.frequence = 500;
		this.tick = 50;
	}

	/**
	 * la gestion du Niveau Facile augmente niveau de 1, la vitesse de 0.2 et la frequence de 1
	 * 
	 */
	public void gestionNiveauFacile() {
		this.niveau += 1;
		this.vitesse += 0.2;
		this.frequence += 1;
		if (frequence > 250)
			frequence -= 20;
	}

	/**
	 * la gestion du Niveau Moyen augmente le niveau de 1 et la vitesse de 0.4 et la frequence ne sera plus augmentee
	 * 
	 */
	public void gestionNiveauMoyen() {
		this.niveau += 1;
		this.vitesse += 0.4;
		if (frequence > 250)
			frequence -= 20;
	}

	/**
	 * Retourne niveau du Niveau
	 * 
	 * @return le niveau du Niveau sous forme d'entier
	 */
	public int getNiveau() {
		return niveau;
	}

	/**
	 * Retourne vitesse du Niveau
	 * 
	 * @return la vitesse du Niveau sous forme de double
	 */
	public double getVitesse() {
		return vitesse;
	}
	
	/**
	 * Mets 锟� jour l'intervalle d'apparition
	 */
	public void update() {
		tick--;
	}
	
	/**
	 * V锟絩ifie si une figure doit appara锟絫re
	 */
	public boolean spawn() {
		return tick == 0;
	}
	
	/**
	 * Remet le compteur au d锟絙ut
	 */
	public void reset() {
		tick = frequence;
	}
	
}
