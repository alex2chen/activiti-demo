package simple;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/2/14
 */
public class TaskListenerImpl implements TaskListener {
    /**
     * 用来指定任务的办理人
     */
    public void notify(DelegateTask delegateTask) {
        // 指定个人任务的办理人、也可以指定组任务的班里人
        delegateTask.setAssignee("灭绝师太");
        // 可以在用户表里面具有用户关系，然后查询后进行设置
    }

}
