package com.student.defense.View;


public enum Textures {
    ENEM_EXAM("enemies", "exam.png"),
    ENEM_PAPERWORK("enemies", "paperwork.png"),
    ENEM_SADNESS("enemies", "sadness.png"),
    ENEM_SHARED_PROJ("enemies", "shared_project.png"),

    PROJECTILE1("projectiles", "metal_ball_1.png"),
    PROJECTILE2("projectiles", "metal_ball_2.png"),
    PROJECTILE3("projectiles", "metal_ball_3.png"),

    TILES_GREEN("tiles", "green_grass.png"),
    TILES_PATH("tiles", "brown_path.png"),
    TILES_BLUE("tiles", "blue_grass.png"),
    TILES_RED("tiles", "red_grass.png"),
    TILES_YELLOW("tiles", "yellow_grass.png"),
    TILES_WATER("tiles", "water.png"),

    TOWER_RITALIN1("towers", "ritalin_1.png"),
    TOWER_RITALIN2("towers", "ritalin_2.png"),
    TOWER_TUTOR1("towers", "tutor_1.png"),
    TOWER_TUTOR2("towers", "tutor_2.png"),
    TOWER_VT1("towers", "video_tutorial_1.png"),
    TOWER_VT2("towers", "video_tutorial_2.png"),

    UPGRADE("", "Upgrade.png"),
    SELL("", "sell.png");

    private String filePath;
    private String fileName;


    Textures(String path, String name) {
        filePath = path;
        fileName = name;
    }

    public String GetFullPath() {
        return "textures/" + filePath + "/" + fileName;
    }

    public String GetFileName() {
        return fileName;
    }
}

