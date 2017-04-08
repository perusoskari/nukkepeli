package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Ville on 3.4.2017.
 */

public class HelpScreen implements Screen {

    private Program host;
    SpriteBatch batch;
    Texture helpScreenArt;
    private Rectangle helpScreenRectangle;
    private OrthographicCamera camera;
    private Vector3 touchPos;
    private float decisionTime;

    public HelpScreen(Program host) {

        this.host = host;
        batch = host.getBatch();

        helpScreenArt = new Texture("helpScreen.png");
        helpScreenRectangle = new Rectangle(0,0,helpScreenArt.getWidth(), helpScreenArt.getHeight());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, helpScreenArt.getWidth(), helpScreenArt.getHeight());
        touchPos = new Vector3();
        decisionTime = 0;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getTouchPos();
        whatIsTouched();
        batch.begin();
        draw(batch);
        batch.end();

    }
    public void draw(SpriteBatch batch) {

        batch.draw(helpScreenArt, helpScreenRectangle.x, helpScreenRectangle.y,
                helpScreenRectangle.getWidth(), helpScreenRectangle.getHeight());
    }
    //Getting the touch position
    public void getTouchPos() {
        decisionTime += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isTouched()) {

            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.input.getY();
            camera.unproject(touchPos);

            System.out.println("X: " + touchPos.x);
            System.out.println("Y: " + touchPos.y);

        }
    }
    //Check what is touched using Rectangles of the main screen buttons
    public void whatIsTouched() {

        if (Gdx.input.isTouched()) {

            if (helpScreenRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f) {
                host.setScreen(host.getMainMenu());
                decisionTime = 0;
                dispose();
            }
        }
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
        helpScreenArt.dispose();
    }
}
