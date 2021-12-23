package test;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.elementi.Agente;
import model.elementi.CuccioloDiWumpus;
import model.elementi.Wumpus;
import model.gioco.Partita;

class TestPartita {
	
	Partita partitaPerTestDefaults;
	Partita partitaPerTestDaMappa;		
	Thread  attesaPerTest;
	Mappa   mappaPerTest;
	int     latoMappa;
	int     probabilitaVoragine;
	int     numTesori;
	int     numSuperstiti;
	int     numCuccioli;
	
	private class Corribile implements Runnable {
		private Partita partitaPerTestTemporizzata;
		
		public Agente getAgente() {
			return this.partitaPerTestTemporizzata.getAgente();			
		}
		
		@Override
		public void run() {				
			try {
				this.partitaPerTestTemporizzata = Partita.avviaPartita(true, new test.Mappa(new File("mappaPerTestQuasiVuota.txt")));
			} 
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {	
				e.printStackTrace();
			}			
		}
	}

	@BeforeEach
	void setUp() {
		this.latoMappa = 10;
		this.probabilitaVoragine = 20;
		this.numTesori = 3;
		this.numSuperstiti = 2;
		this.numCuccioli = 2;
	}
	
	@SuppressWarnings("static-access")
	@Test
	void testCostruttoreDaMappa() {	
		this.mappaPerTest = new Mappa(10, 20, 3, 2, 2);
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		Assertions.assertEquals(this.mappaPerTest.getLatoMappa(), this.partitaPerTestDaMappa.getMappaDiGioco().getLatoMappa());
		Assertions.assertEquals(this.mappaPerTest.DEFAULT_PROBABILITA_VORAGINE, this.partitaPerTestDaMappa.getMappaDiGioco().DEFAULT_PROBABILITA_VORAGINE);
		Assertions.assertEquals(this.mappaPerTest.DEFAULT_NUM_CUCCIOLI, this.partitaPerTestDaMappa.getMappaDiGioco().DEFAULT_NUM_CUCCIOLI);
		Assertions.assertEquals(this.mappaPerTest.DEFAULT_NUM_SUPERSTITI, this.partitaPerTestDaMappa.getMappaDiGioco().DEFAULT_NUM_SUPERSTITI);
		Assertions.assertEquals(this.mappaPerTest.getCuccioli(), this.partitaPerTestDaMappa.getMappaDiGioco().getCuccioli());
	}
	
	@Test
	void testCostruttoreDefaults() {	
		this.partitaPerTestDefaults = Partita.avviaPartita(); // mappa(10, 20, 3, 2, 2) -> Mappa(int latoMappa, int probabilitaVoragine, int numTesori, int numSuperstiti, int numCuccioli)
		Assertions.assertEquals(this.latoMappa, this.partitaPerTestDefaults.getMappaDiGioco().getLatoMappa());
		Assertions.assertEquals(this.probabilitaVoragine, Mappa.DEFAULT_PROBABILITA_VORAGINE);
		Assertions.assertEquals(this.numTesori, Mappa.DEFAULT_NUM_TESORI);
		Assertions.assertEquals(this.numSuperstiti, Mappa.DEFAULT_NUM_SUPERSTITI);
		Assertions.assertEquals(this.numCuccioli, this.partitaPerTestDefaults.getMappaDiGioco().getCuccioli().length);
	}
	
	@SuppressWarnings("static-access")
	@Test
	void testAvviaPartitaNoTemporizzazione() {
		this.partitaPerTestDefaults = Partita.avviaPartita();
		Partita partitaLocale5 = Partita.avviaPartita(false, this.latoMappa, this.probabilitaVoragine, this.numTesori, this.numSuperstiti, this.numCuccioli);
		Assertions.assertEquals(Mappa.DEFAULT_LATO_MAPPA, partitaLocale5.getMappaDiGioco().getLatoMappa());
		Assertions.assertEquals(this.probabilitaVoragine, partitaLocale5.getMappaDiGioco().DEFAULT_PROBABILITA_VORAGINE);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_TESORI, partitaLocale5.getMappaDiGioco().getTesori().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_SUPERSTITI, partitaLocale5.getMappaDiGioco().getSuperstiti().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_CUCCIOLI, partitaLocale5.getMappaDiGioco().getCuccioli().length);
		
