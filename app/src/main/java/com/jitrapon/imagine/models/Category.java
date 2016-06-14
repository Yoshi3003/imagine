package com.jitrapon.imagine.models;

/**
 * Represents all the categories available that a photo will belong to.
 */
public enum Category {

    UNCATEGORIZED(0),
    ABSTRACT(10),
    ANIMALS(11),
    BLACK_AND_WHITE(5),
    CELEBRITIES(1),
    CITY_AND_ARCHITECTURE(9),
    COMMERCIAL(15),
    CONCERT(16),
    FAMILY(20),
    FASHION(14),
    FILM(2),
    FINE_ART(24),
    FOOD(23),
    JOURNALISM(3),
    LANDSCAPES(8),
    MACRO(12),
    NATURE(18),
    NUDE(4),
    PEOPLE(7),
    PERFORMING_ARTS(19),
    SPORT(17),
    STILL_LIFE(6),
    STREET(21),
    TRANSPORTATION(26),
    TRAVEL(13),
    UNDERWATER(22),
    URBAN_EXPLORATION(27),
    WEDDING(25);

    private final int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    public String getURLName() {
        return name();
    }
}
