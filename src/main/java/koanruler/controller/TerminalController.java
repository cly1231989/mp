package koanruler.controller;

import koanruler.entity.*;
import koanruler.service.TerminalService;
import koanruler.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TerminalController {
	@Autowired
	private TerminalService terminalService;


    /**
     * 获取用户及下属机构的终端信息
     * @param userId
     * @return
     */
	@GetMapping("/webservice/user/{id}/ters")
    public String getTerminals(@PathVariable("id") int userId) {
       //return terminalService.getAllTerminalInfo(userId);
        long beginTime = System.currentTimeMillis();

        List terminalInfo = terminalService.getAllTerminalInfo(userId);
        return new ServiceResult1(true, System.currentTimeMillis()-beginTime, "terminalinfo", terminalInfo).toJson();
        //result.put("user", users.getUser(userId));
        //return result.toJson();
    }

    /**
     * 获取用户及下属机构的终端信息，可根据终端编号进行搜索
     * @param terNum 终端编号
     * @return 满足条件的终端数量和终端信息
     */
	@GetMapping("/terminals")
    public ResultData<TerminalUseInfo> searchTerInfo(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(name = "per_page", defaultValue = "-1") Integer countPerPage,
                                                     @RequestParam(name = "filter", defaultValue = "") String terNum,
                                                     @RequestParam(name = "sort", defaultValue = "") Integer sort,
                                                     @RequestParam(name = "bind_only", defaultValue = "false") Boolean onlyFindBoundTer){

        ResultList<TerminalUseInfo> result = terminalService.getTerminalInfo(UserUtil.getCurUser().getId(), page, countPerPage, terNum, onlyFindBoundTer);
        return new ResultData(page, countPerPage, null, null, result.getTotalCount(), result.getDataInfo());
    }

    /**
     * 添加终端
     * @param terminal 终端信息
     */
	@PostMapping("/terminals")
    public void addTerminal(@RequestBody Terminal terminal){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        terminal.setUserid(user.getId());
        terminalService.Save(terminal);
    }

    /**
     * 编辑终端
     * @param terminal 终端信息
     */
    @PutMapping("/terminals/{id}")
    public void editTerminal(@PathVariable Integer id, @RequestBody Terminal terminal){
            Terminal terminal1 = terminalService.getTerminal(id);
            terminal1.setTerminalnumber(terminal.getTerminalnumber());
            terminalService.Save(terminal1);
    }

    /**
     * 删除终端
     * @param id
     */
    @DeleteMapping("/terminals/{id}")
    public void deleteTerminal(@PathVariable Integer id) {
        terminalService.deleteTerminalById(id);
    }
}
