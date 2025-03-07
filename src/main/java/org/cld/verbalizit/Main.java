package org.cld.verbalizit;

import java.io.File;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {

        final String videoFilePath = args[0];
        final String audioFilePath = args.length > 1 ? args[1] : args[0].replaceAll("\\.\\w+$", ".wav");

        File audioFile = new File(audioFilePath);
        if (!audioFile.exists())
            new AudioExtractor(videoFilePath, audioFilePath).run();

        try {
            new SpeechToText().readFile(audioFile, new PrintWriter(System.out));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}