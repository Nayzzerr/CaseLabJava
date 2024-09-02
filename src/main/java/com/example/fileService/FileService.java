package com.example.fileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public FileEntity saveFile(FileEntity file) {
        return fileRepository.save(file);
    }

    public Optional<FileEntity> getFileById(Long id) {
        return fileRepository.findById(id);
    }
}
