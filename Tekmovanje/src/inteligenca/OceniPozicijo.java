package inteligenca;

import java.util.HashSet;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;
import splosno.Koordinati;

public class OceniPozicijo {
	
	HashSet<Vrsta> vrste;
	
	// Metoda oceniPozicijo za igro TicTacToe
	
	public static int oceniPozicijo(Igra igra, Igralec jaz, Koordinati poteza) {
		if (vrste == null) {
			int ocena = 0;
			HashSet<Vrsta> vrste = new HashSet<Vrsta>();
			for (Koordinati p : igra.odigraneB) {
				HashSet<Vrsta> vrsteB = igra.pridobiVrste(p);
				for (Vrsta m : vrsteB) {
					vrste.add(m);
				}
			}
			for (Koordinati p : igra.odigraneW) {
				HashSet<Vrsta> vrsteB = igra.pridobiVrste(p);
				for (Vrsta m : vrsteB) {
					vrste.add(m);
				}
			}		
		}
		else {
			vrsteP = igra.pridobiVrste(poteza);
			for (Vrsta v : vrsteP) {
				vrste.add(v);
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
		int zaporedni_W = 0;
		int zaporedni_B = 0;
		for (int k = 0; k < Igra.PET_V_VRSTO && (count_W == 0 || count_B == 0); k++) {
			//System.out.println("V oceni vrsto se je zgubu");
			switch (plosca[v.x[k]][v.y[k]]) {
			case B: 
				count_B += 1; 
				zaporedni_B += 1;
				break;
			case W: 
				count_W += 1; 
				zaporedni_W += 1;
				break;
			case PRAZNO: 
				if (count_W > 0 && k != 4) zaporedni_W -= 1;
				if (count_B > 0 && k!= 4) zaporedni_B -= 1;
				break;
			}
		}
		
		if (count_W == 4 && jaz == Igralec.B) count_W = 1000;
		if (count_B == 4 && jaz == Igralec.W) count_B = 1000;
		if (count_W == 4 && jaz == Igralec.W) count_W = 25;
		if (count_B == 4 && jaz == Igralec.B) count_B = 25;
		if (count_W == 3 && jaz == Igralec.B && (zaporedni_W == 3 || zaporedni_W == 2)) count_W = 50;
		if (count_B == 3 && jaz == Igralec.W && (zaporedni_B == 3 || zaporedni_B == 2)) count_B = 50;
		
		
		if (count_B > 0 && count_W > 0) { return 0; }
		else if (jaz == Igralec.B) { return count_B - count_W; }
		else { return count_W - count_B; }
	}
	

}
