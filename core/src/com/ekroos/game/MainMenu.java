package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
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
import com.sun.org.apache.xpath.internal.operations.String;

import java.util.Locale;

/**
 * Created by Puoskari on 1.3.2017.
 */

/**
 * Creates the main menu for the game.
 */
public class MainMenu implements Screen {
    private Program host;
    SpriteBatch batch;
    private SoundManager soundManager;

    private BitmapFont font;
    private BitmapFont infoFont;

    CharSequence start;
    CharSequence highScore;
    CharSequence exit;
    CharSequence name;
    CharSequence version;
    CharSequence help;

    private GlyphLayout startGameGlyph;
    private GlyphLayout highScoreGlyph;
    private GlyphLayout exitGlyph;
    private GlyphLayout nameGlyph;
    private GlyphLayout versionGlyph;
    private GlyphLayout helpGlyph;

    private Texture mainMenuArt;
    private Texture multiButton;
    private Texture soundOnTexture;
    private Texture soundOffTexture;
    private Texture soundOnOffTexture;

    private Rectangle mainMenuRectangle;
    private Rectangle playRectangle;
    private Rectangle highScoreRectangle;
    private Rectangle exitRectangle;
    private Rectangle helpRectangle;
    private Rectangle soundOnOffRectangle;

    private Vector3 touchPos;
    private float decisionTime;
    private OrthographicCamera camera;
    private boolean soundOn;

    /**
     * Creates the main menu.
     * @param host is the main program.
     */
    public MainMenu(Program host) {
        this.host = host;
        batch = host.getBatch();
        soundManager = host.getSoundManager();

        if (host.options.getString("music").equals("on")) {
            if (!soundManager.menuMusicIsPlaying()) {
                soundManager.playMenuMusic(0.3f);
            }
        }

        //Creates all the text for the main menu
        localizeText();

        // Here we can give the font additional changes for the main menu
        // NOTE: this is the old way of altering fonts, new way is doing it by Bundlenator class!
        host.parameter.size = 45;
        host.infoParameter.size = 25;

        host.parameter.color = Color.BLACK;
        host.infoParameter.color = Color.GOLD;

        font = host.generator.generateFont(host.parameter);
        infoFont = host.generator.generateFont(host.infoParameter);

        //"Glyphs" meaning the actual text
        startGameGlyph = new GlyphLayout(font, start);
        highScoreGlyph = new GlyphLayout(font, highScore);
        exitGlyph = new GlyphLayout(font, exit);
        nameGlyph = new GlyphLayout(infoFont, name);
        versionGlyph = new GlyphLayout(infoFont, version);
        helpGlyph = new GlyphLayout(font, help);

        //Textures
        mainMenuArt = new Texture("buttonsAndMenu/mainMenuArt.png");
        multiButton = new Texture("buttonsAndMenu/multiButton.png");


        //Rectangles
        mainMenuRectangle = new Rectangle(0,0,mainMenuArt.getWidth(), mainMenuArt.getHeight());


        //Buttons are centered with ultimate precision, what could go wrong?
        playRectangle = new Rectangle((mainMenuRectangle.width / 4) - (multiButton.getWidth() / 2),
                mainMenuRectangle.getHeight() - (mainMenuRectangle.getHeight() / 3),
                multiButton.getWidth(), multiButton.getHeight());

        highScoreRectangle = new Rectangle((mainMenuRectangle.width / 4) - (multiButton.getWidth() / 2),
                mainMenuRectangle.getHeight() - (mainMenuRectangle.getHeight() / 2) - multiButton.getHeight() / 2,
                multiButton.getWidth(), multiButton.getHeight());

        exitRectangle = new Rectangle((mainMenuRectangle.width / 4 + mainMenuRectangle.width / 2) - (multiButton.getWidth() / 2),
                mainMenuRectangle.getHeight() - (mainMenuRectangle.getHeight() / 2) - multiButton.getHeight() / 2,
                multiButton.getWidth(), multiButton.getHeight());
        helpRectangle = new Rectangle((mainMenuRectangle.width / 4 + mainMenuRectangle.width / 2) - (multiButton.getWidth() / 2),
                mainMenuRectangle.getHeight() - (mainMenuRectangle.getHeight() / 3),
                multiButton.getWidth(), multiButton.getHeight());


        camera = new OrthographicCamera();
        camera.setToOrtho(false, mainMenuArt.getWidth(), mainMenuArt.getHeight());
        touchPos = new Vector3();
        decisionTime = 0;

        //Sound stuff
        soundOffTexture = new Texture("buttonsAndMenu/muteButton.png");
        soundOnTexture = new Texture("buttonsAndMenu/soundButton.png");
        if (host.options.getString("music") != "off") {
            soundOn = true;
            soundOnOffTexture = new Texture(soundOnTexture.getTextureData());
        }
        if (host.options.getString("music") != "on") {
            soundOn = false;
            soundOnOffTexture = new Texture(soundOffTexture.getTextureData());
        }
        soundOnOffRectangle = new Rectangle(mainMenuRectangle.getWidth() - soundOnOffTexture.getWidth() - 5f,
                mainMenuRectangle.getHeight() - soundOnOffTexture.getHeight() - 5f,
                soundOnOffTexture.getWidth(), soundOnOffTexture.getHeight());


    }

