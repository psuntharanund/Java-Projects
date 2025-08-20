import java.util.*;

public class WarGame implements AllocateDeck, PopulateMainDeck, ShuffleDeck{

    static AllocateDeck allocate = new WarGame();
    static PopulateMainDeck populate = new WarGame();
    static ShuffleDeck shuffle = new WarGame();
    static WarGame war = new WarGame();
    static Scanner kb = new Scanner(System.in);
    static boolean gameState = false;
    static String nextRound;
    static char repeat;
    static Queue<Integer> mainDeck = new LinkedList<>();
    static Queue<Integer> player1Deck = new LinkedList<>();
    static Queue<Integer> player2Deck = new LinkedList<>();
    static Stack<Integer> player1Tie = new Stack<>();
    static Stack<Integer> player2Tie = new Stack<>();

    public void shuffleDeck(Queue<Integer> deck){
        List<Integer> proxyDeck = new LinkedList<>(deck);
        Collections.shuffle(proxyDeck);
        deck.clear();
        deck.addAll(proxyDeck);
    }
    
    public void allocateDeck(Queue<Integer> mainDeck, Queue<Integer> deck1, Queue<Integer> deck2){
        deck1.clear();
        deck2.clear();
        shuffleDeck(mainDeck);
        List<Integer> deckList = new LinkedList<>(mainDeck);
        for (Integer i = 0; i < deckList.size(); i++){
            if (i % 2 == 0){
            deck1.add(deckList.get(i));
            } else {
            deck2.add(deckList.get(i));
            }
        }
        shuffleDeck(deck1);
        shuffleDeck(deck2);
    }
    
    public int cardRank(int card){
        return card % 13;
    }

    public void populateMainDeck(Queue<Integer> deck){
        deck.clear();
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
        }  else {
            System.out.println("You have not put a valid answer. Please restart program.");
            System.exit(0);
        }
        while (gameState){
            populateMainDeck(mainDeck);
            allocateDeck(mainDeck, player1Deck, player2Deck);
            gameMechanics(player1Deck, player2Deck);
        }
    }

    public void gameMechanics(Queue<Integer> deck1, Queue<Integer> deck2){
        while (!deck1.isEmpty() && !deck2.isEmpty()){
            Integer card1 = deck1.poll();
            Integer card2 = deck2.poll();
            System.out.println("Player 1: " + card1);
            System.out.println("Player 2: " + card2);
            int comp = Integer.compare(cardRank(card1), cardRank(card2));
            if (comp == 0){
                tieGame(deck1, deck2, card1, card2);
            } else if (comp > 0){
                deck1.add(card1);
                deck1.add(card2);
                System.out.println("Player 1 wins this round! Deck count: " + deck1.size());
            } else {
                deck2.add(card1);
                deck2.add(card2);
                System.out.println("Player 2 wins this round! Deck count: " + deck2.size());
            }
        } if (deck1.isEmpty()){
            System.out.println("Player 1 has run out of cards! Player 2 is the victor! Player 2 deck count: " + deck2.size() + " Player 1 deck count: " + deck1.size());
        } else {
            System.out.println("Player 2 has run out of cards! Player 1 is the victor! Player 1 deck count: " + deck1.size() + " Player 2 deck count: " + deck2.size());
        }
        System.out.println("Would you like to restart? Y/N");
        repeat = kb.next().charAt(0);
        if (repeat == 'y' || repeat == 'Y'){
            gameState = true;
        } else {
            System.out.println("You have not put a valid answer. Please restart program.");
            System.exit(0);
        }
    }

    public void tieGame(Queue<Integer> deck1, Queue<Integer> deck2, Integer card1, Integer card2){
        List<Integer> win = new LinkedList<>();
        win.add(card1);
        win.add(card2);
        System.out.println("WAR! Whoever has the biggest card wins the pile!");
        while (true){
            if (deck1.size() < 4 || deck2.size() < 4){
                if (deck1.size() < 4 && deck2.size() < 4){
                    for (int i : win) deck1.add(i); 
                } else if (deck1.size() < 4){
                    for (int i : win) deck2.add(i);
                    while (!deck1.isEmpty()) deck2.add(deck1.poll());
                } else {
                    for (int i : win) deck1.add(i);
                    while (!deck2.isEmpty()) deck1.add(deck2.poll());
                }
            return;
            }     
            for (int i = 0; i < 3; i++){
                win.add(deck1.poll());
                win.add(deck2.poll());
            }
            Integer up1 = deck1.poll();
            Integer up2 = deck2.poll();
            win.add(up1);
            win.add(up2);
            int comp = Integer.compare(cardRank(up1), cardRank(up2));
            if (comp > 0){
                for (int i : win) deck1.add(i);
                System.out.println("Player 1 card count: " + deck1.size());
                return;
            } else {
                for (int i : win) deck2.add(i);
                System.out.println("Player 2 card count: " + deck2.size());
                return;
            }
        }
    }

    public static void main(String[] args){
       war.gameStart(); 
    }
}
