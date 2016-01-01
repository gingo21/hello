package vue;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Ressources.Ressources;
import modele.Carte;

public class VueCarteTaupesGeantes extends VueCarteAliee{

	public VueCarteTaupesGeantes(Carte carte,Ressources ressources, int h, int l ) {
		super(carte, ressources, h, l);
		this.imageFaceCarte = ressources.redimImage(ressources.getImageTaupesGeantes(), h, l);
	}

}