/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Gui;

import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author MSI
 */
public class cc_1 extends javax.swing.JFrame {

    /**
     * Creates new form cc
     */
    public cc_1() {
        initComponents();

        loadunbanked();
        loadbanked();
        loadhistory();
        loadunpaid();
        loadpaid();
        loadchistoryc();

        check.setVisible(true);
        credit.setVisible(false);

        checkbtn.setBackground(Color.WHITE);
        creditbtn.setBackground(new Color(0, 51, 204));

        checkbtn.setForeground(Color.BLACK);
        creditbtn.setForeground(Color.WHITE);

        unbanked.setVisible(true);
        banked.setVisible(false);
        history.setVisible(false);

        unbankedbtn.setBackground(Color.GRAY);
        bankedbtn.setBackground(new Color(0, 153, 153));
        historybtn.setBackground(new Color(0, 153, 153));

        unbankedbtn.setForeground(Color.BLACK);
        bankedbtn.setForeground(Color.WHITE);
        historybtn.setForeground(Color.WHITE);

        unbanked2.setVisible(true);
        banked2.setVisible(false);
        history2.setVisible(false);

        unbankedbtn2.setBackground(Color.GRAY);
        bankedbtn2.setBackground(new Color(0, 153, 153));
        historybtn2.setBackground(new Color(0, 153, 153));

        unbankedbtn2.setForeground(Color.BLACK);
        bankedbtn2.setForeground(Color.WHITE);
        historybtn2.setForeground(Color.WHITE);

    }

