package rocks.isor.meticulousminetracker.listeners;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;
import org.bukkit.event.Listener;

@Module
public abstract class ListenerModule {

	@Binds
	@IntoSet
	public abstract Listener provideBlockDestroyedListener(BlockDestroyedListener blockDestroyedListener);

	@Binds
	@IntoSet
	public abstract Listener provideBlockPlacedListener(BlockPlacedListener blockPlacedListener);

	@Binds
	@IntoSet
	public abstract Listener provideItemCraftedListener(ItemCraftedListener itemCraftedListener);

	@Binds
	@IntoSet
	public abstract Listener provideItemDestroyedListener(ItemDestroyedListener itemDestroyedListener);
}
