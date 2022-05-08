package com.liuxinchi.hpe.controller;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.hpe.common.ApiRestResponse;
import com.liuxinchi.hpe.common.Constant;
import com.liuxinchi.hpe.exception.HpeException;
import com.liuxinchi.hpe.exception.HpeExceptionEnum;
import com.liuxinchi.hpe.model.pojo.Image;
import com.liuxinchi.hpe.model.request.UpdateImageRequest;
import com.liuxinchi.hpe.service.ImageService;
import com.liuxinchi.hpe.util.DownloadUtil;
import com.liuxinchi.hpe.util.SSHUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/image")
public class ImageController {

    private final Logger log = LoggerFactory.getLogger(ImageController.class);

    String fileDir = null;
    String fileName = null;
    String suffixName = null;
    UUID uuid = null;
    URI uri = null;

    @Autowired
    ImageService imageService;

    private URI getHost(URI uri){

        URI effectiveURI;

        try {
            effectiveURI =  new URI(uri.getScheme(),uri.getUserInfo(),uri.getHost(),uri.getPort(),null,null,null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }

        return effectiveURI;
    }

    @ResponseBody
    @PostMapping("/upload")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam(value = "file",required = false) MultipartFile multipartFile) throws HpeException {
        String originalFilename = multipartFile.getOriginalFilename();
        if(StringUtils.isEmpty(originalFilename) || !originalFilename.contains(".")){
            return ApiRestResponse.error(HpeExceptionEnum.UPLOAD_FAILED);
        }
        suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        if(!suffixName.equals(".png")&&!suffixName.equals(".jpg")){
            return ApiRestResponse.error(HpeExceptionEnum.IMAGE_FAILED);
        }

        uuid = UUID.randomUUID();
        fileName = uuid + suffixName;
        fileDir = Constant.FILE_UPLOAD_DIR + uuid + "/";
        File fileDirectory = new File(fileDir);
        File destFile = new File(fileDir + fileName);

        if(!fileDirectory.exists()){
            if(!fileDirectory.mkdir()){
                ApiRestResponse.error(HpeExceptionEnum.MKDIR_FAILED);
            }
        }

        try {
            multipartFile.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            uri = getHost(new URI(httpServletRequest.getRequestURL()+""));
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(HpeExceptionEnum.UPLOAD_FAILED);
        }
        String originlImage = uri + "/upload/" + fileName;
        imageService.addImage(uuid.toString(),originlImage);

        return ApiRestResponse.success(originlImage);
    }

    @ResponseBody
    @PostMapping("/uploadlocal")
    public ApiRestResponse uploadLoacl(HttpServletRequest httpServletRequest, @RequestParam(value = "file",required = false) MultipartFile multipartFile) throws HpeException {
        String originalFilename = multipartFile.getOriginalFilename();
        if(StringUtils.isEmpty(originalFilename) || !originalFilename.contains(".")){
            return ApiRestResponse.error(HpeExceptionEnum.UPLOAD_FAILED);
        }
        suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        if(!suffixName.equals(".png")&&!suffixName.equals(".jpg")){
            return ApiRestResponse.error(HpeExceptionEnum.IMAGE_FAILED);
        }

        uuid = UUID.randomUUID();
        fileName = uuid + suffixName;
        String uploadDir = "Z:\\hpe\\upload\\" + uuid + "\\";
        fileDir = Constant.FILE_UPLOAD_DIR + uuid + "/";
        File fileDirectory = new File(uploadDir);
        File destFile = new File(uploadDir + fileName);

        if(!fileDirectory.exists()){
            if(!fileDirectory.mkdir()){
                ApiRestResponse.error(HpeExceptionEnum.MKDIR_FAILED);
            }
        }

        try {
            multipartFile.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            uri = getHost(new URI(httpServletRequest.getRequestURL()+""));
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(HpeExceptionEnum.UPLOAD_FAILED);
        }
        String originlImage = uri + "/upload/" + uuid + fileName;
        imageService.addImage(uuid.toString(),originlImage);

        return ApiRestResponse.success(originlImage);
    }

