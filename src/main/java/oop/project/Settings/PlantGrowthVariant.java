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
                    (int) (Math.random() * mapSettings.width()),
                    (int) (Math.random()
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
                    (int) (Math.random() * 3 + randomPlantPosition.x() - 1),
                    clamp((int) (Math.random() * 3 + randomPlantPosition.y() - 1),
                            mapSettings.height() - 1)
            ), mapSettings);
        }
    }

    public boolean isPreferred(Vector2d position, Set<Vector2d> plantPositions, MapSettings mapSettings) {
        if (this == EQUATOR) {
            return position.y() >= mapSettings.height() * (1.0 - EQUATOR_PERCENTAGE) / 2.0
                    && position.y() <= mapSettings.height() * EQUATOR_PERCENTAGE
                                        + mapSettings.height() * (1.0 - EQUATOR_PERCENTAGE) / 2.0;
        } else {
            for (int direction = 0; direction < 8; direction++)
                if (plantPositions.contains(
                        Vector2d.fitInsideMap(position.add(Vector2d.unitVector(direction)), mapSettings)
                ))
                    return true;
            return false;
        }
    }

    private static int clamp(int value, int max) {
        return Math.max(0, Math.min(max, value));
    }
}
