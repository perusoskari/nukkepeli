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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Collections;
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
    private BlueLady blueLady;
    private Bundlenator myBundle;
    private boolean infoExists;
    private DollIngameInfo dollIngameInfo;

    //Lots of UI stuff for scores etc.
    private BitmapFont font;
    private BitmapFont font2;
    private Texture gameUpperScreen;
    private Texture pauseTexture;
    private Texture playTexture;
    private Texture pausePlayTexture;
    private Texture soundOnTexture;
    private Texture muteTexture;
    private Texture soundMuteTexture;
    private Rectangle gameUpperScreenRectangle;
    private GlyphLayout score;
    private GlyphLayout quitButtonGlyph;
    private GlyphLayout restartButtonGlyph;
    private float scoreAmount;
    private float time;
    private float decisionTime;
    private CharSequence scoreText;
    private CharSequence restartButtonText;
    private CharSequence quitButtonText;
    private Rectangle UIRectangle;
    private Rectangle pausePlayRectangle;
    private Rectangle soundMuteRectangle;
    private Vector3 touchPos;
    private boolean pause;
    private boolean hasBeenTouched;
    private TimeUtilities timeUtilities;
    private boolean isTheGameOver;
    private GameOver gameOver;
    private boolean scoreHasBeenSet;
    private boolean mute;

    //Lag testing
    HighScoreScreen scoreMark;

    private SoundManager soundManager;

    //"tutorial"
    private Texture spikeTrapTexture;
    private Texture spikeTrapTextureDrawn;
    private Texture trapTexture;
    private Texture trapDrawn;
    private Texture weightTrapTexture;
    private Texture weightTrapDrawn;
    private Rectangle tutorialRectangle;


    public GameScreen(Program host) {
        this.host = host;
        batch = host.getBatch();
        soundManager = host.getSoundManager();
        soundManager.stopMenuMusic();
        soundManager.setMenuMusicIsPlaying(false);

        if (!soundManager.gameMusicIsPlaying()) {
            soundManager.playGameMusic(0.3f);
            soundManager.setGameMusicIsPlaying(true);
        }
        timeUtilities = new TimeUtilities();
        myBundle = new Bundlenator();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f, 5f);
        createThemes();
        currenTheme = themes[0];
        mapMaker = new MapMaker(currenTheme, soundManager);
        mapMaker.createMap();
        touchGrid = new TouchGrid(camera, batch, mapMaker.getTrapTiles(), mapMaker, soundManager);
        ekroos = new Ekroos(1f, 1f);
        isTheGameOver = false;
        blueLady = new BlueLady();
        infoExists = false;

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
        soundOnTexture = new Texture("buttonsAndMenu/soundButton.png");
        muteTexture = new Texture("buttonsAndMenu/muteButton.png");
        soundMuteTexture = new Texture(soundOnTexture.getTextureData());
        pausePlayTexture = new Texture(pauseTexture.getTextureData());

        pausePlayRectangle = new Rectangle(0,
                500f - pausePlayTexture.getHeight(),
                pausePlayTexture.getWidth(), pausePlayTexture.getHeight());
        soundMuteRectangle = new Rectangle(pausePlayRectangle.x + pausePlayRectangle.getWidth() + 5f,
                pausePlayRectangle.y, pausePlayRectangle.getWidth(), pausePlayRectangle.getHeight());

        host.parameter.size = 40;
        host.parameter.color = Color.WHITE;
        font = host.generator.generateFont(host.parameter);
        font2 = myBundle.getHighlyVisibleFont();
        scoreAmount = 0;

        time = 0;
        pause = false;
        timeUtilities.startCountingTime();

        Locale defaultLocale = Locale.getDefault();
        I18NBundle myBundle = I18NBundle.createBundle(Gdx.files.internal("myBundle"), defaultLocale);

        quitButtonText = myBundle.get("exit");
        quitButtonGlyph = new GlyphLayout(font2, quitButtonText);
        scoreText = myBundle.get("score");
        score = new GlyphLayout(font, scoreText);
        restartButtonText = myBundle.get("restart");
        restartButtonGlyph = new GlyphLayout(font2, restartButtonText);
        scoreHasBeenSet = false;
        scoreMark = new HighScoreScreen(host);

        //Gdx.input.vibrate(new long[] {500, 200, 150, 200}, 0);

        //tutorialTextures
        tutorialRectangle = new Rectangle(2.7f, 4.5f, 5f, 0.5f);
        trapTexture = new Texture(Gdx.files.internal("helpScreenStuff/darknessTrap.png"));
        trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/darknessTrapDrawn.png"));
        spikeTrapTexture = new Texture(Gdx.files.internal("helpScreenStuff/spikeTrap.png"));
        spikeTrapTextureDrawn = new Texture(Gdx.files.internal("helpScreenStuff/spikeTrapDrawn.png"));
        weightTrapTexture = new Texture(Gdx.files.internal("helpScreenStuff/weightTrap.png"));
        weightTrapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/weightTrapDrawn.png"));
    }

    @Override
    public void show() {

    }

    public Bundlenator getBundlenator() {
        return myBundle;
    }

    public OrthographicCamera getUICam() {
        return UICam;
    }

    public SpriteBatch getUIBatch() {
        return UIBatch;
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
            mapMaker.checkForThemeChange();
            mapMaker.ifItsTimeToUnlock();
            checkForNewUnlock();
            useGravity();
            touchGrid.checkPanStart();
            touchGrid.touchPositionMove();
            touchGrid.dollsMove(ekroos.get_x() + ekroos.getRectangle().getWidth(),
                    ekroos.get_y() + ekroos.getRectangle().getHeight()/2);
            blueLady.move();
            checkForEkroosDeath();
            countScore();

        } else {
            if (isTheGameOver) {
                if (gameOver.quitPress(camera)) {
                    dispose();
                    soundManager.playSound("buttonPush", 0.4f);
                    soundManager.stopGameMusic();
                    soundManager.setGameMusicIsPlaying(false);
                    host.setScreen(new MainMenu(host));
                }
                if (gameOver.restartPress(camera)) {
                    dispose();
                    soundManager.playSound("buttonPush", 0.4f);
                    host.setScreen(new GameScreen(host));
                }
            }
        }

        batch.begin();
        mapMaker.draw(batch);
        touchGrid.drawGrid();
        ekroos.draw(batch);
        blueLady.draw(batch);

        if (infoExists) {
            dollIngameInfo.draw(batch);
        }
        batch.end();

        touchGrid.drawLine();

        if (isTheGameOver == true) {
            gameOver.draw(batch);

            if (scoreHasBeenSet == false) {
                setScore((Math.round(scoreAmount)));
                soundManager.stopAll();

                if (!touchGrid.givenUp()) {
                    soundManager.playSound("gameOver", 0.5f);
                }
                scoreHasBeenSet = true;

            }

        }


        //UI Stuff
        checkUITouch();
        UIBatch.begin();
        UIBatch.draw(gameUpperScreen, gameUpperScreenRectangle.x, gameUpperScreenRectangle.y,
                gameUpperScreenRectangle.getWidth(), gameUpperScreenRectangle.getHeight());
        UIBatch.draw(pausePlayTexture, pausePlayRectangle.x,
                pausePlayRectangle.y, pausePlayRectangle.getWidth(), pausePlayRectangle.getHeight());
        UIBatch.draw(soundMuteTexture, soundMuteRectangle.x,
                soundMuteRectangle.y, soundMuteRectangle.getWidth(), soundMuteRectangle.getHeight());
        font.draw(UIBatch, score, gameUpperScreenRectangle.getWidth() - score.width,
                UIRectangle.getHeight() - score.height / 2 + 10f);

        if (isTheGameOver) {
            font.draw(UIBatch, score, UIRectangle.width/1.7f, UIRectangle.height - UIRectangle.height/5);
            font2.draw(UIBatch, restartButtonGlyph, UIRectangle.width/2.6f + 40f, UIRectangle.height - (UIRectangle.height/3.2f));
            font2.draw(UIBatch, quitButtonGlyph, UIRectangle.width/2.6f + 40f, UIRectangle.height/2.1f);
        }

        if (infoExists) {
            dollIngameInfo.drawInfo();
        }

        UIBatch.end();
        drawTutorial(batch);



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

    public void drawTutorial(SpriteBatch batch) {
        Texture[] list = new Texture[6];
        list[0] = trapTexture;
        list[1] = trapDrawn;
        list[2] = spikeTrapTexture;
        list[3] = spikeTrapTextureDrawn;
        list[4] = weightTrapTexture;
        list[5] = weightTrapDrawn;
        float space = trapDrawn.getWidth()/400f;

        batch.begin();
        for (int i = 0; i < 6; i++) {
            if (i != 1) {
                batch.draw(list[i], tutorialRectangle.x + (space * i), 5f - tutorialRectangle.height,
                        trapDrawn.getWidth() / 350f,
                        trapDrawn.getHeight() / 350f);
            } else {
                batch.draw(list[i], tutorialRectangle.x + (space * i) - (space/4.5f),
                        5f - (tutorialRectangle.height * 1.3f),
                        trapDrawn.getWidth() / 220f,
                        trapDrawn.getHeight() / 220f);
            }
        }
        batch.end();
    }

    public void setPause(boolean t) {
        pause = t;

        if (t) {
            soundManager.pauseAll();
        } else if (!t) {
            soundManager.resumeAll();
        }
    }

    public void setInfoExists(boolean t) {
        infoExists = t;
    }
    @Override
    public void dispose() {
        mapMaker.dispose();
        touchGrid.dispose();
        ekroos.dispose();
        gameOver.dispose();
        gameUpperScreen.dispose();
        pauseTexture.dispose();
        playTexture.dispose();
        pausePlayTexture.dispose();
        trapDrawn.dispose();
        trapTexture.dispose();
        spikeTrapTextureDrawn.dispose();
        spikeTrapTexture.dispose();
        weightTrapDrawn.dispose();
        weightTrapTexture.dispose();
    }

    public void checkForNewUnlock() {
        if (mapMaker.getPickUpDolls().size > 0) {
            if (ekroos.getRectangle().overlaps(mapMaker.getPickUpDolls().get(0).getRectangle())) {
                mapMaker.unlock(mapMaker.getPickUpDolls().get(0).getType());
                dollIngameInfo = new DollIngameInfo(mapMaker.getPickUpDolls().get(0).getType(),
                        camera, this);
                infoExists = true;
                mapMaker.getPickUpDolls().get(0).dispose();
            }
        }
    }


    /**
     * This method checks if user is giving input.
     */
    public void checkUITouch() {
        if (infoExists) {
            dollIngameInfo.checkForTap();
        }

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
     * this is because when using polling to register input it will otherwise register input
     * multiple times at once.
     */
    public void checkWhatIsTouched() {
            decisionTime += 0.05;

            //If clicking pause
            if (touchPos.x >= pausePlayRectangle.x &&
                    touchPos.x <= pausePlayRectangle.x + pausePlayRectangle.getWidth() &&
                    touchPos.y >= pausePlayRectangle.y &&
                    touchPos.y <= pausePlayRectangle.y + pausePlayRectangle.getHeight() &&
                    pause == false &&
                    decisionTime >= 0.25f) {

                pause = true;
                soundManager.pauseAll();
                soundManager.playSound("buttonPush", 0.4f);
                //Change position
                pausePlayRectangle.x = 1000 / 2 - pausePlayRectangle.getWidth() / 2;
                pausePlayRectangle.y = 500 / 2 - pausePlayRectangle.getHeight() /2;
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
                    decisionTime >= 0.25f) {

                pause = false;
                soundManager.resumeAll();
                soundManager.playSound("buttonPush", 0.4f);
                //Change position
                pausePlayRectangle.x = 0;
                pausePlayRectangle.y = 500f - pausePlayTexture.getHeight();

                //This changes the texture of the pausePlaybutton according to game state
                pausePlayTexture.load(pauseTexture.getTextureData());
                decisionTime = 0;
            }
            if (soundMuteRectangle.contains(touchPos.x, touchPos.y)) {
                if (mute == true && decisionTime >= 0.35f) {
                    soundMuteTexture.load(soundOnTexture.getTextureData());
                    mute = false;
                    soundManager.setMute(mute);
                    soundManager.muteGameMusic(false, 0.3f);
                    decisionTime = 0;
                }
                if (mute == false && decisionTime >= 0.35f) {
                    soundMuteTexture.load(muteTexture.getTextureData());
                    mute = true;
                    soundManager.setMute(mute);
                    soundManager.muteGameMusic(true, 0.3f);
                    decisionTime = 0;
                }
            }
    }

    /**
     * This method includes the logic to count score
     */
    public void countScore() {
        int multiplier = 1;
        if (mapMaker.getTripping()) {
            multiplier = 2;
        }

        time = timeUtilities.getPlaySeconds();
        if (isTheGameOver == false) {
            if (timeUtilities.getPlaySeconds() != timeUtilities.getFlatHelperSeconds()) {
                timeUtilities.setFlatHelperSeconds(timeUtilities.getPlaySeconds());
                if (time % 1 == 0) {
                    scoreAmount += 1;
                }
                for (int i = 0; i < mapMaker.getTrapTiles().size; i++) {
                    if (ekroos.get_x() >= mapMaker.getTrapTiles().get(i).get_x() +
                            mapMaker.getTrapTiles().get(i).getWidth() -
                            mapMaker.getTrapTiles().get(i).getWidth() / 30) {

                        //Add points according to how hard the traps are, TODO: this should be revised at the last sprint
                        if (mapMaker.getTrapTiles().get(i).getTrapType().equals("box")) {
                            scoreAmount += 50 * multiplier;
                        }
                        if (mapMaker.getTrapTiles().get(i).getTrapType().equals("spike")) {
                            scoreAmount += 25 * multiplier;
                        }
                        if (mapMaker.getTrapTiles().get(i).getTrapType().equals("weight")) {
                            scoreAmount += 40 * multiplier;
                        }
                        if (mapMaker.getTrapTiles().get(i).getTrapType().equals("water")) {
                            scoreAmount += 35 * multiplier;
                        }
                        if (mapMaker.getTrapTiles().get(i).getTrapType().equals("soviet")) {
                            scoreAmount += 40 * multiplier;
                        }
                        if (mapMaker.getTrapTiles().get(i).getTrapType().equals("zombie")) {
                            scoreAmount += 40 * multiplier;
                        }
                        if (mapMaker.getTrapTiles().get(i).getTrapType().equals("drum")) {
                            scoreAmount += 45 * multiplier;
                        }
                        if (mapMaker.getTrapTiles().get(i).getTrapType().equals("fire")) {
                            scoreAmount += 60 * multiplier;
                        }
                    }
                }
            }
        }

        score.setText(font, scoreText + " " + scoreAmount);

    }

    public void setScore(Integer score) {

        for (int i = 0; i < scoreMark.getScores().size(); i++) {
            if (score > scoreMark.getScores().get(i)) {
                //Move the player with the old score down
                host.getHighScores().putInteger("Score"  + (i + 1), host.getHighScores().getInteger("Score" + i));
                //Add the new player with higher score
                host.getHighScores().putInteger("Score" + i, score);
                break;
            }
        }
        //Sort the list
        Collections.sort(scoreMark.getScores());
        //Reverse the list so the highest score is first
        Collections.reverse(scoreMark.getScores());

        host.getHighScores().flush();

    }

    /**
     * Make ekroos fall if she is not on top of basic tile
     */
    public void useGravity() {
        boolean safePass = false;

        Array<BoxDollHelp> list = touchGrid.dolls.getBoxHelps();
        Array<SpikeDollHelp> spikeList = touchGrid.dolls.getSpikeHelps();
        Array<WaterDollHelp> waterList = touchGrid.dolls.getWaterHelps();

        Array<TrapTile> trapTiles = mapMaker.getTrapTiles();
        boolean boxHelpUnder = boxHelpUnder(list, trapTiles);
        boolean spikeHelpUnder = spikeHelpUnder(spikeList, trapTiles);
        weightTrapNullify();
        boolean safeWeightUnder = safeWeightUnder(trapTiles);
        sovietTrapNullify();
        boolean safeSovietUnder = safeSovietUnder(trapTiles);
        boolean waterHelpUnder = waterHelpUnder(waterList);
        zombieTrapNullify();
        boolean safeZombieUnder = safeZombieUnder(trapTiles);
        drumTrapNullify();
        boolean safeDrumUnder = safeDrumUnder(trapTiles);
        fireTrapNullify();
        boolean safeFireUnder = safeFireUnder(trapTiles);
        shroomTrapNullify();
        boolean shroomUnder = shroomUnder(trapTiles);

        if (safeWeightUnder || boxHelpUnder || spikeHelpUnder || waterHelpUnder || safeSovietUnder
                || safeZombieUnder || safeDrumUnder || safeFireUnder || shroomUnder) {
            safePass = true;
        }

       ekroos.gravityPull(mapMaker.getIfOnBasicTile(ekroos.get_x(), ekroos.get_y()),
               safePass, mapMaker.getBasicTile());
    }

    public boolean boxHelpUnder(Array<BoxDollHelp> list, Array<TrapTile> trapTiles) {
        for (int i = 0;i < list.size;i++) {
            if (list.get(i).isLock()) {
                if (list.get(i).getRectangle().overlaps(ekroos.getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean spikeHelpUnder(Array<SpikeDollHelp> spikeList, Array<TrapTile> trapTiles) {
        for (int i = 0;i < spikeList.size;i++) {
            if (spikeList.get(i).isLock()) {
                if (spikeList.get(i).getRectangle().overlaps(ekroos.getRectangle())) {
                     return true;
                }
            }
        }
        return false;
    }

    public boolean safeWeightUnder(Array<TrapTile> trapTiles) {
        for (int i = 0;i < trapTiles.size;i++) {

            if (trapTiles.get(i).getTrapType().equals("weight")) {

                if (trapTiles.get(i).isNullified()) {

                    Rectangle tmpRect = new Rectangle(ekroos.getRectangle());
                    tmpRect.setY(tmpRect.y -= 0.5f);

                    if (trapTiles.get(i).getRectangle().overlaps(tmpRect)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean waterHelpUnder(Array<WaterDollHelp> waterList) {
        for (int i = 0; i < waterList.size;i++) {
            if (waterList.get(i).getFrozen()) {
                Rectangle tmp = new Rectangle(ekroos.getRectangle());
                tmp.y = waterList.get(i).getRectangle().y - 0.01f;
                if (waterList.get(i).getRectangle().overlaps(tmp)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean safeSovietUnder(Array<TrapTile> trapList) {
        if (trapList.size > 0) {
            for (int i = 0; i < trapList.size; i++) {
                if (trapList.get(i).getTrapType().equals("soviet")) {
                    if (trapList.get(i).isNullified()) {

                        Rectangle tmpRect = ekroos.getRectangle();
                        tmpRect.setY(tmpRect.y -= 0.5f);

                        if (trapList.get(i).getRectangle().overlaps(tmpRect)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean safeZombieUnder(Array<TrapTile> trapTiles) {
        if (trapTiles.size > 0) {
            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("zombie")) {
                    if (trapTiles.get(i).isNullified()) {
                        if (trapTiles.get(i).getRectangle().overlaps(ekroos.getRectangle())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean safeDrumUnder(Array<TrapTile> trapTiles) {
        if (trapTiles.size > 0) {
            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("drum")) {
                    if (trapTiles.get(i).isNullified()) {
                        Rectangle tmp = new Rectangle(ekroos.getRectangle());
                        tmp.setY(0.2f);
                        if (trapTiles.get(i).getRectangle().overlaps(tmp)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean safeFireUnder(Array<TrapTile> trapTiles) {
        if (trapTiles.size > 0) {
            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("fire")) {
                    if (trapTiles.get(i).isNullified()) {
                        Rectangle tmp = new Rectangle(ekroos.getRectangle());
                        tmp.setY(0.2f);
                        if (trapTiles.get(i).getRectangle().overlaps(tmp)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean shroomUnder(Array<TrapTile> trapTiles) {
        if (trapTiles.size > 0) {
            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("shroom")) {
                    Rectangle tmp = new Rectangle(ekroos.getRectangle());
                    tmp.setY(0.2f);
                    if (trapTiles.get(i).getRectangle().overlaps(tmp)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void checkForEkroosDeath() {

        if (ekroos.getRectangle().getY() < 0f || touchGrid.givenUp()) {

            if (!touchGrid.givenUp()) {
                scoreAmount = 0;
                score.setText(font, scoreText + " " + scoreAmount);
            }
            gameOver = new GameOver();
            pause = true;
            isTheGameOver = true;
        }

       if (mapMaker.getGhostList().size > 0) {
            if (ekroos.getRectangle().overlaps(mapMaker.getGhostList().get(0).getRectangle())) {
                if (touchGrid.dolls.getGhostHelps().size == 0) {
                    scoreAmount = 0;
                    score.setText(font, scoreText + " " + scoreAmount);
                    gameOver = new GameOver();
                    pause = true;
                    isTheGameOver = true;
                } else {
                    mapMaker.getGhostList().get(0).destroy();
                }
            }
        }

        for (int i = 0;i < mapMaker.getTrapTiles().size;i++) {
            if ((mapMaker.getTrapTiles().get(i).getTrapType().equals("weight") &&
                    !mapMaker.getTrapTiles().get(i).isNullified()) ||
                    (mapMaker.getTrapTiles().get(i).getTrapType().equals("soviet") &&
                    !mapMaker.getTrapTiles().get(i).isNullified()) ||
                    (mapMaker.getTrapTiles().get(i).getTrapType().equals("fire") &&
                            !mapMaker.getTrapTiles().get(i).isNullified()) ||
                    (mapMaker.getTrapTiles().get(i).getTrapType().equals("drum") &&
                            !mapMaker.getTrapTiles().get(i).isNullified()) ||
                    (mapMaker.getTrapTiles().get(i).getTrapType().equals("zombie") &&
                            !mapMaker.getTrapTiles().get(i).isNullified())) {

                Rectangle tmpRect = new Rectangle(ekroos.getRectangle());
                tmpRect.setY(tmpRect.y -= 0.5f);

                if (mapMaker.getTrapTiles().get(i).getRectangle().overlaps(tmpRect)) {
                    scoreAmount = 0;
                    score.setText(font, scoreText + " " + scoreAmount);
                    gameOver = new GameOver();
                    pause = true;
                    isTheGameOver = true;
                }
            }
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

                if (list.get(i).getTrapType().equals("weight") && list.get(i).getIfTileIsSafe()) {

                    if (weightHelps.size > 0) {

                        for (int j = 0; j < weightHelps.size; j++) {

                            if (weightHelps.get(j).getRectangle().overlaps(list.get(i).getRectangle())) {
                                if (!list.get(i).isNullified()) {
                                    soundManager.playSound("rockCrumble", 0.2f);
                                }
                                list.get(i).nullify();
                            }
                        }
                    }
                }
            }
        }
    }

    public void sovietTrapNullify() {
        Array<TrapTile> list = mapMaker.getTrapTiles();
        Array<SovietDollHelp> sovietHelps = touchGrid.dolls.getSovietHelps();

        if (list.size > 0) {
            for (int i = 0; i < list.size;i++) {
                if (list.get(i).getTrapType().equals("soviet") && list.get(i).getIfTileIsSafe()) {
                    if (sovietHelps.size > 0) {
                        for (int j = 0; j < sovietHelps.size;j++) {
                            Rectangle tmp = new Rectangle(list.get(i).getRectangle());
                            tmp.setY(mapMaker.getBasicTile().getHeight());
                            if (sovietHelps.get(j).getRectangle().overlaps(tmp)) {
                                list.get(i).nullify();
                            }
                        }
                    }
                }
            }
        }
    }

    public void zombieTrapNullify() {
        Array<TrapTile> list = mapMaker.getTrapTiles();
        Array<ZombieDollHelp> zombieHelps = touchGrid.dolls.getZombieHelps();

        if (list.size > 0) {
            for (int i = 0; i < list.size;i++) {
                if (list.get(i).getTrapType().equals("zombie") && list.get(i).getIfTileIsSafe()) {
                    if (zombieHelps.size > 0) {
                        for (int j = 0; j < zombieHelps.size;j++) {
                            if (zombieHelps.get(j).getRectangle().overlaps(list.get(i).getRectangle())) {
                                list.get(i).nullify();
                            }
                        }
                    }
                }
            }
        }
    }

    public void drumTrapNullify() {
        Array<TrapTile> list = mapMaker.getTrapTiles();
        Array<DrumDollHelp> drumHelps = touchGrid.dolls.getDrumHelps();

        if (list.size > 0) {
            for (int i = 0; i < list.size;i++) {
                if (list.get(i).getTrapType().equals("drum") && list.get(i).getIfTileIsSafe()) {
                    if (drumHelps.size > 0) {
                        for (int j = 0; j < drumHelps.size;j++) {
                            if (!drumHelps.get(j).hasNullified()) {
                                if (drumHelps.get(j).getRectangle().overlaps(list.get(i).getRectangle())) {
                                    list.get(i).nullify();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void fireTrapNullify() {
        Array<TrapTile> list = mapMaker.getTrapTiles();
        Array<FireDollHelp> fireHelps = touchGrid.dolls.getFireHelps();

        if (list.size > 0) {
            for (int i = 0; i < list.size;i++) {
                if (list.get(i).getTrapType().equals("fire") && list.get(i).getIfTileIsSafe()) {
                    if (fireHelps.size > 0) {
                        for (int j = 0; j < fireHelps.size;j++) {

                            if (fireHelps.get(j).getRectangle().overlaps(list.get(i).getRectangle())) {
                                list.get(i).nullify();
                            }
                        }
                    }
                }
            }
        }
    }

    public void shroomTrapNullify() {
        Array<TrapTile> list = mapMaker.getTrapTiles();

        if (list.size > 0) {
            for (int i = 0; i < list.size;i++) {
                if (list.get(i).getTrapType().equals("shroom")) {
                    Rectangle tmp = new Rectangle(ekroos.getRectangle());
                    tmp.setY(0.2f);
                    if (list.get(i).getRectangle().overlaps(tmp)) {
                        list.get(i).nullify();
                    }
                }
            }
        }


    }
}
