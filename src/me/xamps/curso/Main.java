package me.xamps.curso;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.xamps.curso.cmds.ComandosGerais;
import me.xamps.curso.events.EventosGerais;
import me.xamps.curso.utils.Mysql;

public class Main extends JavaPlugin implements Listener {
	
	public static Plugin pl;
	
	private static Mysql mysql;
	
	public static Mysql getMysql() {
		return mysql;
	}
	
	@Override
	public void onEnable() {
		pl = this;
		saveDefaultConfig();
		ConsoleCommandSender cmd = Bukkit.getConsoleSender();
		
		
		mysql = new Mysql(pl.getConfig().getString("mysql.database"), pl.getConfig().getString("mysql.host"),
				pl.getConfig().getString("mysql.porta"), pl.getConfig().getString("mysql.senha"), 
				pl.getConfig().getString("mysql.user"));
		
		try {
			mysql.conectar().createStatement().executeUpdate(
				"CREATE TABLE IF NOT EXISTS `pvp` (`nick` varchar(64), `kill` int, `death` int)");
		} catch (Exception e) {
			cmd.sendMessage("§7");
			cmd.sendMessage("§7Plugin §8- §9Curso");
			cmd.sendMessage("§cErro no MySQL!");
			cmd.sendMessage("§7");
			Bukkit.getServer().getPluginManager().disablePlugin(Main.pl);
			return;
		}
		
		cmd.sendMessage("§7");
		cmd.sendMessage("§7Plugin §8- §9Curso");
		cmd.sendMessage("§7Habilitado com sucesso §8[§91.8.8§8]");
		cmd.sendMessage("§7");
		
		Comandos();
		Eventos();
	}
	
	@Override
	public void onDisable() {
		ConsoleCommandSender cmd = Bukkit.getConsoleSender();
		
		cmd.sendMessage("§7");
		cmd.sendMessage("§7Plugin §8- §9Curso");
		cmd.sendMessage("§7Desabilitado com sucesso §8[§91.8.8§8]");
		cmd.sendMessage("§7");
	}
	
	public void Comandos() {
		getCommand("heal").setExecutor(new ComandosGerais());
		getCommand("gm").setExecutor(new ComandosGerais());
		getCommand("tpall").setExecutor(new ComandosGerais());
		getCommand("tp").setExecutor(new ComandosGerais());
		getCommand("tphere").setExecutor(new ComandosGerais());
		getCommand("cc").setExecutor(new ComandosGerais());
		getCommand("anuncio").setExecutor(new ComandosGerais());
		getCommand("checar").setExecutor(new ComandosGerais());
		getCommand("perfil").setExecutor(new ComandosGerais());
		getCommand("resetkdr").setExecutor(new ComandosGerais());
	}
	
	public void Eventos() {
		Bukkit.getServer().getPluginManager().registerEvents(new EventosGerais(), this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void loginMySQL(PlayerLoginEvent e) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				String nick = e.getPlayer().getName();
				ResultSet rs;
				
				try {
					rs = Main.getMysql().conectar().createStatement().executeQuery(
					"SELECT * FROM `pvp` WHERE `nick`='" + nick + "';");
					
					if (!rs.next()) {
						Main.getMysql().conectar().createStatement().executeUpdate(
								"INSERT INTO `pvp` (`nick`, `kill`, `death`) VALUES ('" + nick + "', '0', '0');");
					}
					rs.getStatement().getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}.runTaskAsynchronously(Main.pl);
	}
	

}
