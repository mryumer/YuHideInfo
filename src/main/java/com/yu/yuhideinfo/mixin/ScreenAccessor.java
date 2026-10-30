package com.yu.yuhideinfo.mixin;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * {@code addDrawableChild} is declared in {@link Screen}, not in the individual
 * screen subclasses. A {@code @Shadow} inside a mixin targeting a subclass does
 * not resolve inherited methods, so an invoker on the declaring class is used.
 */
@Mixin(Screen.class)
public interface ScreenAccessor {
	@Invoker("addDrawableChild")
	<T extends Element & Drawable & Selectable> T yuhideinfo$addDrawableChild(T drawableElement);
}
