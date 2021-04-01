package logika;

import java.util.LinkedList;

public class Vrsta {
	// Vrsta na plošèi je predstavljena z dvema tabelama dolžine 5.
	// To sta tabeli x in y koordinat.
	public LinkedList<Integer> x;
	public LinkedList<Integer> y;
	private LinkedList<LinkedList<Integer>> holder;
	
	public Vrsta(LinkedList<Integer> x, LinkedList<Integer> y) {
		this.x = x;
		this.y = y;
		holder = new LinkedList<LinkedList<Integer>>();
		holder.add(x);
		holder.add(y);
	}

	@Override
	public String toString() {
		//return "Vrsta [x=" + LinkedList.toString(x) + ", y=" + LinkedList.toString(y) + "]";
		return "TO DO";
	}
}