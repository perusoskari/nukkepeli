package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Puoskari on 11.3.2017.
 */

public class TouchGrid {
    OrthographicCamera camera;
    GridBall[] balls;

    public TouchGrid(OrthographicCamera c) {
        camera = c;
        balls = new GridBall[9];

        for (int i = 0;i < 9;i++) {
            balls[i] = new GridBall();
        }
    }

    /**
     * draw the 9 ball grid.
     * @param batch batch.
     */
    public void drawGrid(SpriteBatch batch) {
        float x = 5f;
        float y = 4.5f;
        int gridWidth = 1;
        float gridSpace = 0f;
        float gridVerticalSpace = 0f;
        int ballNumber = 0;
        float gridSpacePlus = 1.2f;

        for (int i = 0;i < 5;i++) {

            for (int j = 0; j < gridWidth; j++) {

            balls[ballNumber].setLocation(x + gridSpace, y - gridVerticalSpace);
            balls[ballNumber].drawThis(batch);
            gridSpace += gridSpacePlus;

                    ballNumber++;
            }

            gridSpace = 0f;
            if (i < 2) {
                gridWidth++;
                gridSpace -= (gridSpacePlus/2) * (i + 1);
            } else if (i >= 2){
                gridWidth--;
                gridSpace -= (gridSpacePlus/2) * (gridWidth/2);
            }

            gridVerticalSpace += gridSpacePlus/2;
        }
    }
}
