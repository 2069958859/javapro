package view;

import Music.Music;
import controller.ClickController;
import controller.GameController;
import model.*;
import model.ChessComponent;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static view.Chessboard.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    public int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    //    public final Dimension FRAME_SIZE ;
    protected final int WIDTH;
    protected static int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private  Chessboard chessboard;
    public static boolean isSkin;
    public static boolean isChanged;
    private GameController gameController;
    private final ChessComponent[][] chessComponents = new ChessComponent[8][8];
    private ClickController clickController=new ClickController(chessboard);
    static JLabel statusLabel = new JLabel();
    static JLabel loadwrong = new JLabel();
    static JButton button1 = new JButton("Restart");
    static JButton button2 = new JButton("Save");
    static JButton button3 = new JButton("Change Color");
    static JButton button4 = new JButton("Load");
    static JButton button5 = new JButton("Music");
    static JButton button6 = new JButton("Change Skin");
    static JButton button10 = new JButton("Playback");
    JButton button8 = new JButton("repentance");
    static  JLabel im=new JLabel();
    static JButton button7=new JButton("open");
    static File file;
    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        CHESSBOARD_SIZE = HEIGHT * 4 / 5;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addChessboard();
        addLabel();
        addChangeButton();
//        addLoadButton();
        addPlayMusicButton();
       // addStopMusicButton();
        addChongZhiButton();
        addSaveButton();
        addhuiqibutton();
        addloadLabel();

        addFileChooser();
        playback();

        changeSkin();
        addBackground();

    }

    private void addLabel() {
        statusLabel.setText("White's Round");
        statusLabel.setLocation(HEIGHT, HEIGHT / 10);
        statusLabel.setSize(200, 40);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    public static void changelabel() {
        if (getCurrentColor() == ChessColor.BLACK) {
            statusLabel.setText("White's Round");
        }
        if (getCurrentColor() == ChessColor.WHITE) {
            statusLabel.setText("Black's Round");
        }
    }

    public static void changelabelload() {
        if (getCurrentColor() == ChessColor.WHITE) {
            statusLabel.setText("White's Round");

        }
        if (getCurrentColor() == ChessColor.BLACK) {
            statusLabel.setText("Black's Round");

        }

    }

    private void addChongZhiButton() {

        button1.setLocation(HEIGHT, HEIGHT / 10 + 40);
        button1.setSize(200, 30);
        button1.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button1);

        button1.addActionListener(e -> {
            gameController.getChessboard().initall();
            recordchessboard.add("R0N0B0Q0K0B0N0R0*P0P0P0P0P0P0P0P0*_0_0_0_0_0_0_0_0*_0_0_0_0_0_0_0_0*_0_0_0_0_0_0_0_0*_0_0_0_0_0_0_0_0*p0p0p0p0p0p0p0p0*r0n0b0q0k0b0n0r0*w");
            Chessboard.setCurrentColor(ChessColor.WHITE);

            statusLabel.setText("White's Round");
            loadwrong.setText("");

            repaint();
        });

    }
    public static void setN(AtomicInteger n) {
        ChessGameFrame.n = n;
    }
    static AtomicInteger n = new AtomicInteger();

    private void addhuiqibutton() {

        button8.setLocation(HEIGHT, HEIGHT / 10 + 190);
        button8.setSize(200, 40);
        button8.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button8);
        button8.addActionListener((e) -> {
            x++;
            n.getAndIncrement();
            if (n.get() == getRecordchessboard().size()) {
                gameController.getChessboard().initall();
                repaint();
            } else {
                gameController.getChessboard().getLastChessBoard(n.get() + x);
                recordchessboard.add(getRecordchessboard().get(recordchessboard.size() - n.get() - x));
                if (getCurrentColor() == ChessColor.BLACK) {
                    setCurrentColor(ChessColor.WHITE);
                    statusLabel.setText("White's Round");
                } else {
                    setCurrentColor(ChessColor.BLACK);
                    statusLabel.setText("Black's Round");

                }
//            changecurrentcolor();
//            changelabel();
                repaint();
//            }
            }
        });
    }

    private void addSaveButton() {

        button2.addActionListener((e) -> {
            //JOptionPane.
            System.out.println("save");
            ArrayList<String> transferToText = new ArrayList<>();
            transferToText = getRecordchessboard();
            //this is an auto filer format: save1.txt 1represent a positive number
            writeFiles(transferToText);

        });
        button2.setLocation(HEIGHT, HEIGHT / 10 + 70);
        button2.setSize(200, 40);
        button2.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button2);
    }

    private void addChangeButton() {
        button3.setLocation(HEIGHT, HEIGHT / 10 + 150);
        button3.setSize(200, 40);
        button3.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button3);
        AtomicInteger n= new AtomicInteger();
        button3.addActionListener((e) ->{
            n.getAndIncrement();
            if(n.get() %2==1){
                isChanged=true;
                this.repaint();
            } else if(n.get() %2==0){
                isChanged=false;
                this.repaint();
            }});
    }
    private void playback() {
        button10.setLocation(HEIGHT, HEIGHT/10+230);
        button10.setSize(200, 40);
        button10.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button10);
        button10.addActionListener((e) ->{
           Thread newTry = new Thread(new MyRunable(this));
           newTry.start();

//           if(currentColor==ChessColor.WHITE){
//               showwhite();
//           }
//           else {
//               showblack();
//           }
//           repaint();
            });
}

