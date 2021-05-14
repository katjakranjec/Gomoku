package inteligenca;


import java.util.List;
import java.util.Random;

import logika.Igra;

import splosno.Koordinati;

public class RandomMinimax extends Inteligenca {
	
	private static final Random RANDOM = new Random();
	
	private static final int ZMAGA = Integer.MAX_VALUE; // vrednost zmage, več kot vsaka druga ocena pozicije
	private static final int NEODLOC = 0;  // vrednost neodločene igre	

	private int globina;
	
	public RandomMinimax (int globina) {
		super("naklju�ni minimax globina " + globina);
		this.globina = globina;
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		List<OcenjenaPoteza> ocenjenePoteze = najboljsePoteze(igra, globina);
		System.out.println(ocenjenePoteze.size() + " potez z vrednostjo " + ocenjenePoteze.get(0).ocena);
		int i = RANDOM.nextInt(ocenjenePoteze.size());	
		return ocenjenePoteze.get(i).poteza;		
	}
	
	// vrne seznam vseh potez, ki imajo največjo vrednost z vidike trenutnega igralca na potezi
	public static List<OcenjenaPoteza> najboljsePoteze(Igra igra, int globina) {
		NajboljseOcenjenePoteze najboljsePoteze = new NajboljseOcenjenePoteze();
		List<Koordinati> moznePoteze = igra.moznePoteze;
		for (Koordinati p: moznePoteze) {
			Igra tempIgra = new Igra(igra); 
			tempIgra.odigraj (p);	//poskusimo vsako potezo v novi kopiji igre
			int ocena;
			switch (tempIgra.stanje(p)) {
			case ZMAGA_W:
			case ZMAGA_B: ocena = ZMAGA; break; // p je zmagovalna poteza
			case NEODLOCENO: ocena = NEODLOC; break;
			default: //nekdo je na potezi
				if (globina==1) ocena = OceniPozicijo.oceniPozicijo(tempIgra,igra.naPotezi());
				else ocena = -najboljsePoteze(tempIgra,globina-1).get(0).ocena; // - ker je drug igralec 
			}
			najboljsePoteze.addIfBest(new OcenjenaPoteza(p, ocena));			
		}
		return najboljsePoteze.list();
	}

	
}
