2026-11-09 说明（优先级5）：
原 .classpath 直接引用了作者本机的绝对路径
C:/Users/86057/Desktop/mysql-connector-j-8.2.0.jar，换机器无法构建。

该绝对路径已删除。服务端数据库功能是可选特性：
- 不放入驱动 jar 时，Sever 会以"内存转发模式"运行，公屏聊天仍可用；
- 需要使用 MySQL 时，把 mysql-connector-j-8.2.0.jar 放到本 lib 目录，
  并在 IDE 中把该 jar 加入 Build Path 即可。
