package P2;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class AggregateHashSet {
	public static HashSet<Integer> merge(ArrayList<HashSet<Integer>> listOfFileShingleLists){	
		HashSet<Integer> result = new LinkedHashSet<Integer>();
		for (int i = 0; i < listOfFileShingleLists.size(); ++i){
			HashSet<Integer> sample = listOfFileShingleLists.get(i);
			result.addAll(sample);
		}
		return result;
	}
	
	public static HashSet<Integer> combine(HashSet<Integer> setOne, HashSet<Integer> setTwo){	
		HashSet<Integer> result = new LinkedHashSet<Integer>();
		result.addAll(setOne);
		result.addAll(setTwo);
		return result;
	}	
	
}
