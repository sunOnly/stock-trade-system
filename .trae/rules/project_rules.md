# trading-system AI编程项目规则
## 一、架构规范
​​分层结构​​：
Controller：处理 HTTP 请求，调用 Service 层
Service：业务逻辑层，使用 @Service 注解
DAO：数据访问层，继承 MyBatis-Plus 的 BaseMapper
​​微服务交互​​：
服务间调用使用 OpenFeign
事件驱动使用 RocketMQ 消息队列
跨服务事务使用 Seata
## 二、命名规范
包命名：com.stock.xxx
​​Java 类​​：
Controller：XxxController
Service：XxxServiceImpl（实现类） + XxxService（接口）
Mapper：XxxMapper
实体：XxxDO（数据库对象）、XxxDTO（传输对象）、XxxVO（视图对象）
​​方法命名​​：
查询：getXxx、listXxxs、pageXxxs
新增：createXxx
修改：updateXxx
删除：deleteXxx
## 三、API 设计规范
​​RESTful 接口​​：
@GetMapping("/{id}")
public CommonResult<XxxVO> get(@PathVariable Long id) { ... }

@PostMapping
public CommonResult<Long> create(@Valid @RequestBody XxxCreateReq req) { ... }
​​响应格式​​：
{
  "code": 200,
  "msg": "成功",
  "data": {...}
}
## 四、数据库规范
​​表设计​​：
必须包含字段：id（主键）、create_time、update_time
删除使用逻辑删除：deleted（0-未删除，1-已删除）
​​MyBatis-Plus 使用​​：
@TableName("sys_user")
public class UserDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    // 其他字段...
}
## 五、安全规范
​​权限控制​​：
接口添加 @PreAuthorize 注解
@PreAuthorize("@ss.hasPermission('system:user:update')")
@PutMapping
public CommonResult<Boolean> update(...) { ... }
​​敏感数据处理​​：
密码字段使用 @EncryptField 注解自动加密
日志脱敏处理
## 六、异常处理
​​全局异常处理​​：
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public CommonResult<?> handle(ServiceException ex) {
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }
}
​​自定义异常​​：
public class ServiceException extends RuntimeException {
    private final Integer code;
    // 构造方法...
}
## 七、日志规范
​​日志格式​​：
pattern=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
​​日志级别​​：
INFO：业务操作日志
DEBUG：调试信息
WARN：可恢复异常
ERROR：系统错误
## 八、测试规范
​​单元测试​​：
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    
    @Test
    public void testGetUser() {
        UserDO user = userService.getUser(1L);
        assertNotNull(user);
    }
}
## 九、Git 提交规范
​​Commit Message格式​​：
<类型>: <描述>
类型：feat（新功能）、fix（修复）、docs（文档）、style（格式）、refactor（重构）、test（测试）
​​分支管理​​：
master：稳定分支
develop：开发分支
feature/xxx：功能分支
## 十、AI 辅助编程指南
​​提示词示例​​：
请按照 yudao 规范生成一个用户管理模块的 RESTful 接口，包含：
- 用户分页查询
- 用户创建接口
- 使用 MyBatis-Plus 实现
- 添加权限控制注解
- 异常处理
- 日志记录
- 单元测试