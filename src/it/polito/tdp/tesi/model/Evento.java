package it.polito.tdp.tesi.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Evento {

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
	
	
	
	
	
	
}
