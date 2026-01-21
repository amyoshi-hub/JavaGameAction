import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.Color;

class Gra extends JPanel implements ActionListener, MouseListener {
   private int size = 10;
   private int charX, charY;
    private int timeInt = 0;
    private String timeDisplay = "";
    private BufferedImage bi;

    private Image[] images;
    private int x = 0, y = 50; 
    private int lastTime = -1;

    Gra() {
        this.addMouseListener(this);
        super.setBackground(Color.black);
        super.setPreferredSize(new Dimension(640, 240));

        this.bi = new BufferedImage(640, 240, BufferedImage.TYPE_INT_ARGB);
        Graphics g = this.bi.getGraphics();
        g.setColor(Color.black); 
        g.fillRect(0, 0, 640, 240);
        g.dispose();
    }

    public void setTime(String val) {
        this.timeDisplay = val;
        try {
            this.timeInt = Integer.parseInt(val);
        } catch(Exception e) { this.timeInt = 0; }
    }

    public void setCharPos(int x, int y) {
    this.charX = x;
    this.charY = y;
}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(this.bi, 0, 0, this);

        g.setColor(Color.green);
        g.drawString(timeDisplay, 10, 100);

        if(images != null && images.length > 0){
            drawAnimation(g);
        }
        System.out.println("Drawing at: " + charX + ", " + charY);
    }

    public void setAnimation(int x, int y, Image[] imgs){
        this.x = x;
        this.y = y;
        this.images = imgs;
        this.repaint();
    }

    private void drawAnimation(Graphics g){
        int frame = timeInt % images.length;
        g.drawImage(this.images[frame], charX, charY, this); 
        lastTime = timeInt;
    }

    public void actionPerformed(ActionEvent e) {}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){} 

    public static void main(String[] args) {
        new Gra();
    }
}

