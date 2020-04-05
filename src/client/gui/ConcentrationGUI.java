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
    private HashMap pokemonMap;

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
        ConcentrationModel model = new ConcentrationModel();
        model.addObserver(this);
        // initiate the controller
        // TODO
        ConcentrationController controller = new ConcentrationController(host, port, model);
    }

    private GridPane makeGridPane(){
        this.pokemonMap = new HashMap();

        GridPane gridPane = new GridPane();
        return gridPane;
    }


    private BorderPane makeBorderPane(ConcentrationModel.Status status, int moves, int matches){
        BorderPane pane = new BorderPane();
        Label label = new Label("Moves: " + moves + "       Matches: " + matches + "       Status: " + status);
        pane.setCenter(makeGridPane());
        pane.setBottom(label);
        return pane;
    }


    @Override
    public void start(Stage stage) throws Exception {
        // TODO
        ConcentrationModel model = new ConcentrationModel();

        Scene scene = new Scene(makeBorderPane(model.getStatus(), model.getNumMoves(), model.getNumMatches()));
        stage.setScene(scene);
        stage.show();
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
