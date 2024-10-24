
import java.sql.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import vmm.DBLoader;


public class edit_profile extends javax.swing.JFrame {

    File f;
    JFileChooser jfc;
    String path = "";

    public edit_profile() {
        initComponents();
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setSize(d.width, d.height);
        photolb1.setBounds(0, 0, d.width, d.height);
        ImageIcon i1 = new ImageIcon("src/uploads/slider12.jpg");
        Image img = i1.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
        photolb1.setIcon(new ImageIcon(img));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jusername.setText(global.username);
        jemail.setEditable(false);
        jButton1.setBackground(Color.white);
        jButton2.setBackground(Color.white);
        setTitle("Edit Profile");
        setVisible(true);
        userData();
    }

    void userData() {
        try {
            ResultSet rs = DBLoader.exexuteSQL("select * from user where email='" + global.email + "' ");
            if (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String photo = rs.getString("photo");
                String mobile_no = rs.getString("mobile_no");
                jmobile.setText(mobile_no);
                jusername.setText(username);
                jemail.setText(email);
                ImageIcon i1 = new ImageIcon(photo);
                Image img = i1.getImage().getScaledInstance(photolb.getWidth(), photolb.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(img);
                photolb.setIcon(icon);

            } else {
                JOptionPane.showMessageDialog(this, "No user Found!!");
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jusername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jemail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jmobile = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        photolb = new javax.swing.JLabel();
        photolb1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("EDIT PROFILE");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(600, 130, 200, 50);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Username");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(510, 290, 130, 16);
        getContentPane().add(jusername);
        jusername.setBounds(720, 290, 150, 22);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Email");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(510, 340, 130, 16);
        getContentPane().add(jemail);
        jemail.setBounds(720, 340, 150, 22);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Mobile No");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(510, 400, 130, 16);
        getContentPane().add(jmobile);
        jmobile.setBounds(720, 400, 150, 22);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Photo");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(510, 460, 130, 16);

        jButton1.setText("Browse..");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(720, 460, 150, 22);

        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(590, 540, 140, 40);

        photolb.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(photolb);
        photolb.setBounds(630, 200, 100, 50);

        photolb1.setText("jLabel6");
        getContentPane().add(photolb1);
        photolb1.setBounds(360, 90, 37, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String username = jusername.getText();
        String email = jemail.getText();
        String mobile_no = jmobile.getText();
        if (username.isEmpty() || email.isEmpty() || mobile_no.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All the fields are required");
        } else if (mobile_no.length() != 10) {
            JOptionPane.showMessageDialog(this, "Please enter valid phone number");
        } else if (f == null) {
            try {
                ResultSet rs = DBLoader.exexuteSQL("select * from user where email ='" + email + "'");
                if (rs.next()) {
                    rs.updateString("username", username);
                    rs.updateString("mobile_no", mobile_no);
                    rs.updateRow();
                    JOptionPane.showMessageDialog(this, "Profile updated Successfully!!");
                    userData();
                } else {
                    JOptionPane.showMessageDialog(this, "No User Found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String pic = savefile.savefile(path);
            try {
                ResultSet rs = DBLoader.exexuteSQL("select * from user where email ='" + email + "'");
                if (rs.next()) {
                    rs.updateString("username", username);
                    rs.updateString("mobile_no", mobile_no);
                    rs.updateString("photo", pic);
                    rs.updateRow();
                    JOptionPane.showMessageDialog(this, "Profile updated Successfully!!");
                    userData();
                } else {
                    JOptionPane.showMessageDialog(this, "No User Found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jfc = new JFileChooser("C://");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        jfc.setFileFilter(filter);
        int ans = jfc.showOpenDialog(this);
        if (ans == JFileChooser.APPROVE_OPTION) {
            f = jfc.getSelectedFile();
            path = f.getPath();
            ImageIcon i1 = new ImageIcon(f.getPath());
            Image img = i1.getImage().getScaledInstance(photolb.getWidth(), photolb.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon i2 = new ImageIcon(img);
            photolb.setIcon(i2);
        } else {
            JOptionPane.showMessageDialog(this, "Cancelled!!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(edit_profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(edit_profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(edit_profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(edit_profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new edit_profile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jemail;
    private javax.swing.JTextField jmobile;
    private javax.swing.JTextField jusername;
    private javax.swing.JLabel photolb;
    private javax.swing.JLabel photolb1;
    // End of variables declaration//GEN-END:variables
}
