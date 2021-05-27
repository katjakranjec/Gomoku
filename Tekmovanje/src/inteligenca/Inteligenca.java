package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

public class Inteligenca extends splosno.KdoIgra {
	
	private static final int ZMAGA = Integer.MAX_VALUE; // vrednost zmage
	private static final int PORAZ = -ZMAGA;  // vrednost izgube
	private static final int NEODLOCENO = 0;  // vrednost neodlo�ene igre	
	
	private static int globina = 5;
	public String imeSkupine;
	
	public Inteligenca () {
		super("alphabeta globina " + globina);
   		this.imeSkupine = ime;
	}

	public Koordinati izberiPotezo (Igra igra) {
		// Na za�etku alpha = PORAZ in beta = ZMAGA
		return alphabetaPoteze(igra, globina, PORAZ, ZMAGA, igra.naPotezi()).poteza;
	}
	
	public static OcenjenaPoteza alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		// �e sem ra�unalnik, maksimiramo oceno z za�etno oceno PORAZ
		// �e sem pa �lovek, minimiziramo oceno z za�etno oceno ZMAGA
		if (igra.naPotezi() == jaz) {ocena = PORAZ;} else {ocena = ZMAGA;}
		List<Koordinati> moznePoteze = igra.moznePoteze;
		Koordinati kandidat = moznePoteze.get(0); // Mo�no je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		for (Koordinati p: moznePoteze) {
			int meja = 50;
			if (globina == 1) meja = 50;
			else if (globina == 2) meja = 300;
			else if (globina == 3) meja = 200;
			else if (globina == 4) meja = 80;
			else if (globina == 5) meja = 50;
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj (p);
			int ocenap = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
			switch (kopijaIgre.stanje(p)) {
			case ZMAGA_B: ocenap = (jaz == Igralec.B ? ZMAGA : PORAZ); break;
			case ZMAGA_W: ocenap = (jaz == Igralec.W ? ZMAGA : PORAZ); break;
			case NEODLOCENO: ocenap = NEODLOCENO; break;
			default:
				// Nekdo je na potezi
				if (igra.naPotezi() == jaz) {
					if (globina == 1 || ocenap < meja || ocenap > 100000);
					else ocenap = alphabetaPoteze (kopijaIgre, globina-1, alpha, beta, jaz).ocena;
				}
				else {
					if (globina == 1 || ocenap > -meja || ocenap < -100000);
					else ocenap = alphabetaPoteze (kopijaIgre, globina-1, alpha, beta, jaz).ocena;
				}	
			}
			if (igra.naPotezi() == jaz) { // Maksimiramo oceno
				if (ocenap > ocena) { // mora biti > namesto >=
					ocena = ocenap;
					kandidat = p;
					alpha = Math.max(alpha,ocena);
				}
			} else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // mora biti < namesto <=
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena);					
				}	
			}
			if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				return new OcenjenaPoteza (kandidat, ocena);
		}
		return new OcenjenaPoteza (kandidat, ocena);
	}

}
