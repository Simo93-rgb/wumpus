package model.elementi;

/**
 * Classe astratta per la creazione e gestione di elementi mobili di un qualsiasi gioco che si svolga
 * su di una scacchiera quadrata.
 * Estende ElementoEliminabile.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public abstract class ElementoMobile extends ElementoEliminabile {

    /*
     * Costruttore di ElementoMobile.
     * Richiama il costruttore della superclasse che istanzia un elemento collocandolo in una casella
     * a caso di una scacchiera quadrata la cui dimensione del lato viene passata al costruttore come
     * parametro.
     *
     * @param latoScacchiera Dimensione del lato della scacchiera.
     * @param nome Nome da dare all'Elemento
     * @throws IllegalArgumentException Se la dimensione del lato della scacchiera non e' strettamente
     * 			maggiore di ZERO.
     *
     */
    protected ElementoMobile(int latoScacchiera, String nome) throws IllegalArgumentException {
        super(latoScacchiera, nome);
    }

    /*
     * Costruttore di ElementoMobile.
     * Richiama il costruttore della superclasse che istanzia un elemento collocandolo in una casella
     * di una scacchiera quadrata, controllando che la dimensione del lato della scacchiera sia un valore
     * strettamente maggiore di ZERO e che le coordinate siano valide rispetto alla dimensione del lato
     * della scacchiera ricevuto come parametro.
     *
     * @param latoScacchiera Dimensione del lato della scacchiera.
     * @param riga Indice della riga in cui si vuole collocare l'elemento.
     * @param colonna Indice della colonna in cui si vuole collocare l'elemento.
     * @param nome Nome da dare all'Elemento
     * @throws IllegalArgumentException Se la dimensione del lato della scacchiera non e' strettamente
     * 			maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della scacchiera.
     */
    protected ElementoMobile(int latoScacchiera, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
        super(latoScacchiera, riga, colonna, nome);
    }

    /*
     * Metodo che assegna una nuova posizione all'elemento mobile.
     * Utilizzato per permettere agli elementi mobili di spostarsi sulla scacchiera.
     * A questo livello l'unico controllo possibile e' che lo spostamento non porti l'elemento ad
     * uscire dalla scacchiera (altri controlli variano a seconda dei tipi di movimenti che si
     * vogliono permettere agli elementi, ma non sono noti a questo livello).
     * Il metodo e' concreto, ma dato che il movimento di un elemento mobile potra' variare a seconda dei
     * vari giochi (ogni gioco puo' avere regole di spostamento differenti) le sottoclassi che estendono
     * ElementoMobile potranno sovrascrivere questo metodo (ed eventualmente richiamarlo in una loro
     * implementazione del metodo per controllare che lo spostamento non porti l'elemento ad uscire
     * dalla scacchiera).
     *
     * @param spostamentoRiga Variazione della posizione di riga del personaggio (movimento verticale).
     * @param spostamentoColonna Variazione della posizione di colonna del personaggio (spostamento orizzontale).
     * @return <em>true</em> se lo spostamento va a buon fine, <em>false</em> altrimenti.
     */
    protected boolean sposta(int spostamentoRiga, int spostamentoColonna) {
        int oldRiga = this.getRiga();
        int oldColonna = this.getColonna();
        int newRiga = oldRiga + spostamentoRiga;
        int newColonna = oldColonna + spostamentoColonna;

        if (newRiga < 0 || newRiga >= this.getLatoScacchiera() || newColonna < 0 || newColonna >= this.getLatoScacchiera()) {
            return false;
        }
		
		/*
		 Uso di una classe anonima per poter istanziare un oggetto di tipo Elemento, che costituisce
		 il vecchio valore con il quale viene creato il PropertyChangeEvent
		*/
        Elemento oldElemento = new Elemento(this.getLatoScacchiera(), oldRiga, oldColonna) {
        };
        this.riga = newRiga;
        this.colonna = newColonna;
        this.supportoEventi.firePropertyChange("spostamento", oldElemento, this);
        return true;
    }
}