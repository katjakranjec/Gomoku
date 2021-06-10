package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.Hashtable;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logika.Igra;
import logika.Igralec;
import vodja.Vodja;
import vodja.VrstaIgralca;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener, ChangeListener{

	private boolean zakljucenaIgra = true;
	
	public Platno platno;
	
	private JLabel status;
	
	// Možne izbire v menijh
	private JMenu igra_menu;
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	private JMenu velikost;
	private JSlider izberiVelikost;
	private JMenuItem barvePolja;
	private JMenuItem barvaMreze;
	private JMenuItem barvaOzadja;
	private JMenu nastavitveIgralca;
	private JMenuItem barvaW;
	private JMenuItem barvaB;
	private JMenuItem imeW;
	private JMenuItem imeB;
	private JMenu nastavitveRacunalnika;
	private JMenu hitrostRacunalnika;
	private JSlider izberiHitrost;
	private JMenu algoritem;
	private JMenuItem minimax;
	private JMenuItem alfaBeta;
	private JMenu globina;
	private JSlider izberiGlobino;
	
	private String StringImeW;
	private String StringImeB;
	
	// Konstruktor Okno(), ki ustvari novo platno in nastavi lastnosti platna ter ga doda v okno in ustvari nastavitve
	public Okno() {
		setTitle("Gomoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		//igralno polje
		platno = new Platno(500, 500);
		platno.setBackground(platno.barvaOzadja);
		
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(platno, polje_layout);
		
		// Vrtica z meniji:
		
		// Meni Nova igra:
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		igra_menu = new JMenu("Nova igra");
		menu_bar.add(igra_menu);
		
		// Nastavitve velikosti igralnega polja
		velikost = new JMenu("Velikost igralnega polja");
		igra_menu.add(velikost);		
		
		izberiVelikost = new JSlider(5, 20, 15);
		velikost.add(izberiVelikost);
		izberiVelikost.addChangeListener(this);
		
		izberiVelikost.setPaintTicks(true);
		izberiVelikost.setMajorTickSpacing(1);
		izberiVelikost.setPaintTicks(true);
		
		Hashtable labelTableVelikost = new Hashtable();
		labelTableVelikost.put(5, new JLabel("5"));
		labelTableVelikost.put(10, new JLabel("10"));
		labelTableVelikost.put(15, new JLabel("15"));
		labelTableVelikost.put(20, new JLabel("20"));
		izberiVelikost.setLabelTable(labelTableVelikost);
		izberiVelikost.setPaintLabels(true);
		
		// Izbira vrste iger
		igraRacunalnikClovek = new JMenuItem("Èlovek - Raèunalnik ");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		igraClovekRacunalnik = new JMenuItem("Raèunalnik - Èlovek");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraClovekClovek = new JMenuItem("Èlovek - Èlovek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		igraRacunalnikRacunalnik = new JMenuItem("Raèunalnik - Raèunalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);

		// Meni Nastavitve:
		JMenu nastavitve = new JMenu("Nastavitve");
		menu_bar.add(nastavitve);
		
		//Nastavitve za igralce
		nastavitveIgralca = new JMenu("Nastavitve igralcev...");
		nastavitve.add(nastavitveIgralca);
		
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
		
		//Nastavitve za raèunalnik
		nastavitveRacunalnika = new JMenu("Nastavitve raèunalnika...");
		nastavitve.add(nastavitveRacunalnika);
		
		hitrostRacunalnika = new JMenu("Hitrost, s katero igra raèunalnik");
		nastavitveRacunalnika.add(hitrostRacunalnika);
		
		izberiHitrost = new JSlider(0, 10, 1);
		hitrostRacunalnika.add(izberiHitrost);
		izberiHitrost.addChangeListener(this);
		
		izberiHitrost.setPaintTicks(true);
		izberiHitrost.setMajorTickSpacing(1);
		izberiHitrost.setPaintTicks(true);
		
		Hashtable labelTableHitrost = new Hashtable();
		labelTableHitrost.put(0, new JLabel("0"));
		labelTableHitrost.put(2, new JLabel("2"));
		labelTableHitrost.put(5, new JLabel("5"));
		labelTableHitrost.put(8, new JLabel("8"));
		labelTableHitrost.put(10, new JLabel("10"));
		izberiHitrost.setLabelTable( labelTableHitrost );
		izberiHitrost.setPaintLabels(true);
		
		algoritem = new JMenu("Algoritem, s katerim igra raèunalnik...");
		nastavitveRacunalnika.add(algoritem);
		
		minimax = new JMenuItem("Minimax");
		algoritem.add(minimax);
		minimax.addActionListener(this);
		
		alfaBeta = new JMenuItem("AlfaBeta");
		algoritem.add(alfaBeta);
		alfaBeta.addActionListener(this);
		
		globina = new JMenu("Globina algoritma...");
		nastavitveRacunalnika.add(globina);
		
		izberiGlobino = new JSlider(1, 5, 1);
		globina.add(izberiGlobino);
		izberiGlobino.addChangeListener(this);
		
		izberiGlobino.setPaintTicks(true);
		izberiGlobino.setMajorTickSpacing(1);
		izberiGlobino.setPaintTicks(true);
		
		Hashtable labelTableGlobina = new Hashtable();
		labelTableGlobina.put(1, new JLabel("1"));
		labelTableGlobina.put(2, new JLabel("2"));
		labelTableGlobina.put(3, new JLabel("3"));
		labelTableGlobina.put(4, new JLabel("4"));
		labelTableGlobina.put(5, new JLabel("5"));
		izberiGlobino.setLabelTable( labelTableGlobina );
		izberiGlobino.setPaintLabels(true);
		
		//Nastavitve barv
		barvePolja = new JMenu("Barva polja...");
		nastavitve.add(barvePolja);
		barvePolja.addActionListener(this);
		
		barvaMreze = new JMenuItem("Barva mreže");
		barvePolja.add(barvaMreze);
		barvaMreze.addActionListener(this);
		
		barvaOzadja = new JMenuItem("Barva ozadja");
		barvePolja.add(barvaOzadja);
		barvaOzadja.addActionListener(this);
		
		//Statusna vrstica za sporoèila
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
	
	private void nastaviIgralce(VrstaIgralca W, VrstaIgralca B) {
		int novaIgra = 0;
		if (!zakljucenaIgra) {
			int optionPane = JOptionPane.showConfirmDialog(null,
				    "Ali res želite zakljuèiti s trenutno igro?",
				    "Potrditev",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.WARNING_MESSAGE);
			if (optionPane != JOptionPane.YES_OPTION) novaIgra = 1;
		}
		if (novaIgra == 0) {
			Vodja vodja = new Vodja(this);
			vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			vodja.vrstaIgralca.put(Igralec.B, B); 
			vodja.vrstaIgralca.put(Igralec.W, W);
			platno.nastaviIgro(vodja);
			platno.zmagovalec = null;
			zakljucenaIgra = false;
			platno.vodja.igramo();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			nastaviIgralce(VrstaIgralca.C, VrstaIgralca.R);
		} else if (e.getSource() == igraRacunalnikClovek) {
			nastaviIgralce(VrstaIgralca.R, VrstaIgralca.C);
		} else if (e.getSource() == igraClovekClovek) {
			nastaviIgralce(VrstaIgralca.C, VrstaIgralca.C);
		} else if (e.getSource() == igraRacunalnikRacunalnik) {
			nastaviIgralce(VrstaIgralca.R, VrstaIgralca.R);
		} else if (e.getSource() == barvaOzadja) {
			Color barvaOzadja = JColorChooser.showDialog(this, "Izberite barvo", platno.barvaOzadja);
			if (barvaOzadja != null) {
				platno.setBackground(barvaOzadja);
			}
		} else if (e.getSource() == barvaMreze) {
			Color barvaMreze = JColorChooser.showDialog(this, "Izberite barvo", platno.barvaMreze);
			if (barvaMreze != null) {
				platno.barvaMreze = barvaMreze;
			}
		}
		else if (e.getSource() == barvaW) {
			Color barvaW = JColorChooser.showDialog(this, "Izberite barvo", platno.barvaW);
			if (barvaW != null) {
				platno.barvaW = barvaW;
			}
			
		} else if (e.getSource() == barvaB) {
			Color barvaB = JColorChooser.showDialog(this, "Izberite barvo", platno.barvaB);
			if (barvaB != null) {
				platno.barvaB = barvaB;
			}
		} else if (e.getSource() == imeW) {
			StringImeW = JOptionPane.showInputDialog("Vnesite ime:");
		} else if (e.getSource() == imeB) {
			StringImeB = JOptionPane.showInputDialog("Vnesite ime:");
		} else if (e.getSource() == izberiHitrost) {
			int hitrost = -1;
			try {
				hitrost = Integer.parseInt(JOptionPane.showInputDialog("Vnesite želeno hitrost raèunalnika v sekundah:"));
				if (hitrost < 0 || hitrost > 10) {
					status.setText("Hitrost raèunalnika mora biti veèja od 0 in manjša od 10.");
					hitrost = -1;
				}
			} catch (NumberFormatException e1) {
			status.setText("To pa ne bo šlo");
			}
			
			if (hitrost != -1) {
				Vodja.hitrost = hitrost;
			}
			
		} else if (e.getSource() == minimax) {
			Vodja.inteligenca = "Minimax";
		} else if (e.getSource() == alfaBeta) {
			Vodja.inteligenca = "AlfaBeta";

		}
		platno.repaint();
	}
	
	// Metoda osveziGUI, ki spreminja sporoèila v vrstici, glede na potek in stanje igre
	public void osveziGUI() {
		if (platno.vodja == null) {
			status.setText("Igra ni v teku.");
			zakljucenaIgra = true;
		}
		else if (platno.vodja.vrstaIgralca != null) {
			switch(platno.vodja.igra.stanje(platno.vodja.poteza)) {
			case NEODLOCENO: status.setText("Neodloèeno!");
			zakljucenaIgra = true;
				break;
			case V_TEKU:
				if (platno.vodja.vrstaIgralca != null) {
					if (platno.vodja.igra.naPotezi == Igralec.W) {
						if (StringImeW == null) {
							status.setText("Na potezi je 2. igralec - " + platno.vodja.vrstaIgralca.get(platno.vodja.igra.naPotezi));
						} else {
							status.setText("Na potezi je " + StringImeW + 
							" - " + platno.vodja.vrstaIgralca.get(platno.vodja.igra.naPotezi));
						}
					}
					if (platno.vodja.igra.naPotezi == Igralec.B) {
						if (StringImeB == null) {
							status.setText("Na potezi je 1. igralec - " + platno.vodja.vrstaIgralca.get(platno.vodja.igra.naPotezi));
						} else {
							status.setText("Na potezi je " + StringImeB + 
							" - " + platno.vodja.vrstaIgralca.get(platno.vodja.igra.naPotezi));
						}
					}
					break;
				}
			case ZMAGA_B:
				if (StringImeB == null) {
					status.setText("Zmagal je 2. igralec - "  + 
							platno.vodja.vrstaIgralca.get(Igralec.B));
				} else status.setText("Zmagal je " + StringImeB + "!");
				zakljucenaIgra = true;

				break;
			case ZMAGA_W:
				if (StringImeB == null) {
					status.setText("Zmagal je 1. igralec - "  + 
							platno.vodja.vrstaIgralca.get(Igralec.W));
				} else status.setText("Zmagal je " + StringImeW + "!");
				zakljucenaIgra = true;

				break;
			}
		}
		platno.repaint();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == izberiGlobino) {
			int globina = -1;
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		    	globina = (int)source.getValue();
		    }
		    if (globina != -1) Vodja.globina = globina;
		} else if (e.getSource() == izberiVelikost) {
			int n = 0;
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		    	n = (int)source.getValue();
		    }
			if (n != 0) {
				int novaIgra = 0;
				if (!zakljucenaIgra) {
					int optionPane = JOptionPane.showConfirmDialog(null,
						    "Ali res želite zakljuèiti s trenutno igro?",
						    "Potrditev",
						    JOptionPane.YES_NO_OPTION,
						    JOptionPane.WARNING_MESSAGE);
					if (optionPane != JOptionPane.YES_OPTION) novaIgra = 1;
				}
				if (novaIgra == 0) {
					Igra.n = n;
					Vodja vodja = new Vodja(this);
					platno.nastaviIgro(vodja);
					platno.zmagovalec = null;
					zakljucenaIgra = true;
					status.setText("Izberite igro!");
				}
			}
		} else if (e.getSource() == izberiHitrost) {
			int hitrost = -1;
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		    	hitrost = (int)source.getValue();
		    }
			if (hitrost != -1) {
				Vodja.hitrost = hitrost;
			}
		}
	}
}
