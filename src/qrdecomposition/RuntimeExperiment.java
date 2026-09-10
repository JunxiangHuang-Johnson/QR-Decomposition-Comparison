package qrdecomposition;

public class RuntimeExperiment {

    public static void main(String[] args) {

        int[][] matrixSizes = {{100, 50}, {200, 100}, {400, 200}};

        int testRounds = 20;
        double conditionNumber = 1e6;

        System.out.printf("%-10s %-10s %-15s %-20s%n", "m", "n", "Method", "Average Runtime (ms)");

        for (int[] size : matrixSizes) {

            int m = size[0];
            int n = size[1];

            double[][] A = ConditionedMatrixGenerator.generateMatrix(m, n, conditionNumber, 100L);

            double cgsTime = 0.0;
            double mgsTime = 0.0;
            double householderTime = 0.0;

            for (int round = 0; round < testRounds; round++) {

                double[][] matrix1 = MatrixUtil.copyMatrix(A);
                long start = System.nanoTime();
                GramSchmidt.getMatrixQR(matrix1);
                long end = System.nanoTime();
                cgsTime += end - start;

                double[][] matrix2 = MatrixUtil.copyMatrix(A);
                start = System.nanoTime();
                ModifiedGramSchmidt.getMatrixQR(matrix2);
                end = System.nanoTime();
                mgsTime += end - start;

                double[][] matrix3 = MatrixUtil.copyMatrix(A);
                start = System.nanoTime();
                Householder.getMatrixQR(matrix3);
                end = System.nanoTime();
                householderTime += end - start;
            }

            double cgsAverage = cgsTime / testRounds / 1_000_000.0;
            double mgsAverage = mgsTime / testRounds / 1_000_000.0;
            double householderAverage = householderTime / testRounds / 1_000_000.0;

            printResult(m, n, "CGS", cgsAverage);
            printResult(m, n, "MGS", mgsAverage);
            printResult(m, n, "Householder", householderAverage);

            System.out.println();
        }
    }

    private static void printResult(int m, int n, String method, double averageTime) {
        System.out.printf("%-10d %-10d %-15s %-20.4f%n", m, n, method, averageTime);
    }
}