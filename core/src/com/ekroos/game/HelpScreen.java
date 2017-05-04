package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by Ville on 3.4.2017.
 */

public class HelpScreen implements Screen {

    private Program host;
    SpriteBatch batch;

    private Texture helpScreenArt;
    private Texture multiButtonArt;
    private Texture grayMultiButtonTexture;
    private Texture gameInfoScreen;

    private Rectangle helpScreenRectangle;
    private Rectangle upperScreenRectangle;
    private Rectangle dollRectangle;
    private Rectangle trapRectangle;
    private Rectangle patternRectangle;
    private Rectangle textRectangle;
    private Rectangle backButtonRectangle;
    private Rectangle dollsOrGameRectangle;
    private Rectangle gameInfoContentRectangle;

    private ArrayList<Texture> dollPictureArray;
    private ArrayList<Texture> trapPictureArray;
    private ArrayList<Texture> patternPictureArray;

    private ArrayList<GlyphLayout> textArray;
    private CharSequence textToChar;
    private CharSequence outOfHowManyChar;
    private CharSequence dollsOrGameChar;

    private GlyphLayout charToArray;
    private GlyphLayout screenTitle;
    private GlyphLayout backText;
    private GlyphLayout outOfHowManyGlyph;
    private GlyphLayout dollsOrGameGlyph;

    private BitmapFont font;
    private BitmapFont otherTextFont;

    private OrthographicCamera camera;
    private Vector3 touchPos;
    private float decisionTime;

    int swipeCounter;
    private boolean isDollScreen;

    private Bundlenator myBundle;
    int lol;
    boolean lolbool;
    boolean lolbool2;

    public HelpScreen(Program host) {

        this.host = host;
        batch = host.getBatch();

        helpScreenArt = new Texture("buttonsAndMenu/helpScreenArt.png");
        multiButtonArt = new Texture("buttonsAndMenu/multiButton.png");
        grayMultiButtonTexture = new Texture("buttonsAndMenu/grayMultiButton.png");
        gameInfoScreen = new Texture("helpScreenStuff/grannyCool.png");

        helpScreenRectangle = new Rectangle(0,0,
                helpScreenArt.getWidth(), helpScreenArt.getHeight());

        upperScreenRectangle = new Rectangle(helpScreenRectangle.x,
                helpScreenRectangle.getHeight() - multiButtonArt.getHeight(),
                helpScreenRectangle.getWidth(),
                multiButtonArt.getHeight());

        dollsOrGameRectangle = new Rectangle(upperScreenRectangle.x, upperScreenRectangle.y,
                upperScreenRectangle.getWidth() / 5, upperScreenRectangle.getHeight());

        backButtonRectangle = new Rectangle(helpScreenRectangle.getWidth() - 110f, 10f, 100f, 50f);

        dollRectangle = new Rectangle(0,
                upperScreenRectangle.y - (helpScreenRectangle.getHeight() / 2 +
                        upperScreenRectangle.getHeight()) + helpScreenRectangle.getHeight() / 4,
                helpScreenRectangle.getWidth() / 3,
                helpScreenRectangle.getHeight() / 2 - upperScreenRectangle.getHeight() - 7f);
        System.out.println(dollRectangle.getWidth() + " " + dollRectangle.getHeight());

        trapRectangle = new Rectangle(dollRectangle.x + dollRectangle.getWidth(),
                dollRectangle.getY(), dollRectangle.getWidth(),
                dollRectangle.getHeight());

        patternRectangle = new Rectangle(trapRectangle.x + trapRectangle.getWidth(),
                dollRectangle.getY(), dollRectangle.getWidth(), dollRectangle.getHeight());

        textRectangle = new Rectangle(0, 0,
                helpScreenRectangle.getWidth(), helpScreenRectangle.getHeight() / 2);

        gameInfoContentRectangle = new Rectangle(0, 0, helpScreenRectangle.getWidth(),
                helpScreenRectangle.getHeight() - upperScreenRectangle.getHeight());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, helpScreenArt.getWidth(), helpScreenArt.getHeight());
        touchPos = new Vector3();
        decisionTime = 0;
        swipeCounter = 0;

        textToChar = "";
        myBundle = new Bundlenator();
        font = myBundle.getDescriptionFont();
        otherTextFont = myBundle.getHighlyVisibleFont();
        charToArray = new GlyphLayout(font, textToChar);

