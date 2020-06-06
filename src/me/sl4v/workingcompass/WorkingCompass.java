package me.sl4v.workingcompass;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkingCompass extends JavaPlugin implements Listener, CommandExecutor
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("Compass", "dummy", "Compass");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        e.getPlayer().setScoreboard(board);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e)
    {
        // e.getPlayer().getScoreboard().getObjective("Compass").getScore("Degrees").setScore(getPlayerDegrees(e.getPlayer()));
        Player player = e.getPlayer();
        Objective compass = player.getScoreboard().getObjective("Compass");
        int playerDegrees = getPlayerDegrees(player);
        compass.getScore("Degrees").setScore(playerDegrees);;
        String compassDisplayName = cardinalDirections(playerDegrees);
        compass.setDisplayName(compassDisplayName);
    }

    private String cardinalDirections(int x)
    {
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        return directions[ (int)Math.round((  ((double)x % 360) / 45)) % 8 ];
    }

    private int getPlayerDegrees(Player player)
    {
        int degrees = (Math.round(player.getLocation().getYaw()) + 270) % 360;
        degrees = degrees - 90;
        if (0 > degrees)
        {
            degrees += 360;
        }
        return degrees;
    }

}
