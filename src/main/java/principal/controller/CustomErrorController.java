package principal.controller;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import principal.servicio.implementacion.FileStorageServiceImpl;

@Controller
public class CustomErrorController implements ErrorController {

	@Autowired
	FileStorageServiceImpl storageService;


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request,Model model) {
        if(is400(request)) {
        Resource resource = storageService.loadError("400.png");
		String url = MvcUriComponentsBuilder
				.fromMethodName(CustomErrorController.class, "serveFile", resource.getFilename()).build()
				.toString();
		model.addAttribute("url",url);

        return "error";
        }
        if(is403(request)) {
        	Resource resource = storageService.loadError("403.png");
    		String url = MvcUriComponentsBuilder
    				.fromMethodName(CustomErrorController.class, "serveFile", resource.getFilename()).build()
    				.toString();
    		model.addAttribute("url",url);

            return "error";
        }
        return "redirect:/";
    }

    private boolean is400(HttpServletRequest request)  {
        return HttpStatus.BAD_REQUEST
                .value() == (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    }
    
    private boolean is403(HttpServletRequest request) {
    	return HttpStatus.FORBIDDEN.value()== (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    }

	  @GetMapping("/images/{filename:.+}")
	  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		    Resource file = storageService.loadError(filename);

		    return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
		  }
}
