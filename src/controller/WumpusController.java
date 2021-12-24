package controller;

import model.gioco.Direzioni;
import model.gioco.Mappa;
import model.gioco.Partita;
import view.WumpusView;
import view.WumpusViewCompleta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe controller della Partita del gioco del Wumpus, per partite controllate da tastiera.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class WumpusController implements KeyListener, ActionListener {
    private final Partita partitaCorrente;
    private final WumpusView vistaCorrente;
    private WumpusViewCompleta vistaCorrenteCompleta;

    /**
     * Costruttore di WumpusController.
     * Avvia una partita con i valori di default, senza temporizzazione e senza visualizzazione completa della Mappa.
     */
    public WumpusController() {
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
    public WumpusController(boolean temporizzazioneTurno, boolean visualizzaMappaCompleta, int... parametri) {
        this.partitaCorrente = Partita.avviaPartita(temporizzazioneTurno, parametri);
        this.vistaCorrente = new WumpusView(partitaCorrente);
        this.vistaCorrente.addKeyListener(this);
        this.vistaCorrente.menuCheckBoxMusica.addActionListener(this);
        this.vistaCorrente.menuItemSalva.addActionListener(this);
        this.vistaCorrente.menuItemEsci.addActionListener(this);
        this.vistaCorrente.regole.addActionListener(this);

        if (visualizzaMappaCompleta) {
            this.vistaCorrenteCompleta = new WumpusViewCompleta(partitaCorrente);
            this.vistaCorrenteCompleta.addKeyListener(this);
            this.vistaCorrenteCompleta.menuCheckBoxMusica.addActionListener(this);
            this.vistaCorrenteCompleta.menuItemSalva.addActionListener(this);
            this.vistaCorrenteCompleta.menuItemEsci.addActionListener(this);
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
    public WumpusController(boolean temporizzazioneTurno, boolean visualizzaMappaCompleta, Mappa mappa) throws IllegalArgumentException, IOException {
        this.partitaCorrente = Partita.avviaPartita(temporizzazioneTurno, mappa);
        this.vistaCorrente = new WumpusView(partitaCorrente);
        this.vistaCorrente.caricamentoCaselleScoperte();
        this.vistaCorrente.addKeyListener(this);
        this.vistaCorrente.menuCheckBoxMusica.addActionListener(this);
        this.vistaCorrente.menuItemSalva.addActionListener(this);
        this.vistaCorrente.menuItemEsci.addActionListener(this);
        this.vistaCorrente.regole.addActionListener(this);

        if (visualizzaMappaCompleta) {
            this.vistaCorrenteCompleta = new WumpusViewCompleta(partitaCorrente);
            this.vistaCorrenteCompleta.addKeyListener(this);
            this.vistaCorrenteCompleta.menuCheckBoxMusica.addActionListener(this);
            this.vistaCorrenteCompleta.menuItemSalva.addActionListener(this);
            this.vistaCorrenteCompleta.menuItemEsci.addActionListener(this);
            this.vistaCorrenteCompleta.regole.addActionListener(this);
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
    public void keyTyped(KeyEvent evt) {
        switch (evt.getKeyChar()) {
            case 'a', 'A' -> this.partitaCorrente.turnoDiGioco("VAI", Direzioni.OVEST);
            case 'w', 'W' -> this.partitaCorrente.turnoDiGioco("VAI", Direzioni.NORD);
            case 'd', 'D' -> this.partitaCorrente.turnoDiGioco("VAI", Direzioni.EST);
            case 's', 'S' -> this.partitaCorrente.turnoDiGioco("VAI", Direzioni.SUD);
        }
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case 37 -> this.partitaCorrente.turnoDiGioco("FRECCIA", Direzioni.OVEST);
            case 38 -> this.partitaCorrente.turnoDiGioco("FRECCIA", Direzioni.NORD);
            case 39 -> this.partitaCorrente.turnoDiGioco("FRECCIA", Direzioni.EST);
            case 40 -> this.partitaCorrente.turnoDiGioco("FRECCIA", Direzioni.SUD);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
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
            String file = "./src/resources/RegoleWumpus.txt";
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