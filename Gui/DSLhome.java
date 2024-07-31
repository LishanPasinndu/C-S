/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.MySQL;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author MSI
 */
public class DSLhome extends javax.swing.JFrame {

    String upiid;
    String upsid;

    DateTimeFormatter dttf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    LocalDateTime now = LocalDateTime.now();
    String date = dttf.format(now);
    DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Creates new form home
     */
    public DSLhome() {
        initComponents();

        loadcompany();
        loadproduct();
        loadpayment_type();
        loadref();
        loadrefup();
        loadcomboref();
        loadvehicle();
        loadroute();
        loadissue();
        dash();
        loadstockprint();
        loadvehicle();
        loadbalance();
        loadincome();

        dash.setVisible(true);
        stock.setVisible(false);
        ref.setVisible(false);
        issue.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);
        dashh.setBackground(new Color(1, 1, 59));

        namedate();

        stockdate.setText(date);

        label.setText("DashBoard");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/d1.png")));

        ImageIcon icon = new ImageIcon(getClass().getResource("/img/head.png"));
        setIconImage(icon.getImage());
    }

    public void loadincome() {

        try {

            ResultSet i = MySQL.search(" SELECT  * FROM `income` ");

            if (i.next()) {

                String spe = i.getString("spend");
                String inc = i.getString("income");

                Double newp = Double.valueOf(inc) - Double.valueOf(spe);
                String newpro = String.valueOf(newp);

                dash_cost.setText(spe);
                dash_income.setText(inc);
                dash_profit.setText(newpro);

                int costt;
                int incomee;
                int profitt;

                double cos = Double.parseDouble(newpro) * 100 / Double.parseDouble(spe);
                profitt = (int) Math.round(Math.abs(cos));

                double sum = Double.parseDouble(spe) + Double.parseDouble(inc);

                double spee = Double.parseDouble(spe) * 100 / sum;
                costt = (int) Math.round(Math.abs(spee));

                double incc = Double.parseDouble(inc) * 100 / sum;
                incomee = (int) Math.round(Math.abs(incc));

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= costt; i++) {
                            costpr.setValue(i);

                            try {
                                Thread.sleep(15);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i <= incomee; i++) {
                            incomepr.setValue(i);

                            try {
                                Thread.sleep(15);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i <= profitt; i++) {
                            profitpr.setValue(i);

                            try {
                                Thread.sleep(15);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                t.start();

            } else {

                dash_cost.setText("0.00");
                dash_income.setText("0.00");
                dash_profit.setText("0.00");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadbalance() {

        try {

            ResultSet rs = MySQL.search("SELECT * FROM `bank` ");
            rs.next();
            String money = rs.getString("money");
            jTextField4s.setText(df.format(Double.parseDouble(money)));

        } catch (Exception e) {
        }

    }

    public void updatebalance() {

        try {

        } catch (Exception e) {
        }

    }

    public void loadvehicle() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `vehicle` ");

            Vector v = new Vector();
            v.add("Select");

            while (rs.next()) {
                v.add(rs.getString("number"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            rvehicle.setModel(dcm);
            jComboBox3.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dash() {

        try {

            //count of company
            ResultSet tc = MySQL.search(" SELECT COUNT(`id`) AS totalcompany FROM `company`; ");
            tc.next();
            String company_count = tc.getString("totalcompany");
            totalc.setText(company_count);
            //count of company

            //count of ref
            ResultSet rc = MySQL.search(" SELECT COUNT(`id`) AS totalref FROM `ref`; ");
            rc.next();
            String ref_count = rc.getString("totalref");
            totalreff.setText(ref_count);
            //count of ref

            //count of product
            ResultSet pc = MySQL.search(" SELECT COUNT(`id`) AS totalp FROM `product`; ");
            pc.next();
            String product_count = pc.getString("totalp");
            totalpp.setText(product_count);
            //count of product

            //count of company
            ResultSet ic = MySQL.search(" SELECT COUNT(`id`) AS totali FROM `issue`; ");
            ic.next();
            String invoice_count = ic.getString("totali");
            totalii.setText(invoice_count);
            //count of company

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void namedate() {

        jLabel9.setText(date);

        try {

            ResultSet rs = MySQL.search("SELECT * FROM `user` ");

            if (rs.next()) {

                String name = rs.getString("name");
                jLabel7.setText(name);

            } else {
                jLabel7.setText("Accountant");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadcompany() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `company` ");

            Vector v = new Vector();
            v.add("Select");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);

            rcompany.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadroute() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `route` ");

            Vector v = new Vector();
            v.add("Select");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            billroute.setModel(dcm);
            //   jComboBox7.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadcomboref() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `company`.`name` LIKE '%DSL%' ");

            Vector v = new Vector();
            v.add("Select");

            while (rs.next()) {
                v.add(rs.getString("fname"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            jComboBox20.setModel(dcm);
            jComboBox1.setModel(dcm);
            jComboBox9.setModel(dcm);
            jComboBox11.setModel(dcm);
            jComboBox12.setModel(dcm);
            billref.setModel(dcm);
            jComboBox26.setModel(dcm);
            dailyref.setModel(dcm);
            jComboBox27.setModel(dcm);
            jComboBox20.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadpayment_type() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `payment_type` ");

            Vector v = new Vector();
            v.add("Select");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            //  jComboBox4.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadproduct() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `product` INNER JOIN `company` ON `company`.`id`=`product`.`company` WHERE `status_id`='1' AND `company`.`name` LIKE '%DSL%' ");

            Vector v = new Vector();
            v.add("Select");

            while (rs.next()) {
                v.add(rs.getString("pname"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            jComboBox2.setModel(dcm);
            jComboBox19.setModel(dcm);
            billproduct.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateTotal() {

        double total = 0;

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String t = jTable1.getValueAt(i, 5).toString();
            total = total + Double.parseDouble(t);
        }

        jTextField29.setText(df.format(total));
    }

    String tdyissuesid;

    public void loadstock() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `stock` INNER JOIN `ref` ON `ref`.`id`=`stock`.`ref` INNER JOIN `product` ON `product`.`id`=`stock`.`product` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `stock`.`status`='1' AND `company`.`name` LIKE '%DSL%' ORDER BY `stock`.`date` ASC ");

            DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("stock.id"));
                v.add(rs.getString("date"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("stock.invoice_no"));
                v.add(rs.getString("stock.code"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("stock.qty"));
                v.add(rs.getString("unit_price"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadstockprint() {

        try {

            ResultSet rs = MySQL.search("SELECT * FROM `stock` INNER JOIN `ref` ON `ref`.`id`=`stock`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` INNER JOIN `product` ON `product`.`id`=`stock`.`product` WHERE `company`.`name` LIKE '%DSL%' ORDER BY `stock`.`date` ASC ");

            DefaultTableModel dtm = (DefaultTableModel) jTable6.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("stock.id"));
                v.add(rs.getString("date"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("stock.invoice_no"));
                v.add(rs.getString("stock.code"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("stock.qty"));
                v.add(rs.getString("unit_price"));
                dtm.addRow(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stockhistoryload() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `history` INNER JOIN `ref` ON `ref`.`id`=`history`.`ref` INNER JOIN `product` ON `product`.`id`=`history`.`product` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `company`.`name` LIKE '%DSL%' ORDER BY `history`.`date` ASC  ;");

            DefaultTableModel dtm = (DefaultTableModel) jTable11.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("history.id"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("history.date"));
                v.add(rs.getString("invoice_no"));
                v.add(rs.getString("code"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("discount"));
                v.add(rs.getString("unit_price"));
                v.add(rs.getString("qty"));
                v.add(rs.getString("amount"));
                v.add(rs.getString("total"));
                v.add(rs.getString("main_di"));
                v.add(rs.getString("di_amount"));
                v.add(rs.getString("net_value"));

                dtm.addRow(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String dailystid;
    String dailyrefid;

    public void loadissue() {

        try {

            ResultSet rs = MySQL.search("SELECT * FROM `issue` INNER JOIN `product` ON `product`.`id`=`issue`.`product_id` INNER JOIN `stock` ON `stock`.`id`=`issue`.`stock_id`"
                    + " INNER JOIN `ref` ON `ref`.`id`=`issue`.`ref_id` INNER JOIN `route` ON `route`.`id`=`issue`.`route_id` INNER JOIN `vehicle` ON `vehicle`.`id`=`ref`.`vehicle` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `company`.`name` LIKE '%DSL%' ORDER BY `issue`.`date` ASC ; ");
            DefaultTableModel dtm = (DefaultTableModel) jTable10.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("stock.id"));
                v.add(rs.getString("issue.date"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("issue.qty"));
                v.add(rs.getString("issue.selling_price"));
                v.add(rs.getString("ref.fname") + " " + rs.getString("ref.lname"));
                v.add(rs.getString("route.name"));
                v.add(rs.getString("vehicle.number"));

                dtm.addRow(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loaddaily() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `bill` INNER JOIN `ref` ON `ref`.`id`=`bill`.`ref` INNER JOIN `route` ON `route`.`id`=`bill`.`route` INNER JOIN `product` ON `product`.`id`=`bill`.`product` ORDER BY `bill`.`id` ASC ; ");

            DefaultTableModel dtm = (DefaultTableModel) jTable14.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("bill.date"));
                v.add(rs.getString("ref.fname") + " " + rs.getString("ref.lname"));
                v.add(rs.getString("route.name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("bill.qty"));
                v.add(rs.getString("bill.price"));
                v.add(rs.getString("bill.amount"));
                v.add(rs.getString("bill.subtotal"));
                v.add(rs.getString("bill.cash"));
                v.add(rs.getString("bill.check"));
                v.add(rs.getString("bill.credit"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void collection() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `collection` INNER JOIN `ref` ON `ref`.`id`=`collection`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `company`.`name` LIKE '%DSL%' ORDER BY `collection`.`id` ASC ; ");

            DefaultTableModel dtm = (DefaultTableModel) jTable15.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("ref.fname") + " " + rs.getString("ref.lname"));
                v.add(rs.getString("collection.date"));
                v.add(rs.getString("cash"));
                v.add(rs.getString("check"));
                v.add(rs.getString("credit"));
                v.add(rs.getString("day"));
                v.add(rs.getString("c.collection"));
                v.add(rs.getString("total"));
                v.add(rs.getString("billpaid"));
                v.add(rs.getString("driver"));
                v.add(rs.getString("inhand"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchdaily() {

        try {

            String ref = jComboBox26.getSelectedItem().toString();
            String pname = jTextField42.getText();
            String shop = jTextField41.getText();
            String bill_no = jTextField43.getText();

            Vector queryVector = new Vector();

            if (ref.equals("Select")) {
                loaddaily();
            } else {
                queryVector.add("`ref`.`fname` = '" + ref + "'  ");
            }

            if (pname.isEmpty()) {
                loaddaily();
            } else {
                queryVector.add(" `product`.`pname` LIKE '%" + pname + "%'  ");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String td = null;
            String fd = null;

            if (jDateChooser12.getDate() != null) {
                fd = sdf.format(jDateChooser12.getDate());
            }

            if (jDateChooser13.getDate() != null) {
                td = sdf.format(jDateChooser13.getDate());
            }

            if (fd != null) {

                queryVector.add("`bill`.`date`>='" + fd + "'");

            }
            if (td != null) {

                queryVector.add("`bill`.`date`<='" + td + "'");

            }

            if (shop.isEmpty()) {
                loaddaily();
            } else {
                queryVector.add(" `bill`.`shop_name` LIKE '%" + shop + "%' ");
            }

            if (bill_no.isEmpty()) {
                loaddaily();
            } else {
                queryVector.add(" `bill`.`bill_no` LIKE '%" + bill_no + "%' ");
            }

            String wherequery = "WHERE";

            for (int i = 0; i < queryVector.size(); i++) {
                wherequery += " ";
                wherequery += queryVector.get(i);
                wherequery += " ";
                if (i != queryVector.size() - 1) {
                    wherequery += "AND";
                }
            }

            ResultSet rs = MySQL.search("SELECT * FROM `bill` INNER JOIN `ref` ON `ref`.`id`=`bill`.`ref` INNER JOIN `route` ON `route`.`id`=`bill`.`route` INNER JOIN `product` ON `product`.`id`=`bill`.`product` " + wherequery + " ORDER BY `bill`.`id` ASC ; ");

            DefaultTableModel dtm = (DefaultTableModel) jTable14.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("bill.date"));
                v.add(rs.getString("ref.fname") + " " + rs.getString("ref.lname"));
                v.add(rs.getString("route.name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("bill.qty"));
                v.add(rs.getString("bill.price"));
                v.add(rs.getString("bill.amount"));
                v.add(rs.getString("bill.subtotal"));
                v.add(rs.getString("bill.cash"));
                v.add(rs.getString("bill.check"));
                v.add(rs.getString("bill.credit"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchcollection() {

        try {

            String ref = jComboBox27.getSelectedItem().toString();

            Vector queryVector = new Vector();

            if (ref.equals("Select")) {
                collection();
            } else {
                queryVector.add("`ref`.`fname` = '" + ref + "' AND `company`.`name` LIKE '%DSL%' ");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String td = null;

            if (jDateChooser14.getDate() != null) {
                td = sdf.format(jDateChooser14.getDate());
            }

            if (td != null) {

                queryVector.add("`collection`.`date` = '" + td + "' AND `company`.`name` LIKE '%DSL%' ");

            }

            String wherequery = "WHERE";

            for (int i = 0; i < queryVector.size(); i++) {
                wherequery += " ";
                wherequery += queryVector.get(i);
                wherequery += " ";
                if (i != queryVector.size() - 1) {
                    wherequery += "AND";
                }
            }

            ResultSet rs = MySQL.search("SELECT * FROM `collection` INNER JOIN `ref` ON `ref`.`id`=`collection`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ORDER BY `collection`.`id` ASC ; ");

            DefaultTableModel dtm = (DefaultTableModel) jTable15.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("ref.fname") + " " + rs.getString("ref.lname"));
                v.add(rs.getString("collection.date"));
                v.add(rs.getString("cash"));
                v.add(rs.getString("check"));
                v.add(rs.getString("credit"));
                v.add(rs.getString("day"));
                v.add(rs.getString("c.collection"));
                v.add(rs.getString("total"));
                v.add(rs.getString("billpaid"));
                v.add(rs.getString("driver"));
                v.add(rs.getString("inhand"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void upitable() {

        double total = 0;

        for (int i = 0; i < jTable9.getRowCount(); i++) {
            String t = jTable9.getValueAt(i, 7).toString();
            total = total + Double.parseDouble(t);
        }

        jLabel236.setText(df.format(total));

    }

    public void searchissue() {

        try {

            String pname = jTextField25.getText();
            String rname = jTextField21.getText();

            Vector queryVector = new Vector();

            if (pname.isEmpty()) {
                loadissue();
            } else {
                queryVector.add(" `product`.`pname` LIKE '%" + pname + "%'  AND `company`.`name` LIKE '%DSL%' ");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String td = null;
            String fd = null;

            if (jDateChooser5.getDate() != null) {
                fd = sdf.format(jDateChooser5.getDate());
            }

            if (jDateChooser6.getDate() != null) {
                td = sdf.format(jDateChooser6.getDate());
            }

            if (fd != null) {

                queryVector.add("`issue`.`date`>='" + fd + "'  AND `company`.`name` LIKE '%DSL%' ");

            }
            if (td != null) {

                queryVector.add("`issue`.`date`<='" + td + "'  AND `company`.`name` LIKE '%DSL%' ");

            }

            if (rname.isEmpty()) {
                loadissue();
            } else {
                queryVector.add(" `ref`.`fname` LIKE '%" + rname + "%'  AND `company`.`name` LIKE '%DSL%' ");
            }

            String wherequery = "WHERE";

            for (int i = 0; i < queryVector.size(); i++) {
                wherequery += " ";
                wherequery += queryVector.get(i);
                wherequery += " ";
                if (i != queryVector.size() - 1) {
                    wherequery += "AND";
                }
            }

            ResultSet rs = MySQL.search("SELECT * FROM `issue` INNER JOIN `product` ON `product`.`id`=`issue`.`product_id` INNER JOIN `stock` ON `stock`.`id`=`issue`.`stock_id`"
                    + " INNER JOIN `ref` ON `ref`.`id`=`issue`.`ref_id` INNER JOIN `route` ON `route`.`id`=`issue`.`route_id` INNER JOIN `vehicle` ON `vehicle`.`id`=`ref`.`vehicle` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ORDER BY `issue`.`date` ASC ; ");
            DefaultTableModel dtm = (DefaultTableModel) jTable10.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("stock.id"));
                v.add(rs.getString("issue.date"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("issue.qty"));
                v.add(rs.getString("issue.selling_price"));
                v.add(rs.getString("ref.fname") + " " + rs.getString("ref.lname"));
                v.add(rs.getString("route.name"));
                v.add(rs.getString("vehicle.number"));

                dtm.addRow(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadref() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `ref` INNER JOIN `vehicle` ON `vehicle`.`id`=`ref`.`vehicle` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `status_id`='1' AND `company`.`name` LIKE '%DSL%' ");
            DefaultTableModel dtm = (DefaultTableModel) jTable4.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("fname"));
                v.add(rs.getString("lname"));
                v.add(rs.getString("nic"));
                v.add(rs.getString("mobile"));
                v.add(rs.getString("vehicle.number"));
                v.add(rs.getString("dfname"));
                v.add(rs.getString("dlname"));
                v.add(rs.getString("dnic"));
                v.add(rs.getString("dmobile"));
                v.add(rs.getString("company.name"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadrefup() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `ref` INNER JOIN `vehicle` ON `vehicle`.`id`=`ref`.`vehicle` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `status_id`='1' AND `company`.`name` LIKE '%DSL%' ");
            DefaultTableModel dtm = (DefaultTableModel) uptabel.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("fname"));
                v.add(rs.getString("lname"));
                v.add(rs.getString("nic"));
                v.add(rs.getString("mobile"));
                v.add(rs.getString("vehicle.number"));
                v.add(rs.getString("dfname"));
                v.add(rs.getString("dlname"));
                v.add(rs.getString("dnic"));
                v.add(rs.getString("dmobile"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadrefearch() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `ref` INNER JOIN `vehicle` ON `vehicle`.`id`=`ref`.`vehicle` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `status_id`='1' AND `company`.`name` LIKE '%DSL%' ");
            DefaultTableModel dtm = (DefaultTableModel) sreftable.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("fname"));
                v.add(rs.getString("lname"));
                v.add(rs.getString("nic"));
                v.add(rs.getString("mobile"));
                v.add(rs.getString("vehicle.number"));
                v.add(rs.getString("dfname"));
                v.add(rs.getString("dlname"));
                v.add(rs.getString("dnic"));
                v.add(rs.getString("dmobile"));
                v.add(rs.getString("company.name"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchstock() {

        try {

            String ref = jComboBox9.getSelectedItem().toString();
            String pname = jTextField15.getText();
            String uitprice = jTextField11.getText();
            String qty = jTextField12.getText();

            Vector queryVector = new Vector();

            if (ref.equals("Select")) {
                loadstock();
            } else {
                queryVector.add("`ref`.`fname` = '" + ref + "' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (pname.isEmpty()) {
                loadstock();
            } else {
                queryVector.add(" `product`.`pname` LIKE '%" + pname + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (uitprice.isEmpty()) {
                loadstock();
            } else {
                queryVector.add(" `unit_price` LIKE '%" + uitprice + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (qty.isEmpty()) {
                loadstock();
            } else {
                queryVector.add(" `qty` LIKE '%" + qty + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            String wherequery = "WHERE";

            for (int i = 0; i < queryVector.size(); i++) {
                wherequery += " ";
                wherequery += queryVector.get(i);
                wherequery += " ";
                if (i != queryVector.size() - 1) {
                    wherequery += "AND";
                }
            }

            ResultSet rs = MySQL.search("SELECT * FROM `stock` INNER JOIN `ref` ON `ref`.`id`=`stock`.`ref` INNER JOIN `product` ON `product`.`id`=`stock`.`product` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ORDER BY `stock`.`date` ASC ");

            DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("stock.id"));
                v.add(rs.getString("date"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("stock.invoice_no"));
                v.add(rs.getString("stock.code"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("stock.qty"));
                v.add(rs.getString("unit_price"));

                dtm.addRow(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchstockreport() {

        try {

            String ref = jComboBox11.getSelectedItem().toString();
            String pname = jTextField19.getText();
            String uitprice = jTextField13.getText();
            String qty = jTextField17.getText();

            Vector queryVector = new Vector();

            if (ref.equals("Select")) {
                loadstockprint();
            } else {
                queryVector.add("`ref`.`fname` = '" + ref + "'  ");
            }

            if (pname.isEmpty()) {
                loadstockprint();
            } else {
                queryVector.add(" `product`.`pname` LIKE '%" + pname + "%'  ");
            }

            if (uitprice.isEmpty()) {
                loadstockprint();
            } else {
                queryVector.add(" `unit_price` LIKE '%" + uitprice + "%' ");
            }

            if (qty.isEmpty()) {
                loadstockprint();
            } else {
                queryVector.add(" `qty` LIKE '%" + qty + "%' ");
            }

            String wherequery = "WHERE";

            for (int i = 0; i < queryVector.size(); i++) {
                wherequery += " ";
                wherequery += queryVector.get(i);
                wherequery += " ";
                if (i != queryVector.size() - 1) {
                    wherequery += "AND";
                }
            }

            ResultSet rs = MySQL.search("SELECT * FROM `stock` INNER JOIN `ref` ON `ref`.`id`=`stock`.`ref` INNER JOIN `product` ON `product`.`id`=`stock`.`product` " + wherequery + " ORDER BY `stock`.`date` ASC ");

            DefaultTableModel dtm = (DefaultTableModel) jTable6.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("stock.id"));
                v.add(rs.getString("date"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("stock.invoice_no"));
                v.add(rs.getString("stock.code"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("stock.qty"));
                v.add(rs.getString("unit_price"));
                dtm.addRow(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchhistorystock() {

        try {

            String ref = jComboBox12.getSelectedItem().toString();
            String pname = jTextField27.getText();
            String invoice = jTextField16.getText();
            String qty = jTextField23.getText();

            Vector queryVector = new Vector();

            if (ref.equals("Select")) {
                stockhistoryload();
            } else {
                queryVector.add("`ref`.`fname` = '" + ref + "' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (pname.isEmpty()) {
                stockhistoryload();
            } else {
                queryVector.add(" `product`.`pname` LIKE '%" + pname + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (invoice.isEmpty()) {
                stockhistoryload();
            } else {
                queryVector.add(" `invoice_no` LIKE '%" + invoice + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (qty.isEmpty()) {
                stockhistoryload();
            } else {
                queryVector.add(" `qty` LIKE '%" + qty + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String td = null;
            String fd = null;

            if (jDateChooser16.getDate() != null) {
                fd = sdf.format(jDateChooser16.getDate());
            }
            if (jDateChooser17.getDate() != null) {
                td = sdf.format(jDateChooser17.getDate());
            }

            if (fd != null) {

                queryVector.add("`history`.`date`>='" + fd + "' AND `company`.`name` LIKE '%DSL%' ");

            }
            if (td != null) {

                queryVector.add("`history`.`date`<='" + td + "' AND `company`.`name` LIKE '%DSL%' ");

            }

            String wherequery = "WHERE";

            for (int i = 0; i < queryVector.size(); i++) {
                wherequery += " ";
                wherequery += queryVector.get(i);
                wherequery += " ";
                if (i != queryVector.size() - 1) {
                    wherequery += "AND";
                }
            }

            ResultSet rs = MySQL.search("SELECT * FROM `history` INNER JOIN `ref` ON `ref`.`id`=`history`.`ref` INNER JOIN `product` ON `product`.`id`=`history`.`product` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ORDER BY `history`.`date` ASC  ;");

            DefaultTableModel dtm = (DefaultTableModel) jTable11.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("history.id"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("history.date"));
                v.add(rs.getString("invoice_no"));
                v.add(rs.getString("code"));
                v.add(rs.getString("product.pname"));
                v.add(rs.getString("discount"));
                v.add(rs.getString("unit_price"));
                v.add(rs.getString("qty"));
                v.add(rs.getString("amount"));
                v.add(rs.getString("total"));
                v.add(rs.getString("main_di"));
                v.add(rs.getString("di_amount"));
                v.add(rs.getString("net_value"));

                dtm.addRow(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchref() {

        try {

            String fname = sfname.getText();
            String lname = slname.getText();
            String nic = snic.getText();
            String mobile = smobile.getText();
            String ddfname = dfname.getText();
            String ddlname = dlname.getText();
            String ddnic = dnic.getText();
            String ddmobile = dmobile.getText();

            Vector queryVector = new Vector();

            //Sno
            if (fname.isEmpty()) {
                loadrefearch();
            } else {
                queryVector.add(" `fname` LIKE '%" + fname + "%' AND `company`.`name` LIKE '%DSL%' ");
            }
            //Sno

            //name
            if (lname.isEmpty()) {
                loadrefearch();
            } else {
                queryVector.add(" `lname` LIKE '%" + lname + "%' AND `company`.`name` LIKE '%DSL%' ");
            }
            //name

            //barcode
            if (nic.isEmpty()) {
                loadrefearch();
            } else {
                queryVector.add(" `nic` LIKE '%" + nic + "%' AND `company`.`name` LIKE '%DSL%' ");
            }
            //barcode

            //barcode
            if (mobile.isEmpty()) {
                loadrefearch();
            } else {
                queryVector.add(" `mobile` LIKE '%" + mobile + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (ddfname.isEmpty()) {
                loadrefearch();
            } else {
                queryVector.add(" `dfname` LIKE '%" + ddfname + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (ddlname.isEmpty()) {
                loadrefearch();
            } else {
                queryVector.add(" `dlname` LIKE '%" + ddlname + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (ddnic.isEmpty()) {
                loadrefearch();
            } else {
                queryVector.add(" `dnic` LIKE '%" + ddnic + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            if (ddmobile.isEmpty()) {
                loadrefearch();
            } else {
                queryVector.add(" `dmobile` LIKE '%" + ddmobile + "%' AND `company`.`name` LIKE '%DSL%' ");
            }

            String wherequery = "WHERE";

            for (int i = 0; i < queryVector.size(); i++) {
                wherequery += " ";
                wherequery += queryVector.get(i);
                wherequery += " ";
                if (i != queryVector.size() - 1) {
                    wherequery += "AND";
                }
            }

            ResultSet rs = MySQL.search("SELECT * FROM `ref` INNER JOIN `vehicle` ON `vehicle`.`id`=`ref`.`vehicle` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ");

            DefaultTableModel dtm = (DefaultTableModel) sreftable.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("fname"));
                v.add(rs.getString("lname"));
                v.add(rs.getString("nic"));
                v.add(rs.getString("mobile"));
                v.add(rs.getString("vehicle.number"));
                v.add(rs.getString("dfname"));
                v.add(rs.getString("dlname"));
                v.add(rs.getString("dnic"));
                v.add(rs.getString("dmobile"));
                v.add(rs.getString("company.name"));

                dtm.addRow(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void zero() {

        try {

            ResultSet hi = MySQL.search("SELECT COUNT(`id`) AS co FROM `stock` WHERE `qty`='0' AND `status_id`='1';");
            hi.next();
            String hic = hi.getString("co");

            int coun = Integer.parseInt(hic);

            for (int i = 0; i < coun; i++) {

                ResultSet es = MySQL.search(" SELECT * FROM `stock` WHERE `qty`='0' ");
                es.next();

                String zsid = es.getString("id");

                MySQL.iud("UPDATE `stock` SET `status_id`='2' WHERE `id`='" + zsid + "' ");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jLabel4 = new javax.swing.JLabel();
        pieChartBuilder1 = new org.knowm.xchart.PieChartBuilder();
        pieChartBuilder2 = new org.knowm.xchart.PieChartBuilder();
        pieChartBuilder3 = new org.knowm.xchart.PieChartBuilder();
        baseSeriesLines1 = new org.knowm.xchart.style.lines.BaseSeriesLines();
        oval1 = new org.knowm.xchart.style.markers.Oval();
        heatMapStyler1 = new org.knowm.xchart.style.HeatMapStyler();
        matlabSeriesMarkers1 = new org.knowm.xchart.style.markers.MatlabSeriesMarkers();
        fontColorDetector1 = new org.knowm.xchart.style.colors.FontColorDetector();
        cross1 = new org.knowm.xchart.style.markers.Cross();
        jFrame1 = new javax.swing.JFrame();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jTextField45 = new javax.swing.JTextField();
        jLabel233 = new javax.swing.JLabel();
        jLabel249 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        stockk = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        reff = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        issuee = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        retuu = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        dailyy = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        incomee = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        dashh = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        vehii = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        ccc = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        icon = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        dash = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        totalc = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        totalref = new javax.swing.JLabel();
        totalreff = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        totalp = new javax.swing.JLabel();
        totalpp = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        totali = new javax.swing.JLabel();
        totalii = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        dash_cost = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        dash_income = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        dash_profit = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        costpr = new javax.swing.JProgressBar();
        incomepr = new javax.swing.JProgressBar();
        profitpr = new javax.swing.JProgressBar();
        jPanel22 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        stock = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        reportstockbtn = new javax.swing.JButton();
        searchstockbtn = new javax.swing.JButton();
        addstockbtn = new javax.swing.JButton();
        stockhistorybtn = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        addstock = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        stockdate = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel70 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel75 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel62 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel101 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel102 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel113 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jLabel163 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jLabel164 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        searchstock = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel94 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        stockreports = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel123 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jLabel135 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton34 = new javax.swing.JButton();
        stockhistory = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel185 = new javax.swing.JLabel();
        jLabel188 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        jLabel191 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel205 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel231 = new javax.swing.JLabel();
        jLabel243 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jDateChooser16 = new com.toedter.calendar.JDateChooser();
        jDateChooser17 = new com.toedter.calendar.JDateChooser();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
        ref = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        searchrefbtn = new javax.swing.JButton();
        updaterefbtn = new javax.swing.JButton();
        addrefbtn = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        addref = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        rdlname = new javax.swing.JTextField();
        rdfname = new javax.swing.JTextField();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        rdnic = new javax.swing.JTextField();
        jLabel130 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        rdmobile = new javax.swing.JTextField();
        jPanel37 = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        rfname = new javax.swing.JTextField();
        jLabel117 = new javax.swing.JLabel();
        rmobile = new javax.swing.JTextField();
        jLabel118 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        rvehicle = new javax.swing.JComboBox<>();
        rlname = new javax.swing.JTextField();
        jLabel116 = new javax.swing.JLabel();
        rnic = new javax.swing.JTextField();
        jButton37 = new javax.swing.JButton();
        jLabel161 = new javax.swing.JLabel();
        rcompany = new javax.swing.JComboBox<>();
        jButton38 = new javax.swing.JButton();
        updateref = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jPanel36 = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel137 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane12 = new javax.swing.JScrollPane();
        uptabel = new javax.swing.JTable();
        jPanel60 = new javax.swing.JPanel();
        jLabel119 = new javax.swing.JLabel();
        uprfname = new javax.swing.JTextField();
        jLabel120 = new javax.swing.JLabel();
        uprlname = new javax.swing.JTextField();
        jLabel121 = new javax.swing.JLabel();
        uprnic = new javax.swing.JTextField();
        jLabel122 = new javax.swing.JLabel();
        uprmobile = new javax.swing.JTextField();
        jLabel136 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel61 = new javax.swing.JPanel();
        jLabel139 = new javax.swing.JLabel();
        updfname = new javax.swing.JTextField();
        jLabel142 = new javax.swing.JLabel();
        updlname = new javax.swing.JTextField();
        jLabel143 = new javax.swing.JLabel();
        updnic = new javax.swing.JTextField();
        jLabel160 = new javax.swing.JLabel();
        updmobile = new javax.swing.JTextField();
        searchref = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel144 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        sreftable = new javax.swing.JTable();
        jButton24 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jLabel146 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        slname = new javax.swing.JTextField();
        sfname = new javax.swing.JTextField();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        snic = new javax.swing.JTextField();
        smobile = new javax.swing.JTextField();
        jPanel51 = new javax.swing.JPanel();
        jLabel147 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        dlname = new javax.swing.JTextField();
        dfname = new javax.swing.JTextField();
        jLabel150 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        dnic = new javax.swing.JTextField();
        dmobile = new javax.swing.JTextField();
        issue = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        searchissuebtn = new javax.swing.JButton();
        tdyissuebtn = new javax.swing.JButton();
        jPanel42 = new javax.swing.JPanel();
        searchissue = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jLabel151 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jTextField21 = new javax.swing.JTextField();
        jLabel157 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        jLabel166 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        retu = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        retucmpnybtn = new javax.swing.JButton();
        retustockbtn = new javax.swing.JButton();
        jPanel49 = new javax.swing.JPanel();
        retustock = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel175 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jButton27 = new javax.swing.JButton();
        jLabel177 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox<>();
        jLabel178 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel179 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox<>();
        jLabel181 = new javax.swing.JLabel();
        jDateChooser9 = new com.toedter.calendar.JDateChooser();
        jLabel202 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel183 = new javax.swing.JLabel();
        jDateChooser10 = new com.toedter.calendar.JDateChooser();
        jLabel180 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jLabel207 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        retucmpny = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jLabel201 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable13 = new javax.swing.JTable();
        jLabel182 = new javax.swing.JLabel();
        gpname = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        gcname = new javax.swing.JLabel();
        jButton26 = new javax.swing.JButton();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        guprice = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        groute = new javax.swing.JLabel();
        gqty = new javax.swing.JLabel();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jLabel203 = new javax.swing.JLabel();
        gid = new javax.swing.JLabel();
        jLabel206 = new javax.swing.JLabel();
        gtotal = new javax.swing.JLabel();
        jLabel215 = new javax.swing.JLabel();
        gref = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        gdate = new javax.swing.JLabel();
        daily = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jLabel190 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jLabel194 = new javax.swing.JLabel();
        jLabel195 = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        printdailybtn = new javax.swing.JButton();
        searchdailybtn = new javax.swing.JButton();
        adddailybtn = new javax.swing.JButton();
        searchdailysumbtn = new javax.swing.JButton();
        jPanel56 = new javax.swing.JPanel();
        adddaily = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jLabel196 = new javax.swing.JLabel();
        issuedte1 = new javax.swing.JLabel();
        jLabel197 = new javax.swing.JLabel();
        jLabel199 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jButton32 = new javax.swing.JButton();
        jLabel204 = new javax.swing.JLabel();
        billqty = new javax.swing.JTextField();
        jLabel235 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel208 = new javax.swing.JLabel();
        billref = new javax.swing.JComboBox<>();
        jLabel230 = new javax.swing.JLabel();
        jLabel214 = new javax.swing.JLabel();
        billroute = new javax.swing.JComboBox<>();
        jLabel220 = new javax.swing.JLabel();
        billno = new javax.swing.JTextField();
        jLabel229 = new javax.swing.JLabel();
        billshop = new javax.swing.JTextField();
        billdate = new com.toedter.calendar.JDateChooser();
        billproduct = new javax.swing.JComboBox<>();
        billuprice = new javax.swing.JTextField();
        jLabel237 = new javax.swing.JLabel();
        billtotal = new javax.swing.JTextField();
        billsubtotal = new javax.swing.JTextField();
        jLabel236 = new javax.swing.JLabel();
        billcash = new javax.swing.JTextField();
        jLabel238 = new javax.swing.JLabel();
        billcredit = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel242 = new javax.swing.JLabel();
        billcnumber = new javax.swing.JTextField();
        jLabel245 = new javax.swing.JLabel();
        jLabel244 = new javax.swing.JLabel();
        billcbank = new javax.swing.JTextField();
        billcdate = new com.toedter.calendar.JDateChooser();
        billcamount = new javax.swing.JTextField();
        jLabel246 = new javax.swing.JLabel();
        adddailysum = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jLabel216 = new javax.swing.JLabel();
        jButton36 = new javax.swing.JButton();
        jLabel217 = new javax.swing.JLabel();
        dailyref = new javax.swing.JComboBox<>();
        jLabel218 = new javax.swing.JLabel();
        dailydate = new com.toedter.calendar.JDateChooser();
        jLabel219 = new javax.swing.JLabel();
        dailycash = new javax.swing.JTextField();
        jLabel221 = new javax.swing.JLabel();
        dailycheck = new javax.swing.JTextField();
        jLabel222 = new javax.swing.JLabel();
        dailycredit = new javax.swing.JTextField();
        jLabel239 = new javax.swing.JLabel();
        dailysale = new javax.swing.JTextField();
        jLabel240 = new javax.swing.JLabel();
        dailycc = new javax.swing.JTextField();
        jLabel241 = new javax.swing.JLabel();
        dailyamount = new javax.swing.JTextField();
        jLabel247 = new javax.swing.JLabel();
        dailypaid = new javax.swing.JTextField();
        jLabel248 = new javax.swing.JLabel();
        dailysalary = new javax.swing.JTextField();
        jLabel250 = new javax.swing.JLabel();
        dailyhand = new javax.swing.JTextField();
        searchdaily = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jLabel223 = new javax.swing.JLabel();
        jLabel224 = new javax.swing.JLabel();
        jComboBox26 = new javax.swing.JComboBox<>();
        jLabel225 = new javax.swing.JLabel();
        jDateChooser12 = new com.toedter.calendar.JDateChooser();
        jTextField41 = new javax.swing.JTextField();
        jLabel226 = new javax.swing.JLabel();
        jLabel227 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jDateChooser13 = new com.toedter.calendar.JDateChooser();
        jLabel228 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable14 = new javax.swing.JTable();
        jLabel234 = new javax.swing.JLabel();
        jTextField43 = new javax.swing.JTextField();
        searchdailysum = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jLabel251 = new javax.swing.JLabel();
        jLabel252 = new javax.swing.JLabel();
        jComboBox27 = new javax.swing.JComboBox<>();
        jLabel253 = new javax.swing.JLabel();
        jDateChooser14 = new com.toedter.calendar.JDateChooser();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable15 = new javax.swing.JTable();
        bank = new javax.swing.JPanel();
        jPanel65 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTextField4s = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        depo = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        with = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenuItem1.setText("jMenuItem1");

        jMenu5.setText("jMenu5");

        jMenu6.setText("jMenu6");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jMenu3.setText("jMenu3");

        jMenu4.setText("jMenu4");

        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel233.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel233.setText("Cash in hand :-");

        jLabel249.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel249.setText("Driver Salary :-");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(2, 2, 86));

        jPanel4.setBackground(new java.awt.Color(1, 1, 59));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/medium.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        stockk.setBackground(new java.awt.Color(57, 126, 189));
        stockk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockk.setMaximumSize(new java.awt.Dimension(32767, 60));
        stockk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockkMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Stock Managment");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stock.png"))); // NOI18N

        javax.swing.GroupLayout stockkLayout = new javax.swing.GroupLayout(stockk);
        stockk.setLayout(stockkLayout);
        stockkLayout.setHorizontalGroup(
            stockkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockkLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        stockkLayout.setVerticalGroup(
            stockkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        reff.setBackground(new java.awt.Color(57, 126, 189));
        reff.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reff.setMaximumSize(new java.awt.Dimension(32767, 60));
        reff.setPreferredSize(new java.awt.Dimension(199, 50));
        reff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reffMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Reference Agent");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ref1.png"))); // NOI18N

        javax.swing.GroupLayout reffLayout = new javax.swing.GroupLayout(reff);
        reff.setLayout(reffLayout);
        reffLayout.setHorizontalGroup(
            reffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reffLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        reffLayout.setVerticalGroup(
            reffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        issuee.setBackground(new java.awt.Color(57, 126, 189));
        issuee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        issuee.setMaximumSize(new java.awt.Dimension(32767, 60));
        issuee.setPreferredSize(new java.awt.Dimension(199, 50));
        issuee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                issueeMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Issue Products");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/issu.png"))); // NOI18N

        javax.swing.GroupLayout issueeLayout = new javax.swing.GroupLayout(issuee);
        issuee.setLayout(issueeLayout);
        issueeLayout.setHorizontalGroup(
            issueeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(issueeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        issueeLayout.setVerticalGroup(
            issueeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        retuu.setBackground(new java.awt.Color(57, 126, 189));
        retuu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        retuu.setMaximumSize(new java.awt.Dimension(32767, 60));
        retuu.setPreferredSize(new java.awt.Dimension(199, 50));
        retuu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                retuuMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Return Products");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return.png"))); // NOI18N

        javax.swing.GroupLayout retuuLayout = new javax.swing.GroupLayout(retuu);
        retuu.setLayout(retuuLayout);
        retuuLayout.setHorizontalGroup(
            retuuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(retuuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        retuuLayout.setVerticalGroup(
            retuuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        dailyy.setBackground(new java.awt.Color(57, 126, 189));
        dailyy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dailyy.setMaximumSize(new java.awt.Dimension(32767, 60));
        dailyy.setPreferredSize(new java.awt.Dimension(199, 50));
        dailyy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dailyyMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Daily Collection");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/daily.png"))); // NOI18N

        javax.swing.GroupLayout dailyyLayout = new javax.swing.GroupLayout(dailyy);
        dailyy.setLayout(dailyyLayout);
        dailyyLayout.setHorizontalGroup(
            dailyyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dailyyLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dailyyLayout.setVerticalGroup(
            dailyyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        incomee.setBackground(new java.awt.Color(57, 126, 189));
        incomee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        incomee.setMaximumSize(new java.awt.Dimension(32767, 60));
        incomee.setPreferredSize(new java.awt.Dimension(199, 50));
        incomee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                incomeeMouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("View Bank ");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/money.png"))); // NOI18N

        javax.swing.GroupLayout incomeeLayout = new javax.swing.GroupLayout(incomee);
        incomee.setLayout(incomeeLayout);
        incomeeLayout.setHorizontalGroup(
            incomeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(incomeeLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        incomeeLayout.setVerticalGroup(
            incomeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        dashh.setBackground(new java.awt.Color(57, 126, 189));
        dashh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dashh.setMaximumSize(new java.awt.Dimension(32767, 60));
        dashh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashhMouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("DashBoard");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/d1.png"))); // NOI18N

        javax.swing.GroupLayout dashhLayout = new javax.swing.GroupLayout(dashh);
        dashh.setLayout(dashhLayout);
        dashhLayout.setHorizontalGroup(
            dashhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashhLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashhLayout.setVerticalGroup(
            dashhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        jButton8.setBackground(new java.awt.Color(49, 131, 213));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Database Backup");
        jButton8.setBorder(null);
        jButton8.setBorderPainted(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        vehii.setBackground(new java.awt.Color(57, 126, 189));
        vehii.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vehii.setMaximumSize(new java.awt.Dimension(32767, 60));
        vehii.setPreferredSize(new java.awt.Dimension(199, 50));
        vehii.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vehiiMouseClicked(evt);
            }
        });

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/issu.png"))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Vehicle Stock");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout vehiiLayout = new javax.swing.GroupLayout(vehii);
        vehii.setLayout(vehiiLayout);
        vehiiLayout.setHorizontalGroup(
            vehiiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehiiLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        vehiiLayout.setVerticalGroup(
            vehiiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehiiLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(vehiiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        ccc.setBackground(new java.awt.Color(57, 126, 189));
        ccc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ccc.setMaximumSize(new java.awt.Dimension(32767, 60));
        ccc.setPreferredSize(new java.awt.Dimension(199, 50));
        ccc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cccMouseClicked(evt);
            }
        });

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stock.png"))); // NOI18N

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Cheque & Credit");
        jLabel31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout cccLayout = new javax.swing.GroupLayout(ccc);
        ccc.setLayout(cccLayout);
        cccLayout.setHorizontalGroup(
            cccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cccLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        cccLayout.setVerticalGroup(
            cccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cccLayout.createSequentialGroup()
                .addGroup(cccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(cccLayout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(ccc, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(vehii, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addComponent(dailyy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addComponent(retuu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addComponent(issuee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addComponent(reff, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addComponent(stockk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addComponent(dashh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(incomee, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(dashh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockk, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reff, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(issuee, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(retuu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dailyy, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vehii, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ccc, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(incomee, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dailyy, dashh, incomee, issuee, reff, retuu, stockk});

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Date |");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/date1.png"))); // NOI18N

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/d1.png"))); // NOI18N

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user1.png"))); // NOI18N

        label.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        label.setText("Dashboard");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Accountant");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setText("user | ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("2022/08/18");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 297, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(icon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(1195, 738));

        dash.setBackground(new java.awt.Color(255, 255, 255));
        dash.setPreferredSize(new java.awt.Dimension(1140, 642));

        jPanel5.setBackground(new java.awt.Color(214, 238, 246));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Total Comapnies");

        totalc.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        totalc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalc.setText("2");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ar.png"))); // NOI18N

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/company.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalc, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(totalc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel6.setBackground(new java.awt.Color(249, 229, 249));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        totalref.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        totalref.setText("Total Reference Agent");

        totalreff.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        totalreff.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalreff.setText("3");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ar.png"))); // NOI18N

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rre.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalreff, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(totalref, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalref, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(totalreff, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addContainerGap())
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel7.setBackground(new java.awt.Color(252, 252, 229));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        totalp.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        totalp.setText("Total Products");

        totalpp.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        totalpp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalpp.setText("25");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ar.png"))); // NOI18N

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pr.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalpp, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(totalp, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(totalpp, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addContainerGap())
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel8.setBackground(new java.awt.Color(228, 251, 228));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        totali.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        totali.setText("Total Invoices");

        totalii.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        totalii.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalii.setText("124");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ar.png"))); // NOI18N

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/repo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalii, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(totali, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totali, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(totalii, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addContainerGap())
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel9.setBackground(new java.awt.Color(247, 226, 226));
        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 102));
        jLabel39.setText("Todays Report");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 102));
        jSeparator1.setAlignmentX(1.0F);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 102));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Cost");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 102));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Income");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 102));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Profit");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        dash_cost.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        dash_cost.setForeground(new java.awt.Color(102, 102, 102));
        dash_cost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash_cost.setText("0.00");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dash_cost, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dash_cost, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        dash_income.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        dash_income.setForeground(new java.awt.Color(102, 102, 102));
        dash_income.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash_income.setText("0.00");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(dash_income, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dash_income, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        dash_profit.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        dash_profit.setForeground(new java.awt.Color(102, 102, 102));
        dash_profit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dash_profit.setText("0.00");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(dash_profit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dash_profit, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 102));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Todays Report");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 102));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Rs.");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 102));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Cost :-");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 102));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Income :-");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 102));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("Profit :-");

        costpr.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        costpr.setForeground(new java.awt.Color(0, 0, 153));
        costpr.setStringPainted(true);

        incomepr.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        incomepr.setForeground(new java.awt.Color(0, 0, 153));
        incomepr.setStringPainted(true);

        profitpr.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        profitpr.setForeground(new java.awt.Color(0, 0, 153));
        profitpr.setStringPainted(true);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(costpr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(incomepr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(profitpr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(72, 72, 72))
            .addComponent(jSeparator1)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(costpr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(incomepr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(profitpr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel10, jPanel11, jPanel12});

        jPanel22.setBackground(new java.awt.Color(220, 227, 244));
        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-product-100 (1).png"))); // NOI18N
        jLabel53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel53MouseClicked(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 153));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Manage Stock");
        jLabel54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel54MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel23.setBackground(new java.awt.Color(220, 227, 244));
        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lissue.png"))); // NOI18N
        jLabel59.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel59MouseClicked(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 0, 153));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("Issue Products");
        jLabel60.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel60MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jPanel25.setBackground(new java.awt.Color(220, 227, 244));
        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-return-64.png"))); // NOI18N
        jLabel61.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel61MouseClicked(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 153));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Return Products");
        jLabel62.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel62MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel26.setBackground(new java.awt.Color(220, 227, 244));
        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-money-100.png"))); // NOI18N
        jLabel63.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel63MouseClicked(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 153));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("View Profit");
        jLabel64.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel64MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel19.setBackground(new java.awt.Color(43, 90, 137));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Main Detailes Of System Of C & S Enterprises");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel20.setBackground(new java.awt.Color(43, 90, 137));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Main Features Of System Of C & S Enterprises");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel18.setBackground(new java.awt.Color(227, 231, 234));

        jLabel65.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(102, 102, 102));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel65.setText("Design & Developed by ");

        jLabel71.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(102, 102, 102));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Syntex Solutions ");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(102, 102, 102));
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("(since - 2022)");

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compnylogo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addGap(41, 41, 41)
                .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addGap(292, 292, 292))
            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel18Layout.createSequentialGroup()
                    .addGap(551, 551, 551)
                    .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(560, Short.MAX_VALUE)))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel65, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
            .addComponent(jLabel71, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel18Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(4, 4, 4)))
        );

        javax.swing.GroupLayout dashLayout = new javax.swing.GroupLayout(dash);
        dash.setLayout(dashLayout);
        dashLayout.setHorizontalGroup(
            dashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(dashLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashLayout.createSequentialGroup()
                        .addGroup(dashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dashLayout.createSequentialGroup()
                                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(3, 3, 3))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dashLayout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashLayout.createSequentialGroup()
                                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)))
                        .addGroup(dashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(dashLayout.createSequentialGroup()
                                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(3, 3, 3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(dashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(dashLayout.createSequentialGroup()
                                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(3, 3, 3))))
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dashLayout.setVerticalGroup(
            dashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        stock.setBackground(new java.awt.Color(234, 234, 247));
        stock.setPreferredSize(new java.awt.Dimension(1140, 642));

        jPanel44.setBackground(new java.awt.Color(227, 231, 234));

        jLabel103.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(102, 102, 102));
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel103.setText("Design & Developed by ");

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compnylogo.png"))); // NOI18N

        jLabel105.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(102, 102, 102));
        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("Syntex Solutions ");

        jLabel106.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(102, 102, 102));
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("(since - 2022)");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addComponent(jLabel103)
                .addGap(0, 0, 0)
                .addComponent(jLabel104)
                .addGap(0, 0, 0)
                .addComponent(jLabel105)
                .addGap(0, 0, 0)
                .addComponent(jLabel106)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel104, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel103, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel105, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel106, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel21.setBackground(new java.awt.Color(234, 234, 247));

        reportstockbtn.setBackground(new java.awt.Color(0, 51, 153));
        reportstockbtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        reportstockbtn.setForeground(new java.awt.Color(255, 255, 255));
        reportstockbtn.setText("Stock Reports");
        reportstockbtn.setBorderPainted(false);
        reportstockbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reportstockbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportstockbtnActionPerformed(evt);
            }
        });

        searchstockbtn.setBackground(new java.awt.Color(0, 51, 153));
        searchstockbtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        searchstockbtn.setForeground(new java.awt.Color(255, 255, 255));
        searchstockbtn.setText("Search stock");
        searchstockbtn.setBorderPainted(false);
        searchstockbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchstockbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchstockbtnActionPerformed(evt);
            }
        });

        addstockbtn.setBackground(new java.awt.Color(0, 0, 153));
        addstockbtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        addstockbtn.setForeground(new java.awt.Color(255, 255, 255));
        addstockbtn.setText("Add Stock");
        addstockbtn.setBorderPainted(false);
        addstockbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addstockbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addstockbtnActionPerformed(evt);
            }
        });

        stockhistorybtn.setBackground(new java.awt.Color(0, 51, 153));
        stockhistorybtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        stockhistorybtn.setForeground(new java.awt.Color(255, 255, 255));
        stockhistorybtn.setText("History of Stock");
        stockhistorybtn.setBorderPainted(false);
        stockhistorybtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockhistorybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockhistorybtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(addstockbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(searchstockbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(reportstockbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(stockhistorybtn, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addstockbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchstockbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reportstockbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stockhistorybtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel21Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {reportstockbtn, searchstockbtn});

        jPanel27.setBackground(new java.awt.Color(234, 234, 247));

        addstock.setBackground(new java.awt.Color(234, 234, 247));

        jPanel29.setBackground(new java.awt.Color(43, 90, 137));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel56.setText("Add to Stock");

        stockdate.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        stockdate.setForeground(new java.awt.Color(255, 255, 255));
        stockdate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stockdate.setText("| 2022/8/22");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(309, 309, 309)
                .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(351, 351, 351))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stockdate, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel69.setText("Select Product :-");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel70.setText("Quantity :-");

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel73.setText("Unit Price :-");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(102, 102, 102));
        jLabel74.setText("Rs");

        jButton3.setBackground(new java.awt.Color(0, 51, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Add to the Table");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton3.setBorderPainted(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Product", "Discount", "Quantity", "Unit Price", "Total Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel75.setText("Sub Total :-");

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel77.setText("NET Value :-");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 153, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Save");
        jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton4.setBorderPainted(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setPreferredSize(new java.awt.Dimension(41, 30));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel62.setBackground(new java.awt.Color(234, 234, 247));
        jPanel62.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)), "Main Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel68.setText("Select Ref :-");

        jLabel101.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel101.setText("Select Date :-");

        jDateChooser3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser3PropertyChange(evt);
            }
        });

        jLabel102.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel102.setText("Invoice No :-");

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3)
                    .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel62Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox1, jDateChooser3, jLabel101, jLabel68});

        jLabel109.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel109.setText("Product Code :-");

        jLabel113.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel113.setText("Discount (%) :-");

        jLabel163.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel163.setText("Total Amount :-");

        jTextField28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField28ActionPerformed(evt);
            }
        });

        jTextField29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField29ActionPerformed(evt);
            }
        });
        jTextField29.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField29KeyReleased(evt);
            }
        });

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel78.setText("Disscount (%) :-");

        jTextField30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField30ActionPerformed(evt);
            }
        });
        jTextField30.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField30KeyReleased(evt);
            }
        });

        jLabel164.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel164.setText("Amount :-");

        jTextField31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField31ActionPerformed(evt);
            }
        });
        jTextField31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField31KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout addstockLayout = new javax.swing.GroupLayout(addstock);
        addstock.setLayout(addstockLayout);
        addstockLayout.setHorizontalGroup(
            addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(addstockLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(addstockLayout.createSequentialGroup()
                        .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(addstockLayout.createSequentialGroup()
                                .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField26, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                            .addGroup(addstockLayout.createSequentialGroup()
                                .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                    .addComponent(jTextField1))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                        .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(addstockLayout.createSequentialGroup()
                                .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(289, 289, 289)
                                .addComponent(jButton1))
                            .addGroup(addstockLayout.createSequentialGroup()
                                .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel163, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(addstockLayout.createSequentialGroup()
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(addstockLayout.createSequentialGroup()
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextField28)))))
                    .addGroup(addstockLayout.createSequentialGroup()
                        .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel164, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        addstockLayout.setVerticalGroup(
            addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addstockLayout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(16, 16, 16)
                .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField28))
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(addstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5)
                    .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel164, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        addstockLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel113, jLabel73, jLabel74, jTextField2, jTextField6});

        addstockLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jComboBox2, jLabel109, jLabel69, jTextField26});

        addstockLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton4, jLabel164, jLabel75, jLabel77, jLabel78, jTextField29, jTextField30, jTextField31, jTextField5});

        addstockLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel163, jLabel70, jTextField1, jTextField28});

        searchstock.setBackground(new java.awt.Color(234, 234, 247));

        jPanel32.setBackground(new java.awt.Color(43, 90, 137));

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("Advanced Search of Stock");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jLabel93.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel93.setText("select ref :-");

        jComboBox9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox9ItemStateChanged(evt);
            }
        });

        jLabel94.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel94.setText("Unit Price :-");

        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField11KeyReleased(evt);
            }
        });

        jLabel95.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(102, 102, 102));
        jLabel95.setText("Rs");

        jLabel96.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel96.setText("Product Name :-");

        jLabel97.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel97.setText("Quantity :-");

        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField12KeyReleased(evt);
            }
        });

        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock Id", "Date", "ref", "invoice ", "code", "Product", "Quantity", "Unit Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
            jTable2.getColumnModel().getColumn(5).setResizable(false);
            jTable2.getColumnModel().getColumn(6).setResizable(false);
            jTable2.getColumnModel().getColumn(7).setResizable(false);
        }

        javax.swing.GroupLayout searchstockLayout = new javax.swing.GroupLayout(searchstock);
        searchstock.setLayout(searchstockLayout);
        searchstockLayout.setHorizontalGroup(
            searchstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(searchstockLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(searchstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchstockLayout.createSequentialGroup()
                        .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(searchstockLayout.createSequentialGroup()
                        .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)))
                .addGroup(searchstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField15, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jTextField12))
                .addGap(48, 48, 48))
            .addGroup(searchstockLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane2)
                .addGap(19, 19, 19))
        );
        searchstockLayout.setVerticalGroup(
            searchstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchstockLayout.createSequentialGroup()
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(searchstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11)
                    .addComponent(jLabel97, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField12))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addContainerGap())
        );

        searchstockLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox9, jLabel93, jLabel94, jLabel96, jLabel97, jTextField11, jTextField12, jTextField15});

        stockreports.setBackground(new java.awt.Color(234, 234, 247));

        jPanel39.setBackground(new java.awt.Color(43, 90, 137));

        jLabel123.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(255, 255, 255));
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setText("Print Stock Report");

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel123, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel123, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jLabel134.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel134.setText("Select ref :-");

        jComboBox11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox11ItemStateChanged(evt);
            }
        });

        jLabel135.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel135.setText("Unit Price :-");

        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField13KeyReleased(evt);
            }
        });

        jLabel140.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(102, 102, 102));
        jLabel140.setText("Rs");

        jLabel141.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel141.setText("Product Name :-");

        jLabel145.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel145.setText("Quantity :-");

        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });
        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField17KeyReleased(evt);
            }
        });

        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });
        jTextField19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField19KeyReleased(evt);
            }
        });

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "date", "ref", "invoice", "code", "product", "quantity", "unit price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane17.setViewportView(jTable6);
        if (jTable6.getColumnModel().getColumnCount() > 0) {
            jTable6.getColumnModel().getColumn(2).setResizable(false);
            jTable6.getColumnModel().getColumn(3).setResizable(false);
            jTable6.getColumnModel().getColumn(4).setResizable(false);
            jTable6.getColumnModel().getColumn(6).setResizable(false);
            jTable6.getColumnModel().getColumn(7).setResizable(false);
        }

        jButton34.setBackground(new java.awt.Color(0, 153, 0));
        jButton34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton34.setForeground(new java.awt.Color(255, 255, 255));
        jButton34.setText("Print Stock Report");
        jButton34.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton34.setBorderPainted(false);
        jButton34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton34.setPreferredSize(new java.awt.Dimension(41, 30));
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout stockreportsLayout = new javax.swing.GroupLayout(stockreports);
        stockreports.setLayout(stockreportsLayout);
        stockreportsLayout.setHorizontalGroup(
            stockreportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(stockreportsLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(stockreportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(stockreportsLayout.createSequentialGroup()
                        .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(stockreportsLayout.createSequentialGroup()
                        .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)))
                .addGroup(stockreportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel141, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel145, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stockreportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19)
                    .addComponent(jTextField17))
                .addGap(48, 48, 48))
            .addGroup(stockreportsLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane17)
                .addGap(19, 19, 19))
            .addGroup(stockreportsLayout.createSequentialGroup()
                .addGap(278, 278, 278)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(305, Short.MAX_VALUE))
        );
        stockreportsLayout.setVerticalGroup(
            stockreportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockreportsLayout.createSequentialGroup()
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(stockreportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel141, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(stockreportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel140, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel135, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel145, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        stockreportsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox11, jTextField13, jTextField17, jTextField19});

        stockhistory.setBackground(new java.awt.Color(234, 234, 247));

        jPanel40.setBackground(new java.awt.Color(43, 90, 137));

        jLabel185.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel185.setForeground(new java.awt.Color(255, 255, 255));
        jLabel185.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel185.setText("History of Stock");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel185, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel185, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jLabel188.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel188.setText("Select Ref :-");

        jComboBox12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox12ItemStateChanged(evt);
            }
        });
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });

        jLabel191.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel191.setText("invoice number :-");

        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField16KeyReleased(evt);
            }
        });

        jLabel205.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel205.setText("Product Name :-");

        jLabel209.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel209.setText("Quantity :-");

        jTextField23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField23ActionPerformed(evt);
            }
        });
        jTextField23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField23KeyReleased(evt);
            }
        });

        jLabel231.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel231.setText("Date From :-");

        jLabel243.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel243.setText("Date To :-");

        jTextField27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField27ActionPerformed(evt);
            }
        });
        jTextField27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField27KeyReleased(evt);
            }
        });

        jDateChooser16.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser16PropertyChange(evt);
            }
        });

        jDateChooser17.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser17PropertyChange(evt);
            }
        });

        jTable11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "date", "ref", "invoice", "code", "product", "dis", "unit price", "qty", "amount", "total", "main_dis", "dis_amount", "net value"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane20.setViewportView(jTable11);

        javax.swing.GroupLayout stockhistoryLayout = new javax.swing.GroupLayout(stockhistory);
        stockhistory.setLayout(stockhistoryLayout);
        stockhistoryLayout.setHorizontalGroup(
            stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(stockhistoryLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(stockhistoryLayout.createSequentialGroup()
                        .addComponent(jLabel188, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(stockhistoryLayout.createSequentialGroup()
                        .addGroup(stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel191, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel231, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField16))))
                .addGap(115, 115, 115)
                .addGroup(stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel243, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel205, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel209, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField27)
                    .addComponent(jDateChooser17, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .addComponent(jTextField23))
                .addGap(48, 48, 48))
            .addGroup(stockhistoryLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane20)
                .addGap(19, 19, 19))
        );
        stockhistoryLayout.setVerticalGroup(
            stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockhistoryLayout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel188, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel205, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel191, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel209, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField23, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addGroup(stockhistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel243, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel231, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        stockhistoryLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox12, jDateChooser16, jDateChooser17, jLabel188, jLabel191, jLabel205, jLabel209, jLabel231, jLabel243, jTextField16, jTextField23, jTextField27});

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addstock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel27Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchstock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel27Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(stockreports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(stockhistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addstock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel27Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchstock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel27Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(stockreports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(stockhistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout stockLayout = new javax.swing.GroupLayout(stock);
        stock.setLayout(stockLayout);
        stockLayout.setHorizontalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(stockLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        stockLayout.setVerticalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ref.setBackground(new java.awt.Color(228, 245, 245));
        ref.setPreferredSize(new java.awt.Dimension(1140, 642));

        jPanel45.setBackground(new java.awt.Color(227, 231, 234));

        jLabel107.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(102, 102, 102));
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel107.setText("Design & Developed by ");

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compnylogo.png"))); // NOI18N

        jLabel111.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(102, 102, 102));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("Syntex Solutions ");

        jLabel112.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(102, 102, 102));
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("(since - 2022)");

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addComponent(jLabel107)
                .addGap(0, 0, 0)
                .addComponent(jLabel110)
                .addGap(0, 0, 0)
                .addComponent(jLabel111)
                .addGap(0, 0, 0)
                .addComponent(jLabel112)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel110, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel107, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel111, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel112, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel28.setBackground(new java.awt.Color(228, 245, 245));

        searchrefbtn.setBackground(new java.awt.Color(0, 51, 153));
        searchrefbtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        searchrefbtn.setForeground(new java.awt.Color(255, 255, 255));
        searchrefbtn.setText("Search Reference Agent");
        searchrefbtn.setBorderPainted(false);
        searchrefbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchrefbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchrefbtnActionPerformed(evt);
            }
        });

        updaterefbtn.setBackground(new java.awt.Color(0, 51, 153));
        updaterefbtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        updaterefbtn.setForeground(new java.awt.Color(255, 255, 255));
        updaterefbtn.setText("Update Ref Detailes");
        updaterefbtn.setBorderPainted(false);
        updaterefbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updaterefbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updaterefbtnActionPerformed(evt);
            }
        });

        addrefbtn.setBackground(new java.awt.Color(0, 0, 153));
        addrefbtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        addrefbtn.setForeground(new java.awt.Color(255, 255, 255));
        addrefbtn.setText("Add Reference Agent");
        addrefbtn.setBorderPainted(false);
        addrefbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addrefbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addrefbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(addrefbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(updaterefbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(searchrefbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addGap(46, 46, 46))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addrefbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(updaterefbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchrefbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));

        addref.setBackground(new java.awt.Color(228, 245, 245));

        jPanel34.setBackground(new java.awt.Color(43, 90, 137));

        jLabel114.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 255, 255));
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel114.setText("Registration Reference Agent");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(427, 427, 427)
                .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(376, 376, 376))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jButton13.setBackground(new java.awt.Color(0, 51, 255));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Register");
        jButton13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton13.setBorderPainted(false);
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ref Id", "First Name", "Last Name", "NIC", "Mobile", "Vehicle", "Driver Fname", "Driver Lname", "Driver NIC", "Driver Mobile", "Comapny"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setResizable(false);
            jTable4.getColumnModel().getColumn(1).setResizable(false);
            jTable4.getColumnModel().getColumn(2).setResizable(false);
            jTable4.getColumnModel().getColumn(3).setResizable(false);
            jTable4.getColumnModel().getColumn(4).setResizable(false);
            jTable4.getColumnModel().getColumn(5).setResizable(false);
            jTable4.getColumnModel().getColumn(6).setResizable(false);
            jTable4.getColumnModel().getColumn(7).setResizable(false);
            jTable4.getColumnModel().getColumn(8).setResizable(false);
            jTable4.getColumnModel().getColumn(9).setResizable(false);
            jTable4.getColumnModel().getColumn(10).setResizable(false);
        }

        jPanel31.setBackground(new java.awt.Color(228, 245, 245));
        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)), "Driver Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 153))); // NOI18N

        rdlname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdlnameActionPerformed(evt);
            }
        });

        rdfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdfnameActionPerformed(evt);
            }
        });

        jLabel131.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel131.setText("Last Name :-");

        jLabel132.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel132.setText("NIC Number :-");

        rdnic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdnicActionPerformed(evt);
            }
        });

        jLabel130.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel130.setText("First Name :-");

        jLabel133.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel133.setText("Mobile  :-");

        rdmobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdmobileActionPerformed(evt);
            }
        });
        rdmobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rdmobileKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rdmobileKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel133, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel130, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rdfname)
                    .addComponent(rdmobile, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdlname, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdnic, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdlname)
                    .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdfname)
                    .addComponent(rdnic, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rdmobile)
                    .addComponent(jLabel133, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel37.setBackground(new java.awt.Color(228, 245, 245));
        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)), "Ref Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel115.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel115.setText("First Name :-");

        rfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfnameActionPerformed(evt);
            }
        });

        jLabel117.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel117.setText("Mobile  :-");

        rmobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmobileActionPerformed(evt);
            }
        });
        rmobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rmobileKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rmobileKeyTyped(evt);
            }
        });

        jLabel118.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel118.setText("Last Name :-");

        jLabel129.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel129.setText("Vehicle :-");

        rlname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rlnameActionPerformed(evt);
            }
        });

        jLabel116.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel116.setText("NIC Number :-");

        rnic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rnicActionPerformed(evt);
            }
        });

        jButton37.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton37.setText("+");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jLabel161.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel161.setText("Company :-");

        jButton38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton38.setText("+");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel117, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel115, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rfname)
                    .addComponent(rmobile, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel129, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(rvehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(rlname, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel161, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(rcompany, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton38, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                    .addComponent(rnic))
                .addContainerGap())
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(rfname)
                    .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rlname)
                    .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rnic))
                .addGap(18, 18, 18)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel161, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rvehicle)
                        .addComponent(jLabel129, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rmobile)
                        .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rcompany))
                    .addComponent(jButton38))
                .addGap(13, 13, 13))
        );

        jPanel37Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton37, jLabel115, jLabel116, jLabel117, jLabel118, jLabel129, rfname, rlname, rmobile, rnic, rvehicle});

        javax.swing.GroupLayout addrefLayout = new javax.swing.GroupLayout(addref);
        addref.setLayout(addrefLayout);
        addrefLayout.setHorizontalGroup(
            addrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(addrefLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(addrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );
        addrefLayout.setVerticalGroup(
            addrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addrefLayout.createSequentialGroup()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        updateref.setBackground(new java.awt.Color(228, 245, 245));

        jPanel35.setBackground(new java.awt.Color(43, 90, 137));

        jLabel124.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(255, 255, 255));
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("Update Reference Agent Detailes");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        jButton19.setBackground(new java.awt.Color(0, 153, 51));
        jButton19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Update Detaileas");
        jButton19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton19.setBorderPainted(false);
        jButton19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jPanel36.setBackground(new java.awt.Color(228, 245, 245));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 153));

        jLabel137.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(255, 0, 51));
        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel137.setText("*");

        jLabel138.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(102, 102, 102));
        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("First of All Double Click Table & Select Agent For Update you want !");

        jSeparator5.setForeground(new java.awt.Color(0, 0, 153));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(202, Short.MAX_VALUE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel137, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel137)
                    .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        uptabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "First Name", "Last Name", "NIC Number", "Mobile", "Vehicle", "Driver Fname", "Driver Lname", "Driver NIC", "Driver Mobile"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        uptabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                uptabelMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(uptabel);
        if (uptabel.getColumnModel().getColumnCount() > 0) {
            uptabel.getColumnModel().getColumn(0).setResizable(false);
            uptabel.getColumnModel().getColumn(1).setResizable(false);
            uptabel.getColumnModel().getColumn(2).setResizable(false);
            uptabel.getColumnModel().getColumn(3).setResizable(false);
            uptabel.getColumnModel().getColumn(4).setResizable(false);
            uptabel.getColumnModel().getColumn(5).setResizable(false);
            uptabel.getColumnModel().getColumn(6).setResizable(false);
            uptabel.getColumnModel().getColumn(7).setResizable(false);
            uptabel.getColumnModel().getColumn(8).setResizable(false);
            uptabel.getColumnModel().getColumn(9).setResizable(false);
        }

        jPanel60.setBackground(new java.awt.Color(228, 245, 245));
        jPanel60.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)), "Referent Agent Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel119.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel119.setText("First Name :-");

        uprfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uprfnameActionPerformed(evt);
            }
        });

        jLabel120.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel120.setText("Last Name :-");

        uprlname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uprlnameActionPerformed(evt);
            }
        });

        jLabel121.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel121.setText("NIC Number :-");

        uprnic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uprnicActionPerformed(evt);
            }
        });

        jLabel122.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel122.setText("Mobile Number :-");

        uprmobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uprmobileActionPerformed(evt);
            }
        });
        uprmobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                uprmobileKeyTyped(evt);
            }
        });

        jLabel136.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel136.setText("Vehicle Number :-");

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel60Layout.createSequentialGroup()
                        .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uprfname, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel60Layout.createSequentialGroup()
                        .addComponent(jLabel122)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uprmobile, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel60Layout.createSequentialGroup()
                        .addComponent(jLabel120)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(uprlname, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel121)
                        .addGap(18, 18, 18)
                        .addComponent(uprnic, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel60Layout.createSequentialGroup()
                        .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uprnic, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel121)
                    .addComponent(uprlname, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uprfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(uprmobile)
                    .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel136, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jComboBox3))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel60Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel119, jLabel120, jLabel121, uprfname, uprlname, uprnic});

        jPanel61.setBackground(new java.awt.Color(228, 245, 245));
        jPanel61.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)), "Drivert Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel139.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel139.setText("First Name :-");

        updfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updfnameActionPerformed(evt);
            }
        });

        jLabel142.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel142.setText("Last Name :-");

        updlname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updlnameActionPerformed(evt);
            }
        });

        jLabel143.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel143.setText("NIC Number :-");

        updnic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updnicActionPerformed(evt);
            }
        });

        jLabel160.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel160.setText("Mobile Number :-");

        updmobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updmobileActionPerformed(evt);
            }
        });
        updmobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                updmobileKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel61Layout.createSequentialGroup()
                        .addComponent(jLabel143)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updnic))
                    .addGroup(jPanel61Layout.createSequentialGroup()
                        .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updfname, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel61Layout.createSequentialGroup()
                        .addComponent(jLabel160)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updmobile))
                    .addGroup(jPanel61Layout.createSequentialGroup()
                        .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updlname, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(updlname, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updmobile)
                    .addComponent(jLabel160, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updnic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel143, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel61Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel139, jLabel142, updfname, updlname});

        jPanel61Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel143, jLabel160, updmobile, updnic});

        javax.swing.GroupLayout updaterefLayout = new javax.swing.GroupLayout(updateref);
        updateref.setLayout(updaterefLayout);
        updaterefLayout.setHorizontalGroup(
            updaterefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(updaterefLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updaterefLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(updaterefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane12)
                    .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        updaterefLayout.setVerticalGroup(
            updaterefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updaterefLayout.createSequentialGroup()
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        searchref.setBackground(new java.awt.Color(228, 245, 245));

        jPanel38.setBackground(new java.awt.Color(43, 90, 137));

        jLabel144.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel144.setText("Advanced Search of Reference Agent");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel144, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel144, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        sreftable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ref Id", "First Name", "Last Name", "NIC", "Mobile", "Vehicle", "Driver Fname", "Driver Lname", "Driver NIC", "Driver Mobile", "Company"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(sreftable);
        if (sreftable.getColumnModel().getColumnCount() > 0) {
            sreftable.getColumnModel().getColumn(0).setResizable(false);
            sreftable.getColumnModel().getColumn(1).setResizable(false);
            sreftable.getColumnModel().getColumn(2).setResizable(false);
            sreftable.getColumnModel().getColumn(3).setResizable(false);
            sreftable.getColumnModel().getColumn(4).setResizable(false);
            sreftable.getColumnModel().getColumn(5).setResizable(false);
            sreftable.getColumnModel().getColumn(6).setResizable(false);
            sreftable.getColumnModel().getColumn(7).setResizable(false);
            sreftable.getColumnModel().getColumn(8).setResizable(false);
            sreftable.getColumnModel().getColumn(9).setResizable(false);
            sreftable.getColumnModel().getColumn(10).setResizable(false);
        }

        jButton24.setBackground(new java.awt.Color(204, 0, 0));
        jButton24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton24.setForeground(new java.awt.Color(255, 255, 255));
        jButton24.setText("Delete Reference Agent");
        jButton24.setBorderPainted(false);
        jButton24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jPanel43.setBackground(new java.awt.Color(228, 245, 245));
        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)), "Referent Agent Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel146.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel146.setText("First Name :-");

        jLabel152.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel152.setText("Last Name :-");

        slname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slnameActionPerformed(evt);
            }
        });
        slname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                slnameKeyReleased(evt);
            }
        });

        sfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sfnameActionPerformed(evt);
            }
        });
        sfname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sfnameKeyReleased(evt);
            }
        });

        jLabel148.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel148.setText("NIC Number :-");

        jLabel149.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel149.setText("Mobile Number :-");

        snic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snicActionPerformed(evt);
            }
        });
        snic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                snicKeyReleased(evt);
            }
        });

        smobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smobileActionPerformed(evt);
            }
        });
        smobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                smobileKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel146, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel152, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sfname)
                    .addComponent(slname, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel149, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel148, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(smobile)
                    .addComponent(snic, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sfname, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snic, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel148, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel149, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slname)
                    .addComponent(jLabel152, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(smobile, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel43Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel146, jLabel149, jLabel152, sfname, slname, smobile});

        jPanel51.setBackground(new java.awt.Color(228, 245, 245));
        jPanel51.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)), "Driver Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel147.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel147.setText("First Name :-");

        jLabel155.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel155.setText("Last Name :-");

        dlname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlnameActionPerformed(evt);
            }
        });
        dlname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dlnameKeyReleased(evt);
            }
        });

        dfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dfnameActionPerformed(evt);
            }
        });
        dfname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dfnameKeyReleased(evt);
            }
        });

        jLabel150.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel150.setText("NIC Number :-");

        jLabel158.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel158.setText("Mobile Number :-");

        dnic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dnicActionPerformed(evt);
            }
        });
        dnic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dnicKeyReleased(evt);
            }
        });

        dmobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dmobileActionPerformed(evt);
            }
        });
        dmobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dmobileKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel147, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dfname)
                    .addComponent(dlname, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel158, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dmobile)
                    .addComponent(dnic, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel51Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dfname, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dnic, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel158, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dlname, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dmobile, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel51Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dfname, dlname, dmobile, dnic, jLabel147, jLabel150, jLabel155, jLabel158});

        javax.swing.GroupLayout searchrefLayout = new javax.swing.GroupLayout(searchref);
        searchref.setLayout(searchrefLayout);
        searchrefLayout.setHorizontalGroup(
            searchrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(searchrefLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(searchrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );
        searchrefLayout.setVerticalGroup(
            searchrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchrefLayout.createSequentialGroup()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton24)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addref, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(updateref, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel33Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchref, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addref, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(updateref, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel33Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchref, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout refLayout = new javax.swing.GroupLayout(ref);
        ref.setLayout(refLayout);
        refLayout.setHorizontalGroup(
            refLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(refLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        refLayout.setVerticalGroup(
            refLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, refLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        issue.setBackground(new java.awt.Color(236, 253, 236));
        issue.setPreferredSize(new java.awt.Dimension(1140, 642));

        jPanel46.setBackground(new java.awt.Color(227, 231, 234));

        jLabel125.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(102, 102, 102));
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel125.setText("Design & Developed by ");

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel126.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compnylogo.png"))); // NOI18N

        jLabel127.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(102, 102, 102));
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("Syntex Solutions ");

        jLabel128.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(102, 102, 102));
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel128.setText("(since - 2022)");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addComponent(jLabel125)
                .addGap(0, 0, 0)
                .addComponent(jLabel126)
                .addGap(0, 0, 0)
                .addComponent(jLabel127)
                .addGap(0, 0, 0)
                .addComponent(jLabel128)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel126, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel125, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel127, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel128, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel41.setBackground(new java.awt.Color(236, 253, 236));

        searchissuebtn.setBackground(new java.awt.Color(0, 51, 153));
        searchissuebtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        searchissuebtn.setForeground(new java.awt.Color(255, 255, 255));
        searchissuebtn.setText("Search & View Issue Products");
        searchissuebtn.setBorderPainted(false);
        searchissuebtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchissuebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchissuebtnActionPerformed(evt);
            }
        });

        tdyissuebtn.setBackground(new java.awt.Color(0, 0, 153));
        tdyissuebtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tdyissuebtn.setForeground(new java.awt.Color(255, 255, 255));
        tdyissuebtn.setText("Today Issue Products");
        tdyissuebtn.setBorderPainted(false);
        tdyissuebtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tdyissuebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tdyissuebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(tdyissuebtn, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addGap(134, 134, 134)
                .addComponent(searchissuebtn, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addGap(91, 91, 91))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tdyissuebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchissuebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));

        searchissue.setBackground(new java.awt.Color(236, 253, 236));

        jPanel52.setBackground(new java.awt.Color(43, 90, 137));

        jLabel151.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 255, 255));
        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel151.setText("Advanced Search of Isuue Products");

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel151, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel151, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jLabel156.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel156.setText("Date From :-");

        jDateChooser5.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser5PropertyChange(evt);
            }
        });

        jTextField21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField21ActionPerformed(evt);
            }
        });
        jTextField21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField21KeyReleased(evt);
            }
        });

        jLabel157.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel157.setText("Ref Name :-");

        jLabel159.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel159.setText("Product Name :-");

        jTextField25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField25ActionPerformed(evt);
            }
        });
        jTextField25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField25KeyReleased(evt);
            }
        });

        jDateChooser6.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser6PropertyChange(evt);
            }
        });

        jLabel166.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel166.setText("Date To :-");

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock Id", "date", "Product Name", "Quantity", "Selling price", "Reference Agent", "Route", "Vehical Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(jTable10);
        if (jTable10.getColumnModel().getColumnCount() > 0) {
            jTable10.getColumnModel().getColumn(0).setResizable(false);
            jTable10.getColumnModel().getColumn(1).setResizable(false);
            jTable10.getColumnModel().getColumn(2).setResizable(false);
            jTable10.getColumnModel().getColumn(3).setResizable(false);
            jTable10.getColumnModel().getColumn(4).setResizable(false);
            jTable10.getColumnModel().getColumn(5).setResizable(false);
            jTable10.getColumnModel().getColumn(6).setResizable(false);
            jTable10.getColumnModel().getColumn(7).setResizable(false);
        }

        javax.swing.GroupLayout searchissueLayout = new javax.swing.GroupLayout(searchissue);
        searchissue.setLayout(searchissueLayout);
        searchissueLayout.setHorizontalGroup(
            searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(searchissueLayout.createSequentialGroup()
                .addGroup(searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchissueLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel156, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jLabel157, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField21)
                            .addComponent(jDateChooser5, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel166, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel159, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jDateChooser6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 147, Short.MAX_VALUE))
                    .addGroup(searchissueLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane10)))
                .addGap(23, 23, 23))
        );
        searchissueLayout.setVerticalGroup(
            searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchissueLayout.createSequentialGroup()
                .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel157, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel159, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchissueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel156, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel166, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        searchissueLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel159, jTextField25});

        searchissueLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDateChooser5, jDateChooser6, jLabel156, jLabel166});

        searchissueLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel157, jTextField21});

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1123, Short.MAX_VALUE)
            .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel42Layout.createSequentialGroup()
                    .addComponent(searchissue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
            .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                    .addComponent(searchissue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout issueLayout = new javax.swing.GroupLayout(issue);
        issue.setLayout(issueLayout);
        issueLayout.setHorizontalGroup(
            issueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(issueLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        issueLayout.setVerticalGroup(
            issueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issueLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        retu.setBackground(new java.awt.Color(251, 230, 230));
        retu.setPreferredSize(new java.awt.Dimension(1140, 642));

        jPanel47.setBackground(new java.awt.Color(227, 231, 234));

        jLabel171.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel171.setForeground(new java.awt.Color(102, 102, 102));
        jLabel171.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel171.setText("Design & Developed by ");

        jLabel172.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel172.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compnylogo.png"))); // NOI18N

        jLabel173.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel173.setForeground(new java.awt.Color(102, 102, 102));
        jLabel173.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel173.setText("Syntex Solutions ");

        jLabel174.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel174.setForeground(new java.awt.Color(102, 102, 102));
        jLabel174.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel174.setText("(since - 2022)");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(jLabel171)
                .addGap(0, 0, 0)
                .addComponent(jLabel172)
                .addGap(0, 0, 0)
                .addComponent(jLabel173)
                .addGap(0, 0, 0)
                .addComponent(jLabel174)
                .addContainerGap(494, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel172, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel171, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel173, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel174, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel48.setBackground(new java.awt.Color(251, 230, 230));

        retucmpnybtn.setBackground(new java.awt.Color(0, 51, 153));
        retucmpnybtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        retucmpnybtn.setForeground(new java.awt.Color(255, 255, 255));
        retucmpnybtn.setText("Return To Company");
        retucmpnybtn.setBorderPainted(false);
        retucmpnybtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        retucmpnybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retucmpnybtnActionPerformed(evt);
            }
        });

        retustockbtn.setBackground(new java.awt.Color(0, 0, 153));
        retustockbtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        retustockbtn.setForeground(new java.awt.Color(255, 255, 255));
        retustockbtn.setText("Return From Shop");
        retustockbtn.setBorderPainted(false);
        retustockbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        retustockbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retustockbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(retustockbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172)
                .addComponent(retucmpnybtn, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel48Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {retucmpnybtn, retustockbtn});

        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(retustockbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(retucmpnybtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel48Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {retucmpnybtn, retustockbtn});

        jPanel49.setBackground(new java.awt.Color(255, 255, 255));

        retustock.setBackground(new java.awt.Color(251, 230, 230));

        jPanel50.setBackground(new java.awt.Color(43, 90, 137));

        jLabel175.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel175.setForeground(new java.awt.Color(255, 255, 255));
        jLabel175.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel175.setText("Return Product From Shop");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel175, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel175, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jButton25.setBackground(new java.awt.Color(0, 51, 255));
        jButton25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton25.setForeground(new java.awt.Color(255, 255, 255));
        jButton25.setText("Add to The Table");
        jButton25.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton25.setBorderPainted(false);
        jButton25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ref", "vehicle", "product", "shop", "qty", "price", "total", "issue date", "return date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(jTable8);
        if (jTable8.getColumnModel().getColumnCount() > 0) {
            jTable8.getColumnModel().getColumn(0).setResizable(false);
            jTable8.getColumnModel().getColumn(1).setResizable(false);
            jTable8.getColumnModel().getColumn(1).setHeaderValue("vehicle");
            jTable8.getColumnModel().getColumn(2).setResizable(false);
            jTable8.getColumnModel().getColumn(3).setResizable(false);
            jTable8.getColumnModel().getColumn(4).setResizable(false);
            jTable8.getColumnModel().getColumn(5).setResizable(false);
            jTable8.getColumnModel().getColumn(6).setResizable(false);
            jTable8.getColumnModel().getColumn(7).setResizable(false);
            jTable8.getColumnModel().getColumn(8).setResizable(false);
        }

        jButton27.setBackground(new java.awt.Color(0, 153, 0));
        jButton27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton27.setForeground(new java.awt.Color(255, 255, 255));
        jButton27.setText("Save Return Products");
        jButton27.setBorderPainted(false);
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jLabel177.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel177.setText("Select Product :-");

        jLabel178.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel178.setText("Return Quantity :-");

        jTextField20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField20ActionPerformed(evt);
            }
        });

        jLabel179.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel179.setText("Select Ref :-");

        jLabel181.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel181.setText("Select Issue Date :-");

        jLabel202.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel202.setText("Selling price :-");

        jTextField22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField22ActionPerformed(evt);
            }
        });

        jLabel183.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel183.setText("Select Return Date :-");

        jLabel180.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel180.setText("Shop Name  :-");

        jTextField24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField24ActionPerformed(evt);
            }
        });

        jLabel207.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel207.setText("Total :-");

        jTextField33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField33ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout retustockLayout = new javax.swing.GroupLayout(retustock);
        retustock.setLayout(retustockLayout);
        retustockLayout.setHorizontalGroup(
            retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(retustockLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(retustockLayout.createSequentialGroup()
                        .addComponent(jButton27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))
                    .addGroup(retustockLayout.createSequentialGroup()
                        .addComponent(jScrollPane8)
                        .addGap(20, 20, 20))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, retustockLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, retustockLayout.createSequentialGroup()
                        .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(retustockLayout.createSequentialGroup()
                                .addComponent(jLabel202, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(retustockLayout.createSequentialGroup()
                                .addComponent(jLabel181, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(5, 5, 5)
                                .addComponent(jDateChooser9, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(retustockLayout.createSequentialGroup()
                                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel180, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(jLabel179, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(4, 4, 4)
                                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox20, 0, 256, Short.MAX_VALUE)
                                    .addComponent(jTextField24))))
                        .addGap(120, 120, 120)
                        .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, retustockLayout.createSequentialGroup()
                                .addComponent(jLabel178, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(retustockLayout.createSequentialGroup()
                                .addComponent(jLabel177, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(retustockLayout.createSequentialGroup()
                                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel183, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel207, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField33)
                                    .addComponent(jDateChooser10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, retustockLayout.createSequentialGroup()
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(375, 375, 375))))
        );
        retustockLayout.setVerticalGroup(
            retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(retustockLayout.createSequentialGroup()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel179, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel177, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel178, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField24, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel180, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel207, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel202, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(retustockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel183, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel181, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        retustockLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox19, jComboBox20, jLabel177, jLabel178, jLabel179, jLabel180, jLabel202, jLabel207, jTextField20, jTextField22, jTextField24, jTextField33});

        retustockLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDateChooser10, jDateChooser9, jLabel181, jLabel183});

        retucmpny.setBackground(new java.awt.Color(251, 230, 230));

        jPanel54.setBackground(new java.awt.Color(43, 90, 137));

        jLabel201.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel201.setForeground(new java.awt.Color(255, 255, 255));
        jLabel201.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel201.setText("Products Return to Company");

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel201, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel201, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jTable13.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "vehicle number", "product name", "Quantity", "Unit Price", "Total", "issue date", "return date", "Ref Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane13.setViewportView(jTable13);
        if (jTable13.getColumnModel().getColumnCount() > 0) {
            jTable13.getColumnModel().getColumn(0).setResizable(false);
            jTable13.getColumnModel().getColumn(1).setResizable(false);
            jTable13.getColumnModel().getColumn(2).setResizable(false);
            jTable13.getColumnModel().getColumn(3).setResizable(false);
            jTable13.getColumnModel().getColumn(4).setResizable(false);
            jTable13.getColumnModel().getColumn(5).setResizable(false);
            jTable13.getColumnModel().getColumn(6).setResizable(false);
            jTable13.getColumnModel().getColumn(7).setResizable(false);
            jTable13.getColumnModel().getColumn(8).setResizable(false);
        }

        jLabel182.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel182.setText("Product Name :-");

        gpname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        gpname.setForeground(new java.awt.Color(102, 102, 102));
        gpname.setText("None");

        jLabel184.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel184.setText("Vehicle number :-");

        gcname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        gcname.setForeground(new java.awt.Color(102, 102, 102));
        gcname.setText("None");

        jButton26.setBackground(new java.awt.Color(0, 0, 153));
        jButton26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton26.setForeground(new java.awt.Color(255, 255, 255));
        jButton26.setText("Select Product to Reutrn From Shop");
        jButton26.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton26.setBorderPainted(false);
        jButton26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jLabel186.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel186.setText("Return Quantity :-");

        jLabel187.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel187.setText("Unit Price :-");

        guprice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        guprice.setForeground(new java.awt.Color(102, 102, 102));
        guprice.setText("0.00");

        jLabel189.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel189.setText("issue date :_");

        groute.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        groute.setForeground(new java.awt.Color(102, 102, 102));
        groute.setText("YYYY-MM-DD");

        gqty.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        gqty.setForeground(new java.awt.Color(102, 102, 102));
        gqty.setText("0");

        jButton28.setBackground(new java.awt.Color(0, 51, 255));
        jButton28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton28.setForeground(new java.awt.Color(255, 255, 255));
        jButton28.setText("Add To The Table ");
        jButton28.setBorderPainted(false);
        jButton28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton29.setBackground(new java.awt.Color(0, 153, 51));
        jButton29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton29.setForeground(new java.awt.Color(255, 255, 255));
        jButton29.setText("Return To Company");
        jButton29.setBorderPainted(false);
        jButton29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jLabel203.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel203.setText("Reeturn ID :-");

        gid.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        gid.setForeground(new java.awt.Color(102, 102, 102));
        gid.setText("None");

        jLabel206.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel206.setText("Total Price :-");

        gtotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        gtotal.setForeground(new java.awt.Color(102, 102, 102));
        gtotal.setText("0.00");

        jLabel215.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel215.setText("Ref Name :-");

        gref.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        gref.setForeground(new java.awt.Color(102, 102, 102));
        gref.setText("None");

        jLabel232.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel232.setText("Return Date :-");

        gdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        gdate.setForeground(new java.awt.Color(102, 102, 102));
        gdate.setText("YYYY-MM-DD");

        javax.swing.GroupLayout retucmpnyLayout = new javax.swing.GroupLayout(retucmpny);
        retucmpny.setLayout(retucmpnyLayout);
        retucmpnyLayout.setHorizontalGroup(
            retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(retucmpnyLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, retucmpnyLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel182, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel184, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel203, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gcname, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(gpname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gid, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(retucmpnyLayout.createSequentialGroup()
                                .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel186, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel187, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(guprice, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(gqty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(retucmpnyLayout.createSequentialGroup()
                                .addComponent(jLabel206, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(35, 35, 35)
                        .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel189, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel215, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel232, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gref, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(groute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(23, 23, 23))
        );
        retucmpnyLayout.setVerticalGroup(
            retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(retucmpnyLayout.createSequentialGroup()
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gid, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel203, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel232)
                    .addComponent(gdate)
                    .addComponent(gqty)
                    .addComponent(jLabel186))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(guprice)
                    .addComponent(jLabel187)
                    .addComponent(gpname, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel182, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gref)
                    .addComponent(jLabel215))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(retucmpnyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel184)
                        .addComponent(gcname))
                    .addComponent(gtotal)
                    .addComponent(jLabel206)
                    .addComponent(groute, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel189, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        retucmpnyLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {gcname, gdate, gid, gpname, gqty, gref, groute, gtotal, guprice, jLabel182, jLabel184, jLabel186, jLabel187, jLabel189, jLabel203, jLabel206, jLabel215, jLabel232});

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel49Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(retustock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(retucmpny, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(retustock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(retucmpny, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout retuLayout = new javax.swing.GroupLayout(retu);
        retu.setLayout(retuLayout);
        retuLayout.setHorizontalGroup(
            retuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel48, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(retuLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        retuLayout.setVerticalGroup(
            retuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, retuLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        daily.setBackground(new java.awt.Color(252, 252, 240));
        daily.setPreferredSize(new java.awt.Dimension(1140, 642));

        jPanel53.setBackground(new java.awt.Color(227, 231, 234));

        jLabel190.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel190.setForeground(new java.awt.Color(102, 102, 102));
        jLabel190.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel190.setText("Design & Developed by ");

        jLabel193.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel193.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compnylogo.png"))); // NOI18N

        jLabel194.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel194.setForeground(new java.awt.Color(102, 102, 102));
        jLabel194.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel194.setText("Syntex Solutions ");

        jLabel195.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel195.setForeground(new java.awt.Color(102, 102, 102));
        jLabel195.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel195.setText("(since - 2022)");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addComponent(jLabel190)
                .addGap(0, 0, 0)
                .addComponent(jLabel193)
                .addGap(0, 0, 0)
                .addComponent(jLabel194)
                .addGap(0, 0, 0)
                .addComponent(jLabel195)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel193, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel190, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel194, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel195, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel55.setBackground(new java.awt.Color(252, 252, 240));

        printdailybtn.setBackground(new java.awt.Color(0, 51, 153));
        printdailybtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        printdailybtn.setForeground(new java.awt.Color(255, 255, 255));
        printdailybtn.setText("Add Daily Collection");
        printdailybtn.setBorderPainted(false);
        printdailybtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printdailybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printdailybtnActionPerformed(evt);
            }
        });

        searchdailybtn.setBackground(new java.awt.Color(0, 51, 153));
        searchdailybtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        searchdailybtn.setForeground(new java.awt.Color(255, 255, 255));
        searchdailybtn.setText("Search & View Daily Bill");
        searchdailybtn.setBorderPainted(false);
        searchdailybtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchdailybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchdailybtnActionPerformed(evt);
            }
        });

        adddailybtn.setBackground(new java.awt.Color(0, 0, 153));
        adddailybtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        adddailybtn.setForeground(new java.awt.Color(255, 255, 255));
        adddailybtn.setText("Add Daily Bill");
        adddailybtn.setBorderPainted(false);
        adddailybtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adddailybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adddailybtnActionPerformed(evt);
            }
        });

        searchdailysumbtn.setBackground(new java.awt.Color(0, 51, 153));
        searchdailysumbtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        searchdailysumbtn.setForeground(new java.awt.Color(255, 255, 255));
        searchdailysumbtn.setText("Search Daily Collection");
        searchdailysumbtn.setBorderPainted(false);
        searchdailysumbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchdailysumbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchdailysumbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(adddailybtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(searchdailybtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(printdailybtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(searchdailysumbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(46, 46, 46))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchdailybtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adddailybtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(printdailybtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchdailysumbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel56.setBackground(new java.awt.Color(252, 252, 240));

        adddaily.setBackground(new java.awt.Color(252, 252, 240));

        jPanel57.setBackground(new java.awt.Color(43, 90, 137));

        jLabel196.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel196.setForeground(new java.awt.Color(255, 255, 255));
        jLabel196.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel196.setText("Add Today Daily Bill | ");

        issuedte1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        issuedte1.setForeground(new java.awt.Color(255, 255, 255));
        issuedte1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        issuedte1.setText("2022/08/24");

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(jLabel196, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(issuedte1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel196)
                .addComponent(issuedte1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel197.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel197.setText("Product Name :-");

        jLabel199.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel199.setText("Selling Quantity :-");

        jButton30.setBackground(new java.awt.Color(0, 51, 255));
        jButton30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton30.setForeground(new java.awt.Color(255, 255, 255));
        jButton30.setText("Add to The Table");
        jButton30.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton30.setBorderPainted(false);
        jButton30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ref name", "bill date", "route", "Bill no", "shop name", "product name", "selling qty", "selling price", "total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable9MouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(jTable9);
        if (jTable9.getColumnModel().getColumnCount() > 0) {
            jTable9.getColumnModel().getColumn(0).setResizable(false);
            jTable9.getColumnModel().getColumn(1).setResizable(false);
            jTable9.getColumnModel().getColumn(2).setResizable(false);
            jTable9.getColumnModel().getColumn(3).setResizable(false);
            jTable9.getColumnModel().getColumn(4).setResizable(false);
            jTable9.getColumnModel().getColumn(5).setResizable(false);
            jTable9.getColumnModel().getColumn(6).setResizable(false);
            jTable9.getColumnModel().getColumn(7).setResizable(false);
            jTable9.getColumnModel().getColumn(8).setResizable(false);
        }

        jButton32.setBackground(new java.awt.Color(0, 153, 0));
        jButton32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton32.setForeground(new java.awt.Color(255, 255, 255));
        jButton32.setText("Save Today Collection");
        jButton32.setBorderPainted(false);
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jLabel204.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel204.setText("selling price :-");

        billqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billqtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billqtyKeyTyped(evt);
            }
        });

        jLabel235.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel235.setText("Sub Total :-");

        jPanel24.setBackground(new java.awt.Color(252, 252, 240));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)), "Main Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel208.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel208.setText("Ref Name :-");

        jLabel230.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel230.setText(" Bill Date :-");

        jLabel214.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel214.setText("Route :-");

        jLabel220.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel220.setText("Bill No :-");

        billno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billnoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billnoKeyTyped(evt);
            }
        });

        jLabel229.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel229.setText("Shop Name :-");

        billshop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billshopKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billshopKeyTyped(evt);
            }
        });

        billdate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                billdatePropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel220, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel208, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(billref, 0, 150, Short.MAX_VALUE)
                    .addComponent(billno))
                .addGap(72, 72, 72)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel229, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                    .addComponent(jLabel230, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(billdate, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel214, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(billroute, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(billshop))
                .addGap(64, 64, 64))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel208)
                    .addComponent(billref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel230)
                    .addComponent(jLabel214)
                    .addComponent(billroute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billdate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(billno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel220, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel229, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billshop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel24Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {billref, billroute, jLabel208, jLabel214, jLabel230});

        jPanel24Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {billno, billshop, jLabel220, jLabel229});

        billuprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                billupriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billupriceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billupriceKeyTyped(evt);
            }
        });

        jLabel237.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel237.setText("Total :-");

        billtotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billtotalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billtotalKeyTyped(evt);
            }
        });

        billsubtotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billsubtotalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billsubtotalKeyTyped(evt);
            }
        });

        jLabel236.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel236.setText("Cash :-");

        billcash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billcashKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billcashKeyTyped(evt);
            }
        });

        jLabel238.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel238.setText("Credit :-");

        billcredit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billcreditKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billcreditKeyTyped(evt);
            }
        });

        jPanel30.setBackground(new java.awt.Color(252, 252, 240));
        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)), "Check Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel242.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel242.setText("Check date :-");

        billcnumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billcnumberKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billcnumberKeyTyped(evt);
            }
        });

        jLabel245.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel245.setText("Bank :-");

        jLabel244.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel244.setText(" Number :-");

        billcbank.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billcbankKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billcbankKeyTyped(evt);
            }
        });

        billcdate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                billcdatePropertyChange(evt);
            }
        });

        billcamount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billcamountKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                billcamountKeyTyped(evt);
            }
        });

        jLabel246.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel246.setText("Amount :-");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel242, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billcdate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel244)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billcnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel246)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billcamount, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel245, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(billcbank, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel242, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billcnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel246, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billcamount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel245, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billcdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel244, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billcbank))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout adddailyLayout = new javax.swing.GroupLayout(adddaily);
        adddaily.setLayout(adddailyLayout);
        adddailyLayout.setHorizontalGroup(
            adddailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(adddailyLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(adddailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(adddailyLayout.createSequentialGroup()
                        .addGroup(adddailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane9)
                            .addComponent(jButton30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, adddailyLayout.createSequentialGroup()
                                .addComponent(jLabel197, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(billproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel199, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(billqty, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel204, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(billuprice, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(billtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 120, Short.MAX_VALUE)))
                        .addGap(16, 16, 16))
                    .addGroup(adddailyLayout.createSequentialGroup()
                        .addComponent(jLabel235, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(billsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel236, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(billcash, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel238, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(billcredit, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adddailyLayout.createSequentialGroup()
                .addGap(309, 309, 309)
                .addComponent(jButton32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(356, 356, 356))
        );
        adddailyLayout.setVerticalGroup(
            adddailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adddailyLayout.createSequentialGroup()
                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(adddailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(billtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billuprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel204)
                    .addComponent(billqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel199, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billproduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel197))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(adddailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel235)
                    .addComponent(billsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel236)
                    .addGroup(adddailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(adddailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel238)
                            .addComponent(billcredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(billcash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        adddailyLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {billproduct, billqty, billtotal, billuprice, jLabel197, jLabel199, jLabel204, jLabel237});

        adddailyLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {billsubtotal, jLabel235});

        adddailysum.setBackground(new java.awt.Color(252, 252, 240));

        jPanel58.setBackground(new java.awt.Color(43, 90, 137));

        jLabel216.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel216.setForeground(new java.awt.Color(255, 255, 255));
        jLabel216.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel216.setText("Add a Daily Collectionn Summary");

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel216, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel216, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jButton36.setBackground(new java.awt.Color(0, 153, 0));
        jButton36.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton36.setForeground(new java.awt.Color(255, 255, 255));
        jButton36.setText("save daily collection");
        jButton36.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton36.setBorderPainted(false);
        jButton36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton36.setPreferredSize(new java.awt.Dimension(41, 30));
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jLabel217.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel217.setText("Select Ref :-");

        jLabel218.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel218.setText("Date :-");

        dailydate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dailydatePropertyChange(evt);
            }
        });

        jLabel219.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel219.setText("Cash Amount :-");

        jLabel221.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel221.setText("Cheque Amount :-");

        jLabel222.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel222.setText("Credit Amount :-");

        jLabel239.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel239.setText("Day Sale :-");

        jLabel240.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel240.setText("C.Collections :-");

        jLabel241.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel241.setText("Total Amount :-");

        jLabel247.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel247.setText("Bill Paid :-");

        jLabel248.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel248.setText("Driver Salary :-");

        jLabel250.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel250.setText("Cash In Hand :-");

        javax.swing.GroupLayout adddailysumLayout = new javax.swing.GroupLayout(adddailysum);
        adddailysum.setLayout(adddailysumLayout);
        adddailysumLayout.setHorizontalGroup(
            adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(adddailysumLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(adddailysumLayout.createSequentialGroup()
                        .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(adddailysumLayout.createSequentialGroup()
                                .addComponent(jLabel250, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dailyhand))
                            .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(adddailysumLayout.createSequentialGroup()
                                    .addComponent(jLabel247, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(dailypaid, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(adddailysumLayout.createSequentialGroup()
                                    .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel222, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel217, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel219, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)))
                                        .addComponent(jLabel240, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(dailycc)
                                        .addComponent(dailyref, javax.swing.GroupLayout.Alignment.LEADING, 0, 212, Short.MAX_VALUE)
                                        .addComponent(dailycash, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dailycredit, javax.swing.GroupLayout.Alignment.LEADING)))))
                        .addGap(169, 169, 169)
                        .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(adddailysumLayout.createSequentialGroup()
                                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel218, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel221))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dailydate, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dailycheck, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(adddailysumLayout.createSequentialGroup()
                                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel248, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel239, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                    .addComponent(jLabel241, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dailyamount)
                                    .addComponent(dailysale)
                                    .addComponent(dailysalary))))))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        adddailysumLayout.setVerticalGroup(
            adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adddailysumLayout.createSequentialGroup()
                .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel218, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dailydate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dailyref, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel217, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dailycheck, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel221, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dailycash)
                    .addComponent(jLabel219, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dailysale, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel239, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dailycredit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel222, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dailycc, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel241, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dailyamount, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel240, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dailypaid, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel248, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dailysalary, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel247, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(adddailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel250, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dailyhand, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        adddailysumLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dailyhand, jLabel250});

        adddailysumLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dailycredit, dailysale, jLabel222, jLabel239});

        adddailysumLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dailycash, dailycheck, jLabel219, jLabel221});

        searchdaily.setBackground(new java.awt.Color(252, 252, 240));

        jPanel59.setBackground(new java.awt.Color(43, 90, 137));

        jLabel223.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel223.setForeground(new java.awt.Color(255, 255, 255));
        jLabel223.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel223.setText("Advanced Search of Daily bill");

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel223, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel223, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jLabel224.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel224.setText("Select ref :-");

        jComboBox26.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox26ItemStateChanged(evt);
            }
        });

        jLabel225.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel225.setText("Date From :-");

        jDateChooser12.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser12PropertyChange(evt);
            }
        });

        jTextField41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField41ActionPerformed(evt);
            }
        });
        jTextField41.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField41KeyReleased(evt);
            }
        });

        jLabel226.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel226.setText("shop name :-");

        jLabel227.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel227.setText("Product Name :-");

        jTextField42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField42ActionPerformed(evt);
            }
        });
        jTextField42.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField42KeyReleased(evt);
            }
        });

        jDateChooser13.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser13PropertyChange(evt);
            }
        });

        jLabel228.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel228.setText("Date To :-");

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "date", "ref name", "route", "bill_no", "shop", "product", "qty", "price", "amount", "sub total", "cash", "check", "credit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane14.setViewportView(jTable14);
        if (jTable14.getColumnModel().getColumnCount() > 0) {
            jTable14.getColumnModel().getColumn(0).setResizable(false);
            jTable14.getColumnModel().getColumn(1).setResizable(false);
            jTable14.getColumnModel().getColumn(2).setResizable(false);
            jTable14.getColumnModel().getColumn(3).setResizable(false);
            jTable14.getColumnModel().getColumn(4).setResizable(false);
            jTable14.getColumnModel().getColumn(5).setResizable(false);
            jTable14.getColumnModel().getColumn(6).setResizable(false);
            jTable14.getColumnModel().getColumn(7).setResizable(false);
            jTable14.getColumnModel().getColumn(8).setResizable(false);
            jTable14.getColumnModel().getColumn(9).setResizable(false);
            jTable14.getColumnModel().getColumn(10).setResizable(false);
            jTable14.getColumnModel().getColumn(11).setResizable(false);
            jTable14.getColumnModel().getColumn(11).setHeaderValue("check");
            jTable14.getColumnModel().getColumn(12).setHeaderValue("credit");
        }

        jLabel234.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel234.setText("Bill Number :-");

        jTextField43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField43ActionPerformed(evt);
            }
        });
        jTextField43.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField43KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout searchdailyLayout = new javax.swing.GroupLayout(searchdaily);
        searchdaily.setLayout(searchdailyLayout);
        searchdailyLayout.setHorizontalGroup(
            searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(searchdailyLayout.createSequentialGroup()
                .addGroup(searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchdailyLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(searchdailyLayout.createSequentialGroup()
                                .addComponent(jLabel224, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox26, 0, 228, Short.MAX_VALUE))
                            .addGroup(searchdailyLayout.createSequentialGroup()
                                .addGroup(searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel225, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(jLabel226, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField41)
                                    .addComponent(jDateChooser12, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))))
                        .addGap(115, 115, 115)
                        .addGroup(searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel234, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel228, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(jLabel227, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jDateChooser13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                            .addComponent(jTextField42, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField43)))
                    .addGroup(searchdailyLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane14)))
                .addGap(23, 23, 23))
        );
        searchdailyLayout.setVerticalGroup(
            searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchdailyLayout.createSequentialGroup()
                .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel224, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel227, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel225, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel228, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser13, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(searchdailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel226, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel234, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );

        searchdailysum.setBackground(new java.awt.Color(252, 252, 240));

        jPanel63.setBackground(new java.awt.Color(43, 90, 137));

        jLabel251.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel251.setForeground(new java.awt.Color(255, 255, 255));
        jLabel251.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel251.setText("Advanced Search of Daily collection Summury");

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel251, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(132, 132, 132))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel251, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jLabel252.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel252.setText("Select ref :-");

        jComboBox27.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox27ItemStateChanged(evt);
            }
        });

        jLabel253.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel253.setText("Date  :-");

        jDateChooser14.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser14PropertyChange(evt);
            }
        });

        jTable15.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ref name", "date", "cash", "check", "credit", "day sale", "c.collection", "total", "bill paid", "driver salary", "cash in hand"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane15.setViewportView(jTable15);
        if (jTable15.getColumnModel().getColumnCount() > 0) {
            jTable15.getColumnModel().getColumn(0).setResizable(false);
            jTable15.getColumnModel().getColumn(1).setResizable(false);
            jTable15.getColumnModel().getColumn(2).setResizable(false);
            jTable15.getColumnModel().getColumn(3).setResizable(false);
            jTable15.getColumnModel().getColumn(4).setResizable(false);
            jTable15.getColumnModel().getColumn(5).setResizable(false);
            jTable15.getColumnModel().getColumn(6).setResizable(false);
            jTable15.getColumnModel().getColumn(7).setResizable(false);
            jTable15.getColumnModel().getColumn(8).setResizable(false);
            jTable15.getColumnModel().getColumn(9).setResizable(false);
            jTable15.getColumnModel().getColumn(10).setResizable(false);
        }

        javax.swing.GroupLayout searchdailysumLayout = new javax.swing.GroupLayout(searchdailysum);
        searchdailysum.setLayout(searchdailysumLayout);
        searchdailysumLayout.setHorizontalGroup(
            searchdailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(searchdailysumLayout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel252, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox27, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140)
                .addComponent(jLabel253, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser14, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(236, Short.MAX_VALUE))
            .addGroup(searchdailysumLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane15)
                .addGap(19, 19, 19))
        );
        searchdailysumLayout.setVerticalGroup(
            searchdailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchdailysumLayout.createSequentialGroup()
                .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(searchdailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(searchdailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jComboBox27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel252, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(searchdailysumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel253, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooser14, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adddaily, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(adddailysum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(searchdaily, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(searchdailysum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adddaily, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(adddailysum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel56Layout.createSequentialGroup()
                    .addComponent(searchdaily, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel56Layout.createSequentialGroup()
                    .addComponent(searchdailysum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout dailyLayout = new javax.swing.GroupLayout(daily);
        daily.setLayout(dailyLayout);
        dailyLayout.setHorizontalGroup(
            dailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(dailyLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dailyLayout.setVerticalGroup(
            dailyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dailyLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bank.setBackground(new java.awt.Color(241, 249, 241));

        jPanel65.setBackground(new java.awt.Color(0, 0, 153));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("View Bank Account Summury");

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel66.setBackground(new java.awt.Color(238, 234, 234));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bank.png"))); // NOI18N

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(297, 297, 297))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
        );

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel36.setText("Account Current Balance :-");

        jTextField4s.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jTextField4s.setForeground(new java.awt.Color(153, 153, 153));
        jTextField4s.setText("350,000");
        jTextField4s.setEnabled(false);
        jTextField4s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4sActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel43.setText("Rs");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(102, 102, 102));
        jLabel45.setText("Deposit Cash or Cheque :-");

        depo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jButton2.setBackground(new java.awt.Color(0, 153, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Deposit");
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(102, 102, 102));
        jLabel47.setText("Withdraw or Other payment :-");

        with.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jButton5.setBackground(new java.awt.Color(153, 51, 0));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Withdraw");
        jButton5.setBorderPainted(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bankLayout = new javax.swing.GroupLayout(bank);
        bank.setLayout(bankLayout);
        bankLayout.setHorizontalGroup(
            bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(bankLayout.createSequentialGroup()
                .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bankLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(bankLayout.createSequentialGroup()
                        .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bankLayout.createSequentialGroup()
                                .addGap(225, 225, 225)
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4s, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel43))
                            .addGroup(bankLayout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(bankLayout.createSequentialGroup()
                                        .addComponent(with, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bankLayout.createSequentialGroup()
                                        .addComponent(depo, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 130, Short.MAX_VALUE)))
                .addContainerGap())
        );
        bankLayout.setVerticalGroup(
            bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankLayout.createSequentialGroup()
                .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jTextField4s, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(depo)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(with)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        bankLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel36, jLabel43, jTextField4s});

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dash, javax.swing.GroupLayout.DEFAULT_SIZE, 1172, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(stock, javax.swing.GroupLayout.DEFAULT_SIZE, 1172, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(ref, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(issue, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(retu, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(daily, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(bank, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dash, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(stock, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(ref, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(issue, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(retu, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(daily, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(bank, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1172, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void dashhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashhMouseClicked

        // TODO add your handling code here:
        dash.setVisible(true);
        stock.setVisible(false);
        ref.setVisible(false);
        issue.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);
        bank.setVisible(false);

        dash();
        loadincome();

        dashh.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));
        vehii.setBackground(new Color(57, 126, 189));
        ccc.setBackground(new Color(57, 126, 189));

        label.setText("DashBoard");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/d1.png")));


    }//GEN-LAST:event_dashhMouseClicked

    private void stockkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockkMouseClicked
        // TODO add your handling code here:

        stock.setVisible(true);
        dash.setVisible(false);
        ref.setVisible(false);
        issue.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);
        bank.setVisible(false);

        loadcompany();
        loadproduct();
        loadpayment_type();
        loadstock();
        loadref();
        loadrefup();
        loadrefearch();
        loadroute();
        loadvehicle();
        loadissue();

        stockk.setBackground(new Color(1, 1, 59));
        dashh.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));
        vehii.setBackground(new Color(57, 126, 189));
        ccc.setBackground(new Color(57, 126, 189));

        addstock.setVisible(true);
        searchstock.setVisible(false);
        stockreports.setVisible(false);
        stockhistory.setVisible(false);

        addstockbtn.setBackground(new Color(206, 245, 245));
        searchstockbtn.setBackground(new Color(0, 0, 153));
        reportstockbtn.setBackground(new Color(0, 0, 153));
        stockhistorybtn.setBackground(new Color(0, 0, 153));

        addstockbtn.setForeground(Color.BLACK);
        searchstockbtn.setForeground(Color.WHITE);
        reportstockbtn.setForeground(Color.WHITE);
        stockhistorybtn.setForeground(Color.WHITE);

        label.setText("Stock Managment");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stock.png")));

    }//GEN-LAST:event_stockkMouseClicked

    private void reffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reffMouseClicked
        // TODO add your handling code here:

        addref.setVisible(true);
        updateref.setVisible(false);
        searchref.setVisible(false);

        addrefbtn.setBackground(new Color(206, 245, 245));
        updaterefbtn.setBackground(new Color(0, 0, 153));
        searchrefbtn.setBackground(new Color(0, 0, 153));

        addrefbtn.setForeground(Color.BLACK);
        updaterefbtn.setForeground(Color.WHITE);
        searchrefbtn.setForeground(Color.WHITE);

        ref.setVisible(true);
        dash.setVisible(false);
        stock.setVisible(false);
        issue.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);
        bank.setVisible(false);

        reff.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));
        vehii.setBackground(new Color(57, 126, 189));
        ccc.setBackground(new Color(57, 126, 189));

        label.setText("Reference Agent");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ref1.png")));

    }//GEN-LAST:event_reffMouseClicked

    private void issueeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_issueeMouseClicked
        // TODO add your handling code here:

        issue.setVisible(true);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);
        bank.setVisible(false);

        searchissue.setVisible(true);

        searchissuebtn.setBackground(new Color(206, 245, 245));
        tdyissuebtn.setBackground(new Color(0, 0, 153));

        searchissuebtn.setForeground(Color.BLACK);
        tdyissuebtn.setForeground(Color.WHITE);

        issuee.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));
        vehii.setBackground(new Color(57, 126, 189));
        ccc.setBackground(new Color(57, 126, 189));

        label.setText("Issue Products");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/issu.png")));


    }//GEN-LAST:event_issueeMouseClicked

    private void retuuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retuuMouseClicked
        // TODO add your handling code here:

        retu.setVisible(true);
        issue.setVisible(false);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        daily.setVisible(false);
        bank.setVisible(false);

        retustock.setVisible(true);
        retucmpny.setVisible(false);

        retustockbtn.setBackground(new Color(206, 245, 245));
        retucmpnybtn.setBackground(new Color(0, 0, 153));

        retustockbtn.setForeground(Color.BLACK);
        retucmpnybtn.setForeground(Color.WHITE);

        retuu.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));
        vehii.setBackground(new Color(57, 126, 189));
        ccc.setBackground(new Color(57, 126, 189));

        label.setText("Return Products");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return.png")));


    }//GEN-LAST:event_retuuMouseClicked

    private void dailyyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dailyyMouseClicked
        // TODO add your handling code here:
        daily.setVisible(true);
        issue.setVisible(false);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        retu.setVisible(false);
        bank.setVisible(false);

        adddaily.setVisible(true);
        searchdaily.setVisible(false);
        adddailysum.setVisible(false);
        searchdailysum.setVisible(false);

        adddailybtn.setBackground(new Color(206, 245, 245));
        searchdailybtn.setBackground(new Color(0, 0, 153));
        printdailybtn.setBackground(new Color(0, 0, 153));
        searchdailysumbtn.setBackground(new Color(0, 0, 153));

        adddailybtn.setForeground(Color.BLACK);
        searchdailybtn.setForeground(Color.WHITE);
        printdailybtn.setForeground(Color.WHITE);
        searchdailysumbtn.setForeground(Color.WHITE);

        dailyy.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));
        vehii.setBackground(new Color(57, 126, 189));
        ccc.setBackground(new Color(57, 126, 189));

        label.setText("Daily Collection");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/daily.png")));

    }//GEN-LAST:event_dailyyMouseClicked

    private void incomeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_incomeeMouseClicked
        // TODO add your handling code here:

        loadbalance();

        issue.setVisible(false);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);
        bank.setVisible(true);

        incomee.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        vehii.setBackground(new Color(57, 126, 189));
        ccc.setBackground(new Color(57, 126, 189));

        label.setText("View Bank");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/money.png")));

    }//GEN-LAST:event_incomeeMouseClicked

    private void addstockbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addstockbtnActionPerformed
        // TODO add your handling code here:

        addstock.setVisible(true);
        searchstock.setVisible(false);
        stockreports.setVisible(false);
        stockhistory.setVisible(false);

        addstockbtn.setBackground(new Color(206, 245, 245));
        searchstockbtn.setBackground(new Color(0, 0, 153));
        reportstockbtn.setBackground(new Color(0, 0, 153));
        stockhistorybtn.setBackground(new Color(0, 0, 153));

        addstockbtn.setForeground(Color.BLACK);
        searchstockbtn.setForeground(Color.WHITE);
        reportstockbtn.setForeground(Color.WHITE);
        stockhistorybtn.setForeground(Color.WHITE);


    }//GEN-LAST:event_addstockbtnActionPerformed

    private void reportstockbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportstockbtnActionPerformed
        // TODO add your handling code here:

        stockreports.setVisible(true);
        addstock.setVisible(false);
        searchstock.setVisible(false);
        stockhistory.setVisible(false);

        loadstockprint();

        reportstockbtn.setBackground(new Color(206, 245, 245));
        searchstockbtn.setBackground(new Color(0, 0, 153));
        addstockbtn.setBackground(new Color(0, 0, 153));
        stockhistorybtn.setBackground(new Color(0, 0, 153));

        reportstockbtn.setForeground(Color.BLACK);
        searchstockbtn.setForeground(Color.WHITE);
        addstockbtn.setForeground(Color.WHITE);
        stockhistorybtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_reportstockbtnActionPerformed

    private void searchstockbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchstockbtnActionPerformed
        // TODO add your handling code here:

        loadstock();

        searchstock.setVisible(true);
        addstock.setVisible(false);
        stockreports.setVisible(false);
        stockhistory.setVisible(false);

        searchstockbtn.setBackground(new Color(206, 245, 245));
        addstockbtn.setBackground(new Color(0, 0, 153));
        reportstockbtn.setBackground(new Color(0, 0, 153));
        stockhistorybtn.setBackground(new Color(0, 0, 153));

        searchstockbtn.setForeground(Color.BLACK);
        addstockbtn.setForeground(Color.WHITE);
        reportstockbtn.setForeground(Color.WHITE);
        stockhistorybtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_searchstockbtnActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        String rref = jComboBox1.getSelectedItem().toString();
        Date deate = jDateChooser3.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ddate = sdf.format(deate);
        String invoice_no = jTextField3.getText();

        String subotal = jTextField29.getText();
        String dis = jTextField30.getText();
        String amount = jTextField31.getText();
        String net = jTextField5.getText();

        if (jTable1.getRowCount() < 0) {
            JOptionPane.showMessageDialog(this, "please add a product to Stock Table", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (subotal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a sub total", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (dis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a disscount ", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (amount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a disscount amount", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (net.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a NET Value", "warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                for (int i = 0; i < jTable1.getRowCount(); i++) {

                    String code = jTable1.getValueAt(i, 0).toString();
                    String pname = jTable1.getValueAt(i, 1).toString();
                    String pdis = jTable1.getValueAt(i, 2).toString();
                    String qty = jTable1.getValueAt(i, 3).toString();
                    String uprice = jTable1.getValueAt(i, 4).toString();
                    String total = jTable1.getValueAt(i, 5).toString();

                    ResultSet rsp = MySQL.search("SELECT * FROM `product` WHERE `pname`='" + pname + "'  ");
                    rsp.next();
                    String pid = rsp.getString("id");

                    ResultSet rsc = MySQL.search("SELECT * FROM `ref` WHERE `fname`='" + rref + "'");
                    rsc.next();
                    String rid = rsc.getString("id");

                    MySQL.iud("INSERT INTO `history`(`ref`,`date`,`invoice_no`,`code`,`product`,`discount`,`unit_price`,`qty`,`amount`,`total`,`main_di`,`di_amount`,`net_value`,`status`) "
                            + "VALUES('" + rid + "','" + ddate + "','" + invoice_no + "','" + code + "','" + pid + "','" + pdis + "','" + uprice + "','" + qty + "','" + total + "','" + subotal + "','" + dis + "','" + amount + "','" + net + "','1'); ");

                    ResultSet ts = MySQL.search("SELECT * FROM `stock` WHERE `product`='" + pid + "' AND `unit_price`='" + uprice + "' AND `ref`='" + rid + "' AND `status`='1' ");

                    if (ts.next()) {

                        String sid = ts.getString("id");

                        int oldqty = Integer.parseInt(ts.getString("qty"));
                        int aqty = Integer.parseInt(qty);

                        int newqty = oldqty + aqty;
                        String snewqty = String.valueOf(newqty);

                        MySQL.iud("UPDATE `stock` SET `qty`='" + snewqty + "' WHERE `id`='" + sid + "'; ");

                    } else {

                        MySQL.iud("INSERT INTO `stock`(`ref`,`date`,`invoice_no`,`code`,`product`,`discount`,`unit_price`,`qty`,`status`) "
                                + "VALUES('" + rid + "','" + ddate + "','" + invoice_no + "','" + code + "','" + pid + "','" + pdis + "','" + uprice + "','" + qty + "','1'); ");

                    }

                }

                ResultSet b = MySQL.search("SELECT * FROM `bank` ");
                b.next();
                String balance = b.getString("money");

                Double bb = Double.valueOf(balance);
                Double co = Double.valueOf(net);
                Double newb = bb - co;

                jTextField4s.setText(String.valueOf(newb));
                MySQL.iud("UPDATE `bank` SET `money`='" + String.valueOf(newb) + "' WHERE `id`='1' ");

                ResultSet i = MySQL.search("SELECT * FROM `income` WHERE `date` = '" + date + "'");
                if (i.next()) {

                    Double as = Double.valueOf(i.getString("spend"));
                    Double ns = as + co;
                    String newspe = String.valueOf(ns);

                    MySQL.iud("UPDATE `income` SET `spend`='" + newspe + "' WHERE `id`='1' ");
                } else {
                    MySQL.iud("UPDATE `income` SET `spend`='" + net + "',`date`='" + date + "' WHERE `id`='1' ");
                }

                JOptionPane.showMessageDialog(this, "Stock added Successfull", "Suucess", JOptionPane.INFORMATION_MESSAGE);

                //table
                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                dtm.setRowCount(0);

                jComboBox1.setSelectedItem("Select");
                jDateChooser3.setDate(null);
                jTextField3.setText("");

                jTextField5.setText("");
                jTextField31.setText("");
                jTextField30.setText("");
                jTextField29.setText("");

                loadstock();
                //table

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void searchrefbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchrefbtnActionPerformed
        // TODO add your handling code here:

        loadrefearch();

        searchref.setVisible(true);
        updateref.setVisible(false);
        addref.setVisible(false);

        searchrefbtn.setBackground(new Color(206, 245, 245));
        updaterefbtn.setBackground(new Color(0, 0, 153));
        addrefbtn.setBackground(new Color(0, 0, 153));

        searchrefbtn.setForeground(Color.BLACK);
        updaterefbtn.setForeground(Color.WHITE);
        addrefbtn.setForeground(Color.WHITE);


    }//GEN-LAST:event_searchrefbtnActionPerformed

    private void updaterefbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updaterefbtnActionPerformed
        // TODO add your handling code here:

        updateref.setVisible(true);
        addref.setVisible(false);
        searchref.setVisible(false);

        loadrefup();

        updaterefbtn.setBackground(new Color(206, 245, 245));
        addrefbtn.setBackground(new Color(0, 0, 153));
        searchrefbtn.setBackground(new Color(0, 0, 153));

        updaterefbtn.setForeground(Color.BLACK);
        addrefbtn.setForeground(Color.WHITE);
        searchrefbtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_updaterefbtnActionPerformed

    private void addrefbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addrefbtnActionPerformed
        // TODO add your handling code here:

        loadref();

        addref.setVisible(true);
        updateref.setVisible(false);
        searchref.setVisible(false);

        addrefbtn.setBackground(new Color(206, 245, 245));
        updaterefbtn.setBackground(new Color(0, 0, 153));
        searchrefbtn.setBackground(new Color(0, 0, 153));

        addrefbtn.setForeground(Color.BLACK);
        updaterefbtn.setForeground(Color.WHITE);
        searchrefbtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_addrefbtnActionPerformed

    private void rmobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmobileActionPerformed

    private void rlnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rlnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rlnameActionPerformed

    private void sfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sfnameActionPerformed

    private void smobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_smobileActionPerformed

    private void slnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_slnameActionPerformed

    private void snicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_snicActionPerformed

    private void rfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rfnameActionPerformed

    private void rnicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rnicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rnicActionPerformed

    private void uprfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uprfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uprfnameActionPerformed

    private void uprlnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uprlnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uprlnameActionPerformed

    private void uprnicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uprnicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uprnicActionPerformed

    private void uprmobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uprmobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uprmobileActionPerformed


    private void searchissuebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchissuebtnActionPerformed
        // TODO add your handling code here:

        loadissue();

        searchissue.setVisible(true);

        searchissuebtn.setBackground(new Color(206, 245, 245));
        tdyissuebtn.setBackground(new Color(0, 0, 153));

        searchissuebtn.setForeground(Color.BLACK);
        tdyissuebtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_searchissuebtnActionPerformed

    private void tdyissuebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tdyissuebtnActionPerformed
        // TODO add your handling code here:

        dsladdissue ai = new dsladdissue();
        ai.setVisible(true);


    }//GEN-LAST:event_tdyissuebtnActionPerformed

    private void jTextField25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25ActionPerformed

    private void retucmpnybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retucmpnybtnActionPerformed
        // TODO add your handling code here:

        retucmpny.setVisible(true);
        retustock.setVisible(false);

        retucmpnybtn.setBackground(new Color(206, 245, 245));
        retustockbtn.setBackground(new Color(0, 0, 153));

        retucmpnybtn.setForeground(Color.BLACK);
        retustockbtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_retucmpnybtnActionPerformed

    private void retustockbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retustockbtnActionPerformed
        // TODO add your handling code here:

        retustock.setVisible(true);
        retucmpny.setVisible(false);

        retustockbtn.setBackground(new Color(206, 245, 245));
        retucmpnybtn.setBackground(new Color(0, 0, 153));

        retustockbtn.setForeground(Color.BLACK);
        retucmpnybtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_retustockbtnActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:

        int r = jTable8.getRowCount();

        if (r <= 0) {
            JOptionPane.showMessageDialog(this, "Please add a product  to table", "warning", JOptionPane.WARNING_MESSAGE);
        } else {

            for (int i = 0; i < jTable8.getRowCount(); i++) {

                String ref = jTable8.getValueAt(i, 0).toString();
                String vehicle = jTable8.getValueAt(i, 1).toString();
                String product = jTable8.getValueAt(i, 2).toString();
                String shop = jTable8.getValueAt(i, 3).toString();
                String qty = jTable8.getValueAt(i, 4).toString();
                String price = jTable8.getValueAt(i, 5).toString();
                String total = jTable8.getValueAt(i, 6).toString();
                String idate = jTable8.getValueAt(i, 7).toString();
                String redate = jTable8.getValueAt(i, 8).toString();

                try {

                    ResultSet rs2 = MySQL.search("SELECT * FROM `product` WHERE `pname`='" + product + "'  ");
                    rs2.next();
                    String rpid = rs2.getString("id");

                    ResultSet rs3 = MySQL.search("SELECT * FROM `ref` WHERE `fname`='" + ref + "'  ");
                    rs3.next();
                    String refid = rs3.getString("id");

                    ResultSet rs4 = MySQL.search("SELECT * FROM `vehicle` WHERE `number`='" + vehicle + "'  ");
                    rs4.next();
                    String vid = rs4.getString("id");

                    MySQL.iud("INSERT INTO `return`(`ref`,`vehicle`,`shop`,`product_id`,`qty`,`price`,`total`,`idate`,`redate`,`status`) "
                            + "VALUES('" + refid + "','" + vid + "','" + shop + "','" + rpid + "','" + qty + "','" + price + "','" + total + "','" + idate + "','" + redate + "','1') ");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            JOptionPane.showMessageDialog(this, "Return product added stock", "success", JOptionPane.INFORMATION_MESSAGE);

            DefaultTableModel dtm = (DefaultTableModel) jTable8.getModel();
            dtm.setRowCount(0);

        }

    }//GEN-LAST:event_jButton27ActionPerformed

    private void jTextField20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField20ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:

        viewreturn vr = new viewreturn(this);
        vr.setVisible(true);

    }//GEN-LAST:event_jButton26ActionPerformed

    private void printdailybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printdailybtnActionPerformed
        // TODO add your handling code here:

        adddailysum.setVisible(true);
        searchdaily.setVisible(false);
        adddaily.setVisible(false);
        searchdailysum.setVisible(false);

        printdailybtn.setBackground(new Color(206, 245, 245));
        searchdailybtn.setBackground(new Color(0, 0, 153));
        adddailybtn.setBackground(new Color(0, 0, 153));
        searchdailysumbtn.setBackground(new Color(0, 0, 153));

        printdailybtn.setForeground(Color.BLACK);
        searchdailybtn.setForeground(Color.WHITE);
        adddailybtn.setForeground(Color.WHITE);
        searchdailysumbtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_printdailybtnActionPerformed

    private void searchdailybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchdailybtnActionPerformed
        // TODO add your handling code here:

        loaddaily();

        searchdaily.setVisible(true);
        adddaily.setVisible(false);
        adddailysum.setVisible(false);
        searchdailysum.setVisible(false);

        searchdailybtn.setBackground(new Color(206, 245, 245));
        adddailybtn.setBackground(new Color(0, 0, 153));
        printdailybtn.setBackground(new Color(0, 0, 153));
        searchdailysumbtn.setBackground(new Color(0, 0, 153));

        searchdailybtn.setForeground(Color.BLACK);
        adddailybtn.setForeground(Color.WHITE);
        printdailybtn.setForeground(Color.WHITE);
        searchdailysumbtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_searchdailybtnActionPerformed

    private void adddailybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adddailybtnActionPerformed
        // TODO add your handling code here:

        adddaily.setVisible(true);
        searchdaily.setVisible(false);
        adddailysum.setVisible(false);
        searchdailysum.setVisible(false);

        adddailybtn.setBackground(new Color(206, 245, 245));
        searchdailybtn.setBackground(new Color(0, 0, 153));
        printdailybtn.setBackground(new Color(0, 0, 153));
        searchdailysumbtn.setBackground(new Color(0, 0, 153));

        adddailybtn.setForeground(Color.BLACK);
        searchdailybtn.setForeground(Color.WHITE);
        printdailybtn.setForeground(Color.WHITE);
        searchdailysumbtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_adddailybtnActionPerformed


    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:

        String sub_total = billsubtotal.getText();
        String cash = billcash.getText();
        String credit = billcredit.getText();
        Date cdate = billcdate.getDate();
        Date bdate = billdate.getDate();
        String cnumber = billcnumber.getText();
        String camount = billcamount.getText();
        String bank = billcbank.getText();
        String sshop = billshop.getText();
        String billno = this.billno.getText();
        String refn = billref.getSelectedItem().toString();

        if (jTable9.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "First added a product to table", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (sub_total.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Sub Total", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (cash.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Cash", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (credit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Credit", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (camount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Check amount", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            for (int i = 0; i < jTable9.getRowCount(); i++) {

                String ref = jTable9.getValueAt(i, 0).toString();
                String billdate = jTable9.getValueAt(i, 1).toString();
                String route = jTable9.getValueAt(i, 2).toString();
                String billnu = jTable9.getValueAt(i, 3).toString();
                String shop = jTable9.getValueAt(i, 4).toString();
                String product = jTable9.getValueAt(i, 5).toString();
                String qty = jTable9.getValueAt(i, 6).toString();
                String price = jTable9.getValueAt(i, 7).toString();
                String total = jTable9.getValueAt(i, 8).toString();

                try {

                    ResultSet rsp = MySQL.search("SELECT * FROM `product` WHERE `pname`='" + product + "'  ");
                    rsp.next();
                    String pid = rsp.getString("id");
                    System.out.println(pid);

                    ResultSet rs1 = MySQL.search("SELECT * FROM `ref` WHERE `fname`='" + ref + "'  ");
                    rs1.next();
                    String rid = rs1.getString("id");
                    System.out.println(rid);

                    ResultSet rs2 = MySQL.search("SELECT * FROM `route` WHERE `name`='" + route + "'  ");
                    rs2.next();
                    String rouid = rs2.getString("id");
                    System.out.println(rouid);

                    MySQL.iud("INSERT INTO `bill`(`ref`,`date`,`route`,`bill_no`,`shop_name`,`product`,`qty`,`price`,`amount`,`subtotal`,`cash`,`check`,`credit`) "
                            + "VALUES('" + rid + "','" + billdate + "','" + rouid + "','" + billnu + "','" + shop + "','" + pid + "','" + qty + "','" + price + "','" + total + "','" + sub_total + "','" + cash + "','" + camount + "','" + credit + "') ");

                    ResultSet vehi = MySQL.search(" SELECT * FROM `vehistock` WHERE `ref`='" + rid + "' AND `product`='" + pid + "' AND `price`='" + price + "' AND `status`='1' ");

                    if (vehi.next()) {

                        String vehisid = vehi.getString("id");
                        int vehiaqty = Integer.parseInt(vehi.getString("qty"));
                        int sqty = Integer.parseInt(qty);
                        int newqty = vehiaqty - sqty;

                        String newvehiqty = String.valueOf(newqty);

                        MySQL.iud(" UPDATE `vehistock` SET `qty`='" + newvehiqty + "' WHERE `id`='" + vehisid + "' ");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            try {

                ResultSet rs = MySQL.search("SELECT * FROM `ref` WHERE `fname`='" + refn + "'  ");
                rs.next();
                String rrid = rs.getString("id");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String creddate = sdf.format(bdate);
                String checkdate = sdf.format(cdate);

                if (credit == "0") {

                } else {
                    MySQL.iud("INSERT INTO `credit`(`date`,`shop_name`,`bill_no`,`credit_amount`,`status`,`ref`) "
                            + "VALUES('" + creddate + "','" + sshop + "','" + billno + "','" + credit + "','1','" + rrid + "') ");

                }

                if (camount == "0") {

                } else {
                    MySQL.iud("INSERT INTO `check`(`date`,`shop_name`,`bill_no`,`amount`,`check_no`,`cash`,`check_amount`,`bank`,`check_date`,`status`,`ref`) "
                            + "VALUES('" + creddate + "','" + sshop + "','" + billno + "','" + sub_total + "','" + cnumber + "','" + cash + "','" + camount + "','" + bank + "','" + checkdate + "','1','" + rrid + "') ");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "Bill Added Succesfull", "success", JOptionPane.INFORMATION_MESSAGE);

            DefaultTableModel dtm = (DefaultTableModel) jTable9.getModel();
            dtm.setRowCount(0);

            billref.setSelectedItem("Select");
            this.billdate.setDate(null);
            billroute.setSelectedItem("Select");
            this.billno.setText("");
            billshop.setText("");
            billsubtotal.setText("");
            billcash.setText("");
            billcredit.setText("");
            billcdate.setDate(null);
            billcnumber.setText("");
            billcamount.setText("");
            billcbank.setText("");

        }


    }//GEN-LAST:event_jButton32ActionPerformed

    private void jTextField41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField41ActionPerformed

    private void jTextField42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField42ActionPerformed

    String iurouten;
    String issuestockid;

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:

        String ref = billref.getSelectedItem().toString();
        Date deate = billdate.getDate();
        String route = billroute.getSelectedItem().toString();
        String billnu = billno.getText();
        String shop = billshop.getText();

        String product = billproduct.getSelectedItem().toString();
        String qty = billqty.getText();
        String uprice = billuprice.getText();
        String total = billtotal.getText();

        if (ref.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a ref", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (deate == null) {
            JOptionPane.showMessageDialog(this, "Please select a date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (route.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a route", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (billnu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a bill number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (shop.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a shop name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (product.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a product", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (qty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a bill product qunatity", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (uprice.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a product unit price", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (total.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a total", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ddate = sdf.format(deate);

            DefaultTableModel dtm = (DefaultTableModel) jTable9.getModel();

            Vector v = new Vector();
            v.add(ref);
            v.add(ddate);
            v.add(route);
            v.add(billnu);
            v.add(shop);
            v.add(product);
            v.add(qty);
            v.add(uprice);
            v.add(total);

            dtm.addRow(v);

            JOptionPane.showMessageDialog(this, "Product added to the Bill", "Success", JOptionPane.INFORMATION_MESSAGE);

            billproduct.setSelectedItem("Select");
            billqty.setText("");
            billuprice.setText("");
            billtotal.setText("");

        }

    }//GEN-LAST:event_jButton30ActionPerformed

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        daily.setVisible(true);
        issue.setVisible(false);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        retu.setVisible(false);
        adddaily.setVisible(true);
        searchdaily.setVisible(false);
        adddailysum.setVisible(false);

        adddailybtn.setBackground(new Color(206, 245, 245));
        searchdailybtn.setBackground(new Color(0, 0, 153));
        printdailybtn.setBackground(new Color(0, 0, 153));

        adddailybtn.setForeground(Color.BLACK);
        searchdailybtn.setForeground(Color.WHITE);
        printdailybtn.setForeground(Color.WHITE);

        dailyy.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));

        label.setText("Daily Collection");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/daily.png")));
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        addproduct ap = new addproduct(this);
        ap.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        String ref = jComboBox1.getSelectedItem().toString();
        Date date = jDateChooser3.getDate();
        String invoice_no = jTextField3.getText();

        String code = jTextField26.getText();
        String product = jComboBox2.getSelectedItem().toString();
        String dis = jTextField6.getText();
        String uprice = jTextField2.getText();
        String qty = jTextField1.getText();
        String total = jTextField28.getText();

        if (ref.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select a ref !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (date == null) {
            JOptionPane.showMessageDialog(this, "Please Select a date !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (invoice_no.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Invoice Number !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Product code !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (product.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select a Product !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (dis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Discount Precentage !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (uprice.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Unit Price !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (qty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Quantity !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (total.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Total Amount !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

            Vector v = new Vector();
            v.add(code);
            v.add(product);
            v.add(dis);
            v.add(qty);
            v.add(uprice);
            v.add(total);

            dtm.addRow(v);

            JOptionPane.showMessageDialog(this, "Product added to the Stock Table", "Success", JOptionPane.INFORMATION_MESSAGE);

            jComboBox2.setSelectedItem("Select");
            jTextField26.setText("");
            jTextField6.setText("");
            jTextField2.setText("");
            jTextField1.setText("");
            jTextField28.setText("");

            updateTotal();

        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        // TODO add your handling code here:


    }//GEN-LAST:event_jTextField5KeyReleased

    private void jComboBox9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox9ItemStateChanged
        // TODO add your handling code here:
        searchstock();
    }//GEN-LAST:event_jComboBox9ItemStateChanged

    private void jTextField11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyReleased
        // TODO add your handling code here:
        searchstock();
    }//GEN-LAST:event_jTextField11KeyReleased

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        // TODO add your handling code here:
        searchstock();
    }//GEN-LAST:event_jTextField15KeyReleased

    private void jTextField12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyReleased
        // TODO add your handling code here:
        searchstock();
    }//GEN-LAST:event_jTextField12KeyReleased

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:

        String fname = rfname.getText();
        String lname = rlname.getText();
        String nic = rnic.getText();
        String mobile = rmobile.getText();
        String vehinum = rvehicle.getSelectedItem().toString();
        String rrcompay = rcompany.getSelectedItem().toString();
        String dddfname = rdfname.getText();
        String dddlname = rdlname.getText();
        String dddnic = rdnic.getText();
        String dddmobile = rdmobile.getText();

        if (fname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a First Name !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (lname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Last Name !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a NIC Number !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Mobile Number !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (vehinum.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select a vehicle !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (rrcompay.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select a Company !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (dddfname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Driver First Name !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (dddlname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Driver Last Name !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (dddnic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a  Driver NIC Number !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (dddmobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Driver Mobile Number !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                ResultSet rs = MySQL.search("SELECT * FROM `ref` WHERE `fname`='" + fname + "' AND `lname`='" + lname + "' AND `nic`='" + nic + "' AND `mobile`='" + mobile + "' ");

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "This Agent Already Registered !", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    ResultSet rs1 = MySQL.search("SELECT * FROM `vehicle`  WHERE `number`='" + vehinum + "' ");
                    rs1.next();
                    String vid = rs1.getString("id");

                    ResultSet rs2 = MySQL.search("SELECT * FROM `company`  WHERE `name`='" + rrcompay + "' ");
                    rs2.next();
                    String cid = rs2.getString("id");

                    MySQL.iud("INSERT INTO `ref`(`fname`,`lname`,`nic`,`mobile`,`status_id`,`vehicle`,`dfname`,`dlname`,`dnic`,`dmobile`,`company`) VALUES('" + fname + "','" + lname + "','" + nic + "','" + mobile + "','1','" + vid + "','" + dddfname + "','" + dddlname + "','" + dddnic + "','" + dddmobile + "','" + cid + "') ");

                    rfname.setText("");
                    rlname.setText("");
                    rnic.setText("");
                    rmobile.setText("");
                    rvehicle.setSelectedItem("Select");
                    rcompany.setSelectedItem("Select");
                    rdfname.setText("");
                    rdlname.setText("");
                    rdnic.setText("");
                    rdmobile.setText("");

                    loadref();

                    JOptionPane.showMessageDialog(this, "Reference Agent & Driver Registration Successful !", "Success", JOptionPane.INFORMATION_MESSAGE);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_jButton13ActionPerformed

    private void rmobileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rmobileKeyReleased
        // TODO add your handling code here:

        String mobile = rmobile.getText();
        String text = mobile + evt.getKeyChar();

        if (text.length() == 1) {
            if (!text.equals("0")) {
                evt.consume();
            }
        } else if (text.length() == 2) {
            if (!text.equals("07")) {
                evt.consume();
            }
        } else if (text.length() == 3) {
            if (!Pattern.compile("07[01245678]").matcher(text).matches()) {
                evt.consume();
            }
        } else if (text.length() <= 10) {
            if (!Pattern.compile("07[01245678][0-9]+").matcher(text).matches()) {
                evt.consume();
            }

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_rmobileKeyReleased

    String updaterefid;

    private void uptabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_uptabelMouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {

            int r = uptabel.getSelectedRow();

            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Please Select a Ref", "warning", JOptionPane.WARNING_MESSAGE);
            } else {

                String rid = uptabel.getValueAt(r, 0).toString();
                String rfname = uptabel.getValueAt(r, 1).toString();
                String rlname = uptabel.getValueAt(r, 2).toString();
                String rnic = uptabel.getValueAt(r, 3).toString();
                String rmobile = uptabel.getValueAt(r, 4).toString();
                String rvehi = uptabel.getValueAt(r, 5).toString();
                String rdfname = uptabel.getValueAt(r, 6).toString();
                String rdlname = uptabel.getValueAt(r, 7).toString();
                String rdnic = uptabel.getValueAt(r, 8).toString();
                String rdmobile = uptabel.getValueAt(r, 9).toString();

                updaterefid = rid;

                uprfname.setText(rfname);
                uprlname.setText(rlname);
                uprnic.setText(rnic);
                uprmobile.setText(rmobile);
                jComboBox3.setSelectedItem(rvehi);
                updfname.setText(rdfname);
                updlname.setText(rdlname);
                updnic.setText(rdnic);
                updmobile.setText(rdmobile);

            }
        }

    }//GEN-LAST:event_uptabelMouseClicked

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:

        String rfname = uprfname.getText();
        String rlname = uprlname.getText();
        String rnic = uprnic.getText();
        String rmobile = uprmobile.getText();
        String rvehi = jComboBox3.getSelectedItem().toString();
        String rdfname = updfname.getText();
        String rdlname = updlname.getText();
        String rdnic = updnic.getText();
        String rdmobile = updmobile.getText();

        if (rfname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a First Name !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (rlname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Last Name !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (rnic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a NIC Number !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (rmobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Mobile Number !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (rvehi.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select a Vehicle !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (rdfname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Driver First Name !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (rdlname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Driver Last Name !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (rdnic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Driver NIC !", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (rdmobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter a Driver Mobile!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                ResultSet rs1 = MySQL.search("SELECT * FROM `vehicle`  WHERE `number`='" + rvehi + "' ");
                rs1.next();
                String vid = rs1.getString("id");

                MySQL.iud("UPDATE `ref` SET `fname`='" + rfname + "',`lname`='" + rlname + "',`nic`='" + rnic + "',`mobile`='" + rmobile + "',`vehicle`='" + vid + "',`dfname`='" + rdfname + "',`dlname`='" + rdlname + "',`dnic`='" + rdnic + "',`dmobile`='" + rdmobile + "' WHERE `id`='" + updaterefid + "' ");

                uprfname.setText("");
                uprlname.setText("");
                uprnic.setText("");
                uprmobile.setText("");
                jComboBox3.setSelectedItem("Select");
                updfname.setText("");
                updlname.setText("");
                updnic.setText("");
                updmobile.setText("");

                loadrefup();
                loadref();

                JOptionPane.showMessageDialog(this, "Reference Agent Update Successful !", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_jButton19ActionPerformed

    private void rmobileKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rmobileKeyTyped
        // TODO add your handling code here:

        String mobile = rmobile.getText();
        String text = mobile + evt.getKeyChar();

        if (text.length() == 1) {
            if (!text.equals("0")) {
                evt.consume();
            }
        } else if (text.length() == 2) {
            if (!text.equals("07")) {
                evt.consume();
            }
        } else if (text.length() == 3) {
            if (!Pattern.compile("07[01245678]").matcher(text).matches()) {
                evt.consume();
            }
        } else if (text.length() <= 10) {
            if (!Pattern.compile("07[01245678][0-9]+").matcher(text).matches()) {
                evt.consume();
            }

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_rmobileKeyTyped

    private void uprmobileKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uprmobileKeyTyped
        // TODO add your handling code here:
        String mobile = uprmobile.getText();
        String text = mobile + evt.getKeyChar();

        if (text.length() == 1) {
            if (!text.equals("0")) {
                evt.consume();
            }
        } else if (text.length() == 2) {
            if (!text.equals("07")) {
                evt.consume();
            }
        } else if (text.length() == 3) {
            if (!Pattern.compile("07[01245678]").matcher(text).matches()) {
                evt.consume();
            }
        } else if (text.length() <= 10) {
            if (!Pattern.compile("07[01245678][0-9]+").matcher(text).matches()) {
                evt.consume();
            }

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_uprmobileKeyTyped

    private void sfnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sfnameKeyReleased
        // TODO add your handling code here:
        searchref();
    }//GEN-LAST:event_sfnameKeyReleased

    private void slnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_slnameKeyReleased
        // TODO add your handling code here:
        searchref();
    }//GEN-LAST:event_slnameKeyReleased

    private void snicKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_snicKeyReleased
        // TODO add your handling code here:
        searchref();
    }//GEN-LAST:event_snicKeyReleased

    private void smobileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smobileKeyReleased
        // TODO add your handling code here:
        searchref();
    }//GEN-LAST:event_smobileKeyReleased

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:

        int r = sreftable.getSelectedRow();

        if (r == -1) {
            JOptionPane.showMessageDialog(this, "First Select Ref in Table!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String refid = sreftable.getValueAt(r, 0).toString();

            MySQL.iud("UPDATE `ref` SET `status_id`='2' WHERE `id`='" + refid + "' ");

            JOptionPane.showMessageDialog(this, "Reference Agent Delete Successful !", "Success", JOptionPane.INFORMATION_MESSAGE);

            loadrefearch();

        }

    }//GEN-LAST:event_jButton24ActionPerformed

    private void jTextField25KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField25KeyReleased
        // TODO add your handling code here:
        searchissue();
    }//GEN-LAST:event_jTextField25KeyReleased

    private void jDateChooser5PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser5PropertyChange
        // TODO add your handling code here:
        searchissue();
    }//GEN-LAST:event_jDateChooser5PropertyChange

    private void jDateChooser6PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser6PropertyChange
        // TODO add your handling code here:
        searchissue();
    }//GEN-LAST:event_jDateChooser6PropertyChange

    private void billqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billqtyKeyTyped
        // TODO add your handling code here:


    }//GEN-LAST:event_billqtyKeyTyped

    private void billqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billqtyKeyReleased
        // TODO add your handling code here:


    }//GEN-LAST:event_billqtyKeyReleased

    private void jComboBox26ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox26ItemStateChanged
        // TODO add your handling code here:
        searchdaily();
    }//GEN-LAST:event_jComboBox26ItemStateChanged

    private void jTextField42KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField42KeyReleased
        // TODO add your handling code here:
        searchdaily();
    }//GEN-LAST:event_jTextField42KeyReleased

    private void jDateChooser12PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser12PropertyChange
        // TODO add your handling code here:
        searchdaily();
    }//GEN-LAST:event_jDateChooser12PropertyChange

    private void jDateChooser13PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser13PropertyChange
        // TODO add your handling code here:
        searchdaily();
    }//GEN-LAST:event_jDateChooser13PropertyChange

    private void jTextField41KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField41KeyReleased
        // TODO add your handling code here:
        searchdaily();
    }//GEN-LAST:event_jTextField41KeyReleased

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:

        String shop = jTextField24.getText();
        String total = jTextField33.getText();
        String product = jComboBox19.getSelectedItem().toString();
        String rreff = jComboBox20.getSelectedItem().toString();
        String reqty = jTextField20.getText();
        String price = jTextField22.getText();
        Date idate = jDateChooser9.getDate();
        Date redate = jDateChooser10.getDate();

        if (rreff.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select ref!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (product.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select Product!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (shop.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter shop name!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (reqty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter return quantity!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (total.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a total!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (idate == null) {
            JOptionPane.showMessageDialog(this, "Please select a issue date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (redate == null) {
            JOptionPane.showMessageDialog(this, "Please select a return date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                ResultSet rv = MySQL.search("SELECT * FROM `ref` INNER JOIN `vehicle` ON `vehicle`.`id`=`ref`.`vehicle` WHERE `ref`.`fname`='" + rreff + "' ");
                rv.next();
                String vehi = rv.getString("vehicle.number");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String sdfidate = sdf.format(idate);
                String sdfredate = sdf.format(redate);

                DefaultTableModel dtm = (DefaultTableModel) jTable8.getModel();

                Vector v = new Vector();
                v.add(rreff);
                v.add(vehi);
                v.add(product);
                v.add(shop);
                v.add(reqty);
                v.add(price);
                v.add(total);
                v.add(sdfidate);
                v.add(sdfredate);

                dtm.addRow(v);

                JOptionPane.showMessageDialog(this, "Product added to table", "Done", JOptionPane.INFORMATION_MESSAGE);

                jComboBox19.setSelectedItem("Select");
                jComboBox20.setSelectedItem("Select");
                jTextField20.setText("");
                jTextField22.setText("");
                jDateChooser9.setDate(null);
                jDateChooser10.setDate(null);
                jTextField24.setText("");
                jTextField33.setText("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_jButton25ActionPerformed

    private void jTextField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:

        String ggid = gid.getText();
        String ggcname = gcname.getText();
        String ggpname = gpname.getText();
        String ggqty = gqty.getText();
        String gguprice = guprice.getText();
        String ggtotal = gtotal.getText();
        String ggdate = gdate.getText();
        String ggref = gref.getText();
        String ggroute = groute.getText();

        if (ggid.equals("None")) {
            JOptionPane.showMessageDialog(this, "Please select a return product", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            DefaultTableModel dtm = (DefaultTableModel) jTable13.getModel();

            Vector v = new Vector();
            v.add(ggid);
            v.add(ggcname);
            v.add(ggpname);
            v.add(ggqty);
            v.add(gguprice);
            v.add(ggtotal);
            v.add(ggdate);
            v.add(ggroute);
            v.add(ggref);

            dtm.addRow(v);

            JOptionPane.showMessageDialog(this, "Product added to table", "Done", JOptionPane.INFORMATION_MESSAGE);

            gid.setText("None");
            gcname.setText("None");
            gpname.setText("None");
            gqty.setText("0");
            guprice.setText("0.00");
            gtotal.setText("0.00");
            gdate.setText("YYYY-MM-DD");
            groute.setText("None");
            gref.setText("None");

        }

    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:

        int r = jTable13.getRowCount();

        if (r <= 0) {
            JOptionPane.showMessageDialog(this, "Please add a product  to table", "warning", JOptionPane.WARNING_MESSAGE);
        } else {

            for (int i = 0; i < jTable13.getRowCount(); i++) {

                String gggid = jTable13.getValueAt(i, 0).toString();

                MySQL.iud("UPDATE `return` SET `status`='2' WHERE `id`='" + gggid + "' ");

            }

            JOptionPane.showMessageDialog(this, "Return product added stock", "success", JOptionPane.INFORMATION_MESSAGE);

            DefaultTableModel dtm = (DefaultTableModel) jTable13.getModel();
            dtm.setRowCount(0);

        }

    }//GEN-LAST:event_jButton29ActionPerformed

    private void jLabel53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseClicked
        // TODO add your handling code here:

        stock.setVisible(true);
        dash.setVisible(false);
        ref.setVisible(false);
        issue.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);

        loadcompany();
        loadproduct();
        loadpayment_type();
        loadstock();
        loadref();
        loadrefup();
        loadrefearch();
        loadroute();
        loadvehicle();
        loadissue();

        stockk.setBackground(new Color(1, 1, 59));
        dashh.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));

        addstock.setVisible(true);
        searchstock.setVisible(false);
        stockreports.setVisible(false);
        // stockreprts.setVisible(false);

        addstockbtn.setBackground(new Color(206, 245, 245));
        searchstockbtn.setBackground(new Color(0, 0, 153));
        reportstockbtn.setBackground(new Color(0, 0, 153));

        addstockbtn.setForeground(Color.BLACK);
        searchstockbtn.setForeground(Color.WHITE);
        reportstockbtn.setForeground(Color.WHITE);

        label.setText("Stock Managment");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stock.png")));

    }//GEN-LAST:event_jLabel53MouseClicked

    private void jLabel54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseClicked
        // TODO add your handling code here:

        stock.setVisible(true);
        dash.setVisible(false);
        ref.setVisible(false);
        issue.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);

        loadcompany();
        loadproduct();
        loadpayment_type();
        loadstock();
        loadref();
        loadrefup();
        loadrefearch();
        loadroute();
        loadvehicle();
        loadissue();

        stockk.setBackground(new Color(1, 1, 59));
        dashh.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));

        addstock.setVisible(true);
        searchstock.setVisible(false);
        stockreports.setVisible(false);
        // stockreprts.setVisible(false);

        addstockbtn.setBackground(new Color(206, 245, 245));
        searchstockbtn.setBackground(new Color(0, 0, 153));
        reportstockbtn.setBackground(new Color(0, 0, 153));

        addstockbtn.setForeground(Color.BLACK);
        searchstockbtn.setForeground(Color.WHITE);
        reportstockbtn.setForeground(Color.WHITE);

        label.setText("Stock Managment");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stock.png")));
    }//GEN-LAST:event_jLabel54MouseClicked

    private void jLabel59MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseClicked
        // TODO add your handling code here:

        issue.setVisible(true);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);

        searchissue.setVisible(false);

        tdyissuebtn.setBackground(new Color(206, 245, 245));
        searchissuebtn.setBackground(new Color(0, 0, 153));

        tdyissuebtn.setForeground(Color.BLACK);
        searchissuebtn.setForeground(Color.WHITE);

        issuee.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));

        label.setText("Issue Products");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/issu.png")));
    }//GEN-LAST:event_jLabel59MouseClicked

    private void jLabel60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseClicked
        // TODO add your handling code here:

        issue.setVisible(true);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);

        searchissue.setVisible(false);

        tdyissuebtn.setBackground(new Color(206, 245, 245));
        searchissuebtn.setBackground(new Color(0, 0, 153));

        tdyissuebtn.setForeground(Color.BLACK);
        searchissuebtn.setForeground(Color.WHITE);

        issuee.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));

        label.setText("Issue Products");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/issu.png")));
    }//GEN-LAST:event_jLabel60MouseClicked

    private void jLabel63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel63MouseClicked
        // TODO add your handling code here:

        issue.setVisible(false);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);

        incomee.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));

        label.setText("View Income");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/money.png")));

    }//GEN-LAST:event_jLabel63MouseClicked

    private void jLabel64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseClicked
        // TODO add your handling code here:
        issue.setVisible(false);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        retu.setVisible(false);
        daily.setVisible(false);

        incomee.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));

        label.setText("View Income");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/money.png")));

    }//GEN-LAST:event_jLabel64MouseClicked

    private void jLabel61MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel61MouseClicked
        // TODO add your handling code here:
        retu.setVisible(true);
        issue.setVisible(false);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        daily.setVisible(false);

        retustock.setVisible(true);
        retucmpny.setVisible(false);

        retustockbtn.setBackground(new Color(206, 245, 245));
        retucmpnybtn.setBackground(new Color(0, 0, 153));

        retustockbtn.setForeground(Color.BLACK);
        retucmpnybtn.setForeground(Color.WHITE);

        retuu.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));

        label.setText("Return Products");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return.png")));
    }//GEN-LAST:event_jLabel61MouseClicked

    private void jLabel62MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel62MouseClicked
        // TODO add your handling code here:
        retu.setVisible(true);
        issue.setVisible(false);
        ref.setVisible(false);
        dash.setVisible(false);
        stock.setVisible(false);
        daily.setVisible(false);

        retustock.setVisible(true);
        retucmpny.setVisible(false);

        retustockbtn.setBackground(new Color(206, 245, 245));
        retucmpnybtn.setBackground(new Color(0, 0, 153));

        retustockbtn.setForeground(Color.BLACK);
        retucmpnybtn.setForeground(Color.WHITE);

        retuu.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));

        label.setText("Return Products");

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return.png")));
    }//GEN-LAST:event_jLabel62MouseClicked

    private void jComboBox11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox11ItemStateChanged
        // TODO add your handling code here:
        searchstockreport();
    }//GEN-LAST:event_jComboBox11ItemStateChanged

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jTextField13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyReleased
        // TODO add your handling code here:
        searchstockreport();
    }//GEN-LAST:event_jTextField13KeyReleased

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17ActionPerformed

    private void jTextField17KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyReleased
        // TODO add your handling code here:
        searchstockreport();
    }//GEN-LAST:event_jTextField17KeyReleased

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jTextField19KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField19KeyReleased
        // TODO add your handling code here:
        searchstockreport();
    }//GEN-LAST:event_jTextField19KeyReleased

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:

        try {

            JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/stockreport.jrxml"));

            HashMap parameters = new HashMap();

            // JREmptyDataSource dataSource = new JREmptyDataSource();
            TableModel tm = jTable6.getModel();
            JRTableModelDataSource dataSource = new JRTableModelDataSource(tm);

            //Connection dataSource = MySQL.getConnection();
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, dataSource);

            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        dailyy.setBackground(new Color(57, 126, 189));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));
        vehii.setBackground(new Color(57, 126, 189));
        ccc.setBackground(new Color(57, 126, 189));

        backup bc = new backup();
        bc.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jComboBox12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox12ItemStateChanged
        // TODO add your handling code here:
        searchhistorystock();
    }//GEN-LAST:event_jComboBox12ItemStateChanged

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField16KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyReleased
        // TODO add your handling code here:
        searchhistorystock();
    }//GEN-LAST:event_jTextField16KeyReleased

    private void jTextField23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23ActionPerformed

    private void jTextField23KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField23KeyReleased
        // TODO add your handling code here:
        searchhistorystock();
    }//GEN-LAST:event_jTextField23KeyReleased

    private void jTextField27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField27ActionPerformed

    private void jTextField27KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField27KeyReleased
        // TODO add your handling code here:
        searchhistorystock();
    }//GEN-LAST:event_jTextField27KeyReleased

    private void jDateChooser16PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser16PropertyChange
        // TODO add your handling code here:
        searchhistorystock();
    }//GEN-LAST:event_jDateChooser16PropertyChange

    private void jDateChooser17PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser17PropertyChange
        // TODO add your handling code here:
        searchhistorystock();
    }//GEN-LAST:event_jDateChooser17PropertyChange

    private void stockhistorybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockhistorybtnActionPerformed
        // TODO add your handling code here:

        stockhistoryload();

        stockreports.setVisible(false);
        addstock.setVisible(false);
        searchstock.setVisible(false);
        stockhistory.setVisible(true);

        stockhistorybtn.setBackground(new Color(206, 245, 245));
        searchstockbtn.setBackground(new Color(0, 0, 153));
        addstockbtn.setBackground(new Color(0, 0, 153));
        reportstockbtn.setBackground(new Color(0, 0, 153));

        stockhistorybtn.setForeground(Color.BLACK);
        searchstockbtn.setForeground(Color.WHITE);
        addstockbtn.setForeground(Color.WHITE);
        reportstockbtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_stockhistorybtnActionPerformed

    private void vehiiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vehiiMouseClicked
        // TODO add your handling code here

        vehii.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));
        ccc.setBackground(new Color(57, 126, 189));

        vehistock vh = new vehistock();
        vh.setVisible(true);
    }//GEN-LAST:event_vehiiMouseClicked

    private void rdfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdfnameActionPerformed

    private void rdlnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdlnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdlnameActionPerformed

    private void rdnicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdnicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdnicActionPerformed

    private void rdmobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdmobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdmobileActionPerformed

    private void rdmobileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rdmobileKeyReleased
        // TODO add your handling code here:
        String mobile = rdmobile.getText();
        String text = mobile + evt.getKeyChar();

        if (text.length() == 1) {
            if (!text.equals("0")) {
                evt.consume();
            }
        } else if (text.length() == 2) {
            if (!text.equals("07")) {
                evt.consume();
            }
        } else if (text.length() == 3) {
            if (!Pattern.compile("07[01245678]").matcher(text).matches()) {
                evt.consume();
            }
        } else if (text.length() <= 10) {
            if (!Pattern.compile("07[01245678][0-9]+").matcher(text).matches()) {
                evt.consume();
            }

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_rdmobileKeyReleased

    private void rdmobileKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rdmobileKeyTyped
        // TODO add your handling code here:
        String mobile = rdmobile.getText();
        String text = mobile + evt.getKeyChar();

        if (text.length() == 1) {
            if (!text.equals("0")) {
                evt.consume();
            }
        } else if (text.length() == 2) {
            if (!text.equals("07")) {
                evt.consume();
            }
        } else if (text.length() == 3) {
            if (!Pattern.compile("07[01245678]").matcher(text).matches()) {
                evt.consume();
            }
        } else if (text.length() <= 10) {
            if (!Pattern.compile("07[01245678][0-9]+").matcher(text).matches()) {
                evt.consume();
            }

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_rdmobileKeyTyped

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
        addvehicle av = new addvehicle(this);
        av.setVisible(true);
    }//GEN-LAST:event_jButton37ActionPerformed

    private void dlnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dlnameActionPerformed

    private void dlnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dlnameKeyReleased
        // TODO add your handling code here:
        searchref();
    }//GEN-LAST:event_dlnameKeyReleased

    private void dfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dfnameActionPerformed

    private void dfnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dfnameKeyReleased
        // TODO add your handling code here:
        searchref();
    }//GEN-LAST:event_dfnameKeyReleased

    private void dnicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dnicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dnicActionPerformed

    private void dnicKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dnicKeyReleased
        // TODO add your handling code here:
        searchref();
    }//GEN-LAST:event_dnicKeyReleased

    private void dmobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dmobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dmobileActionPerformed

    private void dmobileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dmobileKeyReleased
        // TODO add your handling code here:
        searchref();
    }//GEN-LAST:event_dmobileKeyReleased

    private void updfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updfnameActionPerformed

    private void updlnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updlnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updlnameActionPerformed

    private void updnicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updnicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updnicActionPerformed

    private void updmobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updmobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updmobileActionPerformed

    private void updmobileKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_updmobileKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_updmobileKeyTyped

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:

        addcompany aac = new addcompany(this);
        aac.setVisible(true);
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jDateChooser3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser3PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooser3PropertyChange

    private void jTextField28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28ActionPerformed

    private void jTextField29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29ActionPerformed

    private void jTextField29KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField29KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29KeyReleased

    private void jTextField30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField30ActionPerformed

    private void jTextField30KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField30KeyReleased
        // TODO add your handling code here:

        Double subtotal = Double.valueOf(jTextField29.getText());
        Double dis = Double.valueOf(jTextField30.getText());

        if (jTextField30.getText().isEmpty()) {

            jTextField5.setText("");
            jTextField31.setText("");

        } else {

            Double amount = subtotal * dis / 100;
            jTextField31.setText(df.format(amount));

            Double netbalue = subtotal - amount;
            jTextField5.setText(df.format(netbalue));

        }


    }//GEN-LAST:event_jTextField30KeyReleased

    private void jTextField31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31ActionPerformed

    private void jTextField31KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField31KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31KeyReleased

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void jTextField21KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField21KeyReleased
        // TODO add your handling code here:
        searchissue();
    }//GEN-LAST:event_jTextField21KeyReleased

    private void jTextField21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField21ActionPerformed

    private void billcdatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_billcdatePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_billcdatePropertyChange

    private void billnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billnoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_billnoKeyReleased

    private void billnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billnoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_billnoKeyTyped

    private void billshopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billshopKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_billshopKeyReleased

    private void billshopKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billshopKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_billshopKeyTyped

    private void billupriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billupriceKeyReleased
        // TODO add your handling code here:

        int qty = Integer.parseInt(billqty.getText());
        int price = Integer.parseInt(billuprice.getText());

        int total = qty * price;

        billtotal.setText(String.valueOf(total));

    }//GEN-LAST:event_billupriceKeyReleased

    private void billupriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billupriceKeyTyped
        // TODO add your handling code here:


    }//GEN-LAST:event_billupriceKeyTyped

    private void billtotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billtotalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_billtotalKeyReleased

    private void billtotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billtotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_billtotalKeyTyped

    private void billsubtotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billsubtotalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_billsubtotalKeyReleased

    private void billsubtotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billsubtotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_billsubtotalKeyTyped

    private void billcashKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcashKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_billcashKeyReleased

    private void billcashKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcashKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_billcashKeyTyped

    private void billcreditKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcreditKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_billcreditKeyReleased

    private void billcreditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcreditKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_billcreditKeyTyped

    private void billcnumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcnumberKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_billcnumberKeyReleased

    private void billcnumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcnumberKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_billcnumberKeyTyped

    private void billcbankKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcbankKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_billcbankKeyReleased

    private void billcbankKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcbankKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_billcbankKeyTyped

    private void billdatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_billdatePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_billdatePropertyChange

    private void billcamountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcamountKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_billcamountKeyReleased

    private void billcamountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billcamountKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_billcamountKeyTyped

    private void billupriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billupriceKeyPressed
        // TODO add your handling code here:


    }//GEN-LAST:event_billupriceKeyPressed

    private void jTextField43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField43ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField43ActionPerformed

    private void jTextField43KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField43KeyReleased
        // TODO add your handling code here:
        searchdaily();
    }//GEN-LAST:event_jTextField43KeyReleased


    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:

        String ref = dailyref.getSelectedItem().toString();
        Date dailydate = this.dailydate.getDate();
        String cash = dailycash.getText();
        String check = dailycheck.getText();
        String credit = dailycredit.getText();
        String daysale = dailysale.getText();
        String ccolection = dailycc.getText();
        String total = dailyamount.getText();
        String billpaid = dailypaid.getText();
        String driver = dailysalary.getText();
        String cih = dailyhand.getText();

        Double baankadd = Double.valueOf(cih) + Double.valueOf(check);
        Double bankadd = baankadd;

        if (ref.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Select a ref", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (dailydate == null) {
            JOptionPane.showMessageDialog(this, "Select a date", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (cash.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a cash amount", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (check.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a check amount", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (credit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a credit amount", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (daysale.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a day sale", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (ccolection.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a credit collection", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (total.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a total amount", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (billpaid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a bill paid amount", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (driver.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a driver salary", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else if (cih.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a cash in hand", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ddate = sdf.format(dailydate);

            try {

                ResultSet re = MySQL.search(" SELECT * FROM `ref` WHERE `fname`='" + ref + "' ");
                re.next();
                String rid = re.getString("id");

                MySQL.iud("INSERT INTO `collection`(`ref`,`date`,`cash`,`check`,`credit`,`day`,`c.collection`,`total`,`billpaid`,`driver`,`inhand`) "
                        + "VALUES('" + rid + "','" + ddate + "','" + cash + "','" + check + "','" + credit + "','" + daysale + "','" + ccolection + "','" + total + "','" + billpaid + "','" + driver + "','" + cih + "') ");

                ResultSet in = MySQL.search(" SELECT * FROM `income` WHERE `date`='" + date + "' ");
                if (in.next()) {

                    Double ai = Double.valueOf(in.getString("income"));
                    Double di = Double.valueOf(cih);
                    Double newi = ai + di;
                    String newinco = String.valueOf(newi);

                    MySQL.iud("UPDATE `income` SET `income`='" + newinco + "' WHERE `id`='1' ");
                } else {

                    MySQL.iud("UPDATE `income` SET `income`='" + cih + "',`date`='" + date + "' WHERE `id`='1' ");

                }

                ResultSet b = MySQL.search("SELECT * FROM `bank` ");
                b.next();
                String balance = b.getString("money");

                Double ab = Double.valueOf(balance);
                Double nb = ab + bankadd;

                String newab = String.valueOf(nb);
                jTextField4s.setText(newab);

                loadbalance();

                JOptionPane.showMessageDialog(this, "Daily collection added succesfull", "suucess", JOptionPane.INFORMATION_MESSAGE);

                dailyref.setSelectedItem("Select");
                dailyhand.setText("");
                dailysalary.setText("");
                dailypaid.setText("");
                dailyamount.setText("");
                dailycc.setText("");
                dailysale.setText("");
                dailycredit.setText("");
                dailycheck.setText("");
                dailycash.setText("");
                this.dailydate.setDate(null);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_jButton36ActionPerformed

    private void dailydatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dailydatePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dailydatePropertyChange

    private void jComboBox27ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox27ItemStateChanged
        // TODO add your handling code here:
        searchcollection();
    }//GEN-LAST:event_jComboBox27ItemStateChanged

    private void jDateChooser14PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser14PropertyChange
        // TODO add your handling code here:
        searchcollection();
    }//GEN-LAST:event_jDateChooser14PropertyChange

    private void searchdailysumbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchdailysumbtnActionPerformed
        // TODO add your handling code here:

        collection();

        searchdailysum.setVisible(true);
        searchdaily.setVisible(false);
        adddaily.setVisible(false);
        adddailysum.setVisible(false);

        searchdailysumbtn.setBackground(new Color(206, 245, 245));
        searchdailybtn.setBackground(new Color(0, 0, 153));
        adddailybtn.setBackground(new Color(0, 0, 153));
        printdailybtn.setBackground(new Color(0, 0, 153));

        searchdailysumbtn.setForeground(Color.BLACK);
        searchdailybtn.setForeground(Color.WHITE);
        adddailybtn.setForeground(Color.WHITE);
        printdailybtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_searchdailysumbtnActionPerformed

    private void cccMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cccMouseClicked
        // TODO add your handling code here:

        ccc.setBackground(new Color(1, 1, 59));
        stockk.setBackground(new Color(57, 126, 189));
        reff.setBackground(new Color(57, 126, 189));
        issuee.setBackground(new Color(57, 126, 189));
        retuu.setBackground(new Color(57, 126, 189));
        dashh.setBackground(new Color(57, 126, 189));
        incomee.setBackground(new Color(57, 126, 189));
        vehii.setBackground(new Color(57, 126, 189));
        dailyy.setBackground(new Color(57, 126, 189));

        cc c = new cc();
        c.setVisible(true);


    }//GEN-LAST:event_cccMouseClicked

    private void jTextField24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField24ActionPerformed

    private void jTextField33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField33ActionPerformed

    private void jTextField4sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4sActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4sActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        String deposit = depo.getText();

        if (deposit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a deposit money", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                ResultSet b = MySQL.search("SELECT * FROM `bank` ");
                b.next();
                String balance = b.getString("money");
                Double bb = Double.valueOf(balance);

                Double co = Double.valueOf(deposit);
                Double newb = bb + co;

                String newbalance = String.valueOf(newb);

                MySQL.iud("UPDATE `bank` SET `money`='" + newbalance + "' WHERE `id`='1' ");

                loadbalance();

                JOptionPane.showMessageDialog(this, "Money add to the bank account", "Add", JOptionPane.INFORMATION_MESSAGE);

                depo.setText("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        String deposit = with.getText();

        if (deposit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a Withdrawal money", "Warnning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                ResultSet b = MySQL.search("SELECT * FROM `bank` ");
                b.next();
                String balance = b.getString("money");
                Double bb = Double.valueOf(balance);

                Double co = Double.valueOf(deposit);
                Double newb = bb - co;

                String newbalance = String.valueOf(newb);

                MySQL.iud("UPDATE `bank` SET `money`='" + newbalance + "' WHERE `id`='1' ");

                loadbalance();

                JOptionPane.showMessageDialog(this, "Money get to the bank account", "Suucess", JOptionPane.INFORMATION_MESSAGE);

                with.setText("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {

            int r = jTable1.getSelectedRow();

            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Please Select a row", "warning", JOptionPane.WARNING_MESSAGE);
            } else {

                String id = jTable1.getValueAt(r, 0).toString();

                int jd = JOptionPane.showOptionDialog(this, "Do you want delete this product in table", "warnning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);

                if (jd == JOptionPane.YES_OPTION) {

                    DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
                    model.removeRow(r);

                }

            }
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable9MouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {

            int r = jTable9.getSelectedRow();

            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Please Select a row", "warning", JOptionPane.WARNING_MESSAGE);
            } else {

                String id = jTable9.getValueAt(r, 0).toString();

                int jd = JOptionPane.showOptionDialog(this, "Do you want delete this product in table", "warnning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);

                if (jd == JOptionPane.YES_OPTION) {

                    DefaultTableModel model = (DefaultTableModel) this.jTable9.getModel();
                    model.removeRow(r);

                }

            }
        }

    }//GEN-LAST:event_jTable9MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DSLhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DSLhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DSLhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DSLhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                DSLhome h = new DSLhome();

                h.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adddaily;
    private javax.swing.JButton adddailybtn;
    private javax.swing.JPanel adddailysum;
    private javax.swing.JPanel addref;
    private javax.swing.JButton addrefbtn;
    private javax.swing.JPanel addstock;
    private javax.swing.JButton addstockbtn;
    private javax.swing.JPanel bank;
    private org.knowm.xchart.style.lines.BaseSeriesLines baseSeriesLines1;
    private javax.swing.JTextField billcamount;
    private javax.swing.JTextField billcash;
    private javax.swing.JTextField billcbank;
    private com.toedter.calendar.JDateChooser billcdate;
    private javax.swing.JTextField billcnumber;
    private javax.swing.JTextField billcredit;
    private com.toedter.calendar.JDateChooser billdate;
    private javax.swing.JTextField billno;
    private javax.swing.JComboBox<String> billproduct;
    private javax.swing.JTextField billqty;
    private javax.swing.JComboBox<String> billref;
    private javax.swing.JComboBox<String> billroute;
    private javax.swing.JTextField billshop;
    private javax.swing.JTextField billsubtotal;
    private javax.swing.JTextField billtotal;
    private javax.swing.JTextField billuprice;
    private javax.swing.JPanel ccc;
    public javax.swing.JProgressBar costpr;
    private org.knowm.xchart.style.markers.Cross cross1;
    private javax.swing.JPanel daily;
    private javax.swing.JTextField dailyamount;
    private javax.swing.JTextField dailycash;
    private javax.swing.JTextField dailycc;
    private javax.swing.JTextField dailycheck;
    private javax.swing.JTextField dailycredit;
    private com.toedter.calendar.JDateChooser dailydate;
    private javax.swing.JTextField dailyhand;
    private javax.swing.JTextField dailypaid;
    private javax.swing.JComboBox<String> dailyref;
    private javax.swing.JTextField dailysalary;
    private javax.swing.JTextField dailysale;
    private javax.swing.JPanel dailyy;
    private javax.swing.JPanel dash;
    private javax.swing.JLabel dash_cost;
    private javax.swing.JLabel dash_income;
    private javax.swing.JLabel dash_profit;
    private javax.swing.JPanel dashh;
    private javax.swing.JTextField depo;
    private javax.swing.JTextField dfname;
    private javax.swing.JTextField dlname;
    private javax.swing.JTextField dmobile;
    private javax.swing.JTextField dnic;
    private org.knowm.xchart.style.colors.FontColorDetector fontColorDetector1;
    public javax.swing.JLabel gcname;
    public javax.swing.JLabel gdate;
    public javax.swing.JLabel gid;
    public javax.swing.JLabel gpname;
    public javax.swing.JLabel gqty;
    public javax.swing.JLabel gref;
    public javax.swing.JLabel groute;
    public javax.swing.JLabel gtotal;
    public javax.swing.JLabel guprice;
    private org.knowm.xchart.style.HeatMapStyler heatMapStyler1;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel incomee;
    public javax.swing.JProgressBar incomepr;
    private javax.swing.JPanel issue;
    private javax.swing.JLabel issuedte1;
    private javax.swing.JPanel issuee;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JComboBox<String> jComboBox26;
    private javax.swing.JComboBox<String> jComboBox27;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox9;
    private com.toedter.calendar.JDateChooser jDateChooser10;
    private com.toedter.calendar.JDateChooser jDateChooser12;
    private com.toedter.calendar.JDateChooser jDateChooser13;
    private com.toedter.calendar.JDateChooser jDateChooser14;
    private com.toedter.calendar.JDateChooser jDateChooser16;
    private com.toedter.calendar.JDateChooser jDateChooser17;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    private com.toedter.calendar.JDateChooser jDateChooser9;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel236;
    private javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel238;
    private javax.swing.JLabel jLabel239;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel240;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel242;
    private javax.swing.JLabel jLabel243;
    private javax.swing.JLabel jLabel244;
    private javax.swing.JLabel jLabel245;
    private javax.swing.JLabel jLabel246;
    private javax.swing.JLabel jLabel247;
    private javax.swing.JLabel jLabel248;
    private javax.swing.JLabel jLabel249;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel250;
    private javax.swing.JLabel jLabel251;
    private javax.swing.JLabel jLabel252;
    private javax.swing.JLabel jLabel253;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTable13;
    private javax.swing.JTable jTable14;
    private javax.swing.JTable jTable15;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField45;
    public javax.swing.JTextField jTextField4s;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel label;
    private org.knowm.xchart.style.markers.MatlabSeriesMarkers matlabSeriesMarkers1;
    private org.knowm.xchart.style.markers.Oval oval1;
    private org.knowm.xchart.PieChartBuilder pieChartBuilder1;
    private org.knowm.xchart.PieChartBuilder pieChartBuilder2;
    private org.knowm.xchart.PieChartBuilder pieChartBuilder3;
    private javax.swing.JButton printdailybtn;
    public javax.swing.JProgressBar profitpr;
    private javax.swing.JComboBox<String> rcompany;
    private javax.swing.JTextField rdfname;
    private javax.swing.JTextField rdlname;
    private javax.swing.JTextField rdmobile;
    private javax.swing.JTextField rdnic;
    private javax.swing.JPanel ref;
    private javax.swing.JPanel reff;
    private javax.swing.JButton reportstockbtn;
    private javax.swing.JPanel retu;
    private javax.swing.JPanel retucmpny;
    private javax.swing.JButton retucmpnybtn;
    private javax.swing.JPanel retustock;
    private javax.swing.JButton retustockbtn;
    private javax.swing.JPanel retuu;
    private javax.swing.JTextField rfname;
    private javax.swing.JTextField rlname;
    private javax.swing.JTextField rmobile;
    private javax.swing.JTextField rnic;
    private javax.swing.JComboBox<String> rvehicle;
    private javax.swing.JPanel searchdaily;
    private javax.swing.JButton searchdailybtn;
    private javax.swing.JPanel searchdailysum;
    private javax.swing.JButton searchdailysumbtn;
    private javax.swing.JPanel searchissue;
    private javax.swing.JButton searchissuebtn;
    private javax.swing.JPanel searchref;
    private javax.swing.JButton searchrefbtn;
    public javax.swing.JPanel searchstock;
    private javax.swing.JButton searchstockbtn;
    private javax.swing.JTextField sfname;
    private javax.swing.JTextField slname;
    private javax.swing.JTextField smobile;
    private javax.swing.JTextField snic;
    private javax.swing.JTable sreftable;
    private javax.swing.JPanel stock;
    private javax.swing.JLabel stockdate;
    public javax.swing.JPanel stockhistory;
    private javax.swing.JButton stockhistorybtn;
    private javax.swing.JPanel stockk;
    public javax.swing.JPanel stockreports;
    private javax.swing.JButton tdyissuebtn;
    private javax.swing.JLabel totalc;
    private javax.swing.JLabel totali;
    private javax.swing.JLabel totalii;
    private javax.swing.JLabel totalp;
    private javax.swing.JLabel totalpp;
    private javax.swing.JLabel totalref;
    private javax.swing.JLabel totalreff;
    private javax.swing.JPanel updateref;
    private javax.swing.JButton updaterefbtn;
    private javax.swing.JTextField updfname;
    private javax.swing.JTextField updlname;
    private javax.swing.JTextField updmobile;
    private javax.swing.JTextField updnic;
    private javax.swing.JTextField uprfname;
    private javax.swing.JTextField uprlname;
    private javax.swing.JTextField uprmobile;
    private javax.swing.JTextField uprnic;
    private javax.swing.JTable uptabel;
    private javax.swing.JPanel vehii;
    private javax.swing.JTextField with;
    // End of variables declaration//GEN-END:variables
}
