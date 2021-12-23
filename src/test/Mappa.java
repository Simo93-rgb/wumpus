package test;

import java.io.File;
import java.io.IOException;

class Mappa extends model.gioco.Mappa {
	
	Mappa(){
		super();
	}
	
	Mappa(int latoMappa, int probabilitaVoragine, int numTesori, int numSuperstiti, int numCuccioli){
		super(latoMappa, probabilitaVoragine, numTesori, numSuperstiti, numCuccioli);
	}

	Mappa(File f) throws IllegalArgumentException, IOException {
		super(f);  
	}
	
	static Mappa caricamento(String nomeFile) throws IllegalArgumentException, IOException {
		return new Mappa(new File(nomeFile));
	}
}