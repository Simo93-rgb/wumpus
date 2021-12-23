package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import model.elementi.Agente;
import model.elementi.CuccioloDiWumpus;
import model.elementi.Superstite;
import model.elementi.Tesoro;
import model.elementi.Wumpus;

class TestMappa {

	private Mappa mappaPerTest;
	private Mappa mappaPerTestCaricata; 
	private Agente agentePerTest;
	private CuccioloDiWumpus [] cuccioliPerTest;
	private Superstite [] superstitiPerTest;
	private Tesoro [] tesoriPerTest;
	private Wumpus wumpusPerTest;	
	private String salvataggioWumpusTest;
	
	@BeforeEach
	void setUp() throws Exception {		
		this.salvataggioWumpusTest = "mappaPerTestElementiDellaMappa.txt";		
		this.mappaPerTest = new Mappa(); // mappa(10, 20, 3, 2, 2) -> Mappa(int latoMappa, int probabilitaVoragine, int numTesori, int numSuperstiti, int numCuccioli)
		this.mappaPerTestCaricata = Mappa.caricamento(this.salvataggioWumpusTest);
		this.agentePerTest = this.mappaPerTestCaricata.getAgente();
		this.cuccioliPerTest = this.mappaPerTestCaricata.getCuccioli();
		this.superstitiPerTest = this.mappaPerTestCaricata.getSuperstiti();
		this.tesoriPerTest = this.mappaPerTestCaricata.getTesori();
		this.wumpusPerTest = this.mappaPerTestCaricata.getWumpus();		
	}

	@Test
	void testGetLatoMappa() {
		Assertions.assertEquals(Mappa.DEFAULT_LATO_MAPPA, this.mappaPerTest.getLatoMappa());
	}
	
	@ParameterizedTest
	@CsvSource({"10, 20, 3, 3, 0" , "10, 20, 3, 3, 6", "10, 20, 3, 0, 2","10, 20, 3, 6, 2", "10, 20, 0, 3, 2", "10, 20, 6, 3, 2", "10, 9, 3, 3, 2", "10, 41, 3, 3, 2", "4, 20, 3, 3, 2", "16, 20, 3, 3, 2"})
	void testCostruttoreCompletoMappaException(int latoMappa, int probabilitaVoragine, int numTesori, int numSuperstiti, int numCuccioli) {		
		try {
			new Mappa(latoMappa,probabilitaVoragine,numTesori, numSuperstiti, numCuccioli);
			Assertions.fail();
		}
		catch (IllegalArgumentException e) {
			Assertions.assertFalse(false);
		}
	}
	
	@Test
	void testCostruttoreCompletoMappa() {		
		Vector<model.elementi.Elemento> elencoElementiMappa = this.mappaPerTest.getElencoElementiMappa();
		Object elementoTmp = null;
		Integer contatoreWumpus = 0;
		Integer contatoreTesori = 0;
		Integer contatoreSuperstiti = 0;
		Integer contatoreCuccioli = 0;
		Integer numVoragini = null;
		for(int i = 0; i < this.mappaPerTest.getLatoMappa(); i++)
			for(int j = 0; j < this.mappaPerTest.getLatoMappa(); j++) {
				elementoTmp = new Elemento(this.mappaPerTest.getLatoMappa(),i,j);
				int indice = elencoElementiMappa.indexOf(elementoTmp);
				
				if(indice != -1) {
					if(elementoTmp.equals(elencoElementiMappa.get(indice)) && elencoElementiMappa.get(indice) instanceof Wumpus) 
						contatoreWumpus++;
					
					if(elementoTmp.equals(elencoElementiMappa.get(indice)) && elencoElementiMappa.get(indice) instanceof Tesoro)
						contatoreTesori++;
					
					if(elementoTmp.equals(elencoElementiMappa.get(indice)) && elencoElementiMappa.get(indice) instanceof Superstite) 
						contatoreSuperstiti++;
					
					if(elementoTmp.equals(elencoElementiMappa.get(indice)) && elencoElementiMappa.get(indice) instanceof CuccioloDiWumpus)
						contatoreCuccioli++;
				}					
			}		
		Assertions.assertEquals(contatoreWumpus, 1);
		Assertions.assertEquals(contatoreTesori, Mappa.DEFAULT_NUM_TESORI);
		Assertions.assertEquals(contatoreSuperstiti, Mappa.DEFAULT_NUM_SUPERSTITI);
		Assertions.assertEquals(contatoreCuccioli, Mappa.DEFAULT_NUM_CUCCIOLI);
		numVoragini = elencoElementiMappa.size()-contatoreWumpus-contatoreTesori-contatoreSuperstiti-contatoreCuccioli;
		Assertions.assertEquals(numVoragini, this.mappaPerTest.getNumeroVoragini());
	}

