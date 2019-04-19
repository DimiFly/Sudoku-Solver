import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    private Creator creator;
    private Solver solver;
    private GraphicsContext gc;
    private Field[][] fields;
    private int activeX, activeY;
    private int[][] filledInSudoku, startingSudoku;
    private boolean solvingMode, readyForInput;
    private int[][] test;

    @FXML
    private Canvas canvas;

    @FXML
    public void handleButtonGenerate(ActionEvent e) {
        solvingMode = true;
        gc.clearRect(0,0,603,603);
        setAllFields();
        creator = new Creator(9,3,40);
        test = creator.getSudoku();
        System.out.println("Create " + test[0][0]);
        startingSudoku = creator.makeHoles(test);
        System.out.println(startingSudoku[0][0]);
        setAllFreeFiels();
        filledInSudoku = startingSudoku;
        drawSudoku(filledInSudoku);
        drawLines();
    }

    @FXML
    public void handleButtonSolve(ActionEvent e) {
        solvingMode = false;
        System.out.println("This");
        creator.printSudoku(startingSudoku);
        creator.printSudoku(creator.getSudoku());
        drawSudoku(startingSudoku);
        System.out.println(creator.getSudokuWithHoles()[1][1]);
        solver = new Solver(creator.getSudokuWithHoles(), 9, 3);
        drawSudoku(creator.getSudoku());
        drawLines();
    }

    @FXML
    public void handleButtonSave(ActionEvent e) {

    }

    @FXML
    public void handleButtonLoad(ActionEvent e) {
        drawSudoku(test);
        System.out.println("Load " + test[0][0]);
        drawLines();
    }

    @FXML
    public void handleButtonEmpty(ActionEvent e) {
        gc.clearRect(0, 0, 603, 603);
        drawLines();
    }

    @FXML
    public void handleCanvasClicked(MouseEvent e) {
        if(solvingMode) {
            drawSudoku(filledInSudoku);
            drawLines();
            gc.setFill(Color.CORAL);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (e.getX() > fields[i][j].getStartingX() && e.getY() > fields[i][j].getStartingY() &&
                            e.getX() < (fields[i][j].getStartingX() + 67) && e.getY() < (fields[i][j].getStartingY() + 67)) {
                        if(fields[i][j].isEditable()) {
                            readyForInput = true;
                            gc.fillRect(fields[i][j].getStartingX(), fields[i][j].getStartingY(), 67, 67);
                            activeX = j;
                            activeY = i;
                            drawLines();
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent e) {
        System.out.println("Key pressed");
        if(readyForInput) {
            gc.setFill(Color.WHITE);
            String key = e.getText();
            for (int i = 1; i <= 9; i++) {
                if (Integer.parseInt(key) == i) {
                    filledInSudoku[activeX][activeY] = i;
                    System.out.println(key + "/" + filledInSudoku[activeX][activeY]);
                    drawSudoku(filledInSudoku);
                    drawLines();
                }
            }
            readyForInput = false;
        }
    }

    public void drawSudoku(int[][] sudoku) {
        gc.setFont(new Font("Yu Gothic", 30));
        gc.clearRect(0,0,603,603);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(fields[j][i].isEditable()) {
                    if(sudoku[j][i] != 0) {
                        gc.setFill(Color.rgb(11, 107, 142));
                        gc.fillText(sudoku[j][i] + "", (i * 67) + 23, (j * 67) + 40);
                    }
                    //gc.fillRect(j*67, i*67, 67, 67);
                } else {
                    if (sudoku[j][i] != 0) {
                        gc.setFill(Color.WHITE);
                        gc.fillText(sudoku[j][i] + "", (i * 67) + 23, (j * 67) + 40);

                    }
                }
            }
        }

    }

    public void drawLines(){
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        for (int i = 0; i <= 9; i++) {
            if(i % 3 == 0) {
                gc.fillRect(603/9*i-2, 0, 5, 603);
            } else {
                gc.strokeLine(603 / 9 * i, 0, 603 / 9 * i, 603);
        }
        }
        for (int i = 0; i <= 9; i++) {
            if(i % 3 == 0) {
                gc.fillRect(0, 603/9*i-2, 603, 5);
            } else {
                gc.strokeLine(0, 603 / 9 * i, 603, 603 / 9 * i);
            }
        }
    }

    /**
     * Koordinaten der Sudoku-Felder setzen
     */
    public void setAllFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new Field(67*i, 67*j);
            }
        }
    }

    /**
     * Alle Felder die man beschreiben darf setzen
     */
    public void setAllFreeFiels() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(startingSudoku[i][j] == 0) {
                    fields[j][i].setEditable(true);
                }
            }
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        solvingMode = false;
        readyForInput = false;
        fields = new Field[9][9];
        drawLines();
        int a = 4;
        int b = 6;
        int c = 9;
        a = b;
        b = c;

        System.out.println("A: " + a + " B: " + b + " C: " + c);
    }
}
