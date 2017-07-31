package evote;

import java.lang.*;
import java.util.*;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import java.sql.SQLException;

public class VoteCommand extends Command {

	@Override
	public String run(Map<String, String[]> parameters){
		List<BallotPaper> papers = new ArrayList<BallotPaper>();
		int i, counter = 1;
		if(parameters.containsKey("candidate_id")){
			for(String val : parameters.get("candidate_id")){
				if(counter > 5){ break; }
				if(Lib.isInteger(val)){
					i = Integer.parseInt(val);
					if(i > 0){
						// TODO: Check that 'i' is a candidate id.
						papers.add(new BallotPaper(0, counter++, i));
					}
				}
			}
			try{
				DAO.addBallotPaper(papers);
			}catch(SQLException e){
				return "Fail.";
			}
		}
		return "Votes added.";
	}
	
}