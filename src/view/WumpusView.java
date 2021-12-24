package view;

import model.elementi.*;
import model.gioco.Mappa;
import model.gioco.Partita;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.LinkedList;

/**
 * Classe view per generare e gestire la GUI del gioco del Wumpus, per partite controllate tramite tastiera.
 *
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class WumpusView extends JFrame implements PropertyChangeListener {
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    @Serial
    private static final long serialVersionUID = 1622051184356119591L;
    private final Dimension screenSize;
    public JCheckBoxMenuItem menuCheckBoxMusica;
    public JMenuItem menuItemSalva;
    public JMenuItem menuItemEsci;
    public Clip musicaBackground;
    public JMenuItem regole;
    protected Partita partitaCorrente;
    protected Mappa mondoDelWumpus;
    protected Agente giocatore;
    protected Wumpus ilWumpus;
    protected CuccioloDiWumpus[] listaCuccioli;
    protected Tesoro[] listaTesori;
    protected Superstite[] listaSuperstiti;
    protected int latoMappa;
    protected JLabel[][] griglia; // griglia e' un array bidimensionale di JLabel
    protected JLabel etichettaPunteggio;
    protected JLabel etichettaFrecce;


    /**
     * Costruttore di WumpusView.
     * Genera l'interfaccia grafica di una Partita data.
     *
     * @param partitaCorrente Partita di cui generare la view.
     */
    public WumpusView(Partita partitaCorrente) {
        super("Wumpus World");

        this.partitaCorrente = partitaCorrente;
        this.mondoDelWumpus = partitaCorrente.getMappaDiGioco();
        this.latoMappa = this.mondoDelWumpus.getLatoMappa();
        this.giocatore = this.mondoDelWumpus.getAgente();
        this.ilWumpus = this.mondoDelWumpus.getWumpus();
        this.listaCuccioli = this.mondoDelWumpus.getCuccioli();
        this.listaTesori = this.mondoDelWumpus.getTesori();
        this.listaSuperstiti = this.mondoDelWumpus.getSuperstiti();
        this.musicaBackground = this.audioGioco(new File("./src/resources/audio/deathMountain.wav"), true, -20);

        this.setSize(WumpusView.WIDTH, WumpusView.HEIGHT);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (this.screenSize.width - WumpusView.WIDTH) / 2;
        int y = (this.screenSize.height - WumpusView.HEIGHT) / 2;
        this.setLocation(x, y);

        JMenuBar barraMenu = new JMenuBar();
        JMenu menuGioco = new JMenu("Gioco");
        JMenu menuAbout = new JMenu("About");
        this.menuCheckBoxMusica = new JCheckBoxMenuItem("Musica di sottofondo", true);
        this.menuItemSalva = new JMenuItem("Salva");
        this.menuItemEsci = new JMenuItem("Esci");
        this.regole = new JMenuItem("Regole");

        menuGioco.add(this.menuCheckBoxMusica);
        menuGioco.addSeparator();
        menuGioco.add(this.menuItemSalva);
        menuGioco.addSeparator();
        menuGioco.add(this.menuItemEsci);

        menuAbout.add(this.regole);

        barraMenu.add(menuGioco);
        barraMenu.add(menuAbout);

        this.setJMenuBar(barraMenu);

        JPanel indicatoriPartita = new JPanel(new GridLayout(1, 2));
        this.etichettaPunteggio = new JLabel("Punteggio: " + this.giocatore.getPunteggio());
        this.etichettaFrecce = new JLabel("Frecce: " + this.giocatore.getNumFrecce());
        indicatoriPartita.add(this.etichettaPunteggio);
        indicatoriPartita.add(this.etichettaFrecce);
        this.add(indicatoriPartita, BorderLayout.NORTH);

        JPanel planciaDiGioco = new JPanel();
        planciaDiGioco.setLayout(new GridLayout(this.latoMappa, this.latoMappa));

        this.griglia = new JLabel[this.latoMappa][this.latoMappa];

        Border bordo = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);

        for (int i = 0; i < this.latoMappa; i++) {
            for (int j = 0; j < this.latoMappa; j++) {

                this.griglia[i][j] = new JLabel();
                this.griglia[i][j].setPreferredSize(new Dimension(10, 10));
                this.griglia[i][j].setHorizontalAlignment(JLabel.CENTER);
                this.griglia[i][j].setVerticalAlignment(JLabel.CENTER);
                this.griglia[i][j].setBackground(Color.GRAY);
                this.griglia[i][j].setOpaque(true);
                this.griglia[i][j].setBorder(bordo);
                planciaDiGioco.add(this.griglia[i][j]);
            }
        }

        this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteSud.gif"));
        this.visualizzaSegnalatore(this.giocatore);

        this.add(planciaDiGioco, BorderLayout.CENTER);

        this.setVisible(true);

        // Rende la GUI un Observer degli elementi del gioco
        // (agente, Wumpus, cuccioli di Wumpus, tesori e superstiti)
        this.giocatore.aggiungiListener(this);

        this.ilWumpus.aggiungiListener(this);

        for (CuccioloDiWumpus corrente : this.listaCuccioli) {
            corrente.aggiungiListener(this);
        }

        for (Tesoro corrente : this.listaTesori) {
            corrente.aggiungiListener(this);
        }

        for (Superstite corrente : this.listaSuperstiti) {
            corrente.aggiungiListener(this);
        }

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

    private Clip audioGioco(File fileAudio, boolean musicaBackground, float volume) {
        try {
            Clip clip = AudioSystem.getClip();

            clip.open(AudioSystem.getAudioInputStream(fileAudio));

            this.regolaVolume(clip, volume);

            clip.start();
            if (musicaBackground) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void regolaVolume(Clip clip, float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("punteggio")) {
            this.etichettaPunteggio.setText("Punteggio: " + evt.getNewValue());
        }

        if (evt.getPropertyName().equals("numFrecce")) {
            this.etichettaFrecce.setText("Frecce: " + evt.getNewValue());
        }

        if (evt.getPropertyName().equals("freccia")) {
            this.audioGioco(new File("./src/resources/audio/arrowShoot.wav"), false, -10);
            if (evt.getNewValue().equals("NORD")) {
                this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteArcoNord.gif"));
            } else if (evt.getNewValue().equals("EST")) {
                this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteArcoEst.gif"));
            } else if (evt.getNewValue().equals("SUD")) {
                this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteArcoSud.gif"));
            } else if (evt.getNewValue().equals("OVEST")) {
                this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteArcoOvest.gif"));
            }
        }

        if (evt.getPropertyName().equals("eliminazione")) {
            if (evt.getSource() instanceof Tesoro) {
                this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteConTesoro.gif"));
                this.audioGioco(new File("./src/resources/audio/treasureChest.wav"), false, -10);
            } else if (evt.getSource() instanceof Superstite) {
                this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteConSuperstite.gif"));
                this.audioGioco(new File("./src/resources/audio/zeldaHey.wav"), false, -10);
            } else if (evt.getSource() instanceof Wumpus) {
                this.griglia[this.ilWumpus.getRiga()][this.ilWumpus.getColonna()].setIcon(creaImageIcon("/resources/pics/wumpusUcciso.gif"));
                this.musicaBackground.stop();
                this.audioGioco(new File("./src/resources/audio/ganonDie.wav"), false, -10);
            }
        }

        if (evt.getPropertyName().equals("uccisioneSuperstite")) {
            this.griglia[((Elemento) evt.getOldValue()).getRiga()][((Elemento) evt.getOldValue()).getColonna()].setIcon(creaImageIcon("/resources/pics/superstiteUcciso.gif"));
            this.audioGioco(new File("./src/resources/audio/zeldaScream.wav"), false, -10);
        }

        if (evt.getPropertyName().equals("uccisioneCucciolo")) {
            this.griglia[((Elemento) evt.getOldValue()).getRiga()][((Elemento) evt.getOldValue()).getColonna()].setIcon(creaImageIcon("/resources/pics/cuccioloUcciso.gif"));
            this.audioGioco(new File("./src/resources/audio/cuccioloScream.wav"), false, -10);
        }


        if (evt.getPropertyName().equals("spostamento") && evt.getSource() instanceof Agente) {
            Elemento oldPosizione = (Elemento) evt.getOldValue();
            Elemento newPosizione = (Elemento) evt.getNewValue();

            this.griglia[oldPosizione.getRiga()][oldPosizione.getColonna()].setIcon(null);

            if (oldPosizione.getRiga() < newPosizione.getRiga()) {
                this.griglia[newPosizione.getRiga()][newPosizione.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteSud.gif"));
            } else if (oldPosizione.getRiga() > newPosizione.getRiga()) {
                this.griglia[newPosizione.getRiga()][newPosizione.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteNord.gif"));
            } else if (oldPosizione.getColonna() < newPosizione.getColonna()) {
                this.griglia[newPosizione.getRiga()][newPosizione.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteEst.gif"));
            } else if (oldPosizione.getColonna() > newPosizione.getColonna()) {
                this.griglia[newPosizione.getRiga()][newPosizione.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteOvest.gif"));
            }

            this.visualizzaSegnalatore((PersonaggioMondoDelWumpus) newPosizione);
        }

        if (evt.getPropertyName().equals("spostamento") && evt.getSource() instanceof CuccioloDiWumpus) {
            Elemento oldPosizione = (Elemento) evt.getOldValue();
            Elemento newPosizione = (Elemento) evt.getNewValue();

            //Cancella la precedente posizione del cucciolo in ogni caso...
            this.griglia[oldPosizione.getRiga()][oldPosizione.getColonna()].setIcon(null);

			/*
			... invece mette l'icona del cucciolo nella nuova posizione solo se essa e' gia' stata
			 scoperta dall'agente (ovvero se il fondale della casella non � grigio)
			*/
            if (this.griglia[newPosizione.getRiga()][newPosizione.getColonna()].getBackground() != Color.GRAY) {
                if (oldPosizione.getRiga() < newPosizione.getRiga()) {
                    this.griglia[newPosizione.getRiga()][newPosizione.getColonna()].setIcon(creaImageIcon("/resources/pics/cuccioloWumpusSud.gif"));
                } else if (oldPosizione.getRiga() > newPosizione.getRiga()) {
                    this.griglia[newPosizione.getRiga()][newPosizione.getColonna()].setIcon(creaImageIcon("/resources/pics/cuccioloWumpusNord.gif"));
                } else if (oldPosizione.getColonna() < newPosizione.getColonna()) {
                    this.griglia[newPosizione.getRiga()][newPosizione.getColonna()].setIcon(creaImageIcon("/resources/pics/cuccioloWumpusEst.gif"));
                } else if (oldPosizione.getColonna() > newPosizione.getColonna()) {
                    this.griglia[newPosizione.getRiga()][newPosizione.getColonna()].setIcon(creaImageIcon("/resources/pics/cuccioloWumpusOvest.gif"));
                }
            }

        }

        if (evt.getPropertyName().equals("uccisoDaVoragine")) {
            this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteUccisoDaVoragine.gif"));
            this.musicaBackground.stop();
            this.audioGioco(new File("./src/resources/audio/linkFall.wav"), false, -10);
        }

        if (evt.getPropertyName().equals("uccisoDaWumpus")) {
            this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteUccisoDaWumpus.gif"));
            this.musicaBackground.stop();
            this.audioGioco(new File("./src/resources/audio/linkDie.wav"), false, -10);
        }

        if (evt.getPropertyName().equals("cucciolo")) {
            this.griglia[this.giocatore.getRiga()][this.giocatore.getColonna()].setIcon(creaImageIcon("/resources/pics/agenteConCucciolo.gif"));
            this.audioGioco(new File("./src/resources/audio/linkHurt.wav"), false, -10);
        }
    }

    protected void visualizzaSegnalatore(PersonaggioMondoDelWumpus cursore) {
        boolean puzza = this.mondoDelWumpus.controllaPuzza(cursore);
        boolean brezza = this.mondoDelWumpus.controllaBrezza(cursore);

		/*
		 Se nella casella ci sono sia la puzza del Wumpus che la brezza proveniente da una
		 voragine setto il colore del lable a black,
		*/
        if (puzza && brezza) {
            this.griglia[cursore.getRiga()][cursore.getColonna()].setBackground(Color.BLACK);
        }
        // se nella casella c'e' la puzza del Wumpus setto il colore della label a red,
        else if (puzza) {
            this.griglia[cursore.getRiga()][cursore.getColonna()].setBackground(Color.RED);
        }
        // se nella casella c'e' la brezza proveniente da una voragine setto il colore della label a blu.
        else if (brezza) {
            this.griglia[cursore.getRiga()][cursore.getColonna()].setBackground(Color.BLUE);
        }
		/*
		 altrimenti, se non c'e' nulla, imposta lo sfondo della casella a white per indicare
		 che e' una casella gia' visitata
		*/
        else {
            this.griglia[cursore.getRiga()][cursore.getColonna()].setBackground(Color.WHITE);
        }
    }

    public void salvataggioCaselleScoperte() {
		/*
		 Il file viene aperto in modalita' append: si esegue il salvataggio delle
		 caselle gia' visitate dopo aver salvato i dati della mappa, aggiungendo
		 al fondo del file l'elenco delle caselle gia' scoperte
		*/
        try (BufferedWriter salvataggio = new BufferedWriter(new FileWriter("salvataggioWumpus.txt", true))) {
            salvataggio.write("\nCaselle scoperte\n");
            salvataggio.write("================\n");
            for (int i = 0; i < this.latoMappa; i++) {
                for (int j = 0; j < this.latoMappa; j++) {
					/*
					 Se il colore di sfondo della casella e' diverso da GRAY, ovvero
					 se la casella e' gia' stata scoperta...
					*/
                    if (this.griglia[i][j].getBackground() != Color.GRAY) {
						/*
						 ... Salva sul file i dati della casella (una casella per riga) nel formato
						 indice di riga:indice di colonna:componente red del colore:componente green del colore:componente blue del colore
						*/
                        salvataggio.write(i + ":" + j + ":" + this.griglia[i][j].getBackground().getRed() + ":" + this.griglia[i][j].getBackground().getGreen() + ":" + this.griglia[i][j].getBackground().getBlue() + "\n");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Salvataggio non riuscito");
        }
    }

    public void caricamentoCaselleScoperte() throws IllegalArgumentException, IOException {
        String prossimaLinea;
        String[] contenutoLinea;
        File f = new File("salvataggioWumpus.txt");

        try (BufferedReader caricamento = new BufferedReader(new FileReader(f))) {
            prossimaLinea = caricamento.readLine();

            while (prossimaLinea != null && !(prossimaLinea.equals("Caselle scoperte"))) {
                prossimaLinea = caricamento.readLine();
            }

            if (prossimaLinea != null) {

                caricamento.readLine();
                prossimaLinea = caricamento.readLine();

                while (prossimaLinea != null) {
                    contenutoLinea = prossimaLinea.split(":");

                    if (contenutoLinea.length != 5) {
                        throw new IllegalArgumentException("Formato file non valido");
                    }

                    this.griglia[Integer.parseInt(contenutoLinea[0])][Integer.parseInt(contenutoLinea[1])].setBackground(new Color(Integer.parseInt(contenutoLinea[2]), Integer.parseInt(contenutoLinea[3]), Integer.parseInt(contenutoLinea[4])));
                    prossimaLinea = caricamento.readLine();
                }
            } else {
                throw new IllegalArgumentException("Formato file non valido");
            }
        } catch (FileNotFoundException e) {
            // La cattura dell'eccezione rilancia l'eccezione al chiamante
            throw new FileNotFoundException("Il file " + f.getName() + " non esiste.");
        } catch (IOException e) {
            // La cattura dell'eccezione rilancia l'eccezione al chiamante
            throw new IOException("Caricamento non riuscito.");
        }
    }

    public void visualizzaRegole(LinkedList<String> rules) {
        //FRAME
        JFrame frame = new JFrame("Test");
        frame.setSize(WumpusView.WIDTH, WumpusView.HEIGHT);
        frame.setResizable(true);
        int x = ((this.screenSize.width - WumpusView.WIDTH) / 2) - WumpusView.WIDTH;
        int y = (this.screenSize.height - WumpusView.HEIGHT) / 2;
        frame.setLocation(x, y);
        //TEXT AREA
        JTextArea textArea = new JTextArea();
        for (String rule : rules) textArea.setText(textArea.getText() + rule + "\n");

        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setVisible(true);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        frame.add(scroll);
        frame.setVisible(true);
    }

}