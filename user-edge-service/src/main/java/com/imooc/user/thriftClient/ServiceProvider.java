package com.imooc.user.thriftClient;

import com.imooc.thrift.message.MessageService;
import om.imooc.thrift.user.UserService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 客户端thrift连接配置
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.ip}")
    private String serverIp;

    @Value("${thrift.user.port}")
    private int serverPort;

    @Value("${thrift.message.ip}")
    private String serverMessageIp;

    @Value("${thrift.message.port}")
    private int serverMessagePort;

    public UserService.Client getUserService(){
        TProtocol tProtocol = getTProtocol(serverIp , serverPort);
        UserService.Client client = new UserService.Client(tProtocol);
        return client;
    }

    public MessageService.Client getMessageService(){
        TProtocol tProtocol = getTProtocol(serverMessageIp , serverMessagePort);
        MessageService.Client client = new MessageService.Client(tProtocol);
        return client;
    }

    /**
     * 根据ip和端口号获取数据
     * @param ip
     * @param port
     * @return
     */
    public TProtocol getTProtocol(String ip , int port){
        TSocket socket = new TSocket(ip,port,10000);
        TTransport tTransport = new TFastFramedTransport(socket);
        try {
            tTransport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol tProtocol = new TBinaryProtocol(tTransport);
        return tProtocol;
    }
}
