package oop.project.model;

import oop.project.Settings.AnimalSettings;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Animal {
    private final AnimalSettings setts;
    private int direction;
    private final List<Integer> genome;
    private int activatedGene;
    private int energy;

    // Kolejne pokolenia
    public Animal(AnimalSettings setts, List<Integer> genome) {
        this.setts = setts;
        direction = (int) Math.round(Math.random() * 7);
        this.genome = genome;
        activatedGene = (int) Math.round(Math.random() * setts.genomeLength());
        energy = 2 * setts.breedingEnergy();
    }

    // Adam i Ewa
    public Animal(AnimalSettings setts) {
        this(setts, new Random().ints(0, 7)
                                .boxed()
                                .limit(setts.genomeLength())
                                .collect(Collectors.toList()));
        energy = setts.initialEnergy();
    }

    public void turn() {
        direction = (direction + genome.get(activatedGene)) % 8;
        activatedGene = (activatedGene + 1) % setts.genomeLength();
        energy--;
    }

    // Używane przy brzegu mapy
    public void bounce() {
        direction = (direction + 4) % 8;
    }

    public int getDirection() {
        return direction;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public int getEnergy() {
        return energy;
    }
}
