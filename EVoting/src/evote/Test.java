package evote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Test {
	static STV stv = new STV();
	//static CountVotes cv = new CountVotes();
	//static ArrayList<Integer> preferences = new ArrayList<Integer>(Arrays.asList(1,2,3,4,4,3,2,1,2,4,3,1));
	//static ArrayList<Integer> tempPref = new ArrayList<Integer>(preferences);
	//static int numOfCand=4;
	
	//private static ArrayList<Integer[]> action = new ArrayList<Integer[]>();
	//static int[] intArray = new int[numOfCand];
	public static void main(String[] args) {
		stv.STVCalculate();
		
		
		//cv.recountTotalVotes();
		/*int x=cv.getNoOfCandidates();
		System.out.println(x);*/
		//cv.addToElected(3);
		//cv.redistribute(2,3);
		/*int x=0;
		System.out.println(tempPref);
		for(int i=0; i<tempPref.size()/numOfCand; i++){
			intArray[i]= Arrays.asList(tempPref.subList(x,x+numOfCand));
			action.add(Arrays.asList(tempPref.subList(x,x+numOfCand)));
			x+=numOfCand;
		}
		System.out.println(tempPref);*/
		/*System.out.println(tempPref);
		int x=3;
		int y=0;
		try{
			for(int i=0; i<tempPref.size()/numOfCand; i++){
				//System.out.println(i);
				for(int j=y; j<x; j++){
					System.out.println(j);
					tempPref.set(j,tempPref.get(j+1));
				}
				x+=numOfCand;
				y+=numOfCand;
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		/*
		System.out.println(tempPref);
		
		int x=3;
		int y=0;
		
		for(int i=0; i<tempPref.size()/numOfCand; i++){
			for(int j=y; j<x-1; j++){
				Collections.swap(tempPref, j+1, j);
				if(j%numOfCand==0){
					while(tempPref.get(j)==2||tempPref.get(j)==4){
						Collections.swap(tempPref, j+numOfCand-1, j);
						System.out.println(tempPref);
						Collections.swap(tempPref, j+1, j);
					}
				}
			}
			x+=numOfCand;
			y+=numOfCand;
		}
		System.out.println(tempPref);*/
	}
}