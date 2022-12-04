package cc.dreamcode.timeshop.command.implementation;

import cc.dreamcode.timeshop.TimeShopMenu;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "timeshop", aliases = {"ts"})
public class TimeShopCommand {

    private final TimeShopMenu timeShopMenu;

    public TimeShopCommand(TimeShopMenu timeShopMenu) {
        this.timeShopMenu = timeShopMenu;
    }

    @Execute
    void execute(Player player) {
        this.timeShopMenu.open(player);
    }
}
