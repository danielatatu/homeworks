import messaging.*;
import types.*;

/**
 * Processes the commands received in the 3 steps of simulation:
 * precapture, capture and postcapture.
 */
public class Command {

	private Command(){}

	/**
	 * Processes the command received in precapture and passes the tasks
	 * (flash and zoom) to the connected message center.
	 *
	 * @param image image that has to be modified
	 * @param mc connected message center
	 * @return modified image
	 */
	public static MessageImage precapture (String command, MessageImage image, MessageCenter mc) {
		String [] pre = command.split("pre\\(flash=|;|zoom=|,|\\)");

		MessageFlash flash = new MessageFlash (TaskType.FLASH, image.getPixels(),
							image.getWidth(), image.getHeight(), getFlashType(pre[1]) );
		image = (MessageImage)mc.publish(flash);

		if (pre.length > 3) { // if there is a 'zoom' command
			int [] coord = new int[4];
			coord[0] = Integer.parseInt(pre[4]);
			coord[1] = Integer.parseInt(pre[3]);
			coord[2] = Integer.parseInt(pre[6]);
			coord[3] = Integer.parseInt(pre[5]);

			MessageZoom zoom = new MessageZoom (TaskType.ZOOM, image.getPixels(),
									image.getWidth(), image.getHeight(), coord);
			image = (MessageImage)mc.publish(zoom);
		}
		return image;
	}

	/**
	 * Returns a FlashType based on a string.
	 *
	 * @param string type of the flash
	 */
	public static FlashType getFlashType (String string) {
		if (string.equals("on"))
			return FlashType.ON;
		if (string.equals("off"))
			return FlashType.OFF;
		if (string.equals("auto"))
			return FlashType.AUTO;
		return null;
	}

	/**
	 * Processes the command received in capture and passes the task
	 * (raw or normal photo) to the connected message center.
	 *
	 * @param image image that has to be modified
	 * @param mc connected message center
	 */
	public static void capture (String command, MessageImage image, MessageCenter mc) {
		image.generateId(); // generate another ID for this message before reusing it
		image.setTaskType(TaskType.RAW_PHOTO);
		// RAW_PHOTO task is sent even if the image type is normal
		image = (MessageImage)mc.publish(image);

		if ( command.split("photo\\(type=|\\)")[1].equals("normal") ) {
			image.generateId();
			image.setTaskType(TaskType.NORMAL_PHOTO);
			image = (MessageImage)mc.publish(image);
		}
	}

	/**
	 * Processes the command received in postcapture and passes the tasks
	 * (sepia, blur, black_white), if there are any tasks,
	 * to the connected message center.
	 *
	 * @param image image that has to be modified
	 * @param mc connected message center
	 */
	public static void postcapture (String command, MessageImage image, MessageCenter mc) {

		for ( String filter : command.split("\\(|;|\\)") ) {
			if ( ! filter.equals("post") ) {
				image.generateId(); // generate another ID for this message before reusing it
				image.setTaskType(getTaskType(filter));
				image = (MessageImage)mc.publish(image);
			}
		}
	}

	/**
	 * Returns a TaskType based on a string.
	 *
	 * @param string type of the task
	 */
	public static TaskType getTaskType (String string) {
		if (string.equals("sepia"))
			return TaskType.SEPIA;
		if (string.equals("blur"))
			return TaskType.BLUR;
		else if (string.equals("black_white"))
			return TaskType.BLACK_WHITE;
		return null;
	}
}
