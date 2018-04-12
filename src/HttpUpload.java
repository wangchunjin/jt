import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUpload {
    public static final String API="http://www.hankemall.com:8081/php/api/upload";
    public static void main(String[] args) throws Exception {
        String img="C://Users/MyBot/Desktop/12320170709123859.png";
        String result=uploadImg(img);
        System.out.println(result);
    }

    private static String uploadImg(String img) throws Exception {
        File imgFile=new File(img);
        URL url=new URL(API);
        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=----123456789");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        
        OutputStream os=new DataOutputStream(conn.getOutputStream());
        StringBuilder body=new StringBuilder();
        body.append("------123456789\r\n");
        body.append("Content-Disposition: form-data; name='img'; filename='"+imgFile.getName()+"'\r\n");
        body.append("Content-Type: image/jpeg\r\n\r\n");
        os.write(body.toString().getBytes());
        
        InputStream is=new FileInputStream(imgFile);
        byte[] b=new byte[1024];
        int len=0;
        while((len=is.read(b))!=-1){
            os.write(b,0,len);
        }
        String end="\r\n------123456789--";
        os.write(end.getBytes());
        
        //输出返回结果
        InputStream input=conn.getInputStream();
        byte[] res=new byte[1024];
        int resLen=input.read(res);
        return new String(res,0,resLen);
    }
}