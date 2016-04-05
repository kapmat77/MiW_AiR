/**
 * Created by Kapmat on 2016-03-24.
 **/

package Lab4.FX;

import DataClass.Iris;
import DataClass.Wine;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	//#################### MENU ####################
	//Main menu
	@FXML private Menu menuMain;
	@FXML private MenuItem miStart;
	@FXML private MenuItem miLoadData;
	@FXML private MenuItem miSaveOutput;
	@FXML private MenuItem miOption;
	@FXML private MenuItem menuAbout;
	@FXML private MenuItem miExit;
	//Type
	@FXML private RadioMenuItem rmiGraph;
	@FXML private RadioMenuItem rmiTable;
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

	//#################### TABLE VIEW ####################
	@FXML private AnchorPane resultAnchorPane;
	@FXML private TableView resultIrisTable;
	@FXML private TableView resultWineTable;
	//IRIS
	@FXML private TableColumn colIIndex;
	@FXML private TableColumn colLeafLength;
	@FXML private TableColumn colLeafWidth;
	@FXML private TableColumn colPetalLength;
	@FXML private TableColumn colPetalWidth;
	@FXML private TableColumn colType;
	@FXML private TableColumn colISimilarity;

	//################## OPTIONS WINDOW ####################
	@FXML private AnchorPane optionsWindow;
	@FXML private Button cancelButton;

	//###################### TIME ########################
	@FXML private TextField tfGraphTime;
	@FXML private TextField tfTableTime;
	@FXML private TextField tfDiffTime;

	private Stage optionStage = new Stage();
	private Stage mStage = new Stage();
	private Stage aboutStage = new Stage();
	@FXML
	private Stage loadStage = new Stage();

	//Similarity variables - Iris
	private double leafL, leafW, petalL, petalW, similarityThreshold;

	private ModelAGDS modelAGDS = new ModelAGDS();

	//#################### OTHER CONTROLLERS ####################
	private MethodsWindowController mwController = new MethodsWindowController();
	private AboutController aboutController = new AboutController();

	public ModelAGDS getModel() {
		return modelAGDS;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeMethodsWindow(); //TODO
		initializeAboutWindow();

		rmiGraph.setSelected(true);
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
		Scene mScene = new Scene(root, 250, 125);
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
//		FileChooser filechooser = new FileChooser();
//		filechooser.setTitle("Load data");
//		filechooser.setInitialDirectory(new File(System.getProperty("user.dir")));
//		filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Txt files", "*.txt"));
//		File file = filechooser.showOpenDialog(loadStage);
//
//		if (file.getAbsolutePath().contains("dataIris")) {
//			//IRIS
//			modelAGDS.setDataType(1);
//			List<Iris> listOfIrises = Iris.readDataFromFile(modelAGDS.getPath());
//			modelAGDS.setListOfIrises(listOfIrises);
//			initializeModelAGDS();
//		} else if (file.getAbsolutePath().contains("dataWine")){
//			//WINE
//			modelAGDS.setDataType(2);
//			List<Wine> listOfWines = Wine.readDataFromFile(modelAGDS.getPath());
//			modelAGDS.setListOfWines(listOfWines);
//			initializeModelAGDS();
//		}
		//TODO tymczasowo Iris
		modelAGDS.setDataType(1);
		List<Iris> listOfIrises = Iris.readDataFromFile("src/Resources/dataIris.txt");
		modelAGDS.setListOfIrises(listOfIrises);
		initializeModelAGDS();
	}

	public void startAction(ActionEvent event) {
		if (mwController.getLabelMethod().getText().contains("SIMILARITY")) {
			modelAGDS.setLeafL(Double.valueOf(mwController.getTxLlSim().getText()));
			modelAGDS.setLeafW(Double.valueOf(mwController.getTxLwSim().getText()));
			modelAGDS.setPetalL(Double.valueOf(mwController.getTxPlSim().getText()));
			modelAGDS.setPetalW(Double.valueOf(mwController.getTxPwSim().getText()));
			modelAGDS.setSimilarityThreshold(Double.valueOf(mwController.getTxIrisSimilarity().getText().replaceAll(",",".")));
			//TODO wine
			if (rmiGraph.isSelected()) {
				modelAGDS.findPatternsInGraphSimilarity();
			} else if (rmiTable.isSelected()) {
				modelAGDS.findPatternsInTableSimilarity();
			}
		} else if (mwController.getLabelMethod().getText().contains("FILTER")) {
			modelAGDS.setLowestLL(Double.valueOf(mwController.getTxLlMinFil().getText()));
			modelAGDS.setHighestLL(Double.valueOf(mwController.getTxLlMaxFil().getText()));
			modelAGDS.setLowestLW(Double.valueOf(mwController.getTxLwMinFil().getText()));
			modelAGDS.setHighestLW(Double.valueOf(mwController.getTxLwMaxFil().getText()));
			modelAGDS.setLowestPL(Double.valueOf(mwController.getTxPlMinFil().getText()));
			modelAGDS.setHighestPL(Double.valueOf(mwController.getTxPlMaxFil().getText()));
			modelAGDS.setLowestPW(Double.valueOf(mwController.getTxPwMinFil().getText()));
			modelAGDS.setHighestPW(Double.valueOf(mwController.getTxPwMaxFil().getText()));
			//TODO wine
			modelAGDS.findPatternsInGraphFilter();
		} else if (mwController.getLabelMethod().getText().contains("CORRELACTION")) {

		} else if (mwController.getLabelMethod().getText().contains("MIN&MAX")) {

		}
		fillIrisTable();
		updateTime();
	}

	private void updateTime() {
		tfTableTime.setText(String.valueOf(modelAGDS.getTableTime()));
		tfGraphTime.setText(String.valueOf(modelAGDS.getGraphTime()));
		tfDiffTime.setText(String.valueOf(modelAGDS.getTableTime()-modelAGDS.getGraphTime()));
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

	private void fillIrisTable() {
		resultIrisTable = new TableView<Iris>();
		resultIrisTable.setMaxSize(1208,452);
		resultIrisTable.setMinSize(1208,452);
		ObservableList<Iris> data = FXCollections.observableArrayList();
		data.addAll(modelAGDS.getOutputIrisList());

		colIIndex.setCellValueFactory(new PropertyValueFactory<Iris,Integer>("index"));
		colLeafLength.setCellValueFactory(new PropertyValueFactory<Iris,Double>("leafLength"));
		colLeafWidth.setCellValueFactory(new PropertyValueFactory<Iris,Double>("leafWidth"));
		colPetalLength.setCellValueFactory(new PropertyValueFactory<Iris,Double>("petalLength"));
		colPetalWidth.setCellValueFactory(new PropertyValueFactory<Iris,Double>("petalWidth"));
		colType.setCellValueFactory(new PropertyValueFactory<Iris,String>("type"));
		colISimilarity.setCellValueFactory(new PropertyValueFactory<Iris,Double>("similarity"));

		resultIrisTable.setItems(data);
		resultIrisTable.getColumns().addAll(colIIndex, colLeafLength, colLeafWidth, colPetalLength, colPetalWidth, colType, colISimilarity);

		resultAnchorPane.getChildren().addAll(resultIrisTable);
	}

	@FXML
	public void okButton(ActionEvent event) {
		mwController.setLabelMethod("s");
	}

	public void cancelButtonAction(ActionEvent event) {
		((Stage) cancelButton.getScene().getWindow()).close();
	}

	@FXML
	public void aboutAction(ActionEvent event) {
		aboutStage.show();
	}

	@FXML
	public void dataTypeAction(ActionEvent event) {
		rmiGraph.setSelected(false);
		rmiTable.setSelected(false);
		RadioMenuItem rmi = (RadioMenuItem) event.getSource();
		if (rmi.getId().equals(rmiGraph.getId())) {
			rmiGraph.setSelected(true);
		} else if (rmi.getId().equals(rmiTable.getId())) {
			rmiTable.setSelected(true);
		}
	}
}
