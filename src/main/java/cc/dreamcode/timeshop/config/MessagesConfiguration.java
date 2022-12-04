package cc.dreamcode.timeshop.config;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public final class MessagesConfiguration extends OkaeriConfig {

    @Comment({ "", "# Ustaw wiadomości:" })
    public BukkitNotice notEnoughtCurrency = new BukkitNotice(
            NoticeType.CHAT,
            "&cNie posiadasz wystarczającej ilości monet! &7(Potrzebujesz jeszcze: {MISSING} {PRURAL})"
    );

    public BukkitNotice productBought = new BukkitNotice(
            NoticeType.CHAT,
            "&aKupiłeś produkt &f{PRODUCT}&a za &f{PRICE} &amonet!"
    );

    public BukkitNotice noPermission = new BukkitNotice(
            NoticeType.CHAT,
            "&cNie posiadasz uprawnień do tej komendy! &7({PERMISSION})"
    );

    public BukkitNotice invalidUsage = new BukkitNotice(
            NoticeType.CHAT,
            "&6Poprawne użycie: &c{USAGE}"
    );

    public BukkitNotice invalidUsageHead = new BukkitNotice(
            NoticeType.CHAT,
            "&6Poprawne użycie:"
    );

    public BukkitNotice invalidUsageBody = new BukkitNotice(
            NoticeType.CHAT,
            "&8» &c{USAGE}"
    );

    public BukkitNotice invalidCurrency = new BukkitNotice(
            NoticeType.CHAT,
            "&cPodana ilość monet jest nieprawidłowa! &7(<=0)"
    );

    public BukkitNotice reload = new BukkitNotice(
            NoticeType.CHAT,
            "&aPomyślnie przeładowano konfigurację!"
    );

    public BukkitNotice addCurrency = new BukkitNotice(
            NoticeType.CHAT,
            "&aPomyślnie dodano &f{AMOUNT} &amonet, graczowi: &f{USER}!"
    );

    public BukkitNotice removeCurrency = new BukkitNotice(
            NoticeType.CHAT,
            "&aPomyślnie usunięto &f{AMOUNT} &amonet, graczowi: &f{USER}!"
    );

    public BukkitNotice setCurrency = new BukkitNotice(
            NoticeType.CHAT,
            "&aPomyślnie ustawiono &f{AMOUNT} &amonet, graczowi: &f{USER}!"
    );
}
