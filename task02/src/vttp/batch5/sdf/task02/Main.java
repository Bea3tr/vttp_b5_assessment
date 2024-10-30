package vttp.batch5.sdf.task02;

import java.io.*;
import java.util.*;

public class Main {

	private static String PLAYER = "X";
	private static String OPPONENT = "O";
	private static String BLANK = ".";

	public static String[][] tttboard(String file) throws Exception {
		// Read file and create board
		Reader read = new FileReader(file);
		BufferedReader br = new BufferedReader(read);
		// Create board
		String[][] board = new String[3][3];

		int idx = 0;
		String line = "";
		// Populate board
		while (line != null) {
			line = br.readLine();
			if (line == null)
				break;
			String[] pos = line.split("");
			board[idx] = pos;
			idx++;
		}
		br.close();
		return board;
	}

	public static List<int[]> getEmptyPos(String[][] board) {
		// To store empty positions
		List<int[]> emptyPos = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j].equals(BLANK)) {
					int[] pos = { i, j };
					emptyPos.add(pos);
				}
			}
		}
		return emptyPos;
	}

	public static int evaluateBoard(String[][] board) {
		boolean XWin = false;
		boolean OWin = false;
		// Check rows
		for (int row = 0; row < board.length; row++) {
			if (board[row][0].equals(board[row][1]) && board[row][1].equals(board[row][2])) {
				if (board[row][0].equals(PLAYER))
					XWin = true;
			}
			else {
				int countO = 0;
				int countS = 0;
				for (int col = 0; col < board[0].length; col++) {
					if (board[row][col].equals(OPPONENT))
						countO++;
					else if (board[row][col].equals(BLANK))
						countS++;
				}
				if (countO == 2 && countS == 1)
					OWin = true;
			}
		}

		// Check columns
		for (int col = 0; col < board[0].length; col++) {
			if (board[0][col].equals(board[1][col]) && board[1][col].equals(board[2][col])) {
				if (board[0][col].equals(PLAYER)){
					XWin = true;
				}
			} else {
				int countO = 0;
				int countS = 0;
				for (int row = 0; row < board.length; row++) {
					if (board[row][col].equals(OPPONENT))
						countO++;
					else if (board[row][col].equals(BLANK))
						countS++;
				}
				if (countO == 2 && countS == 1) 
					OWin = true;
			}
		}

		// Check diagonals
		if ((board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) ||
				(board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))) {
			if (board[1][1].equals(PLAYER))
				XWin = true;
		}
		else {
			int countO = 0;
			int countS = 0;
			for(int i = 0; i < board.length; i++) {
				if(board[i][i].equals(OPPONENT))
					countO++;
				else if (board[i][i].equals(BLANK))
					countS++;
			}
			if (countO == 2 && countS == 1)
					OWin = true;
			else {
				// Reset counts
				countO = 0;
				countS = 0;
			}	
			for(int i = board.length - 1; i >= 0; i--) {
				for(int j = 0; j < board[0].length; j++) {
					if(board[i][j].equals(OPPONENT))
						countO++;
					else if (board[i][j].equals(BLANK))
						countS++;
				}
			}
			if (countO == 2 && countS == 1)
					OWin = true;
		}
		// Prioritise X
		if(XWin)
			return 1;
		else if(OWin)
			return -1;
		else
			return 0;
	}

	public static void printBoard(String[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	public static int getUtility(String[][] board, int[] pos) {
		board[pos[0]][pos[1]] = PLAYER;
		int utility = evaluateBoard(board);
		return utility;
	}

	public static void main(String[] args) throws Exception {
		// Take in board file as argument
		if (args.length < 1) {
			System.out.println("Missing argument");
			System.exit(0);
		}
		String file = args[0];
		System.out.printf("Processing: %s\n\n", file);

		String[][] board = tttboard(file);
		System.out.println("Board: ");
		printBoard(board);
		System.out.println("----------------------");
		List<int[]> pos = getEmptyPos(board);
		for(int[] p : pos) {
			int utility = getUtility(board, p);
			board[p[0]][p[1]] = BLANK;
			System.out.printf("y=%d, x=%d, utility=%d\n", p[0], p[1], utility);
		}
	}
}
