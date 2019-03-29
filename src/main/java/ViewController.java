import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    private Creator creator;
    private Solver solver;
    private GraphicsContext gc;

    @FXML
    private Canvas canvas;


    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
    }
}
