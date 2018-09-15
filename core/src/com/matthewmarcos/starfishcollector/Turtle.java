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
    }


}