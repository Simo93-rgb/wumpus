package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.gioco.Mappa;
import view.WumpusMenu;

/**
 * Classe controller del WumpusMenu del gioco del Wumpus.
 * 
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class WumpusMenuController implements ActionListener {
	private final WumpusMenu menu;

	/**
	 * Costruttore di WumpusMenuController.
	 * Genera un nuovo WumpusMenu.
	 */
	public WumpusMenuController() {
		this.menu = new WumpusMenu();
		this.menu.bottoneInizioPartita.addActionListener(this);
		this.menu.menuItemCarica.addActionListener(this);
		this.menu.menuItemEsci.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getActionCommand().equals("Avvio partita")) {
			this.menu.setVisible(false);
			
			int[] valoriLetti = this.menu.leggiSpinner();
			boolean temporizzazioneTurno = this.menu.checkBoxPerTemporizzazioneTurno.isSelected();
			boolean visualizzaMappaCompleta = this.menu.checkBoxPerVisualizzazioneMappaCompleta.isSelected();
			
			if(this.menu.jComboBoxTipoComandi.getSelectedItem().toString().equals("WASD")) {
				new WumpusController(temporizzazioneTurno, visualizzaMappaCompleta, valoriLetti);
			}
			else {
				new WumpusControllerJComboBox(temporizzazioneTurno, visualizzaMappaCompleta, valoriLetti);
			}
		}
		
		if(evt.getActionCommand().equals("Carica")) {
			this.menu.setVisible(false);
			
			Mappa mappa = null;
			
			try {
				mappa = Mappa.caricamento();
			
				boolean temporizzazioneTurno = this.menu.checkBoxPerTemporizzazioneTurno.isSelected();
				boolean visualizzaMappaCompleta = this.menu.checkBoxPerVisualizzazioneMappaCompleta.isSelected();
				
				if(this.menu.jComboBoxTipoComandi.getSelectedItem().toString().equals("WASD")) {
					new WumpusController(temporizzazioneTurno, visualizzaMappaCompleta, mappa);
				}
				else {
					new WumpusControllerJComboBox(temporizzazioneTurno, visualizzaMappaCompleta, mappa);
				}
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(evt.getActionCommand().equals("Esci")) {
			System.exit(0);
		}
	}
	
	/*public static void main(String[] args) {
		new WumpusMenuController();
	}*/
}