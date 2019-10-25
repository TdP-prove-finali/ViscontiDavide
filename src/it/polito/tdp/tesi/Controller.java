package it.polito.tdp.tesi;

import java.awt.MenuItem;

import it.polito.tdp.tesi.model.Manifestazione;
import it.polito.tdp.tesi.model.Simulazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
	
	private Simulazione sim;

    @FXML
    private ChoiceBox<Manifestazione> boxManifestazione;

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
    void doSImula(ActionEvent event) {

    }

	public void setSim(Simulazione s) {
		this.sim = s;
		
	}
	

}
