package com.example.youtube.Controller;

import com.example.youtube.HelloApplication;
import com.example.youtube.HelloController;
import com.example.youtube.Model.Channel;
import com.example.youtube.Model.User;
import com.example.youtube.Server.Client;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Pattern;

public class signUpController implements Initializable {
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField secondPasswordField;
    private Stage stage = new Stage();
    private Parent root;
    @FXML
    private Button next;
    Gson gson=new Gson();
    @FXML
    Label error=new Label();


    @FXML
    private Label Passagain = new Label();
    @FXML
    private Button Continue = new Button();

    @FXML
    private Label email = new Label();

    @FXML
    private Label passWord = new Label();
    @FXML
    Label errorUserName=new Label();
    @FXML
    Button Sign_UP=new Button();
    @FXML
    Label emilCh = new Label();
    @FXML
    Label con=new Label();
    @FXML
    Label errorEmail=new Label();

    String Email;
    String password;
    String country;
    String Age ;
    String Username;
    @FXML
    Button Login=new Button();
    @FXML
    Button Profile=new Button();

   //-------------------------------------------------------------
   //this part is for object
   public Client client;
    public  boolean loginOn=false;

    public Channel channel;

    public User user;

    static Stage loginStage=null;






    //-------------------------------------------------



    @FXML
    public void emailFieldClick() {
        emailField.setStyle("-fx-border-color : #4de4ff; -fx-border-radius : 6; -fx-background-color:  #2C2829; -fx-text-inner-color : #ffff");
        passwordField.setStyle("-fx-border-radius : 6; -fx-background-color :  #2C2829; -fx-border-color : #2C2829; -fx-text-inner-color : #ffff");
        secondPasswordField.setStyle("-fx-border-radius : 6; -fx-background-color :  #2C2829; -fx-border-color : #2C2829; -fx-text-inner-color : #ffff");
    }

    @FXML
    public void passwordFieldClick() {
        passwordField.setStyle("-fx-border-radius : 6; -fx-background-color :  #2C2829; -fx-border-color :  #4de4ff; -fx-text-inner-color : #ffff");
        emailField.setStyle("-fx-border-color : #2C2829; -fx-border-radius : 6; -fx-background-color:  #2C2829; -fx-text-inner-color : #ffff");
        secondPasswordField.setStyle("-fx-border-radius : 6; -fx-background-color :  #2C2829; -fx-border-color : #2C2829; -fx-text-inner-color : #ffff");
    }



    @FXML
    public void secondPasswordFieldClick() {
        secondPasswordField.setStyle("-fx-border-radius : 6; -fx-background-color :  #2C2829; -fx-border-color :  #4de4ff; -fx-text-inner-color : #ffff");
        emailField.setStyle("-fx-border-color : #2C2829; -fx-border-radius : 6; -fx-background-color:  #2C2829; -fx-text-inner-color : #ffff");
        passwordField.setStyle("-fx-border-radius : 6; -fx-background-color :  #2C2829; -fx-border-color : #2C2829; -fx-text-inner-color : #ffff");
    }

