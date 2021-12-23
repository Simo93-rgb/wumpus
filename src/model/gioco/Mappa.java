package model.gioco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import model.elementi.Agente;
import model.elementi.CuccioloDiWumpus;
import model.elementi.Elemento;
import model.elementi.PersonaggioMondoDelWumpus;
import model.elementi.Superstite;
import model.elementi.Tesoro;
import model.elementi.Wumpus;

/**
 * Classe che permette la creazione e la gestione di una mappa del gioco del Wumpus.
 * 
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class Mappa {
	private final int latoMappa;
	private Vector<Elemento> elencoElementiMappa;
	private final Agente agente;
	private Wumpus wumpus;
	private Tesoro[] tesori;
	private Superstite[] superstiti;
	private CuccioloDiWumpus[] cuccioli;
	
	
	/**
	 * Costanti che definiscono i valori di default per una partita
	 */
	public static final int DEFAULT_LATO_MAPPA = 10;
	public static final int DEFAULT_PROBABILITA_VORAGINE = 20;
	public static final int DEFAULT_NUM_TESORI = 3;
	public static final int DEFAULT_NUM_SUPERSTITI = 2;
	public static final int DEFAULT_NUM_CUCCIOLI = 2;
    
	/*
	 * Classe per la creazione e di Elementi di tipo Voragine per la Mappa del gioco del Wumpus.
	 * Si tratta di una classe membro non statica: non possono esistere oggetti  di tipo Voragine 
	 * indipendentemente da un oggetto di tipo Mappa.
	 * 
	 * @author Marcello_Mora_8808920
	 * @author Filiberto_Melis_20035059
	 * @author Simone_Garau_20005068
	 */
	public class Voragine extends Elemento {

		/*
		 * Costruttore di Voragine che riceve come parametro la dimensione (in caselle) del lato della mappa,
		 * l'indice di riga e l'indice di colonna della casella in cui collocare la voragine; invoca il
		 * costruttore della superclasse Elemento.
		 * Il costruttore ha visibilita' private in quanto la collocazione delle voragini su di una mappa deve
		 * necessariamente passare attraverso il metodo statico collocaVoragini che le dispone sulla mappa ricevuta
		 * come parametro in base alla logica che ogni casella (tranne quella iniziale dell'Agente) possa essere una
		 * voragine con una certa probabilita' percentuale (passata anch'essa come parametro).
		 * 
	 	 * @param latoMappa Dimensione (in caselle) del lato della mappa in cui collocare la voragine.
		 * @param riga Indice della riga in cui si vuole collocare l'elemento.
		 * @param colonna Indice della colonna in cui si vuole collocare l'elemento.
		 * @throws IllegalArgumentException Se la dimensione del lato della scacchiera non e' strettamente
		 * 			maggiore di 0.
		 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
		 */
		private Voragine(int latoMappa, int riga, int colonna) throws IllegalArgumentException, IndexOutOfBoundsException {
			super(latoMappa, riga, colonna);
		}
	}
	
	/**
	 * Costruttore per Mappa.
	 * Genera una nuova Mappa di gioco con tutti valori di default.
	 */
	public Mappa() {
		this(Mappa.DEFAULT_LATO_MAPPA, Mappa.DEFAULT_PROBABILITA_VORAGINE, Mappa.DEFAULT_NUM_TESORI, Mappa.DEFAULT_NUM_SUPERSTITI, DEFAULT_NUM_CUCCIOLI);
	}
	
	/**
	 * Costruttore per Mappa.
	 * Genera una nuova Mappa di gioco di dimensione latoMappa x latoMappa.
	 * 
	 * @param latoMappa Dimensione in caselle dei lati della Mappa.
	 */
	public Mappa(int latoMappa) {
		this(latoMappa, Mappa.DEFAULT_PROBABILITA_VORAGINE, Mappa.DEFAULT_NUM_TESORI, Mappa.DEFAULT_NUM_SUPERSTITI, DEFAULT_NUM_CUCCIOLI);
	}
	
	/**
	 * Costruttore per Mappa.
	 * Genera una nuova Mappa di gioco di dimensione latoMappa x latoMappa in cui
	 * ogni casella ha una probabilita' del probabilitaVoragine% di contenere una Voragine.
	 * 
	 * @param latoMappa Dimensione in caselle dei lati della Mappa.
	 * @param probabilitaVoragine Probabilita' che una casella contenga una Voragine.
	 */
	public Mappa(int latoMappa, int probabilitaVoragine) {
		this(latoMappa, probabilitaVoragine, Mappa.DEFAULT_NUM_TESORI, Mappa.DEFAULT_NUM_SUPERSTITI, DEFAULT_NUM_CUCCIOLI);
	}
	
	/**
	 * Costruttore per Mappa.
	 * Genera una nuova Mappa di gioco di dimensione latoMappa x latoMappa in cui
	 * ogni casella ha una probabilita' del probabilitaVoragine% di contenere una Voragine.
	 * La mappaconterra' numTesori elementi Tesoro, numSuperstiti elementi Superstite e 
	 * numCuccioli elementi CuccioloDiWumpus.
	 * 
	 * @param latoMappa Dimensione in caselle dei lati della Mappa.
	 * @param probabilitaVoragine Probabilita' che una casella contenga una Voragine.
	 * @param numTesori Numero di elementi Tesoro presenti sulla Mappa.
	 * @param numSuperstiti Numero di elementi Superstite presenti sulla Mappa.
	 * @param numCuccioli Numero di elementi CuccioloDiWumpus presenti sulla Mappa.
	 * @throws IllegalArgumentException Se latoMappa e' minore di 5 o maggiore di 15 caselle;
	 * se probabilitaVoragine e' minore di 10 o maggiore di 40;
	 * se numTesori e' minore di 1 o maggiore di 5;
	 * se numSuperstiti e' minore di 1 o maggiore di 5;
	 * se numCuccioli e' minore di 1 o maggiore di 5;
	 */
	public Mappa(int latoMappa, int probabilitaVoragine, int numTesori, int numSuperstiti, int numCuccioli) throws IllegalArgumentException {
		if(latoMappa < 5 || latoMappa > 15) {
			throw new IllegalArgumentException("La dimensione del lato mappa deve essere compresa tra 5 e 15 caselle");
		}
		
		if(probabilitaVoragine < 10 || probabilitaVoragine > 40) {
			throw new IllegalArgumentException("La probabilita' che ciascuna casella sia una voragine deve essere compresa tra 10% e 40%");
		}
		
		if(numTesori < 1 || numTesori > 5) {
			throw new IllegalArgumentException("Il numero dei tesori deve essere compreso tra 1 e 5");
		}
		
		if(numSuperstiti < 1 || numSuperstiti > 5) {
			throw new IllegalArgumentException("Il numero dei superstiti deve essere compreso tra 1 e 5");
		}
		
		if(numCuccioli < 1 || numCuccioli > 5) {
			throw new IllegalArgumentException("Il numero dei cuccioli deve essere compreso tra 1 e 5");
		}
		
		this.latoMappa = latoMappa;
		this.agente = new Agente(latoMappa, 0, 0);
		
		// Ciclo do-while per la collocazione casuale di Voragini, Wumpus, tesori e superstiti
		// nella mappa.
		do {
			this.elencoElementiMappa = new Vector<Elemento>();
			this.tesori = new Tesoro[numTesori];
			this.superstiti = new Superstite[numSuperstiti];
			this.cuccioli = new CuccioloDiWumpus[numCuccioli];

			// Collocazione di un segnaposto per l'Agente per evitare che nella sua casella
			// possano venire posizionati altri elementi.
			// In questo caso non si controlla il valore di ritorno di aggiungiAllaMappa, dato
			// che l'agente e' il primo elemento a venire inserito, dunque l'inserimento andra'
			// necessariamente a buon fine.
			// L'agente verra' rimosso dalla mappa al termine della creazione della stessa, dato
			// che, per come viene impostata l'implementazione e la gestione delle collisioni,
			// non deve venire considerato un elemento della mappa.
			this.aggiungiAllaMappa(this.agente);
			
			// Collocazione delle voragini.
			this.collocaVoragini(probabilitaVoragine);
			
			// Collocazione del Wumpus.
			do {
				// Istanzia un oggetto di tipo Wumpus collocandolo in una casella a caso e
				// attribuendogli un valore (ovvero il numero di punti che puo' dare al
				// giocatore quando lo salva) casuale tra 300, 350, 400, 450 o 500...
				this.wumpus = new Wumpus(latoMappa, 300 + ((int) (Math.random()*5))*50);
			} while (!(this.aggiungiAllaMappa(this.getWumpus())));
			// ... e lo inserisce nella mappa, ma se la casella e' gia' occupata (cioe' se
			// il metodo aggiungiAllaMappa restituisce false) ripete il ciclo.

			// Collocazione dei tesori.
			// Richiede l'istanziazione di un numero di oggetti di tipo Tesoro pari al valore
			// di numTesori. I tesori hanno valori differenziati: il primo vale 100 punti, il
			// secondo 150, il terzo 200... e cosi' via.
			for(int i = 0; i < numTesori; i++) {
				do {
					// Istanzia un oggetto di tipo Tesoro collocandolo in una casella a caso...
					this.tesori[i] = new Tesoro(latoMappa, 100 + i*50);
				} while (!(this.aggiungiAllaMappa(this.getTesori()[i])));
				// ... e lo inserisce nella mappa, ma se la casella e' gia' occupata (cioe' se
				// il metodo aggiungiAllaMappa restituisce false) ripete il ciclo.
			}

			// Collocazione dei superstiti.
			// Richiede l'istanziazione di un numero di oggetti di tipo Superstite pari al valore
			// di numSuperstiti.
			for(int i = 0; i < numSuperstiti; i++) {
				do {
					// Istanzia un oggetto di Superstite collocandolo in una casella a caso e
					// attribuendogli un valore (ovvero il numero di punti che puo' dare al
					// giocatore quando lo salva) casuale tra 50, 100, 150, 200, 250 o 300...
					this.superstiti[i] = new Superstite(latoMappa, "superstite n. " + (i + 1), 50 + ((int) (Math.random()*6))*50);
				} while (!(this.aggiungiAllaMappa(this.getSuperstiti()[i])));
				// ... e lo inserisce nella mappa, ma se la casella e' gia' occupata (cioe' se
				// il metodo aggiungiAllaMappa restituisce false) ripete il ciclo.
			}
			
			// Collocazione dei cuccioli.
			// Richiede l'istanziazione di un numero di oggetti di tipo CuccioloDiWumpus pari al
			// valore di numCuccioli.
			for(int i = 0; i < numCuccioli; i++) {
				do {
					// Istanzia un oggetto di CuccioloDiWumpus collocandolo in una casella a caso...
					this.cuccioli[i] = new CuccioloDiWumpus(latoMappa, "cucciolo n. " + (i + 1));
				} while (!(this.aggiungiAllaMappa(this.getCuccioli()[i])));
				// ... e lo inserisce nella mappa, ma se la casella e' gia' occupata (cioe' se
				// il metodo aggiungiAllaMappa restituisce false) ripete il ciclo.
			}
			
			// Rimuove l'agente dalla mappa (l'agente non e' un elemento della mappa: lo si
			// posiziona nella mappa in fase di costruzione solo per evitare che altri elementi
			// possano venire messi nella casella iniziale dell'agente).
			// Se l'agente venisse lasciato nel Vector<Elementi> che rappresenta la mappa, non
			// funzionerebbe la rilevazione delle collisioni con gli altri elementi (dato che
			// l'agente e' il primo elemento a venire inserito nel Vector<Elementi> in fase di
			// creazione e dunque il primo elemento presente nel Vector<Elementi> che rappresenta
			// la mappa, se non si rimuove l'agente dal Vector<Elementi> ogni volta che si chiedera'
			// di restituire il contenuto della casella in cui si trova l'agente il primo Elemento
			// a venire trovato nel Vector<Elementi> equals all'agente sarebbe proprio l'agente
			// stesso).
			this.elencoElementiMappa.remove(this.getAgente());
		
		/*
		} while(!(this.controlloCammino(this.getWumpus())) ||
				!(this.controlloCamminoMultiplo(this.getTesori())) ||
				!(this.controlloCamminoMultiplo(this.getSuperstiti())) ||
				!(this.controlloCamminoMultiplo(this.getCuccioli())));
		*/
		
		} while(!(this.controlloValiditaMappa()));
		// Se, per la collocazione casuale dei vari elementi, si ottenesse una mappa "impossibile",
		// ovvero una mappa in cui non esistesse un cammino dalla casella si partenza dell'Agente
		// ai vari elementi da raggiungere per poter svolgere la partita (Wumpus, tesori, superstiti
		// e cuccioli di Wumpus) la mappa viene generata nuovamente.
	}
	
	/*
	 * Costruttore per Mappa che riceve un file e, se il file e' presente, e' accessibile ed e' nel 
	 * formato corretto, genera una mappa in base alle informazioni contenute nel file; altrimenti,
	 * in caso di problemi nella lettura del file, lancia una IOException, mentre nel caso in cui il
	 * file non sia nel formato corretto, lancia una IllegalArgumentException.
	 * E' il costruttore che viene utilizzato nel metodo caricamento.
	 * 
	 * @param f il file da cui leggere i dati per la mappa
	 */
	protected Mappa (File f) throws IllegalArgumentException, IOException {
		try (BufferedReader bufferedReaderPerCaricamento = new BufferedReader(new FileReader(f))) {
			String prossimaLinea;
			String[] contenutoLinea;
			
			this.elencoElementiMappa = new Vector<Elemento>();

			// Lettura del blocco dati "Caratteristiche della mappa"
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			if(!(prossimaLinea.equals("Caratteristiche della mappa"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}

			// Consuma la sottolineatura del titolo del blocco
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			
			// Lettura della dimensione del lato della mappa
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Dimensione del lato della mappa"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int latoCaricato = Integer.parseInt(contenutoLinea[1].trim());
			if(latoCaricato < 5 || latoCaricato > 15) {
				throw new IllegalArgumentException("La dimensione del lato mappa deve essere compresa tra 5 e 15 caselle");
			}
			this.latoMappa = latoCaricato;

			// Lettura del numero delle voragini
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Numero delle voragini"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int numeroVoragini = Integer.parseInt(contenutoLinea[1].trim());
			
			// Lettura del numeo dei tesori
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Numero dei tesori"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int numeroTesori = Integer.parseInt(contenutoLinea[1].trim());
			if(numeroTesori < 1 || numeroTesori > 5) {
				throw new IllegalArgumentException("Il numero dei tesori deve essere compreso tra 1 e 5");
			}
			
			// Lettura del numero dei superstiti
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Numero dei superstiti"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int numeroSuperstiti = Integer.parseInt(contenutoLinea[1].trim());
			if(numeroSuperstiti < 1 || numeroSuperstiti > 5) {
				throw new IllegalArgumentException("Il numero dei superstiti deve essere compreso tra 1 e 5");
			}

			// Lettura del numero dei cuccioli di Wumpus
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Numero dei cuccioli"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int numeroCuccioli = Integer.parseInt(contenutoLinea[1].trim());
			if(numeroCuccioli < 1 || numeroCuccioli > 5) {
				throw new IllegalArgumentException("Il numero dei cuccioli deve essere compreso tra 1 e 5");
			}
			
			this.tesori = new Tesoro[numeroTesori];
			this.superstiti = new Superstite[numeroSuperstiti];
			this.cuccioli = new CuccioloDiWumpus[numeroCuccioli];

			// Consuma la linea vuota dopo il blocco appena letto
			prossimaLinea = bufferedReaderPerCaricamento.readLine();

			// Lettura del blocco dati "Valori dell'agente"
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			if(!(prossimaLinea.equals("Valori dell'agente"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			
			// Consuma la sottolineatura del titolo del blocco
			prossimaLinea = bufferedReaderPerCaricamento.readLine();

			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di riga dell'agente"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int indRigaAgente = Integer.parseInt(contenutoLinea[1].trim());
			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di colonna dell'agente"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int indColonnaAgente = Integer.parseInt(contenutoLinea[1].trim());
			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Nome dell'agente"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			String nomeAgente = contenutoLinea[1].trim();
			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Stato in gioco dell'agente"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			boolean statoAgente = Boolean.parseBoolean(contenutoLinea[1].trim());
			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Numero di frecce dell'agente"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int numFrecceAgente = Integer.parseInt(contenutoLinea[1].trim());
			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Punteggio dell'agente"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int punteggioAgente = Integer.parseInt(contenutoLinea[1].trim());
			
			this.agente = new Agente(latoCaricato, indRigaAgente, indColonnaAgente, nomeAgente, statoAgente, numFrecceAgente, punteggioAgente);

			// Consuma la linea vuota dopo il blocco appena letto
			prossimaLinea = bufferedReaderPerCaricamento.readLine();

			// Lettura del blocco dati "Valori del Wumpus"
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			if(!(prossimaLinea.equals("Valori del Wumpus"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			
			// Consuma la sottolineatura del titolo del blocco			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();

			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di riga del Wumpus"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int indRigaWumpus = Integer.parseInt(contenutoLinea[1].trim());
			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di colonna del Wumpus"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int indColonnaWumpus = Integer.parseInt(contenutoLinea[1].trim());
			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Nome del Wumpus"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			String nomeWumpus = contenutoLinea[1].trim();
			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Valore del Wumpus"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			int valoreWumpus = Integer.parseInt(contenutoLinea[1].trim());
			
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			contenutoLinea = prossimaLinea.split(":");
			if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Stato in gioco del Wumpus"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			boolean statoWumpus = Boolean.parseBoolean(contenutoLinea[1].trim());
			
			this.wumpus = new Wumpus(latoCaricato, indRigaWumpus, indColonnaWumpus, nomeWumpus, valoreWumpus, statoWumpus);
			this.aggiungiAllaMappa(this.getWumpus());
			
			// Consuma la linea vuota dopo il blocco appena letto
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			
			// Lettura del blocco dati "Elenco delle voragini"
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			if(!(prossimaLinea.equals("Elenco delle voragini"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			
			// Consuma la sottolineatura del titolo del blocco
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			
			for(int i = 0; i < numeroVoragini; i++) {
				// Consuma la linea relativa al numero dell'elemento
				prossimaLinea = bufferedReaderPerCaricamento.readLine();

				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di riga"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int indRigaVoragine = Integer.parseInt(contenutoLinea[1].trim());
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di colonna"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int indColonnaVoragine = Integer.parseInt(contenutoLinea[1].trim());
				
				this.aggiungiAllaMappa(this.new Voragine(latoCaricato, indRigaVoragine, indColonnaVoragine));
			}
			
			// Consuma la linea vuota dopo il blocco appena letto
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			
			// Lettura del blocco dati "Elenco dei tesori"
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			if(!(prossimaLinea.equals("Elenco dei tesori"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			
			// Consuma la sottolineatura del titolo del blocco
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			
			for(int i = 0; i < numeroTesori; i++) {
				// Consuma la linea relativa al numero dell'elemento
				prossimaLinea = bufferedReaderPerCaricamento.readLine();

				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di riga"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int indRigaTesoro = Integer.parseInt(contenutoLinea[1].trim());
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di colonna"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int indColonnaTesoro = Integer.parseInt(contenutoLinea[1].trim());
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Valore"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int valoreTesoro = Integer.parseInt(contenutoLinea[1].trim());
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Stato in gioco"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				boolean statoTesoro = Boolean.parseBoolean(contenutoLinea[1].trim());

				this.tesori[i] = new Tesoro(latoCaricato, indRigaTesoro, indColonnaTesoro, valoreTesoro, statoTesoro);
				this.aggiungiAllaMappa(this.tesori[i]);
			}
			
			// Consuma la linea vuota dopo il blocco appena letto
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			
			// Lettura del blocco dati "Elenco dei superstiti"
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			if(!(prossimaLinea.equals("Elenco dei superstiti"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			
			// Consuma la sottolineatura del titolo del blocco
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			
			for(int i = 0; i < numeroSuperstiti; i++) {
				// Consuma la linea relativa al numero dell'elemento
				prossimaLinea = bufferedReaderPerCaricamento.readLine();

				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di riga"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int indRigaSuperstite = Integer.parseInt(contenutoLinea[1].trim());
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di colonna"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int indColonnaSuperstite = Integer.parseInt(contenutoLinea[1].trim());
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Nome"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				String nomeSuperstite = contenutoLinea[1].trim();
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Valore"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int valoreSuperstite = Integer.parseInt(contenutoLinea[1].trim());
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Stato in gioco"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				boolean statoSuperstite = Boolean.parseBoolean(contenutoLinea[1].trim());

				this.superstiti[i] = new Superstite(latoCaricato, indRigaSuperstite, indColonnaSuperstite, nomeSuperstite, valoreSuperstite, statoSuperstite);
				this.aggiungiAllaMappa(this.superstiti[i]);
			}
			
			// Consuma la linea vuota dopo il blocco appena letto
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			
			// Lettura del blocco dati "Elenco dei cuccioli di Wumpus"
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			if(!(prossimaLinea.equals("Elenco dei cuccioli di Wumpus"))) {
				throw new IllegalArgumentException("Formato file non valido");
			}
			
			// Consuma la sottolineatura del titolo del blocco
			prossimaLinea = bufferedReaderPerCaricamento.readLine();
			
			for(int i = 0; i < numeroCuccioli; i++) {
				// Consuma la linea relativa al numero dell'elemento
				prossimaLinea = bufferedReaderPerCaricamento.readLine();

				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di riga"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int indRigaCucciolo = Integer.parseInt(contenutoLinea[1].trim());
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Indice di colonna"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				int indColonnaCucciolo = Integer.parseInt(contenutoLinea[1].trim());
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Nome"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				String nomeCucciolo = contenutoLinea[1].trim();
				
				prossimaLinea = bufferedReaderPerCaricamento.readLine();
				contenutoLinea = prossimaLinea.split(":");
				if(contenutoLinea.length != 2 || !(contenutoLinea[0].equals("Stato in gioco"))) {
					throw new IllegalArgumentException("Formato file non valido");
				}
				boolean statoCucciolo = Boolean.parseBoolean(contenutoLinea[1].trim());

				this.cuccioli[i] = new CuccioloDiWumpus(latoCaricato, indRigaCucciolo, indColonnaCucciolo, nomeCucciolo, statoCucciolo);
				this.aggiungiAllaMappa(this.cuccioli[i]);
			}

		}
		catch (FileNotFoundException e) {
			// La cattura dell'eccezione rilancia l'eccezione al chiamante
			throw new FileNotFoundException("Il file " + f.getName() + " non esiste.");
		}

		catch (IOException e) {
			// La cattura dell'eccezione rilancia l'eccezione al chiamante
			throw new IOException("Caricamento non riuscito.");
		}
	}
	
	/*
	 * Metodo che colloca casualmente le voragini su di una Mappa.
	 * 
	 * @param probabilitaVoragine La probabilita' (in percentuale) per ogni casella di essere una voragine.
	 * @throws IllegalArgumentException Se la percentuale eccede l'intervallo [0, 100].
	 */
	private void collocaVoragini(int probabilitaVoragine) throws IllegalArgumentException {
		if(probabilitaVoragine < 0 || probabilitaVoragine > 100) {
			throw new IllegalArgumentException("La probabilita' richiesta non e' un valore valido"); 
		}

		// Cicli annidati che scorrono la mappa riga per riga...
		for(int i = 0; i < this.getLatoMappa(); i++) {
			// ... e ogni riga casella per casella.
			for(int j = 0; j < this.getLatoMappa(); j++) {
				
				// Esclusione della casella in cui e' posizionato l'agente ad inizio partita. 
				if(i == this.getAgente().getRiga() && j == this.getAgente().getColonna()) {
					continue;
				}
				
				// Ogni casella puo' essere una voragine con una probabilita' pari alla
				// percentuale probabilitaVoragine passata come parametro. Se Math.random()
				// genera un valore casuale inferiore a (double) probabilitaVoragine/100...
				// (il cast esplicito di probabilitaVoragine a double e' necessario dato che,
				// in caso contrario, la divisione tra l'intero probabilitaVoragine, che e'
				// necessariamente minore  di 100, e l'intero 100 darebbe sempre 0)
				else if(Math.random() < ((double) probabilitaVoragine/100)) {
					// ... si colloca la voragine nella casella corrente.
					this.aggiungiAllaMappa(new Voragine(Mappa.this.getLatoMappa(), i, j));
				}
			}
		}
	}

	/**
	 * Metodo che restituisce la dimensione in caselle dei lati della Mappa.
	 * 
	 * @return Dimensione del lato della Mappa in caselle.
	 */
	public int getLatoMappa() {
		return this.latoMappa;
	}
	
	/**
	 * Metodo che restituisce l'Agente presente sulla Mappa.
	 * 
	 * @return Agente sulla Mappa.
	 */
	public Agente getAgente() {
		return this.agente;
	}
	
	/**
	 * Metodo che restituisce il Vector<Elemento> che contiene tutti gli elementi sulla Mappa.
	 * 
	 * @return ElencoElementiMappa della Mappa.
	 */
	public Vector<Elemento> getElencoElementiMappa(){
		return this.elencoElementiMappa;
	}

	/**
	 * Metodo che restituisce il Wumpus presente sulla Mappa.
	 * 
	 * @return Wumpus sulla Mappa.
	 */
	public Wumpus getWumpus() {
		return this.wumpus;
	}
	
	/**
	 * Metodo che restituisce un array degli elementi Tesoro presenti sulla Mappa.
	 * 
	 * @return Array di elementi Tesoro presenti sulla Mappa.
	 */
	public Tesoro[] getTesori() {
		return this.tesori;
	}

	/**
	 * Metodo che restituisce un array degli elementi Superstite presenti sulla Mappa.
	 * 
	 * @return Array di elementi Superstite presenti sulla Mappa.
	 */
	public Superstite[] getSuperstiti() {
		return this.superstiti;
	}

	/**
	 * Metodo che restituisce un array degli elementi CuccioloDiWumpus presenti sulla Mappa.
	 * 
	 * @return Array di elementi CuccioloDiWumpus presenti sulla Mappa.
	 */
	public CuccioloDiWumpus[] getCuccioli() {
		return this.cuccioli;
	}


	/**
	 * Metodo che si occupa di aggiungere un Elemento alla Mappa.
	 * 
	 * @param daAggiungere Elemento da aggiungere.
	 * @return <em>true</em> se la casella della Mappa in cui si chiede di inserire l'Elemento e' libera
	 * 		e l'inserimento va a buon fine, <em>false</em> altrimenti.
	 */
	public boolean aggiungiAllaMappa(Elemento daAggiungere) {
		/* La Mappa e' rappresentata attravero un Vector<Elemento>; per controllare se una casella e' libera
		 * e' sufficiente verificare che nel Vector non ci sia gia' un Elemento uguale a quello che si sta
		 * chiedendo di inserire (la equals di Elemento considera uguali due oggetti di tipo Elemento se i
		 * loro campi latoScacchiera, riga, colonna e inGioco sono uguali, indipendentemente dalla sottoclasse
		 * di Elemento a cui appartengono).
		 */
		if(!(this.elencoElementiMappa.contains(daAggiungere))) {
			this.elencoElementiMappa.add(daAggiungere);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Metodo che restituisce il contenuto di una casella della mappa passata come argomento.
	 * 
	 * @param casellaDaControllare Elemento le cui coordinate si vuole verificare se corrispondano a qualcosa
	 * 		sulla mappa corrente.
	 * @return Elemento contenuto nella casella, o null se la casella e' vuota.
	 */
	public Elemento contenutoCasella(Elemento casellaDaControllare) {
		Elemento risultato = null;
		// Verifica se nel Vector<Elemento> che rappresenta il contenuto della mappa e' presente
		// un Elemento equals a quello passato come parametro; perche' l'Elemento sia considerato
		// equals devono coincidere i valori di latoScacchiera, riga, colonna e inGioco dei due
		// oggetti di tipo Elemento (si veda il codice della equals di Elemento). Se tale Elemento
		// � presente la indexOf ne restituisce l'indice, altrimenti restituisce -1.
		int indice = this.elencoElementiMappa.indexOf(casellaDaControllare);
		
		// Se l'indice restituito e' diverso da -1 significa che nel Vector<Elementi> e' presente un
		// Elemento equals a quello passato come parametro, quindi lo si fa riferire dalla variabile
		// che rappresenta il valore di ritorno del metodo; in caso contrario non cambia il valore null 
		// con cui "risultato" e' stato inizializzato e quindi in questo caso il metodo restituisce null.
		if(indice != -1) {
			risultato = this.elencoElementiMappa.get(indice);
		}
		
		return risultato;
	}
	
	// Il metodo privato controlloValiditaMappa permette di verificare se esista un
	// cammino che colleghi la casella di partenza dell'Agente con ognuno dei vari
	// elementi della mappa (ad eccezione delle voragini); viene usato per verificare
	// se la mappa generata casualmente sia giocabile, ovvero se esista un percorso
	// che colleghi la casella di partenza dell'agente a quella del Wumpus, a quelle
	// dei superstiti, a quelle dei tesori e a quelle dei cuccioli di Wumpus.
	// Il metodo genera:
	// - un PersonaggioMondoDelWumpus, che e' una copia dell'Agente; viene usato per
	//	effettuare gli spostamenti necessari a verificare i vari cammini;
	// - un Vector<Elemento> per memorizzare tutti gli elementi raggiunti durante la
	//	visita della mappa;
	// - un Vector<Elemento>, per tenere traccia delle caselle da cui gia' e' partita
	//	una chiamata ricorsiva verso le quattro caselle adiacenti;
	// quindi chiama il metodo ricorsivo che si occupa di effettuare il controllo vero
	// e proprio.
	// Al termine, si verifica se tutti gli Elementi presenti nel Vector<Elemento>
	// elencoElementiMappa (tranne le voragini) sono contenuti nel Vector<Elemento>
	// trovati: se si', significa che tutti gli elementi presenti sulla mappa sono
	// stati raggiunti durante la visita e il metodo restituisce true, altrimenti, al
	// primo elemento che non viene trovato nel Vector  trovati il metodo restituisce
	// false.
	private boolean controlloValiditaMappa() {
		// Viene creato un PersonaggioMondoDelWumpus "controfigura" copia dell'Agente di
		// gioco per testare i percorsi (si usa una classe anonima).
		PersonaggioMondoDelWumpus controfigura = new PersonaggioMondoDelWumpus(this.getAgente().getLatoScacchiera(), this.getAgente().getRiga(), this.getAgente().getColonna(), "Controfigura") {};
		// Vector in cui verranno collocati i vari elementi raggiunti durante i cammini.
		Vector<Elemento> trovati = new Vector<Elemento>();
		
		// Prima chiamata del metodo ricorsivo vero e proprio; al termine di tutte le
		// chiamate ricorsive il Vector trovati conterra' tutti gli elementi della mappa
		// raggiunti percorrendo tutti i possibili cammini che originano dalla casella
		// in cui si trova inizialmente l'agente.
		controlloCammini(controfigura, new Vector<Elemento>(), trovati);
		
		// Verifica che tutti gli elementi della mappa diversi dalle voragini siano contenuti
		// nel Vector trovati, ovvero che tutti gli elementi della mappa diversi dalle voragini
		// siano raggiungibili a partire dalla casella iniziale dell'Agente
		for(Elemento el : this.getElencoElementiMappa()) {
			if (!(el instanceof Voragine) && !(trovati.contains(el))) {
				return false;
			}
		}
		// Se il ciclo e' terminato, ovvero se tutti gli elementi presenti sulla mappa (eccetto
		// le voragini) sono presenti anche nel Vector trovati, si restituisce true.
		return true;
	}
	
	// Metodo privato ricorsivo che effettua il controllo dei cammini vero e proprio.
	private void controlloCammini(PersonaggioMondoDelWumpus controfigura, Vector<Elemento> caselleGiaRaggiunte, Vector<Elemento> elementiTrovati) {
		// Si aggiunge la casella corrente corrente al Vector delle caselle gia' raggiunte da una
		// chiamata ricorsiva per indicare che non si dovranno avviare da essa nuove chiamate
		// ricorsive quando verra' raggiunta in successivi percorsi.
		caselleGiaRaggiunte.add(controfigura);
		
		Elemento el = this.contenutoCasella(controfigura);

		// Se la casella corrente contiene un elemento lo si aggiunge al Vector degli elementi raggiunti.
		// A questo livello non si controlla che l'elemento non sia una voragine dato che, per come si
		// imposta l'algoritmo di visita delle caselle adiacenti, le caselle contenenti una voragine non
		// possono venire raggiunte (dato che sono bloccanti per il movimento dell'agente)
		if (el != null) {
			elementiTrovati.add(el);
		}
		
		// Poi si effettuano quattro chiamate ricorsive indicando per ciascuna come casella di
		// partenza una delle quattro caselle adiacenti a quella corrente, ma solo se:
		//	* le coordinate della casella adiacente sono valide, ovvero non escono dal tabellone; si
		//		ricorda che la sposta(Direzioni) di ElementoMobile, se la casella di destinazione e'
		//		valida, effettua lo spostamento e restituisce true, mentre in caso contrario non lo
		//		effettua e restituisce false; e' il risultato della valutazione dell'espressione:
		//		nuovaPosizione.sposta(direzione)
		//		se lo spostamento e' lecito, dopo questa chiamata nuovaPosizione contiene il riferimento
		//		alla cella adiacente da cui fare eventualmente partire la chiamata ricorsiva
		//	* la casella adiacente non e' gia' stata raggiunta in una precedente chiamata ricorsiva, 
		//		in modo da evitare loop infiniti; e' il risultato della valutazione dell'espressione:
		//		!(caselleGiaRaggiunte.contains(nuovaPosizione))
		//	* la casella adiacente e' libera o, se occupata, non contiene una voragine che impedisce
		//		 di accedervi; e' il risultato della valutazione dell'espressione:
		//		(this.contenutoCasella(nuovaPosizione) == null) || !(this.contenutoCasella(nuovaPosizione) instanceof Voragine)
		//		Si fa prima il controllo di null in modo da evitare di richiedere la classe di un valore
		//		nullo
		//
		// Costrutto for-each per effettuare le chiamate ricorsive nelle quattro direzioni.
		for(Direzioni direzione : Direzioni.values()) {
			// Si crea una copia della controfigura: dato che ogni chiamata ricorsiva dovra' venire
			// effettuata su una delle caselle adiacenti rispetto alla controfigura ricevuta dall'istanza
			// non si puo' "spostare" direttamente la controfigura, ma bisogna lavorare su di una sua
			// copia creata prima di ogni chiamata ricorsiva.
			PersonaggioMondoDelWumpus nuovaPosizione = new PersonaggioMondoDelWumpus(controfigura.getLatoScacchiera(), controfigura.getRiga(), controfigura.getColonna(), "Controfigura") {};
			
			if(nuovaPosizione.sposta(direzione) && !(caselleGiaRaggiunte.contains(nuovaPosizione)) && ((this.contenutoCasella(nuovaPosizione) == null) || !(this.contenutoCasella(nuovaPosizione) instanceof Voragine))) {
				controlloCammini(nuovaPosizione, caselleGiaRaggiunte, elementiTrovati);
			}
		}
	}
	
	
	/**
	 * Metodo che controlla se su una casella si avverte la brezza generata da una voragine.
	 * 
	 * @param casella Casella che si vuole controllare.
	 * @return <em>true</em> se la casella e' adiacente ad una Voragine, <em>false</em> altrimenti.
	 * @throws NullPointerException
	 */
	public boolean controllaBrezza(Elemento casella) throws NullPointerException {
		for(Direzioni direzione : Direzioni.values()) {
			PersonaggioMondoDelWumpus controfigura = new PersonaggioMondoDelWumpus(casella.getLatoScacchiera(), casella.getRiga(), casella.getColonna(), "Controfigura") {};
			if((controfigura.sposta(direzione)) && (this.contenutoCasella(controfigura) instanceof Voragine)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo che controlla se su una casella si sente la puzza del Wumpus.
	 * 
	 * @param casella Casella che si vuole controllare.
	 * @return <em>true</em> se la casella e' adiacente al Wumpus, <em>false</em> altrimenti.
	 * @throws NullPointerException
	 */
	public boolean controllaPuzza(Elemento casella) throws NullPointerException {
		for(Direzioni direzione : Direzioni.values()) {
			PersonaggioMondoDelWumpus controfigura = new PersonaggioMondoDelWumpus(casella.getLatoScacchiera(), casella.getRiga(), casella.getColonna(), "Controfigura") {};
			if((controfigura.sposta(direzione)) && (this.contenutoCasella(controfigura) instanceof Wumpus)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo che consente il salvataggio su file dello stato corrente della Mappa con nome predefinito "salvataggioWumpus.txt".
	 */
	public void salvataggio() {
		this.salvataggio("salvataggioWumpus.txt");		
	}
	
	/**
	 * Metodo che consente il salvataggio su file dello stato corrente della Mappa con nome del file personalizzato.
	 * 
	 * @param nomeSalvataggio stringa col nome che si vuole dare al file di salvataggio partita.
	 */
	public void salvataggio(String nomeSalvataggio) {
		int contatore;
		try (BufferedWriter salvataggio = new BufferedWriter(new FileWriter(new File(nomeSalvataggio)))) {
			salvataggio.write("Caratteristiche della mappa\n");
			salvataggio.write("===========================\n");
			salvataggio.write("Dimensione del lato della mappa: " + this.getLatoMappa() + "\n");
			salvataggio.write("Numero delle voragini: " + this.contaVoragini() + "\n");
			salvataggio.write("Numero dei tesori: " + this.getTesori().length + "\n");
			salvataggio.write("Numero dei superstiti: " + this.getSuperstiti().length + "\n");
			salvataggio.write("Numero dei cuccioli: " + this.getCuccioli().length + "\n");

			salvataggio.write("\nValori dell'agente\n");
			salvataggio.write("==================\n");
			salvataggio.write("Indice di riga dell'agente: " + this.getAgente().getRiga() + "\n");
			salvataggio.write("Indice di colonna dell'agente: " + this.getAgente().getColonna() + "\n");
			salvataggio.write("Nome dell'agente: " + this.getAgente().getNome() + "\n");
			salvataggio.write("Stato in gioco dell'agente: " + this.getAgente().getInGioco() + "\n");
			salvataggio.write("Numero di frecce dell'agente: " + this.getAgente().getNumFrecce() + "\n");
			salvataggio.write("Punteggio dell'agente: " + this.getAgente().getPunteggio() + "\n");

			salvataggio.write("\nValori del Wumpus\n");
			salvataggio.write("=================\n");
			salvataggio.write("Indice di riga del Wumpus: " + this.getWumpus().getRiga() + "\n");
			salvataggio.write("Indice di colonna del Wumpus: " + this.getWumpus().getColonna() + "\n");
			salvataggio.write("Nome del Wumpus: " + this.getWumpus().getNome() + "\n");
			salvataggio.write("Valore del Wumpus: " + this.getWumpus().getValore() + "\n");
			salvataggio.write("Stato in gioco del Wumpus: " + this.getWumpus().getInGioco() + "\n");

			salvataggio.write("\nElenco delle voragini\n");
			salvataggio.write("=====================\n");
			contatore = 0;
			for(Elemento el : this.elencoElementiMappa) {
				if(el instanceof Mappa.Voragine) {
					salvataggio.write("Voragine n. " + contatore + "\n");
					salvataggio.write("Indice di riga: " + el.getRiga() + "\n");
					salvataggio.write("Indice di colonna: " + el.getColonna() + "\n");
					contatore++;
				}
			}

			salvataggio.write("\nElenco dei tesori\n");
			salvataggio.write("=================\n");
			contatore = 0;
			for(Tesoro corrente : this.getTesori()) {
				salvataggio.write("Tesoro n. " + contatore + "\n");
				salvataggio.write("Indice di riga: " + corrente.getRiga() + "\n");
				salvataggio.write("Indice di colonna: " + corrente.getColonna() + "\n");
				salvataggio.write("Valore: " + corrente.getValore() + "\n");
				salvataggio.write("Stato in gioco: " + corrente.getInGioco() + "\n");
				contatore++;
			}

			salvataggio.write("\nElenco dei superstiti\n");
			salvataggio.write("=====================\n");
			contatore = 0;
			for(Superstite corrente : this.getSuperstiti()) {
				salvataggio.write("Superstite n. " + contatore + "\n");
				salvataggio.write("Indice di riga: " + corrente.getRiga() + "\n");
				salvataggio.write("Indice di colonna: " + corrente.getColonna() + "\n");
				salvataggio.write("Nome: " + corrente.getNome() + "\n");
				salvataggio.write("Valore: " + corrente.getValore() + "\n");
				salvataggio.write("Stato in gioco: " + corrente.getInGioco() + "\n");
				contatore++;
			}
			
			salvataggio.write("\nElenco dei cuccioli di Wumpus\n");
			salvataggio.write("=============================\n");
			contatore = 0;
			for(CuccioloDiWumpus corrente : this.getCuccioli()) {
				salvataggio.write("Cucciolo di Wumpus n. " + contatore + "\n");
				salvataggio.write("Indice di riga: " + corrente.getRiga() + "\n");
				salvataggio.write("Indice di colonna: " + corrente.getColonna() + "\n");
				salvataggio.write("Nome: " + corrente.getNome() + "\n");
				salvataggio.write("Stato in gioco: " + corrente.getInGioco() + "\n");
				contatore++;
			}
		}
		catch (IOException e) {
			System.err.println("Salvataggio non riuscito");
		}
	}
	
	/**
	 * Metodo che permette il caricamento da file di una Mappa salvata.
	 * 
	 * @return Mappa descritta dal file di salvataggio.
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static Mappa caricamento() throws IllegalArgumentException, IOException {
		return new Mappa(new File("salvataggioWumpus.txt"));
	}
	
	private int contaVoragini() {
		int numVoragini = 0;
		for(Elemento el : this.elencoElementiMappa) {
			if(el instanceof Mappa.Voragine) {
				numVoragini++;
			}
		}
		return numVoragini; 
	}
	
	public int getNumeroVoragini() {
		return this.contaVoragini();
	}
	
}