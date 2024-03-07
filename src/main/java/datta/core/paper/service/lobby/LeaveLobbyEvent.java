package datta.core.paper.service.lobby;

import datta.core.paper.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static datta.core.paper.utilities.Color.stringToLocation;
import static datta.core.paper.utilities.Utils.playerInZone;

public class LeaveLobbyEvent implements Listener {

    @EventHandler
    public void move(PlayerMoveEvent event){

        Player player = event.getPlayer();
        String pos1 = "-111 221 86";
        String pos2 = "87 143 -102";

        if (playerInZone(player,stringToLocation(pos1),
                stringToLocation(pos2))){
            player.teleport(Core.getInstance().spawn);
        }
    }
}
