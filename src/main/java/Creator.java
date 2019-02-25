import java.util.Random;

public class Creator {

    private int[][] sudoku;
    private int n, sqrtN, holes;

    public Creator(int n, int sqrtN, int holes) {
        this.n = n;
        this.sqrtN = sqrtN;
        this.holes = holes;
        sudoku = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sudoku[i][j] = 0;
            }
        }
        createSudoku();
        makeHoles();
        printSudoku();
    }

    public void createSudoku() {
        fillDiagonalFields();
        //fillInAll();
        fillRemaining(0, sqrtN);
    }

    public void fillDiagonalFields() {
        int nextNumber;
        for (int i = 0; i < sqrtN; i++) {
            int[][] small = new int[sqrtN][sqrtN];
            Random r = new Random();
            for (int x = 0; x < sqrtN; x++) {
                for (int y = 0; y < sqrtN; y++) {
                    nextNumber = r.nextInt(n) + 1;
                    if (checkIfAlreadyUsed(nextNumber, small)) {
                        if (y == 0) {
                            x--;
                        } else {
                            y--;
                        }
                    } else {
                        small[x][y] = nextNumber;
                    }
                }
            }
            for (int x = 0; x < sqrtN; x++) {
                for (int y = 0; y < sqrtN; y++) {
                    sudoku[x + (i * sqrtN)][y + (i * sqrtN)] = small[x][y];
                }
            }
        }

    }

    public void fillInAll() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (sudoku[x][y] == 0) {
                    for (int i = 1; i <= 9; i++) {
                        if (checkIfSafe(x, y, i)) {
                            sudoku[x][y] = i;
                        }
                        if (i == 9) {
                            if (y == 0) {
                                x--;
                            } else {
                                y--;
                            }
                            for (int j = 0; j < 9; j++) {
                                sudoku[j][y] = 0;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean fillRemaining(int i, int j) {
        if (j >= n && i < n - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= n && j >= n) {
            return true;
        }

        if (i < sqrtN) {
            if (j < sqrtN) {
                j = sqrtN;
            }
        } else if (i < n - sqrtN) {
            if (j == (i / sqrtN) * sqrtN) {
                j = j + sqrtN;
            }
        } else {
            if (j == n - sqrtN) {
                i = i + 1;
                j = 0;
                if (i >= n) {
                    return true;
                }
            }
        }

        for (int num = 1; num <= n; num++) {
            if (checkIfSafe(i, j, num)) {
                sudoku[i][j] = num;
                if (fillRemaining(i, j + 1)) {
                    return true;
                }
                sudoku[i][j] = 0;
            }
        }

        return false;
    }

    public boolean checkIfHasHole() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sudoku[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfSafe(int i, int j, int num) {
        if (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % sqrtN, j - j % sqrtN, num)) {
            return true;
        }
        return false;
    }


    public boolean unUsedInRow(int i, int num) {
        for (int j = 0; j < n; j++) {
            if (sudoku[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    public boolean unUsedInCol(int j, int num) {
        for (int i = 0; i < n; i++) {
            if (sudoku[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < sqrtN; i++)
            for (int j = 0; j < sqrtN; j++)
                if (sudoku[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }

    public boolean checkIfAlreadyUsed(int number, int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (number == matrix[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfAlreadyUsedInRowOrColumn(int number, int[][] matrix, int row, int column) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[column][j] == number) {
                    return true;
                } else if (matrix[i][row] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    public void makeHoles() {
        int x;
        int y;
        for (int i = 0; i < holes; i++) {
            Random r = new Random();
            x = r.nextInt(n);
            y = r.nextInt(n);
            if(sudoku[x][y] == 0){
                i--;
            } else {
                sudoku[x][y] = 0;
            }
        }
    }

    public void printSudoku() {
        String out = "";
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < n; b++) {
                out += sudoku[a][b] + " ";
            }
            out += "\n";
        }
        System.out.println(out);
    }
}
