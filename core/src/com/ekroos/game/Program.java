package com.ekroos.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class Program extends Game implements ApplicationListener {

	SpriteBatch batch;

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    FreeTypeFontGenerator.FreeTypeFontParameter infoParameter;

    Preferences highScores;

    SoundManager soundManager;


	@Override
	public void create () {
		batch = new SpriteBatch();
        soundManager = new SoundManager();

        // Generate font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("myFont.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        infoParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        highScores = Gdx.app.getPreferences("highScores");

        setScreen(getMainMenu());
	}
    public Preferences getHighScores() {
        return highScores;
    }

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
        super.render();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		batch.dispose();
        soundManager.dispose();
	}

    public SpriteBatch getBatch() {
        return batch;
    }

    public MainMenu getMainMenu() {
        return new MainMenu(this);
    }

    public HelpScreen getHelpScreen() {
        return new HelpScreen(this);
    }

    public GameScreen getGameScreen() {
        return new GameScreen(this);
    }

    public HighScoreScreen getHighScoreScreen() {
            return new HighScoreScreen(this);
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}
