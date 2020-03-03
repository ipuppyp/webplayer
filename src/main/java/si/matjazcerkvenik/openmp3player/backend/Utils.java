package si.matjazcerkvenik.openmp3player.backend;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.player.SoundControl;

public class Utils {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	private static Properties props = null;
	
	public static final String PROPERTY_PLAYER_DELAY = "player.delay";
	public static final String PROPERTY_VOLUME_DEFAULT = "player.volume.default";
	public static final String PROPERTY_VOLUME_CUSTOM_SCRIPT = "player.volume.custom.script";
	public static final String PROPERTY_TELNET_ENABLED = "telnet.enabled";
	public static final String PROPERTY_TELNET_PORT = "telnet.port";
	
	public static int PLAYER_DELAY = 5;
	public static String VOLUME_CUSTOM_SCRIPT = null;
	public static boolean TELNET_ENABLED = true;
	public static int TELNET_PORT = 4444;
	
	public static final String datePattern = "yyyy/MM/dd H:mm:ss";
	
	
	public static void init() {
		loadProperties();
		readVersion();
	}

	/**
	 * Read version.txt.
	 */
	public static void readVersion() {

		try {

//			FileInputStream fis = new FileInputStream(OContext.CFG_DIR + "/version.txt");
//
//			DataInputStream dis = new DataInputStream(fis);
//			BufferedReader br = new BufferedReader(new InputStreamReader(dis));

			//OContext.version = br.readLine();
			OContext.version = "1.0";
//			dis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Return type of operating system: WINDOWS, OSX, LINUX
	 * @return
	 */
	public static OperatingSystem getOsType() {
		String os = System.getProperty("os.name");
		
		if (os.equalsIgnoreCase("Mac os X")) {
			return OperatingSystem.OSX;
		} else if (os.equalsIgnoreCase("Linux")) {
			return OperatingSystem.LINUX;
		} else if (os.contains("Windows")) {
			return OperatingSystem.WINDOWS;
		}
		
		return OperatingSystem.OTHER;
	}

	/**
	 * Read openmp3player.properties and load parameters.
	 * @return properties
	 */
	private static Properties loadProperties() {
		
		if (props == null) {
			
			props = new Properties();
			
			try {

				props.load(new FileInputStream(OContext.CFG_DIR + "/openmp3player.properties"));

				PLAYER_DELAY = parseInt(props.getProperty(PROPERTY_PLAYER_DELAY), 5);
				SoundControl.CURRENT_VOLUME_LEVEL = parseInt(props.getProperty(PROPERTY_VOLUME_DEFAULT), 1);
				if (props.getProperty(PROPERTY_VOLUME_CUSTOM_SCRIPT) != null
						&& props.getProperty(PROPERTY_VOLUME_CUSTOM_SCRIPT).length() > 0) {
					VOLUME_CUSTOM_SCRIPT = props.getProperty(PROPERTY_VOLUME_CUSTOM_SCRIPT);
				}
				TELNET_ENABLED = parseBool(props.getProperty(PROPERTY_TELNET_ENABLED), true);
				TELNET_PORT = parseInt(props.getProperty(PROPERTY_TELNET_PORT), 4444);

			}

			catch (IOException e) {
				e.printStackTrace();
			}

		}
		return props;
	}
	
	// not used anywhere
	public static void writeProperties() {
		try {
		    props.store(new FileOutputStream("src/my/project/properties/test.properties"), null);
		} catch (IOException e) {
			logger.error("Utils:writeProperties(): error writing properties", e);
		}
	}
	

	
	/**
	 * Parse String value to integer. If fails, return default value.
	 * @param val
	 * @param defaultValue
	 * @return int
	 */
	private static int parseInt(String val, int defaultValue) {
		
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
		
	}
	
	/**
	 * Parse String value to boolean. If fails, return default value.
	 * @param val
	 * @param defaultValue
	 * @return boolean
	 */
	private static boolean parseBool(String val, boolean defaultValue) {
		
		try {
			return Boolean.parseBoolean(val);
		} catch (Exception e) {
			return defaultValue;
		}
		
	}
	
	
	
	/**
	 * Change permissions of .sh scripts in config directory to a+x.
	 */
	@Deprecated
	public static void changePermissions() {
		
		if (getOsType().equals("WINDOWS")) {
			return;
		}
		
		File dir = new File(OContext.CFG_DIR);
		File[] files = dir.listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getAbsolutePath().endsWith(".sh");
			}
		});
		
		for (int i = 0; i < files.length; i++) {
			logger.info("Utils:changePermissions(): " + files[i].getAbsolutePath());	
			String[] cmd = {"chmod", "a+x", files[i].getAbsolutePath()};
			runConsoleCommand(cmd);
		}
		
	}
	
	public static void runScript(String script, String[] args) {
		
		String[] command = new String[args.length + 1];
		command[0] = OContext.CFG_DIR + "/" + script;
		
		for (int i = 0; i < args.length; i++) {
			command[i+1] = args[i];
		}
		
		runConsoleCommand(command);
		
	}
	
	/**
	 * Run command in terminal
	 * @param command
	 */
	public static void runConsoleCommand(String[] command) {
		
		String cmdStr = "";
		for (int i = 0; i < command.length; i++) {
			cmdStr += command[i] + " ";
		}
		logger.info("Utils:runConsoleCommand(): " + cmdStr.trim());

	    Runtime rt = Runtime.getRuntime();
	    try {
	      Process p = rt.exec(command);

	      String s;

	      BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	      BufferedReader errbr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	      while ((s = br.readLine()) != null) {
	    	  logger.info("Utils:runConsoleCommand(): response : " + s);
	      }
	      while ((s = errbr.readLine()) != null) {
	    	  logger.warn("Utils:runConsoleCommand(): errResponse : " + s);
	      }

	      // wait for ending command
	      try {
	        p.waitFor();
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    
	  }
	
	/**
	 * Return IP address of the server, where OpenMp3Player is running.
	 * @return ip
	 */
	public static String getLocalIp() {
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error("Utils:getLocalIp(): ", e);
		}
		return "unknown host";
	}
	
	
	/**
	 * Get current date and time as formatted string
	 * @return current date and time
	 */
	public static String getNow() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		return sdf.format(d);
	}
	
	public static Date stringToDate(String date) {
		DateFormat df = new SimpleDateFormat(datePattern);
		Date d = new Date();
		try {
			d = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

}
