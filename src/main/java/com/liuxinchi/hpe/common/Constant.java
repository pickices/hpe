package com.liuxinchi.hpe.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

    public static final String SALT = "gsa'jS?D1d45eW.kp]";
    public static final String HPE_USER = "HPE_USER";

    public static String FILE_UPLOAD_DIR;
    public static String SSH_HOSTNAME;
    public static int SSH_PORT;
    public static String SSH_USER;
    public static String SSH_PASSWORD;

    public static String COMMAND_ESTIMATE_IMAGE_PREFIX;
    public static String COMMAND_ESTIMATE_IMAGE_SUFFIX;
    public static String COMMAND_ESTIMATE_VIDEO_PREFIX;
    public static String COMMAND_ESTIMATE_VIDEO_SUFFIX;

    public static String COMMAND_RECONSTRUCT_IMAGE_PREFIX;
    public static String COMMAND_RECONSTRUCT_IMAGE_IMGPATH;
    public static String COMMAND_RECONSTRUCT_IMAGE_OUTPUTIMGPATH;
    public static String COMMAND_RECONSTRUCT_IMAGE_OUTPUTMESHPATH;
    public static String COMMAND_RECONSTRUCT_IMAGE_JSONPATH;


    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    @Value("${ssh.port}")
    public void setSshPort(int sshPort) {
        SSH_PORT = sshPort;
    }

    @Value("${ssh.hostname}")
    public void setSshHostname(String sshHostname) {
        SSH_HOSTNAME = sshHostname;
    }

    @Value("${ssh.user}")
    public void setSshUser(String sshUser) {
        SSH_USER = sshUser;
    }

    @Value("${ssh.password}")
    public void setSshPassword(String sshPassword) {
        SSH_PASSWORD = sshPassword;
    }

    @Value("${command.estimate.image.prefix}")
    public void setCommandEstimateImagePrefix(String commandEstimateImagePrefix) {
        COMMAND_ESTIMATE_IMAGE_PREFIX = commandEstimateImagePrefix;
    }

    @Value("${command.estimate.image.suffix}")
    public void setCommandEstimateImageSuffix(String commandEstimateImageSuffix) {
        COMMAND_ESTIMATE_IMAGE_SUFFIX = commandEstimateImageSuffix;
    }

    @Value("${command.estimate.video.prefix}")
    public void setCommandEstimateVideoPrefix(String commandEstimateVideoPrefix) {
        COMMAND_ESTIMATE_VIDEO_PREFIX = commandEstimateVideoPrefix;
    }

    @Value("${command.estimate.video.suffix}")
    public void setCommandEstimateVideoSuffix(String commandEstimateVideoSuffix) {
        COMMAND_ESTIMATE_VIDEO_SUFFIX = commandEstimateVideoSuffix;
    }

    @Value("${command.reconstruct.image.prefix}")
    public void setCommandReconstructImagePrefix(String commandReconstructImagePrefix) {
        COMMAND_RECONSTRUCT_IMAGE_PREFIX = commandReconstructImagePrefix;
    }

    @Value("${command.reconstruct.image.imgpath}")
    public void setCommandReconstructImageImgpath(String commandReconstructImageImgpath) {
        COMMAND_RECONSTRUCT_IMAGE_IMGPATH = commandReconstructImageImgpath;
    }

    @Value("${command.reconstruct.image.outputimgpath}")
    public void setCommandReconstructImageOutputimgpath(String commandReconstructImageOutputimgpath) {
        COMMAND_RECONSTRUCT_IMAGE_OUTPUTIMGPATH = commandReconstructImageOutputimgpath;
    }

    @Value("${command.reconstruct.image.outputmeshpath}")
    public void setCommandReconstructImageOutputmeshpath(String commandReconstructImageOutputmeshpath) {
        COMMAND_RECONSTRUCT_IMAGE_OUTPUTMESHPATH = commandReconstructImageOutputmeshpath;
    }

    @Value("${command.reconstruct.image.jsonpath}")
    public void setCommandReconstructImageJsonpath(String commandReconstructImageJsonpath) {
        COMMAND_RECONSTRUCT_IMAGE_JSONPATH = commandReconstructImageJsonpath;
    }
}