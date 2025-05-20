package seng201.team019.models;

import seng201.team019.GameEnvironment;

import java.util.Random;

/**
 * Enum representing different random events that can occur during a race.
 */
public enum RandomEvent {

    /**
     * A weather event that disqualifies a randomly selected {@link Route}.
     * All {@link Racer} on this {@link Route} will DNF unless they've finished.
     */
    RouteWeather {
        /**
         * The route that is randomly selected.
         */
        private Route disqualifiedRoute;

        public String getMessage() {
            return String.format("Weather Event on the %s route.", disqualifiedRoute.getDescription());
        }

        @Override
        public void trigger(GameEnvironment gameEnvironment, Race race) {
            //select random route
            Random rand = new Random();
            Route disqualifiedRoute = rand.nextInt(race.getRoutes().size()) == 0
                    ? race.getRoutes().get(rand.nextInt(race.getRoutes().size()))
                    : race.getRoutes().getFirst();

            // DNF each racer on this route
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
    },
    /**
     * The player stops to pick up a random traveler.
     * Player will Gain $1000 but will be delayed.
     */
    PlayerStrandedTraveler {
        @Override
        public String getMessage() {
            return "You picked up a traveler and gained $1000. This delayed you 2 minutes.";
        }

        public void trigger(GameEnvironment gameEnvironment, Race race) {
            gameEnvironment.setBankBalance(gameEnvironment.getBankBalance() + 1000);
        }
    },
    /**
     * The player breaks down.
     * If the player can afford to repair they are given the option to repair.
     */
    PlayerBreaksDown {
        @Override
        public String getMessage() {
            return "Your car breaks down.\n";
        }

        public void triggerYes(GameEnvironment gameEnvironment, Race race) {
            gameEnvironment.setBankBalance(gameEnvironment.getBankBalance() - 1000);
        }

        public void triggerNo(GameEnvironment gameEnvironment, Race race) {
            race.getPlayer().setDidDNF(true, "Player Broke down");
            gameEnvironment.getGarage().getFirst().setBroken(true);
        }
    };

    /**
     * Returns the Message for each the random event
     * @return a string describing the random event
     */
    public abstract String getMessage();

    /**
     * Triggers the effect of this event.
     * <p>
     * This default implementation is not supported and will always throw an
     * {@link UnsupportedOperationException}.
     *
     * @param gameEnvironment the game environment.
     * @param race the race the event is occurring in.
     * @throws UnsupportedOperationException if the event does not support being triggered.
     */
    public void trigger(GameEnvironment gameEnvironment, Race race) {
        throw new UnsupportedOperationException("Not supported for this event");
    }

    /**
     * Triggers the effect of this event when the player selects "Yes".
     * <p>
     * This default implementation is not supported and will always throw an
     * {@link UnsupportedOperationException}.
     *
     * @param gameEnvironment the game environment.
     * @param race the race the event is occurring in.
     * @throws UnsupportedOperationException if the event does not support being triggered.
     */
    public void triggerYes(GameEnvironment gameEnvironment, Race race) {
        throw new UnsupportedOperationException("Not supported for this event");
    }

    /**
     * Triggers the effect of this event when the player selects "No".
     * <p>
     * This default implementation is not supported and will always throw an
     * {@link UnsupportedOperationException}.
     *
     * @param gameEnvironment the game environment.
     * @param race the race the event is occurring in.
     * @throws UnsupportedOperationException if the event does not support being triggered.
     */
    public void triggerNo(GameEnvironment gameEnvironment, Race race) {
        throw new UnsupportedOperationException("Not supported for this event");
    }
}
