package lee.code.essentials;

import lee.code.essentials.commands.cmds.*;
import lee.code.essentials.commands.tabs.*;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.files.CustomFile;
import lee.code.essentials.files.FileManager;
import lee.code.essentials.files.defaults.*;
import lee.code.essentials.listeners.ChatListener;
import lee.code.essentials.listeners.EntityListener;
import lee.code.essentials.listeners.JoinListener;
import lee.code.essentials.listeners.TameListener;
import lee.code.essentials.permissions.RegisterPermissions;
import lee.code.essentials.tablist.TabManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class TheEssentials extends JavaPlugin {

    @Getter private FileManager fileManager;
    @Getter private PluginUtility pluginUtility;
    @Getter private RegisterPermissions registerPermissions;
    @Getter private Data data;
    @Getter private SQLite sqLite;
    @Getter private TabManager tabManager;

    @Override
    public void onEnable() {
        this.fileManager = new FileManager();
        this.pluginUtility = new PluginUtility();
        this.registerPermissions = new RegisterPermissions();
        this.data = new Data();
        this.sqLite = new SQLite();
        this.tabManager = new TabManager();

        fileManager.addConfig("config");
        fileManager.addConfig("lang");
        sqLite.connect();
        sqLite.loadTables();
        data.loadWorldNames();
        tabManager.loadTab();

        loadDefaults();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        sqLite.disconnect();
    }

    private void registerCommands() {

        //cmds
        getCommand("spawn").setExecutor(new SpawnCMD());
        getCommand("setspawn").setExecutor(new SetSpawnCMD());
        getCommand("gamemode").setExecutor(new GameModeCMD());
        getCommand("fly").setExecutor(new FlyCMD());
        getCommand("flyspeed").setExecutor(new FlySpeedCMD());
        getCommand("world").setExecutor(new WorldCMD());
        getCommand("balance").setExecutor(new BalanceCMD());
        getCommand("balancetop").setExecutor(new BalanceTopCMD());
        getCommand("money").setExecutor(new MoneyCMD());
        getCommand("invsee").setExecutor(new InvseeCMD());
        getCommand("color").setExecutor(new ColorCMD());

        //tabs
        getCommand("spawn").setTabCompleter(new SpawnTab());
        getCommand("setspawn").setTabCompleter(new SetSpawnTab());
        getCommand("gamemode").setTabCompleter(new GameModeTab());
        getCommand("fly").setTabCompleter(new FlyTab());
        getCommand("flyspeed").setTabCompleter(new FlySpeedTab());
        getCommand("world").setTabCompleter(new WorldTab());
        getCommand("balance").setTabCompleter(new BalanceTab());
        getCommand("balanceTop").setTabCompleter(new BalanceTopTab());
        getCommand("money").setTabCompleter(new MoneyTab());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new TameListener(), this);
    }


    public void loadDefaults() {

        //config
        Config.setFile(fileManager.getConfig("config").getData());
        for (Config value : Config.values()) fileManager.getConfig("config").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("config").getData().options().copyDefaults(true);
        fileManager.getConfig("config").save();

        //config settings
        BooleanValues.setFile(fileManager.getConfig("config").getData());
        for (BooleanValues value : BooleanValues.values()) fileManager.getConfig("config").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("config").getData().options().copyDefaults(true);
        fileManager.getConfig("config").save();

        //config int values
        IntegerValues.setFile(fileManager.getConfig("config").getData());
        for (IntegerValues value : IntegerValues.values()) fileManager.getConfig("config").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("config").getData().options().copyDefaults(true);
        fileManager.getConfig("config").save();

        //config double values
        DoubleValues.setFile(fileManager.getConfig("config").getData());
        for (IntegerValues value : IntegerValues.values()) fileManager.getConfig("config").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("config").getData().options().copyDefaults(true);
        fileManager.getConfig("config").save();

        //lang
        Lang.setFile(fileManager.getConfig("lang").getData());
        for (Lang value : Lang.values()) fileManager.getConfig("lang").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("lang").getData().options().copyDefaults(true);
        fileManager.getConfig("lang").save();
    }

    public void saveFile(String file) {
        fileManager.getConfig(file).save();
    }

    public CustomFile getFile(String file) {
        return fileManager.getConfig(file);
    }

    public void reloadFiles() {
        fileManager.reloadAll();
    }

    public static TheEssentials getPlugin() {
        return TheEssentials.getPlugin(TheEssentials.class);
    }
}
