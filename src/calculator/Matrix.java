package calculator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Matrix {
    private Scanner scanner = new Scanner(System.in);

    private int rows;
    private int columns;
    private double[][] doubleMatrix;

    /*
     * https://gist.github.com/Cellane/398372/23a3e321daa52d4c6b68795aae093bf773ce2940#file-matrixoperations-java
     * metoda za utvrđivanje inverzne matrice:
     */
    public double[][] invertMatrix (double[][] matrix) {
        double[][] auxiliaryMatrix, invertedMatrix;
        int[] index;

        auxiliaryMatrix = new double[matrix.length][matrix.length];
        invertedMatrix = new double[matrix.length][matrix.length];
        index = new int[matrix.length];

        for (int i = 0; i < matrix.length; ++i) {
            auxiliaryMatrix[i][i] = 1;
        }

        transformToUpperTriangle (matrix, index);

        for (int i = 0; i < (matrix.length - 1); ++i) {
            for (int j = (i + 1); j < matrix.length; ++j) {
                for (int k = 0; k < matrix.length; ++k) {
                    auxiliaryMatrix[index[j]][k] -= matrix[index[j]][i] * auxiliaryMatrix[index[i]][k];
                }
            }
        }

        for (int i = 0; i < matrix.length; ++i) {
            invertedMatrix[matrix.length - 1][i] = (auxiliaryMatrix[index[matrix.length - 1]][i] / matrix[index[matrix.length - 1]][matrix.length - 1]);

            for (int j = (matrix.length - 2); j >= 0; --j) {
                invertedMatrix[j][i] = auxiliaryMatrix[index[j]][i];

                for (int k = (j + 1); k < matrix.length; ++k) {
                    invertedMatrix[j][i] -= (matrix[index[j]][k] * invertedMatrix[k][i]);
                }

                invertedMatrix[j][i] /= matrix[index[j]][j];
            }
        }

        return (invertedMatrix);
    }

    public void transformToUpperTriangle (double[][] matrix, int[] index) {
        double[] c;
        double c0, c1, pi0, pi1, pj;
        int itmp, k;

        c = new double[matrix.length];

        for (int i = 0; i < matrix.length; ++i) {
            index[i] = i;
        }

        for (int i = 0; i < matrix.length; ++i) {
            c1 = 0;

            for (int j = 0; j < matrix.length; ++j) {
                c0 = Math.abs (matrix[i][j]);

                if (c0 > c1) {
                    c1 = c0;
                }
            }

            c[i] = c1;
        }

        k = 0;

        for (int j = 0; j < (matrix.length - 1); ++j) {
            pi1 = 0;

            for (int i = j; i < matrix.length; ++i) {
                pi0 = Math.abs (matrix[index[i]][j]);
                pi0 /= c[index[i]];

                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;

            for (int i = (j + 1); i < matrix.length; ++i) {
                pj = matrix[index[i]][j] / matrix[index[j]][j];
                matrix[index[i]][j] = pj;

                for (int l = (j + 1); l < matrix.length; ++l) {
                    matrix[index[i]][l] -= pj * matrix[index[j]][l];
                }
            }
        }
    }

    /*
     * https://gist.github.com/Cellane/398372/23a3e321daa52d4c6b68795aae093bf773ce2940
     * metoda za izracunavanje determinante:
     */
    public double calculateDeterminant(double[][] matrix) {
        double temporary[][];
        double result = 0;

        if (matrix.length == 1) {
            result = matrix[0][0];
            return (result);
        }

        if (matrix.length == 2) {
            result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
            return (result);
        }

        for (int i = 0; i < matrix[0].length; i++) {
            temporary = new double[matrix.length - 1][matrix[0].length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            result += matrix[0][i] * Math.pow (-1, (double) i) * calculateDeterminant (temporary);
        }
        return result;
    }

    /*
     * metoda za transponovanje matrice po glavnoj dijagonali:
     */
    public void transposeByMainDiagonal() {
        double[][] result = new double[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[j][i] = doubleMatrix[i][j];
            }
        }

        System.out.println("The result is:");
        showDoubleMatrixValues(result);
    }

    /*
     * metoda za transponovanje matrice po sporednoj dijagonali:
     */
    public void transposeBySideDiagonal() {
        double[][] result = new double[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // za transpoziciju po sporednoj dijagonali će biti ovakvo preslikavanje:
                result[i][j] = doubleMatrix[rows - 1 - j][columns - 1 - i];
            }
        }

        System.out.println("The result is:");
        showDoubleMatrixValues(result);
    }

    /*
     * metoda za transponovanje matrice vertikalno:
     */
    public void transposeByVerticalLine() {
        double[][] result = new double[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = doubleMatrix[i][columns - 1 - j];
            }
        }

        System.out.println("The result is:");
        showDoubleMatrixValues(result);
    }

    /*
     * metoda za transponovanje matrice horizontalno:
     */
    public void transposeByHorizontalLine() {

        double[][] result = clone2DArray(doubleMatrix);

        // skoro isto kao za vertikalni flip, samo obrnemo indekse:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = doubleMatrix[rows - 1 - i][j];
            }
        }

        System.out.println("The result is:");
        showDoubleMatrixValues(result);
    }

    /*
     * metoda za množenje dvije matrice:
     */
    public void multiplyWithAnotherMatrix(Matrix secondMatrix) {
        double[][] result = new double[this.rows][secondMatrix.getColumns()];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < secondMatrix.getColumns(); j++) {
                for (int k = 0; k < this.columns; k++) {
                    result[i][j] += doubleMatrix[i][k] * secondMatrix.getDoubleMatrix()[k][j];
                }
            }
        }

        showDoubleMatrixValues(result);
    }

    /*
     * metoda za množenje matrice nekom konstantom:
     */
    public void multiplyByConstantMatrix(double value) {
        double[][] result = clone2DArray(doubleMatrix);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] *= value;
            }
        }

        showDoubleMatrixValues(result);
    }

    /*
     * metoda za sabiranje dvije matrice:
     */
    public void additionMatrix(Matrix secondMatrix) {

        double[][] result = clone2DArray(doubleMatrix);

        for (int i = 0; i < secondMatrix.getDoubleMatrix().length; i++) {
            for (int j = 0; j < secondMatrix.getDoubleMatrix()[i].length; j++) {
                result[i][j] += secondMatrix.getDoubleMatrix()[i][j];
            }
        }

        showDoubleMatrixValues(result);
    }

    /*
     * metoda za prebacivanje iz 2D niza sa "double" vrijednostima u 2D niz "int" vrijednosti:
     */
    private void convertToInteger2DArray(double[][] array) {
        int[][] result = new int[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                result[i][j] = (int) array[i][j];
            }
        }
    }

    /*
     * metoda koja popunjava matricu vrijednostima:
     */
    public void setMatrixValues() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String value = scanner.next();
                double number = Double.parseDouble(value);
                doubleMatrix[i][j] = number;
            }
        }
    }

    /*
     * metoda koja postavlja veličinu matrice:
     */
    public void setMatrixSize() {
        this.rows = Integer.parseInt(scanner.next());
        this.columns = Integer.parseInt(scanner.next());
        //this.columns = scanner.nextInt();

        this.doubleMatrix = new double[rows][columns];
    }

    /*
     * https://stackoverflow.com/questions/9106131/how-to-clone-a-multidimensional-array-in-java
     * metoda za kloniranje 2D niza:
     */
    public double[][] clone2DArray(double[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            System.out.println("Can't clone the array!");
        }

        double[][] copy = matrix.clone();

        for (int i = 0; i < copy.length; i++) {
            copy[i] = copy[i].clone();
        }

        return copy;
    }

    /*
     * metoda za prikazivanje "double "vrijednosti matrice:
     */
    public static void showDoubleMatrixValues(double[][] matrix) {
        for (double[] array : matrix) {
            for (double i : array) {
                // FORMATIRANJE OUTPUT-a:
                NumberFormat nf = new DecimalFormat("##.###");
                System.out.print(nf.format(i) + " ");
            }
            // prelazak u naredni red:
            System.out.println();
        }
        System.out.println();
    }


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public double[][] getDoubleMatrix() {
        return doubleMatrix;
    }

    public void setDoubleMatrix(double[][] doubleMatrix) {
        this.doubleMatrix = doubleMatrix;
    }
}
