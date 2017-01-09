import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.io.*;

// Class to determine the number of phone numbers a chess knight can dial on a standard telephone.
// 1 2 3
// 4 5 6
// 7 8 9
//   0   <-- 0 treated as 11 due to its position
//
// If we want to restrict this only to the number of possibilities, then we can modify this code
// to only store the last digit in a sequence rather than entire sequences. However, the code
// has been made to store the entire sequence so that sequences can be printed for verification.
public class KnightPhone
{
	private static final Hashtable<Byte, byte[]> hshModToMoves = new Hashtable<Byte, byte[]>()
	{{
		put((byte)1, new byte[]{ -5, -1, 5, 7 } );  // if (digit % 3) == 1, we can move -5, -1, +5, or +7 
		put((byte)2, new byte[]{ -7, -5, 5, 7 } );  // if (digit % 3) == 2, we can move -7, -5, +5, or +7
		put((byte)0, new byte[]{ -7, -5, 1, 5 } );  // if (digit % 3) == 0, we can move -7, -5, +1, or +5
	}};

	// Store next-possible-numbers to move to from the current number
	private static final Hashtable<Byte, List<Byte>> hshNextDigits = new Hashtable<Byte, List<Byte>>();

	public static void main(String[] args)
	{
		if (args.length < 1)
		{
			System.out.println("Please provide a number of digits in the telephone number.");
			return;
		}

		int intNumDigits;
		try
		{
			intNumDigits = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Please provide a valid number of digits in the telephone number.");
			return;
		}

		// Iterate over 10 initial starting digits (1-11, skipping 10))
		// Store each as starting point in list of phone numbers; each phone number stored as list of digits
		List<List<Byte>> lstNumberOptions = new ArrayList<List<Byte>>();
		for (byte i = 1; i <= 11; i++)
		{
			if (i == 10) continue;

			List<Byte> lstNumber = new ArrayList<Byte>();
			lstNumber.add(i);
			lstNumberOptions.add(lstNumber);
		}

		int intTotalNumbers = GetList(intNumDigits, lstNumberOptions).size();
		System.out.println(String.format("Total number possibilities for a %1$d-digit phone number: %2$d", intNumDigits, intTotalNumbers));
	}

	// Add a digit to each number in the given list of phone numbers
	private static List<List<Byte>> GetList(int intRemainingIterations, List<List<Byte>> lstNumbers)
	{
		if (intRemainingIterations == 1)
		{
			// The list can be printed to text at this point
			return lstNumbers;
		}

		// Create entirely new list of phone numbers
		List<List<Byte>> lstNewNumbers = new ArrayList<List<Byte>>();
		for (int i = 0; i < lstNumbers.size(); i++)
		{
			// Create new phone number using last digit of current phone number
			List<Byte> lstCurrentNumber = lstNumbers.get(i);
			int intListSize = lstCurrentNumber.size();
			byte btLastDigit = lstCurrentNumber.get(intListSize - 1);
			List<Byte> lstNextDigits = GetNextDigits(btLastDigit);
			for (int j = 0; j < lstNextDigits.size(); j++)
			{
				// Set new phone number as current number + new digit
				List<Byte> lstNewNumber = new ArrayList<Byte>(lstCurrentNumber);
				lstNewNumber.add(lstNextDigits.get(j));
				lstNewNumbers.add(lstNewNumber);
			}
		}

		return GetList(intRemainingIterations - 1, lstNewNumbers);
	}

	// Get the next digit options from the given digit
	private static List<Byte> GetNextDigits(byte btNumber)
	{
		// Dynamic programming lookup
		if (hshNextDigits.contains(btNumber))
		{
			return hshNextDigits.get(btNumber);
		}

		List<Byte> lstNextNumberOptions = new ArrayList<Byte>();

		// Check which possibilities are valid options for next number
		byte btMod = (byte)(btNumber % 3);
		byte[] btMoves = hshModToMoves.get(btMod);
		for (int i = 0; i < btMoves.length; i++)
		{
			byte btProposedNumber = (byte)(btNumber + btMoves[i]);
			if (btProposedNumber >= 1 && btProposedNumber <= 11 && btProposedNumber != 10)
			{
				lstNextNumberOptions.add(btProposedNumber);
			}
		}

		// Add this result to the Hashtable for easy lookup next time
		hshNextDigits.put(btNumber, lstNextNumberOptions);
		return lstNextNumberOptions;
	}
}