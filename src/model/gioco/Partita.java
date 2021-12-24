package model.gioco;

import model.elementi.*;


/**
 * Classe per la creazione e gestione di una partita del mondo del Wumpus.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class Partita {

    private final Mappa mappaDiGioco;
    private final Agente agente;
    private final Wumpus wumpus;
    private final CuccioloDiWumpus[] cuccioli;
    private final Thread tempoPerTurno;


    /**
     * Costruttore per la creazione di una nuova istanza di Partita; genera una nuova partita
     * istanziando una mappa di gioco e collocandovi gli elementi necessari
     */
    private Partita(int latoMappa, int probabilitaVoragine, int numTesori, int numSuperstiti, int numCuccioli) {
        this.mappaDiGioco = new Mappa(latoMappa, probabilitaVoragine, numTesori, numSuperstiti, numCuccioli);
        this.agente = this.getMappaDiGioco().getAgente();
        this.wumpus = this.getMappaDiGioco().getWumpus();
        this.cuccioli = this.getMappaDiGioco().getCuccioli();
        this.tempoPerTurno = new Thread(new Temporizzatore());
        this.tempoPerTurno.setDaemon(true);
    }

    private Partita(Mappa mappaDiGioco) {
        this.mappaDiGioco = mappaDiGioco;
        this.agente = this.getMappaDiGioco().getAgente();
        this.wumpus = this.getMappaDiGioco().getWumpus();
        this.cuccioli = this.getMappaDiGioco().getCuccioli();
        this.tempoPerTurno = new Thread(new Temporizzatore());
        this.tempoPerTurno.setDaemon(true);
    }

    /**
     * Metodo statico per avviare una nuova partita senza temporizzazione per i movimenti
     * dell'agente e con valori di default per la mappa.
     * Istanzia un oggetto di tipo Partita e restituisce l'oggetto di tipo Partita istanziato.
     *
     * @return oggetto di tipo Partita che permette di avviare una partita.
     */
    public static Partita avviaPartita() {
        return Partita.avviaPartita(false);
    }

    /**
     * Metodo statico per avviare una nuova partita con parametri opzionali.
     * Istanzia un oggetto di tipo Partita e restituisce l'oggetto di tipo Partita istanziato.
     *
     * @param temporizzazioneTurno <em>true</em> se si vuole che l'Agente agisca autonomamente
     *                             dopo un certo lasso di tempo, <em>false</em> altrimenti.
     * @param parametri            Parametri opzionali: latoMappa, probabilitaVoragine, numTesori, numSuperstiti e numCuccioli.
     * @return Partita istanziata.
     */
    public static Partita avviaPartita(boolean temporizzazioneTurno, int... parametri) {
        Partita nuovaPartita;

        if (parametri.length >= 5) {
            nuovaPartita = new Partita(parametri[0], parametri[1], parametri[2], parametri[3], parametri[4]);
        } else if (parametri.length == 4) {
            nuovaPartita = new Partita(parametri[0], parametri[1], parametri[2], parametri[3], Mappa.DEFAULT_NUM_CUCCIOLI);
        } else if (parametri.length == 3) {
            nuovaPartita = new Partita(parametri[0], parametri[1], parametri[2], Mappa.DEFAULT_NUM_SUPERSTITI, Mappa.DEFAULT_NUM_CUCCIOLI);
        } else if (parametri.length == 2) {
            nuovaPartita = new Partita(parametri[0], parametri[1], Mappa.DEFAULT_NUM_TESORI, Mappa.DEFAULT_NUM_SUPERSTITI, Mappa.DEFAULT_NUM_CUCCIOLI);
        } else if (parametri.length == 1) {
            nuovaPartita = new Partita(parametri[0], Mappa.DEFAULT_PROBABILITA_VORAGINE, Mappa.DEFAULT_NUM_TESORI, Mappa.DEFAULT_NUM_SUPERSTITI, Mappa.DEFAULT_NUM_CUCCIOLI);
        } else {
            nuovaPartita = new Partita(Mappa.DEFAULT_LATO_MAPPA, Mappa.DEFAULT_PROBABILITA_VORAGINE, Mappa.DEFAULT_NUM_TESORI, Mappa.DEFAULT_NUM_SUPERSTITI, Mappa.DEFAULT_NUM_CUCCIOLI);
        }

        if (temporizzazioneTurno) {
            nuovaPartita.tempoPerTurno.start();
        }

        return nuovaPartita;
    }

    /**
     * Metodo statico per avviare una nuova partita su una Mappa data.
     * Istanzia un oggetto di tipo Partita e restituisce l'oggetto di tipo Partita istanziato.
     *
     * @param temporizzazioneTurno <em>true</em> se si vuole che l'Agente agisca autonomamente
     *                             dopo un certo lasso di tempo, <em>false</em> altrimenti.
     * @param mappa                Mappa da utilizzare nella Partita.
     * @return Partita istanziata.
     */
    public static Partita avviaPartita(boolean temporizzazioneTurno, Mappa mappa) {
        Partita nuovaPartita = new Partita(mappa);

        if (temporizzazioneTurno) {
            nuovaPartita.tempoPerTurno.start();
        }

        return nuovaPartita;
    }

    /**
     * Metodo che restituisce la Mappa di gioco associata ad una Partita.
     *
     * @return Mappa associata alla Partita su cui viene richiamato.
     */
    public Mappa getMappaDiGioco() {
        return this.mappaDiGioco;
    }

    /**
     * Metodo che restituisce l'Agente associato ad una Partita.
     *
     * @return Agente associato alla Partita su cui viene richiamato.
     */
    public Agente getAgente() {
        return this.agente;
    }

    /**
     * Metodo che restituisce il Wumpus associato ad una Partita.
     *
     * @return Wumpus associato alla Partita su cui viene richiamato.
     */
    public Wumpus getWumpus() {
        return this.wumpus;
    }

    /**
     * Metodo che restituisce l'array di CuccioloDiWumpus associato ad una Partita.
     *
     * @return l'array di CuccioloDiWumpus associato alla Partita su cui viene richiamato
     */
    public CuccioloDiWumpus[] getCuccioli() {
        return this.cuccioli;
    }

    /**
     * Metodo che esegue un turno di gioco, purche' Agente e Wumpus siano ancora in gioco;
     * ogni turno comprende un'azione da parte dell'Agente e lo spostamento casuale degli elementi
     * CuccioloDiWumpus ancora in gioco in una casella adiacente, purche' valida e libera (se per lo
     * spostamento di un CuccioloDiWumpus viene selezionata una casella non valida od occupata da un
     * elemento non mobile per quel turno il CuccioloDiWumpus resta fermo).
     *
     * @param azione    Tipo di azione che si vuol far compiere all'Agente (vai o scaglia).
     * @param direzione Direzione in cui avviene l'azione compiuta dall'Agente.
     * @return String descrizione testuale delle azioni compiute dall'agente durante il turno di
     * gioco ed eventuale loro esito.
     */
    public String turnoDiGioco(String azione, Direzioni direzione) {
        StringBuilder res = new StringBuilder();

        if (this.getAgente().getInGioco() && this.getWumpus().getInGioco()) {

            this.tempoPerTurno.interrupt();

            res = new StringBuilder(this.azioneAgente(azione, direzione));

			/*
			 Ulteriore controllo che il Wumpus sia ancora vivo prima di spostare i cuccioli,
			 per evitare il caso in cui un cucciolo possa capitare nella casella dell'agente
			 e lo derubi subito dopo l'uccisione del Wumpus (quando cioe' la partita gia'
			 deve essere terminata)
			*/
            if (this.getWumpus().getInGioco()) {
                for (CuccioloDiWumpus corrente : this.getCuccioli()) {
                    if (corrente.getInGioco()) {
                        res.append("\n").append(corrente.sposta(this.getMappaDiGioco()));
                        res.append("\n").append(this.controlloCasellaAgente());
                    }
                }
            }
        }
        return res.toString();
    }

    // Metodo per far compiere un'azione all'agente (spostamento o lancio freccia)
    private String azioneAgente(String azione, Direzioni direzione) {
        String descrizioneAzione = "";

        if (azione.equals("VAI")) {
            descrizioneAzione += this.getAgente().getNome() + " prova a spostarsi a " + direzione.name().toLowerCase() + ".";
            boolean esitoSpostamento = this.getAgente().sposta(direzione);

            if (!esitoSpostamento) {
                descrizioneAzione += "\nLe pareti della caverna impediscono a " + this.getAgente().getNome() + " di spostarsi a " + direzione.name().toLowerCase() + ".";
            } else {
                descrizioneAzione += "\n" + this.getAgente().getNome() + " si sposta nella casella " + this.getAgente().getRiga() + ", " + this.getAgente().getColonna() + ".";
            }

            descrizioneAzione += "\n" + this.controlloCasellaAgente();
        } else if (azione.equals("FRECCIA")) {
            descrizioneAzione += this.getAgente().getNome() + " scaglia una freccia a " + direzione.name().toLowerCase() + ".";
            Elemento bersaglio = null;

            try {
                bersaglio = this.getAgente().scoccaFreccia(direzione);
            } catch (IndexOutOfBoundsException e) {
				/*
				 Il metodo scoccaFreccia di Agente restituisce un oggetto di tipo Elemento
				 (definito tramite classe anonima) per indicare la casella colpita dalla
				 freccia, null se l'agente non aveva piu' frecce, oppure puo' anche rilanciare
				 la IndexOutOfBoundsException del costruttore di Elemento se viene chiamato
				 con indici di riga o di colonna che eccedono i limiti della mappa.
				 Se viene lanciata l'eccezione significa percio' che la freccia e' stata
				 scagliata al di fuori della mappa: quindi si cattura l'eccezione e si mette
				 in descrizioneAzione il messaggio corrispondente.
				*/
                descrizioneAzione += "\nLa freccia colpisce le pareti della caverna.";
            }

			/*
			 Se invece la freccia ha colpito una casella, si passa l'Elemento che la rappresenta
			 al metodo controllaBersaglio.
			*/
            if (bersaglio != null) {
                descrizioneAzione += "\n" + this.controllaBersaglio(bersaglio);
            } else {
				/*
				 A questo punto, bersaglio puo' contenere null sia se la freccia e' stata scagliata
				 fuori dalla mappa, sia se l'agente ha cercato di scagliare una freccia non avendone
				 piu' a disposizione. Verificando se in descrizioneAzione e' contenuta la stringa
				 che vi viene messa in caso di freccia scagliata fuori dalla mappa si e' in grado
				 di discriminare tra le due situazioni ed eventualmente modificare di conseguenza
				 la stringa da restituire.
				*/
                if (!(descrizioneAzione.contains("La freccia colpisce le pareti della caverna."))) {
                    descrizioneAzione = this.getAgente().getNome() + " vorrebbe scagliare una freccia a " + direzione.name().toLowerCase() + ", ma le ha terminate.";
                }
            }
        }

        return descrizioneAzione;
    }

    /*
     Metodo che controlla il contenuto della casella in cui l'agente e' arrivato
     e lancia gli eventi corrispondenti alle varie eventualita', oltre a restituire
     una stringa che rappresenta la descrizione testuale dell'incontro e del suo esito.
    */
    private String controlloCasellaAgente() {
        String res = "";
        Elemento contenutoCasellaCorrente = this.getMappaDiGioco().contenutoCasella(this.getAgente());

        if (contenutoCasellaCorrente != null) {
            if (contenutoCasellaCorrente instanceof Wumpus) {
                res += this.getAgente().getNome() + " e' stato ucciso dal Wumpus " + this.getWumpus().getNome() + ".";
                this.getAgente().eliminaAgenteWumpus();
            } else if (contenutoCasellaCorrente instanceof Mappa.Voragine) {
                res += this.getAgente().getNome() + " e' caduto in una voragine.";
                this.getAgente().eliminaAgenteVoragine();
            } else if (contenutoCasellaCorrente instanceof Tesoro) {
                res += this.getAgente().getNome() + " ha trovato un tesoro.";
                res += "\n" + ((Tesoro) contenutoCasellaCorrente).eliminaTesoro(this.getAgente());
            } else if (contenutoCasellaCorrente instanceof Superstite) {
                res += this.getAgente().getNome() + " ha salvato il superstite " + ((Superstite) contenutoCasellaCorrente).getNome() + ".";
                res += "\n" + ((Superstite) contenutoCasellaCorrente).eliminaSuperstite(this.getAgente());
            } else if (contenutoCasellaCorrente instanceof CuccioloDiWumpus) {
                res += this.getAgente().getNome() + " incontra il cucciolo di Wumpus " + ((CuccioloDiWumpus) contenutoCasellaCorrente).getNome() + ".";
                res += "\n" + ((CuccioloDiWumpus) contenutoCasellaCorrente).incontroConAgente(this.getAgente());
            }
        }
        return res;
    }

    /*
     Metodo che controlla il contenuto della casella in cui e' stata lanciata la freccia
     e gestisce le varie possibilita'. Il metodo e' private e viene richiamato solo dopo
     il controllo che il parametro bersaglio non sia null; restituisce una stringa che
     rappresenta una descrizione testuale dell'esito del lancio dell freccia.
    */
    private String controllaBersaglio(Elemento bersaglio) {
        String res = "";
        Elemento contenutoDelBersaglio = this.getMappaDiGioco().contenutoCasella(bersaglio);

		/*
		 Caso in cui il contenuto della casella in cui e' stata lanciata la freccia
		 sia null (cioe' la casella non contiene alcun elemento), un'istanza di
		 Voragine, oppure un'istanza di Tesoro.
		*/
        if (contenutoDelBersaglio == null || contenutoDelBersaglio instanceof Mappa.Voragine || contenutoDelBersaglio instanceof Tesoro) {
            res += this.getAgente().getNome() + " non ha colpito nulla.";
        }
		/*
		 Caso in cui il contenuto della casella in cui e' stata lanciata la freccia
		 sia un'istanza di Wumpus.
		*/
        else if (contenutoDelBersaglio instanceof Wumpus) {
            res += this.getAgente().getNome() + " ha ucciso il Wumpus! L'uccisione di " + ((Wumpus) contenutoDelBersaglio).getNome() + " frutta a " + this.getAgente().getNome() + " una ricompensa di " + ((Wumpus) contenutoDelBersaglio).getValore() + " monete d'oro.";
            ((Wumpus) contenutoDelBersaglio).eliminaWumpus(this.getAgente());
        }
		/*
		 Caso in cui il contenuto della casella in cui e' stata lanciata la freccia
		 sia un'istanza di Superstite.
		*/
        else if (contenutoDelBersaglio instanceof Superstite) {
            res += this.getAgente().getNome() + " ha ucciso il superstite " + ((Superstite) contenutoDelBersaglio).getNome() + ".";
            ((Superstite) contenutoDelBersaglio).uccisioneSuperstite();
        }
		/*
		 Caso in cui il contenuto della casella in cui e' stata lanciata la freccia
		 sia un'istanza di CuccioloDiWumpus.
		*/
        else if (contenutoDelBersaglio instanceof CuccioloDiWumpus) {
            res += this.getAgente().getNome() + " ha ucciso il cucciolo di Wumpus " + ((CuccioloDiWumpus) contenutoDelBersaglio).getNome() + ".";
            ((CuccioloDiWumpus) contenutoDelBersaglio).uccisioneCucciolo();
        }
        return res;
    }

    /**
     * Metodo che restituisce una stringa che rappresenta la descrizione di una casella.
     * Viene richiamato per fornire all'utente la descrizione del contenuto della casella
     * in cui si trova l'Agente.
     *
     * @return String che descrive il contenuto della casella in cui si trova l'Agente.
     */
    public String descrizioneCasellaAgente() {
        String descrizione = "";

        descrizione += "Posizione attuale di " + this.getAgente().getNome() + ": " + this.getAgente().getRiga() + ", " + this.getAgente().getColonna();

        if (this.getMappaDiGioco().controllaPuzza(this.getAgente())) {
            descrizione += "\nIn questa casella si sente la puzza del Wumpus";
        }
        if (this.getMappaDiGioco().controllaBrezza(this.getAgente())) {
            descrizione += "\nIn questa casella si sente una brezza: c'e' una voragine nelle vicinanze";
        }

        return descrizione;
    }

    private class Temporizzatore implements Runnable {

        @Override
        public void run() {
            // Ciclo infinito: il temporizzatore deve venire creato come thread demone
            while (true) {
                try {
                    // Il thread si mette in sleep per 8 secondi
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
					/*
					 Il metodo che gestisce il movimento dell'agente lancia un interrupt
					 verso il temporizzatore.
					 Se la sleep viene interrotta da un interrupt, ricomincia il ciclo
					 ovvero ricomincia da capo la sleep di OTTO secondi...
					*/
                    continue;
                }

				/*
				 ...altrimenti, se arriva a questo punto del codice, significa che la sleep
				 non e' stata interrotta da nessun interrupt e quindi sono trascorsi pi�
				 di OTTO secondi dall'ultimo movimento dell'agente, quindi muove l'agente
				 in una direzione casuale.
				*/
                Partita.this.turnoDiGioco("VAI", Direzioni.spostamentoRandom());
            }
        }
    }
}