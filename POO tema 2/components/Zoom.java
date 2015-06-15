package components;

import messaging.Message;
import messaging.MessageImage;
import messaging.MessageZoom;
import types.TaskType;

/**
 * Component used for applying 'zoom' effect on a picture.
 */
public class Zoom extends Component {

	public Zoom() {
		super(TaskType.ZOOM);
	}

	/**
	 * Receives a message with an image and the coordinates of top-left
	 * and bottom-right pixels of the image`s part on which the zoom task
	 * will be applied and returns a message with the zoomed image.
	 */
	@Override
	public Message notify(Message message) {

		MessageZoom zoom = (MessageZoom) message;

		MessageImage image = new MessageImage(null);

		// set the dimensions of the new image
		image.setHeight (zoom.getCoord()[2] - zoom.getCoord()[0] + 1);
		image.setWidth (zoom.getCoord()[3] - zoom.getCoord()[1] + 1);

		int[][][] pixels = new int[image.getHeight()][image.getWidth()][3];

		// copy the pixels between the coordinates given into the new image
		for (int i = zoom.getCoord()[0]; i <= zoom.getCoord()[2]; i++)
			for (int j = zoom.getCoord()[1]; j <= zoom.getCoord()[3]; j++)
				for (int k = 0; k < 3; k++)
					pixels[i - zoom.getCoord()[0]][j - zoom.getCoord()[1]][k] = zoom.getPixels()[i][j][k];

		image.setPixels(pixels);

		return image;
	}
}
