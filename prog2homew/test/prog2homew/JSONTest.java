package prog2homew;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import elements.ReadWrite;

public class JSONTest {

	public ReadWrite readwrite;

	@Test(expected = IOException.class)
	public void readTestNoFileFound() throws IOException, ParseException {
		readwrite = new ReadWrite("nincsfile.json");
		readwrite.readFile();
	}

	@Test
	public void jsonObjectTest() {
		JSONObject jsonobject = readwrite.userNamePoint("jsontest", "7");
		String usname = (String) jsonobject.get("name");

		Assert.assertEquals("jsontest", usname);
	}
	
	@Test
	public void readFromJson() throws IOException, ParseException {
		readwrite = new ReadWrite("example.json");
		readwrite.readFile();
		
		ArrayList<String> results=readwrite.getNamepoint();
		int maximum=0;
		for(int i=0; i<results.size(); i++) {
			int parsed_value=Integer.parseInt(results.get(i).split(" ")[0]);
			if(parsed_value>maximum) {
				maximum=parsed_value;
			}
		}
		Assert.assertEquals(99, maximum, 0);
	}

}
