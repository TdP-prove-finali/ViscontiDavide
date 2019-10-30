package it.polito.tdp.tesi;

import java.util.*;

import it.polito.tdp.tesi.db.ProvaFinaleDAO;
import it.polito.tdp.tesi.model.Manifestazione;
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
	
	private Simulazione sim;
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

    }

    @FXML
    void doSimula(ActionEvent event) {
 /*   	List<String> l = new LinkedList<String>();
    	for(MenuItem c : menuPiatti.getItems()) {
    		if(c instanceof CheckMenuItem) {
    			if(((CheckMenuItem) c).isSelected()) {
    				txtResult.appendText("Piatti selezionati: \n"+c.getText()+"\n");
    			}
    			
    		}
    	}          */
    }

	public void setSim(Simulazione s) {
		this.sim = s;
		
	}
	
	
	public void init() {
/*		for(int i = 0; i<5; i++) {
			String s = String.valueOf(i);
			CheckMenuItem cmi = new CheckMenuItem(s);
			menuPiatti.getItems().add(cmi);
		}
		buttonSimula.setDisable(false);    */
	}
	
	
	
}
