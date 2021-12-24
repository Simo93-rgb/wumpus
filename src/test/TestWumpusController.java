package test;

import controller.WumpusController;
import model.elementi.Agente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.WumpusView;

import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Vector;

class TestWumpusController {

    WumpusController controllerPerTest;
    Mappa mappaPerTest;

    @BeforeEach
    void setUp() throws Exception {
        this.mappaPerTest = new test.Mappa(new File("mappaPerTestPartitaDescrizioneCasellaAgente.txt"));
        this.controllerPerTest = new WumpusController(false, false, this.mappaPerTest);
    }

    @Test
    void testKeyTyped() {
        Assertions.assertEquals(new Agente(this.mappaPerTest.getLatoMappa(), 0, 3), this.controllerPerTest.getPartita().getAgente());
        Vector<PropertyChangeEvent> eventiLanciati = new Vector<>();
        WumpusView vista = this.controllerPerTest.getView();
        PropertyChangeListener listenerDiProva = evt -> {
            if (evt.getPropertyName().equals("spostamento")) {
                eventiLanciati.add(evt);
            }
        };

        this.controllerPerTest.getPartita().getAgente().aggiungiListener(listenerDiProva);

        this.controllerPerTest.keyTyped(new KeyEvent(vista, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'a'));

        Assertions.assertEquals(eventiLanciati.get(0).getPropertyName(), "spostamento");
        Assertions.assertNotEquals(new Agente(this.mappaPerTest.getLatoMappa(), 0, 3), this.controllerPerTest.getPartita().getAgente());
        Assertions.assertEquals(new Agente(this.mappaPerTest.getLatoMappa(), 0, 2), this.controllerPerTest.getPartita().getAgente());
    }

    @Test
    void testKeyPressed() {
        Assertions.assertEquals(new Agente(this.mappaPerTest.getLatoMappa(), 0, 3), this.controllerPerTest.getPartita().getAgente());
        Vector<PropertyChangeEvent> eventiLanciati = new Vector<>();
        WumpusView vista = this.controllerPerTest.getView();
        Integer numFrecce = this.controllerPerTest.getPartita().getAgente().getNumFrecce();
        PropertyChangeListener listenerDiProva = evt -> {
            if (evt.getPropertyName().equals("freccia")) {
                eventiLanciati.add(evt);
            }
        };

        this.controllerPerTest.getPartita().getAgente().aggiungiListener(listenerDiProva);

        this.controllerPerTest.keyPressed(new KeyEvent(vista, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, (char) 38));

        Assertions.assertEquals(eventiLanciati.get(0).getPropertyName(), "freccia");
        Assertions.assertNotEquals(numFrecce, this.controllerPerTest.getPartita().getAgente().getNumFrecce());
    }
}