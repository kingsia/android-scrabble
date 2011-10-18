package tests;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Database;
import model.GameModel;
import model.data.Board;

import org.junit.Before;
import org.junit.Test;

import util.WordObject;
import util.WordObject.Direction;


public class GameModelTest {
	
	GameModel gm = null;
	Database d = null;
	
	@Before
	public void setUp(){
		gm = new GameModel("Lisa", "Mike");
		d = gm.getDatabase();
	}
	
	@Test
	public void testStartGame() throws SQLException {
		assertTrue(gm.startGame());
	}
	
	@Test void testGenerateLetters(){
		boolean result = testGenerateLetters1() && testGenerateLetters2() && testGenerateLetters3();
		assertTrue(result);
	}
	
	@Test
	public boolean testGenerateLetters1(){
		gm.setLettersLeft(5);
		gm.generateLetters(4);
		boolean result = false;
		
		if(gm.getPlayer1().isTurn()){
			result = gm.getPlayer1().getNrLetters() == 7 && gm.getLettersLeft() == 5-4;
		}
		else if(gm.getPlayer2().isTurn()){
			result = gm.getPlayer2().getNrLetters() == 7 && gm.getLettersLeft() == 5-4;
		}
		return result;
	}
	
	@Test
	public boolean testGenerateLetters2(){
		gm.setLettersLeft(3);
		gm.generateLetters(4);
		boolean result = false;
		
		if(gm.getPlayer1().isTurn()){
			result = gm.getPlayer1().getNrLetters()-4+gm.getLettersLeft() == 7-4+3 && gm.getLettersLeft() == 0;
		}
		else if(gm.getPlayer2().isTurn()){
			result = gm.getPlayer2().getNrLetters()-4+gm.getLettersLeft() == 7-4+3 && gm.getLettersLeft() == 0;
		}
		return result;
	}
	
	@Test
	public boolean testGenerateLetters3(){
		gm.getPlayer1().setNrLetters(3);
		gm.getPlayer2().setNrLetters(3);
		gm.setLettersLeft(0);
		gm.generateLetters(2);
		boolean result = false;
		
		if(gm.getPlayer1().isTurn()){
			result = gm.getPlayer1().getNrLetters()-2 == 3-2 && gm.getLettersLeft() == 0;
		}
		else if(gm.getPlayer2().isTurn()){
			result = gm.getPlayer2().getNrLetters()-2 == 3-2 && gm.getLettersLeft() == 0;
		}
		return result;
	}
	
	@Test
	public void testReceivePoints(){
		int points1 = gm.getPlayer1().getPoints();
		int points2 = gm.getPlayer2().getPoints();
		
		gm.receivePoints("Hej");
		
		if(gm.getPlayer1().isTurn()){
			assertFalse(points1 == gm.getPlayer1().getPoints());
		}
		else{
			assertFalse(points2 == gm.getPlayer2().getPoints());
		}
	}
	
	@Test
	public void testChangeTurn() {
		boolean changedTurn = false;
		
		gm.getPlayer1().setTurn(false);
		gm.changeTurn();
		changedTurn = true;
		assertTrue(changedTurn == gm.getPlayer1().isTurn());
	}
	
	@Test
	public void testPass(){
		gm.setPass(2);
		int pass = 2;
		
		gm.pass();
		assertTrue(pass+1 == gm.getPass());
	}
	
	@Test
	public void testPlaceWord(){
		WordObject w = new WordObject("Hej", 0, 2, Direction.VERTICAL);
		Board b = null;
		boolean result = false;
		
		gm.placeWord(w);
		b = gm.getBoard();
		if(b.getCharAt(0, 2) == 'H' && b.getCharAt(0, 3) == 'e' && b.getCharAt(0, 4) == 'j'){
			result = true;
		}
		assertTrue(result);
	}
}
