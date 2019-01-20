package core.aws.task.iam;

import core.aws.client.AWS;
import core.aws.env.Context;
import core.aws.resource.iam.Role;
import core.aws.util.Threads;
import core.aws.workflow.Action;
import core.aws.workflow.Task;

import java.time.Duration;

/**
 * @author mort
 */
@Action("create-iam-role")
public class CreateRoleTask extends Task<Role> {
    public CreateRoleTask(Role role) {
        super(role);
    }

    @Override
    public void execute(Context context) throws Exception {
        resource.remoteRole = AWS.getIam().createRole(resource.path, resource.name, resource.assumeRolePolicyDocument);
        // wait role to be available
        Threads.sleepRoughly(Duration.ofSeconds(10));
    }
}
