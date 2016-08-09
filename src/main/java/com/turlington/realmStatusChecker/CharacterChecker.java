package com.turlington.realmStatusChecker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.turlington.realmStatusChecker.Character.Achievements;

import java.util.Optional;

/**
 * Used to look up things about a character.
 * Created by Mitchell on 4/28/2016.
 */
public class CharacterChecker {
    private static final APIAdapter apiAdapter = new APIAdapter();
    private final Logger logger = LogManager.getLogger(CharacterChecker.class);

    public static void main(String[] args) {
        CharacterChecker characterChecker = new CharacterChecker();
        System.out.println(characterChecker.getEarliestAchievement(RealmName.SKULLCRUSHER, "Barbie"));
    }

    private String getEarliestAchievement(RealmName realmName, String characterName) {
        Achievements achievements = getCharacterAchievements(realmName, characterName);
        return "";
    }

    private Achievements getCharacterAchievements(RealmName realmName, String characterName) {
        Optional<Character> characterOptional = apiAdapter.getCharacterAchievements(realmName, characterName);
        if (!characterOptional.isPresent()) {
            logger.warn("Failed to get character achievements. See error logging for details.");
            System.exit(-1);
        }
        return characterOptional.get().getAchievements();
    }
}
