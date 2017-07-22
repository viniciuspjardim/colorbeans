/*
 * Copyright 2015 Vinícius Petrocione Jardim
 */

package com.vpjardim.colorbeans.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vpjardim.colorbeans.G;
import com.vpjardim.colorbeans.core.ScoreTable;
import com.vpjardim.colorbeans.defaults.Db;
import com.vpjardim.colorbeans.input.InputManager;

import java.text.NumberFormat;

/**
 * Just a black screen waiting load to be done
 *
 * @author Vinícius Jardim
 * 10/06/2016
 */
public class LoadingScreen extends ScreenBase {

    private int frameCount = 0;
    private String atlasStr;

    public LoadingScreen() {
        manageInput = false;
    }

    @Override
    public void show() {
        super.show();
        G.loading = true;
    }

    public void loadStuff() {

        G.game.scale = G.game.height / 720f;
        if(G.height >= 1080) G.game.res = G.RES_MEDIUM;
        else G.game.res = G.RES_SMALL;

        G.style.setDefaults();
        G.style.scale(G.game.scale);

        G.game.assets = new AssetManager();

        FileHandleResolver resolver = new InternalFileHandleResolver();
        G.game.assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        G.game.assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter param;

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/roboto.ttf";
        param.fontParameters.size = G.style.fontSizeMedium;
        G.game.assets.load("roboto.ttf", BitmapFont.class, param);

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/roboto.ttf";
        param.fontParameters.size = G.style.fontSizeBig;
        param.fontParameters.shadowColor = new Color(0x00000040);
        param.fontParameters.shadowOffsetX = 2;
        param.fontParameters.shadowOffsetY = 4;
        param.fontParameters.borderColor = new Color(0x000000ff);
        param.fontParameters.borderWidth = 2;
        G.game.assets.load("roboto_shadow.ttf", BitmapFont.class, param);

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/roboto_i.ttf";
        param.fontParameters.size = G.style.fontSizeMedium;
        G.game.assets.load("roboto-i.ttf", BitmapFont.class, param);

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/dimbo.ttf";
        param.fontParameters.size = G.style.fontSizeBig;
        param.fontParameters.color = Color.WHITE;
        param.fontParameters.borderColor = Color.BLACK;
        param.fontParameters.borderWidth = 4;
        G.game.assets.load("dimbo_white.ttf", BitmapFont.class, param);

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/dimbo.ttf";
        param.fontParameters.size = G.style.fontSizeXBig;
        param.fontParameters.color = new Color(0xe8e2b4ff);
        // param.fontParameters.shadowColor = new Color(0xffffffff);
        // param.fontParameters.shadowOffsetY = 3;
        param.fontParameters.borderColor = new Color(0x7b0a41ff);
        param.fontParameters.borderWidth = 6;
        G.game.assets.load("dimbo_gtitle.ttf", BitmapFont.class, param);

        // Todo fix the font shadow offset when not full screen. It should be smaller

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/dimbo.ttf";
        param.fontParameters.size = G.style.fontSizeBig;
        param.fontParameters.color = new Color(0xab002cff);
        param.fontParameters.shadowColor = new Color(0x660016ff);
        param.fontParameters.shadowOffsetY = -3;
        G.game.assets.load("dimbo_brown.ttf", BitmapFont.class, param);

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/dimbo.ttf";
        param.fontParameters.size = G.style.fontSizeBig;
        param.fontParameters.color = new Color(0x78001cff);
        param.fontParameters.shadowColor = new Color(0x44000fff);
        param.fontParameters.shadowOffsetY = -3;
        G.game.assets.load("dimbo_dbrown.ttf", BitmapFont.class, param);

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/dimbo.ttf";
        param.fontParameters.size = G.style.fontSizeBig;
        param.fontParameters.color = new Color(0x432d63ff);
        param.fontParameters.shadowColor = new Color(0x311732ff);
        param.fontParameters.shadowOffsetY = -3;
        G.game.assets.load("dimbo_purple.ttf", BitmapFont.class, param);

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/dimbo.ttf";
        param.fontParameters.size = G.style.fontSizeBig;
        param.fontParameters.color = new Color(0x2e5111ff);
        param.fontParameters.shadowColor = new Color(0x262909ff);
        param.fontParameters.shadowOffsetY = -3;
        G.game.assets.load("dimbo_green.ttf", BitmapFont.class, param);

        param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "font/dimbo.ttf";
        param.fontParameters.size = G.style.fontSizeBig;
        param.fontParameters.color = new Color(0x565656ff);
        param.fontParameters.shadowColor = new Color(0x3b2c2cff);
        param.fontParameters.shadowOffsetY = -3;
        G.game.assets.load("dimbo_gray.ttf", BitmapFont.class, param);

        G.game.data   = Db.load();
        G.game.input  = new InputManager();
        G.game.batch  = new SpriteBatch();
        G.game.sr     = new ShapeRenderer();
        G.game.score  = ScoreTable.load();
        G.game.intFmt = NumberFormat.getInstance();

        G.game.input.loadInputs();

        if(G.res == G.RES_MEDIUM) atlasStr = "img/pack_m.atlas"; // Medium size sprites
        else atlasStr = "img/pack_s.atlas";                      // Small size sprites

        G.game.assets.load(atlasStr, TextureAtlas.class);
        G.game.assets.load("audio/studio.ogg", Music.class);
        G.game.assets.load("audio/music1.ogg", Music.class);
        G.game.assets.load("audio/chain_11.ogg", Sound.class);
        G.game.assets.load("audio/chain_12.ogg", Sound.class);
        G.game.assets.load("audio/chain_13.ogg", Sound.class);
        G.game.assets.load("audio/chain_14.ogg", Sound.class);
        G.game.assets.load("audio/chain_15.ogg", Sound.class);
        G.game.assets.load("audio/chain_16.ogg", Sound.class);
        G.game.assets.load("audio/chain_17.ogg", Sound.class);
        G.game.assets.load("audio/chain_21.ogg", Sound.class);
        G.game.assets.load("audio/chain_22.ogg", Sound.class);
        G.game.assets.load("audio/chain_23.ogg", Sound.class);
        G.game.assets.load("audio/chain_24.ogg", Sound.class);
        G.game.assets.load("audio/chain_25.ogg", Sound.class);
        G.game.assets.load("audio/chain_26.ogg", Sound.class);
        G.game.assets.load("audio/chain_27.ogg", Sound.class);
        G.game.assets.load("audio/lostfall.ogg", Sound.class);
        G.game.assets.load("audio/trash.ogg", Sound.class);
    }

