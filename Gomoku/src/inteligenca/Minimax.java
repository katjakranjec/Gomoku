package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

import inteligenca.OceniPozicijo;

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
		//System.out.println("Sm pride1");
		OcenjenaPoteza najboljsaPoteza = minimax(igra, this.globina, igra.naPotezi);
		//System.out.println("Sm pride");
		return najboljsaPoteza.poteza;	
	}
	
	// vrne najboljso ocenjeno potezo z vidike igralca jaz
	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPoteza najboljsaPoteza = null;
		//int stevilka = 0;
		List<Koordinati> moznePoteze = igra.moznePoteze;
		for (Koordinati p: moznePoteze) {
			//System.out.println(p);
			//stevilka += 1;
			//System.out.println(stevilka);
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj (p);
			//igra.odigraj(p);
			int ocena;
			switch (kopijaIgre.stanje(p)) {
			case ZMAGA_B: ocena = (jaz == Igralec.B ? ZMAGA : PORAZ); break;
			case ZMAGA_W: ocena = (jaz == Igralec.W ? ZMAGA : PORAZ); break;
			case NEODLOCENO: ocena = NEODLOCENO; break;
			default:
				// nekdo je na potezi
				if (globina == 1) {
					//System.out.println("A do sm sploh pride?");
					ocena = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
					//System.out.println("A do sm sploh pride?");
				}
				// globina > 1
				else ocena = minimax(kopijaIgre, globina-1, jaz).ocena;	
			}
			if (najboljsaPoteza == null 
					// max, Äe je p moja poteza
					|| jaz == igra.naPotezi() && ocena > najboljsaPoteza.ocena
					// sicer min 
					|| jaz != igra.naPotezi() && ocena < najboljsaPoteza.ocena)
				najboljsaPoteza = new OcenjenaPoteza (p, ocena);		
		}
		return najboljsaPoteza;
	}
}