import java.util.ArrayList;


public class Nodes {
	public int[] value;
	public int[] weight;
	public int level;
	public int totalValue;
	public int totalWeight;
	public int upperBound;
	public Nodes leftChild;
	public Nodes rightChild;
	public int[] count;
	public int capacity;
	public int valueIndex;
	public boolean full;
	
	/*
	public Nodes(int value,int weight, int capacity, int level, int totalValue, int totalWeight, int nextWeight, int nextValue){
		this.value= value;
		this.level = level;
		this.totalValue = totalValue;
		this.totalWeight = totalWeight;
		this.upperBound = totalValue + (capacity - totalWeight)*(nextWeight / nextValue);
	}
	*/
	
	
	public Nodes(int[] value, int[] weight, int capacity,  int [] count, int valueIndex, int totalValue, int totalWeight){//int level,
		this.totalValue = totalValue;
		this.totalWeight = totalWeight;
		ableToFit(value, weight,  capacity);
		this.valueIndex = valueIndex;
		this. count = count;
		if(valueIndex > -1){
			//System.out.println("adding to count");
		this.count[valueIndex] +=1;
		
		}
	}
	public void print(){
		System.out.println("----------------");
		System.out.println("tv" +totalValue +" tw "+ totalWeight );
		printout(value);
		printout(count);
		System.out.println("----------------");
	}


	private void ableToFit(int[] value, int[] weight, int size) {
		int filledup =0;
		
		for(int i=0; i< value.length; i++){
		//System.out.println( weight[i]);
			if(weight[i] != -10){
				if(totalWeight+weight[i] <= size ){//weight needs to be less than size
					//leave it in both
					//System.out.println("weight ok");
				} else{
					//System.out.println(i);
					//take it out of weights and values
					weight[i] = -10;
					//value.remove(i);
					filledup ++;

				}
			} else{
				filledup ++;
			}

		}
		//System.out.println("filled up"+filledup);
		if(filledup == value.length){full =true;}
		this.value = value;
		this.weight = weight;
		this.capacity = size;
		//System.out.println("end of ableto fit");
	}
	//for each sub problem...
	//take the set of values, make a new node for each value that you still have room to add, pass along the set.
	// keep in memory the path you took to get here (eg a list that keeps the count of the number of each value you have used).
	//run this until you have no more nodes on the queue.
	//when there is no more items in the values set then check if it is more than the best value, if it is then update it else throw it away.
	
	
	//to optimise this: take a greedy approach? only add the big things
	 
	private static void printout(int[] total) {
		String line = "";
			for (int j = 0; j<total.length; j++){
				line += " " +total[j];
			}
			System.out.println(line);
	}
}
