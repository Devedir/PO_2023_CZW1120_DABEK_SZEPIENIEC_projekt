package oop.project;

import oop.project.Settings.AnimalSettings;
import oop.project.Settings.MapSettings;
import oop.project.model.WorldMap;

import java.io.File;

public class Simulation implements Runnable {
    private final WorldMap worldMap;
    private final MapSettings mapSettings;
    private int day;
    private boolean isPaused = false;
    private boolean statsSaved;
    private final File file;

    public Simulation(MapSettings mapSettings, AnimalSettings animalSettings, boolean areStatsSaved, int id) {
        this.mapSettings = mapSettings;
        worldMap = new WorldMap(mapSettings, animalSettings);
        statsSaved = areStatsSaved;
        this.file = new File("savedStats/data"+id+".csv");
    }

    @Override
    public void run() {
        System.out.println("Start!");
        day = 1;
        worldMap.visualize();
        while (worldMap.getMapStats().getNumOfAnimals() > 0) {
            worldMap.removeDeadAnimals();
            worldMap.moveAllAnimals();
            worldMap.eatPlants();
            worldMap.breedAnimals(day);
            worldMap.growPlants(mapSettings.dailyGrowth());
            worldMap.updateStats(statsSaved, file);
            worldMap.visualize();

            while (isPaused) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println("Symulacja przerwana: " + e.getMessage());
                }
            }


            try {
                Thread.sleep(mapSettings.durationOfDay());
            } catch (InterruptedException e) {
                System.err.println("Symulacja przerwana: " + e.getMessage());
            }
            day++;
        }
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public boolean getIsPaused() {
        return this.isPaused;
    }
}
