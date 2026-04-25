package dev.yesithx.proxycore.velocity.manager;

import com.velocitypowered.api.scheduler.ScheduledTask;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AnnounceManager {

    private final ProxyCoreVelocity plugin;
    private int index = 0;
    private ScheduledTask task;

    public AnnounceManager(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    public void startScheduler() {
        boolean enabled = plugin.getConfigManager().getBoolean("announcements.enabled", true);
        if (!enabled) return;

        int interval = plugin.getConfigManager().getInt("announcements.interval", 60);

        task = plugin.getServer().getScheduler()
                .buildTask(plugin, () -> {
                    List<String> messages = plugin.getConfigManager().getStringList("announcements.messages");
                    if (messages == null || messages.isEmpty()) return;
                    if (index >= messages.size()) index = 0;
                    String msg = messages.get(index++);
                    String prefix = plugin.getConfigManager().getMessage("announce-prefix", "&8[&6Announce&8] &r");
                    plugin.getServer().getAllPlayers().forEach(p -> p.sendMessage(ColorUtil.color(prefix + msg)));
                })
                .repeat(interval, TimeUnit.SECONDS)
                .schedule();
    }

    public void stopScheduler() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public void reload() {
        stopScheduler();
        index = 0;
        startScheduler();
    }

    public boolean isRunning() {
        return task != null;
    }

    public List<String> getMessages() {
        return plugin.getConfigManager().getStringList("announcements.messages");
    }

    public int getIntervalSeconds() {
        return plugin.getConfigManager().getInt("announcements.interval", 60);
    }
}
