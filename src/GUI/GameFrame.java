package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * 
 * GameFrame est la classe étendu de JFrame qui contient :
 * <ul>
 * <li>InformationPanel</li>
 * <li>CameraPanel</li>
 * <li>FigurePanel</li>
 * </ul>
 * 
 * @author LY David
 * @see InformationPanel
 * @see CameraPanel
 * @see FigurePanel
 * 
 */

public class GameFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	/**
	 * Le JPanel principal content :
	 * <ul>
	 * <li>InformationPanel</li>
	 * <li>CameraPanel</li>
	 * <li>FigurePanel</li>
	 * </ul>
	 * 
	 * @see InformationPanel
	 * @see CameraPanel
	 * @see FigurePanel
	 */
	private JPanel contentMainPanel;

	/**
	 * Le JPanel content les informations du joueur.
	 * 
	 * @see InformationPanel
	 */
	private InformationPanel infopanel;

	/**
	 * Le JPanel content les informations du jeu relative aux figures.
	 * 
	 * @see FigurePanel
	 */
	private FigurePanel figurepanel;

	/**
	 * Le JPanel content la caméra.
	 * 
	 * @see CameraPanel
	 */
	private CameraPanel campanel;

	/**
	 * Un objet jeu commun à toutes les classes.
	 * 
	 * @see Jeu
	 */
	private Jeu jeu;

	/**
	 * int qui définit la taille de la fenêtre et les donnent en parametre pour
	 * créer l'objet Jeu.
	 * 
	 * @see Jeu
	 */
	private int frameWidth = 640, frameHeight = 480, panelHeight = 50;

	/**
	 * Thread utilisé pour lancer le CameraPanel.
	 * 
	 * @see CameraPanel
	 */
	private Thread th;

	/**
	 * Une barre de navigation.
	 * 
	 */
	private JMenuBar menu;

	/**
	 * Un élément de la barre de navigation.
	 * 
	 */
	private JMenu fichier;

	/**
	 * Sous-éléments dans la bar de menu qui permettent de retourner au menu
	 * principal ou de quitter l'application.
	 * 
	 * @see Accueil
	 */
	private JMenuItem retour, quitter;

	/**
	 * Constructeur de GameFrame
	 * 
	 * Définit les parametres de la fenetre, comme le titre, la taille, la position
	 * à l'écran, l'action lors de la fermeture de la fenetre.
	 * 
	 * Définit l'action lors de la fermeture de la fenetre, arrête la boucle dans la
	 * méthode play() dans CameraPanel, stop le Thread et ferme la fenetre.
	 * 
	 * Initialisent les JPanels et les ajoutent à la fenetre.
	 * 
	 * Initialise la barre de menu et ajoute les éléments.
	 * 
	 * Initialise et lance le Thread pour le CameraPanel.
	 * 
	 * @see CameraPanel
	 */
	public GameFrame() {
		this.setTitle("Falling Shape");
		this.setSize(frameWidth + 40, frameHeight + panelHeight * 5);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.contentMainPanel = new JPanel();
		this.jeu = new Jeu(choisirNom(), frameWidth, frameHeight);
		this.infopanel = new InformationPanel(jeu, new Dimension(frameWidth, panelHeight));
		this.figurepanel = new FigurePanel(jeu, new Dimension(frameWidth, panelHeight + panelHeight / 4));
		this.campanel = new CameraPanel(jeu, figurepanel, infopanel);
		this.th = new Thread(campanel);

		this.contentMainPanel.setLayout(new BorderLayout());
		this.contentMainPanel.add(infopanel, BorderLayout.NORTH);
		this.contentMainPanel.add(campanel, BorderLayout.CENTER);
		this.contentMainPanel.add(figurepanel, BorderLayout.SOUTH);
		this.setContentPane(contentMainPanel);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				stopLoop();
				th.interrupt();
				dispose();
				System.exit(0);
			}
		});

		this.menu = new JMenuBar();
		this.fichier = new JMenu("Fichier");
		this.fichier.setMnemonic('F');

		this.retour = new JMenuItem("Retour");
		this.retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopLoop();
				th.interrupt();
				dispose();
				MenuFrame mf = new MenuFrame();
				new Thread(mf).start();
			}
		});

		/*
		 * if(jeusolo.perdu()) Reprendre.setEnabled(false); sinon true
		 */

		this.quitter = new JMenuItem("Quitter");
		this.quitter.setMnemonic('Q');
		this.quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
		this.quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopLoop();
				th.interrupt();
				dispose();
				System.exit(0);
			}
		});

		this.fichier.add(retour);
		this.fichier.addSeparator();
		this.fichier.add(quitter);

		this.menu.add(fichier);
		this.setJMenuBar(menu);

		this.setVisible(true);

		this.th.start();

	}

	/**
	 * Lance la méthode play() dans CameraPanel
	 * 
	 * @see CameraPanel#play()
	 */
	public void run() {
		campanel.play();
	}

	/**
	 * Lance la méthode switchStop() dans CameraPanel
	 * 
	 * @see CameraPanel
	 */
	public void stopLoop() {
		this.campanel.switchStop();
	}

	/**
	 * Affiche un popup qui demande le nom du joueur.
	 * 
	 * @see Player.Joueur
	 * 
	 * @return le nom du joueur
	 */
	public String choisirNom() {
		String nom = (String) JOptionPane.showInputDialog(null, "Entrez votre pseudonyme :", "Inscription",
				JOptionPane.QUESTION_MESSAGE);
		if (nom.equals("")) {
			JOptionPane.showMessageDialog(null, "Vous devez avoir un nom pour jouer!", "", JOptionPane.ERROR_MESSAGE);
			return choisirNom();
		}
		return nom;
	}

	/**
	 * Retourne le panneau principal
	 * 
	 * @return contentMainPanel
	 */
	public JPanel getContentMainPanel() {
		return contentMainPanel;
	}

	/**
	 * Redéfinit le panneau principal
	 * 
	 * @param contentMainPanel
	 */
	public void setContentMainPanel(JPanel contentMainPanel) {
		this.contentMainPanel = contentMainPanel;
	}

	/**
	 * Retourne le panneau d'information
	 * 
	 * @return infoPanel
	 */
	public InformationPanel getInfopanel() {
		return infopanel;
	}

	/**
	 * Redéfinit le panneau d'information
	 * 
	 * @param infopanel
	 */
	public void setInfopanel(InformationPanel infopanel) {
		this.infopanel = infopanel;
	}

	/**
	 * Retourne le panneau d'information des figures à conserver
	 * 
	 * @return figurepanel
	 */
	public FigurePanel getFigurepanel() {
		return figurepanel;
	}

	/**
	 * Redéfinit le panneau d'information des figures à conserver
	 * 
	 * @param figurepanel
	 */
	public void setFigurepanel(FigurePanel figurepanel) {
		this.figurepanel = figurepanel;
	}

	/**
	 * Retourne le panneau de la caméra
	 * 
	 * @return campanel
	 */
	public CameraPanel getCampanel() {
		return campanel;
	}

	/**
	 * Redéfinit le panneau de la caméra
	 *
	 * @param campanel
	 */
	public void setCampanel(CameraPanel campanel) {
		this.campanel = campanel;
	}

	/**
	 * Retourne le jeu
	 * 
	 * @return jeu
	 */
	public Jeu getJeu() {
		return jeu;
	}

	/**
	 * Redéfinit le jeu
	 *
	 * @param jeu
	 */
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

	/**
	 * Retourne la largeur de la fenetre
	 * 
	 * @return frameWidth
	 */
	public int getFrameWidth() {
		return frameWidth;
	}

	/**
	 * Redéfinit la largeur de la fenetre
	 * 
	 * @param frameWidth
	 */
	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	/**
	 * Retourne la hauteur de la fenetre
	 * 
	 * @return frameHeight
	 */
	public int getFrameHeight() {
		return frameHeight;
	}

	/**
	 * Redéfinit la hauteur de la fenetre
	 *
	 * @param frameHeight
	 */
	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}

	/**
	 * Retourne la hauteur des panels
	 * 
	 * @return panelHeight
	 */
	public int getPanelHeight() {
		return panelHeight;
	}

	/**
	 * Redéfinit la hauteur des panels
	 * 
	 * @param panelHeight
	 */
	public void setPanelHeight(int panelHeight) {
		this.panelHeight = panelHeight;
	}

	/**
	 * Retourne le thread utilisé pour lancer CameraPanel
	 * 
	 * @return th
	 */
	public Thread getTh() {
		return th;
	}

	/**
	 * Redéfinit le thread
	 *
	 * @param th
	 */
	public void setTh(Thread th) {
		this.th = th;
	}

}
