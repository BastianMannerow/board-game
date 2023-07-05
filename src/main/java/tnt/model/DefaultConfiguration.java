package tnt.model;

import tnt.gui.Language;
import tnt.util.Observable;
import tnt.util.Observer;

public class DefaultConfiguration extends Observable implements Observer {

    public enum Default_Config {
        PLAYER_2,
        PLAYER_3,
        PLAYER_4
    }

    private Default_Config defaultConfig;

    public static DefaultConfiguration[] getDefaultConfig() {
        return defaults;
    }

    private static final DefaultConfiguration[] defaults = {
            new DefaultConfiguration(Default_Config.PLAYER_2),
            new DefaultConfiguration(Default_Config.PLAYER_3),
            new DefaultConfiguration(Default_Config.PLAYER_4)
    };

    public DefaultConfiguration(Default_Config defaultConfig){
        this.defaultConfig = defaultConfig;
        Language.getInstance().addObserver(this);
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        switch (defaultConfig) {
            case PLAYER_2:
                str.append("2");
                break;
            case PLAYER_3:
                str.append("3");
                break;
            case PLAYER_4:
                str.append("4");
        }
        switch (Language.getLanguage()) {
            case GERMAN:
                str.append(" Spieler");
                break;
            case ENGLISH:
            default:
                str.append(" player");
        }
        return str.toString();
    }


    @Override
    public void update() {
        notifyObservers();
    }

}
