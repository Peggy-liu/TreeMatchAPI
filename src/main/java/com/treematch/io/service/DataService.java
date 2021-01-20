package com.treematch.io.service;


import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.treematch.io.model.JsonFile;
import com.treematch.io.model.Match;
import com.treematch.io.model.Question;
import com.treematch.io.model.Step;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class DataService {

	private JsonFile jsonFile;


	@PostConstruct
	public void processJsonFile() throws IOException {
		JsonFile file = new JsonFile();
		ArrayList<Question> questions = new ArrayList<>();
		ArrayList<Step> steps = new ArrayList<>();
		ArrayList<Match> results = new ArrayList<>();
		JsonReader jsonReader = null;
		try {
			jsonReader = new JsonReader(new InputStreamReader(new FileInputStream("questions.json")));
			jsonReader.setLenient(true);
			jsonReader.beginObject();

			while (jsonReader.hasNext()) {
				JsonToken nextToken = jsonReader.peek();
				if (JsonToken.NAME.equals(nextToken)) {
					String name = jsonReader.nextName();
					if (name.equals("questions")) {
						questions = processQuestions(jsonReader);

					}
					else if (name.equals("steps")) {
						steps = processSteps(jsonReader);

					}
					else if (name.equals("results")) {
						results = processResults(jsonReader);

					}
					else {
						jsonReader.skipValue();
					}
				}


			}
			jsonReader.endObject();
			file.setQuestions(questions);
			file.setSteps(steps);
			file.setResults(results);
			this.jsonFile = file;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			jsonReader.close();
		}

	}

	public ArrayList<Question> processQuestions(JsonReader jsonReader) {
		ArrayList<Question> questions = new ArrayList<>();

		try {
			jsonReader.beginArray();
			Question question = null;
			while (jsonReader.hasNext()) {
				JsonToken nextToken = jsonReader.peek();

				if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
					jsonReader.beginObject();
					question = new Question();
					while (jsonReader.hasNext()) {
						JsonToken nextName = jsonReader.peek();
						if (JsonToken.NAME.equals(nextName)) {
							String name = jsonReader.nextName();
							if (name.equals("id")) {
								int id = jsonReader.nextInt();
								question.setId(id);
							}
							else if (name.equals("question")) {
								String ques = jsonReader.nextString();
								//check if the string is number
								if (!NumberUtils.isParsable(ques)) {
									question.setQuestion(ques);
								}
								else {
									throw new IllegalStateException("the question is malformed");
								}
							}
							else if (name.equals("validation")) {
								jsonReader.beginArray();
								ArrayList<String> validation = new ArrayList<>();
								while (jsonReader.hasNext()) {
									validation.add(jsonReader.nextString());
								}
								question.setValidation(validation);
								jsonReader.endArray();
							}
							else {
								jsonReader.skipValue();
								throw new IllegalStateException("The provided json file contains unknown fields. Please check your document.");
							}
						}
					}
					jsonReader.endObject();
					questions.add(question);
				}
			}
			jsonReader.endArray();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return questions;
	}

	public ArrayList<Step> processSteps(JsonReader jsonReader) {
		ArrayList<Step> steps = new ArrayList<>();

		try {
			jsonReader.beginArray();
			while (jsonReader.hasNext()) {
				JsonToken token = jsonReader.peek();
				//Beginning of processing one of each step object
				if (JsonToken.BEGIN_OBJECT.equals(token)) {
					jsonReader.beginObject();
					Step step = new Step();
					while (jsonReader.hasNext()) {
						String prop = jsonReader.nextName();
						if (prop.equals("id")) {
							step.setId(jsonReader.nextInt());
						}
						else if (prop.equals("question_id")) {
							step.setQuestion_id(jsonReader.nextInt());
						}
						else if (prop.equals("answers")) {
							HashMap<String, Integer> answers = new HashMap<>();
							jsonReader.beginObject();
							while (jsonReader.hasNext()) {
								String key = jsonReader.nextName();
								int value = jsonReader.nextInt();
								answers.put(key, value);
							}
							jsonReader.endObject();
							step.setAnswers(answers);
						}
						else if (prop.equals("result_id")) {
							step.setResult_id(jsonReader.nextInt());
						}
						else {
							jsonReader.skipValue();
							throw new IllegalStateException("The provided json file contains unknown fields. Please check your document.");
						}

					}
					jsonReader.endObject();
					steps.add(step);
				}
			}
			jsonReader.endArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return steps;
	}

	public ArrayList<Match> processResults(JsonReader jsonReader) {
		ArrayList<Match> results = new ArrayList<>();
		try {
			jsonReader.beginArray();
			while (jsonReader.hasNext()) {
				JsonToken token = jsonReader.peek();
				if (JsonToken.BEGIN_OBJECT.equals(token)) {
					Match match = new Match();
					jsonReader.beginObject();
					while (jsonReader.hasNext()) {
						String name = jsonReader.nextName();
						if (name.equals("id")) {
							match.setId(jsonReader.nextInt());
						}
						else if (name.equals("name")) {
							String value = jsonReader.nextString();
							//if it is a number string, then it is invalid value
							if (NumberUtils.isParsable(value)) {
								throw new IllegalStateException("the name value should be of type Literal String.");
							}
							else {
								match.setName(value);
							}

						}
						else if (name.equals("description")) {
							String desc = jsonReader.nextString();
							//if it is a number string, then it is invalid value
							if (NumberUtils.isParsable(desc)) {
								throw new IllegalStateException("the description value should be of type Literal String.");
							}
							match.setDescription(desc);
						}
						else {
							jsonReader.skipValue();
							throw new IllegalStateException("The provided json file contains unknown fields. Please check your document.");
						}
					}
					jsonReader.endObject();
					results.add(match);
				}
			}
			jsonReader.endArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	public ArrayList<Question> getAllQuestions() {
		return jsonFile.getQuestions();
	}


	public ArrayList<Step> getAllSteps() {
		return jsonFile.getSteps();
	}

	public ArrayList<Match> getAllResults() {
		return jsonFile.getResults();
	}
}
