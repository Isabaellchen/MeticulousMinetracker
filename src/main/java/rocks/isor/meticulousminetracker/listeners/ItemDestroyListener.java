package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ItemDestroyListener implements Listener {

	@Inject
	public ItemDestroyListener() {}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		System.out.println("Item died");
	}
}
