package it.polito.tdp.tesi.model;

public class Piatto {
	
	private int id;
	private double costo;
	private int tempoPrep;
	private String descrizione;
	private double prezzo;
	private boolean servito;
	
	private int qtaPreparazione;   //qta fissa da preparare
	private int qtaDaProdurre;		//numero di quantita fisse in base agli ordini
	
	private int quantita;
	
	
	public Piatto(int id, double costo, int tempoPrep, String descrizione, double prezzo, int qta) {
		this.id = id;
		this.costo = costo;
		this.tempoPrep = tempoPrep;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.servito = false;
		this.quantita = 0;
		this.qtaPreparazione = qta;
		this.qtaDaProdurre = 0;
	}
	
	


	public Piatto(int id, double costo, int tempoPrep, String descrizione, double prezzo) {
		this.id = id;
		this.costo = costo;
		this.tempoPrep = tempoPrep;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.servito = false;
		this.quantita = 0;
		this.qtaPreparazione = 0;
	}




	public int getQtaPreparazione() {
		return qtaPreparazione;
	}




	public void setQtaPreparazione(int qtaPreparazione) {
		this.qtaPreparazione = qtaPreparazione;
	}


	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}




	public int getQtaDaProdurre() {
		return qtaDaProdurre;
	}




	public void setQtaDaProdurre(int qtaDaProdurre) {
		this.qtaDaProdurre = qtaDaProdurre;
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


	@Override
	public String toString() {
		return "Piatto [id=" + id + ", qtaPreparazione=" + qtaPreparazione + ", qtaDaProdurre=" + qtaDaProdurre
				+ ", quantita=" + quantita + "]";
	}




	public boolean isServito() {
		return servito;
	}


	public void setServito() {
		this.servito = false;
	}

	
	public int getQuantita() {
		return quantita;
	}

	public void incrementaQuantita() {
		this.quantita++;
	}
	
	public boolean haQtaFissa() {
		if(this.qtaPreparazione == 0) 
			return false;
		else
			return true;
	}
	
	public void calcolaQtaDaProdurre() {
		if(this.haQtaFissa()) {
			double d = quantita/qtaPreparazione;
			d = Math.ceil(d)+1;			//rivedere
			this.qtaDaProdurre = (int)d;
		}
	}
	
}
