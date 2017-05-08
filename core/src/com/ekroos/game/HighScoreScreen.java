package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Ville on 9.4.2017.
 */

public class HighScoreScreen implements Screen {

    private Program host;
    SpriteBatch batch;

    Texture highScoreScreenArt;
    Texture returnToMainArt;

    private Rectangle highScoreScreenRectangle;
    private Rectangle returnToMainRectangle;

    GlyphLayout returnToMainGlyph;
    GlyphLayout playerAndScoreGlyph;
    GlyphLayout highScoreHeaderGlyph;
    CharSequence highScoreHeaderChar;
    CharSequence returnToMainChar;
    CharSequence playerAndScoreChar;
    Bundlenator myBundle;
    private BitmapFont font;
    private BitmapFont scoreFont;

    private OrthographicCamera camera;
    private Vector3 touchPos;
    private float decisionTime;

    ArrayList<Integer> scores;
    String intToString;
    CharSequence intToChar;

    float x;
    float y;

    SoundManager soundManager;

    public HighScoreScreen(Program host) {

        this.host = host;
        batch = host.getBatch();
        soundManager = host.getSoundManager();

        myBundle = new Bundlenator();
        font = myBundle.getFont();
        scoreFont = myBundle.getNoNonsenseFont();

        localizeText();

        returnToMainGlyph = new GlyphLayout(myBundle.getHighlyVisibleFont(), returnToMainChar);
        highScoreHeaderGlyph = new GlyphLayout(scoreFont, highScoreHeaderChar);
        highScoreScreenArt = new Texture(Gdx.files.internal("buttonsAndMenu/highScoreScreenArt.png"));
        returnToMainArt = new Texture(Gdx.files.internal("buttonsAndMenu/multiButton.png"));

        highScoreScreenRectangle = new Rectangle(0,0, highScoreScreenArt.getWidth(), highScoreScreenArt.getHeight());

        returnToMainRectangle = new Rectangle(highScoreScreenRectangle.getWidth() - returnToMainArt.getWidth() * 1.25f ,
                highScoreScreenRectangle.y + returnToMainArt.getHeight() / 2,
                returnToMainArt.getWidth(), returnToMainArt.getHeight());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, highScoreScreenArt.getWidth(), highScoreScreenArt.getHeight());

        touchPos = new Vector3();
        decisionTime = 0;

        scores = new ArrayList<Integer>();
        intToString = "";

        initializeScores();

        playerAndScoreChar = "";
        playerAndScoreGlyph = new GlyphLayout(scoreFont, playerAndScoreChar);

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
        drawScores(batch);
        batch.end();

    }
    public void draw(SpriteBatch batch) {

        batch.draw(highScoreScreenArt, highScoreScreenRectangle.x, highScoreScreenRectangle.y,
                highScoreScreenRectangle.getWidth(), highScoreScreenRectangle.getHeight());

        batch.draw(returnToMainArt, returnToMainRectangle.x, returnToMainRectangle.y,
                returnToMainRectangle.getWidth(), returnToMainRectangle.getHeight());

        myBundle.getHighlyVisibleFont().draw(batch, returnToMainGlyph, returnToMainRectangle.x +
                returnToMainRectangle.width / 2 - returnToMainGlyph.width / 2,
                returnToMainRectangle.getHeight() + returnToMainGlyph.height / 2);

        scoreFont.draw(batch, highScoreHeaderGlyph,
                highScoreScreenArt.getWidth() / 2 - highScoreHeaderGlyph.width / 2,
                highScoreScreenArt.getHeight() - highScoreHeaderGlyph.height / 2);

    }
    public void drawScores(SpriteBatch batch) {

        y = highScoreScreenArt.getHeight() - highScoreHeaderGlyph.height * 3;

        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                intToChar = " " + 1 + ".   " + scores.get(i);
            }
            else if (i > 0) {
                intToChar = " " + (i + 1) + ".   " + scores.get(i);
            }

            playerAndScoreChar =  intToChar;
            playerAndScoreGlyph.setText(scoreFont,playerAndScoreChar);
            scoreFont.draw(batch, playerAndScoreGlyph, highScoreScreenRectangle.getWidth() / 3, y);
            y -= playerAndScoreGlyph.height * 1.75;
        }
    }

    public void initializeScores() {
        for (int i = 0; i < 10; i++) {
            intToString = "Score" + i;
            scores.add(host.getHighScores().getInteger(intToString));

        }

    }
    public ArrayList<Integer> getScores() {
        dispose();
        return scores;
    }
    /**
     * This method puts text in Strings, also allows easy localization later
     */
    public void localizeText() {

        returnToMainChar = myBundle.getLocal("return");
        highScoreHeaderChar = myBundle.getLocal("highscore");
    }

    //Getting the touch position
    public void getTouchPos() {
        decisionTime += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isTouched()) {

            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.input.getY();
            camera.unproject(touchPos);
        }
    }

    public void whatIsTouched() {

        if (Gdx.input.isTouched()) {

            if (returnToMainRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f) {
                host.setScreen(host.getMainMenu());
                decisionTime = 0;
                soundManager.playSound("buttonPush", 0.4f);
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
        highScoreScreenArt.dispose();
        returnToMainArt.dispose();
    }
}