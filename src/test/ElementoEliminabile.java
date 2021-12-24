package test;

import java.beans.PropertyChangeSupport;

public class ElementoEliminabile extends model.elementi.ElementoEliminabile {

    public ElementoEliminabile(int latoScacchiera, String nome) throws IllegalArgumentException {
        super(latoScacchiera, nome);
    }

    public ElementoEliminabile(int latoScacchiera, int riga, int colonna, String nome) throws IllegalArgumentException, IndexOutOfBoundsException {
        super(latoScacchiera, riga, colonna, nome);
    }

    @Override
    public boolean eliminaElemento() {
        return super.eliminaElemento();
    }

    public PropertyChangeSupport getSupportoEventi() {
        return this.supportoEventi;
    }
}