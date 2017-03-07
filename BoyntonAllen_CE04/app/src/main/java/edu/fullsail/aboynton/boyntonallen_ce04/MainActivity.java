// Allen Boynton

// JAV1 - 1703

// MainActivity.java

package edu.fullsail.aboynton.boyntonallen_ce04;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Constructor parameters from the SudokuBoard class
    private SudokuBoard sudokuBoard1;
    private SudokuBoard sudokuBoard2;
    private SudokuBoard sudokuBoard3;

    // All double int arrays needed for the game...I think???
    private int[][] userNumbers;
    private EditText[][] boardViews;

    // 3 game boards provided in instructions of project
    private int[][] gameNumbers1;
    private int[][] gameNumbers2;
    private int[][] gameNumbers3;
//    private int[][][] gameNumbers;

    // Arrays for row, column, and corner equality
    private int[][] rowArrays;
    private int[][] columnArrays;
    private int[][] sectionArrays;

    // Generate random generator
//    private Random randomGenerator;
//
//    // Constants for generating which game will be chosen
//    private static final int randomGameNumber = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "----> onCreate(): ON");

        userNumbers = new int[4][4];

        // Numbers for board 1
        gameNumbers1 = new int[][]{{2, 1, 4, 3}, {3, 4, 1, 2}, {4, 3, 2, 1}, {1, 2, 3, 4}};

        // Numbers for board 2
        gameNumbers2 = new int[][]{{4, 1, 2, 3}, {2, 3, 1, 4}, {3, 2, 4, 1}, {1, 4, 3, 2}};

        // Numbers for board 3
        gameNumbers3 = new int[][]{{1, 2, 4, 3}, {4, 3, 1, 2}, {3, 1, 2, 4}, {2, 4, 3, 1}};

        // Array of all gameNumbers
