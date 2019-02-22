import java.util.Random;

public class Creator {

    private int[][] sudoku;

    public Creator () {
        sudoku = new int[9][9];
        fillDiagonalFields();
    }

    public void fillDiagonalFields() {
        int[][] small = new int[3][3];
        Random r = new Random();
        int nextNumber;
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    nextNumber = r.nextInt(9) + 1;
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
            String out = "";
            for (int a = 0; a < 3; a++) {
                for (int j = 0; j < 3; j++) {
                    out += small[a][j] + " ";
                }
                out += "\n";
            }
            System.out.println(out);
            if(i == 0){
                for(int x = 0; x < 3; x++){
                    for(int y = 0; y < 3; y++){
                        small[x][y] = sudoku[x][y];
                    }
                }
            } else if(i == 1){
                for(int x = 0; x < 3; x++){
                    for(int y = 0; y < 3; y++){
                        small[x][y] = sudoku[x + 3][y + 3];
                    }
                }
            } else if(i == 2){
                for(int x = 0; x < 3; x++){
                    for(int y = 0; y < 3; y++){
                        small[x][y] = sudoku[x + 6][y + 6];
                    }
                }
            }
        }


    }

    public boolean checkIfAlreadyUsed(int number, int[][] matrix){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(number == matrix[i][j]){
                    return true;
                }
            }
        }
        return false;
    }
}
