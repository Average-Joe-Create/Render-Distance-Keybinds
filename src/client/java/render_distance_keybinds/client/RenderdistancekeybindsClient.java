package render_distance_keybinds.client;

import net.fabricmc.api.ClientModInitializer;

public class RenderdistancekeybindsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModKeybinds.register();
	}
}
