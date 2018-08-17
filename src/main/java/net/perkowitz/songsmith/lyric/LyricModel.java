package net.perkowitz.songsmith.lyric;

import java.util.List;
import java.util.Set;

/**
 * Created by mikep on 8/15/18.
 */
public interface LyricModel {

    public static String END_OF_LINE = "EOL";

    // adds a song to the model's data structures; a song is just an ordered list of words, including "EOL"
    public void addSong(List<String> words);

    // given the current point in the song, returns a WeightedChoiceCollection representing possible next words/phrases
    // can also take a context of words that may indicate topic, title, etc
    public WeightedChoiceCollection getChoices(List<String> words, Set<String> context);

}
