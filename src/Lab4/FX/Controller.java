package Lab4.FX;

import DataClass.Iris;
import Lab4.AssociativeGraphDataStructure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class Controller extends Variables implements Initializable {

	@FXML private AnchorPane resultAnchorPane;

	@FXML private Tab tabFilter;
	@FXML private Tab tabSimilarity;

	@FXML private Button closeButton;
	@FXML private Button bStart;

	@FXML private TextField similarityLeafLengthInput;
	@FXML private TextField similarityLeafWidthInput;
	@FXML private TextField similarityPetalLengthInput;
	@FXML private TextField similarityPetalWidthInput;
	@FXML private TextField similarityTextField;

	@FXML private TextField filterLeafLengthMIN;
	@FXML private TextField filterLeafLengthMAX;
	@FXML private TextField filterLeafWidthMIN;
	@FXML private TextField filterLeafWidthMAX;
	@FXML private TextField filterPetalLengthMIN;
	@FXML private TextField filterPetalLengthMAX;
	@FXML private TextField filterPetalWidthMIN;
	@FXML private TextField filterPetalWidthMAX;

	@FXML private RadioButton rbTypeSetosa;
	@FXML private RadioButton rbTypeVersicolor;
	@FXML private RadioButton rbTypeVirginica;
	@FXML private RadioButton rbGraph;
	@FXML private RadioButton rbTable;

	@FXML private TableView resultTable;

	@FXML private TableColumn colIndex;
	@FXML private TableColumn colLeafLength;
	@FXML private TableColumn colLeafWidth;
	@FXML private TableColumn colPetalLength;
	@FXML private TableColumn colPetalWidth;
	@FXML private TableColumn colType;
	@FXML private TableColumn colSimilarity;

	@FXML
	public void radioButtonMainAction(ActionEvent event) {
		if (rbTable.isFocused()) {
			rbTable.setSelected(true);
			rbGraph.setSelected(false);
		} else if (rbGraph.isFocused()){
			rbTable.setSelected(false);
			rbGraph.setSelected(true);
		}
	}

	@FXML
	public void radioButtonTypesAction(ActionEvent event) {
		if (rbTypeSetosa.isFocused() && !rbTypeVersicolor.isSelected() && !rbTypeVirginica.isSelected()) {
			rbTypeSetosa.setSelected(true);
		}
		if (rbTypeVersicolor.isFocused() && !rbTypeSetosa.isSelected() && !rbTypeVirginica.isSelected()) {
			rbTypeVersicolor.setSelected(true);
		}
		if (rbTypeVirginica.isFocused() && !rbTypeVersicolor.isSelected() && !rbTypeSetosa.isSelected()) {
			rbTypeVirginica.setSelected(true);
		}
	}

	@FXML
	public void closeButtonAction(ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void startButtonAction(ActionEvent event) throws FileNotFoundException {
		startAGDS();
	}

	private void startAGDS() throws FileNotFoundException {
//		AssociativeGraphDataStructure.initialAGDSandTable();

		if (tabFilter.isSelected()) {
			setLowestLL(Double.valueOf(filterLeafLengthMIN.getText()));
			setHighestLL(Double.valueOf(filterLeafLengthMAX.getText()));
			setLowestLW(Double.valueOf(filterLeafWidthMIN.getText()));
			setHighestLW(Double.valueOf(filterLeafWidthMAX.getText()));
			setLowestPL(Double.valueOf(filterPetalLengthMIN.getText()));
			setHighestPL(Double.valueOf(filterPetalLengthMAX.getText()));
			setLowestPW(Double.valueOf(filterPetalWidthMIN.getText()));
			setHighestPW(Double.valueOf(filterPetalWidthMAX.getText()));

			if (rbTypeSetosa.isSelected() && rbTypeVersicolor.isSelected() && rbTypeVirginica.isSelected()) {
				setType(1);
			} else if (rbTypeSetosa.isSelected() && rbTypeVersicolor.isSelected()) {
				setType(2);
			} else if (rbTypeSetosa.isSelected() && rbTypeVirginica.isSelected()) {
				setType(3);
			} else if (rbTypeVersicolor.isSelected() && rbTypeVirginica.isSelected()) {
				setType(4);
			} else if (rbTypeSetosa.isSelected()) {
				setType(5);
			} else if (rbTypeVersicolor.isSelected()) {
				setType(6);
			} else if (rbTypeVirginica.isSelected()) {
				setType(7);
			}

			if (rbGraph.isSelected()) {
//				AssociativeGraphDataStructure.findPatternsInGraph(AssociativeGraphDataStructure.DataType.IRIS);
			} else if (rbTable.isSelected()) {

			}

		} else if (tabSimilarity.isSelected()) {
			setLeafL(Double.valueOf(similarityLeafLengthInput.getText()));
			setLeafW(Double.valueOf(similarityLeafWidthInput.getText()));
			setPetalL(Double.valueOf(similarityPetalLengthInput.getText()));
			setPetalW(Double.valueOf(similarityPetalWidthInput.getText()));
			setSimilarityThreshold(Double.valueOf(similarityTextField.getText()));

			if (rbGraph.isSelected()) {
//				AssociativeGraphDataStructure.findPatternsInGraph(AssociativeGraphDataStructure.DataType.IRIS);
				show();
			} else if (rbTable.isSelected()) {
//				AssociativeGraphDataStructure.findPatternsInTable()
			}
		}
	}

	private void show() {
		resultTable = new TableView<Iris>();
		resultTable.setMaxSize(600,260);
		resultTable.setMinSize(600,260);
		ObservableList<Iris> data = FXCollections.observableArrayList();
		data.addAll(getShowIrises());


		colIndex.setCellValueFactory(new PropertyValueFactory<Iris,Integer>("index"));
		colLeafLength.setCellValueFactory(new PropertyValueFactory<Iris,Double>("leafLength"));
		colLeafWidth.setCellValueFactory(new PropertyValueFactory<Iris,Double>("leafWidth"));
		colPetalLength.setCellValueFactory(new PropertyValueFactory<Iris,Double>("petalLength"));
		colPetalWidth.setCellValueFactory(new PropertyValueFactory<Iris,Double>("petalWidth"));
		colType.setCellValueFactory(new PropertyValueFactory<Iris,String>("type"));
		colSimilarity.setCellValueFactory(new PropertyValueFactory<Iris,Double>("similarity"));

		resultTable.setItems(data);
		resultTable.getColumns().addAll(colIndex, colLeafLength, colLeafWidth, colPetalLength, colPetalWidth, colType, colSimilarity);

		resultAnchorPane.getChildren().addAll(resultTable);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Similarity tab
		similarityLeafLengthInput.setText("5.1");
		similarityLeafWidthInput.setText("3.5");
		similarityPetalLengthInput.setText("1.4");
		similarityPetalWidthInput.setText("0.2");
		similarityTextField.setText("50");

		//Filter tab
		filterLeafLengthMIN.setText("1");
		filterLeafLengthMAX.setText("6");
		filterLeafWidthMIN.setText("1");
		filterLeafWidthMAX.setText("6");
		filterPetalLengthMIN.setText("1");
		filterPetalLengthMAX.setText("6");
		filterPetalWidthMIN.setText("1");
		filterPetalWidthMAX.setText("6");

		//Radio button type
		rbTypeSetosa.setSelected(true);
		rbTypeVersicolor.setSelected(false);
		rbTypeVirginica.setSelected(false);

		//Radio button main
		rbGraph.setSelected(true);
		rbTable.setSelected(false);
	}
}
