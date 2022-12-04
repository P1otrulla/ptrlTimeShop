package cc.dreamcode.timeshop.command.implementation;

import cc.dreamcode.timeshop.command.CommandConfiguration;
import cc.dreamcode.timeshop.config.MessagesConfiguration;
import cc.dreamcode.timeshop.config.PluginConfiguration;
import cc.dreamcode.timeshop.user.User;
import com.google.common.collect.ImmutableMap;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;

@Route(name = "timeshopadmin", aliases = {"tsa"})
@Permission("dreamtimeshop.admin")
public class TimeShopAdminCommand {

    private final PluginConfiguration configuration;
    private final MessagesConfiguration messages;
    private final CommandConfiguration commands;

    public TimeShopAdminCommand(PluginConfiguration configuration, MessagesConfiguration messages, CommandConfiguration commands) {
        this.configuration = configuration;
        this.messages = messages;
        this.commands = commands;
    }

    @Execute(route = "add")
    @Permission("dreamtimeshop.admin.add")
    void add(CommandSender sender, @Arg @Name("user") User user, @Arg @Name("currency") int currency) {
        if (currency <= 0) {
            this.messages.invalidCurrency.send(sender);
            return;
        }

        user.addCurrency(currency);

        this.messages.addCurrency.send(sender, ImmutableMap.of("USER", user.name(), "AMOUNT", currency));
    }

    @Execute(route = "remove")
    @Permission("dreamtimeshop.admin.remove")
    void remove(CommandSender sender, @Arg @Name("user") User user, @Arg @Name("currency") int currency) {
        if (currency <= 0) {
            this.messages.invalidCurrency.send(sender);
            return;
        }

        user.removeCurrency(currency);

        this.messages.removeCurrency.send(sender, ImmutableMap.of("USER", user.name(), "AMOUNT", currency));
    }

    @Execute(route = "set")
    @Permission("dreamtimeshop.admin.set")
    void set(CommandSender sender, @Arg @Name("user") User user, @Arg @Name("currency") int currency) {
        if (currency < 0) {
            this.messages.invalidCurrency.send(sender);
            return;
        }

        user.setCurrency(currency);

        this.messages.setCurrency.send(sender, ImmutableMap.of("USER", user.name(), "AMOUNT", currency));
    }

    @Execute(route = "reload")
    void reload(CommandSender sender) {
        this.messages.load();
        this.configuration.load();
        this.commands.load();

        this.messages.reload.send(sender);
    }
}
