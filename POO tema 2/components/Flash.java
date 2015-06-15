package components;

import messaging.Message;
import messaging.MessageFlash;
import messaging.MessageImage;
import types.*;

/**
 * Component used for applying 'flash' effect on a picture.
 */
public class Flash extends Component {

	public Flash() {
		super(TaskType.FLASH);
	}

	/**
	 * Receives a message with an image and the type of the flash: auto/on/off
	 * and returns a message with the modified image.
	 */
	@Override
	public Message notify(Message message) {

		MessageFlash flash = (MessageFlash) message;

		MessageImage image = new MessageImage(null);

		// the dimensions of the new image are unchanged
		image.setHeight(flash.getHeight());
		image.setWidth(flash.getWidth());
		image.setPixels(flash.getPixels());

		// if flash type is OFF do not modify the photo
		if (flash.getType() == FlashType.OFF) {
			return image;
		}

		// if flash type is AUTO, calculate the average brightness
		// and decide if it has to be changed to type ON or not
		if (flash.getType() == FlashType.AUTO) {

			int L = 0;
			for(int i = 0; i < image.getHeight(); i++)
				for(int j = 0; j < image.getWidth(); j++)
					L += (int) Math.round ( 0.2126f*image.getPixels()[i][j][0] +
											0.7152f*image.getPixels()[i][j][1] +
											0.0722f*image.getPixels()[i][j][2] );

			L /= image.getHeight() * image.getWidth();

			if (L < 60)
				flash.setType(FlashType.ON);
		}

		// if flash type is ON add 50 to each color channel of each pixel
		if (flash.getType() == FlashType.ON)

			for(int i = 0; i < image.getHeight(); i++)
				for(int j = 0; j < image.getWidth(); j++)
					for(int k = 0; k < 3; k++) {

							image.getPixels()[i][j][k] += 50;

					// adjust the value of the color channel if it is too high
							if (image.getPixels()[i][j][k] > 255)
								image.getPixels()[i][j][k] = 255;
						}
		return image;
	}
}
