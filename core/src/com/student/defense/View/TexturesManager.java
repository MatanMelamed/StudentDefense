package com.student.defense.View;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TexturesManager {

    private static Map<String, Texture> textureMap = null;

    public static Texture GetTexture(Textures texture) {
        return GetTexture(texture.GetFileName());
    }

    public static Texture GetTexture(String textureName) {
        if (textureMap == null) { InitializeTexturesManager(); }

        Texture answer = null;
        if (textureMap.containsKey(textureName)) {
            answer = textureMap.get(textureName);
        }
        return answer;
    }


    public static void InitializeTexturesManager() {
        if (textureMap == null) { LoadTextures(); }
    }

    private static void LoadTextures() {
        textureMap = new HashMap<>();
        for (Textures t : Textures.values()) {
            textureMap.put(t.GetFileName(), new Texture(t.GetFullPath()));
        }
    }

    public static void Dispose() {
        for (String fileName : textureMap.keySet()) {
            textureMap.get(fileName).dispose();
        }
    }
}
