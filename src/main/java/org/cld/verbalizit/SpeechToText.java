package org.cld.verbalizit;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

public class SpeechToText {

    private static Model model = null;
    private static Recognizer recognizer = null;

    public SpeechToText() throws IOException {
        if (model == null) {
            LibVosk.setLogLevel(LogLevel.WARNINGS);
            model = new Model("src/main/resources/vosk");
            recognizer = new Recognizer(model, 44100);
        }
    }

    public void readFile(File waveFile, Writer writer) {
        try (FileInputStream fiS = new FileInputStream(waveFile)) {
            readFile(fiS, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void readFile(AudioInputStream ais, Writer writer) throws IOException, UnsupportedAudioFileException {

        JSONObject result = null;

        int nbytes;
        byte[] b = new byte[4096];
        while ((nbytes = ais.read(b)) >= 0) {
            if (recognizer.acceptWaveForm(b, nbytes)) {
                result = new JSONObject(new JSONTokener(recognizer.getResult()));
                if (result.has("text"))
                    writer.write(result.getString("text")+"\n");
            }
        }
    }

    // https://alphacephei.com/vosk/models
    public void readFile(InputStream iS, Writer writer) throws IOException, UnsupportedAudioFileException {
        readFile(AudioSystem.getAudioInputStream(new BufferedInputStream(iS)), writer);
    }
}
