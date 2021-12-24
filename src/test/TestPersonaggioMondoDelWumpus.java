package test;

import model.gioco.Direzioni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TestPersonaggioMondoDelWumpus {

    PersonaggioMondoDelWumpus elementoPerTest;

    @BeforeEach
    void setUp() {
        this.elementoPerTest = new PersonaggioMondoDelWumpus(10, 5, 7, "Elemento di prova");
    }

    @ParameterizedTest
    @CsvSource({"NORD", "SUD", "EST", "OVEST"})
    void testSposta1(Direzioni direzione) {
        Assertions.assertTrue(elementoPerTest.sposta(direzione));
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 0", "0, 2"})
    void testSposta2(int spostamentoRiga, int spostamentoColonna) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.elementoPerTest.sposta(spostamentoRiga, spostamentoColonna));
    }

    private static class PersonaggioMondoDelWumpus extends model.elementi.PersonaggioMondoDelWumpus {

        private PersonaggioMondoDelWumpus(int latoMappa, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
            super(latoMappa, riga, colonna, nome);
        }

        protected boolean sposta(int spostamentoRiga, int spostamentoColonna) throws IllegalArgumentException {
            return super.sposta(spostamentoRiga, spostamentoColonna);
        }
    }
}