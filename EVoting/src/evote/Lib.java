package evote;

public class Lib {

	public static Boolean isInteger(String value){
		try {
			Integer.parseInt(value);
		}catch(NumberFormatException e){
			return false;
		} return true;
	}
}