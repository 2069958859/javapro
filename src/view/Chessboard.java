package view;


import controller.ClickController;
import model.*;
import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static view.ChessGameFrame.*;


/**
 * 这个类表示面板上的棋盘组件对象
 * 初始化棋盘
 * getChessComponents、getCurrentColor方法
 * putChessOnBoard(ChessComponent chessComponent)方法
 * 交换chessconponent,交换颜色方法
 * record\getrecord
 * 数棋子点
 * loadGame
 * 重写paintComponent
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */

    static int x = 0;

    public static void setX(int x) {
        Chessboard.x = x;
    }
//     static int huiqicishu=
    public static boolean swap;
    private static final int CHESSBOARD_SIZE = 8;
    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    public static ChessColor currentColor = ChessColor.WHITE;

    public ClickController getClickController() {
        return clickController;
    }

    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    public static ArrayList<String> recordchessboard = new ArrayList<>();

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

//    public boolean isDead = false;
    public boolean isDeadw = false;
    public boolean isDeadb = false;

    public boolean staybk = true;
    public boolean staywk = true;
    public boolean staybr = true;
    public boolean staywr = true;
    public static int count = 0;

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        recordchessboard.add("R0N0B0Q0K0B0N0R0*P0P0P0P0P0P0P0P0*_0_0_0_0_0_0_0_0*_0_0_0_0_0_0_0_0*_0_0_0_0_0_0_0_0*_0_0_0_0_0_0_0_0*p0p0p0p0p0p0p0p0*r0n0b0q0k0b0n0r0*w");

        initall();
    }

    public static void setCurrentColor(ChessColor currentColor) {
        Chessboard.currentColor = currentColor;
    }

    public void initall() {
        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        initKingOnBoard(0, CHESSBOARD_SIZE - 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 4, ChessColor.WHITE);

        initBishopOnBoard(0, CHESSBOARD_SIZE / 8 * 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE / 8 * 2, ChessColor.WHITE);

        initQueenOnBoard(0, CHESSBOARD_SIZE - 5, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 5, ChessColor.WHITE);

        initKnightOnBoard(0, CHESSBOARD_SIZE - 7, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 7, ChessColor.WHITE);
        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);

        }

    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public static ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(),
                col = chessComponent.getChessboardPoint().getY();
        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }




    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {  //将死
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);

            if (chess2 instanceof KingChessComponent && chess2.getChessColor().equals(ChessColor.WHITE)){
                isDeadw=true;
            }
            else if (chess2 instanceof KingChessComponent && chess2.getChessColor().equals(ChessColor.BLACK)){
                isDeadb=true;
            }


            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(),
                col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(),
                col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

//        if (chess1 instanceof KingChessComponent && chess1.getChessColor().equals(ChessColor.BLACK)) {
//            staybk = false;
//        }
//        if (chess1 instanceof KingChessComponent && chess1.getChessColor().equals(ChessColor.WHITE)) {
//            staywk = false;
//        }
//        if (chess1 instanceof RookChessComponent && chess1.getChessColor().equals(ChessColor.BLACK)) {
//            staybr = false;
//        }
//        if (chess1 instanceof RookChessComponent && chess1.getChessColor().equals(ChessColor.WHITE)) {
//            staywr = false;
//        }


            record(row1, col1, row2, col2);
        AtomicInteger count = new AtomicInteger(0);
        setN(count);
        setX(0);
        changelabel();
        chess1.repaint();
        chess2.repaint();
        String warningw = "Black win!";
        String warningb = "White win!";
