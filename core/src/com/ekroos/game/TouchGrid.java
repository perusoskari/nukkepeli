package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
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
    private boolean trueOrFalse;
    private ShapeRenderer shapeRenderer;
    private Array<GridBall> touchedBalls;
    private SpriteBatch batch;

    private String pattern;

    //This is the magic list which contains all the patterns to check against
    private ArrayList<PatternList> allPatterns;

    private ArrayList<Integer> trueTouched;

    //Patterns
    //Box
    private ArrayList<Integer> boxArray;
    private PatternList box;
    //Weight
    private ArrayList<Integer> weightArray;
    private PatternList weight;
    //Spike
    private ArrayList<Integer> spikeArray;
    private PatternList spike;

    private boolean isDrawing;
    private int addNumber;
    private Rectangle touchPosition;
    private int counter;

    Dolls dolls;
    private MainDoll mainDoll;
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


        trueTouched = new ArrayList<Integer>();

        pattern = new String();
        createPatterns();


        isDrawing = false;
        addNumber = 0;
        touchPosition = new Rectangle(0f, 0f, 0.26f, 0.26f);

        dolls = new Dolls(batch);
        mainDoll = new MainDoll();
        this.listOfTraps = listOfTraps;

        for (int i = 0;i < 9;i++) {
            balls[i] = new GridBall();
        }

        if (gridIsDrawn = true) {
            checkInput();
        }

        counter = 0;
    }

    /**
     * This method creates all the patterns used in the game.
     * patternList constructor takes in parameters: name, integerlist of balls needed to touch,
     * number of the shape, size of the shape.
     */
    public void createPatterns() {

        //This is a list of all patterns to check against
        allPatterns = new ArrayList<PatternList>();

        //Box pattern
        boxArray = new ArrayList<Integer>();
        box = new PatternList("box",boxArray,1,5);
        box.addBox(boxArray);

        //Weight pattern
        weightArray = new ArrayList<Integer>();
        weight = new PatternList("weight",weightArray,2,6);
        weight.addWeight(weightArray);


        //Spike pattern
        spikeArray = new ArrayList<Integer>();
        spike = new PatternList("spike", spikeArray,3,4);
        spike.addSpike(spikeArray);

        //Add to allPatterns
        allPatterns.add(box);
        allPatterns.add(weight);
        allPatterns.add(spike);

    }

    /**
     * draw the 9 ball grid.
     * and activate the current dolls (move the plank etc).
     */
    public void drawGrid() {
        gridIsDrawn = true;
        float x = 8f;
        float y = 4f;
        int gridWidth = 1;
        float gridSpace = 0f;
        float gridVerticalSpace = 0f;
        int ballNumber = 0;
        float gridSpacePlus = 1.4f;

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

        counter++;
        dolls.dollsDraw();
        mainDoll.draw(batch);
    }

    public void dollsMove(float x, float y) {
        dolls.dollsMove();
        mainDoll.move(x, y);
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
                            //Save the first touched ball into integer for the panStop


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

        checkAgainstKnownPatterns(trueTouched, allPatterns);

        return pattern;
    }

    /**
     * This method changes pattern string according to the shape that has been drawn.
     * If touched balls equal any known pattern it will be recognized.
     * @param trueTouched
     * @param allPatterns
     */
    public void checkAgainstKnownPatterns(ArrayList<Integer> trueTouched, ArrayList<PatternList> allPatterns) {

        //Goes through the list of known pattern and so on..
        for (int i = 0; i < allPatterns.size(); i++) {
            if (trueTouched.equals(allPatterns.get(i).getPatternArray())) {
                pattern = allPatterns.get(i).getName();
                System.out.println(pattern + " pattern");
            }
        }
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

                    if (balls[i].getRectangle().overlaps(touchPosition)) {
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

                // Coordinate magic on panStop coordinates, apparently these do not obey rest of the code
                //x = x / 100;
                //y = y / 100;
                //y = 4.5f - y;
                x = vector2.x;
                y = vector2.y;

                balls = isTouchedTwice(x, y, balls);

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

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                float x = touchPosition.getX();

                if (x <= 6.4f && counter > 30) {
                    mainDoll.startFlight();
                }
                System.out.println(x);
            return true;
            }
        }));
    }

    /**
     * Check if the pattern ends to the first ball to make it touchedTwice
     */
    public GridBall[] isTouchedTwice(float x, float y, GridBall[] balls) {


        if (touchedBalls.size > 0) {
            System.out.println("touchX: " + x + " TouchY: " + y + "@isTouchedTwice");
            System.out.println(touchedBalls.get(0).getRectangle().x + " firstTouched X @isTouchedTwice");
            System.out.println(touchedBalls.get(0).getRectangle().y + " firstTouched Y @isTouchedTwice");
            //Rite of passage for all balls who want to be touched again
            //Add an error margin to the x coordinate of the panStop, this can be fine tuned later
            if (x <= touchedBalls.get(0).getRectangle().x + 0.8f && x >= touchedBalls.get(0).getRectangle().x - 0.8f) {

                //Add an error margin to the y coordinate of the panStop, this can be fine tuned later
                if (y <= touchedBalls.get(0).getRectangle().y + 0.8f && y >= touchedBalls.get(0).getRectangle().y - 0.8f) {

                    //Set the ball to touched twice if it contains the coordinates of your finger, also the ball must have been touched before
                    //The touch must also have happened relatively long time ago, this is because otherwise panStop will go nuts
                    // balls[i].getRectangle().contains(vector2) <-- this is deleted from if below for testing purposes
                    if (touchedBalls.get(0).timeAlive > 0.5f && touchedBalls.get(0).isTouched == true) {
                        touchedBalls.get(0).setTouchedTwice();
                    }
                }
            }
        }
        return balls;
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

    public void touchPositionMove() {


        if (!Gdx.input.isTouched()) {
            touchPosition.setX(-1f);
            touchPosition.setY(-1f);
        } else {
            Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(vector3);
            touchPosition.setX(vector3.x - (touchPosition.getWidth()/2));
            touchPosition.setY(vector3.y - (touchPosition.getHeight()/2));
        }
    }

    public void checkPanStart() {
        if (Gdx.input.isTouched()) {
            Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(vector3);

            for (int i = 0;i < balls.length;i++) {
                //if (balls[i].getRectangle().contains(vector3.x, vector3.y)) {
                if (balls[i].getRectangle().overlaps(touchPosition)) {
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
                shapeRenderer.setColor(255, 0, 0, 1);
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
                shapeRenderer.setColor(255, 0, 0, 1);
                shapeRenderer.rectLine(vector2.x + center, vector2.y + center,
                        vector21.x, vector21.y, 0.1f);
                shapeRenderer.end();
            }
        }
    }
    public void dispose() {

    }
}
