package fr.bebedlastreat.money.listeners;

import fr.bebedlastreat.money.Main;
import fr.bebedlastreat.money.database.Money;
import fr.bebedlastreat.money.utils.PlayerInfos;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {
    private Main main;
    public PlayerJoin(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            UUID uuid = PlayerInfos.getUuidFromName(player.getName());
            Money.setup(uuid);
        });
    }
}
