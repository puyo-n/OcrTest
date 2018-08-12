import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class Test extends JFrame {

	private JFrame frame;
	private JPanel capture_panel;

	private Robot robot;
	private BufferedImage image;

	private Point initialClick;
	static String width;
	static String height;


	public Test() {
		EventQueue.invokeLater(() -> {
			Toolkit.getDefaultToolkit().setDynamicLayout(false);
			// JFrame.setDefaultLookAndFeelDecorated(true);
			frame = new JFrame();

			frame.setUndecorated(true);
			frame.setBackground(new Color(0x0, true));
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setAlwaysOnTop(true); // 常に前に表示

			capture_panel = new JPanel();
			capture_panel.setBackground(new Color(.5f, .8f, .5f, .0f));
			capture_panel.setBorder(new LineBorder(Color.BLACK, 3));

			// p.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			frame.getContentPane().add(capture_panel);

			frame.getContentPane().add(moveFrame(), BorderLayout.NORTH);

			// final JLabel label = new JLabel("Resize Here");
			// label.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			// frame.add(label, BorderLayout.SOUTH);
//			JButton button5 = new JButton("ボタン");
//			button5.setMargin(new Insets(0, 2, 2, 2));
//			frame.add(button5);

			frame.setSize(320, 240);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			repaint();
			new ComponentResizer(frame);
		});

	}

	private JPanel moveFrame() {
		JPanel panel = new JPanel();

		panel.setBackground(new Color(.2f, .2f, .7f, .9f));
		panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				// get location of Window
				int thisX = frame.getLocation().x;
				int thisY = frame.getLocation().y;

				// Determine how much the mouse moved since the initial click
				int xMoved = e.getX() - initialClick.x;
				int yMoved = e.getY() - initialClick.y;

				// Move window to this position
				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				frame.setLocation(X, Y);
				width = String.valueOf(frame.getWidth());
				height = String.valueOf(frame.getHeight());

			}
		});

		// 閉じるボタン
		JButton close_btn = new JButton("close");
		close_btn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		close_btn.setFont(new Font("SansSerif", Font.BOLD, 9));
		close_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		panel.add(close_btn);

		// captureボタン
		JButton capture_btn = new JButton("capture");
		capture_btn.setFont(new Font("SansSerif", Font.BOLD, 9));
		capture_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				capture();
			}
		});
		panel.add(capture_btn, BorderLayout.PAGE_END);

		JTextField text1 = new JTextField(width + " *sあ " + height);
		panel.add(text1);
		pack();
		// BoxLayout layout = new BoxLayout(panel, BoxLayout.X_AXIS);
		// panel.setLayout(layout);

		return panel;
	}

	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmm");

	private void capture() {
		try {
			// キャプチャの範囲
			Rectangle bounds = new Rectangle(
					frame.getLocation().x+capture_panel.getLocation().x,
					frame.getLocation().y+capture_panel.getLocation().y,
					capture_panel.getWidth(), capture_panel.getHeight());
			System.out.println(width + "\t" + height);
			System.out.println("frame:"+frame.getBounds());
			System.out.println("frame:"+capture_panel.getBounds());

			// これで画面キャプチャ
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(bounds);

			// 以下、出力処理
			String dirName = "E:\\Program files\\pleiades\\workspace\\OcrTest";
			// String fileName = "test_" + format.format(new Date()) + ".jpg";
			String fileName = "test.jpg";

			ImageIO.write(image, "jpg", new File(dirName, fileName));

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {

		new Test();

		// JFrame frame = new SimpleTransFrame3("Capture Test");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		// frame.getContentPane().setLayout(new FlowLayout());
		// frame.getContentPane().add(new JButton("Button"));
		//
		// frame.setBounds(100, 100, 400, 200);
		// frame.setVisible(true);
	}
}
