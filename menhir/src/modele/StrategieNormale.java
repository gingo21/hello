package modele;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * La classe Strat�gieNormale �tend la classe Strategie pour d�finir une
 * strat�gie normale pour les joueurs virtuels. Cette strat�gie se traduit par
 * un comportement intelligent au niveau des actions entreprises par les joueurs
 * virtuels.
 * 
 * @see Strategie
 */
public class StrategieNormale extends Strategie {

	/**
	 * Il s'agit d'un attribut pour la gestion de version des classes
	 * impl�mentant Serializable.
	 */
	private static final long serialVersionUID = -8260427463664015887L;

	/**
	 * Il s'agit du constructeur de la classe.
	 * 
	 * @param referenceJoueur
	 */
	public StrategieNormale(Joueur referenceJoueur) {
		super(referenceJoueur);
	}

	/**
	 * Impl�mentation de la fa�on dont va jouer un tour un joueur virtuel normal
	 * pour le choix d'une carte ingr�dient et d'une action
	 */
	public void jouerSonTour(Saison saisonActuelle, ParametresDePartie parametresDePartie) {
		// faire une selection aleatoire de carte et d'action pour les IA
		CarteIngredient carteIA = new CarteIngredient(null);
		ArrayList<Carte> paquetCartesNonUtilises = new ArrayList<Carte>();
		// cartes ingredients
		for (Iterator<Carte> it = this.getReferenceJoueur().getPaquet().getPaquetsDeCartes().get("Cartes Ingredients")
				.iterator(); it.hasNext();) {
			Carte tempCarte = it.next();
			if (!tempCarte.isEstUtilise())
				paquetCartesNonUtilises.add(tempCarte);
		}

		// s�lection action
		TypeAction typeAction = choixActionIngredient(parametresDePartie);
		carteIA = this.choixCarteIngredient(saisonActuelle, typeAction);
		if (typeAction == TypeAction.engrais) {
			carteIA.utiliser(TypeAction.engrais, this.getReferenceJoueur(), this.getReferenceJoueur(), saisonActuelle,
					parametresDePartie);
			this.getReferenceJoueur().score(parametresDePartie.getTypePartie());
			this.setChanged();
			this.notifyObservers(this.getReferenceJoueur().toString());
		}
		if (typeAction == TypeAction.farfadet) {
			Joueur destinataire = this.cibleFarfadet(parametresDePartie);
			carteIA.utiliser(TypeAction.farfadet, destinataire, this.getReferenceJoueur(), saisonActuelle,
					parametresDePartie);

			destinataire.score(parametresDePartie.getTypePartie());
			this.setChanged();
			this.notifyObservers(destinataire.toString());

			this.getReferenceJoueur().score(parametresDePartie.getTypePartie());
			this.setChanged();
			this.notifyObservers(this.getReferenceJoueur().toString());
		}
		if (typeAction == TypeAction.geantGardient) {
			carteIA.utiliser(TypeAction.geantGardient, this.getReferenceJoueur(), this.getReferenceJoueur(),
					saisonActuelle, parametresDePartie);

			this.getReferenceJoueur().score(parametresDePartie.getTypePartie());
			this.setChanged();
			this.notifyObservers(this.getReferenceJoueur().toString());

		}

	}

	/**
	 * Impl�mentation de la fa�on dont va se d�fendre un joueur virtuel normal
	 * s'il poss�de une carte de chiens de gardes
	 */
	public int seDefendre(ParametresDePartie parametresDePartie, Joueur destinataire, Joueur acteur,
			Saison saisonActuelle, int puissance) {
		int puissanceModifie = puissance;
		if (parametresDePartie.getTypePartie() == StatutPartie.avancee
				&& !(destinataire.getPaquet().getPaquetsDeCartes().get("Cartes Chiens De Garde").isEmpty())) {
			CarteChiensDeGarde tempCarte = (CarteChiensDeGarde) destinataire.getPaquet().getPaquetsDeCartes()
					.get("Cartes Chiens De Garde").get(0);
			if (!tempCarte.isEstUtilise()) {
				int tempAlea = 1;
				if (puissance == 0 || tempCarte.getPuissanceActions(saisonActuelle) == 0) {
					tempAlea = 1;
				} else {
					tempAlea = (int) ((Math.random() * 4.0) / 3.0);
				}
				if (tempAlea == 0) {
					puissanceModifie = tempCarte.utiliser(destinataire, saisonActuelle, puissanceModifie);
					this.setChanged();
					this.notifyObservers(this.getReferenceJoueur().getNom() + " se d�fend de " + destinataire.getNom()
							+ " avec ses chiens de garde et ne perd que " + puissanceModifie + " graines de menhir.");
				}
			}
		}

		return puissanceModifie;
	}

