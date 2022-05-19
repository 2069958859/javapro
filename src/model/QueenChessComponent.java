package model;

import view.ChessGameFrame;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的后
 */

public class QueenChessComponent extends ChessComponent {
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    /**
     * 后的棋子对象自身的图片，是上面两种中的一种
     */
    private Image queenImage;
    private static Image qb2;
    private static Image qw2;
    private Image queen2;
    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }
        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
        if(qb2==null){
            qb2 = ImageIO.read(new File("./images/queen-white2.png"));
        } if(qw2==null){
            qw2 = ImageIO.read(new File("./images/queen-black2.png"));
        }
    }

    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */


    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
               queen2= qw2;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
                queen2=qb2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1; col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }else if (source.getX() - source.getY() == destination.getX() - destination.getY()) {
            int col;
            int row;
            for (row = Math.min(source.getX(), destination.getX()) + 1, col = Math.min(source.getY(), destination.getY()) + 1; row < Math.max(source.getX(), destination.getX()); row++, col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }
        else if (source.getX() + source.getY() == destination.getX() + destination.getY()) {
            int row=Math.max(source.getX(),destination.getX())-1;
            int col=Math.min(source.getY(),destination.getY())+1;
            for (; col < Math.max(source.getY(), destination.getY()); row--, col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else { // Not on the same row or the same column or on diagonal
            return false;
        }
        return true;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */

    protected void paintComponent(Graphics g) {
        if(!ChessGameFrame.isChanged){
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 3, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);}
        else {
            super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
            g.drawImage(queen2, 3, 0, getWidth(), getHeight(), this);
            g.setColor(Color.BLACK);

        }

        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            ((Graphics2D)g).setStroke(new BasicStroke(4f));
            g.drawRect(0, 0, getWidth(), getHeight());
        }
    }



}
