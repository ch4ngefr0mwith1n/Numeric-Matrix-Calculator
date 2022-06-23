package calculator;

import java.util.Scanner;

public class NumericMatrixApp {
    private Scanner scanner = new Scanner(System.in);
    String mainMenu = "1. Add matrices\n" +
            "2. Multiply matrix by a constant\n" +
            "3. Multiply matrices\n" +
            "4. Transpose matrix\n" +
            "5. Calculate a determinant\n" +
            "6. Inverse matrix\n" +
            "0. Exit";

    String transposeMenu = "1. Main diagonal\n" +
            "2. Side diagonal\n" +
            "3. Vertical line\n" +
            "4. Horizontal line";

    private void matrixInversion() {
        System.out.print("Enter matrix size: ");
        Matrix matrix = new Matrix();
        matrix.setMatrixSize();

        if (matrix.getRows() != matrix.getColumns()) {
            System.out.println("Matrix needs to be square!");
            return;
        }

        System.out.println("Enter matrix: ");
        matrix.setMatrixValues();

        if (matrix.calculateDeterminant(matrix.getDoubleMatrix()) == 0) {
            System.out.println("This matrix doesn't have an inverse.");
            return;
        }

        Matrix result = new Matrix();
        result.setDoubleMatrix(matrix.invertMatrix(matrix.getDoubleMatrix()));
        result.showDoubleMatrixValues(result.getDoubleMatrix());

    }

    private void determinantValue() {
        System.out.print("Enter matrix size: ");
        Matrix matrix = new Matrix();
        matrix.setMatrixSize();

        if (matrix.getRows() != matrix.getColumns()) {
            System.out.println("Matrix needs to be square!");
            return;
        }

        System.out.println("Enter matrix: ");
        matrix.setMatrixValues();

        System.out.println("The result is:\n" + matrix.calculateDeterminant(matrix.getDoubleMatrix()));
    }

    private void transposeMatrix() {
        System.out.println(transposeMenu);
        System.out.print("Your choice: ");

        String input = scanner.next();

        try {
            int choice = Integer.parseInt(input);

            Matrix matrix = new Matrix();
            System.out.print("Enter matrix size: ");
            matrix.setMatrixSize();

            System.out.println("Enter matrix:");
            matrix.setMatrixValues();

            switch (choice) {
                case 1:
                    matrix.transposeByMainDiagonal();
                    break;
                case 2:
                    matrix.transposeBySideDiagonal();
                    break;
                case 3:
                    matrix.transposeByVerticalLine();
                    break;
                case 4:
                    matrix.transposeByHorizontalLine();
                    break;
                default:

            }
        } catch (Exception e) {
            System.out.println("Invalid option!");
            e.printStackTrace();
        }

    }

    private void multiplyByConstant() {
        System.out.print("Enter size of matrix: ");
        Matrix m = new Matrix();
        m.setMatrixSize();

        System.out.println("Enter matrix:");
        m.setMatrixValues();

        System.out.print("Enter constant: ");
        String input = scanner.next();
        double value = Double.parseDouble(input);

        System.out.println("The result is:");
        m.multiplyByConstantMatrix(value);
        System.out.println();
    }

    private void multiplyMatrices() {
        System.out.print("Enter size of first matrix: ");
        Matrix m1 = new Matrix();
        m1.setMatrixSize();

        System.out.println("Enter first matrix: ");
        m1.setMatrixValues();


        System.out.print("Enter size of second matrix: ");
        Matrix m2 = new Matrix();
        m2.setMatrixSize();

        if (m1.getColumns() != m2.getRows()) {
            System.out.println("ERROR");
            return;
        }

        System.out.println("Enter second matrix:");
        m2.setMatrixValues();

        System.out.println("The result is: ");
        m1.multiplyWithAnotherMatrix(m2);
        System.out.println();
    }

    private void addMatrix() {
        System.out.print("Enter size of first matrix: ");
        Matrix m1 = new Matrix();
        m1.setMatrixSize();

        System.out.println("Enter first matrix: ");
        m1.setMatrixValues();


        System.out.print("Enter size of second matrix: ");
        Matrix m2 = new Matrix();
        m2.setMatrixSize();

        if (m1.getRows() != m2.getRows() || m1.getColumns() != m2.getColumns()) {
            System.out.println("ERROR");
            return;
        }

        System.out.println("Enter second matrix:");
        m2.setMatrixValues();

        System.out.println("The result is: ");
        m1.additionMatrix(m2);
        System.out.println();


    }

    public void start() {
        for (;;) {
            System.out.println(mainMenu);

            System.out.print("Your choice: ");
            String input = scanner.next();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        addMatrix();
                        break;
                    case 2:
                        multiplyByConstant();
                        break;
                    case 3:
                        multiplyMatrices();
                        break;
                    case 4:
                        transposeMatrix();
                        break;
                    case 5:
                        determinantValue();
                        break;
                    case 6:
                        matrixInversion();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Invalid option!");
                e.printStackTrace();
            }
        }
    }

}
