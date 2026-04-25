package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;
import net.kyori.adventure.text.Component;

import java.lang.management.ManagementFactory;
import java.util.Optional;

// ─────────────────────────────────────────────────────────────
//  FindCommand
// ─────────────────────────────────────────────────────────────
class FindCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    FindCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        String[] args = inv.arguments();
        if (args.length < 1) { src.sendMessage(ColorUtil.color("&cUsage: /find <player>")); return; }
        Optional<Player> target = plugin.getServer().getPlayer(args[0]);
        if (target.isEmpty()) { src.sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found","&cPlayer not found.").replace("%player%",args[0]))); return; }
        String server = target.get().getCurrentServer().map(s->s.getServerInfo().getName()).orElse("unknown");
        src.sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("find-result","&a%player% &7is on server &e%server%").replace("%player%",target.get().getUsername()).replace("%server%",server)));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.find"); }
}

// ─────────────────────────────────────────────────────────────
//  GlistCommand
// ─────────────────────────────────────────────────────────────
class GlistCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    GlistCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        src.sendMessage(ColorUtil.color("&8&m-----&r &6&lNetwork List &8&m-----"));
        for (RegisteredServer rs : plugin.getServer().getAllServers()) {
            int count = rs.getPlayersConnected().size();
            String status = count > 0 ? "&a● " : "&c● ";
            src.sendMessage(ColorUtil.color(" " + status + "&e" + rs.getServerInfo().getName() + " &7- &f" + count + " players"));
        }
        src.sendMessage(ColorUtil.color("&7Total: &f" + plugin.getServer().getPlayerCount() + " players"));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.glist"); }
}

// ─────────────────────────────────────────────────────────────
//  NetworkCommand
// ─────────────────────────────────────────────────────────────
class NetworkCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    NetworkCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
        long h = uptime/3600, m = (uptime%3600)/60;
        src.sendMessage(ColorUtil.color("&8&m-----&r &b&lNetwork Stats &8&m-----"));
        src.sendMessage(ColorUtil.color(" &7Players: &f" + plugin.getServer().getPlayerCount()));
        src.sendMessage(ColorUtil.color(" &7Servers: &f" + plugin.getServer().getAllServers().size()));
        src.sendMessage(ColorUtil.color(" &7Uptime: &f" + h + "h " + m + "m"));
        src.sendMessage(ColorUtil.color(" &7Maintenance: " + (plugin.getMaintenanceManager().isEnabled() ? "&cEnabled" : "&aDisabled")));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.network"); }
}

// ─────────────────────────────────────────────────────────────
//  PingCommand
// ─────────────────────────────────────────────────────────────
class PingCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    PingCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        Player target;
        if (inv.arguments().length >= 1) {
            if (!src.hasPermission("proxycore.ping.others")) { src.sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("no-permission","&cNo permission."))); return; }
            Optional<Player> opt = plugin.getServer().getPlayer(inv.arguments()[0]);
            if (opt.isEmpty()) { src.sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found","&cPlayer not found.").replace("%player%",inv.arguments()[0]))); return; }
            target = opt.get();
        } else {
            if (!(src instanceof Player)) { src.sendMessage(ColorUtil.color("&cOnly players can use this without arguments.")); return; }
            target = (Player) src;
        }
        long ping = target.getPing();
        String q = ping < 50 ? "&a✅ Excellent" : ping < 100 ? "&2✅ Good" : ping < 200 ? "&e⚡ Average" : ping < 350 ? "&6⚠ Poor" : "&c💀 Very Poor";
        src.sendMessage(ColorUtil.color("&7" + target.getUsername() + "'s ping: " + q + " &7(" + ping + "ms)"));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.ping"); }
}

// ─────────────────────────────────────────────────────────────
//  IPCommand
// ─────────────────────────────────────────────────────────────
class IPCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    IPCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        if (inv.arguments().length < 1) { src.sendMessage(ColorUtil.color("&cUsage: /vip <player>")); return; }
        Optional<Player> opt = plugin.getServer().getPlayer(inv.arguments()[0]);
        if (opt.isEmpty()) { src.sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found","&cPlayer not found.").replace("%player%",inv.arguments()[0]))); return; }
        String ip = opt.get().getRemoteAddress().getAddress().getHostAddress();
        src.sendMessage(ColorUtil.color("&7" + opt.get().getUsername() + "'s IP: &e" + ip));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.ip"); }
}

// ─────────────────────────────────────────────────────────────
//  AnnounceCommand
// ─────────────────────────────────────────────────────────────
class AnnounceCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    AnnounceCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        if (inv.arguments().length < 1) { inv.source().sendMessage(ColorUtil.color("&cUsage: /vannounce <message>")); return; }
        String msg = String.join(" ", inv.arguments());
        String prefix = plugin.getConfigManager().getMessage("announce-prefix","&8[&6Announce&8] &r");
        plugin.getServer().getAllPlayers().forEach(p -> p.sendMessage(ColorUtil.color(prefix + msg)));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.announce"); }
}

