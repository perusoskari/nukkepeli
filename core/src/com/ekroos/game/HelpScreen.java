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

    Texture helpScreenArt;
    Texture multiButtonArt;

    private Rectangle helpScreenRectangle;
    private Rectangle upperScreenRectangle;
    private Rectangle dollRectangle;
    private Rectangle trapRectangle;
    private Rectangle patternRectangle;
    private Rectangle textRectangle;
    private Rectangle backButtonRectangle;

    private ArrayList<Texture> dollPictureArray;
    private ArrayList<Texture> trapPictureArray;
    private ArrayList<Texture> patternPictureArray;

    private ArrayList<GlyphLayout> textArray;
    private CharSequence textToChar;
    private CharSequence outOfHowManyChar;
    private GlyphLayout charToArray;
    private GlyphLayout screenTitle;
    private GlyphLayout backText;
    private GlyphLayout outOfHowManyGlyph;

    private BitmapFont font;
    private BitmapFont otherTextFont;

    private OrthographicCamera camera;
    private Vector3 touchPos;
    private float decisionTime;

    int swipeCounter;
    private Vector2 startingPoint;
    private boolean lockedForChecking;
    private Bundlenator myBundle;

    public HelpScreen(Program host) {

        this.host = host;
        batch = host.getBatch();

        helpScreenArt = new Texture("buttonsAndMenu/helpScreenArt.png");
        multiButtonArt = new Texture("buttonsAndMenu/multiButton.png");

        helpScreenRectangle = new Rectangle(0,0,
                helpScreenArt.getWidth(), helpScreenArt.getHeight());

        upperScreenRectangle = new Rectangle(helpScreenRectangle.x,
                helpScreenRectangle.getHeight() - multiButtonArt.getHeight(),
                helpScreenRectangle.getWidth(),
                multiButtonArt.getHeight());

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

        camera = new OrthographicCamera();
        camera.setToOrtho(false, helpScreenArt.getWidth(), helpScreenArt.getHeight());
        touchPos = new Vector3();
        decisionTime = 0;
        swipeCounter = 0;
        startingPoint = new Vector2(0,0);
        lockedForChecking = false;

        textToChar = "";
        myBundle = new Bundlenator();
        font = myBundle.getDescriptionFont();
        otherTextFont = myBundle.getHighlyVisibleFont();
        charToArray = new GlyphLayout(font, textToChar);

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

        batch.begin();
        draw(batch);
        batch.end();

    }
    public void draw(SpriteBatch batch) {

        batch.draw(helpScreenArt, helpScreenRectangle.x, helpScreenRectangle.y,
                helpScreenRectangle.getWidth(), helpScreenRectangle.getHeight());

        batch.draw(multiButtonArt, upperScreenRectangle.x, upperScreenRectangle.y,
                upperScreenRectangle.getWidth(), upperScreenRectangle.getHeight());

        batch.draw(multiButtonArt, backButtonRectangle.x, backButtonRectangle.y,
                backButtonRectangle.getWidth(), backButtonRectangle.getHeight());

        otherTextFont.draw(batch, screenTitle,
                upperScreenRectangle.getWidth() / 2 - screenTitle.width / 2,
                helpScreenRectangle.getHeight());

        otherTextFont.draw(batch, backText,
                backButtonRectangle.x + backButtonRectangle.getWidth() / 2 - backText.width / 2,
                backButtonRectangle.y + backButtonRectangle.getHeight() / 2 + backText.height / 2);

        drawArrays(batch);

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
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(puuNukkeTexture);
        dollPictureArray.add(puuNukkeTexture);


        //Pictures of traps
        Texture piikkiAnsaTexture = new Texture("helpScreenStuff/spikeTrap.png");
        Texture pimeysTexture = new Texture("helpScreenStuff/darknessTrap.png");
        Texture weightTexture = new Texture("helpScreenStuff/weightTrap.png");
        Texture drumTexture = new Texture("helpScreenStuff/drumTrap.png");
        Texture spookTexture = new Texture("helpScreenStuff/spookTrap.png");
        Texture waterTexture = new Texture("helpScreenStuff/waterTrap.png");
        Texture zombieTexture = new Texture("helpScreenStuff/zombieTrap.png");
        Texture hatTexture = new Texture("helpScreenStuff/hatTrap.png");
        trapPictureArray.add(piikkiAnsaTexture);
        trapPictureArray.add(pimeysTexture);
        trapPictureArray.add(weightTexture);
        trapPictureArray.add(drumTexture);
        trapPictureArray.add(spookTexture);
        trapPictureArray.add(waterTexture);
        trapPictureArray.add(zombieTexture);
        trapPictureArray.add(hatTexture);


        //Pictures of patterns
        Texture spikeTrapDrawnTexture = new Texture("helpScreenStuff/spikeTrapDrawn.png");
        Texture boxTrapDrawnTexture = new Texture("helpScreenStuff/darknessTrapDrawn.png");
        Texture weightTrapDrawnTexture = new Texture("helpScreenStuff/weightTrapDrawn.png");
        Texture drumTrapDrawnTexture = new Texture("helpScreenStuff/drumTrapDrawn.png");
        Texture spookTrapDrawnTexture = new Texture("helpScreenStuff/spookTrapDrawn.png");
        Texture waterTrapDrawnTexture = new Texture("helpScreenStuff/waterTrapDrawn.png");
        Texture zombieTrapDrawnTexture = new Texture("helpScreenStuff/zombieTrapDrawn.png");
        Texture hatTrapDrawnTexture = new Texture("helpScreenStuff/hatTrapDrawn.png");

        patternPictureArray.add(spikeTrapDrawnTexture);
        patternPictureArray.add(boxTrapDrawnTexture);
        patternPictureArray.add(weightTrapDrawnTexture);
        patternPictureArray.add(drumTrapDrawnTexture);
        patternPictureArray.add(spookTrapDrawnTexture);
        patternPictureArray.add(waterTrapDrawnTexture);
        patternPictureArray.add(zombieTrapDrawnTexture);
        patternPictureArray.add(hatTrapDrawnTexture);



    }
    public void loadDescriptions() {

        outOfHowManyGlyph = new GlyphLayout(font, "");
        textToChar = myBundle.getLocal("helpScreenTitle");
        screenTitle = new GlyphLayout(otherTextFont, textToChar);
        textToChar = myBundle.getLocal("back");
        backText = new GlyphLayout(otherTextFont, textToChar);

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

                if (Math.abs(velocityX) > Math.abs(velocityY)) {
                    System.out.println(velocityX + " velocity x");
                    if (velocityX > 0) {

                        if (swipeCounter < dollPictureArray.size() - 1) {
                            swipeCounter++;
                        }
                    }

                } else {
                    if (swipeCounter > 0) {
                        swipeCounter--;
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

