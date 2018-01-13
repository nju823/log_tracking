package nju.edu.cn.log.log_tracking.http_wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletRequestWrapper;  
  
import org.apache.commons.lang.StringUtils;  
  
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {  
    private final byte[] body;    
      
    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request)  
            throws IOException {    
        super(request);    
        body = readBytes(request.getReader(), "utf-8");  
    }    
    
    @Override    
    public BufferedReader getReader() throws IOException {    
        return new BufferedReader(new InputStreamReader(getInputStream()));    
    }    
    
    @Override    
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais ;
        if(body!=null){
            bais = new ByteArrayInputStream(body);
        }else{
            bais=new ByteArrayInputStream(new byte[1]);
        }

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {    
                return bais.read();    
            }    
        };    
    }   
      
    /** 
     * 通过BufferedReader和字符编码集转换成byte数组 
     * @param br 
     * @param encoding 
     * @return 
     * @throws IOException  
     */  
    private byte[] readBytes(BufferedReader br,String encoding) throws IOException{  
        String str = null,retStr="";  
        while ((str = br.readLine()) != null) {  
            retStr += str;  
        }  
        if (StringUtils.isNotBlank(retStr)) {  
             return retStr.getBytes(Charset.forName(encoding));  
        }  
        return null;  
    }  
      
}  