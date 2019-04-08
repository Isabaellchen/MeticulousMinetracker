package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlockMiningListener implements Listener {

	@Inject
	public BlockMiningListener() {}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		System.out.println("Block broken!");
	}
}