	@Test
	/**
	 * Controllo che i valori nel file "salvataggioWumpusTest.txt" siano congruenti con quelli caricati dal metodo test.Mappa.caricamento("salvataggioWumpusTest.txt")
	 */
	void testCostruttoreDaFile0() {
		Integer numCuccioli = null;
		Integer numSuperstiti = null;
		Integer numTesori = null;
		
		try (BufferedReader bufferedReaderPerLetturaValori = new BufferedReader(new FileReader(new File(this.salvataggioWumpusTest)))) {
			String prossimaLinea = null;
			String[] contenutoLinea = null;
			
			for(int i = 0; i <= 4; i++) 
				prossimaLinea = bufferedReaderPerLetturaValori.readLine();
			contenutoLinea = prossimaLinea.split(":");
			numTesori = Integer.parseInt(contenutoLinea[1].trim());
			
			prossimaLinea = bufferedReaderPerLetturaValori.readLine();
			contenutoLinea = prossimaLinea.split(":");
			numSuperstiti = Integer.parseInt(contenutoLinea[1].trim());
			
			prossimaLinea = bufferedReaderPerLetturaValori.readLine();
			contenutoLinea = prossimaLinea.split(":");
			numCuccioli = Integer.parseInt(contenutoLinea[1].trim());
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assertions.assertNotNull(this.agentePerTest);
		Assertions.assertEquals(numCuccioli, this.cuccioliPerTest.length);
		Assertions.assertEquals(numSuperstiti, this.superstitiPerTest.length);
		Assertions.assertEquals(numTesori, this.tesoriPerTest.length);
		Assertions.assertNotNull(this.wumpusPerTest);
	}
	
	@Test
	/**
	 * Sapendo che i valori letti nel file sono congruenti, come da metodo testCostruttoreDaFile0, controllo che siano coerenti tutti i valori caratterizzanti dei personaggi
	 */
	void testCostruttoreDaFile1() {				
		Mappa mappaPerTestCostruttore = null;
		
		try {
			mappaPerTestCostruttore = new Mappa(new File(this.salvataggioWumpusTest));			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Assertions.assertEquals(mappaPerTestCostruttore.getAgente(), this.agentePerTest);
		
		for(int i = 0; i < this.cuccioliPerTest.length; i++)
			Assertions.assertEquals(mappaPerTestCostruttore.getCuccioli()[i], this.cuccioliPerTest[i]);
		
		for(int i = 0; i < this.superstitiPerTest.length; i++)
			Assertions.assertEquals(mappaPerTestCostruttore.getSuperstiti()[i], this.superstitiPerTest[i]);
		
		for(int i = 0; i < this.tesoriPerTest.length; i++)
			Assertions.assertEquals(mappaPerTestCostruttore.getTesori()[i], this.tesoriPerTest[i]);
		
		Assertions.assertEquals(mappaPerTestCostruttore.getWumpus(), this.wumpusPerTest);			
	}
	
	@Test
	void testAggiungiAllaMappa() {
		Elemento elementoDaAggiungereValido = new Elemento(this.mappaPerTestCaricata.getLatoMappa(),3,3);
		Elemento elementoDaAggiungereNonValido = new Elemento(this.mappaPerTestCaricata.getLatoMappa(),2,2); //qui c'e' il wumpus
		Integer dimensioneElencoElementiMappaIniziale = this.mappaPerTestCaricata.getElencoElementiMappa().size();
		
		Assertions.assertFalse(this.mappaPerTestCaricata.aggiungiAllaMappa(elementoDaAggiungereNonValido));
		Assertions.assertEquals(dimensioneElencoElementiMappaIniziale, this.mappaPerTestCaricata.getElencoElementiMappa().size());
		
		Assertions.assertTrue(this.mappaPerTestCaricata.aggiungiAllaMappa(elementoDaAggiungereValido));
		Assertions.assertEquals(this.mappaPerTestCaricata.getElencoElementiMappa().get(this.mappaPerTestCaricata.getElencoElementiMappa().size()-1), elementoDaAggiungereValido);
		Assertions.assertNotEquals(dimensioneElencoElementiMappaIniziale, this.mappaPerTestCaricata.getElencoElementiMappa().size());		
	}
	
	@Test
	void testContenutoCasella() {
		Elemento elementoNonPresente = new Elemento(this.mappaPerTestCaricata.getLatoMappa(),3,3);
		Elemento elementoPresente = new Elemento(this.mappaPerTestCaricata.getLatoMappa(),2,2); //qui c'e' il wumpus
		
		Assertions.assertEquals(this.mappaPerTestCaricata.contenutoCasella(elementoPresente), elementoPresente);
		Assertions.assertNotEquals(this.mappaPerTestCaricata.contenutoCasella(elementoNonPresente), elementoPresente);
		Assertions.assertNull(this.mappaPerTestCaricata.contenutoCasella(elementoNonPresente));
	}
	
	@Test
	void testControllaBrezza() {
		Elemento brezzaPresente20 = new Elemento(this.mappaPerTestCaricata.getLatoMappa(), 2, 0);
		Elemento brezzaPresente31 = new Elemento(this.mappaPerTestCaricata.getLatoMappa(), 3, 1);
		Elemento brezzaPresente40 = new Elemento(this.mappaPerTestCaricata.getLatoMappa(), 4, 0);
		Elemento brezzaNonPresente = new Elemento(this.mappaPerTestCaricata.getLatoMappa(), 0, 3);
		
		Assertions.assertTrue(this.mappaPerTestCaricata.controllaBrezza(brezzaPresente20));
		Assertions.assertTrue(this.mappaPerTestCaricata.controllaBrezza(brezzaPresente31));
		Assertions.assertTrue(this.mappaPerTestCaricata.controllaBrezza(brezzaPresente40));
		Assertions.assertFalse(this.mappaPerTestCaricata.controllaBrezza(brezzaNonPresente));
	}
	
	@Test
	void testControllaPuzza() {
		Elemento puzzaPresente12 = new Elemento(this.mappaPerTestCaricata.getLatoMappa(), 1, 2);
		Elemento puzzaPresente21 = new Elemento(this.mappaPerTestCaricata.getLatoMappa(), 2, 1);
		Elemento puzzaPresente23 = new Elemento(this.mappaPerTestCaricata.getLatoMappa(), 2, 3);
		Elemento puzzaPresente32 = new Elemento(this.mappaPerTestCaricata.getLatoMappa(), 3, 2);
		Elemento puzzaNonPresente = new Elemento(this.mappaPerTestCaricata.getLatoMappa(), 0, 3);
		
		Assertions.assertTrue(this.mappaPerTestCaricata.controllaPuzza(puzzaPresente12));
		Assertions.assertTrue(this.mappaPerTestCaricata.controllaPuzza(puzzaPresente21));
		Assertions.assertTrue(this.mappaPerTestCaricata.controllaPuzza(puzzaPresente23));
		Assertions.assertTrue(this.mappaPerTestCaricata.controllaPuzza(puzzaPresente32));
		Assertions.assertFalse(this.mappaPerTestCaricata.controllaPuzza(puzzaNonPresente));
	}
	
	@Test
	void testSalvataggio() {		
		try {
			this.mappaPerTestCaricata.salvataggio("mappaPerTestSalvataggioDiTestMappa.txt");
			BufferedReader bufferedReaderWumpus = new BufferedReader(new FileReader(new File("mappaPerTestSalvataggioDiTestMappa.txt"))); 
			BufferedReader bufferedReaderWumpusExpected = new BufferedReader(new FileReader(new File(this.salvataggioWumpusTest)));	
			Scanner wumpusScanner = new Scanner(bufferedReaderWumpus);
			Scanner wumpusTestScanner = new Scanner(bufferedReaderWumpusExpected);
			LinkedList<String> wumpus = new LinkedList<String>();
			LinkedList<String> wumpusExpected = new LinkedList<String>();	
			
			while(wumpusScanner.hasNextLine()) 	
				wumpus.add(wumpusScanner.nextLine());
			while(wumpusTestScanner.hasNextLine()) 		
				wumpusExpected.add(wumpusTestScanner.nextLine());
			
			/* Aggiungo le stringhe per le caselle scoperte che non sono gestite nel metodo salvataggio di Mappa */			
		    for(int i = wumpus.size(); i < wumpusExpected.size(); i++) {
		    	wumpus.add(wumpusExpected.get(i));
		    }		    
			Assertions.assertEquals(wumpus, wumpusExpected);		
		}
		catch(IOException e) {
			e.printStackTrace();
		}			
	}
}