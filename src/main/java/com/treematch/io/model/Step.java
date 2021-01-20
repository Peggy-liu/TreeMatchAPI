package com.treematch.io.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class Step {

	private int id;
	private int question_id;
    private HashMap<String, Integer> answers;
    private int result_id;
}
