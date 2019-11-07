package it.polito.tdp.tesi;

import java.util.*;
import it.polito.tdp.tesi.db.ProvaFinaleDAO;
import it.polito.tdp.tesi.model.Bevanda;
import it.polito.tdp.tesi.model.Manifestazione;
import it.polito.tdp.tesi.model.Piatto;
import it.polito.tdp.tesi.model.Simulazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
	
	private ProvaFinaleDAO dao;

    @FXML
    private ComboBox<Manifestazione> boxManifestazione;

    @FXML
    private MenuButton menuPiatti;

    @FXML
    private MenuButton menuBevande;

    @FXML
    private TextField dipCassa;

    @FXML
    private TextField dipBancone;

    @FXML
    private TextField riordino;

    @FXML
    private TextField paga;

    @FXML
    private TextField numSimulazioni;

    @FXML
    private Button buttonPulisci;

    @FXML
    private Button buttonSimula;

    @FXML
    private TextArea txtResult;

    @FXML
    void doPulisci(ActionEvent event) {
    	dipCassa.clear();
    	dipBancone.clear();
    	riordino.clear();
    	numSimulazioni.clear();
    	this.paga.clear();
    	txtResult.clear();
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	List<Piatto> piatti = new LinkedList<Piatto>();
    	for(MenuItem m : menuPiatti.getItems()) {
    		if(m instanceof CheckMenuItem) {
    			if(((CheckMenuItem) m).isSelected()) {
    				for(Piatto p : dao.listaPiattiDAO()) {
    					if(m.getText().equals(p.getDescrizione())) {
    						piatti.add(p);
    					}
    				}
    			}
    		}
    	}
    	List<Bevanda> bevande = new LinkedList<Bevanda>();
    	for(MenuItem m : menuBevande.getItems()) {
    		if(m instanceof CheckMenuItem) {
    			if(((CheckMenuItem) m).isSelected()) {
    				for(Bevanda b : dao.listaBevandeDAO()) {
    					if(m.getText().equals(b.getDescrizione())) {
    						bevande.add(b);
    					}
    				}
    			}
    		}
    	}
    	int numeroSim = 0;
    	try {
    		numeroSim = Integer.parseInt(numSimulazioni.getText());
    	}catch (NumberFormatException nfe) {
    		txtResult.appendText("Inserire un valore numerico per il numero di simulazioni!\n");
    	}
    	int dipCas = 0;
    	try {
    		dipCas = Integer.parseInt(this.dipCassa.getText());
    	}catch (NumberFormatException nfe) {
    		txtResult.appendText("Inserire un valore numerico per il numero di dipendenti in cassa!\n");
    	}
    	int dipBanc = 0;
    	try {
    		dipBanc = Integer.parseInt(this.dipBancone.getText());
    	}catch (NumberFormatException nfe) {
    		txtResult.appendText("Inserire un valore numerico per il numero di dipendenti al bancone!\n");
    	}
    	int tempo = 0;
    	try {
    		tempo = Integer.parseInt(riordino.getText());
    	}catch (NumberFormatException nfe) {
    		txtResult.appendText("Inserire un valore numerico per il tempo di riordino!\n");
    	}
    	int paga = 0;
    	try {
    		paga = Integer.parseInt(this.paga.getText());
    	}catch (NumberFormatException nfe) {
    		txtResult.appendText("Inserire un valore numerico per la paga oraria dei dipendenti!\n");
    	}
    	Simulazione sim = null;
    	if(numeroSim != 0) {
    		sim = new Simulazione(numeroSim);
    	}
    	Manifestazione m = null;
    	m = boxManifestazione.getValue();
    	
    	if(sim != null && m != null && bevande != null && piatti != null && dipCas != 0 &&
    			dipBanc != 0 && tempo != 0 && paga != 0) {
    		txtResult.clear();
    		sim.simula(m, piatti, bevande, tempo, dipCas, dipBanc, paga);
    	} else {
    		txtResult.appendText("Controllare di aver selezionato tutti i parametri necessari per la simulazione\n");
    		
    	}
    	if(sim != null && m != null && bevande != null && piatti != null && dipCas != 0 &&
    			dipBanc != 0 && tempo != 0 && paga != 0) {
	    	txtResult.appendText("Piatti ordinati: \n\n");
	    	for(Piatto p : sim.getPiattiOrdinati()) {
	    		txtResult.appendText(p.toString()+"\n");
	    	}
	    	txtResult.appendText("\nBevande ordinate: \n\n");
	    	for(Bevanda b : sim.getBevandeOrdinate()) {
	    		txtResult.appendText(b.toString()+"\n");
	    	}
	    	txtResult.appendText("\n\n");
	    	
	    	String s1 = String.format("%.2f", sim.getClientiInsoddisfattiCoda());
	    	String s2 = String.format("%.2f", sim.getClientiInsoddisfattiServizio());
	    	String s3 = String.format("%.2f", sim.getClientiInsEntrambi());
	    	String s4 = String.format("%.2f", sim.getCostoMedio());
	    	String s5 = String.format("%.2f", sim.getGuadagnoMedio());
	    	String s6 = String.format("%.2f", sim.getIncassoMedio());
	    	String s7 = String.format("%.2f", sim.getAttesaMediaCassa());
	    	String s8 = String.format("%.2f", sim.getAttesaMediaServizio());
	    	String s9 = String.format("%.2f", sim.getTempoMaxAttesaServizio());
	    	String s10 = String.format("%.2f", sim.getNumMedioOrdini());
	
	    	txtResult.appendText("Evento : "+m.getDescrizione()+"; costo: "+m.getCosto()+"\n");
	    	txtResult.appendText("clienti insoddisfatti cassa : "+s1+"\n");
	    	txtResult.appendText("clienti insoddisfatti servizio : "+s2+"\n");
	    	txtResult.appendText("clienti insoddisfatti cassa e servizio : "+s3+"\n");
	    	txtResult.appendText("costo medio : " +s4+"\n");
	    	txtResult.appendText("guadagno medio : "+s5+"\n");
	    	txtResult.appendText("incasso medio : " +s6+"\n");
	    	txtResult.appendText("attesa media cassa : "+s7+"\n");
	    	txtResult.appendText("attesa media servizio : "+s8+"\n");
	    	txtResult.appendText("tempo max attesa cassa : "+sim.getTempoMaxAttesaCassa()+"\n");
	    	txtResult.appendText("tempo max attesa servizio : "+s9+"\n");
	    	txtResult.appendText("numero medio di ordini : "+s10);
    	}
   
    }


	public void init() {
		dao = new ProvaFinaleDAO();
		for(Manifestazione m : dao.listaManifestazioniDAO()) {
			boxManifestazione.getItems().add(m);
		}
		for(Piatto p : dao.listaPiattiDAO()) {
			String s = p.getDescrizione();
			CheckMenuItem cmi = new CheckMenuItem(s);
			menuPiatti.getItems().add(cmi);
		}
		for(Bevanda b : dao.listaBevandeDAO()) {
			String s = b.getDescrizione();
			CheckMenuItem cmi = new CheckMenuItem(s);
			menuBevande.getItems().add(cmi);
		}
		for(MenuItem c : menuPiatti.getItems()) {
			if(c instanceof CheckMenuItem) {
				((CheckMenuItem) c).setSelected(true);
			}
		}
		for(MenuItem c : menuBevande.getItems()) {
			if(c instanceof CheckMenuItem) {
				((CheckMenuItem) c).setSelected(true);
			}
		}
		buttonSimula.setDisable(false);
	}
	
	
	
}
