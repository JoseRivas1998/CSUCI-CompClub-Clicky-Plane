package edu.csuci.clickyplane.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class ContentManager implements Disposable {

    public enum Font {
        SCORE(
                "fnt/font.ttf",
                56,
                Color.WHITE,
                1f, Color.BLACK,
                2, 2, Color.BLACK
        );

        public final String path;
        public final int fontSize;
        public final Color fontColor;
        public final float borderWidth;
        public final Color borderColor;
        public final int shadowOffsetX;
        public final int shadowOffsetY;
        public final Color shadowColor;

        Font(String path, int fontSize, Color fontColor, float borderWidth, Color borderColor, int shadowOffsetX, int shadowOffsetY, Color shadowColor) {
            this.path = path;
            this.fontSize = fontSize;
            this.fontColor = fontColor;
            this.borderWidth = borderWidth;
            this.borderColor = borderColor;
            this.shadowOffsetX = shadowOffsetX;
            this.shadowOffsetY = shadowOffsetY;
            this.shadowColor = shadowColor;
        }

        public FreeTypeFontGenerator.FreeTypeFontParameter toParam() {
            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = this.fontSize;
            param.color = this.fontColor;
            if(borderWidth > 0) {
                param.borderWidth = this.borderWidth;
                param.borderColor = this.borderColor;
            }
            if(this.shadowOffsetX > 0 || this.shadowOffsetY > 0) {
                param.shadowOffsetX = this.shadowOffsetX;
                param.shadowOffsetY = this.shadowOffsetY;
                param.shadowColor = this.shadowColor;
            }
            return param;
        }

    }

    public enum Image {
        BACKGROUND("img/background.png"),
        PIPE_BODY("img/pipe_body.png"),
        PIPE_TOP("img/pipe_top.png"),
        PLANE_SPRITESHEET("img/plane_spritesheet.png"),
        GROUND_GRASS("img/ground_grass.png");
        public final String path;

        Image(String path) {
            this.path = path;
        }
    }

    public enum SoundEffect {
        POINT("snd/sfx_point.wav"),
        WING("snd/sfx_wing.wav");
        public final String path;

        SoundEffect(String path) {
            this.path = path;
        }
    }

    private Map<Image, Texture> textures;
    private Map<Font, BitmapFont> fonts;
    private Map<SoundEffect, Sound> soundEffects;

    private GlyphLayout gl;

    public ContentManager() {
        loadTextures();
        loadFonts();
        loadSoundEffects();
    }

    private void loadSoundEffects() {
        soundEffects = new HashMap<SoundEffect, Sound>();
        for (SoundEffect soundEffect : SoundEffect.values()) {
            soundEffects.put(soundEffect, Gdx.audio.newSound(Gdx.files.internal(soundEffect.path)));
        }
    }

    private void loadFonts() {
        fonts = new HashMap<Font, BitmapFont>();
        for(Font font : Font.values()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font.path));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = font.toParam();
            parameter.borderStraight = true;
            fonts.put(font, generator.generateFont(parameter));
            generator.dispose();
        }
        gl = new GlyphLayout();
    }

    private void loadTextures() {
        textures = new HashMap<Image, Texture>();
        for (Image image : Image.values()) {
            textures.put(image, new Texture(image.path));
        }
    }

    public Texture getTexture(Image image) {
        return textures.get(image);
    }

    public TextureRegion getTextureRegion(Image image) {
        return new TextureRegion(getTexture(image));
    }

    public BitmapFont getFont(Font font) {
        return fonts.get(font);
    }

    public float getWidth(Font font, String s) {
        gl.setText(getFont(font), s);
        return gl.width;
    }

    public float getWidth(Font font, String s, float targetWidth, int halign, boolean wrap) {
        gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return gl.width;
    }

    public float getHeight(Font font, String s) {
        gl.setText(getFont(font), s);
        return gl.height;
    }

    public float getHeight(Font font, String s, float targetWidth, int halign, boolean wrap) {
        gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return gl.height;
    }

    public Sound getSound(SoundEffect soundEffect) {
        return soundEffects.get(soundEffect);
    }

    public void playSound(SoundEffect soundEffect) {
        getSound(soundEffect).play();
    }

    @Override
    public void dispose() {
        for (Image image : Image.values()) {
            textures.get(image).dispose();
        }
        textures.clear();
        for (Font font : Font.values()) {
            fonts.get(font).dispose();
        }
        fonts.clear();
        for (SoundEffect soundEffect : SoundEffect.values()) {
            soundEffects.get(soundEffect).dispose();
        }
        soundEffects.clear();
    }

}
