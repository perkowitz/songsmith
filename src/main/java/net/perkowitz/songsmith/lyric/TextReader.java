package net.perkowitz.songsmith.lyric;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by mikep on 8/15/18.
 */
public class TextReader {


    public List<String> readFile(String filename) {

        List<String> words = Lists.newArrayList();

        try {

            File f = new File(filename);

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                String[] splitWords = readLine.split(" ");
                for (String word : splitWords) {
                    if (!word.equals("")) {
                        words.add(word);
                    }
                }
                words.add(LyricModel.END_OF_LINE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;

    }

}
