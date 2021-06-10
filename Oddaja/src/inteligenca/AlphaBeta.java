package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

public class AlphaBeta extends Inteligenca {
	
	private static final int ZMAGA = Integer.MAX_VALUE; // vrednost zmage
	private static final int PORAZ = -ZMAGA;  // vrednost poraza
	private static final int NEODLOCENO = 0;  // vrednost neodloèene igre	
	
	private int globina;
	
	public AlphaBeta (int globina) {
		super("alphabeta globina " + globina);
		this.globina = globina;
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		// Na zaèetku alpha = PORAZ in beta = ZMAGA
		return alphabetaPoteze(igra, this.globina, PORAZ, ZMAGA, igra.naPotezi()).poteza;
	}
	
	public static OcenjenaPoteza alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		// Èe sem raèunalnik, maksimiramo oceno z zaèetno oceno PORAZ
		// Èe sem pa èlovek, minimiziramo oceno z zaèetno oceno ZMAGA
		if (igra.naPotezi() == jaz) {ocena = PORAZ;} else {ocena = ZMAGA;}
		List<Koordinati> moznePoteze = igra.moznePoteze;
		Koordinati kandidat = moznePoteze.get(0); // Možno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		
		//Nastavimo meje, za katere gremo gledati možnosti bolj v globino.
		int meja = 50;
		if (moznePoteze.size() > ((igra.n * igra.n) * 4 / 5) || moznePoteze.size() < ((igra.n * igra.n) * 1 / 5)) {
			//Prva petina in zadnja petina potez
			if (globina == 1) meja = 50;
			else if (globina == 2) meja = 150;
			else if (globina == 3) meja = 100;
			else if (globina == 4) meja = 50;
			else if (globina == 5) meja = 20;
		}
		else {
			//Vmesne poteze
			if (globina == 1) meja = 50;
			else if (globina == 2) meja = 150;
			else if (globina == 3) meja = 70;
			else if (globina == 4) meja = 50;
			else if (globina == 5) meja = 50;
		}
		
		for (Koordinati p: moznePoteze) {
			
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