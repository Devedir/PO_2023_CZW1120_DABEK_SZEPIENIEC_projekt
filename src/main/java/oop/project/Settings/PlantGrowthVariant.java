package oop.project.Settings;

import oop.project.model.Vector2d;
import oop.project.model.WorldMap;

import java.util.Set;

public enum PlantGrowthVariant {
    EQUATOR,
    JUNGLE;

    public Vector2d choosePreferredPosition(MapSettings mapSettings, WorldMap worldMap) {
        if (this == EQUATOR) {
            final double EQUATOR_SIZE = 0.4; // Ułamek wysokości mapy, który ma stanowić równik
            return new Vector2d(
                    (int) Math.floor(Math.random() * mapSettings.width()),
                    (int) Math.floor(Math.random()
                            * mapSettings.height() * EQUATOR_SIZE
                            + mapSettings.height() * (1.0 - EQUATOR_SIZE) / 2.0)
            );
        } else {
            Set<Vector2d> plantPositions = worldMap.getPlantPositions();
            Vector2d randomPlantPosition;
            if (plantPositions.isEmpty())
                randomPlantPosition = worldMap.randomLegalPosition();
            else
                randomPlantPosition = plantPositions.stream()
                        .skip((long) (Math.random() * (plantPositions.size() - 1)))
                        .findFirst()
                        .get(); // Bezpieczne w tym przypadku
            return new Vector2d(
                    clamp(Math.floor(Math.random() * 3 + randomPlantPosition.x() - 1),
                            mapSettings.width() - 1),
                    clamp(Math.floor(Math.random() * 3 + randomPlantPosition.y() - 1),
                            mapSettings.height() - 1)
            );
        }
    }

    private static int clamp(double value, int max) {
        return (int) Math.max(0, Math.min(max, value));
    }
}
