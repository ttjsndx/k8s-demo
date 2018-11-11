namespace java com.imooc.thrift.message
namespace py meaasge.api

service MessageService{
    bool sendMobileMessage(1:string phone, 2:string message);
    bool sendEmailMessage(1:string email, 2:string message);
}