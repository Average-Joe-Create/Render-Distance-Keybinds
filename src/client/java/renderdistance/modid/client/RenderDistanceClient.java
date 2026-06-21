package renderdistance.modid.client;

import net.fabricmc.api.ClientModInitializer;

public class RenderDistanceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModKeybinds.register();
	}
}
