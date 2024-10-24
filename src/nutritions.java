
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class nutritions extends JFrame {

    JTabbedPane tp;
    JScrollPane jsp1, jsp2;
    JTable jt1, jt2;
    JLabel photolb;
    ArrayList<good> al1 = new ArrayList<>();
    ArrayList<bad> al2 = new ArrayList<>();
    int id;
    mytablemodel1 tm1 = new mytablemodel1();
    mytablemodel2 tm2 = new mytablemodel2();
    
    public nutritions(int ID) {
        setLayout(null);
        id = ID;
        tp = new JTabbedPane();
        jt1 = new JTable();
        jt2 = new JTable();
        jt1.setModel(tm1);
        jt2.setModel(tm2);
        photolb = new JLabel();
        jsp1 = new JScrollPane(jt1);
        jsp2 = new JScrollPane(jt2);
        jsp1.setBounds(433, 134, 500, 500);
        tp.add("Good", jsp1);
        tp.add("Bad", jsp2);
        tp.setBackground(Color.white);
        tp.setBounds(433, 134, 500, 500);
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setSize(d.width, d.height);
        photolb.setBounds(0, 0, d.width, d.height);
        ImageIcon i1 = new ImageIcon("src/uploads/slider12.jpg");
        Image img = i1.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
        photolb.setIcon(new ImageIcon(img));
        getNutritions(id);
        add(tp);
        add(photolb);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void getNutritions(int id) {
        try {
            HttpResponse<String> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/"+id+"/nutritionWidget.json")
                    .header("X-RapidAPI-Key", "652fb35be6msh43c979dd6e258dap1ede35jsn29e136726656")
                    .header("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .asString();
            if (response.getStatus() == 200) {
                String res = response.getBody();
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(res);
                JSONArray ja1 = (JSONArray) jsonObject.get("good");
                JSONArray ja2 = (JSONArray) jsonObject.get("bad");
                for (int i = 0; i < ja1.size(); i++) {
                    JSONObject jo1 = (JSONObject) ja1.get(i);
                    String title = (String) jo1.get("title");
                    String amount = (String) jo1.get("amount");
                    double percentage = (Double) jo1.get("percentOfDailyNeeds");
                    int percentageNedded = (int) percentage;
                    al1.add(new good(title,amount,percentageNedded));
                    tm1.fireTableDataChanged();
                }
                for(int j=0;j<ja2.size();j++){
                    JSONObject jo2 = (JSONObject) ja2.get(j);
                    String title = (String) jo2.get("title");
                    String amount = (String) jo2.get("amount");
//                    double percentge = (double) jo2.get("percentOfDailyNeeds");
                    double percentge = (Double) jo2.get("percentOfDailyNeeds");
                    int percentageNeeded = (int) percentge;
                    al2.add(new bad(title,amount,percentageNeeded));
                    tm2.fireTableDataChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class mytablemodel1 extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return al1.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int j) {
            String g[] = {"title", "amount", "percentage"};
            return g[j];
        }

        @Override
        public Object getValueAt(int i, int j) {
            good gd = al1.get(i);
            if (j == 0) {
                return gd.title;
            } else if (j == 1) {
                return gd.amount;
            } else {
                return gd.percentage;
            }
        }
    }

    class mytablemodel2 extends AbstractTableModel{

        @Override
        public int getRowCount() {
            return al2.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int j) {
            String b[] = {"title", "amount", "percentage"};
            return b[j];
        }
        
        
        @Override
        public Object getValueAt(int i, int j) {
            bad bd = al2.get(i);
            if(j==0){
                return bd.title;
            }else if(j==1){
                return bd.amount;
            }else{
                return bd.percentage;
            }
        }
        
    }
    
    public static void main(String[] args) {
        nutritions obj = new nutritions(0);
    }
}
