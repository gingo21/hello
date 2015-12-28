package modèle;

public class JoueurVirtuel extends Joueur {

	private Difficulte difficulte;

	public JoueurVirtuel(String nom,
			PaquetDeRessourcesDePartie referencePaquetPartie, Difficulte difficulte) {
		super(nom, referencePaquetPartie, null);
		if(difficulte == Difficulte.facile) {
			this.setStrategie(new StrategieFacile(this));
		} else if(difficulte == Difficulte.normale) {
			this.setStrategie(new StrategieNormale(this));
		} else {
			this.setStrategie(new StrategieDifficile(this));
		}
		this.difficulte = difficulte;
	}

	public Difficulte getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(Difficulte difficulte) {
		this.difficulte = difficulte;
	}
}
