

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.ObjectOutputStream;

public class UserInterface extends JFrame {

    static UserInterface userInterface=new UserInterface();

    public int ss;

    JPanel paintPanel;
    JPanel colorPanel;

    JButton[] colorBtn = new JButton[7];
    String[] colorBtnText = {"red", "orange", "yellow", "green", "blue", "pink"};
    Color[] color = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.pink};
    JButton eraser;

    PaintCanvas paintCanvas;
    JComboBox jcb;

    JPanel chatPanel;
    JPanel chatInputPanel;
    JTextArea chatArea;
    JTextField chatNameField;
    JTextField chatInputField;
    JButton sendBtn;
    
    SendData sendData;

    public static UserInterface getInstance(){
        return userInterface;
    }

    public void showUI() {
        paintCanvas = PaintCanvas.getInstance();
        colorPanel = new JPanel(new FlowLayout());
        for (int i = 0; i < color.length; i++) {
            colorBtn[i] = new JButton(colorBtnText[i]);
            Color indexColor = color[i];
            colorBtn[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	paintCanvas.setBackground(indexColor);
                }
            });
            colorBtn[i].setOpaque(true);
            colorBtn[i].setBackground(color[i]);
            colorPanel.add(colorBtn[i]);
        }

        eraser = new JButton("ì§??š°ê°?");
        eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	sendData.sendPaintData(new PaintData(-10, -10,-10));
            }
        });
        colorPanel.add(eraser);

 

        String[] str = {"10", "15", "20", "25", "30"};
        jcb = new JComboBox(str);

        jcb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ss=Integer.parseInt(jcb.getSelectedItem().toString());
            }
        });
        jcb.setSelectedItem(str[0]);
        colorPanel.add(jcb);

        paintPanel = new JPanel(new BorderLayout());
        paintPanel.add(colorPanel, BorderLayout.NORTH);
        paintPanel.add(paintCanvas, BorderLayout.CENTER);

        ///

        chatPanel = new JPanel(new BorderLayout());
        chatArea = new JTextArea(15, 40);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
        chatArea.setVisible(true);
        JScrollPane qScroller = new JScrollPane(chatArea);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        chatInputPanel = new JPanel(new FlowLayout());
        chatNameField= new JTextField(10);
        chatInputField = new JTextField(20);
        chatInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendBtn.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        sendBtn = new JButton("Send");
        sendData=SendData.getInstance();
        sendData.setOutputStream(SocketData.getInstance().getSocket());
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chatInputField.getText().length() > 0) {
                    String chatName=chatNameField.getText();
                    String chatInput=chatInputField.getText();
                    ChatData chatData=(chatName.length()==0)?new ChatData(chatInput):new ChatData(chatName,chatInput);
                    //AddChat(chatData);
                    chatInputField.setText("");
                    sendData.sendChatData(chatData);
                }
            }
        });
        chatPanel.add(qScroller, BorderLayout.CENTER);
        chatPanel.add(chatInputPanel, BorderLayout.SOUTH);

        chatInputPanel.add(chatNameField);
        chatInputPanel.add(chatInputField);
        chatInputPanel.add(sendBtn);

        setLayout(new BorderLayout());
        add(paintPanel, BorderLayout.WEST);
        add(chatPanel, BorderLayout.EAST);

        setBounds(100, 100, 1600, 600);
        setResizable(true);
        setVisible(true);

        paintCanvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                PaintData paintData=new PaintData(e.getX() - 20 / 2,e.getY() - 20 / 2,ss);
                paintCanvas.addPaint(paintData);
                sendData.sendPaintData(paintData);
            }
        });

        paintCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                PaintData paintData=new PaintData(e.getX() - 20 / 2,e.getY() - 20 / 2,ss);
                //paintCanvas.addPaint(paintData);
                sendData.sendPaintData(paintData);
            }
        });
    }


    public void AddChat(ChatData chatData){
        chatArea.append("["+chatData.getName()+"] "+chatData.getMessage()+"\n");
    }
}
