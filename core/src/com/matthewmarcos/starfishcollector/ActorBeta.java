package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorBeta extends Actor {
    private TextureRegion textureRegion;
    private Rectangle rectangle;

    public ActorBeta() {
        super();
        this.textureRegion = new TextureRegion();
        this.rectangle = new Rectangle();
    }

    public void setTexture(Texture t) {
        this.textureRegion.setRegion(t);
        setSize(t.getWidth(), t.getHeight());
        rectangle.setSize(t.getWidth(), t.getHeight());
    }

    public Rectangle getRectangle() {
        this.rectangle.setPosition(getX(), getY());
        return this.rectangle;
    }

    public boolean overlaps(ActorBeta other) {
        return this.getRectangle().overlaps(other.getRectangle());
    }

    public void act(float dt) {
        super.act(dt);
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color c = getColor(); // used to apply tint color effect

        batch.setColor(c.r, c.g, c.b, c.a);

        if(isVisible()) {
            batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                       getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
