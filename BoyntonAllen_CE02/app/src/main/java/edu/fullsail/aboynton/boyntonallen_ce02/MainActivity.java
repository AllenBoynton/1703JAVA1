// Allen Boynton

// JAV1 - 1703

// MainActivity.java

package edu.fullsail.aboynton.boyntonallen_ce02;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Constants for testing code / determines 0-9
    private static final int randomNumber = 10;

    // Initializing each editText
    private EditText[] editTexts;
    private int[] answerNums;
    private int[] userNumbers;

    // Generate Random class to create random numbers for each editText
    private Random randomGenerator;

    // Initialize user's number of guesses as 4 and counting down as button is tapped
    private int totalGuesses;
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

        // Create an array of integers
        answerNums = new int[4];

        // Using member variable to determine length
        userNumbers = new int[editTexts.length];

        // Using currentTimeMillis to calculate random number as recommended
        randomGenerator = new Random(System.currentTimeMillis());
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
                    updateColor(userNumbers[i], answerNums[i], editTexts[i]);
                } else {
                    Toast.makeText(MainActivity.this, R.string.ensureSubmit, Toast.LENGTH_SHORT).show();
                    return;
                }
                isGameComplete = String.valueOf(userNumbers[i]).equals(String.valueOf(answerNums[i]));
            }

            // check if guess count is zero and show appropriate alert dialogs
            if (totalGuesses == 0) {
                showFailAlerts(); // If zero show failure
            } else {
                showRemainingGuesses(totalGuesses-=1);
            }

            // if statement to check if game is complete...show winner's alert
            if (isGameComplete) {
                showCorrectAlerts(); // Show success dialog
            }
        }
    };

    // Check if edit text contains a valid number
    private boolean checkInput(EditText number) {
        return number.getText().toString().trim().length() > 0;
    }

    // Alert dialogs for each positive action
    private void showCorrectAlerts() {
        // Sets AlertDialog class for messaging the user
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setTitle(R.string.success)
        .setMessage(R.string.allCorrect)
        .setIcon(R.drawable.ic_action_name)
        .setPositiveButton(R.string.playAgain,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restart();
                    }
                });
        builder.show();
    }

    // Alert dialogs for each negative action
    private void showFailAlerts() {
        if (!isGameComplete) {
            // Sets AlertDialog class for messaging the user
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
            .setTitle(R.string.failed)
            .setMessage(R.string.noGuesses)
            .setIcon(R.drawable.ic_action_name)
            .setPositiveButton(R.string.playAgain,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            restart();
                        }
                    });
            builder.show();
        }
    }

    // Toast alerts user to remaining guesses or guess
    private void showRemainingGuesses(int guesses) {
        if (guesses == 4) {
            Toast.makeText(MainActivity.this, getString(R.string.startGuessToast),
                    Toast.LENGTH_LONG).show();
        } else if ((guesses > 1) && (guesses < 4)) {
            Toast.makeText(MainActivity.this, guesses + getString(R.string.guessesLeft),
                    Toast.LENGTH_SHORT).show();
        } else if (guesses == 1) {
            Toast.makeText(MainActivity.this, guesses + getString(R.string.oneGuessLeft),
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
            answerNums[i] = randomGenerator.nextInt(randomNumber);
        }

        // Show initial number of guesses...even though it is not required
        showRemainingGuesses(totalGuesses = 4);
    }
}
