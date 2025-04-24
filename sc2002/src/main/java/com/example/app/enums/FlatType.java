package com.example.app.enums;

public enum FlatType {
    _2ROOM, _3ROOM;

    @Override
    public String toString() {
        switch (this) {
            case _2ROOM:
                return "2-Room";
            case _3ROOM:
                return "3-Room";
            default:
                throw new IllegalArgumentException("Unknown FlatType: " + this);
        }
    }
}
