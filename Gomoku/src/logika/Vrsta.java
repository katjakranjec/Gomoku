package logika;

import java.util.Arrays;

//Objekt, ki predstavlja vrsto petih polj na plošèi

public class Vrsta {
	// Vrsta na plošèi je predstavljena z dvema tabelama dolžine 5.
	// To sta tabeli x in y koordinat.bla
	public int[] x;
	public int[] y;
	
	public Vrsta(int[] x, int y[]) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vrsta [x=" + Arrays.toString(x) + ", y=" + Arrays.toString(y) + "]";
	}
}
