package client.gui;

import client.controller.ConcentrationController;
import client.model.ConcentrationModel;
import client.model.Observer;

import common.ConcentrationException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
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
    private Image pokeball = new Image(getClass().getResourceAsStream("images/pokeball.png"));
    private static final Background WHITE =
            new Background( new BackgroundFill(Color.WHITE, null, null));
    private static final Background BLUE =
            new Background( new BackgroundFill(Color.BLUE, null, null));

    //ConcentrationGUI constructor
    public ConcentrationGUI(){
        //creates a list of all the pokemon images
        List<String> pokemonList = new ArrayList<>(Arrays.asList("images/abra.png", "images/bulbasaur.png", "images/charizard.png", "images/diglett.png", "images/golbat.png", "images/golem.png", "images/jigglypuff.png", "images/magikarp.png", "images/meowth.png", "images/mewtwo.png", "images/natu.png", "images/pidgey.png", "images/pikachu.png", "images/poliwag.png", "images/psyduck.png", "images/rattata.png", "images/slowpoke.png", "images/snorlak.png", "images/squirtle.png"));
        //shuffles the images
        Collections.shuffle(pokemonList);
        //creates a HashMap of all the images and a corresponding letter
        this.pokemonMap = new HashMap();
        for (int i = 0; i < pokemonList.size(); i++){
            pokemonMap.put(Character.toString('A' + i), new Image(getClass().getResourceAsStream(pokemonList.get(i))));
        }
    }





    @Override
    public void init() {
        try {
            List<String> args = getParameters().getRaw();

            // get host and port from command line
            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            // create the model, and add ourselves as an observer
            this.model = new ConcentrationModel();
            this.model.addObserver(this);
            // initiate the controller
            this.controller = new ConcentrationController(host, port, this.model);
        }
        //catches exceptions
        catch (ConcentrationException | ArrayIndexOutOfBoundsException | NumberFormatException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    //creates a grippane for all of the buttons
    private GridPane makeGridPane(int dim){
        //creates an array of buttons
        this.pokemonArray = new Button[dim][dim];
        //creates a new gridpane
        this.gridPane = new GridPane();

        //creates all of the buttons in the array
        for (int i = 0; i < this.pokemonArray.length; i++){
            for (int i2 = 0; i2 < this.pokemonArray.length; i2++){
                //creates a new button
                Button button = new Button();
                //sets the image of the button as a pokeball
                button.setGraphic(new ImageView(pokeball));
                //makes the background of the button blue
                button.setBackground(BLUE);
                int finalI = i2;
                int finalI1 = i;
                //makes the button change to the pokemon image when the button is clicked
                button.setOnAction(actionEvent -> controller.revealCard(finalI1, finalI));
                pokemonArray[i][i2] = button;
                //adds button to the gridpane
                this.gridPane.add(button, i, i2);
            }
        }


        return this.gridPane;
    }


    //creates a border pane
    private BorderPane makeBorderPane(){
        this.borderPane = new BorderPane();
        //adds labels for the moves, matches, and status in the border pane
        this.movesLabel = new Label("Moves: ");
        this.matchesLabel = new Label("Matches: ");
        this.statusLabel = new Label("Status: ");
        this.borderPane.setLeft(movesLabel);
        this.borderPane.setCenter(matchesLabel);
        this.borderPane.setRight(statusLabel);
        return this.borderPane;
    }

    //updates the labels to have the correct number of moves and matches throughout the game
    private void borderUpdate(){
        this.movesLabel.setText("Moves: " + this.model.getNumMoves());
        this.matchesLabel.setText("Matches: " + this.model.getNumMatches());
        this.statusLabel.setText("Status: " + this.model.getStatus());
    }


    @Override
    public void start(Stage stage) throws Exception {
        //makes the border pane
        BorderPane border = makeBorderPane();
        //updates the label
        borderUpdate();
        //creates a border pane to put the grid and the other border pane in
        BorderPane globalPane = new BorderPane();
        globalPane.setCenter(makeGridPane(this.model.getDIM()));
        globalPane.setBottom(border);

        Scene scene = new Scene(globalPane);
        //creates the title
        stage.setTitle("Concentration GUI");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        toggle = true;
    }


    //creates an Updated class
    public class Updated implements Runnable{
        private ConcentrationModel.CardUpdate card;

        //constructor for Updated
        public Updated(ConcentrationModel.CardUpdate card){
            this.card = card;
        }
        @Override
        public void run() {
            if (card != null){
                //sets the image of the button to a pokemon if the card is revealed
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
            //updates the labels
            borderUpdate();
        }
    }

    @Override
    public void update(ConcentrationModel model, ConcentrationModel.CardUpdate card) {
        if (model.getStatus() == ConcentrationModel.Status.ERROR){
            Platform.exit();
        }
        else{
            //makes sure the GUI is running correctly
            if (toggle == true){
                Platform.runLater(new Updated(card));
            }
        }

    }

    @Override
    public void stop() {
        //closes the application
        if (controller != null) {
            controller.close();
        }
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
