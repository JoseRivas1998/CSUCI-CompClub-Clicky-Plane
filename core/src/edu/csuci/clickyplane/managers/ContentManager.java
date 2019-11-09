package edu.csuci.clickyplane.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class ContentManager implements Disposable {

    public enum Image {
        PIPE_BODY("img/pipe_body.png"),
        PIPE_TOP("img/pipe_top.png"),
        PLANE_SPRITESHEET("img/plane_spritesheet.png");
        public final String path;

        Image(String path) {
            this.path = path;
        }
    }

    private Map<Image, Texture> textures;

    public ContentManager() {
        loadTextures();
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

    @Override
    public void dispose() {
        for (Image image : Image.values()) {
            textures.get(image).dispose();
        }
        textures.clear();
    }

}
