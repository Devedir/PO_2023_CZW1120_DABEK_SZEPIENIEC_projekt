package oop.project.Interface;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import oop.project.Simulation;
import oop.project.model.Vector2d;
import oop.project.model.WorldMap;
import oop.project.model.Animal;

import java.util.*;

public class SimulationVisController {
    private double CELL_WIDTH;
    private double CELL_HEIGHT;
    @FXML
    public GridPane mapGridPane;
    @FXML
    public Label titleOfStatistics;
    @FXML
    public Label dayNumberLabel;
    @FXML
    public Label numberOfAnimalsLabel;
    @FXML
    public Label numberOfPlantsLabel;
    @FXML
    public Label theMostPopularGenotypesLabel;
    @FXML
    public Label averageAmountOfEnergyLabel;
    @FXML
    public Label averageLengthOfLifeLabel;
    @FXML
    public Label averageNumberOfOffspringLabel;
    @FXML
    public Button buttonStartStopId;
    @FXML
    public Button showDominantGenotype;
    @FXML
    public Button showPreferredSpaceId;
    @FXML
    public Label dayNumberInfo;
    @FXML
    public Label numberOfAnimalsInfo;
    @FXML
    public Label numberOfPlantsInfo;
    @FXML
    public Label theMostPopularGenotypesInfo;
    @FXML
    public Label averageAmountOfEnergyInfo;
    @FXML
    public Label averageLengthOfLifeInfo;
    @FXML
    public Label averageNumberOfOffspringInfo;

    private WorldMap worldMap;
    private Simulation simulation;
    private double stageHeight = 360;
    private double stageWidth = 360;
    private boolean showDominantGenotypeActive = false;
    private boolean showPreferredCellsByPlantsActive = false;
    private Optional<Animal> maybeTracked = Optional.empty();
    private int dayNumber = 0;

    public void setWidth(double sceneWidth) {
        this.stageWidth = sceneWidth;
    }

    public void setHeight(double sceneHeight) {
        this.stageHeight = sceneHeight;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void buttonStartStopFunc(ActionEvent actionEvent) {
        simulation.setIsPaused(Objects.equals(simulation.getIsPaused(), false));
    }

    public void showDominantGenotypeFunc(ActionEvent actionEvent) {
        showDominantGenotypeActive = Objects.equals(showDominantGenotypeActive, false);
    }

    public void showPreferredSpaceIdFunc(ActionEvent actionEvent) {
        showPreferredCellsByPlantsActive = Objects.equals(showPreferredCellsByPlantsActive, false);
    }

    public String convertPopularGenotypes(Optional<List<Integer>> arg) {
        if (arg.isEmpty()) {
            return "-";
        } else {
            StringBuilder ret = new StringBuilder();
            for (Integer num : arg.get())
                ret.append(num);
            return ret.toString();
        }
    }

    public String convertPopularGenotypes(List<Integer> arg) {
            StringBuilder ret = new StringBuilder();
            for (Integer num : arg)
                ret.append(num);
            return ret.toString();
    }

    public void drawScene () {
        Platform.runLater(() ->{

            if (maybeTracked.isEmpty()) {
                dayNumber++;
                dayNumberLabel.setText(String.valueOf(dayNumber));
                numberOfAnimalsLabel.setText(String.valueOf(worldMap.getMapStats().getNumOfAnimals()));
                numberOfPlantsLabel.setText(String.valueOf(worldMap.getMapStats().getNumOfPlants()));
                Optional<List<Integer>> popularGenotypesList = worldMap.getMapStats().getMostPopularGenome();
                theMostPopularGenotypesLabel.setText(convertPopularGenotypes(popularGenotypesList));
                OptionalDouble averageAmountOfEnergyOptional = worldMap.getMapStats().getAverageEnergy();
                averageAmountOfEnergyLabel.setText(String.valueOf(OptionalConvert(averageAmountOfEnergyOptional, "-")));
                averageLengthOfLifeLabel.setText(String.valueOf(OptionalConvert(worldMap.getMapStats().getAverageLifespan(), "-")));
                averageNumberOfOffspringLabel.setText(String.valueOf(OptionalConvert(worldMap.getMapStats().getAverageNumOfChildren(), "-")));

                dayNumberInfo.setText("Day number");
                numberOfAnimalsInfo.setText("Number of Animals");
                numberOfPlantsInfo.setText("Number of Plants");
                theMostPopularGenotypesInfo.setText("The most popular\n genotypes");
                averageAmountOfEnergyInfo.setText("Average amount\n   of energy");
                averageLengthOfLifeInfo.setText("Average length of life");
                averageNumberOfOffspringInfo.setText("Average number\nof offspring");
            } else {
                Animal tracked = maybeTracked.get();

                // TODO: Zamienić labelki na staty tracked i wyświetlić info o możliwości walnięcia w ESC
                titleOfStatistics.setText("Animal stats (<-ESC)");
                theMostPopularGenotypesInfo.setText("Genotype");
                theMostPopularGenotypesLabel.setText(convertPopularGenotypes(tracked.getGenome()));
                dayNumberInfo.setText("Active genome");
                dayNumberLabel.setText(String.valueOf(tracked.getDirection()));
                numberOfAnimalsInfo.setText("Amount of energy");
                numberOfAnimalsLabel.setText(String.valueOf(tracked.getEnergy()));
                numberOfPlantsInfo.setText("Amount of eaten plants");
                numberOfPlantsLabel.setText(String.valueOf(tracked.getNumOfPlantsEaten()));
                averageAmountOfEnergyInfo.setText("Amount of kids");
                averageAmountOfEnergyLabel.setText(String.valueOf(tracked.getNumOfChildren()));
                averageLengthOfLifeInfo.setText("Amount of descenders");
                averageLengthOfLifeLabel.setText(String.valueOf(tracked.getNumOfLivingDescendants()));
                averageNumberOfOffspringInfo.setText("Days lived");
                averageNumberOfOffspringLabel.setText(String.valueOf(tracked.getAge()));
            }
            drawMap();
        });
    }

    private void drawMap() {
        clearGrid();
        populateGrid();
    }

    private void populateGrid() {

        int height = worldMap.getMapSettings().height();
        int width = worldMap.getMapSettings().width();

        CELL_HEIGHT = stageHeight * 0.8 / height;
        CELL_WIDTH = stageWidth * 0.5/ width;

        for (int x = 0; x < width; x++)
            mapGridPane.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));

