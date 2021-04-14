
import java.io.IOException;

public class Gomoku {
	
	public static void main(String[] args) throws IOException {
		Vodja vodja = new Vodja();
		//vodja.igramo();
			
		Okno okno = new Okno();
		okno.pack();
		okno.setVisible(true);
		okno.platno.nastaviIgro(vodja);
		
		vodja.igramo();
		}
	}

