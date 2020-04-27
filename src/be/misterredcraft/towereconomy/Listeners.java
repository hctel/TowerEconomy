package be.misterredcraft.towereconomy;

import java.io.File;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) throws ClassNotFoundException, SQLException {
		Player player = event.getPlayer();
		UUID u = player.getUniqueId();
		String uu = u.toString();
		String uuid = uu.replace("-", "");
		be.misterredcraft.towereconomy.Main.checkPlayerExisting(uuid);
		}
}