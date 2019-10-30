package it.polito.tdp.tesi.model;

import java.util.*;

public class Cliente {
	
	private int id;
	private double budget;
	private List<Piatto> listaPiatti;
	private List<Bevanda> listaBevande;
	private int tempoRiordino;
	private boolean primoOrdine; //per fare in modo che ordini cibo e bevanda
	
	private double tempoAttesaCassa;
	private double tempoAttesaServizio;
	private int numOrdini;
	
	private boolean insoddisfatto;
		
	
	public int getNumOrdini() {
		return numOrdini;
	}

	public void addNumOrdini() {
		this.numOrdini++;
	}

	public void setNumOrdini(int numOrdini) {
		this.numOrdini = numOrdini;
	}



	public Cliente(int id, double budget, int tempo) {
		this.tempoAttesaCassa = 0;
		this.tempoAttesaServizio = 0;
		this.numOrdini = 0;
		this.id = id;
		this.budget = budget;
		this.tempoRiordino = tempo;
		this.listaBevande = new LinkedList<Bevanda>();
		this.listaPiatti = new LinkedList<Piatto>();
		this.primoOrdine = true;
		this.insoddisfatto = false;
	}
	
	
	
	
	public boolean isInsoddisfatto() {
		return insoddisfatto;
	}

	public void setInsoddisfatto() {
		this.insoddisfatto = false;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}

	public List<Piatto> getListaPiatti() {
		return listaPiatti;
	}
	public void setListaPiatti(List<Piatto> listaPiatti) {
		this.listaPiatti = listaPiatti;
	}
	public boolean isPrimoOrdine() {
		return primoOrdine;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void addListaPiatti(Piatto p) {
		this.listaPiatti.add(p);
	}
	public void addListaBevande(Bevanda b) {
		this.listaBevande.add(b);
	}
	
	
	public List<Bevanda> getListaBevande() {
		return listaBevande;
	}
	public void setListaBevande(List<Bevanda> listaBevande) {
		this.listaBevande = listaBevande;
	}
	
	public void decrementaBudget(double spesa){
		this.budget -= spesa;
	}
	
	public void primoOrdineCompletato() {
		this.primoOrdine = false;
	}
	public int getTempoRiordino() {
		return tempoRiordino;
	}
	public void setTempoRiordino(int tempoRiordino) {
		this.tempoRiordino = tempoRiordino;
	}

	public double getTempoAttesaCassa() {
		return tempoAttesaCassa;
	}

	public void setTempoAttesaCassa(double tempoAttesaCassa) {
		this.tempoAttesaCassa += tempoAttesaCassa;
	}

	public double getTempoAttesaServizio() {
		return tempoAttesaServizio;
	}

	public void setTempoAttesaServizio(double tempoAttesaServizio) {
		this.tempoAttesaServizio += tempoAttesaServizio;
	}

	public int sommaTempoPiatti() {
		int somma = 0;
		for(Piatto p : listaPiatti) {
			if(!p.isServito())
			somma += p.getTempoPrep();
		}
		return somma;
	}
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", budget=" + budget + ", tempoRiordino=" + tempoRiordino + "]";
	}
	
	
	
}
