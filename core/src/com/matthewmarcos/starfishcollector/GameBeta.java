package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameBeta extends Game {

    protected Stage mainStage;
    protected Stage uiStage;

    public void create() {
        mainStage = new Stage();
        uiStage = new Stage();
        initialize();
    }

    public abstract void initialize();

    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        // Act method
        mainStage.act(dt);

        // User defined
        update(dt);

        // clear screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw the graphics
        mainStage.draw();
        uiStage.act(dt);
        uiStage.draw();
    }

    public abstract void update(float dt);

}
