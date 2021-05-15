import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Case extends Button{
    private boolean cache;
    private boolean bombe;
    private List<Case> voisins;
    private boolean inter;
    private Demineur stage;

    public Case(int size, Demineur stage){
        this.stage = stage;
        this.cache = true;
        Random rand = new Random();
        int number = rand.nextInt(10);
        if (number == 1){
            this.bombe = true;
        }
        else{
            this.bombe = false;
        }
        this.setPrefSize(size,size);
        List<Case> v = new ArrayList<>();
        this.voisins = v;
        this.inter = false;

        Case here = this;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                if(e.getButton() == MouseButton.PRIMARY){
                    if (here.estBombe()){
                        Image image = new Image("file:img/bomb.png");
                        ImageView view = new ImageView(image);
                        view.setPreserveRatio(true);
                        view.setFitWidth(30);
                        here.setGraphic(view);
                        stage.loseDialog();
                    }
                    if (here.cache) {
                        here.decouvre();
                        stage.checkWin();
                    }
                    }
                if(e.getButton() == MouseButton.SECONDARY){
                    here.rightClick();
                }
            }
        });

    }
    public boolean getCache(){
        return this.cache;
    }
    public void addVoisin( Case Voisin){
        this.voisins.add(Voisin);
    }
    public boolean estBombe(){
        return this.bombe;
    }
    public void decouvre(){

        this.setStyle("-fx-background-color: lightgray;-fx-border-color:black;-fx-border-width: 0 0 0 0;");
        this.cache = false;
        stage.getDecouverte().add(this);

        List<Color> couleur = Arrays.asList(Color.BLUE, Color.GREEN, Color.RED,Color.PURPLE, Color.PINK, Color.DARKSALMON, Color.BROWN, Color.AZURE);

            if (this.getNombreBombesVoisines() > 0){
                this.setText(""+this.getNombreBombesVoisines());
                this.setTextFill(couleur.get(this.getNombreBombesVoisines() -1));
                this.setFont(Font.font(null, FontWeight.BOLD, 15));
            }
            else{
                for (Case elem2:this.voisins) {
                    if (elem2.cache) {
                        elem2.decouvre();
                    }
                }
            }

    }
    public int getNombreBombesVoisines(){
        int res = 0;
        for (Case elem:this.voisins){
            if (elem.estBombe()){
                res ++;
            }
        }
        return res;
    }
    public boolean isFlagged(){
        return this.inter;
    }
    public void rightClick(){
        Image image = new Image("file:img/flag.png");
        ImageView view = new ImageView(image);
        ImageView view2 = new ImageView();
        view.setPreserveRatio(true);
        view.setFitWidth(30);
        if (!this.inter && this.cache){
            this.inter = true;
            this.setGraphic(view);
        }
        else if(this.inter){
            this.inter = false;
            this.setGraphic(view2);
        }
    }
    @Override
    public String toString(){
        return"Case, bombe:"+estBombe();
    }
}