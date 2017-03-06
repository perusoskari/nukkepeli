package com.ekroos.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Program extends Game implements ApplicationListener {
	SpriteBatch batch;
	Texture img;
	MainMenu mainMenu;
    GameScreen gameScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        setScreen(getMainMenu());
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
        super.render();

        /**
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
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
		img.dispose();
	}

    public SpriteBatch getBatch() {
        return batch;
    }

    public MainMenu getMainMenu() {
        return new MainMenu(this);
    }

    public GameScreen getGameScreen() {
        return new GameScreen(this);
    }
}
