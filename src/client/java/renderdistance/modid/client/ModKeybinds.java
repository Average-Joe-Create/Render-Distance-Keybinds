package renderdistance.modid.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;
import net.minecraft.network.chat.Component;

public class ModKeybinds {

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
        Identifier.fromNamespaceAndPath("render-distance", "keycategory")
    );

    private static KeyMapping increaseRenderDistance;
    private static KeyMapping decreaseRenderDistance;

    public static void register() {
        increaseRenderDistance = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.render-distance.increase",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_BRACKET,
            CATEGORY
        ));

        decreaseRenderDistance = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.render-distance.decrease",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_BRACKET,
            CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (increaseRenderDistance.consumeClick()) {
                int current = client.options.renderDistance().get();
                if (current < 32) {
                    client.options.renderDistance().set(current + 1);
                    client.levelRenderer.needsUpdate();
                    client.gui.setOverlayMessage(Component.translatable("msg.render-distance.increased", (current + 1)), false);
                } else {
                    client.gui.setOverlayMessage(Component.translatable("msg.render-distance.max"), false);
                }
            }
            while (decreaseRenderDistance.consumeClick()) {
                int current = client.options.renderDistance().get();
                if (current > 2) {
                    client.options.renderDistance().set(current - 1);
                    client.levelRenderer.needsUpdate();
                    client.gui.setOverlayMessage(Component.translatable("msg.render-distance.decreased", (current - 1)), false);
                } else {
                    client.gui.setOverlayMessage(Component.translatable("msg.render-distance.min"), false);
                }
            }
        });
    }
}
