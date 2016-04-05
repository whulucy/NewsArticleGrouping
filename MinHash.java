package P2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
public class MinHash {
	public  static MinSignatureMatrix buildMinMatrix(List<HashFunc> hashFuncList, List<Integer> aggregratedIntegerList, 
		ArrayList<HashSet<Integer>>listofFileShingleSets, InitialMatrix initialMatrix) {
	    MinSignatureMatrix minMatrix = new MinSignatureMatrix(); //new the result first because it is an object	
		int numberOfShingles = aggregratedIntegerList.size();
		int numberOfHashFuncs = hashFuncList.size();
		int numberOfFiles = listofFileShingleSets.size();	
	    minMatrix.setInitialValue(numberOfHashFuncs, numberOfFiles); // the two-dimensional matrix		
	    int hashFuncCtr = 0;

		for (HashFunc hashFunc: hashFuncList){
			for(int Row = 0; Row < numberOfShingles; Row++){
				for (int Col = 0; Col < numberOfFiles; ++Col){
					int newValue = Math.abs(hashFunc.calculateHashFunc(Row));
					if (initialMatrix.getShingleRow(Row).contains(Col)){						
						//update the cell value if the new hashValue is smaller
						if (newValue < minMatrix.getMatrixValue(hashFuncCtr, Col)){
							minMatrix.setMatrixValue(hashFuncCtr, Col, newValue);
						}
					}
				}			
			}
			hashFuncCtr++;
		}
		return minMatrix;
	}
}



