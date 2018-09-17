package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Shark extends BaseActor {
    private boolean direction;
    private int distanceTravelled;
    private float angle;
    private boolean justFlipped;

    public Shark(float x, float y, Stage s) {
        super(x, y, s, "Shark");
        loadTexture("sharky.png");
        setBoundaryPolygon(8);
        direction = true;
        distanceTravelled = 0;


        setAcceleration(300);
        setMaxSpeed(100);
        setDeceleration(300);

        angle = 0;
    }

    public void act(float dt) {
        super.act(dt);
        distanceTravelled++;

        if(distanceTravelled % (60 * 3) == 0) {
            distanceTravelled = 0;
            this.angle += 180;
            justFlipped = true;
        }

        accelerateAtAngle(angle);
        float motionAngle = getMotionAngle() % 360;

        if(motionAngle > 90 && motionAngle <= 270 && justFlipped && getAcceleration() != 0) {
            this.animation.getKeyFrame(dt).flip(true, false);
            justFlipped = false;

        }

        applyPhysics(dt);
    }
}
