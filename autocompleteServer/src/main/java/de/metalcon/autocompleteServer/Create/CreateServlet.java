package de.metalcon.autocompleteServer.Create;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.metalcon.autocompleteServer.Command;

public class CreateServlet extends HttpServlet {

	private final BlockingQueue<Object> responseQueue = new LinkedBlockingQueue<Object>(
			1);

	private BlockingQueue<Command> commandQueue;

	public CreateServlet() {
	}

	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);

		final ServletContext context = config.getServletContext();
		this.commandQueue = (BlockingQueue<Command>) context
				.getAttribute("queue");
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		ProcessCreateResponse resp = ProcessCreateRequest.handleServlet(
				request, this.getServletContext());

		final CreateRequestContainer container = resp.getContainer();

		if (container != null) {
			// stack command
			container.setRequestServlet(this);
			this.commandQueue.add(container);

			// wait for the command to be finished
			try {
				this.responseQueue.take();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			// response.setContentType("text/json");
			response.getWriter().write(resp.getResponse().toJSONString());
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void commandFinished() {
		this.responseQueue.add(new Object());
	}
}
