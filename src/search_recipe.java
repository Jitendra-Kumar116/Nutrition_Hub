
import java.sql.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import vmm.DBLoader;

public class search_recipe extends javax.swing.JFrame {

    /**
     * Creates new form search_recipe
     */
    public search_recipe() {
        initComponents();
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        jButton1.setBackground(Color.white);
        System.out.println(d);
        setSize(d.width, d.height);
        getContentPane().setBackground(Color.black);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jScrollPane1.setVisible(false);
        loading.setVisible(false);
        loading.setBounds(500, 200, 256, 256);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JSearch = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        main = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        loading = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(JSearch);
        JSearch.setBounds(360, 80, 390, 50);

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(800, 80, 140, 50);

        main.setBackground(new java.awt.Color(255, 255, 255));
        main.setLayout(null);
        jScrollPane1.setViewportView(main);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 160, 1320, 530);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SEARCH RECIPE");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(590, 10, 260, 40);

        loading.setForeground(new java.awt.Color(255, 255, 255));
        loading.setText("jLabel2");
        getContentPane().add(loading);
        loading.setBounds(960, 20, 140, 120);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        loading.setVisible(true);
        ImageIcon i1 = new ImageIcon("src/uploads/loading1.gif");
        loading.setIcon(i1);
        String search1 = JSearch.getText();
        String search = search1.replace(" ", "%20");
        if (search.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search something");
            loading.setVisible(false);
        } else {
            try {
                HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/complexSearch?query=" + search)
                        .header("X-RapidAPI-Key", "652fb35be6msh43c979dd6e258dap1ede35jsn29e136726656")
                        .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                        .asString();

                if (response.getStatus() == 200) {
                    jScrollPane1.setVisible(true);
                    String res = response.getBody();
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(res);
                    long count1 = (Long) jsonObject.get("number");//to get count of items
                    int count = (int) count1;
//                    System.out.println(count);
                    JSONArray jsonArray = (JSONArray) jsonObject.get("results");
//                    System.out.println(jsonArray.size());
                    main.removeAll();

                    search_recipe_design obj[] = new search_recipe_design[count];
                    int x = 50, y = 50;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject js = (JSONObject) jsonArray.get(i);
                        long id = (Long) js.get("id");
                        int ID = (int) id;
                        String title = (String) js.get("title");
                        String title1 = title.replace(" ", "%20");
                        String image_url = (String) js.get("image");
                        String image_type = (String) js.get("imageType");
                        String status = "no";
//                        System.out.println(id + " " + title + " " + image_url + " " + image_type);
                        try {
                            ResultSet rs = DBLoader.exexuteSQL("Select * from favourites where recipe_id ='" + ID + "' ");
                            if (rs.next()) {
                                status = "yes";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        obj[i] = new search_recipe_design();
                        if (status == "yes") {
                            obj[i].jButton8.setText("Remove from Favourites");
                        }
                        loading.setVisible(false);
                        obj[i].jLabel1.setText(title);
                        URL url = new URL(image_url.replace("http://", "https://"));
                        BufferedImage img = ImageIO.read(url);
                        Image resized = img.getScaledInstance(obj[i].photolb.getWidth(), obj[i].photolb.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(resized);
                        obj[i].photolb.setIcon(icon);
//                        obj[i].photolb.addMouseListener(new MouseAdapter() {
//                            @Override
//                            public void mouseClicked(MouseEvent e){
//                                detailed_recipe obj = new detailed_recipe(ID);
//                            }
//                        });

                        //get ingredients
                        obj[i].jButton1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Ingredients obj = new Ingredients(ID);
                            }
                        });

                        //get recipe steps
                        obj[i].jButton2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                detailed_recipe obj = new detailed_recipe(ID);
                            }
                        });

                        //get nutritions
                        obj[i].jButton3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                nutritions obj = new nutritions(ID);
                            }
                        });

                        //youtube video
                        obj[i].jButton4.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/videos/search?query=" + title1)
                                            .header("X-RapidAPI-Key", "652fb35be6msh43c979dd6e258dap1ede35jsn29e136726656")
                                            .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                                            .asString();
                                    if (response.getStatus() == 200) {
                                        String res = response.getBody();
                                        JSONParser parser = new JSONParser();
                                        JSONObject jo = (JSONObject) parser.parse(res);
                                        JSONArray ja = (JSONArray) jo.get("videos");
                                        if (!ja.isEmpty()) {
                                            JSONObject jo1 = (JSONObject) ja.get(0);
                                            String youtubeid = (String) jo1.get("youTubeId");
                                            URI u = new URI("www.youtube.com/results?search_query=" + youtubeid);
                                            Desktop d = Desktop.getDesktop();
                                            d.browse(u);
                                        } else {
                                            JOptionPane.showMessageDialog(rootPane, "No Video Found");
                                        }
//                                        https://www.youtube.com/results?search_query=ZngBMVrPzt4
//                                        URL url = new URL("https://www.youtube.com/results?search_query="+youtubeid);
//                                        url.openConnection();
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                        //recipe cost
                        obj[i].jButton5.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                recipe_cost obj = new recipe_cost(ID);
                            }
                        });

                        //recipe summary
                        obj[i].jButton6.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                recipe_summary obj = new recipe_summary(ID);
                            }
                        });

                        //recipe taste
                        obj[i].jButton7.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                recipe_taste obj = new recipe_taste(ID);
                            }
                        });

                        //add to favourites
                        obj[i].jButton8.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                addToFavourites(ID, title);
                            }
                        });

                        obj[i].setBounds(x, y, 1200, 550);
                        main.add(obj[i]);
                        y = y + 580;
                        main.repaint();
                        obj[i].repaint();
                    }
                    main.setPreferredSize(new Dimension(1200, 580 * count));
                    main.revalidate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    void addToFavourites(int id, String title) {
        try {
            ResultSet rs = DBLoader.exexuteSQL("select * from favourites where recipe_id ='" + id + "' ");
            if (rs.next()) {
                rs.deleteRow();
                JOptionPane.showMessageDialog(this, "Recipe removed from favourites");
            } else {
                rs.moveToInsertRow();
                rs.updateString("email", global.email);
                rs.updateLong("recipe_id", id);
                rs.updateString("recipe_name", title);
                rs.insertRow();
                JOptionPane.showMessageDialog(this, "Recipe Added in Favorites");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            java.util.logging.Logger.getLogger(search_recipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(search_recipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(search_recipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(search_recipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new search_recipe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JSearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel loading;
    private javax.swing.JPanel main;
    // End of variables declaration//GEN-END:variables
}
