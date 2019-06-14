package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.tasks.BlockPlacedTasks;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlockPlacedListener implements Listener {

	@Inject
	public BlockPlacedListener() {}

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	@Inject
	public BlockPlacedTasks blockPlacedTasks;

	@EventHandler
	public void on(BlockPlaceEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockPlacedTasks.registerBlockPlaced(e.getBlockPlaced()));
		}
	}

	@EventHandler
	public void on(BlockMultiPlaceEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockPlacedTasks.registerBlockPlaced(e.getReplacedBlockStates()));
		}
	}

	@EventHandler
	public void on(StructureGrowEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockPlacedTasks.registerBlockPlaced(e.getBlocks()));
		}
	}

	@EventHandler
	public void on(BlockFormEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockPlacedTasks.registerBlockPlaced(e.getBlock()));
		}
	}

	@EventHandler
	public void on(BlockSpreadEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockPlacedTasks.registerBlockPlaced(e.getBlock()));
		}
	}

	@EventHandler
	public void on(BlockDispenseEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockPlacedTasks.registerBlockPlaced(e.getBlock()));
		}
	}

	@EventHandler
	public void on(EntityBlockFormEvent e) {
		if (!e.isCancelled()) {
			bukkitScheduler.runTaskAsynchronously(plugin,
					blockPlacedTasks.registerBlockPlaced(e.getBlock()));
		}
	}
}
