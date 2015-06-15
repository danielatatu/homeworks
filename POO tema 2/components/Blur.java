package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

/**
 * Component used for applying 'blur' effect on a picture.
 */
public class Blur extends Component {

	public Blur() {
		super(TaskType.BLUR);
	}

	/**
	 * Receives a message with an image and returns
	 * a message with the blurred image.
	 */
	@Override
	public Message notify (Message message) {

		MessageImage blur = (MessageImage) message;

		// in case the photo has one single pixel
		// the new value of the color channels will be 0
		if ( blur.getHeight() == 1 && blur.getWidth() == 1 ) {
			for (int k = 0; k < 3; k++)
				blur.getPixels()[0][0][k] = 0;
			return blur;
		}

		int[][][] pixels = new int [blur.getHeight()][blur.getWidth()][3];
		// save a copy of the pixels matrix before modifying it
		for (int i = 0; i < blur.getHeight(); i++)
			for (int j = 0; j < blur.getWidth(); j++)
				for (int k = 0; k < 3; k++)
					pixels[i][j][k] = blur.getPixels()[i][j][k];

		int[][][] aux;
		double red, green, blue;
		int n;

		// repeat the process 10 times
		for (int p = 0; p < 10; p++) {

			// save the new form of the pixels matrix each time
			aux = pixels;
			pixels = blur.getPixels();
			blur.setPixels(aux);

			for (int i = 0; i < blur.getHeight(); i++)
				for (int j = 0; j < blur.getWidth(); j++) {

					red = 0; green = 0; blue = 0; n = 0;

					// find the 'valid neighbors' of the current pixel
					for (int ii = i-1; ii <= i+1; ii++)
						for (int jj = j-1; jj <= j+1; jj++)
							if ( ii >= 0 && ii < blur.getHeight() && jj >= 0 &&
								jj < blur.getWidth() && (ii != i || jj != j) ) {

								// calculate the sum of their color channels values
								red += pixels[ii][jj][0];
								green += pixels[ii][jj][1];
								blue += pixels[ii][jj][2];

								n++; // increment 'valid neighbors' number
							}
					// new values of the current pixel`s color channels
					blur.getPixels()[i][j][0] = (int) Math.round (red / n);
					blur.getPixels()[i][j][1] = (int) Math.round (green / n);
					blur.getPixels()[i][j][2] = (int) Math.round (blue / n);
				}
		}
		return blur;
	}
}
