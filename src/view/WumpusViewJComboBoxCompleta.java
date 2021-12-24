package view;

import model.elementi.*;
import model.gioco.Mappa;
import model.gioco.Partita;

import javax.swing.*;
import java.io.Serial;

/**
 * Classe view per generare e gestire la GUI con vista della mappa completa (senza fog of war) del gioco del Wumpus,
 * per partite controllate tramite JComboBox.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class WumpusViewJComboBoxCompleta extends WumpusViewJComboBox {
    @Serial
    private static final long serialVersionUID = 6065999949524257057L;

    /**
     * Costruttore di WumpusViewCompleta.
     * Genera l'interfaccia grafica di una Partita data.
     *
     * @param partitaCorrente Partita di cui generare la view completa.
     */
    public WumpusViewJComboBoxCompleta(Partita partitaCorrente) {
        super(partitaCorrente);

        this.musicaBackground.stop();
        this.setVisible(false);

        for (int i = 0; i < this.latoMappa; i++) {
            for (int j = 0; j < this.latoMappa; j++) {

                ImageIcon miaImmagine = new ImageIcon();
                PersonaggioMondoDelWumpus cursore = new PersonaggioMondoDelWumpus(this.latoMappa, i, j, "Cursore") {
                };
                Elemento contenuto = this.mondoDelWumpus.contenutoCasella(cursore);
                if (contenuto != null && contenuto.getInGioco()) {
                    if (contenuto instanceof Wumpus) miaImmagine = creaImageIcon("/pics/wumpus.gif");
                    if (contenuto instanceof Mappa.Voragine) miaImmagine = creaImageIcon("/pics/voragine.gif");
                    if (contenuto instanceof Tesoro) miaImmagine = creaImageIcon("/pics/tesoro.gif");
                    if (contenuto instanceof Superstite) miaImmagine = creaImageIcon("/pics/superstite.gif");
                }

                this.griglia[i][j].setIcon(miaImmagine);
                this.visualizzaSegnalatore(cursore);
            }
        }

        // Colloca sulla mappa l'icona per l'agente.
        this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/pics/agenteSud.gif"));

        // Colloca sulla mappa le icone per i cuccioli.
        for (CuccioloDiWumpus corrente : this.listaCuccioli) {
            if (corrente.getInGioco()) {
                this.griglia[corrente.getRiga()][corrente.getColonna()].setIcon(creaImageIcon("/pics/cuccioloWumpusSud.gif"));
            }
        }

        this.setVisible(true);
    }

    private ImageIcon creaImageIcon(String path) {
        java.net.URL imgURL = this.getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Non riesco a trovare il file: " + path);
            return null;
        }
    }
}