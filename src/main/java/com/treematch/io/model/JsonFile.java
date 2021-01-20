package com.treematch.io.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class JsonFile {
	private ArrayList<Question> questions;
	private ArrayList<Step> steps;
	private ArrayList<Match>  results;
}
