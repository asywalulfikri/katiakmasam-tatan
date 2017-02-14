package pocong.adventure.game;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import android.content.SharedPreferences;
import android.view.KeyEvent;

public class SelectLayer extends CCLayer {

    int _level;
    int _levelMode;
    CCMenuItemImage decrease;
    int finalStage = 20;
    CCMenuItemImage increase;
    CCLabelAtlas levelLabel;
    int maxLevel;
    CCNode packPage;
    CCNode selectPage;
	
	public SelectLayer(boolean playMusic)
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

		createPackPage();
		createSelectPage();
		addChild(packPage);
		
		setIsKeyEnabled(true);
	}
	
	public void createPackPage()
	{
		packPage = CCNode.node();

		CCSprite title = new CCSprite("menu/level_pack.png");
		title.setAnchorPoint(0.5f, 1);
		title.setPosition(G.width*0.5f, G.height*0.95f);
		packPage.addChild(title);

		CCMenuItemImage beginning = CCMenuItemImage.item("menu/beginning1.png", "menu/beginning2.png", this, "onBeginning");
		beginning.setPosition(G.width*0.35f, G.height*0.7f);
		CCMenuItemImage evolution = CCMenuItemImage.item("menu/evolution1.png", "menu/evolution2.png", "menu/evolutiond.png", this, "onEvolution");
		evolution.setPosition(G.width*0.65f, G.height*0.7f);
		CCMenuItemImage experience = CCMenuItemImage.item("menu/experience1.png", "menu/experience2.png", "menu/experienced.png", this, "onExperience");
		experience.setPosition(G.width*0.35f, G.height*0.5f);
		CCMenuItemImage experience1 = CCMenuItemImage.item("menu/winter.png", "menu/winter1.png", "menu/winterd.png", this, "onExperience1");
		experience1.setPosition(G.width*0.65f, G.height*0.5f);
		CCMenuItemImage experience2 = CCMenuItemImage.item("menu/forest1.png", "menu/forest2.png", "menu/forestd.png", this, "onExperience2");
		experience2.setPosition(G.width*0.35f, G.height*0.3f);
		CCMenuItemImage experience3 = CCMenuItemImage.item("menu/beach.png", "menu/beach1.png", "menu/beachd.png", this, "onExperience3");
		experience3.setPosition(G.width*0.65f, G.height*0.3f);
		CCMenuItemImage back = CCMenuItemImage.item("menu/back1.png", "menu/back2.png", this, "onBack1");
		back.setPosition(G.width*0.09f, G.height*0.06f);

		CCMenu menu = CCMenu.menu(beginning, evolution, experience, experience1, experience2, experience3, back);
		menu.setPosition(0, 0);
		packPage.addChild(menu);
		
		SharedPreferences sharedPreferences = CCDirector.sharedDirector().getActivity().getSharedPreferences(String.format((String)"GameInfo", (Object[])new Object[0]), 0);
        int n2 = sharedPreferences.getInt(String.valueOf((Object)"GameLevel0"), 0);
        int n3 = sharedPreferences.getInt(String.valueOf((Object)"GameLevel1"), 0);
        int n4 = sharedPreferences.getInt(String.valueOf((Object)"GameLevel2"), 0);
        int n5 = sharedPreferences.getInt(String.valueOf((Object)"GameLevel3"), 0);
        int n6 = sharedPreferences.getInt(String.valueOf((Object)"GameLevel4"), 0);
        int n7 = sharedPreferences.getInt(String.valueOf((Object)"GameLevel5"), 0);
        if (n2 <= 20) {
        	evolution.setIsEnabled(false);
        	experience.setIsEnabled(false);
        	experience1.setIsEnabled(false);
        	experience2.setIsEnabled(false);
        	experience3.setIsEnabled(false);
        }
        if (n3 <= 20) {
        	experience.setIsEnabled(false);
        	experience1.setIsEnabled(false);
        	experience2.setIsEnabled(false);
        	experience3.setIsEnabled(false);
        }
        if (n4 <= 20) {
        	experience1.setIsEnabled(false);
        	experience2.setIsEnabled(false);
        	experience3.setIsEnabled(false);
        }
        if (n5 <= 20) {
        	experience2.setIsEnabled(false);
        	experience3.setIsEnabled(false);
        }
        if (n6 <= 20) {
        	experience3.setIsEnabled(false);
        }
	}

	public void onBeginning(Object sender)
	{
		showSelectPage(0);
	}

	public void onEvolution(Object sender)
	{
		showSelectPage(1);
	}

	public void onExperience(Object sender)
	{
		showSelectPage(2);
	}
	
	public void onExperience1(Object sender)
	{
		showSelectPage(3);
	}
	
	public void onExperience2(Object sender)
	{
		showSelectPage(4);
	}
	
	public void onExperience3(Object sender)
	{
		showSelectPage(5);
	}

	public void onBack1(Object sender)
	{
		if( G.sound ) G.soundClick.start();
	
		CCScene s = CCScene.node();
		s.addChild(new MenuLayer(false));
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, s));
	}

	public void showSelectPage(int mode)
	{
        if (G.sound) {
            G.soundClick.start();
        }
        _levelMode = mode;
		SharedPreferences sp = CCDirector.sharedDirector().getActivity().getSharedPreferences("GameInfo", 0);
		maxLevel = sp.getInt(String.format("GameLevel%d", _levelMode), 1);
        if (maxLevel > 20) {
            maxLevel = 20;
        }
        if (maxLevel > 1) {
            removeChild(this.packPage, false);
            addChild(this.selectPage);
            setLevel(this.maxLevel);
            return;
        }
        _level = 1;
        onStart(null);
	}

	// ===================================== select level ==============================================

	public void createSelectPage()
	{
		selectPage = CCNode.node();

		CCSprite title = new CCSprite("menu/level_select.png");
		title.setAnchorPoint(0.5f, 1);
		title.setPosition(G.width*0.5f, G.height*0.7f);
		selectPage.addChild(title);

		levelLabel = CCLabelAtlas.label("0", "font.png", 34, 43, '0');
		levelLabel.setAnchorPoint(0.5f, 0.5f);
		levelLabel.setPosition(G.width*0.5f, G.height*0.5f);
		selectPage.addChild(levelLabel);

		decrease = CCMenuItemImage.item("menu/arrow1.png", "menu/arrow2.png", this, "onDecrease");
		decrease.setPosition(G.width*0.5f-150, G.height*0.5f);
		increase = CCMenuItemImage.item("menu/arrow1.png", "menu/arrow2.png", this, "onIncrease");
		increase.setScaleX(-1);
		increase.setPosition(G.width*0.5f+150, G.height*0.5f);
		CCMenuItemImage start = CCMenuItemImage.item("menu/start1.png", "menu/start2.png", this, "onStart");
		start.setPosition(G.width*0.5f, G.height*0.3f);

		CCMenuItemImage back = CCMenuItemImage.item("menu/back1.png", "menu/back2.png", this, "onBack2");
		back.setPosition(G.width*0.09f, G.height*0.06f);

		CCMenu menu = CCMenu.menu(decrease, increase, start, back);
		menu.setPosition(0, 0);
		selectPage.addChild(menu);
	}

	public void setLevel(int level)
	{

	        _level = level;
			decrease.setOpacity(_level>1?255:128);
			decrease.setIsEnabled(_level>1);
			increase.setOpacity(_level<maxLevel?255:128);
			increase.setIsEnabled(_level<maxLevel);

			levelLabel.setString(String.format("%02d", _level));
	}

	public void onDecrease(Object sender)
	{
		if( G.sound ) G.soundClick.start();
		
		if (_level > 1)
		{
			setLevel(_level-1);
		}
	}

	public void onIncrease(Object sender)
	{
		if( G.sound ) G.soundClick.start();
		
		if (_level < maxLevel)
		{
			setLevel(_level+1);
		}
	}

    public void onStart(Object sender) {
        if (G.sound) {
            G.soundClick.start();
        }
        CCScene cCScene = CCScene.node();
        cCScene.addChild(new GameLayer(this._levelMode, this._level, true));
        CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, cCScene));
    }

    public void onBack2(Object sender) {
        if (G.sound) {
            G.soundClick.start();
        }
        CCScene cCScene = CCScene.node();
        cCScene.addChild(new MenuLayer(false));
        CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, cCScene));
    }

	public boolean ccKeyDown(int keyCode, KeyEvent event)
	{
		if (selectPage.getParent() == null)
		{
			onBack1(null);
		}
		else
		{
			onBack2(null);
		}
		return true;
	}
	
}
