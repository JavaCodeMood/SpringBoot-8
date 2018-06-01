测试不同配置的加载：
执行java -jar xxx.jar，可以观察到服务端口被设置为8080，也就是默认的开发环境（dev）
执行java -jar xxx.jar --spring.profiles.active=test，可以观察到服务端口被设置为9090，也就是测试环境的配置（test）
执行java -jar xxx.jar --spring.profiles.active=prod，可以观察到服务端口被设置为80，也就是生产环境的配置（prod）