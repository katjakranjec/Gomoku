import gui.Okno;
import vodja.Vodja;

public class Gomoku {
	
	public static void main(String[] args) {
		
		Vodja vodja = new Vodja();
		//System.out.println("Mam novga vodjo");
		
		Okno okno = new Okno();
		okno.pack();
		okno.setVisible(true);
		//System.out.println("Mam en okn");
		
		//Vodja vodja = new Vodja();
		okno.platno.nastaviIgro(vodja);
		//vodja.okno.pack();
		//vodja.okno.setVisible(true);
		
		vodja.igramo();
		
		while (okno.platno.vodja.igra != null) {
			okno.osveziGUI();
		}
		
		//vodja.igramo();
	}
}
