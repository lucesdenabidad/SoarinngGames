package datta.core.paper.oneblock.system;

import datta.core.paper.oneblock.OneBlock;
import datta.core.paper.utilities.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import static datta.core.paper.utilities.Color.format;
import static datta.core.paper.utilities.Color.stringToLocation;
import static datta.core.paper.utilities.Utils.send;

public class OneBlockEvents implements Listener {


    // Anti break glass
    @EventHandler
    public void breakGlass(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material type = block.getType();

        if (type.toString().contains("GLASS") || type.toString().contains("DEEPSLATE")) {
            event.setCancelled(true);
        }
    }

    static boolean b = false;
    @EventHandler
    public void move(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (b) {

            if (Utils.playerInZone(player, stringToLocation("-250 -63 -250"), stringToLocation("250 -70 250"))) {
                OneBlock playerTeam = OneBlockManager.getPlayerTeam(player);
                Location add = playerTeam.getBlockLocation().clone().add(0, 3, 0).toCenterLocation();
                add.getWorld().spawnParticle(Particle.CLOUD, add, 50, 1, 0, 1, 0);
                player.teleport(add);
                player.setFallDistance(0);

            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        int lives = OneBlockManager.livesMap.getOrDefault(player, 1);
        if (lives != 1) {
            int newLives = lives - 1;
            OneBlockManager.livesMap.put(player, newLives);
        } else {

            @Nullable ItemStack[] contents = player.getInventory().getContents();
            for (ItemStack i : contents){
                if (i != null && i.getType() != Material.AIR) {
                    player.getWorld().dropItem(player.getLocation().toCenterLocation(), i);
                }
            }

            player.getInventory().clear();
            player.kickPlayer(format("&c¡SIN VIDAS, GRACIAS POR JUGAR!"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void damageTeam(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player target) {
            OneBlock playerTeam = OneBlockManager.getPlayerTeam(damager);
            OneBlock targetTeam = OneBlockManager.getPlayerTeam(target);
            if (playerTeam != null && targetTeam != null) {
                if (playerTeam.getName().equalsIgnoreCase(targetTeam.getName())) {
                    send(damager, "&cNo puedes hacer daños a tus compañeros.");
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        OneBlock playerTeam = OneBlockManager.getPlayerTeam(player);
        if (playerTeam != null){
            Location blockLocation = playerTeam.getBlockLocation().toCenterLocation().clone().add(0,1,0);
            event.setRespawnLocation(blockLocation);
        }
    }
}