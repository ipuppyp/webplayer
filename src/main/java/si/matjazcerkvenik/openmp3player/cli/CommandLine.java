package si.matjazcerkvenik.openmp3player.cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.backend.OContext;
import si.matjazcerkvenik.openmp3player.backend.Utils;
import si.matjazcerkvenik.openmp3player.player.Mp3Player;
import si.matjazcerkvenik.openmp3player.player.SoundControl;

public class CommandLine extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	private Mp3Player mp3Player;
	
	private ServerSocket s;
	private Socket incoming;
	private boolean running = true;
	private static final String prompt = "> ";
	

	public CommandLine(Mp3Player player) {
		setName("CommandLine");
		this.mp3Player = player;
	}

	@Override
	public void run() {

		while (running) {

			try {
				s = new ServerSocket(Utils.TELNET_PORT);
				logger.info("CommandLine: socket opened");
				incoming = s.accept();
				logger.info("CommandLine: socket accept");
			} catch (IOException e) {
				logger.debug(
						"CommandLine: IOException while establishing socket", e);
			}

			try {

				InputStream inps = incoming.getInputStream();
				OutputStream outs = incoming.getOutputStream();

				Scanner in = new Scanner(inps);
				PrintWriter out = new PrintWriter(outs, true);

				out.println("*** OpenMp3Player " + OContext.version + " ***");
				out.print(prompt);
				out.flush();

				boolean done = false;
				while (!done && in.hasNextLine()) {
					out.flush();
					String line = in.nextLine().trim();
					logger.info("CommandLine: cmd: " + line);
					if (line.equalsIgnoreCase("exit")
							|| line.equalsIgnoreCase("close")) {

						done = true;

					} else if (line.equalsIgnoreCase("version")) {

						out.println(OContext.version);

					} else if (line.startsWith("play")) {
						
						String[] args = line.split(" ");
						if (args.length == 0) {
							mp3Player.play(0);
							out.println("Playing: " + mp3Player.getCurrentlyPlaying());
						} else if (args.length > 1) {
							
							for (int i = 1; i < args.length; i++) {
								
								if (args[i].equals("-i")) {
									int indx = 0;
									try {
										indx = Integer.parseInt(args[i+1].trim());
									} catch (NumberFormatException e) {
										out.println("Object not integer: " + args[i+1].trim());
									}
									mp3Player.play(indx);
									out.println("Playing: " + mp3Player.getCurrentlyPlaying());
									
								}
								
							}
							
						} else {
							mp3Player.play(0);
							out.println("Playing: " + mp3Player.getCurrentlyPlaying());
						}
						
						

					} else if (line.equalsIgnoreCase("stop")) {
						
						mp3Player.stop();
						out.println("Stopped");

					} else if (line.equalsIgnoreCase("next")) {
						
						mp3Player.next();
						out.println("Playing: " + mp3Player.getCurrentlyPlaying());

					} else if (line.equalsIgnoreCase("prev")) {
						
						mp3Player.prev();
						out.println("Playing: " + mp3Player.getCurrentlyPlaying());

					} else if (line.startsWith("repeat")) {
						
						String[] args = line.split(" ");
						if (args.length > 1) {
							
							if (args[1].equals("on")) {
								mp3Player.setRepeatOn(true);
							} else if (args[1].equals("off")) {
								mp3Player.setRepeatOn(false);
							}
						}
						
						out.println("Repeat is " + (mp3Player.isRepeatOn() ? "ON" : "OFF"));

					} else if (line.equalsIgnoreCase("help")) {
						
						printHelp(out);

					} else if (line.startsWith("vol")) {
						
						String[] args = line.split(" ");
						if (args.length == 0) {
							out.println("missing argument");
							out.println("  <i>       volume level (number 0-10)");
							out.println("  [+/-]     increase/decrease volume for 1");
						} else {
							
							if (args[1].equals("+")) {
								SoundControl.volumeUp();
								out.println("Set volume: " + SoundControl.CURRENT_VOLUME_LEVEL);
							} else if (args[1].equals("-")) {
								SoundControl.volumeDown();
								out.println("Set volume: " + SoundControl.CURRENT_VOLUME_LEVEL);
							} else {
								try {
									int i = Integer.parseInt(args[1]);
									if (i < 0 || i > 10) {
										out.println("value out of range [0-10]: '" + args[1] + "'");
									} else {
										SoundControl.setVolume(i);
									}
								} catch (NumberFormatException e) {
									out.println("invalid argument: '" + args[1] + "'");
								}
							}
							
							
							
						}

					} else if (line.startsWith("show")) {
						
						String[] args = line.split(" ");
						if (args.length == 0) {
							out.println("missing argument");
							out.println("  -p     playlist");
							out.println("  -s     song");
							out.println("  -v     volume level");
						} else if (args.length > 1) {
							
							if (args[1].equals("-p")) {
								out.println("Current playlist: " + mp3Player.getActivePlaylist());
							} else if (args[1].equals("-s")) {
								out.println("Currently playing: " + mp3Player.getCurrentlyPlaying());
							} else if (args[1].equals("-v")) {
								out.println("Current volume level: " + SoundControl.CURRENT_VOLUME_LEVEL);
							} else {
								out.println("invalid argument: '" + args[1] + "'");
							}
							
							
							
						} else {
							// do nothing
						}

					} else if (line.length() == 0) {
						// do nothing
					} else {
						out.println("Unknown command! Use 'help'");
					}

					out.print(prompt);
					out.flush();

				}

			} catch (IOException e) {
				logger.error("CommandLine: IOException while listening", e);
			}

			try {
				incoming.close();
				s.close();
				logger.info("CommandLine: socket closed");
			} catch (IOException e) {
				logger.error("CommandLine: IOException while closing", e);
			}

		}

	}
	
	private void printHelp(PrintWriter out) {
		out.println("Usage:");
		out.println("  play        - start playing");
		out.println("    -n <i>       - play i-th song");
		out.println("  stop        - stop playing");
		out.println("  next        - play next");
		out.println("  prev        - play previous");
		out.println("  repeat      - repeat song");
		out.println("    [on/off]  - playlist");
		out.println("  show        - show information about:");
		out.println("    -p           - playlist");
		out.println("    -s           - song");
		out.println("    -v           - volume level");
		out.println("  vol         - set volume:");
		out.println("    <i>          - volume level (number in range 0 - 10)");
		out.println("    [+/-]        - increase/decrease volume for 1");
		out.println("  help        - you are looking it");
		out.println("  version     - show version");
	}

}
