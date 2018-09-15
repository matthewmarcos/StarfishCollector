package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StarfishCollectorBeta extends GameBeta {

    private Turtle turtle;
    private ActorBeta starfish;
    private ActorBeta shark;
    private ActorBeta ocean;
    private ActorBeta winMessage;
    private ActorBeta gameOverMessage;

    private boolean win;

    public void initialize() {
        mainStage = new Stage();

        ocean = new ActorBeta();
        ocean.setTexture(new Texture(Gdx.files.internal("water.jpg")));
        mainStage.addActor(ocean);

        starfish = new ActorBeta();
        starfish.setTexture(new Texture(Gdx.files.internal("starfish.png")));
        starfish.setPosition(380, 380);
        mainStage.addActor(starfish);

        shark = new ActorBeta();
        shark.setTexture(new Texture(Gdx.files.internal("sharky.png")));
        shark.setPosition(380, 20);
        mainStage.addActor(shark);

        turtle = new Turtle();
        turtle.setTexture(new Texture(Gdx.files.internal("turtle-1.png")));
        turtle.setPosition(20, 20);
        mainStage.addActor(turtle);

        winMessage = new ActorBeta();
        winMessage.setTexture(new Texture(Gdx.files.internal("you-win.png")));
        winMessage.setPosition(180,180);
        winMessage.setVisible(false);
        mainStage.addActor(winMessage);

        gameOverMessage = new ActorBeta();
        gameOverMessage.setTexture(new Texture(Gdx.files.internal("game-over.png")));
        gameOverMessage.setPosition(180,180);
        gameOverMessage.setVisible(false);
        mainStage.addActor(gameOverMessage);

    }

    public void update(float dt) {
        // check win condition: turtle must be overlapping starfish
        if (turtle.overlaps(starfish)) {
            starfish.remove();
            winMessage.setVisible(true);
        }

        if (turtle.overlaps(shark)) {
            turtle.remove();
            gameOverMessage.setVisible(true);
        }

    }
}
