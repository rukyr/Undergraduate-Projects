public class StringRec {
	public static String decompress(String compressedText) {
		if (compressedText.isEmpty()) {
			return compressedText;
		}
		if (compressedText == "") {
			return "";
		}

		if (compressedText.charAt(0) == '1') {
			return (compressedText.substring(1,2)+decompress(compressedText.substring(2,compressedText.length())));
		}
		
		if (compressedText.charAt(0) == '2') {
			
			compressedText = '1'+compressedText.substring(1,compressedText.length());	
			return (compressedText.substring(1,2)+decompress(compressedText));

		}
		
		if (compressedText.charAt(0) == '3') {
			
			compressedText = '2'+compressedText.substring(1,compressedText.length());
			return (compressedText.substring(1,2)+decompress(compressedText));

		}
		
		if (compressedText.charAt(0) == '4') {
			
			compressedText = '3'+compressedText.substring(1,compressedText.length());
			return (compressedText.substring(1,2)+decompress(compressedText));

		}
		
		if (compressedText.charAt(0) == '5') {
			
			compressedText = '4'+compressedText.substring(1,compressedText.length());
			return (compressedText.substring(1,2)+decompress(compressedText));

		}
		if (compressedText.charAt(0) == '6') {
			
			compressedText = '5'+compressedText.substring(1,compressedText.length());
			return (compressedText.substring(1,2)+decompress(compressedText));

		}
		if (compressedText.charAt(0) == '7') {
			
			compressedText = '6'+compressedText.substring(1,compressedText.length());
			return (compressedText.substring(1,2)+decompress(compressedText));

		}
		
		if (compressedText.charAt(0) == '8') {
			
			compressedText = '7'+compressedText.substring(1,compressedText.length());
			return (compressedText.substring(1,2)+decompress(compressedText));

		}
		
		if (compressedText.charAt(0) == '9') {
			
			compressedText = '8'+compressedText.substring(1,compressedText.length());
			return (compressedText.substring(1,2)+decompress(compressedText));

		} else {
			return compressedText.charAt(0)+decompress(compressedText.substring(1,compressedText.length()));
		}
		
	}
}
