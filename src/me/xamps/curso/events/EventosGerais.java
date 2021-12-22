package me.xamps.curso.events;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.xamps.curso.Main;

public class EventosGerais implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage("§8[§a+§8] §7" + p.getName() + " §7entrou no servidor!");
		p.sendMessage("§a ");
		p.sendMessage("§aBem-vindo ao nosso servidor, §7" + p.getName());
		p.sendMessage("§a ");
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player matou = e.getEntity().getKiller();
		Player morto = e.getEntity().getPlayer();
		e.setDeathMessage(null);
		matou.sendMessage("§a* §7Você matou o jogador §a" + morto.getName());
		morto.sendMessage("§a* §7Você morreu para o jogador §a " + matou.getName());
		new BukkitRunnable() {
			
			@Override
			public void run() {
				try {
					ResultSet rs = Main.getMysql().conectar().createStatement().executeQuery(
					"SELECT * from `pvp` WHERE `nick`='" + matou.getName() + "';");
					
					if(rs.next()) {
						Main.getMysql().conectar().createStatement().executeUpdate(
						"UPDATE `pvp` SET `kill`='" + String.valueOf(rs.getInt("kill") + 1 
								+ "' WHERE `nick`='" + matou.getName() + "';"));
					}
					rs.getStatement().getConnection().close();
					
					ResultSet rs2 = Main.getMysql().conectar().createStatement().executeQuery(
					"SELECT * from `pvp` WHERE `nick`='" + morto.getName() + "';");
					
					if (rs2.next()) {
						Main.getMysql().conectar().createStatement().executeUpdate(
						"UPDATE `pvp` SET `death`='" + String.valueOf(rs.getInt("death") + 1 
								+ "' WHERE `nick`='" + morto.getName() + "';"));
					}
					rs2.getStatement().getConnection().close();
					
				} catch (SQLException e) {
				}
				
			}
		}.runTaskAsynchronously(Main.pl);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage("§8[§c-§8] §7" + p.getName() + " §7saiu do servidor!");
	}
	
	@EventHandler
	public void onChuva(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void kabumCREEPER(BlockExplodeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void kabumTNT(EntityExplodeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	void onDieRespawn(PlayerDeathEvent e) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (e.getEntity().getPlayer() instanceof Player) {
					e.getEntity().getPlayer().spigot().respawn();
				}
			}
		}.runTask(Main.pl);
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

}
