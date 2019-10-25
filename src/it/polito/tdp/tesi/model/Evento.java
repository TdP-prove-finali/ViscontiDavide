package it.polito.tdp.tesi.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{

	public enum TipoEvento {
		ARRIVO_CODA,
		ORDINE,
		SERVIZIO,
		
	}

	private LocalDateTime ora;
	private TipoEvento tipo;
	private Cliente cliente;
	
	
	public Evento(LocalDateTime ora, TipoEvento tipo, Cliente cliente) {
		this.ora = ora;
		this.tipo = tipo;
		this.cliente = cliente;
	}
	
	public LocalDateTime getOra() {
		return ora;
	}
	public void setOra(LocalDateTime ora) {
		this.ora = ora;
	}
	public TipoEvento getTipo() {
		return tipo;
	}
	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int compareTo(Evento arg0) {
		return this.ora.compareTo(arg0.ora);
	}

	@Override
	public String toString() {
		return "Evento [ora=" + ora + ", tipo=" + tipo + ", cliente=" + cliente + "]";
	}
	
	
	
	
	
	
}
