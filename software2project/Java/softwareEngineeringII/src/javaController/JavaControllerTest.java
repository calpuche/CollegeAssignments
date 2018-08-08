package javaController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

class JavaControllerTest {
	@Test
	void testInvalidSUTID() throws IOException {
		URL url = new URL("http://cosc4345-team6.com/master/getNextTest.php?action=getTest&sut=22");
		// Open the connection to the web server
		URLConnection sock = url.openConnection();
		// Create a BufferedReader object to read the return results
		BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		String result;
		String script = null;
		while ((result = reader.readLine()) != null) {
			result = result.replace("\"", "");
			script = "python3 " + result;
		}
		boolean testResult = script.contains("ERROR, SUT ID not in database");
		assert (testResult);
	}
	
	@Test
	void testInvalidPythonResult() throws IOException {
		
		/// not done needs to finish
		// url string without params
				String result = null;
				String script = null;
				String scriptName = null;
				String stringUrl = "http://cosc4345-team6.com/master/putResults.php?";
				// charset for url sake to work
				String charset = java.nio.charset.StandardCharsets.UTF_8.name();
				// first param is putting results
				String action = "putResults";
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
				// gets start time
				Calendar getStartTime = Calendar.getInstance();
				String startTime = df.format(getStartTime.getTime());
				String endtime = df.format(getStartTime.getTime());
				// encodes url into a get input that works
				String query = String.format("action=%s&result=%s&error=%s&startTime=%s&endTime=%s&scriptName=%s&sutID=%s",
						URLEncoder.encode(action, charset), 
						URLEncoder.encode("hello world", charset),
						URLEncoder.encode("", charset), 
						URLEncoder.encode(startTime, charset),
						URLEncoder.encode(endtime, charset), 
						URLEncoder.encode("CPUTest.py", charset),
						URLEncoder.encode("5", charset));
				// creates new url
				URL url = new URL(stringUrl + query);
				//System.out.println(url);
				// System.out.println(url);
				// opens connection
				URLConnection sock = url.openConnection();
				// Create a BufferedReader object to send the return results
				BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				//reader.close();
				while ((result = reader.readLine()) != null) {
					result = result.replace("\"", "");
					script = "python3 " + result;
				}
				//System.out.println(script);
				// gets scriptName for sending in later
				scriptName = script.replaceAll("python3 ", "");
				//System.out.println(scriptName);
				assert(scriptName.contains("Invalid result"));
				
	}
	@Test
	void testInvalidScript() throws IOException {
		
		/// not done needs to finish
		// url string without params
				String result = null;
				String script = null;
				String scriptName = null;
				String stringUrl = "http://cosc4345-team6.com/master/putResults.php?";
				// charset for url sake to work
				String charset = java.nio.charset.StandardCharsets.UTF_8.name();
				// first param is putting results
				String action = "putResults";
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
				// gets start time
				Calendar getStartTime = Calendar.getInstance();
				String startTime = df.format(getStartTime.getTime());
				String endtime = df.format(getStartTime.getTime());
				// encodes url into a get input that works
				String query = String.format("action=%s&result=%s&error=%s&startTime=%s&endTime=%s&scriptName=%s&sutID=%s",
						URLEncoder.encode(action, charset), 
						URLEncoder.encode("SUCCESS", charset),
						URLEncoder.encode("", charset), 
						URLEncoder.encode(startTime, charset),
						URLEncoder.encode(endtime, charset), 
						URLEncoder.encode("fdsafds.py", charset),
						URLEncoder.encode("5", charset));
				// creates new url
				URL url = new URL(stringUrl + query);
				//System.out.println(url);
				// opens connection
				URLConnection sock = url.openConnection();
				// Create a BufferedReader object to send the return results
				BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				//reader.close();
				while ((result = reader.readLine()) != null) {
					result = result.replace("\"", "");
					script = "python3 " + result;
				}
				//System.out.println(script);
				// gets scriptName for sending in later
				scriptName = script.replaceAll("python3 ", "");
				//System.out.println(scriptName);
				assert(scriptName.contains("ERROR, Test ID not in database"));	
	}
	@Test
	void testPutInvalidSUTID() throws IOException {
		
		/// not done needs to finish
		// url string without params
				String result = null;
				String script = null;
				String scriptName = null;
				String stringUrl = "http://cosc4345-team6.com/master/putResults.php?";
				// charset for url sake to work
				String charset = java.nio.charset.StandardCharsets.UTF_8.name();
				// first param is putting results
				String action = "putResults";
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
				// gets start time
				Calendar getStartTime = Calendar.getInstance();
				String startTime = df.format(getStartTime.getTime());
				String endtime = df.format(getStartTime.getTime());
				// encodes url into a get input that works
				String query = String.format("action=%s&result=%s&error=%s&startTime=%s&endTime=%s&scriptName=%s&sutID=%s",
						URLEncoder.encode(action, charset), 
						URLEncoder.encode("SUCCESS", charset),
						URLEncoder.encode("", charset), 
						URLEncoder.encode(startTime, charset),
						URLEncoder.encode(endtime, charset), 
						URLEncoder.encode("CPUTest.py", charset),
						URLEncoder.encode("22", charset));
				// creates new url
				URL url = new URL(stringUrl + query);
				System.out.println(url);
				// opens connection
				URLConnection sock = url.openConnection();
				// Create a BufferedReader object to send the return results
				BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				//reader.close();
				while ((result = reader.readLine()) != null) {
					result = result.replace("\"", "");
					script = "python3 " + result;
				}
				//System.out.println(script);
				// gets scriptName for sending in later
				scriptName = script.replaceAll("python3 ", "");
				//System.out.println(scriptName);
				assert(scriptName.contains("SUT ID not in database"));	
	}
	

}
