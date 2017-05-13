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

/**
 * Creates a highscorescreen.
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

    /**
     * Creates a highscorescreen.
     * @param host is the main program
     */
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
        highScoreScreenArt = new Texture(Gdx.files.internal("buttonsAndMenu/SmokeTausta2.png"));
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

    /**
     * Decides what happens when show is called
     */
    @Override
    public void show() {

    }

    /**
     * Decides what happens when render is called
     * @param delta
     */
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

    /**
     * Decides what happens when draw is called
     * @param batch
     */
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
                highScoreScreenArt.getHeight() - highScoreHeaderGlyph.height / 2 - 20);

    }

    /**
     * Draws the scores oin the screen.
     * @param batch
     */
    public void drawScores(SpriteBatch batch) {

        y = highScoreScreenArt.getHeight() - highScoreHeaderGlyph.height * 4;

        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                intToChar = " " + 1 + ".   " + scores.get(i);
            }
            if (i > 0 && i <= 9) {
                intToChar = " " + (i + 1) + ".   " + scores.get(i);
            }
            if (i == 10) {
                intToChar = " " + (9) + ".   " + scores.get(9);
            }

            playerAndScoreChar =  intToChar;
            playerAndScoreGlyph.setText(scoreFont,playerAndScoreChar);
            scoreFont.draw(batch, playerAndScoreGlyph, highScoreScreenRectangle.getWidth() / 3, y);
            y -= playerAndScoreGlyph.height * 1.75;
        }
    }

    /**
     * Gets the scores from the memory for the scorescreen.
     */
    public void initializeScores() {
        for (int i = 0; i < 10; i++) {
            intToString = "Score" + i;
            scores.add(host.getHighScores().getInteger(intToString));

        }

    }

    /**
     * Returns scores.
     * @return scores-array.
     */
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

    /**
     * Checks what are the touch coordinates.
     */
    public void getTouchPos() {
        decisionTime += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isTouched()) {

            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.input.getY();
            camera.unproject(touchPos);
        }
    }

    /**
     * Checks if some button was touched.
     */
    public void whatIsTouched() {

        if (Gdx.input.isTouched()) {

            if (returnToMainRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f) {
                soundManager.playSound("buttonPush", 0.4f);
                host.setScreen(host.getMainMenu());
                decisionTime = 0;
                dispose();
            }
        }
    }

    /**
     * Decides what to do when resizing.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     *  Decides what to do when pausing.
     */
    @Override
    public void pause() {

    }

    /**
     *  Decides what to do when resuming.
     */
    @Override
    public void resume() {

    }

    /**
     *  Decides what to do when hide is called.
     */
    @Override
    public void hide() {

    }

    /**
     *  Decides what to do when disposing.
     */
    @Override
    public void dispose() {
        highScoreScreenArt.dispose();
        returnToMainArt.dispose();
    }
}