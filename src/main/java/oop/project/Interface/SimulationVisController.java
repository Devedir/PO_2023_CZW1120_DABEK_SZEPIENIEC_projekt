package oop.project.Interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationVisController implements Initializable {
    @FXML
    public GridPane mapGridPane;
    @FXML
    public Label titleOfStatistics;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void buttonStartStopFunc(ActionEvent actionEvent) {
    }

    public void showDominantGenotypeFunc(ActionEvent actionEvent) {
    }

    public void showPreferredSpaceIdFunc(ActionEvent actionEvent) {
    }
}
