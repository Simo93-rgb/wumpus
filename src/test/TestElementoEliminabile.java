package test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TestElementoEliminabile {
	
	private class ElementoEliminabile extends model.elementi.ElementoEliminabile {

		private ElementoEliminabile(int latoScacchiera, String nome) throws IllegalArgumentException {
			super(latoScacchiera, nome);
		}

		private ElementoEliminabile(int latoScacchiera, int riga, int colonna, String nome)
				throws IllegalArgumentException, IndexOutOfBoundsException {
			super(latoScacchiera, riga, colonna, nome);
		}
		
		protected boolean eliminaElemento() {
			return super.eliminaElemento();
		}
		
		private PropertyChangeSupport getSupportoEventi() {
			return this.supportoEventi;
		}
	}
	
	ElementoEliminabile elementoPerTest;

	@BeforeEach
	void setUp() {
		this.elementoPerTest = new ElementoEliminabile(10, 5, 7, "Elemento di prova");
	}

	@Test
	void testCostruttoreConLatoScacchiera1() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {new ElementoEliminabile(0, "Elemento di prova");});
	}
	
	@ParameterizedTest
	@CsvSource({"1, true", "0, false", "-1, false"})
	void testCostruttoreConLatoScacchiera2(String latoScacchiera, String atteso) {
		try {
			new ElementoEliminabile(Integer.parseInt(latoScacchiera), "Elemento di prova") {};
			Assertions.assertTrue(Boolean.parseBoolean(atteso));
		}
		catch (IllegalArgumentException e) {
			Assertions.assertTrue(!(Boolean.parseBoolean(atteso)));
		}
	}
	
	@Test
	void testCostruttoreConLatoScacchieraEIndici1() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {new ElementoEliminabile(0, 0, 0, "Elemento di prova") {};}); 
	}
	
	@ParameterizedTest
	@CsvSource({"10, 5, 5, true", "10, 0, 0, true", "10, -1, 0, false", "10, 10, 0, false", "10, 11, 0, false", "10, 0, -1, false", "10, 0, 10, false", "10, 0, 11, false"})
	void testCostruttoreConLatoScacchieraEIndici2(String latoScacchiera, String riga, String colonna, String atteso) {
		try {
			new ElementoEliminabile(Integer.parseInt(latoScacchiera), Integer.parseInt(riga), Integer.parseInt(colonna), "Elemento di prova") {};
			Assertions.assertTrue(Boolean.parseBoolean(atteso));
		}
		catch (IndexOutOfBoundsException e) {
			Assertions.assertTrue(!(Boolean.parseBoolean(atteso)));
		}
	}
	
	@ParameterizedTest
	@CsvSource({"10, 5, 5, true", "10, 0, 0, true", "10, -1, 0, false", "10, 10, 0, false", "10, 11, 0, false", "10, 0, -1, false", "10, 0, 10, false", "10, 0, 11, false"})
	void testCostruttoreConLatoScacchieraEIndici3(int latoScacchiera, int riga, int colonna, boolean atteso) {
		try {
			new ElementoEliminabile(latoScacchiera, riga, colonna, "Elemento di prova") {};
			Assertions.assertTrue(atteso);
		}
		catch (IndexOutOfBoundsException e) {
			Assertions.assertTrue(!(atteso));
		}
	}
	
	@Test
	void testGetName() {
		Assertions.assertEquals("Elemento di prova", this.elementoPerTest.getNome());
	}
	
	@Test
	void testEliminaElemento1() {
		Assertions.assertTrue(this.elementoPerTest.eliminaElemento());
	}
	
	@Test
	void testEliminaElemento2() {
		this.elementoPerTest.eliminaElemento();
		Assertions.assertFalse(this.elementoPerTest.eliminaElemento());
	}
	
	@Test
	void testEliminaElemento3() {
		this.elementoPerTest.eliminaElemento();
		Assertions.assertEquals(false, this.elementoPerTest.getInGioco());
	}
	
	@Test
	void testPresenzaPropertyChangeSupport() {
		Assertions.assertNotNull(this.elementoPerTest.getSupportoEventi());
		Assertions.assertTrue(this.elementoPerTest.getSupportoEventi() instanceof PropertyChangeSupport);
	}

	@Test
	void testAggiuntaListener() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("eliminazione")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		PropertyChangeListener[] listenerRegistrati = this.elementoPerTest.getSupportoEventi().getPropertyChangeListeners();
		Assertions.assertEquals(1, listenerRegistrati.length);
	}
	
	@Test
	void testLancioEventoEliminazione1() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("eliminazione")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.eliminaElemento();
		
		Assertions.assertFalse(eventiLanciati.isEmpty());
		Assertions.assertEquals(1, eventiLanciati.size());
	}
	
	@Test
	void testLancioEventoEliminazione2() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("eliminazione")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.eliminaElemento();

		PropertyChangeEvent eventoLanciato = eventiLanciati.get(0);
		Assertions.assertEquals("eliminazione", eventoLanciato.getPropertyName());
	}
	
	@Test
	void testLancioEventoEliminazione3() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("eliminazione")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.eliminaElemento();

		PropertyChangeEvent eventoLanciato = eventiLanciati.get(0);
		Assertions.assertTrue(((model.elementi.Elemento) eventoLanciato.getOldValue()).getInGioco());
		Assertions.assertFalse(((model.elementi.Elemento) eventoLanciato.getNewValue()).getInGioco());
	}
}