package datta.core.paper.stage;

import datta.core.paper.Core;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import static datta.core.paper.utilities.Color.format;
import static datta.core.paper.utilities.Utils.send;

public class StageManager implements Listener {

    @Getter
    @Setter
    public static boolean buildBoolean = false;

    @Getter
    @Setter
    public static boolean breakBoolean = false;

    @Getter
    @Setter
    public static boolean pvpBoolean = false;

    @Getter
    @Setter
    public static boolean moveBoolean = true;


    @Getter
    @Setter
    public static boolean opBypass = true;

    private final Plugin plugin;

    public StageManager(Core plugin) {
        this.plugin = plugin;
        plugin.register(new StageCommand());
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (opBypass && player.isOp()) {
            return; // Si opBypass es true y el jugador es op, no se cancela
        }
        if (!buildBoolean) {
            event.setCancelled(true);
            player.sendMessage(format("&cLa colocación de bloques está desactivada en este momento."));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (opBypass && player.isOp()) {
            return; // Si opBypass es true y el jugador es op, no se cancela
        }
        if (!breakBoolean) {
            event.setCancelled(true);
            player.sendMessage(format("&cLa destrucción de bloques está desactivada en este momento."));
        }
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (opBypass && player.isOp()) {
            return;
        }
        if (!moveBoolean) {
            event.setCancelled(true);
            player.sendMessage(format("&cEspera las instrucciones del administrador."));
        }
    }


    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player target) {
            if (!pvpBoolean) {
                send(damager, "&cEl 'pvp' está desactivado.");
                event.setCancelled(true);
            }
        }
    }
}
