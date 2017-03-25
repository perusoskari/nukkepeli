package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Puoskari on 11.3.2017.
 */

public class TouchGrid {
    private OrthographicCamera camera;
    private GridBall[] balls;
    private boolean gridIsDrawn;
    private ShapeRenderer shapeRenderer;
    private Array<GridBall> touchedBalls;
    private SpriteBatch batch;

    private String pattern;
    private ArrayList<Integer> trueTouched;
    private ArrayList<Integer> boxArray;
    private PatternList box;
    private boolean isDrawing;
    private int addNumber;

    Dolls dolls;
    private Array<TrapTile> listOfTraps;
    Vector2 vector2;

    public TouchGrid(OrthographicCamera c, SpriteBatch batch, Array<TrapTile> listOfTraps) {
        vector2 = new Vector2();
        camera = c;
        camera.setToOrtho(false, 10f, 5f);
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        balls = new GridBall[9];
        gridIsDrawn = false;
        touchedBalls = new Array<GridBall>();

        pattern = new String();
        trueTouched = new ArrayList<Integer>();
        boxArray = new ArrayList<Integer>();
        // The box shape, list of shapes could be elsewhere
        box = new PatternList("box",boxArray,1,5);
        box.addBox(boxArray);
        isDrawing = false;
        addNumber = 0;

        dolls = new Dolls(batch);
        this.listOfTraps = listOfTraps;

        for (int i = 0; i <boxArray.size(); i++) {
           // System.out.println(boxArray.get(i));
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
     * and activate the current dolls (move the plank etc).
     */
    public void drawGrid() {
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

                //Number the balls
                balls[ballNumber].setBallNumber(ballNumber);

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

        dolls.dollsActivate();
    }

    /**
    *this method checks what balls are touched and makes a String based on it
    *panStop will use the string to create an object based on String content. IE. "Triangle"
     */
    public String getWhatPattern(GridBall[] array) {

            for (int i = 0; i < array.length; i++) {

                    //Check if ball is touched..
                    if (array[i].isTouched == true) {

                        //First touched ball is always added
                        if (i == 0) {
                            trueTouched.add(array[i].getBallNumber());

                        }
                        //Add balls only if the next ball is not the same as the one before
                        else if (array[i] != array[i - 1]) {
                            trueTouched.add(array[i].getBallNumber());
                        }
                    }
            }
        //Sort the list for the first time so the smallest number is the one to get added again
        Collections.sort(trueTouched);

        //If a ball is touched twice aka shape is ready add the smallest number to the list
        for (int i = 0; i < array.length; i++) {
            if (array[i].touchedTwice == true) {

                trueTouched.add(trueTouched.get(0));
            }
        }
        //Sort the list second time so the added number goes to the beginning of the list.
        //This is because when detecting what shape is in question the arrays are formatted in a way
        //which lists numbers from smallest to biggest
        Collections.sort(trueTouched);

        for (int i = 0; i < trueTouched.size(); i++) {
            System.out.println(trueTouched.get(i));
        }

        //If trueTouched is the same as some shape, change the pattern String accordingly.
        // TODO: A method to check against all patterns and shapes so we don't have a bloated list here.
            if(trueTouched.equals(boxArray)) {
                pattern = "box";
            }

        return pattern;
    }
    public void checkInput() {

        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                Vector3 vector3 = new Vector3(x, y, 0);
                camera.unproject(vector3);

                vector2.x = vector3.x;
                vector2.y = vector3.y;


                for (int i = 0;i < balls.length;i++) {

                    if (balls[i].getRectangle().contains(vector2)) {
                        touchedBalls.add(balls[i]);
                        balls[i].setIsTouched(true);
                    }
                }

                //Start calculating the time when the ball was touched
                for (int i = 0; i < balls.length; i++) {
                    if(balls[i].isTouched == true) {
                        balls[i].realityCheck();
                    }
                }
                return true;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {

                //Copied from pan, check for the last time where the vector is and make the containing ball touchedTwice;
                for (int i = 0;i < balls.length;i++) {

                    //Set the ball to touched twice if it contains the coordinates of your finger, also the ball must have been touched before
                    //The touch must also have happened relatively long time ago, this is because otherwise panStop will go nuts
                    if (balls[i].getRectangle().contains(vector2) && balls[i].timeAlive > 0.5f && balls[i].isTouched == true) {
                        balls[i].setTouchedTwice();
                    }
                }

                dolls.useDoll(getWhatPattern(balls), listOfTraps);

                //Clear those that twice were touched, also alter the shape of time
                for (int i = 0; i < balls.length; i ++) {
                    balls[i].touchedTwice = false;
                    balls[i].timeAlive = 0;
                }

                //clear those whom truly were touched
                trueTouched.clear();

                // "empty" the pattern string
                pattern = "";

                touchedBalls.clear();
                for (int i = 0;i < balls.length;i++) {
                    balls[i].setIsTouched(false);
                }

                return true;
            }
        }));
    }

    public void fingerLifted() {
        if (!Gdx.input.isTouched()) {
            trueTouched.clear();
            touchedBalls.clear();
            pattern = "";

            for (int i = 0;i < balls.length;i++) {
                balls[i].setIsTouched(false);
            }
        }
    }

    public void checkPanStart() {
        if (Gdx.input.isTouched()) {
            Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(vector3);

            for (int i = 0;i < balls.length;i++) {
                if (balls[i].getRectangle().contains(vector3.x, vector3.y)) {
                    if (!balls[i].isTouched) {
                        touchedBalls.add(balls[i]);
                    }
                    balls[i].setIsTouched(true);
                }
            }
        }

        fingerLifted();

    }

    public void drawLine() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        float center = balls[0].getRectangle().getWidth()/2;

        if (touchedBalls.size > 1) {

            for (int i = 1; i < touchedBalls.size; i++) {
                Vector2 vector2 = touchedBalls.get( i - 1).getPosition();
                Vector2 vector21 = touchedBalls.get(i).getPosition();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.rectLine(vector2.x + center, vector2.y + center,
                        vector21.x + center, vector21.y + center, 0.1f);
                shapeRenderer.end();
            }
        }

        if (touchedBalls.size > 0) {
            if (Gdx.input.isTouched()) {
                Vector2 vector2 = touchedBalls.get(touchedBalls.size - 1).getPosition();
                Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(vector3);
                Vector2 vector21 = new Vector2(vector3.x, vector3.y);

                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.rectLine(vector2.x + center, vector2.y + center,
                        vector21.x, vector21.y, 0.1f);
                shapeRenderer.end();
            }
        }
    }
    public void dispose() {

    }
}
