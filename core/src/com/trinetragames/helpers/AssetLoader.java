package com.trinetragames.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture,  textureWood;
	public static TextureRegion logo, bbLogo, bg1, bg2, grass,
								middleground, foreground, taptap,
								barup,bardown, 
								playButtonUp, playButtonDown,
								gpsButtonUp, gpsButtonDown,
								rateButtonUp, rateButtonDown,
								ready, gameOver, highScore, retry,
								scoreboard, bronzeMedel, silverMedel,
								goldMedel, platinumMedel,sharingButton,laterButton,shareBoard,closeButton;
	public static Animation redBirdsAnim, yellowBirdsAnim, blueBirdsAnim, greyBirdsAnim,
										arctinBirdsAnim, pinkBirdsAnim, greenBirdsAnim,purpleBirdsAnim;
	public static Sound dead, flap, coin, fall, button;
	public static BitmapFont font, shadow, whiteFont;
	public static ParticleEffect particleEffect;
	private static Preferences prefs;
	public static float screenWidth = Gdx.graphics.getWidth();
	public static float screenHeight = Gdx.graphics.getHeight();
	public static float gameHeight=512;
	public static float gameWidth = ((gameHeight*screenWidth)/(screenHeight));
	public static TextureAtlas birdsTextureAtlas;

	public static TextureRegion[] redBirds,yellowBirds,blueBirds,greyBirds,greenBirds,pinkBirds,arctinBirds,purpleBirds;
	
	public static void load() {
		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		birdsTextureAtlas = new TextureAtlas(Gdx.files.internal("data/BirdsAtlas.pack"));

		logo = new TextureRegion(texture, 1024, 817, 312, 23);

		textureWood = new Texture(Gdx.files.internal("data/Pole.png"));
		textureWood.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		playButtonUp = new TextureRegion(texture, 1393, 718, 134, 81);
		playButtonDown = new TextureRegion(texture, 1760, 718, 134, 81);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);
		
		gpsButtonUp = new TextureRegion(texture, 1531, 718, 134, 81);
		gpsButtonDown = new TextureRegion(texture, 1898, 718, 134, 81);
		gpsButtonUp.flip(false, true);
		gpsButtonDown.flip(false, true);
		
		rateButtonUp = new TextureRegion(texture, 1674, 718, 78, 47);
		rateButtonDown = new TextureRegion(texture, 1898, 660, 78, 47);
		rateButtonUp.flip(false, true);
		rateButtonDown.flip(false, true);

		sharingButton = birdsTextureAtlas.findRegion("ShareButton");
		sharingButton.flip(false, true);

		laterButton = birdsTextureAtlas.findRegion("LaterButton");
		laterButton.flip(false, true);

		shareBoard = birdsTextureAtlas.findRegion("ShareWindow");
		shareBoard.flip(false, true);

		closeButton = birdsTextureAtlas.findRegion("CloseButton");
		closeButton.flip(false, true);

		ready = new TextureRegion(texture, 1024, 732, 75, 16);
		ready.flip(false, true);

		retry = new TextureRegion(texture, 1024, 764, 73, 16);
		retry.flip(false, true);
		
		gameOver = new TextureRegion(texture, 1024, 748, 102, 16);
		gameOver.flip(false, true);

		scoreboard = new TextureRegion(texture, 1368, 512, 271, 141);
		scoreboard.flip(false, true);

		highScore = new TextureRegion(texture, 1024, 780, 106, 16);
		highScore.flip(false, true);

		bbLogo = new TextureRegion(texture, 1024, 739, 364, 70);
		bbLogo.flip(false, true);
		
		taptap = new TextureRegion(texture, 1656, 512, 241, 206);
		taptap.flip(false, true);
		
//MEDELS
		bronzeMedel = new TextureRegion(texture,1365, 660, 52, 52);
		bronzeMedel.flip(false, true);
		silverMedel = new TextureRegion(texture,1422, 660, 52, 52);
		silverMedel.flip(false, true);
		goldMedel = new TextureRegion(texture,1478, 660, 52, 52);
		goldMedel.flip(false, true);
		platinumMedel = new TextureRegion(texture,1535, 660, 52, 52);
		platinumMedel.flip(false, true);

//PARTICLE EFFECT
		particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("data/lleaf.p"),
				Gdx.files.internal("data"));
		particleEffect.setPosition(Gdx.graphics.getWidth() / 2,
				-100);
		particleEffect.start();
		
		
		
//BACKGROUNDS
		bg1 = new TextureRegion(texture, 1024, 0, (int)gameWidth, 512);
		bg1.flip(false, true);
		
		bg2 = new TextureRegion(texture, 1536, 0, (int)gameWidth, 512);
		bg2.flip(false, true);
		
		middleground = new TextureRegion(texture, 0, 512, 1024, 512);
		middleground.flip(false, true);

		foreground = new TextureRegion(texture, 0, 0, 1024, 512);
		foreground.flip(false, true);

		grass = new TextureRegion(texture, 1024, 852, 1024, 172);
		grass.flip(false, true);

		
