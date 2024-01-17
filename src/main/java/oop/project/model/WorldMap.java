package oop.project.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import oop.project.Settings.AnimalSettings;
import oop.project.Settings.MapSettings;
import java.util.HashSet;
import java.util.Set;

public class WorldMap {
    private final MapSettings mapSettings;
    private final AnimalSettings animalSettings;
    private final Multimap<Vector2d, Animal> animalMap = ArrayListMultimap.create();

    //private Map<Vector2d, Plant> plantMap = new HashMap<>();
    // Powinno wystarczyć tylko info o pozycji roślinek:
    private final Set<Vector2d> plantPositions = new HashSet<>();
    public WorldMap(MapSettings mapSettings, AnimalSettings animalSettings) {
        this.mapSettings = mapSettings;
        this.animalSettings = animalSettings;
        growPlants(mapSettings.initialNumOfPlants());
        placeInitialAnimals();
    }

    private void placeInitialAnimals() {
        for (int i = 0; i < mapSettings.initialNumOfAnimals(); i++) {
            placeAnimal(randomLegalPosition());
        }
    }

    public Vector2d randomLegalPosition() {
        return new Vector2d(
                (int) Math.floor(Math.random() * mapSettings.width()),
                (int) Math.floor(Math.random() * mapSettings.height())
        );
    }

    public void removeDeadAnimals() { // TODO
    }

    public void moveAllAnimals() { // TODO
    }

    public void eatPlants() { // TODO
    }

    public void breedAnimals() { // TODO
    }

    public void growPlants(int numOfPlants) { // TODO
        int grown = 0;
        while (grown < numOfPlants) {
            Vector2d position;
            double draw = Math.random() * 10.0;
            if (draw < 8.0)
                position = mapSettings.plantGrowthVariant().choosePreferredPosition(mapSettings, this);
            else
                position = randomLegalPosition();
            if (!plantPositions.contains(position)) {
                plantPositions.add(position);
                grown++;
            }
        }
    }

    public void visualize() { // TODO
    }

    public void placeAnimal(Vector2d position) {
        if (!position.isLegal(mapSettings))
            throw new IllegalArgumentException();
        animalMap.put(position, new Animal(animalSettings));
    }

    // animal ma funkcje turn(), która obraca zwierzaka zgodnie z genotypem

    private boolean checkIfMoveIsLegal(){
        return false;
    }

    public void makeMove(){
        // if move is legal make a single move on a multimap
    }

    public Set<Vector2d> getPlantPositions() {
        return plantPositions;
    }
}
