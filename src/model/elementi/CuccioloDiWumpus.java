package model.elementi;

import java.util.HashSet;

import model.gioco.Direzioni;
import model.gioco.Mappa;

/**
 * Classe concreta che estende PersonaggioMondoDelWumpus, che permette la creazione e gestione
 * dei Cuccioli di Wumpus in una partita del gioco del Wumpus.
 * I cuccioli di Wumpus si muovono casualmente sulla mappa solo se la casella scelta come
 * destinazione e' valida e libera.
 * 
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class CuccioloDiWumpus extends PersonaggioMondoDelWumpus {
	
	public static final String DEFAULT_NOME = "Goriya";

	/**
	 * Costruttore di CuccioloDiWumpus.
	 * Genera un nuovo CuccioloDiWumpus in una casella a caso nella mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 */
	public CuccioloDiWumpus(int latoMappa) throws IllegalArgumentException {
		this(latoMappa, CuccioloDiWumpus.DEFAULT_NOME);
	}
	
	/**
	 * Costruttore di CuccioloDiWumpus.
	 * Genera un nuovo CuccioloDiWumpus in una casella a caso nella mappa.
	 * 
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param nome Nome da attribuire al CuccioloDiWumpus.
 	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 */
	public CuccioloDiWumpus(int latoMappa, String nome) throws IllegalArgumentException {
		super(latoMappa, nome);
	}
	
	/**
	 * Costruttore di CuccioloDiWumpus.
	 * Genera un nuovo CuccioloDiWumpus verificando che la posizione scelta sia entro i confini della mappa.
	 *
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param riga Indice della riga in cui si vuole collocare il CuccioloDiWumpus.
	 * @param colonna Indice della colonna in cui si vuole collocare il CuccioloDiWumpus.
	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public CuccioloDiWumpus(int latoMappa, int riga, int colonna) throws IllegalArgumentException, IndexOutOfBoundsException {
		this(latoMappa, riga, colonna, CuccioloDiWumpus.DEFAULT_NOME);
	}

	/**
	 * Costruttore di CuccioloDiWumpus.
	 * Genera un nuovo CuccioloDiWumpus verificando che la posizione scelta sia entro i confini della mappa.
	 *
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param riga Indice della riga in cui si vuole collocare il CuccioloDiWumpus.
	 * @param colonna Indice della colonna in cui si vuole collocare il CuccioloDiWumpus.
	 * @param nome Nome da attribuire al CuccioloDiWumpus.
	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public CuccioloDiWumpus(int latoMappa, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
		super(latoMappa, riga, colonna, nome);
	}
	
	/**
	 * Costruttore di CuccioloDiWumpus.
	 * Genera un nuovo CuccioloDiWumpus verificando che la posizione scelta sia entro i confini della mappa.
	 *
	 * @param latoMappa Dimensione del lato della mappa di gioco.
	 * @param riga Indice della riga in cui si vuole collocare il CuccioloDiWumpus.
	 * @param colonna Indice della colonna in cui si vuole collocare il CuccioloDiWumpus.
	 * @param nome Nome da attribuire al CuccioloDiWumpus.
	 * @param inGioco Indica se il CuccioloDiWumpus è in gioco.
	 * @throws IllegalArgumentException Se la dimensione del lato della mappa non e' strettamente maggiore di 0.
	 * @throws IndexOutOfBoundsException Se le coordinate eccedono i limiti della mappa.
	 */
	public CuccioloDiWumpus(int latoMappa, int riga, int colonna, String nome, boolean inGioco) throws IllegalArgumentException, IndexOutOfBoundsException {
		this(latoMappa, riga, colonna, nome);
		this.inGioco = inGioco;
	}

	/**
	 * Metodo che permette di spostare il CuccioloDiWumpus nella mappa di gioco.
	 * La mappa di gioco in cui spostare il CuccioloDiWumpus viene passata come parametro al metodo
	 * (necessario per effettuare i controlli richiesti per la validita' dello spostamento)
	 * Il movimento del CuccioloDiWumpus avviene in una direzione casuale.
	 * 
	 * @param mappaDiGioco la mappa in cui il CuccioloDiWumpus deve spostarsi.
	 * @return <em>true</em> se lo spostamento va a buon fine, <em>false</em> altrimenti.
	 */
	public String sposta(Mappa mappaDiGioco) {
		String res = "";
		// La direzione di spostamento e' generata casualmente
		// (metodo statico spostamentoRandom() dell'enumerativo Direzioni)
		Direzioni direzione;
		// HashSet per tenere traccia delle direzioni di spostamento gia' tentate.
		// Si usa un HashSet per escludere automaticamente eventuali nuove generazioni
		// di valori gia' precedentemente generati (l'HashSet non permette l'inserimento
		// di elementi ripetuti); in questo modo se l'HashSet raggiunge la dimensione 4
		// si ha la conferma di aver gia' tentato tutte le 4 direzioni di spostamento
		// possibili.
		HashSet<Direzioni> direzioniGiaTentate = new HashSet<Direzioni>();
		// Controfigura del cucciolo per verificare se lo spostamento e' possibile
		PersonaggioMondoDelWumpus controfigura;
		
		boolean spostamento = false;
		
		// Ciclo do...while per spostare casualmente il cucciolo in una casella adiacente,
		// purche' valida e libera.
		// Il ciclo viene ripetuto finche' lo spostamento non va a buon fine (ovvero se si
		// raggiunge una casella non valida oppure gia' occupata), purche' non si siano gia'
		// tentate tutte le direzioni possibili; qualora si verificasse l'impossibilta' di
		// spostare il cucciolo in nessuna delle caselle adiacenti il ciclo non verrebbe
		// comunque ripetuto ulteriormente.
		// La condizione di ripetizione e' data dalla valutazione dell'espressione
		// (!(spostamento) || !(mappaDiGioco.contenutoCasella(controfigura) == null)) && direzioniGiaTentate.size() < 4
		do {
			// Ciclo do...while per generare uno spostamento casuale; nel caso in cui si
			// generi nuovamente uno spostamento gia' precedentemente tentato e ci siano
			// ancora possibili direzioni da tentare, si genera una nuova direzione;
			// la condizione di ripetizione e' data dalla valutazione dell'espressione
			// direzioniGiaTentate.contains(direzione) && direzioniGiaTentate.size() < 4
			do {
				direzione = Direzioni.spostamentoRandom();
			} while(direzioniGiaTentate.contains(direzione) && direzioniGiaTentate.size() < 4);
			
			// Si aggiunge la direzione di spostamento appena generata all'HashSet delle
			// direzioni gia' tentate
			direzioniGiaTentate.add(direzione);
			
			// Si istanzia una controfigura del cucciolo per verificare se lo spostamento e'
			// possibile; la controfigura e' istanziata come oggetto di una classe anonima con
			// tipo statico PersonaggioMondoDelWumpus e con valori di latoScacchiera, Riga e 
			// Colonna identici a quelli del cucciolo che si sta tentando di spostare.
			// Non e' necessario definire nulla in questa classe anonima, dato che viene usato
			// semplicemente il costuttore di PersonaggioMondoDelWumpus con firma
			// PersonaggioMondoDelWumpus(int, int, int, String).
			// Sulla controfigura potranno venire richiamati tutti i metodi implementati fino a
			// PersonaggioMondoDelWumpus: verra' richiamata l'implementazione che i metodi hanno
			// a questo livello di astrazione.
			controfigura = new PersonaggioMondoDelWumpus(this.getLatoScacchiera(), this.getRiga(), this.getColonna(), "Controfigura") {};
			
			// Tentativo di spostamento della controfigura nella direzione generata casualmente
			spostamento = controfigura.sposta(direzione);

		} while((!(spostamento) || !(mappaDiGioco.contenutoCasella(controfigura) == null)) && direzioniGiaTentate.size() < 4);

		
		// Se il ciclo e' terminato trovando una casella valida effettua lo spostamento effettivo
		// del cucciolo.
		if(spostamento && mappaDiGioco.contenutoCasella(controfigura) == null) {
			this.sposta(direzione);
			res += "Il cucciolo di Wumpus " + this.getNome() + " si sposta nella casella " + this.getRiga() + ", " + this.getColonna();
		}
		// Altrimenti, se il ciclo fosse terminato per l'esaurimento dei tentativi, il cucciolo non
		// si puo' spostare.
		else {
			res += "Il cucciolo di Wumpus " + this.getNome() + " non si muove.";
		}
		
		return res;
	}
	
	/**
	 * Metodo per gestire la collisione del CuccioloDiWumpus con l'Agente.
	 * 
	 * @param agente Agente con cui è avvenuta la collisione.
	 * @return String stringa contenente la descrizione dell'andamento dell'incontro
	 */
	public String incontroConAgente(Agente agente) {
		String res = "";
		boolean oldValue = this.getInGioco();
		// Il cucciolo ruba qualcosa al giocatore (se il giocatore ha qualcosa da rubare),
		// poi scompare.

		// Se il giocatore ha sia oro che frecce, il cucciolo sottrae o una freccia o dell'oro
		// con probabilita' differenti (rispettivamente 20% e 80%),
		if(agente.getPunteggio() > 0 && agente.getNumFrecce() > 0) {
			if(Math.random() < 0.2) {
				res += this.getNome() + " ruba a " + agente.getNome() + " una freccia.";
				agente.variaFrecce(-1);
			}
			else {
				res += this.getNome() + " ruba a " + agente.getNome() + " 50 monete d'oro.";
				agente.variaPunteggio(-50);
			}
		}
		// altrimenti se il giocatore ha solo frecce ma non oro, il cucciolo sottrae una freccia,
		else if(agente.getPunteggio() == 0 && agente.getNumFrecce() > 0) {
			res += this.getNome() + " ruba a " + agente.getNome() + " una freccia.";
			agente.variaFrecce(-1);
		}
		// altrimenti se il giocatore ha oro ma non frecce, il cucciolo sottrae dell'oro.
		else if(agente.getPunteggio() > 0 && agente.getNumFrecce() == 0) {
			res += this.getNome() + " ruba a " + agente.getNome() + " 50 monete d'oro.";
			agente.variaPunteggio(-50);
		}
		// Se il giocatore non ha ne' frecce ne' oro il cucciolo non ruba nulla.
		else {
			res += this.getNome() + " non trova nulla da rubare.";
		}
			
		// Dopo aver derubato il giocatore il cucciolo viene eliminato dal gioco.
		res += "\n" + this.getNome() + " scompare dalla mappa.";
		this.eliminaElemento();
		this.supportoEventi.firePropertyChange("cucciolo", oldValue, this.getInGioco());
		return res;
	}
	
	/**
	 * Metodo che rimuove dal gioco il CuccioloDiWumpus perché ucciso da parte dell'Agente.
	 *
	 * @return <em>true</em> se l'eliminazione va a buon fine, <em>false</em> altrimenti.
	 */
	public boolean uccisioneCucciolo() {
		if(this.getInGioco()) {
			// Uso di una classe anonima per poter istanziare un oggetto di tipo Elemento, che costituisce
			// il vecchio valore con il quale viene creato il PropertyChangeEvent
			Elemento oldElemento = new Elemento(this.getLatoScacchiera(), this.getRiga(), this.getColonna()) {};
			this.inGioco = false;
			this.supportoEventi.firePropertyChange("uccisioneCucciolo", oldElemento, this);
			return true;
		}
		else {
			return false;
		}
	}
}