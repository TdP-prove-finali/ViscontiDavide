package it.polito.tdp.tesi.model;

import java.util.*;

import it.polito.tdp.tesi.db.ProvaFinaleDAO;

public class Simulazione {

	private int numeroSimulazioni;
	private double costoMedio;
	private double guadagnoMedio;
	private double incassoMedio;
	private double clientiInsoddisfattiCoda;
	private double clientiInsoddisfattiServizio;
	private double clientiInsEntrambi;
	private double numMedioOrdini;

	
	private int tempoMaxAttesaCassa; 
	private double tempoMaxAttesaServizio;
	private double attesaMediaCassa;
	private double attesaMediaServizio;
	
	private List<Piatto> piattiOrdinati;
	private List<Bevanda> bevandeOrdinate;
	
	private Map<Integer,Piatto> mappaPiatti;
	private Map<Integer,Bevanda> mappaBevande;
	
	private ProvaFinaleDAO dao;
	
	
	public Simulazione() {
		this.dao = new ProvaFinaleDAO();
	}
	
	public Simulazione(int numero) {
		this.numeroSimulazioni = numero;
		this.clientiInsoddisfattiCoda = 0;
		this.clientiInsoddisfattiServizio = 0;
		this.clientiInsEntrambi = 0;
		this.costoMedio = 0;
		this.incassoMedio = 0;
		this.guadagnoMedio = 0;
		this.numMedioOrdini = 0;
		this.tempoMaxAttesaCassa = 0;
		this.tempoMaxAttesaServizio = 0;
		this.attesaMediaCassa = 0;
		this.attesaMediaServizio = 0;
		this.bevandeOrdinate = new ArrayList<Bevanda>();
		this.piattiOrdinati = new ArrayList<Piatto>();
		this.mappaBevande = new HashMap<Integer, Bevanda>();
		this.mappaPiatti = new HashMap<Integer, Piatto>();
		this.dao = new ProvaFinaleDAO();
	}

	public ProvaFinaleDAO getDao() {
		return dao;
	}

	public void setDao(ProvaFinaleDAO dao) {
		this.dao = dao;
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
	
	public double getClientiInsoddisfattiCoda() {
		return clientiInsoddisfattiCoda;
	}

	public void setClientiInsoddisfattiCoda(double clientiInsoddisfattiCoda) {
		this.clientiInsoddisfattiCoda = clientiInsoddisfattiCoda;
	}

	public double getClientiInsoddisfattiServizio() {
		return clientiInsoddisfattiServizio;
	}

	public void setClientiInsoddisfattiServizio(double clientiInsoddisfattiServizio) {
		this.clientiInsoddisfattiServizio = clientiInsoddisfattiServizio;
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
		
			Model m = new Model(man);
			m.creaClienti(tempoRiordino);
			m.creaDipendenti(dipCassa, dipBancone, pagaOraria);
			
			m.init(p,b);
			m.run();

			for(Cliente c : m.getIdMapClienti().values()) {
				if(c.isInsoddisfattoCoda() && !c.isInsoddisfattoServizio())
					clientiInsoddisfattiCoda++;
				if(c.isInsoddisfattoServizio() && !c.isInsoddisfattoCoda())
					clientiInsoddisfattiServizio++;
				if(c.isInsoddisfattoCoda() && c.isInsoddisfattoServizio())
					clientiInsEntrambi++;
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
					mappaPiatti.get(pi.getId()).setQuantita2(pi.getQuantita());    
					mappaPiatti.get(pi.getId()).setQtaDaProdurre2(pi.getQtaDaProdurre());
				}else {
					mappaPiatti.put(pi.getId(), pi);
				}
			}
			for(Bevanda be : bevandeOrdinate) {
				if(mappaBevande.containsKey(be.getId())) {
					mappaBevande.get(be.getId()).setQuantita2(be.getQuantita());
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
		clientiInsoddisfattiCoda = clientiInsoddisfattiCoda/numeroSimulazioni;
		clientiInsoddisfattiServizio = clientiInsoddisfattiServizio/numeroSimulazioni;
		clientiInsEntrambi = clientiInsEntrambi/numeroSimulazioni;
		attesaMediaCassa = attesaMediaCassa/numeroSimulazioni;
		attesaMediaServizio = attesaMediaServizio/numeroSimulazioni;
		
		List<Bevanda> lb = new ArrayList<Bevanda>();
		List<Piatto> lp = new ArrayList<Piatto>();
		
		for(Piatto pia : mappaPiatti.values()) {
			double qta = pia.getQuantita()/numeroSimulazioni;
			double d = 0;
			if(pia.getQtaPreparazione() != 0) {
				d = qta/pia.getQtaPreparazione();
				double a = Math.floor(d);
				if(d - a > 0) {
					d++;
					d = Math.floor(d);
				}
			}
			pia.setQtaOrdinMedia(qta);
			pia.setQtaProdMedia(d);
			lp.add(pia);
		}
		
		for(Bevanda bev : mappaBevande.values()) {
			double qta = bev.getQuantita()/numeroSimulazioni;
			bev.setQtaOrdinMedia(qta);
			lb.add(bev);
		}
		
		piattiOrdinati.clear();
		bevandeOrdinate.clear();
		piattiOrdinati.addAll(lp);
		bevandeOrdinate.addAll(lb);
		
		mappaPiatti.clear();
		mappaBevande.clear();
	}

	public double getNumMedioOrdini() {
		return numMedioOrdini;
	}

	public void setNumMedioOrdini(double numMedioOrdini) {
		this.numMedioOrdini = numMedioOrdini;
	}

	public int getTempoMaxAttesaCassa() {
		return tempoMaxAttesaCassa;
	}

	public void setTempoMaxAttesaCassa(int tempoMaxAttesaCassa) {
		this.tempoMaxAttesaCassa = tempoMaxAttesaCassa;
	}

	public double getTempoMaxAttesaServizio() {
		return tempoMaxAttesaServizio;
	}

	public void setTempoMaxAttesaServizio(double tempoMaxAttesaServizio) {
		this.tempoMaxAttesaServizio = tempoMaxAttesaServizio;
	}

	public double getAttesaMediaCassa() {
		return attesaMediaCassa;
	}

	public void setAttesaMediaCassa(double attesaMediaCassa) {
		this.attesaMediaCassa = attesaMediaCassa;
	}

	public double getAttesaMediaServizio() {
		return attesaMediaServizio;
	}

	public void setAttesaMediaServizio(double attesaMediaServizio) {
		this.attesaMediaServizio = attesaMediaServizio;
	}

	public double getClientiInsEntrambi() {
		return clientiInsEntrambi;
	}

	public void setClientiInsEntrambi(double clientiInsEntrambi) {
		this.clientiInsEntrambi = clientiInsEntrambi;
	}
	
	
	
}
