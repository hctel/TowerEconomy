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

//I know the main class of a plugin shouldn't be named "Main". I was dumb and very drunk, don't blame me x)
public class Main extends JavaPlugin {
	public static Plugin plugin;
	private Connection connection;
	private String host, database, username, password;
	private int port;
	private static Statement statement;	
	@Override
	public void onEnable() {
		/*
		* SQL Credentials
		*/
		host = "localhost"; //test value
		port = 3306; //test value
		database = "TowerEconomy"; //test value
		username = "root"; //test value
		password = "1234"; //test value
		
		System.out.println("[TowerEconomy] TowerEconomy v1 par MisterRedCraft. En cours de développement");
		System.out.println("[TowerEconomy] Plugin ident: TowerEconomy-v1-dev01-b1_mV1122");
		
		/*
		* Registering commands and listeners
		*/
		registerEvents(this, new Listeners());
		getCommand("money").setExecutor(new Commands());
		getCommand("moneyedit").setExecutor(new Commands());
		getCommand("shop").setExecutor(new Commands());
		getCommand("editshop").setExecutor(new Commands());
		getCommand("abouttowereconomy").setExecutor(new Commands());
		
		/*
		* Opening SQL connection
		*/
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
		ResultSet result = statement.executeQuery("SELECT * FROM TowerManiaCoinSystem WHERE playerUUID = '" + player + "';"); //Querying player's UUID in database
		System.out.println(result.next());
		if(!result.next()) { //if uuid not found
			statement.execute("INSERT INTO TowerManiaCoinSystem (PLAYERUUID, COINS) VALUES ('"+player+"', 500);"); //Creating player's entry if non existent
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