        isDollScreen = true;

        lol = 0;
        lolbool = false;
        lolbool2 = false;

        loadTextures();
        loadDescriptions();

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
        listenToSwipe();

        outOfHowManyChar = swipeCounter + 1 + "/" + dollPictureArray.size();
        outOfHowManyGlyph.setText(font, outOfHowManyChar);


        lol++;
        if (lolbool == false) {
            lolbool = true;
        }
        else {
            lolbool = false;
        }
        if (lolbool2 == false) {
            lolbool2 = true;
        }
        else{
            lolbool2 = false;
        }


        batch.begin();
        draw(batch);
        batch.end();

    }
    public void draw(SpriteBatch batch) {


        batch.draw(helpScreenArt, helpScreenRectangle.x, helpScreenRectangle.y,
                helpScreenRectangle.getWidth(), helpScreenRectangle.getHeight());

        batch.draw(multiButtonArt, upperScreenRectangle.x, upperScreenRectangle.y,
                upperScreenRectangle.getWidth(), upperScreenRectangle.getHeight());



        batch.draw(grayMultiButtonTexture, dollsOrGameRectangle.x, dollsOrGameRectangle.y,
                dollsOrGameRectangle.getWidth(), dollsOrGameRectangle.getHeight());

        otherTextFont.draw(batch, screenTitle,
                upperScreenRectangle.getWidth() / 2 - screenTitle.width / 2,
                helpScreenRectangle.getHeight());




        if (isDollScreen == true) {
            drawArrays(batch);
            otherTextFont.draw(batch,
                    dollsOrGameGlyph,
                    dollsOrGameRectangle.x +
                            dollsOrGameRectangle.getWidth() / 2 - dollsOrGameGlyph.width / 2,
                    dollsOrGameRectangle.y +
                            dollsOrGameRectangle.getHeight() / 2 + dollsOrGameGlyph.height / 2);
        }
        if (isDollScreen == false) {
            otherTextFont.draw(batch,
                    dollsOrGameGlyph,
                    dollsOrGameRectangle.x +
                            dollsOrGameRectangle.getWidth() / 2 - dollsOrGameGlyph.width / 2,
                    dollsOrGameRectangle.y +
                            dollsOrGameRectangle.getHeight() / 2 + dollsOrGameGlyph.height / 2);
           // batch.draw(gameInfoScreen, gameInfoContentRectangle.x, gameInfoContentRectangle.y,
          //          gameInfoContentRectangle.getWidth(), gameInfoContentRectangle.getHeight());
            batch.draw(gameInfoScreen,gameInfoContentRectangle.x, gameInfoContentRectangle.y,
                    gameInfoContentRectangle.getWidth() /2, gameInfoContentRectangle.getHeight() / 2,
                    1000,400
                    ,1f,1f,lol,0,0,1000,500,lolbool,lolbool2);
            font.draw(batch, "Ekroos meni ja otti juoksunuket \n Nyt pitää juosta >9000/fps. sinistä leidiä karkuun", 600, 400);
        }

        batch.draw(multiButtonArt, backButtonRectangle.x, backButtonRectangle.y,
                backButtonRectangle.getWidth(), backButtonRectangle.getHeight());

        otherTextFont.draw(batch, backText,
                backButtonRectangle.x + backButtonRectangle.getWidth() / 2 - backText.width / 2,
                backButtonRectangle.y + backButtonRectangle.getHeight() / 2 + backText.height / 2);

    }
    public void drawArrays(SpriteBatch batch) {

        batch.draw(dollPictureArray.get(swipeCounter),
                dollRectangle.x,
                dollRectangle.y,
                dollRectangle.getWidth(),
                dollRectangle.getHeight());

        batch.draw(trapPictureArray.get(swipeCounter),
                trapRectangle.x,
                trapRectangle.y,
                trapRectangle.getWidth(),
                trapRectangle.getHeight());

        batch.draw(patternPictureArray.get(swipeCounter),
                patternRectangle.x,
                patternRectangle.y,
                patternRectangle.getWidth(),
                patternRectangle.getHeight());

        font.draw(batch, textArray.get(swipeCounter),
                textRectangle.getWidth() / 2 - textArray.get(swipeCounter).width / 2,
                textRectangle.getHeight() - 50f);

        font.draw(batch, outOfHowManyGlyph,
                helpScreenRectangle.getWidth() - outOfHowManyGlyph.width,
                helpScreenRectangle.getHeight() - upperScreenRectangle.getHeight());


    }

    public void loadTextures() {


        //Make the arrays
        dollPictureArray = new ArrayList<Texture>();
        trapPictureArray = new ArrayList<Texture>();
        patternPictureArray = new ArrayList<Texture>();

        //Add textures to them

        //Pictures of dolls
        Texture puuNukkeTexture = new Texture("helpScreenStuff/puuNukkeHelp.png");
        Texture drumNukkeTexture = new Texture("helpScreenStuff/drumNukkeHelp.png");
        Texture spookNukkeTexture = new Texture("helpScreenStuff/spookNukkeHelp.png");
        Texture waterNukkeTexture = new Texture("helpScreenStuff/waterNukkeHelp.png");
        Texture zombieNukkeTexture = new Texture("helpScreenStuff/zombieNukkeHelp.png");
        Texture hatNukkeTexture = new Texture("helpScreenStuff/hatNukkeHelp.png");
        Texture shroomNukkeTexture = new Texture("helpScreenStuff/shroomNukkeHelp.png");
        Texture fireNukkeTexture = new Texture("helpScreenStuff/fireNukkeHelp.png");
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(drumNukkeTexture);
        dollPictureArray.add(spookNukkeTexture);
        dollPictureArray.add(waterNukkeTexture);
        dollPictureArray.add(zombieNukkeTexture);
        dollPictureArray.add(hatNukkeTexture);
        dollPictureArray.add(shroomNukkeTexture);
        dollPictureArray.add(fireNukkeTexture);



        //Pictures of traps
        Texture piikkiAnsaTexture = new Texture("helpScreenStuff/spikeTrap.png");
        Texture pimeysTexture = new Texture("helpScreenStuff/darknessTrap.png");
        Texture weightTexture = new Texture("helpScreenStuff/weightTrap.png");
        Texture drumTexture = new Texture("helpScreenStuff/drumTrap.png");
        Texture spookTexture = new Texture("helpScreenStuff/spookTrap.png");
        Texture waterTexture = new Texture("helpScreenStuff/waterTrap.png");
        Texture zombieTexture = new Texture("helpScreenStuff/zombieTrap.png");
        Texture hatTexture = new Texture("helpScreenStuff/hatTrap.png");
        Texture shroomTexture = new Texture("helpScreenStuff/shroomTrap.png");
        Texture fireTexture = new Texture("helpScreenStuff/fireTrap.png");
        trapPictureArray.add(piikkiAnsaTexture);
        trapPictureArray.add(pimeysTexture);
        trapPictureArray.add(weightTexture);
        trapPictureArray.add(drumTexture);
        trapPictureArray.add(spookTexture);
        trapPictureArray.add(waterTexture);
        trapPictureArray.add(zombieTexture);
        trapPictureArray.add(hatTexture);
        trapPictureArray.add(shroomTexture);
        trapPictureArray.add(fireTexture);


        //Pictures of patterns
        Texture spikeTrapDrawnTexture = new Texture("helpScreenStuff/spikeTrapDrawn.png");
        Texture boxTrapDrawnTexture = new Texture("helpScreenStuff/darknessTrapDrawn.png");
        Texture weightTrapDrawnTexture = new Texture("helpScreenStuff/weightTrapDrawn.png");
        Texture drumTrapDrawnTexture = new Texture("helpScreenStuff/drumTrapDrawn.png");
        Texture spookTrapDrawnTexture = new Texture("helpScreenStuff/spookTrapDrawn.png");
        Texture waterTrapDrawnTexture = new Texture("helpScreenStuff/waterTrapDrawn.png");
        Texture zombieTrapDrawnTexture = new Texture("helpScreenStuff/zombieTrapDrawn.png");
        Texture hatTrapDrawnTexture = new Texture("helpScreenStuff/hatTrapDrawn.png");
        Texture shroomTrapDrawnTexture = new Texture("helpScreenStuff/shroomTrapDrawn.png");
        Texture fireTrapDrawnTexture = new Texture("helpScreenStuff/fireTrapDrawn.png");

        patternPictureArray.add(spikeTrapDrawnTexture);
        patternPictureArray.add(boxTrapDrawnTexture);
        patternPictureArray.add(weightTrapDrawnTexture);
        patternPictureArray.add(drumTrapDrawnTexture);
        patternPictureArray.add(spookTrapDrawnTexture);
        patternPictureArray.add(waterTrapDrawnTexture);
        patternPictureArray.add(zombieTrapDrawnTexture);
        patternPictureArray.add(hatTrapDrawnTexture);
        patternPictureArray.add(shroomTrapDrawnTexture);
        patternPictureArray.add(fireTrapDrawnTexture);



    }
    public void loadDescriptions() {

        outOfHowManyGlyph = new GlyphLayout(font, "");
        textToChar = myBundle.getLocal("helpScreenTitle");
        screenTitle = new GlyphLayout(otherTextFont, textToChar);
        textToChar = myBundle.getLocal("back");
        backText = new GlyphLayout(otherTextFont, textToChar);
        dollsOrGameChar = myBundle.getLocal("game");
        dollsOrGameGlyph = new GlyphLayout(otherTextFont, dollsOrGameChar);

        //List of descriptions
        textArray = new ArrayList<GlyphLayout>();
        //Add all the descriptions
        textToChar = myBundle.getLocal("dummyDesc");
        charToArray = new GlyphLayout(font, textToChar);
        textArray.add(charToArray);
        textArray.add(charToArray);
        textArray.add(charToArray);
        textArray.add(charToArray);
        textArray.add(charToArray);
        textArray.add(charToArray);
        textArray.add(charToArray);
        textArray.add(charToArray);
        textArray.add(charToArray);
        textArray.add(charToArray);

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
    //Check what is touched using Rectangles of the main screen buttons
    public void whatIsTouched() {

        if (Gdx.input.isTouched()) {

            if (backButtonRectangle.contains(touchPos.x, touchPos.y) && decisionTime >= 0.5f) {
                host.setScreen(host.getMainMenu());
                decisionTime = 0;
                dispose();
            }
            if (dollsOrGameRectangle.contains(touchPos.x, touchPos.y)) {
                if (isDollScreen == true && decisionTime >= 0.5f) {
                    decisionTime = 0;
                    dollsOrGameChar = myBundle.getLocal("dolls");
                    dollsOrGameGlyph.setText(otherTextFont, dollsOrGameChar);
                    isDollScreen = false;
                }
                if (isDollScreen == false && decisionTime >= 0.5f) {

                    decisionTime = 0;
                    dollsOrGameChar = myBundle.getLocal("game");
                    dollsOrGameGlyph.setText(otherTextFont, dollsOrGameChar);
                    isDollScreen = true;
                }
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
        multiButtonArt.dispose();

        for (int i = 0; i < dollPictureArray.size(); i++) {
            dollPictureArray.get(i).dispose();
        }
        for (int i = 0; i < dollPictureArray.size(); i++) {
            trapPictureArray.get(i).dispose();
        }
        for (int i = 0; i < dollPictureArray.size(); i++) {
            patternPictureArray.get(i).dispose();
        }
    }
    public void listenToSwipe() {

        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {

                Vector3 vector3 = new Vector3(velocityX, velocityY, 0);
                camera.unproject(vector3);

                if (Math.abs(vector3.x) > Math.abs(vector3.y)) {

                    if (vector3.x > 0) {
                        if (isDollScreen == true) {
                            if (swipeCounter < dollPictureArray.size() - 1) {
                                swipeCounter++;
                            }
                        }
                    }

                } else {
                    if (isDollScreen == true) {
                        if (swipeCounter > 0) {
                            swipeCounter--;
                        }
                    }
                }
                return true;
            }
            /**
            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {

                if (lockedForChecking == false) {
                    lockedForChecking = true;
                    startingPoint.x = touchPos.x;
                }
                //If moving touchPos to right
                if (touchPos.x >= startingPoint.x + 350f) {
                    decisionTime += 0.05f;
                    //Register swipe right
                    if (swipeCounter > 0 && decisionTime >= 0.35f) {
                        swipeCounter--;
                    }
                }

                //If moving touchPos to left
                if (touchPos.x <= startingPoint.x - 350f) {
                    decisionTime += 0.05f;
                    //Register swipe left
                    if (swipeCounter < dollPictureArray.size() - 1 && decisionTime >= 0.35f) {
                        swipeCounter++;
                    }
                }

                System.out.println(swipeCounter);
                return true;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {

                decisionTime = 0;
                lockedForChecking = false;
                return true;
            }
            */

    }));

    }
}

