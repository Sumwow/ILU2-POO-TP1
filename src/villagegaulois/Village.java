package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private Marche marche;
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.marche = new Marche(nbEtals);
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {

	    StringBuilder sb = new StringBuilder();

	    sb.append(vendeur.getNom())
	      .append(" cherche un endroit pour vendre ")
	      .append(nbProduit)
	      .append(" ")
	      .append(produit)
	      .append(".\n");

	    int indice = marche.trouverEtalLibre();

	    if (indice == -1) {
	        sb.append("Il n'y a plus d'étal disponible.\n");
	    } else {
	        marche.utiliserEtal(indice, vendeur, produit, nbProduit);

	        sb.append("Le vendeur ")
	          .append(vendeur.getNom())
	          .append(" vend des ")
	          .append(produit)
	          .append(" à l'étal n°")
	          .append(indice + 1)
	          .append(".\n");
	    }

	    return sb.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {

	    StringBuilder sb = new StringBuilder();

	    Etal[] etalsProduit = marche.trouverEtals(produit);

	    if (etalsProduit.length == 0) {
	        sb.append("Il n'y a pas de vendeur qui propose des ")
	          .append(produit)
	          .append(" au marché.\n");
	    }
	    else if (etalsProduit.length == 1) {
	        sb.append("Seul le vendeur ")
	          .append(etalsProduit[0].getVendeur().getNom())
	          .append(" propose des ")
	          .append(produit)
	          .append(" au marché.\n");
	    }
	    else {
	        sb.append("Les vendeurs qui proposent des ")
	          .append(produit)
	          .append(" sont :\n");

	        for (Etal e : etalsProduit) {
	            sb.append("- ")
	              .append(e.getVendeur().getNom())
	              .append("\n");
	        }
	    }

	    return sb.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
	    return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {

	    StringBuilder sb = new StringBuilder();

	    Etal etal = marche.trouverVendeur(vendeur);

	    if (etal == null) {
	        sb.append(vendeur.getNom())
	          .append(" n'est pas installé au marché.\n");
	    } else {
	        etal.libererEtal();
	        sb.append(vendeur.getNom())
	          .append(" quitte son étal.\n");
	    }

	    return sb.toString();
	}
	
	public String afficherMarche() {
	    return marche.afficherMarche();
	}
	
	private class Marche {
		private Etal[] etals;

		private Marche(int nbrEtals) {
			etals = new Etal[nbrEtals];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
		        if (!etals[i].isEtalOccupe()) {
		            return i;
		        }
		    }
		    return -1;	
		}
		
		private Etal[] trouverEtals(String produit) {
			int compteur = 0;
		    for (int i = 0; i < etals.length; i++) {
		        if (etals[i].contientProduit(produit)) {
		            compteur++;
		        }
		    }
		    
		    Etal[] resultat = new Etal[compteur];

		    int index = 0;
		    for (int i = 0; i < etals.length; i++) {
		        if (etals[i].contientProduit(produit)) {
		            resultat[index] = etals[i];
		            index++;
		        }
		    }
		    return resultat;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
		    for (int i = 0; i < etals.length; i++) {
		        if (etals[i].getVendeur() == gaulois) {
		            return etals[i];
		        }
		    }

		    return null;
		}
		
		private String afficherMarche() {
		    String resultat = "";
		    int nbEtalVide = 0;
		    for (int i = 0; i < etals.length; i++) {
		        if (etals[i].isEtalOccupe()) {
		            resultat += etals[i].afficherEtal();
		        } else {
		            nbEtalVide++;
		        }
		    }
		    if (nbEtalVide > 0) {
		        resultat += "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
		    }
		    return resultat;
		}
	}
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}