package vodja;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import gui.Okno;
import inteligenca.AlphaBeta;
import inteligenca.Inteligenca;
import inteligenca.Minimax;
import inteligenca.OceniPozicijo;

import java.util.Map;
import java.util.EnumMap;
import java.util.List;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

public class Vodja {
	
	public Map<Igralec,VrstaIgralca> vrstaIgralca;
	
	public Okno okno;
	
	public boolean clovekNaVrsti;
	
	public Igra igra;
	
	public Koordinati poteza;
	
	public int velikost;
	
	public static int hitrost = 0;

	public Vodja(Okno okno) {
		this.igra = new Igra ();
		this.poteza = null;
		this.clovekNaVrsti = false;
		this.okno = okno;
		//igramo();
	}
	
	public void igramo() {
		okno.osveziGUI();
		if (igra == null) return;
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
	
//	public void igrajRacunalnikovoPotezo() {
//		List<Koordinati> moznePoteze = igra.moznePoteze;
//		int randomIndex = random.nextInt(moznePoteze.size());
//		Koordinati k = moznePoteze.get(randomIndex);
//		igra.odigraj(k);
//		poteza = k;
//		igramo();
//	}
	
	
	//public static Inteligenca racunalnikovaInteligenca = new Minimax(1);
	public static Inteligenca racunalnikovaInteligenca = new AlphaBeta(2);
	
	public void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker =
		new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				Koordinati m = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(hitrost);} catch (Exception e) {};
//				List<Koordinati> moznePoteze = igra.moznePoteze;
//				int randomIndex = random.nextInt(moznePoteze.size());
//				return moznePoteze.get(randomIndex);
				System.out.println(m);
				return m;
			}
			@Override
			protected void done () {
				Koordinati k = null;
				try {k = get();} catch (Exception e) {};
				if (igra == zacetkaIgra) {
					igra.odigraj(k);
					//OceniPozicijo.oceniPozicijo(zacetkaIgra, Igralec.W);
					poteza = k;
					igramo ();
				}
			}
		};
		worker.execute();
	}
	
	public void igrajClovekovaPoteza(Koordinati k){
		if (igra.odigraj(k)) clovekNaVrsti = false;
		poteza = k;
		igramo();
	} 
}
