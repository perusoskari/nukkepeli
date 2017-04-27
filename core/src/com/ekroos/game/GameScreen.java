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

    //Lots of UI stuff for scores etc.
    private BitmapFont font;
    private Texture gameUpperScreen;
    private Texture pauseTexture;
    private Texture playTexture;
    private Texture pausePlayTexture;
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
    private Vector3 touchPos;
    private boolean pause;
    private boolean hasBeenTouched;
    private TimeUtilities timeUtilities;
    private boolean isTheGameOver;
    private GameOver gameOver;
    private boolean scoreHasBeenSet;

    //Lag testing
    HighScoreScreen scoreMark;


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
        touchGrid = new TouchGrid(camera, batch, mapMaker.getTrapTiles(), mapMaker);
        ekroos = new Ekroos(1f, 1f);
        isTheGameOver = false;
        blueLady = new BlueLady();

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
        pausePlayRectangle = new Rectangle(0,
                500f - pausePlayTexture.getHeight(),
                pausePlayTexture.getWidth(), pausePlayTexture.getHeight());

        host.parameter.size = 40;
        host.parameter.color = Color.WHITE;
        font = host.generator.generateFont(host.parameter);
        scoreAmount = 0;

        time = 0;
        pause = false;
        timeUtilities.startCountingTime();

        Locale defaultLocale = Locale.getDefault();
        I18NBundle myBundle = I18NBundle.createBundle(Gdx.files.internal("myBundle"), defaultLocale);

        quitButtonText = myBundle.get("exit");
        quitButtonGlyph = new GlyphLayout(font, quitButtonText);
        scoreText = myBundle.get("score");
        score = new GlyphLayout(font, scoreText);
        restartButtonText = myBundle.get("restart");
        restartButtonGlyph = new GlyphLayout(font, restartButtonText);
        scoreHasBeenSet = false;
        scoreMark = new HighScoreScreen(host);

        //Gdx.input.vibrate(new long[] {500, 200, 150, 200}, 0);
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
                    host.setScreen(new MainMenu(host));
                }
                if (gameOver.restartPress(camera)) {
                    dispose();
                    host.setScreen(new GameScreen(host));
                }
            }
        }

        batch.begin();
        mapMaker.draw(batch);
        touchGrid.drawGrid();
        ekroos.draw(batch);
        blueLady.draw(batch);
        batch.end();

        touchGrid.drawLine();

        if (isTheGameOver == true) {
            gameOver.draw(batch);

            if (scoreHasBeenSet == false) {
                setScore((Math.round(scoreAmount)));
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
        font.draw(UIBatch, score, gameUpperScreenRectangle.getWidth() - score.width,
                UIRectangle.getHeight() - score.height / 2);

        if (isTheGameOver) {
            font.draw(UIBatch, score, UIRectangle.width/1.7f, UIRectangle.height - UIRectangle.height/5);
            font.draw(UIBatch, restartButtonGlyph, UIRectangle.width/2.6f, UIRectangle.height - (UIRectangle.height/3.2f));
            font.draw(UIBatch, quitButtonGlyph, UIRectangle.width/2.6f, UIRectangle.height/2.1f);
        }

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
        gameOver.dispose();
        gameUpperScreen.dispose();
        pauseTexture.dispose();
        playTexture.dispose();
        pausePlayTexture.dispose();
    }

    public void checkForNewUnlock() {
        if (mapMaker.getPickUpDolls().size > 0) {
            if (ekroos.getRectangle().overlaps(mapMaker.getPickUpDolls().get(0).getRectangle())) {
                mapMaker.unlock(mapMaker.getPickUpDolls().get(0).getType());
                mapMaker.getPickUpDolls().get(0).dispose();
            }
        }
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
            decisionTime += 0.05;

            //If clicking pause
            if (touchPos.x >= pausePlayRectangle.x &&
                    touchPos.x <= pausePlayRectangle.x + pausePlayRectangle.getWidth() &&
                    touchPos.y >= pausePlayRectangle.y &&
                    touchPos.y <= pausePlayRectangle.y + pausePlayRectangle.getHeight() &&
                    pause == false &&
                    decisionTime >= 0.25f) {

                pause = true;
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
                //Change position
                pausePlayRectangle.x = 0;
                pausePlayRectangle.y = 500f - pausePlayTexture.getHeight();

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
        int multiplier = 1;
        if (mapMaker.getTripping()) {
            multiplier = 2;
        }

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
        float correctHeight = 0;
        for (int i = 0;i < list.size;i++) {
            for (int j = 0; j < trapTiles.size;j++) {
                if (trapTiles.get(j).getTrapType().equals("box") && trapTiles.get(j).getIfTileIsSafe()) {
                    correctHeight = trapTiles.get(j).getRectangle().getHeight();
                    break;
                }
            }
            if (list.get(i).isLock()) {

                if (list.get(i).getRectangle().setY(correctHeight).overlaps(ekroos.getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean spikeHelpUnder(Array<SpikeDollHelp> spikeList, Array<TrapTile> trapTiles) {
        Rectangle tmp = new Rectangle(mapMaker.getBasicTile().getRectangle());
        for (int i = 0;i < spikeList.size;i++) {
            float correctHeight = mapMaker.getBasicTile().getHeight();
           // for (int b = 0; b < trapTiles.size; b++) {

                /**if (trapTiles.get(b).getTrapType().equals("spike") && trapTiles.get(b).getIfTileIsSafe()) {
                    correctHeight = mapMaker.getTrapTiles().get(b).getRectangle().getHeight();
                }*/

           // }
            //correctHeight = tmp.height;
            if (spikeList.get(i).isLock()) {
                spikeList.get(i).getRectangle().setY(correctHeight);
                //if (spikeList.get(i).getRectangle().setY(correctHeight).overlaps(ekroos.getRectangle())) {
                  //  return true;
                //}
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
            if (mapMaker.getTrapTiles().get(i).getTrapType().equals("weight") &&
                    !mapMaker.getTrapTiles().get(i).isNullified()) {

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
