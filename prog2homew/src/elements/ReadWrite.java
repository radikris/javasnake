package elements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import com.google.common.primitives.Ints;

public class ReadWrite {

	private String fileName;

	Multimap<String, String> name_points = ArrayListMultimap.create();
	public ArrayList<String> namepoint = new ArrayList<String>();

	public static JSONArray name_point_json;

	public ReadWrite(String fname) {
		this.fileName = fname;
	}

	public void readFile() throws IOException, ParseException {
		namepoint = new ArrayList<String>();

		JSONParser parser = new JSONParser();

		String filepath = "src/assets/" + fileName;
		name_point_json = (JSONArray) parser.parse(new FileReader(filepath));

		for (Object o : name_point_json) {
			JSONObject person = (JSONObject) o;

			String name = (String) person.get("name");

			String points = (String) person.get("point");

			String line = points + " " + name;

			namepoint.add(line);
		}

		Collections.sort(namepoint, new Comparator<String>() {
			public int compare(String a, String b) {
				if (a != null && b != null) {
					int n1 = Integer.parseInt(a.split(" ")[0]);
					int n2 = Integer.parseInt(b.split(" ")[0]);
					return n1 - n2;
				} else {
					return 0;
				}

			}
		});
		Collections.reverse(namepoint);

	}

	public void writeFile(String username, String point) throws Exception {
		String filepath = "src/assets/" + fileName;
		writeJsonSimpleDemo("src/assets/example.json", username, point);
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<String> getNamepoint() {
		return namepoint;
	}

	public static void writeJsonSimpleDemo(String filename, String username, String point) throws Exception {
		JSONObject userObject = userNamePoint(username, point);

		name_point_json.add(userObject);

		Files.write(Paths.get(filename), name_point_json.toJSONString().getBytes());
	}

	public static Object readJsonSimpleDemo(String filename) throws Exception {
		FileReader reader = new FileReader(filename);
		JSONParser jsonParser = new JSONParser();
		return jsonParser.parse(reader);
	}

	public static JSONObject userNamePoint(String username, String point) {
		JSONObject userObject = new JSONObject();
		userObject.put("name", username);
		userObject.put("point", point);

		return userObject;
	}

}
