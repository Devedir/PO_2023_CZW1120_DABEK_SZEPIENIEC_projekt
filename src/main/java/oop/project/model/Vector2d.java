package oop.project.model;

import oop.project.Settings.MapSettings;

import java.util.Set;

public record Vector2d(int x, int y) {
    @Override
    public String toString() {
        return "(%d, %d)".formatted(x, y);
    }

    public static Vector2d unitVector(int direction) {
        return switch (direction) {
            case 0 -> new Vector2d(0, 1);
            case 1 -> new Vector2d(1, 1);
            case 2 -> new Vector2d(1, 0);
            case 3 -> new Vector2d(1, -1);
            case 4 -> new Vector2d(0, -1);
            case 5 -> new Vector2d(-1, -1);
            case 6 -> new Vector2d(-1, 0);
            case 7 -> new Vector2d(-1, 1);
            default -> throw new IllegalArgumentException("Nieprawidłowy kierunek: " + direction);
        };
    }

    public static Vector2d fitInsideMap(Vector2d vector, MapSettings mapSettings) {
        return new Vector2d(vector.x() % mapSettings.width(), vector.y());
    }

    public boolean isNextToPlant(Set<Vector2d> plantPositions, MapSettings mapSettings) {
        for (int direction = 0; direction < 8; direction++)
            if (plantPositions.contains(
                    fitInsideMap(this.add(unitVector(direction)), mapSettings)
                ))
                    return true;
        return false;
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x() && y <= other.y();
    }

    public boolean follows(Vector2d other) {
        return x >= other.x() && y >= other.y();
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x(), y + other.y());
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x(), y - other.y());
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(x, other.x()), Math.max(y, other.y()));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(x, other.x()), Math.min(y, other.y()));
    }

    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }
}
