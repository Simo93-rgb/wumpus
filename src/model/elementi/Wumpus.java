package model.elementi;

/**
 * Classe concreta che estende ElementoEliminabile, che permette la creazione e gestione
 * del Wumpus in una partita del gioco del Wumpus.
 * 
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class Wumpus extends ElementoEliminabile {
	private final int valore;
	
	public static final String DEFAULT_NOME = "Ganon";
	public static final int DEFAULT_VALORE = 200;
	
	/**
	 * Costruttore di Wumpus.
	 * Genera un nuovo Wumpus in una casella a caso nella mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 */
	public Wumpus(int latoMappa) throws IllegalArgumentException {
		this(latoMappa, Wumpus.DEFAULT_NOME, Wumpus.DEFAULT_VALORE);
	}
	
	/**
	 * Costruttore di Wumpus.
	 * Genera un nuovo Wumpus in una casella a caso nella mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param nome Nome da attribuire al Wumpus.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 */
	public Wumpus(int latoMappa, String nome) throws IllegalArgumentException {
		this(latoMappa, nome, Wumpus.DEFAULT_VALORE);
	}
	
	/**
	 * Costruttore di Wumpus.
	 * Genera un nuovo Wumpus in una casella a caso nella mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param valore Punteggio che si guadagna uccidendo il Wumpus.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 */
	public Wumpus(int latoMappa, int valore) throws IllegalArgumentException {
		this(latoMappa, Wumpus.DEFAULT_NOME, valore);
	}

	/**
	 * Costruttore di Wumpus.
	 * Genera un nuovo Wumpus in una casella a caso nella mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param nome Nome da attribuire al Wumpus.
	 * @param valore Punteggio che si guadagna uccidendo il Wumpus.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 */
	public Wumpus(int latoMappa, String nome, int valore) throws IllegalArgumentException {
		super(latoMappa, nome);
		this.valore = valore;
	}

	/**
	 * Costruttore di Wumpus.
	 * Genera un nuovo Wumpus verificando che la posizione scelta sia entro i confini della mappa.
	 *
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param riga Indice della riga in cui si vuole collocare il Wumpus.
	 * @param colonna Indice della colonna in cui si vuole collocare il Wumpus.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public Wumpus(int latoMappa, int riga, int colonna) throws IllegalArgumentException, IndexOutOfBoundsException {
		this(latoMappa, riga, colonna, Wumpus.DEFAULT_NOME, Wumpus.DEFAULT_VALORE);
	}
	
	/**
	 * Costruttore di Wumpus.
	 * Genera un nuovo Wumpus verificando che la posizione scelta sia entro i confini della mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param riga Indice della riga in cui si vuole collocare il Wumpus.
	 * @param colonna Indice della colonna in cui si vuole collocare il Wumpus.
	 * @param nome Nome da attribuire al Wumpus.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public Wumpus(int latoMappa, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
		this(latoMappa, riga, colonna, nome, Wumpus.DEFAULT_VALORE);
	}

	/**
	 * Costruttore di Wumpus.
	 * Genera un nuovo Wumpus verificando che la posizione scelta sia entro i confini della mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param riga Indice della riga in cui si vuole collocare il Wumpus.
	 * @param colonna Indice della colonna in cui si vuole collocare il Wumpus.
	 * @param valore Punteggio che si guadagna uccidendo il Wumpus.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public Wumpus(int latoMappa, int riga, int colonna, int valore) throws IllegalArgumentException, IndexOutOfBoundsException {
		this(latoMappa, riga, colonna, Wumpus.DEFAULT_NOME, valore);
	}

	/**
	 * Costruttore di Wumpus.
	 * Genera un nuovo Wumpus verificando che la posizione scelta sia entro i confini della mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param riga Indice della riga in cui si vuole collocare il Wumpus.
	 * @param colonna Indice della colonna in cui si vuole collocare il Wumpus.
	 * @param nome Nome da attribuire al Wumpus.
	 * @param valore Punteggio che si guadagna uccidendo il Wumpus.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public Wumpus(int latoMappa, int riga, int colonna, String nome, int valore) throws IllegalArgumentException, IndexOutOfBoundsException {
		super(latoMappa, riga, colonna, nome);
		this.valore = valore;
	}
	
	/**
	 * Costruttore di Wumpus.
	 * Genera un nuovo Wumpus verificando che la posizione scelta sia entro i confini della mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param riga Indice della riga in cui si vuole collocare il Wumpus.
	 * @param colonna Indice della colonna in cui si vuole collocare il Wumpus.
	 * @param nome Nome da attribuire al Wumpus.
	 * @param valore Punteggio che si guadagna uccidendo il Wumpus.
	 * @param inGioco Indicatore della presenza del Wumpus sulla mappa.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public Wumpus(int latoMappa, int riga, int colonna, String nome, int valore, boolean inGioco)  throws IllegalArgumentException, IndexOutOfBoundsException {
		this(latoMappa, riga, colonna, nome, valore);
		this.inGioco = inGioco;
	}
	
	/**
	 * Metodo che restituisce il valore del Wumpus.
	 * 
	 * @return valore del Wumpus.
	 */
	public int getValore() {
		return this.valore;
	}

	/**
	 * Metodo che rimuove il Wumpus dal gioco dopo esser stato ucciso dall'Agente.
	 * L'Agente ottiene un numero di punti pari al campo valore del Wumpus.
	 * 
	 * @param agente Agente che uccide il Wumpus.
	 * @return <em>true</em> se l'eliminazione va a buon fine, <em>false</em> altrimenti.
	 */
	public boolean eliminaWumpus(Agente agente) {
		boolean valoreDiRitorno = this.eliminaElemento();
		
		if(valoreDiRitorno) {
			//System.out.println(agente.getNome() + " ha ucciso il Wumpus! L'uccisione di " + this.getNome() + " frutta a " + agente.getNome() + " una ricompensa di " + this.getValore() + " monete d'oro.");
			agente.variaPunteggio(this.getValore());
		}
		
		return valoreDiRitorno;
	}
}