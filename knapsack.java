import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class knapsack {

	
	public static void main(String[] args){
		
		
		
		
		
		//TODO: switch from unbounded to bounded by N.
		// TODO: Write a testing function for all the programs (run each 100 times and average)
		int [][] values = new int [100][3]; 
		int [][] weights= new int [100][3]; 
		
		int [] tests = {10,20,30,100,500,1000};
		long startTime;	
		long endTime;	
		long average=0;
		long [] total =new long [tests.length];

		
		
		
		for(int i=0; i< 100; i++){
			values[i] = generateValues(3);
			weights[i] = generateValues(3);
		}
		
		//coreBruteForceN(values[0], weights[0], 200);
		//printout(values[0]);
		//printout(weights[0]);
		int [] A = {7,5,5,4};//weights
		int [] B = {49,30,25,24};//values
		int [] C = {3,2,1,3};// amount of each
		System.out.println("CoreBruteForce:");
		coreBruteForceN(A,B,C, 10);
		System.out.println("CoreDynamic:");
		coreDynamic(A,B, 10);
		//coreDynamic(values[0], weights[0], 50);
		System.out.println("CompletionDynamic:");
		completionDynamic(A,B, 10);
		System.out.println("CompletionGraph:");
		completionGraph(A,B, 10);
		/*
		//TODO: deal with the time thing
		
		for(int j=0; j< tests.length; j++){
			startTime = System.nanoTime();
			for(int i=0; i< tests[j]; i++){
				for(int k=0; k<100;k++){
				coreBruteForce(values[k], weights[k], 20);
				}
			}
			endTime = System.nanoTime();
			average =  (endTime-startTime)/tests[j]*100;
			total[j] = average;
		}
		printout(total);*/
	
	}
	private static int[] generateValues(int i) {
		int k = 0;
		int [] list = new int [i];
		while (k < i){
			//creates a random value between 0-100
			list[k] = (int) (Math.random()*100);
			k++;
		}
		return list;
	}
	
	
	/**
	 * 
	 * @param A string of instructions of the form "3 diamonds, 2 rubies, 15 emeralds"
	 * @param An array of weights (how valuable is each item)
	 * @param size, the number of elements allowed in the knapsack
	 * @return
	 */
	public static int[] coreBruteForceN( int[] weights, int [] values, int [] N, int size){
		//TODO: Update this for n values
		
		int[] result = new int[values.length];
		int [] A = new int [values.length];
		
		int j =0;
		while(j < values.length){
			A[j] =0;
			j++;
		}

		int bestValue = 0;
		int bestWeight = 0;
		for(int i =0; i< (Math.pow(2,(values.length))); i++){
			//printout(result);
			j = values.length-1;
			int tempWeight = 0;
			int tempValue = 0;
			while (A[j] !=0 && j>0){
				A[j] =0;
				j--;		
			}
			
			A[j] =1;
			//printout(A);
			for(int k=0; k < values.length; k++){
				if(A[k] ==1){
					tempWeight += weights[k];
					tempValue += values[k];
				}	
			}
			//System.out.println("weight "+tempWeight + " size "+ size +" | tempValue"+ tempValue +" best value"+ bestValue);
			if(tempValue > bestValue && tempWeight <= size){
				bestValue = tempValue;
				bestWeight = tempWeight;
				//System.out.println("update");
				//result = A;
				for(int v=0; v<A.length; v++){
					   result[v]=A[v];
					}
			}
			//printout(A);

		}
		//System.out.println("result:");
		printout(result);
		return result;
	}
	public void coreBruteForce3( int[] weights, int [] values, int size){
		int max=0;
		int[] result = new int[values.length];
	//try every combination...
	for(int diamonds =0; diamonds<= values[0]; diamonds++){
		for(int rubies=0; rubies <= values[1]; rubies ++){
			for(int emeralds =0; emeralds <= values[2]; emeralds ++){
				//System.out.println(diamonds+" "+ rubies+" "+emeralds);
				//if its the best so far save it as result and max
				int temp = (diamonds*weights[0])+(rubies*weights[1])+(emeralds*weights[2]);
				
				if (temp > max &&  (diamonds + rubies + emeralds) <= size){
					//System.out.println("updated");
					max = temp;
					result[0] = diamonds;
					result[1] = rubies;
					result[2] = emeralds;
				}
			}
			
		}
		
	}
	}
	
	
	/**
	 * 
	 * @param An array of the weights
	 * @param An array of the values
	 * @param W the threshold
	 * @returnan array x[1..n] such that x[i] = 1 if we choose to take object i, and 0 if we leave it.
	 */

	public static int [] coreDynamic(int [] weight, int [] value, int size){
		int [][] matrix = new int [value.length][size+1];

		for(int i =0; i<value.length; i++){//value.length
			for(int j=0; j< size+1 ; j++){//capacity
				//System.out.println(i+","+j);
				if(i == 0 ){
					if( j >= weight[0]) {matrix[i][j] = value[0];}
					else {matrix[i][j] = 0;
					}
					}
				else{
					if(j < weight[i] ){
						matrix[i][j] = matrix[i-1][j];
					} else{
						if(matrix[i-1][j] > (matrix[i-1][ (j - weight[i]) ]) + value[i]){
							matrix[i][j] = matrix[i-1][j];
						} else{
							matrix[i][j] = (matrix[i-1][ (j - weight[i]) ]) + value[i];
						}
					}
				}
			}
		}
		printout(matrix);
		
		
		//work back through from the bottom right corner as with Assignment 2

		int[] result = new int[value.length];
		int s = size;
		int n = value.length-1; 
		while( s>0){
			//System.out.println(n+","+s);
			if(n == 0){
				result[n] = 1;
				n--;
				s=0;
			} else{
				//int te = matrix[(n-1)][ s];//doesnt like 
				if(matrix[n][s] == matrix[(n-1)][ s]){
					result[n] =0;
					n--;
			} else{
				result[n] = 1;
				n--;
				s -= weight[n];
			}
			}
		}
		printout(result);
		return result;
	}

	
	public static int [] completionDynamic(int[] weight, int [] value, int size){
		ArrayList <Integer> weightsTemp = new ArrayList<Integer>();
		ArrayList <Integer> valuesTemp = new ArrayList<Integer>();
		int x =0;
		int max;
		int [] key = new int [value.length];
		while(x < value.length){
			//work out how many of that item is less than max size
			max = (int) Math.floor(size/weight[x]);
			key[x] = max;
			int i=1;
			while (i <= max ){
				weightsTemp.add(weight[x]);
				valuesTemp.add(value[x]);
				i++;
			}
			x++;
		}
		weight = convert(weightsTemp);
		value = convert(valuesTemp);
		
		int [][] matrix = new int [value.length][size+1];
		int [] result = new int[value.length];
		
		// do we just add rows for each vi that there is room for ?
		
				for(int i =0; i<value.length; i++){//value.length
					for(int j=0; j< size+1 ; j++){//capacity
						//System.out.println(i+","+j);
						if(i == 0 ){//first row...
							if( j >= weight[0]) {matrix[i][j] = value[0];}
							else {matrix[i][j] = 0;
							}
							}
						else{
							if(j < weight[i] ){
								matrix[i][j] = matrix[i-1][j];
							} else{
								if(matrix[i-1][j] > (matrix[i-1][ (j - weight[i]) ]) + value[i]){
									matrix[i][j] = matrix[i-1][j];
								} else{
									matrix[i][j] = (matrix[i-1][ (j - weight[i]) ]) + value[i];
								}
								//max(vi + matrix[i-1][j])
								/*
								int X = 0;
								int biggest =0;
								
								 int temp = ((X*value[i]) + matrix[i-1][(j-weight[i])] );
								while(temp <= (size+1) ){
									biggest = temp;
									X++;
									temp = ((X*value[i]) + matrix[i-1][(j-weight[i])] );
								}
								
								matrix[i][j] = biggest;*/
							}
						}
					}
				}
		printout(matrix);
		//work back through from the bottom right corner as with Assignment 2
		int s = size;
		int n = value.length-1; 
		while( s>0){
			//System.out.println(n+","+s);
			if(n == 0){
				result[n] = 1;
				n--;
				s=0;
			} else{
				//int te = matrix[(n-1)][ s];//doesnt like 
				if(matrix[n][s] == matrix[(n-1)][ s]){
					result[n] =0;
					n--;
			} else{
				result[n] = 1;
				n--;
				s -= weight[n];
			}
			}
		}
		int [] res = new int[key.length];
		//turn the result back into something we want:
		int i=0;
		int total =0;
		while(i < key.length){
			x =0;
			while(x < key[i]){
				//System.out.println(i +" "+x+" "+ result[i+x]);
				res[i] += result[total+x];
				x++;
				
			}
			total += x;
			i++;
		}
		printout(res);
		//printout(value);
		//printout(result);
		return res;
	}
	
	public static void completionGraph( int [] weight,  int [] value, int size){
		Queue <Nodes> q = new LinkedList <Nodes>();
		Nodes root = new Nodes(value, weight, size, new int [value.length],-1 , 0, 0);
		Nodes current;
		int MaxValue = root.totalValue;
		int [] best = new int[value.length];
		q.add(root);
		
		while(!q.isEmpty()){
			//System.out.println("polling queue of length "+ q.size());
			current = q.poll();//take the top one off
			//current.print();
			//for each sub problem...
			if(current.full == true){ // if its full compare it to teh max value
				//System.out.println("end of branch");
				if(current.totalValue > MaxValue){
					//System.out.println("update");
					//printout(current.count);
					MaxValue= current.totalValue;
					for(int v=0; v<best.length; v++){
						best[v] = current.count[v];
						}
				}
				
			} else{//otherwise branch so that each possible value you add is a new node.
			//take the set of values, make a new node for each value that you still have room to add, pass along the set.
				
				for(int i=0; i< value.length; i++){
					//printout(current.weight);
					if(current.weight[i] != -10){
						//System.out.println("adding to queue"+ weight[i]);
						int [] copy = new int[value.length];
						int [] copyw = new int[weight.length];
						for (int x =0; x< value.length; x++){
							copy[x] = current.count[x];
							copyw[x] = current.weight[x];
						}
						q.add(new Nodes( current.value, copyw, current.capacity, copy, i, current.totalValue + current.value[i], current.totalWeight+ current.weight[i]));
					}
				}
			}
			// keep in memory the path you took to get here (eg a list that keeps the count of the number of each value you have used).
			//run this until you have no more nodes on the queue.
			//when there is no more items in the values set then check if it is more than the best value, if it is then update it else throw it away.
			
		}
		
		printout(best);
	}
	
	

public static void challengeGraph(int[] weight, int [] value, int size){
	/*
	PriorityQueue<nodeType> PQ
	nodeType current, temp
	Initialize the root
	PQ.enqueue(the root)
	MaxValue = value(root)
	while(PQ is not empty)
	current = PQ.GetMax()
		if (current.nodeBound > MaxValue)
			Set the left child of the current node to include the next item
			
		if (the left child has value greater than MaxValue) then
		MaxValue = value (left child)
		Update Best Solution
		
		if (left child bound better than MaxValue)
			PQ.enqueue(left child)
			Set the right child of the current node not to include the next item
			if (right child bound better than MaxValue)
				PQ.enqueue(right child)
	return the best solution and it's maximum value
	
	
	if (current.nodeBound > MaxValue){
				//Set the left child of the current node to include the next item
				current.leftChild = new Nodes(value[current.level+1], weight[current.level+1], current.level+1, 
						current.totalValue+value[current.level+1], current.totalWeight+weight[current.level+1] );
			}
			if ((current.leftChild.value) > MaxValue) {//the left child has value greater than MaxValue
				MaxValue = current.leftChild.value; //(left child)
				//Update Best Solution
			}
			//look left
			
			
			if (current.leftChild.upperBound > MaxValue){
				q.add(current.leftChild);
				
			}
			//look right
			
			//Set the right child of the current node not to include the next item
			if (current.rightChild.upperBound > MaxValue)
				q.add(current.rightChild);
	
	*
	*
	*/
	}
	
	
	/*
	 * Helper methods:
	 */
	
	private static int[] convert(ArrayList<Integer> list) {
		int[] res = new int [list.size()];
		for (int i=0; i< list.size(); i++){
			res[i] = list.get(i);
			
		}
		return res;
	}
	private static void printout(int[] total) {
		String line = "";
			for (int j = 0; j<total.length; j++){
				line += " " +total[j];
			}
			System.out.println(line);
	}
	private static void printout(long[] total) {
		String line = "";
			for (int j = 0; j<total.length; j++){
				line += " " +total[j];
			}
			System.out.println(line);
	}
	
	private static void printout(int [][] table) {
		for (int i =0; i < table.length; i++){
			String line = "";
			for (int j = 0; j<table[0].length; j++){
				line += " " +table[i][j];
			}
			System.out.println(line);
		}
		
	}
}
