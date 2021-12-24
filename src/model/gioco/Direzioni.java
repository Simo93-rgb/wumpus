package model.gioco;

/**
 * Enumerativo dei possibili spostamenti dei personaggi sulla mappa di gioco.
 * Indica la variazione di coordinate da applicare al personaggio.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public enum Direzioni {
    NORD(-1, 0), EST(0, 1), SUD(1, 0), OVEST(0, -1);

    private final int spostamentoRiga;
    private final int spostamentoColonna;

    Direzioni(int spostamentoRiga, int spostamentoColonna) {
        this.spostamentoRiga = spostamentoRiga;
        this.spostamentoColonna = spostamentoColonna;
    }

    /**
     * Metodo statico che sceglie casualmente una direzione di spostamento.
     * Usato per gestire gli spostamenti dei cuccioli di Wumpus.
     *
     * @return Direzione casuale di spostamento.
     */
    public static Direzioni spostamentoRandom() {
        Direzioni spostamento = null;
        int selettore = (int) (Math.random() * 4);

        switch (selettore) {
            case 0 -> spostamento = Direzioni.NORD;
            case 1 -> spostamento = Direzioni.EST;
            case 2 -> spostamento = Direzioni.SUD;
            case 3 -> spostamento = Direzioni.OVEST;
        }

        return spostamento;
    }

    /**
     * Metodo che restituisce la distanza in righe dello spostamento verticale.
     *
     * @return <em>-1</em> per uno spostamento verso l'alto,
     * <em>1</em> per uno spostamento verso il basso,
     * <em>0</em> altrimenti.
     */
    public int getSpostamentoRiga() {
        return this.spostamentoRiga;
    }

    /**
     * Metodo che restituisce la distanza in colonne dello spostamento orizzontale.
     *
     * @return <em>-1</em> per uno spostamento verso sinistra,
     * <em>1</em> per uno spostamento verso destra,
     * <em>0</em> altrimenti.
     */
    public int getSpostamentoColonna() {
        return this.spostamentoColonna;
    }
}