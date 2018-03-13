1.建议使用Intellij IDEA进行开发

2.部署时可能需要修改application.properties，请查看其中的注释说明

3.run.bat进行运行，build.bat进行打包，打好的war位于target目录中

4. 打包时需要修改pom.xml，将spring-boot-starter-tomcat的scope设置为provided。