    @FXML
    public void endOfSignUp() {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //check connection client
        if (client!=null) {
            con.setText("[ CONNECT ]");
            con.setTextFill(Color.RED);
            con.setVisible(true);
        }
        Continue.setVisible(false);
        System.out.println("[START] SING_UP");
        error.setVisible(false);
        error.setTextFill(Color.RED);

        //get first information value gmail and passWord
        next.setOnAction(e -> {
            /* check request */
            if (UserEixst()) {
                /* valid email ***@***.*** */
                if (validateEmailStrict(emailField.getText())) {
                    if (passwordField.getText().equals(secondPasswordField.getText())) {
                        /* passWord should be bigger than 5 */
                        if (passwordField.getText().length() > 5) {
                                System.out.println("[ACCEPT USER AND CHANEL ]");
                                Continue.setVisible(true);
                                next.setVisible(false);
                                passWord.setText("Age: ");
                                email.setText("Username :");
                                Passagain.setVisible(true);
                                secondPasswordField.setVisible(true);
                                secondPasswordField.setText("");
                                Passagain.setText("County :");
                                Email = emailField.getText();
                                emailField.setText(" ");
                                //this method hash the password TODO change the algorithm of hashing
                                password = hashPasswordSHA256(passwordField.getText());
                                passwordField.setText("");
                                error.setText("");
                                error.setVisible(false);
                                errorEmail.setText("");
                                errorEmail.setVisible(false);
                        } else {
                        error.setVisible(true);
                        error.setText("THR PASSWORD  IS  NOT  STRONG ");
                        }
                    } else {
                    error.setVisible(true);
                    error.setText("NOT EQUAL");

                    }
                } else {
                    emilCh.setVisible(true);
                    errorEmail.setVisible(true);
                    errorEmail.setTextFill(Color.RED);
                    errorEmail.setText("YOUR EMAIL IS NOT CORRECT");
                }
            }else
            {
                errorEmail.setVisible(true);
                errorEmail.setTextFill(Color.RED);
                errorEmail.setText("[YOU HAVE AN ACCOUNT ] ");
            }
        });

        // complete getting value age userName and country and create  an ID for user
        Continue.setOnAction(e -> {
            Age=passwordField.getText();
            country=secondPasswordField.getText();
            if(CheckUserName(emailField.getText())) {   //ToDO
                System.out.println(isInteger(Age));
                System.out.println(Age);
                if (isInteger(Age)) {
                    Username = emailField.getText();
                    System.out.println("User :" +
                            Age +
                            country +
                            Username);

                    LocalDateTime localDateTime = LocalDateTime.now();
                    UUID uuid = UUID.randomUUID();
                    String ID = uuid.toString();
                    //ID+Username=ID
                    ID = ID + "rouzbeh";


//                Create a user
                    User user = new User(Username, Email, localDateTime.toString(), password, country, ID, Age);
//                Create a channel
                    Channel channel = new Channel(ID, "", "", ID + "CHA", Username, ID + "POR", "l");//get name and username


                    try {
                        System.out.println("[TRY ] ");
                        System.out.println("[AFTER RESPONSE] ");
                        if (client != null) {
                            System.out.println("[Client is not null]");
                            if (client.addUserRequest(user)) {
                                if (client.addChannelRequest(channel)) {
                                    System.out.println("[ACCEPT User AND channel] ");
                                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));//go to Home page
                                    Scene scene = null;
                                    try {
                                        scene = new Scene(fxmlLoader.load(), 1190, 627);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    HelloController helloController = fxmlLoader.getController();
                                    //insert user and channel and true
                                    helloController.isDarkModeOn=true;
                                    helloController.user = user;
                                    helloController.channel = channel;
                                    helloController.loginOn = true;

                                    stage.setTitle("Youtube");
                                    stage.setScene(scene);
                                    stage.show();
                                    Continue.getScene().getWindow().hide();
                                } else {
                                    System.out.println("[ADD CHANNEL ] FAIL");
                                }
                            } else {
                                System.out.println("[ADD USER] FAIL");
                            }
                        }
                        } catch(IOException ex){
                            throw new RuntimeException(ex);
                        }
                    } else{
                        error.setVisible(true);
                        error.setTextFill(Color.RED);
                        error.setText("Please inter correct Number ");
                    }
                } else {
                    errorEmail.setVisible(true);
                    errorEmail.setTextFill(Color.RED);
                    errorEmail.setText("Your user name is used ");
                }

        });

        Login.setOnAction(e->{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 469, 614);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //pass the client
            LoginController loginController=fxmlLoader.getController();
            loginController.client=this.client;
            stage.setTitle("Youtube");
            stage.setScene(scene);
            stage.show();
            Login.getScene().getWindow().hide();
        });




    }
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean CheckUserName(String text) {


        //this is for check the username is existed
        //todo by default is true
        return true;
    }

    private boolean UserEixst() {//check user is existed or not


        //check use exist or not
        return true;
    }



    public static boolean validateEmailStrict(String email) {
        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regexPattern, email);
    }






    public static String hashPasswordSHA256 (String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}
