package oop.project;

import oop.project.Settings.AnimalSettings;
import oop.project.Settings.MapSettings;
import oop.project.model.WorldMap;

public class Simulation implements Runnable { // TODO
    private final WorldMap worldMap;
    private final MapSettings mapSettings;
    private final AnimalSettings animalSettings;
    private int day;

    public Simulation(MapSettings mapSettings, AnimalSettings animalSettings) {
        this.mapSettings = mapSettings;
        this.animalSettings = animalSettings;
        worldMap = new WorldMap(mapSettings);
    }

    @Override
    public void run() {
        System.out.println("Start!");
        initializeSimulation();
        day = 1;
        visualize();
        while (day < 10) { // Warunek tymczasowy
            removeDeadAnimals();
            moveAllAnimals();
            eatPlants();
            breedAnimals();
            growPlants();
            visualize();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Symulacja przerwana: " + e.getMessage());
            }
            day++;
        }
    }

    private void initializeSimulation() { // TODO
    }

    private void removeDeadAnimals() { // TODO
    }

    private void moveAllAnimals() { // TODO
    }

    private void eatPlants() { // TODO
    }

    private void breedAnimals() { // TODO
    }

    private void growPlants() { // TODO
    }

    private void visualize() { // TODO
    }
}
