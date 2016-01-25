package components.bag;

import java.util.List;

import components.Block;

public interface Bag {

	public List<Block> popBlocks(int a);
	public void returnBlocks(List<Block> b);
	public int size();
}
