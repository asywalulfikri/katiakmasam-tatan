package pocong.adventure.game;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class victory extends CCLayer {

	private int _levelMode;

	public victory(int levelMode)
	{
		setScale(G.scale);
		setAnchorPoint(0, 0);
		_levelMode = levelMode+1;
		// background
		Object[] arrobject2 = new Object[]{this._levelMode};
		CCSprite bg = new CCSprite(String.format((String)"game/game_bg%d.png", (Object[])arrobject2));
		bg.setPosition(G.displayCenter());
		addChild(bg);

		CCSprite help = new CCSprite("game/nextworld.png");
		help.setScale(Math.min(G.display_w/1280, G.display_h/800) / G.scale);
		help.setPosition(G.displayCenter());
		addChild(help);

		// buttons
		CCMenuItemImage back = CCMenuItemImage.item("menu/back1.png", "menu/back2.png", this, "onBack");
		back.setPosition(G.width*0.09f, G.height*0.06f);

		CCMenu menu = CCMenu.menu(back);
		menu.setPosition(0, 0);
		addChild(menu);
		
		setIsTouchEnabled(true);
		setIsKeyEnabled(true);
	}

	public void onBack(Object sender)
	{
		if( G.sound ) G.soundClick.start();
		MainActivity.app.showInterstitialAds();
		MainActivity.app.closebanner();
		CCScene s = CCScene.node();
		s.addChild(new SelectLayer(true));
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, s));
	}
	
	public boolean ccTouchesEnded(MotionEvent event)
	{
		onBack(null);
		return true;
	}

	public boolean ccKeyDown(int keyCode, KeyEvent event)
	{
		onBack(null);
		MainActivity.app.closebanner();
		return true;
	}
}
