package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import MoteurPhysique.Cercle;
import MoteurPhysique.Polygone;

/**
 * FigurePanel est la classe étendu de JPanel qui contient les informations des
 * figures à conserver.
 * 
 * @author LY David
 *
 * @see MoteurPhysique.Figure
 */

public class FigurePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Le jeu permettant la gestion de la partie.
	 * 
	 * @see Jeu
	 */
	private Jeu jeu;

	/**
	 * JLabel d'information sur le combinaison, figureEject et figuresAconserver.
	 * 
	 * @see MoteurPhysique.Figure
	 */
	private JLabel combinaison, figureEject, figuresAconserver;

	/**
	 * 
	 * Constructeur de FigurePanel
	 * 
	 * Initialise les JLabels combinaison, figureEject et figuresAconserver. Ajout
	 * des objets précités dans FigurePanel. Définit la taille du panneau.
	 * 
	 * @param jeu
	 *            le jeu permettant de gérer la partie
	 * @param dimension
	 *            les dimensions du JPanel, InformationPanel
	 * 
	 * @see Jeu
	 * @see MoteurPhysique.Figure
	 * 
	 */
	public FigurePanel(Jeu jeu, Dimension dimension) {
		this.jeu = jeu;
		this.setPreferredSize(dimension);
		this.figuresAconserver = new JLabel();
		this.combinaison = new JLabel();
		this.figureEject = new JLabel();
		this.add(combinaison);
		this.add(figureEject);
		this.add(figuresAconserver);
		this.jeu.dessineFigureAConserver(550, 30);
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Figures",
				TitledBorder.CENTER, TitledBorder.BOTTOM, new Font("Tahoma", Font.BOLD, 12), Color.BLACK));
		repaint();
	}

	/**
	 * Méthode permettant de dessiner la ou les figures à conserver tout au long du
	 * jeu tant que la partie n'est pas terminée.
	 * 
	 * @see Jeu
	 * @see MoteurPhysique.Figure
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < jeu.getFiguresAconserver().size(); i++) {
			g.setColor(jeu.getFiguresAconserver().get(i).c);
			if (jeu.getFiguresAconserver().get(i) instanceof Cercle) {
				Cercle c = (Cercle) jeu.getFiguresAconserver().get(i);
				g.fillOval((int) (c.getX() - c.getRayon()), (int) (c.getY() - c.getRayon()), (int) (c.getRayon() * 2),
						(int) (c.getRayon() * 2));
			} else if (jeu.getFiguresAconserver().get(i) instanceof Polygone) {
				Polygone p = (Polygone) jeu.getFiguresAconserver().get(i);
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
	 * Redéfinit tous les JLabels présent dans FigurePanel. Met à jour les
	 * informations :
	 * <ul>
	 * <li>combinaison, le nombre de figures à sauvegarder restantes</li>
	 * <li>figureEject, le nombre de figures à ejecter avant le prochain niveau</li>
	 * <li>figuresAconserver, la ou les figures à conserver</li>
	 * </ul>
	 * 
	 * @see Jeu
	 * @see MoteurPhysique.Figure
	 */
	public void setAllLabel() {
		int combinaison = 3 - jeu.getCombinaison();
		this.combinaison.setText("Combinaison restante : " + combinaison);
		int figureEject = 10 - jeu.getFigureEject();
		this.figureEject.setText(" |  Figure a ejecter restante : " + figureEject);
		this.figuresAconserver.setText("  |  Figure à conserver :                                 ");
		repaint();
		revalidate();
	}

	/**
	 * Retourne le jeu
	 * 
	 * @return jeu
	 * @see Jeu
	 */
	public Jeu getjeu() {
		return jeu;
	}
	
	/**
	 * Redéfinit le jeu
	 * 
	 * @param jeu
	 *            le jeu permettant de gérer la partie
	 * @see Jeu
	 */
	public void setjeu(Jeu jeu) {
		this.jeu = jeu;
	}

	/**
	 * Retourne combinaison
	 * 
	 * @return combinaison
	 */
	public JLabel getCombinaison() {
		return combinaison;
	}

	/**
	 * Redéfinit combinaison
	 * 
	 * @param combinaison
	 * 			le nombre de figures à sauvegarder
	 * 
	 * @see Jeu
	 */
	public void setCombinaison(JLabel combinaison) {
		this.combinaison = combinaison;
	}

	/**
	 * Retourne figureEject
	 * 
	 * @return figureEject 
	 */
	public JLabel getFigureEject() {
		return figureEject;
	}

	/**
	 * Redéfinit figureEject
	 * 
	 * @param figureEject
	 * 			le nombre de figures à ejecter
	 */
	public void setFigureEject(JLabel figureEject) {
		this.figureEject = figureEject;
	}

	/**
	 * Retourne figuresAconserver
	 * 
	 * @return figuresAconserver
	 */
	public JLabel getFiguresAconserver() {
		return figuresAconserver;
	}

	/**
	 * Redéfinit figureAconserver
	 * 
	 * @param figuresAconserver
	 * 			le JLabel d'information		
	 */
	public void setFiguresAconserver(JLabel figuresAconserver) {
		this.figuresAconserver = figuresAconserver;
	}

}
