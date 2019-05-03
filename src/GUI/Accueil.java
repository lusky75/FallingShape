package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import MoteurPhysique.Cercle;
import MoteurPhysique.Figure;
import MoteurPhysique.Polygone;
import MoteurPhysique.Usine;
import Player.AdapteScore;

/**
 * 
 * Accueil est la classe étendu de JPanel qui contient :
 * <ul>
 * <li>Les JButton buttonjouer, buttonscore, buttonquitter</li>
 * <li>Le panneau des scores</li>
 * </ul>
 * 
 * @author LY David
 * @see ScorePanel
 * 
 */

public class Accueil extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Le titre de la fenetre qui est le nom du jeu
	 */
	private JLabel labeltitle;

	/**
	 * Les boutons permettant de naviguer
	 */
	private JButton buttonjouer, buttonscore, buttonquitter;

	/**
	 * Le panel affiche les 10 meilleurs scores enregistrés
	 * 
	 * @see ScorePanel
	 */
	private ScorePanel scorepanel;

	/**
	 * Créer une liste vide de score ou récupère les informations sauvegardes s'ils
	 * existent
	 * 
	 * @see Player.AdapteScore
	 */
	private AdapteScore adaptescore;

	/**
	 * Liste de figures
	 * 
	 * @see MoteurPhysique.Figure
	 */
	private ArrayList<Figure> figures;

	/**
	 * Usine permetant de créer des figures aléatoirement
	 * 
	 * @see MoteurPhysique.Usine
	 */
	private Usine usine;

	/**
	 * Constructeur de Accueil
	 * 
	 * 
	 * Initialisation du titre, du bouton jouer, score & quitter, des variables
	 * permettant la chute des figures, et du panneau des scores. Ajout des boutons
	 * au JPanel
	 * 
	 * @see MoteurPhysique.Usine
	 * @see MoteurPhysique.Figure
	 * 
	 * 
	 */

	public Accueil() {
		super();
		this.figures = new ArrayList<Figure>();
		this.usine = new Usine();
		this.adaptescore = new AdapteScore();
		this.scorepanel = new ScorePanel(adaptescore.getListeScore());
		this.scorepanel.setBounds(550, 150, 550, 550);
		this.scorepanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "",
				TitledBorder.CENTER, TitledBorder.BOTTOM, new Font("Tahoma", Font.BOLD, 12), Color.BLACK));

		this.setLayout(null);
		this.labeltitle = new JLabel("Falling Shapes");
		this.labeltitle.setBounds(60, 50, 550, 60);
		this.labeltitle.setFont(new Font("Roboto", Font.BOLD, 50));

		Font font = new Font("Roboto", Font.BOLD, 15);
		this.buttonjouer = new JButton("Jouer");
		this.buttonjouer.setFont(font);
		this.buttonjouer.setBounds(60, 150, 135, 50);
		this.buttonjouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame topFrame = getAncestor();
				topFrame.dispose();
				GameFrame gf = new GameFrame();
				new Thread(gf).start();
			}
		});

		this.buttonscore = new JButton("Scores");
		this.buttonscore.setFont(font);
		this.buttonscore.setBounds(60, 240, 135, 50);
		this.buttonscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add(scorepanel);
				revalidate();
			}
		});

		this.buttonquitter = new JButton("Quitter");
		this.buttonquitter.setFont(font);
		this.buttonquitter.setBounds(60, 330, 135, 50);
		buttonquitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		this.add(labeltitle);
		this.add(buttonjouer);
		this.add(buttonscore);
		this.add(buttonquitter);
	}

	/**
	 * Méthode qui affiche la chute des figures dans le menu d'accueil
	 * 
	 * @see MoteurPhysique.Figure
	 * @see MoteurPhysique.Usine
	 */
	public void animation() {
		while (true) {
			Random rand = new Random();
			int n = 1281;
			int x = rand.nextInt() % n;
			this.figures.add(usine.getFigure(x, 0, 0, 1, 0.1));
			for (Figure fig : figures) {
				fig.move();
			}
			repaint();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Méthode qui affiche la chute des figures
	 * 
	 * @see MoteurPhysique.Figure
	 * @see MoteurPhysique.Polygone
	 * @see MoteurPhysique.Cercle
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i < figures.size(); i++) {
			g.setColor(figures.get(i).c);
			if (figures.get(i) instanceof Cercle) {
				Cercle c = (Cercle) figures.get(i);
				g.fillOval((int) (c.getX() - c.getRayon()), (int) (c.getY() - c.getRayon()), (int) (c.getRayon() * 2),
						(int) (c.getRayon() * 2));
			} else if (figures.get(i) instanceof Polygone) {
				Polygone p = (Polygone) figures.get(i);
				int[] xPoints = new int[p.getPoints().length];
				int[] yPoints = new int[p.getPoints().length];
				for (int k = 0; k < xPoints.length; k++) {
					xPoints[k] = (int) p.getPoints()[k].getX();
					yPoints[k] = (int) p.getPoints()[k].getY();
				}
				g.fillPolygon(xPoints, yPoints, xPoints.length);
			}
		}
	}

	/**
	 * Retourne la liste de figures
	 * 
	 * @return figures
	 */
	public ArrayList<Figure> getFigures() {
		return figures;
	}

	/**
	 * Redéfinit la liste de figures
	 * 
	 * @param figures
	 */
	public void setFigures(ArrayList<Figure> figures) {
		this.figures = figures;
	}

	/**
	 * Retourne la JFrame dans laquelle Accueil est contenu
	 * 
	 * @return JFrame
	 */
	public JFrame getAncestor() {
		return (JFrame) SwingUtilities.getWindowAncestor(this);
	}

	/**
	 * Retourne le JLabel labeltitle
	 * 
	 * @return labeltitle
	 */
	public JLabel getLabeltitle() {
		return labeltitle;
	}

	/**
	 * Retourne le bouton jouer
	 * 
	 * @return buttonjouer
	 */
	public JButton getJouer() {
		return buttonjouer;
	}

	/**
	 * Retourne le bouton score
	 * 
	 * @return buttonscore
	 */
	public JButton getScore() {
		return buttonscore;
	}

	/**
	 * Retourne le bouton quitter
	 * 
	 * @return buttonquitter
	 */
	public JButton getQuitter() {
		return buttonquitter;
	}

}
