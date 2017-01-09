using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;

/// ***********************************************************************
/// Class ExcerptGenerator
///
/// <summary>
///     Generate an example excerpt from a provided text using trigrams.
/// </summary>
/// 
public class ExcerptGenerator
{
	public static void Main()
	{
		// Read file
        string strText = new WebClient().DownloadString("http://www.gutenberg.org/files/1342/1342-0.txt");
		
		// Create trigrams
		string[] strPunctuation = { ".", "!", "?" };
		Dictionary<string[], List<string>> dctTrigrams = new Dictionary<string[], List<string>>();
		string[] strWords = strText.Split().Where(str => !String.IsNullOrWhiteSpace(str)).ToArray();
		for (int i = 0; i < strWords.Length - 2; i++)
		{
			string[] strKey = { strWords[i].ToLowerInvariant(), strWords[i + 1].ToLowerInvariant() };
			string strNextWord = strWords[i + 2].ToLowerInvariant();
			
			// Skip if word or next word ends in punctuation
			if (strPunctuation.Any(punctuation => strKey.Any(key => key.EndsWith(punctuation))))
			{
				continue;
			}
			
            // Map word pair (key) to third word
			if (!dctTrigrams.Keys.Any(key => key[0] == strKey[0] && key[1] == strKey[1]))
			{
				dctTrigrams.Add(strKey, new List<string>(){ strNextWord });
			}
			else
			{
				dctTrigrams.Single(kvp => kvp.Key[0] == strKey[0] && kvp.Key[1] == strKey[1]).Value.Add(strNextWord);
			}
		}
		
		// Get initial word using random number gen,
		// Seed random number gen to get next word from list of options from key
		Random rand = new Random();
		StringBuilder sbOutput = new StringBuilder();
		KeyValuePair<string[], List<string>> kvpNextEntry = dctTrigrams.ElementAt(rand.Next(0, dctTrigrams.Count));
		sbOutput.AppendFormat("{0} {1}", char.ToUpper(kvpNextEntry.Key[0][0]) + kvpNextEntry.Key[0].Substring(1), kvpNextEntry.Key[1]); // Capitalize first word
		string[] strCurrentKey = kvpNextEntry.Key;
		
		// Get next word while trigrams exist
		while (dctTrigrams.Keys.Any(key => key[0] == strCurrentKey[0] && key[1] == strCurrentKey[1]))
		{
			kvpNextEntry = dctTrigrams.Single(kvp => kvp.Key[0] == strCurrentKey[0] && kvp.Key[1] == strCurrentKey[1]);
			string strNextWord = kvpNextEntry.Value[rand.Next(0, kvpNextEntry.Value.Count)];
			sbOutput.AppendFormat(" {0}", strNextWord);
			strCurrentKey = new string[] { strCurrentKey[1], strNextWord };
		}
		
		// Add ellipsis to end if last word didn't end with punctuation
		string strLastCharacter = sbOutput.ToString(sbOutput.Length - 1, 1);
		if (!strPunctuation.Any(punctuation => strLastCharacter == punctuation))
		{
			sbOutput.Append("…");
		}
		
		Console.WriteLine(sbOutput.ToString());
	}
} // end class ExcerptGenerator