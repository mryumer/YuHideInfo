package com.yu.yuhideinfo.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;

/**
 * A compact 16x16 button placed next to a server address field.
 * An open eye means the address is visible, a closed eye means it is masked.
 */
public class EyeButtonWidget extends ButtonWidget {
	private static final int ICON_COLOR = 0xFFE0E0E0;
	private static final int ICON_COLOR_DISABLED = 0xFFA0A0A0;
	private static final int PUPIL_COLOR = 0xFF202020;

	private boolean revealed;

	public EyeButtonWidget(int x, int y, boolean initiallyRevealed, PressAction onPress) {
		super(
			x,
			y,
			16,
			16,
			net.minecraft.text.Text.translatable("yuhideinfo.button.toggle"),
			onPress,
			DEFAULT_NARRATION_SUPPLIER
		);
		this.revealed = initiallyRevealed;
		this.updateTooltip();
	}

	public boolean isRevealed() {
		return this.revealed;
	}

	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
		this.updateTooltip();
	}

	private void updateTooltip() {
		this.setTooltip(Tooltip.of(net.minecraft.text.Text.translatable(
			this.revealed ? "yuhideinfo.button.hide" : "yuhideinfo.button.show"
		)));
	}

	@Override
	protected void drawIcon(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		this.drawButton(context);

		int color = this.active ? ICON_COLOR : ICON_COLOR_DISABLED;
		int x = this.getX();
		int y = this.getY();

		if (this.revealed) {
			drawOpenEye(context, x, y, color);
		} else {
			drawClosedEye(context, x, y, color);
		}
	}

	private static void drawOpenEye(DrawContext context, int x, int y, int color) {
		// Rounded eye outline.
		context.fill(x + 4, y + 5, x + 12, y + 6, color);
		context.fill(x + 3, y + 6, x + 13, y + 10, color);
		context.fill(x + 4, y + 10, x + 12, y + 11, color);
		// Pupil.
		context.fill(x + 7, y + 7, x + 10, y + 10, PUPIL_COLOR);
	}

	private static void drawClosedEye(DrawContext context, int x, int y, int color) {
		// Eyelid.
		context.fill(x + 4, y + 7, x + 12, y + 8, color);
		context.fill(x + 3, y + 6, x + 4, y + 7, color);
		context.fill(x + 12, y + 6, x + 13, y + 7, color);
		// Lashes.
		context.fill(x + 5, y + 9, x + 6, y + 10, color);
		context.fill(x + 8, y + 9, x + 9, y + 10, color);
		context.fill(x + 11, y + 9, x + 12, y + 10, color);
	}
}