//BIRDS
		redBirds = new TextureRegion[3];
		redBirds[0] = birdsTextureAtlas.findRegion("Red1");
		redBirds[1] = birdsTextureAtlas.findRegion("Red2");
		redBirds[2] = birdsTextureAtlas.findRegion("Red3");
		redBirds[0].flip(false,true);
		redBirds[1].flip(false,true);
		redBirds[2].flip(false,true);
		redBirdsAnim = new Animation(0.09f,redBirds);
		redBirdsAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		yellowBirds = new TextureRegion[3];
		yellowBirds[0] = birdsTextureAtlas.findRegion("Yellow1");
		yellowBirds[1] = birdsTextureAtlas.findRegion("Yellow2");
		yellowBirds[2] = birdsTextureAtlas.findRegion("Yellow3");
		yellowBirds[0].flip(false,true);
		yellowBirds[1].flip(false,true);
		yellowBirds[2].flip(false,true);
		yellowBirdsAnim = new Animation(0.09f,yellowBirds);
		yellowBirdsAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		blueBirds = new TextureRegion[3];
		blueBirds[0] = birdsTextureAtlas.findRegion("Blue1");
		blueBirds[1] = birdsTextureAtlas.findRegion("Blue2");
		blueBirds[2] = birdsTextureAtlas.findRegion("Blue3");
		blueBirds[0].flip(false,true);
		blueBirds[1].flip(false,true);
		blueBirds[2].flip(false,true);
		blueBirdsAnim = new Animation(0.09f,blueBirds);
		blueBirdsAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		greyBirds = new TextureRegion[3];
		greyBirds[0] = birdsTextureAtlas.findRegion("Grey1");
		greyBirds[1] = birdsTextureAtlas.findRegion("Grey2");
		greyBirds[2] = birdsTextureAtlas.findRegion("Grey3");
		greyBirds[0].flip(false,true);
		greyBirds[1].flip(false,true);
		greyBirds[2].flip(false,true);
		greyBirdsAnim = new Animation(0.09f,greyBirds);
		greyBirdsAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		arctinBirds = new TextureRegion[3];
		arctinBirds[0] = birdsTextureAtlas.findRegion("Arctin1");
		arctinBirds[1] = birdsTextureAtlas.findRegion("Arctin2");
		arctinBirds[2] = birdsTextureAtlas.findRegion("Arctin3");
		arctinBirds[0].flip(false,true);
		arctinBirds[1].flip(false,true);
		arctinBirds[2].flip(false,true);
		arctinBirdsAnim = new Animation(0.09f,arctinBirds);
		arctinBirdsAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		greenBirds = new TextureRegion[3];
		greenBirds[0] = birdsTextureAtlas.findRegion("Green1");
		greenBirds[1] = birdsTextureAtlas.findRegion("Green2");
		greenBirds[2] = birdsTextureAtlas.findRegion("Green3");
		greenBirds[0].flip(false,true);
		greenBirds[1].flip(false,true);
		greenBirds[2].flip(false,true);
		greenBirdsAnim = new Animation(0.09f,greenBirds);
		greenBirdsAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		pinkBirds = new TextureRegion[3];
		pinkBirds[0] = birdsTextureAtlas.findRegion("Pink1");
		pinkBirds[1] = birdsTextureAtlas.findRegion("Pink2");
		pinkBirds[2] = birdsTextureAtlas.findRegion("Pink3");
		pinkBirds[0].flip(false,true);
		pinkBirds[1].flip(false,true);
		pinkBirds[2].flip(false,true);
		pinkBirdsAnim = new Animation(0.09f,pinkBirds);
		pinkBirdsAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		purpleBirds = new TextureRegion[3];
		purpleBirds[0] = birdsTextureAtlas.findRegion("Purple1");
		purpleBirds[1] = birdsTextureAtlas.findRegion("Purple2");
		purpleBirds[2] = birdsTextureAtlas.findRegion("Purple3");
		purpleBirds[0].flip(false,true);
		purpleBirds[1].flip(false,true);
		purpleBirds[2].flip(false,true);
		purpleBirdsAnim = new Animation(0.09f,purpleBirds);
		purpleBirdsAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
//PIPES
		bardown = new TextureRegion(textureWood, 0, 0, 120, 674);
		barup = new TextureRegion(textureWood, 0, 0, 120, 674);
		barup.flip(false, true);

		
//SOUNDS
		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.ogg"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.ogg"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.ogg"));
		fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.ogg"));
		button = Gdx.audio.newSound(Gdx.files.internal("data/button.ogg"));
		
//FONTS
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.getData().setScale(.5f, -.5f);

		whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
		whiteFont.getData().setScale(.35f, -.35f);

		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.getData().setScale(.5f, -.5f);

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("ZombieBird");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
		if (!prefs.contains("Locked")) {
			prefs.putBoolean("Locked", false);
		}
		if (!prefs.contains("gameTimes")) {
			prefs.putInteger("gameTimes", 0);
		}
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}
	public static void setGameTimes(int val) {
		prefs.putInteger("gameTimes", val);
		prefs.flush();
	}
	public static void setLocked(Boolean val) {
		prefs.putBoolean("Locked", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static Boolean getLocked() {
		return prefs.getBoolean("Locked");
	}
	public static int getGameTimes() {
		return prefs.getInteger("gameTimes");
	}

	public static void dispose() {
		texture.dispose();
		textureWood.dispose();
		dead.dispose();
		flap.dispose();
		coin.dispose();
		font.dispose();
		shadow.dispose();
		whiteFont.dispose();
		particleEffect.dispose();
	}

}