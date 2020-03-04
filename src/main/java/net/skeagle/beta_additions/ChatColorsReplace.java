package net.skeagle.beta_additions;

import org.bukkit.ChatColor;

public enum ChatColorsReplace {
    BLACK(ChatColor.BLACK, '0'),
    DARK_BLUE(ChatColor.DARK_BLUE, '1'),
    DARK_GREEN(ChatColor.DARK_GREEN, '2'),
    DARK_AQUA(ChatColor.DARK_AQUA, '3'),
    DARK_RED(ChatColor.DARK_RED, '4'),
    DARK_PURPLE(ChatColor.DARK_PURPLE, '5'),
    GOLD(ChatColor.GOLD, '6'),
    GRAY(ChatColor.GRAY, '7'),
    DARK_GRAY(ChatColor.DARK_GRAY, '8'),
    BLUE(ChatColor.BLUE, '9'),
    GREEN(ChatColor.GREEN, 'a'),
    AQUA(ChatColor.AQUA, 'b'),
    RED(ChatColor.RED, 'c'),
    PURPLE(ChatColor.LIGHT_PURPLE, 'd'),
    YELLOW(ChatColor.YELLOW, 'e'),
    WHITE(ChatColor.WHITE, 'f');

    ChatColor color;
    char code;

    ChatColorsReplace(ChatColor color, char code) {
        this.color = color;
        this.code = code;
    }

    public ChatColor getColor() {
        return this.color;
    }
    public char getCode() {
        return this.code;
    }



}
