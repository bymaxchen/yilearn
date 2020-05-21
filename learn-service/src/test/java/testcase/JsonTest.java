package testcase;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.dto.request.AddQuestionRequest;
import com.zhuiyi.ms.learn.dto.transfer.QuestionNodeDto;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Date;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 17:55
 **/
public class JsonTest {

    @Test
    public void test() {
//        AddQuestionRequest request = new AddQuestionRequest();
        QuestionNodeDto request = new QuestionNodeDto();

        System.out.println(JSON.toJSONString(request, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.PrettyFormat));
    }

    @Test
    public void test2() throws Exception {
        URL url = new URL("https://test-cdn.wezhuiyi.com/im/10092/1537340498989_4177_o_1cnoa4os91tnvq8fv181d1ect7s/hellow.txt");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        InputStream is = conn.getInputStream();
        OutputStream os = new FileOutputStream(new File("C:\\Users\\HZF\\Desktop\\hellow.txt"));
        byte[] bytes = new byte[1024];
        while (is.read(bytes) != -1) {
            os.write(bytes);
        }
        is.close();
        os.close();
    }

    @Test
    public void test3() throws Exception {
        Date date = new Date();
        System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));

        Date date2 = DateUtils.parseDate(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), Constants.DATE_TIME_FORMAT);
        System.out.println(date2);
    }
}
