// Allen Boynton

// JAV1 - 1703

// SudokuBoard.java

package edu.fullsail.aboynton.boyntonallen_ce04;

class SudokuBoard extends MainActivity {

    private final int[] numberArray1;
    private final int[] numberArray2;
    private final int[] numberArray3;
    private final int[] numberArray4;

    SudokuBoard(int[] numberArray1, int[] numberArray2, int[] numberArray3, int[] numberArray4) {
        this.numberArray1 = numberArray1;
        this.numberArray2 = numberArray2;
        this.numberArray3 = numberArray3;
        this.numberArray4 = numberArray4;
    }

    int[] getNumberArray1() {

        return numberArray1;
    }

    int[] getNumberArray2() {
        return numberArray2;
    }

    int[] getNumberArray3() {
        return numberArray3;
    }

    int[] getNumberArray4() {
        return numberArray4;
    }
}

