package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Turtle extends BaseActor {

    private boolean isAlive;

    public Turtle(float x, float y, Stage s) {
        super(x, y, s, "Turtle");
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
        isAlive = true;
    }

    public void act(float dt) {
        super.act(dt);

        if(isAlive) {
            if (Gdx.input.isKeyPressed(Keys.LEFT))
                accelerateAtAngle(180);
            if (Gdx.input.isKeyPressed(Keys.RIGHT))
                accelerateAtAngle(0);
            if (Gdx.input.isKeyPressed(Keys.UP))
                accelerateAtAngle(90);
            if (Gdx.input.isKeyPressed(Keys.DOWN))
                accelerateAtAngle(270);
        }

        applyPhysics(dt);
        setAnimationPaused(!isMoving());
        if (getSpeed() > 0)
            setRotation(getMotionAngle());

        boundToWorld();
        alignCamera();

    }

    public void die() {
        clearActions();
        addAction(Actions.fadeOut(1));
        isAlive = false;
    }
}