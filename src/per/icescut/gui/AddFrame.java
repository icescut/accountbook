package per.icescut.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import per.icescut.action.AccountAction;
import per.icescut.action.RecordAction;
import per.icescut.entry.AType;
import per.icescut.util.ArrayUtil;
import per.icescut.util.Constants;
import per.icescut.util.Global;

/**
 * 记一笔窗口
 * 
 * @author Alan Liang
 */
public class AddFrame extends javax.swing.JFrame {

    /**
     * 创建及初始化窗口
     */
    public AddFrame(final MainFrame parent) {
	setTitle("记一笔");
	setResizable(false);
	// 设置字段
	cobOwner = new JComboBox(Constants.OWNERS);
	cobCategoryLv1 = new JComboBox(Constants.CATEGORY_LV1_PAY);
	cobCategoryLv2 = new JComboBox(Constants.CATEGORY_LV2_PAY[0]);
	cobAccount = new JComboBox(Global.getAccountNames());
	cobTransferIn = new JComboBox(Global.getAccountNames());
	cobTransferOut = new JComboBox(Global.getAccountNames());
	cobType = new JComboBox(AType.names());

	initComponents();

	initInputFields();
	
	Global.setCenterFrame(this);

	// 事件处理
	// 模态窗口及页码更新
	this.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		parent.updateTable(parent.getCurrentPage());
		parent.updateTotalPage();
		parent.setupPage();
		parent.setEnabled(true);
	    }

