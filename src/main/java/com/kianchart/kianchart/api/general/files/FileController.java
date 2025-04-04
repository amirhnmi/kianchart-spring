package com.kianchart.kianchart.api.general.files;

import com.kianchart.kianchart.core.enums.FileType;
import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.response.CustomResponseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping("/file/list")
    public CustomResponseEntity<List<FileDTO.Response>> getAllFile(
            @RequestParam FileType fileType,
            @RequestParam(defaultValue = "asc")SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total
    ){
        List<FileDTO.Response> files = fileService.getAllFile(sort,skip,limit,fileType);
        Long totalValue = total ? fileService.countAllFile(fileType) : null;
        return CustomResponseEntity.showList(files, totalValue);
    }

    @PostMapping(value = "/file/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CustomResponseEntity<FileDTO.Response> createFile(
            @RequestParam("fileType") FileType fileType,
            @RequestParam("file") MultipartFile file,
            @RequestParam("creatorId") Long creatorId
    ) throws IOException {
        FileDTO.Response fileResponse = fileService.createFile(file,creatorId,fileType);
        return CustomResponseEntity.showDetail(fileResponse);
    }

    @DeleteMapping("/file/{id}/delete")
    public ResponseEntity<String> deleteFile(@PathVariable Long id){
        fileService.deleteFile(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
