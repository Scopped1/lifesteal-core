package it.scopped.lifestealcore.providers.messages;

import it.scopped.lifestealcore.LifestealCore;
import it.scopped.lifestealcore.utility.StringsUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;

public class MessagesProviderImpl implements MessagesProvider {

    private final LifestealCore plugin;

    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public MessagesProviderImpl(LifestealCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public Component translate(String message, Object... params) {
        Component legacy = LEGACY_COMPONENT_SERIALIZER.deserialize(StringsUtil.replace(message, params));
        String miniMessage = MINI_MESSAGE.serialize(legacy);

        return MINI_MESSAGE.deserialize(miniMessage);
    }

    @Override
    public List<Component> translate(List<String> messages, Object... params) {
        return messages.stream().map(message -> translate(message, params)).toList();
    }

    @Override
    public void send(Player player, String message, Object... params) {
        player.sendMessage(translate(player, message, params));
    }

    @Override
    public void actionBar(Player player, String message, Object... params) {
        player.sendActionBar(translate(player, message, params));
    }

    @Override
    public void title(Player player, String title, String subTitle, Object... params) {
        player.showTitle(
                Title.title(
                        translate(player, title, params),
                        translate(player, subTitle, params),
                        Title.Times.times(
                                Duration.ofSeconds(2),
                                Duration.ofSeconds(3),
                                Duration.ofSeconds(2)
                        )
                )
        );
    }

    @Override
    public void broadcast(String message, Object... params) {
        for (Player onlinePlayer : plugin.server().getOnlinePlayers()) {
            send(onlinePlayer, message, params);
        }
    }
}
