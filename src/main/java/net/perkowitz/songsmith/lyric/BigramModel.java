package net.perkowitz.songsmith.lyric;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mikep on 8/16/18.
 */
public class BigramModel implements LyricModel {

    Map<String, WeightedChoiceCollection> bigrams = Maps.newHashMap();

    public void addSong(List<String> words) {

        for (int i = 0; i < words.size()-1; i++) {
            String thisWord = words.get(i);
            String nextWord = words.get(i+1);

            WeightedChoiceCollection weightedChoiceCollection = bigrams.get(thisWord);
            if (weightedChoiceCollection == null) {
                weightedChoiceCollection = new WeightedChoiceCollection();
                bigrams.put(thisWord, weightedChoiceCollection);
            }

            weightedChoiceCollection.countChoice(nextWord, 1.0);

        }

//        for (String word : bigrams.keySet()) {
//            System.out.println(word);
//            List<WeightedChoiceCollection.Choice> choices = bigrams.get(word).getChanceArray();
//            for (WeightedChoiceCollection.Choice choice : choices) {
//                System.out.printf("  %s : %f\n", choice.getLabel(), choice.getChance());
//            }
//        }

    }


    public WeightedChoiceCollection getChoices(List<String> words, Set<String> context) {

        String lastWord = words.get(words.size() - 1);
        return bigrams.get(lastWord);
    }


}
