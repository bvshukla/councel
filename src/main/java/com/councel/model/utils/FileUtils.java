package com.councel.model.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class FileUtils {
	static Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
			  "cloud_name", "dzpmyntml",
			  "api_key", "329833382453231",
			  "api_secret", "fiPriH1ydx51mov2wTy7OeovoSg"));
	public static void main(String[] args) throws Exception {
	    File file = new File("D:\\counsel\\profilepic\\ProfilePic_259_63245301878564.jpg");
	    
	    //File toUpload = new File("daisy.png");
	    Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
	    System.out.println(uploadResult);
	  }
	
	public static String uploadToCloudinary(File inputFile){
		Map uploadResult = new HashMap();
		try {
			uploadResult = cloudinary.uploader().upload(inputFile, ObjectUtils.emptyMap());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //System.out.println(uploadResult);
		String url = (String)uploadResult.get("url");
		return url;
	}
}
