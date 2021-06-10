package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

public class Minimax extends Inteligenca {
	
	private static final int ZMAGA = Integer.MAX_VALUE; // vrednost zmage
	private static final int PORAZ = -ZMAGA;  // vrednost izgube
	private static final int NEODLOCENO = 0;  // vrednost neodloèene igre	
	
	private int globina;
	
	public Minimax (int globina) {
		super("minimax globina " + globina);
		this.globina = globina;
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		OcenjenaPoteza najboljsaPoteza = minimax(igra, this.globina, igra.naPotezi);
		return najboljsaPoteza.poteza;	
	}
	
	// Metoda vrne najboljše ocenjeno potezo z vidike igralca jaz
	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPoteza najboljsaPoteza = null;
		List<Koordinati> moznePoteze = igra.moznePoteze;
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj (p);
			int ocena;
			switch (kopijaIgre.stanje(p)) {
			case ZMAGA_B: ocena = (jaz == Igralec.B ? ZMAGA : PORAZ); break;
			case ZMAGA_W: ocena = (jaz == Igralec.W ? ZMAGA : PORAZ); break;
			case NEODLOCENO: ocena = NEODLOCENO; break;
			default:
				// nekdo je na potezi
				if (globina == 1) {
					ocena = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				}
				// globina > 1
				else ocena = minimax(kopijaIgre, globina-1, jaz).ocena;	
			}
			if (najboljsaPoteza == null 
					// max, èe je p moja poteza
					|| jaz == igra.naPotezi() && ocena > najboljsaPoteza.ocena
					// sicer min 
					|| jaz != igra.naPotezi() && ocena < najboljsaPoteza.ocena)
				najboljsaPoteza = new OcenjenaPoteza (p, ocena);		
		}
		return najboljsaPoteza;
	}
}