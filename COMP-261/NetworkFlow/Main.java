 

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;
    static ResourceBundle bundle;
    static Locale locale;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        // load the strings for language support
        // currently en_NZ and mi_NZ are supported
        locale = new Locale("en", "NZ");
        bundle = ResourceBundle.getBundle("resources/strings", locale);

        // load the fxml file to set up the GUI
        reload();

    }

    // set locale function
    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("resources/strings", locale);
    }

    public void reload() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MapView.fxml"), bundle);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("title")); // set the title of the window from the bundle
        stage.setScene(new Scene(root, 800, 700));
        stage.show();
        stage.setOnCloseRequest(e -> {
            System.exit(0);
            
        });
    
    }

    public static void main(String[] args) {
        launch(args);
    }

}
