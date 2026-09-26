package histoire;
import personnages.Gaulois;
import villagegaulois.Etal;


public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		etal.libererEtal();
		try {
		    Gaulois asterix = new Gaulois("Astérix", 8);
		    etal.acheterProduit(1, asterix);
		} catch (IllegalArgumentException e) {
		    e.printStackTrace();
		} catch (IllegalStateException e) {
		    e.printStackTrace();
		}
		System.out.println("Fin du test");
		}

}
