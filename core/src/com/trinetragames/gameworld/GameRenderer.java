package com.trinetragames.gameworld;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.trinetragames.birdbeats.BirdBeats;
import com.trinetragames.gameobject.Bird;
import com.trinetragames.gameobject.ForeGround;
import com.trinetragames.gameobject.Grass;
import com.trinetragames.gameobject.MidGround;
import com.trinetragames.gameobject.Pipe;
import com.trinetragames.gameobject.ScrollHandler;
import com.trinetragames.helpers.AssetLoader;
import com.trinetragames.helpers.InputHandler;
import com.trinetragames.tweenaccessor.Value;
import com.trinetragames.tweenaccessor.ValueAccessor;
import com.trinetragames.ui.SimpleButton;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batcher;

	private int midPointY;

	// Game Objects
	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private MidGround md1, md2;
	private ForeGround fg1, fg2;
	private Pipe pipe1, pipe2, pipe3;
	private ParticleEffect particleEffect;
	// Game Assets
	private TextureRegion bg1, bg2, grass, middleground, foreground, birdMidR, birdMidY,
			birdMidG, birdMidB,birdMidA, birdMidGr, birdMidP,birdMidPu, barup, shareBoard,
			bardown, scoreboard, bronzeMedel, silverMedel, goldMedel, platinumMedel, taptap, bblogo;
	private BitmapFont whitefont, shadow, font;
	private Animation redBirdsAnim, yellowBirdsAnim, blueBirdsAnim, greyBirdsAnim,
			arctinBirdsAnim, pinkBirdsAnim, greenBirdsAnim, purpleBirdAnim;
	private float gameWidth, gameHeight;

	// Tween stuff
	private TweenManager manager;
	private Value alpha = new Value();

	// Buttons
	private List<SimpleButton> menuButtons;
	private List<SimpleButton> gameoverButtons;
	private List<SimpleButton> shareBoardButtons;
	private Color transitionColor;
	
	private GlyphLayout glyphLayout;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		myWorld = world;

		this.midPointY = midPointY;
		this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();
		this.gameoverButtons = ((InputHandler) Gdx.input.getInputProcessor()).getGameoverButtons();
		this.shareBoardButtons = ((InputHandler) Gdx.input.getInputProcessor()).getShareBoardButtons();
		cam = new OrthographicCamera();
		cam.setToOrtho(true, AssetLoader.gameWidth, gameHeight);
		particleEffect = new ParticleEffect();
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		glyphLayout = new GlyphLayout();

		initGameObjects();
		initAssets();

		transitionColor = new Color();
		prepareTransition(255, 255, 255, .5f);
	}

	private void initGameObjects() {
		bird = myWorld.getBird();
		scroller = myWorld.getScroller();
		md1 = scroller.getMd1();
		md2 = scroller.getMd2();
		fg1 = scroller.getFg1();
		fg2 = scroller.getFg2();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
		pipe1 = scroller.getPipe1();
		pipe2 = scroller.getPipe2();
		pipe3 = scroller.getPipe3();
	}

	private void initAssets() {
		bg1 = AssetLoader.bg1;
		bg2 = AssetLoader.bg2;
		shareBoard = AssetLoader.shareBoard;
		middleground = AssetLoader.middleground;
		foreground = AssetLoader.foreground;
		grass = AssetLoader.grass;
		redBirdsAnim = AssetLoader.redBirdsAnim;
		yellowBirdsAnim=AssetLoader.yellowBirdsAnim;
		blueBirdsAnim=AssetLoader.blueBirdsAnim;
		greyBirdsAnim= AssetLoader.greyBirdsAnim;
		arctinBirdsAnim = AssetLoader.arctinBirdsAnim;
		greenBirdsAnim=AssetLoader.greenBirdsAnim;
		pinkBirdsAnim=AssetLoader.pinkBirdsAnim;
		purpleBirdAnim = AssetLoader.purpleBirdsAnim;
		birdMidR = AssetLoader.redBirds[1];
		birdMidY = AssetLoader.yellowBirds[1];
		birdMidB = AssetLoader.blueBirds[1];
		birdMidG = AssetLoader.greyBirds[1];
		birdMidA = AssetLoader.arctinBirds[1];
		birdMidGr = AssetLoader.greenBirds[1];
		birdMidP = AssetLoader.pinkBirds[1];
		birdMidPu = AssetLoader.pinkBirds[1];
		barup = AssetLoader.barup;
		bardown = AssetLoader.bardown;
		scoreboard = AssetLoader.scoreboard;
		bronzeMedel = AssetLoader.bronzeMedel;
		silverMedel = AssetLoader.silverMedel;
		goldMedel = AssetLoader.goldMedel;
		platinumMedel = AssetLoader.platinumMedel;
		taptap = AssetLoader.taptap;
		particleEffect = AssetLoader.particleEffect;
		bblogo = AssetLoader.bbLogo;
		gameWidth = AssetLoader.gameWidth;
		gameHeight = AssetLoader.gameHeight;
		whitefont = AssetLoader.whiteFont;
		shadow = AssetLoader.shadow;
		font = AssetLoader.font;
	}

	private void drawMidGround() {
		batcher.draw(middleground, md1.getX(), md1.getY(), md1.getWidth(), md1.getHeight());
		batcher.draw(middleground, md2.getX(), md2.getY(), md2.getWidth(), md2.getHeight());
	}

	private void drawForeGround() {
		batcher.draw(foreground, fg1.getX(), fg1.getY(), fg1.getWidth(), fg1.getHeight());
		batcher.draw(foreground, fg2.getX(), fg2.getY(), fg2.getWidth(), fg2.getHeight());
	}

	private void drawGrass() {
		// Draw the grass
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawPipes() {
		batcher.draw(barup, pipe1.getX(), pipe1.getY() + 150 + pipe1.getRandY(), pipe1.getWidth(), pipe1.getHeight());
		batcher.draw(bardown, pipe1.getX(), pipe1.getY() - 287 + pipe1.getRandY(), pipe1.getWidth(), pipe1.getHeight());

		batcher.draw(barup, pipe2.getX(), pipe2.getY() + 150 + pipe2.getRandY(), pipe2.getWidth(), pipe2.getHeight());
		batcher.draw(bardown, pipe2.getX(), pipe2.getY() - 287 + pipe2.getRandY(), pipe2.getWidth(), pipe2.getHeight());

		batcher.draw(barup, pipe3.getX(), pipe3.getY() + 150 + pipe3.getRandY(), pipe3.getWidth(), pipe3.getHeight());
		batcher.draw(bardown, pipe3.getX(), pipe3.getY() - 287 + pipe3.getRandY(), pipe3.getWidth(), pipe3.getHeight());
	}

	private void drawBirdCentered(float runTime) {

		switch (bird.getRandBirdUnlocked()) {

			case 1:
				batcher.draw(redBirdsAnim.getKeyFrame(runTime), (AssetLoader.gameWidth/2)-(bird.getWidth()/2), bird.getY() - 35, bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				break;
			case 2:
				batcher.draw(yellowBirdsAnim.getKeyFrame(runTime), (AssetLoader.gameWidth/2)-(bird.getWidth()/2), bird.getY() - 35, bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				break;

			case 3:
				batcher.draw(blueBirdsAnim.getKeyFrame(runTime), (AssetLoader.gameWidth/2)-(bird.getWidth()/2), bird.getY() - 35, bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				break;
			case 4:
				batcher.draw(greyBirdsAnim.getKeyFrame(runTime), (AssetLoader.gameWidth/2)-(bird.getWidth()/2), bird.getY() - 35, bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				break;
			case 5:
				batcher.draw(arctinBirdsAnim.getKeyFrame(runTime), (AssetLoader.gameWidth/2)-(bird.getWidth()/2), bird.getY() - 35, bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				break;
			case 6:
				batcher.draw(greenBirdsAnim.getKeyFrame(runTime), (AssetLoader.gameWidth/2)-(bird.getWidth()/2), bird.getY() - 35, bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				break;
			case 7:
				batcher.draw(pinkBirdsAnim.getKeyFrame(runTime), (AssetLoader.gameWidth/2)-(bird.getWidth()/2), bird.getY() - 35, bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				break;
			case 8:
				batcher.draw(purpleBirdAnim.getKeyFrame(runTime), (AssetLoader.gameWidth/2)-(bird.getWidth()/2), bird.getY() - 35, bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				break;

			default:
				batcher.draw(redBirdsAnim.getKeyFrame(runTime), (AssetLoader.gameWidth/2)-(bird.getWidth()/2), bird.getY() - 35, bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				break;
		}
	}

	private void drawBird(float runTime) {
		int no;
		if(AssetLoader.getLocked()==false){
			no=bird.getRandBirdLocked();
		}else{
			no = bird.getRandBirdUnlocked();
		}

		switch (no) {

		case 1:

			if (bird.shouldntFlap()) {
				batcher.draw(birdMidR, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
						bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

			} else {
				batcher.draw(redBirdsAnim.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
			}
			break;
		case 2:

			if (bird.shouldntFlap()) {
				batcher.draw(birdMidY, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
						bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

			} else {
				batcher.draw(yellowBirdsAnim.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
			}
			break;

		case 3:

			if (bird.shouldntFlap()) {
				batcher.draw(birdMidB, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
						bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

			} else {
				batcher.draw(blueBirdsAnim.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
			}
			break;
		case 4:

			if (bird.shouldntFlap()) {
				batcher.draw(birdMidG, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
						bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

			} else {
				batcher.draw(greyBirdsAnim.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
			}
			break;
			case 5:

				if (bird.shouldntFlap()) {
					batcher.draw(birdMidA, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
							bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

				} else {
					batcher.draw(arctinBirdsAnim.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
							bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				}
				break;
			case 6:

				if (bird.shouldntFlap()) {
					batcher.draw(birdMidGr, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
							bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

				} else {
					batcher.draw(greenBirdsAnim.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
							bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				}
				break;
			case 7:

				if (bird.shouldntFlap()) {
					batcher.draw(birdMidP, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
							bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

				} else {
					batcher.draw(pinkBirdsAnim.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
							bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				}
				break;
			case 8:

				if (bird.shouldntFlap()) {
					batcher.draw(birdMidPu, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
							bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

				} else {
					batcher.draw(purpleBirdAnim.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
							bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
				}
				break;

		default:

			if (bird.shouldntFlap()) {
				batcher.draw(birdMidR, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
						bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

			} else {
				batcher.draw(redBirdsAnim.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
						bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
			}

			break;
		}

	}

	private void drawMenuUI() {
	
		batcher.draw(bblogo, ((gameWidth / 2) - 130), midPointY - 130, 260, 50);
		for (SimpleButton button : menuButtons) {
			button.draw(batcher);
		}

	}

	private void drawGameOverUI() {

		for (SimpleButton button : gameoverButtons) {
			button.draw(batcher);
		}

	}

	private void drawScoreboard() {
		batcher.draw(scoreboard, ((gameWidth / 2) - 115), midPointY - 80, 230, 120);


		if (AssetLoader.getHighScore() >= 2 && AssetLoader.getHighScore()<12) {
			batcher.draw(bronzeMedel, ((gameWidth / 2) - 86), midPointY - 34, 41, 41);
		}

		if (AssetLoader.getHighScore() >= 12 && AssetLoader.getHighScore()<50) {
			batcher.draw(silverMedel, ((gameWidth / 2) - 86), midPointY - 34, 41, 41);
		}

		if (AssetLoader.getHighScore() >= 50 && AssetLoader.getHighScore()<120) {
			batcher.draw(goldMedel, ((gameWidth / 2) - 86), midPointY - 34, 41, 41);
		}

		if (AssetLoader.getHighScore() >= 120) {
			batcher.draw(platinumMedel, ((gameWidth / 2) - 86), midPointY - 34, 41, 41);
		}
		
		String scorei = String.valueOf(myWorld.getScore());
		glyphLayout.setText(whitefont, scorei);
		whitefont.draw(batcher, glyphLayout, (gameWidth / 2) + 92 - glyphLayout.width, midPointY - 44);
		String highscorei = String.valueOf(AssetLoader.getHighScore());
		glyphLayout.setText(whitefont, highscorei);
		whitefont.draw(batcher, glyphLayout, (gameWidth / 2)+ 92- glyphLayout.width, midPointY );

	}

	private void drawReady() {
		glyphLayout.setText(shadow, "GET READY!");
		shadow.draw(batcher, glyphLayout, ((gameWidth / 2) - glyphLayout.width/2), midPointY - 108);
		glyphLayout.setText(font, "GET READY!");
		font.draw(batcher, glyphLayout, ((gameWidth / 2) - glyphLayout.width/2), midPointY - 110);
		batcher.draw(taptap, ((gameWidth / 2) - 65), midPointY -45, 130, 112);
	}

	private void drawGameOver() {
		glyphLayout.setText(shadow, "GAME OVER");
		shadow.draw(batcher, glyphLayout, ((gameWidth / 2) - glyphLayout.width/2), midPointY - 136);
		glyphLayout.setText(font, "GAME OVER");
		font.draw(batcher, glyphLayout, ((gameWidth / 2) - glyphLayout.width/2), midPointY - 138);
		
	}

	private void drawScore() {
		glyphLayout.setText(shadow, "" + myWorld.getScore());
		shadow.draw(batcher, glyphLayout, (gameWidth / 2) - glyphLayout.width/2, midPointY - 181);
		glyphLayout.setText(font, "" + myWorld.getScore());
		font.draw(batcher, glyphLayout, (gameWidth / 2) - glyphLayout.width/2, midPointY - 183);
	}

	private void drawHighScore() {
		glyphLayout.setText(shadow, "HIGH SCORE");
		shadow.draw(batcher, glyphLayout, ((gameWidth / 2) - glyphLayout.width/2), midPointY - 136);
		glyphLayout.setText(font, "HIGH SCORE");
		font.draw(batcher, glyphLayout, ((gameWidth / 2) - glyphLayout.width/2), midPointY - 138);
	}

	private void drawAchievements() {
		if (BirdBeats.adsController.isSignedIn()) {
			BirdBeats.adsController.submitScore(myWorld.getScore());
		}
	}

	public void render(float delta, float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		particleEffect.update(Gdx.graphics.getDeltaTime());
		shapeRenderer.begin(ShapeType.Filled);

		// Draw Background color
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.rect(0, 0, gameWidth, midPointY + 146);

		// Draw Grass
		shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 146, (int) gameWidth, 24);

		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 156, (int) gameWidth, 124);

		shapeRenderer.end();

		batcher.begin();
		batcher.disableBlending();

		if (pipe1.getRandNo() == 1) {
			batcher.draw(bg1, 0, 0, gameWidth, 512);
		} else if (pipe1.getRandNo() == 2) {
			batcher.draw(bg2, 0, 0, gameWidth, 512);
		} else {
			batcher.draw(bg2, 0, 0, gameWidth, 512);
		}

		batcher.enableBlending();
		if (myWorld.isMenu() || myWorld.isRunning() || myWorld.isReady()) {
			particleEffect.draw(batcher);
		}
		drawMidGround();
		
		drawForeGround();
		drawPipes();
		if (myWorld.isRunning()) {
			drawBird(runTime);
			drawScore();
		} else if (myWorld.isReady()) {
			drawBird(runTime);
			drawReady();
			drawScore();
		} else if (myWorld.isMenu()) {
			drawBirdCentered(runTime);
			drawMenuUI();
		} else if (myWorld.isGameOver()) {
			drawScoreboard();
			drawAchievements();
			drawBird(runTime);
			drawGameOver();
			drawGameOverUI();
		} else if (myWorld.isHighScore()) {
			drawScoreboard();
			drawAchievements();
			drawBird(runTime);
			drawHighScore();
			drawGameOverUI();
		}else if(myWorld.isShareBoard()){
			drawShareBoard(runTime);
		}

		drawGrass();

		batcher.end();
		drawTransition(delta);

	}

	public void prepareTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0).ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(transitionColor.r, transitionColor.g, transitionColor.b, alpha.getValue());
			shapeRenderer.rect(0, 0, gameWidth, gameHeight);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

		}
	}

	private void drawShareBoard(float runtime){
		batcher.draw(shareBoard,(AssetLoader.gameWidth/2)-(shareBoard.getRegionWidth()/2),
				(AssetLoader.gameHeight/2)-(shareBoard.getRegionHeight()/2),
				shareBoard.getRegionWidth(),
				shareBoard.getRegionHeight());
		batcher.draw(purpleBirdAnim.getKeyFrame(runtime),
				((AssetLoader.gameWidth / 2) - (bird.getWidth() / 2))-90,
				10 * (float) Math.sin(7 * runtime+10) + ((gameHeight/2)-75 ),
				bird.getWidth() / 1f, bird.getHeight() / 1f);
		batcher.draw(greenBirdsAnim.getKeyFrame(runtime),
				(AssetLoader.gameWidth / 2) - (bird.getWidth() / 2)-30,
				10 * (float) Math.sin(7 * runtime+20) + ((gameHeight/2)-75),
				bird.getWidth() / 1f, bird.getHeight() / 1f);
		batcher.draw(pinkBirdsAnim.getKeyFrame(runtime),
				((AssetLoader.gameWidth / 2) - (bird.getWidth() / 2))+30,
				10 * (float) Math.sin(7 * runtime+30) + ((gameHeight/2)-75),
				bird.getWidth() / 1f, bird.getHeight() / 1f);
		batcher.draw(arctinBirdsAnim.getKeyFrame(runtime),
				((AssetLoader.gameWidth / 2) - (bird.getWidth() / 2))+90,
				10 * (float) Math.sin(7 * runtime+40) + ((gameHeight/2)-75),
				bird.getWidth() / 1f, bird.getHeight() / 1f);
		for (SimpleButton button : shareBoardButtons) {
			button.draw(batcher);
		}

	}

}