    /**
     *  Decides what to do when show  is called.
     */
    @Override
    public void show() {

    }

    /**
     *  Decides what to render.
     * @param delta
     */
    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(camera.combined);
        getTouchPos();
        whatIsTouched();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        draw(batch);
        batch.end();
    }

    /**
     *  Decides what to do when draw is called.
     * @param sb is the spritebatch.
     */
    public void draw(SpriteBatch sb) {

        sb.draw(mainMenuArt, mainMenuRectangle.x, mainMenuRectangle.y
                , mainMenuRectangle.getWidth(), mainMenuRectangle.getHeight());

        sb.draw(multiButton, playRectangle.x, playRectangle.y
                , playRectangle.getWidth(), playRectangle.getHeight());

        sb.draw(multiButton, highScoreRectangle.x, highScoreRectangle.y
                , highScoreRectangle.getWidth(), highScoreRectangle.getHeight());

        sb.draw(multiButton, exitRectangle.x, exitRectangle.y
                , exitRectangle.getWidth(), exitRectangle.getHeight());

        sb.draw(multiButton, helpRectangle.x, helpRectangle.y
                , helpRectangle.getWidth(), helpRectangle.getHeight());

        sb.draw(soundOnOffTexture, soundOnOffRectangle.x, soundOnOffRectangle.y,
                soundOnOffRectangle.getWidth(), soundOnOffRectangle.getHeight());

        //More centering of the texts
        font.draw(sb,startGameGlyph, playRectangle.x + playRectangle.getWidth() / 2 - startGameGlyph.width / 2
                , playRectangle.y + playRectangle.getHeight() - 5f);

        font.draw(sb,highScoreGlyph, highScoreRectangle.x + highScoreRectangle.getWidth() / 2 - highScoreGlyph.width / 2
                , highScoreRectangle.y + highScoreRectangle.getHeight() - 8f);

        font.draw(sb,exitGlyph, exitRectangle.x + exitRectangle.getWidth() / 2 - exitGlyph.width / 2
                , exitRectangle.y + exitRectangle.getHeight() - 8f);

        font.draw(sb,helpGlyph, helpRectangle.x + helpRectangle.getWidth() / 2 - helpGlyph.width / 2
                , helpRectangle.y + helpRectangle.getHeight() - 8f);

        infoFont.draw(sb, nameGlyph, 0, mainMenuRectangle.getHeight());
        infoFont.draw(sb, versionGlyph, 0, mainMenuRectangle.getHeight() - nameGlyph.height);
    }

    /**
     * Gets the touchposition of the finger or cursor.
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
     * Decides what to do when a button is touched.
     */
    public void whatIsTouched() {

        if (Gdx.input.isTouched()) {

            //If play button is touched move to GameScreen
            if (playRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f)
            {
                host.setScreen(host.getGameScreen());
                decisionTime = 0;
                soundManager.playSound("buttonPush", 0.4f);
                dispose();
            }

            if (highScoreRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f)
            {
                host.setScreen(host.getHighScoreScreen());
                decisionTime = 0;
                soundManager.playSound("buttonPush", 0.4f);
                dispose();
            }

            if (helpRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f)
            {
                host.setScreen(host.getHelpScreen());
                decisionTime = 0;
                soundManager.playSound("buttonPush", 0.4f);
                dispose();
            }

            if (exitRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f)
            {
                System.out.println("Bye!");
                decisionTime = 0;
                soundManager.playSound("buttonPush", 0.4f);
                Gdx.app.exit();
            }
            if (soundOnOffRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f && soundOn == true) {
                soundManager.playSound("buttonPush", 0.4f);
                decisionTime = 0;
                soundOnOffTexture.load(soundOffTexture.getTextureData());
                soundManager.stopMenuMusic();
                soundOn = false;
                host.options.putString("music", "off");
                host.options.flush();
            }
            if (soundOnOffRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f && soundOn == false) {
                soundManager.playSound("buttonPush", 0.4f);
                decisionTime = 0;
                soundOnOffTexture.load(soundOnTexture.getTextureData());
                soundManager.playMenuMusic(0.3f);
                soundOn = true;
                host.options.putString("music", "on");
                host.options.flush();
            }
        }
    }

    /**
     * This method puts text in Strings, also allows easy localization later
     */
    public void localizeText() {

        //Create the bundles etc.
        Locale defaultLocale = Locale.getDefault();
        I18NBundle myBundle = I18NBundle.createBundle(Gdx.files.internal("myBundle"), defaultLocale);

        start = myBundle.get("start");
        highScore = myBundle.get("highScore");
        exit= myBundle.get("exit");
        name = myBundle.get("name");
        version = myBundle.get("version");
        help = myBundle.get("help");
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
     * Decides what to do when pausing.
     */
    @Override
    public void pause() {

    }

    /**
     * Decides what to do when resuming.
     */
    @Override
    public void resume() {

    }

    /**
     * Decides what to do when hide is called.
     */
    @Override
    public void hide() {

    }

    /**
     * Decides what to dispose.
     */
    @Override
    public void dispose() {
        mainMenuArt.dispose();
        multiButton.dispose();
    }
}
