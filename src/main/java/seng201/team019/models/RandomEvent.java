package seng201.team019.models;


import seng201.team019.GameEnvironment;

import java.util.Random;

public enum RandomEvent {
    RouteWeather {
        private Route disqualifiedRoute;

        public String getMessage() {
            return String.format("Weather Event on the %s route.", disqualifiedRoute.getDescription());
        }

        @Override
        public void trigger(GameEnvironment gameEnvironment, Race race) {
            Random rand = new Random();
            Route disqualifiedRoute = rand.nextInt(race.getRoutes().size()) == 0 ? race.getRoutes().get(rand.nextInt(race.getRoutes().size())) : race.getRoutes().getFirst();
            for (Racer racer : race.getRacers()) {
                if (!racer.getRoute().equals(disqualifiedRoute) || racer.isFinished()) {
                    continue;
                }
                if (racer instanceof Player) {
                    // cast to Player to get access to overloaded setDidDnfMethod
                    ((Player) racer).setDidDNF(true, "Player could not continue due to weather event");
                } else {
                    racer.setDidDNF(true);
                }

            }
            this.disqualifiedRoute = disqualifiedRoute;
        }
    }, PlayerStrandedTraveler {
        @Override
        public String getMessage() {
            return "You picked up a traveler and gained $1000. This delayed you 2 minutes.";
        }

        public void trigger(GameEnvironment gameEnvironment, Race race) {
            gameEnvironment.setBankBalance(gameEnvironment.getBankBalance() + 1000);
        }
    }, PlayerBreaksDown {
        @Override
        public String getMessage() {
            return "Your car breaks down.\n";
        }

        public void triggerYes(GameEnvironment gameEnvironment, Race race) {
            gameEnvironment.setBankBalance(gameEnvironment.getBankBalance() - 1000);
        }

        public void triggerNo(GameEnvironment gameEnvironment, Race race) {
            race.getPlayer().setDidDNF(true,"Player Broke down");
        }
    };


    public abstract String getMessage();

    public void trigger(GameEnvironment gameEnvironment, Race race) {
        throw new UnsupportedOperationException("Not supported for this event");
    }

    // Add abstract methods for optional Events
    public void triggerYes(GameEnvironment gameEnvironment, Race race) {
        throw new UnsupportedOperationException("Not supported for this event");
    }

    public void triggerNo(GameEnvironment gameEnvironment, Race race) {
        throw new UnsupportedOperationException("Not supported for this event");
    }
}
