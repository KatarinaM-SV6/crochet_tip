package com.ftn.sbnz.model.models;

public enum Color {
    RED("Red"),
    RED_LIGHT("Light Red"),
    RED_DARK("Dark Red"),
    GREEN("Green"),
    GREEN_LIGHT("Light Green"),
    GREEN_DARK("Dark Green"),
    BLUE("Blue"),
    BLUE_LIGHT("Light Blue"),
    BLUE_DARK("Dark Blue"),
    YELLOW("Yellow"),
    ORANGE("Orange"),
    ORANGE_LIGHT("Light Orange"),
    ORANGE_DARK("Dark Orange"),
    PURPLE("Purple"),
    PURPLE_LIGHT("Light Purple"),
    PURPLE_DARK("Dark Purple"),
    PINK("Pink"),
    PINK_LIGHT("Light Pink"),
    PINK_DARK("Dark Pink"),
    TURQOISE("Turqoise"),
    MAGENTA("Magenta"),
    BEIGE_LIGHT("Light Beige"),
    BEIGE_DARK("Dark Beige"),
    GRAY("Gray"),
    GRAY_LIGHT("Light Gray"),
    GRAY_DARK("Dark Gray"),
    BLACK("Black"),
    WHITE("White"),
    BROWN("Brown"),
    BROWN_LIGHT("Light Brown"),
    BROWN_DARK("Dark Brown");

    private final String displayName;

    Color(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
