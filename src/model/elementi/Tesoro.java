package model.elementi;

/**
 * Classe concreta che estende ElementoEliminabile, che permette la creazione e gestione
 * degli elementi Tesoro in una partita del gioco del Wumpus.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class Tesoro extends ElementoEliminabile {
    public static final int DEFAULT_VALORE = 100;
    private final int valore;

    /**
     * Costruttore di Tesoro.
     * Genera un nuovo Tesoro in una casella a caso nella mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     */
    public Tesoro(int latoMappa) throws IllegalArgumentException {
        this(latoMappa, Tesoro.DEFAULT_VALORE);
    }

    /**
     * Costruttore di Tesoro.
     * Genera un nuovo Tesoro in una casella a caso nella mappa.
     * Viene passato null come valore per il campo nome degli oggetti di tipo Tesoro
     * (semanticamente non ha molto senso che un tesoro abbia un nome)
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param valore    Punteggio che si guadagna recuperando il Tesoro.
     * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     */
    public Tesoro(int latoMappa, int valore) throws IllegalArgumentException {
        super(latoMappa, null);
        this.valore = valore;
    }

    /**
     * Costruttore di Tesoro.
     * Genera un nuovo Tesoro verificando che la posizione scelta sia entro i confini della mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param riga      Indice della riga in cui si vuole collocare il Superstite.
     * @param colonna   Indice della colonna in cui si vuole collocare il Superstite.
     * @throws IllegalArgumentException  Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
     */
    public Tesoro(int latoMappa, int riga, int colonna) throws IllegalArgumentException, IndexOutOfBoundsException {
        this(latoMappa, riga, colonna, Tesoro.DEFAULT_VALORE);
    }

    /**
     * Costruttore di Tesoro.
     * Genera un nuovo Tesoro verificando che la posizione scelta sia entro i confini della mappa.
     * Viene passato null come valore per il campo nome degli oggetti di tipo Tesoro
     * (semanticamente non ha molto senso che un tesoro abbia un nome)
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param riga      Indice della riga in cui si vuole collocare il Tesoro.
     * @param colonna   Indice della colonna in cui si vuole collocare il Tesoro.
     * @param valore    Punteggio che si guadagna recuperando il Tesoro.
     * @throws IllegalArgumentException  Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
     */
    public Tesoro(int latoMappa, int riga, int colonna, int valore) throws IllegalArgumentException, IndexOutOfBoundsException {
        super(latoMappa, riga, colonna, null);
        this.valore = valore;
    }

    /**
     * Costruttore di Tesoro.
     * Genera un nuovo Tesoro verificando che la posizione scelta sia entro i confini della mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param riga      Indice della riga in cui si vuole collocare il Tesoro.
     * @param colonna   Indice della colonna in cui si vuole collocare il Tesoro.
     * @param valore    Punteggio che si guadagna recuperando il Tesoro.
     * @param inGioco   Indicatore della presenza del Tesoro sulla mappa.
     * @throws IllegalArgumentException  Se la dimensione del lato della mappa non e' strettamente maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
     */
    public Tesoro(int latoMappa, int riga, int colonna, int valore, boolean inGioco) throws IllegalArgumentException, IndexOutOfBoundsException {
        this(latoMappa, riga, colonna, valore);
        this.inGioco = inGioco;
    }

    /**
     * Metodo che restituisce il valore del Tesoro.
     *
     * @return valore del Tesoro.
     */
    public int getValore() {
        return this.valore;
    }

    /**
     * Metodo che rimuove il Tesoro dal gioco dopo esser stato raccolto dall'Agente.
     * L'Agente ottiene un numero di punti pari al campo valore del Tesoro.
     *
     * @param agente Agente che raccoglie il Tesoro.
     * @return <em>true</em> se l'eliminazione va a buon fine, <em>false</em> altrimenti.
     */
    public String eliminaTesoro(Agente agente) {
        String res = "";
        boolean esitoEliminazione = this.eliminaElemento();

        if (esitoEliminazione) {
            res += "Il recupero del tesoro frutta a " + agente.getNome() + " " + this.getValore() + " monete d'oro.";
            agente.variaPunteggio(this.getValore());
        }

        return res;
    }
}