package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class StarfishCollector extends GameBeta {
    private Turtle turtle;
    private Starfish starfish;
    private BaseActor ocean;
    private Rock rock;

    public void initialize() {
        ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);
        starfish = new Starfish(380, 380, mainStage);
        rock = new Rock(200, 200, mainStage);
        turtle = new Turtle(20, 20, mainStage);
    }

    public void update(float dt) {
        turtle.preventOverlap(rock);

        if(turtle.overlaps(starfish) && !starfish.isCollected()) {
            starfish.collect();

            Whirlpool w = new Whirlpool(0, 0, mainStage);
            w.centerAtActor(starfish);
            w.setOpacity(0.25f);

            BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
            youWinMessage.loadTexture("you-win.png");
            // TODO: Make constant for screen width and height
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));

        }
    }
}
