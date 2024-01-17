package oop.project.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import oop.project.Settings.MapSettings;

import java.util.ArrayList;
import java.util.List;


public class WorldMap {
    private final MapSettings mapSettings;
    private final Multimap<Vector2d, Animal> animalMap = ArrayListMultimap.create();
    //private Map<Vector2d, Plant> plantMap = new HashMap<>();
    // Powinno wystarczyć tylko info o pozycji roślinek:
    private final List<Vector2d> plantPositions = new ArrayList<>();
    public WorldMap(MapSettings mapSettings) {
        this.mapSettings = mapSettings;
    }

    public void place() {

    }

    // rusza wszystkimi zwierzakami
    public void moveAll() {

    }

    // animal ma funkcje turn(), która obraca zwierzaka zgodnie z genotypem

    public boolean checkIfMoveIsLegal(){
        return false;
    }

    public void makeMove(){
        // if move is legal make a single move on a multimap
    }

}
