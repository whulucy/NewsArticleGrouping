package P2;
import java.util.HashSet;

public class SimilarityVarify {
	public static double test(HashSet<Integer> setOne, HashSet<Integer> setTwo){
		double result = 0.0;
		HashSet<Integer> combined = AggregateHashSet.combine(setOne, setTwo);
		int unionSize = combined.size();	
		int count = 0;
		for (Integer value : setOne){
			if(setTwo.contains(value))
			count++;
		}
		result = count / (unionSize * 1.0);
		return result;
	}

}
