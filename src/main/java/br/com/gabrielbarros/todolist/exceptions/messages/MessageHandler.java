package br.com.gabrielbarros.todolist.exceptions.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageHandler {

	public static String settingParamsToMessage(String source, String... params) {

		for (int index = 0; index < params.length; index++) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("{").append(index).append("}");
			String regex = stringBuilder.toString();
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(source);

			if (matcher.find()) {
				source = source.replace(regex, params[index]);
			} else {
				throw new RuntimeException("Error in the number of parameters for message formatting");
			}
		}

		return source;
	}

}
