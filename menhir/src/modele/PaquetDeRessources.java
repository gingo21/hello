package modele;

import java.io.Serializable;
import java.util.Observable;

public abstract class PaquetDeRessources extends Observable implements Serializable {

	private int grainesDeMenhir;

	public PaquetDeRessources(int nombreDeGraines) {
		super();
		this.grainesDeMenhir = nombreDeGraines;
	}

	public int getGrainesDeMenhir() {
		return grainesDeMenhir;
	}

	
	public void setGrainesDeMenhir(int grainesDeMenhir) {
		this.grainesDeMenhir = grainesDeMenhir;
	}

	public String toString() {
		return " [grainesDeMenhir=" + grainesDeMenhir + "]";
	}

	public abstract void ajouterUneCarte(Carte carte);

	public abstract void afficherCartes();

	public void donnerDesGrainesDeMenhir(Joueur joueur, int nombre) {
		this.setGrainesDeMenhir(this.getGrainesDeMenhir() - nombre);
		PaquetDeRessourcesDeJoueur tempPaquetJoueur = joueur.getPaquet();
		tempPaquetJoueur.setGrainesDeMenhir(tempPaquetJoueur
				.getGrainesDeMenhir() + nombre);
		this.setChanged();
		this.notifyObservers("don " + nombre + " graine(s)");
	}

	public void donnerUneGraineDeMenhir(Joueur joueur) {
		this.setGrainesDeMenhir(this.getGrainesDeMenhir() - 1);
		PaquetDeRessourcesDeJoueur tempPaquetJoueur = joueur.getPaquet();
		tempPaquetJoueur.setGrainesDeMenhir(tempPaquetJoueur
				.getGrainesDeMenhir() + 1);
		this.setChanged();
		this.notifyObservers("don 1 graine(s)");
	}
	
	public void rafraichirLesObservers() {
		this.setChanged();
		this.notifyObservers();
	}

}
