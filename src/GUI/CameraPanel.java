package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import MoteurPhysique.Cercle;
import MoteurPhysique.PolyJoueur;
import MoteurPhysique.Polygone;
import Player.Score;

/**
 * 
 * CameraPanel est la classe étendu de JPanel et implémente Runnable et qui
 * contient :
 * <ul>
 * <li>L'image de la camera</li>
 * <li>Le jeu</li>
 * <li>Les figures du jeu</li>
 * </ul>
 * 
 * @author LY David
 * @see Jeu
 * @see MoteurPhysique.Figure
 * 
 */

public class CameraPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	/**
	 * Image de la camera
	 */
	private BufferedImage image;

	/**
	 * Flux vidéo capturé par la caméra
	 */
	private VideoCapture capture;

	/**
	 * Le jeu qui permet d'appeler les méthodes de gestions du jeu
	 * 
	 * @see Jeu
	 */
	private Jeu jeu;

	/**
	 * Le panneau d'information des figures à conserver
	 * 
	 * @see FigurePanel
	 */
	private FigurePanel figurepanel;

	/**
	 * Le panneau d'information relative au joueur
	 * 
	 * @see InformationPanel
	 */
	private InformationPanel infopanel;

	/**
	 * Boolean qui vérifie si c'est la première partie
	 */
	private boolean replay = true;

	/**
	 * Boolean qui stop les boucles en cas d'arrêt du programme
	 */
	private boolean stop = false;

	/**
	 * Récupère les thread courant
	 */
	private volatile Thread currentThread;

	/**
	 * Définit la tranche de couleur à détecter à la caméra, en l'occurence le rouge
	 */
	static Scalar rgba_min = new Scalar(0, 0, 130);
	static Scalar rgba_max = new Scalar(80, 80, 255);

	/**
	 * Constructeur de CameraPanel
	 * 
	 * Initialise le jeu, panneau d'information du joueur et le panneau
	 * d'information des figures. Dessine une bordure noir. Définit le thread
	 * courant
	 * 
	 * @param jeu
	 * 			Le jeu
	 * @param figurepanel
	 * 			Le panneau d'information des figures à conserver
	 * @param infopanel
	 * 			Le panneau d'information relative au joueur
	 */
	public CameraPanel(Jeu jeu, FigurePanel figurepanel, InformationPanel infopanel) {
		this.jeu = jeu;
		this.infopanel = infopanel;
		this.figurepanel = figurepanel;
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Cam��ra",
				TitledBorder.CENTER, TitledBorder.BOTTOM, new Font("Tahoma", Font.BOLD, 12), Color.BLACK));
		this.setPreferredSize(new Dimension(650, 510));
		this.currentThread = Thread.currentThread();
	}

	/**
	 * Démarre le flux vidéo, capture et décode le flux et le stock dans
	 * webcam_image une matrice(Mat) représentant une image. Puis converti cette
	 * image en noir et blanc, avec en blanc la tranche de couleur définit par
	 * rgba_min et rgba_max. Repère le contour de l'image en noir et blanc puis
	 * appelle la fonction permettant de dessiner le PolyJoueur, jusqu'à ce que le
	 * jeu s'arrête ou que le joueur perde la partie.
	 * 
	 * @see MoteurPhysique.PolyJoueur
	 * @see Jeu
	 * @see Player.Joueur
	 */
	public void run() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		capture = new VideoCapture(0);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Mat webcam_image = new Mat();
		if (capture.isOpened()) {
			while (!stop) {
				capture.read(webcam_image);
				if (!webcam_image.empty()) {
					Core.flip(webcam_image, webcam_image, 1);
					Mat threshold = new Mat();
					Core.inRange(webcam_image, rgba_min, rgba_max, threshold);

					// Find the contours
					List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
					Imgproc.findContours(threshold, contours, new Mat(), Imgproc.RETR_LIST,
							Imgproc.CHAIN_APPROX_SIMPLE);

					// Get contour index with largest area
					double max_area = -1;
					int index = 0;
					for (int i = 0; i < contours.size(); i++) {
						if (Imgproc.contourArea(contours.get(i)) > max_area) {
							max_area = Imgproc.contourArea(contours.get(i));
							index = i;
						}
					}
					if (!jeu.perdu() && !jeu.getPause()) {
						Point[] array;
						MoteurPhysique.Point[] pts = null;
						if (index != 0) {
							array = contoursPoint(contours, index);
							pts = new MoteurPhysique.Point[array.length];

							for (int i = 0; i < array.length; i++) {
								pts[i] = new MoteurPhysique.Point(array[i].x, array[i].y);
							}

						}
						jeu.maj(pts);
					}
					MatToBufferedImage(webcam_image);
					repaint();
				}
			}
			capture.release();
		}
	}

	/**
	 * Méthode permetant la gestion du jeu. Appelle la méthode gestion dans Jeu,
	 * augment le niveau si la condition est respecté et met à jour les informations
	 * des panneaux. Tant que les conditions jeu.perdu() et stop sont respectées. 
	 * 
	 * @see Jeu#perdu()
	 * @see Jeu#gestion(MoteurPhysique.Figure)
	 * @see Player.Niveau
	 * @see InformationPanel
	 * @see FigurePanel
	 * @see CameraPanel
	 * 
	 */
	public void play() {
		while (!jeu.perdu() && !stop) {
			// Shapes management -----------------------------------
			if (!jeu.getPause()) {
				System.out.print("");
				for (int i = 0; i < jeu.getFigures().size(); i++) {
					jeu.gestion(jeu.getFigures().get(i));
				}
				infopanel.setAllLabel();
				figurepanel.setAllLabel();

				jeu.getNiveau().update();

				if (jeu.getNiveau().spawn()) {
					jeu.ajouteFigure(jeu.getNiveau().getVitesse());
					jeu.getNiveau().reset();
				}

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}
			System.out.print("");
		}
		GameOver();
	}

	/**
	 * Appeler lorsque la partie se termine. Propose de rejouer ou d'enregistrer son
	 * score ou quitter.
	 * 
	 * @see Jeu
	 * @see Player.Score
	 * @see Player.AdapteScore
	 */
	public void GameOver() {
		if (jeu.perdu() && replay) {
			int rejouer = JOptionPane.showConfirmDialog(null, "Vous avez perdu! Vous voulez rejouer?", null,
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (rejouer == JOptionPane.YES_OPTION) {
				jeu = new Jeu(jeu.getJoueur().getNom(), jeu.getX(), jeu.getY());
				jeu.dessineFigureAConserver(550, 30);
				figurepanel.setjeu(jeu);
				infopanel.setjeu(jeu);
				play();

			} else {
				this.replay = false;
				int register = JOptionPane.showConfirmDialog(null, "Enregistrer votre score?", null,
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

				if (register == JOptionPane.YES_OPTION) {
					Score s = new Score(jeu.getJoueur().getNom(), jeu.getJoueur().getScore());

					// Si le score est dans le top, ecris le score du joueur
					if (jeu.getAdaptescore().scoreAccepte(s)) {
						jeu.getAdaptescore().writeScore();
					}
					switchStop();
					currentThread.interrupt();
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
					topFrame.dispose();
					MenuFrame mf = new MenuFrame();
					new Thread(mf).start();
				} else {
					System.out.println("Fin de partie!");
					switchStop();
					currentThread.interrupt();
					System.exit(0);
				}
			}
		}
	}

	/**
	 * Retourne un tableau de Point, qui est le contours de l'image en noir et
	 * blanc.
	 * 
	 * @param contours
	 * 			Liste de MatOfPoint
	 * @param index
	 * 			nombres de points 
	 * @return un tableau de Point
	 */
	public Point[] contoursPoint(List<MatOfPoint> contours, int index) {
		// Approximate the largest contour
		MatOfPoint2f approxCurve = new MatOfPoint2f();
		MatOfPoint2f oriCurve = new MatOfPoint2f(contours.get(index).toArray());
		Imgproc.approxPolyDP(oriCurve, approxCurve, 6.0, true);

		return approxCurve.toArray();
	}

	/**
	 * Affiche la caméra à l'écran et dessine les figures.
	 * 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image == null)
			return;
		g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), null);

		PolyJoueur pj = jeu.getPJ();
		int[] axPoints = new int[pj.getPoints().length];
		int[] ayPoints = new int[pj.getPoints().length];
		for (int i = 0; i < axPoints.length; i++) {
			axPoints[i] = (int) pj.getPoints()[i].getX();
			ayPoints[i] = (int) pj.getPoints()[i].getY();
		}
		g.fillPolygon(axPoints, ayPoints, axPoints.length);

		for (int i = 0; i < jeu.getFigures().size(); i++) {
			g.setColor(jeu.getFigures().get(i).c);
			if (jeu.getFigures().get(i) instanceof Cercle) {
				Cercle c = (Cercle) jeu.getFigures().get(i);
				g.fillOval((int) (c.getX() - c.getRayon()), (int) (c.getY() - c.getRayon()), (int) (c.getRayon() * 2),
						(int) (c.getRayon() * 2));
			} else if (jeu.getFigures().get(i) instanceof Polygone) {
				Polygone p = (Polygone) jeu.getFigures().get(i);
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
	 * Transforme un Mat en BufferedImage.
	 * 
	 * @param matBGR
	 * 			Une matrice 
	 */
	public void MatToBufferedImage(Mat matBGR) {
		int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels();
		byte[] source = new byte[width * height * channels];
		matBGR.get(0, 0, source);
		image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		final byte[] target = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(source, 0, target, 0, source.length);
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
	 * 			Le jeu
	 */
	public void setjeu(Jeu jeu) {
		this.jeu = jeu;
	}

	/**
	 * Retourne stop
	 * 
	 * @return stop
	 */
	public boolean getStop() {
		return stop;
	}

	/**
	 * Redéfinit le stop par son contraire
	 * 
	 */
	public void switchStop() {
		this.stop = !stop;
	}

}