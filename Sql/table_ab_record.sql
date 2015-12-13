CREATE TABLE ab_record (
	id INT AUTO_INCREMENT PRIMARY KEY,
	type_value TINYINT NOT NULL,
	owner TINYINT,
	category_lv1 TINYINT,
	category_lv2 TINYINT,
	value_date DATE NOT NULL,
	amount DECIMAL(11,2) NOT NULL,
	account TINYINT,
	transfer_out TINYINT,
	transfer_in TINYINT,
	remark VARCHAR(300)
) ENGINE=MYISAM CHARSET gbk COLLATE gbk_chinese_ci;