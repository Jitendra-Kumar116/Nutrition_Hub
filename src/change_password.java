
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import vmm.DBLoader;
import java.sql.*;
import javax.swing.ImageIcon;


public class change_password extends javax.swing.JFrame {

    /**
     * Creates new form change_password
     */
    public change_password() {
        initComponents();
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setSize(d.width, d.height);
        photolb.setBounds(0, 0, d.width, d.height);
        ImageIcon i1 = new ImageIcon("src/uploads/slider12.jpg");
        Image img = i1.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
        photolb.setIcon(new ImageIcon(img));
        jTextField1.setEditable(false);
        jTextField1.setText(global.email);
        setVisible(true);
        jButton1.setBackground(Color.white);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jPasswordField3 = new javax.swing.JPasswordField();
        photolb = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CHANGE PASSWORD");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(530, 160, 310, 40);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Email");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(460, 260, 130, 30);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Old Password");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(460, 320, 130, 30);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("New Password");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(460, 380, 130, 30);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Confirm New Password");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(460, 440, 130, 30);
        getContentPane().add(jTextField1);
        jTextField1.setBounds(630, 260, 210, 30);

        jButton1.setText("SUBMIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(560, 530, 190, 40);
        getContentPane().add(jPasswordField1);
        jPasswordField1.setBounds(630, 320, 210, 30);
        getContentPane().add(jPasswordField2);
        jPasswordField2.setBounds(630, 380, 210, 30);
        getContentPane().add(jPasswordField3);
        jPasswordField3.setBounds(630, 440, 210, 30);

        photolb.setText("jLabel6");
        getContentPane().add(photolb);
        photolb.setBounds(0, 0, 37, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        changePassword();
    }//GEN-LAST:event_jButton1ActionPerformed

    void changePassword() {
        String email = jTextField1.getText();
        String oldpassword = jPasswordField1.getText();
        String newpassword = jPasswordField2.getText();
        String connewpassword = jPasswordField3.getText();
        if (email.isEmpty() || oldpassword.isEmpty() || newpassword.isEmpty() || connewpassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All the fields are required");
        } else if (!newpassword.equals(connewpassword)) {
            JOptionPane.showMessageDialog(this, "New Password and Cponfirm new Password must be same");
        } else {
            try {
                ResultSet rs = DBLoader.exexuteSQL("select * from user where email ='" + email + "' ");
                if (rs.next()) {
                    String password = rs.getString("password");
                    if (password.equals(oldpassword)) {
                        rs.updateString("password", newpassword);
                        rs.updateRow();
                        JOptionPane.showMessageDialog(this, "Password Updated Successfully!!");
                        global.email="";
                        global.username="";
                        dispose();
                        user_login obj = new user_login();
                    }else{
                        JOptionPane.showMessageDialog(this, "Old Password is wrong");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No user Found with this email");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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
            java.util.logging.Logger.getLogger(change_password.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(change_password.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(change_password.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(change_password.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new change_password().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel photolb;
    // End of variables declaration//GEN-END:variables
}
