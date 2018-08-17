package net.perkowitz.songsmith.lyric;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mikep on 8/15/18.
 *
 * Class for randomly choosing among a set of weighted choices. Computes a sum array for all choices,
 * and then does a binary search through those sums to find the appropriate choice. Faster than linear scan!
 * Computes the sum array lazily, but invalidates whenever the set of choices changes.
 * TODO: we could actually just add the new choice to the end of the sum array and update the chanceTotal
 *
 */
public class WeightedChoiceCollection {

    Map<String, Double> labelWeights = Maps.newHashMap();
    List<Choice> chanceArray = null;
    Double chanceTotal = null;


    public List<Choice> getChanceArray() {
        if (chanceArray == null) {
            computeChanceArray();
        }
        return chanceArray;
    }

    /**
     * add a choice to the collection. if it already exists, replace with new chance value
     * if chance value is zero, remove it
     *
     * @param label
     * @param chance
     */
    public void addChoice(String label, Double chance) {
        if (chance == null || chance == 0) {
            removeChoice(label);
        } else {
            labelWeights.put(label, chance);
        }

        // invalidate the chanceArray
        chanceArray = null;
        chanceTotal = null;
    }


    /**
     * if label already exists, add the chance to the existing chance
     *
     * @param label
     * @param chance
     */
    public void countChoice(String label, Double chance) {

        if (chance == null || chance == 0) {
            return;
        } else if (labelWeights.get(label) != null) {
            labelWeights.put(label, labelWeights.get(label) + chance);
        } else {
            labelWeights.put(label, chance);
        }

        // invalidate the chanceArray
        chanceArray = null;
        chanceTotal = null;
    }

    public void removeChoice(String label) {
        labelWeights.remove(label);
    }


    public String makeChoice() {

        if (chanceArray == null) {
            computeChanceArray();
        }

        Double r = Math.random() * chanceTotal;
        return binarySearch(0, chanceArray.size()-1, r);
    }

    /*** private implementation ************************************************************/

    private void computeChanceArray() {

        chanceTotal = 0.0;
        chanceArray = Lists.newArrayList();
        for (String label : labelWeights.keySet()) {
            chanceTotal += labelWeights.get(label);
            Choice choice = new Choice(label, chanceTotal);
            chanceArray.add(choice);
        }
    }

    private String binarySearch(int lower, int upper, Double target) {

        if (upper < lower) {
            return null;
        } else if (target < chanceArray.get(lower).getChance()) {
            // if it's lower than the current lower bound, the result should be that bound's key
            return chanceArray.get(lower).getLabel();
        } if (lower == upper || lower == upper - 1) {
            // if the value is between the bounds and they're one apart, the key should be the upper's key
            return chanceArray.get(upper).getLabel();
        }

        int mid = (int) Math.ceil((lower + upper) / 2);
        Double midValue = chanceArray.get(mid).getChance();
        if (target <= midValue) {
            return binarySearch(lower, mid, target);
        } else {
            return binarySearch(mid, upper, target);
        }
    }

    public class Choice {

        @Getter @Setter private String label;
        @Getter @Setter private Double chance;

        public Choice(String label, Double chance) {
            this.label = label;
            this.chance = chance;
        }


    }


}
