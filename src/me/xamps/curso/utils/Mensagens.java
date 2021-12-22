package me.xamps.curso.utils;

import me.xamps.curso.Main;

public class Mensagens {
	
	public static String cor(String s) {
		return s.replace("&", "§");
	}
	
	public static String noPerm = cor(Main.pl.getConfig().getString("Mensagens.Sem_Permissao"));
	public static String offlinePlayer = cor(Main.pl.getConfig().getString("Mensagens.Jogador_OFF"));
	public static String consolePlayer = cor(Main.pl.getConfig().getString("Mensagens.Console"));

}