	/**
	 * Impl�mentation de la fa�on dont va attaquer un joueur virtuel normal s'il
	 * poss�de une carte de taupes g�antes
	 */
	public void attaquer(ParametresDePartie parametresDePartie, Joueur destinataire, Joueur acteur,
			Saison saisonActuelle) {
		if (parametresDePartie.getTypePartie() == StatutPartie.avancee
				&& !(acteur.getPaquet().getPaquetsDeCartes().get("Cartes Taupes Geantes").isEmpty())) {
			CarteTaupesGeantes tempCarte = (CarteTaupesGeantes) acteur.getPaquet().getPaquetsDeCartes()
					.get("Cartes Taupes Geantes").get(0);
			if (!tempCarte.isEstUtilise()) {
				int tempAlea = 1;
				if (destinataire.getPaquet().getCarteChamp().getMenhirAdultes() == 0) {
					tempAlea = 1;
				} else if (destinataire.getPaquet().getCarteChamp().getMenhirAdultes() < tempCarte
						.getPuissanceActions(saisonActuelle)) {
					tempAlea = (int) Math.random() * 8;
				} else if (destinataire.getPaquet().getCarteChamp().getMenhirAdultes() > 5) {
					tempAlea = (int) Math.random() * 2;
				} else {
					tempAlea = (int) Math.random() * 4;
				}
				if (tempAlea == 0) {
					tempCarte.utiliser(destinataire, saisonActuelle);
					this.setChanged();
					this.notifyObservers(this.getReferenceJoueur().getNom() + " attaque " + destinataire.getNom()
							+ " avec ses taupes et lui d�truit " + tempCarte.utiliser(destinataire, saisonActuelle)
							+ " menhirs adultes sur sa carte champ.");
				}
			}
		}

	}

	/**
	 * Impl�mentation de la fa�on dont un joueur virtuel normal va choisir si
	 * oui ou non il veut une carte alli�e en partie avanc�e
	 */
	public void choixDeManche(ParametresDePartie parametresDePartie) {
		int tempAlea = (int) (Math.random() * 2);
		if (tempAlea == 1) {
			this.setChoixCarteAlliee(true);
		}

	}

