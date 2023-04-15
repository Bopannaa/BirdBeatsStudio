package com.trinetragames.birdbeats;

import com.badlogic.gdx.Game;
import com.trinetragames.helpers.AssetLoader;
import com.trinetragames.screens.SplashScreen;

public class BirdBeats extends Game{
public static AdsController adsController;
	
	public BirdBeats(AdsController adsController){
        if (adsController != null) {
        	BirdBeats.adsController = adsController;
        } else {
        	BirdBeats.adsController = new DummyAdsController();
        }
    }
	
	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
		if(adsController.isNetworkConnected()) {
			adsController.showBannerAd();
			adsController.setTrackerScreenName("com.trinetragames.birdbeats.BirdBeats");}
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
