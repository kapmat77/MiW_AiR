/**
 * Created by Kapmat on 2016-03-24.
 **/

package Lab4.FX;

import DataClass.Iris;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

public class MainController implements Initializable {

	//#################### MENU ####################
	//Main menu
	@FXML private Menu menuMain;
	@FXML private MenuItem miStart;
	@FXML private MenuItem miLoadData;
	@FXML private MenuItem miSaveOutput;
	@FXML private MenuItem miOption;
	@FXML private MenuItem miExit;
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
	private Stage aboutStage = new Stage();

	//Similarity variables - Iris
	private double leafL, leafW, petalL, petalW, similarityThreshold;

	private ModelAGDS modelAGDS = new ModelAGDS();

	//#################### OTHER CONTROLLERS ####################
	private MethodsWindowController mwController = new MethodsWindowController();
	private AboutController aboutController = new AboutController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeMethodsWindow(); //TODO
		initializeAboutWindow();

		initializeModelAGDS();

		resultIrisTable.setVisible(true);
		resultWineTable.setVisible(false);
	}

	private void initializeAboutWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Lab4/FX/AboutWindow.fxml"));
		Region root = null;
		try {
			root = (Region) loader.load();
		} catch (IOException e) {
			System.out.println("ERROR while loading AboutWindow.fxml. " + e.getMessage());
		}
		Scene mScene = new Scene(root, 200, 100);
		aboutStage.setScene(mScene);
		aboutController = loader.<AboutController>getController();
		aboutStage.setTitle("About window");
		aboutStage.setResizable(false);
	}

	private void initializeModelAGDS() {
		modelAGDS.buildGraphAndTable();

	}

	private void initializeMethodsWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Lab4/FX/MethodsWindow.fxml"));
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
			mwController.setVisibility();
		} else if (rmi.getId().equals(rmiFilter.getId())) {
			rmiFilter.setSelected(true);
			mwController.setLabelMethod("Method: FILTER");
			mwController.setVisibility();
		} else  if (rmi.getId().equals(rmiCorrelation.getId())) {
			rmiCorrelation.setSelected(true);
			mwController.setLabelMethod("Method: CORRELACTION");
			mwController.setVisibility();
		} else if (rmi.getId().equals(rmiMinMax.getId())) {
			rmiMinMax.setSelected(true);
			mwController.setLabelMethod("Method: MIN&MAX");
			mwController.setVisibility();
		}
		mStage.show();
	}

	public void loadDataAction(ActionEvent event) throws FileNotFoundException {
		//TODO wymczasowe wczytywanie
		modelAGDS.setDataType(1);
		List<Iris> listOfIrises = Iris.readDataFromFile(modelAGDS.getPath());
		modelAGDS.setListOfIrises(listOfIrises);
	}

	public void startAction(ActionEvent event) {
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

	public void generateSimilarityGraphicAction(ActionEvent event) {
	}

	public void generateCorrelationGraphicAction(ActionEvent event) {
	}

	public void generateAgdsGraphicAction(ActionEvent event) {
	}

	@FXML
	private void aboutAction(ActionEvent event) {
		aboutStage.show();
	}

	public void cancelButtonAction(ActionEvent event) {
		((Stage) cancelButton.getScene().getWindow()).close();
	}
}
