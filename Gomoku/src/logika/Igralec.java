package logika;

import java.util.HashSet;
import java.util.LinkedList;

// Možni igralci

public enum Igralec {
	W, B;
	
	private HashSet<Vrsta> VRSTE = new HashSet<Vrsta>();

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

		return VRSTE;
	}
	
	private void vrsteVSmeri(int a1, int a2, int k1, int k2, int x, int y){
		for (int a = 0; a < 5; ++a) {
			LinkedList<Integer> vrsta_x = new LinkedList<Integer>();
			LinkedList<Integer> vrsta_y = new LinkedList<Integer>();
			int mejaX = x + a1 * a;
			int mejaY = y + a2 * a;
			for (int k = 0; k < Igra.PET_V_VRSTO; ++k) {
				if (mejaX + k1 * k >= 0 && mejaX + k1 * k < 15 && mejaY + k2 * k >= 0 && mejaY + k2 * k < 15) {
					vrsta_x.add(mejaX + k1 * k);
					vrsta_y.add(mejaY + k2 * k);
				}
			}
			if (vrsta_x.size() == 5) {
				VRSTE.add(new Vrsta(vrsta_x, vrsta_y));
			}
		}
	}
}