import com.alibaba.fastjson.JSON;
import com.zhuiyi.ms.learn.entity.vo.req.AudioReqVO;
import com.zhuiyi.ms.learn.util.HttpUtil;
import okhttp3.Headers;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Base64;



public class base64Test {
    public static void main(String[] args) {

        try
        {
            File file1 = new File("./1.mp3");
            File file2 = new File("./2.mp3");
            File file3 = new File("./3.mp3");
            File file4 = new File("./4.mp3");

            byte[] bytes1 = FileUtils.readFileToByteArray(file1);
            byte[] bytes2 = FileUtils.readFileToByteArray(file2);
            byte[] bytes3 = FileUtils.readFileToByteArray(file3);
            byte[] bytes4 = FileUtils.readFileToByteArray(file4);

            Base64.Encoder enc = Base64.getEncoder();

            String encoded1 = enc.encodeToString(bytes1);
            String encoded2 = enc.encodeToString(bytes2);
            String encoded3 = enc.encodeToString(bytes3);
            String encoded4 = enc.encodeToString(bytes4);

            Headers.Builder headers = new Headers.Builder();
            headers.add("Content-Type", "application/json");
//            headers.add("Cookie", "busId=533396884");

            AudioReqVO audioReqVO = new AudioReqVO();
            audioReqVO.setMp3Data(encoded1);
            audioReqVO.setType(0);
            audioReqVO.setSessionId("test__cxb");
            HttpUtil.httpPost("http://localhost:9367/learn/api/v1/audio/save?busId=533396884", JSON.toJSON(audioReqVO), headers.build());

            audioReqVO.setMp3Data(encoded2);
            audioReqVO.setType(1);
            HttpUtil.httpPost("http://localhost:9367/learn/api/v1/audio/save?busId=533396884", JSON.toJSON(audioReqVO), headers.build());

//            audioReqVO.setMp3Data(encoded3);
//            audioReqVO.setType(1);
//            HttpUtil.httpPost("http://localhost:9367/learn/api/v1/audio/save?busId=533396884", JSON.toJSON(audioReqVO), headers.build());

            audioReqVO.setMp3Data(encoded4);
            audioReqVO.setType(2);
            audioReqVO.setMp3Hash("testwoca");

            HttpUtil.httpPost("http://localhost:9367/learn/api/v1/audio/save?busId=533396884", JSON.toJSON(audioReqVO), headers.build());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
