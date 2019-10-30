package it.polito.tdp.tesi.model;

import java.util.*;

public class Simulazione {

	private Model m;

	private int numeroSimulazioni;
	private double costoMedio;
	private double guadagnoMedio;
	private double incassoMedio;
	private double clientiInsoddisfatti;
	private double numMedioOrdini;
	
	private int tempoMaxAttesaCassa; 
	private double tempoMaxAttesaServizio;
	private double attesaMediaCassa;
	private double attesaMediaServizio;
	
	private List<Piatto> piattiOrdinati;
	private List<Bevanda> bevandeOrdinate;
	
	private Map<Integer,Piatto> mappaPiatti;
	private Map<Integer,Bevanda> mappaBevande;
	
	
	public Simulazione() {}
	
	public Simulazione(int numero) {
		this.numeroSimulazioni = numero;
		this.clientiInsoddisfatti = 0;
		this.costoMedio = 0;
		this.incassoMedio = 0;
		this.guadagnoMedio = 0;
		this.numMedioOrdini = 0;
		this.bevandeOrdinate = new ArrayList<Bevanda>();
		this.piattiOrdinati = new ArrayList<Piatto>();
		this.mappaBevande = new HashMap<Integer, Bevanda>();
		this.mappaPiatti = new HashMap<Integer, Piatto>();
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

	public Map<Integer, Piatto> getMappaPiatti() {
		return mappaPiatti;
	}

	public void setMappaPiatti(Map<Integer, Piatto> mappaPiatti) {
		this.mappaPiatti = mappaPiatti;
	}

	public Map<Integer, Bevanda> getMappaBevande() {
		return mappaBevande;
	}

	public void setMappaBevande(Map<Integer, Bevanda> mappaBevande) {
		this.mappaBevande = mappaBevande;
	}
	
	

	public void simula (Manifestazione man, List<Piatto> p, List<Bevanda> b, int tempoRiordino, int dipCassa,
						int dipBancone, double pagaOraria) {
		
		for(int i = 0; i< numeroSimulazioni; i ++) {
			
			piattiOrdinati.clear();
			bevandeOrdinate.clear();
			
			m = new Model(man, p, b);
			m.creaClienti(tempoRiordino);
			m.creaDipendenti(dipCassa, dipBancone, pagaOraria);
			
			m.init();
			m.run();

			for(Cliente c : m.getIdMapClienti().values()) {
				if(c.isInsoddisfatto())
					clientiInsoddisfatti++;
			}
			
			guadagnoMedio += m.getGuadagno();
			costoMedio += m.getCostoTotale();
			incassoMedio += m.getIncasso();
			numMedioOrdini += m.getNumOrdiniTotale();
			piattiOrdinati.addAll(m.getPiattiOrdinati());
			bevandeOrdinate.addAll(m.getBevandeOrdinate());
			
			
			attesaMediaCassa += m.calcolaAttesaCassa(m.getIdMapClienti());
			attesaMediaServizio += m.calcolaAttesaServizio(m.getIdMapClienti());
			
			for(Piatto pi : piattiOrdinati) {
				if(mappaPiatti.containsKey(pi.getId())) {
					mappaPiatti.get(pi.getId()).setQuantita(pi.getQuantita());
					mappaPiatti.get(pi.getId()).setQtaDaProdurre(pi.getQtaDaProdurre());
				}else {
					mappaPiatti.put(pi.getId(), pi);
				}
			}
			for(Bevanda be : bevandeOrdinate) {
				if(mappaBevande.containsKey(be.getId())) {
					mappaBevande.get(be.getId()).setQuantita(be.getQuantita());
				}else {
					mappaBevande.put(be.getId(), be);
				}
			}
			if(m.getTempoMaxAttesaCassa() > tempoMaxAttesaCassa)
				tempoMaxAttesaCassa = m.getTempoMaxAttesaCassa();
			if(m.getTempoMaxAttesaServizio() > tempoMaxAttesaServizio)
				tempoMaxAttesaServizio = m.getTempoMaxAttesaServizio();
		}
		guadagnoMedio = guadagnoMedio/numeroSimulazioni;
		costoMedio = costoMedio/numeroSimulazioni;
		incassoMedio = incassoMedio/numeroSimulazioni;
		numMedioOrdini = numMedioOrdini/numeroSimulazioni;
		clientiInsoddisfatti = clientiInsoddisfatti/numeroSimulazioni;
		attesaMediaCassa = attesaMediaCassa/numeroSimulazioni;
		attesaMediaServizio = attesaMediaServizio/numeroSimulazioni;
		
		List<Bevanda> lb = new ArrayList<Bevanda>();
		List<Piatto> lp = new ArrayList<Piatto>();
		
		for(Piatto pia : mappaPiatti.values()) {
			double qta = pia.getQuantita()/numeroSimulazioni;
			double qtaProd = pia.getQtaDaProdurre()/numeroSimulazioni;
			Piatto p1 = new Piatto();
			p1 = pia;
			p1.setQtaOrdinMedia(qta);
			p1.setQtaProdMedia(qtaProd);
			lp.add(p1);
		}
		
		for(Bevanda bev : mappaBevande.values()) {
			double qta = bev.getQuantita()/numeroSimulazioni;
			Bevanda b1 = new Bevanda();
			b1 = bev;
			b1.setQtaOrdinMedia(qta);
			lb.add(b1);
		}
		piattiOrdinati = lp;
		bevandeOrdinate = lb;
	}
	
	
	
}