//        gameNumbers = new int[][][]{gameNumbers1, gameNumbers2, gameNumbers3};

        // Assign ids to all edit texts
        boardViews = new EditText[][]{
                {
                        (EditText) findViewById(R.id.edit_r1_c1), (EditText) findViewById(R.id.edit_r1_c2),
                        (EditText) findViewById(R.id.edit_r1_c3), (EditText) findViewById(R.id.edit_r1_c4)
                },
                {
                        (EditText) findViewById(R.id.edit_r2_c1), (EditText) findViewById(R.id.edit_r2_c2),
                        (EditText) findViewById(R.id.edit_r2_c3), (EditText) findViewById(R.id.edit_r2_c4)
                },
                {
                        (EditText) findViewById(R.id.edit_r3_c1), (EditText) findViewById(R.id.edit_r3_c2),
                        (EditText) findViewById(R.id.edit_r3_c3), (EditText) findViewById(R.id.edit_r3_c4)
                },
                {
                        (EditText) findViewById(R.id.edit_r4_c1), (EditText) findViewById(R.id.edit_r4_c2),
                        (EditText) findViewById(R.id.edit_r4_c3), (EditText) findViewById(R.id.edit_r4_c4)
                }

        };

        // Creating an array for each row to check users input
        int[] row1 = new int[]{R.id.edit_r1_c1, R.id.edit_r1_c2, R.id.edit_r1_c3, R.id.edit_r1_c4};
        int[] row2 = new int[]{R.id.edit_r2_c1, R.id.edit_r2_c2, R.id.edit_r2_c3, R.id.edit_r2_c4};
        int[] row3 = new int[]{R.id.edit_r3_c1, R.id.edit_r3_c2, R.id.edit_r3_c3, R.id.edit_r3_c4};
        int[] row4 = new int[]{R.id.edit_r4_c1, R.id.edit_r4_c2, R.id.edit_r4_c3, R.id.edit_r4_c4};

        // Collection of rows within the grid
        rowArrays = new int[][]{row1, row2, row3, row4};
        Log.i(TAG, "----> onCreate() : rowArrays.length = " + rowArrays.length);

        // Creating an array for each column to check users input
        int[] col1 = new int[]{R.id.edit_r1_c1, R.id.edit_r2_c1, R.id.edit_r3_c1, R.id.edit_r4_c1};
        int[] col2 = new int[]{R.id.edit_r1_c2, R.id.edit_r2_c2, R.id.edit_r3_c2, R.id.edit_r4_c2};
        int[] col3 = new int[]{R.id.edit_r1_c3, R.id.edit_r2_c3, R.id.edit_r3_c3, R.id.edit_r4_c3};
        int[] col4 = new int[]{R.id.edit_r1_c4, R.id.edit_r2_c4, R.id.edit_r3_c4, R.id.edit_r4_c4};

        // Collection of columns within the grid
        columnArrays = new int[][]{col1, col2, col3, col4};
        Log.i(TAG, "----> onCreate() : columnArrays.length = " + columnArrays.length);

        // Creating an array for each section to check users input
        int[] section1_1 = new int[]{R.id.edit_r1_c1, R.id.edit_r1_c2, R.id.edit_r2_c1, R.id.edit_r2_c2};
        int[] section1_2 = new int[]{R.id.edit_r1_c3, R.id.edit_r1_c4, R.id.edit_r2_c3, R.id.edit_r2_c4};
        int[] section2_1 = new int[]{R.id.edit_r3_c1, R.id.edit_r3_c2, R.id.edit_r4_c1, R.id.edit_r4_c2};
        int[] section2_2 = new int[]{R.id.edit_r3_c3, R.id.edit_r3_c4, R.id.edit_r4_c3, R.id.edit_r4_c4};

        // Creating an array for each corner to check users input
        sectionArrays = new int[][]{section1_1, section1_2, section2_1, section2_2};
        Log.i(TAG, "----> onCreate() : sectionArrays.length = " + sectionArrays.length);

        /* ********************************* Game Boards *****************************************/

        // Getting the parameters for each board in order to assign to the views
        try {
            sudokuBoard1 = new SudokuBoard(sudokuBoard1.getNumberArray1(), sudokuBoard1.getNumberArray2(), sudokuBoard1.getNumberArray3(), sudokuBoard1.getNumberArray4());
            sudokuBoard2 = new SudokuBoard(sudokuBoard2.getNumberArray1(), sudokuBoard2.getNumberArray2(), sudokuBoard2.getNumberArray3(), sudokuBoard2.getNumberArray4());
            sudokuBoard3 = new SudokuBoard(sudokuBoard3.getNumberArray1(), sudokuBoard3.getNumberArray2(), sudokuBoard3.getNumberArray3(), sudokuBoard3.getNumberArray4());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sets the OnClickListener for the board views
        setBoardOnListeners();
//        replay();

        Log.d(TAG, "<---- onCreate(): OFF");
    }

    private void setBoardOnListeners() {
        // Create a common OnClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < boardViews.length; ++i) {
                    for (EditText[] boardView : boardViews) {
                        for (int[] aGameNumbers1 : gameNumbers1) {
                            if (boardView[i].getText().toString().isEmpty()) {
                                boardView[i].setText(aGameNumbers1[gameNumbers1.length]);
                                Log.i(TAG, "----> onClick(boardViews.length)" + boardViews.length);
                            } else {
                                // Show a Toast Dialog if edit views have empty ones
                                Toast.makeText(MainActivity.this,
                                        R.string.toastIncomplete, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            /*Attempting to check users answers to each game board. I could not
                             *figure out how to figure in for the numbers that are initially visible.
                             *It seems efficient but I am also aware that all boards are not all visible.*/

                            if (Objects.equals(String.valueOf(userNumbers[i]), String.valueOf(gameNumbers1[i])) ||
                                    Objects.equals(String.valueOf(userNumbers[i]), String.valueOf(gameNumbers2[i])) ||
                                    Objects.equals(String.valueOf(userNumbers[i]), String.valueOf(gameNumbers3[i]))) {
                                // Show an Alert Dialog
                                checkHasErrorsAlert();
                            } else {
                                // Show an Alert Dialog
                                checkIsCorrectAlert();
                            }

                            if (Arrays.deepToString(rowArrays).equals(String.valueOf(gameNumbers1[i][i])) ||
                                    Arrays.deepToString(columnArrays).equals(String.valueOf(gameNumbers1[i][i])) ||
                                    Objects.equals(String.valueOf(sectionArrays[i]), String.valueOf(gameNumbers1[i][i]))) {
                                // Show an Alert Dialog for being not correct
                                checkHasErrorsAlert();
                            } else {
                                // Show an Alert Dialog for being correct
                                checkIsCorrectAlert();
                            }
                        }
                    }
                }
            }
        };
        // Set up onClickListener for checker buttons
        findViewById(R.id.button_checker).setOnClickListener(listener);
    }

    private void checkHasErrorsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.alertBoardChecked)
                .setMessage(R.string.alertHasErrors)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.closeToast, Toast.LENGTH_SHORT);
                    }
                });
        builder.show();
    }

    private void checkIsCorrectAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.alertBoardChecked)
                .setMessage(R.string.alertComplete)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.resetBoard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.resetToast, Toast.LENGTH_SHORT);
                    }
                });
        builder.show();
    }

    // Method restarts game and initializes random numbers
//    private void replay() {
//        // Generate Random class to create random number
//
//        // Resets the number of elements in the guess range
//        for (int[][] gameNumber : gameNumbers) {
//            gameNumber[3][3] = randomGenerator.nextInt(randomGameNumber);
//            Log.d(TAG, "----> replay(): " + gameNumber.length);
//        }
//    }
}
