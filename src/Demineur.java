import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;


public class Demineur extends Application implements DialogPopup{
    private Stage stage;
    private List<Case> listeBombe;
    private List<Case> listeFlagged;
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        int nbCasesX = 15;
        int nbCasesY = 15;
        int nombreBombe = 0;
        List<Case> listeBombe = new ArrayList<>();
        List<Case> listeFlagged = new ArrayList<>();

        BorderPane borderPane = new BorderPane();
       GridPane gridpane = new GridPane();

       Case[][] table = new Case[nbCasesX][nbCasesY];
       for (int x = 0; x< nbCasesX;x++){
           for (int y = 0; y< nbCasesY;y++) {
               Case c = new Case(50, this);
               if (c.estBombe()){
                   listeBombe.add(c);
                   nombreBombe++;
               }
               table[x][y] = c;
           }
       }
       this.listeBombe = listeBombe;
        for (int x = 0; x< nbCasesX;x++){
            for (int y = 0; y< nbCasesY;y++) {
                gridpane.add(table[x][y],x,y);
            }
        }
        ObservableList<Node> childrens = gridpane.getChildren();
        for (int i = 0; i<=nbCasesX;i++) {
            for (int j = 0; j <= nbCasesY; j++) {
                Case actualCase = new Case(100, this);
                List<Case> result = new ArrayList<>();
                for (Node node : childrens) {
                    if (gridpane.getRowIndex(node) == i - 1 && gridpane.getColumnIndex(node) == j - 1) {
                        Case voisin1 = (Case) node;
                        result.add(voisin1);
                    }
                    if (gridpane.getRowIndex(node) == i && gridpane.getColumnIndex(node) == j - 1) {
                        Case voisin1 = (Case) node;
                        result.add(voisin1);
                    }
                    if (gridpane.getRowIndex(node) == i + 1 && gridpane.getColumnIndex(node) == j - 1) {
                        Case voisin1 = (Case) node;
                        result.add(voisin1);
                    }
                    if (gridpane.getRowIndex(node) == i - 1 && gridpane.getColumnIndex(node) == j) {
                        Case voisin1 = (Case) node;
                        result.add(voisin1);
                    }
                    if (gridpane.getRowIndex(node) == i + 1 && gridpane.getColumnIndex(node) == j) {
                        Case voisin1 = (Case) node;
                        result.add(voisin1);
                    }
                    if (gridpane.getRowIndex(node) == i + 1 && gridpane.getColumnIndex(node) == j + 1) {
                        Case voisin1 = (Case) node;
                        result.add(voisin1);
                    }
                    if (gridpane.getRowIndex(node) == i && gridpane.getColumnIndex(node) == j + 1) {
                        Case voisin1 = (Case) node;
                        result.add(voisin1);
                    }
                    if (gridpane.getRowIndex(node) == i - 1 && gridpane.getColumnIndex(node) == j + 1) {
                        Case voisin1 = (Case) node;
                        result.add(voisin1);
                    }
                    if (gridpane.getRowIndex(node) == i && gridpane.getColumnIndex(node) == j) {
                        actualCase = (Case) node;
                    }

                }
                for (Case elem : result) {
                    actualCase.addVoisin(elem);
                }

            }
            borderPane.setCenter(gridpane);
            Label nbrBombe = new Label("Nombres de bombes:"+ nombreBombe);
            VBox left = new VBox();
            left.setAlignment(Pos.CENTER);
            left.setPadding(new Insets(5));
            left.getChildren().add(nbrBombe);
            borderPane.setLeft(left);

        }
       Scene scene = new Scene(borderPane);
       stage.setTitle("Demineur");
       stage.setScene(scene);
       stage.show();
   }

   public static void main(String args[]){
       launch(args);
   }

    @Override
    public void loseDialog() {
        for (Case elem:this.listeBombe){
            elem.setStyle("-fx-background-color: lightgray;-fx-border-color:black;-fx-border-width: 0 0 0 0;");
            Image image = new Image("file:img/bomb.png");
            ImageView view = new ImageView(image);
            view.setPreserveRatio(true);
            view.setFitWidth(30);
            elem.setGraphic(view);
        }
        final Stage dialog = new Stage();
        dialog.setTitle("You lose ! :(");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        GridPane dialogVbox = new GridPane();
        dialogVbox.add(new Text("You lose ! :("),0,1);
        Button leave = new Button("Exit");
        Button replay = new Button("Replay");
        dialogVbox.add(replay,1,3);
        dialogVbox.add(leave,2,3);
        leave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.hide();
            }
        });
        replay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.hide();
                Stage stage2 = new Stage();
                start(stage2);

            }
        });
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @Override
    public void winDialog(Case cas) {
        final Stage dialog = new Stage();
        dialog.setTitle("You Win ! :(");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        GridPane dialogVbox = new GridPane();
        dialogVbox.add(new Text("You Win ! :("),0,1);
        Button leave = new Button("Exit");
        Button replay = new Button("Replay");
        dialogVbox.add(replay,1,3);
        dialogVbox.add(leave,2,3);
        leave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.hide();
            }
        });
        replay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.hide();
                Stage stage2 = new Stage();
                start(stage2);
            }
        });
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
