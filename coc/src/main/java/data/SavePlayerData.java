package data;

import model.Player;

import java.sql.Connection;
import java.sql.DriverManager;


public class SavePlayerData extends Thread {
    private final Player player;
    public SavePlayerData(Player player)
    {
        this.player = player;
    }


    @Override
    public void run() {
        try{
        String Url = "jdbc:mysql://localhost/game";
        String name = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(Url, name, "");
        String query = String.format("INSERT INTO player(ID, password, level, win, lose, mapId) VALUES ('%s','%s','%d','%d','%d','%d')",
                player.getIdPlayer(), player.getPassword(), player.getLevel(), player.getNumberWin(), player.getNumberLose(), player.getMap().getMapId());

        connection.prepareStatement(query).execute();
        connection.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
