package dev.yesithx.proxycore.bungeecord;

import dev.yesithx.proxycore.bungeecord.command.*;
import dev.yesithx.proxycore.bungeecord.listener.PlayerListener;
import dev.yesithx.proxycore.bungeecord.manager.AnnounceManager;
import dev.yesithx.proxycore.bungeecord.manager.MessageManager;
import dev.yesithx.proxycore.bungeecord.manager.MaintenanceManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;

public class ProxyCoreBungee extends Plugin {

    private static ProxyCoreBungee instance;
    private Configuration config;
    private Configuration messages;
    private AnnounceManager announceManager;
    private MessageManager messageManager;
    private MaintenanceManager maintenanceManager;

    @Override
    public void onEnable() {
        instance = this;
        loadConfigs();

        announceManager = new AnnounceManager(this);
        messageManager = new MessageManager(this);
        maintenanceManager = new MaintenanceManager(this);

        registerCommands();
        registerListeners();

        announceManager.startScheduler();

        getLogger().info("ProxyCore enabled! by YesithTK");
    }

    @Override
    public void onDisable() {
        announceManager.stopScheduler();
        getLogger().info("ProxyCore disabled.");
    }

    private void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new FindCommand(this));
        getProxy().getPluginManager().registerCommand(this, new GlistCommand(this));
        getProxy().getPluginManager().registerCommand(this, new NetworkCommand(this));
        getProxy().getPluginManager().registerCommand(this, new PingCommand(this));
        getProxy().getPluginManager().registerCommand(this, new IPCommand(this));
        getProxy().getPluginManager().registerCommand(this, new AnnounceCommand(this));
        getProxy().getPluginManager().registerCommand(this, new StaffChatCommand(this));
        getProxy().getPluginManager().registerCommand(this, new MsgCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ReplyCommand(this));
        getProxy().getPluginManager().registerCommand(this, new BroadcastCommand(this));
        getProxy().getPluginManager().registerCommand(this, new SendCommand(this));
        getProxy().getPluginManager().registerCommand(this, new SendAllCommand(this));
        getProxy().getPluginManager().registerCommand(this, new KickCommand(this));
        getProxy().getPluginManager().registerCommand(this, new StaffListCommand(this));
        getProxy().getPluginManager().registerCommand(this, new SudoCommand(this));
        getProxy().getPluginManager().registerCommand(this, new MaintenanceCommand(this));
        getProxy().getPluginManager().registerCommand(this, new AlertCommand(this));
        getProxy().getPluginManager().registerCommand(this, new PListCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ProxyReloadCommand(this));
        getProxy().getPluginManager().registerCommand(this, new UptimeCommand(this));
    }

    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
    }

    private void loadConfigs() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        config = loadConfig("config.yml");
        messages = loadConfig("messages.yml");
    }

    public void reloadConfigs() {
        config = loadConfig("config.yml");
        messages = loadConfig("messages.yml");
        announceManager.reload();
    }

    private Configuration loadConfig(String name) {
        File file = new File(getDataFolder(), name);
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream(name)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                getLogger().severe("Could not save " + name + ": " + e.getMessage());
            }
        }
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            getLogger().severe("Could not load " + name + ": " + e.getMessage());
            return null;
        }
    }

    public static ProxyCoreBungee getInstance() { return instance; }
    public Configuration getConfig() { return config; }
    public Configuration getMessages() { return messages; }
    public AnnounceManager getAnnounceManager() { return announceManager; }
    public MessageManager getMessageManager() { return messageManager; }
    public MaintenanceManager getMaintenanceManager() { return maintenanceManager; }
                           }
  
