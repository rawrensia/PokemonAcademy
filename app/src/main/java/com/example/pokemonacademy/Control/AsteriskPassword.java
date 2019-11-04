
/**
 * When user inputs the password, it will be asterisk for security.
 *
 * @author  Laurensia
 * @version 1.0
 * @since   2019-10-07
 */

package com.example.pokemonacademy.Control;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

public class AsteriskPassword extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;
        public PasswordCharSequence(CharSequence source) {
            mSource = source; // Store char sequence
        }
        public char charAt(int index) {
            return '*'; // This is the important part
        }
        public int length() {
            return mSource.length(); // Return default
        }
        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end); // Return default
        }
    }
}
