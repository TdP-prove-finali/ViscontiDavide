package it.polito.tdp.tesi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.tesi.model.Bevanda;
import it.polito.tdp.tesi.model.Manifestazione;
import it.polito.tdp.tesi.model.Piatto;


public class ProvaFinaleDAO {
	
	public List<Piatto> listaPiattiDAO() {
		
		String sql = "SELECT * FROM piatti ORDER BY descrizione ASC";
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Piatto> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Piatto(res.getInt("id"),res.getDouble("costo"),res.getInt("tempo_prep"),
							res.getString("descrizione"),res.getDouble("prezzo"),res.getInt("quantita"),res.getString("genere")));
					
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
	
	public List<Bevanda> listaBevandeDAO() {
			
			String sql = "SELECT * FROM bevande ORDER BY descrizione ASC";
			
			try {
				Connection conn = ConnectDB.getConnection() ;
	
				PreparedStatement st = conn.prepareStatement(sql) ;
				
				List<Bevanda> list = new ArrayList<>() ;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					try {
						list.add(new Bevanda(res.getInt("id"),res.getDouble("prezzo"),
								res.getString("descrizione"),res.getDouble("costo")));
						
					} catch (Throwable t) {
						t.printStackTrace();
						System.out.println(res.getInt("id"));
					}
				}
				
				conn.close();
				return list ;
	
			} catch (SQLException e) {
				e.printStackTrace();
				return null ;
			}
		}
	
		
	public List<Manifestazione> listaManifestazioniDAO() {
		
		String sql = "SELECT * FROM evento ORDER BY descrizione ASC";
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Manifestazione> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Manifestazione(res.getInt("id"),res.getInt("costo"),
							res.getString("descrizione"),res.getDouble("budget_medio"),res.getInt("partecipanti")));
					
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
public List<Piatto> listaPiattiPrincipaliDAO() {
		
		String sql = "SELECT * FROM piatti WHERE genere= 'principale' ORDER BY descrizione ASC";
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Piatto> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Piatto(res.getInt("id"),res.getDouble("costo"),res.getInt("tempo_prep"),
							res.getString("descrizione"),res.getDouble("prezzo"),res.getInt("quantita"),res.getString("genere")));
					
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
}
