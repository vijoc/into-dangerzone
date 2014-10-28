package scenes.julia;

import java.util.Map;
import java.util.HashMap;

import math.Complex;
import math.ComplexFunction;

public class JuliaSet {
	
	private Complex parameterC = new Complex(0.5f, 0.5f);
	private ComplexFunction fn;
	
	private float translateX;
	private float translateY;
	
	private float realRange;
	private float imaginaryRange;
	
	public JuliaSet(float realRange, float imaginaryRange) {
		this.fn = new ComplexFunction() {
			public Complex function(Complex z) {
				return Complex.add(z.squared(), parameterC);
			}
		};
		
		this.realRange = realRange;
		this.imaginaryRange = imaginaryRange;
		
		this.translateX = realRange * 0.5f;
		this.translateY = imaginaryRange * 0.5f;
	}
	
	public Complex getC() { return this.parameterC; }
	
	public void setC(Complex c) {
		if(!parameterC.equals(c)) {
			parameterC = c;
		}
	}
	
	public void setFunction(ComplexFunction fn) {
		this.fn = fn;
	}
	
	public Map<Integer, Integer> calc() {
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		
		for(float x = 0; x < realRange; x++) {
			for(float y = 0; y < imaginaryRange; y++) {
				
			}
		}
		
		return result;
	}
}
