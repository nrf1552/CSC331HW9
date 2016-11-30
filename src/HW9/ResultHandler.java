package HW9;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultHandler {
	String csv;
	String line;
	String separator;
	BufferedReader reader;

	List<Result> personalResults;
	List<Result> allResults;
	List<String> users;

	public ResultHandler() {
		csv = "results.csv";
		line = null;
		separator = ",";
		readCsv();
	}

	public Result getPersonalBest(String name, int numberOfProblems, int numberFamily, boolean isAddSubtract) {
		// refresh data
		readCsv();

		// return first result from list (since it's sorted)
		for (Result r : allResults) {
			if (r.name.equalsIgnoreCase(name) && r.numberOfProblems == numberOfProblems
					&& r.numberFamily == numberFamily && r.isAddSubtract == isAddSubtract) {
				return r;
			}
		}

		return null;
	}

	public List<Result> getAllPersonalResults(String name) {
		// refresh data
		readCsv();

		List<Result> results = new ArrayList<Result>();

		for (Result r : allResults) {
			if (r.name.equalsIgnoreCase(name)) {
				results.add(r);
			}
		}

		return results;
	}

	public Result getAllTimeBest(int numberOfProblems, int numberFamily, boolean isAddSubtract) {
		// refresh data
		readCsv();

		// return first result from list (since it's sorted)
		for (Result r : allResults) {
			if (r.numberOfProblems == numberOfProblems && r.numberFamily == numberFamily
					&& r.isAddSubtract == isAddSubtract) {
				return r;
			}
		}

		return null;
	}

	public List<Result> getAllResults() {
		readCsv();

		return allResults;
	}

	public List<String> getUsers() {
		readCsv();

		List<String> users = new ArrayList<String>();

		for (Result result : allResults) {
			boolean found = false;

			for (String user : users) {
				if (user.equalsIgnoreCase(result.name)) {
					found = true;
					break;
				}
			}

			if (!found) {
				users.add(result.name);
			}
		}
		
		Collections.sort(users);

		return users;
	}

	private void readCsv() {
		allResults = new ArrayList<Result>();

		try {
			reader = new BufferedReader(new FileReader(csv));

			int l = 0;
			while ((line = reader.readLine()) != null) {
				// skip headers
				if (l != 0) {
					String[] data = line.split(separator);
					Result result = new Result();
					result.name = data[0];
					result.numberOfProblems = Integer.parseInt(data[1]);
					result.numberFamily = Integer.parseInt(data[2]);
					result.isAddSubtract = Boolean.parseBoolean(data[3]);
					result.averageTime = Long.parseLong(data[4]);
					allResults.add(result);
				}
				l++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// sort list by time
		Collections.sort(allResults);
	}

	public void saveResult(String name,int numberOfProblems, int numberFamily,boolean isAddSubtract, long averageTime){
		
	}
	// data object
	private class Result implements Comparable<Result> {
		public String name;
		public int numberOfProblems, numberFamily;
		public boolean isAddSubtract;
		public long averageTime;

		@Override
		public int compareTo(Result arg0) {
			return Long.compare(averageTime, arg0.averageTime);
		}
	}
}
