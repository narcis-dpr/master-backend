package com.howtodojava.rest.controller;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.*;
import com.google.common.collect.ImmutableList;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.List;

public class CloudVisionUtils {
    private static final String APPLICATION_NAME = "DropWizardExample";

    private static final int MAX_LABELS = 3;

    public CloudVisionUtils(Vision vision) {
        this.vision = vision;
    }

    public static void printLabels(PrintStream out, Path imagePath, List<EntityAnnotation> labels) {
        out.printf("Labels for image %s:\n", imagePath);
        for (EntityAnnotation label : labels) {
            out.printf(
                    "\t%s (score: %.3f)\n",
                    label.getDescription(),
                    label.getScore());
        }
        if (labels.isEmpty()) {
            out.println("\tNo labels found.");
        }
    }

    public static Vision getVisionService() throws IOException, GeneralSecurityException {
        GoogleCredential credential =
                GoogleCredential.getApplicationDefault().createScoped(VisionScopes.all());
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    private final Vision vision;

    public List<EntityAnnotation> labelImage(String image, int maxResults) throws IOException {
        byte[] data = Base64.decodeBase64(image);

        AnnotateImageRequest request =
                new AnnotateImageRequest()
                        .setImage(new Image().encodeContent(data))
                        .setFeatures(ImmutableList.of(
                                new Feature()
                                        .setType("LABEL_DETECTION")
                                        .setMaxResults(maxResults)));
        Vision.Images.Annotate annotate =
                vision.images()
                        .annotate(new BatchAnnotateImagesRequest().setRequests(ImmutableList.of(request)));
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotate.setDisableGZipContent(true);

        BatchAnnotateImagesResponse batchResponse = annotate.execute();
        assert batchResponse.getResponses().size() == 1;
        AnnotateImageResponse response = batchResponse.getResponses().get(0);
        if (response.getLabelAnnotations() == null) {
            throw new IOException(
                    response.getError() != null
                            ? response.getError().getMessage()
                            : "Unknown error getting image annotations");
        }

        return response.getLabelAnnotations();
    }

//    public static void main(String[] args) throws IOException, GeneralSecurityException {
//        Path imagePath = Paths.get("/Users/narges/Documents/Andy/cookware-frying-pan-garlic-877226.jpg");
//
//        CloudVisionUtils app = new CloudVisionUtils(getVisionService());
//        printLabels(System.out, imagePath, app.labelImage(imagePath, MAX_LABELS));
//        List<EntityAnnotation> a;
//        a = app.labelImage(imagePath,MAX_LABELS);
//        Float b = 10.0f;
//
//        for (int i = 0;i <MAX_LABELS;i++){
//            String c = a.get(i).getDescription();
//            if (c.equals("Food")) {
//                System.out.println("    " + c);
//                b = a.get(i).getScore();
//
//                if (b >= 0.90)
//                    System.out.println("***********" + "    b:= " +b + "    Food    " + c);
//            }
//        }


   // }



}
