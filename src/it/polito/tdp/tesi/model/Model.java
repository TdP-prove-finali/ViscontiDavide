package it.polito.tdp.tesi.model;

import java.time.LocalDateTime;
import java.util.*;

import it.polito.tdp.tesi.db.ProvaFinaleDAO;
import it.polito.tdp.tesi.model.Evento.TipoEvento;

public class Model {

	private double incasso;
	private double costoTotale;
	private double guadagno;
	private double numOrdiniTotale;
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
	
	private Map<Integer,Cliente> idMapClienti;
	
	private double tempoMaxAttesaServizio;
	private double maxAttesaServizio;   //per insoddisfazione
	private int tempoMaxAttesaCassa;
	private double maxAttesaCassa;
	
	private PriorityQueue<Evento> queue;
	
	private List<Piatto> piattiOrdinati;
	private List<Bevanda> bevandeOrdinate;
	private List<Piatto> listaPiattiPrincipali;
	private List<Piatto> listaPiattiSec;
	
	private ProvaFinaleDAO dao;
	
	
	public Model(Manifestazione m) { 
		this.incasso = 0;
		this.costoTotale = 0;
		this.numOrdiniTotale = 0;
		this.guadagno = 0;
		this.tempoMaxAttesaCassa = 0;
		this.maxAttesaServizio = 1800;
		this.maxAttesaCassa = 1800;
		this.tempoMaxAttesaServizio = 0;
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
		this.bevandeOrdinate = new LinkedList<Bevanda>();
		this.piattiOrdinati = new LinkedList<Piatto>();
		this.listaPiattiPrincipali = new LinkedList<Piatto>();
		this.listaPiattiSec = new LinkedList<Piatto>();
		this.r = new Random();
		this.idMapClienti = new HashMap<Integer, Cliente>();
		this.dao = new ProvaFinaleDAO();
	}
	
	
	public void creaDipendenti(int cassa, int bancone, double paga) {
		
		listaDipendentiBancone.clear();
		listaDipendentiCassa.clear();
		
		for(int i = 0; i < cassa; i++) {
			Dipendente d = new Dipendente(i, paga);		//un dipendente fisso bevande, tra quelli al bancone
			listaDipendentiCassa.add(d);
		}
		for(int i = cassa; i < (bancone+cassa); i++) {
			Dipendente d = new Dipendente(i, paga);
			listaDipendentiBancone.add(d);
		}
	}
	
	
	public void creaClienti(int tempo) {  
		
		listaClienti.clear();
		listaClientiCoda.clear();
		listaClientiDaServire.clear();
		
		int numero = m.getNumeroPersone();
		int probabilita = r.nextInt(2);
		if(probabilita == 0)
			numero += numero*((r.nextInt(40)+1)/100);
		if(probabilita == 1)
			numero -= numero*((r.nextInt(40)+1)/100);
		
		System.out.println("numero clienti: "+numero);
		
		for(int i = 0; i < numero; i ++) {
			int prob2 = r.nextInt(2);
			
			double tmp = 0;
			if(prob2 == 0)
				tmp = tempo+tempo*(r.nextInt((int)tempo/3))/100;
			if(prob2 == 1)
				tmp = tempo-tempo*(r.nextInt((int)tempo/3))/100;
			
			Cliente c = new Cliente(i,m.getBudgetMedio(),(int)tmp);
			
			listaClienti.add(c);	
			idMapClienti.put(i, c);
		}
	}
	
	
	public void init(List<Piatto> lp, List<Bevanda> lb) {
		
		this.listaBevande = lb;
		this.listaPiatti = lp;
		
		for(Piatto p : lp) {
			if(p.getGenere().equals("principale")) {
				listaPiattiPrincipali.add(p);
			}else {
				listaPiattiSec.add(p);
			}
		}
		queue.clear();
		bevandeOrdinate.clear();
		piattiOrdinati.clear();
		
		
		int numero = listaClienti.size();     //divido i clienti
		int primaFascia = numero/3;   //19-20
		int secondaFascia = numero/2; //20-21
		int terzaFascia = numero/6;   //21-22
		
		System.out.println("fasce: "+primaFascia+"  "+secondaFascia+"  "+terzaFascia);
		
		for(int i = 0; i < primaFascia; i ++) {
			double budget = listaClienti.get(i).getBudget();
			int probabilita = r.nextInt(2);
			if(probabilita == 0)
				budget += budget*(r.nextInt(30)+1)/100;
			if(probabilita == 1)
				budget -= budget*(r.nextInt(30)+1)/100;
			
			listaClienti.get(i).setBudget(budget);
			
			
			int intervalloTempo = 60*60/primaFascia; //in secondi
			
			Evento e = new Evento(oraInizio.plusSeconds(intervalloTempo*i),TipoEvento.ARRIVO_CODA,listaClienti.get(i));
			
			queue.add(e);
		}
		oraInizio = oraInizio.plusHours(1);
		for(int i = primaFascia; i < (primaFascia+secondaFascia); i ++) {
			double budget = listaClienti.get(i).getBudget();
			int probabilita = r.nextInt(2);
			if(probabilita == 0)
				budget += budget*(r.nextInt(30)+1)/100;
			if(probabilita == 1)
				budget -= budget*(r.nextInt(30)+1)/100;
			
			listaClienti.get(i).setBudget(budget);
			
			int intervalloTempo = 60*60/secondaFascia;
			
			Evento e = new Evento(oraInizio.plusSeconds(intervalloTempo*(i-primaFascia)),TipoEvento.ARRIVO_CODA,listaClienti.get(i));
			
			queue.add(e);
		}
		oraInizio = oraInizio.plusHours(1);
		for(int i = (primaFascia+secondaFascia); i < (primaFascia+secondaFascia+terzaFascia); i ++) {
			double budget = listaClienti.get(i).getBudget();
			int probabilita = r.nextInt(2);
			if(probabilita == 0)
				budget += budget*(r.nextInt(30)+1)/100;
			if(probabilita == 1)
				budget -= budget*(r.nextInt(30)+1)/100;
			
			listaClienti.get(i).setBudget(budget);

			int intervalloTempo = 60*60/terzaFascia;
			
			Evento e = new Evento(oraInizio.plusSeconds(intervalloTempo*(i-(primaFascia+secondaFascia))),TipoEvento.ARRIVO_CODA,listaClienti.get(i));
			
			queue.add(e);
		}
	}
	
	
	public void run() {
		
		this.bevandeOrdinate.clear();
		this.piattiOrdinati.clear();

		while (!queue.isEmpty()) {
			Evento ev = queue.poll();
						
			if(ev.getOra().isBefore(oraFine)) {	
				
				switch(ev.getTipo()) {
				
				case ARRIVO_CODA:
					
					int personeInCoda = listaClientiCoda.size(); //stimo 30 secondi ciascuno +-10 
					double secondiAttesa = personeInCoda*(30+r.nextInt(11)) / listaDipendentiCassa.size();
					
					ev.getCliente().setTempoAttesaCassa(secondiAttesa);
					
					if(secondiAttesa > tempoMaxAttesaCassa)
						tempoMaxAttesaCassa = (int)secondiAttesa;
					
					if(secondiAttesa > maxAttesaCassa) {
						if(!ev.getCliente().isInsoddisfattoCoda())
							ev.getCliente().setInsoddisfattoCoda();
					}
					
					listaClientiCoda.add(ev.getCliente());
												
					Evento e1 = new Evento(ev.getOra().plusSeconds((int)secondiAttesa), TipoEvento.ORDINE, ev.getCliente());
					
					
					idMapClienti.remove(ev.getCliente().getId());
					idMapClienti.put(ev.getCliente().getId(), ev.getCliente());
					
					if(e1.getOra().isBefore(oraFine))
						queue.add(e1);
					
					break;
					
				case ORDINE:
					
					int bevande = listaBevande.size();
					int piatti = listaPiatti.size();
					
					int piattiPrinc = listaPiattiPrincipali.size();
					
					listaClientiCoda.remove(ev.getCliente());
					
					if(ev.getCliente().isPrimoOrdine()) {  //piatto principale e bevanda
						
						Piatto p = new Piatto();
						p = listaPiattiPrincipali.get(r.nextInt(piattiPrinc));
						Bevanda b = new Bevanda();
						b = listaBevande.get(r.nextInt(bevande));
						
						double spesa = p.getPrezzo()+b.getPrezzo();
						
						if(ev.getCliente().getBudget() > (spesa)) {
							ev.getCliente().decrementaBudget(spesa);
							ev.getCliente().addListaBevande(b);
							ev.getCliente().addListaPiatti(p);
							
							piattiOrdinati.add(p);
							bevandeOrdinate.add(b);
							
							incasso += spesa;
							
							double attesa = 0;
						
							for(int i = 1; i < listaClientiDaServire.size(); i ++) {
								attesa += listaClientiDaServire.get(i).getListaPiatti().get(0).getTempoPrep();
							}
							attesa = attesa/(listaDipendentiBancone.size()-1);   //uno per le bevande
							
							attesa += p.getTempoPrep();
							
							if(attesa > maxAttesaServizio) {
								if(!ev.getCliente().isInsoddisfattoServizio())
									ev.getCliente().setInsoddisfattoServizio();
							}
							if(attesa > tempoMaxAttesaServizio)
								tempoMaxAttesaServizio = attesa;
							
							ev.getCliente().setTempoAttesaServizio(attesa);
							ev.getCliente().addNumOrdini();
							
							
							listaClientiDaServire.add(ev.getCliente());
													
							Evento e2 = new Evento(ev.getOra().plusSeconds((int)attesa), TipoEvento.SERVIZIO, ev.getCliente());
							
							idMapClienti.remove(ev.getCliente().getId());
							idMapClienti.put(ev.getCliente().getId(), ev.getCliente());
							
							if(e2.getOra().isBefore(oraFine))
								queue.add(e2);
							}
						
						break;
					}
					else {
						int prob = r.nextInt(3);
						
						if(prob == 0) { // solo piatto
							Piatto p = new Piatto();
							p = listaPiatti.get(r.nextInt(piatti));
							
							if(p.getGenere().equals("principale")) {
								
								if(ev.getCliente().contaPrincipali() < 2) {
							
									if(ev.getCliente().getBudget() > p.getPrezzo()) {
										ev.getCliente().addListaPiatti(p);
										ev.getCliente().decrementaBudget(p.getPrezzo());
										
										piattiOrdinati.add(p);
										
										incasso += p.getPrezzo();
										
										double attesa = 0;
									
										for(int i = 1; i < listaClientiDaServire.size(); i ++) {
											attesa += listaClientiDaServire.get(i).getListaPiatti().get(0).getTempoPrep();
										}
										attesa = attesa/(listaDipendentiBancone.size()-1);
										
										attesa += p.getTempoPrep();
	
										ev.getCliente().setTempoAttesaServizio(attesa);
										ev.getCliente().addNumOrdini();
										
										listaClientiDaServire.add(ev.getCliente());
										
										if(attesa > maxAttesaServizio) {
											if(!ev.getCliente().isInsoddisfattoServizio())
												ev.getCliente().setInsoddisfattoServizio();
										}
										if(attesa > tempoMaxAttesaServizio)
											tempoMaxAttesaServizio = attesa;
										
										
										Evento e2 = new Evento(ev.getOra().plusSeconds((int)attesa), TipoEvento.SERVIZIO, ev.getCliente());
										
										idMapClienti.remove(ev.getCliente().getId());
										idMapClienti.put(ev.getCliente().getId(), ev.getCliente());
										
										if(e2.getOra().isBefore(oraFine))
											queue.add(e2);
									}
								}else { //principale ma non ordinabile
									Piatto p1 = new Piatto();
									p1 = listaPiattiSec.get(r.nextInt(listaPiattiSec.size()));
									
									if(ev.getCliente().getBudget() > p1.getPrezzo()) {
										ev.getCliente().addListaPiatti(p1);
										ev.getCliente().decrementaBudget(p1.getPrezzo());
										
										piattiOrdinati.add(p1);
										
										incasso += p1.getPrezzo();
										
										double attesa = 0;
									
										for(int i = (int)(listaDipendentiBancone.size()/2); i < listaClientiDaServire.size(); i ++) {
											attesa += listaClientiDaServire.get(i).getListaPiatti().get(0).getTempoPrep();
										}
										attesa = attesa/(listaDipendentiBancone.size()-1);
										
										attesa += p1.getTempoPrep();
	
										ev.getCliente().setTempoAttesaServizio(attesa);
										ev.getCliente().addNumOrdini();
										
										listaClientiDaServire.add(ev.getCliente());
										
										if(attesa > maxAttesaServizio) {
											if(!ev.getCliente().isInsoddisfattoServizio())
												ev.getCliente().setInsoddisfattoServizio();
										}
										if(attesa > tempoMaxAttesaServizio)
											tempoMaxAttesaServizio = attesa;
										
										
										Evento e2 = new Evento(ev.getOra().plusSeconds((int)attesa), TipoEvento.SERVIZIO, ev.getCliente());
										
										idMapClienti.remove(ev.getCliente().getId());
										idMapClienti.put(ev.getCliente().getId(), ev.getCliente());
										
										if(e2.getOra().isBefore(oraFine))
											queue.add(e2);
									}
								}
							}else { //non è principale
								if(ev.getCliente().getBudget() > p.getPrezzo()) {
									ev.getCliente().addListaPiatti(p);
									ev.getCliente().decrementaBudget(p.getPrezzo());
									
									piattiOrdinati.add(p);
									
									incasso += p.getPrezzo();
									
									double attesa = 0;
								
									for(int i = (int)(listaDipendentiBancone.size()/2); i < listaClientiDaServire.size(); i ++) {
										attesa += listaClientiDaServire.get(i).getListaPiatti().get(0).getTempoPrep();
									}
									attesa = attesa/(listaDipendentiBancone.size()-1);
									
									attesa += p.getTempoPrep();

									ev.getCliente().setTempoAttesaServizio(attesa);
									ev.getCliente().addNumOrdini();
									
									listaClientiDaServire.add(ev.getCliente());
									
									if(attesa > maxAttesaServizio) {
										if(!ev.getCliente().isInsoddisfattoServizio())
											ev.getCliente().setInsoddisfattoServizio();
									}
									if(attesa > tempoMaxAttesaServizio)
										tempoMaxAttesaServizio = attesa;
									
									
									Evento e2 = new Evento(ev.getOra().plusSeconds((int)attesa), TipoEvento.SERVIZIO, ev.getCliente());
									
									idMapClienti.remove(ev.getCliente().getId());
									idMapClienti.put(ev.getCliente().getId(), ev.getCliente());
									
									if(e2.getOra().isBefore(oraFine))
										queue.add(e2);
								}
							}
						}
						
						if(prob == 1) { // solo bevanda, no tempo di attesa
							Bevanda b = new Bevanda();
							
							b = listaBevande.get(r.nextInt(bevande));
							
							if(ev.getCliente().getBudget() > b.getPrezzo()) {
								ev.getCliente().addListaBevande(b);
								ev.getCliente().decrementaBudget(b.getPrezzo());
								
								bevandeOrdinate.add(b);
								
								incasso += b.getPrezzo();
								
								ev.getCliente().addNumOrdini();
								
								idMapClienti.remove(ev.getCliente().getId());
								idMapClienti.put(ev.getCliente().getId(), ev.getCliente());
							}
						}
						if(prob == 2) { // entrambi
							Piatto p = new Piatto();
							p = listaPiatti.get(r.nextInt(piatti));
							Bevanda b = new Bevanda();
							b = listaBevande.get(r.nextInt(bevande));
							
							if(p.getGenere().equals("principale")) {
								
								if(ev.getCliente().contaPrincipali() < 2) {
														
									double spesa = p.getPrezzo()+b.getPrezzo();
									
									if(ev.getCliente().getBudget() > (spesa)) {
										ev.getCliente().decrementaBudget(spesa);
										ev.getCliente().addListaBevande(b);
										ev.getCliente().addListaPiatti(p);
										
										incasso += spesa;
										
										piattiOrdinati.add(p);
										bevandeOrdinate.add(b);
										
										double attesa = 0;
									
										for(int i = (int)(listaDipendentiBancone.size()/2); i < listaClientiDaServire.size(); i ++) {
											attesa += listaClientiDaServire.get(i).getListaPiatti().get(0).getTempoPrep();
										}
										attesa = attesa/(listaDipendentiBancone.size()-1);
										
										attesa += p.getTempoPrep();
		
										ev.getCliente().setTempoAttesaServizio(attesa);
										ev.getCliente().addNumOrdini();
										
										listaClientiDaServire.add(ev.getCliente());
		
										if(attesa > maxAttesaServizio) {
											if(!ev.getCliente().isInsoddisfattoServizio())
												ev.getCliente().setInsoddisfattoServizio();
										}
										if(attesa > tempoMaxAttesaServizio)
											tempoMaxAttesaServizio = attesa;
										
										Evento e2 = new Evento(ev.getOra().plusSeconds((int)attesa), TipoEvento.SERVIZIO, ev.getCliente());
										
										idMapClienti.remove(ev.getCliente().getId());
										idMapClienti.put(ev.getCliente().getId(), ev.getCliente());
										
										if(e2.getOra().isBefore(oraFine))
											queue.add(e2);
									}
								}else { //principale ma non ordinabile
									Piatto p1 = new Piatto();
									p1 = listaPiattiSec.get(r.nextInt(listaPiattiSec.size()));
									double spesa = p1.getPrezzo()+b.getPrezzo();
									
									if(ev.getCliente().getBudget() > spesa) {
										ev.getCliente().addListaPiatti(p1);
										ev.getCliente().addListaBevande(b);
										ev.getCliente().decrementaBudget(spesa);
										
										piattiOrdinati.add(p1);
										bevandeOrdinate.add(b);
										
										incasso += spesa;
										
										double attesa = 0;
									
										for(int i = (int)(listaDipendentiBancone.size()/2); i < listaClientiDaServire.size(); i ++) {
											attesa += listaClientiDaServire.get(i).getListaPiatti().get(0).getTempoPrep();
										}
										attesa = attesa/(listaDipendentiBancone.size()-1);
										
										attesa += p1.getTempoPrep();
	
										ev.getCliente().setTempoAttesaServizio(attesa);
										ev.getCliente().addNumOrdini();
										
										listaClientiDaServire.add(ev.getCliente());
										
										if(attesa > maxAttesaServizio) {
											if(!ev.getCliente().isInsoddisfattoServizio())
												ev.getCliente().setInsoddisfattoServizio();
										}
										if(attesa > tempoMaxAttesaServizio)
											tempoMaxAttesaServizio = attesa;
										
										
										Evento e2 = new Evento(ev.getOra().plusSeconds((int)attesa), TipoEvento.SERVIZIO, ev.getCliente());
										
										idMapClienti.remove(ev.getCliente().getId());
										idMapClienti.put(ev.getCliente().getId(), ev.getCliente());
										
										if(e2.getOra().isBefore(oraFine))
											queue.add(e2);
									}
								}
							}else { //non è principale
								double spesa = p.getPrezzo()+b.getPrezzo();
								
								if(ev.getCliente().getBudget() > spesa) {
									ev.getCliente().addListaPiatti(p);
									ev.getCliente().addListaBevande(b);
									ev.getCliente().decrementaBudget(spesa);
									
									piattiOrdinati.add(p);
									bevandeOrdinate.add(b);
									
									incasso += spesa;
									
									double attesa = 0;
								
									for(int i = (int)(listaDipendentiBancone.size()/2); i < listaClientiDaServire.size(); i ++) {
										attesa += listaClientiDaServire.get(i).getListaPiatti().get(0).getTempoPrep();
									}
									attesa = attesa/(listaDipendentiBancone.size()-1);
									
									attesa += p.getTempoPrep();

									ev.getCliente().setTempoAttesaServizio(attesa);
									ev.getCliente().addNumOrdini();
									
									listaClientiDaServire.add(ev.getCliente());
									
									if(attesa > maxAttesaServizio) {
										if(!ev.getCliente().isInsoddisfattoServizio())
											ev.getCliente().setInsoddisfattoServizio();
									}
									if(attesa > tempoMaxAttesaServizio)
										tempoMaxAttesaServizio = attesa;
									
									
									Evento e2 = new Evento(ev.getOra().plusSeconds((int)attesa), TipoEvento.SERVIZIO, ev.getCliente());
									
									idMapClienti.remove(ev.getCliente().getId());
									idMapClienti.put(ev.getCliente().getId(), ev.getCliente());
									
									if(e2.getOra().isBefore(oraFine))
										queue.add(e2);
								}
							}
							
						}
					}
					break;
					
				case SERVIZIO:
					
					listaClientiDaServire.remove(ev.getCliente());
					
					Evento e3 = new Evento(ev.getOra().plusMinutes(ev.getCliente().getTempoRiordino()), TipoEvento.ARRIVO_CODA, ev.getCliente());
					
					if(e3.getOra().isBefore(oraFine))
						queue.add(e3);
					
					break;
				}
			}
			
		}
		costoTotale = m.getCosto()+calcolaCostoDipendenti(listaDipendentiCassa, listaDipendentiBancone)+calcolaCostoTotalePB(piattiOrdinati, bevandeOrdinate);
		guadagno = incasso - costoTotale;
		
		List<Piatto> temp = quantitaOrdinatePiatti(piattiOrdinati);
		piattiOrdinati.clear();
		piattiOrdinati.addAll(temp);
		for(Piatto p : piattiOrdinati) { 
			p.calcolaQtaDaProdurre();
		}
		for(Piatto p : piattiOrdinati) {
			System.out.println(p.toString());
		}
		
		bevandeOrdinate = quantitaOrdinateBevande(bevandeOrdinate);
		for(Bevanda p : bevandeOrdinate) {
			System.out.println(p.toString());
		}
		
		for(Cliente c : idMapClienti.values()) {
			this.numOrdiniTotale += c.getNumOrdini();
		}
		numOrdiniTotale = numOrdiniTotale/listaClienti.size();
	}

