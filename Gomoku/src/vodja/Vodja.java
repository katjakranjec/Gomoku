package vodja;

import java.util.Random;

import gui.Okno;

import java.util.Map;
import java.util.List;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

public class Vodja {
	
	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	
	public static Okno okno;
	
	public static boolean clovekNaVrsti = false;
	
	public static Igra igra = null;
	
	public static Koordinati poteza = null;
	
	
	public static void igramoNovoIgro () {
		igra = new Igra ();
		poteza = null;
		igramo();
	}
	
	public static void igramo () {
		okno.osveziGUI();
		if (igra == null) return;
		System.out.println(poteza);
		switch (igra.stanje(poteza)) {
		case ZMAGA_W: 
		case ZMAGA_B: 
		case NEODLOCENO: 
			return; // odhajamo iz metode igramo
		case V_TEKU: 
			Igralec igralec = igra.naPotezi;
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			switch (vrstaNaPotezi) {
			case C: 
				clovekNaVrsti = true;
				break;
			case R:
				igrajRacunalnikovoPotezo();
				break;
			}
		}
	}

	
	private static Random random = new Random ();
	
	public static void igrajRacunalnikovoPotezo() {
		List<Koordinati> moznePoteze = igra.moznePoteze;
		int randomIndex = random.nextInt(moznePoteze.size());
		Koordinati k = moznePoteze.get(randomIndex);
		igra.odigraj(k);
		poteza = k;
		igramo ();
	}
	
	public static void igrajClovekovaPoteza(Koordinati k){
		if (igra.odigraj(k)) clovekNaVrsti = false;
		poteza = k;
		igramo ();
	} 

}
