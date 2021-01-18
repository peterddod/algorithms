import java.util.Map;

public class LZWComp {
    public static String slowComp(String text) {
        int textPos = 0;
        int textLen = text.length();
        int codeWordLength = 8;
        Map<String, String> codeMap; // initialise dictionary with ascii characters
        
        while (textPos < textLen) {
            // find longest string s in text from position i thats in dictionary

            // output codeword for the string s

            // move to next position in text
            textPos += s.length;
            // c = character in position i of text

            // add string s+c to dictionary, paired with next available codeword (may have to increment codewordlength so this is possible)
        }
    }
}
