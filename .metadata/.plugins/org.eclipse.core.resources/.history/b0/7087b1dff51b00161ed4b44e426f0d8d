package evote;

import java.lang.*;
import java.util.*;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class TestCommand extends Command {
	
	@Override
	public String run(Map<String, String[]> parameters){
		String word = "";
		if(parameters.containsKey("candidate_id")){
			for(String val : parameters.get("candidate_id")){
				word += val + ",";
			}
		}
		for(String name : parameters.keySet()){
			word += "\n" + name + ": ";
			
		}
		return "I are command." + word;
	}
}