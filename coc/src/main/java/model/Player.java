package model;

import model.Map.Map;

import java.io.Serializable;

public class Player implements Serializable {
    public Player(String idPlayer, String password, Map map) {
        this.idPlayer = idPlayer;
        this.password = password;
        this.level = 1;
        this.numberWin = 0;
        this.numberLose = 0;
        this.map = map;
    }

    private final String idPlayer;
    private final String password;

    public Map getMap() {
        return map;
    }

    private final Map map;
    private int level;
    private int numberWin;
    private int numberLose;

    public String getIdPlayer() {
        return idPlayer;
    }

    public String getPassword() {
        return password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumberWin() {
        return numberWin;
    }

    public void setNumberWin(int numberWin) {
        this.numberWin = numberWin;
    }

    public int getNumberLose() {
        return numberLose;
    }

    public void setNumberLose(int numberLose) {
        this.numberLose = numberLose;
    }
}