    public void loadunbanked() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `check` INNER JOIN `ref` ON `ref`.`id`=`check`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `check`.`status`='1' AND `company`.`name` LIKE '%Cycle%' ");
            DefaultTableModel dtm = (DefaultTableModel) unbankchecktable.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("check.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("amount"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("bank"));
                v.add(rs.getString("check_date"));
                v.add(rs.getString("ref.fname"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadunpaid() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `credit` INNER JOIN `ref` ON `ref`.`id`=`credit`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `credit`.`status`='1' AND `company`.`name` LIKE '%Cycle%' ");
            DefaultTableModel dtm = (DefaultTableModel) unbankchecktable6.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("credit.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("credit_amount"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("credit.paid_date"));
                v.add(rs.getString("cash"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("credit.check_date"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadpaid() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `credit` INNER JOIN `ref` ON `ref`.`id`=`credit`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `credit`.`status`='2' AND `company`.`name` LIKE '%Cycle%' ");
            DefaultTableModel dtm = (DefaultTableModel) paidtable.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("credit.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("credit_amount"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("credit.paid_date"));
                v.add(rs.getString("cash"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("credit.check_date"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadchistoryc() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `credit` INNER JOIN `ref` ON `ref`.`id`=`credit`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `company`.`name` LIKE '%Cycle%' ");
            DefaultTableModel dtm = (DefaultTableModel) historytablec.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("credit.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("credit_amount"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("credit.paid_date"));
                v.add(rs.getString("cash"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("credit.check_date"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchsitoryc() {

        try {

            String ccheckno = cnum8.getText();
            String shopp = shop8.getText();
            String bill = billno8.getText();

            Vector queryVector = new Vector();

            if (ccheckno.isEmpty()) {
                loadchistoryc();
            } else {
                queryVector.add("`credit`.`credit_amount` LIKE '%" + ccheckno + "%' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (shopp.isEmpty()) {
                loadchistoryc();
            } else {
                queryVector.add(" `credit`.`shop_name` LIKE '%" + shopp + "%' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (bill.isEmpty()) {
                loadchistoryc();
            } else {
                queryVector.add(" `credit`.`bill_no` LIKE '%" + bill + "%' AND `company`.`name` LIKE '%Cycle%' ");
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

            ResultSet rs = MySQL.search("SELECT * FROM `credit` INNER JOIN `ref` ON `ref`.`id`=`credit`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ");
            DefaultTableModel dtm = (DefaultTableModel) historytablec.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("credit.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("credit_amount"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("credit.paid_date"));
                v.add(rs.getString("cash"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("credit.check_date"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchunpaid() {

        try {

            String ccheckno = cnum6.getText();
            String shopp = shop6.getText();
            String bill = billno6.getText();

            Vector queryVector = new Vector();

            if (ccheckno.isEmpty()) {
                loadunpaid();
            } else {
                queryVector.add("`credit`.`credit_amount` LIKE '%" + ccheckno + "%' AND `credit`.`status`='1' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (shopp.isEmpty()) {
                loadunpaid();
            } else {
                queryVector.add(" `credit`.`shop_name` LIKE '%" + shopp + "%' AND `credit`.`status`='1' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (bill.isEmpty()) {
                loadunpaid();
            } else {
                queryVector.add(" `credit`.`bill_no` LIKE '%" + bill + "%' AND `credit`.`status`='1' AND `company`.`name` LIKE '%Cycle%' ");
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

            ResultSet rs = MySQL.search("SELECT * FROM `credit` INNER JOIN `ref` ON `ref`.`id`=`credit`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ");
            DefaultTableModel dtm = (DefaultTableModel) unbankchecktable6.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("credit.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("credit_amount"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("credit.paid_date"));
                v.add(rs.getString("cash"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("credit.check_date"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchpaid() {

        try {

            String ccheckno = cnum7.getText();
            String shopp = shop7.getText();
            String bill = billno7.getText();

            Vector queryVector = new Vector();

            if (ccheckno.isEmpty()) {
                loadpaid();
            } else {
                queryVector.add("`credit`.`credit_amount` LIKE '%" + ccheckno + "%' AND `credit`.`status`='2' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (shopp.isEmpty()) {
                loadpaid();
            } else {
                queryVector.add(" `credit`.`shop_name` LIKE '%" + shopp + "%' AND `credit`.`status`='2' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (bill.isEmpty()) {
                loadpaid();
            } else {
                queryVector.add(" `credit`.`bill_no` LIKE '%" + bill + "%' AND `credit`.`status`='2' AND `company`.`name` LIKE '%Cycle%' ");
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

            ResultSet rs = MySQL.search("SELECT * FROM `credit` INNER JOIN `ref` ON `ref`.`id`=`credit`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ");
            DefaultTableModel dtm = (DefaultTableModel) paidtable.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("credit.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("credit_amount"));
                v.add(rs.getString("ref.fname"));
                v.add(rs.getString("credit.paid_date"));
                v.add(rs.getString("cash"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("credit.check_date"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadbanked() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `check` INNER JOIN `ref` ON `ref`.`id`=`check`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `check`.`status`='2' AND `company`.`name` LIKE '%Cycle%' ");
            DefaultTableModel dtm = (DefaultTableModel) unbankchecktable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("check.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("amount"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("bank"));
                v.add(rs.getString("check_date"));
                v.add(rs.getString("ref.fname"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadhistory() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `check` INNER JOIN `ref` ON `ref`.`id`=`check`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` WHERE `company`.`name` LIKE '%Cycle%' ");
            DefaultTableModel dtm = (DefaultTableModel) unbankchecktable2.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("check.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("amount"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("bank"));
                v.add(rs.getString("check_date"));
                v.add(rs.getString("ref.fname"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchunbanked() {

        try {

            String ccheckno = cnum.getText();
            String shopp = shop.getText();
            String bill = billno.getText();

            Vector queryVector = new Vector();

            if (ccheckno.isEmpty()) {
                loadunbanked();
            } else {
                queryVector.add("`check`.`check_no` LIKE '%" + ccheckno + "%' AND `check`.`status`='1' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (shopp.isEmpty()) {
                loadunbanked();
            } else {
                queryVector.add(" `check`.`shop_name` LIKE '%" + shopp + "%' AND `check`.`status`='1' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (bill.isEmpty()) {
                loadunbanked();
            } else {
                queryVector.add(" `check`.`bill_no` LIKE '%" + bill + "%' AND `check`.`status`='1' AND `company`.`name` LIKE '%Cycle%' ");
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

            ResultSet rs = MySQL.search("SELECT * FROM `check` INNER JOIN `ref` ON `ref`.`id`=`check`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ");
            DefaultTableModel dtm = (DefaultTableModel) unbankchecktable.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("check.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("amount"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("bank"));
                v.add(rs.getString("check_date"));
                v.add(rs.getString("ref.fname"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchhistory() {

        try {

            String ccheckno = cnum2.getText();
            String shopp = shop2.getText();
            String bill = billno2.getText();

            Vector queryVector = new Vector();

            if (ccheckno.isEmpty()) {
                loadhistory();
            } else {
                queryVector.add("`check`.`check_no` LIKE '%" + ccheckno + "%' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (shopp.isEmpty()) {
                loadhistory();
            } else {
                queryVector.add(" `check`.`shop_name` LIKE '%" + shopp + "%' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (bill.isEmpty()) {
                loadhistory();
            } else {
                queryVector.add(" `check`.`bill_no` LIKE '%" + bill + "%' AND `company`.`name` LIKE '%Cycle%' ");
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

            ResultSet rs = MySQL.search("SELECT * FROM `check` INNER JOIN `ref` ON `ref`.`id`=`check`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ");
            DefaultTableModel dtm = (DefaultTableModel) unbankchecktable2.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("check.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("amount"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("bank"));
                v.add(rs.getString("check_date"));
                v.add(rs.getString("ref.fname"));

                dtm.addRow(v);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchbanked() {

        try {

            String ccheckno = cnum1.getText();
            String shopp = shop1.getText();
            String bill = billno1.getText();

            Vector queryVector = new Vector();

            if (ccheckno.isEmpty()) {
                loadbanked();
            } else {
                queryVector.add("`check`.`check_no` LIKE '%" + ccheckno + "%' AND `check`.`status`='2' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (shopp.isEmpty()) {
                loadbanked();
            } else {
                queryVector.add(" `check`.`shop_name` LIKE '%" + shopp + "%' AND `check`.`status`='2' AND `company`.`name` LIKE '%Cycle%' ");
            }

            if (bill.isEmpty()) {
                loadbanked();
            } else {
                queryVector.add(" `check`.`bill_no` LIKE '%" + bill + "%' AND `check`.`status`='2' AND `company`.`name` LIKE '%Cycle%' ");
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

            ResultSet rs = MySQL.search("SELECT * FROM `check` INNER JOIN `ref` ON `ref`.`id`=`check`.`ref` INNER JOIN `company` ON `company`.`id`=`ref`.`company` " + wherequery + " ");
            DefaultTableModel dtm = (DefaultTableModel) unbankchecktable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("check.date"));
                v.add(rs.getString("shop_name"));
                v.add(rs.getString("bill_no"));
                v.add(rs.getString("amount"));
                v.add(rs.getString("check_no"));
                v.add(rs.getString("check_amount"));
                v.add(rs.getString("bank"));
                v.add(rs.getString("check_date"));
                v.add(rs.getString("ref.fname"));

                dtm.addRow(v);

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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        creditbtn = new javax.swing.JButton();
        checkbtn = new javax.swing.JButton();
        check = new javax.swing.JPanel();
        unbankedbtn = new javax.swing.JButton();
        bankedbtn = new javax.swing.JButton();
        historybtn = new javax.swing.JButton();
        unbanked = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cnum = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        shop = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        billno = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        unbankchecktable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        banked = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cnum1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        shop1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        billno1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        unbankchecktable1 = new javax.swing.JTable();
        history = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cnum2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        unbankchecktable2 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        shop2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        billno2 = new javax.swing.JTextField();
        credit = new javax.swing.JPanel();
        unbankedbtn2 = new javax.swing.JButton();
        bankedbtn2 = new javax.swing.JButton();
        historybtn2 = new javax.swing.JButton();
        unbanked2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cnum6 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        shop6 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        billno6 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        unbankchecktable6 = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        cnum9 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        cnum10 = new javax.swing.JTextField();
        pdate = new com.toedter.calendar.JDateChooser();
        jLabel44 = new javax.swing.JLabel();
        cnum11 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        billdate1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        banked2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        cnum7 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        shop7 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        billno7 = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        paidtable = new javax.swing.JTable();
        history2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        cnum8 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        shop8 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        billno8 = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        historytablec = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(228, 251, 251));

        jPanel2.setBackground(new java.awt.Color(0, 0, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cheque And Credit Detailes");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        creditbtn.setBackground(new java.awt.Color(0, 51, 204));
        creditbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        creditbtn.setForeground(new java.awt.Color(255, 255, 255));
        creditbtn.setText("Serach & View Credit");
        creditbtn.setBorderPainted(false);
        creditbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditbtnActionPerformed(evt);
            }
        });

        checkbtn.setBackground(new java.awt.Color(0, 51, 204));
        checkbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkbtn.setForeground(new java.awt.Color(255, 255, 255));
        checkbtn.setText("Serach & View Cheque");
        checkbtn.setBorderPainted(false);
        checkbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbtnActionPerformed(evt);
            }
        });

        check.setBackground(new java.awt.Color(238, 238, 253));

        unbankedbtn.setBackground(new java.awt.Color(0, 153, 153));
        unbankedbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        unbankedbtn.setForeground(new java.awt.Color(255, 255, 255));
        unbankedbtn.setText("Unbanked Cheques");
        unbankedbtn.setBorderPainted(false);
        unbankedbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unbankedbtnActionPerformed(evt);
            }
        });

        bankedbtn.setBackground(new java.awt.Color(0, 153, 153));
        bankedbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bankedbtn.setForeground(new java.awt.Color(255, 255, 255));
        bankedbtn.setText("Banked Cheques");
        bankedbtn.setBorderPainted(false);
        bankedbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bankedbtnActionPerformed(evt);
            }
        });

        historybtn.setBackground(new java.awt.Color(0, 153, 153));
        historybtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        historybtn.setForeground(new java.awt.Color(255, 255, 255));
        historybtn.setText("History of Cheques");
        historybtn.setBorderPainted(false);
        historybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historybtnActionPerformed(evt);
            }
        });

        unbanked.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 153));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Unbanked Cheques");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Cheque Number :-");

        cnum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnumKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Shop Name :-");

        shop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                shopKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Bill Number :-");

        billno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billnoKeyReleased(evt);
            }
        });

        unbankchecktable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "date", "shop", "bill", "amount", "cheque_no", "check amount", "bank", "check date", "ref"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        unbankchecktable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unbankchecktableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(unbankchecktable);
        if (unbankchecktable.getColumnModel().getColumnCount() > 0) {
            unbankchecktable.getColumnModel().getColumn(0).setResizable(false);
            unbankchecktable.getColumnModel().getColumn(1).setResizable(false);
            unbankchecktable.getColumnModel().getColumn(2).setResizable(false);
            unbankchecktable.getColumnModel().getColumn(3).setResizable(false);
            unbankchecktable.getColumnModel().getColumn(4).setResizable(false);
            unbankchecktable.getColumnModel().getColumn(5).setResizable(false);
            unbankchecktable.getColumnModel().getColumn(6).setResizable(false);
            unbankchecktable.getColumnModel().getColumn(7).setResizable(false);
            unbankchecktable.getColumnModel().getColumn(8).setHeaderValue("check amount");
            unbankchecktable.getColumnModel().getColumn(9).setHeaderValue("check date");
        }

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Do you want Deposit Cheque , Double clicked on the table row  and its automatically moved banked cheques table");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout unbankedLayout = new javax.swing.GroupLayout(unbanked);
        unbanked.setLayout(unbankedLayout);
        unbankedLayout.setHorizontalGroup(
            unbankedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(unbankedLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(unbankedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(unbankedLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cnum, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shop, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(billno, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        unbankedLayout.setVerticalGroup(
            unbankedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unbankedLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(unbankedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shop, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cnum)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billno, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        unbankedLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cnum, jLabel5, jLabel6, shop});

        banked.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 0, 153));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Banked Cheques");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Cheque Number :-");

        cnum1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnum1KeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Shop Name :-");

        shop1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                shop1KeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Bill Number :-");

        billno1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billno1KeyReleased(evt);
            }
        });

        unbankchecktable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "date", "shop", "bill", "amount", "cheque_no", "check amount", "bank", "check date", "ref"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        unbankchecktable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unbankchecktable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(unbankchecktable1);
        if (unbankchecktable1.getColumnModel().getColumnCount() > 0) {
            unbankchecktable1.getColumnModel().getColumn(0).setResizable(false);
            unbankchecktable1.getColumnModel().getColumn(1).setResizable(false);
            unbankchecktable1.getColumnModel().getColumn(2).setResizable(false);
            unbankchecktable1.getColumnModel().getColumn(3).setResizable(false);
            unbankchecktable1.getColumnModel().getColumn(4).setResizable(false);
            unbankchecktable1.getColumnModel().getColumn(5).setResizable(false);
            unbankchecktable1.getColumnModel().getColumn(6).setResizable(false);
            unbankchecktable1.getColumnModel().getColumn(7).setResizable(false);
            unbankchecktable1.getColumnModel().getColumn(8).setHeaderValue("check amount");
            unbankchecktable1.getColumnModel().getColumn(9).setHeaderValue("check date");
        }

        javax.swing.GroupLayout bankedLayout = new javax.swing.GroupLayout(banked);
        banked.setLayout(bankedLayout);
        bankedLayout.setHorizontalGroup(
            bankedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(bankedLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(bankedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(bankedLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cnum1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shop1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(billno1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        bankedLayout.setVerticalGroup(
            bankedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankedLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(bankedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shop1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cnum1)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billno1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        history.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(0, 0, 153));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("History of Cheques");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Cheque Number :-");

        cnum2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnum2KeyReleased(evt);
            }
        });

        unbankchecktable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "date", "shop", "bill", "amount", "cheque_no", "check amount", "bank", "check date", "ref"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        unbankchecktable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unbankchecktable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(unbankchecktable2);
        if (unbankchecktable2.getColumnModel().getColumnCount() > 0) {
            unbankchecktable2.getColumnModel().getColumn(0).setResizable(false);
            unbankchecktable2.getColumnModel().getColumn(1).setResizable(false);
            unbankchecktable2.getColumnModel().getColumn(2).setResizable(false);
            unbankchecktable2.getColumnModel().getColumn(3).setResizable(false);
            unbankchecktable2.getColumnModel().getColumn(4).setResizable(false);
            unbankchecktable2.getColumnModel().getColumn(5).setResizable(false);
            unbankchecktable2.getColumnModel().getColumn(6).setResizable(false);
            unbankchecktable2.getColumnModel().getColumn(7).setResizable(false);
            unbankchecktable2.getColumnModel().getColumn(8).setHeaderValue("check amount");
            unbankchecktable2.getColumnModel().getColumn(9).setHeaderValue("check date");
        }

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Shop Name :-");

        shop2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                shop2KeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Bill Number :-");

        billno2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billno2KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout historyLayout = new javax.swing.GroupLayout(history);
        history.setLayout(historyLayout);
        historyLayout.setHorizontalGroup(
            historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(historyLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(historyLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cnum2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shop2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(billno2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        historyLayout.setVerticalGroup(
            historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shop2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cnum2)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billno2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout checkLayout = new javax.swing.GroupLayout(check);
        check.setLayout(checkLayout);
        checkLayout.setHorizontalGroup(
            checkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(unbankedbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(64, 64, 64)
                .addComponent(bankedbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(62, 62, 62)
                .addComponent(historybtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(81, 81, 81))
            .addGroup(checkLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(unbanked, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(checkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(checkLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(banked, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(checkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(checkLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(history, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        checkLayout.setVerticalGroup(
            checkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(checkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unbankedbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bankedbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(historybtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(unbanked, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(checkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(checkLayout.createSequentialGroup()
                    .addGap(65, 65, 65)
                    .addComponent(banked, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(checkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(checkLayout.createSequentialGroup()
                    .addGap(66, 66, 66)
                    .addComponent(history, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        credit.setBackground(new java.awt.Color(238, 238, 253));

        unbankedbtn2.setBackground(new java.awt.Color(0, 153, 153));
        unbankedbtn2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        unbankedbtn2.setForeground(new java.awt.Color(255, 255, 255));
        unbankedbtn2.setText("Unpaid Credit");
        unbankedbtn2.setBorderPainted(false);
        unbankedbtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unbankedbtn2ActionPerformed(evt);
            }
        });

        bankedbtn2.setBackground(new java.awt.Color(0, 153, 153));
        bankedbtn2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bankedbtn2.setForeground(new java.awt.Color(255, 255, 255));
        bankedbtn2.setText("Paid Credit");
        bankedbtn2.setBorderPainted(false);
        bankedbtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bankedbtn2ActionPerformed(evt);
            }
        });

        historybtn2.setBackground(new java.awt.Color(0, 153, 153));
        historybtn2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        historybtn2.setForeground(new java.awt.Color(255, 255, 255));
        historybtn2.setText("History of Credit");
        historybtn2.setBorderPainted(false);
        historybtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historybtn2ActionPerformed(evt);
            }
        });

        unbanked2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(0, 0, 153));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Unpaid Credit");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setText("credit amount :-");

        cnum6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnum6KeyReleased(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setText("Shop Name :-");

        shop6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                shop6KeyReleased(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("Bill Number :-");

        billno6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billno6KeyReleased(evt);
            }
        });

        unbankchecktable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "date", "shop", "bill", "amount", "Ref", "paid date", "cash", "check number", "check amount", "check date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        unbankchecktable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unbankchecktable6MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(unbankchecktable6);
        if (unbankchecktable6.getColumnModel().getColumnCount() > 0) {
            unbankchecktable6.getColumnModel().getColumn(0).setResizable(false);
            unbankchecktable6.getColumnModel().getColumn(1).setResizable(false);
            unbankchecktable6.getColumnModel().getColumn(2).setResizable(false);
            unbankchecktable6.getColumnModel().getColumn(3).setResizable(false);
            unbankchecktable6.getColumnModel().getColumn(4).setResizable(false);
            unbankchecktable6.getColumnModel().getColumn(5).setResizable(false);
            unbankchecktable6.getColumnModel().getColumn(6).setResizable(false);
            unbankchecktable6.getColumnModel().getColumn(7).setResizable(false);
            unbankchecktable6.getColumnModel().getColumn(8).setResizable(false);
            unbankchecktable6.getColumnModel().getColumn(9).setHeaderValue("check amount");
            unbankchecktable6.getColumnModel().getColumn(10).setHeaderValue("check date");
        }

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 153, 0));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Do you want update credit , Double clicked on the table row ");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)), "Paid Detailes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setText("Id :-");

        cnum9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnum9KeyReleased(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel42.setText("Cash :-");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setText("Cheque No :-");

        cnum10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnum10KeyReleased(evt);
            }
        });

        pdate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                pdatePropertyChange(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setText("Cheque amount:-");

        cnum11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnum11KeyReleased(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setText("Check Date :-");

        billdate1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                billdate1PropertyChange(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 153, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Paid Credit");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setText("Paid date :-");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 153, 153));
        jLabel15.setText("None");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cnum10, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cnum11)
                            .addComponent(pdate, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(billdate1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                            .addComponent(cnum9)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(pdate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(cnum9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(billdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(cnum11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(cnum10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cnum10, cnum9, jLabel15, jLabel41, jLabel42, jLabel43, jLabel46, pdate});

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {billdate1, cnum11, jLabel44, jLabel45});

        javax.swing.GroupLayout unbanked2Layout = new javax.swing.GroupLayout(unbanked2);
        unbanked2.setLayout(unbanked2Layout);
        unbanked2Layout.setHorizontalGroup(
            unbanked2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, unbanked2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(unbanked2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, unbanked2Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cnum6, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shop6, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(billno6, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        unbanked2Layout.setVerticalGroup(
            unbanked2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unbanked2Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(unbanked2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(cnum6)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(shop6)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billno6))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        banked2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(0, 0, 153));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Paid Credit");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setText("Cheque Number :-");

        cnum7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnum7KeyReleased(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setText("Shop Name :-");

        shop7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                shop7KeyReleased(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel36.setText("Bill Number :-");

        billno7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billno7KeyReleased(evt);
            }
        });

        paidtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "date", "shop", "bill", "amount", "Ref", "paid date", "cash", "check number", "c.amount", "Check date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        paidtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paidtableMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(paidtable);
        if (paidtable.getColumnModel().getColumnCount() > 0) {
            paidtable.getColumnModel().getColumn(0).setResizable(false);
            paidtable.getColumnModel().getColumn(1).setResizable(false);
            paidtable.getColumnModel().getColumn(2).setResizable(false);
            paidtable.getColumnModel().getColumn(3).setResizable(false);
            paidtable.getColumnModel().getColumn(4).setResizable(false);
            paidtable.getColumnModel().getColumn(5).setResizable(false);
            paidtable.getColumnModel().getColumn(6).setResizable(false);
            paidtable.getColumnModel().getColumn(7).setResizable(false);
            paidtable.getColumnModel().getColumn(8).setResizable(false);
            paidtable.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout banked2Layout = new javax.swing.GroupLayout(banked2);
        banked2.setLayout(banked2Layout);
        banked2Layout.setHorizontalGroup(
            banked2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(banked2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cnum7, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shop7, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billno7, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(banked2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(banked2Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 958, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(29, Short.MAX_VALUE)))
        );
        banked2Layout.setVerticalGroup(
            banked2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(banked2Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(banked2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shop7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cnum7)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billno7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(446, 446, 446))
            .addGroup(banked2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(banked2Layout.createSequentialGroup()
                    .addGap(111, 111, 111)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(22, Short.MAX_VALUE)))
        );

        history2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(0, 0, 153));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("History of Credits");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setText("Credit amount :-");

        cnum8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnum8KeyReleased(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel39.setText("Shop Name :-");

        shop8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                shop8KeyReleased(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel40.setText("Bill Number :-");

        billno8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billno8KeyReleased(evt);
            }
        });

        historytablec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "date", "shop", "bill", "amount", "Ref", "paid date", "cash", "check number", "c.amount", "Check date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        historytablec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                historytablecMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(historytablec);
        if (historytablec.getColumnModel().getColumnCount() > 0) {
            historytablec.getColumnModel().getColumn(0).setResizable(false);
            historytablec.getColumnModel().getColumn(1).setResizable(false);
            historytablec.getColumnModel().getColumn(2).setResizable(false);
            historytablec.getColumnModel().getColumn(3).setResizable(false);
            historytablec.getColumnModel().getColumn(4).setResizable(false);
            historytablec.getColumnModel().getColumn(5).setResizable(false);
            historytablec.getColumnModel().getColumn(6).setResizable(false);
            historytablec.getColumnModel().getColumn(7).setResizable(false);
            historytablec.getColumnModel().getColumn(8).setResizable(false);
            historytablec.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout history2Layout = new javax.swing.GroupLayout(history2);
        history2.setLayout(history2Layout);
        history2Layout.setHorizontalGroup(
            history2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(history2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(history2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 958, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(history2Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cnum8, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shop8, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(billno8, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        history2Layout.setVerticalGroup(
            history2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(history2Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(history2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shop8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cnum8)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billno8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout creditLayout = new javax.swing.GroupLayout(credit);
        credit.setLayout(creditLayout);
        creditLayout.setHorizontalGroup(
            creditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creditLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(unbankedbtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(64, 64, 64)
                .addComponent(bankedbtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(62, 62, 62)
                .addComponent(historybtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(81, 81, 81))
            .addGroup(creditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(unbanked2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(creditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(creditLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(banked2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(creditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(creditLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(history2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        creditLayout.setVerticalGroup(
            creditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creditLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(creditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unbankedbtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bankedbtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(historybtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(unbanked2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(creditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(creditLayout.createSequentialGroup()
                    .addGap(65, 65, 65)
                    .addComponent(banked2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(creditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(creditLayout.createSequentialGroup()
                    .addGap(66, 66, 66)
                    .addComponent(history2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(checkbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(80, 80, 80)
                .addComponent(creditbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(148, 148, 148))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(check, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(credit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(creditbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(check, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(114, 114, 114)
                    .addComponent(credit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(20, 20, 20)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void checkbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbtnActionPerformed
        // TODO add your handling code here:

        check.setVisible(true);
        credit.setVisible(false);

        checkbtn.setBackground(Color.WHITE);
        creditbtn.setBackground(new Color(0, 51, 204));

        checkbtn.setForeground(Color.BLACK);
        creditbtn.setForeground(Color.WHITE);

        unbanked.setVisible(true);
        banked.setVisible(false);
        history.setVisible(false);

        unbankedbtn.setBackground(Color.GRAY);
        bankedbtn.setBackground(new Color(0, 153, 153));
        historybtn.setBackground(new Color(0, 153, 153));

        unbankedbtn.setForeground(Color.BLACK);
        bankedbtn.setForeground(Color.WHITE);
        historybtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_checkbtnActionPerformed

    private void creditbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditbtnActionPerformed
        // TODO add your handling code here:

        loadunpaid();
        loadpaid();

        credit.setVisible(true);
        check.setVisible(false);

        creditbtn.setBackground(Color.WHITE);
        checkbtn.setBackground(new Color(0, 51, 204));

        creditbtn.setForeground(Color.BLACK);
        checkbtn.setForeground(Color.WHITE);

        unbanked2.setVisible(true);
        banked2.setVisible(false);
        history2.setVisible(false);

        unbankedbtn2.setBackground(Color.GRAY);
        bankedbtn2.setBackground(new Color(0, 153, 153));
        historybtn2.setBackground(new Color(0, 153, 153));

        unbankedbtn2.setForeground(Color.BLACK);
        bankedbtn2.setForeground(Color.WHITE);
        historybtn2.setForeground(Color.WHITE);

    }//GEN-LAST:event_creditbtnActionPerformed

    private void unbankedbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unbankedbtnActionPerformed
        // TODO add your handling code here:

        unbanked.setVisible(true);
        banked.setVisible(false);
        history.setVisible(false);

        unbankedbtn.setBackground(Color.GRAY);
        bankedbtn.setBackground(new Color(0, 153, 153));
        historybtn.setBackground(new Color(0, 153, 153));

        unbankedbtn.setForeground(Color.BLACK);
        bankedbtn.setForeground(Color.WHITE);
        historybtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_unbankedbtnActionPerformed

    private void bankedbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bankedbtnActionPerformed
        // TODO add your handling code here:

        banked.setVisible(true);
        unbanked.setVisible(false);
        history.setVisible(false);

        bankedbtn.setBackground(Color.GRAY);
        unbankedbtn.setBackground(new Color(0, 153, 153));
        historybtn.setBackground(new Color(0, 153, 153));

        bankedbtn.setForeground(Color.BLACK);
        unbankedbtn.setForeground(Color.WHITE);
        historybtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_bankedbtnActionPerformed

    private void historybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historybtnActionPerformed
        // TODO add your handling code here:

        history.setVisible(true);
        banked.setVisible(false);
        unbanked.setVisible(false);

        historybtn.setBackground(Color.GRAY);
        bankedbtn.setBackground(new Color(0, 153, 153));
        unbankedbtn.setBackground(new Color(0, 153, 153));

        historybtn.setForeground(Color.BLACK);
        bankedbtn.setForeground(Color.WHITE);
        unbankedbtn.setForeground(Color.WHITE);

    }//GEN-LAST:event_historybtnActionPerformed

    private void cnumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnumKeyReleased
        // TODO add your handling code here:
        searchunbanked();
    }//GEN-LAST:event_cnumKeyReleased

    private void shopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shopKeyReleased
        // TODO add your handling code here:
        searchunbanked();
    }//GEN-LAST:event_shopKeyReleased

    private void billnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billnoKeyReleased
        // TODO add your handling code here:    
        searchunbanked();
    }//GEN-LAST:event_billnoKeyReleased

    private void unbankchecktableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unbankchecktableMouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {

            int r = unbankchecktable.getSelectedRow();

            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Please Select a cheque", "warning", JOptionPane.WARNING_MESSAGE);
            } else {

                String id = unbankchecktable.getValueAt(r, 0).toString();

                int jd = JOptionPane.showOptionDialog(this, "Do you want update this cheque", "warnning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);

                if (jd == JOptionPane.YES_OPTION) {

                    MySQL.iud("UPDATE `check` SET `status`='2' WHERE `id`='" + id + "' ");

                    loadunbanked();
                    loadbanked();

                }

            }
        }

    }//GEN-LAST:event_unbankchecktableMouseClicked

    private void cnum1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnum1KeyReleased
        // TODO add your handling code here:
        searchbanked();
    }//GEN-LAST:event_cnum1KeyReleased

    private void shop1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shop1KeyReleased
        // TODO add your handling code here:
        searchbanked();
    }//GEN-LAST:event_shop1KeyReleased

    private void billno1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billno1KeyReleased
        // TODO add your handling code here:
        searchbanked();
    }//GEN-LAST:event_billno1KeyReleased

    private void unbankchecktable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unbankchecktable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_unbankchecktable1MouseClicked

    private void cnum2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnum2KeyReleased
        // TODO add your handling code here:
        searchhistory();
    }//GEN-LAST:event_cnum2KeyReleased

    private void unbankchecktable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unbankchecktable2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_unbankchecktable2MouseClicked

    private void shop2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shop2KeyReleased
        // TODO add your handling code here:
        searchhistory();
    }//GEN-LAST:event_shop2KeyReleased

    private void billno2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billno2KeyReleased
        // TODO add your handling code here:
        searchhistory();
    }//GEN-LAST:event_billno2KeyReleased

    private void unbankedbtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unbankedbtn2ActionPerformed
        // TODO add your handling code here:
        unbanked2.setVisible(true);
        banked2.setVisible(false);
        history2.setVisible(false);

        unbankedbtn2.setBackground(Color.GRAY);
        bankedbtn2.setBackground(new Color(0, 153, 153));
        historybtn2.setBackground(new Color(0, 153, 153));

        unbankedbtn2.setForeground(Color.BLACK);
        bankedbtn2.setForeground(Color.WHITE);
        historybtn2.setForeground(Color.WHITE);
    }//GEN-LAST:event_unbankedbtn2ActionPerformed

    private void bankedbtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bankedbtn2ActionPerformed
        // TODO add your handling code here:
        banked2.setVisible(true);
        unbanked2.setVisible(false);
        history2.setVisible(false);

        bankedbtn2.setBackground(Color.GRAY);
        unbankedbtn2.setBackground(new Color(0, 153, 153));
        historybtn2.setBackground(new Color(0, 153, 153));

        bankedbtn2.setForeground(Color.BLACK);
        unbankedbtn2.setForeground(Color.WHITE);
        historybtn2.setForeground(Color.WHITE);
    }//GEN-LAST:event_bankedbtn2ActionPerformed

    private void historybtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historybtn2ActionPerformed
        // TODO add your handling code here:

        history2.setVisible(true);
        unbanked2.setVisible(false);
        banked2.setVisible(false);

        historybtn2.setBackground(Color.GRAY);
        unbankedbtn2.setBackground(new Color(0, 153, 153));
        bankedbtn2.setBackground(new Color(0, 153, 153));

        historybtn2.setForeground(Color.BLACK);
        unbankedbtn2.setForeground(Color.WHITE);
        bankedbtn2.setForeground(Color.WHITE);

    }//GEN-LAST:event_historybtn2ActionPerformed

    private void cnum6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnum6KeyReleased
        // TODO add your handling code here:
        searchunpaid();
    }//GEN-LAST:event_cnum6KeyReleased

    private void shop6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shop6KeyReleased
        // TODO add your handling code here:
        searchunpaid();
    }//GEN-LAST:event_shop6KeyReleased

    private void billno6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billno6KeyReleased
        // TODO add your handling code here:
        searchunpaid();
    }//GEN-LAST:event_billno6KeyReleased

    private void unbankchecktable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unbankchecktable6MouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {

            int r = unbankchecktable6.getSelectedRow();

            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Please Select a credit", "warning", JOptionPane.WARNING_MESSAGE);
            } else {

                String id = unbankchecktable6.getValueAt(r, 0).toString();

                int jd = JOptionPane.showOptionDialog(this, "Do you want update this credit", "warnning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, JOptionPane.YES_OPTION);

                if (jd == JOptionPane.YES_OPTION) {

                    jLabel15.setText(id);

                }

            }
        }

    }//GEN-LAST:event_unbankchecktable6MouseClicked

    private void cnum7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnum7KeyReleased
        // TODO add your handling code here:
        searchpaid();
    }//GEN-LAST:event_cnum7KeyReleased

    private void shop7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shop7KeyReleased
        // TODO add your handling code here:
        searchpaid();
    }//GEN-LAST:event_shop7KeyReleased

    private void billno7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billno7KeyReleased
        // TODO add your handling code here:
        searchpaid();
    }//GEN-LAST:event_billno7KeyReleased

    private void cnum8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnum8KeyReleased
        // TODO add your handling code here:
        searchsitoryc();
    }//GEN-LAST:event_cnum8KeyReleased

    private void shop8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shop8KeyReleased
        // TODO add your handling code here:
        searchsitoryc();
    }//GEN-LAST:event_shop8KeyReleased

    private void billno8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billno8KeyReleased
        // TODO add your handling code here:
        searchsitoryc();
    }//GEN-LAST:event_billno8KeyReleased

    private void cnum9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnum9KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cnum9KeyReleased

    private void cnum10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnum10KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cnum10KeyReleased

    private void pdatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_pdatePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_pdatePropertyChange

    private void cnum11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnum11KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cnum11KeyReleased

    private void billdate1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_billdate1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_billdate1PropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String cid = jLabel15.getText();
        Date date = pdate.getDate();

        if (cid.equals("None")) {
            JOptionPane.showMessageDialog(this, "Please select a table row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (date == null) {
            JOptionPane.showMessageDialog(this, "Please select a paid date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ppdate = sdf.format(date);

            String cash = cnum9.getText();
            String cnum = cnum10.getText();
            String amount = cnum11.getText();
            Date cddate = billdate1.getDate();
            String cdate = sdf.format(cddate);

            MySQL.iud(" UPDATE `credit` SET `paid_date`='" + ppdate + "',`cash`='" + cash + "',`check_no`='" + cnum + "',`check_amount`='" + amount + "',`check_date`='" + cdate + "',`status`='2' WHERE `id`='" + cid + "' ");

            JOptionPane.showMessageDialog(this, "Credit paid detailes updated", "Suucess", JOptionPane.INFORMATION_MESSAGE);

            jLabel15.setText("None");
            pdate.setDate(null);
            billdate1.setDate(null);
            cnum9.setText("");
            cnum10.setText("");
            cnum11.setText("");

            loadunpaid();
            loadpaid();

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void paidtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paidtableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_paidtableMouseClicked

    private void historytablecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historytablecMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_historytablecMouseClicked

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
            java.util.logging.Logger.getLogger(cc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel banked;
    private javax.swing.JPanel banked2;
    private javax.swing.JButton bankedbtn;
    private javax.swing.JButton bankedbtn2;
    private com.toedter.calendar.JDateChooser billdate1;
    private javax.swing.JTextField billno;
    private javax.swing.JTextField billno1;
    private javax.swing.JTextField billno2;
    private javax.swing.JTextField billno6;
    private javax.swing.JTextField billno7;
    private javax.swing.JTextField billno8;
    private javax.swing.JPanel check;
    private javax.swing.JButton checkbtn;
    private javax.swing.JTextField cnum;
    private javax.swing.JTextField cnum1;
    private javax.swing.JTextField cnum10;
    private javax.swing.JTextField cnum11;
    private javax.swing.JTextField cnum2;
    private javax.swing.JTextField cnum6;
    private javax.swing.JTextField cnum7;
    private javax.swing.JTextField cnum8;
    private javax.swing.JTextField cnum9;
    private javax.swing.JPanel credit;
    private javax.swing.JButton creditbtn;
    private javax.swing.JPanel history;
    private javax.swing.JPanel history2;
    private javax.swing.JButton historybtn;
    private javax.swing.JButton historybtn2;
    private javax.swing.JTable historytablec;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable paidtable;
    private com.toedter.calendar.JDateChooser pdate;
    private javax.swing.JTextField shop;
    private javax.swing.JTextField shop1;
    private javax.swing.JTextField shop2;
    private javax.swing.JTextField shop6;
    private javax.swing.JTextField shop7;
    private javax.swing.JTextField shop8;
    private javax.swing.JTable unbankchecktable;
    private javax.swing.JTable unbankchecktable1;
    private javax.swing.JTable unbankchecktable2;
    private javax.swing.JTable unbankchecktable6;
    private javax.swing.JPanel unbanked;
    private javax.swing.JPanel unbanked2;
    private javax.swing.JButton unbankedbtn;
    private javax.swing.JButton unbankedbtn2;
    // End of variables declaration//GEN-END:variables
}
