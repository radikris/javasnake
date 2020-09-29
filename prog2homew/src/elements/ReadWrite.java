package elements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
	public ArrayList<String> names = new ArrayList<String>();
	public ArrayList<Integer> points = new ArrayList<Integer>();
	Multimap<String, String> name_points = ArrayListMultimap.create();
	public ArrayList<String> namepoint = new ArrayList<String>();

	public ReadWrite(String fname) {
		this.fileName = fname;
	}

	public void readFile() throws Exception {
		namepoint = new ArrayList<String>();
		FileReader fr = new FileReader("src/assets/players.txt");
		BufferedReader br = new BufferedReader(fr);
		while (true) {
			String line = br.readLine();
			if (line != null) {
				String[] splited = line.split("\\s+");
				String point = splited[0];
				String name = "";
				for (int i = 1; i < splited.length; i++) {
					name = name.concat(splited[i]);
					if (i + 1 != splited.length)
						name = name.concat(" ");

				}

				namepoint.add(line);
				// name_points.put(name, point);
			}
			if (line == null)
				break;

		}
		br.close();

		Collections.sort(namepoint, new Comparator<String>() {
			public int compare(String a, String b) {
				int n1 = Integer.parseInt(a.split(" ")[0]);
				int n2 = Integer.parseInt(b.split(" ")[0]);

				return n1 - n2;
			}
		});
		Collections.reverse(namepoint);

	}

	public void writeFile(String username, String point) throws IOException {
		FileWriter fw = new FileWriter("src/assets/players.txt");
		PrintWriter pw = new PrintWriter(fw);
		for (int i = 0; i < namepoint.size(); i++) {
			pw.println(namepoint.get(i));
		}
		pw.println(point + " " + username);
		pw.close();
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * public Multimap<String, String> sortedByDescendingFrequency(Multimap<String,
	 * String> multimap) { // ImmutableMultimap.Builder preserves key/value order
	 * ImmutableMultimap.Builder<String, String> result =
	 * ImmutableMultimap.builder(); for (Multiset.Entry<String> entry :
	 * DESCENDING_COUNT_ORDERING.sortedCopy(multimap.keys().entrySet())) {
	 * result.putAll(entry.getElement(), multimap.get(entry.getElement())); } return
	 * result.build(); }
	 */

	public ArrayList<String> getNamepoint() {
		return namepoint;
	}

}
