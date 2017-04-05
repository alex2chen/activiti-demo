package simple;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/2/14
 */
public class A_IT_HelloWorld {
    // 自动读取classPath里面的activiti.cfg.xml
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程
     */
    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService() // 与流程定义和部署对象相关的Service
                .createDeployment() // 创建一个部署对象
                .name("helloworld部署程序") // 设置对应流程的名称
                .addClasspathResource("bpmn/helloworld.bpmn") //一次只能加载一个文件(windows与linux下面要区分)
                .addClasspathResource("bpmn/helloworld.png") // 加载图片
                .deploy(); // 完成部署
        System.out.println("部署Id：" + deployment.getId()); // 部署Id
        System.out.println("部署名称：" + deployment.getName()); // 部署名称
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        String processDefinitionKey = "helloworld"; // 使用Key的启动，默认按照对心版本的流程定义启动
        ProcessInstance pi = processEngine.getRuntimeService() // 与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey); // 使用流程定义的Key启动流程实例，key对应helloworld.bpmn文件中的流程名称

        System.out.println("流程实例Id" + pi.getId()); // 流程实例Id
        System.out.println("流程定义Id" + pi.getProcessDefinitionId()); // 流程定义Id
    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask() {
        String assignee = "王五";
        List<Task> list = processEngine.getTaskService() // 与正在执行的任务管理相关的Service
                .createTaskQuery() // 创建任务查询对象
                .taskAssignee(assignee) // 制定个人任务查询，指定办理人
                .list();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务Id：" + task.getId());
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务的创建时间：" + task.getCreateTime());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例Id:" + task.getProcessInstanceId());
                System.out.println("执行对象Id:" + task.getExecutionId());
                System.out.println("流程定义Id:" + task.getProcessDefinitionId());
                System.out.println("#####################################################");
            }
        }
    }

    /**
     * 完成我的任务
     */
    @Test
    public void completeMyPersonalTask() {
        String taskId = "12502";
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);
        System.out.println("完成任务：任务Id" + taskId);
    }

    /**
     * 查询流程状态（判断流程正在执行还是结束）
     */
    @Test
    public void isProcessEnd() {
        String processInstanceId = "";

        ProcessInstance pi = processEngine.getRuntimeService()
                .createProcessInstanceQuery()// 创建一个流程实例查询
                .processInstanceId(processInstanceId) // 使用流程实例Id查询
                .singleResult();

        // 获取流程实例查询不到（1）
        // 或者获取流程实例历史，查询结束时间ok（2）
        if (pi == null) {
            System.out.println("流程已经结束");
        } else {
            System.out.println("流程没有结束");
        }
    }

    /**
     * 查询历史任务
     */
    @Test
    public void findHistroyTask() {
        String assignee = "张三";
        List<HistoricTaskInstance> list = processEngine.getHistoryService() // 与历史数据相关的Service
                .createHistoricTaskInstanceQuery()// 历史任务表
                .taskAssignee(assignee) // 设置对应人
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricTaskInstance hti : list) {
                System.out.println(hti.getId() + "" + hti.getName() + "" + hti.getProcessInstanceId() + " " + hti.getStartTime()
                        + "" + hti.getEndTime() + "" + hti.getDurationInMillis());
                System.out.println("#################################################");
            }
        }
    }


    /**
     * 查询历史流程实例
     */
    @Test
    public void finHistroyProcessInstance() {
        String processInstanceId = "30001";
        HistoricProcessInstance hpi = processEngine.getHistoryService() //  与历史数据相关的Service
                .createHistoricProcessInstanceQuery() // 创建流程实例查询表
                .processInstanceId(processInstanceId)
                .singleResult();

        System.out.println(hpi.getId() + "" + hpi.getProcessDefinitionId() + "" + hpi.getStartTime() + "" + hpi.getEndTime()
                + "" + hpi.getDurationInMillis());

    }

}
