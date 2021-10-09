package module;

import java.io.IOException;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * 
 * @author GOXR3PLUS
 * https://github.com/goxr3plus/Java-Google-Text-To-Speech/blob/master/src/application/Trying_Different_Languages.java
 *
 */

public class TextToSpeech {
	public static void speak(String text) {
		SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		Thread thread = new Thread(() -> {
			try {
				AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
				player.play();
			} catch (IOException | JavaLayerException e) {
				e.printStackTrace();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
}
