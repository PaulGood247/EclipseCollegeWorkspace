package evote;

import java.lang.*;
import java.util.*;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import java.sql.SQLException;

import com.google.gson.Gson;

public class CandidateReadCommand extends Command {
	
	@Override
	public String run(Map<String, String[]> parameters){
		// TODO: Remove vote count from the candidate data.
		return (new Gson().toJson(DAO.getAllCandidates()));
	}
	
}