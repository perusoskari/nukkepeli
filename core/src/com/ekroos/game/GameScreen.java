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
    private Program host;
    SpriteBatch batch;
    private OrthographicCamera camera;
    private MapMaker mapMaker;
    private Ekroos ekroos;
    private String[] themes;
    private String currenTheme;
    private TouchGrid touchGrid;


    public GameScreen(Program host) {
        this.host = host;
        batch = host.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f, 5f);
        createThemes();
        currenTheme = themes[0];
        mapMaker = new MapMaker(currenTheme);
        mapMaker.createMap();
        touchGrid = new TouchGrid(camera, batch, mapMaker.getTrapTiles());
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
        touchGrid.checkPanStart();
        touchGrid.touchPositionMove();
        batch.begin();
        mapMaker.draw(batch);
        touchGrid.drawGrid();
        ekroos.draw(batch);
        batch.end();
        touchGrid.drawLine();
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
        touchGrid.dispose();
    }

    /**
     * Make ekroos fall if she is not on top of basic tile
     */
    public void useGravity() {
        boolean boxHelpUnder = false;

        Array<BoxDollHelp> list = touchGrid.dolls.getBoxHelps();


        for (int i = 0;i < list.size;i++) {
            float correctHeight = mapMaker.getTrapTiles().get(0).getRectangle().getHeight();

            if (list.get(i).isLock()) {

                if (list.get(i).getRectangle().setY(correctHeight).overlaps(ekroos.getRectangle())) {
                    boxHelpUnder = true;
                }
            }
        }

       ekroos.gravityPull(mapMaker.getIfOnBasicTile(ekroos.get_x(), ekroos.get_y()),
               boxHelpUnder, mapMaker.getBasicTile());
    }

    /**
     * creates the themes duh
     */
    public void createThemes() {
        themes = new String[3];
        themes[0] = "liila";
        themes[1] = "turkoosi";
        themes[2] = "kelta";
    }

    /**
     * set theme to random one (all themes should be in "themes" array)
     */
    public void themeChange() {
        int a = MathUtils.random(2);

        Gdx.app.log("theme is now", themes[a]);
        mapMaker.setTheme(themes[a]);
    }

    /**
     * Checks if current theme has been rolling for set amount of tiles
     * if it has, change theme.
     */
    public void checkForThemeChange() {
        if (mapMaker.getTilesCreatedInCurrentTheme() >= 23) {
            mapMaker.setTilesCreatedInCurrentTheme(0);
            themeChange();
        }
    }
}
