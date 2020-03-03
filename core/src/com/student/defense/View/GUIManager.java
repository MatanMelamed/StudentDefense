package com.student.defense.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.student.defense.Model.Levels.Level;
import com.student.defense.Model.Towers.TowerType;
import com.student.defense.Model.Maps.Tile;
import com.student.defense.Utils.FrameRate;
import com.student.defense.Model.Towers.TowerEffects.TowerEffect;


public class GUIManager {

    public static final int V_WIDTH = 1366;
    public static final int V_Height = 768;

    private FrameRate fps;

    private SpriteBatch batch;
    private OrthographicCamera gameCamera; // defines what is seen - view scope
    private BitmapFont font;

    private MapUI mapUI;
    private StatsUI statsUI;
    private ShopUI shopUI;
    private Level level;

    private ShapeRenderer shapeRenderer;

    //region Singleton
    private static GUIManager guiManager = new GUIManager();

    public static GUIManager GetManager() {
        return guiManager;
    }
    //endregion

    private GUIManager() {
        gameCamera = new OrthographicCamera();
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        fps = new FrameRate();


        mapUI = new MapUI(this);
        statsUI = new StatsUI(this);
        shopUI = new ShopUI(this);
    }

    private void ClearScreen() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void SetLevelToRender(Level l) {
        mapUI.SetLevel(l);
        statsUI.SetLevel(l);
        shopUI.SetLevel(l);

        int x = 50, y = 50, width = 600, height = 600;
        mapUI.SetArea(new Rectangle(x, y, width, height));

        x = (int) mapUI.GetArea().x;
        y = ((int) mapUI.GetArea().y) + l.GetMap().GetHeight() + 20;
        width = 275;
        height = 175;
        statsUI.SetArea(new Rectangle(x, y, width, height));

        x = (int) (statsUI.GetArea().x + statsUI.GetArea().width) + 100;
        y = (int) (statsUI.GetArea().y);
        width = 450;
        height = 175;
        shopUI.SetArea(new Rectangle(x, y, width, height));

        level = l;
    }

    public void renderLevel(float dt) {
        ClearScreen();
        batch.setProjectionMatrix(gameCamera.combined);

        mapUI.Render(dt);
        statsUI.Render(dt);
        shopUI.Render(dt);

        fps.update();
        fps.render();
    }

    public void ShopItemToggle(TowerType type) {
        shopUI.toggleType(type);
    }

    // GameMap Queries
    public boolean IsCoordinatesInMapArea(int x, int y) {
        x -= mapUI.GetArea().x;
        y -= mapUI.GetArea().y;

        return !(x < 0) && !(x > level.GetMap().GetWidth()) && !(y < 0) && !(y > level.GetMap().GetHeight());
    }

    public Tile GetTileByScreenPosition(int x, int y) {
        x -= mapUI.GetArea().x;
        y -= mapUI.GetArea().y;
        return level.GetMap().GetTileByScreen(y, x);
    }

    public boolean IsCoordinatesInShopArea(int x, int y) {
        Rectangle area = shopUI.GetArea();
        boolean t = (area.x <= x && x <= area.x + area.width) && (area.y <= y && y <= area.y + area.height);
        return t;
    }

    public TowerType GetShopTowerTypeByScreen(int x, int y) {
        return shopUI.getTypeByScreenPoint(x, y);
    }

    public void TowerOnMapSelected(Tile tile) {
        mapUI.towerSelected(tile);
    }

    public void DeselectTowerOnMap() {
        mapUI.towerDeselect();
    }

    public boolean IsTowerUpgrade(int x, int y) {
        boolean b = mapUI.isUpgrade(x, y);
        return mapUI.isUpgrade(x, y);
    }

    public boolean IsTowerSold(int x, int y) {
        return mapUI.isSell(x, y);
    }

    public void AddTowerEffectToGui(TowerEffect effect) {mapUI.AddTowerEffect(effect);}

    public void RemoveTowerEffectFromGui(TowerEffect effect) {mapUI.RemoveTowerEffect(effect);}

    void DrawTexture(Texture t, float x, float y) {
        //y = V_Height - y - t.GetHeight();
        y = InvertY((int) y, t.getHeight());
        batch.draw(t, x, y);
    }

    void DrawRectangle(int x, int y, int width, int height, Color color, ShapeRenderer.ShapeType shapeType) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(shapeType);
        shapeRenderer.rect(x, InvertY(y, height), width, height);
        shapeRenderer.end();
    }

    void DrawRectangle(Rectangle rect, Color color, ShapeRenderer.ShapeType shapeType) {
        DrawRectangle((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height, color, shapeType);
    }

    void DrawCircle(int x, int y, int radius, Color color, ShapeRenderer.ShapeType shapeType) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(shapeType);
        shapeRenderer.circle(x, InvertY(y, 0), radius);
        shapeRenderer.end();
    }

    void DrawText(String text, float x, float y, float scale) {
        if (scale != 1) {
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            font.getData().setScale(scale);
        }

        font.draw(batch, text, x, InvertY(y, 10));
    }


    float InvertY(float y, float size) {
        return V_Height - y - size;
    }

    // Not important
    public void Resize(int width, int height) {
        gameCamera.setToOrtho(false);
        fps.resize(width, height);
    }

    public void Dispose() {
        batch.dispose();
        fps.dispose();
        mapUI.Dispose();
    }

    SpriteBatch GetBatch() {
        return batch;
    }

}
