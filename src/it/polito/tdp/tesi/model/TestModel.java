package it.polito.tdp.tesi.model;

import it.polito.tdp.tesi.db.ProvaFinaleDAO;

public class TestModel {

	public static void main(String[] args) {

		ProvaFinaleDAO dao = new ProvaFinaleDAO();
		
		Simulazione sim = new Simulazione(10);
		sim.simula(dao.listaManifestazioniDAO().get(2), dao.listaPiattiDAO(), dao.listaBevandeDAO(),
				40, 2, 4, 7);
	}

}
