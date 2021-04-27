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
	
	private final static Color ODZADJE = new Color(222,184,135);
	private final static Color BARVA_W = new Color(250,235,215);
	private final static Color BARVA_B = new Color(128,0,0);
	private final static Color BARVA_MREZE = Color.BLACK;
	private final static Color BARVA_OBROBE = Color.BLACK;
	private final static Color ZMAGA = Color.YELLOW;

	private final static double DEBELINA_MREZE = 0.08;
	private final static double DEBELINA_OBROBE = 0.065;
	
	private Igralec zmagovalec;
	private Vrsta zmagovalnaVrsta;

	public Platno() {
		setBackground(ODZADJE);
		addMouseListener(this);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
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
		if (Vodja.poteza != null) {
			zmagovalnaVrsta = Vodja.igra.zmagovalnaVrsta(Vodja.poteza);
			if (zmagovalnaVrsta != null) {
				zmagovalec = Vodja.igra.cigavaVrsta(zmagovalnaVrsta);
			}
		}

//		mreža
		g2d.setColor(BARVA_MREZE);
		g2d.setStroke(new BasicStroke((float)(w * DEBELINA_MREZE)));
		
//		navpiène èrte
		for (int i = 1; i < Igra.n + 1; i++) {
			g2d.drawLine((int)(i * w), 0, (int)(i * w), (int)((Igra.n + 1) * w));
		}
		
//		vodoravne èrte
		for (int j = 1; j < Igra.n + 1; j++) {
			g2d.drawLine(0, (int) (j * w), (int)((Igra.n + 1) * w), (int) (j * w));
		}
		
		if (Vodja.igra != null) {
//			žetoni
			g2d.setStroke(new BasicStroke((float)(w * DEBELINA_OBROBE)));
			
			for (Koordinati zeton: Vodja.igra.odigraneWhite) {
				g2d.setColor(BARVA_W);
				int x = zeton.getX() + 1;
				int y = zeton.getY() + 1;
				if (zmagovalec == Igralec.W) {
					Koordinati[] koordinate = zmagovalnaVrsta.koordinateVVrsti();
					if (Arrays.asList(koordinate).contains(zeton)) {
						g2d.setColor(ZMAGA);
					}
				}
				g2d.fillOval((int)(x * w - polmer), (int) (y * w - polmer), (int) (polmer * 2), (int) (polmer * 2));
			}	
			
			
			for (Koordinati zeton: Vodja.igra.odigraneWhite) {
				g2d.setColor(BARVA_OBROBE);
				int x = zeton.getX() + 1;
				int y = zeton.getY() + 1;
				g2d.drawOval((int) (x * w - polmer), (int) (y * w - polmer), (int) (polmer * 2), (int) (polmer * 2));
			}
			
			for (Koordinati zeton: Vodja.igra.odigraneBlack) {
				g2d.setColor(BARVA_B);
				int x = zeton.getX() + 1;
				int y = zeton.getY() + 1;
				if (zmagovalec == Igralec.B) {
					Koordinati[] koordinate = zmagovalnaVrsta.koordinateVVrsti();
					if (Arrays.asList(koordinate).contains(zeton)) {
						g2d.setColor(ZMAGA);
					}
				}
				g2d.fillOval((int) (x * w - polmer), (int) (y * w - polmer), (int) (polmer * 2), (int) (polmer * 2));			

			}
			
			for (Koordinati zeton: Vodja.igra.odigraneBlack) {
				g2d.setColor(BARVA_OBROBE);
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
		if (Vodja.clovekNaVrsti) {
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
			Vodja.igrajClovekovaPoteza(krizisce);
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
