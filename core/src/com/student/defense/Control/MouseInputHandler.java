package com.student.defense.Control;

import com.badlogic.gdx.InputProcessor;
import com.student.defense.Model.Levels.Cashier;
import com.student.defense.Model.Towers.Tower;
import com.student.defense.Model.Towers.TowerEffects.TowerEffect;
import com.student.defense.Model.Towers.TowerFactory;
import com.student.defense.Model.Towers.TowerType;
import com.student.defense.Model.Maps.Tile;
import com.student.defense.View.GUIManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class MouseInputHandler implements InputProcessor {

    enum MouseMode {
        Clear,
        BuildingMode,
        TowerSelected
    }

    private GUIManager guiManager;
    private PlayScreen playScreen;

    private MouseMode mode;
    private TowerType selectedShopTowerType;
    private Tile selectedMapTowerTile;
    private Cashier cashier;


    public MouseInputHandler() {
        guiManager = GUIManager.GetManager();
        cashier = Cashier.GetCashier();
        playScreen = PlayScreen.GetPlayScreen();

        mode = MouseMode.Clear;
        selectedShopTowerType = null;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (mode == MouseMode.Clear) {
            HandleClearMode(screenX, screenY);
        } else if (mode == MouseMode.BuildingMode) {
            HandleBuildingMode(screenX, screenY);
        } else if (mode == MouseMode.TowerSelected) {
            HandleSelectTowerMode(screenX, screenY);
        }

        if (screenX > 1300 && screenY > 700) {
            playScreen.TogglePause();
        }

        return false;
    }

    private void HandleClearMode(int x, int y) {
        if (guiManager.IsCoordinatesInShopArea(x, y)) {
            TowerType newSelectedType = guiManager.GetShopTowerTypeByScreen(x, y);
            if (newSelectedType != null) {
                selectedShopTowerType = newSelectedType;
                guiManager.ShopItemToggle(selectedShopTowerType);
                mode = MouseMode.BuildingMode;
            }

        } else if (guiManager.IsCoordinatesInMapArea(x, y)) {
            Tile tile = guiManager.GetTileByScreenPosition(x, y);
            if (tile.hasTower()) {
                mode = MouseMode.TowerSelected;
                selectedMapTowerTile = tile;
                guiManager.TowerOnMapSelected(tile);
            }
        }
    }

    private void HandleBuildingMode(int x, int y) {
        if (guiManager.IsCoordinatesInShopArea(x, y)) { // clicked In shop
            TowerType newSelectedType = guiManager.GetShopTowerTypeByScreen(x, y);

            if (newSelectedType != null) {
                if (newSelectedType == selectedShopTowerType) {
                    mode = MouseMode.Clear;
                } else {
                    selectedShopTowerType = newSelectedType;
                }
                guiManager.ShopItemToggle(selectedShopTowerType);
            }
        } else if (guiManager.IsCoordinatesInMapArea(x, y)) {
            Tile tile = guiManager.GetTileByScreenPosition(x, y);
            if (!tile.hasTower() && (!tile.isPath()) && (CanAfford(selectedShopTowerType, 1))) {
                BuildTower(tile);
            }
        }
    }

    private void HandleSelectTowerMode(int x, int y) {

        if (guiManager.IsCoordinatesInMapArea(x, y)) {
            Tower tower = selectedMapTowerTile.getTower();

            if (guiManager.IsTowerUpgrade(x, y) && CanAfford(tower.GetType(), tower.GetLevel() + 1)) {
                tower.Upgrade();
            } else if (guiManager.IsTowerSold(x, y)) {
                SellTower(tower);
            } else {
                Tile tile = guiManager.GetTileByScreenPosition(x, y);
                if (tile != selectedMapTowerTile && tile.hasTower()) {
                    selectedMapTowerTile = tile;
                    guiManager.TowerOnMapSelected(tile);
                } else {
                    mode = MouseMode.Clear;
                    guiManager.DeselectTowerOnMap();
                }
            }
        } else if (guiManager.IsCoordinatesInShopArea(x, y)) {
            TowerType newSelectedType = guiManager.GetShopTowerTypeByScreen(x, y);
            if (newSelectedType != null) {
                selectedShopTowerType = newSelectedType;
                guiManager.ShopItemToggle(selectedShopTowerType);
                mode = MouseMode.BuildingMode;
            } else {
                mode = MouseMode.Clear;
            }
            guiManager.DeselectTowerOnMap();
        } else {
            mode = MouseMode.Clear;
            guiManager.DeselectTowerOnMap();
        }
    }

    private boolean CanAfford(TowerType type, int level) {
        return playScreen.currentLevel.GetMoney() >= cashier.GetTowerCost(type, level);
    }

    private void SellTower(Tower tower) {
        TowerFactory.Checkout(tower);
        selectedMapTowerTile.DestroyTower();
        playScreen.currentLevel.RemoveTower(tower);
        playScreen.currentLevel.EarnMoney((int) (cashier.GetTowerCost(tower.GetType(), tower.GetLevel()) * 0.9));
        guiManager.DeselectTowerOnMap();
    }

    private void BuildTower(Tile tile) {
        Tower tower = TowerFactory.GetNewTower(selectedShopTowerType);

        tile.BuildTower(tower);
        playScreen.currentLevel.AddTower(tower);
        playScreen.currentLevel.ChargeMoney(cashier.GetTowerCost(selectedShopTowerType, 1));

        if (!tower.IsUsedBefore()) {
            tower.AddPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals(Tower.FIRE_EVENT)) {
                        final TowerEffect effect = (TowerEffect) evt.getNewValue();
                        GUIManager.GetManager().AddTowerEffectToGui(effect);
                        playScreen.currentLevel.AddEffect(effect);

                        effect.AddPropertyChangeListener(new PropertyChangeListener() {
                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                if (evt.getPropertyName().equals(TowerEffect.EFFECT_ENDED)) {
                                    playScreen.currentLevel.RemoveEffect(effect);
                                    GUIManager.GetManager().RemoveTowerEffectFromGui(effect);
                                }
                            }
                        });
                    }
                }
            });
        }

        tower.PlaceOnTile(tile);
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
