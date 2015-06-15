package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

/**
 * Component used for turning a photo upside-down.
 */
public class RawPhoto extends Component {

	public RawPhoto() {
		super(TaskType.RAW_PHOTO);
	}

	/**
	 * Receives a message with an image and returns
	 * a message with the image turned upside-down.
	 */
	@Override
	public Message notify(Message message) {

		MessageImage raw = (MessageImage) message;

		for(int i = 0; i < raw.getHeight()/2; i++)
			for(int j = 0; j < raw.getWidth(); j++)
				for(int k = 0; k < 3; k++) {
					int aux = raw.getPixels()[i][j][k];
					raw.getPixels()[i][j][k] = raw.getPixels()[raw.getHeight() - i - 1][j][k];
					raw.getPixels()[raw.getHeight() - i - 1][j][k] = aux;
				}
		return raw;
	}
}