//    private void addLoadButton() {
//
//        button4.setLocation(HEIGHT, HEIGHT / 10 + 240);
//        button4.setSize(200, 40);
//        button4.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button4);
//
//        button4.addActionListener(e -> {
//            System.out.println("Click load");
//
//            gameController.loadGameFromFile(file.getAbsolutePath());
//        });
//    }

    private void addPlayMusicButton() {

        button5.setLocation(HEIGHT, HEIGHT / 10 + 360);
        button5.setSize(200, 40);
        button5.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button5);

        //背景音乐启动
        AtomicInteger n= new AtomicInteger();
        button5.addActionListener(e -> {
            n.getAndIncrement();
            if(n.get() %2==1){
                audioPlayWave1.start();}
            else if(n.get() %2==0){
                audioPlayWave1.stop();
            }
        });
    }

    private void addBackground(){
        ImageIcon icon=new ImageIcon("./images/img.png");
        im.setIcon(icon);
//        icon.setImage(icon.getImage().getScaledInstance(1000,750 , Image.SCALE_DEFAULT));
        im.setHorizontalAlignment(SwingConstants.CENTER);
        im.setSize(WIDTH,HEIGHT);
        im.setOpaque(true);
        add(im);
    }
    public static void dead(String warning){
        JOptionPane j=new JOptionPane();
        j.showMessageDialog(null,warning);
    }

    private void changeSkin(){
        button6.setLocation(HEIGHT, HEIGHT / 10 + 420);
        button6.setSize(200, 40);
        button6.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button6);
        AtomicInteger n= new AtomicInteger();
        button6.addActionListener((e) ->{
            n.getAndIncrement();
            if(n.get() %2==1){
                isSkin=true;
                this.repaint();
                statusLabel.setForeground(Color.WHITE);
                button1.setBackground(Color.green.darker());
                button1.setOpaque(true);
                button1.setBorderPainted(true);
                button2.setBackground(Color.green.darker());
                button2.setOpaque(true);
                button2.setBorderPainted(true);
                button3.setBackground(Color.green.darker());
                button3.setOpaque(true);
                button3.setBorderPainted(true);
                button4.setBackground(Color.green.darker());
                button4.setOpaque(true);
                button4.setBorderPainted(true);
                button5.setBackground(Color.green.darker());
                button5.setOpaque(true);
                button5.setBorderPainted(true);
                button6.setBackground(Color.green.darker());
                button6.setOpaque(true);
                button6.setBorderPainted(true);
                button7.setBackground(Color.green.darker());
                button7.setOpaque(true);
                button7.setBorderPainted(true);
                button8.setBackground(Color.green.darker());
                button8.setOpaque(true);
                button8.setBorderPainted(true);
                button10.setBackground(Color.green.darker());
                button10.setOpaque(true);
                button10.setBorderPainted(true);


                ImageIcon icon2=new ImageIcon("./images/img2.jpg");
                im.setIcon(icon2);
                icon2.setImage(icon2.getImage().getScaledInstance(1000,750 , Image.SCALE_DEFAULT));
                im.setHorizontalAlignment(SwingConstants.CENTER);
                im.setSize(WIDTH,HEIGHT);
                im.setOpaque(true);
                add(im);
                this.repaint();
            } else if(n.get() %2==0){
                statusLabel.setForeground(Color.BLACK);
                isSkin=false;
                button1.setOpaque(false);
                button1.setBorderPainted(true);
                button2.setOpaque(false);
                button2.setBorderPainted(true);
                button3.setOpaque(false);
                button3.setBorderPainted(true);
                button4.setOpaque(false);
                button4.setBorderPainted(true);
                button5.setOpaque(false);
                button5.setBorderPainted(true);
                button6.setOpaque(false);
                button6.setBorderPainted(true);
                button7.setOpaque(false);
                button7.setBorderPainted(true);
                button8.setOpaque(false);
                button8.setBorderPainted(true);
                button10.setOpaque(false);
                button10.setBorderPainted(true);

                ImageIcon icon=new ImageIcon("./images/img.png");
                im.setIcon(icon);
//        icon.setImage(icon.getImage().getScaledInstance(1000,750 , Image.SCALE_DEFAULT));
                im.setHorizontalAlignment(SwingConstants.CENTER);
                im.setSize(WIDTH,HEIGHT);
                im.setOpaque(true);
                add(im);
                this.repaint();
            }});

    }
    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboard.setBorder(BorderFactory.createLineBorder(Color.BLUE,8,true));
        gameController = new GameController(chessboard);
        chessboard.setBounds(getHEIGHT()/ 10, getHEIGHT()/ 10,getHEIGHT()*4/5,getHEIGHT()*4/5);
        add(chessboard);
    }
