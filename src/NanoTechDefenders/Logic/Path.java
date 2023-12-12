package NanoTechDefenders.Logic;

import NanoTechDefenders.Logic.Controlling.LogicController;

import java.awt.*;

/**
 * Die Klasse Path definiert den Pfad, dem die Feinde im Spiel folgen werden.
 */
public class Path {
    private static double xFactor = LogicController.getXFactor();
    private static double yFactor = LogicController.getYFactor();
    private static boolean pointsCalculated;

    // Array von Punkten, die die Ecken des Pfads definieren
    private static Point[] path = {
            new Point(-100, 135),
            new Point(230, 135),
            new Point(230, 390),
            new Point(487, 390),
            new Point(487, 70),
            new Point(742, 70),
            new Point(742, 582),
            new Point(102, 582),
            new Point(102, 710),
            new Point(935, 710),
            new Point(935, 135),
            new Point(1318, 135),
            new Point(1318, 262),
            new Point(1062, 262),
            new Point(1062, 390),
            new Point(1318, 390),
            new Point(1318, 517),
            new Point(1126, 517),
            new Point(1126, 710),
            new Point(1447, 710),
            new Point(1447, 135),
            new Point(1500, 135)
    };

    /**
     * Ruft das Array von Eckpunkten für den Pfad ab.
     *
     * @return Das Array von Eckpunkten.
     */
    public static Point[] getCorners() {
        if (!pointsCalculated) {
            for (Point p : path) {
                p.setLocation(p.getX() * xFactor, p.getY() * yFactor);
            }
            pointsCalculated = true;
        }

        return path;
    }
}
