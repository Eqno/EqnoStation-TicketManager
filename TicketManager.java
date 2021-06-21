package date062102;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import com.sun.security.auth.NTDomainPrincipal;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainPanel extends JPanel {
	///
	final int verticalGap = 10;
	final int horizontalGap = 70;
	final int labelTextGap = 10;
	final int borderTop = 50;
	final int borderBottom = 50;
	final int borderLeft = 100;
	final int borderRight = 100;
	///
	
	public MainPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(Box.createHorizontalStrut(borderLeft));
		add(new FrameBox());
		add(Box.createHorizontalStrut(borderRight));
	}
	class FrameBox extends JPanel {
		public FrameBox() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			///
			add(Box.createVerticalStrut(borderTop));
			///
			
			Box numberBox = Box.createHorizontalBox();
			numberBox.add(new JLabel("车        次："));
			JTextField busNumber = new JTextField();
			numberBox.add(busNumber);
			numberBox.add(Box.createHorizontalStrut(labelTextGap));
			JButton queryStation = new JButton("查询");
			numberBox.add(queryStation);
			add(numberBox);
			
			///
			add(Box.createVerticalStrut(verticalGap));
			///
			
			Box timeBox = Box.createHorizontalBox();
			timeBox.add(new JLabel("出发时间："));
			JTextField startTimeLeft = new JTextField();
			timeBox.add(startTimeLeft);
			timeBox.add(new JLabel(" — "));
			JTextField startTimeRight = new JTextField();
			timeBox.add(startTimeRight);
			timeBox.add(Box.createHorizontalStrut(labelTextGap));
			timeBox.add(new JLabel("到达时间："));
			JTextField arriveTimeLeft = new JTextField();
			timeBox.add(arriveTimeLeft);
			timeBox.add(new JLabel(" — "));
			JTextField arriveTimeRight = new JTextField();
			timeBox.add(arriveTimeRight);
			add(timeBox);

			///
			add(Box.createVerticalStrut(verticalGap));
			///
			
			Box stationBox = Box.createHorizontalBox();

			stationBox.add(new JLabel("经  停  站："));
			JTextField passStation = new JTextField();
			passStation.setColumns(12);
			stationBox.add(passStation);
			stationBox.add(Box.createHorizontalStrut(labelTextGap));
			
			stationBox.add(new JLabel("票        价："));
			JTextField price = new JTextField();
			stationBox.add(price);
			stationBox.add(Box.createHorizontalStrut(labelTextGap));
			
			JButton addStation = new JButton("添加");
			stationBox.add(addStation);
			stationBox.add(Box.createHorizontalStrut(labelTextGap));
			JButton delStation = new JButton("删除");
			stationBox.add(delStation);
			
			add(stationBox);
			
			///
			add(Box.createVerticalStrut(verticalGap));
			///
			
			Box addedBox = Box.createHorizontalBox();
			addedBox.add(new JLabel("已  添  加："));
			JTextArea info = new JTextArea();
			info.setLineWrap(true);
			info.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(
	        		info,
	                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
	                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
	        );
	        scrollPane.setPreferredSize(new Dimension(810, 100));
			addedBox.add(scrollPane);
			add(addedBox);
			
			///
			add(Box.createVerticalStrut(verticalGap));
			///
			
			Box ticketNumBox = Box.createHorizontalBox();
			ticketNumBox.add(new JLabel("车票总数："));
			JTextField total = new JTextField();
			ticketNumBox.add(total);
			ticketNumBox.add(Box.createHorizontalStrut(labelTextGap));
			ticketNumBox.add(new JLabel("已  售  出："));
			JTextField sold = new JTextField();
			ticketNumBox.add(sold);
			add(ticketNumBox);
			
			///
			add(Box.createVerticalStrut(30));
			///
			
			Box buttonBox = Box.createHorizontalBox();
			JButton submit = new JButton("提交");
			JButton reset = new JButton("重置");
			buttonBox.add(submit);
			buttonBox.add(Box.createHorizontalStrut(horizontalGap));
			buttonBox.add(reset);
			add(buttonBox);
			
			///
			add(Box.createVerticalStrut(borderBottom));
			///
			
			///
			ActionListener queryListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			};
			///
		}
	}
}

class AddInfo extends MainPanel {
	public AddInfo() {
		
	}
}
class ModInfo extends MainPanel {
	public ModInfo() {
	}
}
class QueInfo extends MainPanel {
	public QueInfo() {
	}
}
class DelInfo extends MainPanel {
	public DelInfo() {
	}
}
class Book extends MainPanel {
	public Book() {
	}
}

