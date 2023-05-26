package principal.servicio.interfaces;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
  public void init();

  public void save(MultipartFile file);

  public Resource load(String filename);
  
  public void deleteAll();

  public Stream<Path> loadAll();

void delete(String filename);

public Resource loadError(String foto);
}