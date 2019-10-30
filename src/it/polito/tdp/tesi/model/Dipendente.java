package it.polito.tdp.tesi.model;

public class Dipendente {
	
	private int id;
	private double pagaOraria;
	
	
	
	public Dipendente(int id, double paga) {
		this.id = id;
		this.pagaOraria = paga;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		Dipendente other = (Dipendente) obj;
		if (id != other.id)
			return false;
		return true;
	}


	public double getPagaOraria() {
		return pagaOraria;
	}


	public void setPagaOraria(double pagaOraria) {
		this.pagaOraria = pagaOraria;
	}
	
	

}
