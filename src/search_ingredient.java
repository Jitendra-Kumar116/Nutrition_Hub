
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class search_ingredient extends javax.swing.JFrame {

    /**
     * Creates new form search_ingredient
     */
    ArrayList<ingredient> al;
    mytablemodel tm;

    public search_ingredient() {
        initComponents();
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setSize(d.width, d.height);
        photolb.setBounds(0, 0, d.width, d.height);
        ImageIcon i1 = new ImageIcon("src/uploads/slider12.jpg");
        Image img = i1.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
        photolb.setIcon(new ImageIcon(img));
        al = new ArrayList<>();
        tm = new mytablemodel();
        jTable1.setModel(tm);
        jScrollPane2.setVisible(false);
        jButton2.setVisible(false);
        jButton3.setVisible(false);
        jButton1.setBackground(Color.white);
        jButton2.setBackground(Color.white);
        jButton3.setBackground(Color.white);

        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setResizable(false);
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
        jsearch = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        photolb = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SEARCH INGREDIENTS");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(510, 150, 320, 39);
        getContentPane().add(jsearch);
        jsearch.setBounds(430, 210, 330, 40);

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(780, 210, 130, 40);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(440, 280, 460, 190);

        jButton2.setText("Get Ingredient Information");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(440, 510, 190, 50);

        jButton3.setText("Get Ingredient Substitute");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(690, 510, 200, 50);

        photolb.setText("jLabel2");
        getContentPane().add(photolb);
        photolb.setBounds(0, 0, 37, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    class mytablemodel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return al.size();
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public String getColumnName(int j) {
            String i = "Ingredient";
            return i;
        }

        @Override
        public Object getValueAt(int i, int j) {
            ingredient ig = al.get(i);
            return ig.name;
        }

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String search = jsearch.getText();
        jScrollPane2.setVisible(true);
        jButton2.setVisible(true);
        jButton3.setVisible(true);
        try {
            HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/ingredients/search?query=" + search)
                    .header("X-RapidAPI-Key", "652fb35be6msh43c979dd6e258dap1ede35jsn29e136726656")
                    .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .asString();
            if (response.getStatus() == 200) {
                String res = response.getBody();
                JSONParser parser = new JSONParser();
                JSONObject jo = (JSONObject) parser.parse(res);
                JSONArray ja = (JSONArray) jo.get("results");
                for (int i = 0; i < ja.size(); i++) {
                    JSONObject jo1 = (JSONObject) ja.get(i);
                    String name = (String) jo1.get("name");
                    long id = (Long) jo1.get("id");
//                    System.out.println(name);
                    al.add(new ingredient(name, id));
                }
                tm.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int i = -1;
        i = jTable1.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row");
        } else {
            String name = al.get(i).name;
            long id = al.get(i).id;
            System.out.println(name + " " + id);
            ingridients_nutrients obj = new ingridients_nutrients(name, id);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int i = -1;
        i = jTable1.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row");
        } else {
            String name = al.get(i).name;
            long id = al.get(i).id;
            System.out.println(name + " " + id);
            ingredient_substitute obj = new ingredient_substitute(name, id);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(search_ingredient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(search_ingredient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(search_ingredient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(search_ingredient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new search_ingredient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jsearch;
    private javax.swing.JLabel photolb;
    // End of variables declaration//GEN-END:variables
}
