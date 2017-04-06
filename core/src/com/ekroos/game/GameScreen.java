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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
    private TimeUtilities timeUtilities;



    public GameScreen(Program host) {
        this.host = host;
        batch = host.getBatch();
        timeUtilities = new TimeUtilities();
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
        pauseTexture = new Texture("buttonsAndMenu/pauseButton.png");
        playTexture = new Texture("buttonsAndMenu/playButton.png");
        gameUpperScreen = new Texture("buttonsAndMenu/gameScreenUpper.png");
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
        timeUtilities.startCountingTime();
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

        checkForEkroosDeath();

        //Actual game stuff
        if (pause == false) {

            mapMaker.mapMove();
            mapMaker.checkForThemeChange();
            useGravity();
            touchGrid.checkPanStart();
            touchGrid.touchPositionMove();
            touchGrid.dollsMove(ekroos.get_x() + ekroos.getRectangle().getWidth(),
                    ekroos.get_y() + ekroos.getRectangle().getHeight()/2);
            countScore();
            System.out.println(timeUtilities.getPlaySeconds());
        }

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
        ekroos.dispose();
        gameUpperScreen.dispose();
        pauseTexture.dispose();
        playTexture.dispose();
        pausePlayTexture.dispose();
    }

    /**
     * This method checks if user is giving input.
     */
    public void checkUITouch() {

        //Start counting the time when input was given
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
            decisionTime += 0.02;

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

        time = timeUtilities.getPlaySeconds();
        if (timeUtilities.getPlaySeconds() != timeUtilities.getFlatHelperSeconds()) {
            timeUtilities.setFlatHelperSeconds(timeUtilities.getPlaySeconds());
            if (time % 1 == 0) {
                scoreAmount += 1;
            }
            for (int i = 0; i < mapMaker.getTrapTiles().size; i++) {
                if (ekroos.get_x() >= mapMaker.getTrapTiles().get(i).get_x() +
                        mapMaker.getTrapTiles().get(i).getWidth() -
                        mapMaker.getTrapTiles().get(i).getWidth() / 30) {

                    scoreAmount += 50;
                }
            }
        }


        scoreText = myBundle.get("score")+ " " + scoreAmount;
        score.setText(font, scoreText);
    }

    /**
     * Make ekroos fall if she is not on top of basic tile
     */
    public void useGravity() {
        boolean safePass = false;

        boolean boxHelpUnder = false;

        Array<BoxDollHelp> list = touchGrid.dolls.getBoxHelps();
        Array<SpikeDollHelp> spikeList = touchGrid.dolls.getSpikeHelps();


        for (int i = 0;i < list.size;i++) {
            float correctHeight = mapMaker.getTrapTiles().get(0).getRectangle().getHeight();

            if (list.get(i).isLock()) {

                if (list.get(i).getRectangle().setY(correctHeight).overlaps(ekroos.getRectangle())) {
                    boxHelpUnder = true;
                }
            }
        }
        Array<TrapTile> trapTiles = mapMaker.getTrapTiles();
        boolean spikeHelpUnder = false;

        for (int i = 0;i < spikeList.size;i++) {
            float correctHeight = 0;
            for (int b = 0; b < trapTiles.size; b++) {

                if (trapTiles.get(b).getTrapType().equals("2")) {
                    correctHeight = mapMaker.getTrapTiles().get(b).getRectangle().getHeight();
                }
            }
            if (spikeList.get(i).isLock()) {

                if (spikeList.get(i).getRectangle().setY(correctHeight).overlaps(ekroos.getRectangle())) {
                    spikeHelpUnder = true;
                }
            }
        }

        boolean safeWeightUnder = false;

        weightTrapNullify();


        for (int i = 0;i < trapTiles.size;i++) {

                if (trapTiles.get(i).getTrapType().equals("3")) {

                    if (trapTiles.get(i).isNullified()) {

                        Rectangle tmpRect = ekroos.getRectangle();
                        tmpRect.setY(tmpRect.y -= 0.5f);

                        if (trapTiles.get(i).getRectangle().overlaps(tmpRect)) {
                            safeWeightUnder = true;
                        }
                    }
            }
        }

        if (safeWeightUnder || boxHelpUnder || spikeHelpUnder) {
            safePass = true;
        }


       ekroos.gravityPull(mapMaker.getIfOnBasicTile(ekroos.get_x(), ekroos.get_y()),
               safePass, mapMaker.getBasicTile());
    }

    public void checkForEkroosDeath() {
        System.out.println(Gdx.graphics.getDeltaTime());
        if (ekroos.getRectangle().getY() < 0f) {
            dispose();
            System.out.println(timeUtilities.getPlaySeconds());
            host.setScreen(new MainMenu(host));
        }
    }

    /**
     * creates the themes duh
     */
    public void createThemes() {
        themes = new String[3];
        themes[0] = "kitchen";
        themes[1] = "cellar";
        themes[2] = "saloon";
    }

    public void weightTrapNullify() {
        Array<TrapTile> list = mapMaker.getTrapTiles();
        Array<WeightDollHelp> weightHelps = touchGrid.dolls.getWeightHelps();

        if (list.size > 0) {

            for (int i = 0; i < list.size; i++) {

                if (list.get(i).getTrapType().equals("3") && list.get(i).getIfTileIsSafe()) {

                    if (weightHelps.size > 0) {

                        for (int j = 0; j < weightHelps.size; j++) {

                            if (weightHelps.get(j).getRectangle().overlaps(list.get(i).getRectangle())) {
                                list.get(i).nullify();
                            }
                        }
                    }
                }
            }
        }
    }

}
