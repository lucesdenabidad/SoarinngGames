package datta.core.paper.oneblock;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OneBlock {

    @Getter String name;
    @Getter ChatColor color;
    @Getter List<Player> members;
    @Getter Location blockLocation;

    public OneBlock(String name, ChatColor color, Location blockLocation) {
        this.name = name;
        this.color = color;
        this.members = new ArrayList<>();
        this.blockLocation = blockLocation;
    }

    public void add(Player player){
        members.add(player);
    }
}
