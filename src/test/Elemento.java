package test;

class Elemento extends model.elementi.Elemento {

    protected Elemento(int latoScacchiera) throws IllegalArgumentException {
        this(latoScacchiera, (int) (Math.random() * latoScacchiera), (int) (Math.random() * latoScacchiera));
    }

    protected Elemento(int latoScacchiera, int riga, int colonna) throws IllegalArgumentException, IndexOutOfBoundsException {
        super(latoScacchiera, riga, colonna);
    }
}