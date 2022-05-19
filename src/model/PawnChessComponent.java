package model;

import view.ChessGameFrame;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的兵
 */
public class PawnChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private static Image pb2;
    private static Image pw2;
    private Image pawnImage;
    private Image pawn2;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */


    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }
        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
        if(pb2==null){
            pb2 = ImageIO.read(new File("./images/pawn-white2.png"));
        } if(pw2==null){
            pw2 = ImageIO.read(new File("./images/pawn-black2.png"));
        }

    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
                pawn2= pw2;

            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
                pawn2=pb2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }


    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */



    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessColor color = getChessColor();
        ChessboardPoint source = getChessboardPoint();

        if (color.getName() == "White" && source.getX() == 6) {
            int col = source.getY();
            if ((destination.getX() - source.getX() == -1 || destination.getX() - source.getX() == -2) && destination.getY() == source.getY()) {
                for (int row = destination.getX(); row <= source.getX(); row++) {
                    if ((chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return true;
                    }
                }
            }
        }  if (color.getName() == "Black" && source.getX() == 1) {
            int col = source.getY();
            if ((destination.getX() - source.getX() == 1 || destination.getX() - source.getX() == 2) && destination.getY() == source.getY()) {
                for (int row = Math.min(source.getX(), destination.getX()) + 1;
                     row <= Math.max(source.getX(), destination.getX()); row++) {
                    if ((chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return true;
                    }
                }
            }

        }  if (destination.getX() - source.getX() == 1 && color.getName() == "Black" && destination.getY() == source.getY() && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
            return true;

        } if (source.getX() - destination.getX() == 1 && color.getName() == "White" && destination.getY() == source.getY() && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
            return true;

        }  if (destination.getX() - source.getX() == 1 && Math.abs(destination.getY() - source.getY()) == 1 && color.getName() == "Black" && !(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
            return true;
        }  if (Math.abs(source.getY() - destination.getY()) == 1 && source.getX() - destination.getX() == 1 && color.getName() == "White" && !(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
            return true;
        }

            return false;

    }


    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        if(!ChessGameFrame.isChanged){
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);}
        else {super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
            g.drawImage(pawn2, 0, 0, getWidth() , getHeight(), this);
            g.setColor(Color.BLACK);
        }
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            ((Graphics2D)g).setStroke(new BasicStroke(4f));
            g.drawRect(0, 0, getWidth(), getHeight());
        }
    }
}
