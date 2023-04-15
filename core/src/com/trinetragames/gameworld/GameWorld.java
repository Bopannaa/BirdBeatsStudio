package com.trinetragames.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.trinetragames.birdbeats.BirdBeats;
import com.trinetragames.gameobject.Bird;
import com.trinetragames.gameobject.ScrollHandler;
import com.trinetragames.helpers.AssetLoader;

public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int score = 0;
	private float runTime = 0;
	private int midPointY;
	private GameRenderer renderer;
	private GameState currentState;
	private int loop;
	private int loop1;
	private int adloop;
	private  int no;

	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, HIGHSCORE, SHAREBOARD;
	}

	public GameWorld(int midPointY) {
		currentState = GameState.MENU;
		this.midPointY = midPointY;
		bird = new Bird(55, midPointY - 11,AssetLoader.redBirds[0].getRegionWidth()-33,AssetLoader.redBirds[0].getRegionHeight()-33);
		// The grass should start 66 pixels below the midPointY
		scroller = new ScrollHandler(this);
		ground = new Rectangle(0, 512, AssetLoader.gameWidth, 2);
	}

	public void update(float delta) {
		runTime += delta;

		switch (currentState) {
		case READY:
		case MENU:
			updateReady(delta);
			break;
		case RUNNING:
			if (BirdBeats.adsController.isNetworkConnected()) {
				if (BirdBeats.adsController != null) {
					if (loop < 1) {
						BirdBeats.adsController.hideBannerAd();
						loop++;
						loop1 = 0;
					}
				}
			}
			updateRunning(delta);
			break;
		case GAMEOVER:
			if (adloop>5)
			{
				if (BirdBeats.adsController.isNetworkConnected()) {
					if (BirdBeats.adsController != null) {
						if (loop1 < 1) {
							BirdBeats.adsController.setTrackerScreenName("com.trinetragames.birdbeats.BirdBeats");
							BirdBeats.adsController.showBannerAd();
							loop1++;
							adloop=0;
						}
					}
				}
			}
			else
			{
			if (BirdBeats.adsController.isNetworkConnected()) {
				if (BirdBeats.adsController != null) {
					if (loop1 < 1) {
						BirdBeats.adsController.setTrackerScreenName("com.trinetragames.birdbeats.BirdBeats");
						BirdBeats.adsController.showAds();
						loop1++;
						adloop++;
					}
				}
			}
			}
			break;
		case HIGHSCORE:

			if (BirdBeats.adsController.isNetworkConnected()) {
				if (BirdBeats.adsController != null) {
					if (loop1 < 1) {
						BirdBeats.adsController.setTrackerScreenName("com.trinetragames.birdbeats.BirdBeats");
						BirdBeats.adsController.showAds();
					}
				}
			}
			break;

		case SHAREBOARD:
			break;

		default:
			break;
		}

	}

	private void updateReady(float delta) {
		bird.updateReady(runTime);
		scroller.updateReady(delta);
	}
	

	public void updateRunning(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
		scroller.update(delta);

		if (scroller.collides(bird) && bird.isAlive()) {
			scroller.stop();
			bird.die();
			AssetLoader.dead.play();
			renderer.prepareTransition(255, 255, 255, .3f);
			AssetLoader.fall.play();
		}

		if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {

			if (bird.isAlive()) {
				AssetLoader.dead.play();
				renderer.prepareTransition(255, 255, 255, .3f);
				bird.die();
			}

			scroller.stop();
			bird.decelerate();
			if(AssetLoader.getLocked()==false){
				if(AssetLoader.getGameTimes()>6){
					currentState = GameState.SHAREBOARD;
				}else{
					no++;
					AssetLoader.setGameTimes(AssetLoader.getGameTimes()+no);
					currentState = GameState.GAMEOVER;

					if (score > AssetLoader.getHighScore()) {
						AssetLoader.setHighScore(score);

						currentState = GameState.HIGHSCORE;
					}
				}
			}else{
				currentState = GameState.GAMEOVER;

				if (score > AssetLoader.getHighScore()) {
					AssetLoader.setHighScore(score);

					currentState = GameState.HIGHSCORE;
				}
			}
		}
	}

	public Bird getBird() {
		return bird;

	}

	public int getMidPointY() {
		return midPointY;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int increment) {
		score += increment;
	}

	public void start() {
		currentState = GameState.RUNNING;
	}

	public void ready() {
		currentState = GameState.READY;
		renderer.prepareTransition(0, 0, 0, 1.3f);
	}

	public void restart() {
		score = 0;
		loop = 0;
		bird.onRestart(midPointY - 11);
		scroller.onRestart();
		ready();
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public boolean isShareBoard() {
		return currentState == GameState.SHAREBOARD;
	}

	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}

	public void setNo(int no) {
		this.no = no;
	}

}
