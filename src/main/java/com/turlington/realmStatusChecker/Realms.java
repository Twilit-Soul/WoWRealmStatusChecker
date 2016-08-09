package com.turlington.realmStatusChecker;

import java.util.List;

/**
 * Holds data about a realm.
 * Created by Mitchell on 4/27/2016.
 */
class Realms {
    List<Realm> realms;

    List<Realm> getRealms() {
        return realms;
    }

    class Realm {
        private String type, population, name, slug, battlegroup, locale, timezone;
        private boolean queue, status;
        private Wintergrasp wintergrasp;
        private TolBarad tolBarad;
        private List<String> connected_realms;

        String getName() {
            return name;
        }

        boolean getStatus() {
            return status;
        }

        public String getSlug() {
            return slug;
        }

        private class Wintergrasp {
            private int area, controllingFaction, status;
            private long next;
        }

        private class TolBarad {
            private int area, controllingFaction, status;
            private long next;
        }
    }
}
