/**
 * Created by Kapmat on 2016-03-24.
 **/

package Lab4.FX;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

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
//	//Data type
//	@FXML private Menu menuData;
//	@FXML private RadioMenuItem rmiIris;
//	@FXML private RadioMenuItem rmiWine;
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

	//################## OPTIONS WINDOW ####################
	@FXML private AnchorPane optionsWindow;
	@FXML private Button cancelButton;



	private Stage optionStage = new Stage();
	private Stage mStage = new Stage();

	//Similarity variables - Iris
	private double leafL, leafW, petalL, petalW, similarityThreshold;

	private ModelAGDS modelAGDS = new ModelAGDS();
	private MethodsWindowController mwController = new MethodsWindowController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeMethodsWindow();

		resultIrisTable.setVisible(true);
		resultWineTable.setVisible(false);
	}

	private void initializeMethodsWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MethodsWindow.fxml"));
		Region root = null;
		try {
			root = (Region) loader.load();
		} catch (IOException e) {
			System.out.println("ERROR while loading MethodsWindow.fxml. " + e.getMessage());
		}
		Scene mScene = new Scene(root, 600, 500);
		mStage.setScene(mScene);
		mwController = loader.<MethodsWindowController>getController();
		mStage.setTitle("Methods window");
		mStage.setResizable(false);
	}

	@FXML
	public void exitAction(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	public void optionAction(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("OptionWindow.fxml"));
		optionStage.setTitle("Main menu -> Options");
		optionStage.setScene(new Scene(root, 400, 300));
		optionStage.setResizable(false);
		optionStage.show();
	}

	private void disableRimMethods() {
		rmiSimilarity.setSelected(false);
		rmiFilter.setSelected(false);
		rmiCorrelation.setSelected(false);
		rmiMinMax.setSelected(false);
	}

	public void saveOutputAction(ActionEvent event) {
	}

	@FXML
	public void methodsChangeAction(ActionEvent event) throws IOException {
		disableRimMethods();
		RadioMenuItem rmi = (RadioMenuItem) event.getSource();
		if (rmi.getId().equals(rmiSimilarity.getId())) {
			rmiSimilarity.setSelected(true);
			mwController.setLabelMethod("Method: SIMILARITY");
		} else if (rmi.getId().equals(rmiFilter.getId())) {
			rmiFilter.setSelected(true);
			mwController.setLabelMethod("Method: FILTER");
		} else  if (rmi.getId().equals(rmiCorrelation.getId())) {
			rmiCorrelation.setSelected(true);
			mwController.setLabelMethod("Method: CORRELACTION");
		} else if (rmi.getId().equals(rmiMinMax.getId())) {
			rmiMinMax.setSelected(true);
			mwController.setLabelMethod("Method: MIN&MAX");
		}
		mStage.show();
	}

	public void loadDataAction(ActionEvent event) {
	}

	public void startAction(ActionEvent event) {
	}

//	}

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

	public void generateSimilarityGraphicAction(ActionEvent event) {
	}

	public void generateCorrelationGraphicAction(ActionEvent event) {
	}

	public void generateAgdsGraphicAction(ActionEvent event) {
	}

	public void cancelButtonAction(ActionEvent event) {
		((Stage) cancelButton.getScene().getWindow()).close();
	}
}
