package phohawkenics.validator;

public class Validator {
	protected boolean containsOnlyNumbers (String text) {
		if (text.matches("[0-9]+"))
			return true;
		else
			return false;
	}
	
	protected boolean containsOnlyAlphabetic (String text) {
		if (text.matches("^[\\p{L}0-9]*$"))
			return true;
		else
			return false;
	}
}
