public class HuffmanTest_Handout {
    public static void main(String[] args) {
        String text = "AABBCCCCDD\n";
        int expectedEncodedLength = 25;
        String expectedEncodedText = "1101101111110000101101100"; 
        //Just a possible encoded sequence. It is not the only correct answer!
        String expectedDecodedText = "AABBCCCCDD\n";
        
        // Create a new instance of HuffmanCoding
        HuffmanCoding huffman = new HuffmanCoding(text);
        
        // Encode the text
        String encodedText = huffman.encode(text);
        System.out.println("Encoded Text: " + encodedText);
        System.out.println("Expected Encoded Text: " + expectedEncodedText);
        
        // Decode the encoded text
        String decodedText = huffman.decode(encodedText);
        System.out.println("Decoded Text: " + decodedText);
        System.out.println("Expected Decoded Text: " + expectedDecodedText);
        
        // Judge the correctness of the results
        boolean isEncodedCorrect = encodedText.equals(expectedEncodedText);
        boolean isDecodedCorrect = decodedText.equals(expectedDecodedText);
                // Compare the lengths of the encoded sequence and the expected sequence
        int encodedLength = encodedText.length();
        
        
        if (isEncodedCorrect && isDecodedCorrect && encodedLength == expectedEncodedLength) {
            System.out.println("Encoding and decoding results are correct.");
            System.out.println("Encoded Length: " + encodedLength);
            System.out.println("Expected Encoded Length: " + expectedEncodedLength);
        } 
        else {
            if (!isDecodedCorrect) {
                System.out.println("Decoding result is incorrect.");
            }
            if (encodedLength != expectedEncodedLength) {
                System.out.println("Encoded length does not match the expected encoded length.");
            }
        }
        
        // Display additional information if needed
        System.out.println(huffman.getInformation());
    }
}