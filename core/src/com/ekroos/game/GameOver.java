package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Puoskari on 7.4.2017.
 *
 */

public class GameOver {
    private Texture texture;
    private Rectangle textureRect;
    private Rectangle restartButton;
    private Rectangle quitButton;
    private Texture restartTexture;

    /**
     * Game over pop up.
     */
    public GameOver() {
        texture = new Texture(Gdx.files.internal("buttonsAndMenu/SmokeTausta2.png"));
        textureRect = new Rectangle(3f, 1f, 5f, 3f);
        restartButton = new Rectangle(textureRect.x + textureRect.getWidth() / 2 - 3f / 2,
                textureRect.y + textureRect.getHeight() / 4 * 2 + 0.5f, 3f, 0.5f);
        quitButton = new Rectangle(restartButton.x,
                restartButton.y - restartButton.getHeight() * 2, 3f, 0.5f);
        restartTexture = new Texture(Gdx.files.internal("buttonsAndMenu/multiButton.png"));
    }

    /**
     * Draws the game over screen and its buttons.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, textureRect.x, textureRect.y + 0.2f, textureRect.getWidth(),
                textureRect.getHeight());
        batch.draw(restartTexture, quitButton.getX(), quitButton.getY(), quitButton.getWidth(),
                quitButton.getHeight());
        batch.draw(restartTexture, restartButton.getX(), restartButton.getY(), restartButton.getWidth(),
                restartButton.getHeight());
        batch.end();
    }

    /**
     * Checks if the restart button is pressed.
     * @param camera
     * @return
     */
    public boolean restartPress(OrthographicCamera camera) {
        camera.setToOrtho(false, 10f, 5f);
        Vector3 vector3 = new Vector3();

        if (Gdx.input.isTouched()) {
            vector3.x = Gdx.input.getX();
            vector3.y = Gdx.input.getY();
            vector3.z = 0;
            camera.unproject(vector3);

            if (restartButton.contains(vector3.x, vector3.y)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the quit button is pressed.
     * @param camera    used to get real x and y of the tap.
     * @return
     */
    public boolean quitPress(OrthographicCamera camera) {
        camera.setToOrtho(false, 10f, 5f);
        Vector3 vector3 = new Vector3();

        if (Gdx.input.isTouched()) {
            vector3.x = Gdx.input.getX();
            vector3.y = Gdx.input.getY();
            vector3.z = 0;
            camera.unproject(vector3);

            if (quitButton.contains(vector3.x, vector3.y)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Disposes buttons textures.
     */
    public void dispose() {
        texture.dispose();
        restartTexture.dispose();
    }

    /**
     * Get the hitbox of quit button.
     * @return
     */
    public Rectangle getQuitButton() {
        return quitButton;
    }

    /**
     * Get the hitbox of restart button.
     * @return
     */
    public Rectangle getRestartButton() {
        return restartButton;
    }
}
