package view;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.gioco.Partita;

/**
 * Classe view per generare e gestire la GUI del gioco del Wumpus, per partite controllate tramite JComboBox.
 * 
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class WumpusViewJComboBox extends WumpusView {
	private static final long serialVersionUID = -6420766303190976502L;
	
	protected JComboBox<String> comboBoxAzione;
	protected JComboBox<String> comboBoxDirezione;
	public JButton bottoneEsegui;

	/**
     * Costruttore di WumpusView.
     * Genera l'interfaccia grafica di una Partita data.
     * 
     * @param partitaCorrente Partita di cui generare la view.
     */
	public WumpusViewJComboBox(Partita partitaCorrente) {
		super(partitaCorrente);
		
		this.setVisible(false);
		
		this.comboBoxAzione = new JComboBox<String>();
		this.comboBoxAzione.addItem("Vai");
		this.comboBoxAzione.addItem("Freccia");
		
		this.comboBoxDirezione = new JComboBox<String>();
		this.comboBoxDirezione.addItem("NORD");
		this.comboBoxDirezione.addItem("EST");
		this.comboBoxDirezione.addItem("SUD");
		this.comboBoxDirezione.addItem("OVEST");
		
		this.bottoneEsegui = new JButton("Esegui");
		
		JPanel barraComandi = new JPanel();
		barraComandi.add(this.comboBoxAzione);
		barraComandi.add(this.comboBoxDirezione);
		barraComandi.add(this.bottoneEsegui);
		
		this.add(barraComandi, BorderLayout.SOUTH);

		this.setVisible(true);
	}
	
	/**
	 * Metodo che restituisce in forma di String il messaggio risultante dalla concatenazione
	 * degli elementi selezionati nelle due JComboBox comboBoxAzione e comboBoxDirezione.
	 * 
	 * @return la String contenente il messaggio risultante.
	 */
	public String getComboBoxesMessage() {
		String res = "";
		res += this.comboBoxAzione.getSelectedItem().toString().toUpperCase();
		res += " ";
		res += this.comboBoxDirezione.getSelectedItem().toString().toUpperCase();
		return res;
	}
}