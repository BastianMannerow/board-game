package tnt.model;

import tnt.gui.Language;
public class DefaultConfiguration {

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
        str.append(" ").append(Language.player());
        return str.toString();
    }


    public int getAmountOfPlayer() {
        switch (defaultConfig){
            case PLAYER_3:
                return 3;
            case PLAYER_4:
                return 4;
            default:
                return 2;
        }
    }
}
