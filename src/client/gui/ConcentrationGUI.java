package client.gui;

import client.controller.ConcentrationController;
import client.model.ConcentrationModel;
import client.model.Observer;

import common.ConcentrationException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * A JavaFX GUI client for the network concentration game.  It represent the "View"
 * component of the MVC architecture in use here.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class ConcentrationGUI extends Application implements Observer<ConcentrationModel, ConcentrationModel.CardUpdate> {
    private ConcentrationModel model;
    private ConcentrationController controller;
    private HashMap<String, Image> pokemonMap;
    private Button[][] pokemonArray;
    private GridPane gridPane;
    private BorderPane borderPane;
    private Label movesLabel;
    private Label matchesLabel;
    private Label statusLabel;
    private boolean toggle = false;
    private Image pokeball = new Image(getClass().getResourceAsStream("pokeball.png"));
    private static final Background WHITE =
            new Background( new BackgroundFill(Color.WHITE, null, null));
    private static final Background BLUE =
            new Background( new BackgroundFill(Color.BLUE, null, null));

    public class Updated implements Runnable{
        private ConcentrationModel.CardUpdate card;

        public Updated(ConcentrationModel.CardUpdate card){
            this.card = card;
        }
        @Override
        public void run() {
            if (card != null){
                if (card.isRevealed()){
                    pokemonArray[card.getRow()][card.getCol()].setGraphic(new ImageView(pokemonMap.get(card.getVal())));
                    pokemonArray[card.getRow()][card.getCol()].setBackground(WHITE);
                    pokemonArray[card.getRow()][card.getCol()].setOnAction(null);
                }
                else{
                    pokemonArray[card.getRow()][card.getCol()].setGraphic(new ImageView(pokeball));
                    pokemonArray[card.getRow()][card.getCol()].setBackground(BLUE);
                    pokemonArray[card.getRow()][card.getCol()].setOnAction(actionEvent -> controller.revealCard(card.getRow(), card.getCol()));
                }
            }
        }
    }

    private ConcentrationGUI(){
        List<String> pokemonList = new ArrayList<>(Arrays.asList("abra.png", "bulbasaur.png", "charizard.png", "diglett.png", "golbat.png", "golem.png", "jigglypuff.png", "magikarp.png", "meowth.png", "mewtwo.png", "natu.png", "pidgey.png", "pikachu.png", "poliwag.png", "psyduck.png", "rattata.png", "slowpoke.png", "snorlak.png", "squirtle.png"));
        Collections.shuffle(pokemonList);
        this.pokemonMap = new HashMap();
        for (int i = 0; i < pokemonList.size(); i++){
            pokemonMap.put(Character.toString('A' + i), new Image(getClass().getResourceAsStream("images/" + pokemonList.get(i))));
        }
    }





    @Override
    public void init() throws ConcentrationException {

        List<String> args = getParameters().getRaw();

        // get host and port from command line
        String host = args.get(0);
        int port = Integer.parseInt(args.get(1));

        // create the model, and add ourselves as an observer
        // TODO
        this.model = new ConcentrationModel();
        this.model.addObserver(this);
        // initiate the controller
        // TODO
        this.controller = new ConcentrationController(host, port, this.model);
    }

    private GridPane makeGridPane(int dim){
        this.pokemonArray = new Button[dim][dim];
        this.gridPane = new GridPane();
        for (int i = 0; i < this.pokemonArray.length; i++){
            for (int i2 = 0; i2 < this.pokemonArray.length; i2++){
                Button button = new Button();
                button.setGraphic(new ImageView(pokeball));
                button.setBackground(BLUE);
                int finalI = i2;
                int finalI1 = i;
                button.setOnAction(actionEvent -> controller.revealCard(finalI1, finalI));
                this.gridPane.add(button, i, i2);
            }
        }


        return this.gridPane;
    }


    private BorderPane makeBorderPane(){
        this.borderPane = new BorderPane();
        this.movesLabel = new Label("Moves: ");
        this.matchesLabel = new Label("Matches: ");
        this.statusLabel = new Label("Status: ");
        this.borderPane.setLeft(movesLabel);
        this.borderPane.setCenter(matchesLabel);
        this.borderPane.setRight(statusLabel);
        return this.borderPane;
    }

    private void borderUpdate(){
        this.movesLabel.setText("Moves: " + this.model.getNumMoves());
        this.matchesLabel.setText("Matches: " + this.model.getNumMatches());
        this.statusLabel.setText("Status: " + this.model.getStatus());
    }


    @Override
    public void start(Stage stage) throws Exception {
        // TODO
        BorderPane border = makeBorderPane();
        borderUpdate();
        BorderPane globalPane = new BorderPane();
        globalPane.setCenter(makeGridPane(this.model.getDIM()));
        globalPane.setBottom(border);

        Scene scene = new Scene(globalPane);
        stage.setTitle("Concentration GUI");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        toggle = true;
    }



    @Override
    public void update(ConcentrationModel model, ConcentrationModel.CardUpdate card) {
        // TODO
        if (model.getStatus() == ConcentrationModel.Status.ERROR){
            Platform.exit();
        }
        else{
            if (toggle == true){
                Platform.runLater(new Updated(card));
            }
        }

    }

    @Override
    public void stop() {
        // TODO
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ConcentrationGUI host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
