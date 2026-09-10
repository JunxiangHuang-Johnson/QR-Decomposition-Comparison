package qrdecomposition;

public class MatrixUtil {
    public static double[][] multiplication(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        int k = b.length;
        int l = b[0].length;

        if (n != k) {
            throw new IllegalArgumentException("Dimension mismatch.");
        }
        double[][] result = new double[m][l];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < l; j++) {
                double sum = 0.0;
                for (int p = 0; p < n; p++) {
                    sum += a[i][p] * b[p][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    public static double[] matrixTimesVector(double[][] a, double[] b) {
        int m = a.length;
        int n = a[0].length;
        int l = b.length;
        if (n != l) {
            throw new IllegalArgumentException("Dimension mismatch.");
        }
        double[] result = new double[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i] += a[i][j] * b[j];
            }
        }
        return result;
    }

    public static double[] vectorTimesMatrix(double[] a, double[][] b) {
        int m = a.length;
        int p = b.length;
        int n = b[0].length;
        if(m != p) {
            throw new IllegalArgumentException("Dimension mismatch.");
        }
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i] += a[j] * b[j][i];
            }
        }
        return result;
    }

    public static double[][] transpose(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] result = new double[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[j][i] = a[i][j];
            }
        }
        return result;
    }

    public static double[][] matrixAdd(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        int k = b.length;
        int l = b[0].length;

        if (m != k) {
            throw new IllegalArgumentException("Dimension mismatch.");
        }
        if (n != l) {
            throw new IllegalArgumentException("Dimension mismatch.");
        }
        double[][] result = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    public static double[][] matrixMinus(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        int k = b.length;
        int l = b[0].length;

        if (m != k) {
            throw new IllegalArgumentException("Dimension mismatch.");
        }
        if (n != l) {
            throw new IllegalArgumentException("Dimension mismatch.");
        }
        double[][] result = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    public static double[][] scalarMultiplication(double scalar, double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] result = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = scalar * a[i][j];
            }
        }
        return result;
    }

    public static double[][] identityMatrix(int size) {
        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++) {
            result[i][i] = 1;
        }
        return result;
    }

    public static double norm(double[][] A) {
        double sum = 0.0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                sum += A[i][j] * A[i][j];
            }
        }
        return Math.sqrt(sum);
    }

    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%12.6f", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static double[][] copyMatrix(double[][] A) {
        double[][] result = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                result[i][j] = A[i][j];
            }
        }
        return result;
    }
}
