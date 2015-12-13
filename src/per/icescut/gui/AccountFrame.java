package per.icescut.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.table.DefaultTableModel;

import per.icescut.entry.Account;
import per.icescut.util.Global;

/**
 * 资产明细查询
 * @author Alan Liang
 */
public class AccountFrame extends javax.swing.JFrame {

    public AccountFrame(final MainFrame parent) {
	setTitle("资产查询");
	setResizable(false);
	initComponents();
        
        setupAsset();
        setupTable();
        
        Global.setCenterFrame(this);
        // 事件
        // 设置模态窗口
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
        	parent.setEnabled(true);
            }
            @Override
            public void windowDeactivated(WindowEvent e) {
        	AccountFrame.this.requestFocus();
            }
	});
    }
    
    private void setupAsset() {
	BigDecimal asset = new BigDecimal(0);
	BigDecimal liability = new BigDecimal(0);
	BigDecimal netAsset = new BigDecimal(0);
	int i = 0;
	for (Account a : Global.accountList) {
	    BigDecimal amount = a.getAmount();
	    netAsset = netAsset.add(amount);
	    if(amount.doubleValue() > 0) {
		asset = asset.add(amount);
	    } else {
		liability = liability.add(amount);
	    }
	}
	lblAssetAmount.setText(asset.toString());
	lblLiabilityAmount.setText(liability.toString());
	lblNetAssetAmount.setText(netAsset.toString());
    }
    
    /**
     * 设置表格的内容 
     */
    private void setupTable() {
	int len = Global.accountList.size();
	String[][] accounts = new String[len][2];
	int i = 0;
	for (Account a : Global.accountList) {
	    accounts[i][0] = a.getName();
	    accounts[i][1] = a.getAmount().toString();
	    i++;
	}
	dtmAccount = new DefaultTableModel(accounts, columnNames);
	tblAccount.setModel(dtmAccount);
	tblAccount.repaint();
	tblAccount.updateUI();
    }

    /**
     * netBeans生成
     */                          
    private void initComponents() {

        lblNetAssetTitle = new javax.swing.JLabel();
        lblNetAssetAmount = new javax.swing.JLabel();
        lblAssetTitle = new javax.swing.JLabel();
        lblAssetAmount = new javax.swing.JLabel();
        lblLiabilityTitle = new javax.swing.JLabel();
        lblLiabilityAmount = new javax.swing.JLabel();
        spAccount = new javax.swing.JScrollPane();
        tblAccount = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNetAssetTitle.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblNetAssetTitle.setText("净资产：");

        lblNetAssetAmount.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblNetAssetAmount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblAssetTitle.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblAssetTitle.setForeground(new java.awt.Color(255, 0, 0));
        lblAssetTitle.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblAssetTitle.setText("资产：");

        lblAssetAmount.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblAssetAmount.setForeground(new java.awt.Color(255, 0, 0));
        lblAssetAmount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblLiabilityTitle.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblLiabilityTitle.setForeground(new java.awt.Color(102, 205, 0));
        lblLiabilityTitle.setText("负债：");

        lblLiabilityAmount.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblLiabilityAmount.setForeground(new java.awt.Color(102, 205, 0));
        lblLiabilityAmount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        tblAccount.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        tblAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spAccount.setViewportView(tblAccount);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNetAssetTitle)
                            .addComponent(lblAssetTitle))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblAssetAmount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblLiabilityTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblLiabilityAmount))
                            .addComponent(lblNetAssetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblAssetAmount, lblLiabilityAmount, lblNetAssetAmount});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblAssetTitle, lblLiabilityTitle, lblNetAssetTitle});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNetAssetTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNetAssetAmount, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAssetTitle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblAssetAmount, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblLiabilityTitle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblLiabilityAmount, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblAssetAmount, lblLiabilityAmount, lblNetAssetAmount});

        pack();
    }                  

    private String[] columnNames = {"帐户", "资产负债"};
    private DefaultTableModel dtmAccount = null;
    
    private javax.swing.JLabel lblAssetAmount;
    private javax.swing.JLabel lblAssetTitle;
    private javax.swing.JLabel lblLiabilityAmount;
    private javax.swing.JLabel lblLiabilityTitle;
    private javax.swing.JLabel lblNetAssetAmount;
    private javax.swing.JLabel lblNetAssetTitle;
    private javax.swing.JScrollPane spAccount;
    private javax.swing.JTable tblAccount;                   
}
