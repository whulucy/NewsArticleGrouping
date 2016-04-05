package P2;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class P2 {
	final static int NUM_SHINGLE = 9;
	
	public static String parseLine(String raw_line) {
		int len;
		raw_line = raw_line.trim();
		len = raw_line.length();
		if (len == 0) return "";
	    if (raw_line.charAt(0) == '#') return "";
	    return raw_line;
	}
	
	public static void main(String[] args){
		/*
		 * put all the files in an ArrayList of String
		 */		
		ArrayList<String> fileFolder = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		try{
			/*
			 * read the text files
			 */
		    while ((line = in.readLine()) != null ){
		    	line = parseLine(line);
		    	if (line.equals("")) {
		    		continue;
		    	}
		    	if (line.charAt(0)=='#'){
		    		continue;
		    	}
		    	String[] lineParts = line.split("\\s+");
		    	for (String file : lineParts){
		    		if (!file.trim().isEmpty())
		    			fileFolder.add(file);	
		    	}
		    	
		    }
		} catch(IOException e ){
            e.printStackTrace();
        }
		/*  need to remove */
		for (int i = 0; i < fileFolder.size(); ++i){
			System.out.println(fileFolder.get(i));
			
		}
		
		if (fileFolder.size() == 0){
			System.out.println("Empty file, no valid txt files was provided");
			System.exit(-1);
		}
		
		/*
		 * I need an array of strings by the end
		 * each text file will be converted into a string 
		 * 
		 */
		ArrayList<String> fileString = new ArrayList<String>();
		
		for (int i = 0; i < fileFolder.size(); ++i){
			try{
			String sum = "";
			/*
			 * read each file line by line
			 */
			File file = new File(fileFolder.get(i)); 
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String temp = scanner.nextLine();
				sum+=temp;
			}
			fileString.add(sum);
			scanner.close();
			} catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < fileString.size(); ++i){
			Debug.print(fileString.get(i));
			Debug.print("---------------------------");
		}
		
		for (int i = 0; i < fileString.size(); ++i){
			 String temp = fileString.get(i).trim();
			 String temp1 = temp.replaceAll("[^a-zA-Z ]", "").toLowerCase(); // ignore numbers
			 String temp2 = temp1.replaceAll("[\\t\\n\\r]", " "); // change tab newline to whitespace
			 String temp3 = temp2.trim().replaceAll(" +", " "); //merge multiple whitespace into a single whitespace
			 fileString.set(i, temp3); 
		}
		 
		 Debug.print("the fileFolder size: " + fileFolder.size());
		 Debug.print("the fileString size: " + fileString.size());
		 

		 Debug.print("shingleList still follows the original file order");
		 
		 // use hashset to store the shingle sets for each txt article
		 // the ArrayList then stores a list of HashSet<String>	 
		 ArrayList<HashSet<String>> fileShingleList = new ArrayList<HashSet<String>>();
		 
		 for (int i = 0; i < fileString.size(); ++i){
			 // new the HashSet
			 HashSet<String> shingle = new HashSet<String> ();	 
			 String wholeString = fileString.get(i);
			 //add all the file Shingles to the HashSet
			 if (wholeString.length() >= NUM_SHINGLE){
				 for (int j = 0; j < wholeString.length() - NUM_SHINGLE + 1; ++j){
					 if (!shingle.contains(wholeString.substring(j, j + NUM_SHINGLE))){
						 shingle.add(wholeString.substring(j, j + NUM_SHINGLE));
					 } else {
						 System.out.println(wholeString.substring(j, j + NUM_SHINGLE));
					 }
				 }					 
				 fileShingleList.add(shingle);
			 }			 
		 }
		 
		 //need to create aggregrateShingleSet <Integer> and also listOfFileShingleSets List<set<Integer>>	
		 //convert the ArrayList<HashSet<String>> into ArrayList<HashSet<Integer>>
		 ArrayList<HashSet<Integer>> listofFileShingleSets = new ArrayList<HashSet<Integer>>(); 
		 
		 for (int j = 0; j < fileShingleList.size(); ++j){
			 HashSet<Integer> shingleHashSet = new HashSet<Integer>();
			 HashSet<String> StringSet = fileShingleList.get(j);
			 for (String string : StringSet){
				 shingleHashSet.add(string.hashCode()); // does hashCode always produce the same integer?
			 }
			listofFileShingleSets.add(shingleHashSet);			 
		 }
		 		 
		 HashSet<Integer> aggregratedSet = AggregateHashSet.merge(listofFileShingleSets);
		 
		 List<Integer> aggregratedShingleList = new ArrayList<Integer>(aggregratedSet);   //change the HashSet into ArrayList
		 
		 
		 //First step in constructing the signature matrix
		InitialMatrix originalMatrix = new InitialMatrix(aggregratedShingleList, listofFileShingleSets);
		
		Debug.print(originalMatrix);
		
		//stop here Feb 13, 2015
		 
		int numOfAllShingles = aggregratedShingleList.size();
		Debug.print("the size of numOfAllShingleSets is: " + numOfAllShingles);		
		
		HashFuncProducer hfp = new HashFuncProducer(numOfAllShingles);
		
		List<HashFunc> hashFuncList = new ArrayList<HashFunc>();	
		
		int numOfHashFuncsToProduce = (numOfAllShingles/15);
		HashFunc hashFunc = null;
		
		for(int i = 0; i < numOfHashFuncsToProduce; i++){
			hashFunc = hfp.getHashFunction();
			hashFuncList.add(hashFunc);
			hfp.generateNewHashFunc(); //to change the value of  parameters in the hashFuncProducer
		}
			
		//create the min-Hash using the generated hash functions
		MinSignatureMatrix minMatrix = MinHash.buildMinMatrix(hashFuncList, aggregratedShingleList, listofFileShingleSets, originalMatrix);
		

		LSHFilter lshFilter = new LSHFilter (minMatrix);

		Debug.print(lshFilter);
		
		//After dividing the matrix into band and rows, begin to hash each band into different rows
		
		List<PairSet> filePairSet = new ArrayList<PairSet>();
		
		for(int fromRow = 0, toRow = lshFilter.getrRows()-1; 
				toRow < minMatrix.getRows(); fromRow += lshFilter.getrRows(), toRow += lshFilter.getrRows()) {
			HashMap<Integer, List<Integer>> hashMap = new HashMap<Integer, List<Integer>>();
			int bucketHashCode = 0;
			Debug.print("The size of all the files are: " + listofFileShingleSets.size());
			for(int col = 0; col < listofFileShingleSets.size(); col++) {
				bucketHashCode = lshFilter.hashColumnToBucket(minMatrix.getColumnBand(fromRow, toRow, col));
				if(hashMap.get(bucketHashCode) == null) hashMap.put(bucketHashCode, new ArrayList<Integer>());
				hashMap.get(bucketHashCode).add(col);
			}
						
			//the Integer is the fileId from the fileFolder in the beginning
			for(Integer key:hashMap.keySet()) {
				List<Integer> fileSet = hashMap.get(key);
				if(fileSet.size() > 1) {
					Debug.print("fileSet: " + fileSet);
					for(int i = 0; i < fileSet.size(); i++) {
						for(int j = i + 1; j < fileSet.size(); j++) {
							int numberOne = fileSet.get(i);  //the fileID
							int numberTwo = fileSet.get(j);  //the fileID
							double similarity = 0.0;
							similarity = SimilarityVarify.test(listofFileShingleSets.get(numberOne), listofFileShingleSets.get(numberTwo));
							Debug.print("Pair: " + numberOne + " vs " + numberTwo + " " +similarity);
							int numberA;
							int numberB;
							if (numberOne < numberTwo){
								numberA = numberOne;
								numberB = numberTwo;								
							} else {
								numberA = numberTwo;
								numberB = numberOne;
							}							
							PairSet result = new PairSet(numberA, numberB);
							if (!filePairSet.contains(result)){
								filePairSet.add(result);
							}
						}
					}
				}
			}
		}
		
		if (filePairSet.size() == 0){
			System.out.println("There are no similar files from the input" + 
		                     "\nThe Similarity between all of them are lower than the threshold");
		}  		
			//new the QuickFindUF using the size of all the files in the input
		QuickFindUF uf = new QuickFindUF(fileFolder.size());
		for (PairSet set : filePairSet){
			int A = set.getNumberA();
			int B = set.getNumberB();
			if (uf.connected(A, B)) continue;
			uf.union(A, B);
			Debug.print("the new union of two files are: " + A + " - " + B);
			Debug.print("the total number of groups are :" + uf.count());
		}
		
			
			
		HashMap<Integer, ArrayList<Integer>> hashMapFile = new HashMap<Integer, ArrayList<Integer>>();
		int bucketNum = 0;
		for (int i = 0; i < fileFolder.size(); ++i ){
			 bucketNum = uf.find(i) % fileFolder.size();
			 if(hashMapFile.get(bucketNum) == null) hashMapFile.put(bucketNum, new ArrayList<Integer>());
				hashMapFile.get(bucketNum).add(i);
			 
		}
		
		

		System.out.println("There are in total " + uf.count() + " groups of articles from the input list");
		
		
//			int sizeOfHashMapFile = hashMapFile.size();
		//Debug.print("value of count :" + uf.count() + " the size of hashMapFile :" + hashMapFile.size());
		int groupCount = 0;
		for (int k = 0; k < fileFolder.size(); ++k){
			ArrayList<Integer> groupList = hashMapFile.get(k);
			if (groupList == null)  continue;
			//if(groupList.size() <2) continue;
			System.out.println("The Number #" + groupCount + " group include the following files: ");
			
			for (int m = 0; m < groupList.size(); ++m){
				System.out.print(fileFolder.get(groupList.get(m)) + ", ");
			}
			System.out.println();
		    groupCount++;
			
		}
			
		
		    //error: the fileId number has been mixed by the hashset or hashmap, therefore, it can't get the original doc files from the fileFolder		
    
	}

}
