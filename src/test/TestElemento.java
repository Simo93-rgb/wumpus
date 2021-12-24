package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TestElemento {

    Elemento elementoPerTest;

    @BeforeEach
    void setUp() {
        this.elementoPerTest = new Elemento(10, 5, 7);
    }

    @Test
    void testCostruttoreConLatoScacchiera1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Elemento(0));
    }

    @Test
    void testCostruttoreConLatoScacchiera1bis() {
        try {
            new Elemento(0);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertTrue(true);
        }
    }

    @ParameterizedTest
    @CsvSource({"1, true", "0, false", "-1, false"})
    void testCostruttoreConLatoScacchiera2(String latoScacchiera, String atteso) {

        try {
            new Elemento(Integer.parseInt(latoScacchiera));
            Assertions.assertTrue(Boolean.parseBoolean(atteso));
        } catch (IllegalArgumentException e) {
            Assertions.assertFalse(Boolean.parseBoolean(atteso));
        }
    }

    @ParameterizedTest
    @CsvSource({"1, true", "0, false", "-1, false"})
    void testCostruttoreConLatoScacchiera2bis(int latoScacchiera, boolean atteso) {

        try {
            new Elemento(latoScacchiera);
            Assertions.assertTrue(atteso);
        } catch (IllegalArgumentException e) {
            Assertions.assertFalse(atteso);
        }
    }

    @Test
    void testCostruttoreConLatoScacchieraEIndici1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Elemento(0, 0, 0));
    }

    @ParameterizedTest
    @CsvSource({"10, 5, 5, true", "10, 0, 0, true", "10, -1, 0, false", "10, 10, 0, false", "10, 11, 0, false", "10, 0, -1, false", "10, 0, 10, false", "10, 0, 11, false"})
    void testCostruttoreConLatoScacchieraEIndici2(String latoScacchiera, String riga, String colonna, String atteso) {
        try {
            new Elemento(Integer.parseInt(latoScacchiera), Integer.parseInt(riga), Integer.parseInt(colonna));
            Assertions.assertTrue(Boolean.parseBoolean(atteso));
        } catch (IndexOutOfBoundsException e) {
            Assertions.assertFalse(Boolean.parseBoolean(atteso));
        }
    }

    @ParameterizedTest
    @CsvSource({"10, 5, 5, true", "10, 0, 0, true", "10, -1, 0, false", "10, 10, 0, false", "10, 11, 0, false", "10, 0, -1, false", "10, 0, 10, false", "10, 0, 11, false"})
    void testCostruttoreConLatoScacchieraEIndici2bis(int latoScacchiera, int riga, int colonna, boolean atteso) {
        try {
            new Elemento(latoScacchiera, riga, colonna);
            Assertions.assertTrue(atteso);
        } catch (IndexOutOfBoundsException e) {
            Assertions.assertFalse(atteso);
        }
    }

    @Test
    void testGetLatoScacchiera() {
        Assertions.assertEquals(10, this.elementoPerTest.getLatoScacchiera());
    }

    @Test
    void testGetRiga() {
        Assertions.assertEquals(5, this.elementoPerTest.getRiga());
    }

    @Test
    void testGetColonna() {
        Assertions.assertEquals(7, this.elementoPerTest.getColonna());
    }

    @Test
    void testGetInGioco() {
        Assertions.assertTrue(this.elementoPerTest.getInGioco());
    }

    @ParameterizedTest
    @CsvSource({"10, 5, 7, true", "8, 5, 7, false", "10, 6, 7, false", "10, 5, 8, false"})
    void testEquals(String latoScacchiera, String riga, String colonna, String atteso) {
        Elemento attuale = new Elemento(Integer.parseInt(latoScacchiera), Integer.parseInt(riga), Integer.parseInt(colonna));
        Assertions.assertEquals(Boolean.parseBoolean(atteso), this.elementoPerTest.equals(attuale));
    }

    @ParameterizedTest
    @CsvSource({"10, 5, 7, true", "8, 5, 7, false", "10, 6, 7, false", "10, 5, 8, false"})
    void testEqualsConSottoclasse(String latoScacchiera, String riga, String colonna, String atteso) {
        ElementoEliminabile altro = new ElementoEliminabile(Integer.parseInt(latoScacchiera), Integer.parseInt(riga), Integer.parseInt(colonna), "Elemento eliminabile");
        Assertions.assertEquals(Boolean.parseBoolean(atteso), this.elementoPerTest.equals(altro));
    }

    @Test
    void testEqualsConElementoEliminato() {
        ElementoEliminabile altro = new ElementoEliminabile(10, 5, 7, "Elemento eliminabile");
        altro.eliminaElemento();
        Assertions.assertNotEquals(this.elementoPerTest, altro);
    }

    @Test
    void testEqualsConNull() {
        Assertions.assertNotEquals(null, this.elementoPerTest);
    }

    @Test
    void testEqualsConOggettoGenerico() {
        Assertions.assertNotEquals(this.elementoPerTest, new Object());
    }

    private static class ElementoEliminabile extends model.elementi.ElementoEliminabile {

        private ElementoEliminabile(int latoScacchiera, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
            super(latoScacchiera, riga, colonna, nome);
        }

        protected boolean eliminaElemento() {
            return super.eliminaElemento();
        }
    }
}