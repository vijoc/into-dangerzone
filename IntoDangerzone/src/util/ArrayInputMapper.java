package util;
public class ArrayInputMapper {

	/**
	 * Map given input to given output. 
	 * 
	 * If input has more elements than output, then the values are mapped so 
	 * that on average each output maps to the average of (input.length / 
	 * output.length) values.
	 * 
	 * If output has more elements than input, then the values are mapped so
	 * that each input value is used on average (output.length / input.length)
	 * output values.
	 * @param input
	 * @param output
	 */
	public static void map(float[] input, float[] output) {
		// TODO We need to verify this actually works.
		// In case of more particles than input
		if(input.length < output.length) {
			float sizeRatio = output.length / input.length;
			float accumulator = 0.0f;
			
			for(int i = 0; i < input.length; i++) {
				accumulator += sizeRatio;
				for(int j = 0; j < accumulator - 1; j++) {
					output[j] = input[i];
				}
				accumulator -= (int) accumulator;
			}
		}
		// In case of more input than particles
		else {
			float sizeRatio = ((float) input.length) / output.length;
			float accumulator = 0.0f;
			
			for(int i = 0; i < output.length; i++) {
				float sum = 0;
				accumulator += sizeRatio;
				
				for(int j = 0; j < accumulator-1; j++) {
					sum = sum + input[i+j];
				}
				output[i] = sum / (int) accumulator;
				accumulator -= (int) accumulator;
			}
		}
	}
}
