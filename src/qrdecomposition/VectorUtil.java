package qrdecomposition;

public class VectorUtil {
    public static double dotProduct(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vector dimensions must be equal.");
        }
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] * b[i]);
        }
        return sum;

    }

    public static double[][] outerproduct(double[] a, double[] b) {
        double[][] result = new double[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                result[i][j] = a[i] * b[j];
            }
        }
        return result;
    }

    public static double[] projVector(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vector dimensions must be equal.");
        } else if (norm(b) == 0) {
            throw new IllegalArgumentException("Cannot project onto the zero vector.");
        } else {
            double dotProduct = dotProduct(a, b);
            double norm = norm(b);
            double[] c = multiplication(dotProduct / Math.pow(norm, 2.0), b);
            return c;
        }
    }

    public static double norm(double[] a) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] * a[i]);
        }
        return Math.sqrt(sum);
    }

    public static double[] multiplication(double scalar, double[] a) {
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = scalar * a[i];
        }
        return c;
    }

    public static double[] unit(double[] a) {
        double norm = norm(a);
        if (norm == 0) {
            throw new IllegalArgumentException("Zero vector cannot be normalized.");
        }
        return multiplication((1 / norm), a);

    }

    public static double[] standardUnitVector(int index, int size) {
        double[] result = new double[size];
        result[index] = 1;
        return result;
    }

    public static double[] vectorAdd(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vector dimensions must be equal.");
        }
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }
        return c;

    }

    public static double[] vectorMinus(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vector dimensions must be equal.");
        }
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] - b[i];
        }
        return c;

    }
}
