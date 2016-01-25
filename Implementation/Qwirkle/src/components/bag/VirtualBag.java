package components.bag;

import java.util.LinkedList;
import java.util.List;

import components.Block;

public class VirtualBag implements Bag{

	// ------------------------------- Instance Variables ------------------------------ //
	
		private int blocksLeft;
	
	// ------------------------------- Constructors ------------------------------------ //
	
		public VirtualBag(){
			this.blocksLeft = 108;
		}
		
	// ------------------------------- Commands ---------------------------------------- //
	
		public List<Block> popBlocks(int a){
			blocksLeft -= a;
			return null;
		}
		
		public void returnBlocks(List<Block> b){
			blocksLeft += b.size();
		}
		
	// ------------------------------- Queries ----------------------------------------- //

		public int size(){
			return blocksLeft;
		}
		
		public boolean isEmpty(){
			return size() <= 0;
		}
}
