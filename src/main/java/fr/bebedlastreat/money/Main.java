package fr.bebedlastreat.money;

import fr.bebedlastreat.money.commands.MoneyCommand;
import fr.bebedlastreat.money.database.MySQL;
import fr.bebedlastreat.money.listeners.PlayerJoin;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    private static Main instance;

    private BasicDataSource connectionPool;
    private MySQL mysql;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        initConnection();

        getCommand("coins").setExecutor(new MoneyCommand(this));
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoin(this), this);

        System.out.println("[" + getDescription().getName() + "] enable");
        super.onEnable();
    }

    private void initConnection(){
        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
        connectionPool.setUsername(getConfig().getString("sql.user"));
        connectionPool.setPassword(getConfig().getString("sql.password"));
        connectionPool.setUrl("jdbc:mysql://" + getConfig().getString("sql.host") + ":" + getConfig().getString("sql.port") + "/" + getConfig().getString("sql.database") + "?autoReconnect=true");
        connectionPool.setInitialSize(1);
        connectionPool.setMaxTotal(10);
        mysql = new MySQL(connectionPool);
        mysql.createTables();
    }

    public static Main getInstance() {
        return instance;
    }

    public MySQL getMySQL() {
        return mysql;
    }
}
