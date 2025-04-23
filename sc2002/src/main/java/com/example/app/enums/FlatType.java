package com.example.app.enums;

/**
 * Enum representing the type of flat.
 * It includes various types of flats available in the system.
 */
public enum FlatType {
    _2ROOM, _3ROOM;

    @Override
    public String toString() {
        switch (this) {
            case _2ROOM:
                return "2 Room";
            case _3ROOM:
                return "3 Room";
            default:
                return super.toString();
        }
    }
}
