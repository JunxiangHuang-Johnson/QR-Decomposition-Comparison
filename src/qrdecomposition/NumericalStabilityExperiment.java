package qrdecomposition;

public class NumericalStabilityExperiment {

    public static void main(String[] args) {
        int m = 100;
        int n = 50;
        int testRounds = 20;

        double[] conditionNumbers = {1.0, 1e2, 1e4, 1e6, 1e8, 1e10, 1e12};

        System.out.printf("%-15s %-15s %-25s %-25s%n", "Condition", "Method", "Average Orthogonality Error", "Average Reconstruction Error");

        for (int i = 0; i < conditionNumbers.length; i++) {
            double conditionNumber = conditionNumbers[i];
            testConditionNumber(m, n, conditionNumber, testRounds);
            System.out.println();
        }
    }

    private static void testConditionNumber(int m, int n, double conditionNumber, int testRounds) {
        double cgsOrthogonalitySum = 0.0;
        double cgsReconstructionSum = 0.0;

        double mgsOrthogonalitySum = 0.0;
        double mgsReconstructionSum = 0.0;

        double householderOrthogonalitySum = 0.0;
        double householderReconstructionSum = 0.0;

        for (int round = 0; round < testRounds; round++) {

            long seed = 100L + round;

            double[][] A = ConditionedMatrixGenerator.generateMatrix(m, n, conditionNumber, seed);

            QRResult cgsResult = GramSchmidt.getMatrixQR(MatrixUtil.copyMatrix(A));
            ErrorResult cgsError = calculateErrors(A, cgsResult);
            cgsOrthogonalitySum += cgsError.getOrthogonalityError();
            cgsReconstructionSum += cgsError.getReconstructionError();

            QRResult mgsResult = ModifiedGramSchmidt.getMatrixQR(MatrixUtil.copyMatrix(A));
            ErrorResult mgsError = calculateErrors(A, mgsResult);
            mgsOrthogonalitySum += mgsError.getOrthogonalityError();
            mgsReconstructionSum += mgsError.getReconstructionError();

            QRResult householderResult = Householder.getMatrixQR(MatrixUtil.copyMatrix(A));
            ErrorResult householderError = calculateErrors(A, householderResult);
            householderOrthogonalitySum += householderError.getOrthogonalityError();
            householderReconstructionSum += householderError.getReconstructionError();
        }

        double cgsAverageOrthogonality = cgsOrthogonalitySum / testRounds;
        double cgsAverageReconstruction = cgsReconstructionSum / testRounds;

        double mgsAverageOrthogonality = mgsOrthogonalitySum / testRounds;
        double mgsAverageReconstruction = mgsReconstructionSum / testRounds;

        double householderAverageOrthogonality = householderOrthogonalitySum / testRounds;
        double householderAverageReconstruction = householderReconstructionSum / testRounds;

        printResult(conditionNumber, "CGS", cgsAverageOrthogonality, cgsAverageReconstruction);
        printResult(conditionNumber, "MGS", mgsAverageOrthogonality, mgsAverageReconstruction);
        printResult(conditionNumber, "Householder", householderAverageOrthogonality, householderAverageReconstruction);
    }

    private static ErrorResult calculateErrors(double[][] originalA, QRResult result) {
        double[][] Q = result.getQ();
        double[][] R = result.getR();

        double orthogonalityError = calculateOrthogonalityError(Q);
        double reconstructionError = calculateReconstructionError(originalA, Q, R);

        return new ErrorResult(orthogonalityError, reconstructionError);
    }

    private static void printResult(double conditionNumber, String methodName, double averageOrthogonalityError, double averageReconstructionError) {
        System.out.printf("%-15.1e %-15s %-25.8e %-25.8e%n", conditionNumber, methodName, averageOrthogonalityError, averageReconstructionError);
    }

    private static double calculateOrthogonalityError(double[][] Q){
        double[][] I = MatrixUtil.identityMatrix(Q[0].length);
        double[][] result = MatrixUtil.matrixMinus(MatrixUtil.multiplication(MatrixUtil.transpose(Q), Q), I);
        double orthogonalityError = MatrixUtil.norm(result);
        return orthogonalityError;
    }

    private static double calculateReconstructionError(double[][] A, double[][] Q, double[][] R){
        double[][] result = MatrixUtil.matrixMinus(MatrixUtil.multiplication(Q, R), A);
        double reconstructionError = MatrixUtil.norm(result) / MatrixUtil.norm(A);
        return reconstructionError;
    }

}