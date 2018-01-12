package nju.edu.cn.log.log_tracking.http_wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class WrapperOutputStream extends ServletOutputStream{
    private ByteArrayOutputStream bos;

    public WrapperOutputStream(ByteArrayOutputStream bos) {
        this.bos = bos;
    }

    @Override
    public void write(int b) throws IOException {
        bos.write(b);
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}