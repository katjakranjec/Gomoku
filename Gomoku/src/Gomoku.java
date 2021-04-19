import gui.Okno;
import vodja.Vodja;

public class Gomoku {
	
//	public static void main(String[] args) throws IOException {
//		
//		ZacetnoOkno zacetno_okno = new ZacetnoOkno(700, 700);
//		zacetno_okno.pack();
//		zacetno_okno.setVisible(true);
//		
//		Vodja vodja = new Vodja();
//
//		if (zacetno_okno.nacin_igre == "3") {
//			vodja.s = "3";
//		
//			Okno okno = new Okno();
//			okno.pack();
//			okno.setVisible(true);
//			okno.platno.nastaviIgro(vodja);
//	
//			vodja.igramo();
//			zacetno_okno.setVisible(false);
//			}
//		}
	
	public static void main(String[] args){
		Okno glavno_okno = new Okno();
		glavno_okno.pack();
		glavno_okno.setVisible(true);
		Vodja.okno = glavno_okno;
	}
}
