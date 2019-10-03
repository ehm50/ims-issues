package org.digam.issues.boundary;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.digam.issues.entity.Issue;


@Path("issues")
public class IssuesResource {

	@Inject
	private IssuesService service;

	@GET
	public Response getAll() {
		List<Issue> issues = service.getAll();
		GenericEntity<List<Issue>> list = new GenericEntity<List<Issue>>(issues) {
		};
		return Response.ok(list).build();
	}

	@GET
	@Path("{id}")
	public Response get(@PathParam("id") Long id) {
		final Optional<Issue> issueFound = service.get(id);
		if (issueFound.isPresent()) {
			return Response.ok(issueFound.get()).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(Issue newIssue, @Context UriInfo uriInfo) {
		service.add(newIssue);
		return Response.created(getLocation(uriInfo, newIssue.getId())).build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, Issue updated) {
		updated.setId(id);
		boolean done = service.update(updated);

		return done ? Response.ok(updated).build() : Response.status(Response.Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("{id}")
	public Response remove(@PathParam("id") Long id) {
		service.remove(id);
		return Response.ok().build();
	}

	URI getLocation(UriInfo uriInfo, Long id) {
		return uriInfo.getAbsolutePathBuilder().path("" + id).build();
	}
}