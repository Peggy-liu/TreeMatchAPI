package com.treematch.io.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Question {
	private int id;
	private String question;
	private ArrayList<String> validation;

}
