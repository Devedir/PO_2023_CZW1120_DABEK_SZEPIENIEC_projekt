package oop.project.Interface;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import oop.project.Settings.SettingsBuilder;
import oop.project.Settings.MutationVariant;
import oop.project.Settings.PlantGrowthVariant;
import oop.project.Simulation;

public class SimulationController implements Initializable {
    @FXML
    public Button startButton;
    @FXML
    public Slider startingNumberOfAnimalsSlider;
    @FXML
    public Label startingNumberOfAnimalsLabel;
    @FXML
    public Slider heightSlider;
    @FXML
    public Label heightLabel;
    @FXML
    public Slider widthSlider;
    @FXML
    public Label widthLabel;
    @FXML
    public Slider startingNumberOfPlantsSlider;
    @FXML
    public Label startingNumberOfPlantsLabel;
    @FXML
    public Slider numberOfDailyAddedPlantsSlider;
    @FXML
    public Label numberOfDailyAddedPlantsLabel;
    @FXML
    public Slider startingAnimalEnergySlider;
    @FXML
    public Label startingAnimalEnergyLabel;
    @FXML
    public Slider amountOfEnergyFromEatingSlider;
    @FXML
    public Label amountOfEnergyFromEatingLabel;
    @FXML
    public Slider energyNeededToReproduceSlider;
    @FXML
    public Label energyNeededToReproduceLabel;
    @FXML
    public Slider energyGivenToOffspringSlider;
    @FXML
    public Label energyGivenToOffspringLabel;
    @FXML
    public Slider LengthOfGenesSlider;
    @FXML
    public Label LengthOfGenesLabel;
    @FXML
    public Slider minimumNumberOfMutationSlider;
    @FXML
    public Label minimumNumberOfMutationLabel;
    @FXML
    public Slider maximumNumberOfMutationSlider;
    @FXML
    public Label maximumNumberOfMutationLabel;
    @FXML
    private ListView plantGrowthListView;
    @FXML
    private ListView MutationListView;
    int mapHeight;
    int mapWidth;
    String plantsGrowthVariant;
    String mutationVariant;
    int startingNumberOfAnimals;
    int startingNumberOfPlants;
    int numberOfDailyAddedPlants;
    int startingAnimalEnergy;
    int amountOfEnergyFromEating;
    int energyNeededToReproduce;
    int energyGivenToOffspring;
    int lengthOfGenes;
    int minimumNumberOfMutations;
    int maximumNumberOfMutations;

    String[] plantsGrowthToChoose = {"forested equator", "moving jungle"};
    String[] mutationsToChoose = {"complete random", "small correction"};


    public void startButtonClicked(ActionEvent actionEvent) {

        System.out.println("button START clicked");
        System.out.println(mapHeight);
        System.out.println(mapWidth);
        System.out.println(startingNumberOfAnimals);
        System.out.println(startingNumberOfPlants);
        System.out.println(numberOfDailyAddedPlants);
        System.out.println(startingAnimalEnergy);
        System.out.println(amountOfEnergyFromEating);
        System.out.println(energyNeededToReproduce);
        System.out.println(energyGivenToOffspring);
        System.out.println(lengthOfGenes);
        System.out.println(minimumNumberOfMutations);
        System.out.println(maximumNumberOfMutations);
        System.out.println(plantsGrowthVariant);
        System.out.println(mutationVariant);

        SettingsBuilder builder = new SettingsBuilder();

        builder.setHeight(mapHeight);
        builder.setWidth(mapWidth);

        PlantGrowthVariant plantsGrowthVariantConverted;
        if (Objects.equals(plantsGrowthVariant, "forested equator")) {
            plantsGrowthVariantConverted = PlantGrowthVariant.EQUATOR;
        } else {
            plantsGrowthVariantConverted = PlantGrowthVariant.JUNGLE;
        }
        MutationVariant mutationVariantConverted;
        if (Objects.equals(mutationVariant, "complete random")) {
            mutationVariantConverted = MutationVariant.FULLY_RANDOM;
        } else {
            mutationVariantConverted = MutationVariant.SLIGHT_CORRECTION;
        }
        builder.setPlantGrowthVariant(plantsGrowthVariantConverted);
        builder.setMutationVariant(mutationVariantConverted);

        builder.setInitialNumOfPlants(startingNumberOfPlants);
        builder.setInitialNumOfAnimals(startingNumberOfAnimals);
        builder.setDailyGrowth(numberOfDailyAddedPlants);
        builder.setInitialEnergy(startingAnimalEnergy);
        builder.setEatingEnergy(amountOfEnergyFromEating);
        builder.setBreedableEnergy(energyNeededToReproduce);
        builder.setBreedingEnergy(energyGivenToOffspring);
        builder.setGenomeLength(lengthOfGenes);
        builder.setMinMutations(minimumNumberOfMutations);
        builder.setMaxMutations(maximumNumberOfMutations);

        System.out.println("builder stworzony!");

        Simulation simulation = new Simulation(
                builder.buildMapSettings(),
                builder.buildAnimalSettings()
        );


//        TODO
//        Application.launch(SomeNewSimulationApp.class);




    }


