package logika;

import java.util.HashSet;
import java.util.LinkedList;

// Možni igralci

public enum Igralec {
	W, B;
	
	private HashSet<Vrsta> VRSTE = new HashSet<Vrsta>();
	private LinkedList<Koordinati> ODIGRANE = new LinkedList<Koordinati>();
	public Igra igra;

	public Igralec nasprotnik() {
		return (this == W ? B : W);
	}

	public Polje getPolje() {
		return (this == W ? Polje.W : Polje.B);
	}
	
	
	public HashSet<Vrsta> getVrste() {
		return VRSTE;
	}
	
	
	public HashSet<Vrsta> getVrste(Koordinati q) {
		
		int x = q.getX();
		int y = q.getY();
		
		//SMER S->J
		vrsteVSmeri(0, -1, 0, 1, x, y);
		
		//SMER SZ -> JV
		vrsteVSmeri(-1, -1, 1, 1, x, y);
		
		//SMER Z -> V
		vrsteVSmeri(-1, 0, 1, 0, x, y);
		
		//SMER JZ -> SV
		vrsteVSmeri(-1, 1, 1, -1, x, y);
		
		//Doda koordinato v ODIGRANE
		ODIGRANE.add(q);

		return VRSTE;
	}
	
	private void vrsteVSmeri(int a1, int a2, int k1, int k2, int x, int y){
		for (int a = 0; a < Igra.PET_V_VRSTO; ++a) {
			LinkedList<Integer> vrsta_x = new LinkedList<Integer>();
			LinkedList<Integer> vrsta_y = new LinkedList<Integer>();
			int mejaX = x + a1 * a;
			int mejaY = y + a2 * a;
			for (int k = 0; k < Igra.PET_V_VRSTO; ++k) {
				if (mejaX + k1 * k >= 0 && mejaX + k1 * k < igra.n && mejaY + k2 * k >= 0 && mejaY + k2 * k < igra.n) {
					vrsta_x.add(mejaX + k1 * k);
					vrsta_y.add(mejaY + k2 * k);
				}
			}
			if (vrsta_x.size() == Igra.PET_V_VRSTO) {
				VRSTE.add(new Vrsta(vrsta_x, vrsta_y));
			}
		}
	}
	
	public void odstraniVrste() {
		// Koordinati zadnja_poteza = ODIGRANE.getLast();
		ODIGRANE.removeLast();
		for (Vrsta vrsta: VRSTE) {
			LinkedList<Koordinati> koordinate = Vrsta.koordinateVVrsti(vrsta);
			int stevilo = 0;
			for (Koordinati u: koordinate) {
				if (ODIGRANE.contains(u)) stevilo += 1;
			}
			if (stevilo == 0) VRSTE.remove(vrsta);
		}
	}
}