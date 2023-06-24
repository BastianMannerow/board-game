package tnt.model.gods.movement;
import tnt.model.Board;
import tnt.model.Field;
import tnt.model.Figure;
import tnt.model.Player;
import tnt.model.interfaces.Gods;

import java.util.ArrayList;

public class Artemis implements Gods{

    @Override
    public String getName() {
        return "Artemis";
    }


    // muss überarbeited werden, da die art und weise wir götter jetzt implementiert werden
    public static ArrayList<Field> getValidMove(Figure figure, int originalFigureX, int originalFigureY){
//        for(Gods god:activeGods) {
//            switch (god) {
//                case Apollo:
//                    ArrayList<Field> apolloFields = Apollo.getValidMove(playerOrder, board);
//                case Charon:
//                case Hermes:
//                case Minotaures:
//                case Triton:
//            }
//        }
        //Remove Artemis double
        return null;
    }
    // muss überarbeited werden, da die art und weise wir götter jetzt implementiert werden
    //public static ArrayList<Field> getValidMove(Figure figure, int originalFigureX, int originalFigureY){
    //    for(Gods god: activeGods) {
    //        switch (god) {
    //            case Apollo:
    //                ArrayList<Field> apolloFields = Apollo.getValidMove(playerOrder, board);
    //            case Charon:
    //            case Hermes:
    //            case Minotaures:
    //           case Triton:
    //        }
}
        //Remove Artemis double

