

import java.awt.Color;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Okno extends JFrame {

	public Platno platno;
	
	public static final Color BURLY_WOOD = new Color(222,184,135);
	
	public Okno() {
		super();
		setTitle("Gomoku");
		platno = new Platno(700, 700);
		platno.setBackground(BURLY_WOOD);
		add(platno);
	}
	
}
