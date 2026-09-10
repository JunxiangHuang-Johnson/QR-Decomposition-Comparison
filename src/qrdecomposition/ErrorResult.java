package qrdecomposition;

public class ErrorResult {
    private final double orthogonalityError;
    private final double reconstructionError;

    public ErrorResult(double orthogonalityError, double reconstructionError) {
        this.orthogonalityError = orthogonalityError;
        this.reconstructionError = reconstructionError;
    }

    public double getOrthogonalityError() {
        return orthogonalityError;
    }

    public double getReconstructionError() {
        return reconstructionError;
    }
}
