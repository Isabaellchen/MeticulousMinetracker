package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.tasks.ItemDestroyedTasks;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ItemDestroyedListener implements Listener {

	@Inject
	public ItemDestroyedListener() {}

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	@Inject
	public ItemDestroyedTasks itemDestroyedTasks;

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		System.out.println("Item died");
	}

	@EventHandler
	public void on(BlockFromToEvent event) {
		switch (event.getBlock().getType()) {
			case LEGACY_LAVA:
			case LAVA:
				bukkitScheduler.runTaskAsynchronously(plugin,
						itemDestroyedTasks.registerItemDestroyedCollection(event.getToBlock().getDrops()));
		}
	}
}
