package it.scopped.lifestealcore.providers.messages;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public interface MessagesProvider {

    Component translate(String message, Object... params);

    List<Component> translate(List<String> messages, Object... params);

    default Component translate(Player player, String message, Object... params) {
        return translate(PlaceholderAPI.setPlaceholders(player, message), params);
    }

    default List<Component> translate(Player player, List<String> messages, Object... params) {
        return translate(PlaceholderAPI.setPlaceholders(player, messages), params);
    }

    void send(Player player, String message, Object... params);

    void actionBar(Player player, String message, Object... params);

    void title(Player player, String title, String subTitle, Object... params);

    void broadcast(String message, Object... params);

}