	public double calcolaAttesaServizio(Map<Integer,Cliente> map) {
		double d = 0;
		int ordini = 0;
		for(Cliente c : map.values()) {
			d += c.getTempoAttesaServizio();
			ordini += c.getNumOrdini();
		}
		d = d/ordini;
		return d;
	}
	
	public double calcolaAttesaCassa(Map<Integer,Cliente> map) {
		double d = 0;
		int ordini = 0;
		for(Cliente c : map.values()) {
			d += c.getTempoAttesaCassa();
			ordini += c.getNumOrdini();
		}
		d = d/ordini;
		return d;
	}
	
	public double getIncasso() {
		return incasso;
	}


	public void setIncasso(double incasso) {
		this.incasso = incasso;
	}

	
	public double getCostoTotale() {
		return costoTotale;
	}


	public void setCostoTotale(double costoTotale) {
		this.costoTotale = costoTotale;
	}


	public double getGuadagno() {
		return guadagno;
	}


	public void setGuadagno(double guadagno) {
		this.guadagno = guadagno;
	}


	public int getTempoMaxAttesaCassa() {
		return tempoMaxAttesaCassa;
	}


	public void setTempoMaxAttesaCassa(int tempoMaxAttesaCassa) {
		this.tempoMaxAttesaCassa = tempoMaxAttesaCassa;
	}


