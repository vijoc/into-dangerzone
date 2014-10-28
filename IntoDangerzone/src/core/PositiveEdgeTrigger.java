package core;

public class PositiveEdgeTrigger implements InputProvider<Boolean> {

	private InputProvider<Boolean> provider;
	boolean prevValue;
	
	public PositiveEdgeTrigger(InputProvider<Boolean> provider) {
		this.provider = provider;
	}
	
	@Override
	public Boolean readInput() {
		boolean returnValue = false;
		boolean newValue = provider.readInput();
		
		if(newValue && !prevValue) returnValue = true;
		prevValue = newValue;
		
		return returnValue;
	}

}
