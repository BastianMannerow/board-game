package tnt.model.gods.sabotage;
import tnt.model.Field;
import tnt.model.Figure;
import java.util.ArrayList;
import tnt.model.interfaces.Gods;
public class Hypnus implements Gods{
    @Override
    public String getName() {
        return "Hypnus";
    }
    public static ArrayList<Field> sabotage(Figure figure, ArrayList<Field> possibleFields){
        return possibleFields;
    }
}
