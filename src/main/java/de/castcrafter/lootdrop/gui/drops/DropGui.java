package de.castcrafter.lootdrop.gui.drops;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.PagingButtons;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import de.castcrafter.lootdrop.config.trades.SupplyTrade;
import de.castcrafter.lootdrop.config.trades.SupplyTradeRecipe;
import de.castcrafter.lootdrop.larry.LarryNpc;
import io.th0rgal.oraxen.api.OraxenItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * The type Drop gui.
 */
public class DropGui extends ChestGui {

  private final DropsGui dropsGui;

  /**
   * Instantiates a new Drop gui.
   *
   * @param dropsGui        the drops gui
   * @param openedForPlayer the opened for player
   * @param supplyTrade     the hourly drop
   */
  public DropGui(DropsGui dropsGui, HumanEntity openedForPlayer, SupplyTrade supplyTrade) {
    super(6, "<shift:-8><glyph:daily_rewards_ui>");

    this.dropsGui = dropsGui;

    setOnClose(event -> {
      LarryNpc.openLarryGuis.remove(this);
    });

    LarryNpc.openLarryGuis.add(this);

    setOnGlobalClick(event -> event.setCancelled(true));
    setOnGlobalDrag(event -> event.setCancelled(true));

    StaticPane staticPane = new StaticPane(0, 5, 9, 1);
    staticPane.addItem(getBackItem(), 4, 0);

    addPane(staticPane);

    PaginatedPane paginatedPane = new PaginatedPane(1, 2, 7, 3);

    int page = 0;
    int y = 0;
    for (SupplyTradeRecipe recipe : supplyTrade.getRecipes()) {
      paginatedPane.addPane(page, new DropRecipePane(openedForPlayer, this, recipe, y));

      y++;

      if (y == 3) {
        page++;
        y = 0;
      }
    }

    PagingButtons pagingButtons = new PagingButtons(Slot.fromXY(0, 3), 9, paginatedPane);
    DropsGui.setBackItem(pagingButtons);
    DropsGui.setForwardItem(pagingButtons);

    addPane(paginatedPane);
    addPane(pagingButtons);

    update();
  }

  /**
   * Gets back item.
   *
   * @return the back item
   */
  private GuiItem getBackItem() {
    ItemStack itemStack = OraxenItems.getItemById("daily_reward_back").build();
    ItemMeta itemMeta = itemStack.getItemMeta();

    if (itemMeta != null) {
      itemMeta.displayName(
          Component.text("Zurück", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
      itemStack.setItemMeta(itemMeta);
    }

    return new GuiItem(itemStack, event -> {
      event.setCancelled(true);

      dropsGui.show(event.getWhoClicked());
      dropsGui.update();
    });
  }
}
