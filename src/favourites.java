
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import vmm.DBLoader;


public class favourites extends javax.swing.JFrame {

    /**
     * Creates new form favourites
     */
    public favourites() {
        initComponents();
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setSize(d.width, d.height);
        getContentPane().setBackground(Color.black);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("My favourites");
        getFavourites();
    }

    void getFavourites() {
        int x = 50, y = 50;
        int j = 0;
        main.removeAll();
        try {
            ResultSet rs = DBLoader.exexuteSQL("select * from favourites where email ='" + global.email + "' ");
            while (rs.next()) {
                j++;
                String recipe_name = rs.getString("recipe_name");
                String recipe = recipe_name.replace(" ", "%20");
                String recipe_id = rs.getString("recipe_id");
                int favourite_id = rs.getInt("favourite_id");
//                System.out.println(recipe_id + " " + recipe_name);
                HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/complexSearch?query=" + recipe)
                        .header("X-RapidAPI-Key", "652fb35be6msh43c979dd6e258dap1ede35jsn29e136726656")
                        .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                        .asString();
//                System.out.println(response.getBody());
                if (response.getStatus() == 200) {
                    String ans = response.getBody();
                    JSONParser parser = new JSONParser();
                    JSONObject jo = (JSONObject) parser.parse(ans);
                    JSONArray ja = (JSONArray) jo.get("results");
                    JSONObject jo1 = (JSONObject) ja.get(0);
                    long id = (Long) jo1.get("id");
                    int ID = (int) id;
                    String title = (String) jo1.get("title");
                    String title1 = title.replace(" ", "%20");
                    String image_url = (String) jo1.get("image");
                    String image_type = (String) jo1.get("imageType");
                    search_recipe_design obj = new search_recipe_design();
                    URL url = new URL(image_url.replace("http://", "https://"));
                    BufferedImage img = ImageIO.read(url);
                    Image resized = img.getScaledInstance(obj.photolb.getWidth(), obj.photolb.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(resized);
                    obj.photolb.setIcon(icon);
                    obj.jLabel1.setText(title);
                    obj.setBounds(x, y, 1200, 550);
                    y = y + 580;

                    //get ingredients
                    obj.jButton1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Ingredients obj = new Ingredients(ID);
                        }
                    });

                    //get recipe steps
                    obj.jButton2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            detailed_recipe obj = new detailed_recipe(ID);
                        }
                    });

                    //get nutritions
                    obj.jButton3.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            nutritions obj = new nutritions(ID);
                        }
                    });

                    //get youtube video
                    obj.jButton4.addActionListener(new ActionListener() {
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

                    //get cost of recipe
                    obj.jButton5.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            recipe_cost obj = new recipe_cost(ID);
                        }
                    });

                    //get Summary
                    obj.jButton6.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            recipe_summary obj = new recipe_summary(ID);
                        }
                    });

                    //get taste
                    obj.jButton7.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            recipe_taste obj = new recipe_taste(ID);
                        }
                    });

                    //remove favourite
                    obj.jButton8.setText("Remove Favourite");
                    obj.jButton8.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            removeFavourite(favourite_id);
                        }
                    });

                    main.add(obj);
                    main.repaint();
                    obj.repaint();
                }
                main.setPreferredSize(new Dimension(1200, 580 * j));
                main.revalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void removeFavourite(int id) {
        ResultSet rs;
        try {
            rs = DBLoader.exexuteSQL("select * from favourites where favourite_id ='" + id + "' ");
            if (rs.next()) {
                rs.deleteRow();
                JOptionPane.showMessageDialog(this, "Removed From favourites");
                getFavourites();
            }else{
                JOptionPane.showMessageDialog(this, "Not Found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        main = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MY FAVOURITES");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(610, 30, 310, 40);

        main.setBackground(new java.awt.Color(255, 255, 255));
        main.setForeground(new java.awt.Color(255, 255, 255));
        main.setLayout(null);
        jScrollPane1.setViewportView(main);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 100, 1330, 570);

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
            java.util.logging.Logger.getLogger(favourites.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(favourites.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(favourites.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(favourites.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new favourites().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel main;
    // End of variables declaration//GEN-END:variables
}