	    @Override
	    public void windowDeactivated(WindowEvent e) {
		// 禁止在主窗口中输入
		AddFrame.this.requestFocus();
	    }
	});
	// 按钮事件
	btnConfirm.addActionListener(confirmActionListener);
	btnCancel.addActionListener(cancelActionListener);
	btnReset.addActionListener(resetActionListener);
	// 根据收入转设置分类是否可用
	cobType.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		String type = cobType.getSelectedItem().toString();
		setupType(type);
	    }

	});
	// 根据主分类的选择、设置子分类
	cobCategoryLv1.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		String item = cobCategoryLv1.getSelectedItem().toString();
		setupCategoryLv2(item);
	    }
	});
	// 转入转出不能是同一个帐户
	// TODO 可否使得已选择的项在另一边不可选？
	cobTransferOut.addActionListener(transferActionListener);
	cobTransferIn.addActionListener(transferActionListener);
	// 限制输入长度
	txaRemark.getDocument().addDocumentListener(new DocumentListener() {

	    @Override
	    public void removeUpdate(DocumentEvent e) {
		// no action
	    }

	    @Override
	    public void insertUpdate(DocumentEvent e) {
		SwingUtilities.invokeLater(new Runnable() {

		    @Override
		    public void run() {
			int length = txaRemark.getText().length();
			if (length > 100) {
			    JOptionPane.showMessageDialog(null, "不能输入超过100个字符");
			    txaRemark.replaceRange("", length - 1, length);
			}
		    }
		});

	    }

	    @Override
	    public void changedUpdate(DocumentEvent e) {
		// no action
	    }
	});

	// 快捷键设置
	btnConfirm.registerKeyboardAction(confirmActionListener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
		JComponent.WHEN_IN_FOCUSED_WINDOW);
	btnCancel.registerKeyboardAction(cancelActionListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
		JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /**
     * 根据主分类的选择、设置子分类
     * 
     * @param item
     */
    private void setupCategoryLv2(String item) {
	AType t = AType.getTypeByName(cobType.getSelectedItem().toString());
	int index = -1;
	ComboBoxModel<String> cbmLv2 = null;
	switch (t) {
	case Pay:
	    index = ArrayUtil.IndexOfArray(Constants.CATEGORY_LV1_PAY, item);
	    // TODO handle if -1
	    cbmLv2 = new DefaultComboBoxModel<>(Constants.CATEGORY_LV2_PAY[index]);
	    break;
	case Income:
	    index = ArrayUtil.IndexOfArray(Constants.CATEGORY_LV1_INCOME, item);
	    // TODO handle if -1
	    cbmLv2 = new DefaultComboBoxModel<>(Constants.CATEGORY_LV2_INCOME[index]);
	    break;
	default:
	    // TODO 1
	    break;
	}
	cobCategoryLv2.setModel(cbmLv2);
    }

    /**
     * 当改变收入转的时候，更新分类及资产的显示
     * 
     * @param type
     *            收入转
     */
    private void setupType(String type) {
	String[] modelLv1 = null;
	String[] modelLv2 = null;
	AType t = AType.getTypeByName(type);
	switch (t) {
	case Pay:
	    modelLv1 = Constants.CATEGORY_LV1_PAY;
	    modelLv2 = Constants.CATEGORY_LV2_PAY[0];
	    shiftType(false);
	    break;
	case Income:
	    modelLv1 = Constants.CATEGORY_LV1_INCOME;
	    modelLv2 = Constants.CATEGORY_LV2_INCOME[0];
	    shiftType(false);
	    break;
	case Transfer:
	    shiftType(true);
	    break;
	default:
	    // TODO 1
	    break;
	}
	if (modelLv1 != null) {
	    ComboBoxModel<String> cbmLv1 = new DefaultComboBoxModel<>(modelLv1);
	    ComboBoxModel<String> cbmLv2 = new DefaultComboBoxModel<>(modelLv2);
	    cobCategoryLv1.setModel(cbmLv1);
	    cobCategoryLv2.setModel(cbmLv2);
	}
    }

    /**
     * 隐藏或显示转出转入
     * 
     * @param showTransfer
     */
    private void shiftType(boolean showTransfer) {
	// 隐藏分类和资产、显示转入与转出
	if (showTransfer) {
	    lblCategoryLv1.setVisible(false);
	    lblCategoryLv2.setVisible(false);
	    cobCategoryLv1.setVisible(false);
	    cobCategoryLv2.setVisible(false);
	    lblAccount.setVisible(false);
	    cobAccount.setVisible(false);
	    lblTransferIn.setVisible(true);
	    cobTransferIn.setVisible(true);
	    cobTransferIn.setSelectedIndex(1);
	    lblTransferOut.setVisible(true);
	    cobTransferOut.setVisible(true);
	} else {
	    // 显示分类和资产、隐藏转入与转出
	    lblCategoryLv1.setVisible(true);
	    lblCategoryLv2.setVisible(true);
	    cobCategoryLv1.setVisible(true);
	    cobCategoryLv2.setVisible(true);
	    lblTransferIn.setVisible(false);
	    cobTransferIn.setVisible(false);
	    lblTransferOut.setVisible(false);
	    cobTransferOut.setVisible(false);
	}
	pack();

    }

    /**
     * 输入一条记录后重置输入框的值
     */
    private void initAfterConfirm() {
	txtDate.setText(Constants.SDF.format(new Date()));
	txtAmount.setText("0.00");
	txaRemark.setText("");
    }

    private void initInputFields() {
	cobType.setSelectedItem(AType.Pay.getName());
	cobOwner.setSelectedItem(Constants.OWNERS[0]);
	setupType(AType.Pay.getName());
	initAfterConfirm();
    }

    private boolean verifyInput() {
	StringBuilder sb = new StringBuilder();
	int i = 0;
	// 检查日期
	String datestr = txtDate.getText();
	Date inputDate = null;
	Date startDate = new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime();
	Calendar endCal = new GregorianCalendar();
	endCal.add(Calendar.YEAR, 1);
	Date endDate = endCal.getTime();
	try {
	    inputDate = Constants.SDF.parse(datestr);
	    if (inputDate.before(startDate) || inputDate.after(endDate)) {
		sb.append(++i);
		sb.append(". ");
		sb.append(datestr);
		sb.append("不在合法的日期内（晚于1970-01-01并且不能超过一年后）\n");
	    }
	} catch (ParseException e) {
	    sb.append(++i);
	    sb.append(". ");
	    sb.append(datestr);
	    sb.append("不是合法的日期格式（YYYY-MM-DD）\n");
	}
	// 检查金额
	String amountstr = txtAmount.getText();
	if (!Pattern.matches("-?\\d+\\.?\\d*", amountstr)) {
	    sb.append(++i);
	    sb.append(". ");
	    sb.append(amountstr);
	    sb.append("不是合法的金额格式\n");
	} else {
	    double amount = Double.parseDouble(amountstr);
	    if (Math.abs(amount) > 9999999999.99) {
		sb.append(++i);
		sb.append(". ");
		sb.append(amountstr);
		sb.append("不能大于10亿");
	    }
	}

	if (sb.toString().length() == 0) {
	    return true;
	} else {
	    JOptionPane.showMessageDialog(null, sb.toString());
	    return false;
	}
    }

    /**
     * NetBeans生成代码
     */
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        addPanel = new javax.swing.JPanel();
        //cobCategoryLv2 = new javax.swing.JComboBox();
        lblType = new javax.swing.JLabel();
        //cobType = new javax.swing.JComboBox();
        lblOwner = new javax.swing.JLabel();
        lblCategoryLv1 = new javax.swing.JLabel();
        lblCategoryLv2 = new javax.swing.JLabel();
        //cobCategoryLv1 = new javax.swing.JComboBox();
        //cobOwner = new javax.swing.JComboBox();
        lblDate = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        lblAmount = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        lblRemark = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaRemark = new javax.swing.JTextArea();
        lblTransferOut = new javax.swing.JLabel();
        //cobTransferOut = new javax.swing.JComboBox();
        lblTransferIn = new javax.swing.JLabel();
        //cobTransferIn = new javax.swing.JComboBox();
        lblAccount = new javax.swing.JLabel();
        //cobAccount = new javax.swing.JComboBox();
        btnConfirm = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFont(new java.awt.Font("微软雅黑", 0, 24)); // NOI18N

        lblTitle.setFont(new java.awt.Font("微软雅黑", 0, 36)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("记一笔");

        addPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        cobCategoryLv2.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        lblType.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblType.setText("收入转：");

        cobType.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        lblOwner.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblOwner.setText("成员：");

        lblCategoryLv1.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblCategoryLv1.setText("分类：");

        lblCategoryLv2.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblCategoryLv2.setText(">");

        cobCategoryLv1.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        cobOwner.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        lblDate.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblDate.setText("日期：");

        txtDate.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        lblAmount.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblAmount.setText("金额：");

        txtAmount.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        lblRemark.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblRemark.setText("备注：");

        txaRemark.setColumns(20);
        txaRemark.setRows(5);
        jScrollPane1.setViewportView(txaRemark);

        lblTransferOut.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblTransferOut.setText("转出：");

        cobTransferOut.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        lblTransferIn.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblTransferIn.setText("转入：");

        cobTransferIn.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        lblAccount.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblAccount.setText("资产：");

        cobAccount.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        javax.swing.GroupLayout addPanelLayout = new javax.swing.GroupLayout(addPanel);
        addPanel.setLayout(addPanelLayout);
        addPanelLayout.setHorizontalGroup(
            addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(addPanelLayout.createSequentialGroup()
                        .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRemark)
                            .addGroup(addPanelLayout.createSequentialGroup()
                                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblType)
                                    .addComponent(lblCategoryLv1)
                                    .addComponent(lblTransferOut)
                                    .addComponent(lblDate)
                                    .addComponent(lblAccount))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cobAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(addPanelLayout.createSequentialGroup()
                                        .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cobCategoryLv1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cobType, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cobTransferOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(addPanelLayout.createSequentialGroup()
                                                .addComponent(lblTransferIn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cobTransferIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(addPanelLayout.createSequentialGroup()
                                                .addComponent(lblCategoryLv2)
                                                .addGap(45, 45, 45)
                                                .addComponent(cobCategoryLv2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(addPanelLayout.createSequentialGroup()
                                                .addComponent(lblOwner)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cobOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(addPanelLayout.createSequentialGroup()
                                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblAmount)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        addPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cobAccount, cobCategoryLv1, cobCategoryLv2, cobOwner, cobTransferIn, cobTransferOut, cobType, txtAmount, txtDate});

        addPanelLayout.setVerticalGroup(
            addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblType, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cobType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOwner, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cobOwner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategoryLv1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cobCategoryLv1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCategoryLv2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cobCategoryLv2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAccount)
                    .addComponent(cobAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTransferOut, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cobTransferOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTransferIn, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cobTransferIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAmount, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRemark)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );

        addPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblAccount, lblAmount, lblCategoryLv1, lblCategoryLv2, lblDate, lblOwner, lblTransferIn, lblTransferOut, lblType});

        addPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cobAccount, cobCategoryLv1});

        btnConfirm.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        btnConfirm.setText("确定");

        btnReset.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        btnReset.setText("重置");

        btnCancel.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        btnCancel.setText("取消");
        
        txaRemark.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConfirm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfirm)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReset)
                        .addComponent(btnCancel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private javax.swing.JPanel addPanel;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox cobCategoryLv1;
    private javax.swing.JComboBox cobCategoryLv2;
    private javax.swing.JComboBox cobOwner;
    private javax.swing.JComboBox cobType;
    private javax.swing.JComboBox cobAccount;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txaRemark;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblAccount;
    private javax.swing.JLabel lblCategoryLv1;
    private javax.swing.JLabel lblCategoryLv2;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblOwner;
    private javax.swing.JLabel lblRemark;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblType;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtDate;
    private javax.swing.JLabel lblTransferOut;
    private javax.swing.JLabel lblTransferIn;
    private javax.swing.JComboBox cobTransferOut;
    private javax.swing.JComboBox cobTransferIn;

    private RecordAction recordAction = Global.recordAction;
    private AccountAction accountAction = Global.accountAction;
    private ActionListener confirmActionListener = new ConfirmActionListener();
    private ActionListener cancelActionListener = new CancelActionListener();
    private ActionListener resetActionListener = new ResetActionListener();
    private ActionListener transferActionListener = new TransferActionListener();

    /**
     * 按下确定后，检查输入的合法性，插入记录，更新帐户
     * 
     * @author Alan Liang
     *
     */
    private class ConfirmActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    verifyInput();
	    String type = cobType.getSelectedItem().toString();
	    String owner = cobOwner.getSelectedItem().toString();
	    String categoryLv1 = cobCategoryLv1.getSelectedItem().toString();
	    String categoryLv2 = cobCategoryLv2.getSelectedItem().toString();
	    String transferIn = cobTransferIn.getSelectedItem().toString();
	    String transferOut = cobTransferOut.getSelectedItem().toString();
	    String account = cobAccount.getSelectedItem().toString();
	    String date = txtDate.getText();
	    String amount = txtAmount.getText();
	    String remark = txaRemark.getText();
	    // 设置Record
	    if (type.equals(AType.Transfer.getName())) {
		recordAction.setupRecordTransfer(type, owner, transferIn, transferOut, date, amount, remark);
		accountAction.updateAccountForTransfer(transferIn, transferOut, amount);
	    } else {
		recordAction.setupRecord(type, owner, categoryLv1, categoryLv2, account, date, amount, remark);
		accountAction.updateAccount(type, account, amount);
	    }

	    if (recordAction.insertRecord()) {
		JOptionPane.showMessageDialog(null, "写入记录成功");
		initAfterConfirm();
	    } else {
		JOptionPane.showMessageDialog(null, "记录失败");
		// TODO 记入LOG并且回滚
	    }
	}

    }

    /**
     * 当按下取消时，退出当前窗口
     * 
     * @author Alan Liang
     *
     */
    private class CancelActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    AddFrame.this.dispatchEvent(new WindowEvent(AddFrame.this, WindowEvent.WINDOW_CLOSING));
	}

    }

    /**
     * 当按下重置时，初始化输入框
     * 
     * @author Alan Liang
     *
     */
    private class ResetActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    initInputFields();
	}

    }

    /**
     * 限制转入转出转户不能是同一个转户
     * 
     * @author Alan Liang
     *
     */
    private class TransferActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (cobTransferIn.getSelectedItem().equals(cobTransferOut.getSelectedItem())) {
		JComboBox cob = (JComboBox) e.getSource();
		if (cob.getSelectedIndex() == 0) {
		    cob.setSelectedIndex(1);
		} else {
		    cob.setSelectedIndex(0);
		}
		JOptionPane.showMessageDialog(null, "转入与转出不能是同一个帐户");
	    }

	}

    }
}
