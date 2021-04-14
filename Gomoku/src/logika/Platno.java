package logika;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Platno extends JPanel {

	protected Igra igra;
	
	protected Color barvaMreze;
	protected Color barvaB;
	protected Color barvaW;
	protected float debelinaMreze;
	
	protected int sirina;
	protected int visina;
	protected int polmer;
	
	
	public Platno(int sirina, int visina) {
		super();
		setPreferredSize(new Dimension(sirina, visina));
		igra = null;
		
		
		this.barvaMreze = Color.LIGHT_GRAY;
		this.barvaB = Color.BLACK;
		this.barvaW = Color.LIGHT_GRAY;
		this.debelinaMreze = 2;
		
		this.sirina = sirina;
		this.visina = visina;
		this.polmer = 10;
		
		//addMouseListener(this);
		//addMouseMotionListener(this);
		//addKeyListener(this);
		//setFocusable(true);
			
	}
	
	public void nastaviIgro(Igra igra) {
		this.igra = igra;
		repaint();
		
	}
	
	private static int round(double x) {
		return (int)(x + 0.5);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if (igra == null) return;
		
		g2d.setColor(barvaMreze);
		g2d.setStroke(new BasicStroke(this.debelinaMreze));
		//navpiène èrte
		int sirinskiRazmik = round(sirina / (igra.n + 2));
		
		for (int i = 1; i < igra.n + 1; i++) {
			g2d.drawLine(i * sirinskiRazmik, sirinskiRazmik, i * sirinskiRazmik, (igra.n) * sirinskiRazmik);
		}
		//vodoravne èrte
		int visinskiRazmik = round(visina / (igra.n + 2));
		
		for (int j = 1; j < igra.n + 1; j++) {
			g2d.drawLine(visinskiRazmik, j * visinskiRazmik, (igra.n) * visinskiRazmik, j * visinskiRazmik);
		}
		
		for (Koordinati zeton: igra.odigranePrvi) {
			g2d.setColor(barvaW);
			int x = zeton.getX() + 1;
			int y = zeton.getY() + 1;
			g2d.fillOval(x * sirinskiRazmik - polmer, y * sirinskiRazmik - polmer, polmer * 2, polmer * 2);
			
		}
		
		for (Koordinati zeton: igra.odigraneDrugi) {
			g2d.setColor(barvaB);
			int x = zeton.getX() + 1;
			int y = zeton.getY() + 1;
			g2d.fillOval(x * sirinskiRazmik - polmer, y * sirinskiRazmik - polmer, polmer * 2, polmer * 2);
			
		}
		
		
		repaint();
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
