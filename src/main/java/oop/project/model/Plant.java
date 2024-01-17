package oop.project.model;

// imo niepotrzebna klasa
public class Plant implements WorldElement{
    private Vector2d position;

    public Plant (Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }
}
