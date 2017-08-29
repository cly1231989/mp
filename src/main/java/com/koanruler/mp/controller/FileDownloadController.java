package com.koanruler.mp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

/**
 * Created by hose on 2017/8/29.
 */
@Controller
@RequestMapping("/sys/filedown")
public class FileDownloadController {
    @RequestMapping(value = "/downurl", method = { RequestMethod.POST, RequestMethod.GET })
    public void downurl(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String fileName =  request.getParameter("filename") ;
        Map<String , Object > result = new HashMap<String , Object >() ;

        if ( fileName == null || fileName.isEmpty() )
        {
            result.put("result", -1 ) ;
            result.put("memo", "获取文件名为空") ;
            response.getWriter().print(result);
            return ;
        }

        //System.out.println( "fileName: " + fileName);
        String yearandmonthfolder = fileName.substring(0, 6);
        //System.out.println( "yearandmonthfolder: " + yearandmonthfolder);

        String datefolder = fileName.substring(6, 8);
        //System.out.println( "datefolder: " + datefolder);

        //WEB路径
        String strWebPath  	= request.getContextPath() ;
        //System.out.println( "strWebPath: " + strWebPath);

        String strServer;
        Integer port;

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");
        //应对因为进行了IP和端口映射而Redirect无效的情况,
        //如果进行了映射，需要在config.properties中定义ip和port
        try{
            strServer = bundle.getString("ip");
        }catch(MissingResourceException e){
            strServer = request.getLocalAddr() ;
        }

        try{
            port = Integer.parseInt( bundle.getString("port") );
        }catch(MissingResourceException e){
            port = request.getServerPort() ;
        }

        //System.out.println( "port: " + port);
        //System.out.println( "strServer: " + strServer);

        String webpath 		= 	"http://" + strServer + ":"  + String.valueOf(port)+
                "/download/" + yearandmonthfolder + "/" +  datefolder +
                "/" + fileName ;
        //System.out.println( "webpath: " + webpath);

        //定向
        response.sendRedirect(webpath);
    }
}
