package oop.project;

import oop.project.Settings.SettingsBuilder;
import oop.project.Settings.MutationVariant;
import oop.project.Settings.PlantGrowthVariant;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Tymczasowo input jest zwyczajnym wpisywaniem w konsolę
        Scanner scanner = new Scanner(System.in);
        SettingsBuilder builder = new SettingsBuilder();

        System.out.println("Dzień dobry panie Darwin, możemy rozpoczynać!");
        System.out.println("Podaj proszę parametry symulacji. Możesz zostawić domyślne wpisując wartość nieliczbową.");
        System.out.print("Wysokość mapy: ");
        if (scanner.hasNextInt()) builder.setHeight(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Szerokość mapy: ");
        if (scanner.hasNextInt()) builder.setWidth(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Wariant wzrostu roślin (1/2): ");
        if (scanner.hasNextInt()) builder.setPlantGrowthVariant(
                scanner.nextInt() == 2 ?
                    PlantGrowthVariant.JUNGLE : PlantGrowthVariant.EQUATOR
        );
        else scanner.nextLine();
        System.out.print("Wariant mutacji (1/2): ");
        if (scanner.hasNextInt()) builder.setMutationVariant(
                scanner.nextInt() == 2 ?
                        MutationVariant.SLIGHT_CORRECTION : MutationVariant.FULLY_RANDOM
        );
        else scanner.nextLine();
        System.out.print("Początkowa licza roślin: ");
        if (scanner.hasNextInt()) builder.setInitialNumOfPlants(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Początkowa liczba zwierząt: ");
        if (scanner.hasNextInt()) builder.setInitialNumOfAnimals(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Dzienna ilość nowych roślin: ");
        if (scanner.hasNextInt()) builder.setDailyGrowth(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Początkowa energia zwierzaka: ");
        if (scanner.hasNextInt()) builder.setInitialEnergy(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Ilość energii z jedzenia: ");
        if (scanner.hasNextInt()) builder.setEatingEnergy(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Energia potrzebna do rozmnażania: ");
        if (scanner.hasNextInt()) builder.setBreedableEnergy(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Energia oddawana potomkowi: ");
        if (scanner.hasNextInt()) builder.setBreedingEnergy(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Minimalna liczba mutacji: ");
        if (scanner.hasNextInt()) builder.setMinMutations(scanner.nextInt());
        else scanner.nextLine();
        System.out.print("Maksymalna liczba mutacji: ");
        if (scanner.hasNextInt()) builder.setMaxMutations(scanner.nextInt());
        else scanner.nextLine();

        Simulation simulation = new Simulation(
                builder.buildMapSettings(),
                builder.buildAnimalSettings()
        );

        simulation.run();
    }
}
