package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgCommand extends Command {

    private final ProxyCoreBungee plugin;

    public MsgCommand(ProxyCoreBungee plugin) {
        super("pmsg", "proxycore.msg", "pm", "tell", "w");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ColorUtil.color("&cUsage: /pmsg <player> <message>"));
            return;
        }
        ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color(plugin.getMessages().getString("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }

        String senderName = (sender instanceof ProxiedPlayer) ? sender.getName() : "Console";
        StringBuilder msg = new StringBuilder();
        for (int i = 1; i < args.length; i++) msg.append(args[i]).append(" ");

        String toSender = ColorUtil.color("&7[&fYou &7→ &f" + target.getName() + "&7] &f" + msg.toString().trim());
        String toTarget = ColorUtil.color("&7[&f" + senderName + " &7→ &fYou&7] &f" + msg.toString().trim());

        sender.sendMessage(toSender);
        target.sendMessage(toTarget);

        if (sender instanceof ProxiedPlayer) {
            plugin.getMessageManager().setLastMessaged(((ProxiedPlayer) sender).getUniqueId(), target.getUniqueId());
        }
    }
}
