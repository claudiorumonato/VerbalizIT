package org.cld.verbalizit;

import java.io.*;

import ws.schild.jave.EncoderException;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.progress.EncoderProgressListener;

public class AudioExtractor implements Runnable {

    private String videoFile = null;
    private String audioFile = null;

    public AudioExtractor(String videoPath) {
        this(videoPath, videoPath.replaceAll("\\.\\w+$", ".mp3"));
    }

    public AudioExtractor(String videoPath, String audioPath) {
        videoFile = videoPath;
        audioFile = audioPath;
    }

    public void run() {

        File source = new File(videoFile);
        File target = new File(audioFile);

        //Audio Attributes
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");
        audio.setBitRate(128000);
        audio.setChannels(1);
        audio.setSamplingRate(44100);

        //Encoding attributes
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("wav");
        attrs.setAudioAttributes(audio);

        //Encode
        try {
            new Encoder().encode(new MultimediaObject(source), target, attrs, new EncoderProgressListener() {

                @Override
                public void sourceInfo(MultimediaInfo multimediaInfo) {
                    System.out.println("[SOURCE] " + multimediaInfo);
                }

                @Override
                public void progress(int i) {
                    System.out.println("[PROGRESS] " + (i / 10) + "." + (i % 10) + "%");
                }

                @Override
                public void message(String s) {
                    System.out.println("[MESSAGE] " + s);
                }
            });
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
    }

}
