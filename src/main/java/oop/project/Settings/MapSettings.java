package oop.project.Settings;

public record MapSettings(
        int width,
        int height,
        PlantGrowthVariant plantGrowthVariant,
        int initialNumOfPlants,
        int initialNumOfAnimals,
        int dailyGrowth
) {
}
