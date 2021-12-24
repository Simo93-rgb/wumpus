package test;

import model.elementi.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

class TestElementiDellaMappa {

    Mappa mappaPerTest;
    Agente agentePerTest;
    CuccioloDiWumpus cuccioloPerTest;
    Superstite superstitePerTest;
    Tesoro tesoroPerTest;
    Wumpus wumpusPerTest;


    @BeforeEach
    void setUp() throws Exception {
        this.mappaPerTest = Mappa.caricamento("mappaPerTestElementiDellaMappa.txt");
        this.agentePerTest = this.mappaPerTest.getAgente();
        this.cuccioloPerTest = this.mappaPerTest.getCuccioli()[0];
        this.superstitePerTest = this.mappaPerTest.getSuperstiti()[0];
        this.tesoroPerTest = this.mappaPerTest.getTesori()[0];
        this.wumpusPerTest = this.mappaPerTest.getWumpus();
    }

    @Test
    void testSpostaCucciolo() {
        int riga = this.cuccioloPerTest.getRiga();
        int colonna = this.cuccioloPerTest.getColonna();

        Assertions.assertEquals("Il cucciolo di Wumpus " + this.cuccioloPerTest.getNome() + " si sposta nella casella " + riga + ", " + (colonna + 1), this.cuccioloPerTest.sposta(mappaPerTest));
        Assertions.assertEquals(riga, this.cuccioloPerTest.getRiga());
        Assertions.assertEquals(colonna + 1, this.cuccioloPerTest.getColonna());
    }

    @Test
    void testIncontroConAgente() {
        Vector<PropertyChangeEvent> eventiLanciati = new Vector<>();

        PropertyChangeListener listenerDiProva = evt -> {

            if (evt.getPropertyName().equals("cucciolo")) {
                eventiLanciati.add(evt);
            }
        };

        this.cuccioloPerTest.aggiungiListener(listenerDiProva);
        this.cuccioloPerTest.incontroConAgente(this.agentePerTest);

        PropertyChangeEvent eventoLanciato = eventiLanciati.get(0);
        Assertions.assertEquals("cucciolo", eventoLanciato.getPropertyName());
        Assertions.assertFalse(this.cuccioloPerTest.getInGioco());
        Assertions.assertTrue(this.agentePerTest.getPunteggio() == 50 || this.agentePerTest.getNumFrecce() == 0);
    }

    @Test
    void testEliminaSuperstite() {
        int numFrecce = this.agentePerTest.getNumFrecce();
        int punteggio = this.agentePerTest.getPunteggio();
        String res = this.superstitePerTest.eliminaSuperstite(this.agentePerTest);

        Assertions.assertNotEquals("", res);
        Assertions.assertTrue(res.equals(this.superstitePerTest.getNome() + " ricompensa Link con una freccia.") || res.equals(this.superstitePerTest.getNome() + " ricompensa Link con " + this.superstitePerTest.getValore() + " monete d'oro."));
        Assertions.assertFalse(this.superstitePerTest.getInGioco());
        Assertions.assertTrue(this.agentePerTest.getNumFrecce() == numFrecce + 1 || this.agentePerTest.getPunteggio() == punteggio + this.superstitePerTest.getValore());
    }

    @Test
    void testEliminaTesoro() {
        int punteggio = this.agentePerTest.getPunteggio();
        String res = this.tesoroPerTest.eliminaTesoro(agentePerTest);

        Assertions.assertNotEquals("", res);
        Assertions.assertEquals("Il recupero del tesoro frutta a Link " + this.tesoroPerTest.getValore() + " monete d'oro.", res);
        Assertions.assertFalse(this.tesoroPerTest.getInGioco());
        Assertions.assertEquals(punteggio + this.tesoroPerTest.getValore(), this.agentePerTest.getPunteggio());
    }

    @Test
    void testEliminaWumpus() {
        int punteggio = this.agentePerTest.getPunteggio();

        Assertions.assertTrue(this.wumpusPerTest.eliminaWumpus(agentePerTest));
        Assertions.assertFalse(this.wumpusPerTest.getInGioco());
        Assertions.assertEquals(punteggio + this.wumpusPerTest.getValore(), this.agentePerTest.getPunteggio());
    }
}