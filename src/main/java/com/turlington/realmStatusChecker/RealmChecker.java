package com.turlington.realmStatusChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.turlington.realmStatusChecker.Realms.Realm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.turlington.realmStatusChecker.APIAdapter.APILanguage.ENGLISH;

/**
 * Checks the status of realms.
 * Created by Mitchell on 4/27/2016.
 */
public class RealmChecker {
    private static final APIAdapter apiAdapter = new APIAdapter();
    private final Logger logger = LogManager.getLogger(RealmChecker.class);

    //TODO: look into "realms" parameter to only request realms we still need to check.

    public static void main(String[] args) {
        new RealmChecker().checkRealms();
    }

    private void checkRealms() {
        logger.info("Beginning realm check...");
        Map<String, Realm> realmMap = new HashMap<>();
        List<Realm> realms = getRealms();
        realms.parallelStream().filter(realm -> !realm.getStatus()).
                forEach(realm -> realmMap.put(realm.getName(), realm));
        do {
            realms = getRealms();
            for (Realm realm : realms) {
                String name = realm.getName();
                if (realmMap.containsKey(name)) {
                    if (realm.getStatus()) {
                        realmMap.remove(name);
                        System.out.println("Realm "+name+" has come online!");
                    }
                }
            }
        } while (anyRealmOffline(realmMap));
        logger.info("All realms are online.");
    }

    private List<Realm> getRealms() {
        Optional<Realms> realmsOptional = apiAdapter.getRealms(ENGLISH);
        if (!realmsOptional.isPresent()) {
            logger.warn("Failed to get realms. See error logging for details.");
            System.exit(-1);
        }
        return realmsOptional.get().getRealms();
    }

    private boolean anyRealmOffline(Map<String, Realm> realmMap) {
        return (realmMap.values().parallelStream().anyMatch(realm -> !realm.getStatus()));
    }
}
