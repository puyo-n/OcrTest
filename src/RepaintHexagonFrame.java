import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RepaintHexagonFrame extends JFrame {

    public RepaintHexagonFrame() {
        super("RepaintHexagonFrame");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        HexagonPanel panel = new HexagonPanel();
        panel.setPreferredSize(new Dimension(300, 300));
        getContentPane().add(panel, BorderLayout.CENTER);

        pack();
    }

    public static void main(String args[]) {
        new RepaintHexagonFrame().setVisible(true);
    }

    private class HexagonPanel extends JPanel {

        public void paint(Graphics g) {
            Dimension dim = getSize();
            Polygon hex = new Polygon();

            // 正六角形の大きさをpanelいっぱいに決定
            double radius =
                Math.min(dim.height / 2.0, dim.width / Math.sqrt(3.0));

            // 正六角形の座標
            for (int i = 0; i < 6; i++) {
                hex.addPoint(
                    (int) (Math.sin(Math.PI / 3 * i) * radius + dim.width / 2),
                    (int) (Math.cos(Math.PI / 3 * i) * radius
                        + dim.height / 2));
            }

            // 背景をクリア
            g.setColor(getBackground());
            g.fillRect(0, 0, dim.width, dim.height);

            // 正六角形を描画
            g.setColor(Color.BLUE);
            g.fillPolygon(hex);
        }
    }

}