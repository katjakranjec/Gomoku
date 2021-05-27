package logika;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import splosno.Koordinati;

public class Igra {

	public static final int PET_V_VRSTO = 5;
	public static int n = 0; // Velikost igralne plo��e je N � N
	
	private Polje[][] plosca; // Igralno polje
	public Igralec naPotezi; // Ime igralca, ki je na potezi
	public List<Koordinati> moznePoteze; // Vse mo�ne poteze
	public List<Koordinati> odigraneW;
	public List<Koordinati> odigraneB;
	
	
	public Igra() {
		if (n == 0) {
			n = 15;
		}
		this.odigraneW = new LinkedList<Koordinati>();
		this.odigraneB = new LinkedList<Koordinati>();
		this.moznePoteze = new LinkedList<Koordinati>();
		plosca = new Polje[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				plosca[i][j] = Polje.PRAZNO;
				moznePoteze.add(new Koordinati(i, j));
			}
		}
		naPotezi = Igralec.B;
	}
	
	public Igra(Igra igra) {
		this.plosca = new Polje[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				this.plosca[i][j] = igra.plosca[i][j];
			}
		}
		this.naPotezi = igra.naPotezi;
		this.odigraneW = new LinkedList<Koordinati>();
		this.odigraneB = new LinkedList<Koordinati>();
		this.moznePoteze = new LinkedList<Koordinati>();
		for (Koordinati u : igra.odigraneW) this.odigraneW.add(u);
		for (Koordinati v : igra.odigraneB) this.odigraneB.add(v);
		for (Koordinati w : igra.moznePoteze) this.moznePoteze.add(w);
	}
	
	public Polje[][] getPlosca () {
		return plosca;
	}
	
	public Igralec naPotezi() {
		return naPotezi;
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
					vrsta_y[k] = mejaY + k2 * k;
				}
			}
			if (IntStream.of(vrsta_x).anyMatch(t -> t == n) || IntStream.of(vrsta_y).anyMatch(t -> t == n));
			else vrste.add(new Vrsta(vrsta_x, vrsta_y));
		}
	}
	
	public HashSet<Vrsta> pridobiVrste(Koordinati q) {
		int x = q.getX();
		int y = q.getY();
		
		HashSet<Vrsta> vrste = new HashSet<Vrsta>();
		
		//SMER S <-> J
		vrsteVSmeri(0, -1, 0, 1, x, y, vrste);
		
		//SMER SZ <-> JV
		vrsteVSmeri(-1, -1, 1, 1, x, y, vrste);
		
		//SMER Z <-> V
		vrsteVSmeri(-1, 0, 1, 0, x, y, vrste);
		
		//SMER JZ <-> SV
		vrsteVSmeri(-1, 1, 1, -1, x, y, vrste);

		return vrste;
	}

	
	public Igralec cigavaVrsta(Vrsta t) {
		int count_W = 0;
		int count_B = 0;
		for (int k = 0; k < PET_V_VRSTO && (count_W == 0 || count_B == 0); k++) {
			switch (plosca[t.x[k]][t.y[k]]) {
			case B: count_B += 1; break;
			case W: count_W += 1; break;
			case PRAZNO: break;
			}
		}
		if (count_B == PET_V_VRSTO) { return Igralec.B; }
		else if (count_W == PET_V_VRSTO) { return Igralec.W; }
		else return null;
	}


	public Vrsta zmagovalnaVrsta(Koordinati poteza) {
		HashSet<Vrsta> vrste = pridobiVrste(poteza);
		for (Vrsta t : vrste) {
			Igralec lastnik = cigavaVrsta(t);
			if (lastnik != null) return t;
		}
		return null;
	}
	

	public Stanje stanje(Koordinati poteza) {
		if (poteza == null) return Stanje.V_TEKU;
		else {
			// Ali imamo zmagovalca?
			Vrsta t = zmagovalnaVrsta(poteza);
			if (t != null) {
				switch (plosca[t.x[0]][t.y[0]]) {
				case B: return Stanje.ZMAGA_B; 
				case W: return Stanje.ZMAGA_W;
				case PRAZNO: assert false;
				}
			}
			// Ali imamo kak�no prazno polje?
			// �e ga imamo, igre ni konec in je nekdo na potezi
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (plosca[i][j] == Polje.PRAZNO) return Stanje.V_TEKU;
				}
			}
			// Polje je polno, rezultat je neodlo�en
			return Stanje.NEODLOCENO;
		}
	}


	public boolean odigraj(Koordinati p) {
		if (moznePoteze.contains(p)) {
			plosca[p.getX()][p.getY()] = naPotezi.getPolje();
			moznePoteze.remove(p);
			if (naPotezi == Igralec.W) odigraneW.add(p);
			if (naPotezi == Igralec.B) odigraneB.add(p);
			naPotezi = naPotezi.nasprotnik();
			return true;
		}
		else {
			return false;
		}
	}

	public Koordinati razveljavi() {
		Koordinati poteza = null;
		if (naPotezi.nasprotnik() == Igralec.W && odigraneW.size() != 0) {
			poteza = odigraneW.remove(odigraneW.size() - 1);
			moznePoteze.add(poteza);
			plosca[poteza.getX()][poteza.getY()] = Polje.PRAZNO;
		} else if (odigraneB.size() != 0){
			poteza = odigraneB.remove(odigraneB.size() - 1);
			moznePoteze.add(poteza);
			plosca[poteza.getX()][poteza.getY()] = Polje.PRAZNO;
		}
		naPotezi = naPotezi.nasprotnik();
		return poteza;
	}
}