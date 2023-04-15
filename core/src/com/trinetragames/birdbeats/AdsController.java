package com.trinetragames.birdbeats;

public interface AdsController {
	

	public void showBannerAd();
	public void hideBannerAd();
	public void showAds();
	public boolean isNetworkConnected();
	public void setTrackerScreenName(String path);
	public void signIn();
	public void signOut();
	public void rateGame();
	public void submitScore(long score);
	public void showScores();
	public boolean isSignedIn();
	public void shareLink(String title, String message);
	public void noNetworkConnection();
	public void connectFacebook();
	public void disconnectFacebook();
	public boolean isConnectedFacebook();
	public  void facebookShare();
}
