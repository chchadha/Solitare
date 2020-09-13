package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire
 * Encryption algorithm.
 *
 * @author RU NB CS112
 */
public class Solitaire {

	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;

	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a
	 * circular linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i = 0; i < cardValues.length; i++) {
			cardValues[i] = i + 1;
		}

		// shuffle the cards
		Random randgen = new Random();
		for (int i = 0; i < cardValues.length; i++) {
			int other = randgen.nextInt(28);
			int temp = cardValues[i];
			cardValues[i] = cardValues[other];
			cardValues[other] = temp;
		}

		// create a circular linked list from this deck and make deckRear point
		// to its last node
		CardNode cn = new CardNode();
		cn.cardValue = cardValues[0];
		cn.next = cn;
		deckRear = cn;
		for (int i = 1; i < cardValues.length; i++) {
			cn = new CardNode();
			cn.cardValue = cardValues[i];
			cn.next = deckRear.next;
			deckRear.next = cn;
			deckRear = cn;
		}
	}

	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */

	public void makeDeck(Scanner scanner) throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
			cn.cardValue = scanner.nextInt();
			cn.next = cn;
			deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
			cn.cardValue = scanner.nextInt();
			cn.next = deckRear.next;
			deckRear.next = cn;
			deckRear = cn;
		}
	}

	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {

		/*First checked if list is empty and if so the program should
		 * throw a NoSuchElementException(). Otherwise a pointer is created
		 * at the front of the CLL and once the value of 27 is found the value
		 * at 27 is swapped with the value after 27. The data has been exchanged between
		 * the two nodes not the nodes themselves.
		 */
		if (deckRear == null) { // list is empty
			throw new NoSuchElementException();
		}

		CardNode ptr = deckRear.next;

		do {
			if (ptr.cardValue == 27) {

				int temp = ptr.next.cardValue;
				ptr.next.cardValue = ptr.cardValue;
				ptr.cardValue = temp;
				break;

			} else {

				ptr = ptr.next;
			}

		} while (ptr != deckRear.next);

		//System.out.println("Step 1 Joker A: ");
		//printList(deckRear);

	}

	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {

		/* Similar to jokerA if the deck is empty we will throw a NoSuchElementException.
		 * Otherwise we create a pointer at the front of the CLL. However, this condition consists of
		 * a few base cases since it is the second node in the CLL. Three pointers are used: prev, ptr, and the
		 * value after ptr. This time I moved my nodes around rather than switching my data. The instructions say that
		 * the second pointer will be moved two spots down. In that case one base case is if the joker is two spots away from
		 * the deckRear in which the joker would become the rear. The second case is that if the joker is in the back, which means
		 * that we need to move the joker two spots over and reassign deckRear.The last case is if the joker is anywhere else
		 * in the CLL otherwise the pointers will continue to move forward.
		 */

		if (deckRear == null) {
			throw new NoSuchElementException();
		}


		CardNode prev = deckRear;
		CardNode ptr = deckRear.next;
		CardNode after = ptr.next;

		do {
			if (ptr.cardValue == 28) {

				if(ptr.next.next == deckRear){
					ptr.next = after.next.next;
					after.next.next = ptr;
					prev.next = after;
					deckRear = ptr;
					break;
				}

				if (ptr == deckRear) {
					ptr.next = after.next;
					after.next = ptr;
					prev.next = after;
					deckRear = after;
					break;
				}

				ptr.next = after.next.next;
				after.next.next = ptr;
				prev.next = after;
				break;
			} else {
				prev = ptr;
				ptr = after;
				after = after.next;

			}
		} while (ptr != deckRear.next);

		//System.out.println("Step 2 Joker B: ");
		//printList(deckRear);

	}

	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {

		/* In triple cut everything before the first pointer is moved after the second pointer
		 * and everything after the second pointer is moved before the first pointer in the same order.
		 * The program checks if the deck is empty in which case it will throw a NoSuchElementException.
		 * I created a pointer two pointers at the front of the CLL and rear of the CLL. The first pointer
		 * will check the CLL for the joker and place the pointer there by breaking the while loop. I have a
		 * second do while to check for the second joker in the list and places the second pointer at that joker by breaking
		 * the do while. We check if the rear of the deck is equal to our pointer indicating there is a joker and move deckRear
		 * If my deckRear is the same we know that the joker is in the front of the CLL and make the rear equal to the second pointer
		 * thus pushing all my elements behind the second pointer to the front. IF my deckRear is at my second joker then we set deckRear
		 * to the one before our first pointer thus pushing everything after to the front of the CLL.
		 *

		 */

		if(deckRear == null){
			throw new NoSuchElementException();
		}

		CardNode ptr = deckRear.next;
		CardNode prev = deckRear;
		CardNode origRear = deckRear;
		CardNode origFront = deckRear.next;


		do{
			if(ptr.cardValue == 27 || ptr.cardValue == 28){
				break;

			}
			else{
				prev=ptr;
				ptr=ptr.next;
			}

		}while(ptr != deckRear.next);


		CardNode ptr2 = ptr.next;


		do{
			if(ptr2.cardValue == 27 || ptr2.cardValue == 28){
				break;
			}else{
				ptr2 = ptr2.next;
			}
		}while(ptr2!=deckRear.next);



		if(prev == deckRear){
			deckRear = ptr2;
		}
		else if(ptr2 == deckRear){
			deckRear = prev;
		}else{

			CardNode afterPtr2 = ptr2.next;
			deckRear = ptr2;
			origRear.next=ptr;
			deckRear.next = origFront;
			prev.next = afterPtr2;
			deckRear = prev;
		}

		//System.out.print("Step 3: Triple Cut ");
		//printList(deckRear);

	}

	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */

	void countCut() {
		/* countCut will check the value of the last card in the deck and move the first amount of cards that
		 * are equal to the last cards value before the rear. I used a for loop to iterate how many nodes from the
		 * front will move in front of the rear. In this case I created a pointer to iterate until it reached the
		 * card before my rear. Then for cardCount (value of last cards) amount of times I will move my front node to
		 * after my ptr and update the pointers.
		 *
		 */

		if (deckRear == null) {
			return;
		}
		CardNode front = deckRear.next;

		CardNode ptr = deckRear.next;
		int cardCount = deckRear.cardValue;

		while(ptr.next != deckRear){
			ptr=ptr.next;
		}



		for (int i = 0; i < cardCount; i++) {

			deckRear.next = front.next;
			front.next = ptr.next;
			ptr.next = front;
			ptr = front;
			front = deckRear.next;

		}

		//System.out.print("Step 4: Count Cut  ");
		//printList(deckRear);

		// COMPLETE THIS METHOD
	}

	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count
	 * Cut, then counts down based on the value of the first card and extracts
	 * the next card value as key. But if that value is 27 or 28, repeats the
	 * whole process (Joker A through Count Cut) on the latest (current) deck,
	 * until a value less than or equal to 26 is found, which is then returned.
	 *
	 * @return Key between 1 and 26
	 */

	int getKey() {

		/* To get the key I iterated my fist four methods and then created a pointer to the front of the CLL.
		 * I also created an integer variable that held the value of the first card in the CLL. If the value of
		 * the first card was 28 then we set the firstCard equal to 27 and move the pointer over 27 times. The do
		 * while is to check the value of the next card to see whether or not it is a joker. If its not we will return the
		 * card value otherwise the process is repeated.
		 *
		 */
		CardNode ptr=null;


		do {

			jokerA();
			jokerB();
			tripleCut();
			countCut();

			ptr = deckRear.next;
			int firstCard = deckRear.next.cardValue;

			if(firstCard==28){
				firstCard = 27;
			}

			for (int i = 0; i < firstCard; i++) {
				ptr = ptr.next;
			}

		} while (ptr.cardValue > 26);

		return ptr.cardValue;


	}

	// COMPLETE THIS METHOD
	// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE

	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 *
	 * @param rear
	 *            Rear pointer
	 */

	private static void printList(CardNode rear) {
		if (rear == null) {
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 *
	 * @param message
	 *            Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */

	// 38

	public String encrypt(String message) {

		/* To encrypt and decrypt I used three arrays. First I created a new empty string where I would
		 * check if the character at i is upper case. If the character is upper case then the program adds
		 * to the empty string otherwise it will skip anything else. The three arrays are the size of the new
		 * message since everything that is not uppercase has been deleted. In one  array I convert the letters
		 * to their number values and for the second I use the getKey() method to find the key values which will be
		 * added to the number values of the character. If the sum of numValues and the characters is over 26 then the
		 * program will subtract 26 to ensure that the value of the card is in bounds of the number of letters in the alphabet.
		 * The program will then use the third array and convert the numbers to letters and add them to the empty string, encryptedMessage.
		 * The message would then be returned in the program. Decrypt works similarly with the three arrays however instead of
		 * subtracting 'A' the program is adding it since we are doing the complete opposite of encrypt.
		 */


		String newMessage = "";

		for (int i = 0; i < message.length(); i++) {

			char ch = message.charAt(i);

			if (Character.isUpperCase(ch)) {
				newMessage += ch;
			} else {
				continue;
			}
		}



		int[] numValues = new int[newMessage.length()];
		int[] keyString = new int[newMessage.length()];
		int[] encryptNum = new int[newMessage.length()];

		for (int i = 0; i < newMessage.length(); i++) {
			numValues[i] = newMessage.charAt(i) - 'A' + 1;
			keyString[i] = getKey();
			encryptNum[i] = numValues[i] + keyString[i];

			if (encryptNum[i] > 26) {
				encryptNum[i] = encryptNum[i] - 26;
			}
		}

		String encryptedMessage = "";

		for (int i = 0; i < newMessage.length(); i++) {

			encryptedMessage = encryptedMessage + (char)(encryptNum[i]+'A'-1);
		}

		return encryptedMessage;

	}

	/**
	 * Decrypts a message, which consists of upper case letters only
	 *
	 * @param message
	 *            Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */

	public String decrypt(String message) {

		int[] numValues = new int[message.length()];
		int[] keyString = new int[message.length()];
		int[] decryptNum = new int[message.length()];

		for (int i = 0; i < message.length(); i++) {
			numValues[i] = message.charAt(i) - 'A' + 1;
			keyString[i] = getKey();
			decryptNum[i] = numValues[i] - keyString[i];

			if (decryptNum[i] < 1) {
				decryptNum[i] = decryptNum[i] + 26;
			}
		}

		String decryptedMessage = "";

		for (int i = 0; i < message.length(); i++) {

			decryptedMessage = decryptedMessage + (char)(decryptNum[i]+'A'-1);
		}

		return decryptedMessage;
	}

}