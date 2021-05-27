package logika;

// Možni igralci

public enum Igralec {
	W, B;
	
//	private HashSet<Vrsta> VRSTE = new HashSet<Vrsta>();
//	private LinkedList<Koordinati> ODIGRANE = new LinkedList<Koordinati>();
	public Igra igra;

	public Igralec nasprotnik() {
		return (this == W ? B : W);
	}

	public Polje getPolje() {
		return (this == W ? Polje.W : Polje.B);
	}
}