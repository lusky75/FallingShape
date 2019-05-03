package Player;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;


/**
 * La classe AdapteScore contient un tableau de Score qui affichera les 10 meilleurs score des joueurs 
 * ayant enregistrer leur score lorsqu'ils auront fini leur partie
 * 
 * @author Chen Lucas
 *
 */
public class AdapteScore {
	/**
	 * Tableau de Score initiliase de taille 10
	 * @see Score
	 */
	private Score[] listeScore = new Score[10];

	/**
	 * Constructeur de la classe AdapteScore qui creer un fichier "score.ser".
	 * si le fichier existe, on prends les valeurs du fichier et on les stock dans le tableau listeScore du AdapteScore
	 * sinon, on initialisera listeScore chaque valeur du tableau par un Score vide et on ecrit les scores
	 * 
	 * Enfin, pour eviter toutes sortes d'erreur, nous afficherons un message d'erreur pour eviter que le Jeu se plante.
	 * 
	 * @see Score#Score()
	 *
	 */
	public AdapteScore() {
		try {
			File file = new File("score.ser");
			if (file.exists()) {
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
				this.listeScore = (Score[]) ois.readObject();
				ois.close();
			} else {
				for (int i = 0; i < 10; i++) {
					this.listeScore[i] = new Score();
				}
				writeScore();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erreur(0) de chargement du fichier score" + e.getCause(), "Erreur",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur(1) de chargement du fichier score" + e.getCause(), "Erreur",
					JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erreur(2) de chargement du fichier score" + e.getCause(), "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	
	/**
	 * La fonction a pour but de verifier si le Score pris en parametre sera acceptee dans l'enregistrement des scores.
	 * Nous allons alors trier le tableau listeScore, et a chaque instant nous verifions si le score est plus grand
	 * qu'une valeur du tableau, alors la valeur boolean sera alors vrai.
	 * 
	 * @param score
	 * @return verif
	 * 			La valeur boolean verif
	 */
	public boolean scoreAccepte(Score score) {
		boolean verif = false;

		for (int i = 0; i < listeScore.length; i++) {
			Score scr = new Score(); // score nul
			scr = listeScore[i];

			if (score.getPoint() >= scr.getPoint()) {
				verif = true;
				listeScore[i] = score;
				score = scr;
			}
		}
		return verif;
	}

	/**
	 * La fonction permet d'ecrire toutes les valeurs du tableau listeScore du AdapteScoredans le fichier "score.ser" 
	 * sinon renvoie des valeurs d'erreurs si le fichier n'est pas trouve.
	 * 
	 */
	public void writeScore() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(new File("score.ser"))));
			oos.writeObject(listeScore);
			oos.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erreur(0) d'enregistrement dans le fichier score", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur(1) d'enregistrement dans le fichier score", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Renvoie le tableau de Score du AdapteScore
	 * 
	 * @return listeScore
	 * 			Le tableau de Score du AdapteScore
	 */
	public Score[] getListeScore() {
		return this.listeScore;
	}

}
