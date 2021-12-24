package model.elementi;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Classe astratta per la creazione e gestione di elementi di un qualsiasi gioco che si svolga
 * su di una scacchiera quadrata che possono venire eliminati dal gioco nel corso di una partita.
 * Estende Elemento aggiungendo un metodo per cambiare lo stato della variabile d'istanza booleana
 * "inGioco" nel corso di una partita, in modo da segnalare che un determinato elemento non e' piu'
 * da considerarsi in gioco per quella partita.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public abstract class ElementoEliminabile extends Elemento {
    protected final String nome;
    /*
     * Istanza di PropertyChangeSupport per gestire gli eventi riguardanti un personaggio
     */
    protected PropertyChangeSupport supportoEventi;

    /*
     * Costruttore di ElementoEliminabile.
     * Richiama il costruttore della superclasse che istanzia un elemento collocandolo in una casella
     * a caso di una scacchiera quadrata la cui dimensione del lato viene passata al costruttore come
     * parametro.
     * La dimensione del lato della scacchiera deve essere un valore strettamente maggiore di ZERO.
     *
     * @param latoScacchiera Dimensione del lato della scacchiera.
     * @throws IllegalArgumentException Se la dimensione del lato della scacchiera non e' strettamente
     * 			maggiore di ZERO.
     */
    protected ElementoEliminabile(int latoScacchiera, String nome) throws IllegalArgumentException {
        super(latoScacchiera);
        this.nome = nome;
        this.supportoEventi = new PropertyChangeSupport(this);
    }

    /*
     * Costruttore di ElementoEliminabile.
     * Richiama il costruttore della superclasse che istanzia un elemento collocandolo in una casella
     * di una scacchiera quadrata, controllando che la dimensione del lato della scacchiera sia un valore
     * strettamente maggiore di ZERO e che le coordinate siano valide rispetto alla dimensione del lato
     * della scacchiera ricevuto come parametro.
     *
     * @param latoScacchiera Dimensione del lato della scacchiera.
     * @param riga Indice della riga in cui si vuole collocare l'elemento.
     * @param colonna Indice della colonna in cui si vuole collocare l'elemento.
     * @throws IllegalArgumentException Se la dimensione del lato della scacchiera non e' strettamente
     * 			maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della scacchiera.
     */
    protected ElementoEliminabile(int latoScacchiera, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
        super(latoScacchiera, riga, colonna);
        this.nome = nome;
        this.supportoEventi = new PropertyChangeSupport(this);
    }

    /**
     * Metodo che restituisce il nome del personaggio sul quale viene chiamato.
     *
     * @return String che rappresenta il nome del personaggio.
     */
    public String getNome() {
        return this.nome;
    }

    /*
     * Metodo che elimina l'elemento dal gioco e restituisce un booleano per indicare se l'eliminazione
     * e' andata a buon fine o meno. L'eliminazione e' ottenuta cambiando il valore del campo inGioco
     * da true a false.
     * L'eliminazione di un elemento comporta il lancio di un evento "eliminazione".
     *
     * @return <em>true</em> se e' avvenuta l'eliminazione dell'elemento, <em>false</em> altrimenti (cioe'
     * 		se l'elemento su cui e' stato chiamato il metodo gia' era stato precedentemente eliminato).
     */
    protected boolean eliminaElemento() {
        if (this.getInGioco()) {
			/*
			 Uso di una classe anonima per poter istanziare un oggetto di tipo Elemento, che costituisce
			 il vecchio valore con il quale viene creato il PropertyChangeEvent
			*/
            Elemento oldElemento = new Elemento(this.getLatoScacchiera(), this.getRiga(), this.getColonna()) {
            };
            this.inGioco = false;
            this.supportoEventi.firePropertyChange("eliminazione", oldElemento, this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo che aggiunge un PropertyChangeListener di ElementoEliminabile.
     *
     * @param listener PropertyChangeListener da aggiungere.
     */
    public void aggiungiListener(PropertyChangeListener listener) {
        this.supportoEventi.addPropertyChangeListener(listener);
    }
}