package com.jitrapon.imagine.models;

import com.jitrapon.imagine.R;

/**
 * Represents all the categories available that a photo will belong to.
 */
public enum Category {

    UNCATEGORIZED(0, R.drawable.bg_uncategorized),
    ABSTRACT(10, R.drawable.bg_abstract),
    ANIMALS(11, R.drawable.bg_animals),
    BLACK_AND_WHITE(5, R.drawable.bg_black_and_white),
    CELEBRITIES(1, R.drawable.bg_celebrities),
    CITY_AND_ARCHITECTURE(9, R.drawable.bg_city_and_architecture),
    COMMERCIAL(15, R.drawable.bg_commercial),
    CONCERT(16, R.drawable.bg_concert),
    FAMILY(20, R.drawable.bg_family),
    FASHION(14, R.drawable.bg_fashion),
    FILM(2, R.drawable.bg_film),
    FINE_ART(24, R.drawable.bg_fine_art),
    FOOD(23, R.drawable.bg_food),
    JOURNALISM(3, R.drawable.bg_journalism),
    LANDSCAPES(8, R.drawable.bg_landscape),
    MACRO(12, R.drawable.bg_macro),
    NATURE(18, R.drawable.bg_nature),
    NUDE(4, R.drawable.bg_nude),
    PEOPLE(7, R.drawable.bg_people),
    PERFORMING_ARTS(19, R.drawable.bg_performing_arts),
    SPORT(17, R.drawable.bg_sport),
    STILL_LIFE(6, R.drawable.bg_still_life),
    STREET(21, R.drawable.bg_street),
    TRANSPORTATION(26, R.drawable.bg_transportation),
    TRAVEL(13, R.drawable.bg_travel),
    UNDERWATER(22, R.drawable.bg_underwater),
    URBAN_EXPLORATION(27, R.drawable.bg_urban_exploration),
    WEDDING(25, R.drawable.bg_wedding);

    private final int id;
    private final int background;

    Category(int id, int background) {
        this.id = id;
        this.background = background;
    }

    public int getId() { return id; }

    public String asQueryParameter() {
        return name().toLowerCase().replace('_', ' ');
    }

    public int asBackground() { return background; }
}
