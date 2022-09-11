package me.gasthiiml.pearls.listeners;

import me.gasthiiml.pearls.Main;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GeneralListener implements Listener {

    private final Map<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getItem().getType() == Material.ENDER_PEARL) {
            System.out.println(cooldown);

            if(cooldown.containsKey(event.getPlayer().getUniqueId())) {
                long time = cooldown.get(event.getPlayer().getUniqueId());

                if(time < System.currentTimeMillis()) {
                    cooldown.put(event.getPlayer().getUniqueId(), (System.currentTimeMillis() + (Main.getInstance().getConfig().getInt("pearl-cooldown", 2) * 1000L)));
                } else {
                    event.setCancelled(true);
                }
            } else {
                cooldown.put(event.getPlayer().getUniqueId(), (System.currentTimeMillis() + (Main.getInstance().getConfig().getInt("pearl-cooldown", 2) * 1000L)));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPearlDamage(PlayerTeleportEvent event){
        Player p = event.getPlayer();

        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            int damage = Main.getInstance().getConfig().getInt("pearl-damage", 2);

            if(damage == 0)
                p.setNoDamageTicks(1);
            else
                p.damage(damage);

            event.setCancelled(true);
            p.teleport(event.getTo());
        }
    }

}
