package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
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
	public void on(EntityDeathEvent e) {
		bukkitScheduler.runTaskAsynchronously(plugin,
				itemDestroyedTasks.registerItemDestroyed(e.getDrops()));
	}

	@EventHandler
	public void on(ItemDespawnEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					itemDestroyedTasks.registerItemDestroyed(e.getEntity().getItemStack()));
		}
	}

	@EventHandler
	public void on(EntityDamageEvent e) {
		if (!e.isCancelled()) {

		}
	}
}
