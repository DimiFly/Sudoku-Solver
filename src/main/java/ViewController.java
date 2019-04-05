import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    private Creator creator;
    private Solver solver;
    private GraphicsContext gc;

    @FXML
    private Canvas canvas;


    @FXML
    public void handleButtonGenerate(ActionEvent e) {
        gc.clearRect(0,0,603,603);
        creator = new Creator(9,3,45);
        drawLines();
        drawSudoku(creator.getSudokuWithHoles());
    }

    @FXML
    public void handleButtonSolve(ActionEvent e) {

    }

    @FXML
    public void handleButtonSave(ActionEvent e) {

    }

    @FXML
    public void handleButtonLoad(ActionEvent e) {

    }

    @FXML
    public void handleButtonEmpty(ActionEvent e) {

    }

    public void drawSudoku(int[][] sudoku) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Yu Gothic", 30));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudoku[i][j] != 0) {
                    gc.fillText(sudoku[i][j] + "", 603 - (i * 67) - 40, 603 - (j * 67) - 23);
                }
            }
        }

    }

    public void drawLines(){
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        for (int i = 0; i <= 9; i++) {
            if(i == 3 || i == 6) {
                gc.fillRect(603/9*i-2, 0, 5, 603);
            } else {
                gc.strokeLine(603 / 9 * i, 0, 603 / 9 * i, 603);
            }
        }
        for (int i = 0; i <= 9; i++) {
            if(i == 3 || i == 6) {
                gc.fillRect(0, 603/9*i-2, 603, 5);
            } else {
                gc.strokeLine(0, 603 / 9 * i, 603, 603 / 9 * i);
            }
        }
        gc.strokeLine(1, 0, 1, 603);
        gc.strokeLine(0, 1, 603, 1);
        gc.strokeLine(602, 0, 602, 603);
        gc.strokeLine(0, 602, 603, 602);
    }

    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();

    }
}
