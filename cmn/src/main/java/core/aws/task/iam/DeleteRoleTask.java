package core.aws.task.iam;

import core.aws.client.AWS;
import core.aws.env.Context;
import core.aws.resource.iam.Role;
import core.aws.util.ToStringHelper;
import core.aws.workflow.Action;
import core.aws.workflow.Task;

/**
 * @author mort
 */
@Action("del-iam-role")
public class DeleteRoleTask extends Task<Role> {
    public DeleteRoleTask(Role resource) {
        super(resource);
    }

    @Override
    public void execute(Context context) throws Exception {
        String name = resource.remoteRole.getRoleName();
        AWS.getIam().deleteRole(name, resource.remoteRole.getPath());
    }

    @Override
    public String toString() {
        return new ToStringHelper(this)
            .add(resource.getClass().getSimpleName() + "{" + resource.id)
            .add(resource.status)
            .add("path", resource.remoteRole.getPath() + "}")
            .toString();
    }
}
