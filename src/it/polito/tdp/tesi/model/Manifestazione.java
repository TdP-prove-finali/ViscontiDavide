package it.polito.tdp.tesi.model;

import java.util.*;

public class Manifestazione {

	private int id;
	private int costo;
	private Random r;
	private String descrizione;
	private double budgetMedio;
	private int numeroPersone;
	
	
	public Manifestazione() {
		r = new Random();
	}


	public Manifestazione(int id, int costo, String descrizione, double budgetMedio, int numeroPersone) {
		this.id = id;
		this.costo = costo;
		this.descrizione = descrizione;
		this.budgetMedio = budgetMedio;
		this.numeroPersone = numeroPersone;
		r = new Random();
	}

	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Random getR() {
		return r;
	}


	public void setR(Random r) {
		this.r = r;
	}


	public int getCosto() {
		return costo;
	}


	public void setCosto(int costo) {
		this.costo = costo;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public double getBudgetMedio() {
		return budgetMedio;
	}


	public void setBudgetMedio(double budgetMedio) {
		this.budgetMedio = budgetMedio;
	}


	public int getNumeroPersone() {
		return numeroPersone;
	}


	public void setNumeroPersone(int numeroPersone) {
		this.numeroPersone = numeroPersone;
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
		Manifestazione other = (Manifestazione) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
