package P2;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InitialMatrix {
	private List<List<Integer>> InitialMatrix; 

	public InitialMatrix(List<Integer> aggregratedShingleList, ArrayList<HashSet<Integer>> listofFileShingleSets) {
		this.InitialMatrix = new ArrayList<List<Integer>>();
		Debug.print(aggregratedShingleList.size());
		for(int i = 0; i < aggregratedShingleList.size(); i++) {
			InitialMatrix.add(new ArrayList<Integer>());
		}
		for(int shingleSetIndex = 0; shingleSetIndex < aggregratedShingleList.size(); shingleSetIndex++) {
			int fileId = 0;
			for(HashSet<Integer> fileShingleSet:listofFileShingleSets) {
				if(fileShingleSet.contains(aggregratedShingleList.get(shingleSetIndex))) {
					addFileToShingleRow(shingleSetIndex, fileId);
				}
				fileId++;
			}
		}
	}	
	public void addFileToShingleRow(int row, int fileId) {
		InitialMatrix.get(row).add(fileId);
	}
	
	public List<Integer> getShingleRow(int rowNum) {
		return InitialMatrix.get(rowNum);
	}
	public String toString() {
		String sol = "";
		int shingleSetIndex = 0;	
		for(List<Integer> fileList : InitialMatrix) {
			sol = sol + "shingleSetIndex: " + shingleSetIndex 
					+ " fileList " + fileList + "\n";
			shingleSetIndex ++;
		}
		return sol;
	}
}