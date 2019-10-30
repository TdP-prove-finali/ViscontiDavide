package it.polito.tdp.tesi.db;

import it.polito.tdp.tesi.model.Bevanda;
import it.polito.tdp.tesi.model.Manifestazione;

public class TestDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProvaFinaleDAO dao = new ProvaFinaleDAO();
		
		for(Bevanda b : dao.listaBevandeDAO()) {
			System.out.println(b.toString());
		}
		for(Manifestazione m : dao.listaManifestazioniDAO()) {
			System.out.println(m.toString());
		}
	}

}
