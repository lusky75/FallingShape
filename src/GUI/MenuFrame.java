package GUI;

import javax.swing.*;

/**
 * 
 * MenuFrame est la classe étendu de JFrame qui contient le panneau Accueil
 * 
 * @author LY David
 * @see Accueil
 */

public class MenuFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	/**
	 * Le JPanel Accueil contient le menu d'accueil.
	 * 
	 * @see Accueil
	 */
	private Accueil accueil;

	/**
	 * Constructeur de MenuFrame
	 * 
	 * Définit les parametres de la fenetre, comme le titre, la taille, la position
	 * à l'écran, l'action lors de la fermeture de la fenetre et n'autorise pas le
	 * redimensionnement.
	 * 
	 * Initialise le panneau accueil et l'ajoute à la fenetre.
	 * 
	 * Effectue un thread sleep de 800ms le temps que la fenetre s'affiche
	 * correctement puis lance l'animation des figures
	 * 
	 * @see Accueil
	 * @see Accueil#animation()
	 */
	public MenuFrame() {
		this.setTitle("Falling Shapes");
		this.setSize(1280, 960);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.accueil = new Accueil();
		this.setContentPane(accueil);

		this.setVisible(true);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lance la méthode animation dans Accueil
	 * 
	 * @see Accueil#animation()
	 */
	public void run() {
		accueil.animation();
	}
}
