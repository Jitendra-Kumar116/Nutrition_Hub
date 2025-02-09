
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class ingredient_substitute extends javax.swing.JFrame {

    /**
     * Creates new form ingredient_substitute
     */
    long id;
    String name;
    ArrayList<substitute> al;
    mytablemodel tm;

    public ingredient_substitute(String Name,long ID) {
        initComponents();
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setSize(d.width, d.height);
        photolb.setBounds(0, 0, d.width, d.height);
        ImageIcon i1 = new ImageIcon("src/uploads/slider12.jpg");
        Image img = i1.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
        photolb.setIcon(new ImageIcon(img));
        id = ID;
        name = Name;
        al = new ArrayList<>();
        tm = new mytablemodel();
        jname.setText(Name);
        jTable1.setModel(tm);
        jScrollPane1.setVisible(false);
        getSubstitute();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    void getSubstitute() {
        try {
            HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/ingredients/"+id+"/substitutes")
                    .header("X-RapidAPI-Key", "652fb35be6msh43c979dd6e258dap1ede35jsn29e136726656")
                    .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .asString();
            if (response.getStatus() == 200) {
                String ans = response.getBody();
                JSONParser parser = new JSONParser();
                JSONObject jo = (JSONObject) parser.parse(ans);
                String status = (String) jo.get("status");
                String message = (String) jo.get("message");
                if (status.equals("success")) {
                    jScrollPane1.setVisible(true);
                    String ingredients = (String) jo.get("ingredient");
                    jname.setText("Name :"+ingredients);
                    jmessage.setText("Message :"+message);
                    JSONArray ja = (JSONArray) jo.get("substitutes");
                    for (int i = 0; i < ja.size(); i++) {
                        String substitute = (String) ja.get(i);
                        al.add(new substitute(substitute));
                    }
                    tm.fireTableDataChanged();
                }else{
                    jmessage.setText(message);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            String s = "substitutes";
            return s;
        }

        @Override
        public Object getValueAt(int i, int j) {
            substitute st = al.get(i);
            return st.substitutes;
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
        jname = new javax.swing.JLabel();
        jmessage = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        photolb = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INGREDIENT SUBSTITUTE");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(490, 180, 420, 30);

        jname.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jname.setForeground(new java.awt.Color(255, 255, 255));
        jname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jname.setText("jLabel2");
        getContentPane().add(jname);
        jname.setBounds(530, 230, 320, 30);

        jmessage.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jmessage.setForeground(new java.awt.Color(255, 255, 255));
        jmessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jmessage.setText("lb2");
        getContentPane().add(jmessage);
        jmessage.setBounds(460, 290, 470, 30);

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
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(540, 370, 290, 90);

        photolb.setText("jLabel2");
        getContentPane().add(photolb);
        photolb.setBounds(450, 80, 37, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ingredient_substitute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ingredient_substitute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ingredient_substitute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ingredient_substitute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ingredient_substitute("",0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel jmessage;
    private javax.swing.JLabel jname;
    private javax.swing.JLabel photolb;
    // End of variables declaration//GEN-END:variables
}
