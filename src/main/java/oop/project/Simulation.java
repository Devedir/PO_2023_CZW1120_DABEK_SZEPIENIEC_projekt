package oop.project;

import oop.project.Settings.AnimalSettings;
import oop.project.Settings.MapSettings;
import oop.project.model.WorldMap;

public class Simulation { // TODO
    WorldMap worldMap;
    MapSettings mapSettings;
    AnimalSettings animalSettings;

    public Simulation(MapSettings mapSettings, AnimalSettings animalSettings) {
        this.mapSettings = mapSettings;
        this.animalSettings = animalSettings;
        worldMap = new WorldMap(mapSettings);
    }

    public void run() {
        System.out.println("Start!");
    }
}
