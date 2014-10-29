package scenes.julia;

import math.Complex;
import math.ComplexFunction;

public class JuliaSet {
	
	private Complex parameterC = new Complex(0.5f, 0.5f);
	private ComplexFunction fn;
	
	private int iterations = 12;
	
	public JuliaSet() {
		
		this.fn = new ComplexFunction() {
			public Complex function(Complex z) {
				return Complex.add(z.squared(), parameterC);
			}
		};
	}
	
	public Complex getC() { return this.parameterC; }
	
	public void setC(Complex c) {
		if(!parameterC.equals(c)) {
			parameterC = c;
			fn = new ComplexFunction() {
				public Complex function(Complex z) {
					return Complex.add(z.squared(), parameterC);
				}
			};
		}
	}
	
	public void setCx(float cx) {
		setC(new Complex(cx, parameterC.y()));
	}
	
	public void setCy(float cy) {
		setC(new Complex(parameterC.x(), cy));
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
