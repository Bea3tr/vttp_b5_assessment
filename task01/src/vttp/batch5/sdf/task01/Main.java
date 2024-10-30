package vttp.batch5.sdf.task01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import vttp.batch5.sdf.task01.models.BikeEntry;

// Use this class as the entry point of your program

public class Main {

	public static void main(String[] args) {
		// Read day.csv & parse columns to get details
		try {
			Reader read = new FileReader("day.csv");
			BufferedReader br = new BufferedReader(read);
			List<BikeEntry> entries = new ArrayList<>();

			// First line is headers - ignore
			String line = br.readLine();
			while (line != null) {
				line = br.readLine();
				if (line == null)
					break;
				// Get individual records in array
				String[] cols = line.split(",");
				// Takes in String array
				BikeEntry entry = BikeEntry.toBikeEntry(cols);
				entries.add(entry);
			}
			br.close();
			// Find top 5 days with most cyclist
			List<BikeEntry> sorted = entries.stream()
					.sorted((e1, e2) -> Integer.compare(e1.getRegistered(), e2.getRegistered()))
					.collect(Collectors.toList());
			// In descending order
			Collections.reverse(sorted);

			// Get top five days
			List<BikeEntry> topFiveDays = sorted.subList(0, 5);
			topFiveDays.forEach(tf -> System.out.println(tf.getRegistered()));
			String[] POSITION = { "highest", "second highest", "third highest", "fourth highest", "fifth highest" };
			String[] WEATHER = { "Clear, Few clouds, Partly cloudy, Partly cloudy",
					"Mist + Cloudy, Mist + Broken clouds, Mist + Few clouds, Mist",
					"Light Snow, Light Rain + Thunderstorm + Scattered clouds, Light Rain + Scattered clouds",
					"Heavy Rain + Ice Pallets + Thunderstorm + Mist, Snow + Fog" };

			for (int i = 0; i < topFiveDays.size(); i++) {
				BikeEntry be = topFiveDays.get(i);
				String pos = POSITION[i];
				String season = Utilities.toSeason(be.getSeason());
				String day = Utilities.toWeekday(be.getWeekday());
				String month = Utilities.toMonth(be.getMonth());
				int total = be.getRegistered();
				String weather = WEATHER[be.getWeather() - 1];
				String holiday = (be.isHoliday()) ? "a holiday" : "not a holiday";
				System.out.printf(
						"The %s recorded number of cyclists was in %s,on a %s in the month of %s. There were a total of %d cyclists. The weather was %s. %s was %s\n\n",
						pos, season, day, month, total, weather, day, holiday);
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
