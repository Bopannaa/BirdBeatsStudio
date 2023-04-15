package com.trinetragames.gameobject;

import com.trinetragames.gameworld.GameWorld;
import com.trinetragames.helpers.AssetLoader;

public class ScrollHandler {

	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	private MidGround md1, md2;
	private ForeGround fg1, fg2;
	public static final int MIDGROUND_SCROLL_SPEED = -30;
	public static final int FOREGROUND_SCROLL_SPEED = -70;
	public static final int PIPE_SCROLL_SPEED = -130;
	public static final int GRASS_SCROLL_SPEED = -150;
	public static final int PIPE_GAP = 108;

	private GameWorld gameWorld;

	public ScrollHandler(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		frontGrass = new Grass(0, 340, 1024, 172, GRASS_SCROLL_SPEED);
		backGrass = new Grass(frontGrass.getTailX(), 340, 1024, 172,
				GRASS_SCROLL_SPEED);
		md1 = new MidGround(0, 0, 1024, 512, MIDGROUND_SCROLL_SPEED);
		md2 = new MidGround(md1.getTailX(), 0, 1024, 512,
				MIDGROUND_SCROLL_SPEED);

		fg1 = new ForeGround(0, 0, 1024, 512, FOREGROUND_SCROLL_SPEED);
		fg2 = new ForeGround(fg1.getTailX(), 0, 1024, 512,
				FOREGROUND_SCROLL_SPEED);
		pipe1 = new Pipe(463, 60, 60, 337, PIPE_SCROLL_SPEED);
		pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 25, 60, 337,
				PIPE_SCROLL_SPEED);
		pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 60, 60, 337,
				PIPE_SCROLL_SPEED);
	}

	public void updateReady(float delta) {

		md1.update(delta);
		md2.update(delta);
		fg1.update(delta);
		fg2.update(delta);
		frontGrass.update(delta);
		backGrass.update(delta);

		if (md1.isScrolledLeft()) {
			md1.reset(md2.getTailX());
		} else if (md2.isScrolledLeft()) {
			md2.reset(md1.getTailX());

		}

		if (fg1.isScrolledLeft()) {
			fg1.reset(fg2.getTailX());
		} else if (fg2.isScrolledLeft()) {
			fg2.reset(fg1.getTailX());

		}

		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());

		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());

		}

	}

	public void update(float delta) {
		// Update our objects
		md1.update(delta);
		md2.update(delta);
		fg1.update(delta);
		fg2.update(delta);
		frontGrass.update(delta);
		backGrass.update(delta);
		pipe1.update(delta);
		pipe2.update(delta);
		pipe3.update(delta);

		if (md1.isScrolledLeft()) {
			md1.reset(md2.getTailX());
		} else if (md2.isScrolledLeft()) {
			md2.reset(md1.getTailX());

		}

		if (fg1.isScrolledLeft()) {
			fg1.reset(fg2.getTailX());
		} else if (fg2.isScrolledLeft()) {
			fg2.reset(fg1.getTailX());

		}

		if (pipe1.isScrolledLeft()) {
			pipe1.reset(pipe3.getTailX() + PIPE_GAP);
		} else if (pipe2.isScrolledLeft()) {
			pipe2.reset(pipe1.getTailX() + PIPE_GAP);

		} else if (pipe3.isScrolledLeft()) {
			pipe3.reset(pipe2.getTailX() + PIPE_GAP);
		}

		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());

		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());

		}
	}

	public void stop() {
		md1.stop();
		md2.stop();
		fg1.stop();
		fg2.stop();
		frontGrass.stop();
		backGrass.stop();
		pipe1.stop();
		pipe2.stop();
		pipe3.stop();
	}

	public boolean collides(Bird bird) {

		if (!pipe1.isScored()
				&& pipe1.getX() + (pipe1.getWidth() / 2) < bird.getX()
						+ bird.getWidth()) {
			addScore(1);
			pipe1.setScored(true);
			AssetLoader.coin.play();
		} else if (!pipe2.isScored()
				&& pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX()
						+ bird.getWidth()) {
			addScore(1);
			pipe2.setScored(true);
			AssetLoader.coin.play();

		} else if (!pipe3.isScored()
				&& pipe3.getX() + (pipe3.getWidth() / 2) < bird.getX()
						+ bird.getWidth()) {
			addScore(1);
			pipe3.setScored(true);
			AssetLoader.coin.play();

		}

		return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3
				.collides(bird));
	}

	private void addScore(int increment) {
		gameWorld.addScore(increment);
	}

	public Grass getFrontGrass() {
		return frontGrass;
	}

	public Grass getBackGrass() {
		return backGrass;
	}
	
	public MidGround getMd1() {
		return md1;
	}

	public MidGround getMd2() {
		return md2;
	}

	public ForeGround getFg1() {
		return fg1;
	}

	public ForeGround getFg2() {
		return fg2;
	}

	public Pipe getPipe1() {
		return pipe1;
	}

	public Pipe getPipe2() {
		return pipe2;
	}

	public Pipe getPipe3() {
		return pipe3;
	}

	public void onRestart() {
		frontGrass.onRestart(0, GRASS_SCROLL_SPEED);
		backGrass.onRestart(frontGrass.getTailX(), GRASS_SCROLL_SPEED);
		md1.onRestart(0, MIDGROUND_SCROLL_SPEED);
		md2.onRestart(md1.getTailX(), MIDGROUND_SCROLL_SPEED);
		fg1.onRestart(0, FOREGROUND_SCROLL_SPEED);
		fg2.onRestart(fg1.getTailX(), FOREGROUND_SCROLL_SPEED);
		pipe1.onRestart(463, PIPE_SCROLL_SPEED);
		pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, PIPE_SCROLL_SPEED);
		pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, PIPE_SCROLL_SPEED);
	}

}
