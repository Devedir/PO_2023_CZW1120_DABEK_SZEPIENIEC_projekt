package oop.project;

import oop.project.Settings.AnimalSettings;
import oop.project.Settings.MapSettings;
import oop.project.model.WorldMap;

public class Simulation implements Runnable { // TODO
    private final WorldMap worldMap;
    private int day;

    public Simulation(MapSettings mapSettings, AnimalSettings animalSettings) {
        worldMap = new WorldMap(mapSettings, animalSettings);
    }

    @Override
    public void run() {
        System.out.println("Start!");
        day = 1;
        worldMap.visualize();
        while (day < 10) { // Warunek tymczasowy
            worldMap.removeDeadAnimals();
            worldMap.moveAllAnimals();
            worldMap.eatPlants();
            worldMap.breedAnimals();
            worldMap.growPlants();
            worldMap.visualize();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Symulacja przerwana: " + e.getMessage());
            }
            day++;
        }
    }
}
