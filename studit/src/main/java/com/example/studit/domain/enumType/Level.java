package com.example.studit.domain.enumType;

public enum Level {
    LV3(5, null),
    LV2(5, LV3),
    LV1(1, LV2);

    private final int nextStandard;
    private final Level nextLevel;

    Level(int nextStandard, Level nextLevel) {
        this.nextStandard = nextStandard;
        this.nextLevel = nextLevel;
    }

    public static Level getNextLevel(int levelScore){
        //LV3인 경우 메서드에 들어오지 않음
            if (levelScore >= Level.LV2.nextStandard) {
                return LV2.nextLevel;
            }

            if (levelScore >= Level.LV1.nextStandard) {
                return LV1.nextLevel;
            }

            return LV1;
    }
}
