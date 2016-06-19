package showHand;

import java.util.*;

public class ShowHandGame {
	
	public int round = 0;
	Poker poker = new Poker();
	int bet = 0;
	int clip = 0;
	int lastMax = 0;
	List<Player> players = new ArrayList<>();
	
	public void init(String[] playerList){
		poker.initPoker();
		initPlayer(playerList);
	}
	
	public void initPlayer(String[] playerStr){
		if((playerStr.length > 0) && playerStr.length <= 5){
			for(String str :playerStr){
				Player player = new Player(str);
				players.add(player);
			}
		}
	}
	
	public void newRound(){
		bet = 0;
		clip =0;
		poker.initPoker();
		for(Player p : players){
			p.initPlayer();
		}
		for(int i = 0; i < 1; i++){
			for(Player p : players){
				p.playerCard.getCards(poker.deliverCard());
			}
		}
		for(int i = 2; i < 5; i++){
			int quiteNum = 0;
			lastMax = Deliver(players, lastMax);
			for(Player p : players){
				System.out.println(p.getName() + "的牌为： ");
				p.showCards(p.getName());
			}
			if(players.get(lastMax).goon()){
				bet = players.get(lastMax).bet();
				clip += bet;
				for(int j = 1; j < players.size(); j++){
					int follower = (j + lastMax) % players.size();
					if(players.get(follower).isOn() && players.get(follower).isFollow(bet)){
						clip += bet;
					}else {
						players.get(follower).unFollow();
						quiteNum ++;
					}
				}
			
			}
			if(quiteNum == players.size() - 1){
				for(Player p : players){
					if(p.isOn()){
						p.win(clip);
					}
				}
				round++;
				return;
			}
			
		}
	}
			
		
	
	
	public void showResult(){
		System.out.println("总共进行了" + round + "局");
		for(Player p : players){
			System.out.println(p.getName() + "余额为： " + p.getAccount() + "; 胜利次数" + p.getWinTime());
		}
	}
	
	public int Deliver(List<Player> playerList, int index){
		int j = 0;
		int indexMax = 0;
		Card maxCard = new Card(0, 0);
		Card card;
		for(int i = index; i < index + playerList.size(); i++){
			j = i % playerList.size();
			if(playerList.get(j).isOn()){
				card = poker.deliverCard();
				playerList.get(i % playerList.size()).playerCard.getCards(card);
				if(card.compareTo(maxCard) > 0){
					maxCard = card;
					indexMax = j;
				}
			}
			
			//System.out.println(maxCard);
		}
		return indexMax;
	}


}
