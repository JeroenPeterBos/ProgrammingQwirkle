package players;

import java.util.ArrayList;
import java.util.List;

import components.Block;
import components.Board;
import logic.Game;
import logic.Move;
import logic.move.ExchangeMove;
import logic.move.PlayBlocksMove;

public class StupidStrategy implements Strategy {
	
	
	@Override
	public Move determineMove(Player p, Game g) {
		if (p.hasPossibleMove()) {
			List<String> pore = new ArrayList<String>();
			pore.add("p");
			pore.add("e");
			String choosepe = pore.get((int) Math.random() * pore.size());
			if (choosepe.equals("p")) {
				return p.getPossibleMove();				
			} else if (choosepe.equals("e")) {
				Move m = new ExchangeMove(p, g);
				m.addBlock(p.getHand().get(0));
				return m;
			}
			return null;
		} else {
			Move m = new ExchangeMove(p, g);
			m.addBlock(p.getHand().get(0));
			return m;
		}
		
	}

	@Override
	public String getName() {
		return "StupidStrategy";
	}



}
