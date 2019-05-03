package GUI;

/**
 * 
 * Main est la classe permet de lancer le jeu
 * 
 * @author LY David
 */

public class Main {
	
	/**
	 * Le main qui permet de lancer le menu d'accueil
	 * 
	 * @see MenuFrame
	 * 
	 * @param args
	 * 			argument du main
	 */
	public static void main(String[] args) {
		MenuFrame mf = new MenuFrame();
		new Thread(mf).start();
	}

}
