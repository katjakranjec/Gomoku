package logika;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Igra {

	public static final int PET_V_VRSTO = 5;
	public int n = 15; // Velikost igralne plošèe je N × N
	
	private Polje[][] plosca; // Igralno polje
	public Igralec naPotezi; // Ime igralca, ki je na potezi
	public List<Koordinati> POTEZE = new LinkedList<Koordinati>(); // Vse možne poteze
	
	
	public Igra() {
		plosca = new Polje[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				plosca[i][j] = Polje.PRAZNO;
				POTEZE.add(new Koordinati(i, j));
			}
		}
		naPotezi = Igralec.W;
	}

	private void vrsteVSmeri(int a1, int a2, int k1, int k2, int x, int y, HashSet<Vrsta> vrste) {
		for (int a = 0; a < Igra.PET_V_VRSTO; ++a) {
			int[] vrsta_x = new int[PET_V_VRSTO];
			int[] vrsta_y = new int[PET_V_VRSTO];
			Arrays.fill(vrsta_x, n);
			Arrays.fill(vrsta_y, n);
			int mejaX = x + a1 * a;
			int mejaY = y + a2 * a;
			for (int k = 0; k < Igra.PET_V_VRSTO; ++k) {
				if (mejaX + k1 * k >= 0 && mejaX + k1 * k < n && mejaY + k2 * k >= 0 && mejaY + k2 * k < n) {
					vrsta_x[k] = mejaX + k1 * k;
					vrsta_y[y] = mejaY + k2 * k;
				}
			}
			if (IntStream.of(vrsta_x).anyMatch(t -> t == n) || IntStream.of(vrsta_y).anyMatch(t -> t == n));
			else vrste.add(new Vrsta(vrsta_x, vrsta_y));
		}
	}
	
	private HashSet<Vrsta> pridobiVrste(Koordinati q) {
		int x = q.getX();
		int y = q.getY();
		
		HashSet<Vrsta> vrste = new HashSet<Vrsta>();
		
		//SMER S->J
		vrsteVSmeri(0, -1, 0, 1, x, y, vrste);
		
		//SMER SZ -> JV
		vrsteVSmeri(-1, -1, 1, 1, x, y, vrste);
		
		//SMER Z -> V
		vrsteVSmeri(-1, 0, 1, 0, x, y, vrste);
		
		//SMER JZ -> SV
		vrsteVSmeri(-1, 1, 1, -1, x, y, vrste);

		return vrste;
	}

	
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
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
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
