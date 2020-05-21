import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URI;
import java.util.Map;

public class InitDb {

    public static final String DRIVER = "com.mysql.jdbc.Driver";

    public static void main(String[] args) {
        String username = "";
        String password = "";
        String url = "";
        for(Map.Entry entry:System.getenv().entrySet()){
            if (entry.getKey().toString().equals("spring.datasource.dynamic.datasource.master.username")) {
                username = entry.getValue().toString();
            }
            if (entry.getKey().toString().equals("spring.datasource.dynamic.datasource.master.password")) {
                password = entry.getValue().toString();
            }
            if (entry.getKey().toString().equals("spring.datasource.dynamic.datasource.master.url")) {
                url = entry.getValue().toString();
            }
        }
        try {
            URI uri = new URI(url.replace("jdbc:log4jdbc:", ""));
            String host = uri.getHost();
            int port = uri.getPort();
            Class.forName(DRIVER).newInstance();
            Connection Conn = DriverManager.getConnection
                    ("jdbc:mysql://" + host + ":" + port, username, password);
            Statement s = Conn.createStatement();
            int Result=s.executeUpdate("CREATE DATABASE IF NOT EXISTS ds_learn_business DEFAULT CHARSET utf8;");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS ds_learn_business.t_audio_record (\n" +
                    "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                    "  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                    "  `text_id` varchar(20) NOT NULL ,\n" +
                    "  `text` text NOT NULL ,\n" +
                    "  `audio_file_path` tinytext NOT NULL ,\n" +
                    "  PRIMARY KEY (`id`) USING BTREE,\n" +
                    "  UNIQUE KEY `text_id` (`text_id`) USING BTREE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT ");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS ds_learn_business.t_exam_tts (\n" +
                    "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                    "  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                    "  `exam_id` bigint(20) NOT NULL ,\n" +
                    "  `text_id` varchar(20) NOT NULL ,\n" +
                    "  `is_delete` tinyint(4) NOT NULL DEFAULT '0' ,\n" +
                    "  `text` text NOT NULL ,\n" +
                    "  PRIMARY KEY (`id`) USING BTREE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT ");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS ds_learn_business.t_learn_business (\n" +
                    "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                    "  `bus_id` int(11) NOT NULL DEFAULT '0',\n" +
                    "  `db_name` varchar(128) DEFAULT NULL,\n" +
                    "  `start_time` timestamp NULL DEFAULT NULL,\n" +
                    "  `company` varchar(80) DEFAULT NULL,\n" +
                    "  `is_delete` tinyint(4) NOT NULL DEFAULT '0',\n" +
                    "  PRIMARY KEY (`id`) USING BTREE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT");

        } catch (Exception e) {
            System.out.println("failed to init ds_learn_business" + e.getMessage());
        }
    }
}