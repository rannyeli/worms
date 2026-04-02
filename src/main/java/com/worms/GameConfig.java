package com.worms;

import java.util.List;

public final class GameConfig {

	private GameConfig() {}

	public static final int SCREEN_WIDTH = 1150;
	public static final int SCREEN_HEIGHT = 700;

	public static final int BLOCK_SIZE = 42;
	public static final int NUM_ROWS = 16;
	public static final int NUM_COLS = 55;

	public static final int TURN_DURATION_SECONDS = 30;

	public static final int WALK_SPEED = 6;
	public static final int JUMP_SPEED = 6;
	public static final int SCROLL_SPEED = 2;
	public static final int MOUSE_SCROLL_SPEED = 20;

	public static final int CENTER_X = SCREEN_WIDTH / 2;

	public static final int AI_TICK_MS = 20;
	public static final int AI_AIM_DELAY_MS = 100;

	public static final List<String> USA_NAMES = List.of("jhon", "thomas", "james", "michael", "louis");
	public static final List<String> ISRAEL_NAMES = List.of("yosef", "jacob", "yehuda", "david", "avraham");
	public static final List<String> RUSSIA_NAMES = List.of("alex", "dmitry", "igor", "juri", "emil");
}
