import java.util.ArrayList;
import java.util.Scanner;

import messaging.MessageCenter;
import messaging.SpecializedMessageCenter;
import components.*;

public class Factory {
	private static Factory instance = new Factory();
	private Factory(){}

	public static Factory createFactory() {
		return instance;
	}

	/**
	 * Builds instance of a component based on a string.
	 *
	 * @param name name of the component
	 * @return instance of the specific component
	 */
	public Component getComponent (String name) {
		if (name.equals("ImageLoader"))
			return new ImageLoader();
		if (name.equals("ImageSaver"))
			return new ImageSaver();
		if (name.equals("Flash"))
			return new Flash();
		if (name.equals("Zoom"))
			return new Zoom();
		if (name.equals("RawPhoto"))
			return new RawPhoto();
		if (name.equals("NormalPhoto"))
			return new NormalPhoto();
		if (name.equals("Sepia"))
			return new Sepia();
		if (name.equals("BlackWhite"))
			return new BlackWhite();
		if (name.equals("Blur"))
			return new Blur();
		return null;
	}

	/**
	 * Creates a list of MessageCenters, subscribing their associated components.
	 * Uses information from configuration file in Simulation Manager.
	 *
	 * @param sc reads from configuration file
	 * @param centers list of MessageCenters
	 */
	public void createCentersList (Scanner sc, ArrayList <MessageCenter> centers) {

		int n = Integer.parseInt(sc.nextLine());

		for (int i = 0; i < n; i++) {
			String [] read = sc.nextLine().split(" ");

			MessageCenter mc = new SpecializedMessageCenter(read[0]);
			for (int j = 1; j < read.length; j++)
				mc.subscribe(getComponent(read[j]));

			centers.add(mc);
		}
	}

	/**
	 * Creates a network of MessageCenters by adding
	 * connected centers to the list of neighbors of each center.
	 * Uses information from configuration file in Simulation Manager.
	 *
	 * @param sc reads from configuration file
	 * @param centers list of MessageCenters
	 */
	public void createNetwork (Scanner sc, ArrayList <MessageCenter> centers) {

		for (MessageCenter c : centers) {
			String [] read = sc.nextLine().split(" ");

			for (int j = 1; j < read.length; j++)
				for (MessageCenter v : centers)
					if ( v.getName().equals(read[j]) ) {
						c.addConectedCenter(v);
						break;
					}
		}
	}

}
