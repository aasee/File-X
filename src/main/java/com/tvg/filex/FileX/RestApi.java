package com.tvg.filex.FileX;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApi {

	@GetMapping("/files")
	public List<String> getUploadFiles() {
		File uploadDir = new File(Utils.getUploadDir());
		String[] files = uploadDir.list();
		return Stream.of(files).collect(Collectors.toList());
	}

	@PostMapping("/upload")
	public ResponseEntity handleUpload(@RequestParam("file") MultipartFile uploadfile) {
		if (uploadfile.isEmpty()) {
			return new ResponseEntity("please select a file!", HttpStatus.BAD_REQUEST);
		}
		try {
			uploadfile.transferTo(new File(Utils.getUploadDir() + "/" + uploadfile.getOriginalFilename()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity("Success!", HttpStatus.OK);
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> handleDownload(@PathVariable String fileName) throws FileNotFoundException {
		File file = new File(Utils.getUploadDir() + "/" + fileName);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

}
