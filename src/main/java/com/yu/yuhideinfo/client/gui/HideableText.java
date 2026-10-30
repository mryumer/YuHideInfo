package com.yu.yuhideinfo.client.gui;

/**
 * Implemented by {@code TextFieldWidget} through {@code TextFieldWidgetMixin}.
 * The real text is never changed: only its on-screen representation is masked.
 */
public interface HideableText {
	void yuhideinfo$setHidden(boolean hidden);

	boolean yuhideinfo$isHidden();
}
