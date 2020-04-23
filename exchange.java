import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class exchange extends Application {
    String FromCountry;
    String ToCountry;
    Label ratelbl;
    Double rate;
    @Override
    public void start(Stage stage) throws Exception {
        Label from=new Label("From");
        Label to=new Label("To");

        Button showresults=new Button("Show results");
        ComboBox combobox1=new ComboBox();
        ComboBox combobox2=new ComboBox();
        GridPane gridPane=new GridPane();
        File file=new File("src\\physical_currency_list.csv");
        BufferedReader br=new BufferedReader(new FileReader(file));
        try{
            String line;
            while((line=br.readLine())!=null){
                combobox1.getItems().add(line);
                combobox2.getItems().add(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        combobox1.setPromptText("Select the country Code");
        combobox2.setPromptText("Select the country Code");
        TextField tf=new TextField();
        TextField tf2=new TextField();
        combobox1.setOnAction(e->{
            String value= (String) combobox1.getValue();
            FromCountry=String.valueOf(value.charAt(0))+value.charAt(1)+value.charAt(2);
           // System.out.println(FromCountry+"From combobox1");
        });
        combobox2.setOnAction(e->{
            String value= (String) combobox2.getValue();
            ToCountry=String.valueOf(value.charAt(0))+value.charAt(1)+value.charAt(2);
          //  System.out.println(ToCountry+"From combobox2");
        });
        showresults.setOnAction(k->{
            gridPane.getChildren().remove(ratelbl);
            URL urlObject= null;
            try {
                urlObject = new URL("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency="+FromCountry+"&to_currency="+ToCountry+"&apikey=6NFP86R2UPNXZIAA");
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            InputStream is2= null;
            try {
                is2 = urlObject.openStream();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            InputStreamReader isr2=new InputStreamReader(is2);

            BufferedReader br2=new BufferedReader(isr2);
            String string="";
            JsonParser jsonParser2=new JsonParser();

            try {
                String line;
                while ((line = br2.readLine()) != null) {
                    string=string+line+'\n';
                }}

            catch(IOException e){
                e.printStackTrace();

            }
            //System.out.println(string2);
            JsonObject jsonobject1=(JsonObject) jsonParser2.parse(string);
            JsonObject jsonObject=jsonobject1.getAsJsonObject("Realtime Currency Exchange Rate");
            JsonElement jsonElement=jsonObject.get("5. Exchange Rate");
            rate=jsonElement.getAsDouble();
            double money=Integer.parseInt(tf.getText())*rate;
            tf2.setText(String.valueOf(money));

        });



        gridPane.add(from,0,0);
        gridPane.add(combobox1,1,0);
        gridPane.add(to,2,0);
        gridPane.add(combobox2,3,0);
        gridPane.add(showresults,4,0);
        gridPane.add(tf,1,1);
        gridPane.add(tf2,3,1);
        gridPane.setHgap(4.4);
        gridPane.setVgap(4.4);
        Scene scene=new Scene(gridPane,800,100);
        stage.setScene(scene);
        stage.show();

    }
}
