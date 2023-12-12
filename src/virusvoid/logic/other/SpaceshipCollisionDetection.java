package virusvoid.logic.other;

import virusvoid.logic.objects.GameObject;

import java.awt.*;

/**
 * Handles collision detection between the spaceship and other game objects (rectangles) using the Separating Axis Theorem (SAT).
 */
public class SpaceshipCollisionDetection {

    /**
     * Checks for collision between the spaceship hitbox and a game object.
     *
     * @param spaceshipHitbox The hitbox of the spaceship.
     * @param gameObject      The game object to check for collision.
     * @return {@code true} if a collision is detected, {@code false} otherwise.
     */
    public static boolean collision(Polygon spaceshipHitbox, GameObject gameObject) {
        Rectangle rectangleToCheck = new Rectangle(gameObject.x, gameObject.y, gameObject.getWidth(), gameObject.getHeight());

        // Divide the complex polygon into triangles
        Polygon[] triangles = triangulateComplexPolygon(spaceshipHitbox);

        // Collision detection for each triangle
        for (Polygon triangle : triangles) {
            if (!axisSeparation(triangle, rectangleToCheck)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for separation along an axis for each edge of the polygon.
     *
     * @param polygon    The polygon for which separation is checked.
     * @param rectangle  The rectangle for which separation is checked.
     * @return {@code true} if separation is found, {@code false} otherwise.
     */
    private static boolean axisSeparation(Polygon polygon, Rectangle rectangle) {
        int[] xPoints = polygon.xpoints;
        int[] yPoints = polygon.ypoints;
        int pointCount = polygon.npoints;

        for (int i = 0; i < pointCount; i++) {
            int x1 = xPoints[i];
            int y1 = yPoints[i];
            int x2 = xPoints[(i + 1) % pointCount];
            int y2 = yPoints[(i + 1) % pointCount];

            if (checkAxisSeparation(polygon, rectangle, x1, y1, x2, y2)) {
                return true; // Separation found
            }
        }

        return false; // No Separation found
    }

    /**
     * Checks for separation along a specified axis.
     *
     * @param polygon    The polygon for which separation is checked.
     * @param rectangle  The rectangle for which separation is checked.
     * @param x1         The x-coordinate of the first point on the edge.
     * @param y1         The y-coordinate of the first point on the edge.
     * @param x2         The x-coordinate of the second point on the edge.
     * @param y2         The y-coordinate of the second point on the edge.
     * @return {@code true} if separation is found, {@code false} otherwise.
     */
    private static boolean checkAxisSeparation(Polygon polygon, Rectangle rectangle, int x1, int y1, int x2, int y2) {
        int edgeX = x2 - x1;
        int edgeY = y2 - y1;

        // Normalize the axis
        double length = Math.sqrt(edgeX * edgeX + edgeY * edgeY);
        double axisX = -edgeY / length;
        double axisY = edgeX / length;


        // Projection of the corners of the polygon onto the axis
        double[] polygonProjections = projectPolygonOntoAxis(polygon, axisX, axisY);

        // Projection of the rectangle onto the axis
        double[] rectangleProjections = projectRectangleOntoAxis(rectangle, axisX, axisY);

        // Checks for Separation
        return polygonProjections[1] < rectangleProjections[0] || rectangleProjections[1] < polygonProjections[0];
    }

    /**
     * Projects polygon vertices onto a specified axis.
     *
     * @param polygon  The polygon to project.
     * @param axisX    The x-component of the axis.
     * @param axisY    The y-component of the axis.
     * @return An array containing the minimum and maximum projections.
     */
    private static double[] projectPolygonOntoAxis(Polygon polygon, double axisX, double axisY) {
        int[] xPoints = polygon.xpoints;
        int[] yPoints = polygon.ypoints;
        int pointCount = polygon.npoints;

        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < pointCount; i++) {
            double dotProduct = xPoints[i] * axisX + yPoints[i] * axisY;
            min = Math.min(min, dotProduct);
            max = Math.max(max, dotProduct);
        }

        return new double[]{min, max};
    }

    /**
     * Projects a rectangle onto a specified axis.
     *
     * @param rectangle  The rectangle to project.
     * @param axisX      The x-component of the axis.
     * @param axisY      The y-component of the axis.
     * @return An array containing the minimum and maximum projections.
     */
    private static double[] projectRectangleOntoAxis(Rectangle rectangle, double axisX, double axisY) {
        int x1 = rectangle.x;
        int y1 = rectangle.y;
        int x2 = rectangle.x + rectangle.width;
        int y2 = rectangle.y + rectangle.height;


        double projection1 = x1 * axisX + y1 * axisY;
        double projection2 = x2 * axisX + y2 * axisY;
        return new double[]{Math.min(projection1, projection2), Math.max(projection1, projection2)};
    }

    /**
     * Triangulates a complex polygon into multiple triangles.
     *
     * @param complexPolygon  The complex polygon to be triangulated.
     * @return An array of triangles.
     */
    private static Polygon[] triangulateComplexPolygon(Polygon complexPolygon) {
        Polygon[] triangles = new Polygon[7];

        int[][] triangleIndices = {
                {0, 1, 8},
                {1, 2, 3},
                {1, 2, 4},
                {1, 4, 5},
                {1, 5, 8},
                {5, 6, 8},
                {6, 7, 8}
        };

        for (int i = 0; i < triangleIndices.length; i++) {
            triangles[i] = extractTriangle(complexPolygon, triangleIndices[i]);
        }

        return triangles;
    }

    /**
     * Extracts a triangle from a polygon based on specified vertex indices.
     *
     * @param polygon  The polygon from which to extract the triangle.
     * @param indices  The vertex indices of the triangle.
     * @return A new polygon representing the extracted triangle.
     */
    private static Polygon extractTriangle(Polygon polygon, int[] indices) {
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        for (int i = 0; i < 3; i++) {
            int index = indices[i];
            xPoints[i] = polygon.xpoints[index];
            yPoints[i] = polygon.ypoints[index];
        }

        return new Polygon(xPoints, yPoints, 3);
    }
}