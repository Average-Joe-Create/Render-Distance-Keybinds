package render_distance_keybinds.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
        ResourceLocation.fromNamespaceAndPath("render-distance", "keycategory")
    );

    private static KeyMapping increaseRenderDistance;
    private static KeyMapping decreaseRenderDistance;

    public static void register() {
        increaseRenderDistance = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.render-distance.increase",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_BRACKET,
            CATEGORY
        ));

        decreaseRenderDistance = KeyBindingHelper.registerKeyBinding(new KeyMapping(
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
                    client.gui.setOverlayMessage(Component.translatable("msg.render-distance.increased", (current + 1)), false);
                } else {
                    client.gui.setOverlayMessage(Component.translatable("msg.render-distance.max"), false);
                }
            }
            while (decreaseRenderDistance.consumeClick()) {
                int current = client.options.renderDistance().get();
                if (current > 2) {
                    client.options.renderDistance().set(current - 1);
                    client.gui.setOverlayMessage(Component.translatable("msg.render-distance.decreased", (current - 1)), false);
                } else {
                    client.gui.setOverlayMessage(Component.translatable("msg.render-distance.min"), false);
                }
            }
        });
    }
}
