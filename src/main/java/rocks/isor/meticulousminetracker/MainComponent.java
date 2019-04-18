package rocks.isor.meticulousminetracker;

import dagger.Component;
import rocks.isor.meticulousminetracker.listeners.ListenerModule;

import javax.inject.Singleton;

@Singleton
@Component(
		modules = {
				PluginModule.class,
				ListenerModule.class
		})
public interface MainComponent {
	void inject(Main main);
}
