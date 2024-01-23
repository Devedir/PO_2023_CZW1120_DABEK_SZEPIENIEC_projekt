package oop.project.model;

import oop.project.Settings.AnimalSettings;
import oop.project.Statistics.AnimalStats;

import java.util.*;
import java.util.stream.Collectors;

public class Animal {
    private final AnimalSettings setts;
    private final AnimalStats animalStats;
    private int direction;
    private final List<Integer> genome;
    private int activatedGene;
    private int energy;
    private int age;
    private final Set<Animal> children;

    // Kolejne pokolenia
    public Animal(AnimalSettings setts, List<Integer> genome) {
        animalStats = new AnimalStats();
        this.setts = setts;
        direction = (int) Math.floor(Math.random() * 7);
        this.genome = genome;
        activatedGene = (int) Math.floor(Math.random() * setts.genomeLength());
        energy = 2 * setts.breedingEnergy();
        age = 0;
        children = new HashSet<>();
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

    public int getAge() {
        return age;
    }

    public void updateAge() {
        age++;
    }

    public Set<Animal> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return energy == animal.getEnergy() && age == animal.getAge()
                && getChildren().size() == animal.getChildren().size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEnergy(), getAge(), getChildren().size());
    }
}
