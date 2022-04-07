## 连连看
使用 springboot 写的小网页游戏   
现在已经支持网络对战功能
现在聊天室内容已经完整  

已知问题:
多个房间的设计比较困难，现在的做法是启动多个netty服务器监听不同的websocket接口。  
玩家可能在游戏途中退出，逻辑不完整



redis部分:https://www.jianshu.com/p/5596c3a4978d