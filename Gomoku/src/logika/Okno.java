package logika;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Okno extends JFrame {

	public Platno platno;
	
	public Okno() {
		super();
		setTitle("Gomoku");
		platno = new Platno(400, 400);
		add(platno);
	}
	
}
