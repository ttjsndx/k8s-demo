# coding=utf-8
from message.api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import smtplib
from email.mime.text import  MIMEText
from email.header import Header

sender = "18157146511@163.com"
authCode = 'wyyxsqmwwl123'
class MessageServiceHandler:

    def send_sendMobileMessage(self, phone, message):
        print "没钱做不了真的，告辞！" + phone
        return True

    def sendEmailMessage(self, email, message):
        print "sendEmailMessage, email:" + email + ", message:" + message
        messageObj = MIMEText(message, "plan", "utf-8")
        messageObj['From'] = sender
        messageObj['To'] = email
        messageObj['Subject'] = Header('python-thrift基础服务搭建', 'utf-8')
        try:
            smtpObj = smtplib.SMTP('smpt.163.com')
            smtpObj.login(sender, authCode)
            smtpObj.sendmail(sender, [email], messageObj.as_string())
            print "send mail success!"
            return True
        except smtplib.SMTPException, ex:
            print "send mail failed!"
            print ex.message
            return False


if __name__ == '__main__':
    # ====================================================
    # thrift.transport thrift的传输建立模块
    # - TSocket 客户端的socket transport实现，需要指定ip及端口
    # - TTransport 传输模块的基类
    # thrift.protocol thrift的传输协议模块
    # - TBinaryProtocol 按照二进制传输
    # - TJSONProtocol 按照json传输
    # thrift.server thrift 启动服务的模块
    # - TSimpleServer 实现单线程阻塞的服务器
    # - TThreadedServer 为每一个请求新开一个线程
    # - TNonblockingServer NIO高级非阻塞模式，不会为每一个请求新开一个线程，将多个请求分配到一个线程中，只维护少量线程
    # ====================================================

    # 指明MessageService中的两个虚拟方法的由MessageServiceHandler实现
    handler = MessageServiceHandler()
    processor = MessageService.Processor(handler)
    # 指定传输端口
    transport = TSocket.TServerSocket("localhost", "9090")
    # 设置 按帧（frame）传输
    tFactory = TTransport.TFramedTransportFactory()
    # 使用二进制传输数据
    pFactory = TBinaryProtocol.TBinaryProtocolFactory()

    # 建立一个单线程阻塞的通信服务。基本只适用于测试，生产中使用 TThreadedServer或者TNonblockingServer
    # TSimpleServer参数说明
    # - processor 消息的处理者
    # - transport 消息的监听者
    # - tFactory 传输的方式
    # - pFactory 传输的协议
    server = TServer.TSimpleServer(processor, transport, tFactory, pFactory)
    print "python thrift server start"
    server.serve()
    print "python thrift server exit"
