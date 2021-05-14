package inteligenca;

import java.util.HashSet;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;
import splosno.Koordinati;

public class OceniPozicijo {
	
	// Metoda oceniPozicijo za igro TicTacToe
	
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int ocena = 0;
		HashSet<Vrsta> vrste = new HashSet<Vrsta>();
		for (Koordinati p : igra.odigraneB) {
			HashSet<Vrsta> vrsteB = igra.pridobiVrste(p);
			for (Vrsta m : vrsteB) {
				//System.out.println(m);
				vrste.add(m);
			}
		}
		for (Koordinati p : igra.odigraneW) {
			HashSet<Vrsta> vrsteB = igra.pridobiVrste(p);
			for (Vrsta m : vrsteB) {
				//System.out.println(m);
				vrste.add(m);
			}
		}
		for (Vrsta v : vrste) {
			ocena = ocena + oceniVrsto(v, igra, jaz);
		}
		return ocena;	
	}
	
	public static int oceniVrsto (Vrsta v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int count_W = 0;
		int count_B = 0;
		for (int k = 0; k < Igra.PET_V_VRSTO && (count_W == 0 || count_B == 0); k++) {
			//System.out.println("V oceni vrsto se je zgubu");
			switch (plosca[v.x[k]][v.y[k]]) {
			case B: count_B += 1; break;
			case W: count_W += 1; break;
			case PRAZNO: break;
			}
		}
		if (count_B > 0 && count_W > 0) { return 0; }
		else if (jaz == Igralec.B) { return count_B - count_W; }
		else { return count_W - count_B; }
	}
	

}