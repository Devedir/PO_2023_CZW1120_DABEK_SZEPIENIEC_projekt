package oop.project.Settings;

public record AnimalSettings(
        int eatingEnergy,
        int initialEnergy,
        int breedableEnergy,
        int breedingEnergy,
        int minMutations,
        int maxMutations,
        int genomeLength,
        MutationVariant mutationVariant
) {
}
