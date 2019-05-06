package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlockPlacementListener implements Listener {

	@Inject
	public BlockPlacementListener() {}

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	@EventHandler
	public void on(BlockBreakEvent event) {
	}
}
