/**
* 生成信息服务thrift接口的文件，使用本地的thrift命令生成
* idea中集成太麻烦了，没做出来
* @2wl 2019-02-12
**/
namespace java com.imooc.thrift.message
namespace py meaasge.api

service MessageService{
    bool sendMobileMessage(1:string phone, 2:string message);
    bool sendEmailMessage(1:string email, 2:string message);
}