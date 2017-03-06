package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Puoskari on 1.3.2017.
 */

public class GameScreen implements Screen {
    Program host;
    SpriteBatch batch;
    OrthographicCamera camera;
    MapMaker mapMaker;
    Ekroos ekroos;
    String[] themes;
    String currenTheme;


    public GameScreen(Program host) {
        this.host = host;
        batch = host.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f, 5f);
        themes = new String[3];
        themes[0] = "liila";
        themes[1] = "turkoosi";
        themes[2] = "kelta";
        currenTheme = themes[1];
        mapMaker = new MapMaker(currenTheme);
        mapMaker.createMap();
        ekroos = new Ekroos(1f, 1f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapMaker.mapMove();
        useGravity();
        checkForThemeChange();
        batch.begin();
        mapMaker.draw(batch);
        ekroos.draw(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mapMaker.dispose();
    }

    public void useGravity() {
       ekroos.gravityPull(mapMaker.getIfOnBasicTile(ekroos.get_x(), ekroos.get_y()));
    }

    public void checkForThemeChange() {
        if (mapMaker.getTilesCreatedInCurrentTheme() >= 23) {
            mapMaker.setTilesCreatedInCurrentTheme(0);
            themeChange();
        }
    }

    public void themeChange() {
        int a = MathUtils.random(2);

        Gdx.app.log("theme is now", themes[a]);
        mapMaker.setTheme(themes[a]);
    }
}
