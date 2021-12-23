package test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.gioco.Direzioni;

class TestAgente {
	
	private class Agente extends model.elementi.Agente {
		
		private Agente(int latoMappa, int riga, int colonna, String nome, boolean inGioco, int numFrecce, int punteggio)
				throws IllegalArgumentException, IndexOutOfBoundsException {
			super(latoMappa, riga, colonna, nome, inGioco, numFrecce, punteggio);
		}
		
		protected void variaPunteggio(int variazione) {
			super.variaPunteggio(variazione);
		}
	}

	Agente elementoPerTest;
	
	@BeforeEach
	void setUp() {
		elementoPerTest = new Agente(10, 5, 7, "Elemento di prova", true, 1, 100);
	}	

	@Test
	void testScoccaFreccia1() {
		Assertions.assertEquals(elementoPerTest.scoccaFreccia(Direzioni.NORD), new Elemento(10, 4, 7));
	}

	@Test
	void testScoccaFreccia2() {
		Assertions.assertEquals(elementoPerTest.scoccaFreccia(Direzioni.SUD), new Elemento(10, 6, 7));
	}
	
	@Test
	void testScoccaFreccia3() {
		Assertions.assertEquals(elementoPerTest.scoccaFreccia(Direzioni.EST), new Elemento(10, 5, 8));
	}
	
	@Test
	void testScoccaFreccia4() {
		Assertions.assertEquals(elementoPerTest.scoccaFreccia(Direzioni.OVEST), new Elemento(10, 5, 6));
	}
	
	@Test
	void testScoccaFreccia5() {
		Assertions.assertEquals(elementoPerTest.scoccaFreccia(Direzioni.OVEST), new Elemento(10, 5, 6));
		Assertions.assertEquals(elementoPerTest.getNumFrecce(), 0);
		Assertions.assertNull(elementoPerTest.scoccaFreccia(Direzioni.OVEST));
		Assertions.assertEquals(elementoPerTest.getNumFrecce(), 0);
	}	
		
	@Test
	void testLancioEventoFreccia1() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("freccia")) {
					eventiLanciati.add(evt);
				}
				
				if(evt.getPropertyName().equals("numFrecce")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.scoccaFreccia(Direzioni.NORD);
		
		Assertions.assertFalse(eventiLanciati.isEmpty());
		Assertions.assertEquals(2, eventiLanciati.size());
	}
	
	@Test
	void testLancioEventoFreccia2() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("freccia")) {
					eventiLanciati.add(evt);
				}
				
				if(evt.getPropertyName().equals("numFrecce")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.scoccaFreccia(Direzioni.NORD);
		
		PropertyChangeEvent eventoLanciato1 = eventiLanciati.get(1);
		Assertions.assertEquals("freccia", eventoLanciato1.getPropertyName());
		
		PropertyChangeEvent eventoLanciato2 = eventiLanciati.get(0);
		Assertions.assertEquals("numFrecce", eventoLanciato2.getPropertyName());
		Assertions.assertEquals(eventoLanciato2.getOldValue(), 1);
		Assertions.assertEquals(eventoLanciato2.getNewValue(), 0);
		Assertions.assertEquals(eventoLanciato2.getNewValue(), this.elementoPerTest.getNumFrecce());
	}
	
	@Test
	void testVariaPunteggio() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("punteggio")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.variaPunteggio(100);
		
		PropertyChangeEvent eventoLanciato = eventiLanciati.get(0);
		Assertions.assertEquals("punteggio", eventoLanciato.getPropertyName());
		Assertions.assertEquals(eventoLanciato.getOldValue(), 100);
		Assertions.assertEquals(eventoLanciato.getNewValue(), 200);
		Assertions.assertEquals(eventoLanciato.getNewValue(), this.elementoPerTest.getPunteggio());
	}	
	
	@Test
	void testEliminaAgenteWumpus() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("uccisoDaWumpus")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.eliminaAgenteWumpus();
		
		PropertyChangeEvent eventoLanciato = eventiLanciati.get(0);
		Assertions.assertEquals("uccisoDaWumpus", eventoLanciato.getPropertyName());
		Assertions.assertTrue((boolean) eventoLanciato.getOldValue());
		Assertions.assertFalse((boolean) eventoLanciato.getNewValue());
		Assertions.assertEquals(eventoLanciato.getNewValue(), this.elementoPerTest.getInGioco());
	}	
	
	@Test
	void testEliminaAgenteVoragine() {
		Vector<PropertyChangeEvent> eventiLanciati = new Vector<PropertyChangeEvent>();
		
		PropertyChangeListener listenerDiProva = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("uccisoDaVoragine")) {
					eventiLanciati.add(evt);
				}
			}
		};
		
		this.elementoPerTest.aggiungiListener(listenerDiProva);
		this.elementoPerTest.eliminaAgenteVoragine();
		
		PropertyChangeEvent eventoLanciato = eventiLanciati.get(0);
		Assertions.assertEquals("uccisoDaVoragine", eventoLanciato.getPropertyName());
		Assertions.assertTrue((boolean) eventoLanciato.getOldValue());
		Assertions.assertFalse((boolean) eventoLanciato.getNewValue());
		Assertions.assertEquals(eventoLanciato.getNewValue(), this.elementoPerTest.getInGioco());
	}	
}
