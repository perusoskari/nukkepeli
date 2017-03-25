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

/**
 * Created by Puoskari on 1.3.2017.
 */

public class MainMenu implements Screen {
    Program host;
    SpriteBatch batch;

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    FreeTypeFontGenerator.FreeTypeFontParameter infoParameter;
    BitmapFont font;
    BitmapFont infoFont;

    GlyphLayout startGameGlyph;
    GlyphLayout highScoreGlyph;
    GlyphLayout exitGlyph;
    GlyphLayout nameGlyph;
    GlyphLayout versionGlyph;

    Texture mainMenuArt;
    Texture multiButton;
    Rectangle mainMenuRectangle;
    Rectangle playRectangle;
    Rectangle highScoreRectangle;
    Rectangle exitRectangle;

    Vector3 touchPos;

    OrthographicCamera camera;

    public MainMenu(Program host) {
        this.host = host;
        batch = host.getBatch();

        // Generate font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("MyFont.ttf"));

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        infoParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // Here we can give the font additional changes
        parameter.size = 45;
        infoParameter.size = 25;

        parameter.color = Color.BLACK;
        infoParameter.color = Color.GOLD;

        font = generator.generateFont(parameter);
        infoFont = generator.generateFont(infoParameter);

        //"Glyphs" meaning the actual text, TODO: put the texts behind Strings to make localisation easier later
        startGameGlyph = new GlyphLayout(font, "Start Game");
        highScoreGlyph = new GlyphLayout(font, "High Scores");
        exitGlyph = new GlyphLayout(font, "Exit");
        nameGlyph = new GlyphLayout(infoFont, "Ekroosin eeppiset seikkailut");
        versionGlyph = new GlyphLayout(infoFont, "Version: ??.?");


        //Textures
        mainMenuArt = new Texture("mainMenuArt.png");
        multiButton = new Texture("multiButton.png");

        //Rectangles
        mainMenuRectangle = new Rectangle(0,0,900f, 450f);

        //Buttons are centered with ultimate precision, what could go wrong?
        playRectangle = new Rectangle((mainMenuRectangle.width / 2) - (multiButton.getWidth() / 2),
                mainMenuRectangle.getHeight() - (mainMenuRectangle.getHeight() / 3),
                multiButton.getWidth(), multiButton.getHeight());

        highScoreRectangle = new Rectangle((mainMenuRectangle.width / 2) - (multiButton.getWidth() / 2),
                mainMenuRectangle.getHeight() - (mainMenuRectangle.getHeight() / 2) - multiButton.getHeight() / 2,
                multiButton.getWidth(), multiButton.getHeight());

        exitRectangle = new Rectangle((mainMenuRectangle.width / 2) - (multiButton.getWidth() / 2),
                mainMenuRectangle.getHeight() - (mainMenuRectangle.getHeight() / 3 * 2) - multiButton.getHeight(),
                multiButton.getWidth(), multiButton.getHeight());


        camera = new OrthographicCamera();
        camera.setToOrtho(false, mainMenuArt.getWidth(), mainMenuArt.getHeight());
        touchPos = new Vector3();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(camera.combined);
        getTouchPos();
        whatIsTouched();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Gdx.app.log("in mm", "render");
        this.dispose();
        //host.setScreen(host.getGameScreen());

        batch.begin();
        draw(batch);
        batch.end();
    }
    public void draw(SpriteBatch sb) {

        sb.draw(mainMenuArt, mainMenuRectangle.x, mainMenuRectangle.y
                , mainMenuRectangle.getWidth(), mainMenuRectangle.getHeight());

        sb.draw(multiButton, playRectangle.x, playRectangle.y
                , playRectangle.getWidth(), playRectangle.getHeight());

        sb.draw(multiButton, highScoreRectangle.x, highScoreRectangle.y
                , highScoreRectangle.getWidth(), highScoreRectangle.getHeight());

        sb.draw(multiButton, exitRectangle.x, exitRectangle.y
                , exitRectangle.getWidth(), exitRectangle.getHeight());

        //More centering of the texts
        font.draw(sb,startGameGlyph, playRectangle.x + playRectangle.getWidth() / 2 - startGameGlyph.width / 2
                , playRectangle.y + playRectangle.getHeight() - 5f);

        font.draw(sb,highScoreGlyph, highScoreRectangle.x + highScoreRectangle.getWidth() / 2 - highScoreGlyph.width / 2
                , highScoreRectangle.y + highScoreRectangle.getHeight() - 8f);

        font.draw(sb,exitGlyph, exitRectangle.x + exitRectangle.getWidth() / 2 - exitGlyph.width / 2
                , exitRectangle.y + exitRectangle.getHeight() - 8f);

        infoFont.draw(sb, nameGlyph, 0, mainMenuRectangle.getHeight());
        infoFont.draw(sb, versionGlyph, 0, mainMenuRectangle.getHeight() - nameGlyph.height);
    }

    //Getting the touch position
    public void getTouchPos() {

        if (Gdx.input.isTouched()) {

            touchPos.x = Gdx.input.getX();
            touchPos.y = Gdx.input.getY();
            camera.unproject(touchPos);

            System.out.println("X: " + touchPos.x);
            System.out.println("Y: " + touchPos.y);

        }
    }

    //Check what is touched using Rectangles of the main screen buttons
    //TODO: dispose everything properly once moving to a new screen
    public void whatIsTouched() {

        if (Gdx.input.isTouched()) {

            //If play button is touched move to GameScreen
            if (touchPos.x >= playRectangle.x &&
                    touchPos.x <= playRectangle.x + playRectangle.getWidth() &&
                    touchPos.y >= playRectangle.y &&
                    touchPos.y <= playRectangle.y + playRectangle.getHeight())
            {
                host.setScreen(host.getGameScreen());
                dispose();
            }

            if (touchPos.x >= highScoreRectangle.x &&
                    touchPos.x <= highScoreRectangle.x + highScoreRectangle.getWidth() &&
                    touchPos.y >= highScoreRectangle.y &&
                    touchPos.y <= highScoreRectangle.y + highScoreRectangle.getHeight())
            {
                System.out.println("High Score not yet Implemented!");
                dispose();
            }
            if (touchPos.x >= exitRectangle.x &&
                    touchPos.x <= exitRectangle.x + exitRectangle.getWidth() &&
                    touchPos.y >= exitRectangle.y &&
                    touchPos.y <= exitRectangle.y + exitRectangle.getHeight())
            {
                System.out.println("Bye!");
                Gdx.app.exit();
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
    }
}
