package module;

import java.io.IOException;

import com.darkprograms.speech.translator.GoogleTranslate;

/**
 *
 *
 * @author GOXR3PLUS
 *
 */

public class APIGoogleTranslate {
	public static StringBuilder translate(String sourceLanguage,
										  String targetLanguage,
										  String text) {
		String[] sentences = text.split("\\.");
		StringBuilder result = new StringBuilder();
		try {
			for (String sentence: sentences) {
				result.append(GoogleTranslate.translate(sourceLanguage, targetLanguage, sentence));
				result.append(". ");
			}
			if (result.length() > 0) {
				result.deleteCharAt(result.length() - 1);
				result.deleteCharAt(result.length() - 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
