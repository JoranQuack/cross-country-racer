package seng201.team019.services;


import seng201.team019.models.Player;
import seng201.team019.models.Race;
import seng201.team019.models.Racer;
import seng201.team019.models.RandomEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;



public class RandomEventGeneratorService {


        private final Random rand = new Random();

        public boolean raceHasRandomEvent(float percentage){
            // todo: implement this
            return true;
        }

        public RandomEvent generateRandomEvent(Race race) {

            double chanceOfReliabilityEvent = 1-race.getPlayer().getCar().getReliability();

            if (rand.nextDouble() < chanceOfReliabilityEvent) {
                return RandomEvent.PlayerBreaksDown;
            } else{
                List<RandomEvent> RemainingEvents = Arrays.stream(RandomEvent.values()).filter(e -> e != RandomEvent.PlayerBreaksDown).toList();
                return RemainingEvents.get(rand.nextInt(RemainingEvents.size()));
            }

        }

        public long eventTriggerTime(long startTime, long endTime){
            return rand.nextInt((int) (endTime - startTime)) + startTime;
        }


}