	public List<Piatto> getPiattiOrdinati() {
		return piattiOrdinati;
	}


	public void setPiattiOrdinati(List<Piatto> piattiOrdinati) {
		this.piattiOrdinati = piattiOrdinati;
	}


	public List<Bevanda> getBevandeOrdinate() {
		return bevandeOrdinate;
	}


	public void setBevandeOrdinate(List<Bevanda> bevandeOrdinate) {
		this.bevandeOrdinate = bevandeOrdinate;
	}

	

	public double getNumOrdiniTotale() {
		return numOrdiniTotale;
	}


	public void setNumOrdiniTotale(double numOrdiniTotale) {
		this.numOrdiniTotale = numOrdiniTotale;
	}


	public double calcolaCostoTotalePB(List<Piatto> p, List<Bevanda> b) {
		double d = 0;
		for(Piatto a : p) {
			d += a.getCosto();
		}
		for(Bevanda aa : b) {
			d += aa.getCosto();
		}
		return d;
	}
	
	public double calcolaCostoDipendenti(List<Dipendente> l1, List<Dipendente> l2) {
		double d = 0;
		for(Dipendente dip : l1) {
			d += dip.getPagaOraria()*6;    //ognuno lavora 6 ore
		}
		for(Dipendente dip : l2) {
			d += dip.getPagaOraria()*6;
		}
		return d;
	}
	
