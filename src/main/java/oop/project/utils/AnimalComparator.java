package oop.project.utils;

import oop.project.model.Animal;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal a1, Animal a2) {
        int result = Integer.compare(a1.getEnergy(), a2.getEnergy());
        if (result != 0) return result;
        result = Integer.compare(a1.getAge(), a2.getAge());
        if (result != 0) return result;
        result = Integer.compare(a1.getNumOfChildren(), a2.getNumOfChildren());
        return result;
    }
}
