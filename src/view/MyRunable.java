package view;

import model.ChessColor;

import java.awt.*;

import static view.ChessGameFrame.*;
import static view.Chessboard.getRecordchessboard;
import static view.Chessboard.setCurrentColor;

public class MyRunable implements Runnable {
    public ChessGameFrame chessGameFrame;

    public MyRunable(ChessGameFrame chessGameFrame) {
        this.chessGameFrame = chessGameFrame;
    }

    @Override
    public void run() {
        for (int i = 0; i < Chessboard.recordchessboard.size(); i++) {
            chessGameFrame.getGameController().getChessboard().getLastChessBoard(Chessboard.recordchessboard.size() - i);
            if (Chessboard.getRecordchessboard().get(getRecordchessboard().size() - 1).charAt(Chessboard.getRecordchessboard().get(getRecordchessboard().size() - 1).length() - 1) == 'w') {
                setCurrentColor(ChessColor.BLACK);
            }
            if (Chessboard.getRecordchessboard().get(getRecordchessboard().size() - 1).charAt(Chessboard.getRecordchessboard().get(getRecordchessboard().size() - 1).length() - 1) == 'b') {
                setCurrentColor(ChessColor.WHITE);
            }

            chessGameFrame.repaint();
            try {
                showwhite();
                if (Chessboard.getRecordchessboard().get(i).charAt(Chessboard.getRecordchessboard().get(getRecordchessboard().size() - 1).length() - 1) == 'w') {
                    showwhite();
                }
                if (Chessboard.getRecordchessboard().get(i).charAt(Chessboard.getRecordchessboard().get(getRecordchessboard().size() - 1).length() - 1) == 'b') {
                    showblack();
                }
                chessGameFrame.repaint();
//                if (Chessboard.getRecordchessboard().get(i).charAt(Chessboard.getRecordchessboard().get(getRecordchessboard().size() - 1).length() - 1) == 'b') {
//                    showwhite();
//                }
//                if (Chessboard.getRecordchessboard().get(i).charAt(Chessboard.getRecordchessboard().get(getRecordchessboard().size() - 1).length() - 1) == 'w') {
//                    showblack();
//                }
                Thread.sleep(500);


            } catch (Exception e) {

            }
        }
        chessGameFrame.getGameController().getChessboard().initall();
        showwhite();
        setCurrentColor(ChessColor.WHITE);
        chessGameFrame.repaint();


    }


}
