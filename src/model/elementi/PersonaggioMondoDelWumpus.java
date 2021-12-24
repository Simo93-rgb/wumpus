package model.elementi;

import model.gioco.Direzioni;

/**
 * Classe astratta per la creazione e gestione dei personaggi mobili del mondo del Wumpus
 * (Agente e CuccioloDiWumpus).
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public abstract class PersonaggioMondoDelWumpus extends ElementoMobile {

    /*
     * Costruttore di PersonaggioDelWumpus.
     * Genera un nuovo personaggio in una casella a caso nella mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param nome Nome da attribuire al personaggio
     * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente
     *		maggiore di ZERO.
     */
    protected PersonaggioMondoDelWumpus(int latoMappa, String nome) throws IllegalArgumentException {
        super(latoMappa, nome);
    }

    /*
     * Costruttore di PersonaggioDelWumpus.
     * Genera un nuovo personaggio verificando che la posizione scelta sia entro i confini della mappa.
     *
     * @param latoMappa Dimensione del lato della mappa di gioco.
     * @param riga Indice della riga in cui si vuole collocare il personaggio.
     * @param colonna Indice della colonna in cui si vuole collocare il personaggio.
     * @param nome Nome da attribuire al personaggio
     * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente
     *		maggiore di ZERO.
     * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
     */
    protected PersonaggioMondoDelWumpus(int latoMappa, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
        super(latoMappa, riga, colonna, nome);
    }

    /**
     * Metodo che consente di spostare un personaggio indicando la direzione di spostamento per mezzo
     * dell'enumerativo Direzioni
     *
     * @param direzioneSpostamento La direzione di spostamento (un'istanza dell'enumerativo Direzioni)
     * @return <em>true</em> se lo spostamento e' andato a buon fine, <em>false</em> altrimenti.
     */
    public boolean sposta(Direzioni direzioneSpostamento) {
        return this.sposta(direzioneSpostamento.getSpostamentoRiga(), direzioneSpostamento.getSpostamentoColonna());
    }

    /*
     * Metodo che assegna una nuova posizione al personaggio (override di sposta() di ElementoMobile).
     * Utilizzato per permettere ai personaggi di muoversi sulla mappa di gioco.
     * Viene fatto l'override del metodo sposta() di ElementoMobile in modo che non sia possibile
     * spostare istanze di PersonaggioGiocoWumpus se non passando attraverso questa implementazione
     * del metodo, che controlla anche che lo spostamento avvenga di una sola casella alla volta e
     * che non sia in diagonale.
     * In realta' anche questa implementazione di sposta non sara' richiamabile dall'esterno (il metodo e'
     * protected): per spostare un personaggio si dovra' chiamare il metodo specifico per quel tipo di
     * personaggio (Agente o CuccioloDiWumpus), dato che le modalita' di spostamento sono differenti.
     * Questo metodo viene richiamato dalle sottoclassi per compiere effettivamente lo spostamento.
     *
     * @param spostamentoRiga Variazione della posizione di riga del personaggio (spostamento verticale).
     * <em>-1</em> per uno spostamento verso nord, <em>1</em> per uno spostamento verso sud, <em>0</em> altrimenti.
     * @param spostamentoColonna Variazione della posizione di colonna del personaggio (spostamento verticale).
     * <em>-1</em> per uno spostamento verso ovest, <em>1</em> per uno spostamento verso est, <em>0</em> altrimenti.
     * @return <em>true</em> se lo spostamento va a buon fine, <em>false</em> altrimenti.
     * @throws IllegalArgumentException Se lo spostamento e' maggiore di una casella o se e' in diagonale.
     */
    @Override
    protected boolean sposta(int spostamentoRiga, int spostamentoColonna) throws IllegalArgumentException {

        if (spostamentoRiga < -1 || spostamentoRiga > 1 || spostamentoColonna < -1 || spostamentoColonna > 1) {
            throw new IllegalArgumentException("Lo spostamento puo' avvenire solo di una casella alla volta");
        }

        if (spostamentoRiga != 0 && spostamentoColonna != 0) {
            throw new IllegalArgumentException("Lo spostamento non puo' avvenire in diagonale");
        }

        return super.sposta(spostamentoRiga, spostamentoColonna);
    }

}