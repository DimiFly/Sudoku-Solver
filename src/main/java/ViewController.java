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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    private Creator creator;
    private Solver solver;
    private GraphicsContext gc;
    private Field[][] fields;
    private ArrayList<Field> freeFields;
    private int activeX, activeY;
    private int[][] filledInSudoku, startingSudoku;
    private boolean solvingMode, readyForInput;

    @FXML
    private Canvas canvas;

    @FXML
    public void handleButtonGenerate(ActionEvent e) {
        solvingMode = true;
        gc.clearRect(0,0,603,603);
        creator = new Creator(9,3,45);
        startingSudoku = creator.getSudokuWithHoles();
        filledInSudoku = startingSudoku;
        setAllFreeFiels();
        drawSudoku(startingSudoku);
        drawLines();
    }

    @FXML
    public void handleButtonSolve(ActionEvent e) {
        solvingMode = false;
        solver = new Solver(filledInSudoku, 9, 3);
        drawSudoku(solver.getSudoku());
        drawLines();
    }

    @FXML
    public void handleButtonSave(ActionEvent e) {

    }

    @FXML
    public void handleButtonLoad(ActionEvent e) {

    }

    @FXML
    public void handleButtonEmpty(ActionEvent e) {
        gc.clearRect(0, 0, 603, 603);
    }

    @FXML
    public void handleCanvasClicked(MouseEvent e) {
        if(solvingMode) {
            readyForInput = true;
            drawSudoku(filledInSudoku);
            drawLines();
            gc.setFill(Color.CORAL);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (e.getX() > fields[i][j].getStartingX() && e.getY() > fields[i][j].getStartingY() &&
                            e.getX() < (fields[i][j].getStartingX() + 67) && e.getY() < (fields[i][j].getStartingY() + 67)) {
                        if(checkIfEditable(fields[i][j])) {
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
        if(readyForInput) {
            gc.setFill(Color.WHITE);
            String key = e.getText();
            System.out.println(key);
            for (int i = 1; i <= 9; i++) {
                if (Integer.parseInt(key.trim()) == i) {
                    filledInSudoku[activeX][activeY] = i;
                    drawSudoku(filledInSudoku);
                    drawLines();
                }
            }
        }
    }

    public void drawSudoku(int[][] sudoku) {
        gc.setFont(new Font("Yu Gothic", 30));
        gc.clearRect(0,0,603,603);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudoku[j][i] == 0) {
                    gc.setFill(Color.rgb(11, 107, 142));
                    //gc.fillRect(i*67, j*67, 67, 67);
                } else {
                    gc.setFill(Color.WHITE);
                    gc.fillText(sudoku[j][i] + "", (i * 67) + 23, (j * 67) + 40);
                    freeFields.add(new Field(i*67, i*67));
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

    public void setAllFreeFiels() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(startingSudoku[i][j] == 0) {
                    freeFields.add(new Field(67*i, 67*j));
                }
            }
        }
    }

    public boolean checkIfEditable(Field field) {
        for (int i = 0; i < freeFields.size(); i++) {
            if(field.getStartingX() == freeFields.get(i).getStartingX() &&
                    field.getStartingY() == freeFields.get(i).getStartingY()){
                System.out.println("Editable");
                return true;
            }
        }
        return false;
    }

    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        solvingMode = false;
        readyForInput = false;
        fields = new Field[9][9];
        freeFields = new ArrayList<Field>();
        setAllFields();
    }
}