//
    /**
     * 在游戏面板中添加标签
     */





    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */



    Music audioPlayWave1 = new Music("doudizhu.wav");// 开音乐 音樂名



//    private void addStopMusicButton() {
//        JButton button = new JButton("Stop Music");
//        button.setLocation(HEIGHT, HEIGHT / 10 + 450);
//        button.setSize(200, 40);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        //背景音乐启动
//        button.addActionListener(e -> {
//            audioPlayWave1.stop();
//        });
//    }






    private void writeFiles(ArrayList<String> transfer) {
        int num = 1;
        FileWriter writer = null;
        String path = "save" + num + ".txt";
        try {
            File file = new File(path);
//            System.out.println("01");
            while (file.exists()) {
                num++;
                path = new String("save" + num + ".txt");
                file = new File(path);
//                System.out.println("02");
            }
            file.createNewFile();
            writer = new FileWriter(path);
//            System.out.println("03");
//            System.out.println("size");
            System.out.println(transfer.size());
            for (int i = 0; i < transfer.size(); i++) {

                String str = transfer.get(i).toString() + "\n";

                System.out.println(transfer.size());
//                System.out.println("04");
                writer.write(str);

            }
            writer.close();
            System.out.println(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
//    public void removechess() {
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (chessComponent[i][j] != null) {
//                    remove(chessComponent[i][j]);
//                }
//            }
//        }
//    }
    private void addloadLabel() {
        loadwrong.setText("");
        loadwrong.setLocation(HEIGHT, HEIGHT / 10 + 110);
        loadwrong.setSize(200, 40);
        loadwrong.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(loadwrong);

    }

    public static void loadchesswrong() {
        loadwrong.setText("Wrong ChessComponent");

    }
    public static void nonextfang() {
        loadwrong.setText("no next player");

    }
    public static void noteight() {
        loadwrong.setText("not a 8*8 chessboard");

    }
    public static void nottxt(){
            loadwrong.setText("Wrong Format");
    }


    public static void showwhite() {
        statusLabel.setText("White's Round");

    }
    public static void showblack() {
        statusLabel.setText("Black's Round");

    }


//    public void addFrameButton(){
//        JButton frame=new JButton("frame");
//        frame.addActionListener(e -> {
//           this.chessboard.getBord
//
//        });
//        frame.setLocation(HEIGHT, HEIGHT / 10 + 540);
//        frame.setSize(200, 40);
//        frame.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(frame);}

    public Chessboard getChessboard() {
        return chessboard;
    }

    private void addFileChooser(){
        button7.setLocation(HEIGHT, HEIGHT / 10 + 470);
        button7.setSize(200, 40);
        button7.setFont(new Font("Rockwell", Font.BOLD, 20));
        button7.setOpaque(true);
        add(button7);




        button7.addActionListener(e -> {
            // TODO Auto-generated method stub
            JFileChooser jfc=new JFileChooser("/Users/wangpinhuang/Desktop/proj");
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
            jfc.showDialog(new JLabel(), "选择");
            file=jfc.getSelectedFile();
            gameController.loadGameFromFile(file.getAbsolutePath());
        });
        }
        public void Time(){
        time();
        }

    public GameController getGameController() {
        return gameController;
    }
}