// ─────────────────────────────────────────────────────────────
//  StaffChatCommand
// ─────────────────────────────────────────────────────────────
class StaffChatCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    StaffChatCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        if (inv.arguments().length < 1) { src.sendMessage(ColorUtil.color("&cUsage: /vstaffchat <message>")); return; }
        String name = src instanceof Player ? ((Player)src).getUsername() : "Console";
        String msg = String.join(" ", inv.arguments());
        Component formatted = ColorUtil.color("&8[&cStaff&8] &7" + name + ": &f" + msg);
        plugin.getServer().getAllPlayers().stream().filter(p -> p.hasPermission("proxycore.staffchat")).forEach(p -> p.sendMessage(formatted));
        plugin.getLogger().info("[StaffChat] " + name + ": " + msg);
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.staffchat"); }
}

// ─────────────────────────────────────────────────────────────
//  MsgCommand
// ─────────────────────────────────────────────────────────────
class MsgCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    MsgCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        String[] args = inv.arguments();
        if (args.length < 2) { src.sendMessage(ColorUtil.color("&cUsage: /vmsg <player> <message>")); return; }
        Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
        if (opt.isEmpty()) { src.sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found","&cPlayer not found.").replace("%player%",args[0]))); return; }
        Player target = opt.get();
        String senderName = src instanceof Player ? ((Player)src).getUsername() : "Console";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) sb.append(args[i]).append(" ");
        String msg = sb.toString().trim();
        src.sendMessage(ColorUtil.color("&7[&fYou &7→ &f" + target.getUsername() + "&7] &f" + msg));
        target.sendMessage(ColorUtil.color("&7[&f" + senderName + " &7→ &fYou&7] &f" + msg));
        if (src instanceof Player) plugin.getMessageManager().setLastMessaged(((Player)src).getUniqueId(), target.getUniqueId());
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.msg"); }
}

// ─────────────────────────────────────────────────────────────
//  ReplyCommand
// ─────────────────────────────────────────────────────────────
class ReplyCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    ReplyCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        if (!(src instanceof Player)) { src.sendMessage(ColorUtil.color("&cOnly players can use /vreply.")); return; }
        if (inv.arguments().length < 1) { src.sendMessage(ColorUtil.color("&cUsage: /vreply <message>")); return; }
        Player player = (Player) src;
        Optional<Player> targetOpt = plugin.getMessageManager().getLastMessaged(player.getUniqueId());
        if (targetOpt.isEmpty()) { src.sendMessage(ColorUtil.color("&cYou have no one to reply to.")); return; }
        Player target = targetOpt.get();
        String msg = String.join(" ", inv.arguments());
        player.sendMessage(ColorUtil.color("&7[&fYou &7→ &f" + target.getUsername() + "&7] &f" + msg));
        target.sendMessage(ColorUtil.color("&7[&f" + player.getUsername() + " &7→ &fYou&7] &f" + msg));
        plugin.getMessageManager().setLastMessaged(player.getUniqueId(), target.getUniqueId());
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.msg"); }
}

// ─────────────────────────────────────────────────────────────
//  BroadcastCommand
// ─────────────────────────────────────────────────────────────
class BroadcastCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    BroadcastCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        if (inv.arguments().length < 1) { inv.source().sendMessage(ColorUtil.color("&cUsage: /vbroadcast <message>")); return; }
        String prefix = plugin.getConfigManager().getMessage("broadcast-prefix","&8[&4Broadcast&8] &r");
        Component msg = ColorUtil.color(prefix + String.join(" ", inv.arguments()));
        plugin.getServer().getAllPlayers().forEach(p -> p.sendMessage(msg));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.broadcast"); }
}

// ─────────────────────────────────────────────────────────────
//  SendCommand
// ─────────────────────────────────────────────────────────────
class SendCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    SendCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        String[] args = inv.arguments();
        if (args.length < 2) { src.sendMessage(ColorUtil.color("&cUsage: /vsend <player> <server>")); return; }
        Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
        if (opt.isEmpty()) { src.sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found","&cPlayer not found.").replace("%player%",args[0]))); return; }
        Optional<RegisteredServer> serverOpt = plugin.getServer().getServer(args[1]);
        if (serverOpt.isEmpty()) { src.sendMessage(ColorUtil.color("&cServer &e" + args[1] + " &cnot found.")); return; }
        opt.get().createConnectionRequest(serverOpt.get()).fireAndForget();
        src.sendMessage(ColorUtil.color("&aSent &e" + opt.get().getUsername() + " &ato &e" + args[1] + "&a."));
        opt.get().sendMessage(ColorUtil.color("&7You have been moved to &e" + args[1] + "&7."));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.send"); }
}

