/**
 * Created by Kapmat on 2016-03-24.
 **/

package Lab4.FX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class NewControll implements Initializable {

	@FXML private Label lOptionHeader;

	//#################### MENU ####################
	//Main menu
	@FXML private Menu menuMain;
	@FXML private MenuItem miStart;
	@FXML private MenuItem miLoadData;
	@FXML private MenuItem miSaveOutput;
	@FXML private MenuItem miOption;
	@FXML private MenuItem miExit;
	//Data type
	@FXML private Menu menuData;
	@FXML private RadioMenuItem rmiIris;
	@FXML private RadioMenuItem rmiWine;
	//Methods
	@FXML private Menu menuMethods;
	@FXML private RadioMenuItem rmiSimilarity;
	@FXML private RadioMenuItem rmiFilter;
	@FXML private RadioMenuItem rmiMinMax;
	@FXML private RadioMenuItem rmiCorrelation;
	//Generate graphic
	@FXML private Menu menuGraphic;
	@FXML private MenuItem miSimilarityGraphic;
	@FXML private MenuItem miCorrelationGraphic;
	@FXML private MenuItem miAgdsGraphic;
	//About
	@FXML private Menu menuAbout;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lOptionHeader.setText("Initialize");
//		TEST.setSelected(false);
	}

//	@FXML
//	public void testAction(ActionEvent event) {
//		if (TEST.isSelected()) {
//			TEST.setSelected(true);
//			disableOtherRadioMenuItem();
//			lOptionHeader.setText("TEST working!");
//		} else {
//			TEST.setSelected(false);
//			lOptionHeader.setText("OFF radioMenuItem test");
//		}
//	}

	private void disableOtherRadioMenuItem() {

	}
	
	public void exitAction(ActionEvent event) {
	}
	
	public void optionAction(ActionEvent event) {
	}
	
	public void saveOutputAction(ActionEvent event) {
	}
	
	public void loadDataAction(ActionEvent event) {
	}
	
	public void startAction(ActionEvent event) {
	}
	
	public void wineAction(ActionEvent event) {
	}
	
	public void irisAction(ActionEvent event) {
	}

	public void similarityAction(ActionEvent event) {
	}

	public void filterAction(ActionEvent event) {
	}

	public void minMaxAction(ActionEvent event) {
	}

	public void correlactionAction(ActionEvent event) {
	}

	public void generateSimilarityGraphicAction(ActionEvent event) {
	}

	public void generateCorrelationGraphicAction(ActionEvent event) {
	}

	public void generateAgdsGraphicAction(ActionEvent event) {
	}
}
