package oop.project.Settings;

import oop.project.model.Vector2d;
import oop.project.model.WorldMap;

import java.util.Set;

public enum PlantGrowthVariant {
    EQUATOR,
    JUNGLE;

    private final double EQUATOR_PERCENTAGE = 0.4; // Ułamek wysokości mapy, który ma stanowić równik

    public Vector2d choosePreferredPosition(MapSettings mapSettings, WorldMap worldMap) {
        if (this == EQUATOR) {
            return new Vector2d(
                    (int) Math.floor(Math.random() * mapSettings.width()),
                    (int) Math.floor(Math.random()
                            * mapSettings.height() * EQUATOR_PERCENTAGE
                            + mapSettings.height() * (1.0 - EQUATOR_PERCENTAGE) / 2.0)
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
            return Vector2d.fitInsideMap(new Vector2d(
                    (int) Math.floor(Math.random() * 3 + randomPlantPosition.x() - 1),
                    clamp(Math.floor(Math.random() * 3 + randomPlantPosition.y() - 1),
                            mapSettings.height() - 1)
            ), mapSettings);
        }
    }

    private static int clamp(double value, int max) {
        return (int) Math.max(0, Math.min(max, value));
    }
}
