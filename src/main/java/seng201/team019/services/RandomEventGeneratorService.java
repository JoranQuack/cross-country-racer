package seng201.team019.services;


import seng201.team019.models.RandomEvent;

import java.util.Arrays;
import java.util.Random;



public class RandomEventGeneratorService {


        private final Random rand = new Random();

        public boolean raceHasRandomEvent(float percentage){
            // todo: implement this
            return true;
        }

        public RandomEvent generateRandomEvent(){
            return RandomEvent.values()[rand.nextInt(RandomEvent.values().length)];
        }

        public long eventTriggerTime(long startTime, long endTime){
            return rand.nextInt((int) (endTime - startTime)) + startTime;
        }


}
