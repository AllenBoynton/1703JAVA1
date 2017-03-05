// Allen Boynton

// JAV1 - 1703

// MainActivity.java

package edu.fullsail.aboynton.boyntonallen_ce01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Reports/Prints to the console
    private static final String TAG = "MainActivity";

    // TextView used to display the output
    private TextView textView;

    // Using arrays to loop through the buttons in numbers and operators
    private static int[] numberButtons;
    private static int[] operatorButtons;

    // Integers used to make the calculations
    private int number1 = 0;

    private String operator;

    // A check if the last key entered is a number
    private boolean lastNumber;

    // A check to make sure there are no errors
    private boolean isEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "----> onCreate(): ON");

        // Display where users entry is and entire expression remains
        this.textView = (TextView) findViewById(R.id.textView);
        textView.setText("");

        // IDs of all the number buttons
        numberButtons = new int[]{R.id.button0,
                R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9
        };

        // IDs of all the operator buttons
        operatorButtons = new int[]{R.id.button_division, R.id.button_multiplication,
                R.id.button_subtraction, R.id.button_addition
        };

        // Sets the OnClickListener for number buttons
        setNumberOnClickListener();

        // Sets the OnClickListener to operator buttons
        setOperatorOnClickListener();

        Log.i(TAG, "<---- onCreate(): OFF");
    }

    // Sets the number buttons to OnClickListeners
    private void setNumberOnClickListener() {
        // Create a common OnClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View sender) {
                Button button = (Button) sender;
                if (textView.getText().toString().isEmpty()) {
                    textView.setText(button.getText());
                    Log.i(TAG, "----> setNumberOnClickListener(1) " + textView.getText().toString());
                    isEmpty = false;
                } else {
                    textView.setText(""); // Allows equation to finish but loses display
                    textView.append(button.getText());
                    Log.i(TAG, "<---- setNumberOnClickListener(2) " + textView.getText().toString());
                }
                lastNumber = true;
            }
        };
        // Assign the listener to the array of number buttons
        for (int numberButton : numberButtons) {
            findViewById(numberButton).setOnClickListener(listener);
        }
    }

    // Sets the operators OnClickListener
    private void setOperatorOnClickListener() {
        // Create an OnClickListener for operators
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View sender) {
                Button button = (Button) sender;
                // Check that an operator can only be entered if a number is present & not empty
                if (lastNumber && !isEmpty) {
                    try {
                        // Defines what the operators are
                        if (button.getText().toString().equals("+")) {
                            operator = "+";
                        } else if (button.getText().toString().equals("-")) {
                            operator = "-";
                        } else if (button.getText().toString().equals("*")) {
                            operator = "*";
                        } else if (button.getText().toString().equals("/")) {
                            operator = "/";
                        }

                        number1 = Integer.parseInt(textView.getText().toString());

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    textView.append(button.getText());
                    lastNumber = false;

                    Log.i(TAG, "----> setOperatorOnClickListener() " + operator);
                }
            }
        };
        // Assign the listener to all the operator buttons
        for (int operatorButton : operatorButtons) {
            findViewById(operatorButton).setOnClickListener(listener);
        }

        // Equals button
        findViewById(R.id.button_equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View sender) {
                if (lastNumber && !isEmpty) {
                    double result = 0;

                    try {
                        int number2 = Integer.parseInt(textView.getText().toString());
                        // Iterate through to correct function
                        switch (operator) {
                            case "+":
                                result = number1 + number2;
                                break;
                            case "-":
                                result = number1 - number2;
                                break;
                            case "*":
                                result = number1 * number2;
                                break;
                            case "/":
                                if (number2 == 0) {
                                    textView.setText(R.string.e);
                                } else {
                                    result = number1 / number2;
                                }
                                break;
                        }
                        // Calculate the result and display
                        textView.setText(number1 + operator + number2 + "=" + result);
                        Log.d(TAG, "<---- onClick() = " + number1 + operator + number2 + " = " + result);
                        lastNumber = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Clear button
        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View sender) {
                textView.setText("");
                // Need to reset the setting from each equation
                lastNumber = false;
                isEmpty = false;
            }
        });
    }
}
