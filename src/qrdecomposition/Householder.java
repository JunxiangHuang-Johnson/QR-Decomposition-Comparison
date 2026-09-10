package qrdecomposition;

public class Householder {
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

    public static QRResult getMatrixQR(double[][] A) {

        int m = A.length;
        int n = A[0].length;

        double[][] Q = MatrixUtil.identityMatrix(m);

        for (int i = 0; i < n; i++) {
            double[] x = GetColumn.getColumn(A, i);
            for (int j = 0; j < i; j++) {
                x[j] = 0;
            }
            double norm = VectorUtil.norm(x);
            if(norm < 1e-12){
                continue;
            }
            double alpha;
            if(x[i] >= 0){
                alpha = -norm;
            }else{
                alpha = norm;
            }
            double[] e = VectorUtil.standardUnitVector(i,m);
            double[] v = VectorUtil.vectorMinus(x, VectorUtil.multiplication(alpha,e));
            double denominator = VectorUtil.dotProduct(v,v);
            if(denominator < 1e-24){
                continue;
            }
            double scalar = 2.0 / denominator;
            // Update A
            double[] vTA = MatrixUtil.vectorTimesMatrix(v, A);
            for (int row = 0; row < m; row++) {
                for (int col = 0; col < n; col++) {
                    A[row][col] -= scalar * v[row] * vTA[col];
                }
            }

            // Update Q
            double[] Qv = MatrixUtil.matrixTimesVector(Q, v);
            for (int row = 0; row < m; row++) {
                for (int col = 0; col < m; col++) {
                    Q[row][col] -= scalar * Qv[row] * v[col];
                }
            }
        }

        double[][] reducedQ = new double[m][n];
        double[][] reducedR = new double[n][n];

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                reducedQ[i][j]=Q[i][j];
            }
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                reducedR[i][j]=A[i][j];
            }
        }
        return new QRResult(reducedQ,reducedR);
    }
}
