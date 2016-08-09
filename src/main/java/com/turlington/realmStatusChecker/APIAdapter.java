package com.turlington.realmStatusChecker;


import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Hello world!
 */
public class APIAdapter {
    private final Gson gson = new Gson();
    private final Logger logger = LogManager.getLogger(APIAdapter.class);
    private static final String API_KEY = "2gv8ep8at8cnk4ngcbrpzqydk96s8b5k";
    private static final String REALM_STATUS_API_START = "https://us.api.battle.net/wow/realm/status";
    private static final String CHARACTER_API_START = "https://us.api.battle.net/wow/character/";
    private static final String LOCALE_START = "locale=";
    private static final String API_KEY_START = "&apikey=";
    static final String FIELDS_START = "fields=";
    private static final String JSONP_START = "&jsonp=";

    /**
     * Sends the passed in request, and returns whatever the response is, hopefully.
     */
    String callURL(String myURL) throws IOException {
        final StringBuilder sb = new StringBuilder();
        final URLConnection urlConn = new URL(myURL).openConnection();
        try (InputStreamReader in = new InputStreamReader(urlConn.getInputStream(),
                StandardCharsets.UTF_8); BufferedReader bufferedReader = new BufferedReader(in)) {
            urlConn.setReadTimeout(60 * 1000);
            if (urlConn.getInputStream() != null) {
                int cp;
                while ((cp = bufferedReader.read()) != -1) {
                    sb.append((char) cp);
                }
            }
        }

        return sb.toString();
    }

    Optional<Realms> getRealms(APILanguage language) {
        try {
            return Optional.of(gson.fromJson(getRealmStatusJson(language), Realms.class));
        } catch (Exception e) {
            logger.warn("Couldn't get realms: "+e.getMessage());
            return Optional.empty();
        }
    }

    Optional<Character> getCharacterAchievements(RealmName realmName, String characterName) {
        try {
            return Optional.of(gson.fromJson(getCharacterJson(realmName, Field.ACHIEVEMENTS, characterName), Character.class));
        } catch (Exception e) {
            logger.warn("Couldn't get character acievements: "+e.getMessage());
            return Optional.empty();
        }
    }

    private String getRealmStatusJson(APILanguage language) throws IOException {
        return callURL(getRealmStatusURL(language, API_KEY));
    }

    private String getRealmStatusURL(APILanguage language, String key) {
        return getRealmStatusURL(language, key, "");
    }

    private String getRealmStatusURL(APILanguage language, String key, String jsonp) {
        return REALM_STATUS_API_START + "?"+LOCALE_START + language.getCode() +
                ((jsonp.isEmpty()) ? "" : JSONP_START + jsonp) + API_KEY_START + key;
    }

    private String getCharacterJson(RealmName realmName, Field field, String characterName) throws IOException {
        return callURL(getCharacterURL(realmName, field, characterName, APILanguage.ENGLISH, API_KEY, null));
    }

    private String getCharacterURL(RealmName realmName, Field field, String characterName, APILanguage language, String key, String jsonp) {
        return CHARACTER_API_START+realmName.getSlug()+"/"+characterName+
                (field != null ? "?"+FIELDS_START+field.getText() : "")+
                "&"+LOCALE_START+language.getCode()+API_KEY_START+key;
    }

    @SuppressWarnings("unused")
    private enum Field {
        ACHIEVEMENTS("achievements"),
        APPEARANCE("appearance"),
        FEED("feed"),
        GUILD("guild"),
        HUNTER_PETS("hunterPets"),
        ITEMS("items"),
        MOUNTS("mounts"),
        PETS("pets"),
        PET_SLOTS("petslots"),
        PROGRESSION("progression"),
        PVP("pvp"),
        QUESTS("quests"),
        REPUTATION("reputation"),
        STATISTICS("statistics"),
        STATS("stats"),
        TALENTS("talents"),
        TITLES("titles"),
        AUDIT("audit");

        private final String text;

        Field(String text) {
            this.text = text;
        }

        String getText() {
            return text;
        }
    }

    enum APILanguage {
        ENGLISH("en_US"),
        PORTUGUESE("pt_BR"),
        SPANISH("es_MX");

        private final String code;

        APILanguage(String code) {
            this.code = code;
        }

        String getCode() {
            return code;
        }
    }
}
