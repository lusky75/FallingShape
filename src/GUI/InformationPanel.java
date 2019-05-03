package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * 
 * InformationPanel est la classe étendu de JPanel qui contient les informations
 * du joueur
 * 
 * @author LY David
 * @see Player.Joueur
 */
public class InformationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Le jeu permettant la gestion de la partie.
	 * 
	 * @see Jeu
	 */
	private Jeu jeu;

	/**
	 * JLabel d'information sur le nomJoueur choisi en début de partie, le score
	 * courant, les pvRestant et le niveau courant.
	 */
	private JLabel nomJoueur, score, pvRestant, niveau;

	/**
	 * Bouton permettant de mettre en pause le jeu.
	 */
	private JButton pause;

	/**
	 * Constructeur de InformationPanel
	 * 
	 * Initialise le bouton pause et les JLabels jeu, score, pvRestant, nomJoueur et
	 * niveau. Ajout des objets précités dans InformationPanel. Le bouton pause
	 * permet de mettre le jeu en pause via un boolean stop. Définit la taille du panneau.
	 * 
	 * @param jeu
	 *            le jeu permettant de gérer la partie
	 * @param dimension
	 *            les dimensions du JPanel, InformationPanel
	 * 
	 * @see CameraPanel#play()
	 * @see Jeu#switchPause()
	 * @see Jeu
	 */
	public InformationPanel(Jeu jeu, Dimension dimension) {
		this.jeu = jeu;
		this.setPreferredSize(dimension);
		this.score = new JLabel();
		this.pvRestant = new JLabel();
		this.nomJoueur = new JLabel();
		this.niveau = new JLabel();
		this.pause = new JButton("Pause");
		this.pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseResume();
				if(jeu.getPause()) {
					System.out.println("Le jeu est en pause");
				} else {
					System.out.println("Reprise du jeu");
				}
				repaint();
				validate();
			}
		});
		this.add(nomJoueur);
		this.add(pvRestant);
		this.add(score);
		this.add(niveau);
		this.add(pause);
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Informations",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 12), Color.BLACK));
		repaint();
	}
	
	/**
	 * Méthode qui change l'état du boolean pause
	 * 
	 * @see Jeu
	 */
	public void pauseResume() {
		this.jeu.switchPause();
	}

	/**
	 * Redéfinit tous les JLabels présent dans InformationPanel. Met à jour les
	 * informations :
	 * <ul>
	 * <li>le nom du joueur lorsque la partie commence,</li>
	 * <li>les PV(points de vie) lorsque que le joueur gagne ou perd un ou plusieurs
	 * points de vie,</li>
	 * <li>le score lorsque le joueur voit son score augmenté(e),</li>
	 * <li>le niveau lorsque le joueur augmente d'un niveau.</li>
	 * </ul>
	 * 
	 * @see InformationPanel
	 * @see Player.Joueur
	 * @see Player.Niveau
	 * 
	 */
	public void setAllLabel() {
		this.nomJoueur.setText("Joueur : " + jeu.getJoueur().getNom());
		this.pvRestant.setText(" | PV : " + jeu.getJoueur().getPV());
		this.score.setText("  | Score : " + jeu.getJoueur().getScore());
		this.niveau.setText(" | Niveau : " + jeu.getNiveau().getNiveau());
		repaint();
		validate();
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
	 * Retourne le nomJoueur
	 * 
	 * @return nomJoueur
	 * @see Player.Joueur
	 */
	public JLabel getNomJoueur() {
		return nomJoueur;
	}

	/**
	 * Redéfinit le nomJoueur
	 * 
	 * @param nomJoueur
	 *            le nom du joueur choisi en début de partie
	 * @see Player.Joueur
	 */
	public void setNomJoueur(JLabel nomJoueur) {
		this.nomJoueur = nomJoueur;
	}

	/**
	 * Retourne le score
	 * 
	 * @return score
	 * @see Player.Score
	 */
	public JLabel getScore() {
		return score;
	}

	/**
	 * Redéfinit le score
	 * 
	 * @param score
	 *            le score du joueur en fonction des figures correctement sorties
	 * @see Player.Score
	 */
	public void setScore(JLabel score) {
		this.score = score;
	}

	/**
	 * Retourne pvRestant
	 * 
	 * @return pvRestant
	 * @see Player.Joueur
	 */
	public JLabel getPvRestant() {
		return pvRestant;
	}

	/**
	 * Redéfinit pvRestant
	 * 
	 * @param pvRestant
	 *            les points de vie restant du joueur avant la défaite
	 * @see Player.Joueur
	 */
	public void setPvRestant(JLabel pvRestant) {
		this.pvRestant = pvRestant;
	}

	/**
	 * Retourne le niveau courant
	 * 
	 * @return niveau
	 * @see Player.Niveau
	 */
	public JLabel getNiveau() {
		return niveau;
	}

	/**
	 * Redéfinit le niveau.
	 * 
	 * @param niveau
	 *            la difficulté du jeu
	 * @see Player.Niveau
	 */
	public void setNiveau(JLabel niveau) {
		this.niveau = niveau;
	}

}
