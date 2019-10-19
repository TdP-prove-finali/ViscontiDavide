package it.polito.tdp.tesi.model;


public class Bevanda {

	private int id;
	private double prezzo;
	private String descrizione;
	
	private double costo; //per produzione
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public Bevanda(int id, double prezzo, String descrizione, double costo) {
		this.id = id;
		this.prezzo = prezzo;
		this.descrizione = descrizione;
		this.costo = costo;
	}
	
	public Bevanda() {
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
		Bevanda other = (Bevanda) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
