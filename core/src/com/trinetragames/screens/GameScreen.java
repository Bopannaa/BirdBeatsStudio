package com.trinetragames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.trinetragames.gameworld.GameRenderer;
import com.trinetragames.gameworld.GameWorld;
import com.trinetragames.helpers.AssetLoader;
import com.trinetragames.helpers.InputHandler;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;

	// This is the constructor, not the class declaration
	public GameScreen() {

		float screenWidth = AssetLoader.screenWidth;
		float screenHeight = AssetLoader.screenHeight;
		float gameWidth = AssetLoader.gameWidth;
		float gameHeight = AssetLoader.gameHeight;
		int midPointY = (int) (gameHeight / 2);

		world = new GameWorld(midPointY);
		Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
		renderer = new GameRenderer(world, (int) gameHeight, midPointY);
		world.setRenderer(renderer);
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
