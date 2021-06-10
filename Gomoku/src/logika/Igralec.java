package logika;

// Možni igralci

public enum Igralec {
	W, B;
	
	public Igra igra;

	public Igralec nasprotnik() {
		return (this == W ? B : W);
	}

	public Polje getPolje() {
		return (this == W ? Polje.W : Polje.B);
	}
}