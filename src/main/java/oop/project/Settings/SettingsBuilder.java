package oop.project.Settings;

/**
 * Nieklasyczny builder, bo settery nie zwracają this,
 * aby rozbić proces tworzenia recordów ustawień.
 */
public class SettingsBuilder {
    private int eatingEnergy;
    private int initialEnergy;
    private int breedableEnergy;
    private int breedingEnergy;
    private int minMutations;
    private int maxMutations;
    private int genomeLength;
    private int width;
    private int height;
    PlantGrowthVariant plantGrowthVariant;
    MutationVariant mutationVariant;
    int initialNumOfPlants;
    int initialNumOfAnimals;
    int dailyGrowth;
    int durationOfDay;

    //Ustawia wartości domyślne
    public SettingsBuilder() {
        eatingEnergy = 5;
        initialEnergy = 20;
        breedableEnergy = 50;
        breedingEnergy = initialEnergy / 2;
        genomeLength = 7;
        minMutations = 0;
        maxMutations = genomeLength / 3;
        width = 15;
        height = 10;
        plantGrowthVariant = PlantGrowthVariant.EQUATOR;
        mutationVariant = MutationVariant.FULLY_RANDOM;
        initialNumOfPlants = height;
        initialNumOfAnimals = initialNumOfPlants / 2;
        dailyGrowth = initialNumOfAnimals;
    }

    public void setEatingEnergy(int eatingEnergy) {
        this.eatingEnergy = eatingEnergy;
    }

    public void setInitialEnergy(int initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    public void setBreedableEnergy(int breedableEnergy) {
        this.breedableEnergy = breedableEnergy;
    }

    public void setBreedingEnergy(int breedingEnergy) {
        this.breedingEnergy = breedingEnergy;
    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public void setMinMutations(int minMutations) {
        this.minMutations = minMutations;
    }

    public void setMaxMutations(int maxMutations) {
        this.maxMutations = maxMutations;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPlantGrowthVariant(PlantGrowthVariant plantGrowthVariant) {
        this.plantGrowthVariant = plantGrowthVariant;
    }

    public void setMutationVariant(MutationVariant mutationVariant) {
        this.mutationVariant = mutationVariant;
    }

    public void setInitialNumOfPlants(int initialNumOfPlants) {
        this.initialNumOfPlants = initialNumOfPlants;
    }

    public void setInitialNumOfAnimals(int initialNumOfAnimals) {
        this.initialNumOfAnimals = initialNumOfAnimals;
    }

    public void setDailyGrowth(int dailyGrowth) {
        this.dailyGrowth = dailyGrowth;
    }

    public AnimalSettings buildAnimalSettings() {
        return new AnimalSettings(
                eatingEnergy, initialEnergy, breedableEnergy, breedingEnergy,
                minMutations, maxMutations, genomeLength, mutationVariant
        );
    }

    public MapSettings buildMapSettings() {
        return new MapSettings(
                width, height, plantGrowthVariant,
                initialNumOfPlants, initialNumOfAnimals, dailyGrowth, durationOfDay
        );
    }

    public void setDurationOfDays(int durationOfDay) {
        this.durationOfDay = durationOfDay;
    }
}
