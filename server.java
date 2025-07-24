package Chatting_Application;
import javax.swing.*;
import javax.xml.crypto.Data;

import java.util.Calendar;
import java.text.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.net.*;
import java.io.*;

public class server  implements ActionListener {
     JTextField text;
     JPanel a1;
    static Box vertical= Box.createVerticalBox();
     static JFrame f=new JFrame();
      static DataOutputStream dos;
    server(){
        f.setLayout(null);
        //creating a panel for the top section of the chat window
        //setting background color and size of the panel
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
       f. add(p1);
        
        //label for back button
        //using icon for back button
        //action perfomed here while click the mouse
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3); 
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.exit(0);
            }
        });

        //label for profile 
        //using icon for profile
         ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/shweta-8.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6); 
        profile .setBounds(40,10,50,50);
        p1.add(profile);

        //label for video call
        //using icon for video call
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i9= new ImageIcon(i8);
        JLabel video = new JLabel(i9); 
        video .setBounds(300,20,30,30);
        p1.add(video);
 

         //label for audio call
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11= i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12= new ImageIcon(i11);
        JLabel audio = new JLabel(i12); 
        audio .setBounds(360,20,30,30);
        p1.add(audio);
  
         //label for more options
        //using icon for more options
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14= i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15= new ImageIcon(i14);
        JLabel more = new JLabel(i15); 
        more .setBounds(420,20,10,25);
        p1.add(more);
      
         //label for name
        JLabel name = new JLabel("Shweta");
        name.setBounds(110,20,100,30);
        p1.add(name);
        name.setFont(new Font("SAN_SERIF", Font.BOLD,18));
        name.setForeground(Color.WHITE);    

       //active now label
        JLabel status = new JLabel("Active now");
        status.setBounds(110,35,100,30);
        p1.add(status);
        status.setFont(new Font("SAN_SERIF", Font.BOLD,10));
       status.setForeground(Color.WHITE);    

       //creating a panel for the chat area 
        //setting background color and size of the panel
        //adding text field and send button
       a1 = new JPanel();
        a1.setBounds(5,75,425,570);
        f.add(a1);
        text= new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN,16));
       f. add(text);
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.BOLD,16));
        send.addActionListener(this);
       f. add(send);

        f.setSize(450,700);
        f.setLocation(200,50);
        //to delete the title bar and make it look like a chat window
        f.setUndecorated(true);
        f.setTitle("Server Chatting Application");
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible( true);

    }
    public void actionPerformed(ActionEvent ae) {
        String out=text.getText();
       
        JPanel p2 = formateLabel(out);   
     
        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(10));//adding space between messages      

        a1.add(vertical,BorderLayout.PAGE_START);
        try {
            dos.writeUTF(out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // send the message to the client
        text.setText("");
        f.repaint();
        f.invalidate();
       f. validate();
    }
    public static JPanel formateLabel(String out) {
        //formatting the label for the message
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2,BoxLayout.Y_AXIS));
        JLabel output=new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(BorderFactory.createEmptyBorder(15,15,15,50));
        p2.add(output);
        Calendar c = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        JLabel time = new JLabel();
        time.setText(sdf.format(c.getTime()));
        p2.add(time);
        return p2;
    }
    public  static void  main(String args[]){
        new server();

        try{
            ServerSocket ss = new ServerSocket(6001);
            while (true) {
                Socket s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                while(true){
                    String msg=dis.readUTF();
                    JPanel p2 = formateLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(p2,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
