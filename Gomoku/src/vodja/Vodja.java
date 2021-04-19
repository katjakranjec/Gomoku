package vodja;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Random;

import gui.Okno;

import java.util.Map;
import java.util.EnumMap;
import java.util.List;

import logika.Igra;
import logika.Igralec;
import logika.Koordinati;
import logika.Stanje;

public class Vodja {
	
	public static enum VrstaIgralca { R, C; }

	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	
	public static Okno okno;
	
	public static boolean clovekNaVrsti = false;
	
	public static Igra igra = null;
	
	public static Koordinati poteza = null;
	
	
	public static void igramoNovoIgro () {
		igra = new Igra ();
		igramo();
	}
	
	public static void igramo () {
		okno.osveziGUI();
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
				igrajRacunalnikovoPotezo(igra);
				break;
			}
		}
	}
//	public static void igramo () throws IOException {
//		while (true) {
//			System.out.println("Nova igra. Prosim, da izberete:");
//			System.out.println(" 1 - B èlovek, W raèunalnik");
//			System.out.println(" 2 - B raèunalnik, W èlovek");
//			System.out.println(" 3 - B èlovek, W èlovek");
//			System.out.println(" 4 - izhod");
//			String s = r.readLine();
//			if (s.equals("1")) {
//				vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
//				vrstaIgralca.put(Igralec.B, VrstaIgralca.C); 
//				vrstaIgralca.put(Igralec.W, VrstaIgralca.R); 			
//			} else if (s.equals("2")) {
//				vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
//				vrstaIgralca.put(Igralec.B, VrstaIgralca.R); 
//				vrstaIgralca.put(Igralec.W, VrstaIgralca.C); 			
//			} else if (s.equals("3")) {
//				vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
//				vrstaIgralca.put(Igralec.B, VrstaIgralca.C); 
//				vrstaIgralca.put(Igralec.W, VrstaIgralca.C); 			
//			} else if (s.equals("4")) {
//				System.out.println("Nasvidenje!");
//				break;
//			} else {
//				System.out.println("Vnos ni veljaven");
//				continue;
//			}
			// Èe je s == "1", "2" ali "3"
			//Igra igra = new Igra ();
//			igranje : while (true) {
//				Igralec igralec = igra.naPotezi;
//				VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
//				Koordinati poteza = null;
//				switch (vrstaNaPotezi) {
//				case C: 
//					poteza = clovekovaPoteza(igra);
//					break;
//				case R:
//					poteza = racunalnikovaPoteza(igra);
//					break;
//				}
//				System.out.println("Igralec " + igralec + " je igral " + poteza);
//				switch (igra.stanje(poteza)) {
//				case ZMAGA_B: 
//					System.out.println("Zmagal je igralec B");
//					System.out.println("Zmagovalna vrsta " + igra.zmagovalnaVrsta(poteza).toString());
//					break igranje;
//				case ZMAGA_W: 
//					System.out.println("Zmagal je igralec W");
//					System.out.println("Zmagovalna vrsta " + igra.zmagovalnaVrsta(poteza).toString());
//					break igranje;
//				case NEODLOCENO: 
//					System.out.println("Igra je neodloèena");
//					break igranje;
//				case V_TEKU: continue igranje;
//				}
//			}
//		}
//	}
	
	private static Random random = new Random ();
	
	public static Koordinati igrajRacunalnikovoPotezo(Igra igra) {
		List<Koordinati> moznePoteze = igra.mozne_poteze;
		int randomIndex = random.nextInt(moznePoteze.size());
		Koordinati poteza = moznePoteze.get(randomIndex);
		igra.odigraj(poteza);
		return poteza;		
	}
	
	public static void igrajClovekovaPoteza(Koordinati poteza){
		if (igra.odigraj(poteza)) clovekNaVrsti = false;
		igramo ();
	} 
//		while (true) {
//			System.out.println("Igralec " + igra.naPotezi.toString() +
//					" vnesite potezo \"x y\"");
//			String s = r.readLine();
//			int i = s.indexOf (' '); // kje je presledek
//			if (i == -1 || i  == s.length()) { 
//				System.out.println("Napaèen format"); continue; 
//			}
//			String xString = s.substring(0,i);
//			String yString = s.substring(i+1);
//			int x, y;
//			try {
//				x = Integer.parseInt(xString);
//				y = Integer.parseInt(yString);		
//			} catch (NumberFormatException e) {
//				System.out.println("Napaèen format"); continue; 
//			}
//			if (x < 0 || x >= igra.n || y < 0 || y >= igra.n){
//				System.out.println("Napaèen format"); continue; 			
//			}
//			Koordinati poteza = new Koordinati(x,y);
//			if (igra.odigraj(poteza)) return poteza;
//			System.out.println(poteza.toString() + " ni možna");
//		}
//	}

}
