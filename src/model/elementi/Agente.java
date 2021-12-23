package model.elementi;

import model.gioco.Direzioni;

/**
 * Classe concreta che estende PersonaggioMondoDelWumpus, che permette la creazione e gestione
 * dell'Agente in una partita del gioco del Wumpus.
 * L'Agente viene mosso dal giocatore.
 * 
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class Agente extends PersonaggioMondoDelWumpus {

	private int numFrecce;
	private int punteggio;
	
	public static final String DEFAULT_NOME = "Link";

	
	/**
	 * Costruttore di Agente.
	 * Genera un nuovo Agente in una casella a caso nella mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 */
	public Agente(int latoMappa) throws IllegalArgumentException {
		this(latoMappa, Agente.DEFAULT_NOME);
	}
	
	/**
	 * Costruttore di Agente.
	 * Genera un nuovo Agente in una casella a caso nella mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param nome Nome da attribuire all'Agente.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 */
	public Agente(int latoMappa, String nome) throws IllegalArgumentException {
		super(latoMappa, nome);
		this.numFrecce = 1;
		this.punteggio = 0;
	}

	/**
	 * Costruttore di Agente.
	 * Genera un nuovo Agente verificando che la posizione scelta sia entro i confini della mappa.
	 * 
	 * @param riga Indice della riga in cui si vuole collocare l'Agente.
	 * @param colonna Indice della colonna in cui si vuole collocare l'Agente.
	 * @param latoMappa Dimensione del lato della mappa di gioco.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public Agente(int latoMappa, int riga, int colonna) throws IllegalArgumentException, IndexOutOfBoundsException {
		this(latoMappa, riga, colonna, Agente.DEFAULT_NOME);
	}

	/**
	 * Costruttore di Agente.
	 * Genera un nuovo Agente verificando che la posizione scelta sia entro i confini della mappa.
	 * 
	 * @param riga Indice della riga in cui si vuole collocare l'Agente.
	 * @param colonna Indice della colonna in cui si vuole collocare l'Agente.
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param nome Nome da attribuire all'Agente.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public Agente(int latoMappa, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
		super(latoMappa, riga, colonna, nome);
		this.numFrecce = 1;
		this.punteggio = 0;
	}
	
	/**
	 * Costruttore di Agente.
	 * Genera un nuovo Agente verificando che la posizione scelta sia entro i confini della mappa.
	 * Viene utilizzato nel caricamento di una partita. 
	 * 
	 * @param riga Indice della riga in cui si vuole collocare l'Agente.
	 * @param colonna Indice della colonna in cui si vuole collocare l'Agente.
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param nome Nome da attribuire all'Agente.
	 * @param inGioco Indica se l'Agente e' in gioco.
	 * @param numFrecce Numero di frecce dell'Agente.
	 * @param punteggio Punteggio dell'Agente.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public Agente(int latoMappa, int riga, int colonna, String nome, boolean inGioco, int numFrecce, int punteggio) throws IllegalArgumentException, IndexOutOfBoundsException {
		super(latoMappa, riga, colonna, nome);
		this.inGioco = inGioco;
		this.numFrecce = numFrecce;
		this.punteggio = punteggio;
	}
	

	/**
	 * Metodo che restituisce il numero di frecce possedute dall'Agente.
	 * 
	 * @return numero di frecce dell'Agente.
	 */
	public int getNumFrecce() {
		return this.numFrecce;
	}
	
	/**
	 * Metodo che restituisce il punteggio dell'Agente.
	 * 
	 * @return punteggio dell'Agente.
	 */
	public int getPunteggio() {
		return this.punteggio;
	}

	/**
	 * Metodo per lo spostamento dell'agente sulla mappa e stampa dei relativi messaggi a console.
	 * 
	 * @param direzioneSpostamento La direzione in cui si vuole che lo spostamento avvenga
	 * @return <em>true</em> se lo spostamento va a buon fine, <em>false</em> altrimenti.
	 */
	@Override
	public boolean sposta(Direzioni direzioneSpostamento) {
		return super.sposta(direzioneSpostamento);
	}
	
	/**
	 * Metodo per il lancio di una freccia.
	 * 
	 * @param direzione La direzione in cui si vuole scoccare la freccia.
	 * @return Un Bersaglio che rappresenta la casella colpita.
	 */
	public Elemento scoccaFreccia(Direzioni direzione) throws IndexOutOfBoundsException {
		if(this.getNumFrecce() <= 0) {
			return null;
		}

		this.variaFrecce(-1);
		this.supportoEventi.firePropertyChange("freccia", null, direzione.name());
		
		// Restituisce un'istanza di una classe anonima con tipo statico Elemento che rappresenta
		// la casella in cui l'agente ha scagliato la freccia, o lancia una IndexOutOfBoundsException
		// se le coordinate passate eccedono i limiti della mappa (il lancio dell'eccezione viene
		// effettuato direttamente dal costruttore di Elemento).
		// A livello superiore si potra' catturare la IndexOutOfBoundsException e gestire nel modo
		// che si riterra' opportuno il fatto che la freccia sia stata scagliata fuori dalla mappa.
		// Non e' necessario definire nulla in questa classe anonima, dato che viene usato semplicemente
		// il costuttore di Elemento con firma Elemento(int, int, int).
		return new Elemento(this.getLatoScacchiera(), this.getRiga() + direzione.getSpostamentoRiga(), this.getColonna() + direzione.getSpostamentoColonna()) {};
	}
	
	/**
	 * Metodo che modifica il punteggio dell'Agente di un determinato valore.
	 * 
	 * @param variazione Variazione del punteggio dell'Agente.
	 */
	protected void variaPunteggio(int variazione) {
		int oldPunteggio = this.getPunteggio();
		this.punteggio += variazione;
		this.supportoEventi.firePropertyChange("punteggio", oldPunteggio, this.getPunteggio());
	}
	
	/**
	 * Metodo che modifica il numero di frecce dell'Agente.
	 * 
	 * @param variazione Variazione del numero di frecce dell'Agente.
	 */
	protected void variaFrecce(int variazione) {
		int oldNumFrecce = this.getNumFrecce();
		this.numFrecce += variazione;
		this.supportoEventi.firePropertyChange("numFrecce", oldNumFrecce, this.getNumFrecce());
	}
	
	/**
	 * Metodo che rimuove l'Agente dal gioco a causa del Wumpus.
	 * Comporta la perdita della partita.
	 * 
	 * @return <em>true</em> se l'eliminazione va a buon fine, <em>false</em> altrimenti.
	 */
	public boolean eliminaAgenteWumpus() {
		boolean oldValue = this.getInGioco();
		boolean valoreDiRitorno = this.eliminaElemento();
		this.supportoEventi.firePropertyChange("uccisoDaWumpus", oldValue, this.getInGioco());
		
		return valoreDiRitorno;
	}
	
	/**
	 * Metodo che rimuove l'Agente dal gioco a causa di una voragine.
	 * Comporta la perdita della partita.
	 * 
	 * @return <em>true</em> se l'eliminazione va a buon fine, <em>false</em> altrimenti.
	 	*/
	public boolean eliminaAgenteVoragine() {
		boolean oldValue = this.getInGioco();
		boolean valoreDiRitorno = this.eliminaElemento();
		this.supportoEventi.firePropertyChange("uccisoDaVoragine", oldValue, this.getInGioco());
		
		return valoreDiRitorno;
	}	
}