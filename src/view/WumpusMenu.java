package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * Classe che permette la creazione e la lettura dei valori del menu' iniziale del gioco del Wumpus.
 * 
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class WumpusMenu extends JFrame {
	private static final long serialVersionUID = 4790426944084044672L;
	
    public JMenuItem menuItemCarica;
	public JMenuItem menuItemEsci;
	private final JSpinner spinnerPerLatoMappa;
	private final JSpinner spinnerPerProbabilitaVoragine;
	private final JSpinner spinnerPerNumeroCuccioli;
	private final JSpinner spinnerPerNumeroSuperstiti;
	private final JSpinner spinnerPerNumeroTesori;
	public JComboBox<String> jComboBoxTipoComandi;
	public JCheckBox checkBoxPerTemporizzazioneTurno;
	public JCheckBox checkBoxPerVisualizzazioneMappaCompleta;
	public JButton bottoneInizioPartita;

	/**
	 * Costruttore di WumpusMenu.
	 * Genera una finestra interattiva per la personalizzazione della Partita.
	 */
	public WumpusMenu() {
		super("Benvenuto nel mondo del Wumpus!");
		
		this.setSize(400, 300);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        
		JMenuBar barraMenu = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuItemCarica = new JMenuItem("Carica");
		menuItemEsci = new JMenuItem("Esci");
		barraMenu.add(menuFile);
		menuFile.add(menuItemCarica);
		menuFile.addSeparator();
		menuFile.add(menuItemEsci);
		
		this.add(barraMenu, BorderLayout.NORTH);
		
		JPanel pannelloSpinner = new JPanel(new GridLayout(8, 1));

		SpinnerModel modelloPerLatoMappa = new SpinnerNumberModel(10, 5, 15, 1); 
		this.spinnerPerLatoMappa = new JSpinner(modelloPerLatoMappa);
		JPanel rigaPerLatoMappa = new JPanel(new GridLayout(1, 2));
		rigaPerLatoMappa.add(new JLabel("Lato mappa: "));
		rigaPerLatoMappa.add(this.spinnerPerLatoMappa);
		pannelloSpinner.add(rigaPerLatoMappa);

		SpinnerModel modelloPerProbabilitaVoragine = new SpinnerNumberModel(20, 10, 40, 1);
		this.spinnerPerProbabilitaVoragine = new JSpinner(modelloPerProbabilitaVoragine);
		JPanel rigaPerProbabilitaVoragine = new JPanel(new GridLayout(1, 2));
		rigaPerProbabilitaVoragine.add(new JLabel("Probabilit� voragini (%): "));
		rigaPerProbabilitaVoragine.add(this.spinnerPerProbabilitaVoragine);
		pannelloSpinner.add(rigaPerProbabilitaVoragine);
		
		SpinnerModel modelloPerNumeroTesori = new SpinnerNumberModel(3, 1, 5, 1);
		this.spinnerPerNumeroTesori = new JSpinner(modelloPerNumeroTesori);
		JPanel rigaPerNumeroTesori = new JPanel(new GridLayout(1, 2));
		rigaPerNumeroTesori.add(new JLabel("Numero tesori: "));
		rigaPerNumeroTesori.add(this.spinnerPerNumeroTesori);
		pannelloSpinner.add(rigaPerNumeroTesori);

		SpinnerModel modelloPerNumeroSuperstiti = new SpinnerNumberModel(2, 1, 5, 1);
		this.spinnerPerNumeroSuperstiti = new JSpinner(modelloPerNumeroSuperstiti);
		JPanel rigaPerNumeroSuperstiti = new JPanel(new GridLayout(1, 2));
		rigaPerNumeroSuperstiti.add(new JLabel("Numero superstiti: "));
		rigaPerNumeroSuperstiti.add(this.spinnerPerNumeroSuperstiti);
		pannelloSpinner.add(rigaPerNumeroSuperstiti);
		
		SpinnerModel modelloPerNumeroCuccioli = new SpinnerNumberModel(2, 1, 5, 1);
		this.spinnerPerNumeroCuccioli = new JSpinner(modelloPerNumeroCuccioli);
		JPanel rigaPerNumeroCuccioli = new JPanel(new GridLayout(1, 2));
		rigaPerNumeroCuccioli.add(new JLabel("Numero cuccioli: "));
		rigaPerNumeroCuccioli.add(this.spinnerPerNumeroCuccioli);
		pannelloSpinner.add(rigaPerNumeroCuccioli);

		this.jComboBoxTipoComandi = new JComboBox<String>();
		this.jComboBoxTipoComandi.addItem("WASD");
		this.jComboBoxTipoComandi.addItem("JComboBox");
		JPanel rigaPerTipoComandi = new JPanel(new GridLayout(1, 2));
		rigaPerTipoComandi.add(new JLabel("Tipo comandi: "));
		rigaPerTipoComandi.add(this.jComboBoxTipoComandi);
		pannelloSpinner.add(rigaPerTipoComandi);
		
		this.checkBoxPerTemporizzazioneTurno = new JCheckBox("Temporizzazione turno");
		JPanel rigaPerTemporizzazioneTurno = new JPanel();
		rigaPerTemporizzazioneTurno.add(this.checkBoxPerTemporizzazioneTurno);
		pannelloSpinner.add(rigaPerTemporizzazioneTurno);
		
		this.checkBoxPerVisualizzazioneMappaCompleta = new JCheckBox("Visualizzazione mappa completa");
		JPanel rigaPerVisualizzazioneMappaCompleta = new JPanel();
		rigaPerVisualizzazioneMappaCompleta.add(this.checkBoxPerVisualizzazioneMappaCompleta);
		pannelloSpinner.add(rigaPerVisualizzazioneMappaCompleta);
		
		this.add(pannelloSpinner, BorderLayout.CENTER);

		this.bottoneInizioPartita = new JButton("Avvio partita");
		JPanel pannelloBasso = new JPanel();
		pannelloBasso.add(this.bottoneInizioPartita);
		this.add(pannelloBasso, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}

	/**
	 * Metodo che legge i valori dei parametri inseriti nel menu' di personalizzazione.
	 * 
	 * @return Array dei parametri della Partita.
	 */
	public int[] leggiSpinner() {
		int[] valoriSpinner = new int[5];
		
		valoriSpinner[0] = (int) this.spinnerPerLatoMappa.getValue();
		valoriSpinner[1] = (int) this.spinnerPerProbabilitaVoragine.getValue();
		valoriSpinner[2] = (int) this.spinnerPerNumeroTesori.getValue();
		valoriSpinner[3] = (int) this.spinnerPerNumeroSuperstiti.getValue();
		valoriSpinner[4] = (int) this.spinnerPerNumeroCuccioli.getValue();
		
		return valoriSpinner;
	}
}