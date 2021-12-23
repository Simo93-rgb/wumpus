package test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TestElementoMobile {
	
	private class ElementoMobile extends model.elementi.ElementoMobile {
		
		private ElementoMobile(int latoScacchiera, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
			super(latoScacchiera, riga, colonna, nome);
		}		
		
		protected boolean sposta(int spostamentoRiga, int spostamentoColonna) {
			return super.sposta(spostamentoRiga, spostamentoColonna);
		}
	}
	
	ElementoMobile elementoPerTest;

	@BeforeEach
	void setUp() {
		this.elementoPerTest = new ElementoMobile(10, 5, 7, "Elemento di prova");
	}

	@Test
	void testSposta1() {
		Assertions.assertTrue(this.elementoPerTest.sposta(1, 1));
	}
	
	@ParameterizedTest
	@CsvSource({"1, -8", "1, 3", "-6, 1"})
	void testSposta2(int spostamentoRiga, int spostamentoColonna) {
		Assertions.assertFalse(this.elementoPerTest.sposta(spostamentoRiga, spostamentoColonna));
	}
	
	@Test
	void testLancioEventoSpostamento1() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("spostamento")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.sposta(1,1);
		
		Assertions.assertFalse(eventiLanciati.isEmpty());
		Assertions.assertEquals(1, eventiLanciati.size());
	}
	
	@Test
	void testLancioEventoSpostamento2() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("spostamento")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.sposta(1,1);
		
		PropertyChangeEvent eventoLanciato = eventiLanciati.get(0);
		Assertions.assertEquals("spostamento", eventoLanciato.getPropertyName());
	}
	
	@Test
	void testLancioEventoSpostamento3() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("spostamento")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.sposta(1,1);
		
		PropertyChangeEvent eventoLanciato = eventiLanciati.get(0);
		Assertions.assertEquals(((model.elementi.Elemento) eventoLanciato.getOldValue()).getRiga(), 5);
		Assertions.assertEquals(((model.elementi.Elemento) eventoLanciato.getNewValue()).getRiga(), 6);
		Assertions.assertEquals(((model.elementi.Elemento) eventoLanciato.getOldValue()).getColonna(), 7);		
		Assertions.assertEquals(((model.elementi.Elemento) eventoLanciato.getNewValue()).getColonna(), 8);
	}
}