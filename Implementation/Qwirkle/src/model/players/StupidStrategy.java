package model.players;

import java.util.ArrayList;
import java.util.List;

import model.components.move.Move;
import model.components.move.Trade;
import model.game.Game;



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
				Move m = new Trade(p, g);
				m.addBlock(p.getHand().get(0));
				return m;
			}
			return null;
		} else {
			Move m = new Trade(p, g);
			m.addBlock(p.getHand().get(0));
			return m;
		}
		
	}

	@Override
	public String getName() {
		return "StupidStrategy";
	}



}