//录入、修改、查询、删除。
class Manager extends JFrame {
	public Manager() {
		JPanel mainPanel = new JPanel();
		CardLayout card = new CardLayout();
		mainPanel.setLayout(card);
		
		mainPanel.add(new AddInfo(), "录入");
		mainPanel.add(new ModInfo(), "修改");
		mainPanel.add(new QueInfo(), "查询");
		mainPanel.add(new DelInfo(), "删除");
		mainPanel.add(new Book(), "预定");
		JMenuBar menuBar = new JMenuBar();
		SelectButton addInfo = new SelectButton("录入");
		addInfo.setForeground(Color.RED);
		SelectButton modInfo = new SelectButton("修改");
		SelectButton queInfo = new SelectButton("查询");
		SelectButton delInfo = new SelectButton("删除");
		SelectButton book = new SelectButton("预定");
		
		///
		ActionListener switchListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				card.show(mainPanel, e.getActionCommand().trim());
				addInfo.setForeground(Color.BLACK);
				modInfo.setForeground(Color.BLACK);
				queInfo.setForeground(Color.BLACK);
				delInfo.setForeground(Color.BLACK);
				book.setForeground(Color.BLACK);
				((JButton) e.getSource()).setForeground(Color.RED);
			}
		};
		///
		
		addInfo.addActionListener(switchListener);
		modInfo.addActionListener(switchListener);
		queInfo.addActionListener(switchListener);
		delInfo.addActionListener(switchListener);
		book.addActionListener(switchListener);
		menuBar.add(addInfo);
		menuBar.add(modInfo);
		menuBar.add(queInfo);
		menuBar.add(delInfo);
		menuBar.add(book);
		setJMenuBar(menuBar);
		
		add(mainPanel);
	}
	class SelectButton extends JButton {
		public SelectButton(String s) {
			super("             "+s+"             ");
			setContentAreaFilled(false);
			setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		}
	}
}

public class TicketManager {
	public static void main(String[] args) {
		///
		final String[] usr = {"admin"};
		final String[] pwd = {"123456"};
		///
		
		///
		final int verticalGap = 20;
		final int horizontalGap = 70;
		final int labelTextGap = 10;
		final int borderTop = 30;
		final int borderBottom = 50;
		final int borderLeft = 150;
		final int borderRight = 150;
		///
	    
		JFrame login = new JFrame();
		login.setVisible(true);
		// login.setResizable(false);
		login.setSize(500, 300);
		login.setLocationRelativeTo(null);
		login.setTitle("长途汽车站票务信息管理系统");
		login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Container content = login.getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		///
		JTextField username = new JTextField();
		JPasswordField password = new JPasswordField();
		ActionListener JudgeLogIn = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputUsr = username.getText();
				String inputPwd = String.valueOf(password.getPassword());
				boolean flag = false;
				for (int i=0; i<usr.length; i++) {
					System.out.println(inputUsr+" "+inputPwd);
					if (inputUsr.equals(usr[i]) && inputPwd.equals(pwd[i])) {
						login.setVisible(false);
						flag = true;
						JOptionPane.showMessageDialog(null, inputUsr+", 欢迎回来！"); 

						Manager manager = new Manager();
						manager.setVisible(true);
						manager.setSize(810, 500);
						// manager.setResizable(false);
						manager.setLocationRelativeTo(null);
						manager.setTitle("长途汽车站票务信息管理系统");
						manager.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					}
				}
				if (!flag) {
					JOptionPane.showMessageDialog(null, "用户名或密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		password.addActionListener(JudgeLogIn);
		username.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				password.setText("");
				username.transferFocus();
			}
		});
		///
		
		///
		content.add(Box.createVerticalStrut(borderTop));
		///

		JLabel loginTip = new JLabel("管理员登录"); 
		loginTip.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
		loginTip.setAlignmentX(Box.CENTER_ALIGNMENT);
		content.add(loginTip);

		///
		content.add(Box.createVerticalStrut(verticalGap));
		///

		/*
		 * JPanel usernamePanel = new JPanel();
		 * usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		 * usernamePanel.add(new JLabel("用户名："));
		 * JTextField username = new JTextField();
		 * usernamePanel.add(username);
		 * content.add(usernamePanel);
		 */

		Box usernameBox = Box.createHorizontalBox();
		usernameBox.add(Box.createHorizontalStrut(borderLeft));
		usernameBox.add(new JLabel("用户名："));
		usernameBox.add(username);
		usernameBox.add(Box.createHorizontalStrut(borderRight));
		content.add(usernameBox);

		///
		content.add(Box.createVerticalStrut(10));
		///

		/*
		 * JPanel passwordPanel = new JPanel();
		 * passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		 * passwordPanel.add(new JLabel("密码："));
		 * JPasswordField password = new JPasswordField();
		 * passwordPanel.add(password);
		 * content.add(passwordPanel);
		 */

		Box passwordBox = Box.createHorizontalBox();
		passwordBox.add(Box.createHorizontalStrut(borderLeft));
		passwordBox.add(new JLabel("密    码："));
		passwordBox.add(password);
		passwordBox.add(Box.createHorizontalStrut(borderRight));
		content.add(passwordBox);

		///
		content.add(Box.createVerticalStrut(verticalGap));
		///e

		Box buttonBox = Box.createHorizontalBox();
		JButton loginButton = new JButton("登录");
		loginButton.addActionListener(JudgeLogIn);
		JButton closeButton = new JButton("关闭");
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonBox.add(loginButton);
		buttonBox.add(Box.createHorizontalStrut(horizontalGap));
		buttonBox.add(closeButton);
		content.add(buttonBox);
		
		///
		content.add(Box.createVerticalStrut(borderBottom));
		///
	}
}
