public class Solver {

    private int[][] sudoku;

    public Solver(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    public void solve() {
        for (int x = 0; x < sudoku.length; x++) {
            for (int y = 0; y < sudoku.length; y++) {
                if (sudoku[x][y] == 0) {
                    for (int i = 1; i <= sudoku.length; i++) {
                        if (checkIfSafe(x, y, i)) {
                            sudoku[x][y] = i;
                        }
                    }
                }
            }
        }
    }

    public boolean checkIfSafe(int i, int j, int num) {
        int sqrtN = (int) Math.sqrt(sudoku.length);
        if (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % sqrtN, j - j % sqrtN, num)) {
            return true;
        }
        return false;
    }

    // check in the row for existence
    public boolean unUsedInRow(int i, int num) {
        for (int j = 0; j < sudoku.length; j++) {
            if (sudoku[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // check in the row for existence
    public boolean unUsedInCol(int j, int num) {
        for (int i = 0; i < sudoku.length; i++) {
            if (sudoku[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // Returns false if given 3 x 3 block contains num.
    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < Math.sqrt(sudoku.length); i++)
            for (int j = 0; j < Math.sqrt(sudoku.length); j++)
                if (sudoku[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }
}