	public List<Bevanda> quantitaOrdinateBevande(List<Bevanda> lb) {
		Map<Bevanda,Bevanda> l = new HashMap<Bevanda, Bevanda>();
		for(Bevanda b : lb) {
			if (!l.containsKey(b)) {
				b.setQuantita(0);
				b.incrementaQuantita();
				l.put(b, b);
			}
			if (l.containsKey(b)) {
				l.get(b).incrementaQuantita();
			}
		}
		List<Bevanda> lista = new LinkedList<Bevanda>(l.values());
		return lista;
	}
	
	public List<Piatto> quantitaOrdinatePiatti(List<Piatto> lp) {
		Map<Piatto,Piatto> m = new HashMap<Piatto, Piatto>();
		for(Piatto p : lp) {
			if (m.containsKey(p)) {
				m.get(p).incrementaQuantita();
			}
			if (!m.containsKey(p)) {
				p.setQuantita(0);
				p.incrementaQuantita();
				m.put(p, p);
			}
		}
		List<Piatto> l = new LinkedList<Piatto>(m.values());
		return l;
	}

	public List<Cliente> getListaClientiCoda() {
		return listaClientiCoda;
	}


	public void setListaClientiCoda(List<Cliente> listaClientiCoda) {
		this.listaClientiCoda = listaClientiCoda;
	}


