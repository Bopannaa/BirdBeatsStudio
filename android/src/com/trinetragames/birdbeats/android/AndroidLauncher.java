package com.trinetragames.birdbeats.android;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.trinetragames.birdbeats.AdsController;
import com.trinetragames.birdbeats.BirdBeats;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class AndroidLauncher extends AndroidApplication implements AdsController, GameHelper.GameHelperListener {

	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-9891028280350848/5947211013";
	Tracker globaltracker;
	AdView bannerAd;
	private GameHelper gameHelper;
	private static final int REQUEST_CODE_UNUSED = 9002;
	private CallbackManager callbackManager;
	private LoginManager loginManager;
	private AccessToken accessToken;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
		globaltracker = analytics.newTracker(R.xml.global_tracker);
		globaltracker.enableAdvertisingIdCollection(true);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new BirdBeats(this), config);
		setupAds();
		setUpFacebook();
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(bannerAd, params);

		setContentView(layout);
		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}
		gameHelper.setMaxAutoSignInAttempts(0);
		gameHelper.setup(this);

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	public void setupAds() {
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(0xff000000); // black
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				bannerAd.loadAd(ad);
			}
		});

	}

	@Override
	public void hideBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public void showAds() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}

	@Override
	public void setTrackerScreenName(String path) {

		// TODO Auto-generated method stub
		// Set screen name.
		// Where path is a String representing the screen name.
		globaltracker.setScreenName(path);
		globaltracker.send(new HitBuilders.AppViewBuilder().build());
	}

	@Override
	public void onStart() {
		super.onStart();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
		gameHelper.onStart(this);
		// ...
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"AndroidLauncher Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.trinetragames.birdbeats.android/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"AndroidLauncher Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.trinetragames.birdbeats.android/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		// ...
		GoogleAnalytics.getInstance(this).reportActivityStop(this);
		gameHelper.onStop();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.disconnect();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				// @Override
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				// @Override
				public void run() {
					gameHelper.signOut();
				}
			});
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		final String appPackageName = getPackageName();
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
		} catch (ActivityNotFoundException anfe) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
		}
	}

	@Override
	public void submitScore(long score) {
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
			return;
		} else {
			signIn();
			return;
		}
	}

	@Override
	public void showScores() {
		if (isSignedIn() == true) {
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
			return;
		} else {
			signIn();
			return;
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void shareLink(String title, String message) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, message);
		try {
			Intent finalIntent = Intent.createChooser(share, title);
			startActivity(finalIntent);
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void noNetworkConnection() {
		runOnUiThread(new Runnable() {
			// @Override
			public void run() {
				Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void connectFacebook() {
		loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
	}

	@Override
	public void disconnectFacebook() {
		loginManager.logOut();
	}

	@Override
	public boolean isConnectedFacebook() {
		if (accessToken.getCurrentAccessToken() != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void facebookShare() {
		final String appPackageName = getPackageName();
		ShareLinkContent content = new ShareLinkContent.Builder()
				.setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName))
				.setContentDescription("Play this game")
				.setContentTitle("Bird Beats")
				.build();
		ShareDialog shareDialog = new ShareDialog(this);
		shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub

	}

	public void setUpFacebook()
	{
		// First initialize the Facebook SDK
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(getApplication());

		// create the callback manager
		callbackManager = CallbackManager.Factory.create();

		// create the access token
		accessToken = AccessToken.getCurrentAccessToken();

		// create the login manager
		loginManager = LoginManager.getInstance();

		// create the callback for the login manager
		loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
		{
			@Override
			public void onSuccess(LoginResult loginResult)
			{
				// Your app code goes here for when a login is successful.
				// here I update the access token with the login result.
				accessToken.setCurrentAccessToken(loginResult.getAccessToken());
			}

			@Override
			public void onCancel()
			{
				// your app code goes here for when the login is cancelled
				// either by the user or by the Facebook SDK
			}

			@Override
			public void onError(FacebookException exception)
			{
				// your app code goes here for a login exception error
				// such as permissions were denied or another error.
			}
		});
	}

}
