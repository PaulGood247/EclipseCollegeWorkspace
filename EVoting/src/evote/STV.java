package evote;

import java.util.ArrayList;

public class STV {
	CountVotes cv 		= new CountVotes();
	float quota, 
		votesToBeRedistributed;
	int totalVotes 		= cv.getTotalVotes(),
		noOfCandidates 	= cv.getNoOfCandidates(),
		positions 		= 2,	// Amount of candidates that can be elected
		lowestCand 		= 1,	// Id of the candidate with the lowest votes
		highestCand     = 1;
	ArrayList<Integer> eliminated = new ArrayList<Integer>();
	
	public void STVCalculate(){
		cv.recountTotalVotes();
		quota = (float)totalVotes/noOfCandidates;
		
		getHighestCand();
		getLowestCand();
		do{
			while(cv.getCandVotes(highestCand)>quota){
				addToElected(highestCand);
				getHighestCand();
				if(positions<=0){
					break;
				}
			}
			getLowestCand();
			distributeVotes(lowestCand);
			if(positions<=0){
				break;
			}
		}while(positions>0);
		
		System.out.println("Finished");
		
		cv.disconnect();
	}

	public void getLowestCand(){
		for(int i = 1; i <= cv.getNoOfCandidates(); i++){
			if(!eliminated.contains(i) && cv.getCandVotes(i) < cv.getCandVotes(lowestCand)){
				lowestCand = i;
				System.out.println("Lowest Cand : " + lowestCand);
			}
		}
	}
	
	public void getHighestCand(){
		for(int i = 1; i <= cv.getNoOfCandidates(); i++){
			if(!eliminated.contains(i) && cv.getCandVotes(i) > cv.getCandVotes(highestCand)){
				highestCand = i;
				System.out.println("Highest Cand : " + highestCand);
			}
		}
	}
	
	public void shuffleVotes(int x){
		// take candidates votes away from quota and redistribute the overflow to ballot papers second choice
		votesToBeRedistributed = cv.getCandVotes(x)-quota;
		for(int i = 0; i < votesToBeRedistributed; i++){
			cv.redistribute(x, noOfCandidates);
		}
	}
	
	public void distributeVotes(int x){
		//redistribute all of this candidates votes as they are eliminated
		
		votesToBeRedistributed = cv.getCandVotes(x);
		for(int i = 0; i < votesToBeRedistributed; i++){
			cv.redistribute(x, noOfCandidates);
			System.out.println("Distributing votes");
		}
		eliminated.add(x);
	}
	
	public void addToElected(int x){
		if(positions > 0){
			//add them to the elected candidate table
			cv.addToElected(x);
			shuffleVotes(x);
			System.out.println("Add To elected : "+x);
		}
		positions--;
		eliminated.add(x);
	}
}