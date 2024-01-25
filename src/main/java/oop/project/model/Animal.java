package oop.project.model;

import oop.project.Interface.SimulationVisController;
import oop.project.Settings.AnimalSettings;

import java.util.*;
import java.util.stream.Collectors;

public class Animal {
    private final AnimalSettings animalSettings;
    private int direction;
    private final List<Integer> genome;
    private int activatedGene;
    private int energy;
    private int numOfPlantsEaten;
    private int age;
    private final int birthday;
    private int numOfAllChildren;
    private final Optional<Animal> parent1;
    private final Optional<Animal> parent2;
    private final List<Animal> descendants;
    private Optional<SimulationVisController> tracker;

    // Kolejne pokolenia
    public Animal(AnimalSettings animalSettings, List<Integer> genome, Animal parent1, Animal parent2, int birthday) {
        this.animalSettings = animalSettings;
        direction = (int) (Math.random() * 8);
        this.genome = genome;
        activatedGene = (int) (Math.random() * animalSettings.genomeLength());
        energy = 2 * animalSettings.breedingEnergy();
        numOfPlantsEaten = 0;
        age = 0;
        this.birthday = birthday;
        descendants = new ArrayList<>();
        numOfAllChildren = 0;
        this.parent1 = parent1 == null ?
                Optional.empty() : Optional.of(parent1);
        this.parent2 = parent2 == null ?
                Optional.empty() : Optional.of(parent2);
        tracker = Optional.empty();
    }

    // Adam i Ewa
    public Animal(AnimalSettings animalSettings) {
        this(animalSettings, new Random().ints(0, 8)
                                .boxed()
                                .limit(animalSettings.genomeLength())
                                .collect(Collectors.toList()),
                null, null, 1);
        energy = animalSettings.initialEnergy();
    }

    public void turn() {
        direction = (direction + genome.get(activatedGene)) % 8;
        activatedGene = (activatedGene + 1) % animalSettings.genomeLength();
        energy--;
    }

    // Używane przy brzegu mapy
    public void bounce() {
        direction = (direction + 4) % 8;
        energy--;
    }

    public void eat() {
        energy += animalSettings.eatingEnergy();
        numOfPlantsEaten++;
    }

    public Animal mateWith(Animal weakerPartner, int birthday) {
        double cuttingPercentage = (double) energy / (energy + weakerPartner.getEnergy());
        int numOfStrongerGenes = (int) Math.ceil(genome.size() * cuttingPercentage);
        List<Integer> newGenome = new ArrayList<>();
        if (Math.random() < 0.5) { // strona lewa
            newGenome.addAll(genome.subList(0, numOfStrongerGenes));
            newGenome.addAll(weakerPartner.getGenome().subList(numOfStrongerGenes, animalSettings.genomeLength()));
        } else { // strona prawa
            int numOfWeakerGenes = animalSettings.genomeLength() - numOfStrongerGenes;
            newGenome.addAll(weakerPartner.getGenome().subList(0, numOfWeakerGenes));
            newGenome.addAll(genome.subList(numOfWeakerGenes, animalSettings.genomeLength()));
        }
        animalSettings.mutationVariant().mutateGenome(newGenome, animalSettings);
        Animal child = new Animal(animalSettings, newGenome, this, weakerPartner, birthday);
        numOfAllChildren++;
        descendants.add(child);
        return child;
    }

    public void die() {
        parent1.ifPresent(animal -> animal.reattachDescendants(this));
        parent2.ifPresent(animal -> animal.reattachDescendants(this));
    }

    void reattachDescendants(Animal deadChild) {
        descendants.addAll(deadChild.getDescendants());
        descendants.remove(deadChild);
    }

    public int getNumOfLivingDescendants() {
        if (descendants.isEmpty()) return 0;
        return descendants.stream()
                .map(Animal::getNumOfLivingDescendants)
                .reduce(descendants.size(), Integer::sum);
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

    public int getNumOfPlantsEaten() {
        return numOfPlantsEaten;
    }

    public int getAge() {
        return age;
    }

    public void updateAge() {
        age++;
    }

    public int getDeathDate() {
        if (energy > 0)
            throw new IllegalStateException("Zwierzę nadal żyje!");
        return birthday + age;
    }

    public int getNumOfChildren() {
        return numOfAllChildren;
    }

    public List<Animal> getDescendants() {
        return descendants;
    }

    public void setTracker(SimulationVisController tracker) {
        this.tracker = tracker == null ? Optional.empty() : Optional.of(tracker);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return energy == animal.getEnergy() && age == animal.getAge()
                && numOfAllChildren == animal.getNumOfChildren();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEnergy(), getAge(), getNumOfChildren());
    }
}
