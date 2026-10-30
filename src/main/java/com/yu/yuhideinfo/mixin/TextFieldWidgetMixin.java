package com.yu.yuhideinfo.mixin;

import com.yu.yuhideinfo.client.gui.HideableText;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin implements HideableText {
	@Unique
	private boolean yuhideinfo$hidden;

	@Shadow
	private String text;

	@Shadow
	private TextRenderer textRenderer;

	@Shadow
	private int textX;

	@Shadow
	private int textY;

	@Shadow
	private int selectionStart;

	@Shadow
	private boolean textShadow;

	@Override
	public void yuhideinfo$setHidden(boolean hidden) {
		this.yuhideinfo$hidden = hidden;
	}

	@Override
	public boolean yuhideinfo$isHidden() {
		return this.yuhideinfo$hidden;
	}

	/**
	 * The widget has already drawn the real address by the time this runs.
	 * Redraw its background, then draw a masked version on top. The stored
	 * text is never modified, so connecting and saving keep working.
	 */
	@Inject(method = "renderWidget", at = @At("RETURN"))
	private void yuhideinfo$renderMaskedText(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
		TextFieldWidget self = (TextFieldWidget) (Object) this;
		if (!this.yuhideinfo$hidden || !self.isVisible()) {
			return;
		}

		boolean focused = self.isFocused();
		Identifier background = Identifier.ofVanilla(focused ? "widget/text_field_highlighted" : "widget/text_field");
		context.drawGuiTexture(
			RenderPipelines.GUI_TEXTURED,
			background,
			self.getX(),
			self.getY(),
			self.getWidth(),
			self.getHeight()
		);

		String masked = "*".repeat(this.text.length());
		if (!masked.isEmpty()) {
			String visibleMask = this.textRenderer.trimToWidth(masked, self.getInnerWidth());
			context.drawText(
				this.textRenderer,
				visibleMask,
				this.textX,
				this.textY,
				TextFieldWidget.DEFAULT_EDITABLE_COLOR,
				this.textShadow
			);
		}

		if (focused && (Util.getMeasuringTimeMs() / 300L) % 2L == 0L) {
			int cursor = Math.min(this.selectionStart, this.text.length());
			String maskedPrefix = "*".repeat(cursor);
			String visiblePrefix = this.textRenderer.trimToWidth(maskedPrefix, self.getInnerWidth());
			int caretX = this.textX + this.textRenderer.getWidth(visiblePrefix);
			int rightEdge = self.getX() + self.getWidth() - 2;

			if (caretX <= rightEdge) {
				context.fill(
					caretX,
					this.textY - 1,
					caretX + 1,
					this.textY + 9,
					TextFieldWidget.DEFAULT_EDITABLE_COLOR
				);
			}
		}
	}
}
