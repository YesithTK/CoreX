package dev.yesithx.proxycore.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.yesithx.proxycore.velocity.command.*;
import dev.yesithx.proxycore.velocity.listener.PlayerListener;
import dev.yesithx.proxycore.velocity.manager.AnnounceManager;
import dev.yesithx.proxycore.velocity.manager.MaintenanceManager;
import dev.yesithx.proxycore.velocity.manager.MessageManager;
import dev.yesithx.proxycore.velocity.util.ConfigManager;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "proxycore",
        name = "ProxyCore",
        version = "1.0.0",
        description = "All-in-one proxy utilities for Velocity networks",
        authors = {"YesithTK"}
)
public class ProxyCoreVelocity {

    private static ProxyCoreVelocity instance;
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    private ConfigManager configManager;
    private AnnounceManager announceManager;
    private MessageManager messageManager;
    private MaintenanceManager maintenanceManager;

    @Inject
    public ProxyCoreVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        instance = this;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        configManager = new ConfigManager(this);
        configManager.load();

        messageManager = new MessageManager(this);
        maintenanceManager = new MaintenanceManager(this);
        announceManager = new AnnounceManager(this);

        registerCommands();
        server.getEventManager().register(this, new PlayerListener(this));

        announceManager.startScheduler();
        logger.info("ProxyCore enabled! by YesithTK");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        announceManager.stopScheduler();
        logger.info("ProxyCore disabled.");
    }

    private void registerCommands() {
        var cm = server.getCommandManager();
        cm.register(cm.metaBuilder("find").aliases("vfind").plugin(this).build(), new FindCommand(this));
        cm.register(cm.metaBuilder("vglist").plugin(this).build(), new GlistCommand(this));
        cm.register(cm.metaBuilder("vnetwork").plugin(this).build(), new NetworkCommand(this));
        cm.register(cm.metaBuilder("vping").plugin(this).build(), new PingCommand(this));
        cm.register(cm.metaBuilder("vip").plugin(this).build(), new IPCommand(this));
        cm.register(cm.metaBuilder("vannounce").aliases("vann").plugin(this).build(), new AnnounceCommand(this));
        cm.register(cm.metaBuilder("vstaffchat").aliases("vsc").plugin(this).build(), new StaffChatCommand(this));
        cm.register(cm.metaBuilder("vmsg").aliases("vtell").plugin(this).build(), new MsgCommand(this));
        cm.register(cm.metaBuilder("vreply").aliases("vr").plugin(this).build(), new ReplyCommand(this));
        cm.register(cm.metaBuilder("vbroadcast").aliases("vbc").plugin(this).build(), new BroadcastCommand(this));
        cm.register(cm.metaBuilder("vsend").plugin(this).build(), new SendCommand(this));
        cm.register(cm.metaBuilder("vsendall").plugin(this).build(), new SendAllCommand(this));
        cm.register(cm.metaBuilder("vkick").plugin(this).build(), new KickCommand(this));
        cm.register(cm.metaBuilder("vstafflist").aliases("vsl").plugin(this).build(), new StaffListCommand(this));
        cm.register(cm.metaBuilder("vsudo").plugin(this).build(), new SudoCommand(this));
        cm.register(cm.metaBuilder("vmaintenance").aliases("vmaint").plugin(this).build(), new MaintenanceCommand(this));
        cm.register(cm.metaBuilder("valert").plugin(this).build(), new AlertCommand(this));
        cm.register(cm.metaBuilder("vplist").plugin(this).build(), new PListCommand(this));
        cm.register(cm.metaBuilder("vproxyreload").aliases("vpcreload").plugin(this).build(), new ProxyReloadCommand(this));
        cm.register(cm.metaBuilder("vuptime").plugin(this).build(), new UptimeCommand(this));
    }

    public void reload() {
        configManager.load();
        announceManager.reload();
    }

    public static ProxyCoreVelocity getInstance() { return instance; }
    public ProxyServer getServer() { return server; }
    public Logger getLogger() { return logger; }
    public Path getDataDirectory() { return dataDirectory; }
    public ConfigManager getConfigManager() { return configManager; }
    public AnnounceManager getAnnounceManager() { return announceManager; }
    public MessageManager getMessageManager() { return messageManager; }
    public MaintenanceManager getMaintenanceManager() { return maintenanceManager; }
                                   }
          
