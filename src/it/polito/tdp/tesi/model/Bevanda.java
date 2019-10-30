package it.polito.tdp.tesi.model;


public class Bevanda {

	private int id;
	private double prezzo;
	private String descrizione;
	
	private int quantita;
	private double costo;
	
	private double qtaOrdinMedia;
	
	
	
	public Bevanda(int id, double prezzo, String descrizione, double costo) {
		this.id = id;
		this.prezzo = prezzo;
		this.descrizione = descrizione;
		this.costo = costo;
		this.quantita = 0;
		this.qtaOrdinMedia = 0;
	}
	
	public Bevanda() {
	}
	
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
	
	public double getQtaOrdinMedia() {
		return qtaOrdinMedia;
	}

	public void setQtaOrdinMedia(double qtaOrdinMedia) {
		this.qtaOrdinMedia = qtaOrdinMedia;
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
	
	public int getQuantita() {
		return quantita;
	}
	public void incrementaQuantita() {
		this.quantita ++;
	}
	@Override
	public String toString() {
		return "Bevanda [prezzo=" + prezzo + ", descrizione=" + descrizione + "]";
	}
	public void setQuantita(int quantita) {
		this.quantita += quantita;
	}
	
	
	
}
