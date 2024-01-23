package oop.project.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import oop.project.Settings.AnimalSettings;
import oop.project.Settings.MapSettings;
import oop.project.Statistics.MapStats;

import java.util.HashSet;
import java.util.Set;

public class WorldMap {
    private final MapSettings mapSettings;
    private final AnimalSettings animalSettings;
    private final MapStats mapStats;
    private final Multimap<Vector2d, Animal> animalMap = ArrayListMultimap.create();
    private final Set<Vector2d> plantPositions = new HashSet<>();
    public WorldMap(MapSettings mapSettings, AnimalSettings animalSettings) {
        this.mapSettings = mapSettings;
        this.animalSettings = animalSettings;
        mapStats = new MapStats(mapSettings, animalSettings, this);
        growPlants(mapSettings.initialNumOfPlants());
        placeInitialAnimals();
    }

    private void placeInitialAnimals() {
        for (int i = 0; i < mapSettings.initialNumOfAnimals(); i++) {
            Animal newAnimal = new Animal(animalSettings);
            placeAnimal(randomLegalPosition(), newAnimal);
            mapStats.updateAnimals(1, newAnimal);
        }
    }

    public void placeAnimal(Vector2d position, Animal newAnimal) {
        if (!isLegal(position))
            throw new IllegalArgumentException("Nieprawidłowa pozycja: " + position);
        animalMap.put(position, newAnimal);
    }

    public Vector2d randomLegalPosition() {
        return new Vector2d(
                (int) Math.floor(Math.random() * mapSettings.width()),
                (int) Math.floor(Math.random() * mapSettings.height())
        );
    }

    public void removeDeadAnimals() { // TODO
    }

    public void moveAllAnimals() {
        animalMap.asMap().forEach((position, animalCollection) -> { // Po multimapach Google'a nie można iterować po ludzku
            for (Animal animal : animalCollection) {
                animal.turn();
                int direction = animal.getDirection();
                Vector2d newPosition = position.add(Vector2d.unitVector(direction));
                if (newPosition.y() < 0 || newPosition.y() >= mapSettings.height()) {
                    animal.bounce();
                    continue;
                } else if (newPosition.x() < 0) {
                    newPosition = new Vector2d(mapSettings.width() - 1, newPosition.y());
                } else if (newPosition.x() >= mapSettings.width()) {
                    newPosition = new Vector2d(0, newPosition.y());
                }
                animalMap.put(newPosition, animal);
                animalCollection.remove(animal);
            }
        });
    }

    public void eatPlants() { // TODO
    }

    public void breedAnimals() { // TODO
    }

    public void growPlants(int numOfPlants) {
        for (int grown = 0;
             grown < Math.min(numOfPlants, mapSettings.width() * mapSettings.height() - mapStats.getNumOfPlants());
             grown++
        ) {
            Vector2d position = new Vector2d(0, 0);
            // Wartość domyślna dla skrajnego przypadku nieznalezienia pozycji
            final int MAX_ITERATIONS = mapSettings.width() * mapSettings.height() * 3;
            // Żeby uniknąć bezsensownego siedzenia wieczność w pętli (co jest możliwe przy zatłoczonej mapie)
            double draw = Math.random() * 10.0;
            if (draw < 8.0) {
                int i = 0;
                do {
                    if (i == MAX_ITERATIONS) break;
                    position = mapSettings.plantGrowthVariant().choosePreferredPosition(mapSettings, this);
                    i++;
                } while (plantPositions.contains(position));
            } else {
                int i = 0;
                do {
                    if (i == MAX_ITERATIONS) break;
                    position = randomLegalPosition();
                    i++;
                } while (plantPositions.contains(position) || position.isNextToPlant(plantPositions, mapSettings));
            }
            plantPositions.add(position);
        }
    }

    public void visualize() { // TODO
    }

    public void updateStats() { // TODO: coś tu trzeba będzie pewnie jeszcze dokończyć
        mapStats.dailyEnergyUpdate();
        mapStats.dailyPlantsUpdate();
        animalMap.asMap().forEach((position, animalCollection) -> {
            for (Animal animal : animalCollection)
                animal.updateAge();
        });
    }

    private boolean isLegal(Vector2d position){
        return position.x() >= 0 && position.x() < mapSettings.width() &&
                position.y() >= 0 && position.y() < mapSettings.height();
    }

    public Set<Vector2d> getPlantPositions() {
        return plantPositions;
    }

    public Multimap<Vector2d, Animal> getAnimalMap() {
        return animalMap;
    }
}
