package oop.project.Statistics;

import oop.project.Settings.AnimalSettings;
import oop.project.Settings.MapSettings;
import oop.project.model.Animal;
import oop.project.model.Vector2d;
import oop.project.model.WorldMap;

import java.util.*;

public class MapStats {
    private final MapSettings mapSettings;
    private final AnimalSettings animalSettings;
    private final WorldMap worldMap;
    private int numOfAnimals;
    private int numOfPlants;
    private int numOfFreeFields;
    private final Map<List<Integer>, Integer> genomeCounter;
    private int energySum;
    private int lifespanSum;
    private int numOfDeadAnimals;
    private int numOfChildrenSum;

    public MapStats(MapSettings mapSettings, AnimalSettings animalSettings, WorldMap worldMap) {
        this.mapSettings = mapSettings;
        this.animalSettings = animalSettings;
        this.worldMap = worldMap;
        numOfAnimals = 0;
        numOfPlants = mapSettings.initialNumOfPlants();
        energySum = mapSettings.initialNumOfAnimals() * animalSettings.initialEnergy();
        lifespanSum = 0;
        numOfChildrenSum = 0;
        genomeCounter = new HashMap<>();
        updateNumOfFreeFields();
    }

    public void updateNumOfFreeFields() {
        Set<Vector2d> occupiedFields = new HashSet<>();
        occupiedFields.addAll(worldMap.getAnimalMap().keySet());
        occupiedFields.addAll(worldMap.getPlantPositions());
        numOfFreeFields = mapSettings.width() * mapSettings.height() - occupiedFields.size();
    }

    public void updateAnimals(int change, Animal animal) {
        numOfAnimals += change;
        List<Integer> genome = animal.getGenome();
        int newCount = genomeCounter.containsKey(genome) ?
                genomeCounter.get(genome) + change : 1;
        if (newCount != 0)
            genomeCounter.put(genome, newCount);
        else
            genomeCounter.remove(genome);
    }

    public void breedingUpdate(Animal newborn) {
        updateAnimals(1, newborn);
        numOfChildrenSum++;
    }

    public void deathUpdate(Animal deadAnimal) {
        updateAnimals(-1, deadAnimal);
        numOfDeadAnimals++;
        lifespanSum += deadAnimal.getAge();
        numOfChildrenSum -= deadAnimal.getNumOfChildren();
    }

    public void eatingUpdate() {
        numOfPlants--;
        energySum += animalSettings.eatingEnergy();
    }

    public void addPlant() {
        numOfPlants++;
    }

    public void dailyEnergyUpdate() {
        energySum -= numOfAnimals;
    }

    public int getNumOfAnimals() {
        return numOfAnimals;
    }

    public int getNumOfPlants() {
        return numOfPlants;
    }

    public int getNumOfFreeFields() {
        return numOfFreeFields;
    }

    public Optional<List<Integer>> getMostPopularGenome() {
        Optional<List<Integer>> anyGenome = genomeCounter.keySet().stream().findAny();
        if (anyGenome.isEmpty()) return Optional.empty();
        List<Integer> popularGenome = anyGenome.get();
        int maxCount = genomeCounter.get(popularGenome);
        for (Map.Entry<List<Integer>, Integer> entry : genomeCounter.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                popularGenome = entry.getKey();
            }
        }
        return Optional.of(popularGenome);
    }

    public OptionalDouble getAverageEnergy() {
        return numOfAnimals == 0 ?
                OptionalDouble.empty() : OptionalDouble.of((double) energySum / numOfAnimals);
    }

    public OptionalDouble getAverageLifespan() {
        return numOfDeadAnimals == 0 ?
                OptionalDouble.empty() : OptionalDouble.of((double) lifespanSum / numOfDeadAnimals);
    }

    public OptionalDouble getAverageNumOfChildren() {
        return numOfAnimals == 0 ?
                OptionalDouble.empty() : OptionalDouble.of((double) numOfChildrenSum / numOfAnimals);
    }

    public List<Vector2d> getMostPopularGenomePositions(){
        List<Vector2d> listOfPositions = new ArrayList<>();
        Optional<List<Integer>> mostPopularGenomeOptional = getMostPopularGenome();
        if (mostPopularGenomeOptional.isEmpty()) {
            return listOfPositions;
        }else {
            List<Integer> mostPopularGenome = mostPopularGenomeOptional.get();
            for (Vector2d position : worldMap.getAnimalMap().keySet()) {
                Collection<Animal> animalsAtPosition = worldMap.getAnimalMap().get(position);
                for (Animal animal : animalsAtPosition) {
                    if (animal.getGenome().equals(mostPopularGenome)) {
                        listOfPositions.add(position);
                        break;
                    }
                }
            }
            return listOfPositions;
        }
    }
}
