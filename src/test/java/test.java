import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.frws.file.ByteFileObj;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.frws.sdk.core.obj.UploadParamObj;
import com.wckj.jdone.modules.act2.service.JdoneAct2InstanceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:zengrk
 */
public class test {
    @Autowired
    private JdoneAct2InstanceService jdoneAct2InstanceService;
    public static void main(String[] args) throws Exception{
            URL url = new URL("https://img2.baidu.com/it/u=3853345508,384760633&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1200");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.connect();
            InputStream cin = httpConn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = cin.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            cin.close();
            byte[] fileData = outStream.toByteArray();
            UploadParamObj paramObj = new UploadParamObj();
            paramObj.setOrgSysCode("320900000000");
            paramObj.setOrgName("**省**市**盐城**局");
            paramObj.setBizType("rlzp");
            String zpid = StringUtils.getGuid32();
            paramObj.setBizId(zpid);
            FrwsApiForThirdPart.uploadByteFile(new ByteFileObj(StringUtils.getGuid32() + ".png", fileData), paramObj);
            FileInfoObj fileInfo = FrwsApiForThirdPart.getFileInfoByBizIdBizType(zpid, "rlzp");
            String rlzpUrl = fileInfo.getDownUrl();
            System.out.println(rlzpUrl);
       // Map<String, Object> result=new HashMap<>();
//        Date now=new Date();
//        long hour = 1000*60*60;
//        if(now.getTime()>(Double.valueOf(2)-Double.valueOf(0.1))*hour){
//            Double v=(Double.valueOf(0.2)-Double.valueOf(0.1))*hour;
//            long l = v.longValue();
//            //System.out.println(new Long((Double.valueOf(0.2)-Double.valueOf(0.1))));
//            System.out.println((Double.valueOf(0.2)-Double.valueOf(0.1)*hour));
//            System.out.println("成功");
        }
}