    private void setAll(int index, int value) {
        switch (index) {
            case 1:
                setMapHeight(value);
                break;
            case 2:
                setMapWidth(value);
                break;
            case 3:
                setStartingAnimalEnergy(value);
                break;
            case 4:
                if (mapHeight * mapWidth < value) {
                    setStartingNumberOfAnimals(mapHeight * mapWidth);
                } else {
                    setStartingNumberOfAnimals(value);
                }
                break;
            case 5:
                if (mapHeight * mapWidth < value) {
                    setStartingNumberOfPlants(mapHeight * mapWidth);
                } else {
                    setStartingNumberOfPlants(value);
                }
                break;
            case 6:
                setNumberOfDailyAddedPlants(value);
                break;
            case 7:
                setAmountOfEnergyFromEating(value);
                break;
            case 8:
                // mozliwe, że jakiś błąd -> przy jednym teście wstawił mi w energyGivenToOffspriong -1
                if (energyNeededToReproduce <= value) {
                    setEnergyGivenToOffspring(energyNeededToReproduce-1);
                } else {
                    setEnergyGivenToOffspring(value);
                }
                break;
            case 9:
                if (energyGivenToOffspring > value) {
                    setEnergyNeededToReproduce(energyGivenToOffspring+1);
                } else {
                    setEnergyNeededToReproduce(value);
                }
                break;
            case 10:
                if (maximumNumberOfMutations > value) {
                    setLengthOfGenes(maximumNumberOfMutations);
                } else {
                    setLengthOfGenes(value);
                }
                break;
            case 11:
                if (lengthOfGenes < value) {
                    setMinimumNumberOfMutations(lengthOfGenes);
                } else {
                    setMinimumNumberOfMutations(value);
                }
                break;
            case 12:
                if (lengthOfGenes < value) {
                    setMaximumNumberOfMutations(lengthOfGenes);
                } else if (value < minimumNumberOfMutations) {
                    setMaximumNumberOfMutations(minimumNumberOfMutations);
                } else {
                    setMaximumNumberOfMutations(value);
                }
                break;
        }
    }

    private void setupDefaultValues (Slider someSlider, int index){
        int value = (int) someSlider.getValue();
        setAll(index, value);
    }

    private void setupSlider(Slider someSlider, Label someLabel, int index) {
        someSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int value = (int) someSlider.getValue();
                someLabel.setText(Integer.toString(value));
                setAll(index, value);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupDefaultValues(heightSlider, 1);
        setupDefaultValues(widthSlider, 2);
        setupDefaultValues(startingAnimalEnergySlider,3);
        setupDefaultValues(startingNumberOfAnimalsSlider,4);
        setupDefaultValues(startingNumberOfPlantsSlider, 5);
        setupDefaultValues(numberOfDailyAddedPlantsSlider, 6);
        setupDefaultValues(amountOfEnergyFromEatingSlider,7);
        setupDefaultValues(energyGivenToOffspringSlider,8);
        setupDefaultValues(energyNeededToReproduceSlider, 9);
        setupDefaultValues(LengthOfGenesSlider,  10);
        setupDefaultValues(minimumNumberOfMutationSlider, 11);
        setupDefaultValues(maximumNumberOfMutationSlider, 12);

        setupSlider(heightSlider, heightLabel, 1);
        setupSlider(widthSlider, widthLabel, 2);
        setupSlider(startingAnimalEnergySlider, startingAnimalEnergyLabel, 3);
        setupSlider(startingNumberOfAnimalsSlider, startingNumberOfAnimalsLabel, 4);
        setupSlider(startingNumberOfPlantsSlider, startingNumberOfPlantsLabel, 5);
        setupSlider(numberOfDailyAddedPlantsSlider, numberOfDailyAddedPlantsLabel, 6);
        setupSlider(amountOfEnergyFromEatingSlider, amountOfEnergyFromEatingLabel, 7);
        setupSlider(energyGivenToOffspringSlider, energyGivenToOffspringLabel, 8);
        setupSlider(energyNeededToReproduceSlider, energyNeededToReproduceLabel, 9);
        setupSlider(LengthOfGenesSlider, LengthOfGenesLabel, 10);
        setupSlider(minimumNumberOfMutationSlider, minimumNumberOfMutationLabel, 11);
        setupSlider(maximumNumberOfMutationSlider, maximumNumberOfMutationLabel, 12);

        plantGrowthListView.getItems().addAll(plantsGrowthToChoose);
        MutationListView.getItems().addAll(mutationsToChoose);

        plantGrowthListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                plantsGrowthVariant = (String) plantGrowthListView.getSelectionModel().getSelectedItem();
            }
        });

        MutationListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mutationVariant = (String) MutationListView.getSelectionModel().getSelectedItem();
            }
        });

    }



    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setStartingNumberOfAnimals(int startingNumberOfAnimals) {
        this.startingNumberOfAnimals = startingNumberOfAnimals;
    }

    public void setStartingNumberOfPlants(int startingNumberOfPlants) {
        this.startingNumberOfPlants = startingNumberOfPlants;
    }

    public void setNumberOfDailyAddedPlants(int numberOfDailyAddedPlants) {
        this.numberOfDailyAddedPlants = numberOfDailyAddedPlants;
    }

    public void setStartingAnimalEnergy(int startingAnimalEnergy) {
        this.startingAnimalEnergy = startingAnimalEnergy;
    }

    public void setAmountOfEnergyFromEating(int amountOfEnergyFromEating) {
        this.amountOfEnergyFromEating = amountOfEnergyFromEating;
    }

    public void setEnergyNeededToReproduce(int energyNeededToReproduce) {
        this.energyNeededToReproduce = energyNeededToReproduce;
    }

    public void setEnergyGivenToOffspring(int energyGivenToOffspring) {
        this.energyGivenToOffspring = energyGivenToOffspring;
    }

    public void setLengthOfGenes(int lengthOfGenes) {
        this.lengthOfGenes = lengthOfGenes;
    }

    public void setMinimumNumberOfMutations(int minimumNumberOfMutations) {
        this.minimumNumberOfMutations = minimumNumberOfMutations;
    }

    public void setMaximumNumberOfMutations(int maximumNumberOfMutations) {
        this.maximumNumberOfMutations = maximumNumberOfMutations;
    }

}