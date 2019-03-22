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
        printSudoku();
        makeHoles();
        printSudoku();
    }

    /**
     * Sudoku erstellen
     */
    public void createSudoku() {
        fillDiagonalFields();
        fillRemaining(0, sqrtN);
    }

    /**
     * Diagonale 3x3 Felder füllen
     */
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

    /**
     * Restliche Zahlen einfüllen
     * @param i
     * @param j
     * @return
     */
    public boolean fillRemaining(int i, int j) {
        if (i < n - 1 && j >= n) {
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

    /**
     * Kontrollieren ob die Zahl den Sudokuregeln entspricht
     * @param i
     * @param j
     * @param num
     * @return
     */
    public boolean checkIfSafe(int i, int j, int num) {
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
        for (int j = 0; j < n; j++) {
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
        for (int i = 0; i < n; i++) {
            if (sudoku[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kontrollieren ob die Zahl im entsprechenden 3x3 Feld schon benutzt wird
     * @param rowStart
     * @param colStart
     * @param num
     * @return
     */
    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < sqrtN; i++) {
            for (int j = 0; j < sqrtN; j++) {
                if (sudoku[rowStart + i][colStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Kontrollieren ob die Zahl im gerade zu füllenden Feld schon benutzt wird
     * @param number
     * @param matrix
     * @return
     */
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

    /**
     * Löcher im Sudoku stanzen
     */
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

    /**
     * Sudoku in der Konsole ausgeben
     */
    public void printSudoku() {
        String out = "";
        for (int a = 0; a < n; a++) {
            if(a % 3 == 0 && a != 0){
                out += "------------------- \n";
            }
            for (int b = 0; b < n; b++) {
                if(b % 3 == 0 && b != 0){
                    out += "|";
                }
                out += sudoku[a][b] + " ";
            }
            out += "\n";
        }
        System.out.println(out);
        System.out.println("___________________");
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

    public int getHoles() {
        return holes;
    }

    public void setHoles(int holes) {
        this.holes = holes;
    }

    public int[][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }
}