//            ChessGameFrame.dead(warningw);
        if(isjiangsi()){

        }
        if (isDeadw) {
            dead2(warningw);
            initall();
            setCurrentColor(ChessColor.BLACK);
            showwhite();
            repaint();
            isDeadw=false;
        }
        else if (isDeadb) {
            dead2(warningb);
            initall();
            setCurrentColor(ChessColor.BLACK);
            showwhite();
            repaint();
            isDeadb=false;

        }

    }



    public boolean otherChessCanMoveTo(ChessComponent chess2) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(chessComponents[i][j] instanceof EmptySlotComponent)) {
                    ChessComponent chess1 = chessComponents[i][j];
                    if (chess1.getChessColor() != chess2.getChessColor() && chess1.canMoveTo(chessComponents, chess2.getChessboardPoint())) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public boolean kingCanMoveTo(ChessComponent king) {//king能挪走来躲避
        int inix = king.getChessboardPoint().getX();
        int iniy = king.getChessboardPoint().getY();
        ChessComponent[][] cc = getChessComponents();
        int finx1 = inix + 1;
        int finx2 = inix - 1;
        int finy1 = iniy + 1;
        int finy2 = iniy - 1;
        if ((inix < 0 || iniy - 1 > 7 || inix > 7 || iniy - 1 < 0) || !(cc[inix][iniy - 1] instanceof EmptySlotComponent) || king.getChessColor() == cc[inix][iniy - 1].getChessColor() || otherChessCanMoveTo(cc[inix][iniy - 1])) {
            return false;
        }
        if ((inix < 0 || iniy + 1 > 7 || inix > 7 || iniy + 1 < 0) || !(cc[inix][iniy + 1] instanceof EmptySlotComponent) || king.getChessColor() == cc[inix][iniy + 1].getChessColor() || otherChessCanMoveTo(cc[inix][iniy + 1])) {
            return false;
        }
        if ((inix - 1 < 0 || iniy > 7 || inix - 1 > 7 || iniy < 0) || !(cc[inix - 1][iniy] instanceof EmptySlotComponent) || king.getChessColor() == cc[inix - 1][iniy].getChessColor() || otherChessCanMoveTo(cc[inix - 1][iniy])) {
            return false;
        }
        if (inix + 1 < 0 || iniy > 7 || inix + 1 > 7 || iniy < 0 || !(cc[inix + 1][iniy] instanceof EmptySlotComponent) || king.getChessColor() == cc[inix + 1][iniy].getChessColor() || otherChessCanMoveTo(cc[inix + 1][iniy])) {
            return false;
        }
        if (iniy + 1 > 7 || inix + 1 > 7 || iniy + 1 < 0 || !(cc[inix + 1][iniy + 1] instanceof EmptySlotComponent) || king.getChessColor() == cc[inix + 1][iniy + 1].getChessColor() || otherChessCanMoveTo(cc[inix + 1][iniy + 1])) {
            return false;
        }
        if (inix - 1 < 0 || iniy + 1 > 7 || inix - 1 > 7 || iniy + 1 < 0 || !(cc[inix - 1][iniy + 1] instanceof EmptySlotComponent) || king.getChessColor() == cc[inix - 1][iniy + 1].getChessColor() || otherChessCanMoveTo(cc[inix - 1][iniy + 1])) {
            return false;
        }
        if (inix + 1 < 0 || iniy - 1 > 7 || inix + 1 > 7 || iniy - 1 < 0 || !(cc[inix + 1][iniy - 1] instanceof EmptySlotComponent) || king.getChessColor() == cc[inix + 1][iniy - 1].getChessColor() || otherChessCanMoveTo(cc[inix + 1][iniy - 1])) {
            return false;
        }
        if (inix - 1 < 0 || iniy - 1 > 7 || inix - 1 > 7 || iniy - 1 < 0 || !(cc[inix - 1][iniy - 1] instanceof EmptySlotComponent) || king.getChessColor() == cc[inix - 1][iniy - 1].getChessColor() || otherChessCanMoveTo(cc[inix - 1][iniy - 1])) {
            return false;
        }

        return true;
    }

    public boolean isjiangsi() {

        ChessComponent chess;
        ChessComponent king;
        //        ChessComponent[][] chessComponentw = new ChessComponent[8][8];
//        ChessComponent[][] chessComponent2 = new ChessComponent[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent) {
                    king = chessComponents[i][j];
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (!(chessComponents[k][l] instanceof EmptySlotComponent)) {
                                chess = chessComponents[k][l];
                                //不同颜色，有棋子可以移动到王那里；没有棋子可以吃掉这个攻击的棋子；王能走的位置都有棋子可以到达
//                                if (chess.getChessColor() != king.getChessColor() && chess.canMoveTo(chessComponents, king.getChessboardPoint())){
//                                    return true;
//                                }
                                if (chess.getChessColor() != king.getChessColor() && chess.canMoveTo(chessComponents, king.getChessboardPoint()) && king.getChessColor() == ChessColor.BLACK) {
                                    dead("The black king is in danger!");

//                                    if (!otherChessCanMoveTo(chess) && !kingCanMoveTo(king) && king.getChessColor() == ChessColor.BLACK) {
//
                                        return true;
//                                    }
                                }
                               else if (chess.getChessColor() != king.getChessColor() && chess.canMoveTo(chessComponents, king.getChessboardPoint()) && king.getChessColor() == ChessColor.WHITE) {
                                    dead("The white king is in danger!");

//                                    if (!otherChessCanMoveTo(chess) && !kingCanMoveTo(king) && king.getChessColor() == ChessColor.BLACK) {
//
                                        return true;
//                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    private void record(int fromx, int fromy, int tox, int toy) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessComponent m = chessComponents[i][j];
                if (m instanceof RookChessComponent) {
                    if (m.getChessColor().equals(ChessColor.BLACK)) {
                        str.append("R");
                        if (!staybr) {
                            str.append("1");
                        }
                        if (staybr) {
                            str.append("0");
                        }
                    } else {
                        str.append("r");
                        if (!staywr) {
                            str.append("1");
                        }
                        if (staywr) {
                            str.append("0");
                        }

                    }
                }


                if (m instanceof KingChessComponent) {
                    if (m.getChessColor().equals(ChessColor.BLACK)) {
                        str.append("K");
                        if (!staybk) {
                            str.append("1");
                        }
                        if (staybk) {
                            str.append("0");
                        }
                    } else {
                        str.append("k");
                        if (!staywk) {
                            str.append("1");
                        }
                        if (staywk) {
                            str.append("0");
                        }

                    }
                }
                if (m instanceof BishopChessComponent) {
                    if (m.getChessColor().equals(ChessColor.BLACK)) {
                        str.append("B");
                        str.append("0");
                    } else {
                        str.append("b");
                        str.append("0");

                    }
                }
                if (m instanceof KnightChessComponent) {
                    if (m.getChessColor().equals(ChessColor.BLACK)) {
                        str.append("N");
                        str.append("0");
                    } else {
                        str.append("n");
                        str.append("0");

                    }
                }
                if (m instanceof PawnChessComponent) {
                    if (m.getChessColor().equals(ChessColor.BLACK)) {
                        str.append("P");
                        str.append("0");
                    } else {
                        str.append("p");
                        str.append("0");

                    }
                }
                if (m instanceof QueenChessComponent) {
                    if (m.getChessColor().equals(ChessColor.BLACK)) {
                        str.append("Q");
                        str.append("0");
                    } else {
                        str.append("q");
                        str.append("0");

                    }
                }
                if (m instanceof EmptySlotComponent) {
                    str.append("_");
                    str.append("0");

                }


            }
            str.append("*");
        }
        count++;
        if (count % 2 == 1) {
            str.append("w");
        } else {
            str.append("b");
        }
        recordchessboard.add(str.toString());
    }

    public static ArrayList<String> getRecordchessboard() {
        return recordchessboard;
    }


    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public static void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }




    public void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setStroke(new BasicStroke(4f));
    }


    protected Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void getLastChessBoard(int x) {//x从1开始递增
        initiateEmptyChessboard();
        for (int i = 0; i <= 119; i = i + 17) {
            for (int j = i; j <= i + 15; j = j + 2) {
                int end = getRecordchessboard().size();

                if (getRecordchessboard().get(end - x).charAt(j) == 'B') {
                    initBishopOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'K') {
                    initKingOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'N') {
                    initKnightOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'P') {
                    initPawnOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'Q') {
                    initQueenOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'R') {
                    initRookOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'b') {
                    initBishopOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'k') {
                    initKingOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'n') {
                    initKnightOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'p') {
                    initPawnOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'q') {
                    initQueenOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                } else if (getRecordchessboard().get(end - x).charAt(j) == 'r') {
                    initRookOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                } else if (getRecordchessboard().get(end - x).charAt(j) == '_') {
//                    chessComponents[i / 17][(j % 17) / 2] = new EmptySlotComponent(i / 17, (j % 17) % 2, getRecordchessboard().get(end - 1).charAt(j));
                }
            }
        }
    }

    public void loadGame(List<String> chessData) {
        int count = 0;
        int cc = 0;
        initiateEmptyChessboard();
        if (!ChessGameFrame.file.getName().endsWith("txt")) {
            nottxt();
            initiateEmptyChessboard();
            repaint();
        } else {
            B:
            while (true) {
                int end = chessData.size();
                if (chessData.get(end - 1).length() > 137) {//比137多一定非8*8
                    noteight();
                    initiateEmptyChessboard();
                    repaint();
                    break;
                }
                char a[] = new char[chessData.get(end - 1).length()];//共137个

                for (int i = 0; i < chessData.get(end - 1).length(); i++) {//看有几个“*”来看是否8行
                    if (chessData.get(end - 1).charAt(i) == '*') {
                        count++;
                    }
//                if(chessData.get(end - 1).charAt(i) == '*'){
//
//                }
                }
                if (count != 8) {
                    noteight();
                    initiateEmptyChessboard();
                    repaint();
                    break;
                }
                A:
                for (int i = 0; i <= 119; i = i + 17) {
                    for (int j = i; j <= i + 15; j = j + 2) {
                        if (chessData.get(end - 1).charAt(j) == 'B') {
                            initBishopOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                        } else if (chessData.get(end - 1).charAt(j) == 'K') {
                            initKingOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                        } else if (chessData.get(end - 1).charAt(j) == 'N') {
                            initKnightOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                        } else if (chessData.get(end - 1).charAt(j) == 'P') {
                            initPawnOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                        } else if (chessData.get(end - 1).charAt(j) == 'Q') {
                            initQueenOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                        } else if (chessData.get(end - 1).charAt(j) == 'R') {
                            initRookOnBoard(i / 17, (j % 17) / 2, ChessColor.BLACK);
                        } else if (chessData.get(end - 1).charAt(j) == 'b') {
                            initBishopOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                        } else if (chessData.get(end - 1).charAt(j) == 'k') {
                            initKingOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                        } else if (chessData.get(end - 1).charAt(j) == 'n') {
                            initKnightOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                        } else if (chessData.get(end - 1).charAt(j) == 'p') {
                            initPawnOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                        } else if (chessData.get(end - 1).charAt(j) == 'q') {
                            initQueenOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                        } else if (chessData.get(end - 1).charAt(j) == 'r') {
                            initRookOnBoard(i / 17, (j % 17) / 2, ChessColor.WHITE);
                        } else if (chessData.get(end - 1).charAt(j) == '_') {
//                    chessComponents[i / 17][(j % 17) / 2] = new EmptySlotComponent(i / 17, (j % 17) % 2, chessData.get(end - 1).charAt(j));
                        } else {//不是那些种类的棋子
                            loadchesswrong();
                            initiateEmptyChessboard();
                            break B;
                        }
                        repaint();
                    }
                    if (chessData.get(end - 1).charAt(i + 16) != '*') {//非8列
                        noteight();
                        initiateEmptyChessboard();
                        repaint();
                        break B;

                    }
                }
                if (chessData.get(end - 1).charAt(chessData.get(end - 1).length() - 1) == 'w') {
                    setCurrentColor(ChessColor.BLACK);
                } else if (chessData.get(end - 1).charAt(chessData.get(end - 1).length() - 1) == 'b') {
                    setCurrentColor(ChessColor.WHITE);
                } else {//无黑白方
                    nonextfang();
                    initiateEmptyChessboard();
                    repaint();
                    break;

                }
                changelabelload();
                break;
//            }
            }

        }
    }
}





