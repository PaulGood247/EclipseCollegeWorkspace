package evote;

import java.util.*;


public abstract class Command {
	public abstract String run(Map<String, String[]> parameters);
}
