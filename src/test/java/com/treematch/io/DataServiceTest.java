package com.treematch.io;

import com.google.gson.stream.JsonReader;
import com.treematch.io.service.DataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;

public class DataServiceTest {


	private DataService service = new DataService();


	@Test
	public void testProcessQuestion() throws FileNotFoundException {
		//test 1: invalid id format
		JsonReader reader = new JsonReader(new StringReader("[{\n" +
				"    \"id\": \"1.3\",\n" +
				"    \"question\":\"hello\" ,\n" +
				"    \"validation\": [\"courtyard\", \"garden\", \"farm\"]\n" +
				"  }]"));

		//test 2: invalid question format(number instead of string)
		JsonReader reader2 = new JsonReader(new StringReader("[{\n" +
				"    \"id\": \"1\",\n" +
				"    \"question\": 1.3 ,\n" +
				"    \"validation\": [\"courtyard\", \"garden\", \"farm\"]\n" +
				"  }]"));

		//test 3: invalid validation field
		JsonReader reader3 = new JsonReader(new StringReader("[{\n" +
				"    \"id\": \"1\",\n" +
				"    \"question\": 1.3 ,\n" +
				"    \"validation\": \"hello\"\n" +
				"  }]"));
		reader.setLenient(true);
		reader2.setLenient(true);
		reader3.setLenient(true);

		Assertions.assertThrows(Exception.class, () -> service.processQuestions(reader));
		Assertions.assertThrows(Exception.class, () -> service.processQuestions(reader2));
		Assertions.assertThrows(Exception.class, () -> service.processQuestions(reader3));

	}

	@Test
	public void testProcessStep(){
		//test 1: invalid id
		JsonReader reader = new JsonReader(new StringReader("[{\n" +
				"    \"id\": 1.3,\n" +
				"    \"question_id\": 1,\n" +
				"    \"answers\": {\n" +
				"      \"courtyard\": 2,\n" +
				"      \"garden\": 5,\n" +
				"      \"farm\": 8\n" +
				"    }}]"));

		//test 2: invalid question format(float number string instead of string) (integer string is allowed)
		JsonReader reader2 = new JsonReader(new StringReader("[{\n" +
				"    \"id\": 1,\n" +
				"    \"question_id\": \"1.3\",\n" +
				"    \"answers\": {\n" +
				"      \"courtyard\": 2,\n" +
				"      \"garden\": 5,\n" +
				"      \"farm\": 8\n" +
				"    }}]"));

		//test 3: invalid answers field
		JsonReader reader3 = new JsonReader(new StringReader("[{\n" +
				"    \"id\": 2,\n" +
				"    \"question_id\": 2,\n" +
				"    \"answers\": [\"hello there\"]\n" +
				"  }]"));
		//test 4: invalid result field
		JsonReader reader4 = new JsonReader(new StringReader("[{ \"id\": 13, \"result_id\": \"hello\" }]"));

		//test 5: invalid field name
		JsonReader reader5 = new JsonReader(new StringReader("[{\n" +
				"    \"random_id\": 1,\n" +
				"    \"question_id\": 1,\n" +
				"    \"answers_test\": {\n" +
				"      \"courtyard\": 2,\n" +
				"      \"garden\": 5,\n" +
				"      \"farm\": 8\n" +
				"    }}]"));
		reader.setLenient(true);
		reader2.setLenient(true);
		reader3.setLenient(true);
		reader4.setLenient(true);
		reader5.setLenient(true);

		Assertions.assertThrows(Exception.class, () -> service.processSteps(reader));
		Assertions.assertThrows(Exception.class, () -> service.processSteps(reader2));
		Assertions.assertThrows(Exception.class, () -> service.processSteps(reader3));
		Assertions.assertThrows(Exception.class, () -> service.processSteps(reader4));
		Assertions.assertThrows(Exception.class, () -> service.processSteps(reader5));
	}


	@Test
	public void testProcessResult(){
		//test 1: invalid id format
		JsonReader reader = new JsonReader(new StringReader("[{\n" +
				"    \"id\": \"1.3\",\n" +
				"    \"name\": \"Curry leaf tree\",\n" +
				"    \"description\": \"A small tree that's great for courtyards, from which leaves can be picked and added to any curry!\"\n" +
				"  }]"));

		//test 2: invalid name value
		JsonReader reader2 = new JsonReader(new StringReader("[{\n" +
				"    \"id\": 2,\n" +
				"    \"name\": 3.3,\n" +
				"    \"description\": \"A robust small tree with small, fragrant, dark green leaves that are great for use in stews and curries.\"\n" +
				"  }]"));

		//test 3: invalid description value
		JsonReader reader3 = new JsonReader(new StringReader("[{\n" +
				"    \"id\": 3,\n" +
				"    \"name\": \"Frangipani\",\n" +
				"    \"description\": [\"hello\", \"test\"]\n" +
				"  }]"));
		reader.setLenient(true);
		reader2.setLenient(true);
		reader3.setLenient(true);

		Assertions.assertThrows(Exception.class, () -> service.processResults(reader));
		Assertions.assertThrows(Exception.class, () -> service.processResults(reader2));
		Assertions.assertThrows(Exception.class, () -> service.processResults(reader3));
	}
}
