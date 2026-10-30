package com.yu.yuhideinfo.mixin;

import com.yu.yuhideinfo.client.gui.EyeButtonWidget;
import com.yu.yuhideinfo.client.gui.HideableText;
import net.minecraft.client.gui.screen.multiplayer.AddServerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AddServerScreen.class)
public abstract class AddServerScreenMixin {
	@Shadow
	private TextFieldWidget addressField;

	@Inject(method = "init", at = @At("TAIL"))
	private void yuhideinfo$addAddressVisibilityButton(CallbackInfo ci) {
		HideableText hideable = (HideableText) this.addressField;
		hideable.yuhideinfo$setHidden(true);

		EyeButtonWidget button = new EyeButtonWidget(
			this.addressField.getX() + this.addressField.getWidth() + 4,
			this.addressField.getY() + 2,
			false,
			pressed -> {
				boolean wasHidden = hideable.yuhideinfo$isHidden();
				hideable.yuhideinfo$setHidden(!wasHidden);
				((EyeButtonWidget) pressed).setRevealed(wasHidden);
			}
		);

		((ScreenAccessor) (Object) this).yuhideinfo$addDrawableChild(button);
	}
}
