package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.tasks.BlockDestroyedTasks;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlockDestroyedListener implements Listener {

	@Inject
	public BlockDestroyedListener() {}

	@Inject
	public BlockDestroyedTasks blockDestroyedTasks;

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	@EventHandler
	public void on(BlockBreakEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockDestroyedTasks.registerBlockDestroyed(e.getBlock()));
		}
	}

	@EventHandler
	public void on(BlockBurnEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockDestroyedTasks.registerBlockDestroyed(e.getBlock()));
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void on(EntityExplodeEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockDestroyedTasks.registerBlockDestroyed(e.blockList()));
		}
	}

	@EventHandler
	public void on(BlockFromToEvent e) {
		if (!e.isCancelled()) {
			if (Material.LAVA.equals(e.getBlock().getType())) {
				for (ItemStack itemStack : e.getToBlock().getDrops()) {
					bukkitScheduler.runTaskAsynchronously(plugin,
							blockDestroyedTasks.registerBlockDestroyedByLava(e.getToBlock(), itemStack.getType().ordinal()));
				}
			}
		}
	}

	@EventHandler
	public void on(LeavesDecayEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockDestroyedTasks.registerBlockDestroyed(e.getBlock()));
		}
	}
}
