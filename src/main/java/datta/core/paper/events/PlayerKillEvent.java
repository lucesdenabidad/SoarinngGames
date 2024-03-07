package datta.core.paper.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static datta.core.paper.oneblock.system.OneBlockManager.killMap;

public class PlayerKillEvent implements Listener {

    @EventHandler
    public void death(PlayerDeathEvent event){
        Player player = event.getPlayer();
        if (player.getKiller() != null){
            Player killer = player.getKiller();
            Integer orDefault = killMap.getOrDefault(killer, 0);
            killMap.put(killer, orDefault + 1);

        }
    }
}
