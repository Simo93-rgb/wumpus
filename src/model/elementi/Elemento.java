package model.elementi;

/**
 * Classe astratta per la creazione e gestione di elementi di un qualsiasi gioco che si svolga
 * su di una scacchiera quadrata.
 * 
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public abstract class Elemento {
	/*
	 * Dimensione del lato della scacchiera su cui l'elemento viene posizionato.
	 * Campo di tipo final: la dimensione del lato della scacchiera in cui l'elemento
	 * viene posizionato non puo' cambiare nel corso di una partita.
	 * Viene utilizzata per gestire gia' a questo livello il controllo sul fatto che
	 * il posizionamento di un Elemento sia lecito o meno rispetto alla dimensione del
	 * lato della mappa.
	 */
	protected final int latoScacchiera;
	/*
	 * Indice di riga dell'elemento.
	 */
	protected int riga;
	/*
	 * Indice di colonna dell'elemento.
	 */
	protected int colonna;
	/*
	 * Stato dell'elemento (true -> l'elemento e' in gioco; false -> l'elemento non e' in gioco).
	 */
	protected boolean inGioco;
	
	/*
	 * Costruttore di Elemento.
	 * Istanzia un oggetto di tipo Elemento collocandolo in una casella a caso di una scacchiera quadrata
	 * la cui dimensione del lato (in caselle) viene passata al costruttore come parametro.
	 * Le coordinate casuali vengono generate attraverso l'espressione (int) (Math.random()*latoScacchiera)
	 * che restituisce un intero compreso tra 0 e (latoScacchiera - 1).
	 * La dimensione del lato della scacchiera deve essere un valore strettamente maggiore di 0.
	 * 
	 * @param latoScacchiera Dimensione (in caselle) del lato della scacchiera.
	 * @throws IllegalArgumentException Se la dimensione del lato della scacchiera non e' strettamente
	 * 			maggiore di 0.
	 */
	protected Elemento(int latoScacchiera) throws IllegalArgumentException {
		this(latoScacchiera, (int) (Math.random()*latoScacchiera), (int) (Math.random()*latoScacchiera));
	}
	
	/*
	 * Costruttore di Elemento.
	 * Istanzia un oggetto di tipo Elemento collocandolo in una casella di una scacchiera quadrata la cui
	 * dimensione del lato (in caselle) viene passata al costruttore come parametro, controllando che la 
	 * dimensione del lato sia un valore strettamente maggiore di 0 e che le coordinate siano valide rispetto
	 * alla dimensione del lato della scacchiera.
	 * 
	 * @param latoScacchiera Dimensione (in caselle) del lato della scacchiera.
	 * @param riga Indice della riga in cui si vuole collocare l'elemento.
	 * @param colonna Indice della colonna in cui si vuole collocare l'elemento.
 	 * @throws IllegalArgumentException Se la dimensione del lato della scacchiera non e' strettamente
	 * 			maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della scacchiera.
	 */
	protected Elemento(int latoScacchiera, int riga, int colonna) throws IllegalArgumentException, IndexOutOfBoundsException {
		if(latoScacchiera <= 0) {
			throw new IllegalArgumentException ("La dimensione del lato della scacchiera deve essere un valore positivo");
		}
		
		if(riga < 0 || riga >= latoScacchiera || colonna < 0 || colonna >= latoScacchiera) {
			throw new IndexOutOfBoundsException("Non e' possibile collocare l'elemento al di fuori della scacchiera");
		}

		this.latoScacchiera = latoScacchiera;
		this.riga = riga;
		this.colonna = colonna;
		this.inGioco = true;
	}

	/**
	 * Metodo che restituisce la dimensione del lato della scacchiera in cui si trova l'Elemento.
	 * 
	 * @return int che rappresenta la dimensione del lato della scacchiera in cui si trova l'Elemento.
	 */
	public int getLatoScacchiera() {
		return this.latoScacchiera;
	}
	
	/**
	 * Metodo che restituisce l'indice della riga della scacchiera in cui si trova l'Elemento.
	 * 
	 * @return int che rappresenta la riga della mappa in cui si trova l'Elemento.
	 */
	public int getRiga() {
		return this.riga;
	}

	/**
	 * Metodo che restituisce l'indice della colonna della scacchiera in cui si trova l'Elemento.
	 * 
	 * @return int che rappresenta la colonna della mappa in cui si trova l'Elemento.
	 */
	public int getColonna() {
		return this.colonna;
	}

	/**
	 * Metodo che restituisce un booleano che indica se l'Elemento è ancora in gioco o no.
	 * 
	 * @return <em>true</em> se l'Elemento e' ancora in gioco, <em>false</em> altrimenti.
	 */
	public boolean getInGioco() {
		return this.inGioco;
	}
	
	/**
	 * Metodo per confrontare un oggetto di tipo (statico) Elemento con un altro Object.
	 * Due oggetti di tipo Elemento vengono considerati uguali se sono uguali tutti i loro campi
	 * (dimensione del lato della mappa a cui appartengono, indice di riga, indice di colonna,
	 * stato in gioco).
	 * 
	 * @param altro L'Object con il quale testare l'uguaglianza
	 * @return <em>true</em> se l'oggetto passato come parametro e' di tipo Elemento e se i campi dei due oggetti
	 * 		si equivalgono, <em>false</em> altrimenti.
	 */
	@Override
	public boolean equals(Object altro) {
		if(altro == null || !(altro instanceof Elemento altroElemento)) {
			return false;
		}

		return((this.getLatoScacchiera() == altroElemento.getLatoScacchiera()) && (this.getRiga() == altroElemento.getRiga()) && (this.getColonna() == altroElemento.getColonna()) && (this.getInGioco() == altroElemento.getInGioco()));
	}
	
	/**
	 * Metodo che restituisce la rappresentazione di un Elemento in forma di String
	 * 
	 * @return String che contiene la rappresentazione dell'Elemento in forma di stringa
	 */
	
	@Override
	public String toString() {
		return "[Lato Scacchiera : " + this.getLatoScacchiera() +  ", Riga: " + this.getRiga() + ", Colonna: " + this.getColonna() + ", In Gioco: " + this.getInGioco() + ", Classe: " + this.getClass().getName() + "]";
	}
	
}