	/**
	 * Cette m�thode permet au joueur virtuel de choisir intelligemment une
	 * action ingr�dient lors de son tour en foncion des statistiques des autres
	 * joueurs.
	 * 
	 * @param parametresDePartie
	 *            r�cup�re les param�tres de la partie.
	 * @return l'action finalement choisie.
	 */
	public TypeAction choixActionIngredient(ParametresDePartie parametresDePartie) {
		double forceAleaEngrais = 0;
		double forceAleaGeant = 0;
		double forceAleaFarfadet = 0;
		JoueurVirtuel tempJoueur = (JoueurVirtuel) this.getReferenceJoueur();

		if (tempJoueur.getPaquet().getGrainesDeMenhir() > 2) {
			forceAleaEngrais = (Math.random() * 2);
			if (tempJoueur.getPaquet().getGrainesDeMenhir() > 5) {
				forceAleaGeant = 0;
				forceAleaFarfadet = 0;
			} else {
				forceAleaGeant = (Math.random() * 1);
				forceAleaFarfadet = (Math.random() * 0.75);
			}
		} else if (tempJoueur.getPaquet().getGrainesDeMenhir() != 0) {
			forceAleaEngrais = (Math.random() * 1);
			forceAleaGeant = (Math.random() * 1.5);
			forceAleaFarfadet = (Math.random() * 1);
		} else {
			forceAleaEngrais = 0;
			forceAleaGeant = (Math.random() * 2);
			forceAleaFarfadet = (Math.random() * 1.5);
		}

		int farfadetCapacity = 0;
		Joueur tempJoueurbis = null;
		for (Iterator<Joueur> it = parametresDePartie.getListeJoueurs().iterator(); it.hasNext();) {
			tempJoueurbis = it.next();
			if (tempJoueurbis != this.getReferenceJoueur()) {
				int tempValue = tempJoueur.getPaquet().getGrainesDeMenhir();
				if (tempValue > farfadetCapacity) {
					farfadetCapacity = tempValue;
				}
			}
		}

		if (farfadetCapacity == 0) {
			forceAleaFarfadet = 0;
		} else if (farfadetCapacity <= 2) {
			forceAleaFarfadet = (Math.random() * 0.75);
		} else if (farfadetCapacity > 5) {
			forceAleaFarfadet = (Math.random() * 2);
		}

		TypeAction tempTypeAction = TypeAction.geantGardient;
		if (forceAleaGeant > forceAleaEngrais && forceAleaGeant > forceAleaFarfadet) {
			tempTypeAction = TypeAction.geantGardient;
		} else if (forceAleaEngrais > forceAleaGeant && forceAleaEngrais > forceAleaFarfadet) {
			tempTypeAction = TypeAction.engrais;
		} else {
			tempTypeAction = TypeAction.farfadet;
		}

		return tempTypeAction;
	}

	/**
	 * Cette m�thode permet au joueur virtuel de choisir intelligemment une
	 * carte ingr�dient lors de son tour en fonction de l'action qu'il aura
	 * choisie tout simplement en regardant quelle est la carte avec la
	 * meilleure puissance.
	 * 
	 * @param saisonActuelle
	 *            r�cup�re la saison actuelle de la partie en cours.
	 * @param typeAction
	 *            r�cup�re l'action s�lectionn�e pr�cedemment.
	 * @return la carte finalement choisie.
	 */
	public CarteIngredient choixCarteIngredient(Saison saisonActuelle, TypeAction typeAction) {

		int tempValue = 0;
		int maxValue = 0;
		CarteIngredient tempCarte = null;
		CarteIngredient carteMax = null;
		for (Iterator<Carte> it = this.getReferenceJoueur().getPaquet().getPaquetsDeCartes().get("Cartes Ingredients")
				.iterator(); it.hasNext();) {
			tempCarte = (CarteIngredient) it.next();
			tempValue = tempCarte.getPuissanceActions(saisonActuelle, typeAction);
			if (tempValue > maxValue) {
				carteMax = tempCarte;
				maxValue = tempValue;
			}
		}

		return carteMax;
	}

	/**
	 * Cette m�thode permet au joueur virtuel de choisir intelligemment un
	 * joueur � attaquer avec ses farfadets en foncion des statistiques des
	 * autres joueurs.
	 * 
	 * @param parametresDePartie
	 *            r�cup�re les param�tres de la partie.
	 * @return le joueur cible finalement choisi.
	 */
	public Joueur cibleFarfadet(ParametresDePartie parametresDePartie) {
		int farfadetCapacity = 0;
		Joueur tempJoueur = null;
		Joueur joueurCible = null;
		for (Iterator<Joueur> it = parametresDePartie.getListeJoueurs().iterator(); it.hasNext();) {
			tempJoueur = it.next();
			if (tempJoueur != this.getReferenceJoueur()) {
				int tempValue = tempJoueur.getPaquet().getGrainesDeMenhir();
				if (tempValue > farfadetCapacity) {
					farfadetCapacity = tempValue;
					joueurCible = tempJoueur;
				}
			}
		}
		return joueurCible;
	}

}
