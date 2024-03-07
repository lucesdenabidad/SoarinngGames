package datta.core.paper.oneblock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class OneBlockElement {
    private ItemStack itemStack;
    private Material material;
    private Entity entity;
    private EntityType type;

    public OneBlockElement(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public OneBlockElement(Material material) {
        this.material = material;
    }

    public OneBlockElement(Entity entity) {
        this.entity = entity;
    }

    public OneBlockElement(EntityType type) {
        this.type = type;
    }

    public void spawn(Location location) {
        if (itemStack != null) {
            location.getWorld().dropItem(location.clone().add(0,1,0), itemStack);
        } else if (material != null) {
            location.getBlock().setType(material);
        } else if (entity != null) {
            entity.teleport(location);
        } else if (type != null) {
            location.getWorld().spawnEntity(location.clone().add(0,1,0), type);

        }
    }
}