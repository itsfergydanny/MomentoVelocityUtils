package com.dnyferguson.momentovelocityutils.utils;

import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;

public class Chat {
    public static TextComponent combine(TextComponent... textComponents) {
        TextComponent.Builder builder = TextComponent.of("").toBuilder();
        for (TextComponent textComponent : textComponents) {
            builder.append(textComponent);
        }

        return builder.build();
    }

    public static TextComponent black(String msg) {
        return TextComponent.of(msg).color(TextColor.BLACK);
    }

    public static TextComponent dark_blue(String msg) {
        return TextComponent.of(msg).color(TextColor.DARK_BLUE);
    }

    public static TextComponent dark_green(String msg) {
        return TextComponent.of(msg).color(TextColor.DARK_GREEN);
    }

    public static TextComponent dark_aqua(String msg) {
        return TextComponent.of(msg).color(TextColor.DARK_AQUA);
    }

    public static TextComponent dark_red(String msg) {
        return TextComponent.of(msg).color(TextColor.DARK_RED);
    }

    public static TextComponent dark_purple(String msg) {
        return TextComponent.of(msg).color(TextColor.DARK_PURPLE);
    }

    public static TextComponent gold(String msg) {
        return TextComponent.of(msg).color(TextColor.GOLD);
    }

    public static TextComponent gray(String msg) {
        return TextComponent.of(msg).color(TextColor.GRAY);
    }

    public static TextComponent dark_gray(String msg) {
        return TextComponent.of(msg).color(TextColor.DARK_GRAY);
    }

    public static TextComponent blue(String msg) {
        return TextComponent.of(msg).color(TextColor.BLUE);
    }

    public static TextComponent green(String msg) {
        return TextComponent.of(msg).color(TextColor.GREEN);
    }

    public static TextComponent aqua(String msg) {
        return TextComponent.of(msg).color(TextColor.AQUA);
    }

    public static TextComponent red(String msg) {
        return TextComponent.of(msg).color(TextColor.RED);
    }

    public static TextComponent light_purple(String msg) {
        return TextComponent.of(msg).color(TextColor.LIGHT_PURPLE);
    }

    public static TextComponent yellow(String msg) {
        return TextComponent.of(msg).color(TextColor.YELLOW);
    }

    public static TextComponent white(String msg) {
        return TextComponent.of(msg).color(TextColor.WHITE);
    }

    public static TextComponent bold(TextComponent component) {
        return component.decoration(TextDecoration.BOLD, true);
    }

    public static TextComponent italic(TextComponent component) {
        return component.decoration(TextDecoration.ITALIC, true);
    }

    public static TextComponent underline(TextComponent component) {
        return component.decoration(TextDecoration.UNDERLINED, true);
    }

    public static TextComponent strikethrough(TextComponent component) {
        return component.decoration(TextDecoration.STRIKETHROUGH, true);
    }

    public static TextComponent obfuscated(TextComponent component) {
        return component.decoration(TextDecoration.OBFUSCATED, true);
    }
}
