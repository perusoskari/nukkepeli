package com.ekroos.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Puoskari on 11.3.2017.
 */

public class TouchGrid {
    OrthographicCamera camera;
    GridBall[] balls;
    boolean gridIsDrawn;
    boolean dragStarted;
    ShapeRenderer shapeRenderer;
    Array<GridBall> touchedBalls;

    String pattern;
    ArrayList<Integer> trueTouched;
    ArrayList<Integer> boxArray;
    PatternList box;

    public TouchGrid(OrthographicCamera c) {
        camera = c;
        camera.setToOrtho(false, 10f, 5f);
        shapeRenderer = new ShapeRenderer();
        balls = new GridBall[9];
        gridIsDrawn = false;
        dragStarted = false;
        touchedBalls = new Array<GridBall>();

        pattern = new String();
        trueTouched = new ArrayList<Integer>();
        boxArray = new ArrayList<Integer>();
        box = new PatternList("box",boxArray,1,5);
        box.addBox(boxArray);

        for (int i = 0; i <boxArray.size(); i++) {
            System.out.println(boxArray.get(i));
        }


        for (int i = 0;i < 9;i++) {
            balls[i] = new GridBall();
        }

        if (gridIsDrawn = true) {
            checkInput();
        }

    }

    /**
     * draw the 9 ball grid.
     * @param batch batch.
     */
    public void drawGrid(SpriteBatch batch) {
        gridIsDrawn = true;
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
    /**
    *this method checks what balls are touched and makes a String based on it
    *panStop will use the string to create an object based on String content. ie. "Triangle"
     */
    public String getWhatPattern(Array<GridBall> array) {
            //New Method(work in progress)
            for (int i = 0; i < array.size; i++) {
                //Check if ball is touched..
                //TODO = need a way to stop isTouched from adding multiple same balls to trueTouched array
                if (array.get(i).isTouched == true) {
                    //..then, add the ball number to trueTouched list
                    trueTouched.add(i);
                    //Sort the list from smallest to biggest
                    Collections.sort(trueTouched);
                }
            }
        //this method will work if truetouched starts to work correctly..
        //    if(trueTouched.equals(boxArray)) {
        //        System.out.println("YAY");
        //    }

        for(int i = 0; i < trueTouched.size(); i++) {
            System.out.println(trueTouched.get(i) + " this is truetouched");
        }
        for(int i = 0; i < boxArray.size(); i++) {
            System.out.println(boxArray.get(i) + " this is boxarray");
        }

         //   for(int i = 0; i < trueTouched.size(); i++) {
         //       if (trueTouched.containsAll(box.patternArray)) {
         //           System.out.println("YAY!");
         //       }
         //   }

        return pattern;
    }
    public void checkInput() {
        System.out.println("taalla?");
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                Vector3 vector3 = new Vector3(x, y, 0);
                camera.unproject(vector3);
                Vector2 vector2 = new Vector2(vector3.x, vector3.y);

                for (int i = 0;i < balls.length;i++) {

                    if (balls[i].getRectangle().contains(vector2)) {
                       // if (!balls[i].checkIsTouched()) {
                            Gdx.app.log("p",Integer.toString(i) + "is touched");
                            touchedBalls.add(balls[i]);
                       // }
                        balls[i].setIsTouched(true);
                    }
                }
                return true;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {

                getWhatPattern(touchedBalls);
                System.out.println(pattern);
                pattern = ""; // "empty" the pattern string

                touchedBalls.clear();
                for (int i = 0;i < balls.length;i++) {
                    balls[i].setIsTouched(false);
                }

                return true;
            }
        }));
    }

    public void checkPanStart() {
        if (Gdx.input.isTouched()) {
            Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(vector3);

            for (int i = 0;i < balls.length;i++) {
                if (balls[i].getRectangle().contains(vector3.x, vector3.y)) {
                    if (!balls[i].isTouched) {
                        Gdx.app.log("p", Integer.toString(i) + "is touched");
                        dragStarted = true;
                        touchedBalls.add(balls[i]);
                    }
                    balls[i].setIsTouched(true);
                }
            }
        }


    }

    public void drawLine() {
        if (touchedBalls.size > 1) {
            shapeRenderer.setProjectionMatrix(camera.combined);

            for (int i = 1; i < touchedBalls.size; i++) {
                Vector2 vector2 = touchedBalls.get( i - 1).getPosition();
                Vector2 vector21 = touchedBalls.get(i).getPosition();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.rectLine(vector2.x + 0.18f, vector2.y + 0.18f,
                        vector21.x + 0.18f, vector21.y + 0.18f, 0.1f);
                shapeRenderer.end();
            }
        }
    }
    public void dispose() {

    }
}
