package cc.dreamcode.timeshop.shared;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class ItemUtil {

    public static void giveItem(Player player, ItemStack itemStack) {
        if (hasSpace(player.getInventory(), itemStack)) {
            player.getInventory().addItem(itemStack);
            player.updateInventory();

            return;
        }

        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), itemStack);
    }

    private static boolean hasSpace(Inventory inventory, ItemStack itemStack) {
        if (inventory.firstEmpty() != -1) {
            return true;
        }

        for (ItemStack itemInv : inventory.getContents()) {
            if (itemInv == null) {
                continue;
            }

            if (!itemInv.isSimilar(itemStack)) {
                continue;
            }

            if (itemInv.getMaxStackSize() > itemInv.getAmount()) {
                return true;
            }
        }

        return false;
    }

    private ItemUtil() {

    }
}