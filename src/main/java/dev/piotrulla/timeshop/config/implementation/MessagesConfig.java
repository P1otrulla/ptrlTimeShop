package dev.piotrulla.timeshop.config.implementation;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Sound;

public class MessagesConfig extends OkaeriConfig {

    public Notice receivedCurrency = BukkitNotice.builder()
            .actionBar("&3Otrzymano &b5 monet czasu &3za &b5 minut gry!")
            .sound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f)
            .build();

}
