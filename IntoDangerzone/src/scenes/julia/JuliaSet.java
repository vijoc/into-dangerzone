package scenes.julia;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import math.Complex;
import math.ComplexFunction;

public class JuliaSet {
	
	private Complex parameterC = new Complex(0.5f, 0.5f);
	private ComplexFunction fn;
	
	private int iterations = 10;
	
	private float translateX;
	private float translateY;
	
	private float shrink;
	private float shrinkv;
	
	private float realRange;
	private float imaginaryRange;
	
	public JuliaSet() {
		
		this.fn = new ComplexFunction() {
			public Complex function(Complex z) {
				return Complex.add(z.squared(), parameterC);
			}
		};
		
		this.realRange = realRange;
		this.imaginaryRange = imaginaryRange;
		
		this.translateX = realRange * 0.5f;
		this.translateY = imaginaryRange * 0.5f;
		
		this.shrink = realRange / 2;
		this.shrinkv = 2*shrink;
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
	
	public int calc(Complex z) {
		int result = -1;
		
		for(int i = 0; i < iterations; i++) {
			z = fn.function(z);
			
			if(Math.sqrt(z.squaredModule()) < 2) {
				result = i;
			}
		}
		
		return result;
	}
}
