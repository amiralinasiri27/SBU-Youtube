package com.example.youtube.DataBase;

import com.example.youtube.Model.*;
import com.example.youtube.Model.Math;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.nio.file.WatchEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DataBaseManager {


    static Connection connection;
    static Statement statement;

    private DataBaseManager() {

    }

    /**
     * Start connection in database
     */
    private static void StartConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube", "root", "");
            statement = connection.createStatement();

        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
    }

    /**
     * End  connection in database
     */
    private static void EncConnection() {
        try {
            if (connection != null) {
                statement.close();
                connection.close();

            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    /**
     * get method
     */


    //get User form database in Login
    public static User get_User(String name, String passWord) {
        StartConnection();
        String query = "SELECT * FROM User WHERE Email='%s' AND passWord='%s'";
        try {

            query = String.format(query, name, passWord);

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String idUser = rs.getString("IDuser");
                String country = rs.getString("Country");
                String password = rs.getString("passWord");
                String data = rs.getString("Time");
                String Age = rs.getString("Age");
                System.out.println(username+" "+email);

                return new User(username, email, data, country, password, idUser, Age);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        EncConnection();
        return null;
    }


    //get Channel with  0= username  1=ID_chanel
    public static synchronized Channel get_Channel(String identifier, int number) {
        StartConnection();
        String query;
        if (number == 0) {
            query = "SELECT * FROM chanel WHERE username='%s'";
        } else {
            query = "SELECT * FROM chanel WHERE ID_chanel='%s'";
        }

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, identifier);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String ID_chanel = resultSet.getString("ID_chanel");
                String Name = resultSet.getString("Name");
                String image_Chanel = resultSet.getString("image_Chanel");
                String username = resultSet.getString("username");
                String Image_Pro = resultSet.getString("Image_Pro");
                String information = resultSet.getString("information");
                String Link = resultSet.getString("Link");
                return new Channel(ID_chanel, Name, image_Chanel, username, Image_Pro, information, Link);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return null;
    }


    //this is for get video from chanel return all video in chanel
    public synchronized static ArrayList<Video> getList_video(String IDC) {//TODO check
        ArrayList<Video> videos = new ArrayList<>();
        StartConnection();

        String query = "SELECT * FROM video WHERE Chanel_ID=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IDC);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String path = resultSet.getString("path");
                String ID_video = resultSet.getString("ID_video");
                String Chanel_ID = resultSet.getString("Chanel_ID");
                String time_uplode = resultSet.getString("time_uplode");
                int view = resultSet.getInt("view");
                int PlayTime = resultSet.getInt("PlayTime");

                String name = resultSet.getString("name");
                String information = resultSet.getString("information");
                String Block = resultSet.getString("Block");
                String category = resultSet.getString("category");
                videos.add(new Video(ID_video, Chanel_ID, name, information, time_uplode, PlayTime, view, Block,category));
            }
            EncConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return videos;
    }


    // this is for get the comment for video
    public static synchronized ArrayList<Comment> getListComment(String video_ID) {//TODO check

        StartConnection();
        String query = "SELECT * FROM  comment WHERE ID_video=? ";
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, video_ID);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String comment = resultSet.getString("comment");
                String witer = resultSet.getString("witerer");
                String ID_video = resultSet.getString("ID_video");
                int like = resultSet.getInt("like");
                int dislike = resultSet.getInt("dislike");
                String time = resultSet.getString("Time");
                String UserID = resultSet.getString("UserID");
                String id = resultSet.getString("ID");
                comments.add(new Comment(comment, UserID, witer, ID_video, time, like, dislike, id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return comments;

    }

    //comment that User get for his video
    public static synchronized ArrayList<Comment> getListComment_userGet(String IDU) {//TODO check
        StartConnection();
        String query = "SELECT * FROM  comment WHERE IDChanel=? ";
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IDU);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String comment = resultSet.getString("comment");
                String witer = resultSet.getString("witerer");
                String ID_video = resultSet.getString("ID_video");
                int like = resultSet.getInt("like");
                int dislike = resultSet.getInt("dislike");
                String time = resultSet.getString("Time");
                String UserID = resultSet.getString("UserID");
                String id = resultSet.getString("ID");
                comments.add(new Comment(comment, UserID, witer, ID_video, time, like, dislike, id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return comments;

    }


    //get list of comment that the User send
    public static ArrayList<Comment> getListCommentUser(String wirtter) {//TODO check

        StartConnection();
        String query = "SELECT * FROM  comment WHERE witer=? ";
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, wirtter);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String comment = resultSet.getString("comment");
                String witer = resultSet.getString("witerer");
                String ID_video = resultSet.getString("ID_video");
                int like = resultSet.getInt("like");
                int dislike = resultSet.getInt("dislike");
                String time = resultSet.getString("Time");
                String UserID = resultSet.getString("UserID");
                String id = resultSet.getString("ID");
                comments.add(new Comment(comment, UserID, witer, ID_video, time, like, dislike, id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return comments;

    }


    //get list of video
    public static ArrayList<Video> getListVideoInPlayList(String IDP) {//TODO check
        StartConnection();

        ArrayList<Video> videos = new ArrayList<>();

        String query = "SELECT * FROM video   JOIN  video_playlist ON video.ID_video = video_playlist.ID_playList WHERE video_playlist.ID_playList=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IDP);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String path = resultSet.getString("path");
                String ID_video = resultSet.getString("ID_video");
                String Chanel_ID = resultSet.getString("Chanel_ID");
                String time_uplode = resultSet.getString("time_uplode");
                int view = resultSet.getInt("view");
                int PlayTime = resultSet.getInt("PlayTime");
                int like = resultSet.getInt("like");
                int Dis_like = resultSet.getInt("Dis_like");
                String name = resultSet.getString("name");
                String information = resultSet.getString("information");
                String Block = resultSet.getString("Block");
                String category = resultSet.getString("category");

                videos.add(new Video(ID_video, Chanel_ID, name, information, time_uplode, PlayTime, view, Block,category));
            }
            EncConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return videos;

    }

    //get list of playlist in chanel
    public static synchronized ArrayList<PlayList> getPlayList(String IDC) {//TODO check

        StartConnection();
        String query = "SELECT * FROM playlist  JOIN  joinplaylistto_chanel ON joinplaylistto_chanel.ID_chanel = playlist.IDPlaylist WHERE joinplaylistto_chanel.ID_chanel=? ";
        ArrayList<PlayList> playLists = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IDC);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String idPlaylist = resultSet.getString("ID_Playlist");
                String name = resultSet.getString("name");
                String idChanel = resultSet.getString("ID_chanel");
                String image = resultSet.getString("image");
                String description = resultSet.getString("discribe");
                playLists.add(new PlayList(name, idPlaylist, idChanel, description, image));
            }
            EncConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return playLists;


    }


    public static synchronized ArrayList<Video> getListVideoByCategory(String category) {//TODO check
        ArrayList<Video> videos = new ArrayList<>();
        StartConnection();

        String query = "SELECT * FROM video   JOIN  category_video ON video.ID_video = category_video.ID_video WHERE category_video.category=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, category);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String ID_video = resultSet.getString("ID_video");
                String Chanel_ID = resultSet.getString("Chanel_ID");
                String time_uplode = resultSet.getString("time_uplode");
                int view = resultSet.getInt("view");
                int PlayTime = resultSet.getInt("PlayTime");
                String name = resultSet.getString("name");
                String information = resultSet.getString("information");
                String Block = resultSet.getString("Block");
                videos.add(new Video(ID_video, Chanel_ID, name, information, time_uplode, PlayTime, view,Block,category));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return videos;
    }

    public static synchronized ArrayList<Video> getlistVideoSave(String IDU) {//TODO check
        ArrayList<Video> videos = new ArrayList<>();
        StartConnection();

        String query = "SELECT * FROM video   JOIN  savevidoe ON video.ID_video = savevidoe.IDVideo WHERE savevidoe.IDUser=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IDU);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String ID_video = resultSet.getString("ID_video");
                String Chanel_ID = resultSet.getString("Chanel_ID");
                String time_uplode = resultSet.getString("time_uplode");
                int view = resultSet.getInt("view");
                int PlayTime = resultSet.getInt("PlayTime");
                String name = resultSet.getString("name");
                String information = resultSet.getString("information");
                String Block = resultSet.getString("Block");
                String category = resultSet.getString("category");

                videos.add(new Video(ID_video, Chanel_ID, name, information, time_uplode, PlayTime, view, Block,category));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return videos;

    }

    public static synchronized ArrayList<PlayList> getListPlayListSave(String IDU) {
        ArrayList<PlayList> playLists = new ArrayList<>();
        StartConnection();

        String query = "SELECT * FROM playlist    JOIN  saveplaylist  ON saveplaylist.IDPalyList = playlist.ID_Playlist WHERE savevidoe.IDUser=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IDU);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String ID = resultSet.getString("ID_Playlist");
                String Chanel_ID = resultSet.getString("ID_chanel");
                String image = resultSet.getString("Image");
                String name = resultSet.getString("name");
                String information = resultSet.getString("discribe");
                playLists.add(new PlayList(name, ID, Chanel_ID, information, image));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return playLists;

    }


    public static synchronized ArrayList<Video> getListVideoInHistory(String IDUser) {
        ArrayList<Video> videos = new ArrayList<>();
        StartConnection();

        String query = "SELECT * FROM video   JOIN  viode_history ON video.ID_video = viode_history.IDVideo WHERE viode_history.IDUser=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IDUser);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String ID_video = resultSet.getString("ID_video");
                String Chanel_ID = resultSet.getString("Chanel_ID");
                String time_uplode = resultSet.getString("time_uplode");
                int view = resultSet.getInt("view");
                int PlayTime = resultSet.getInt("PlayTime");
                String name = resultSet.getString("name");
                String information = resultSet.getString("information");
                String Block = resultSet.getString("Block");
                String category = resultSet.getString("category");


                videos.add(new Video(ID_video, Chanel_ID, name, information, time_uplode, PlayTime, view, Block,category));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return videos;
    }

    public static synchronized ArrayList<Video> getListVideoByCategoryRandom(int numVideos, String ID) {//TODO cehck
        //TODO change channel ID TO user iD
        ArrayList<Integer> valus = new ArrayList<>();

        ArrayList<Video> videos = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>(Arrays.asList("Sport", "news", "LifeStyle", "Comedy"
                , "Education", "Autosandveh", "TravelandEvents"
                , "Gaming", "Science_Technology", "Other"));
        StartConnection();

        try {
            int i = 0;
            while (i < numVideos) {
                i++;

                String videoQuery = "SELECT * FROM video  WHERE video.category = ? ORDER BY RAND() LIMIT ?";
                PreparedStatement videoPs = connection.prepareStatement(videoQuery);
                videoPs.setString(1, category.get(i));
                videoPs.setInt(2, valus.get(i));
                ResultSet videoResultSet = videoPs.executeQuery();

                // Add the videos to the list
                while (videoResultSet.next()) {
                    String ID_video = videoResultSet.getString("ID_video");
                    String Chanel_ID = videoResultSet.getString("Chanel_ID");
                    String time_uplode = videoResultSet.getString("time_uplode");
                    int view = videoResultSet.getInt("view");
                    int PlayTime = videoResultSet.getInt("PlayTime");

                    String name = videoResultSet.getString("name");
                    String information = videoResultSet.getString("information");
                    String Block = videoResultSet.getString("Block");
                    String category1 = videoResultSet.getString("category");
                    videos.add(new Video(ID_video, Chanel_ID, name, information, time_uplode, PlayTime, view, Block,category1));
                }
            }

            EncConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }

        return videos;
    }
    public static synchronized ArrayList<Video> getListVideoByCategoryRandom(int numVideos) {//TODO cehck
        //TODO change channel ID TO user iD

        ArrayList<Integer> valus = new ArrayList<>(getinformation());

        ArrayList<Video> videos = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>(Arrays.asList("Sport", "news", "LifeStyle", "Comedy"
                , "Education", "Autosandveh", "TravelandEvents"
                , "Gaming", "Science_Technology", "Other"));
        StartConnection();

        try {
            int i = 0;
            while (i < numVideos) {
                i++;

                String videoQuery = "SELECT * FROM video  WHERE video.category = ? ORDER BY RAND() LIMIT ?";
                PreparedStatement videoPs = connection.prepareStatement(videoQuery);
                videoPs.setString(1, category.get(i));
                videoPs.setInt(2, valus.get(i));
                ResultSet videoResultSet = videoPs.executeQuery();

                // Add the videos to the list
                while (videoResultSet.next()) {
                    String ID_video = videoResultSet.getString("ID_video");
                    String Chanel_ID = videoResultSet.getString("Chanel_ID");
                    String time_uplode = videoResultSet.getString("time_uplode");
                    int view = videoResultSet.getInt("view");
                    int PlayTime = videoResultSet.getInt("PlayTime");

                    String name = videoResultSet.getString("name");
                    String information = videoResultSet.getString("information");
                    String Block = videoResultSet.getString("Block");
                    String category1 = videoResultSet.getString("category");

                    videos.add(new Video(ID_video, Chanel_ID, name, information, time_uplode, PlayTime, view, Block,category1));
                }
            }

            EncConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }

        return videos;
    }


    //this is for user
    public static ArrayList<Integer> getinformation(String ID) {
        StartConnection();
        ArrayList<Integer> values = new ArrayList<>();
        String query = "SELECT * FROM category_user WHERE IDChanel = '" + ID + "'";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                for (int i = 1; i <= 10; i++) {
                    values.add(resultSet.getInt(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EncConnection();
        }

        return values;
    }
    //this is for all user when not connection

    public static ArrayList<Integer> getinformation() {
        StartConnection();
        ArrayList<Integer> values = new ArrayList<>();
        String query = "SELECT * FROM category_user " ;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                for (int i = 1; i <= 10; i++) {
                    values.set(i,resultSet.getInt(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EncConnection();
        }

        return values;
    }

    //who you follow
    public static synchronized ArrayList<Channel> follower(String IDC) {//TODO check

        ArrayList<Channel> chanels = new ArrayList<>();
        StartConnection();

        String query = "SELECT * FROM Chanel   JOIN   ON Chanel.ID_chanel = follower.IDChanelTar WHERE follower.IDChanelTar=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IDC);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String ID_chanel = resultSet.getString("ID_chanel");
                String Name = resultSet.getString("Name");
                String image_Chanel = resultSet.getString("image_Chanel");
                String username = resultSet.getString("username");
                String Image_Pro = resultSet.getString("Image_Pro");
                String information = resultSet.getString("information");
                String Link = resultSet.getString("Link");

                chanels.add(new Channel(
                        ID_chanel,
                        Name,
                        information,
                        image_Chanel,
                        username,
                        Image_Pro, Link
                ));
            }
            EncConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }


        EncConnection();
        return chanels;

    }


    //who follow you
    public static synchronized ArrayList<Channel> following(String IDC) {//TODO check
        //TODO change Channel ID to User ID
        ArrayList<Channel> chanels = new ArrayList<>();
        StartConnection();

        String query = "SELECT * FROM Chanel   JOIN   ON Chanel.ID_chanel = following.IDChanelTar WHERE following.IDChanelTar=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IDC);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String ID_chanel = resultSet.getString("ID_chanel");
                String Name = resultSet.getString("Name");
                String image_Chanel = resultSet.getString("image_Chanel");
                String username = resultSet.getString("username");
                String Image_Pro = resultSet.getString("Image_Pro");
                String information = resultSet.getString("information");
                String Link = resultSet.getString("Link");

                chanels.add(new Channel(
                        ID_chanel,
                        Name,
                        information,
                        image_Chanel,
                        username,
                        Image_Pro,
                        Link
                ));
            }
            EncConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }


        EncConnection();
        return chanels;

    }


    /**
     * insert method
     */


//  TODO check in User
    public synchronized static boolean Cr_User(User user) {

        StartConnection();
        String query = "INSERT INTO user (username, Email, passWord, IDuser, Time,Age, Country) values ('%s','%s','%s','%s','%s','%s','%s')";
        LocalDate localDate = LocalDate.now();

        //TODO you can set time for user now or when create a user
        query = String.format(query, user.getUsername(), user.getEmail(), user.getPassword(), user.getID(), localDate.toString(), user.getAge(), user.getCountry());

        try {
            statement.execute(query);
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        EncConnection();
        return true;
    }

    /**
     * insert chanel
     */
    public synchronized static boolean Cr_Chanel(Channel channel) {

        StartConnection();
        System.out.println("[INSERT CHANNEL ]");
        String query = "INSERT INTO chanel (ID_chanel,Name,information,image_Chanel,username,Image_Pro,Link) VALUES ('%s','%s','%s','%s','%s','%s','%s')";
        query = String.format(query, channel.getId(), channel.getName(), channel.getDescription(), channel.getImage(), channel.getUsername(), channel.getImage_pro(), channel.getLink());
        try {

            statement.execute(query);

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("We have it ");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }


        EncConnection();
        return true;

    }

    /**
     * insert video
     */
    public synchronized static boolean Cr_Video(Video video) {
        StartConnection();


        String query = "INSERT INTO video (ID_video, Chanel_ID, time_uplode, view, PlayTime,  name, information,category,Block) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, video.getID());
            statement.setString(2, video.getIDChanel());
            statement.setString(3, String.valueOf(video.getUploadTime()));
            statement.setInt(4, video.getView());
            statement.setInt(5, video.getDuration());
            statement.setString(6, video.getName());
            statement.setString(7, video.getDescription());
            statement.setString(8, video.getCategory());
            statement.setString(9, video.getBlock());
            statement.execute();
        } catch (SQLException ee) {
            throw new RuntimeException(ee);
        }
        EncConnection();
        return true;
    }

    /**
     * Create PlayList
     */
    public static boolean Cr_PlayList(PlayList playList) {
        StartConnection();
        ADD_playList_chanel(playList.getID(), playList.getChannelID());
        String query = "INSERT INTO playList (ID_PlayList,name,discribe,Image,ID_chanel) values ('%s','%s','%s','%s','%s')";
        query = String.format(query, playList.getID(), playList.getName(), playList.getDescription(), playList.getImage(), playList.getChannelID());
        try {
            statement.execute(query);
        } catch (SQLIntegrityConstraintViolationException er) {
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        EncConnection();
        return true;

    }

    /**
     * insert comment
     */
    public static boolean Cr_comment(Comment comment) {

        StartConnection();
        String query = "INSERT INTO comment (comment,wirter,UserID,ID_video,`like`,dislike,Time) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, comment.getText());
            statement.setString(2, comment.getUserUsername());
            statement.setString(3, comment.getUserID());
            statement.setString(4, comment.getVideoID());
            statement.setInt(5, 0);
            statement.setInt(6, 0);
            statement.setString(7, String.valueOf(comment.getTime()));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        EncConnection();
        return true;


    }

    public static boolean ADD_playList_chanel(String IDP, String IDC) {
        StartConnection();
        String query = "INSERT INTO joinplaylistto_chanel (ID_Playlist,ID_chanel) VALUES ('%s','%s')";
        query = String.format(query, IDP, IDC);
        try {
            statement.execute(query);

        } catch (SQLIntegrityConstraintViolationException er) {
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        EncConnection();
        return true;
    }

    public static boolean ADD_video_history(String IDV, String IDU) {
        StartConnection();
        String query = "INSERT INTO viode_history (IDUser,IDVideo,Time) VALUES ('%s','%s','%s')";
        LocalDateTime localDate = LocalDateTime.now();
        query = String.format(query, IDV, IDU, String.valueOf(localDate));
        try {
            statement.execute(query);

        } catch (SQLIntegrityConstraintViolationException er) {
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;

    }

    /**
     * add follower or following (1=follower -- else following)
     */
    public static boolean ADD_follower_following(String IDU, String IDC, int Identifier) {
        StartConnection();
        String query;
        if (Check_Follower_Following(IDU, IDC, Identifier)) {
            if (Identifier == 1) {
                query = "INSERT INTO follower (IDChanel,IDuser) VALUES ('%s','%s')";//TODO change
            } else
                query = "INSERT  INTO following  (IDChanel,IDuser) VALUES ('%s','%s')";

            query = String.format(query, IDC, IDU);
            try {
                statement.execute(query);

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        EncConnection();
        return true;

    }

    public static boolean Check_Follower_Following(String IDU, String IDC, int Identifier) {//TODO check
        String query;

        if (Identifier == 1) {
            query = "SELECT * FROM follower WHERE IDChanel='%s' AND IDuser ='%s'";
        } else {
            query = "SELECT * FROM following WHERE IDChanel='%s' AND IDuser ='%s'";

        }
        query = String.format(query, IDC, IDU);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public synchronized static boolean Karam(int Karma, String UserId, String Video) {
        String query;
        if (!CheckKarma(UserId, Video)) {
            query = "INSERT INTO Karma (karma, IDChanel, IDVideo) VALUES (?, ?, ?)";
        } else {
            query = "UPDATE Karma SET karma = ? WHERE IDUser = ? AND IDVideo = ?";
        }

        StartConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, Karma);
            statement.setString(2, UserId);
            statement.setString(3, Video);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }

    public static boolean CheckKarma(String UserId, String Video) {
        StartConnection();
        String query;
        boolean exists;

        query = "SELECT COUNT(*) FROM Karma WHERE IDVChanel" +
                " = ? AND IDVideo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, UserId);
            statement.setString(2, Video);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            exists = count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        finally {
            EncConnection();
        }

        return exists;
    }


    //this is for short video karam
    public synchronized static boolean KaramShort(int Karma, String UserId, String IDShort) {
        String query;
        if (!CheckKarma(UserId, IDShort)) {
            query = "INSERT INTO Karma (karma, IDChanel, IDShort) VALUES (?, ?, ?)";
        } else {
            query = "UPDATE Karma SET karma = ? WHERE IDUser = ? AND IDShort = ?";
        }

        StartConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, Karma);
            statement.setString(2, UserId);
            statement.setString(3, IDShort);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }

    public static boolean CheckKarmaShort(String UserId, String Video) {
        StartConnection();
        String query;
        boolean exists;

        query = "SELECT COUNT(*) FROM Karma WHERE IDVChanel" +
                " = ? AND IDVideo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, UserId);
            statement.setString(2, Video);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            exists = count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        finally {
            EncConnection();
        }

        return exists;
    }


    /**
     * delete
     */


    //this method delete a video in all playlist and history and chanel
    public static void DE_Video(String idV, String idC) {
        StartConnection();
        delete_Video_chanel(idV, idC);
        delete_Video_history(idV);
        delete_Video_Playlist(idV);
        delete_Video(idV);
//        delete_Video_category(idV);
        EncConnection();
    }

    private static void delete_Video_Playlist(String IDV) {
        String query = "DELETE FROM video_playlist WHERE IDvideo ='%s'  ";
        query = String.format(query, IDV);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void delete_Video_chanel(String IDV, String IDC) {
        String query = "DELETE FROM video_chanel WHERE ID_video ='%s' AND    ID_chanel='%s' ";
        query = String.format(query, IDV, IDC);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void delete_Video_history(String idV) {
        StartConnection();
        String query = "DELETE FROM viode_history  WHERE IDVideo ='%s'  ";
        query = String.format(query, idV);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void delete_Video(String idV) {
        String query = "DELETE FROM video  WHERE ID_video ='%s'  ";
        query = String.format(query, idV);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    private static  void delete_Video_category(String idV){
//        String query = "DELETE FROM category_video  WHERE ID_video ='%s'  ";
//        query = String.format(query, idV);
//        try {
//            statement.execute(query);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//

    /**
     * delete play list so delete playList and Video_playlist
     *
     * @param idp
     */

    public static void deletePlayList(String idp) {
        StartConnection();
        String query = "DELETE FROM playlist WHERE ID_Playlist ='%s'";
        String query1 = "DELETE FROM video_playlist WHERE ID_playlist ='%s'";
        query = String.format(query, idp);
        query1 = String.format(query1, idp);
        try {
            statement.execute(query);
            statement.execute(query1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            EncConnection();
        }
    }


    //this method delete all comments in vidoe
    public static void delete_Comment_ALL(String IDV) {

        StartConnection();
        String query = "DELETE FROM comment WHERE ID_video='%s'  ";
        query = String.format(query, IDV);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            EncConnection();
        }
    }


    //this method delete comment for writer
    public static void delete_Comment_writer(String IDV, String IDCom) {
        StartConnection();
        String query = "DELETE FROM comment WHERE wirter ='%s'  AND IDcommet='%s'";
        query = String.format(query, IDV, IDCom);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            EncConnection();
        }
    }

    //Only the person who posted the video can delete comment
    public static void delete_comment_User(String IDU, String IDCom) {
        StartConnection();
        String query = "DELETE FROM comment WHERE UserID ='%s'  AND IDcommet='%s'";
        query = String.format(query, IDU, IDCom);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            EncConnection();
        }

    }


    public static boolean deleteFormHistory(String IDU, String IDV, String action, int Min) {
        StartConnection();
        String query = "";

        if (action.equals("1")) {
            query = "DELETE FROM viode_history WHERE IDVideo = ? AND idUser = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, IDV);
                ps.setString(2, IDU);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (action.equals("2")) {
            query = "DELETE FROM viode_history WHERE idUser = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, IDU);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (action.equals("3")) {
            query = "SELECT * FROM viode_history WHERE idUser = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, IDU);
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getString("Time"));
                        int hourDiff = LocalDateTime.now().getMinute() - localDateTime.getMinute();
                        System.out.println(hourDiff);
                        if (hourDiff > Min) {
                            String deleteQuery = "DELETE FROM viode_history WHERE IDVideo = ? AND idUser = ?";
                            try (PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {
                                deletePs.setString(1, resultSet.getString("IDVideo"));
                                deletePs.setString(2, resultSet.getString("idUser"));
                                deletePs.executeUpdate();
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        EncConnection();
        return true;
    }

    public static boolean UnFollow(String IDU, String IDC, int Identifier) {
        StartConnection();
        String query;
        if (Identifier == 1) {
            query = "DELETE FROM follower WHERE IDuser='%s' AND IDChanel='%s'";
        } else
            query = "DELETE FROM following  WHERE IDuser='%s'AND IDChanel='%s'";
        query = String.format(query, IDU, IDC);

        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }finally {
            EncConnection();
        }

        return true;


    }


    /**
     * update method
     */

    public static boolean CH_PassWordUser(String IDU, String PassWord) {//TODO check
        StartConnection();


        String query = "UPDATE User SET passWord = ? WHERE IDuser = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, PassWord);
            statement.setString(2, IDU);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }

        return true;
    }


    public static boolean CH_UserName(String Username, String NewUsername) {
        UP_witter_Comment(Username, NewUsername);
        StartConnection();
        String query1 = "UPDATE chanel SET username  = ? WHERE username =?";

        try (PreparedStatement statement = connection.prepareStatement(query1)) {
            statement.setString(1, Username);
            statement.setString(2, NewUsername);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;

    }


    //change the name of Playlist
    public static synchronized boolean UP_Name_Playlist(String name, String IDP) {//TODO CHECK
        StartConnection();
        String query = "UPDATE playList SET name ='%s'  WHERE ID_Playlist='%s'";
        query = String.format(query, name, IDP);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            EncConnection();
        }
        return false;

    }

    public static synchronized boolean UP_view_vidoe(String IDV, String IDC) {//TODO CHECK

        StartConnection();

        String category = getCategoryVideo(IDV);
        UP_Trending(IDC, category, 1);


        String query = "UPDATE video SET view=view+1  WHERE ID_vidoe ='%s'";
        query = String.format(query, IDV);
        try {
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }

    public static synchronized String getCategoryVideo(String IDV) {
        String query = "SELECT  * From category_video WHERE ID_video='%s'";
        query = String.format(query, IDV);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                return resultSet.getString("category");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            EncConnection();
        }
        return null;
    }

    //--------check method
    public static boolean CheckEmailUser(String Email) {  //correct
        StartConnection();
        String query = "SELECT COUNT(*) FROM user WHERE Email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, Email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            EncConnection();
        }
        return false;
    }

    public static boolean CheckUserName(String Usernaem) {  //correct
        StartConnection();
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, Usernaem);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            EncConnection();
        }
        return false;
    }

    public static boolean CheckUserPass_Email(String Email, String Pass) {//TODO check
        StartConnection();
        String query = "SELECT COUNT(*) FROM user WHERE Email = ? And passWord=? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, Email);
            statement.setString(2, Pass);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            EncConnection();
        }
        return false;
    }

    public static boolean ChecksaveVideo(String IDV, String IDU) {//TODO check
        StartConnection();
        String query = "SELECT COUNT(*) FROM savevidoe WHERE IDVideo=? AND IDUser=? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, IDV);
            statement.setString(2, IDU);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            EncConnection();
        }
        return false;
    }

    public static boolean ChecksavePlayllist(String IDP, String IDU) {//TODO check
        StartConnection();
        String query = "SELECT COUNT(*) FROM saveplaylist WHERE IDUser=? AND IDPalyList=? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, IDU);
            statement.setString(2, IDP);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            EncConnection();
        }
        return false;
    }


    /**
     * update comment
     *
     * @param newwitter
     * @param witter
     * @return
     */
    public static synchronized boolean UP_witter_Comment(String newwitter, String witter) {//TODO CHECK
        StartConnection();
        String query = "UPDATE comment  SET wirter ='%s'  WHERE wirter = '%s'";
        query = String.format(query, newwitter, witter);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;

    }

    public static synchronized boolean UP_Like_Comment(String IDC) {//check  true
        StartConnection();
        String query = "UPDATE comment  SET LikeComment = LikeComment + 1  WHERE IDcommet = '%s'";
        query = String.format(query, IDC);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }

    public static boolean UP_DisLike_Comment(String IDU) {//check true
        StartConnection();
        String query = "UPDATE comment SET dislike = dislike + 1 WHERE IDcommet = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, IDU);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
    }

    public static synchronized boolean UP_Like_Vidoe(String IDV, String IDC) {//check  true
        String category = getCategoryVideo(IDV);
        UP_Trending(IDC, category, 5);
        StartConnection();

        String query = "UPDATE vidoe  SET 'like' = 'like' + 1  WHERE ID_video = '%s'";
        query = String.format(query, IDV);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }

    public static synchronized boolean UP_DisLike_Vidoe(String IDV, String IDC) {//check  true
        String category = getCategoryVideo(IDV);
        UP_Trending(IDC, category, 2);
        StartConnection();
        String query = "UPDATE vidoe  SET Dis_Like = Dis_Like + 1  WHERE ID_video = '%s'";
        query = String.format(query, IDV);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }

    public static boolean UP_Comment_Comment(String IDC, String comment) {//check true
        StartConnection();
        String query = "UPDATE comment SET comment='%s' WHERE IDcommet = '%s'";
        query = String.format(query, comment, IDC);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }

    /**
     * Update chanel
     */

    public static synchronized boolean UP_Name_Chanel(String IDC, String name) {//TODO check
        StartConnection();
        String query = "UPDATE chanel SET Name='%s' WHERE ID_chanel = '%s'";
        query = String.format(query, name, IDC);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }


        return true;
    }


    public static synchronized boolean UP_information_Chanel(String information, String IDC) {//TODO check it
        StartConnection();
        String query = "UPDATE chanel SET Name='%s' WHERE ID_chanel = '%s'";
        query = String.format(query, information, IDC);
        try {
            statement.execute(query);
        } catch (SQLException r) {
            r.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }

    public static synchronized boolean UP_Link_Chanel(String Link, String IDC) {//TODO Check it
        StartConnection();
        String query = "UPDATE chanel SET Link = Link + '#%s' WHERE IDcommet = ?";
        try {
            query = String.format(query, Link, IDC);
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            return true;
        }
    }


    public static synchronized boolean UP_Username_Chanel(String Username, String IDC) {
        StartConnection();
        String query = "UPDATE chanel SET username='%s' WHERE ID_chanel = '%s'";
        try {
            query = String.format(query, Username, IDC);
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }


    /**
     * check method
     */


    /**
     * Search  in data base
     */
    public static synchronized ArrayList<Video> SE_video(String Word, String Way) {
        StartConnection();
        ArrayList<Video> Videos = new ArrayList<>();
        String query;
        ResultSet resultSet;
        try {
            if (Way.equals("information")) {
                query = "SELECT * " +
                        "FROM Video " +
                        " WHERE  information REGEXP ? ";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                resultSet = statement.executeQuery();
            } else if (Way.equals("name")) {
                query = "SELECT * " +
                        "FROM Video " +
                        " WHERE  name REGEXP ? ";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                resultSet = statement.executeQuery();
            } else {
                query = "SELECT * " +
                        "FROM Video " +
                        " WHERE  information REGEXP ? "
                        + "OR name REGEXP ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                statement.setString(2, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                resultSet = statement.executeQuery();
            }


            // Process the results
            while (resultSet.next()) {
                Videos.add(new Video(resultSet.getString("ID_video"),
                        resultSet.getString("Chanel_ID"),
                        resultSet.getString("name"),
                        resultSet.getString("information"),
                        resultSet.getString("time_uplode"),
                        resultSet.getInt("PlayTime"),
                        resultSet.getInt("view"),
                        resultSet.getString("Block"),
                        resultSet.getString("category")

                ));

            }


        } catch (SQLException r) {
            r.printStackTrace();
        } finally {
            EncConnection();
        }
        return Videos;
    }


    //get chanel by name informatin and
    public static synchronized ArrayList<Channel> SE_Chanel(String Word, String Way) {//CHECK IT
        StartConnection();
        ArrayList<Channel> Chanel = new ArrayList<>();
        String query;
        ResultSet resultSet;

        try {
            if (Way.equals("informatin")) {
                query = "SELECT * " +
                        "FROM Chanel " +
                        " WHERE  information REGEXP ? ";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                resultSet = statement.executeQuery();
            } else if (Way.equals("name")) {
                query = "SELECT * " +
                        "FROM Chanel " +
                        " WHERE  name REGEXP ? ";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                resultSet = statement.executeQuery();
            } else {
                query = "SELECT * " +
                        "FROM Chanel " +
                        " WHERE  information REGEXP ? "
                        + "OR name REGEXP ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                statement.setString(2, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                resultSet = statement.executeQuery();
            }


            // Process the results
            while (resultSet.next()) {
                Chanel.add(new Channel(resultSet.getString("ID_chanel"),
                        resultSet.getString("name"),
                        resultSet.getString("information"),
                        resultSet.getString("imageChanel"),
                        resultSet.getString("username"),
                        resultSet.getString("imagePro"),
                        resultSet.getString("link")
                ));

            }


        } catch (SQLException r) {
            r.printStackTrace();
        } finally {
            EncConnection();
        }
        return Chanel;
    }

    public static synchronized ArrayList<PlayList> SE_playLists(String Word, String Way) {
        String query;
        ResultSet resultSet;
        ArrayList<PlayList> playLists = new ArrayList<>();
        StartConnection();
        try {
            if (Way.equals("name")) {
                query = "SELECT * " +
                        "FROM playList " +
                        " WHERE  name REGEXP ? ";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                resultSet = statement.executeQuery();
            } else if (Way.equals("discribe")) {
                query = "SELECT * " +
                        "FROM playList " +
                        " WHERE  discribe REGEXP ? ";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                resultSet = statement.executeQuery();
            } else {
                query = "SELECT * " +
                        "FROM playList " +
                        " WHERE  discribe REGEXP ? " +
                        "OR name REGEXP ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                statement.setString(2, "([A-Z]|[0-9])?" + Word + "([A-Z]|[0-9])?");
                resultSet = statement.executeQuery();
            }
            while (resultSet.next()) {
                playLists.add(new PlayList(
                        resultSet.getString("name"),
                        resultSet.getString("ID_Playlist"),
                        resultSet.getString("ID_chanel"),
                        resultSet.getString("discribe"),
                        resultSet.getString("image")
                ));
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            EncConnection();
        }


        return playLists;

    }

    /**
     * Save Play list
     */

    public static synchronized boolean SA_playlist(String IDP, String IDU) {//TODO check it
        StartConnection();
        String query = "INSERT INTO savePlaylist (IDUser,IDPalyList) VALUSE ('%s','%s')";
        query = String.format(query, IDU, IDP);
        try{
            statement.execute(query);
        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            EncConnection();
        }
        return true;
    }
    public static synchronized boolean SA_Video(String IDV, String IDU) {//TODO check it
        StartConnection();
        String query = "INSERT INTO saveVidoe (IDVideo,IDUser) VALUSE ('%s','%s')";
        query = String.format(query, IDV, IDU);

        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            EncConnection();
        }
        return true;
    }

    /**
     * udpate the taste table
     */


    public static synchronized boolean UP_Trending(String ID, String cat, int value) {
        StartConnection();
        String query = "Update category_user set " + cat + " = " + cat + " +" + value + " Where IDChanel ='%s' ";
        query = String.format(query, ID, cat);
        try {
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            EncConnection();
        }

        return true;
    }

    public static synchronized boolean ADD_Trending(String ID) {
        StartConnection();
        String query = "INSERT into category_user (IDChanel,Sport,news,LifeStyle,Comedy,Education,Autosandveh,TravelandEvents,Gaming,Science_Technology,Other) " +
                "VALUES  ('%s',0,0,0,0,0,0,0,0,0,0)";
        query = String.format(query, ID);
        try {
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            EncConnection();
        }

        return true;
    }
    public static synchronized int countVideoLike(String IDV,String K) {
        int number = 0;
        StartConnection();

        String query = "SELECT COUNT(*) FROM karma WHERE IDVideo=? AND Karma=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, IDV);
            statement.setString(2, K);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    number = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        EncConnection();
        return number;
    }
    public static synchronized int countShortLike(String IDV,String K) {
        int number = 0;
            StartConnection();

        String query = "SELECT COUNT(*) FROM karmashort WHERE IDShort=? AND Karma=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, IDV);
            statement.setString(2, K);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    number = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            EncConnection();
        }
        return number;
    }

    public static synchronized int countChanelVideo(String IDV) {
        int number = 0;
        StartConnection();

        String query = "SELECT COUNT(*) FROM video_chanel WHERE ID_chanel=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, IDV);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    number = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return  number;
    }
    public static synchronized int countfollowing(String IDV) {
        int number = 0;
        StartConnection();

        String query = "SELECT COUNT(*) FROM following WHERE ID_chanel=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, IDV);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    number = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return  number;
    }
    public static synchronized int countfollower(String IDV) {
        int number = 0;
        StartConnection();

        String query = "SELECT COUNT(*) FROM follower WHERE IDChanel=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, IDV);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    number = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            EncConnection();
        }
        return  number;
    }
}
