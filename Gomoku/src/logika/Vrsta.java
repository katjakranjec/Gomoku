package logika;

import java.util.Arrays;

import splosno.Koordinati;

public class Vrsta {
	// Vrsta na plošèi je predstavljena z dvema tabelama dolžine 5.
	// To sta tabeli x in y koordinat.
	public int[] x;
	public int[] y;
	
	public Vrsta(int[] x, int[] y) {
		this.x = x;
		this.y = y;
	}
	
	public Koordinati[] koordinateVVrsti() {
		Koordinati[] koordinate = new Koordinati[5];
		for (int i = 0; i < 5; i++) {
			int a = this.x[i];
			int b = this.y[i];
			koordinate[i] = (new Koordinati(a, b));
		}
		return koordinate;
	}

	@Override
	public String toString() {
		return "[" + Arrays.toString(x) + "], [" + Arrays.toString(y) + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true; // hiter in pogost primer
		if (o == null || this.getClass() != o.getClass()) return false;
		Vrsta k = (Vrsta) o; // vemo, da je Koordinati tip objekta o
		return this.x.equals(k.x) && this.y.equals(k.y);

	}
	
	//menda, ce sta razlicna elementa, hashCode ne rabi bit razlicen, vazno je samo,
	//da je za enaka elementa enak
	@Override
	public int hashCode() {
		int vsota = 0;
		for (int koordinata : x) {
			vsota += koordinata;
		}
		return vsota;
	}

//preverjanje delovanje metode equals in hashCode:
//	public static void main(String[] args){
//		LinkedList<Integer> x1 = new LinkedList<Integer>();
//		LinkedList<Integer> y1 = new LinkedList<Integer>();
//		LinkedList<Integer> x2 = new LinkedList<Integer>();
//		LinkedList<Integer> y2 = new LinkedList<Integer>();
//		for (int i = 0; i < 10; ++i) {
//			x1.add(i);
//			x2.add(i);
//		}
//		for (int i = 10; i < 20; ++i) {
//			y1.add(i);
//			y2.add(i);	
//		}
//		Vrsta v = new Vrsta(x1, y1);
//		Vrsta u = new Vrsta(x2, y2);
//		System.out.println(u.equals(v));
//	}
}