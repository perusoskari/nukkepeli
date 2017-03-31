package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;


/**
 * Created by Puoskari on 1.3.2017.
 */

public class GameScreen implements Screen {
    private Program host;
    SpriteBatch batch;
    SpriteBatch UIBatch;
    private OrthographicCamera camera;
    private OrthographicCamera UICam;
    private MapMaker mapMaker;
    private Ekroos ekroos;
    private String[] themes;
    private String currenTheme;
    private TouchGrid touchGrid;

    //Lots of UI stuff for scores etc.
    private BitmapFont font;
    private Texture gameUpperScreen;
    private Texture pauseTexture;
    private Texture playTexture;
    private Texture pausePlayTexture;
    private Rectangle gameUpperScreenRectangle;
    private GlyphLayout score;
    private float scoreAmount;
    private float time;
    private float decisionTime;
    private CharSequence scoreText;
    private Rectangle UIRectangle;
    private Rectangle pausePlayRectangle;
    private Vector3 touchPos;
    private boolean pause;
    private boolean hasBeenTouched;

    private InputProcessor inputProcessor;



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

        //Upper screen graphics, text, score etc.
        UIBatch = new SpriteBatch();
        UICam = new OrthographicCamera();
        UIRectangle = new Rectangle(0,0, 1000f, 500f);
        UICam.setToOrtho(false, UIRectangle.getWidth(), UIRectangle.getHeight());

        touchPos = new Vector3();
        pauseTexture = new Texture("pauseButton.png");
        playTexture = new Texture("playButton.png");
        gameUpperScreen = new Texture("gameScreenUpper.png");
        gameUpperScreenRectangle = new Rectangle(0f,
                UIRectangle.getHeight() - gameUpperScreen.getHeight(),
                UIRectangle.getWidth(), gameUpperScreen.getHeight());

        pausePlayTexture = new Texture(pauseTexture.getTextureData());
        pausePlayRectangle = new Rectangle(0, gameUpperScreenRectangle.y,
                pausePlayTexture.getWidth(), pausePlayTexture.getHeight());
        host.parameter.size = 40;
        host.parameter.color = Color.WHITE;
        font = host.generator.generateFont(host.parameter);
        scoreAmount = 0;
        scoreText = "";
        score = new GlyphLayout(font, scoreText);
        time = 0;
        pause = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        UIBatch.setProjectionMatrix(UICam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Actual game stuff
        if (pause == false) {

            mapMaker.mapMove();
            useGravity();
            checkForThemeChange();
            touchGrid.checkPanStart();
            touchGrid.touchPositionMove();
            touchGrid.dollsMove(ekroos.get_x() + ekroos.getRectangle().getWidth(),
                    ekroos.get_y() + ekroos.getRectangle().getHeight()/2);
            countScore();
        }

        System.out.println(Gdx.graphics.getDeltaTime());
        batch.begin();
        mapMaker.draw(batch);
        touchGrid.drawGrid();
        ekroos.draw(batch);
        batch.end();

        touchGrid.drawLine();

        //UI Stuff
        checkUITouch();
        UIBatch.begin();
        UIBatch.draw(gameUpperScreen, gameUpperScreenRectangle.x, gameUpperScreenRectangle.y,
                gameUpperScreenRectangle.getWidth(), gameUpperScreenRectangle.getHeight());
        UIBatch.draw(pausePlayTexture, pausePlayRectangle.x,
                pausePlayRectangle.y, pausePlayRectangle.getWidth(), pausePlayRectangle.getHeight());
        font.draw(UIBatch, score, gameUpperScreenRectangle.getWidth() - score.width,
                UIRectangle.getHeight() - score.height / 2);
        UIBatch.end();


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
     * This method checks if user is giving input.
     */
    public void checkUITouch() {

        //Start counting the time when input was given
        decisionTime += Gdx.graphics.getDeltaTime();

            if (Gdx.input.isTouched()) {
                hasBeenTouched = true;
                touchPos.x = Gdx.input.getX();
                touchPos.y = Gdx.input.getY();
                UICam.unproject(touchPos);
                checkWhatIsTouched();
            }
    }

    /**
     * This method checks what things have been touched on UI. Contains counter named decisionTime
     * this is because when using polling to register input it will otherwise reigster input
     * multiple times at once.
     */
    public void checkWhatIsTouched() {

            //If clicking pause
            if (touchPos.x >= pausePlayRectangle.x &&
                    touchPos.x <= pausePlayRectangle.x + pausePlayRectangle.getWidth() &&
                    touchPos.y >= pausePlayRectangle.y &&
                    touchPos.y <= pausePlayRectangle.y + pausePlayRectangle.getHeight() &&
                    pause == false &&
                    decisionTime >= 0.5f) {

                pause = true;
                //This changes the texture of the pausePlaybutton according to game state
                pausePlayTexture.load(playTexture.getTextureData());
                decisionTime = 0;
            }

            //If clicking resume
            if (touchPos.x >= pausePlayRectangle.x &&
                    touchPos.x <= pausePlayRectangle.x + pausePlayRectangle.getWidth() &&
                    touchPos.y >= pausePlayRectangle.y &&
                    touchPos.y <= pausePlayRectangle.y + pausePlayRectangle.getHeight() &&
                    pause == true &&
                    decisionTime >= 0.5f) {

                pause = false;
                //This changes the texture of the pausePlaybutton according to game state
                pausePlayTexture.load(pauseTexture.getTextureData());
                decisionTime = 0;
            }
    }
    /**
     * This method includes the logic to count score
     * TODO: Centralized bundle so it will not have to be created every time on all instances when it is needed
     */
    public void countScore() {
        //Create the bundles etc.
        Locale defaultLocale = Locale.getDefault();
        I18NBundle myBundle = I18NBundle.createBundle(Gdx.files.internal("myBundle"), defaultLocale);

        time += Gdx.graphics.getDeltaTime();
        scoreAmount += MathUtils.round(time);
        scoreText = myBundle.get("score")+ " " + scoreAmount;
        score.setText(font, scoreText);
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
