import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaException;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Type;
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
    private IO io;
    private Stage primaryStage;

    @FXML
    private Slider numbers;

    @FXML
    private Canvas canvas;

    @FXML
    private Label actualNumber;

    @FXML
    private Button minimizeButton;

    @FXML
    public void handleButtonGenerate(ActionEvent e) {
        solvingMode = true;
        gc.clearRect(0, 0, 603, 603);
        setAllFields();
        creator = new Creator(9, 3, getNumbers());
        startingSudoku = creator.makeHoles(creator.getSudoku());
        io.outputTempSudoku(startingSudoku);
        setAllFreeFiels();
        filledInSudoku = startingSudoku;
        drawSudoku(filledInSudoku);
        drawLines();
    }

    @FXML
    public void handleButtonSolve(ActionEvent e) {
        solvingMode = false;
        solver = new Solver(io.inputTempSudoku(), 9, 3);
        if(!solver.solve()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("This Sudoku can't be solved");

            alert.showAndWait();
        } else {
            drawSudoku(solver.getSudoku());
            drawLines();
        }
    }

    @FXML
    public void handleButtonSave(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.setInitialFileName("NewSudoku");
        fc.setTitle("Save Sudoku");
        fc.setInitialDirectory(new File("Sudokus/"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Sudoku Files (*.sudoku)", "*.sudoku"),
                new FileChooser.ExtensionFilter("All Files (*.*)", "*.*"));
        try {
            File file = fc.showSaveDialog(new Stage());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    bw.write(getTempSudoku()[i][j] + " ");
                }
                bw.newLine();
            }
            bw.close();
        } catch (FileNotFoundException exc) {
            System.err.println("The file could not be found.");
        } catch (IOException exc) {
            System.err.println("The file could not be written.");
        }
    }

    @FXML
    public void handleButtonLoad(ActionEvent e) {
        solvingMode = true;
        setAllFields();
        Node node = (Node) e.getSource();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Sudoku");
        fileChooser.setInitialDirectory(new File("Sudokus/"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(node.getScene().getWindow());
        if (selectedFile != null) {
            io.outputTempSudoku(io.inputSudoku(selectedFile.getAbsolutePath()));
            startingSudoku = io.inputSudoku(selectedFile.getAbsolutePath());
            setAllFreeFiels();
            filledInSudoku = startingSudoku;
            drawSudoku(filledInSudoku);
            drawLines();
        } else {
            System.out.println("No File Chosen");
        }
    }

    @FXML
    public void handleButtonEmpty(ActionEvent e) {
        solvingMode = false;
        gc.clearRect(0, 0, 603, 603);
        drawLines();
    }

    @FXML
    public void handleCanvasClicked(MouseEvent e) {
        if (solvingMode) {
            drawSudoku(filledInSudoku);
            drawLines();
            gc.setFill(Color.rgb(100, 100, 100));
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (e.getX() > fields[i][j].getStartingX() && e.getY() > fields[i][j].getStartingY() &&
                            e.getX() < (fields[i][j].getStartingX() + 67) && e.getY() < (fields[i][j].getStartingY() + 67)) {
                        if (fields[i][j].isEditable()) {
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
        if (readyForInput) {
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
            if (checkIfSudokuIsCorrect()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Well done!");
                alert.setHeaderText("Well done!");
                alert.setContentText("You have successfully completed the Sudoku");

                alert.showAndWait();
            }

        }
    }

    @FXML
    public void handleButtonClose() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void handleButtonMinimize() {
        minimizeButton.setOnAction(e ->
                ((Stage) ((Button) e.getSource()).getScene().getWindow()).setIconified(true)
        );
    }

    /**
     * Sudoku zeichnen
     * @param sudoku
     */
    public void drawSudoku(int[][] sudoku) {
        gc.setFont(new Font("Yu Gothic", 30));
        gc.clearRect(0, 0, 603, 603);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                /*if(fields[j][i].isEditable()) {
                    if(sudoku[j][i] != 0) {
                        gc.setFill(Color.rgb(11, 107, 142));
                        gc.fillText(sudoku[j][i] + "", (i * 67) + 23, (j * 67) + 40);
                    }
                    //gc.fillRect(j*67, i*67, 67, 67);
                } else {*/
                if (sudoku[j][i] != 0) {
                    gc.setFill(Color.WHITE);
                    gc.fillText(sudoku[j][i] + "", (i * 67) + 23, (j * 67) + 40);

                }
                //}
            }
        }

    }

    /**
     * Sudokulinien zeichnen
     */
    public void drawLines() {
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        for (int i = 0; i <= 9; i++) {
            if (i % 3 == 0) {
                gc.fillRect(603 / 9 * i - 2, 0, 5, 603);
            } else {
                gc.strokeLine(603 / 9 * i, 0, 603 / 9 * i, 603);
            }
        }
        for (int i = 0; i <= 9; i++) {
            if (i % 3 == 0) {
                gc.fillRect(0, 603 / 9 * i - 2, 603, 5);
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
                fields[i][j] = new Field(67 * i, 67 * j);
            }
        }
    }

    /**
     * Alle Felder die man beschreiben darf setzen
     */
    public void setAllFreeFiels() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (startingSudoku[i][j] == 0) {
                    fields[j][i].setEditable(true);
                }
            }
        }
    }

    /**
     * Kontrollieren ob das Sudoku korrekt ausgefüllt wurde
     * @return
     */
    public boolean checkIfSudokuIsCorrect() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.println(filledInSudoku[i][j]);
                if(!creator.checkIfSafe(i, j, filledInSudoku[i][j], filledInSudoku) || filledInSudoku[i][j] == 0){
                    return false;
                }
            }
        }
        solvingMode = false;
        return true;
    }

    /**
     * Das temporäre Sudoku einlesen
     * @return
     */
    public int[][] getTempSudoku() {
        return io.inputTempSudoku();
    }

    @FXML
    public void handleSliderAction (MouseEvent e) {
        actualNumber.setText(Integer.toString((int) numbers.getValue()));
    }

    public int getNumbers() {
        return 81 - (int) Math.round(numbers.getValue());
    }

    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        solvingMode = false;
        readyForInput = false;
        fields = new Field[9][9];
        drawLines();
        io = new IO();
    }
}
