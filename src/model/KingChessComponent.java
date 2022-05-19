package model;

import view.ChessGameFrame;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的王
 */
public class KingChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private static Image kb2;
    private static Image kw2;
    private Image kingImage;
    private Image king2;
    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }
        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
        if(kb2==null){
            kb2 = ImageIO.read(new File("./images/king-white2.png"));
        } if(kw2==null){
            kw2 = ImageIO.read(new File("./images/king-black2.png"));
        }

    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
                king2=kw2;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
                king2=kb2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
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
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(source.getX() - destination.getX()) == 1 && source.getY() == destination.getY()) {
            return true;
        } else if (Math.abs(source.getY() - destination.getY()) == 1 && source.getX() == destination.getX()) {
            return true;
        } else if (Math.abs((source.getY()) - destination.getY()) == 1 && Math.abs(source.getX() - destination.getX()) == 1) {
            return true;
        } else { // Not on the same row or the same column or on diagonal
            return false;
        }
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
        g.drawImage(kingImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);}
        else{super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
            g.drawImage(king2, 0, 0, getWidth(), getHeight(), this);
            g.setColor(Color.BLACK);}

        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            ((Graphics2D)g).setStroke(new BasicStroke(4f));
            g.drawRect(0, 0, getWidth(), getHeight());
        }
    }
}
