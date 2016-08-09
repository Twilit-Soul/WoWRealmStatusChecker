package com.turlington.realmStatusChecker;

import java.util.List;

/**
 * Represents a WoW character.
 * Created by Mitchell on 4/27/2016.
 */
class Character {
    private long lastModified;
    private String name, realm, battlegroup, thumbnail, calcClass;
    private int className, race, gender, level, achievementPoints, faction, totalHonorableKills;
    private Achievements achievements;

    public Achievements getAchievements() {
        return achievements;
    }

    class Achievements {
        private List<Integer> achievementsCompleted, criteria, criteriaQuantity;
        private List<Long> achievementsCompletedTimestamp, criteriaTimestamp, criteriaCreated;
    }
}
