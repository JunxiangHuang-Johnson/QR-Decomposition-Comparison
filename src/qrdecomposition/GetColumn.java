package qrdecomposition;

public class GetColumn {
    public static double[] getColumn(double[][] matrix, int column) {
        if (column < 0 || column >= matrix[0].length) {
            throw new IllegalArgumentException("Column index is out of bounds.");
        }
        double[] result = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = matrix[i][column];
        }
        return result;
    }
}
