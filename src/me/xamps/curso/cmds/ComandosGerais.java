package me.xamps.curso.cmds;

import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.xamps.curso.Main;
import me.xamps.curso.utils.Mensagens;

public class ComandosGerais implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Mensagens.consolePlayer);
			return true;
		}
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("heal")) {
			if(!p.hasPermission("curso.vip")) {
				p.sendMessage(Mensagens.noPerm);
				return true;
			}
			if(args.length == 0) {
				p.sendMessage(Main.pl.getConfig().getString("Gerais.Curado").replace("&", "§"));
				p.setHealth(20);
				p.setFireTicks(0);
				p.setFoodLevel(20);
			}
		
			
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				p.sendMessage(Mensagens.offlinePlayer);
			} else {
				p.sendMessage(Main.pl.getConfig().getString("Gerais.HealOutros").replace("&", "§").replace("%jogador%", target.getName()));
				target.sendMessage("§9* §7Você foi curado por §9" + p.getName());
				target.sendMessage(Main.pl.getConfig().getString("Gerais.HealPorOutros").replace("&", "§").replace("%jogador%", p.getName()));
				target.setHealth(20);
				target.setFireTicks(0);
				target.setFoodLevel(20);
			}
			return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("gm")) {
			if (!p.hasPermission("curso.admin")) {
				p.sendMessage(Mensagens.noPerm);
				return true;
			}
			if (args.length == 0) { 
				p.sendMessage(Main.pl.getConfig().getString("Sintaxes.GameMode").replace("&", "§"));
				return true;
			} else {
				if (args[0].equalsIgnoreCase("1")) {
					p.sendMessage(Main.pl.getConfig().getString("Gerais.GMCriativo").replace("&", "§"));
					p.setGameMode(GameMode.CREATIVE);
				} else if (args[0].equalsIgnoreCase("0")) {
						p.sendMessage(Main.pl.getConfig().getString("Gerais.GMSurvival").replace("&", "§"));
						p.setGameMode(GameMode.SURVIVAL);
				} else {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						p.sendMessage(Mensagens.offlinePlayer);
						return true;
					} else {
						if (args.length == 1) { 
							p.sendMessage(Main.pl.getConfig().getString("Sintaxes.GameMode").replace("&", "§"));
							return true;
						} else {
							if (args[1].equalsIgnoreCase("1")) {
								p.sendMessage(Main.pl.getConfig().getString("Gerais.ChangedGMCriativo").replace("&", "§").replace("%jogador%", target.getName()));
								target.sendMessage(Main.pl.getConfig().getString("Gerais.ChangedByGMCriativo").replace("&", "§").replace("%jogador%", p.getName()));
								target.setGameMode(GameMode.CREATIVE);
							} else if (args[1].equalsIgnoreCase("0")) {
									p.sendMessage(Main.pl.getConfig().getString("Gerais.ChangedGMSurvival").replace("&", "§").replace("%jogador%", target.getName()));
									target.sendMessage(Main.pl.getConfig().getString("Gerais.ChangedByGMSurvival").replace("&", "§").replace("%jogador%", p.getName()));
									target.setGameMode(GameMode.SURVIVAL);
							} else {
								p.sendMessage(Main.pl.getConfig().getString("Sintaxes.GameMode").replace("&", "§"));
								return true;
							}							
						}
					}
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("tpall")) {
			if (!p.hasPermission("curso.admin")) {
				p.sendMessage(Mensagens.noPerm);
			}
			
			for(Player todos:Bukkit.getOnlinePlayers()) {
				todos.teleport(p.getLocation());
				todos.sendMessage(Main.pl.getConfig().getString("Gerais.PuxouTodos").replace("&", "§").replace("%jogador%", p.getName()));
				p.sendMessage(Main.pl.getConfig().getString("Gerais.TodosPuxados").replace("&", "§"));
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("tp")) {
			if (!p.hasPermission("curso.admin")) {
				p.sendMessage(Mensagens.noPerm);
			}
			
			if (args.length == 0) {
				p.sendMessage(Main.pl.getConfig().getString("Sintaxes.Tp").replace("&", "§"));
				return true;
			}
			
			if (args.length == 1) {
				Player j1 = Bukkit.getPlayer(args[0]);
				if (j1 == null) {
					p.sendMessage(Mensagens.offlinePlayer);
					return true;
				}
				p.teleport(j1);
				p.sendMessage(Main.pl.getConfig().getString("Gerais.Teleported").replace("&", "§"));
			}
			
			if (args.length == 2) {
				Player j1 = Bukkit.getPlayer(args[0]);
				if (j1 == null) {
					p.sendMessage(Mensagens.offlinePlayer);
					return true;
				}
				Player j2 = Bukkit.getPlayer(args[1]);
				if (j2 == null) {
					p.sendMessage(Mensagens.offlinePlayer);
					return true;
				}
				
				p.sendMessage(Main.pl.getConfig().getString("Gerais.Teleported").replace("&", "§"));
				j1.teleport(j2);
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("tphere")) {
			if (!p.hasPermission("curso.admin")) {
				p.sendMessage(Mensagens.noPerm);
				return true;
			}
			
			if (args.length == 0) {
				p.sendMessage(Main.pl.getConfig().getString("Gerais.Tphere").replace("&", "§"));
				return true;
			}
			
			if (args.length == 1) {
				Player puxado = Bukkit.getPlayer(args[0]);
				if (puxado == null) {
					p.sendMessage(Mensagens.offlinePlayer);
					return true;
				}
				puxado.teleport(p);
				p.sendMessage(Main.pl.getConfig().getString("Gerais.Teleported").replace("&", "§"));
				puxado.sendMessage(Main.pl.getConfig().getString("Gerais.PuxadoPorStaff").replace("&", "§").replace("%jogador%", p.getName()));
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("cc")) {
			if (!p.hasPermission("curso.admin")) {
				p.sendMessage(Mensagens.noPerm);
				return true;
			}
			
			for(int i = 0; i < 150; i++) {
				for(Player todos:Bukkit.getOnlinePlayers()) {
					todos.sendMessage(" ");
				}
			}
			for(Player todos:Bukkit.getOnlinePlayers()) {
				todos.sendMessage(Main.pl.getConfig().getString("Mensagens.ChatLimpo").replace("&", "§").replace("%jogador%", p.getName()));
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("anuncio")) {
			if (!p.hasPermission("curso.admin")) {
				p.sendMessage(Mensagens.noPerm);
				return true;
			}
			
			if (args.length == 0) {
				p.sendMessage(Main.pl.getConfig().getString("Sintaxes.Anuncio").replace("&", "§"));
				return true;
			} else {
				StringBuilder stringBuilder = new StringBuilder();
				for(int i = 0; i < args.length; i++) {
					stringBuilder.append(args[i]);
					stringBuilder.append(" ");
				}
				Bukkit.broadcastMessage("§a ");
				Bukkit.broadcastMessage("§8[§c§lANUNCIO§8] §7" + stringBuilder.toString());
				Bukkit.broadcastMessage("§a ");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("checar")) {
			if (!p.hasPermission("curso.admin")) {
				p.sendMessage(Mensagens.noPerm);
				return true;
			}
			
			if (args.length == 0) {
				p.sendMessage("§e* §7Use /checar (jogador)");
				return true;
			}
			
			Player checado = Bukkit.getPlayer(args[0]);
			if (checado == null) {
				p.sendMessage(Mensagens.offlinePlayer);
				return true;
			}
			
			p.sendMessage("§a* §7Checagem ");
			p.sendMessage("§aJogador: §7" + checado.getName());
			p.sendMessage("§aGameMode: §7" + checado.getGameMode());
			p.sendMessage("§aVida: §7" + checado.getHealth());
			p.sendMessage("§aLocalização: §7X: " + checado.getLocation().getBlockX() + " Y: " + checado.getLocation().getBlockY() + " Z: " + checado.getLocation().getBlockZ());
		//	p.sendMessage("§aIp: §7" + checado.getAddress().getHostString());
			
			try {
				ResultSet rs = Main.getMysql().conectar().createStatement().executeQuery(
				"SELECT * FROM `pvp` WHERE `nick`='" + checado.getName() + "';");
				
				if (rs.next()) {
					p.sendMessage("§a");
					p.sendMessage("§aAbates: §7" + rs.getInt("kill"));
					p.sendMessage("§aMortes: §7" + rs.getInt("death"));
					p.sendMessage("§a");
				}
				rs.getStatement().getConnection().close();
			} catch (Exception e) {
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("perfil")) {
			if (args.length == 0) {
				p.sendMessage("§a");
				p.sendMessage("§a* §7Meu Perfil");
				p.sendMessage("§aNome: §7" + p.getName());
				p.sendMessage("§aVida: §7" + p.getHealth());
				
				try {
					ResultSet rs = Main.getMysql().conectar().createStatement().executeQuery(
					"SELECT * FROM `pvp` WHERE `nick`='" + p.getName() + "';");
					
					if (rs.next()) {
						p.sendMessage("§a");
						p.sendMessage("§aAbates: §7" + rs.getInt("kill"));
						p.sendMessage("§aMortes: §7" + rs.getInt("death"));
						p.sendMessage("§a");
					}
					rs.getStatement().getConnection().close();
				} catch (Exception e) {
				}
			} else {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					p.sendMessage(Mensagens.offlinePlayer);
					return true;
				}
				
				p.sendMessage("§a");
				p.sendMessage("§a* §7Perfil de §a" + target.getName());
				p.sendMessage("§aNome: §7" + target.getName());
				p.sendMessage("§aVida: §7" + target.getHealth());
				
				try {
					ResultSet rs = Main.getMysql().conectar().createStatement().executeQuery(
					"SELECT * FROM `pvp` WHERE `nick`='" + target.getName() + "';");
					
					if (rs.next()) {
						p.sendMessage("§a");
						p.sendMessage("§aAbates: §7" + rs.getInt("kill"));
						p.sendMessage("§aMortes: §7" + rs.getInt("death"));
						p.sendMessage("§a");
					}
					rs.getStatement().getConnection().close();
				} catch (Exception e) {
				}
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("resetkdr")) {
			if (!p.hasPermission("curso.admin")) {
				p.sendMessage(Mensagens.noPerm);
				return true;
			}
			
			if (args.length == 0) {
				p.sendMessage("§e* §7Use /resetkdr (jogador)");
				return true;
			}
			
			String check = args[0];
			try {
				ResultSet rs = Main.getMysql().conectar().createStatement().executeQuery(
				"SELECT * from `pvp` WHERE `nick`='" + check + "';");
				
				if(rs.next()) {
					Main.getMysql().conectar().createStatement().executeUpdate(
					"UPDATE `pvp` SET `kill`='0' WHERE `nick`='" + check + "';");
					Main.getMysql().conectar().createStatement().executeUpdate(
					"UPDATE `pvp` SET `death`='0' WHERE `nick`='" + check + "';");
					
					p.sendMessage("§a* §7KDR resetado com sucesso!");
					rs.getStatement().getConnection().close();
				} else {
					p.sendMessage(Mensagens.offlinePlayer);
					rs.getStatement().getConnection().close();
				}
				rs.getStatement().getConnection().close();
			} catch (Exception e) {
			}
		}
		return false;
	}

}
