package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Igra {

	public static final int PET_V_VRSTO = 5;
	public static final int N = 15; // Velikost igralne plošèe je N × N
	
	private Polje[][] plosca; // Igralno polje
	public Igralec naPotezi; // Ime igralca, ki je na potezi
	public List<Koordinati> POTEZE = new LinkedList<Koordinati>(); // Vse možne poteze
	
	
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.PRAZNO;
				POTEZE.add(new Koordinati(i, j));
			}
		}
		naPotezi = Igralec.W;
	}

	//to funkcijo sem zamenjala s komponento POTEZE
//	public List<Koordinati> poteze() {
//		LinkedList<Koordinati> ps = new LinkedList<Koordinati>();
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				if (plosca[i][j] == Polje.PRAZNO) {
//					ps.add(new Koordinati(i, j));
//				}
//			}
//		}
//		return ps;
//	}

	
	private Igralec cigavaVrsta(Vrsta t) {
		int count_W = 0;
		int count_B = 0;
		for (int k = 0; k < PET_V_VRSTO && (count_W == 0 || count_B == 0); k++) {
			switch (plosca[t.x.get(k)][t.y.get(k)]) {
			case B: count_B += 1; break;
			case W: count_W += 1; break;
			case PRAZNO: break;
			}
		}
		if (count_B == PET_V_VRSTO) { return Igralec.B; }
		else if (count_W == PET_V_VRSTO) { return Igralec.W; }
		else { return null; }
	}


	public Vrsta zmagovalnaVrsta() {
		for (Vrsta t : naPotezi.nasprotnik().getVrste()) {
			Igralec lastnik = cigavaVrsta(t);
			if (lastnik != null) return t;
		}
		return null;
	}
	

	public Stanje stanje() {
		// Ali imamo zmagovalca?
		Vrsta t = zmagovalnaVrsta();
		if (t != null) {
			switch (plosca[t.x.getFirst()][t.y.getFirst()]) {
			case B: return Stanje.ZMAGA_B; 
			case W: return Stanje.ZMAGA_W;
			case PRAZNO: assert false;
			}
		}
		// Ali imamo kakšno prazno polje?
		// Èe ga imamo, igre ni konec in je nekdo na potezi
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.PRAZNO) return Stanje.V_TEKU;
			}
		}
		// Polje je polno, rezultat je neodloèen
		return Stanje.NEODLOCENO;
	}


	public boolean odigraj(Koordinati p) {
		if (plosca[p.getX()][p.getY()] == Polje.PRAZNO) {
			plosca[p.getX()][p.getY()] = naPotezi.getPolje();
			POTEZE.remove(p);
			naPotezi.getVrste(p);
			naPotezi = naPotezi.nasprotnik();
			return true;
		}
		else {
			return false;
		}
	}
}
