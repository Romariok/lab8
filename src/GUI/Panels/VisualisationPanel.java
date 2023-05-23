package GUI.Panels;

import Auth.AuthResponse;
import Auth.Session;
import Data.HumanBeing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Random;

import static GUI.Frames.TableFrame.currentUserList;


public class VisualisationPanel extends JPanel {
    private javax.swing.Timer timer;
    private HashMap<String, Color> usersColor = new HashMap<>();

    private Graphics2D g2d = null;
    public VisualisationPanel(){

        setPreferredSize(new Dimension(513, 513));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for (HumanBeing hb : currentUserList) {
                    int orgX = hb.getCoordinates().getX();
                    Long orgY = hb.getCoordinates().getY();
                    if (x >= orgX && x <= orgX + 20 && y >= orgY && y <= orgY + 20) {
                        showHumanBeing(getById(hb.getId()));
                    }
                }
            }
        });
        timer = new javax.swing.Timer(10, (ActionEvent e) -> {
            if(g2d == null) return;
            repaint();
        });
        timer.start();


    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g2d = (Graphics2D) g;
        drawElements();
    }

    public javax.swing.Timer getTimer(){
        return this.timer;
    }
    public HumanBeing getById(Long id){
        return currentUserList.stream().filter(o -> o.getId() == id).findFirst().get();
    }

    public void showHumanBeing(HumanBeing hb){
        JWindow w = new JWindow();
        JLabel l1 = new JLabel("Id: " + hb.getId());
        JLabel l2 = new JLabel("Name: " + hb.getName());
        JLabel l3 = new JLabel("Date: " + hb.getCreationDate());
        JLabel l4 = new JLabel("Coordinate X: " + hb.getCoordinates().getX());
        JLabel l5 = new JLabel("Coordinate Y: " + hb.getCoordinates().getY());
        JLabel l6 = new JLabel("Real Hero: " + hb.isRealHero());
        JLabel l7 = new JLabel("Has toothpick: " + hb.getHasToothpick());
        JLabel l8 = new JLabel("Impact speed: " + hb.getImpactSpeed());
        JLabel l9 = new JLabel("Soundtrack name: " + hb.getSoundtrackName());
        JLabel l10 = new JLabel("Weapon type: " + hb.getWeaponType().toString());
        JLabel l11 = new JLabel("Mood: " + hb.getMood().toString());
        JLabel l12 = new JLabel("Car: " + hb.getCar().getCool());
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        l3.setAlignmentX(Component.CENTER_ALIGNMENT);
        l4.setAlignmentX(Component.CENTER_ALIGNMENT);
        l5.setAlignmentX(Component.CENTER_ALIGNMENT);
        l6.setAlignmentX(Component.CENTER_ALIGNMENT);
        l7.setAlignmentX(Component.CENTER_ALIGNMENT);
        l8.setAlignmentX(Component.CENTER_ALIGNMENT);
        l9.setAlignmentX(Component.CENTER_ALIGNMENT);
        l10.setAlignmentX(Component.CENTER_ALIGNMENT);
        l11.setAlignmentX(Component.CENTER_ALIGNMENT);
        l12.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton b = new JButton("OK");
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.addActionListener((ActionEvent evt) -> {
            w.setVisible(false);
        });
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(l1);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(l2);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(l3);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(l4);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(l5);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(l6);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(l7);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(l8);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(l9);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(l10);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(l11);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(l12);
        p.add(Box.createRigidArea(new Dimension(0, 10)));


        p.add(b);
        w.add(p);
        w.setSize(400, 500);
        w.setLocation(300, 300);
        w.setVisible(true);
    }

    public void drawElements(){
        try{
            currentUserList.forEach((HumanBeing e) -> {
                int x = e.getCoordinates().getX();
                int y = Integer.parseInt(e.getCoordinates().getY().toString());
                drawMan(x, y, new Random(e.getId()));
            });
        }
        catch (Exception ignore){}

    }
    public void drawMan(Integer x, int y, Random random){
        int width = 20;
        int height = 20;
        int headRad=10;
//        int offset = 4;
        int headX =x+ (width/2)-headRad/2;
        int headY=y+ (height/2)-headRad-2;
        int rectX=x;
        int rectY = y+(height/2);
//        int rectWidth = width;
        int rectHeight = height - rectY+y;

        g2d.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));

        g2d.fillRect(rectX, rectY, width, rectHeight);
        g2d.fillOval(headX,headY, headRad, headRad);
    }
}
