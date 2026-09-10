package qrdecomposition;

public class QRResult {
    private double[][] Q;
    private double[][] R;

    public QRResult(double[][] Q, double[][] R) {
        this.Q = Q;
        this.R = R;
    }

    public double[][] getQ() {
        return Q;
    }

    public double[][] getR() {
        return R;
    }

}
