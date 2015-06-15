package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

/**
 * Component used for applying 'sepia' effect on a picture.
 */
public class Sepia extends Component {

	public Sepia() {
		super(TaskType.SEPIA);
	}

	/**
	 * Receives a message with an image and returns
	 * a message with the sepia tone image.
	 */
	@Override
	public Message notify(Message message) {

		MessageImage sepia = (MessageImage) message;

		for(int i = 0; i < sepia.getHeight(); i++)
			for(int j = 0; j < sepia.getWidth(); j++) {

				// initial value of red channel of the pixel
				int inputRed = sepia.getPixels()[i][j][0];
				// initial value of green channel of the pixel
				int inputGreen = sepia.getPixels()[i][j][1];
				// initial value of blue channel of the pixel
				int inputBlue = sepia.getPixels()[i][j][2];

				// new value of red channel of the pixel
				int sp = (int) Math.round( (inputRed * 0.393) + (inputGreen * 0.769) + (inputBlue * 0.189) );
				if (sp > 255) // adjust the value of the red channel if it is too high
					sp = 255;
				sepia.getPixels()[i][j][0] = sp;

				// new value of green channel of the pixel
				sp = (int) Math.round( (inputRed * 0.349) + (inputGreen * 0.686) + (inputBlue * 0.168) );
				if (sp > 255) // adjust the value of the green channel if it is too high
					sp = 255;
				sepia.getPixels()[i][j][1] = sp;

				// new value of blue channel of the pixel
				sp = (int) Math.round( (inputRed * 0.272) + (inputGreen * 0.534) + (inputBlue * 0.131) );
				if (sp > 255) // adjust the value of the blue channel if it is too high
					sp = 255;
				sepia.getPixels()[i][j][2] = sp;
			}
		return sepia;
	}
}
