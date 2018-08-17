package net.perkowitz.songsmith.lyric;

import com.google.common.collect.Lists;

import java.util.List;

import static net.perkowitz.songsmith.lyric.LyricModel.END_OF_LINE;

/**
 * Created by mikep on 8/15/18.
 */
public class Lyric {

    public static void main(String args[]) {

        TextReader textReader = new TextReader();

        List<String> testSong = Lists.newArrayList("A", "B", "A", END_OF_LINE);

        List<String> words = textReader.readFile(args[0]);

        BigramModel bigramModel = new BigramModel();
        bigramModel.addSong(words);

        List<String> song = generate(bigramModel, 20);
        render(song);

//        testWCC();

    }

    public static void testWCC() {

        WeightedChoiceCollection wcc = new WeightedChoiceCollection();
        wcc.addChoice("One", 0.5);
        wcc.addChoice("Two", 0.3);
        wcc.addChoice("Three", 0.2);

        for (int i = 0; i < 10; i++) {
            System.out.printf("Choice: %s\n", wcc.makeChoice());
        }

    }

    public static List<String> generate(LyricModel model, int count) {

        List<String> words = Lists.newArrayList();
        words.add(END_OF_LINE);
        for (int i = 0; i < count; i++) {
            WeightedChoiceCollection weightedChoiceCollection = model.getChoices(words, null);
            String choice = weightedChoiceCollection.makeChoice();
            words.add(choice);
        }

        return words;
    }

    public static void render(List<String> words) {

        for (String word : words) {
            if (word.equals(END_OF_LINE)) {
                System.out.println();
            } else {
                System.out.printf("%s ", word);
            }
        }
        System.out.println();
    }

}