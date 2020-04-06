package client.gui;

import client.controller.ConcentrationController;
import client.model.ConcentrationModel;
import client.model.Observer;

import common.ConcentrationException;

import javafx.application.Application;
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
    private HashMap pokemonMap;
    private Button[][] pokemonArray;
    private GridPane gridPane;
    private BorderPane borderPane;
    private Label movesLabel;
    private Label matchesLabel;
    private Label statusLabel;
    private boolean toggle = false;

    private Image abra = new Image(getClass().getResourceAsStream("abra.png"));
    private Image bulbasaur = new Image(getClass().getResourceAsStream("bulbasaur.png"));
    private Image charizard = new Image(getClass().getResourceAsStream("charizard.png"));
    private Image diglett = new Image(getClass().getResourceAsStream("diglett.png"));
    private Image golbat = new Image(getClass().getResourceAsStream("golbat.png"));
    private Image golem = new Image(getClass().getResourceAsStream("golem.png"));
    private Image jigglypuff = new Image(getClass().getResourceAsStream("jigglypuff.png"));
    private Image magikarp = new Image(getClass().getResourceAsStream("magikarp.png"));
    private Image meowth = new Image(getClass().getResourceAsStream("meowth.png"));
    private Image mewtwo = new Image(getClass().getResourceAsStream("mewtwo.png"));
    private Image natu = new Image(getClass().getResourceAsStream("natu.png"));
    private Image pidgey = new Image(getClass().getResourceAsStream("pidgey.png"));
    private Image pikachu = new Image(getClass().getResourceAsStream("pikachu.png"));
    private Image pokeball = new Image(getClass().getResourceAsStream("pokeball.png"));
    private Image poliwag = new Image(getClass().getResourceAsStream("poliwag.png"));
    private Image psyduck = new Image(getClass().getResourceAsStream("psyduck.png"));
    private Image rattata = new Image(getClass().getResourceAsStream("rattata.png"));
    private Image slowpoke = new Image(getClass().getResourceAsStream("slowpoke.png"));
    private Image snorlax = new Image(getClass().getResourceAsStream("snorlak.png"));
    private Image squirtle = new Image(getClass().getResourceAsStream("squirtle.png"));





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
        this.pokemonMap = new HashMap();
        this.pokemonArray = new Button[dim][dim];
        this.gridPane = new GridPane();
        for (int i = 0; i < this.pokemonArray.length; i++){
            for (int i2 = 0; i2 < this.pokemonArray.length; i2++){
                Button button = new Button();
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
