package dev.yesithx.proxycore.bungeecord.util;

import net.md_5.bungee.api.ChatColor;

public class ColorUtil {
    public static String color(String text) {
        if (text == null) return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
