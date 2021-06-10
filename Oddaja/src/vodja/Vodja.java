package vodja;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import gui.Okno;
import inteligenca.AlphaBeta;
import inteligenca.Inteligenca;
import inteligenca.Minimax;

import java.util.Map;

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
	
	public static int hitrost = 1;
	
	public static int globina = -1;
	
	public static String inteligenca = "";

	
	// Konstruktor Vodja(Okno okno), ki nastavi zaèetne vrednosti spremenljivk
	public Vodja(Okno okno) {
		this.igra = new Igra ();
		this.poteza = null;
		this.clovekNaVrsti = false;
		this.okno = okno;
	}
	
	// Metoda igramo, ki nadzira potek igre. Iz metode odhajamo, ko je stanje enako
	// ZMAGA_B, ZMAGA_W ali NEODLOÈENO.
	public void igramo() {
		okno.osveziGUI();
		if (igra == null) return;
		switch (igra.stanje(poteza)) {
		case ZMAGA_W:
		case ZMAGA_B: 
		case NEODLOCENO:
			return;
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
	
	public static Inteligenca racunalnikovaInteligenca;
	
	// Metoda, ki odigra raèunalnikovo potezo. Metoda ne vraèa nièesar.
	public void igrajRacunalnikovoPotezo() {
		if (globina == -1) globina = 1; // Na zaèetku je globina nastavljena na 1
		if (inteligenca == "") racunalnikovaInteligenca = new AlphaBeta(globina);
		if (inteligenca == "Minimax") racunalnikovaInteligenca = new Minimax(globina);
		if (inteligenca == "AlfaBeta") racunalnikovaInteligenca = new AlphaBeta(globina);
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker =
		new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				Koordinati m = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(hitrost);} catch (Exception e) {};
				System.out.println(m);
				return m;
			}
			@Override
			protected void done () {
				Koordinati k = null;
				try {k = get();} catch (Exception e) {};
				if (igra == zacetkaIgra) {
					igra.odigraj(k);
					poteza = k;
					igramo ();
				}
			}
		};
		worker.execute();
	}
	
	// Metoda, ki odigra èlovekovo potezo. Metoda ne vraèa nièesar.
	public void igrajClovekovaPoteza(Koordinati k){
		if (igra.odigraj(k)) clovekNaVrsti = false;
		poteza = k;
		igramo();
	} 
}
