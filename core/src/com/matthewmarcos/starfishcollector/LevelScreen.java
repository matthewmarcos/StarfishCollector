package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class LevelScreen extends BaseScreen {
    private Turtle turtle;
    private BaseActor ocean;
    private boolean gameFinished;

    public void initialize() {
        ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);
        BaseActor.setWorldBounds(ocean);

        new Starfish(400, 400, mainStage);
        new Starfish(500, 100, mainStage);
        new Starfish(100, 450, mainStage);
        new Starfish(200, 250, mainStage);

        new Rock(200, 150, mainStage);
        new Rock(100, 300, mainStage);
        new Rock(300, 350, mainStage);
        new Rock(450, 200, mainStage);

        new Shark(200, 20, mainStage);

        turtle = new Turtle(20, 20, mainStage);
        gameFinished = false;
    }

    public void update(float dt) {
        for(BaseActor rock : BaseActor.getList(mainStage, "Rock")) {
            turtle.preventOverlap(rock);
        }

        for(BaseActor sharkActor : BaseActor.getList(mainStage, "Shark")) {
            Shark shark = (Shark)sharkActor;
            if (turtle.overlaps(shark) && !gameFinished) {
                gameFinished = true;

                Whirlpool w = new Whirlpool(0, 0, mainStage);
                w.centerAtActor(turtle);
                w.setOpacity(0.25f);

                BaseActor gameOverMessage = new BaseActor(0, 0, uiStage);
                gameOverMessage.loadTexture("game-over.png");
                // TODO: Make a constant for screen width and height
                gameOverMessage.centerAtPosition(400, 300);
                gameOverMessage.moveBy(0, 100);
                gameOverMessage.setOpacity(0);
                gameOverMessage.addAction(Actions.after(Actions.fadeIn(1)));


                turtle.die();
            }
        }

        for(BaseActor starfishActor : BaseActor.getList(mainStage, "Starfish")) {
            Starfish starfish = (Starfish) starfishActor;

            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect();

                Whirlpool w = new Whirlpool(0, 0, mainStage);
                w.centerAtActor(starfish);
                w.setOpacity(0.25f);
            }
        }

        if(BaseActor.count(mainStage, "Starfish") == 0 && !gameFinished) {
            gameFinished = true;

            BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
            youWinMessage.loadTexture("you-win.png");
            // TODO: Make a constant for screen width and height
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.moveBy(0, 100);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));

        }

        if(gameFinished) {

            BaseActor continueMessage = new BaseActor(0, 0, uiStage);
            continueMessage.loadTexture("message-continue.png");
            // TODO: Make a constant for screen width and height
            continueMessage.centerAtPosition(400, 300);
            continueMessage.moveBy(0, -100);
            continueMessage.setOpacity(0);
            continueMessage.addAction(Actions.after(Actions.fadeIn(1)));

            if(Gdx.input.isKeyPressed(Input.Keys.C)) {
                StarfishGame.setActiveScreen(new LevelScreen());
            }

        }

    }
}
