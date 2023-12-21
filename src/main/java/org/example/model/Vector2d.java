package org.example.model;

public record Vector2d(int x, int y) {
    @Override
    public String toString() {
        return "(%d, %d)".formatted(x, y);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x() && y == vector2d.y();
    }

}
