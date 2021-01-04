package cn.trpc.rpc;

/**
 * @Program: temp
 * @ClassName: Response
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-19 22:21
 * @Description: 响应对象
 * @Version: V1.0
 */
public class Response {

    private long requestId; // 请求ID
    private int status; // 状态 99：异常 200：正常
    private Object content; // 响应内容

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static Response response_ok(Object content, long requestId) {
        Response response = new Response();
        response.setRequestId(requestId);
        response.setStatus(200);
        response.setContent(content);
        return response;
    }

    public static Response response_fail(Object content,long requestId) {
        Response response = new Response();
        response.setRequestId(requestId);
        response.setStatus(99);
        response.setContent(content);
        return response;
    }

    @Override
    public String toString() {
        return "Response{" +
                "requestId=" + requestId +
                ", status=" + status +
                ", content=" + content +
                '}';
    }
}
