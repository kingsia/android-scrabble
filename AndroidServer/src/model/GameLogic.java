package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import util.WordObject;

/**
 * A logic class that describes all general game states and modifiers.
 * 
 * @author
 *
 */
public class GameLogic extends Logic{
	
	private Database db = null;
	
	public GameLogic(){
		super();
		db = getDatabase();	//	Get the inherited database.
	}
	
	public boolean checkWord(WordObject o){
		String query = "SELECT * FROM words WHERE word = '"+o.getWord()+"' LIMIT 1";
		boolean result = false;
		
		try {
			ResultSet set = db.execQuery(query);
			if(set.next()){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<String> getOpponentData(String username){
		List<String> opponents = new LinkedList<String>();
		String query = "SELECT * FROM game WHERE (host_name = '"+username+"' OR opponent_name = '"+username+"')";
		
		try {
			ResultSet set = db.execQuery(query);
			while(set.next()){
				if(set.getString("host_name").equalsIgnoreCase(username)){
					opponents.add(set.getString("opponent_name"));
				}
				else{
					opponents.add(set.getString("host_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opponents;
	}
}
