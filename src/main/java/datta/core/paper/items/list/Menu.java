package datta.core.paper.items.list;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.items.CustomItem;
import datta.core.paper.service.gamemenu.MainMenu;
import datta.core.paper.utilities.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static datta.core.paper.utilities.Utils.send;

public class Menu extends CustomItem {
    @Override
    public ItemStack itemStack() {
        return new ItemBuilder(Material.PLAYER_HEAD, "&aMenú de SGC",
                "&fAbre el menu principal de SGC").buildPlayerHead("Soarinng");
    }

    @CommandPermission("core.menuitem")
    @CommandAlias("menuitem")
    @Override
    public void handleCommand(Player player, String[] args) {
        player.getInventory().addItem(itemStack());
        send(player, "&a&lEvento &8> &fRecibiste el &e'Menuitem'&f en tu inventario.");
    }

    @EventHandler
    public void handleEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.hasItem()){
            ItemStack item = event.getItem();
            if (item.isSimilar(itemStack())){
                new MainMenu().runMenu(player);
            }
        }
    }
}