package cn.trpc.rpc.protocol.trpc.codec;

import cn.trpc.common.serialize.Serialization;
import cn.trpc.common.tools.ByteUtil;
import cn.trpc.remoting.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: trpc
 * @ClassName: TrpcCodec
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:20
 * @Description: trpc编解码
 * @Version: V1.0
 */
public class TrpcCodec implements Codec {

    private static final byte[] MAGIC = new byte[]{(byte) 0xda, (byte) 0xbb}; // 定义协议头
    private static final int HEAD_LEN = 6;  // 协议头长度

    private ByteBuf tmpMsg = Unpooled.buffer(); // 临时存储报文

    private Serialization serialization; // 序列化方式

    private Class classType; // 解码类对象

    /* 编码 */
    @Override
    public byte[] encode(Object out) throws Exception {
        byte[] bytes = (byte[]) out;

        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(MAGIC[0]);
        buf.writeByte(MAGIC[1]);
        buf.writeBytes(ByteUtil.int2bytes(bytes.length));
        buf.writeBytes(bytes);

        byte[] content = new byte[buf.readableBytes()];
        buf.readBytes(content);

        return content;
    }

    /* 解码 */
    @Override
    public List<Object> decode(byte[] data) throws Exception {
        List<Object> out = new ArrayList<>();
        ByteBuf msg = Unpooled.buffer();
        /* 1. 合并报文 */
        if (tmpMsg.readableBytes() > 0) {
            msg.writeBytes(tmpMsg);
        }
        msg.writeBytes(data);

        for ( ; ; ) {
            /* 2. 判断是否满足一次请求长度 */
            if (HEAD_LEN >= msg.readableBytes()) {
                tmpMsg.clear();
                tmpMsg.writeBytes(msg);
                return out;
            }

            /* 3. 是否满足协议约定 */
            byte[] magic = new byte[2];
            msg.readBytes(magic);
            for ( ; ; ) {
                if (MAGIC[0] != magic[0] || MAGIC[1] != magic[1]) {
                    /* 3.1 是否报文数据已全部读取完毕 */
                    if (msg.readableBytes() == 0) {
                        tmpMsg.clear();
                        tmpMsg.writeByte(magic[1]);
                        return out;
                    }
                    magic[0] = magic[1];
                    magic[1] = msg.readByte();
                } else {
                    break;
                }
            }

            /* 4. 获取报文长度 */
            byte[] lenBytes = new byte[4];
            msg.readBytes(lenBytes);
            int len = ByteUtil.bytes2int(lenBytes);

            /* 5. 获取报文 */
            /* 5.1 判断剩余报文长度，是否是一个完整的报文 */
            if (msg.readableBytes() < len) {
                tmpMsg.clear();
                tmpMsg.writeBytes(magic);
                tmpMsg.writeBytes(lenBytes);
                tmpMsg.writeBytes(msg);
                return out;
            }
            byte[] bodyBytes = new byte[len];
            msg.readBytes(bodyBytes);
            Object body = this.serialization.deserialize(bodyBytes, classType);
            out.add(body);
        }
    }

    /* 初始化 */
    @Override
    public Codec getInstance() {
        TrpcCodec codec = new TrpcCodec();
        codec.setSerialization(this.serialization);
        codec.setClassType(this.classType);
        return codec;
    }

    public Serialization getSerialization() {
        return serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }
}
