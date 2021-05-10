package vodja;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import gui.Okno;

import java.util.Map;
import java.util.EnumMap;
import java.util.List;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

public class Vodja {
	
	public Map<Igralec,VrstaIgralca> vrstaIgralca;
	
	//public Okno okno;
	
	public boolean clovekNaVrsti;
	
	public Igra igra;
	
	public Koordinati poteza;
	
	public int velikost;

	public Vodja() {
		this.igra = new Igra ();
		this.poteza = null;
		this.clovekNaVrsti = false;
		vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
		vrstaIgralca.put(Igralec.W, VrstaIgralca.R); 
		vrstaIgralca.put(Igralec.B, VrstaIgralca.C);
		//this.okno = new Okno();
		//igramo();
	}
	
	public void igramo() {
		//okno.osveziGUI();
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
	
	//public void igrajRacunalnikovoPotezo() {
		//List<Koordinati> moznePoteze = igra.moznePoteze;
		//int randomIndex = random.nextInt(moznePoteze.size());
		//Koordinati k = moznePoteze.get(randomIndex);
		//igra.odigraj(k);
		//poteza = k;
		//igramo();
	//}
	
	public void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker =
		new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
			try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {};
			List<Koordinati> moznePoteze = igra.moznePoteze;
			int randomIndex = random.nextInt(moznePoteze.size());
			return moznePoteze.get(randomIndex);
			}
			@Override
			protected void done () {
			Koordinati poteza = null;
			try {poteza = get();} catch (Exception e) {};
			if (igra == zacetkaIgra) {
			igra.odigraj(poteza);
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