    @ResponseBody
    @GetMapping ("/estimate")
    public ApiRestResponse estimate(HttpServletRequest httpServletRequest) throws InterruptedException, HpeException, IOException {

        Image image = imageService.selectImageByName(uuid.toString());
        if(StringUtils.isEmpty(image.getImageName())){
            return ApiRestResponse.error(HpeExceptionEnum.NEED_UPLOAD);
        }

        if(!SSHUtil.login()){
            return ApiRestResponse.error(HpeExceptionEnum.SSH_LOGIN_ERROR);
        }

        try {
            uri = getHost(new URI(httpServletRequest.getRequestURL() + ""));
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(HpeExceptionEnum.UPLOAD_FAILED);
        }
//        command = Constant.COMMAND_VIDEO_PREFIX + " " + fileDir + newFileName + " " + Constant.COMMAND_VIDEO_SUFFIX + newFileName;

        String command = Constant.COMMAND_ESTIMATE_IMAGE_PREFIX + " " +
                fileDir + " " +
                Constant.COMMAND_ESTIMATE_IMAGE_SUFFIX;

        log.info("COMMAND : " + command);
        String result = SSHUtil.exec(command);
        log.info("SHELL : " + result);
        if(!result.startsWith("seconds.", result.length()-9)){
            return ApiRestResponse.error(result);
        }

        String skeletonImage = uri + "/output/skeletonimage/" + uuid + "_rendered.png";
        String json = uri + "/output/json/" + uuid + "_keypoints.json";

        UpdateImageRequest updateImageRequest = new UpdateImageRequest();
        updateImageRequest.setImageName(uuid.toString());
        updateImageRequest.setSkeletonImage(skeletonImage);
        updateImageRequest.setJson(json);
        imageService.updateImageByName(updateImageRequest);

        return ApiRestResponse.success(skeletonImage);
    }

    @ResponseBody
    @GetMapping("/reconstruct")
    public ApiRestResponse reconstruct(HttpServletRequest httpServletRequest) throws InterruptedException, HpeException, IOException {

        Image image = imageService.selectImageByName(uuid.toString());

        if(StringUtils.isEmpty(image.getImageName())){
            return ApiRestResponse.error(HpeExceptionEnum.NEED_UPLOAD);
        }

        if(!SSHUtil.login()){
            return ApiRestResponse.error(HpeExceptionEnum.SSH_LOGIN_ERROR);
        }

        try {
            uri = getHost(new URI(httpServletRequest.getRequestURL()+""));
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(HpeExceptionEnum.UPLOAD_FAILED);
        }

        String command = null;
        if(!StringUtils.isEmpty(image.getJson())){
            command = Constant.COMMAND_RECONSTRUCT_IMAGE_PREFIX + " " +
                    Constant.COMMAND_RECONSTRUCT_IMAGE_IMGPATH + uuid + "/" + fileName + " " +
                    Constant.COMMAND_RECONSTRUCT_IMAGE_OUTPUTIMGPATH + uuid + "_mesh" + suffixName + " " +
                    Constant.COMMAND_RECONSTRUCT_IMAGE_OUTPUTMESHPATH + uuid + ".obj" + " " +
                    Constant.COMMAND_RECONSTRUCT_IMAGE_JSONPATH + uuid + "_keypoints.json";

        }else {
            command = Constant.COMMAND_RECONSTRUCT_IMAGE_PREFIX + " " +
                    Constant.COMMAND_RECONSTRUCT_IMAGE_IMGPATH + uuid + "/" + fileName + " " +
                    Constant.COMMAND_RECONSTRUCT_IMAGE_OUTPUTIMGPATH + uuid + "_mesh" + suffixName + " " +
                    Constant.COMMAND_RECONSTRUCT_IMAGE_OUTPUTMESHPATH + uuid + ".obj";

        }
        log.info("COMMAND : " + command);
        String result = SSHUtil.exec(command);
        log.info("SHELL : " + result);

        if(!result.startsWith("done!", result.length()-6)){
            return ApiRestResponse.error(result);
        }

        String meshImage = uri + "/output/meshimage/" + uuid + "_mesh" + suffixName;
        String mesh = uri + "/output/mesh/" + uuid + ".obj";

        UpdateImageRequest updateImageRequest = new UpdateImageRequest();
        updateImageRequest.setImageName(uuid.toString());
        updateImageRequest.setMeshImage(meshImage);
        updateImageRequest.setMesh(mesh);
        imageService.updateImageByName(updateImageRequest);

        return ApiRestResponse.success();
    }

    @ResponseBody
    @GetMapping("/downloadjson")
    public void downloadJson( HttpServletResponse response) throws Throwable {
        Image image = imageService.selectImageByName(uuid.toString());
        if(image == null || StringUtils.isEmpty(image.getJson())){
            throw new HpeException(HpeExceptionEnum.NEED_ESTIMATE);
        }
        DownloadUtil.downloadByURL(image.getJson(),response,uuid.toString());
    }

    @ResponseBody
    @GetMapping("/downloadmesh")
    public void downloadMesh( HttpServletResponse response) throws Throwable {
        Image image = imageService.selectImageByName(uuid.toString());
        if(image == null || StringUtils.isEmpty(image.getMesh())){
            throw new HpeException(HpeExceptionEnum.NEED_RECONSTRUCT);
        }
        DownloadUtil.downloadByURL(image.getMesh(),response,uuid.toString());
    }

    @ResponseBody
    @GetMapping("/listall")
    public ApiRestResponse listAll(Integer pageNum, Integer pageSize){
        PageInfo pageInfo = imageService.selectAll(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
}
