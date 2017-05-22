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
    Preferences options;

    SoundManager soundManager;


    /**
     * The ringmaster.
     */
	@Override
	public void create () {
		batch = new SpriteBatch();
        soundManager = new SoundManager();

        // Generate font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("myFont.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        infoParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        highScores = Gdx.app.getPreferences("highScores");

        options = Gdx.app.getPreferences("options");

        if (!options.getString("music").equals("off")) {
            options.putString("music", "on");
            options.flush();
        }
        System.out.println(options.getString("music"));


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

    /**
     * Disposes the sprite batch and sounds manager.
     */
	@Override
	public void dispose () {
		batch.dispose();
        soundManager.dispose();
	}

    /**
     * Get the sprite batch.
     * @return
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Returns main menu.
     * @return new mainmenu
     */
    public MainMenu getMainMenu() {
        return new MainMenu(this);
    }

    /**
     * Returns helpscreen.
     * @return new helpscreen.
     */
    public HelpScreen getHelpScreen() {
        return new HelpScreen(this);
    }

    /**
     * Retuns gamescreen.
     * @return new gamescreen.
     */
    public GameScreen getGameScreen() {
        return new GameScreen(this);
    }

    /**
     * Returns highscorescreen.
     * @return new highscorescreen.
     */
    public HighScoreScreen getHighScoreScreen() {
            return new HighScoreScreen(this);
    }

    /**
     * Returns the soundmanager.
     * @return soundmanager.
     */
    public SoundManager getSoundManager() {
        return soundManager;
    }
}
