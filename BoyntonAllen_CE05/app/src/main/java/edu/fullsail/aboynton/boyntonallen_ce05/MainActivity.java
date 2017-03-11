// Allen Boynton

// JAV1 - 1703

// MainActivity.java

package edu.fullsail.aboynton.boyntonallen_ce05;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    // Reports/Prints to the console
    private static final String TAG = "MyActivity";

    // Created userWords to hold all the userInput of strings
    private final ArrayList<String> userWords = new ArrayList<>();

    // Number picker manages collection by use of array index
    private NumberPicker wordPicker = null;

    // Initialize the user's input
    private EditText userInput;

    // Initialize the 2 math views
    private TextView averageView;
    private TextView medianView;

    // Global variables
    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "----> Main Activity(): ON");

        //Assign the widgets reference from XML layout resource file
        wordPicker = (NumberPicker) findViewById(R.id.number_picker);

        userInput = (EditText) findViewById(R.id.userInput);

        averageView = (TextView) findViewById(R.id.average_output);
        medianView = (TextView) findViewById(R.id.median_output);

        // Converts edit word to strings
        word = userInput.getText().toString();

        Log.d(TAG, "<---- Main Activity(): OFF");

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: On");

                // Calls on method to setup picker
                addPicker();

                // Variables within onClick
                int id = view.getId();

                // If/else to determine which button is tapped and the action that follows
                if (id == R.id.add_button) {
                    // Shows toasts depending on user entry
                    checkInvalidWords();
                    formulateAverage();
                    formulateMedian();
                } else if (id == R.id.view_button) {
                    if (userWords.size() > 0) {
                        // Show an Alert Dialog
                        removeWordAlert();
                    } else {
                        // Added toast to inform user there are no words. Also, if stops from crashing when 0
                        Toast.makeText(MainActivity.this, R.string.emptyStringList, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    formulateAverage();
                    formulateMedian();
                }
                Log.d(TAG, "onClick: Off");
            }
        };
        // Set up onClickListener for 2 buttons
        findViewById(R.id.add_button).setOnClickListener(listener);
        findViewById(R.id.view_button).setOnClickListener(listener);
    }

    private void addPicker() {
        Log.d(TAG, "----> addPicker(): ON");

        // Sets the maximum number of NumberPicker
        wordPicker.setMaxValue(userWords.size());
        // Sets the minimum number of NumberPicker
        wordPicker.setMinValue(0);
        // Allows the selector wheel wraps around continuously
        wordPicker.setWrapSelectorWheel(true);

        Log.d(TAG, "<---- addPicker(): OFF");
    }

    /* Checks if the valid word was not entered in the edit field
        then a toast should be shown stating why it wasn't valid */
    private void checkInvalidWords() {
        Log.d(TAG, "----> checkInvalidWords(): ON");


        // HashSet will find duplicate Elements in ArrayList
        Set<String> uniqueWords = new HashSet<>(userWords);
        userWords.clear();
        userWords.addAll(uniqueWords);

        String string = userInput.getText().toString();

        // Shows a Toast Dialog that nothing was entered
        if (userInput.getText().toString().isEmpty()) {
            Log.i(TAG, "----> checkInvalidWords(): Empty field");

            Toast.makeText(MainActivity.this, R.string.noEntryToast, Toast.LENGTH_SHORT).show();
        }
        // Shows a toast dialog for the length of trimmed word being zero
        else if (string.trim().isEmpty()) {
            Log.i(TAG, "----> checkInvalidWords(): Trimmed word is zero");

            Toast.makeText(MainActivity.this, R.string.zeroLengthToast, Toast.LENGTH_SHORT).show();
        }
        /* Shows a toast if a duplicate word is entered.
           HashSet will find that word and add it to the Set. */
        else if (uniqueWords.contains(string)) {  //uniqueWords.size() > 0) {
            for (String uniqueWord : uniqueWords) {
                uniqueWords.remove(uniqueWord);
                Log.e(TAG, "----> checkInvalidWords(): Entered duplicate: " + uniqueWord);
            }
            uniqueWords.clear();

            Toast.makeText(MainActivity.this, R.string.uniqueStringToast, Toast.LENGTH_SHORT).show();
        } else {
            userWords.add(string.trim());

            // Log to console to get answers
            Log.d(TAG, "----> checkInvalidWords(): Added: " + string);
            // Print to console to test
            if (userWords.size() > 0) {
                Log.i(TAG, "----> checkInvalidWords(): THE STRINGS IN THE ARRAY ARE: " + userWords);
            }
            Log.i(TAG, "----> checkInvalidWords(): Number of elements in array: " + userWords.size());
        }
        userInput.setText(""); // Reset edit text

        Log.d(TAG, "<---- checkInvalidWords(): OFF");
    }

    /* Insures the present word at the selected index is shown.
        AlertDialog is used to show the selected word and has
        two buttons, "REMOVE" AND A "CLOSE" buttons */
    private void removeWordAlert() {
        Log.d(TAG, "----> removeWordAlert(): ON");

        // Sets AlertDialog class locally to be less dependant on the class
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.wordMsgAlert);

        // Gets the word whose index was picked
        int position = wordPicker.getValue();
        word = userWords.get(position);

        // Setting Dialog Message
        builder.setMessage(word);
        builder.setIcon(R.mipmap.ic_launcher);
        Log.i("----> removeWordAlert(): Alert word: ", word); // Check in console

        // Remove button - removes work chosen
        builder.setNegativeButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { // onClick within alert
                // Remove word here with .remove()
                if (userWords.size() > 0) {
                    userWords.remove(word);
                }

                // Log the value of the element
                Log.i("----> removeWordAlert(): onClick Removed: ", word);
                Log.i("----> removeWordAlert(): onClick New Size: ", String.valueOf(userWords.size()));

                // Confirm with user their choice with a toast
                Toast.makeText(MainActivity.this, R.string.alert_removed_confirmation, Toast.LENGTH_SHORT).show();
                dialog.cancel();

                formulateAverage();
                formulateMedian();
            }
        });
        // Setup of cancel button to dismiss dialog
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);

                // Confirm with user their choice with a toast
                Toast.makeText(MainActivity.this, R.string.alert_toast_closed, Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
        Log.d(TAG, "<---- removeWordAlert(): OFF");
    }

    // Method formulates an equation for the average word length
    private void formulateAverage() {
        // Variables to get average
        double wordCount;
        double sum = 0d;
        double average = 0d;

        for (int i = 0; i < userWords.size(); i++) {
            wordCount = userWords.get(i).length();
            sum = sum + wordCount;
            average = sum / userWords.size();
        }
        averageView.setText(String.format("  %.2f", average));

        Log.e(TAG, "----> formulateAverage(): " + sum + " / " + userWords.size() + " = " + average);
    }

    // Method formulates an equation for a word length median by sorting first
    private void formulateMedian() {
        Collections.sort(userWords);
        double median;

        if (userWords.size() > 0) {
            if (userWords.size() % 2 == 1) {
                // If number of words is odd
                median = userWords.get(userWords.size() / 2).length();
            } else {
                // If number of words is even
                median = (userWords.get(userWords.size() / 2).length()
                        + userWords.get(userWords.size() / 2 - 1).length()) / 2.0;
            }
            medianView.setText(String.format("  %.2f", median));

            Log.e(TAG, "----> formulateMedian(): # of words: " + userWords.size() + ", median: " + median);
        } else {
            medianView.setText(String.format("  %.2f", 0d));
        }
    }
}
