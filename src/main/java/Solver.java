public class Solver {

    private int[][] sudoku;
    private int n, sqrtN;

    public Solver(int[][] sudoku, int n, int sqrtN) {
        this.sudoku = sudoku;
        this.n = n;
        this.sqrtN = sqrtN;
        solve();
        printSudoku();
    }

    /**
     * Sudoku lösen
     * @return
     */
    public boolean solve() {
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                if (sudoku[row][col] == 0) {
                    for(int num = 1; num <= n; num++) {
                        if (checkIfSafe(row, col, num)){
                            sudoku[row][col] = num;
                            if(solve()){
                                return true;
                            } else {
                                sudoku[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Kontrollieren ob die Zahl den Sudokuregeln entspricht
     */
    public boolean checkIfSafe(int i, int j, int num) {
        int sqrtN = (int) Math.sqrt(sudoku.length);
        if (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % sqrtN, j - j % sqrtN, num)) {
            return true;
        }
        return false;
    }

    /**
     * Kontrollieren ob die Zahl in der Reihe schon benutzt wird
     * @param i
     * @param num
     * @return
     */
    public boolean unUsedInRow(int i, int num) {
        for (int j = 0; j < sudoku.length; j++) {
            if (sudoku[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kontrollieren ob die Zahl in der Zeile schon benutzt wird
     * @param j
     * @param num
     * @return
     */
    public boolean unUsedInCol(int j, int num) {
        for (int i = 0; i < sudoku.length; i++) {
            if (sudoku[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kontrollieren ob die Zahl im 3x3 Feld schon benutzt wird
     * @param rowStart
     * @param colStart
     * @param num
     * @return
     */
    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < Math.sqrt(sudoku.length); i++)
            for (int j = 0; j < Math.sqrt(sudoku.length); j++)
                if (sudoku[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }

    /**
     * Sudoku in der Konsole ausgeben
     */
    public void printSudoku() {
        String out = "";
        for (int a = 0; a < n; a++) {
            if(a % sqrtN == 0 && a != 0){
                out += "------------------- \n";
            }
            for (int b = 0; b < n; b++) {
                if(b % sqrtN == 0 && b != 0){
                    out += "|";
                }
                out += sudoku[a][b] + " ";
            }
            out += "\n";
        }
        System.out.println(out);
        System.out.println("___________________");
    }

    public int[][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getSqrtN() {
        return sqrtN;
    }

    public void setSqrtN(int sqrtN) {
        this.sqrtN = sqrtN;
    }
}
