package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
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
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
	    StringBuilder chaine = new StringBuilder();
	    chaine.append(vendeur.getNom());
	    chaine.append(" cherche un endroit pour vendre ");
	    chaine.append(nbProduit);
	    chaine.append(" ");
	    chaine.append(produit);
	    chaine.append(".\n");
	    
	    int indiceEtal = marche.trouverEtalLibre();
	    if (indiceEtal != -1) {
	        marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
	        chaine.append("Le vendeur ");
	        chaine.append(vendeur.getNom());
	        chaine.append(" vend des ");
	        chaine.append(produit);
	        chaine.append(" à l'étal n°");
	        chaine.append(indiceEtal + 1);
	        chaine.append(".\n");
	    }
	    
	    return chaine.toString();
	}

	public String afficherMarche() {
	    StringBuilder chaine = new StringBuilder();
	    chaine.append("Le marché du village \"");
	    chaine.append(nom);
	    chaine.append("\" possède plusieurs étals :\n");
	    chaine.append(marche.afficherMarche()); 
	    
	    return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur){
	    return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
	    Etal etal = rechercherEtal(vendeur);
	    if (etal != null) {
	        return etal.libererEtal();
	    }
	    return vendeur.getNom() + " n'était pas installé au marché.\n";
	}
	
	public String rechercherVendeursProduit(String produit) {
	    StringBuilder chaine = new StringBuilder();
	    Etal[] etalsProduit = marche.trouverEtals(produit);
	    
	    if (etalsProduit.length == 0) {
	        chaine.append("Il n'y a pas de vendeur qui propose des ");
	        chaine.append(produit);
	        chaine.append(" au marché.\n");
	    } else if (etalsProduit.length == 1) {
	        chaine.append("Seul le vendeur ");
	        chaine.append(etalsProduit[0].getVendeur().getNom());
	        chaine.append(" propose des ");
	        chaine.append(produit);
	        chaine.append(" au marché.\n");
	    } else {
	        chaine.append("Les vendeurs qui proposent des ");
	        chaine.append(produit);
	        chaine.append(" sont :\n");
	        for (int i = 0; i < etalsProduit.length; i++) {
	            chaine.append("- ");
	            chaine.append(etalsProduit[i].getVendeur().getNom());
	            chaine.append("\n");
	        }
	    }
	    return chaine.toString();
	}
	
	private class Marche
	{
		private Etal[] etals;

		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				this.etals[i] = new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}

		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		public Etal[] trouverEtals(String produit) {
		    int nbEtalsProduit = 0;
		    for (int i = 0; i < etals.length; i++) {
		        if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
		            nbEtalsProduit++;
		        }
		    }
		    
		    Etal[] etalsTrouves = new Etal[nbEtalsProduit];
		    int index = 0;
		    for (int i = 0; i < etals.length; i++) {
		        if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
		            etalsTrouves[index] = etals[i];
		            index++;
		        }
		    }
		    return etalsTrouves;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && gaulois == etals[i].getVendeur()) {
					return etals[i];
				}
			}
			return null;
		}

		public String afficherMarche() {
		    StringBuilder chaine = new StringBuilder();
		    int nbEtalVide = 0;
		    
		    for (int i = 0; i < etals.length; i++) {
		        if (etals[i].isEtalOccupe()) {
		            chaine.append(etals[i].afficherEtal());
		        } else {
		            nbEtalVide++;
		        }
		    }
		    
		    if (nbEtalVide > 0) {
		        chaine.append("Il reste ");
		        chaine.append(nbEtalVide);
		        chaine.append(" étals non utilisés dans le marché.\n");
		    }
		    return chaine.toString();
		}
	}
}