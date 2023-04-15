package com.trinetragames.gameobject;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends Scrollable {

	private Random r;
	
	private int randNo;

	private Rectangle barUp, barDown;

	public static final int VERTICAL_GAP = 99;
	//private float groundY;

	private boolean isScored = false;
	
	private int randY = 100;
	


	// When Pipe's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Pipe(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		// Initialize a Random object for Random number generation

		
		r = new Random();
		barUp = new Rectangle();
		barDown = new Rectangle();

		//this.groundY = groundY;
	}

	@Override
	public void update(float delta) {
		// Call the update method in the superclass (Scrollable)
		super.update(delta);

		// The set() method allows you to set the top left corner's x, y
		// coordinates,
		// along with the width and height of the rectangle

		//barUp.set(position.x, position.y, width, height);
		//barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
				//groundY - (position.y + height + VERTICAL_GAP));
		barUp.set(position.x, position.y + 150 + randY, 60, 337);
		barDown.set(position.x, position.y - 287 + randY, 60, 337);


	}

	@Override
	public void reset(float newX) {
		// Call the reset method in the superclass (Scrollable)
		super.reset(newX);
		// Change the height to a random number
		randY = r.nextInt(175);
		isScored = false;
	}
	
	public int getRandY() {
		return randY;
	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x);
		randNo = r.nextInt(2) + 1;
		//AssetLoader.setHighScore(0);
	}


	public Rectangle getBarUp() {
		return barUp;
	}

	public Rectangle getBarDown() {
		return barDown;
	}
	
	public int getRandNo() {
		return randNo;
	}

	public boolean collides(Bird bird) {
		if (position.x < bird.getX() + bird.getWidth()) {
			return (Intersector.overlaps(bird.getBoundingCircle(), barUp)
					|| Intersector.overlaps(bird.getBoundingCircle(), barDown));
		}
		return false;
	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean b) {
		isScored = b;
	}
}
