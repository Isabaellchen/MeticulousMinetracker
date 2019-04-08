package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ItemCraftingListener implements Listener {

	@Inject
	public ItemCraftingListener() {}

	@EventHandler
	public void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {
		System.out.println("Item crafted");
	}
}
