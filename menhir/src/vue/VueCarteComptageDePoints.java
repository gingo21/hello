package vue;

import java.awt.Graphics;

import Ressources.Ressources;
import modele.Carte;

/**
 * Repr�sentation graphique d'une carte comptage de points
 * H�rite de VueCarte
 */
public class VueCarteComptageDePoints extends VueCarte {

	private static final long serialVersionUID = 6388366603442276545L;
	
	/**
	 * Cr�er la vue graphique d'une carte Comptage de Points
	 * @param carte la carte associ�
	 * @param r l'ensemble des ressources images
	 * @param h hauteur de la carte
	 * @param l largeur de la carte
	 * @param IA carte appartenant ou non � IA  
	 */
	public VueCarteComptageDePoints(Carte carte, Ressources ressources, int h, int l, boolean IA) {
		super(carte, ressources, h, l, IA);
		this.imageFaceCarte = ressources.redimImage(ressources.getImageComptageDePoints(), h, l);
		this.imageDos = ressources.redimImage(ressources.getImageDosLutin(), h, l);
		this.hidden = false;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!this.hidden) {
			g.drawImage(imageFaceCarte, 0, 0, this);
		} else {
			g.drawImage(imageDosLutin, 0, 0, this);
		}
	}

}
