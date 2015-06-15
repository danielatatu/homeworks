package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

/**
 * Component used for applying 'black and white' effect on a picture.
 */
public class BlackWhite extends Component {

	public BlackWhite() {
		super(TaskType.BLACK_WHITE);
	}

	/**
	 * Receives a message with an image and returns
	 * a message with the image converted to grayscale.
	 */
	@Override
	public Message notify(Message message) {

		MessageImage black_white = (MessageImage) message;

		for(int i = 0; i < black_white.getHeight(); i++)
			for(int j = 0; j < black_white.getWidth(); j++) {

				// new value of the color channels of the pixel
				int bw = (int) Math.round( (black_white.getPixels()[i][j][0] * 0.3) +
											(black_white.getPixels()[i][j][1] * 0.59) +
											(black_white.getPixels()[i][j][2] * 0.11) );
				if (bw > 255) // adjust the value of the color channels if it is too high
					bw = 255;
				for (int k = 0; k < 3; k++)
					black_white.getPixels()[i][j][k] = bw;
			}
		return black_white;
	}
}
