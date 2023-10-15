package br.com.gabrielbarros.todolist.config;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper modelMapper = new ModelMapper();

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Condition<?, ?> notNullCondition = context -> context.getSource() != null;

		modelMapper.getConfiguration().setPropertyCondition(notNullCondition);

		return modelMapper;
	}
}

