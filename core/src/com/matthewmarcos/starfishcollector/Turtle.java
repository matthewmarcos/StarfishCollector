package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turtle extends BaseActor {
    public Turtle(float x, float y, Stage s) {
        super(x, y, s);
        int imageCount = 6; // Iterate 1 through 6 for filenames
        String[] fileNames = new String[imageCount];
        for(int i = 0 ; i < imageCount ; i++) {
            fileNames[i] = String.format("turtle-%d.png", i + 1);
        }

        loadAnimationFromFiles(fileNames, 0.1f, true);
        setBoundaryPolygon(8);

        setAcceleration(500);
        setMaxSpeed(100);
        setDeceleration(500);
    }

    public void act(float dt) {
        super.act( dt );
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            accelerateAtAngle(180);
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            accelerateAtAngle(0);
        if (Gdx.input.isKeyPressed(Keys.UP))
            accelerateAtAngle(90);
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            accelerateAtAngle(270);
        applyPhysics(dt);
        setAnimationPaused(!isMoving());
        if (getSpeed() > 0)
            setRotation(getMotionAngle());

    }


}