package inteligenca;

import java.util.HashSet;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;
import splosno.Koordinati;

public class OceniPozicijo {
	
	
	// Metoda oceni pozicijo sprejme trenutno igro igralca.
	// Metoda najprej ustvari prazen HashSet<Vrsta> v katerega nalaga vrste, ki jih pridobi,
	// ko uporabi metodo pridobiVrste na vseh odigranih potezah.
	// Metoda se nato z zanko zapelje po vseh vrstah iz tega HashSeta<Vrsta> in na njih uporabi
	// metodo oceniVrsto.
	// Metoda vrne vsoto vseh ocenjenih vrst.
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
	
	// Metoda sprejme vrsto, igro in igralca. Najprej ustvari spremenljivke count_W, count_B,
	// zaporedni_W in zaporedni_B. Za vsako polje v vrsti doda 1 countu in zaporednemu B oziroma W, èe ta vsebuje
	// žeton prave barve. Æe je polje prazno pa zaporednima obema odšteje 1.
	public static int oceniVrsto (Vrsta v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int count_W = 0;
		int count_B = 0;
		int zaporedni_W = 0;
		int zaporedni_B = 0;
		for (int k = 0; k < Igra.PET_V_VRSTO && (count_W == 0 || count_B == 0); k++) {
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
		
		// Spremenimo counta glede na pomembnost posavitve vrste za zmago:
		// Èe ima nasprotnik 4 v vrsto, njegov count poveèamo na 1000
		// Èe imamo mi 4 v vrsto naš count poveèamo na 25.
		// Èe ima nasprotnik 3 v vrsto brez dvojne luknje, njegov count poveèamo na 25.
		if (count_W == 4 && jaz == Igralec.B) count_W = 1000;
		if (count_B == 4 && jaz == Igralec.W) count_B = 1000;
		if (count_W == 4 && jaz == Igralec.W) count_W = 25;
		if (count_B == 4 && jaz == Igralec.B) count_B = 25;
		if (count_W == 3 && jaz == Igralec.B && (zaporedni_W == 3 || zaporedni_W == 2)) count_W = 25;
		if (count_B == 3 && jaz == Igralec.W && (zaporedni_B == 3 || zaporedni_B == 2)) count_B = 25;
		
		
		// Èe imata oba igralca v vrsti žetone, vrnemo oceno 0.
		// Drugaèe vrnemo naš count.
		if (count_B > 0 && count_W > 0) { return 0; }
		else if (jaz == Igralec.B) { return count_B - count_W; }
		else { return count_W - count_B; }
	}
	

}