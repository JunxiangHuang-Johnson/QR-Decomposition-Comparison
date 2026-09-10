package qrdecomposition;

public class ModifiedGramSchmidt {

    public static void main(String[] args) {

        double[][] matrix = MatrixGenerator.generateMatrix(50,40,-39,98.3,100);

        QRResult result = getMatrixQR(matrix);

        double[][] Q = result.getQ();
        double[][] R = result.getR();

        System.out.println("Matrix Q:");
        MatrixUtil.printMatrix(Q);

        System.out.println("Matrix R:");
        MatrixUtil.printMatrix(R);
    }


    public static QRResult getMatrixQR(double[][] matrix) {
        //u represents the current vector being orthogonalized.
        //v represents a normalized orthogonal vector (i.e., a column of matrix Q).

        int rows = matrix.length;
        int columns = matrix[0].length;

        double[][] Q = new double[rows][columns];
        double[][] R = new double[columns][columns];

        double[][] qColumns = new double[columns][];

        for (int i = 0; i < columns; i++) {
            double[] u = GetColumn.getColumn(matrix, i);
            for (int j = 0; j < i; j++) {
                double[] v = qColumns[j];
                R[j][i] = VectorUtil.dotProduct(u, v);
                for(int k = 0; k < rows; k++){
                    u[k] -= R[j][i] * v[k];
                }
            }
            double norm = VectorUtil.norm(u);
            if(norm < 1e-12){
                throw new IllegalArgumentException("Matrix columns are linearly dependent.");
            }
            R[i][i] = norm;
            double[] v = VectorUtil.multiplication(1.0/norm,u);
            qColumns[i] = v;
            for (int j = 0; j < rows; j++) {
                Q[j][i] = v[j];
            }
        }
        return new QRResult(Q, R);
    }
}