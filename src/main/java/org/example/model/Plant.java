package org.example.model;

public class Plant implements WorldElement{
    Vector2d position;

    public Plant (Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }
}
