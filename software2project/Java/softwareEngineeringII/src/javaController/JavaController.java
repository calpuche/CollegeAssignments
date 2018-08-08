package javaController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class JavaController {
	private static Scanner sc;

	public static void main(String[] args) throws InterruptedException {
		try {
			// Get SUT ID from user
			sc = new Scanner(System.in);
			System.out.println("Enter the System Under Testing ID");
			int sut = sc.nextInt();
			String sutString = Integer.toString(sut);
			// do a while loop that continously runs for the amount of time
			// Also needs to have an amount of time in between
			System.out.println("How long do you want to run the program for(in seconds)?");
			int time = sc.nextInt();
			time = time * 1000;
			System.out.println("How long do you want to wait between tests(in seconds)?");
			int wait = sc.nextInt();
			long t = System.currentTimeMillis();
			long end = t + time;
			String result = null;
			String script = null;
			String scriptName = null;
			int counter = 0;
			while (System.currentTimeMillis() < end) {
				// Create a url with a query string to get the next test to execute
				// on this client
				URL url = new URL("http://cosc4345-team6.com/master/getNextTest.php?action=getTest&sut=" + sut);
				// Open the connection to the web server
				URLConnection sock = url.openConnection();
				// Create a BufferedReader object to read the return results
				BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				// Initialize all variables
				while ((result = reader.readLine()) != null) {
					result = result.replace("\"", "");
					script = "python3 " + result;
				}
				//System.out.println(script);
				// gets scriptName for sending in later
				scriptName = script.replaceAll("python3 ", "");
				if (scriptName.contains("SUT ID not in database")) {
					System.out.println("SUT is invalid, please try again!");
					break;
				} else if (scriptName.contains("Cannot connect to database")) {
					System.out.println("Cannot connect to database");
					break;
				} else if (scriptName.contains("No results from database")) {
					System.out.println("Failed to get results from database");
					break;
				} else if (scriptName.contains("Over max daily frequency")) {
					if(counter>20) {
						break;
					}
					counter++;
				} else {
					runTest(script, scriptName, sut, sutString);
				}
				TimeUnit.SECONDS.sleep(wait);
			}
			System.out.println("Thank You for using Struix!");
			System.out.println("Have a good day!");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			System.out.println(e);
			throw new RuntimeException(e);

		}
	}

	PrintStream out = System.out;

	public static void runTest(String script, String scriptName, int sut, String sutString) throws IOException {

		String pythonResults = null;
		String pythonErrors = null;
		// Read the return results and create a string with the python
		// command to execute

		// date formater for how it is in db
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSSS");
		// gets start time
		Calendar getStartTime = Calendar.getInstance();
		String startTime = df.format(getStartTime.getTime());
		// Now spawn another process to execute the Python script
		Process p = Runtime.getRuntime().exec(script);
		// stdInput gets python results
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		// stdError gets any errors if there are any
		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		Calendar getEndTime = Calendar.getInstance();
		String endTime = df.format(getEndTime.getTime());
		// System.out.println("StartTime: " + startTime + " End Time: " + endTime);
		// We need to know if the test script succeeded or failed
		// Replace this code with something that checks the return value for
		// "SUCCESS"
		// For now, just print the return results
		// if no python errors than it was a success
		pythonResults = stdInput.readLine();
		pythonErrors = stdError.readLine();
		if (pythonErrors == null) {
			if (pythonResults.contains("SUCCESS")) {
				pythonResults = "SUCCESS";
				// to upper for db uniformness
				pythonErrors = "";
			}
		} else {
			// else it is a a failures
			pythonResults = "FAILURE";
		}
		// url string without params
		String stringUrl = "http://cosc4345-team6.com/master/putResults.php?";
		// charset for url sake to work
		String charset = java.nio.charset.StandardCharsets.UTF_8.name();
		// first param is putting results
		String action = "putResults";
		// encodes url into a get input that works
		String query = String.format("action=%s&result=%s&error=%s&startTime=%s&endTime=%s&scriptName=%s&sutID=%s",
				URLEncoder.encode(action, charset), URLEncoder.encode(pythonResults, charset),
				URLEncoder.encode(pythonErrors, charset), URLEncoder.encode(startTime, charset),
				URLEncoder.encode(endTime, charset), URLEncoder.encode(scriptName, charset),
				URLEncoder.encode(sutString, charset));
		// creates new url
		URL url = new URL(stringUrl + query);
		//System.out.println(url);
		// opens connection
		URLConnection sock = url.openConnection();
		// Create a BufferedReader object to send the return results
		BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		reader.close();
		System.out.println("Test: " + scriptName + " Completed");
	}
}
