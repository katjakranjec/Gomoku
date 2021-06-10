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
		if (this == o) return true; 
		if (o == null || this.getClass() != o.getClass()) return false;
		Vrsta k = (Vrsta) o; 
		return this.x.equals(k.x) && this.y.equals(k.y);

	}
	

	@Override
	public int hashCode() {
		int vsota = 0;
		for (int koordinata : x) {
			vsota += koordinata;
		}
		return vsota;
	}
}