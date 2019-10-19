package it.polito.tdp.tesi.model;

public class Piatto {
	
	private int id;
	private double costo;
	private int difficolta;
	private int tempoPrep;
	private String descrizione;
	private double prezzo;
	private boolean servito;
	
	
	public Piatto(int id, double costo, int difficolta, int tempoPrep, String descrizione, double prezzo) {
		this.id = id;
		this.costo = costo;
		this.difficolta = difficolta;
		this.tempoPrep = tempoPrep;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.servito = false;
	}


	public Piatto() {
	}


	public int getId() {
		return id;
	}


	public double getPrezzo() {
		return prezzo;
	}


	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getCosto() {
		return costo;
	}


	public void setCosto(double costo) {
		this.costo = costo;
	}


	public int getDifficolta() {
		return difficolta;
	}


	public void setDifficolta(int difficolta) {
		this.difficolta = difficolta;
	}


	public int getTempoPrep() {
		return tempoPrep;
	}


	public void setTempoPrep(int tempoPrep) {
		this.tempoPrep = tempoPrep;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
		Piatto other = (Piatto) obj;
		if (id != other.id)
			return false;
		return true;
	}


	public boolean isServito() {
		return servito;
	}


	public void setServito() {
		this.servito = false;
	}


	

}
