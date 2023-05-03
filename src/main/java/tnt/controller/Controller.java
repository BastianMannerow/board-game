package tnt.controller;

import tnt.gui.View;

public class Controller {

    public void setView(View v) {
        v.getBtnCounterUp().setOnAction(event -> {
            v.getModel().setPlayersTurn(v.getModel().getPlayersTurn() + 1);
        });

        v.getBtnCounterDown().setOnAction(event -> {
            v.getModel().setPlayersTurn(v.getModel().getPlayersTurn() - 1);
        });
    }

}
