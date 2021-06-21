package date062103;

import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

//录入、修改、查询、删除。
class Manager extends JFrame {
	ArrayList<String[]> data = new ArrayList<String[]>();
	public Manager() throws IOException {
		File f = new File("ticketInfo.txt");
		BufferedReader bufr;
		try {
			bufr = new BufferedReader(new FileReader(f));
		} catch (Exception e) {
			f.createNewFile();
			bufr = new BufferedReader(new FileReader(f));
		}
		String line;
		while ((line=bufr.readLine())!=null) {
			data.add(line.split(" "));
		}
		bufr.close();
		
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
	
	class Main extends JPanel {
		///
		final int verticalGap = 10;
		final int horizontalGap = 70;
		final int labelTextGap = 10;
		final int borderTop = 50;
		final int borderBottom = 50;
		final int borderLeft = 100;
		final int borderRight = 100;
		///
		
		JButton query;
		JTextField busNumber;
		JTextField startTimeLeft, startTimeRight;
		JTextField arriveTimeLeft, arriveTimeRight;
		JTextField passStation, price;
		JButton addStation, delStation;
		JTextArea info;
		JTextField total, sold;
		JButton submit, reset;
		
		public Main() throws IOException {
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
				busNumber = new JTextField();
				numberBox.add(busNumber);
				numberBox.add(Box.createHorizontalStrut(labelTextGap));
				query = new JButton("查询");
				query.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String busnum = busNumber.getText();
						for (String[] i: data) {
							for (int j=0; j<i.length; j++) {
								System.out.print(i[j] + " ");
							}
							System.out.println();
							if (i[0].equals(busnum)) {
								startTimeLeft.setText(i[1]);
								startTimeRight.setText(i[2]);
								arriveTimeLeft.setText(i[3]);
								arriveTimeRight.setText(i[4]);
								String passed = "";
								ArrayList<String> ps = new ArrayList<String>();
								ArrayList<String> pp = new ArrayList<String>();
								int index = 5;
								for (; index<i.length; index++) {
									if (i[index].equals("STOP")) {
										break;
									}
									if (index%2==1) {
										ps.add(i[index]);
										passed = passed+i[index]+" ";
									}
									else {
										pp.add(i[index]);
										passed = passed+i[index]+";\n";
									}
								}
								passStation.setText("总共"+(index-5)/2+"站");
								price.setText("最多"+pp.get(pp.size()-1)+"元");
								info.setText(passed);
								index ++;
								total.setText(i[index]);
								index ++;
								sold.setText(i[index]);
							}
						}
					}
				});
				numberBox.add(query);
				add(numberBox);
				
				///
				add(Box.createVerticalStrut(verticalGap));
				///
				
				Box timeBox = Box.createHorizontalBox();
				timeBox.add(new JLabel("出发时间："));
				startTimeLeft = new JTextField();
				timeBox.add(startTimeLeft);
				timeBox.add(new JLabel(" ："));
				startTimeRight = new JTextField();
				timeBox.add(startTimeRight);
				timeBox.add(Box.createHorizontalStrut(labelTextGap));
				timeBox.add(new JLabel("到达时间："));
				arriveTimeLeft = new JTextField();
				timeBox.add(arriveTimeLeft);
				timeBox.add(new JLabel(" ："));
				arriveTimeRight = new JTextField();
				timeBox.add(arriveTimeRight);
				add(timeBox);

				///
				add(Box.createVerticalStrut(verticalGap));
				///
				
				Box stationBox = Box.createHorizontalBox();

				stationBox.add(new JLabel("经  停  站："));
				passStation = new JTextField();
				passStation.setColumns(12);
				stationBox.add(passStation);
				stationBox.add(Box.createHorizontalStrut(labelTextGap));
				
				stationBox.add(new JLabel("票        价："));
				price = new JTextField();
				stationBox.add(price);
				stationBox.add(Box.createHorizontalStrut(labelTextGap));
				
				addStation = new JButton("添加");
				stationBox.add(addStation);
				stationBox.add(Box.createHorizontalStrut(labelTextGap));
				delStation = new JButton("删除");
				stationBox.add(delStation);
				
				add(stationBox);
				
				///
				add(Box.createVerticalStrut(verticalGap));
				///
				
				Box addedBox = Box.createHorizontalBox();
				addedBox.add(new JLabel("已  添  加："));
				info = new JTextArea();
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
				total = new JTextField();
				ticketNumBox.add(total);
				ticketNumBox.add(Box.createHorizontalStrut(labelTextGap));
				ticketNumBox.add(new JLabel("已  售  出："));
				sold = new JTextField();
				ticketNumBox.add(sold);
				add(ticketNumBox);
				
				///
				add(Box.createVerticalStrut(30));
				///
				
				Box buttonBox = Box.createHorizontalBox();
				submit = new JButton("提交");
				reset = new JButton("重置");
				buttonBox.add(submit);
				buttonBox.add(Box.createHorizontalStrut(horizontalGap));
				buttonBox.add(reset);
				add(buttonBox);
				
				///
				add(Box.createVerticalStrut(borderBottom));
				///
			}
		}
	}

	class AddInfo extends Main {
		public AddInfo() throws IOException {
		}
	}
	class ModInfo extends Main {
		public ModInfo() throws IOException {
		}
	}
	class QueInfo extends Main {
		public QueInfo() throws IOException {
		}
	}
	class DelInfo extends Main {
		public DelInfo() throws IOException {
		}
	}
	class Book extends Main {
		public Book() throws IOException {
		}
	}
}

public class ManagerWindow {
	public static void main(String[] args) throws IOException {
		Manager manager = new Manager();
		manager.setVisible(true);
		manager.setSize(810, 500);
		manager.setLocationRelativeTo(null);
		manager.setTitle("长途汽车站票务信息管理系统");
		manager.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
