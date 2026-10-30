package com.yu.yuhideinfo;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class YuHideInfoClient implements ClientModInitializer {
	public static final String MOD_ID = "yuhideinfo";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("YuHideInfo initialized: server address fields are hidden by default.");
	}
}
