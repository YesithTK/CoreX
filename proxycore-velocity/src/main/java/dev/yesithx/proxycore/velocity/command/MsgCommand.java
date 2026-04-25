package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Optional;

public final class MsgCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public MsgCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        String[] args = inv.arguments();
        if (args.length < 2) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vmsg <player> <message>"));
            return;
        }
        Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
        if (opt.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }
        Player target = opt.get();
        String senderName = inv.source() instanceof Player ? ((Player) inv.source()).getUsername() : "Console";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) sb.append(args[i]).append(" ");
        String msg = sb.toString().trim();

        inv.source().sendMessage(ColorUtil.color("&7[&fYou &7→ &f" + target.getUsername() + "&7] &f" + msg));
        target.sendMessage(ColorUtil.color("&7[&f" + senderName + " &7→ &fYou&7] &f" + msg));

        if (inv.source() instanceof Player) {
            plugin.getMessageManager().setLastMessaged(((Player) inv.source()).getUniqueId(), target.getUniqueId());
        }
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.msg");
    }
}
