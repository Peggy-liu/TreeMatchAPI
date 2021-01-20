package com.treematch.io.controller;


import com.treematch.io.model.Answer;
import com.treematch.io.model.Match;
import com.treematch.io.model.Question;
import com.treematch.io.model.Step;
import com.treematch.io.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@Slf4j
public class APIController {

	@Autowired
	private DataService service;

	@GetMapping("/api/begin")
	public Question getFirstQuestion() {
		int index = 1;
		if (service.getAllQuestions().stream().findFirst().filter(q -> q.getId() == index).isPresent()) {
			return service.getAllQuestions().stream().findFirst().filter(q -> q.getId() == index).get();
		}

		return new Question();
	}

	@PostMapping("/api/answer")
	public Object submitAndNext(@RequestBody Answer answer) {
		ArrayList<Step> steps = service.getAllSteps();
		Step matchedStep = null;
		Object result = null;
		if (steps.stream().filter(a -> a.getId() == answer.getStep_id()).findFirst().isPresent()) {
			matchedStep = steps.stream().filter(a -> a.getId() == answer.getStep_id()).findFirst().get();
			try {
				int nextStepID = matchedStep.getAnswers().get(answer.getAnswer());
				if (steps.stream().filter(step -> step.getId() == nextStepID).findFirst().isPresent()) {
					Step nextStep = steps.stream().filter(step -> step.getId() == nextStepID).findFirst().get();
					if (nextStep.getResult_id() > 0) {
						int result_id = nextStep.getResult_id();
						ArrayList<Match> matches = service.getAllResults();
						result = matches.stream().filter(match -> match.getId() == result_id).findFirst().get();
					}
					else {
						Question question = service.getAllQuestions().stream().filter(q -> q.getId() == nextStep.getQuestion_id()).findFirst().get();
						result = question;
					}
				}
			} catch (NullPointerException e) {
				log.error("The answer given did not match with the record. No match was found.");
				result = ResponseEntity.status(500).body("The answer given did not match with the record. No match was found.");
			}


		}
		else {
			return ResponseEntity.status(200).body("There is no record match with the given id. Please use another id.");
		}


		return result;
	}


}
