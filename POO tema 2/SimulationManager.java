import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import messaging.*;
import types.*;

/**
 * @author Daniela Florentina Tatu, 325CA
 */
public class SimulationManager {

	private MessageCenter messageCenter;

	public SimulationManager(String networkConfigFile) throws Exception {
		this.messageCenter = buildNetwork(networkConfigFile);
	}

	/**
	 * Builds the network of message centers.
	 *
	 * @param networkConfigFile configuration file
	 * @return the first message center from the configuration file
	 * @throws Exception
	 */
	private MessageCenter buildNetwork(String networkConfigFile) throws Exception {

		Scanner sc = new Scanner(new File(networkConfigFile));

		ArrayList <MessageCenter> centers = new ArrayList <MessageCenter>();
		Factory.createFactory().createCentersList(sc, centers);
		Factory.createFactory().createNetwork(sc, centers);

		sc.close();
		return centers.get(0);
	}

	/**
	 * Reads the commands from stdin and uses the messageCenter to solve all the tasks.
	 * Stops when receives 'exit' command.
	 *
	 * @throws IOException
	 */
	public void start() throws IOException {

		Scanner in = new Scanner(System.in);
		String command;

		while ( ! (command = in.nextLine()).equals("exit") ) {

			String [] data = command.split(" ");

			MessageLoad load = new MessageLoad(TaskType.IMAGE_LOAD, data[0]);
			MessageImage image = (MessageImage)messageCenter.publish(load);

			image = Command.precapture(data[2], image, messageCenter);
			Command.capture(data[3], image, messageCenter);
			Command.postcapture(data[4], image, messageCenter);

			MessageSave save = new MessageSave (TaskType.IMAGE_SAVE, image.getPixels(),
										image.getWidth(), image.getHeight(), data[1]);
			MessageSuccess success = (MessageSuccess)messageCenter.publish(save);

			if (success == null)
				System.out.println("Task-ul nu a fost finalizat cu succes!");
		}

		in.close();
	}

	/**
	 * Main method
	 *
	 * @param args program arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage: java SimulationManager <network_config_file>");
			return;
		}
		SimulationManager simulationManager = new SimulationManager(args[0]);
		simulationManager.start();
	}
}
