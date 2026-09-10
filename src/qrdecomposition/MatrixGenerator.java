package qrdecomposition;

import java.util.Random;

public class MatrixGenerator {
    public static double[][] generateMatrix(int rows, int columns, double min, double max, long seed) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("The number of rows and columns must be positive.");
        }
        if (rows < columns) {
            throw new IllegalArgumentException("The number of rows must be greater than or equal to the number of columns.");
        }
        if (min >= max) {
            throw new IllegalArgumentException("The minimum value must be less than the maximum value.");
        }
        double[][] matrix = new double[rows][columns];
        Random r = new Random(seed);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = r.nextDouble(min, max);
            }
        }
        return matrix;
    }
}
