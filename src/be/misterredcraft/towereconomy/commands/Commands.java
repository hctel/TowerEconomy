package be.misterredcraft.towereconomy.commands;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		String towerEconomyHeader = "§1§lTower§6§lEconomy §8§l>> §r";
		String error = "§cUne erreur est survenue. Veuillez contacter un administrateur.";
		Player player = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("money")) {
			UUID u = player.getUniqueId();
			String uu = u.toString();
			String uuid = uu.replace("-", "");
			System.out.println(uuid);
			try {
				int money = be.misterredcraft.towereconomy.Main.getPlayerMoney(uuid);
				player.sendRawMessage(towerEconomyHeader + "Vous avez " + money + " ¥");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("moneyedit")) {
			if(args.length <= 1 || args.length > 3) {
				player.sendRawMessage("§cUtilisation : /moneyedit <add|remove|set|check> <joueur> [montant]");
			}
			else if(args[0].equalsIgnoreCase("check")) {
				Player t = Bukkit.getPlayer(args[1]);
				UUID u = t.getUniqueId();
				String uu = u.toString();
				String uuid = uu.replace("-", "");
				try {
					int money = be.misterredcraft.towereconomy.Main.getPlayerMoney(uuid);
					player.sendRawMessage(towerEconomyHeader + t.getName() + " a " + money + " ¥");
				} catch (SQLException e) {
					e.printStackTrace();
					player.sendRawMessage(error);
				}
			}
			else if(args[0].equalsIgnoreCase("set")) {
				if(args.length != 3) {
					player.sendRawMessage("§cUtilisation : /moneyedit <add|remove|set|check> <joueur> [montant]");
				}
				else {
					Player t = Bukkit.getPlayer(args[1]);
					String amountString = args[2];
					UUID u = t.getUniqueId();
					String uu = u.toString();
					String UUID = uu.replace("-", "");
					int amount = Integer.parseInt(amountString);
					try {
						be.misterredcraft.towereconomy.Main.setPlayerMoney(UUID, amount);
						player.sendRawMessage(towerEconomyHeader + "Le nombre de ¥ de " + t.getName() + " a été fixé à "  + amount);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						player.sendRawMessage(error);
					}
					}
				}
			else if(args[0].equalsIgnoreCase("add")) {
				if(args.length != 3) {
					player.sendRawMessage("§cUtilisation : /moneyedit <add|remove|set|check> <joueur> [montant]");
				}
				else {
					Player t = Bukkit.getPlayer(args[1]);
					String amountString = args[2];
					UUID u = t.getUniqueId();
					String uu = u.toString();
					String UUID = uu.replace("-", "");
					int amount = Integer.parseInt(amountString);
					int balance = 0;
					try {
						balance = be.misterredcraft.towereconomy.Main.getPlayerMoney(UUID);
					} catch (SQLException e) {
						player.sendRawMessage(error);
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int toSet = amount+balance;
					
					try {
						be.misterredcraft.towereconomy.Main.setPlayerMoney(UUID, toSet);
						player.sendRawMessage(towerEconomyHeader +amount  + " ¥ ont été ajoutés au compte de "  + t.getName());
					} catch (SQLException e) {
						player.sendRawMessage(error);						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length != 3) {
					player.sendRawMessage("§cUtilisation : /moneyedit <add|remove|set|check> <joueur> [montant]");
				}
				else {
					Player t = Bukkit.getPlayer(args[1]);
					String amountString = args[2];
					UUID u = t.getUniqueId();
					String uu = u.toString();
					String UUID = uu.replace("-", "");
					int amount = Integer.parseInt(amountString);
					int balance = 0;
					try {
						balance = be.misterredcraft.towereconomy.Main.getPlayerMoney(UUID);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int toSet = balance-amount;
					
					try {
						be.misterredcraft.towereconomy.Main.setPlayerMoney(UUID, toSet);
						player.sendRawMessage(towerEconomyHeader +amount  + " ¥ ont été retirés du compte de "  + t.getName());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			else if(args[0].equalsIgnoreCase("egg")) {
				player.sendRawMessage("Coded by MisterRedCraft 04-2020");
			}
			return true;
			}
			
		if(cmd.getName().equalsIgnoreCase("shop")) {
			player.sendRawMessage(towerEconomyHeader + "Cette commande est toujours en cous de développement. Elle sera implémentée dans une prochaine mise à jour.");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("editshop")) {
			player.sendRawMessage(towerEconomyHeader + "Cette commande est toujours en cous de développement. Elle sera implémentée dans une prochaine mise à jour.");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("abouttowereconomy")) {
			player.sendRawMessage("----------TowerMania Economy-------------");
			player.sendRawMessage("Plugin codé par MisterRedCraft");
			player.sendRawMessage("TowerEconomy stocke l'argent de chaque");
			player.sendRawMessage("joueur en fonction de leur UUID dans une");
			player.sendRawMessage("banque de données MySQL.");
			player.sendRawMessage("Le shop n'aura que les mêmes items. Seuls");
			player.sendRawMessage("les prix changeront en fonction de l'offre");
			player.sendRawMessage("et de la demande.");
			return true;
		}
		return false;
	}
	
}