// ─────────────────────────────────────────────────────────────
//  SendAllCommand
// ─────────────────────────────────────────────────────────────
class SendAllCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    SendAllCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        if (inv.arguments().length < 1) { src.sendMessage(ColorUtil.color("&cUsage: /vsendall <server>")); return; }
        Optional<RegisteredServer> serverOpt = plugin.getServer().getServer(inv.arguments()[0]);
        if (serverOpt.isEmpty()) { src.sendMessage(ColorUtil.color("&cServer not found.")); return; }
        RegisteredServer rs = serverOpt.get();
        plugin.getServer().getAllPlayers().forEach(p -> {
            p.sendMessage(ColorUtil.color("&7All players are being moved to &e" + rs.getServerInfo().getName() + "&7."));
            p.createConnectionRequest(rs).fireAndForget();
        });
        src.sendMessage(ColorUtil.color("&aSent all players to &e" + rs.getServerInfo().getName() + "&a."));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.sendall"); }
}

// ─────────────────────────────────────────────────────────────
//  KickCommand
// ─────────────────────────────────────────────────────────────
class KickCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    KickCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        String[] args = inv.arguments();
        if (args.length < 1) { src.sendMessage(ColorUtil.color("&cUsage: /vkick <player> [reason]")); return; }
        Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
        if (opt.isEmpty()) { src.sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found","&cPlayer not found.").replace("%player%",args[0]))); return; }
        String reason = args.length > 1 ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)) : plugin.getConfigManager().getMessage("default-kick-reason","You have been kicked.");
        opt.get().disconnect(ColorUtil.color(reason));
        src.sendMessage(ColorUtil.color("&aKicked &e" + opt.get().getUsername() + " &afor: &f" + reason));
        plugin.getServer().getAllPlayers().forEach(p -> p.sendMessage(ColorUtil.color("&8[&cKick&8] &e" + opt.get().getUsername() + " &7has been kicked: &f" + reason)));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.kick"); }
}

// ─────────────────────────────────────────────────────────────
//  StaffListCommand
// ─────────────────────────────────────────────────────────────
class StaffListCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    StaffListCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        src.sendMessage(ColorUtil.color("&8&m----&r &c&lStaff Online &8&m----"));
        long count = plugin.getServer().getAllPlayers().stream().filter(p -> p.hasPermission("proxycore.staff")).peek(p -> {
            String server = p.getCurrentServer().map(s -> s.getServerInfo().getName()).orElse("?");
            src.sendMessage(ColorUtil.color(" &c● &e" + p.getUsername() + " &7(" + server + ")"));
        }).count();
        if (count == 0) src.sendMessage(ColorUtil.color(" &7No staff members online."));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.stafflist"); }
}

// ─────────────────────────────────────────────────────────────
//  SudoCommand
// ─────────────────────────────────────────────────────────────
class SudoCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    SudoCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        String[] args = inv.arguments();
        if (args.length < 2) { src.sendMessage(ColorUtil.color("&cUsage: /vsudo <player> <command>")); return; }
        Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
        if (opt.isEmpty()) { src.sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found","&cPlayer not found.").replace("%player%",args[0]))); return; }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) sb.append(args[i]).append(" ");
        String cmd = sb.toString().trim();
        plugin.getServer().getCommandManager().executeAsync(opt.get(), cmd);
        src.sendMessage(ColorUtil.color("&aMade &e" + opt.get().getUsername() + " &arun: &f/" + cmd));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.sudo"); }
}

// ─────────────────────────────────────────────────────────────
//  MaintenanceCommand
// ─────────────────────────────────────────────────────────────
class MaintenanceCmd implements SimpleCommand {
    private final ProxyCoreVelocity plugin;
    MaintenanceCmd(ProxyCoreVelocity p) { plugin = p; }

    @Override
    public void execute(Invocation inv) {
        CommandSource src = inv.source();
        if (inv.arguments().length < 1) { src.sendMessage(ColorUtil.color("&cUsage: /vmaintenance <on|off>")); return; }
        boolean enable = inv.arguments()[0].equalsIgnoreCase("on");
        plugin.getMaintenanceManager().setEnabled(enable);
        if (enable) {
            plugin.getServer().getAllPlayers().stream()
                    .filter(p -> !p.hasPermission("proxycore.maintenance.bypass"))
                    .forEach(p -> p.disconnect(ColorUtil.color(plugin.getMaintenanceManager().getKickMessage())));
        }
        plugin.getServer().getAllPlayers().forEach(p -> p.sendMessage(ColorUtil.color("&8[&6ProxyCore&8] &7Maintenance: " + (enable ? "&cEnabled" : "&aDisabled"))));
        src.sendMessage(ColorUtil.color("&7Maintenance is now " + (enable ? "&cEnabled" : "&aDisabled") + "&7."));
    }
    @Override public boolean hasPermission(Invocation inv) { return inv.source().hasPermission("proxycore.maintenance"); }
}

// ────
