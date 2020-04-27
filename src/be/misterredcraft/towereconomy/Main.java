package be.misterredcraft.towereconomy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import be.misterredcraft.towereconomy.Listeners;
import be.misterredcraft.towereconomy.commands.Commands;

public class Main extends JavaPlugin {
	public static Plugin plugin;
	private Connection connection;
	private String host, database, username, password;
	private int port;
	private static Statement statement;	
	@Override
	public void onEnable() {
		host = "localhost";
		port = 3306;
		database = "TowerEconomy";
		username = "root";
		password = "1234";
		System.out.println("[TowerEconomy] TowerEconomy v1 par MisterRedCraft. En cours de développement");
		System.out.println("[TowerEconomy] Plugin ident: TowerEconomy-v1-dev01-b1_mV1122");
		registerEvents(this, new Listeners());
		getCommand("money").setExecutor(new Commands());
		getCommand("moneyedit").setExecutor(new Commands());
		getCommand("shop").setExecutor(new Commands());
		getCommand("editshop").setExecutor(new Commands());
		getCommand("abouttowereconomy").setExecutor(new Commands());
		//plugin = this;
		try {     
            openConnection();
            statement = connection.createStatement();
            System.out.println("SQL Connection enabled");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	} 
	public void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
		Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
		}
	public void openConnection() throws SQLException, ClassNotFoundException {
	    if (connection != null && !connection.isClosed()) {
	        return;
	    }
	 
	    synchronized (this) {
	        if (connection != null && !connection.isClosed()) {
	            return;
	        }
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
	    }
	
}
	public static void checkPlayerExisting(String player) throws SQLException, ClassNotFoundException {
		System.out.println("Checking if user exists in TowerEconomy database");
		ResultSet result = statement.executeQuery("SELECT * FROM TowerManiaCoinSystem WHERE playerUUID = '" + player + "';");
		/*List<String> inDatabasePlayer = new ArrayList<String>();
		while (result.next()) {
		    String name = result.getString("playerUUID");
		    inDatabasePlayer.add(name);
		}
		if(!(Arrays.asList(inDatabasePlayer).contains(player))) {
			statement.execute("INSERT INTO TowerManiaCoinSystem (PLAYERUUID, COINS) VALUES ('"+player+"', 500);");
			}*/
		System.out.println(result.next());
		if(result.next() == false) {
			statement.execute("INSERT INTO TowerManiaCoinSystem (PLAYERUUID, COINS) VALUES ('"+player+"', 500);");
		}
	
	}
	public static int getPlayerMoney(String player) throws SQLException {
		ResultSet result = statement.executeQuery("SELECT COINS FROM TowerManiaCoinSystem WHERE playerUUID = '" + player + "';");
		result.next();
		int money = result.getInt("COINS");
		return money;
	}
	public static void setPlayerMoney(String UUID, int amount) throws SQLException {
		statement.executeUpdate("UPDATE TowerManiaCoinSystem  SET COINS = " + amount + " WHERE playerUUID = '" + UUID + "';");
	}
}