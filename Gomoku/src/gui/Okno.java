package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logika.Igralec;
import splosno.Koordinati;
import vodja.Vodja;
import vodja.VrstaIgralca;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener{

	public Platno platno;
	
	private JLabel status;
	
//	 Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	private JMenuItem velikostIgre;
	private JMenuItem barvePolja;
	private JMenuItem barvaMreze;
	private JMenuItem barvaOdzadja;
	private JMenuItem nastavitveIgralca;
	private JMenuItem barvaW;
	private JMenuItem barvaB;
	private JMenuItem imeW;
	private JMenuItem imeB;
	private JMenuItem nastavitveRacunalnika;
	private JMenuItem hitrostRacunalnika;
	private JMenuItem algoritem;
	private JButton razveljavi;
	
	private String StringImeW;
	private String StringImeB;
	
	public Okno() {
		setTitle("Gomoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
//		igralno polje
		platno = new Platno();
		
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(platno, polje_layout);
		
//		Menu
		
//		Nova igra
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Nova igra");
		menu_bar.add(igra_menu);
		
//		Velikost igralnega polja
		velikostIgre = new JMenuItem("Velikost igralnega polja");
		igra_menu.add(velikostIgre);
		velikostIgre.addActionListener(this);
		
//		izbire vrst iger
		igraClovekRacunalnik = new JMenuItem("�lovek - Ra�unalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Ra�unalnik - �lovek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		igraClovekClovek = new JMenuItem("�lovek - �lovek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		igraRacunalnikRacunalnik = new JMenuItem("Ra�unalnik - Ra�unalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);

//		Nastavitve
		JMenu nastavitve = new JMenu("Nastavitve");
		menu_bar.add(nastavitve);
		
//		Nastavitve za igralce
		nastavitveIgralca = new JMenu("Nastavitve igralcev...");
		nastavitve.add(nastavitveIgralca);
		nastavitveIgralca.addActionListener(this);
		
		imeW = new JMenuItem("Ime 1. igralca");
		nastavitveIgralca.add(imeW);
		imeW.addActionListener(this);
		
		imeB = new JMenuItem("Ime 2. igralca");
		nastavitveIgralca.add(imeB);
		imeB.addActionListener(this);
		
		barvaW = new JMenuItem("Barva 1. igralca");
		nastavitveIgralca.add(barvaW);
		barvaW.addActionListener(this);
		
		barvaB = new JMenuItem("Barva 2. igralca");
		nastavitveIgralca.add(barvaB);
		barvaB.addActionListener(this);
		
//		Nastavitve za ra�unalnik
		nastavitveRacunalnika = new JMenu("Nastavitve ra�unalnika...");
		nastavitve.add(nastavitveRacunalnika);
		nastavitveRacunalnika.addActionListener(this);
		
		hitrostRacunalnika = new JMenuItem("Hitrost, s katero igra ra�unalnik");
		nastavitveRacunalnika.add(hitrostRacunalnika);
		hitrostRacunalnika.addActionListener(this);
		
		algoritem = new JMenuItem("Algoritem, s katerim igra ra�unalnik");
		nastavitveRacunalnika.add(algoritem);
		algoritem.addActionListener(this);
		
//		Nastavitve barv
		barvePolja = new JMenu("Barva polja...");
		nastavitve.add(barvePolja);
		barvePolja.addActionListener(this);
		
		barvaMreze = new JMenuItem("Barva mre�e");
		barvePolja.add(barvaMreze);
		barvaMreze.addActionListener(this);
		
		barvaOdzadja = new JMenuItem("Barva odzadja");
		barvePolja.add(barvaOdzadja);
		barvaOdzadja.addActionListener(this);
		
//		Gumb Razveljavi
		JPanel gumb = new JPanel();
		gumb.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		razveljavi = new JButton("Razveljavi");
		gumb.add(razveljavi);
		razveljavi.addActionListener(this);
		
		menu_bar.add(gumb);
		
//		statusna vrstica za sporo�ila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Izberite igro!");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.R);
			Vodja.igramoNovoIgro();
			platno.zmagovalec = null;
		} else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
			platno.zmagovalec = null;
		} else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
			platno.zmagovalec = null;
		} else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.R);
			Vodja.igramoNovoIgro();
			platno.zmagovalec = null;
		} else if (e.getSource() == velikostIgre) {
			if (platno.zmagovalec != null || (Vodja.igra == null)) {
				int velikost = 0;
				try {
					velikost = Integer.parseInt(JOptionPane.showInputDialog("Vnesite �eleno velikost polja:"));
					if (velikost < 5 || velikost > 20) {
						status.setText("Velikost polja mora biti ve�ja od 5 in manj�a od 20.");
						velikost = 0;
					}
				} catch (NumberFormatException e1) {
					status.setText("�e �elite dolo�iti velikost igralnega polja vnesite �tevilko (Nova igra > Velikost igralnega polja)");
				}
				if (velikost != 0) {
					Vodja.igra.n = velikost;
					Vodja.igra = null;					
				}
			} else {
				status.setText("Spreminjanje velikosti polja med igro ni mo�no.");
			}
		} else if (e.getSource() == barvaOdzadja) {
			Color barvaOdzadja = JColorChooser.showDialog(this, "Izberite barvo", platno.ODZADJE);
			if (barvaOdzadja != null) {
				platno.setBackground(barvaOdzadja);
			}
		} else if (e.getSource() == barvaMreze) {
			Color barvaMreze = JColorChooser.showDialog(this, "Izberite barvo", platno.BARVA_MREZE);
			if (barvaMreze != null) {
				platno.BARVA_MREZE = barvaMreze;
			}
		}
		else if (e.getSource() == barvaW) {
			Color barvaW = JColorChooser.showDialog(this, "Izberite barvo", platno.BARVA_W);
			if (barvaW != null) {
				platno.BARVA_W = barvaW;
			}
			
		} else if (e.getSource() == barvaB) {
			Color barvaB = JColorChooser.showDialog(this, "Izberite barvo", platno.BARVA_B);
			if (barvaB != null) {
				platno.BARVA_B = barvaB;
			}
		} else if (e.getSource() == imeW) {
			StringImeW = JOptionPane.showInputDialog("Vnesite ime:");
		} else if (e.getSource() == imeB) {
			StringImeB = JOptionPane.showInputDialog("Vnesite ime:");
		} else if (e.getSource() == hitrostRacunalnika) {
//			TO DO
		} else if (e.getSource() == algoritem) {
//			TO DO
		} else if (e.getSource() == razveljavi){
			if (Vodja.igra != null) {
				Koordinati p = Vodja.igra.razveljavi();
				if (p != null) {
					Vodja.poteza = p;
					platno.zmagovalec = null;
					Vodja.igramo();
				}
			}
		}
		platno.repaint();
	}
	
	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanje(Vodja.poteza)) {
			case NEODLOCENO: status.setText("Neodlo�eno!");
				break;
			case V_TEKU: 
				if (Vodja.igra.naPotezi == Igralec.W) {
					if (StringImeW == null) {
						status.setText("Na potezi je 1. igralec - " + Vodja.vrstaIgralca.get(Vodja.igra.naPotezi));
					} else {
						status.setText("Na potezi je " + StringImeW + 
						" - " + Vodja.vrstaIgralca.get(Vodja.igra.naPotezi));
					}
				}
				if (Vodja.igra.naPotezi == Igralec.B) {
					if (StringImeB == null) {
						status.setText("Na potezi je 2. igralec  - " + Vodja.vrstaIgralca.get(Vodja.igra.naPotezi));
					} else {
						status.setText("Na potezi je " + StringImeB + 
						" - " + Vodja.vrstaIgralca.get(Vodja.igra.naPotezi));
					}
				}
				break;
			case ZMAGA_B:
				if (StringImeB == null) {
					status.setText("Zmagal je 2. igralec - "  + 
							Vodja.vrstaIgralca.get(Igralec.B));
				} else status.setText("Zmagal je " + StringImeB + "!");
				break;
			case ZMAGA_W:
				if (StringImeB == null) {
					status.setText("Zmagal je 1. igralec - "  + 
							Vodja.vrstaIgralca.get(Igralec.W));
				} else status.setText("Zmagal je " + StringImeW + "!");
				break;
			}
		}
		platno.repaint();
	}
}
