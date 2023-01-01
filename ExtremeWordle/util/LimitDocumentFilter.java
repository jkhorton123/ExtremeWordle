package util;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
public class LimitDocumentFilter extends DocumentFilter {

    private int limit;

    public LimitDocumentFilter(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit can not be <= 0");
        }
        this.limit = limit;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        /*
        Validates the text to ensure it is the right length and is either an empty string or an alphabetic character
        */
        int currentLength = fb.getDocument().getLength();
        int overLimit = (currentLength + text.length()) - limit - length;
        if (overLimit > 0) {
            text = text.substring(0, text.length() - overLimit);
        }
        if(text.equals("")) {
            super.replace(fb, offset, length, text, attrs); 
            return;
        }
        Character c = text.toCharArray()[0];
        if (Character.isAlphabetic(c)) {
            text = text.toUpperCase();
            super.replace(fb, offset, length, text, attrs); 
        }
    }

}