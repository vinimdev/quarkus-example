package helsinque.ms;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.client.api.ClientMultipartForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;
import java.util.Map;

@ApplicationScoped
@Path("/api")
public class MultipartResource {

  @RestClient
  MultipartSpringService springService;

  @POST
  @Path("/multipart")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Map<String, String> multipart(@RestForm("files") List<FileUpload> files) {
    ClientMultipartForm multipart = ClientMultipartForm.create();
    files.forEach(f -> multipart.binaryFileUpload("files", f.fileName(), f.filePath().toString(), f.contentType()));

    return springService.springMultipartCall(multipart);
  }
}
