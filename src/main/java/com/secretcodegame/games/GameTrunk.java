package com.secretcodegame.games;

import java.util.HashMap;
import java.util.Map;

public class GameTrunk {

    private SecretNumberGame numberGame;
    private SecretWordGame letterGame;
    private InstructionManual instructionManual;
    private static final String CORRECT_POSITION = "~";
    private static final String CORRECT_CHARACTER = "!";
    private static final String WRONG_CHARACTER = "0";

    public GameTrunk() {
        numberGame = new SecretNumberGame();
        letterGame = new SecretWordGame();
        instructionManual = new InstructionManual();
    }

    private String historyLog;
    private int attemptNumber;


    public String getHistoryLog() {
        return historyLog;
    }

    public void setHistoryLog(String historyLog) {
        this.historyLog = historyLog;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public void setNumberAnswer() {
        numberGame.setNumberAnswer();
    }

    public void setWordAnswer(){
        letterGame.setWordAnswer();
    }

    public void printInstructions() {
        String instructions = instructionManual.getInstructions();
        System.out.println(instructions);
    }


    public String runGame(String userGuess, int gameMode) {

        Map<Character, Integer> answerMap = new HashMap<>();
        boolean validGuess = true;
        char[] guess = userGuess.toCharArray();
        int correctPosCount = 0;
        int correctCharCount = 0;
        int wrongCharCount = 0;
        String result;

        switch(gameMode){
            case 1 :
                answerMap = numberGame.getNumberAnswer();
                for (int i = 0; i < userGuess.length(); i++) {
                    if (!Character.isDigit(userGuess.charAt(i))) {
                        validGuess = false;
                    }
                }
                break;
            case 2 :
                answerMap = letterGame.getWordAnswer();
                for (int i = 0; i < userGuess.length(); i++) {
                    if (!Character.isLetter(userGuess.charAt(i))) {
                        validGuess = false;
                    }
                }
                break;
            default :
                break;
        }


        if ((userGuess.length() != 5) || (!validGuess)) {
            result = "\n***Don't do that. All guesses must consist of five digits.***";
            System.out.println(result);

        } else {
            for (int i = 0; i < guess.length; i++) {
                if (answerMap.containsKey(guess[i])) {
                    if (answerMap.get(guess[i]) == i) {
                        correctPosCount++;
                    } else {
                        correctCharCount++;
                    }
                } else {
                    wrongCharCount++;
                }
            }

            result = (userGuess + " : " + CORRECT_POSITION.repeat(correctPosCount) + CORRECT_CHARACTER.repeat(correctCharCount) + WRONG_CHARACTER.repeat(wrongCharCount));
            addHistoryLog(result);

            if (correctPosCount == 5) {
                System.out.println("\n**********************************");
                System.out.println("       YOU'RE A WINNER!    ");
                System.out.println("    The secret code was: " + userGuess);
                System.out.println("**********************************");
                System.out.println("  You won in " + getAttemptNumber() + " tries. Well done!");
                System.out.println("**********************************");
                result = "WINNER";
            }
        }
        return result;
    }


    public void addHistoryLog(String newGuess){
       String history = getHistoryLog() + "\n" + newGuess;
       setHistoryLog(history);
       setAttemptNumber(getAttemptNumber() + 1);
    }
}




