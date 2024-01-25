package oop.project.Settings;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public enum MutationVariant {
    FULLY_RANDOM,
    SLIGHT_CORRECTION;

    public void mutateGenome(List<Integer> genome, AnimalSettings animalSettings) {
        int numOfChanges = (int) (Math.random() * animalSettings.genomeLength());
        Set<Integer> indices = new HashSet<>();
        Random random = new Random();
        while (indices.size() < numOfChanges)
            indices.add(random.nextInt(animalSettings.genomeLength()));
        if (this == FULLY_RANDOM)
            for (int index : indices)
                genome.set(index, (int) (Math.random() * 8));
        else
            for (int index : indices) {
                int change = Math.random() < 0.5 ? -1 : 1;
                int val = genome.get(index) + change;
                int newGene = switch (val) {
                    case -1 -> 7;
                    case 8 -> 0;
                    default -> val;
                };
                genome.set(index, newGene);
            }
    }
}
