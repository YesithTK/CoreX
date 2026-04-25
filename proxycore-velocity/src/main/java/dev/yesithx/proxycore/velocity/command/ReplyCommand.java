package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Optional;

public final class ReplyCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public ReplyCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        if (!(inv.source() instanceof Player)) {
            inv.source().sendMessage(ColorUtil.color("&cOnly players can use /vreply."));
            return;
        }
        if (inv.arguments().length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vreply <message>"));
            return;
        }
        Player player = (Player) inv.source();
        Optional<Player> targetOpt = plugin.getMessageManager().getLastMessaged(player.getUniqueId());
        if (targetOpt.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color("&cYou have no one to reply to."));
            return;
        }
        Player target = targetOpt.get();
        String msg = String.join(" ", inv.arguments());
        player.sendMessage(ColorUtil.color("&7[&fYou &7→ &f" + target.getUsername() + "&7] &f" + msg));
        target.sendMessage(ColorUtil.color("&7[&f" + player.getUsername() + " &7→ &fYou&7] &f" + msg));
        plugin.getMessageManager().setLastMessaged(player.getUniqueId(), target.getUniqueId());
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.msg");
    }
}
