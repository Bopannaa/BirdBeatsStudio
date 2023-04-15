package com.trinetragames.helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.trinetragames.birdbeats.BirdBeats;
import com.trinetragames.gameobject.Bird;
import com.trinetragames.gameworld.GameWorld;
import com.trinetragames.ui.SimpleButton;

public class InputHandler implements InputProcessor {
	private Bird myBird;
	private GameWorld myWorld;

	private List<SimpleButton> menuButtons;
	private List<SimpleButton> gameoverButtons;
	private List<SimpleButton> shareBoardButtons;

	private SimpleButton playButton , gpsButton ,rateButton, sharingButton, laterButton, closeButton;

	private float scaleFactorX;
	private float scaleFactorY;

	public InputHandler(GameWorld myWorld, float scaleFactorX,
			float scaleFactorY) {
		this.myWorld = myWorld;
		myBird = myWorld.getBird();

		int midPointY = myWorld.getMidPointY();

		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;

		menuButtons = new ArrayList<SimpleButton>();
		gameoverButtons = new ArrayList<SimpleButton>();
		shareBoardButtons = new ArrayList<SimpleButton>();
		playButton = new SimpleButton(
				(int)AssetLoader.gameWidth / 2 -103-18,
				midPointY + 70, 103, 56, AssetLoader.playButtonUp,
				AssetLoader.playButtonDown);
		gpsButton = new SimpleButton(
				(int)(AssetLoader.gameWidth / 2) +18,
				midPointY + 70, 103, 56, AssetLoader.gpsButtonUp,
				AssetLoader.gpsButtonDown);
		rateButton = new SimpleButton(
				((int)AssetLoader.gameWidth / 2) -30,
				midPointY + 5, 60, 32, AssetLoader.rateButtonUp,
				AssetLoader.rateButtonDown);
		sharingButton = new SimpleButton(((AssetLoader.gameWidth/2)-AssetLoader.sharingButton.getRegionWidth()-16),
				((AssetLoader.gameHeight/2)+40),
				92,52, AssetLoader.sharingButton,
				AssetLoader.sharingButton);
		laterButton = new SimpleButton(((AssetLoader.gameWidth/2)+14),
				((AssetLoader.gameHeight/2)+40),
				92,51, AssetLoader.laterButton,
				AssetLoader.laterButton);
		closeButton = new SimpleButton((AssetLoader.gameWidth/2)+(AssetLoader.shareBoard.getRegionWidth()/2)-28,
				(AssetLoader.gameHeight/2)-(AssetLoader.shareBoard.getRegionHeight()/2)+8,
				AssetLoader.closeButton.getRegionWidth()/1.75f,
				AssetLoader.closeButton.getRegionHeight()/1.75f,
				AssetLoader.closeButton,
				AssetLoader.closeButton);
		menuButtons.add(playButton);
		menuButtons.add(gpsButton);
		menuButtons.add(rateButton);
		gameoverButtons.add(playButton);
		gameoverButtons.add(gpsButton);
		shareBoardButtons.add(sharingButton);
		shareBoardButtons.add(laterButton);
		shareBoardButtons.add(closeButton);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (myWorld.isMenu()) {
			playButton.isTouchDown(screenX, screenY);
			gpsButton.isTouchDown(screenX, screenY);
			rateButton.isTouchDown(screenX, screenY);
		} else if (myWorld.isReady()) {
			myWorld.start();
			myBird.onClick();
		} else if (myWorld.isRunning()) {
			myBird.onClick();
		}

		if (myWorld.isGameOver() || myWorld.isHighScore()) {
			playButton.isTouchDown(screenX, screenY);
			gpsButton.isTouchDown(screenX, screenY);
	    }
		if (myWorld.isShareBoard()) {
			sharingButton.isTouchDown(screenX, screenY);
			laterButton.isTouchDown(screenX, screenY);
			closeButton.isTouchDown(screenX, screenY);
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (myWorld.isMenu()) {
			if (playButton.isTouchUp(screenX, screenY)) {
				myWorld.ready();
				return true;
			}
			if (gpsButton.isTouchUp(screenX, screenY)) {
				if (!BirdBeats.adsController.isSignedIn())
                {
					BirdBeats.adsController.signIn();
                }
				BirdBeats.adsController.showScores();
                return true;
			}
			if (rateButton.isTouchUp(screenX, screenY)) {
				if(BirdBeats.adsController.isNetworkConnected()){
					BirdBeats.adsController.rateGame();
				}else
				{
					BirdBeats.adsController.noNetworkConnection();
				}
				return true;
			}
		}
		else if (myWorld.isGameOver() || myWorld.isHighScore()) {
			if (playButton.isTouchUp(screenX, screenY)) {
				myWorld.restart();
				return true;
			}
			if (gpsButton.isTouchUp(screenX, screenY)) {
				/*if (!BirdBeats.adsController.isSignedIn())
                {
					BirdBeats.adsController.signIn();
                }
				BirdBeats.adsController.showScores();*/
				BirdBeats.adsController.facebookShare();
                return true;
			}
		}else if (myWorld.isShareBoard()) {
			if (sharingButton.isTouchUp(screenX, screenY)) {
				if(BirdBeats.adsController.isNetworkConnected()){
					AssetLoader.setLocked(true);
					myWorld.setCurrentState(GameWorld.GameState.GAMEOVER);
					//BirdBeats.adsController.shareLink("try this game", "https://play.google.com/store/apps/details?id=com.trinetragames.birdbeats.android");
					if(BirdBeats.adsController.isConnectedFacebook()){
						BirdBeats.adsController.facebookShare();
					}else {
						BirdBeats.adsController.connectFacebook();
					}
				}else{
					BirdBeats.adsController.noNetworkConnection();
				}
				return true;
			}
			if (laterButton.isTouchUp(screenX, screenY)) {
				AssetLoader.setGameTimes(0);
				myWorld.setNo(0);
				myWorld.setCurrentState(GameWorld.GameState.GAMEOVER);
				return true;
			}
			if (closeButton.isTouchUp(screenX, screenY)) {
				AssetLoader.setGameTimes(0);
				myWorld.setNo(0);
				myWorld.setCurrentState(GameWorld.GameState.GAMEOVER);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {

		// Can now use Space Bar to play the game
		if (keycode == Keys.SPACE) {

			if (myWorld.isMenu()) {
				myWorld.ready();
			} else if (myWorld.isReady()) {
				myWorld.start();
			}

			myBird.onClick();

			if (myWorld.isGameOver() || myWorld.isHighScore()) {
				myWorld.restart();
			}

		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

	public List<SimpleButton> getMenuButtons() {
		return menuButtons;
	}

	public List<SimpleButton> getGameoverButtons() {
		return gameoverButtons;
	}

	public List<SimpleButton> getShareBoardButtons() {
		return shareBoardButtons;
	}
}
