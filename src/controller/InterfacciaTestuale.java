package controller;

import java.util.Scanner;

import model.elementi.CuccioloDiWumpus;
import model.gioco.Direzioni;
import model.gioco.Partita;
import view.WumpusViewCompleta;

/**
 * Classe che permette di giocare una partita per mezzo di una semplice interfaccia testuale
 * direttamente nella console
 * I possibili comandi sono:
 * vai
 * freccia
 * seguiti da un punto cardinale per indicare la direzione in cui spostare l'Agente
 * o in cui scoccare la freccia
 *   
 * @author Marcello_Mora_8808920
 * @author Filiberto_Melis_20035059
 * @author Simone_Garau_20005068
 */
public class InterfacciaTestuale {
	public static void main(String[] args) {
		System.out.println("Inizio partita");
		Partita miaPartita = Partita.avviaPartita();
		new WumpusViewCompleta(miaPartita);
		Scanner tastiera = new Scanner(System.in);
		
		while(miaPartita.getMappaDiGioco().getAgente().getInGioco() && miaPartita.getMappaDiGioco().getWumpus().getInGioco()) {
			System.out.println(miaPartita.descrizioneCasellaAgente());
			for(CuccioloDiWumpus corrente : miaPartita.getCuccioli()) {
				if (corrente.getInGioco()) {
					System.out.println("Posizione attuale di " + corrente.getNome() + ": " + corrente.getRiga() + ", " + corrente.getColonna());
				}
			}
			System.out.println("Numero frecce: " + miaPartita.getAgente().getNumFrecce());
			System.out.println("Punteggio: " + miaPartita.getAgente().getPunteggio());
			System.out.println();
			System.out.print("Digita un comando\n> ");
			String comando = tastiera.nextLine();
			String[] comandi = comando.split(" ");
			
			if (comandi.length > 1) {
				String azione = comandi[0].toUpperCase();
				if (azione.equals("VAI") || azione.equals("FRECCIA")) {
					switch (comandi[1].toUpperCase()) {
						case "NORD" -> System.out.println(miaPartita.turnoDiGioco(azione, Direzioni.NORD));
						case "EST" -> System.out.println(miaPartita.turnoDiGioco(azione, Direzioni.EST));
						case "SUD" -> System.out.println(miaPartita.turnoDiGioco(azione, Direzioni.SUD));
						case "OVEST" -> System.out.println(miaPartita.turnoDiGioco(azione, Direzioni.OVEST));
						default -> System.out.println("Comando non riconosciuto");
					}
				}
				else  {
					System.out.println("Comando non riconosciuto");
				}
			}
			else  {
				System.out.println("Comando non riconosciuto");
			}
			
		}
		
		System.out.println("Partita terminata");
		System.out.println("Punteggio: " + miaPartita.getAgente().getPunteggio());
		tastiera.close();
	}
}