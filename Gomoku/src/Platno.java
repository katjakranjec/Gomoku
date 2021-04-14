

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Igra;
import logika.Koordinati;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener {

	protected Vodja vodja;
	
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
		vodja = null;
		
		
		this.barvaMreze = Color.BLACK;
		this.barvaB = Color.MAGENTA;
		this.barvaW = Color.GREEN;
		this.debelinaMreze = 2;
		
		this.sirina = sirina;
		this.visina = visina;
		this.polmer = 15;
		
		addMouseListener(this);
		//addMouseMotionListener(this);
		//addKeyListener(this);
		//setFocusable(true);
			
	}
	
	public void nastaviIgro(Vodja vodja) {
		this.vodja = vodja;
		repaint();
		
	}
	
	private static int round(double x) {
		return (int)(x + 0.5);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if (vodja == null) return;
		
		g2d.setColor(barvaMreze);
		g2d.setStroke(new BasicStroke(this.debelinaMreze));
		//navpiène èrte
		int sirinskiRazmik = round(sirina / (vodja.igra.n + 2));
		
		for (int i = 1; i < vodja.igra.n + 1; i++) {
			g2d.drawLine(i * sirinskiRazmik, sirinskiRazmik, i * sirinskiRazmik, (vodja.igra.n) * sirinskiRazmik);
		}
		//vodoravne èrte
		int visinskiRazmik = round(visina / (vodja.igra.n + 2));
		
		for (int j = 1; j < vodja.igra.n + 1; j++) {
			g2d.drawLine(visinskiRazmik, j * visinskiRazmik, (vodja.igra.n) * visinskiRazmik, j * visinskiRazmik);
		}
		
		for (Koordinati zeton: vodja.igra.odigranePrvi) {
			g2d.setColor(barvaW);
			int x = zeton.getX() + 1;
			int y = zeton.getY() + 1;
			g2d.fillOval(x * sirinskiRazmik - polmer, y * sirinskiRazmik - polmer, polmer * 2, polmer * 2);
			
		}
		
		for (Koordinati zeton: vodja.igra.odigraneDrugi) {
			g2d.setColor(barvaB);
			int x = zeton.getX() + 1;
			int y = zeton.getY() + 1;
			g2d.fillOval(x * sirinskiRazmik - polmer, y * sirinskiRazmik - polmer, polmer * 2, polmer * 2);
			
		}
		
		
		repaint();
	
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (vodja == null) return;
		int klikX = e.getX();
		int klikY = e.getY();
		
		double x = klikX / (round(sirina / (vodja.igra.n + 2)));
		double y = klikY / (round(sirina / (vodja.igra.n + 2)));
		
		int koordinata_x = round(x);
		int koordinata_y = round(y);
		
		if (x - koordinata_x > 0.25 && x - koordinata_x < -0.25) return;
		if (y - koordinata_y > 0.25 && y - koordinata_y < -0.25) return;
		
		if (koordinata_x > vodja.igra.n || koordinata_y > vodja.igra.n) return;
		
		Koordinati krizisce = new Koordinati(koordinata_x, koordinata_y);
		vodja.igra.odigraj(krizisce);
		repaint();
		
		
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
