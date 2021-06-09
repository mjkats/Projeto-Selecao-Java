package br.com.logapp.project.service;

import java.text.Normalizer;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.logapp.project.enumerate.MatchState;
import br.com.logapp.project.nonmodel.Vogal;

import static br.com.logapp.project.enumerate.MatchState.*;

@Service
public class VogalService {
	
	public Vogal getVogalFromString(String input) {
		Instant start = Instant.now();
		
		if (input.isBlank() || input.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide input");
		
		String cleanInput = removeAccentAndToLower(input);

		List<String> patternsFound = new ArrayList<>();
		Map<Character, Integer> vowelsMatchCount = new HashMap<>();
		
		List<Character> vowels = Collections.unmodifiableList(Arrays.asList(
				'a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U'
		));
		
		MatchState state = OTHER;
		StringBuilder strBuilder = new StringBuilder();
		
		for (int i = 0; i < cleanInput.length(); i++) {
			if (vowels.contains(cleanInput.charAt(i))) {
				state = changeState(state, true);
				
				if (state.equals(THIRD_VOGAL)) {
					if (!vowelsMatchCount.keySet().contains(cleanInput.charAt(i))) {
						char[] chars = {cleanInput.charAt(i-2), cleanInput.charAt(i-1), cleanInput.charAt(i)};
						strBuilder.append(chars);
						patternsFound.add(strBuilder.toString());
						strBuilder.delete(0, strBuilder.length());
					}
				}
				
				if (vowelsMatchCount.containsKey(cleanInput.charAt(i))) {
					int count = vowelsMatchCount.get(cleanInput.charAt(i));
					vowelsMatchCount.put(cleanInput.charAt(i), count+=1);
				} else {
					vowelsMatchCount.put(cleanInput.charAt(i), 1);
				}
				
			} else if (Character.isLetter(cleanInput.charAt(i))){
				state = changeState(state, false);
			} else {
				state = OTHER;
			}
				
		}
		
		Character vowel = retrieveValidVowelFromPatternsFound(patternsFound, vowelsMatchCount);	
		Instant end = Instant.now();
		return(new Vogal(input, vowel, Duration.between(start, end).toMillis() + "ms"));
	}
	
	public String removeAccentAndToLower(String input) {
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(normalized).replaceAll("").toLowerCase();
	}
	
	public MatchState changeState(MatchState state, boolean isVogal) {
		if (state.equals(OTHER)) {
			if (isVogal)
				return FIRST_VOGAL;
			return FIRST_CONSONANT;
		}
		
		if (state.equals(FIRST_CONSONANT)) {
			if (isVogal)
				return FIRST_VOGAL;
			return FIRST_CONSONANT;
		}
		
		if (state.equals(FIRST_VOGAL)) {
			if (isVogal)
				return FIRST_VOGAL;
			return SECOND_CONSONANT;
		}
		
		if (state.equals(SECOND_CONSONANT)) {
			if (isVogal)
				return THIRD_VOGAL;
			return FIRST_CONSONANT;
		}
		
		if (state.equals(THIRD_VOGAL)) {
			if (isVogal)
				return FIRST_VOGAL;
			return SECOND_CONSONANT;
		}
		
		return OTHER;
	}
	
	public Character retrieveValidVowelFromPatternsFound(List<String> patternsFound, Map<Character, Integer> vowelsMatchCount) {
		List<String> wrongPatterns = new ArrayList<>();
		
		for (int i = 0; i < patternsFound.size(); i++) {
			if (vowelsMatchCount.get(patternsFound.get(i).charAt(2)) > 1)
				wrongPatterns.add(patternsFound.get(i));
		}
		
		if (!wrongPatterns.isEmpty())
			patternsFound.removeAll(wrongPatterns);
		
		if (patternsFound.isEmpty())
			return(null);
		return(patternsFound.get(0).charAt(2));
	}

}