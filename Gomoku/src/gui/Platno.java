package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JPanel;

import logika.Igra;
import logika.Igralec;
import logika.Vrsta;
import splosno.Koordinati;
import vodja.Vodja;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener {
	
	//public Color ODZADJE = new Color(222,184,135);
	//public Color BARVA_W = new Color(250,235,215);
	//public Color BARVA_B = new Color(128,0,0);
	//public Color BARVA_MREZE = Color.BLACK;
	//private final static Color BARVA_OBROBE = Color.BLACK;
	//private final static Color ZMAGA = Color.YELLOW;

	//private final static double DEBELINA_MREZE = 0.08;
	//private final static double DEBELINA_OBROBE = 0.065;
	
	//public Igralec zmagovalec = null;
	//private Vrsta zmagovalnaVrsta;

	//public Platno() {
	//	setBackground(ODZADJE);
	//	addMouseListener(this);
	//}
	
	
	
	
	//@Override
	//public Dimension getPreferredSize() {
	//	return new Dimension(400, 400);
	//}
	
	public Vodja vodja;
	
	protected Color barvaOzadja;
	protected Color barvaMreze;
	protected Color barvaB;
	protected Color barvaW;
	protected Color barvaObrobe;
	protected double debelinaMreze;
	protected double debelinaObrobe;
	
	protected int sirina;
	protected int visina;
	protected int polmer;
	
	public Igralec zmagovalec;
	private Vrsta zmagovalnaVrsta;
	private Color barvaZmage;
	
	public static final Color MAROON = new Color(128,0,0);
	public static final Color ANTIQUE_WHITE = new Color(250,235,215);
	public static final Color BURLY_WOOD = new Color(222,184,135);
	
	
	
	public Platno(int sirina, int visina) {
		super();
		setPreferredSize(new Dimension(sirina, visina));
		vodja = null;
		//vodja = new Vodja();
		
		this.barvaOzadja = BURLY_WOOD;
		this.barvaMreze = Color.BLACK;
		this.barvaB = MAROON;
		this.barvaW = ANTIQUE_WHITE;
		this.barvaObrobe = Color.BLACK;
		this.debelinaMreze = 0.08;
		this.debelinaObrobe = 0.065;
		
		this.sirina = sirina;
		this.visina = visina;
		
		this.polmer = 15;
		
		this.zmagovalec = null;
		this.zmagovalnaVrsta = null;
		this.barvaZmage = Color.YELLOW;
		
		addMouseListener(this);
	}
	
	public void nastaviIgro(Vodja vodja) {
		this.vodja = vodja;
		repaint();
		
	}
	
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / (Igra.n + 1);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		double w = squareWidth();
		double polmer = w / 2.3;
		
//		zmagovalec
		if (vodja.poteza != null && vodja.igra != null) {
			zmagovalnaVrsta = vodja.igra.zmagovalnaVrsta(vodja.poteza);
			if (zmagovalnaVrsta != null) {
				zmagovalec = vodja.igra.cigavaVrsta(zmagovalnaVrsta);
			}
		}

//		mreža
		g2d.setColor(barvaMreze);
		g2d.setStroke(new BasicStroke((float)(w * debelinaMreze)));
		
//		navpiène èrte
		for (int i = 1; i < Igra.n + 1; i++) {
			g2d.drawLine((int)(i * w), 0, (int)(i * w), (int)((Igra.n + 1) * w));
		}
		
//		vodoravne èrte
		for (int j = 1; j < Igra.n + 1; j++) {
			g2d.drawLine(0, (int) (j * w), (int)((Igra.n + 1) * w), (int) (j * w));
		}
		
		if (vodja.igra != null) {
//			žetoni
			g2d.setStroke(new BasicStroke((float)(w * debelinaObrobe)));
			
			for (Koordinati zeton: vodja.igra.odigraneW) {
				g2d.setColor(barvaW);
				int x = zeton.getX() + 1;
				int y = zeton.getY() + 1;
				if (zmagovalec == Igralec.W) {
					Koordinati[] koordinate = zmagovalnaVrsta.koordinateVVrsti();
					if (Arrays.asList(koordinate).contains(zeton)) {
						g2d.setColor(barvaZmage);
					}
				}
				g2d.fillOval((int)(x * w - polmer), (int) (y * w - polmer), (int) (polmer * 2), (int) (polmer * 2));
			}	
			
			for (Koordinati zeton: vodja.igra.odigraneW) {
				g2d.setColor(barvaObrobe);
				int x = zeton.getX() + 1;
				int y = zeton.getY() + 1;
				g2d.drawOval((int) (x * w - polmer), (int) (y * w - polmer), (int) (polmer * 2), (int) (polmer * 2));
			}
			
			for (Koordinati zeton: vodja.igra.odigraneB) {
				g2d.setColor(barvaB);
				int x = zeton.getX() + 1;
				int y = zeton.getY() + 1;
				if (zmagovalec == Igralec.B) {
					Koordinati[] koordinate = zmagovalnaVrsta.koordinateVVrsti();
					if (Arrays.asList(koordinate).contains(zeton)) {
						g2d.setColor(barvaZmage);
					}
				}
				g2d.fillOval((int) (x * w - polmer), (int) (y * w - polmer), (int) (polmer * 2), (int) (polmer * 2));			
			}
			
			for (Koordinati zeton: vodja.igra.odigraneB) {
				g2d.setColor(barvaObrobe);
				int x = zeton.getX() + 1;
				int y = zeton.getY() + 1;
				g2d.drawOval((int) (x * w - polmer), (int) (y * w - polmer), (int) (polmer * 2), (int) (polmer * 2));
			}
		}
	}
	
	private static int round(double x) {
		return (int)(x + 0.5);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (vodja.clovekNaVrsti) {
			double w = squareWidth();
			
			double x = (e.getX() / w);
			double y = (e.getY() / w);
			
			int zaokrozen_x = round(x);
			int zaokrozen_y = round(y);
			
			if (zaokrozen_x - x > 0.3 || zaokrozen_x - x < -0.3) return;
			if (y - zaokrozen_y > 0.3 || y - zaokrozen_y < -0.3) return;
			
			int koordinata_x = zaokrozen_x - 1;
			int koordinata_y = zaokrozen_y - 1;
			
			if (koordinata_x >= Igra.n || koordinata_y >= Igra.n) return;
			
			Koordinati krizisce = new Koordinati(koordinata_x, koordinata_y);
			vodja.igrajClovekovaPoteza(krizisce);
			repaint();
		}
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
