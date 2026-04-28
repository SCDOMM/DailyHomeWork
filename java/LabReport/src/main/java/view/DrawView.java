package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawView {
    private static JPanel drawPanel2 = new JPanel();
    private static int frequency = 0;
    private static Point[] points = new Point[3];

    public static void onCreate(JFrame frame) {
        drawPanel2.removeAll();
        drawPanel2.setBackground(Color.GREEN);
        drawPanel2.setLayout(new BoxLayout(drawPanel2,BoxLayout.Y_AXIS));

        JTextArea drawText = new JTextArea();
        drawText.setEditable(false);
        drawText.setText("请手动点击三个顶点，图形将自动绘制");
        drawText.setVisible(true);
        drawPanel2.add(drawText);

        initEvent();

        frame.getContentPane().add(drawPanel2);
        frame.revalidate();
        frame.repaint();
    }

    private static void initEvent() {
        Canvas canvas1 = new Canvas() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                if (points[0]!=null&&points[1]!=null&&points[2]!=null) {
                    Polygon polygon = new Polygon();
                    for (Point p : points) {
                        polygon.addPoint(p.x, p.y);
                    }
                    g.fillPolygon(polygon);
                }
            }
        };
        canvas1.setSize(600, 300);
        drawPanel2.add(canvas1);

        canvas1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getPoint() + " " + frequency);
                points[frequency] = e.getPoint();
                frequency++;
                if(frequency==1){
                    Graphics g = canvas1.getGraphics();
                    g.clearRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
                }
                if (frequency == 3) {
                    canvas1.repaint();
                    frequency=0;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


    }


}
