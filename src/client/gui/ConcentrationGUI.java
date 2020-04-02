package client.gui;

import client.controller.ConcentrationController;
import client.model.ConcentrationModel;
import client.model.Observer;

import common.ConcentrationException;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.*;

/**
 * A JavaFX GUI client for the network concentration game.  It represent the "View"
 * component of the MVC architecture in use here.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class ConcentrationGUI extends Application implements Observer<ConcentrationModel, ConcentrationModel.CardUpdate> {
    @Override
    public void init() {
        List<String> args = getParameters().getRaw();

        // get host and port from command line
        String host = args.get(0);
        int port = Integer.parseInt(args.get(1));

        // create the model, and add ourselves as an observer
        // TODO

        // initiate the controller
        // TODO
    }

    @Override
    public void start(Stage stage) throws Exception {
        // TODO
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
