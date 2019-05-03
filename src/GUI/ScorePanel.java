package GUI;

import Player.Score;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ScorePanel est la classe etendu de JPanel qui contient le tableau des scores
 * 
 * @author LY David, Chen Lucas
 * @see Player.Score
 */

public class ScorePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Un tableau de int qui definit la taille en pixel de la police des scores en
	 * fonction de son classement
	 * 
	 * @see Player.Score
	 * 
	 */
	private int[] policeSize = { 25, 24, 23, 22, 21, 20, 19, 18, 17, 16 };

	/**
	 * Un tableau de Score qui contient les 10 meilleurs scores
	 * 
	 * @see Player.Score
	 */
	private Score[] list;

	/**
	 * Constructeur de ScorePanel
	 * 
	 * Définit l'attribut list, l'arrière plan en blanc puis appelle la méthode
	 * AffichePanel()
	 * 
	 * @param list
	 *            Listes des scores potentiellement existante
	 * 
	 * @see Player.AdapteScore
	 */
	public ScorePanel(Score[] list) {
		this.list = list;
		this.setBackground(Color.white);
		AffichePanel();
	}

	/**
	 * Récupération des informations et creation du panneau des meilleurs scores
	 * 
	 * @see Player.Score
	 */
	public void AffichePanel() {
		JLabel text = new JLabel("Meilleurs Scores : ");
		this.add(text);
		text.setPreferredSize(new Dimension(300, 40));
		text.setFont(new Font("Comics Sans MS", Font.BOLD, 30));

		for (int i = 0; i < this.list.length; i++) {
			JLabel lab = new JLabel(Integer.toString(i + 1) + this.list[i].toString());
			lab.setFont(new Font("Comics Sans MS", Font.BOLD, policeSize[i]));
			lab.setPreferredSize(new Dimension(400, 40));
			this.add(lab);
		}
	}
}
