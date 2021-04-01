package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Igra {

	public static final int PET_V_VRSTO = 5;
	public static final int N = 15; // Velikost igralne plošèe je N × N
	
	private Polje[][] plosca; // Igralno polje
	public Igralec naPotezi; // Ime igralca, ki je na potezi
	private static final HashSet<Vrsta> VRSTE = new HashSet<Vrsta>();
	
	public void preverjanje(int x, int y) {
		
		int trenutniX = x;
		int trenutniY = y;		
		int koncniX = x;
		int koncniY = y;
		
		//SMER S->J
		if (y < PET_V_VRSTO - 1) trenutniY = 0;
		else trenutniY = y - PET_V_VRSTO + 1;
		if (y > (N - PET_V_VRSTO)) koncniY = N - PET_V_VRSTO;
		
		for (int a = trenutniY; a < koncniY + 1; a++) {
			LinkedList<Integer> vrsta_x = new LinkedList<Integer>();
			LinkedList<Integer> vrsta_y = new LinkedList<Integer>();
			for (int k = 0; k < PET_V_VRSTO; k++) {
				vrsta_x.add(k, trenutniX);
				vrsta_y.add(k, trenutniY + k);
			}
			VRSTE.add(new Vrsta(vrsta_x, vrsta_y));
		}
		
		trenutniX = x;
		trenutniY = y;		
		koncniX = x;
		koncniY = y;
		
		//SMER SZ->JV
		if (x < y) {
			if (x < PET_V_VRSTO - 1) {
				trenutniX = 0;
				trenutniY = y - (koncniX - trenutniX);
			}
			else if (y > (N - PET_V_VRSTO)) {
				koncniY = N - PET_V_VRSTO;
				koncniX = x - (koncniY - trenutniY);
			}
			else {
				trenutniX = x - PET_V_VRSTO + 1;
				trenutniY = y - PET_V_VRSTO + 1;
				koncniX = x;
				koncniY = y;
			}
		}
		else {
			if (y < PET_V_VRSTO - 1) {
				trenutniY = 0;
				trenutniX = x - (koncniY - trenutniY);
			}
			else if (x > (N - PET_V_VRSTO)) {
				koncniX = N - PET_V_VRSTO;
				koncniY = y - (koncniX - trenutniX);
			}
			else {
				trenutniY = y - PET_V_VRSTO + 1;
				trenutniX = x - PET_V_VRSTO + 1;
				koncniX = x;
				koncniY = y;
			}
		}
		
		if ((y > (N - PET_V_VRSTO) && (x < PET_V_VRSTO - 1)) || (x > (N - PET_V_VRSTO)) && (y < PET_V_VRSTO - 1));
		else {
			for (int b = trenutniX; b < koncniX + 1; b++) {
				for (int a = trenutniY; a < koncniY + 1; a++) {
					LinkedList<Integer> vrsta_x = new LinkedList<Integer>();
					LinkedList<Integer> vrsta_y = new LinkedList<Integer>();
					for (int k = 0; k < PET_V_VRSTO; k++) {
						vrsta_x.add(k, trenutniX + k);
						vrsta_y.add(k, trenutniY + k);
					}
					VRSTE.add(new Vrsta(vrsta_x, vrsta_y));
				}
			}
		}
		
		trenutniX = x;
		trenutniY = y;		
		koncniX = x;
		koncniY = y;
		
		//SMER Z->V
		if (x < PET_V_VRSTO - 1) trenutniX = 0;
		else trenutniX = x - 4;
		if (x > (N - PET_V_VRSTO)) koncniX = N - PET_V_VRSTO;
		
		for (int a = trenutniX; a < koncniX + 1; a++) {
			LinkedList<Integer> vrsta_x = new LinkedList<Integer>();
			LinkedList<Integer> vrsta_y = new LinkedList<Integer>();
			for (int k = 0; k < PET_V_VRSTO; k++) {
				vrsta_x.add(k, trenutniX + k);
				vrsta_y.add(k, trenutniY);
			}
			VRSTE.add(new Vrsta(vrsta_x, vrsta_y));
		}
		
		//SMER JZ-> SV
		if (x < y) {
			if (x < PET_V_VRSTO - 1) {
				trenutniX = 0;
				trenutniY = y + (koncniX - trenutniX);
			}
			else if (y > (N - PET_V_VRSTO)) {
				koncniY = N - PET_V_VRSTO;
				koncniX = x - (koncniY - trenutniY);
			}
			else {
				trenutniX = x - PET_V_VRSTO + 1;
				trenutniY = y + PET_V_VRSTO - 1;
				koncniX = x;
				koncniY = y;
			}
		}
		else {
			if (y < PET_V_VRSTO - 1) {
				trenutniY = 0;
				trenutniX = x - (koncniY - trenutniY);
			}
			else if (x > (N - PET_V_VRSTO)) {
				koncniX = N - PET_V_VRSTO;
				koncniY = y + (koncniX - trenutniX);
			}
			else {
				trenutniY = y + PET_V_VRSTO - 1;
				trenutniX = x - PET_V_VRSTO + 1;
				koncniX = x;
				koncniY = y;
			}
		}
		
		if ((x + y < N - 1) || (x + y > 2 * N - PET_V_VRSTO - 1));
		else {
			for (int b = trenutniX; b < koncniX + 1; b++) {
				for (int a = trenutniY; a < koncniY + 1; a++) {
					LinkedList<Integer> vrsta_x = new LinkedList<Integer>();
					LinkedList<Integer> vrsta_y = new LinkedList<Integer>();
					for (int k = 0; k < PET_V_VRSTO; k++) {
						vrsta_x.add(k, trenutniX + k);
						vrsta_y.add(k, trenutniY - k);
					}
					VRSTE.add(new Vrsta(vrsta_x, vrsta_y));
				}
			}
		}
	}
	
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}
		}
		naPotezi = Igralec.W;
	}
	
	public List<Koordinati> poteze() {
		LinkedList<Koordinati> ps = new LinkedList<Koordinati>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.PRAZNO) {
					ps.add(new Koordinati(i, j));
				}
			}
		}
		return ps;
	}

	
	private Igralec cigavaVrsta(Vrsta t) {
		int count_W = 0;
		int count_B = 0;
		for (int k = 0; k < PET_V_VRSTO && (count_W == 0 || count_B == 0); k++) {
			//System.out.println("A to kej nardi?");
			switch (plosca[t.x.getFirst()][t.y.getFirst()]) {
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
		for (Vrsta t : VRSTE) {
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
			preverjanje(p.getX(), p.getY());
			naPotezi = naPotezi.nasprotnik();
			return true;
		}
		else {
			return false;
		}
	}
}
