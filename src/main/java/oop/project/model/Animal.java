package oop.project.model;

import oop.project.Settings.AnimalSettings;
import oop.project.Statistics.AnimalStats;

import java.util.*;
import java.util.stream.Collectors;

public class Animal {
    private final AnimalSettings animalSettings;
    private final AnimalStats animalStats;
    private int direction;
    private final List<Integer> genome;
    private int activatedGene;
    private int energy;
    private int age;
    private int birthday;
    private final List<Animal> children;
    private Optional<Animal> parent1;
    private Optional<Animal> parent2;
    private List<Animal> descendants;

    // Kolejne pokolenia
    public Animal(AnimalSettings animalSettings, List<Integer> genome, Animal parent1, Animal parent2, int birthday) {
        animalStats = new AnimalStats();
        this.animalSettings = animalSettings;
        direction = (int) Math.floor(Math.random() * 7);
        this.genome = genome;
        activatedGene = (int) Math.floor(Math.random() * animalSettings.genomeLength());
        energy = 2 * animalSettings.breedingEnergy();
        age = 0;
        this.birthday = birthday;
        children = new ArrayList<>();
        this.parent1 = parent1 == null ?
                Optional.empty() : Optional.of(parent1);
        this.parent2 = parent2 == null ?
                Optional.empty() : Optional.of(parent2);
    }

    // Adam i Ewa
    public Animal(AnimalSettings animalSettings) {
        this(animalSettings, new Random().ints(0, 7)
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
    }

    public void eat() {
        energy += animalSettings.eatingEnergy();
    }

    public Animal mateWith(Animal weakerPartner, int birthday) {
        double cuttingPercentage = (double) weakerPartner.getEnergy() / energy;
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
        children.add(child);
        descendants.add(child);
        return child;
    }

    public void die() {
        parent1.ifPresent(animal -> animal.reattachDescendants(this));
        parent2.ifPresent(animal -> animal.reattachDescendants(this));
    }

    public void reattachDescendants(Animal deadChild) {
        descendants.addAll(deadChild.getChildren());
        descendants.remove(deadChild);
        children.remove(deadChild);
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

    public List<Animal> getChildren() {
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
