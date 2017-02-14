package pocong.adventure.game;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.menus.CCMenuItemToggle;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.KeyEvent;

public class MenuLayer extends CCLayer {

	public MenuLayer(boolean playMusic)
	{
		if (playMusic)
		{
			if (G.bgSound.isPlaying()) G.bgSound.pause();
			G.bgSound = G.soundMenu;
			if (G.music) G.bgSound.start();
		}
			
		setScale(G.scale);
		setAnchorPoint(0, 0);
		
		// background
		CCSprite bg = new CCSprite("menu/menu_bg.png");
		bg.setPosition(G.displayCenter());
		addChild(bg);
		
		CCSprite bird = new CCSprite("menu/bird.png");
		bird.setAnchorPoint(0, 0);
		bird.setPosition(0, 0);
		addChild(bird);


		CCSprite title = new CCSprite("menu/title.png");
		title.setPosition(G.width*0.47f, G.height*0.73f);
		addChild(title);

		// buttons
		CCMenuItemImage play = CCMenuItemImage.item("menu/play1.png", "menu/play2.png", this, "onPlay");
		play.setAnchorPoint(1, 0);
		play.setPosition(G.width, 0);
		
		CCMenuItemImage rate = CCMenuItemImage.item("menu/rate1.png", "menu/rate2.png", this, "onRate");
		rate.setPosition(G.width*0.2f, 70);
				
		CCMenuItemImage more = CCMenuItemImage.item("menu/more1.png", "menu/more2.png", this, "onMore");
		more.setPosition(G.width*0.3f, 70);
		
		/*CCMenuItemToggle sound = CCMenuItemToggle.item(this, "onSound",
			CCMenuItemImage.item("menu/sound_off.png", "menu/sound_off.png"),
			CCMenuItemImage.item("menu/sound_on.png", "menu/sound_on.png"));
		sound.setSelectedIndex(G.sound?1:0);
		sound.setPosition(G.width*0.4f, 70);*/

		CCMenuItemToggle music = CCMenuItemToggle.item(this, "onMusic",
			CCMenuItemImage.item("menu/music_off.png", "menu/music_off.png"),
			CCMenuItemImage.item("menu/music_on.png", "menu/music_on.png"));
		music.setSelectedIndex(G.music?1:0);
		music.setPosition(G.width*0.5f, 70);
	/*
		CCMenuItemImage help = CCMenuItemImage.item("menu/help1.png", "menu/help2.png", this, "onHelp");
		help.setPosition(G.width*0.6f, 70);*/

		CCMenu menu = CCMenu.menu(play, music, rate, more);
		menu.setPosition(0, 0);
		addChild(menu);
		
		setIsKeyEnabled(true);
	}
	
	public void onPlay(Object sender)
	{
		if( G.sound ) G.soundClick.start();
		
		CCScene s = CCScene.node();
		s.addChild(new SelectLayer(false));
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, s));
	}


	public void onSound(Object sender)
	{
		if( G.sound ) G.soundClick.start();

		G.sound = !G.sound;
		SharedPreferences sp = CCDirector.sharedDirector().getActivity().getSharedPreferences("GameInfo", 0);
		SharedPreferences.Editor  et = sp.edit();
		et.putBoolean("sound", G.sound);
		et.commit();
	}

	public void onMusic(Object sender)
	{
		if( G.sound ) G.soundClick.start();
		
		G.music = !G.music;
		SharedPreferences sp = CCDirector.sharedDirector().getActivity().getSharedPreferences("GameInfo", 0);
		SharedPreferences.Editor  et = sp.edit();
		et.putBoolean("music", G.music);
		et.commit();

		if (G.music)
		{
			G.bgSound.start();
		}
		else
		{
			G.bgSound.pause();
		}
	}

	public void onHelp(Object sender)
	{
		if( G.sound ) G.soundClick.start();

		CCScene s = CCScene.node();
		s.addChild(new HelpLayer());
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, s));
	}
	
	public void onRate(Object sender)
	 { 
		if( G.sound ) G.soundClick.start();
		Context context = CCDirector.sharedDirector().getActivity(); 
		try 
		{ 
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()))); 
		} 
		catch(Exception e)
		{ 
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName()))); 
				
		} 

		}

	
	public void onMore(Object sender)
	 { 
		if( G.sound ) G.soundClick.start();
		Context context = CCDirector.sharedDirector().getActivity(); 
		try 
		{ 
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=asywalul")));
		} 
		catch(Exception e)
		{ 
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=asywalul")));
				
		} 
		}
	
	public boolean ccKeyDown(int keyCode, KeyEvent event)
	{

		MainActivity.app.showInterstitialAds();
		int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
		Runtime r = Runtime.getRuntime();
		r.gc();
		System.gc();
		return true;
	}


	
}
