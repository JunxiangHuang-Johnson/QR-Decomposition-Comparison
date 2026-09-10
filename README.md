# QR Decomposition Comparison

This project implements and compares three QR decomposition methods in Java:

- Classical Gram-Schmidt (CGS)
- Modified Gram-Schmidt (MGS)
- Householder Reflection

The main focus is on numerical stability and computational efficiency.

## Numerical Stability Experiment

The numerical stability experiment compares the three methods using:

- Orthogonality error
- Reconstruction error

Matrices with different condition numbers are tested to study how numerical stability changes as the matrices become more ill-conditioned.

Experimental settings:

- Matrix size: 100 × 50
- Test rounds: 20
- Condition numbers: 1, 1e2, 1e4, 1e6, 1e8, 1e10, 1e12

## Runtime Experiment

A simple runtime comparison is also included as a follow-up extension.

Matrix sizes:

- 100 × 50
- 200 × 100
- 400 × 200

Each method is run 100 times and the average runtime is recorded.

## Project Structure

```text
src/qrdecomposition/
├── GramSchmidt.java
├── ModifiedGramSchmidt.java
├── Householder.java
├── NumericalStabilityExperiment.java
├── RuntimeExperiment.java
├── ConditionedMatrixGenerator.java
├── MatrixGenerator.java
├── MatrixUtil.java
├── VectorUtil.java
├── GetColumn.java
├── QRResult.java
└── ErrorResult.java
