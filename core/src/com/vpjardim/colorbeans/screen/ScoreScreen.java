/*
 * Copyright 2015-2018 Vinícius Petrocione Jardim. All rights reserved
 */

package com.vpjardim.colorbeans.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.vpjardim.colorbeans.G;
import com.vpjardim.colorbeans.core.ScoreTable;
import com.vpjardim.colorbeans.events.Event;
import com.vpjardim.colorbeans.events.EventHandler;
import com.vpjardim.colorbeans.events.EventListener;

/**
 * @author Vinícius Jardim
 * 2016/09/03
 */
public class ScoreScreen extends ScreenBase {

    // Todo finish score screen

    private Stage stage;
    private EventListener specialKeyDown;

    @Override
    public void show() {

        super.show();

        specialKeyDown = (Event e) -> {
            int key = (Integer) e.getAttribute();
            if(key == G.game.data.escBt || key == Input.Keys.BACK)
                action = ACT_NEXT;
            else if(key == G.game.data.printScreenBt)
                printScreen();
        };

        EventHandler.getHandler().addListener("SpecialButtons.keyDown", specialKeyDown);

        bgColor = G.game.data.bgColor();

        stage = new Stage(viewport, G.game.batch);
        G.game.input.addProcessor(stage);

        // ==== Tables ====
        Table titleT   = new Table(G.game.skin);
        Table contentT = new Table(G.game.skin);
        Table tabT     = new Table(G.game.skin);
        Table campaignT    = new Table(G.game.skin);
        Table trainingT   = new Table(G.game.skin);

        titleT.setFillParent(true);
        titleT.setBackground("tbg");

        // #debugCode
        titleT.setDebug(G.game.dbg.uiTable);
        contentT.setDebug(G.game.dbg.uiTable);
        tabT.setDebug(G.game.dbg.uiTable);
        campaignT.setDebug(G.game.dbg.uiTable);
        trainingT.setDebug(G.game.dbg.uiTable);

        // ==== Labels ====
        Label.LabelStyle labelStyle =
                G.game.skin.get("labelDef", Label.LabelStyle.class);

        Label trainingL = new Label("Content 2", labelStyle);

        // ==== Buttons ====
        final TextButton backBtt = new TextButton("Back",
                G.game.skin.get("bttYellow", TextButton.TextButtonStyle.class));
        final TextButton campaignButt = new TextButton("Campaign",
                G.game.skin.get("bttRed", TextButton.TextButtonStyle.class));
        final TextButton trainingButt = new TextButton("Training",
                G.game.skin.get("bttRed", TextButton.TextButtonStyle.class));

        // Let only one tab button be checked at a time
        ButtonGroup logicGroup = new ButtonGroup();
        logicGroup.setMinCheckCount(1);
        logicGroup.setMaxCheckCount(1);
        logicGroup.add(campaignButt);
        logicGroup.add(trainingButt);

        // ==== Scrolls ====
        final ScrollPane campaignScroll = new ScrollPane(campaignT);
        final ScrollPane trainingScroll = new ScrollPane(trainingT);
        campaignScroll.setScrollingDisabled(true, false);
        trainingScroll.setScrollingDisabled(true, false);

        // ==== Listeners ====
        backBtt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action = ScreenBase.ACT_NEXT;
            }
        });

        // ==== Stack ====
        final Stack tabs = new Stack();
        tabs.add(trainingScroll);
        tabs.add(campaignScroll);

        // Listen to changes in the tab button checked states
        // Set visibility of the tab content to match the checked state
        final ChangeListener tabListener = new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                TextButton.TextButtonStyle buttOff = G.game.skin.get(
                        "bttRed", TextButton.TextButtonStyle.class);

                TextButton.TextButtonStyle buttOn = G.game.skin.get(
                        "bttGreen", TextButton.TextButtonStyle.class);

                campaignScroll.setVisible(campaignButt.isChecked());
                trainingScroll.setVisible(trainingButt.isChecked());

                if(campaignButt.isChecked()) campaignButt.setStyle(buttOn);
                else campaignButt.setStyle(buttOff);

                if(trainingButt.isChecked()) trainingButt.setStyle(buttOn);
                else trainingButt.setStyle(buttOff);
            }
        };

        // Put tabs in initial state
        tabListener.changed(null, null);

        campaignButt.addListener(tabListener);
        trainingButt.addListener(tabListener);

        // ==== Align, Pad / widths / heights ====
        float bttW = G.style.buttWidth;
        float padM = G.style.padMedium;

        trainingL.setAlignment(Align.topLeft);

        titleT.pad(padM);
        titleT.defaults().minWidth(bttW);
        contentT.defaults().align(Align.left);
        tabT.defaults().minWidth(bttW);

        // ==== Assembling from outer to inner components ====
        titleT.row();
        titleT.add(contentT).expand().fill();
        titleT.row();
        titleT.add(backBtt);

        contentT.add(tabT).align(Align.center);
        contentT.row();
        contentT.add(tabs).expand().fill();

        tabT.add(campaignButt);
        tabT.add(trainingButt);

        trainingT.add(trainingL).expand().fill();

        G.game.input.targetsClear();

        // Loop through scores and assemble each row
        int cont = 1;
        for(ScoreTable.Row row : G.game.score.getRows()) {
            Label label;

            label = new Label(Integer.toString(cont), labelStyle);
            label.setAlignment(Align.center);
            campaignT.add(label).width(bttW);

            label = new Label(row.nick, labelStyle);
            label.setAlignment(Align.center);
            campaignT.add(label).width(bttW);

            label = new Label(G.game.intFmt.format(row.score), labelStyle);
            label.setAlignment(Align.center);
            campaignT.add(label).width(bttW);

            campaignT.row().pad(padM, 0, 0, 0);
            cont++;
        }

        stage.addActor(titleT);
        titleT.setDebug(G.game.dbg.uiTable); // #debugCode
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
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
