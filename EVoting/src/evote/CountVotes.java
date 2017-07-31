package evote;

public class CountVotes {

	//receive all votes from client and add them to database and then count each vote per candidate

	int candidateID;
		
	public CountVotes(){
		DAO.connect(null);
	}
	public int getTotalVotes(){
		return DAO.getTotalVoteCount();
	}
	
	public int getNoOfCandidates(){
		return DAO.getTotalNoOfCand();
	}
	
	public int getCandVotes(int x){
		return DAO.getACandidatesVotes(x);
	}
	
	public void addToElected(int x){
		DAO.addToElected(x);
	}
	
	public void redistribute(int x, int y){
		DAO.redistribute(x,y);
		recountTotalVotes();
	}
	
	public void recountTotalVotes(){
		DAO.recountCandTotalVotes();
	}
	public void disconnect(){
		DAO.disconnect();
	}
}
