import java.math.BigInteger;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

enum Code{
	ABC, DEF, GHI, JKL, MNO, PQRS, TUV, WXYZ;
}

/**
 * Code and decode words
 * 
 * @author manuel
 *
 */
public class CodeDecode {
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		BigInteger code = new BigInteger("0");
		ArrayList<Integer> arlCode = new ArrayList<Integer>();
		String word = "";
		
		// Take a word or a code from the keyboard
		do {
			System.out.print("Introduzca una palabra o código numérico: ");
			if(scn.hasNextBigInteger()) {
				code = scn.nextBigInteger();
				System.out.println("\nCódigo: " + code.toString() + "\n");
				break;
			}else if(scn.hasNext()) {
				word = scn.next();
				System.out.println("\nPalabra: " + word + "\n");
				break;
			}
		}while(true);
		scn.close();
		
		if(word.length() > 0) {
			// If a word, try code and decode it
			arlCode = code(word);
			decode(arlCode);
		}else {
			// If a numeric code, try decode it 
			for(char chr: code.toString().toCharArray())
				arlCode.add(Integer.parseInt(String.valueOf(chr)));
			decode(arlCode);
		}
	};
	
	/**
	 * Code a word into an integer ArrayList printing the process 
	 * @param word Word to code
	 * @return Numeric code of the word
	 */
	private static ArrayList<Integer> code(String word) {
		word = Normalizer.normalize(word, Normalizer.Form.NFD).toUpperCase();
		int number = 0;
		ArrayList<Integer> arlCode = new ArrayList<Integer>();

		for(char chr: word.toCharArray()) {
			number = 0;
			for(Code cod: Code.values())
				// Searching for the character block number
				if(cod.toString().indexOf(chr) >=0) {
					number = cod.ordinal() + 2;
					break;
				};
			if(number > 0) {
				System.out.println(chr + "    " + Code.values()[number - 2] + " (" + number + ")");
				arlCode.add(number);
			}else
				System.out.println(chr + "    Carácter no codificable");
		}
		System.out.print("\nPalabra codificada: ");
		for(Integer i: arlCode )
			System.out.print(i);
		System.out.println();
		System.out.println();
		return arlCode;
	};
	
	/**
	 * Decode an ArrayList of integer values
	 * This method looks for errors in the entry code and calls the process to decode it.
	 * @param arlCode Codified word
	 */
	private static void decode(ArrayList<Integer> arlCode) {
		String err = "";
		Integer i;
		
		// The iterator is required when there are items to delete
		Iterator<Integer> itr = arlCode.iterator(); 
		while (itr.hasNext()) {
			i = itr.next(); 
			if(i < 2 || i>9) {
				if(err.equals(""))
					err += i;
				else
					err += ", " + i;
				itr.remove();
			}
		} 
		if(err.length()> 0)
			System.out.println("Números de bloque [" + err + "] erroneos. Se eliminan del código a decodificar.");
		
		if(arlCode.size() == 0) {
			System.out.println("Código vacío");
			return;
		};
		System.out.println("\nPosibles valores para el código " + arlCode + ":\n");
		decodeProcess(arlCode, "", 0);
	};
	
	/**
	 * Recursive process for decode the numeric ArrayList 
	 * @param arlCode Code 
	 * @param substr Substring in process 
	 * @param idx Index for the ArrayList of the code
	 */
	private static void decodeProcess(ArrayList<Integer> arlCode, String substr, int idx) {
		String str = Code.values()[arlCode.get(idx) - 2].toString();
		for(char chr: str.toCharArray())
			if(idx < arlCode.size() - 1)
				decodeProcess(arlCode, substr + chr, idx + 1);
			else
				System.out.println(substr + chr);
	};
}
