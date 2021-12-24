package controller;

import model.gioco.Direzioni;
import model.gioco.Mappa;
import model.gioco.Partita;
import view.WumpusView;
import view.WumpusViewJComboBox;
import view.WumpusViewJComboBoxCompleta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe controller della Partita del gioco del Wumpus, per partite controllate da JComboBox.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class WumpusControllerJComboBox implements ActionListener {
    private final Partita partitaCorrente;
    private final WumpusViewJComboBox vistaCorrente;
    private WumpusViewJComboBoxCompleta vistaCorrenteCompleta;

    /**
     * Costruttore di WumpusController.
     * Avvia una partita con i valori di default, senza temporizzazione e senza visualizzazione completa della Mappa.
     */
    public WumpusControllerJComboBox() {
        this(false, false, 10, 20, 3, 2, 2);
    }

    /**
     * Costruttore di WumpusController.
     * Avvia una partita con i parametri dati.
     *
     * @param temporizzazioneTurno    <em>true</em> se si vuole che l'Agente agisca autonomamente
     *                                dopo un certo lasso di tempo, <em>false</em> altrimenti.
     * @param visualizzaMappaCompleta <em>true</em> se si vuole che il giocatore possa visualizzare
     *                                completamente la mappa di gioco (senza fog of war), <em>false</em> altrimenti.
     * @param parametri               Parametri opzionali: latoMappa, probabilitaVoragine, numTesori, numSuperstiti e numCuccioli.
     */
    public WumpusControllerJComboBox(boolean temporizzazioneTurno, boolean visualizzaMappaCompleta, int... parametri) {
        this.partitaCorrente = Partita.avviaPartita(temporizzazioneTurno, parametri);
        this.vistaCorrente = new WumpusViewJComboBox(partitaCorrente);
        vistaCorrente.bottoneEsegui.addActionListener(this);
        vistaCorrente.menuCheckBoxMusica.addActionListener(this);
        vistaCorrente.menuItemSalva.addActionListener(this);
        vistaCorrente.menuItemEsci.addActionListener(this);
        this.vistaCorrente.regole.addActionListener(this);

        if (visualizzaMappaCompleta) {
            this.vistaCorrenteCompleta = new WumpusViewJComboBoxCompleta(partitaCorrente);
            vistaCorrenteCompleta.bottoneEsegui.addActionListener(this);
            vistaCorrenteCompleta.menuCheckBoxMusica.addActionListener(this);
            vistaCorrenteCompleta.menuItemSalva.addActionListener(this);
            vistaCorrenteCompleta.menuItemEsci.addActionListener(this);
            this.vistaCorrenteCompleta.regole.addActionListener(this);
        }
    }

    /**
     * Costruttore di WumpusController.
     * Avvia una partita su una Mappa data.
     *
     * @param temporizzazioneTurno    <em>true</em> se si vuole che l'Agente agisca autonomamente
     *                                dopo un certo lasso di tempo, <em>false</em> altrimenti.
     * @param visualizzaMappaCompleta <em>true</em> se si vuole che il giocatore possa visualizzare
     *                                completamente la mappa di gioco (senza fog of war), <em>false</em> altrimenti.
     * @param mappa                   Mappa da utilizzare nella Partita.
     */
    public WumpusControllerJComboBox(boolean temporizzazioneTurno, boolean visualizzaMappaCompleta, Mappa mappa) {
        this.partitaCorrente = Partita.avviaPartita(temporizzazioneTurno, mappa);
        this.vistaCorrente = new WumpusViewJComboBox(partitaCorrente);
        vistaCorrente.bottoneEsegui.addActionListener(this);
        vistaCorrente.menuCheckBoxMusica.addActionListener(this);
        vistaCorrente.menuItemSalva.addActionListener(this);
        vistaCorrente.menuItemEsci.addActionListener(this);

        if (visualizzaMappaCompleta) {
            this.vistaCorrenteCompleta = new WumpusViewJComboBoxCompleta(partitaCorrente);
            vistaCorrenteCompleta.bottoneEsegui.addActionListener(this);
            vistaCorrenteCompleta.menuCheckBoxMusica.addActionListener(this);
            vistaCorrenteCompleta.menuItemSalva.addActionListener(this);
            vistaCorrenteCompleta.menuItemEsci.addActionListener(this);
        }
    }

    /**
     * Metodo che restituisce la Partita corrente.
     *
     * @return Partita corrente.
     */
    public Partita getPartita() {
        return this.partitaCorrente;
    }

    /**
     * Metodo che restituisce la WumpusView corrente.
     *
     * @return WumpusView corrente.
     */
    public WumpusView getView() {
        return this.vistaCorrente;
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("Esegui")) {

            /*
             Recupero l'informazione sulla GUI in cui e' stato premuto il bottone "Esegui"
             (corrisponde alla finestra attualmente attiva) e casto il risultato (un oggetto di
             tipo Window) ad un oggetto di tipo WumpusView
            */
            WumpusViewJComboBox finestraAttiva = (WumpusViewJComboBox) javax.swing.FocusManager.getCurrentManager().getActiveWindow();

            /*
             Recupero la String che descrive l'azione da compiere attraverso il metodo getComboBoxesMessage()
             richiamato sulla WumpusView in cui e' stato premuto il pulsante
            */
            String azioneDaCompiere = finestraAttiva.getComboBoxesMessage();

            /*
             E' necessario fare in questo modo nel caso in cui si stiano visualizzando le due finestre,
             una per la visualizzazione normale e l'altra per la visualizzazione completa. In questo modo
             viene eseguito il comando impostato attraverso le JComboBox della GUI in cui e' stato premuto
             il bottone "Esegui"
             Divido la String contenente l'azione da compiere nell'array di String "comandi"
             La prima String dell'array (ovvero "comandi[0]") contiene l'azione da compiere ("Vai" o "Freccia"),
             la seconda String (ovvero "comandi[1]") la direzione in cui compierla.
            */
            String[] comandi = azioneDaCompiere.split(" ");

            switch (comandi[1].toUpperCase()) {
                case "NORD" -> this.partitaCorrente.turnoDiGioco(comandi[0].toUpperCase(), Direzioni.NORD);
                case "EST" -> this.partitaCorrente.turnoDiGioco(comandi[0].toUpperCase(), Direzioni.EST);
                case "SUD" -> this.partitaCorrente.turnoDiGioco(comandi[0].toUpperCase(), Direzioni.SUD);
                case "OVEST" -> this.partitaCorrente.turnoDiGioco(comandi[0].toUpperCase(), Direzioni.OVEST);
            }
        }

        if (evt.getActionCommand().equals("Musica di sottofondo")) {
            if (((JCheckBoxMenuItem) evt.getSource()).isSelected()) {
                this.vistaCorrente.musicaBackground.start();
                if (vistaCorrenteCompleta != null) {
                    this.vistaCorrente.menuCheckBoxMusica.setSelected(true);
                    this.vistaCorrenteCompleta.menuCheckBoxMusica.setSelected(true);
                }
            } else {
                this.vistaCorrente.musicaBackground.stop();
                if (vistaCorrenteCompleta != null) {
                    this.vistaCorrente.menuCheckBoxMusica.setSelected(false);
                    this.vistaCorrenteCompleta.menuCheckBoxMusica.setSelected(false);
                }
            }
        }

        if (evt.getActionCommand().equals("Salva")) {
            this.partitaCorrente.getMappaDiGioco().salvataggio();
            this.vistaCorrente.salvataggioCaselleScoperte();
        }

        if (evt.getActionCommand().equals("Esci")) {
            System.exit(0);
        }

        if (evt.getActionCommand().equals("Regole")) {
            FileReader myReader;
            Scanner myScanner;
            String file = "./bin/RegoleWumpus.txt";
            try {
                myReader = new FileReader(file);
                BufferedReader bf = new BufferedReader(myReader);
                myScanner = new Scanner(bf);
                LinkedList<String> regole = new LinkedList<>();

                while (myScanner.hasNextLine()) {
                    regole.add(myScanner.nextLine());
                }
                this.vistaCorrente.visualizzaRegole(regole);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}