    @Override
    public void render(float delta) {

        super.render(delta);

        // Only start loading after the first frame to avoid a white screen blink at the startup.
        // Spite using AssetManager, that load things at a nonblocking method (other thread),
        // it takes a while, sufficient to cause white screen on the game startup.
        if(frameCount == 1) loadStuff();

        // If stuff has done loading, init some vars and go to the next screen
        if(frameCount > 1 && G.game.assets.update()) {

            action = ScreenBase.ACT_NEXT;

            // After loading is done we can create atlas and skin
            G.game.atlas = G.game.assets.get(atlasStr, TextureAtlas.class);

            G.game.skin  = new Skin();
            G.game.skin.addRegions(G.game.atlas);

            BitmapFont font;

            font = G.game.assets.get("roboto.ttf", BitmapFont.class);
            G.game.skin.add("roboto", font);

            font = G.game.assets.get("roboto_shadow.ttf", BitmapFont.class);
            G.game.skin.add("roboto_shadow", font);

            font = G.game.assets.get("dimbo_white.ttf", BitmapFont.class);
            G.game.skin.add("dimbo_white", font);

            font = G.game.assets.get("dimbo_gtitle.ttf", BitmapFont.class);
            G.game.skin.add("dimbo_gtitle", font);

            font = G.game.assets.get("dimbo_brown.ttf", BitmapFont.class);
            G.game.skin.add("dimbo_brown", font);

            font = G.game.assets.get("dimbo_dbrown.ttf", BitmapFont.class);
            G.game.skin.add("dimbo_dbrown", font);

            font = G.game.assets.get("dimbo_purple.ttf", BitmapFont.class);
            G.game.skin.add("dimbo_purple", font);

            font = G.game.assets.get("dimbo_green.ttf", BitmapFont.class);
            G.game.skin.add("dimbo_green", font);

            font = G.game.assets.get("dimbo_gray.ttf", BitmapFont.class);
            G.game.skin.add("dimbo_gray", font);


            G.game.skin.load(Gdx.files.internal("img/skin.json"));

            G.loading = false;
        }

        frameCount++;
    }
}