		Partita partitaLocale4 = Partita.avviaPartita(false, this.latoMappa, this.probabilitaVoragine, this.numTesori, this.numSuperstiti);
		Assertions.assertEquals(Mappa.DEFAULT_LATO_MAPPA, partitaLocale4.getMappaDiGioco().getLatoMappa());
		Assertions.assertEquals(this.probabilitaVoragine, partitaLocale4.getMappaDiGioco().DEFAULT_PROBABILITA_VORAGINE);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_TESORI, partitaLocale4.getMappaDiGioco().getTesori().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_SUPERSTITI, partitaLocale4.getMappaDiGioco().getSuperstiti().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_CUCCIOLI, partitaLocale4.getMappaDiGioco().getCuccioli().length);
		
		Partita partitaLocale3 = Partita.avviaPartita(false, this.latoMappa, this.probabilitaVoragine, this.numTesori);
		Assertions.assertEquals(Mappa.DEFAULT_LATO_MAPPA, partitaLocale3.getMappaDiGioco().getLatoMappa());
		Assertions.assertEquals(this.probabilitaVoragine, partitaLocale3.getMappaDiGioco().DEFAULT_PROBABILITA_VORAGINE);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_TESORI, partitaLocale3.getMappaDiGioco().getTesori().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_SUPERSTITI, partitaLocale3.getMappaDiGioco().getSuperstiti().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_CUCCIOLI, partitaLocale3.getMappaDiGioco().getCuccioli().length);
		
		Partita partitaLocale2 = Partita.avviaPartita(false, this.latoMappa, this.probabilitaVoragine);
		Assertions.assertEquals(Mappa.DEFAULT_LATO_MAPPA, partitaLocale2.getMappaDiGioco().getLatoMappa());
		Assertions.assertEquals(this.probabilitaVoragine, partitaLocale2.getMappaDiGioco().DEFAULT_PROBABILITA_VORAGINE);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_TESORI, partitaLocale2.getMappaDiGioco().getTesori().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_SUPERSTITI, partitaLocale2.getMappaDiGioco().getSuperstiti().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_CUCCIOLI, partitaLocale2.getMappaDiGioco().getCuccioli().length);
		
		Partita partitaLocale1 = Partita.avviaPartita(false, this.latoMappa);
		Assertions.assertEquals(Mappa.DEFAULT_LATO_MAPPA, partitaLocale1.getMappaDiGioco().getLatoMappa());
		Assertions.assertEquals(this.probabilitaVoragine, partitaLocale1.getMappaDiGioco().DEFAULT_PROBABILITA_VORAGINE);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_TESORI, partitaLocale1.getMappaDiGioco().getTesori().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_SUPERSTITI, partitaLocale1.getMappaDiGioco().getSuperstiti().length);
		Assertions.assertEquals(Mappa.DEFAULT_NUM_CUCCIOLI, partitaLocale1.getMappaDiGioco().getCuccioli().length);
	}
	
	@Test
	void testAvviaPartitaConTemporizzazione() throws InterruptedException {	
		this.partitaPerTestDefaults = Partita.avviaPartita(); // mappa(10, 20, 3, 2, 2) -> Mappa(int latoMappa, int probabilitaVoragine, int numTesori, int numSuperstiti, int numCuccioli)
		Corribile partita = new Corribile();
		this.attesaPerTest = new Thread(partita);
		this.attesaPerTest.setDaemon(true);
		this.attesaPerTest.start();
		this.attesaPerTest.join();
		model.elementi.Agente agenteLocale = partita.getAgente();
		Thread.sleep(8100);	
				
		Assertions.assertNotEquals(this.partitaPerTestDefaults.getAgente(), agenteLocale);
		this.attesaPerTest.interrupt();
	}
	
	@Test
	void testTurnoDiGiocoAgenteTrueWumpusTrue() throws IllegalArgumentException, IOException {
		/**
		 * Premessa: per come è costruita la mappa il cucciolo di Wumpus può andare solamente nella casella 
		 *           (4,1) ed essendo garantito lo spostamento siamo sicuri che andra'
		 *           nella casella opportuna.
		 */
		File f = new File("mappaPerTestPartitaWumpusTrueAgenteTrue.txt");		
		this.mappaPerTest = new test.Mappa(f);
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);			
		String descrizioneTurno = this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.SUD);
		Assertions.assertNotEquals(descrizioneTurno, "");	
	}
	
	@Test
	void testTurnoDiGiocoAgenteTrueWumpusFalse() throws IllegalArgumentException, IOException {
		File f = new File("mappaPerTestPartitaWumpusFalseAgenteTrue.txt");		
		this.mappaPerTest = new test.Mappa(f);
		Wumpus agenteLocale = new Wumpus(this.mappaPerTest.getLatoMappa(),2,2);
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		Assertions.assertEquals(this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.SUD), "");		
		Assertions.assertNotEquals(this.partitaPerTestDaMappa.getAgente(), agenteLocale);
	}
	
	@Test
	void testTurnoDiGiocoAgenteFalseWumpusTrue() throws IllegalArgumentException, IOException {
		File f = new File("mappaPerTestPartitaWumpusTrueAgenteFalse.txt");		
		this.mappaPerTest = new test.Mappa(f);
		Agente agenteLocale = new Agente(this.mappaPerTest.getLatoMappa(),0,1);
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		Assertions.assertEquals(this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.SUD), "");
		Assertions.assertNotEquals(this.partitaPerTestDaMappa.getAgente(), agenteLocale);
	}	
	
	@Test
	void testTurnoDiGiocoAgenteFalseWumpusFalse() throws IllegalArgumentException, IOException {
		File f = new File("mappaPerTestPartitaWumpusFalseAgenteFalse.txt");		
		this.mappaPerTest = new test.Mappa(f);
		Agente agenteLocale = new Agente(this.mappaPerTest.getLatoMappa(),0,1);
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		Assertions.assertEquals(this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.SUD), "");
		Assertions.assertNotEquals(this.partitaPerTestDaMappa.getAgente(), agenteLocale);
	}
	
	@Test
	void testTurnoDiGiocoSpostamentoSUD() throws IllegalArgumentException, IOException {
		/**
		 * Premessa: per come è costruita la mappa il cucciolo di Wumpus può andare solamente nella casella 
		 *           (4,1) ed essendo garantito lo spostamento siamo sicuri che andra'
		 *           nella casella opportuna. Quindi e' lecito l'inserimento manuale della strnga per il cucciolo di Wumpus.
		 */
		File f = new File("mappaPerTestPartitaSpostamentoSUD.txt");		
		this.mappaPerTest = new test.Mappa(f);
		Agente agenteLocale = new Agente(this.mappaPerTest.getLatoMappa(),1,0);
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		
		String descrizioneTurnoAtteso = "";		
		String descrizioneTurno = this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.SUD);
		Assertions.assertNotEquals(descrizioneTurno, "");	
		descrizioneTurnoAtteso += agenteLocale.getNome() + " prova a spostarsi a " + "sud" + ".";
		descrizioneTurnoAtteso += "\n" + agenteLocale.getNome() + " si sposta nella casella " + agenteLocale.getRiga() + ", " + agenteLocale.getColonna() + ".";
		descrizioneTurnoAtteso += """


				Il cucciolo di Wumpus cucciolo n. 1 si sposta nella casella 4, 1
				""";
		Assertions.assertEquals(descrizioneTurno, descrizioneTurnoAtteso);
		Assertions.assertEquals(this.partitaPerTestDaMappa.getAgente(), agenteLocale);
	}
	
	@Test
	void testTurnoDiGiocoSpostamentoNORD() throws IllegalArgumentException, IOException {
		/**
		 * Premessa: per come è costruita la mappa il cucciolo di Wumpus può andare solamente nella casella 
		 *           (4,1) ed essendo garantito lo spostamento siamo sicuri che andra'
		 *           nella casella opportuna. Quindi e' lecito l'inserimento manuale della strnga per il cucciolo di Wumpus.
		 */
		File f = new File("mappaPerTestPartitaSpostamentoNORD.txt");		
		this.mappaPerTest = new test.Mappa(f);
		Agente agenteLocale = new Agente(this.mappaPerTest.getLatoMappa(),0,0);
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		
		String descrizioneTurnoAtteso = "";		
		String descrizioneTurno = this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.NORD);
		Assertions.assertNotEquals(descrizioneTurno, "");	
		descrizioneTurnoAtteso += agenteLocale.getNome() + " prova a spostarsi a " + "nord" + ".";
		descrizioneTurnoAtteso += "\n" + agenteLocale.getNome() + " si sposta nella casella " + agenteLocale.getRiga() + ", " + agenteLocale.getColonna() + ".";
		descrizioneTurnoAtteso += """


				Il cucciolo di Wumpus cucciolo n. 1 si sposta nella casella 4, 1
				""";
		Assertions.assertEquals(descrizioneTurno, descrizioneTurnoAtteso);
		Assertions.assertEquals(this.partitaPerTestDaMappa.getAgente(), agenteLocale);
	}
	
	@Test
	void testTurnoDiGiocoSpostamentoOVEST() throws IllegalArgumentException, IOException {
		/**
		 * Premessa: per come è costruita la mappa il cucciolo di Wumpus può andare solamente nella casella 
		 *           (4,1) ed essendo garantito lo spostamento siamo sicuri che andra'
		 *           nella casella opportuna. Quindi e' lecito l'inserimento manuale della strnga per il cucciolo di Wumpus.
		 */
		File f = new File("mappaPerTestPartitaSpostamentoOVEST.txt");		
		this.mappaPerTest = new test.Mappa(f);
		Agente agenteLocale = new Agente(this.mappaPerTest.getLatoMappa(),0,0);
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		
		String descrizioneTurnoAtteso = "";		
		String descrizioneTurno = this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.OVEST);
		Assertions.assertNotEquals(descrizioneTurno, "");	
		descrizioneTurnoAtteso += agenteLocale.getNome() + " prova a spostarsi a " + "ovest" + ".";
		descrizioneTurnoAtteso += "\n" + agenteLocale.getNome() + " si sposta nella casella " + agenteLocale.getRiga() + ", " + agenteLocale.getColonna() + ".";
		descrizioneTurnoAtteso += """


				Il cucciolo di Wumpus cucciolo n. 1 si sposta nella casella 4, 1
				""";
		Assertions.assertEquals(descrizioneTurno, descrizioneTurnoAtteso);
		Assertions.assertEquals(this.partitaPerTestDaMappa.getAgente(), agenteLocale);
	}
	
	@Test
	void testTurnoDiGiocoSpostamentoEST() throws IllegalArgumentException, IOException {
		/**
		 * Premessa: per come è costruita la mappa il cucciolo di Wumpus può andare solamente nella casella 
		 *           (4,1) ed essendo garantito lo spostamento siamo sicuri che andra'
		 *           nella casella opportuna. Quindi e' lecito l'inserimento manuale della strnga per il cucciolo di Wumpus.
		 */
		File f = new File("mappaPerTestPartitaSpostamentoEST.txt");		
		this.mappaPerTest = new test.Mappa(f);
		Agente agenteLocale = new Agente(this.mappaPerTest.getLatoMappa(),0,1);
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		
		String descrizioneTurnoAtteso = "";		
		String descrizioneTurno = this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.EST);
		Assertions.assertNotEquals(descrizioneTurno, "");	
		descrizioneTurnoAtteso += agenteLocale.getNome() + " prova a spostarsi a " + "est" + ".";
		descrizioneTurnoAtteso += "\n" + agenteLocale.getNome() + " si sposta nella casella " + agenteLocale.getRiga() + ", " + agenteLocale.getColonna() + ".";
		descrizioneTurnoAtteso += """


				Il cucciolo di Wumpus cucciolo n. 1 si sposta nella casella 4, 1
				""";
		Assertions.assertEquals(descrizioneTurno, descrizioneTurnoAtteso);
		Assertions.assertEquals(this.partitaPerTestDaMappa.getAgente(), agenteLocale);
	}
	
	@Test
	void testTurnoDiGiocoCuccioli() throws IllegalArgumentException, IOException {
		File f = new File("mappaPerTestPartitaTurnoDiGioco.txt");		
		this.mappaPerTest = new test.Mappa(f);		
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		CuccioloDiWumpus [] cuccioliLocali = new CuccioloDiWumpus[this.partitaPerTestDaMappa.getCuccioli().length];
		for(int i = 0; i < this.partitaPerTestDaMappa.getCuccioli().length; i++) 
			cuccioliLocali[i] = new CuccioloDiWumpus(this.partitaPerTestDaMappa.getMappaDiGioco().getLatoMappa(), this.partitaPerTestDaMappa.getCuccioli()[i].getRiga(),this.partitaPerTestDaMappa.getCuccioli()[i].getColonna()) ;
		
		/*Eseguo qualche spostamento su e giu' di agente, in numero dispari, per muovere un po' i cuccioli*/
		for(int i = 0; i < 5; i++) 
			if(i%2==0) 
				Assertions.assertNotEquals(this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.SUD), "");			
			else 
				Assertions.assertNotEquals(this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.NORD), "");		
		
		Assertions.assertNotEquals(cuccioliLocali, this.partitaPerTestDaMappa.getCuccioli());
	}
	
	@Test
	void TestDescrizioneCasellaAgente() throws IllegalArgumentException, IOException {
		File f = new File("mappaPerTestPartitaDescrizioneCasellaAgente.txt");
		this.mappaPerTest = new test.Mappa(f);
		String turnoDiGiocoTest = "";
		String messaggioBrezza = "";
		String messaggioBrezzaExpected = "Posizione attuale di Link: 0, 3\nIn questa casella si sente una brezza: c'e' una voragine nelle vicinanze";
		String messaggioMisto = "";
		String MessaggioMistoExpected = """
				Posizione attuale di Link: 1, 2
				In questa casella si sente la puzza del Wumpus
				In questa casella si sente una brezza: c'e' una voragine nelle vicinanze""";
		String messaggioPuzza = "";
		String messaggioPuzzaExpected = "Posizione attuale di Link: 2, 1\nIn questa casella si sente la puzza del Wumpus";
				
		this.partitaPerTestDaMappa = Partita.avviaPartita(false, this.mappaPerTest);
		
		
		messaggioBrezza += this.partitaPerTestDaMappa.descrizioneCasellaAgente();		
		turnoDiGiocoTest = this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.OVEST);
		Assertions.assertNotEquals(turnoDiGiocoTest , "");
		turnoDiGiocoTest = this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.SUD);
		Assertions.assertNotEquals(turnoDiGiocoTest , "");
		messaggioMisto += this.partitaPerTestDaMappa.descrizioneCasellaAgente();
		turnoDiGiocoTest = this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.OVEST);
		Assertions.assertNotEquals(turnoDiGiocoTest , "");
		turnoDiGiocoTest = this.partitaPerTestDaMappa.turnoDiGioco("VAI", model.gioco.Direzioni.SUD);
		Assertions.assertNotEquals(turnoDiGiocoTest , "");
		messaggioPuzza += this.partitaPerTestDaMappa.descrizioneCasellaAgente();
		
		
		Assertions.assertEquals(messaggioBrezza, messaggioBrezzaExpected);
		Assertions.assertEquals(messaggioMisto, MessaggioMistoExpected);
		Assertions.assertEquals(messaggioPuzza, messaggioPuzzaExpected);
	}
}