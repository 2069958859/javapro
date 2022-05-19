import view.ChessStartFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            ChessGameFrame mainFrame = new ChessGameFrame(1000, 750);
//            mainFrame.setVisible(true);
            ChessStartFrame kaishi=new ChessStartFrame(1000,750);
            kaishi.setVisible(true);

            //背景音乐启动
        });
    }
}