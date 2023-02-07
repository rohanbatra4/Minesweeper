import java.util.Scanner;
import java.util.Random;

public class Minesweeper {
  static Scanner sc = new Scanner(System.in);
  static Random rand = new Random();

  static int ROWS = 10;
  static int COLS = 10;
  static int MINES = 10;
  static char[][] board = new char[ROWS][COLS];
  static boolean[][] mines = new boolean[ROWS][COLS];
  static boolean[][] revealed = new boolean[ROWS][COLS];

  static void generateMines() {
    int minesPlanted = 0;
    while (minesPlanted < MINES) {
      int row = rand.nextInt(ROWS);
      int col = rand.nextInt(COLS);
      if (!mines[row][col]) {
        mines[row][col] = true;
        minesPlanted++;
      }
    }
  }

  static void generateBoard() {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        board[row][col] = '-';
        int minesAround = 0;
        if (mines[row][col]) {
          board[row][col] = '*';
        } else {
          if (row > 0) {
            if (mines[row - 1][col]) {
              minesAround++;
            }
          }
          if (row < ROWS - 1) {
            if (mines[row + 1][col]) {
              minesAround++;
            }
          }
          if (col > 0) {
            if (mines[row][col - 1]) {
              minesAround++;
            }
          }
          if (col < COLS - 1) {
            if (mines[row][col + 1]) {
              minesAround++;
            }
          }
          if (row > 0 && col > 0) {
            if (mines[row - 1][col - 1]) {
              minesAround++;
            }
          }
          if (row > 0 && col < COLS - 1) {
            if (mines[row - 1][col + 1]) {
              minesAround++;
            }
          }
          if (row < ROWS - 1 && col > 0) {
            if (mines[row + 1][col - 1]) {
              minesAround++;
            }
          }
          if (row < ROWS - 1 && col < COLS - 1) {
            if (mines[row + 1][col + 1]) {
              minesAround++;
            }
          }
          board[row][col] = (char) (minesAround + '0');
        }
      }
    }
  }

  static void printBoard() {
    System.out.println();
    System.out.print("  ");
    for (int i = 0; i < COLS; i++) {
      System.out.print(i);
    }
    System.out.println();
    for (int row = 0; row < ROWS; row++) {
      System.out.print(row + " ");
      for (int col = 0; col < COLS; col++) {
        if (revealed[row][col]) {
          System.out.print(board[row][col]);
        } else {
          System.out.print('-');
        }
      }
      System.out.println();
    }
  }

  static void reveal(int row, int col) {
    revealed[row][col] = true;
    if (board[row][col] == '0') {
      if (row > 0) {
        if (!revealed[row - 1][col]) {
          reveal(row - 1, col);
        }
      }
      if (row < ROWS - 1) {
        if (!revealed[row + 1][col]) {
          reveal(row + 1, col);
        }
      }
      if (col > 0) {
        if (!revealed[row][col - 1]) {
          reveal(row, col - 1);
        }
      }
      if (col < COLS - 1) {
        if (!revealed[row][col + 1]) {
          reveal(row, col + 1);
        }
      }
      if (row > 0 && col > 0) {
        if (!revealed[row - 1][col - 1]) {
          reveal(row - 1, col - 1);
        }
      }
      if (row > 0 && col < COLS - 1) {
        if (!revealed[row - 1][col + 1]) {
          reveal(row - 1, col + 1);
        }
      }
      if (row < ROWS - 1 && col > 0) {
        if (!revealed[row + 1][col - 1]) {
          reveal(row + 1, col - 1);
        }
      }
      if (row < ROWS - 1 && col < COLS - 1) {
        if (!revealed[row + 1][col + 1]) {
          reveal(row + 1, col + 1);
        }
      }
    }
  }

  static boolean checkWin() {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (!mines[row][col] && !revealed[row][col]) {
          return false;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    generateMines();
    generateBoard();
    while (true) {
      printBoard();
      System.out.print("Enter row: ");
      int row = sc.nextInt();
      System.out.print("Enter col: ");
      int col = sc.nextInt();
      try {
        if (mines[row][col]) {
          printBoard();
          System.out.println("You lost!");
          break;
        } else {
          reveal(row, col);
          if (checkWin()) {
            printBoard();
            System.out.println("You won!");
            break;
          }
        }
      } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Invalid coordinates!");
      }
    }
  }
}
