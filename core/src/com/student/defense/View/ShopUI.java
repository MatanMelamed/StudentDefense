package com.student.defense.View;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.student.defense.Model.Towers.TowerType;

public class ShopUI extends LevelUIComponent {

    class ShopItem {
        int x, y;
        TowerType type;

        ShopItem(int x, int y, TowerType type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }

    private ShopItem[] items = new ShopItem[3];
    private TowerType typeToToggle;

    ShopUI(GUIManager guiManager) {
        super(guiManager);
        typeToToggle = null;
    }

    @Override
    void SetArea(Rectangle newArea) {
        super.SetArea(newArea);
        int i = 0;
        int widthSeparation = 128;

        for (TowerType type : TowerType.values()) {
            items[i] = new ShopItem((int) (area.x + widthSeparation * i + 64), (int) (area.y + (area.height - 64) / 2), type);
            i++;
        }
    }

    void toggleType(TowerType type) {
        if (typeToToggle == null) {
            typeToToggle = type;
        } else if (typeToToggle == type) {
            typeToToggle = null;
        } else {
            typeToToggle = type;
        }
    }

    @Override
    protected void RenderExecution(float dt) {
        manager.DrawRectangle(area, Color.DARK_GRAY, ShapeRenderer.ShapeType.Filled);

        batch.begin();
        for (ShopItem item : items) {
            if (typeToToggle != null && item.type == typeToToggle) {
                batch.end();
                manager.DrawRectangle(item.x - 5, item.y - 5, 74, 74, Color.YELLOW, ShapeRenderer.ShapeType.Filled);
                batch.begin();
            }
            DrawItemName(item);
            manager.DrawTexture(item.type.GetBaseTexture(), item.x, item.y);
        }
        batch.end();
    }

    private void DrawItemName(ShopItem item) {
        String name = item.type.typeName;

        manager.DrawText(item.type.typeName, item.x - name.length(), item.y - 32, 0.9f);
    }

    TowerType getTypeByScreenPoint(int x, int y) {
        TowerType result = null;
        for (ShopItem item : items) {
            if ((item.x <= x && x <= item.x + 64) && (item.y <= y && y <= item.y + 64)) {
                result = item.type;
                break;
            }
        }
        return result;
    }
}
