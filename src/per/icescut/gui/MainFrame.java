package per.icescut.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import per.icescut.action.RecordAction;
import per.icescut.util.Constants;
import per.icescut.util.Global;

/**
 * 主窗口，包括到功能窗口的按钮和对流水的查询
 * 
 * @author Alan Liang
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
	setTitle("记帐本");
	setResizable(false);
	initComponents();
	updateTotalPage();
	setupPage();
	updateTable(currentPage);
	setNavAvail();
	pack();
	Global.setCenterFrame(this);

	// 事件
	// 弹出记一笔窗口
	btnAdd.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (addFrame == null) {
		    addFrame = new AddFrame(MainFrame.this);
		}
		addFrame.setVisible(true);
		MainFrame.this.setEnabled(false);
	    }
	});
	// 弹出资产窗口
	btnAccount.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (accountFrame == null) {
		    accountFrame = new AccountFrame(MainFrame.this);
		}
		accountFrame.setVisible(true);
		MainFrame.this.setEnabled(false);
	    }
	});

	// 导航事件
	btnFirst.setActionCommand(String.valueOf(NAV_FIRST));
	btnFirst.addActionListener(navActionListener);
	btnPrev.setActionCommand(String.valueOf(NAV_PREV));
	btnPrev.addActionListener(navActionListener);
	btnNext.setActionCommand(String.valueOf(NAV_NEXT));
	btnNext.addActionListener(navActionListener);
	btnLast.setActionCommand(String.valueOf(NAV_LAST));
	btnLast.addActionListener(navActionListener);
	txtJump.setActionCommand(String.valueOf(NAV_JUMP));
	txtJump.addActionListener(navActionListener);
    }

    /**
     * 更新总页数
     */
    protected void updateTotalPage() {
	int addition = Global.recordCount % Constants.GUI_TABLE_ROW == 0 ? 0 : 1;
	totalPage = Global.recordCount / Constants.GUI_TABLE_ROW + addition;
    }

    /**
     * 根据页码更新表格的内容
     * 
     * @param page
     */
    protected void updateTable(int page) {
	dtmRecord = new DefaultTableModel(recordAction.queryByPage(page), columnNames);
	tblRecord.setModel(dtmRecord);
	tblRecord.repaint();
	tblRecord.updateUI();
    }

    /**
     * 设置页码
     */
    protected void setupPage() {
	StringBuilder sb = new StringBuilder();
	sb.append("第");
	sb.append(currentPage);
	sb.append("页/共");
	sb.append(totalPage);
	sb.append("页");
	lblPage.setText(sb.toString());
	txtJump.setText(String.valueOf(currentPage));
    }

    /**
     * 设置导航按钮的可用
     */
    private void setNavAvail() {
	btnFirst.setEnabled(true);
	btnPrev.setEnabled(true);
	btnNext.setEnabled(true);
	btnLast.setEnabled(true);
	if (currentPage == 1) {
	    btnFirst.setEnabled(false);
	    btnPrev.setEnabled(false);
	}
	if (currentPage == totalPage) {
	    btnLast.setEnabled(false);
	    btnNext.setEnabled(false);
	}
    }

    protected int getCurrentPage() {
	return currentPage;
    }

    /**
     * NetBeans生成代码
     */
    private void initComponents() {

	funcPanel = new javax.swing.JPanel();
	btnAdd = new javax.swing.JButton();
	btnAccount = new javax.swing.JButton();
	queryPanel = new javax.swing.JPanel();
	jPanel1 = new javax.swing.JPanel();
	btnFirst = new javax.swing.JButton();
	btnPrev = new javax.swing.JButton();
	txtJump = new javax.swing.JTextField();
	btnNext = new javax.swing.JButton();
	btnLast = new javax.swing.JButton();
	lblTitle = new javax.swing.JLabel();
	lblDetail = new javax.swing.JLabel();
	lblPage = new javax.swing.JLabel();
	spRecord = new javax.swing.JScrollPane();
	tblRecord = new javax.swing.JTable();

	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	funcPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

	btnAdd.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
	btnAdd.setText("记一笔");

	btnAccount.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
	btnAccount.setText("资产");

	javax.swing.GroupLayout funcPanelLayout = new javax.swing.GroupLayout(funcPanel);
	funcPanel.setLayout(funcPanelLayout);
	funcPanelLayout
		.setHorizontalGroup(funcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
				funcPanelLayout.createSequentialGroup()
					.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnAdd).addContainerGap())
		.addGroup(funcPanelLayout.createSequentialGroup().addContainerGap().addComponent(btnAccount)
			.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

	funcPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
		new java.awt.Component[] { btnAccount, btnAdd });

	funcPanelLayout.setVerticalGroup(funcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(funcPanelLayout.createSequentialGroup().addContainerGap().addComponent(btnAdd)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnAccount)
			.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

	queryPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

	btnFirst.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
	btnFirst.setText("<<");

	btnPrev.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
	btnPrev.setText("<");

	txtJump.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
	txtJump.setText("0");

	btnNext.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
	btnNext.setText(">");

	btnLast.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
	btnLast.setText(">>");

	lblTitle.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
	lblTitle.setText("流水：");

	lblDetail.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
	lblDetail.setText("全部");

	lblPage.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N

	javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	jPanel1.setLayout(jPanel1Layout);
	jPanel1Layout
		.setHorizontalGroup(
			jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
					.addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup().addComponent(lblTitle)
							.addPreferredGap(
								javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(lblDetail)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addGroup(jPanel1Layout.createSequentialGroup().addComponent(btnFirst).addGap(2, 2, 2)
				.addComponent(btnPrev)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(txtJump, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
					javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(btnNext)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(jPanel1Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
						.addComponent(lblPage))
					.addGroup(jPanel1Layout.createSequentialGroup().addComponent(btnLast).addGap(0,
						0, Short.MAX_VALUE)))))));

	jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
		new java.awt.Component[] { btnFirst, btnLast, btnNext, btnPrev });

	jPanel1Layout
		.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(jPanel1Layout.createSequentialGroup().addGap(0, 10, Short.MAX_VALUE)
				.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
					.addComponent(lblTitle).addComponent(lblDetail))
				.addGap(18, 18, 18)
				.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(btnNext).addComponent(lblPage).addComponent(btnPrev)
					.addComponent(btnFirst).addComponent(btnLast).addComponent(txtJump,
						javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE))));

	tblRecord.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
	tblRecord
		.setModel(new javax.swing.table.DefaultTableModel(
			new Object[][] { { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null } },
			new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
	spRecord.setViewportView(tblRecord);

	javax.swing.GroupLayout queryPanelLayout = new javax.swing.GroupLayout(queryPanel);
	queryPanel.setLayout(queryPanelLayout);
	queryPanelLayout
		.setHorizontalGroup(queryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addGroup(queryPanelLayout.createSequentialGroup().addComponent(spRecord,
				javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addGap(0, 0, Short.MAX_VALUE)));
	queryPanelLayout
		.setVerticalGroup(queryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(queryPanelLayout.createSequentialGroup()
				.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
					javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(spRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 250,
					javax.swing.GroupLayout.PREFERRED_SIZE)
				.addContainerGap(16, Short.MAX_VALUE)));

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup().addContainerGap()
			.addComponent(funcPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(queryPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addContainerGap()));
	layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup().addContainerGap()
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(funcPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
					javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addComponent(queryPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addContainerGap()));

	pack();
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String args[]) {
	Global.setup();
	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException ex) {
	    java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}

	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new MainFrame().setVisible(true);
	    }
	});
    }

    /**
     * 导航处理
     * 
     * @author Alan Liang
     *
     */
    private class NavActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    int type = Integer.parseInt(e.getActionCommand());
	    boolean change = false;
	    switch (type) {
	    case NAV_FIRST:
		if (currentPage != 1) {
		    currentPage = 1;
		    change = true;
		}
		break;
	    case NAV_PREV:
		if (currentPage > 1) {
		    currentPage--;
		    change = true;
		}
		break;
	    case NAV_NEXT:
		if (currentPage < totalPage) {
		    currentPage++;
		    change = true;
		}
		break;
	    case NAV_LAST:
		if (currentPage != totalPage) {
		    currentPage = totalPage;
		    change = true;
		}
		break;
	    case NAV_JUMP:
		int tmp = Integer.parseInt(txtJump.getText());
		if (tmp >= 1 && tmp <= totalPage && currentPage != tmp) {
		    currentPage = tmp;
		    change = true;
		} else {
		    JOptionPane.showMessageDialog(null, "页码不合法");
		}
		break;
	    }
	    if (change) {
		MainFrame.this.updateTable(currentPage);
		MainFrame.this.setupPage();
		MainFrame.this.setNavAvail();
	    }
	}
    }

    private String[] columnNames = { "日期", "描述", "金额" };
    private int currentPage = 1;
    private int totalPage;
    private static final int NAV_FIRST = 1;
    private static final int NAV_PREV = 2;
    private static final int NAV_NEXT = 3;
    private static final int NAV_LAST = 4;
    private static final int NAV_JUMP = 5;

    private javax.swing.JButton btnAccount;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JPanel funcPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDetail;
    private javax.swing.JLabel lblPage;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel queryPanel;
    private javax.swing.JScrollPane spRecord;
    private javax.swing.JTable tblRecord;
    private javax.swing.JTextField txtJump;

    private AddFrame addFrame;
    private AccountFrame accountFrame;
    private NavActionListener navActionListener = new NavActionListener();
    private DefaultTableModel dtmRecord = null;
    private RecordAction recordAction = Global.recordAction;
}