	public List<Cliente> getListaClientiDaServire() {
		return listaClientiDaServire;
	}


	public void setListaClientiDaServire(List<Cliente> listaClientiDaServire) {
		this.listaClientiDaServire = listaClientiDaServire;
	}


	public List<Cliente> getListaClienti() {
		return listaClienti;
	}


	public void setListaClienti(List<Cliente> listaClienti) {
		this.listaClienti = listaClienti;
	}


	public List<Dipendente> getListaDipendentiCassa() {
		return listaDipendentiCassa;
	}


	public void setListaDipendentiCassa(List<Dipendente> listaDipendentiCassa) {
		this.listaDipendentiCassa = listaDipendentiCassa;
	}


	public List<Dipendente> getListaDipendentiBancone() {
		return listaDipendentiBancone;
	}


	public void setListaDipendentiBancone(List<Dipendente> listaDipendentiBancone) {
		this.listaDipendentiBancone = listaDipendentiBancone;
	}


	public List<Piatto> getListaPiatti() {
		return listaPiatti;
	}


	public void setListaPiatti(List<Piatto> listaPiatti) {
		this.listaPiatti = listaPiatti;
	}


	public List<Bevanda> getListaBevande() {
		return listaBevande;
	}


	public void setListaBevande(List<Bevanda> listaBevande) {
		this.listaBevande = listaBevande;
	}


	public Map<Integer, Cliente> getIdMapClienti() {
		return idMapClienti;
	}


	public void setIdMapClienti(Map<Integer, Cliente> idMapClienti) {
		this.idMapClienti = idMapClienti;
	}


	public ProvaFinaleDAO getDao() {
		return dao;
	}


	public double getTempoMaxAttesaServizio() {
		return tempoMaxAttesaServizio;
	}


	public void setTempoMaxAttesaServizio(double tempoMaxAttesaServizio) {
		this.tempoMaxAttesaServizio = tempoMaxAttesaServizio;
	}


	public double getMaxAttesaServizio() {
		return maxAttesaServizio;
	}


	public void setMaxAttesaServizio(double maxAttesaServizio) {
		this.maxAttesaServizio = maxAttesaServizio;
	}

	
}
