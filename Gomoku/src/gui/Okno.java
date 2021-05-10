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

import logika.Igra;
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
	private JMenuItem barvaOzadja;
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
		platno = new Platno(500, 500);
		platno.setBackground(platno.barvaOzadja);
		
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
		igraClovekRacunalnik = new JMenuItem("Ra�unalnik - �lovek");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("�lovek - Ra�unalnik ");
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
		
		barvaOzadja = new JMenuItem("Barva ozadja");
		barvePolja.add(barvaOzadja);
		barvaOzadja.addActionListener(this);
		
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
			int novaIgra = 0;
			if (platno.vodja.igra != null) {
				int optionPane = JOptionPane.showConfirmDialog(null,
					    "Ali res �elite zaklju�iti s trenutno igro?",
					    "Potrditev",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				if (optionPane != JOptionPane.YES_OPTION) novaIgra = 1;
			}
			if (novaIgra == 0) {
				Vodja vodja = new Vodja();
				vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.C); 
				vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.R);
				platno.nastaviIgro(vodja);
				//Vodja.igramoNovoIgro();
				platno.zmagovalec = null;
				platno.vodja.igramo();
				//Probejmo to:
				//while (platno.vodja.igra != null) {
				//	osveziGUI();
				//}
			}
		} else if (e.getSource() == igraRacunalnikClovek) {
			int novaIgra = 0;
			if (platno.vodja.igra != null) {
				int optionPane = JOptionPane.showConfirmDialog(null,
					    "Ali res �elite zaklju�iti s trenutno igro?",
					    "Potrditev",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				if (optionPane != JOptionPane.YES_OPTION) novaIgra = 1;
			}
			if (novaIgra == 0) {
				//platno.vodja = new Vodja();
				//platno.vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				//platno.vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.R); 
				//platno.vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.C);
				//Vodja.igramoNovoIgro();
				//platno.zmagovalec = null;
				//platno.vodja.igramo();
				Vodja vodja = new Vodja();
				vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.R); 
				vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.C);
				platno.nastaviIgro(vodja);
				//Vodja.igramoNovoIgro();
				platno.zmagovalec = null;
				platno.vodja.igramo();
				//Probejmo to:
				//while (platno.vodja.igra != null) {
				//	osveziGUI();
				//}
			}
		} else if (e.getSource() == igraClovekClovek) {
			int novaIgra = 0;
			if (platno.vodja.igra != null) {
				int optionPane = JOptionPane.showConfirmDialog(null,
					    "Ali res �elite zaklju�iti s trenutno igro?",
					    "Potrditev",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				if (optionPane != JOptionPane.YES_OPTION) novaIgra = 1;
			}
			if (novaIgra == 0) {
				//platno.vodja = new Vodja();
				//platno.vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				//platno.vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.C); 
				//platno.vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.C);
				//Vodja.igramoNovoIgro();
				//platno.zmagovalec = null;
				//platno.vodja.igramo();
				Vodja vodja = new Vodja();
				vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.C); 
				vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.C);
				platno.nastaviIgro(vodja);
				//Vodja.igramoNovoIgro();
				platno.zmagovalec = null;
				platno.vodja.igramo();
				//Probejmo to:
				//while (platno.vodja.igra != null) {
				//	osveziGUI();
				//}
			}
		} else if (e.getSource() == igraRacunalnikRacunalnik) {
			int novaIgra = 0;
			if (platno.vodja.igra != null) {
				int optionPane = JOptionPane.showConfirmDialog(null,
					    "Ali res �elite zaklju�iti s trenutno igro?",
					    "Potrditev",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				if (optionPane != JOptionPane.YES_OPTION) novaIgra = 1;
			}
			if (novaIgra == 0) {
				//platno.vodja = new Vodja();
				//platno.vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				//platno.vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.R); 
				//platno.vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.R);
				//Vodja.igramoNovoIgro();
				//platno.zmagovalec = null;
				//platno.vodja.igramo();
				Vodja vodja = new Vodja();
				vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.R); 
				vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.R);
				platno.nastaviIgro(vodja);
				//Vodja.igramoNovoIgro();
				platno.zmagovalec = null;
				platno.vodja.igramo();
				//Probejmo to:
				//while (platno.vodja.igra != null) {
				//	osveziGUI();
				//}
			}
		} else if (e.getSource() == velikostIgre) {
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
				int novaIgra = 0;
				if (platno.vodja.igra != null) {
					int optionPane = JOptionPane.showConfirmDialog(null,
						    "Ali res �elite zaklju�iti s trenutno igro?",
						    "Potrditev",
						    JOptionPane.YES_NO_OPTION,
						    JOptionPane.WARNING_MESSAGE);
					if (optionPane != JOptionPane.YES_OPTION) novaIgra = 1;
				}
				if (novaIgra == 0) {
					//platno.vodja.igra.n = velikost
					Igra.n = velikost;
					VrstaIgralca beli = platno.vodja.vrstaIgralca.get(Igralec.W);
					VrstaIgralca crni = platno.vodja.vrstaIgralca.get(Igralec.B);
					//Vodja vodja = new Vodja();
					//platno.nastaviIgro(vodja);
					//platno.vodja.igra = null;
					//platno.vodja.clovekNaVrsti = false;
					//status.setText("Izberite igro!");
					//platno.vodja.igramo();
					
					//platno.vodja = new Vodja();
					//platno.vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
					//platno.vodja.vrstaIgralca.put(Igralec.W, VrstaIgralca.C); 
					//platno.vodja.vrstaIgralca.put(Igralec.B, VrstaIgralca.C);
					//Vodja.igramoNovoIgro();
					//platno.zmagovalec = null;
					//platno.vodja.igramo();
					
					Vodja vodja = new Vodja();
					vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
					vodja.vrstaIgralca.put(Igralec.B, crni); 
					vodja.vrstaIgralca.put(Igralec.W, beli);
					platno.nastaviIgro(vodja);
					//Vodja.igramoNovoIgro();
					platno.zmagovalec = null;
					platno.vodja.igramo();
				}
			}
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
		} else if (e.getSource() == hitrostRacunalnika) {
			int hitrost = -1;
			try {
				hitrost = Integer.parseInt(JOptionPane.showInputDialog("Vnesite �eleno hitrost ra�unalnika v sekundah:"));
				if (hitrost < 0 || hitrost > 10) {
					status.setText("Hitrost ra�unalnika mora biti ve�ja od 0 in manj�a od 10.");
					hitrost = -1;
				}
			} catch (NumberFormatException e1) {
			status.setText("To pa ne bo �lo");
			}
			
			if (hitrost != -1) {
				Vodja.hitrost = hitrost;
			}
			
		} else if (e.getSource() == algoritem) {
//			TO DO
		} else if (e.getSource() == razveljavi){
			if (platno.vodja.igra != null) {
				Koordinati p = platno.vodja.igra.razveljavi();
				if (p != null) {
					platno.vodja.poteza = p;
					platno.zmagovalec = null;
					platno.vodja.igramo();
					osveziGUI();
				}
			}
		}
		platno.repaint();
	}
	
	public void osveziGUI() {
		if (platno.vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(platno.vodja.igra.stanje(platno.vodja.poteza)) {
			case NEODLOCENO: status.setText("Neodlo�eno!");
				break;
			case V_TEKU: 
				if (platno.vodja.igra.naPotezi == Igralec.W) {
					if (StringImeW == null) {
						status.setText("Na potezi je 1. igralec - " + platno.vodja.vrstaIgralca.get(platno.vodja.igra.naPotezi));
					} else {
						status.setText("Na potezi je " + StringImeW + 
						" - " + platno.vodja.vrstaIgralca.get(platno.vodja.igra.naPotezi));
					}
				}
				if (platno.vodja.igra.naPotezi == Igralec.B) {
					if (StringImeB == null) {
						status.setText("Na potezi je 2. igralec  - " + platno.vodja.vrstaIgralca.get(platno.vodja.igra.naPotezi));
					} else {
						status.setText("Na potezi je " + StringImeB + 
						" - " + platno.vodja.vrstaIgralca.get(platno.vodja.igra.naPotezi));
					}
				}
				break;
			case ZMAGA_B:
				if (StringImeB == null) {
					status.setText("Zmagal je 2. igralec - "  + 
							platno.vodja.vrstaIgralca.get(Igralec.B));
				} else status.setText("Zmagal je " + StringImeB + "!");
				break;
			case ZMAGA_W:
				if (StringImeB == null) {
					status.setText("Zmagal je 1. igralec - "  + 
							platno.vodja.vrstaIgralca.get(Igralec.W));
				} else status.setText("Zmagal je " + StringImeW + "!");
				break;
			}
		}
		platno.repaint();
	}
}
