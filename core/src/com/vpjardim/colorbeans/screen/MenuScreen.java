/*
 * Copyright 2015-2018 Vinícius Petrocione Jardim. All rights reserved
 */

package com.vpjardim.colorbeans.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.vpjardim.colorbeans.G;
import com.vpjardim.colorbeans.animation.MenuBeans;
import com.vpjardim.colorbeans.events.Event;
import com.vpjardim.colorbeans.events.EventHandler;
import com.vpjardim.colorbeans.events.EventListener;

/**
 * @author Vinícius Jardim
 * 2015/12/06
 */
public class MenuScreen extends ScreenBase {

    // Todo finish falling beans animation

    public static final int ACT_PLAY     = 10;
    public static final int ACT_TRAINING = 11;
    public static final int ACT_SCORE    = 12;
    public static final int ACT_CONFIG   = 13;

    private Stage stage;
    private MenuBeans beansAnim;
    private Table table;
    private Label label;
    private TextureAtlas.AtlasRegion titleRegion;
    private EventListener specialKeyDown;

    @Override
    public void show() {

        super.show();

        specialKeyDown = (Event e) -> {
            int key = (Integer) e.getAttribute();

            if(key == G.game.data.escBt || key == Input.Keys.BACK)
                Gdx.app.exit();
            else if(key == G.game.data.printScreenBt)
                printScreen();
        };

        EventHandler.getHandler().addListener("SpecialButtons.keyDown", specialKeyDown);

        bgColor = G.game.data.bgColor();

        stage = new Stage(viewport, G.game.batch);
        beansAnim = new MenuBeans();
        G.game.input.addProcessor(stage);

        Table outerT = new Table(G.game.skin);
        table = new Table(G.game.skin);

        outerT.setFillParent(true);
        table.setBackground("tbg");

        TextButton playButt, trainingButt, scoreButt, optionsButt, exitButt;

        playButt = new TextButton("Play!",
                G.game.skin.get("bttGreen", TextButton.TextButtonStyle.class));
        playButt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action = ACT_PLAY;
            }
        });

        trainingButt = new TextButton("Training",
                G.game.skin.get("bttBlue", TextButton.TextButtonStyle.class));
        trainingButt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action = ACT_TRAINING;
            }
        });

        scoreButt = new TextButton("Score Board",
                G.game.skin.get("bttYellow", TextButton.TextButtonStyle.class));
        scoreButt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action = ACT_SCORE;
            }
        });

        optionsButt = new TextButton("Options...",
                G.game.skin.get("bttGray", TextButton.TextButtonStyle.class));
        optionsButt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action = ACT_CONFIG;
            }
        });

        exitButt = new TextButton("Exit",
                G.game.skin.get("bttRed", TextButton.TextButtonStyle.class));
        exitButt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Label.LabelStyle labelStyle =
                G.game.skin.get("labelGTitle", Label.LabelStyle.class);

        float bttW = G.style.buttWidth;
        float padS = G.style.padSmall;

        table.defaults().width(bttW).pad(padS);

        outerT.add();
        outerT.add(table);
        outerT.add();

        table.add().width(G.style.ribbonWidth).height(G.style.ribbonHeight * 0.88f).pad(0, padS, padS, padS);
        table.row();
        table.add(playButt);
        table.row();
        table.add(trainingButt);
        table.row();
        table.add(scoreButt);
        table.row();
        table.add(optionsButt);
        table.row();
        table.add(exitButt).width(bttW).pad(G.style.padMedium, padS, padS, padS);
        table.getMaxWidth();

        label = new Label(" ", labelStyle);
        label.setAlignment(Align.center);
        titleRegion = G.game.atlas.findRegion("game/title");

        stage.addActor(outerT);
        table.setDebug(G.game.dbg.uiTable); // #debugCode
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        beansAnim.update();

        G.game.batch.begin();
        beansAnim.render();
        G.game.batch.end();

        stage.act(delta);
        stage.draw();

        label.setSize(table.getWidth() + G.style.ribbonSide * 2 - G.style.menuBgPad * 2, G.style.ribbonHeight);
        label.setPosition(table.getX() + G.style.menuBgPad - G.style.ribbonSide, G.height -label.getHeight() - table.getY() - table.getHeight() * 0.04f);

        G.game.batch.begin();
        label.draw(G.game.batch, 1f);

        final float titleX = table.getX() + table.getWidth() / 2f - titleRegion.originalWidth / 2f;
        final float titleY = label.getY() + 0.64f * label.getHeight() - titleRegion.originalHeight / 2f;

        G.game.batch.draw(titleRegion, titleX, titleY, titleRegion.originalWidth,
                titleRegion.originalHeight);
        G.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        beansAnim.resize();
    }

    @Override
    public void dispose() {
        super.dispose();
        G.game.input.removeProcessor(stage);
        EventHandler.getHandler().removeListener("SpecialButtons.keyDown", specialKeyDown);
        // Only dispose what does not come from game.assets. Do not dispose skin.
        stage.dispose();
    }
}
