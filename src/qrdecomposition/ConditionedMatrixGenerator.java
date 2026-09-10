package qrdecomposition;

import java.util.Random;

public class ConditionedMatrixGenerator {

    public static double[][] generateMatrix(int m, int n, double conditionNumber, long seed) {
        if (m < n) {
            throw new IllegalArgumentException("The number of rows must be greater than or equal to the number of columns.");
        }

        if (conditionNumber < 1.0) {
            throw new IllegalArgumentException("The condition number must be greater than or equal to 1.");
        }

        Random random = new Random(seed);

        // 1. Generate two random matrices.
        double[][] randomU = generateRandomMatrix(m, n, random);
        double[][] randomV = generateRandomMatrix(n, n, random);

        // 2. Orthonormalize the columns to get U and V.
        double[][] U = orthonormalizeColumns(randomU);
        double[][] V = orthonormalizeColumns(randomV);

        // 3. Construct the diagonal singular value matrix Sigma.
        double[][] sigma = generateSigmaMatrix(n, conditionNumber);

        // 4. Calculate A = U * Sigma * V^T.
        double[][] vTranspose = transpose(V);
        double[][] uSigma = multiply(U, sigma);

        return multiply(uSigma, vTranspose);
    }

    private static double[][] generateRandomMatrix(int rows, int columns, Random random) {
        double[][] matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = random.nextGaussian();
            }
        }

        return matrix;
    }

    private static double[][] orthonormalizeColumns(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        double[][] Q = new double[m][n];

        for (int i = 0; i < n; i++) {
            double[] v = getColumn(matrix, i);

            for (int j = 0; j < i; j++) {
                double[] qj = getColumn(Q, j);

                double coefficient = dotProduct(qj, v);

                for (int row = 0; row < m; row++) {
                    v[row] -= coefficient * qj[row];
                }
            }

            double norm = norm(v);

            if (norm < 1e-12) {
                throw new IllegalArgumentException("The generated matrix has nearly linearly dependent columns.");
            }

            for (int row = 0; row < m; row++) {
                Q[row][i] = v[row] / norm;
            }
        }

        return Q;
    }

    private static double[][] generateSigmaMatrix(int n, double conditionNumber) {
        double[][] sigma = new double[n][n];

        if (n == 1) {
            sigma[0][0] = 1.0;
            return sigma;
        }

        for (int i = 0; i < n; i++) {
            double exponent = (double) i / (n - 1);

            double singularValue = Math.pow(1.0 / conditionNumber, exponent);

            sigma[i][i] = singularValue;
        }

        return sigma;
    }

    private static double[] getColumn(double[][] matrix, int column) {
        double[] result = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            result[i] = matrix[i][column];
        }

        return result;
    }

    private static double dotProduct(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vector dimensions must be equal.");
        }

        double sum = 0.0;

        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }

        return sum;
    }

    private static double norm(double[] vector) {
        return Math.sqrt(dotProduct(vector, vector));
    }

    private static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        double[][] result = new double[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    private static double[][] multiply(double[][] A, double[][] B) {
        int aRows = A.length;
        int aColumns = A[0].length;

        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }

        double[][] result = new double[aRows][bColumns];

        for (int i = 0; i < aRows; i++) {
            for (int k = 0; k < aColumns; k++) {
                for (int j = 0; j < bColumns; j++) {
                    result[i][j] +=
                            A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }
}