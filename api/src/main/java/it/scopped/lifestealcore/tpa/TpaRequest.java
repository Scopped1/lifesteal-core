package it.scopped.lifestealcore.tpa;

import org.bukkit.entity.Player;

public record TpaRequest(
        Player sender,
        Player receiver,
        RequestType requestType
) {

    public void compute() {
        if (requestType == RequestType.HERE) {
            receiver.teleport(sender);
        } else {
            sender.teleport(receiver);
        }
    }

    public enum RequestType {
        NORMAL, HERE
    }

}
