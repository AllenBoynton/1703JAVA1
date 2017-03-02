// Allen Boynton

// JAV1 - 1703

// MainActivity.java

package edu.fullsail.aboynton.boyntonallen_ce02;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Constants for testing code / determines 0-9
    private static final String TAG = "VERIFIED_WORKS";
    private static final int numOfElements = 10;

    // Initializing each editText
    private EditText[] editTexts;
    private int[] answerNums;
    private int[] userNumbers;

    // Generate Random class to create random numbers for each editText
    private Random randomNumber;

    // Initialize user's number of guesses as 4 and counting down as button is tapped
    private static int totalGuesses = 3;
    private boolean isGameComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set OnClick for submit button
        findViewById(R.id.submit_guess).setOnClickListener(listener);

        // Set views for each editText view
        editTexts = new EditText[]{
                (EditText) findViewById(R.id.number_one),
                (EditText) findViewById(R.id.number_two),
                (EditText) findViewById(R.id.number_three),
                (EditText) findViewById(R.id.number_four)
        };

        // Number holders used to assign the random number
        int answerNum1 = 0, answerNum2 = 0, answerNum3 = 0, answerNum4 = 0;
        answerNums = new int[]{answerNum1, answerNum2, answerNum3, answerNum4};

        // Using member variable to determine length
        userNumbers = new int[editTexts.length];

        // Using currentTimeMillis to calculate random number as recommended
        randomNumber = new Random(System.currentTimeMillis());
        restart();
    }

    // Steps of entering guess, comparing, updating and give results
    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Enters the user's choice number in the text
            for (int i = 0; i < editTexts.length; ++i) {
                if (checkInput(editTexts[i])) {
                    userNumbers[i] = Integer.parseInt(editTexts[i].getText().toString());
                } else {
                    // If a field is empty, a toast will inform the user
                    noTextToast();
                }
            }

            // Checks equality of strings for which ones are correct
            for (int i = 0; i < editTexts.length; ++i) {
                if (noTextToast()) {
                    return;
                } else if (String.valueOf(userNumbers[i]).equals(String.valueOf(answerNums[i]))) {
                    Log.i(TAG, "Equality: user: " + userNumbers[i] + ", answers: " + answerNums[i]);
                    isGameComplete = true;
                }
            }

            // Updates number color to let user know if too high/low 0-9
            for (int i = 0; i < userNumbers.length; ++i) {
                // Only changes color once all numbers are entered
                if (noTextToast()) {
                    return;
                } else {
                    updateColor(userNumbers[i], answerNums[i], editTexts[i]);
                    if (userNumbers[i] != answerNums[i]) {
                        isGameComplete = false;
                    }
                }
            }

            // check if guess count is zero and show appropriate alert dialogs
            if (totalGuesses <= 0) {
                // If zero show failure
                showFailAlerts();
            } else {
                // Decrement guess count and show user by toast
                showRemainingGuesses(totalGuesses--);
            }

            // if statement to check if game is complete...show winner's alert
            if (isGameComplete) {
                // Show success dialog
                showCorrectAlerts();
            }
        }
    };

    // Eliminates white space
    private boolean checkInput(EditText number) {
        return number.getText().toString().trim().length() > 0;
    }

    // Alert dialogs for each positive action
    private void showCorrectAlerts() {
        // Sets AlertDialog class for messaging the user
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.success);
        builder.setMessage(R.string.allCorrect);
        builder.setIcon(R.drawable.ic_action_name);
        builder.setPositiveButton(R.string.playAgain,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.restarted,
                                Toast.LENGTH_SHORT).show();
                        restart();
                    }
                });
        builder.show();
    }

    // Alert dialogs for each negative action
    private void showFailAlerts() {
        if (!isGameComplete) {
            // Sets AlertDialog class for messaging the user
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.failed);
            builder.setMessage(R.string.noGuesses);
            builder.setIcon(R.drawable.ic_action_name);
            builder.setPositiveButton(R.string.playAgain,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, R.string.restarted,
                                    Toast.LENGTH_SHORT).show();
                            restart();
                        }
                    });
            builder.show();
        }
    }

    // Toast alert if editViews are empty
    private boolean noTextToast() {
        Log.i(TAG, "----> noTextToast(editTexts.length): " + (editTexts.length));
//        showRemainingGuesses(totalGuesses);
        for (EditText editText : editTexts) {
            if (editText.getText().toString().isEmpty()) {
                Log.i(TAG, "----> noTextToast(empty): " + editText.getText().toString().isEmpty());
                Toast.makeText(this, R.string.ensureSubmit, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    // Toast alerts user to remaining guesses or guess
    private void showRemainingGuesses(int guesses) {
        if (guesses > 1) {
            Toast.makeText(this, guesses + getString(R.string.guessesLeft),
                    Toast.LENGTH_SHORT).show();
        } else if (guesses == 1) {
            Toast.makeText(this, guesses + getString(R.string.oneGuessLeft),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Method changes view depending on guessed number compared to random number
    private void updateColor(int guess, int number, EditText view) {
        if (guess == number) {
            view.setTextColor(Color.GREEN);
        } else if (guess < number) {
            view.setTextColor(Color.BLUE);
        } else if (guess > number) {
            view.setTextColor(Color.RED);
        }
    }

    // Method restarts game and initializes random numbers
    private void restart() {
        // Resets text color and edit texts to empty
        for (EditText editText : editTexts) {
            editText.setTextColor(Color.BLACK);
            editText.setText("");
        }

        // Resets the number of elements in the guess range
        for (int i = 0; i < answerNums.length; i++) {
            answerNums[i] = randomNumber.nextInt(numOfElements);
        }

        // Resets # of guesses with new game
        totalGuesses = 3;
    }
}
