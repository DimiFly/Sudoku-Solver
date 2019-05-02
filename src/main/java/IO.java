import javafx.stage.*;

import java.io.*;

public class IO {

    public void outputSudoku(int[][] sudoku, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    bufferedWriter.write(sudoku[i][j] + " ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outputTempSudoku(int[][] sudoku) {
        try {
            FileWriter fileWriter = new FileWriter("Sudokus/temp.sudoku");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    bufferedWriter.write(sudoku[i][j] + " ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] inputTempSudoku() {
        String line;
        int[][] sudoku = new int[9][9];
        int counter = 0;
        try {
            FileReader fileReader = new FileReader("temp.sudoku");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String[] sudokuLine= line.split(" ");
                for (int i = 0; i < 9; i++) {
                    sudoku[counter][i] = Integer.parseInt(sudokuLine[i]);
                }
                counter++;
            }
            bufferedReader.close();
            return sudoku;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int[][] inputSudoku(String fileName) {
        String line;
        int[][] sudoku = new int[9][9];
        int counter = 0;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String[] sudokuLine= line.split(" ");
                for (int i = 0; i < 9; i++) {
                    sudoku[counter][i] = Integer.parseInt(sudokuLine[i]);
                }
                counter++;
            }
            bufferedReader.close();
            return sudoku;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
