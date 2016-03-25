/**
 * Created by Kapmat on 2016-03-24.
 **/

package Lab4.FX;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

	//#################### TABLE VIEW ####################
	@FXML private TableView resultIrisTable;
	@FXML private TableView resultWineTable;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rmiIris.setSelected(true);
		resultIrisTable.setVisible(true);
		resultWineTable.setVisible(false);
		rmiSimilarity.setSelected(true);
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

	private void disableRimData() {
		rmiIris.setSelected(false);
		rmiWine.setSelected(false);
	}

	private void disableRimMethods() {
		rmiSimilarity.setSelected(false);
		rmiFilter.setSelected(false);
		rmiCorrelation.setSelected(false);
		rmiMinMax.setSelected(false);
	}

	@FXML
	public void exitAction(ActionEvent event) {
		Platform.exit();
	}
	
	public void optionAction(ActionEvent event) {
	}
	
	public void saveOutputAction(ActionEvent event) {
	}
	
	public void loadDataAction(ActionEvent event) {
	}
	
	public void startAction(ActionEvent event) {
	}

	@FXML
	public void dataChangeAction(ActionEvent event) {
		disableRimData();
		RadioMenuItem rmi = (RadioMenuItem) event.getSource();
		if (rmi.getId().equals(rmiIris.getId())) {
			rmiIris.setSelected(true);
			changeVisibility(rmiIris.getId());
		} else if (rmi.getId().equals(rmiWine.getId())) {
			rmiWine.setSelected(true);
			changeVisibility(rmiWine.getId());
		}
	}

	private void changeVisibility(String id) {
		switch (id) {
			case "rmiIris":
				resultIrisTable.setVisible(true);
				resultWineTable.setVisible(false);
				break;
			case "rmiWine":
				resultIrisTable.setVisible(false);
				resultWineTable.setVisible(true);
				break;

		}
	}

	@FXML
	public void methodsChangeAction(ActionEvent event) {
		disableRimMethods();
		RadioMenuItem rmi = (RadioMenuItem)  event.getSource();
		if (rmi.getId().equals(rmiSimilarity.getId())) {
			rmiSimilarity.setSelected(true);
		} else if (rmi.getId().equals(rmiFilter.getId())) {
			rmiFilter.setSelected(true);
		} else  if (rmi.getId().equals(rmiCorrelation.getId())) {
			rmiCorrelation.setSelected(true);
		} else if (rmi.getId().equals(rmiMinMax.getId())) {
			rmiMinMax.setSelected(true);
		}
	}

	public void generateSimilarityGraphicAction(ActionEvent event) {
	}

	public void generateCorrelationGraphicAction(ActionEvent event) {
	}

	public void generateAgdsGraphicAction(ActionEvent event) {
	}
}
