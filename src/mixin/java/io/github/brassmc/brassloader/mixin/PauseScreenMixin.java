package io.github.brassmc.brassloader.mixin;

import io.github.brassmc.brassloader.gui.ModsListScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "createPauseMenu", at = @At("RETURN"))
    private void brass$addModsButton(CallbackInfo ci) {
        addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 104, 204, 20,
                Component.translatable("brassloader.menu.mods"), button -> {
            assert minecraft != null;
            minecraft.setScreen(new ModsListScreen(this));
        }));
    }

    @Override
    protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T widget) {
        if (widget instanceof Button button && button.getMessage() instanceof MutableComponent mutableComponent && mutableComponent.getContents() instanceof TranslatableContents contents) {
            if (contents.getKey().equals("menu.returnToMenu") || contents.getKey().equals("menu.disconnect")) {
                button.y += 24;
            }
        }

        return super.addRenderableWidget(widget);
    }
}
