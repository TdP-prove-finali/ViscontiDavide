package it.polito.tdp.tesi.model;

import java.time.LocalDateTime;
import java.util.*;
import it.polito.tdp.tesi.model.Evento.TipoEvento;

public class Model {

	private int incasso;
	private List<Cliente> listaClientiCoda;
	private List<Cliente> listaClientiDaServire;
	private List<Cliente> listaClienti;     //tutti i clienti
	private List<Dipendente> listaDipendentiCassa;
	private List<Dipendente> listaDipendentiBancone;
	private List<Piatto> listaPiatti;
	private List<Bevanda> listaBevande;
	private Manifestazione m;
	private Random r;
	private LocalDateTime oraInizio;
	private LocalDateTime oraFine;
	private int clientiInsoddisfatti;
	
	private double tempoMaxAttesaServizio;
	private int tempoMaxAttesaCassa;
	
	private PriorityQueue<Evento> queue;
	
	private List<Piatto> piattiOrdinati;
	private List<Bevanda> bevandeOrdinate;
	
//	private dao;
	
	
	public Model(Manifestazione m, List<Piatto> l, List<Bevanda> b) {   //passo la lista dall'interfaccia
		this.incasso = 0;
		this.tempoMaxAttesaServizio = 0;
		this.tempoMaxAttesaCassa = 0;
		this.tempoMaxAttesaServizio = 30;
		this.clientiInsoddisfatti = 0;
		this.m = m;
		this.oraInizio = LocalDateTime.of(2019, 1, 1, 19, 0, 0);
		this.oraFine = LocalDateTime.of(2019, 1, 2, 1, 0, 0);
		this.queue = new PriorityQueue<Evento>();
		this.listaBevande = new LinkedList<Bevanda>();
		this.listaClientiCoda = new LinkedList<Cliente>();
		this.listaClientiDaServire = new LinkedList<Cliente>();
		this.listaClienti = new LinkedList<Cliente>();
		this.listaDipendentiCassa = new LinkedList<Dipendente>();
		this.listaDipendentiBancone = new LinkedList<Dipendente>();
		this.listaPiatti = new LinkedList<Piatto>();
		this.listaPiatti = l;
		this.listaBevande = b;
		this.bevandeOrdinate = new LinkedList<Bevanda>();
		this.piattiOrdinati = new LinkedList<Piatto>();
		this.r = new Random();
	}
	
	
	public void creaDipendenti(int cassa, int bancone) { //passo i parametri dall'interfaccia (scelta utente)
		for(int i = 0; i < cassa; i++) {
			Dipendente d = new Dipendente(i, false);
			listaDipendentiCassa.add(d);
		}
		for(int i = cassa; i < (bancone+cassa); i++) {
			Dipendente d = new Dipendente(i, false);
			listaDipendentiBancone.add(d);
		}
	}
	
	
	public void creaClienti(int tempo) {     //vedere se va bene int o devo mettere double
		int numero = m.getNumeroPersone();
		int probabilita = r.nextInt(2);
		if(probabilita == 0)
			numero += numero*(r.nextInt(40)+1)/100;
		if(probabilita == 1)
			numero -= numero*(r.nextInt(40)+1)/100;
		int prob2 = r.nextInt(2);
		if(prob2 == 0)
			tempo += tempo*(r.nextInt((int)tempo/3));
		if(prob2 == 1)
			tempo -= tempo*(r.nextInt((int)tempo/3));
		for(int i = 0; i < numero; i++) {
			Cliente c = new Cliente(i,m.getBudgetMedio(),tempo);
			listaClienti.add(c);
		}
	}
	
	
	public void init() {
		int numero = listaClienti.size();     //divido i clienti
		int primaFascia = numero/3;   //19-20
		int secondaFascia = numero/2; //20-21
		int terzaFascia = numero/6;   //21-22
		
		for(int i = 0; i < primaFascia; i ++) {
			double budget = listaClienti.get(i).getBudget();
			int probabilita = r.nextInt(2);
			if(probabilita == 0)
				budget += budget*(r.nextInt(30)+1)/100;
			if(probabilita == 1)
				budget -= budget*(r.nextInt(30)+1)/100;
			int intervalloTempo = 60*60/primaFascia; //in secondi
			Evento e = new Evento(oraInizio.plusSeconds(intervalloTempo*i),TipoEvento.ARRIVO_CODA,listaClienti.get(i));
			queue.add(e);
		}
		for(int i = primaFascia; i < secondaFascia; i ++) {
			double budget = listaClienti.get(i).getBudget();
			int probabilita = r.nextInt(2);
			if(probabilita == 0)
				budget += budget*(r.nextInt(30)+1)/100;
			if(probabilita == 1)
				budget -= budget*(r.nextInt(30)+1)/100;
			int intervalloTempo = 60*60/secondaFascia; //in secondi
			oraInizio = oraInizio.plusHours(1);
			Evento e = new Evento(oraInizio.plusSeconds(intervalloTempo*i),TipoEvento.ARRIVO_CODA,listaClienti.get(i));
			queue.add(e);
		}
		for(int i = secondaFascia; i < terzaFascia; i ++) {
			double budget = listaClienti.get(i).getBudget();
			int probabilita = r.nextInt(2);
			if(probabilita == 0)
				budget += budget*(r.nextInt(30)+1)/100;
			if(probabilita == 1)
				budget -= budget*(r.nextInt(30)+1)/100;
			int intervalloTempo = 60*60/terzaFascia; //in secondi
			oraInizio = oraInizio.plusHours(1);
			Evento e = new Evento(oraInizio.plusSeconds(intervalloTempo*i),TipoEvento.ARRIVO_CODA,listaClienti.get(i));
			queue.add(e);
		}
	}
	
	
	public void run() {

		while (!queue.isEmpty()) {
			Evento ev = queue.poll();
			
			if(ev.getOra().isBefore(oraFine)) {
				
				switch(ev.getTipo()) {
				
				case ARRIVO_CODA:
					
					int personeInCoda = listaClientiCoda.size(); //stimo 35 secondi ciascuno
					int secondiAttesa = personeInCoda*35 / listaDipendentiCassa.size();
					
					if(secondiAttesa > tempoMaxAttesaCassa)
						tempoMaxAttesaCassa = secondiAttesa;
					
					listaClientiCoda.add(ev.getCliente());
							
					Evento e1 = new Evento(ev.getOra().plusSeconds(secondiAttesa), TipoEvento.ORDINE, ev.getCliente());
					queue.add(e1);
					
					break;
					
				case ORDINE:
					
					int bevande = listaBevande.size();
					int piatti = listaPiatti.size();
					
					listaClientiCoda.remove(ev.getCliente());
					
					if(ev.getCliente().isPrimoOrdine()) {
						
						Piatto p = new Piatto();
						p = listaPiatti.get(r.nextInt(piatti));
						Bevanda b = new Bevanda();
						b = listaBevande.get(r.nextInt(bevande));
						
						double spesa = p.getPrezzo()+b.getPrezzo();
						
						if(ev.getCliente().getBudget() > (spesa)) {
							ev.getCliente().decrementaBudget(spesa);
							ev.getCliente().addListaBevande(b);
							ev.getCliente().addListaPiatti(p);
							
							double attesa = 0;
						
							for(int i = 1; i < listaClientiDaServire.size(); i ++) {
								attesa += listaClientiDaServire.get(i).sommaTempoPiatti();
							}
							attesa = attesa/listaDipendentiBancone.size();
							
							if(attesa > tempoMaxAttesaServizio) 
								clientiInsoddisfatti++;
							
							
							Evento e2 = new Evento(ev.getOra().plusMinutes((int)attesa), TipoEvento.SERVIZIO, ev.getCliente());
							queue.add(e2);
							}
					}
					else {
						int prob = r.nextInt(3);
						
						if(prob == 0) { // solo piatto
							Piatto p = new Piatto();
							p = listaPiatti.get(r.nextInt(piatti));
							
							if(ev.getCliente().getBudget() > p.getPrezzo()) {
								ev.getCliente().addListaPiatti(p);
								ev.getCliente().decrementaBudget(p.getPrezzo());
							}
						}
						if(prob == 1) { // solo bevanda
							Bevanda b = new Bevanda();
							b = listaBevande.get(r.nextInt(bevande));
							
							if(ev.getCliente().getBudget() > b.getPrezzo()) {
								ev.getCliente().addListaBevande(b);
								ev.getCliente().decrementaBudget(b.getPrezzo());
							}
						}
						if(prob == 2) { // entrambi
							Piatto p = new Piatto();
							p = listaPiatti.get(r.nextInt(piatti));
							Bevanda b = new Bevanda();
							b = listaBevande.get(r.nextInt(bevande));
							
							double spesa = p.getPrezzo()+b.getPrezzo();
							
							if(ev.getCliente().getBudget() > (spesa)) {
								ev.getCliente().decrementaBudget(spesa);
								ev.getCliente().addListaBevande(b);
								ev.getCliente().addListaPiatti(p);
							}
						}
					}
					break;
					
				case SERVIZIO:
					
					ev.getCliente().getListaPiatti().get(0).setServito();  //piatto servito
					
					Evento e3 = new Evento(ev.getOra().plusMinutes(ev.getCliente().getTempoRiordino()), TipoEvento.ARRIVO_CODA, ev.getCliente());
					queue.add(e3);
				}
			}
			
		}
	}


	public double getTempoMaxAttesaServizio() {
		return tempoMaxAttesaServizio;
	}


	public void setTempoMaxAttesaServizio(double tempoMedioAttesaServizio) {
		this.tempoMaxAttesaServizio = tempoMedioAttesaServizio;
	}


	public int getIncasso() {
		return incasso;
	}


	public void setIncasso(int incasso) {
		this.incasso = incasso;
	}


	public int getClientiInsoddisfatti() {
		return clientiInsoddisfatti;
	}


	public void setClientiInsoddisfatti(int clientiInsoddisfatti) {
		this.clientiInsoddisfatti = clientiInsoddisfatti;
	}
	
	
}
