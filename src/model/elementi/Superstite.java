package model.elementi;

/**
 * Classe concreta che estende ElementoEliminabile, che permette la creazione e gestione
 * degli elementi Superstite in una partita del gioco del Wumpus.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class Superstite extends ElementoEliminabile {
    public static final String DEFAULT_NOME = "Zelda";
    public static final int DEFAULT_VALORE = 50;
    private final int valore;

    /**
     * Costruttore di Superstite.
     * Genera un nuovo Superstite in una casella a caso nella mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     */
    public Superstite(int latoMappa) throws IllegalArgumentException {
        this(latoMappa, Superstite.DEFAULT_NOME, Superstite.DEFAULT_VALORE);
    }

    /**
     * Costruttore di Superstite.
     * Genera un nuovo Superstite in una casella a caso nella mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param nome      Nome da attribuire al Superstite.
     * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     */
    public Superstite(int latoMappa, String nome) throws IllegalArgumentException {
        this(latoMappa, nome, Superstite.DEFAULT_VALORE);
    }

    /**
     * Costruttore di Superstite.
     * Genera un nuovo Superstite in una casella a caso nella mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param valore    Punteggio che si guadagna salvando il Superstite.
     * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     */
    public Superstite(int latoMappa, int valore) throws IllegalArgumentException {
        this(latoMappa, Superstite.DEFAULT_NOME, valore);
    }

    /**
     * Costruttore di Superstite.
     * Genera un nuovo Superstite in una casella a caso nella mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param nome      Nome da attribuire al Superstite.
     * @param valore    Punteggio che si guadagna salvando il Superstite.
     * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     */
    public Superstite(int latoMappa, String nome, int valore) throws IllegalArgumentException {
        super(latoMappa, nome);
        this.valore = valore;
    }

    /**
     * Costruttore di Superstite.
     * Genera un nuovo Superstite verificando che la posizione scelta sia entro i confini della mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param riga      Indice della riga in cui si vuole collocare il Superstite.
     * @param colonna   Indice della colonna in cui si vuole collocare il Superstite.
     * @throws IllegalArgumentException  Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
     */
    public Superstite(int latoMappa, int riga, int colonna) throws IllegalArgumentException, IndexOutOfBoundsException {
        this(latoMappa, riga, colonna, Superstite.DEFAULT_NOME, Superstite.DEFAULT_VALORE);
    }

    /**
     * Costruttore di Superstite.
     * Genera un nuovo Superstite verificando che la posizione scelta sia entro i confini della mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param riga      Indice della riga in cui si vuole collocare il Superstite.
     * @param colonna   Indice della colonna in cui si vuole collocare il Superstite.
     * @param nome      Nome da attribuire al Superstite.
     * @throws IllegalArgumentException  Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
     */
    public Superstite(int latoMappa, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
        this(latoMappa, riga, colonna, nome, Superstite.DEFAULT_VALORE);
    }

    /**
     * Costruttore di Superstite.
     * Genera un nuovo Superstite verificando che la posizione scelta sia entro i confini della mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param riga      Indice della riga in cui si vuole collocare il Superstite.
     * @param colonna   Indice della colonna in cui si vuole collocare il Superstite.
     * @param valore    Punteggio che si guadagna salvando il Superstite.
     * @throws IllegalArgumentException  Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
     */
    public Superstite(int latoMappa, int riga, int colonna, int valore) throws IllegalArgumentException, IndexOutOfBoundsException {
        this(latoMappa, riga, colonna, Superstite.DEFAULT_NOME, valore);
    }

    /**
     * Costruttore di Superstite.
     * Genera un nuovo Superstite verificando che la posizione scelta sia entro i confini della mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param riga      Indice della riga in cui si vuole collocare il Superstite.
     * @param colonna   Indice della colonna in cui si vuole collocare il Superstite.
     * @param nome      Nome da attribuire al Superstite.
     * @param valore    Punteggio che si guadagna salvando il Superstite.
     * @throws IllegalArgumentException  Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
     */
    public Superstite(int latoMappa, int riga, int colonna, String nome, int valore) throws IllegalArgumentException, IndexOutOfBoundsException {
        super(latoMappa, riga, colonna, nome);
        this.valore = valore;
    }

    /**
     * Costruttore di Superstite.
     * Genera un nuovo Superstite verificando che la posizione scelta sia entro i confini della mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param riga      Indice della riga in cui si vuole collocare il Superstite.
     * @param colonna   Indice della colonna in cui si vuole collocare il Superstite.
     * @param nome      Nome da attribuire al Superstite.
     * @param valore    Punteggio che si guadagna salvando il Superstite.
     * @param inGioco   Indicatore della presenza del Superstite sulla mappa.
     * @throws IllegalArgumentException  Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
     */
    public Superstite(int latoMappa, int riga, int colonna, String nome, int valore, boolean inGioco) throws IllegalArgumentException, IndexOutOfBoundsException {
        this(latoMappa, riga, colonna, nome, valore);
        this.inGioco = inGioco;
    }

    /**
     * Metodo che restituisce il valore del Superstite.
     *
     * @return valore del Superstite.
     */
    public int getValore() {
        return this.valore;
    }

    /**
     * Metodo che rimuove il Superstite dal gioco dopo essere stato salvato dall'Agente.
     * Se l'Agente non ha frecce ne ottiene una, altrimenti ottiene un numero di punti pari al campo valore del Superstite.
     *
     * @param agente Agente che incontra il Superstite.
     * @return String stringa che descrive l'andamento dell'incontro.
     */
    public String eliminaSuperstite(Agente agente) {
        String res = "";
        boolean esitoEliminazione = this.eliminaElemento();

        if (esitoEliminazione) {
			/*
			 Bonus freccia o tesoro (aumenta il numFrecce o il punteggio).
			 Se il giocatore non ha frecce, il superstite d� una freccia, altrimenti d� una freccia o
			 dell'oro con probabilita' differenti (20% - 80%)
			*/
            if (agente.getNumFrecce() == 0 || Math.random() < 0.2) {
                res += this.getNome() + " ricompensa " + agente.getNome() + " con una freccia.";
                agente.variaFrecce(1);
            } else {
                res += this.getNome() + " ricompensa " + agente.getNome() + " con " + this.getValore() + " monete d'oro.";
                agente.variaPunteggio(this.getValore());
            }
        }

        return res;
    }

    /**
     * Metodo che rimuove il Superstite dal gioco se colpito da una freccia dell'Agente.
     *
     * @return <em>true</em> se l'eliminazione va a buon fine, <em>false</em> altrimenti.
     */
    public boolean uccisioneSuperstite() {
        if (this.getInGioco()) {
			/*
			 Uso di una classe anonima per poter istanziare un oggetto di tipo Elemento, che costituisce
			 il vecchio valore con il quale viene creato il PropertyChangeEvent
			*/
            Elemento oldElemento = new Elemento(this.getLatoScacchiera(), this.getRiga(), this.getColonna()) {
            };
            this.inGioco = false;
            this.supportoEventi.firePropertyChange("uccisioneSuperstite", oldElemento, this);
            return true;
        } else {
            return false;
        }
    }
}