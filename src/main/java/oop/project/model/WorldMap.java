package oop.project.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import oop.project.Interface.SimulationVisController;
import oop.project.Settings.AnimalSettings;
import oop.project.Settings.MapSettings;
import oop.project.Statistics.MapStats;
import oop.project.utils.AnimalComparator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class WorldMap {
    private final MapSettings mapSettings;
    private final AnimalSettings animalSettings;
    private final MapStats mapStats;
    private final ListMultimap<Vector2d, Animal> animalMap;
    private final Set<Vector2d> plantPositions;
    private SimulationVisController controller;

    public WorldMap(MapSettings mapSettings, AnimalSettings animalSettings) {
        this.mapSettings = mapSettings;
        this.animalSettings = animalSettings;
        plantPositions = new HashSet<>();
        animalMap = ArrayListMultimap.create();
        mapStats = new MapStats(mapSettings, animalSettings, this);
        growPlants(mapSettings.initialNumOfPlants());
        placeInitialAnimals();
    }

    private void placeInitialAnimals() {
        for (int i = 0; i < mapSettings.initialNumOfAnimals(); i++) {
            Animal newAnimal = new Animal(animalSettings);
            animalMap.put(randomLegalPosition(), newAnimal);
            mapStats.updateAnimals(1, newAnimal);
        }
    }

    public Vector2d randomLegalPosition() {
        return new Vector2d(
                (int) Math.floor(Math.random() * mapSettings.width()),
                (int) Math.floor(Math.random() * mapSettings.height())
        );
    }

    public void removeDeadAnimals() {
        ListMultimap<Vector2d, Animal> toRemove = ArrayListMultimap.create();
        animalMap.asMap().forEach((position, animalCollection) -> {
            for (Animal animal : animalCollection) {
                if (animal.getEnergy() <= 0) {
                    animal.die();
                    mapStats.deathUpdate(animal);
                    toRemove.put(position, animal);
                }
            }
        });
        toRemove.asMap().forEach((position, animalCollection) -> {
            for (Animal animal : animalCollection)
                animalMap.remove(position, animal);
        });
    }

    public void moveAllAnimals() {
        ListMultimap<Vector2d, Animal> newPositions = ArrayListMultimap.create();
        ListMultimap<Vector2d, Animal> oldPositions = ArrayListMultimap.create();
        animalMap.asMap().forEach((position, animalCollection) -> {
            for (Animal animal : animalCollection) {
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
                newPositions.put(newPosition, animal);
                oldPositions.put(position, animal);
                animal.turn();
            }
        });
        animalMap.putAll(newPositions);
        oldPositions.asMap().forEach((position, animalCollection) -> {
            for (Animal animal : animalCollection)
                animalMap.remove(position, animal);
        });
        for (Vector2d key : animalMap.keySet())
            animalMap.get(key).sort(Collections.reverseOrder(new AnimalComparator()));
    }

    public void eatPlants() {
        Iterator<Vector2d> iterator = plantPositions.iterator();
        while (iterator.hasNext()) {
            Vector2d plantPosition = iterator.next();
            if (!animalMap.containsKey(plantPosition))
                continue;
            Animal strongest = animalMap.get(plantPosition).get(0);
            strongest.eat();
            mapStats.eatingUpdate();
            iterator.remove();
        }
    }

    public void breedAnimals(int birthday) {
        ListMultimap<Vector2d, Animal> newborns = ArrayListMultimap.create();
        animalMap.asMap().forEach((position, animalCollection) -> {
            List<Animal> animalList = animalCollection.stream().toList();
            for (int i = 1; i < animalList.size(); i += 2) {
                if (animalList.get(i).getEnergy() < animalSettings.breedableEnergy())
                    break;
                Animal baby = animalList.get(i - 1).mateWith(animalList.get(i), birthday);
                newborns.put(position, baby);
                mapStats.breedingUpdate(baby);
            }
        });
        animalMap.putAll(newborns);
    }

    public void growPlants(int numOfPlants) {
        for (int grown = 0;
             grown < Math.min(numOfPlants,
                     mapSettings.width() * mapSettings.height() - mapStats.getNumOfPlants());
             grown++
        ) {
            Vector2d position;
            double draw = Math.random() * 10.0;
            if (draw < 8.0) {
                do position = mapSettings.plantGrowthVariant()
                        .choosePreferredPosition(mapSettings, this);
                while (plantPositions.contains(position));
            } else {
                final int MAX_ITERATIONS = mapSettings.width() * mapSettings.height() * 4;
                // Żeby uniknąć bezsensownego siedzenia wieczność w pętli (co jest możliwe przy zatłoczonej mapie)
                // zakładam, że po takiej ilości iteracji najprawdopodobniej nie ma już żadnych pól 2 kategorii,
                // więc dodaję roślinę na pole pierwszej kategorii.
                int i = 0;
                do {
                    if (i == MAX_ITERATIONS) {
                        do position = mapSettings.plantGrowthVariant()
                                .choosePreferredPosition(mapSettings, this);
                        while (plantPositions.contains(position));
                        break;
                    }
                    position = randomLegalPosition();
                    i++;
                } while (plantPositions.contains(position)
                        || mapSettings.plantGrowthVariant().isPreferred(position, plantPositions, mapSettings));
            }
            plantPositions.add(position);
            mapStats.addPlant();
        }
    }

    public void visualize() {
        controller.drawScene();
    }

    public void updateStats(boolean statsSaved, File file) {
        mapStats.dailyEnergyUpdate();
        mapStats.updateNumOfFreeFields();
        animalMap.asMap().forEach((position, animalCollection) -> {
            for (Animal animal : animalCollection)
                animal.updateAge();
        });

        if (!statsSaved) return;
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(mapStats.getNumOfAnimals() + ",");
            writer.write(mapStats.getNumOfPlants() + ",");
            writer.write(mapStats.getNumOfFreeFields() + ",");
            Optional<List<Integer>> MPG = mapStats.getMostPopularGenome();
            writer.write((MPG.isPresent() ? MPG.get().toString() : "-") + ",");
            OptionalDouble avgEnergy = mapStats.getAverageEnergy();
            writer.write((avgEnergy.isPresent() ? Double.toString(avgEnergy.getAsDouble()) : "-") + ",");
            OptionalDouble avgLS = mapStats.getAverageLifespan();
            writer.write((avgLS.isPresent() ? Double.toString(avgLS.getAsDouble()) : "-") + ",");
            OptionalDouble avgNOC = mapStats.getAverageNumOfChildren();
            writer.write(avgNOC.isPresent() ? Double.toString(avgNOC.getAsDouble()) : "-");
            writer.write(System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Vector2d> getPlantPositions() {
        return plantPositions;
    }

    public ListMultimap<Vector2d, Animal> getAnimalMap() {
        return animalMap;
    }

    public MapStats getMapStats() {
        return mapStats;
    }

    public MapSettings getMapSettings() {
        return mapSettings;
    }

    public boolean isAnimalAt (int x, int y) {
        Vector2d vector = new Vector2d(x, y);
        return animalMap.containsKey(vector);
    }

    public int getAnimalEnergyAtPosition(int x, int y) {
        Vector2d vector = new Vector2d(x, y);
        return animalMap.get(vector).get(0).getEnergy();
    }

    public AnimalSettings getAnimalSettings() {
        return animalSettings;
    }

    public void setController(SimulationVisController controller) {
        this.controller = controller;
    }
}