        for (int y = 0; y < height; y++)
            mapGridPane.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));

        List<Vector2d>  mostPopularGenomes =  worldMap.getMapStats().getMostPopularGenomePositions();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (worldMap.isAnimalAt(x, y)) {
                    int finalX = x;
                    int finalY = y;
                    if (showPreferredCellsByPlantsActive && worldMap.getMapSettings().plantGrowthVariant()
                            .isPreferred(new Vector2d(x, y), worldMap.getPlantPositions(), worldMap.getMapSettings())) {
                        addCellToGridPane(x, y, "green");
                    } else if (showDominantGenotypeActive && mostPopularGenomes.contains(new Vector2d(x, y))) {
                        Region cell = new Region();
                        cell.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                        cell.setStyle("-fx-background-color: black;");
                        cell.setOnMouseClicked(event -> trackAnimalAt(finalX, finalY));
                        mapGridPane.add(cell, x, y);
                    } else {
                        Region cell = new Region();
                        cell.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                        Color color = createRGBcolor(x, y);
                        cell.setStyle("-fx-background-color: #" + color.toString().substring(2,8) + ";");
                        cell.setOnMouseClicked(event -> trackAnimalAt(finalX, finalY));
                        mapGridPane.add(cell, x, y);
                    }
                } else if (worldMap.getPlantPositions().contains(new Vector2d(x, y))) {
                    addCellToGridPane(x, y, "darkgreen");
                } else {
                    if (showPreferredCellsByPlantsActive && worldMap.getMapSettings().plantGrowthVariant()
                            .isPreferred(new Vector2d(x, y), worldMap.getPlantPositions(), worldMap.getMapSettings())) {
                        addCellToGridPane(x, y, "green");
                    }else {
                        addCellToGridPane(x, y, "gray");
                    }
                }
            }
        }
    }

    private void addCellToGridPane(int x, int y, String color) {
        Region cell = new Region();
        cell.setMinSize(CELL_WIDTH, CELL_HEIGHT);
        cell.setStyle("-fx-background-color: %s;".formatted(color));
        mapGridPane.add(cell, x, y);
    }

    private void trackAnimalAt(int x, int y) {
        Animal tracked = worldMap.getAnimalMap().get(new Vector2d(x, y)).get(0);
        maybeTracked = Optional.of(tracked);
        tracked.setTracker(this);

        // TODO: Zamienić labelki na staty tracked i wyświetlić info o możliwości walnięcia w ESC
        titleOfStatistics.setText("Animal stats (<-ESC)");
        theMostPopularGenotypesInfo.setText("Genotype");
        theMostPopularGenotypesLabel.setText(convertPopularGenotypes(tracked.getGenome()));
        dayNumberInfo.setText("Active genome");
        dayNumberLabel.setText(String.valueOf(tracked.getDirection()));
        numberOfAnimalsInfo.setText("Amount of energy");
        numberOfAnimalsLabel.setText(String.valueOf(tracked.getEnergy()));
        numberOfPlantsInfo.setText("Amount of eaten plants");
        numberOfPlantsLabel.setText(String.valueOf(tracked.getNumOfPlantsEaten()));
        averageAmountOfEnergyInfo.setText("Amount of kids");
        averageAmountOfEnergyLabel.setText(String.valueOf(tracked.getNumOfChildren()));
        averageLengthOfLifeInfo.setText("Amount of descenders");
        averageLengthOfLifeLabel.setText(String.valueOf(tracked.getNumOfLivingDescendants()));
        averageNumberOfOffspringInfo.setText("Days lived");
        averageNumberOfOffspringLabel.setText(String.valueOf(tracked.getAge()));



    }

    public void untrack() {
        if (maybeTracked.isEmpty()) return;
        maybeTracked.get().setTracker(null);
        maybeTracked = Optional.empty();

        // TODO: Zamienić labelki na staty mapy i usunąć info o potencjale klapnięcia w ESC
        titleOfStatistics.setText("Map statistics");
//        drawScene();
//        dayNumber++;
        System.out.println("rysuje nową scene: dzień " + dayNumber);
        dayNumberLabel.setText(String.valueOf(dayNumber));
        numberOfAnimalsLabel.setText(String.valueOf(worldMap.getMapStats().getNumOfAnimals()));
        numberOfPlantsLabel.setText(String.valueOf(worldMap.getMapStats().getNumOfPlants()));
        Optional<List<Integer>> popularGenotypesList = worldMap.getMapStats().getMostPopularGenome();
        theMostPopularGenotypesLabel.setText(convertPopularGenotypes(popularGenotypesList));
        OptionalDouble averageAmountOfEnergyOptional = worldMap.getMapStats().getAverageEnergy();
        averageAmountOfEnergyLabel.setText(String.valueOf(OptionalConvert(averageAmountOfEnergyOptional, "-")));
        averageLengthOfLifeLabel.setText(String.valueOf(OptionalConvert(worldMap.getMapStats().getAverageLifespan(), "-")));
        averageNumberOfOffspringLabel.setText(String.valueOf(OptionalConvert(worldMap.getMapStats().getAverageNumOfChildren(), "-")));
        theMostPopularGenotypesInfo.setText("Genotype");

        dayNumberInfo.setText("Day number");
        numberOfAnimalsInfo.setText("Number of Animals");
        numberOfPlantsInfo.setText("Number of Plants");
        theMostPopularGenotypesInfo.setText("The most popular\n genotypes");
        averageAmountOfEnergyInfo.setText("Average amount\n   of energy");
        averageLengthOfLifeInfo.setText("Average length of life");
        averageNumberOfOffspringInfo.setText("Average number\nof offspring");

    }

    private Color createRGBcolor(int x, int y) {
        Vector2d vector = new Vector2d(x ,y);
        int energy = worldMap.getAnimalMap().get(vector).get(0).getEnergy();
        int breedableEnergy = worldMap.getAnimalSettings().breedableEnergy();
        if (energy == 0)
            return Color.rgb(120, 60, 60);
        else if (energy < breedableEnergy / 2)
            return Color.rgb(225, 80, 80);
        else if (energy < breedableEnergy)
            return Color.rgb(200, 40, 40);
        else if (energy < breedableEnergy * 2)
            return Color.rgb(170, 0, 0);
        else
            return Color.rgb(130, 0, 0);
}

    private void clearGrid() {
        mapGridPane.getChildren().retainAll(mapGridPane.getChildren().get(0));
        mapGridPane.getColumnConstraints().clear();
        mapGridPane.getRowConstraints().clear();
    }

    private static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String OptionalConvert(OptionalDouble arg, String instead) {
        return arg.isEmpty() ?
                instead : String.valueOf(roundToTwoDecimalPlaces(arg.getAsDouble()));
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void runSimulation() {
        Thread simulationThread = new Thread(simulation);
//        this.simulationThread = simulationThread;
        simulationThread.start();
    }
}
