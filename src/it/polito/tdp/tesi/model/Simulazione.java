package it.polito.tdp.tesi.model;

import java.util.*;

public class Simulazione {

	private Model m;

	private int numeroSimulazioni;
	private double costoMedio;
	private double guadagnoMedio;
	private double incassoMedio;
	private double clientiInsoddisfatti;
	
	private List<Integer> guadagni;  //forse inutile, posso usare solo le variabili
	private List<Integer> costi;
	private List<Integer> incassi;
	
	private List<Piatto> piattiOrdinati;
	private List<Bevanda> bevandeOrdinate;
	
	
	public Simulazione() {}
	
	public Simulazione(int numero) {
		this.numeroSimulazioni = numero;
		this.clientiInsoddisfatti = 0;
		this.costoMedio = 0;
		this.incassoMedio = 0;
		this.guadagnoMedio = 0;
		this.bevandeOrdinate = new ArrayList<Bevanda>();
		this.piattiOrdinati = new ArrayList<Piatto>();
		this.guadagni = new ArrayList<Integer>();
		this.costi = new ArrayList<Integer>();
		this.incassi = new ArrayList<Integer>();
	}

	public int getNumeroSimulazioni() {
		return numeroSimulazioni;
	}

	public void setNumeroSimulazioni(int numeroSimulazioni) {
		this.numeroSimulazioni = numeroSimulazioni;
	}

	public double getCostoMedio() {
		return costoMedio;
	}

	public void setCostoMedio(double costoMedio) {
		this.costoMedio = costoMedio;
	}

	public double getGuadagnoMedio() {
		return guadagnoMedio;
	}

	public void setGuadagnoMedio(double guadagnoMedio) {
		this.guadagnoMedio = guadagnoMedio;
	}

	public double getIncassoMedio() {
		return incassoMedio;
	}

	public void setIncassoMedio(double incassoMedio) {
		this.incassoMedio = incassoMedio;
	}

	public double getClientiInsoddisfatti() {
		return clientiInsoddisfatti;
	}

	public void setClientiInsoddisfatti(double clientiInsoddisfatti) {
		this.clientiInsoddisfatti = clientiInsoddisfatti;
	}
	
	
	public void simula (Manifestazione man, List<Piatto> p, List<Bevanda> b, int tempoRiordino, int dipCassa,
						int dipBancone, double pagaOraria) { 
										//devo tenermi tutti i parametri utili
		
		for(int i = 0; i< numeroSimulazioni; i ++) {
			
			m = new Model(man, p, b);
			m.creaClienti(tempoRiordino);
			m.creaDipendenti(dipCassa, dipBancone, pagaOraria);
			
			m.init();
			m.run();
			clientiInsoddisfatti += m.getClientiInsoddisfatti();
			guadagnoMedio += m.getGuadagno();
			costoMedio += m.getCostoTotale();
			incassoMedio += m.getIncasso();
			piattiOrdinati.addAll(m.getPiattiOrdinati());
			bevandeOrdinate.addAll(m.getBevandeOrdinate());
		}
	}
	
	public void raggruppaPiatti() {
		//doppio for
	}
	
}
