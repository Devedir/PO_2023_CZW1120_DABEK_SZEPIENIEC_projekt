package org.example.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class WorldMap {
    private int width;
    private int height;
    private Multimap<Vector2d, Animal> animalMap = ArrayListMultimap.create();
    private Map<Vector2d, Plant> plantMap = new HashMap<>();
    public WorldMap(int width, int height) {

    }

    public void place() {

    }

    // rusza wsyzstkimi zwierzakami
    public void moveAll() {

    }

    // animal ma funkcje getNextMove(), która oddaje wezwgledny kierunek ruchu, który zwraca byte

    public boolean checkIfMoveIsLegal(){
        return false;
    }

    public void makeMove(){
        // if move is legal make a single move on a multimap
    }

}
