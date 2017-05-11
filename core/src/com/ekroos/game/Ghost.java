package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Puoskari on 13.4.2017.
 */

public class Ghost {
    private Animator animator;
    private Array<Ghost> ghostList;
    private TextureRegion currentFrame;
    private Rectangle rectangle;
    private float stateTime;
    private float moveSpeed;
    private SoundManager soundManager;

    /**
     * Ghost that moves towards Ekroos.
     * @param list
     * @param soundManager
     */
    public Ghost(Array<Ghost> list, SoundManager soundManager) {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.35f;
        this.soundManager = soundManager;
        stateTime = 0.0f;
        ghostList = list;
        animator = new Animator();
        animator.createAnimation("traps/spookSheet8.png", stateTime, 8, 1, 1 / 10f);
        currentFrame = animator.getCurrentFrame(stateTime);
        rectangle = new Rectangle(10.2f, 0.7f, currentFrame.getRegionWidth()/60f,
                currentFrame.getRegionHeight()/60f);
    }

    /**
     * Moves ghost towards Ekroos.
     */
    public void move() {
        rectangle.x -= moveSpeed;
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animator.getCurrentFrame(stateTime);
        checkForDispose();
    }

    /**
     * Draws the ghost.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, rectangle.x, rectangle.y, rectangle.getWidth(),
                rectangle.getHeight());
    }

    /**
     * Checks if object is over the map and removes it from array that has all the ghosts if it does.
     */
    public void checkForDispose() {
        if (rectangle.x + rectangle.width < 0f) {
            ghostList.removeValue(this, true);
        }
    }

    /**
     * Eliminates the ghost.
     */
    public void destroy() {
        soundManager.playSound("ghostDead", 0.5f);
        ghostList.removeValue(this, true);
    }

    /**
     * Get the hitbox of the ghost.
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }
}
