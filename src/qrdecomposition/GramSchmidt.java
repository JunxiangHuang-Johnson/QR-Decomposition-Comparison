package qrdecomposition;

public class GramSchmidt {
    public static void main(String[] args) {
        double[][] matrix = MatrixGenerator.generateMatrix(50,40,-39, 98.3, 100);

        QRResult result = getMatrixQR(matrix);
        double[][] Q = result.getQ();
        double[][] R = result.getR();

        System.out.println("Matrix Q:");
        MatrixUtil.printMatrix(Q);
        System.out.println("Matrix R:");
        MatrixUtil.printMatrix(R);
    }

    public static QRResult getMatrixQR(double[][] matrix) {
        //u represents the current column of the original matrix.
        //v represents the normalized orthogonal vector (i.e., a column of matrix Q).
        //p represents the sum of the projection vectors.

        int rows = matrix.length;
        int columns = matrix[0].length;

        double[][] Q = new double[rows][columns];
        double[][] R = new double[columns][columns];

        double[][] qColumns = new double[columns][];

        for (int i = 0; i < columns; i++) {
            double[] u = GetColumn.getColumn(matrix, i);
            double[] p = new double[rows];
            for (int j = 0; j < i; j++) {
                double[] column = qColumns[j];
                R[j][i] = VectorUtil.dotProduct(u, column);
                for(int k = 0; k < rows; k++){
                    p[k] += R[j][i] * column[k];
                }
            }
            double[] minus = VectorUtil.vectorMinus(u, p);
            double norm = VectorUtil.norm(minus);
            if(norm < 1e-12){
                throw new IllegalArgumentException("Matrix columns are linearly dependent.");
            }
            double[] v = VectorUtil.multiplication(1.0 / norm, minus);
            R[i][i] = norm;
            qColumns[i] = v;
            for(int j = 0; j < rows; j++){
                Q[j][i] = v[j];
            }
        }
        return new QRResult(Q,R);
    }
}
