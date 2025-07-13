import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WarGame implements AllocateDeck, PopulateMainDeck, ShuffleDeck{

    static AllocateDeck allocate = new WarGame();
    static PopulateMainDeck populate = new WarGame();
    static ShuffleDeck shuffle = new WarGame();
    static WarGame war = new WarGame();
    static Scanner kb = new Scanner(System.in);
    static boolean gameState = false;
    static String nextRound;
    static char repeat;
    static ArrayList<Integer> mainDeck = new ArrayList<>();
    static ArrayList<Integer> player1Deck = new ArrayList<>();
    static ArrayList<Integer> player2Deck = new ArrayList<>();
    static ArrayList<Integer> player1Tie = new ArrayList<>();
    static ArrayList<Integer> player2Tie = new ArrayList<>();

    public void shuffleDeck(ArrayList<Integer> deck){
        Collections.shuffle(deck);
    }
    
    public void allocateDeck(ArrayList<Integer> deck1, ArrayList<Integer> deck2, ArrayList<Integer> deck3){
        shuffleDeck(deck1);
        for (Integer i = 0; i < deck1.size(); i+=2){
            deck2.add(deck1.get(i));
        }
        for (Integer i = 1; i < deck1.size(); i+=2){
            deck3.add(deck1.get(i));
        }
        shuffleDeck(deck2);
        shuffleDeck(deck3);
    }
    
    public void populateMainDeck(ArrayList<Integer> deck){
        Integer deckSize = 52;
        for (Integer i = 0; i < deckSize; i++){
            deck.add(i);
        }
    }
    
    public void gameStart(){
        System.out.println("Would you like to begin? Y/N");
        repeat = kb.next().charAt(0);
        if (repeat == 'y' || repeat == 'Y'){
            gameState = true;
        } else {
            System.out.println("You have not put a valid answer. Please restart program.");
            System.exit(0);
        }
        while (gameState){
            populateMainDeck(mainDeck);
            allocateDeck(mainDeck, player1Deck, player2Deck);
            gameMechanics(player1Deck, player2Deck);
        }
    }

    public void gameMechanics(ArrayList<Integer> deck1, ArrayList<Integer> deck2){
        boolean player1Wins = false;
        boolean player2Wins = false;
        
       while (gameState){
            for (int i = 0; i < deck1.size(); i++){
                System.out.println("Player 1: " + deck1.get(i));
                System.out.println("Player 2: " + deck2.get(i));
                if (deck1.get(i) > deck2.get(i)){
                    deck1.add(deck2.get(i));
                    deck2.remove(i);
                } else if (deck2.get(i) > deck1.get(i)){
                    deck2.add(deck1.get(i));
                    deck1.remove(i);
                } else {
                    tieGame(deck1, deck2);
                }
            }
        }

    }

    public void tieGame(ArrayList<Integer> deck1, ArrayList<Integer> deck2){
        for (int i = 0; i < 4; i++){
            player1Tie.add(deck1.get(i));
            deck1.remove(i);
            player2Tie.add(deck2.get(i));
            deck2.remove(i);
        }
        System.out.println("Player 1: " + player1Tie.get(3));
        System.out.println("Player 2: " + player2Tie.get(3));
        if (player1Tie.get(3) > player2Tie.get(3)){
            for (int i = 0; i < 4; i++){
                player1Tie.add(player2Tie.get(i));
                player2Tie.remove(i);
            }
        } else {
            for (int i = 0; i < 4; i++){
                player2Tie.add(player1Tie.get(i));
                player1Tie.remove(i);
            }
        }
        if (player1Tie.size() != 0){
            for (int i = 0; i < player1Tie.size(); i++){
                deck1.add(player1Tie.get(i));
                player1Tie.remove(i);
            }
        } else {
            for (int i = 0; i < player2Tie.size(); i++){
                deck2.add(player2Tie.get(i));
                player2Tie.remove(i);
            }
        }
    }

    public static void main(String[] args){
       war.gameStart(); 
    }
}
