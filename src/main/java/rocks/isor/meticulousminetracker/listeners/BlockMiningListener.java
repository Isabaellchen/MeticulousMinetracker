package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.tasks.BlockMiningTasks;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class BlockMiningListener implements Listener {

	@Inject
	public BlockMiningListener() {}

	@Inject
	public BlockMiningTasks blockMiningTasks;

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Block block = event.getBlock();
		Collection<ItemStack> drops = block.getDrops();

		bukkitScheduler.runTaskAsynchronously(plugin, blockMiningTasks.registerNewBlockBroken(block, drops));
	}
}
